begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|autotagging
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|LiteralFactory
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
name|NonLiteral
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
name|TripleImpl
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|autotagging
operator|.
name|TagInfo
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
name|rdf
operator|.
name|Properties
import|;
end_import

begin_class
specifier|public
class|class
name|EnhancementRDFUtils
block|{
specifier|private
name|EnhancementRDFUtils
parameter_list|()
block|{     }
comment|/** 	 * @param literalFactory the LiteralFactory to use  	 * @param graph the MGraph to use 	 * @param contentItemId the contentItemId the enhancement is extracted from 	 * @param relatedEnhancements enhancements this textAnnotation is related to 	 * @param tag the related entity 	 */
specifier|public
specifier|static
name|UriRef
name|writeEntityAnnotation
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|,
name|LiteralFactory
name|literalFactory
parameter_list|,
name|MGraph
name|graph
parameter_list|,
name|UriRef
name|contentItemId
parameter_list|,
name|Collection
argument_list|<
name|NonLiteral
argument_list|>
name|relatedEnhancements
parameter_list|,
name|TagInfo
name|tag
parameter_list|)
block|{
name|UriRef
name|entityAnnotation
init|=
name|EnhancementEngineHelper
operator|.
name|createEntityEnhancement
argument_list|(
name|graph
argument_list|,
name|engine
argument_list|,
name|contentItemId
argument_list|)
decl_stmt|;
comment|// first relate this entity annotation to the text annotation(s)
for|for
control|(
name|NonLiteral
name|enhancement
range|:
name|relatedEnhancements
control|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|DC_RELATION
argument_list|,
name|enhancement
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|UriRef
name|entityUri
init|=
operator|new
name|UriRef
argument_list|(
name|tag
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
comment|// add the link to the referred entity
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|FISE_ENTITY_REFERENCE
argument_list|,
name|entityUri
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|FISE_ENTITY_LABEL
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|tag
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|FISE_CONFIDENCE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|tag
operator|.
name|getConfidence
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|entityType
range|:
name|tag
operator|.
name|getType
argument_list|()
control|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|Properties
operator|.
name|FISE_ENTITY_TYPE
argument_list|,
operator|new
name|UriRef
argument_list|(
name|entityType
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|entityAnnotation
return|;
block|}
block|}
end_class

end_unit

