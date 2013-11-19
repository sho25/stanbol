begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_expr_stmt
unit|#
name|set
argument_list|(
name|$symbol_pound
operator|=
literal|'#'
argument_list|)
expr|#
name|set
argument_list|(
name|$symbol_dollar
operator|=
literal|'$'
argument_list|)
expr|#
name|set
argument_list|(
name|$symbol_escape
operator|=
literal|'\' )
package|package
name|$
block|{
package|package
block|}
end_expr_stmt

begin_empty_stmt
empty_stmt|;
end_empty_stmt

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|PlainLiteralImpl
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
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|ontologies
operator|.
name|DCTERMS
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
name|ontologies
operator|.
name|RDFS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|Blob
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|EngineException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|InvalidContentException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|ServiceProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|ContentItemHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|impl
operator|.
name|AbstractEnhancementEngine
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|,
name|inherit
operator|=
literal|true
argument_list|)
annotation|@
name|Service
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
name|value
operator|=
literal|"${artifactId}-example"
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|ExampleEnhancer
extends|extends
name|AbstractEnhancementEngine
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
block|{
comment|/**      * Using slf4j for logging      */
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ExampleEnhancer
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * ServiceProperties are currently only used for automatic ordering of the       * execution of EnhancementEngines (e.g. by the WeightedChain implementation).      * Default ordering means that the engine is called after all engines that      * use a value< {@link ServiceProperties#ORDERING_CONTENT_EXTRACTION}      * and>= {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT}.      */
specifier|public
name|Map
name|getServiceProperties
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|Collections
operator|.
name|singletonMap
argument_list|(
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
name|ORDERING_DEFAULT
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * @return if and how (asynchronously) we can enhance a ContentItem      */
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
comment|// check if content is present
try|try
block|{
if|if
condition|(
operator|(
name|ci
operator|.
name|getBlob
argument_list|()
operator|==
literal|null
operator|)
operator|||
operator|(
name|ci
operator|.
name|getBlob
argument_list|()
operator|.
name|getStream
argument_list|()
operator|.
name|read
argument_list|()
operator|==
operator|-
literal|1
operator|)
condition|)
block|{
return|return
name|CANNOT_ENHANCE
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to get the text for "
operator|+
literal|"enhancement of content: "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|InvalidContentException
argument_list|(
name|this
argument_list|,
name|ci
argument_list|,
name|e
argument_list|)
throw|;
block|}
comment|// no reason why we should require to be executed synchronously
return|return
name|ENHANCE_ASYNC
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
try|try
block|{
comment|//get the (generated or submitted) text version of the ContentItem
name|Blob
name|textBlob
init|=
name|ContentItemHelper
operator|.
name|getBlob
argument_list|(
name|ci
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
literal|"text/plain"
argument_list|)
argument_list|)
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|String
name|content
init|=
name|ContentItemHelper
operator|.
name|getText
argument_list|(
name|textBlob
argument_list|)
decl_stmt|;
comment|// get the metadata graph
name|MGraph
name|metadata
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
comment|// update some sample data
name|UriRef
name|textAnnotation
init|=
name|EnhancementEngineHelper
operator|.
name|createTextEnhancement
argument_list|(
name|ci
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|DCTERMS
operator|.
name|type
argument_list|,
operator|new
name|UriRef
argument_list|(
literal|"http://example.org/ontology/LengthEnhancement"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|RDFS
operator|.
name|comment
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
literal|"A text of "
operator|+
name|content
operator|.
name|length
argument_list|()
operator|+
literal|" charaters"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Exception reading content item."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|InvalidContentException
argument_list|(
literal|"Exception reading content item."
argument_list|,
name|ex
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit
