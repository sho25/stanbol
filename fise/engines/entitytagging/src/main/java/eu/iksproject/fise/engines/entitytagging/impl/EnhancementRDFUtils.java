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
name|entitytagging
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
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|Language
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
name|Literal
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfRepresentation
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfValueFactory
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Sign
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_comment
comment|/**  * Utility taken form the engine.autotagging bundle and adapted from  * using TagInfo to {@link Sign}.  * @author Rupert Westenthaler  * @author ogrisel (original utility)  *  */
end_comment

begin_class
specifier|public
class|class
name|EnhancementRDFUtils
block|{
comment|/** 	 * @param literalFactory the LiteralFactory to use  	 * @param graph the MGraph to use 	 * @param contentItemId the contentItemId the enhancement is extracted from 	 * @param relatedEnhancements enhancements this textAnnotation is related to 	 * @param entity the related entity 	 */
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
name|Sign
name|entity
parameter_list|)
block|{
comment|//1. check if the returned Entity does has a label -> if not return null
comment|//add labels (set only a single label. Use "en" if available!
name|Text
name|label
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Text
argument_list|>
name|labels
init|=
name|entity
operator|.
name|getRepresentation
argument_list|()
operator|.
name|getText
argument_list|(
name|Properties
operator|.
name|RDFS_LABEL
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
init|;
name|labels
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Text
name|actLabel
init|=
name|labels
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|label
operator|==
literal|null
condition|)
block|{
name|label
operator|=
name|actLabel
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
literal|"en"
operator|.
name|equals
argument_list|(
name|actLabel
operator|.
name|getLanguage
argument_list|()
argument_list|)
condition|)
block|{
name|label
operator|=
name|actLabel
expr_stmt|;
block|}
block|}
block|}
name|Literal
name|literal
decl_stmt|;
if|if
condition|(
name|label
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|label
operator|.
name|getLanguage
argument_list|()
operator|==
literal|null
condition|)
block|{
name|literal
operator|=
operator|new
name|PlainLiteralImpl
argument_list|(
name|label
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|literal
operator|=
operator|new
name|PlainLiteralImpl
argument_list|(
name|label
operator|.
name|getText
argument_list|()
argument_list|,
operator|new
name|Language
argument_list|(
name|label
operator|.
name|getLanguage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//Now create the entityAnnotation
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
name|entity
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
comment|//add the label parsed above
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
name|literal
argument_list|)
argument_list|)
expr_stmt|;
comment|//TODO: add real confidence values!
comment|// -> in case of SolrYards this will be a Lucene score and not within the range [0..1]
comment|// -> in case of SPARQL there will be no score information at all.
name|Object
name|score
init|=
name|entity
operator|.
name|getRepresentation
argument_list|()
operator|.
name|getFirst
argument_list|(
name|RdfResourceEnum
operator|.
name|resultScore
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|Double
name|scoreValue
init|=
operator|new
name|Double
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
comment|//use -1 if no score is available!
if|if
condition|(
name|score
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|scoreValue
operator|=
name|Double
operator|.
name|valueOf
argument_list|(
name|score
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
comment|//ignore
block|}
block|}
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
name|scoreValue
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Reference
argument_list|>
name|types
init|=
name|entity
operator|.
name|getRepresentation
argument_list|()
operator|.
name|getReferences
argument_list|(
name|Properties
operator|.
name|RDF_TYPE
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
init|;
name|types
operator|.
name|hasNext
argument_list|()
condition|;
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
name|types
operator|.
name|next
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//TODO: for now add the information about this entity to the graph
comment|// -> this might be replaced by some additional engine at the end
comment|//		RdfValueFactory rdfValueFactory = RdfValueFactory.getInstance();
comment|//		RdfRepresentation representation = rdfValueFactory.toRdfRepresentation(entity.getRepresentation());
comment|//		graph.addAll(representation.getRdfGraph());
return|return
name|entityAnnotation
return|;
block|}
block|}
end_class

end_unit

