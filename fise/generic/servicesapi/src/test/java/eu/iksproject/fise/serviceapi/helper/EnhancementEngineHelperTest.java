begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|serviceapi
operator|.
name|helper
package|;
end_package

begin_import
import|import static
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|FISE_RELATED_CONTENT_ITEM
import|;
end_import

begin_import
import|import static
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
operator|.
name|RDF_TYPE
import|;
end_import

begin_import
import|import static
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|TechnicalClasses
operator|.
name|FISE_EXTRACTION
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|SimpleMGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|TripleImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|EngineException
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
import|;
end_import

begin_class
specifier|public
class|class
name|EnhancementEngineHelperTest
block|{
specifier|public
specifier|static
class|class
name|MyEngine
implements|implements
name|EnhancementEngine
block|{
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
return|return
literal|0
return|;
block|}
specifier|public
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
comment|// do nothing
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEnhancementEngineHelper
parameter_list|()
throws|throws
name|Exception
block|{
name|ContentItem
name|ci
init|=
operator|new
name|ContentItem
argument_list|()
block|{
name|MGraph
name|mgraph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|InputStream
name|getStream
parameter_list|()
block|{
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
literal|"There is content"
operator|.
name|getBytes
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
literal|"text/plain"
return|;
block|}
annotation|@
name|Override
specifier|public
name|MGraph
name|getMetadata
parameter_list|()
block|{
return|return
name|mgraph
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
literal|"urn:test:contentItem"
return|;
block|}
block|}
decl_stmt|;
name|EnhancementEngine
name|engine
init|=
operator|new
name|MyEngine
argument_list|()
decl_stmt|;
name|UriRef
name|extraction
init|=
name|EnhancementEngineHelper
operator|.
name|createNewExtraction
argument_list|(
name|ci
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|MGraph
name|metadata
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|metadata
operator|.
name|contains
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|extraction
argument_list|,
name|FISE_RELATED_CONTENT_ITEM
argument_list|,
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|metadata
operator|.
name|contains
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|extraction
argument_list|,
name|RDF_TYPE
argument_list|,
name|FISE_EXTRACTION
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// and so on
block|}
block|}
end_class

end_unit

