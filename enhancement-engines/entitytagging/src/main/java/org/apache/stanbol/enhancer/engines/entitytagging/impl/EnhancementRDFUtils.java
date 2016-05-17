begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|entitytagging
operator|.
name|impl
package|;
end_package

begin_import
import|import static
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
name|rdf
operator|.
name|Properties
operator|.
name|DC_RELATION
import|;
end_import

begin_import
import|import static
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
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_CONFIDENCE
import|;
end_import

begin_import
import|import static
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
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_ENTITY_LABEL
import|;
end_import

begin_import
import|import static
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
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_ENTITY_REFERENCE
import|;
end_import

begin_import
import|import static
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
name|rdf
operator|.
name|Properties
operator|.
name|ENHANCER_ENTITY_TYPE
import|;
end_import

begin_import
import|import static
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
name|rdf
operator|.
name|Properties
operator|.
name|RDF_TYPE
import|;
end_import

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
name|commons
operator|.
name|rdf
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
name|commons
operator|.
name|rdf
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
name|commons
operator|.
name|rdf
operator|.
name|Graph
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
name|commons
operator|.
name|rdf
operator|.
name|BlankNodeOrIRI
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
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
name|entityhub
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Entity
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
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
comment|/**  * Utility taken form the engine.autotagging bundle and adapted from using TagInfo to {@link Entity}.  *   * @author Rupert Westenthaler  * @author ogrisel (original utility)  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|EnhancementRDFUtils
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|EnhancementRDFUtils
parameter_list|()
block|{}
comment|/**      * @param literalFactory      *            the LiteralFactory to use      * @param graph      *            the Graph to use      * @param contentItemId      *            the contentItemId the enhancement is extracted from      * @param relatedEnhancements      *            enhancements this textAnnotation is related to      * @param suggestion      *            the entity suggestion      * @param nameField the field used to extract the name      * @param lang the preferred language to include or<code>null</code> if none      */
specifier|public
specifier|static
name|IRI
name|writeEntityAnnotation
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|,
name|LiteralFactory
name|literalFactory
parameter_list|,
name|Graph
name|graph
parameter_list|,
name|IRI
name|contentItemId
parameter_list|,
name|Collection
argument_list|<
name|BlankNodeOrIRI
argument_list|>
name|relatedEnhancements
parameter_list|,
name|Suggestion
name|suggestion
parameter_list|,
name|String
name|nameField
parameter_list|,
name|String
name|lang
parameter_list|)
block|{
name|Representation
name|rep
init|=
name|suggestion
operator|.
name|getEntity
argument_list|()
operator|.
name|getRepresentation
argument_list|()
decl_stmt|;
comment|// 1. extract the "best label"
comment|//Start with the matched one
name|Text
name|label
init|=
name|suggestion
operator|.
name|getMatchedLabel
argument_list|()
decl_stmt|;
comment|//if the matched label is not in the requested language
name|boolean
name|langMatch
init|=
operator|(
name|lang
operator|==
literal|null
operator|&&
name|label
operator|.
name|getLanguage
argument_list|()
operator|==
literal|null
operator|)
operator|||
operator|(
name|label
operator|.
name|getLanguage
argument_list|()
operator|!=
literal|null
operator|&&
name|label
operator|.
name|getLanguage
argument_list|()
operator|.
name|startsWith
argument_list|(
name|lang
argument_list|)
operator|)
decl_stmt|;
comment|//search if a better label is available for this Entity
if|if
condition|(
operator|!
name|langMatch
condition|)
block|{
name|Iterator
argument_list|<
name|Text
argument_list|>
name|labels
init|=
name|rep
operator|.
name|getText
argument_list|(
name|nameField
argument_list|)
decl_stmt|;
while|while
condition|(
name|labels
operator|.
name|hasNext
argument_list|()
operator|&&
operator|!
name|langMatch
condition|)
block|{
name|Text
name|actLabel
init|=
name|labels
operator|.
name|next
argument_list|()
decl_stmt|;
name|langMatch
operator|=
operator|(
name|lang
operator|==
literal|null
operator|&&
name|actLabel
operator|.
name|getLanguage
argument_list|()
operator|==
literal|null
operator|)
operator|||
operator|(
name|actLabel
operator|.
name|getLanguage
argument_list|()
operator|!=
literal|null
operator|&&
name|actLabel
operator|.
name|getLanguage
argument_list|()
operator|.
name|startsWith
argument_list|(
name|lang
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|langMatch
condition|)
block|{
comment|//if the language matches ->
comment|//override the matched label
name|label
operator|=
name|actLabel
expr_stmt|;
block|}
block|}
block|}
comment|//else the matched label will be the best to use
name|Literal
name|literal
decl_stmt|;
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
comment|// Now create the entityAnnotation
name|IRI
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
name|BlankNodeOrIRI
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
name|DC_RELATION
argument_list|,
name|enhancement
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|IRI
name|entityUri
init|=
operator|new
name|IRI
argument_list|(
name|rep
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
name|ENHANCER_ENTITY_REFERENCE
argument_list|,
name|entityUri
argument_list|)
argument_list|)
expr_stmt|;
comment|// add the label parsed above
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|entityAnnotation
argument_list|,
name|ENHANCER_ENTITY_LABEL
argument_list|,
name|literal
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|suggestion
operator|.
name|getScore
argument_list|()
operator|!=
literal|null
condition|)
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
name|ENHANCER_CONFIDENCE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|suggestion
operator|.
name|getScore
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Iterator
argument_list|<
name|Reference
argument_list|>
name|types
init|=
name|rep
operator|.
name|getReferences
argument_list|(
name|RDF_TYPE
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
while|while
condition|(
name|types
operator|.
name|hasNext
argument_list|()
condition|)
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
name|ENHANCER_ENTITY_TYPE
argument_list|,
operator|new
name|IRI
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
comment|//add the name of the ReferencedSite that manages the Entity
if|if
condition|(
name|suggestion
operator|.
name|getEntity
argument_list|()
operator|.
name|getSite
argument_list|()
operator|!=
literal|null
condition|)
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
operator|new
name|IRI
argument_list|(
name|RdfResourceEnum
operator|.
name|site
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|suggestion
operator|.
name|getEntity
argument_list|()
operator|.
name|getSite
argument_list|()
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

