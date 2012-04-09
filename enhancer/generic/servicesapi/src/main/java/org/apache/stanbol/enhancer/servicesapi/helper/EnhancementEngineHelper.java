begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|servicesapi
operator|.
name|helper
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|java
operator|.
name|util
operator|.
name|Random
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|UUID
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
name|Triple
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
name|TripleCollection
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
name|TypedLiteral
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
name|rdf
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
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|TechnicalClasses
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
specifier|public
class|class
name|EnhancementEngineHelper
block|{
specifier|protected
specifier|static
name|Random
name|rng
init|=
operator|new
name|Random
argument_list|()
decl_stmt|;
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
name|EnhancementEngineHelper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|void
name|setSeed
parameter_list|(
name|long
name|seed
parameter_list|)
block|{
name|rng
operator|.
name|setSeed
argument_list|(
name|seed
argument_list|)
expr_stmt|;
block|}
comment|/**      * Create a new instance with the types enhancer:Enhancement and      * enhancer:TextAnnotation in the metadata-graph of the content      * item along with default properties (dc:creator and dc:created) and return      * the UriRef of the extraction so that engines can further add.      *      * @param ci the ContentItem being under analysis      * @param engine the Engine performing the analysis      *      * @return the URI of the new enhancement instance      */
specifier|public
specifier|static
name|UriRef
name|createTextEnhancement
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|)
block|{
return|return
name|createTextEnhancement
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|engine
argument_list|,
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Create a new instance with the types enhancer:Enhancement and      * enhancer:TextAnnotation in the parsed graph along with default properties      * (dc:creator, dc:created and enhancer:extracted-form) and return      * the UriRef of the extraction so that engines can further add.      *      * @param metadata the graph      * @param engine the engine      * @param contentItemId the id      *      * @return the URI of the new enhancement instance      */
specifier|public
specifier|static
name|UriRef
name|createTextEnhancement
parameter_list|(
name|MGraph
name|metadata
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|,
name|UriRef
name|contentItemId
parameter_list|)
block|{
name|UriRef
name|enhancement
init|=
name|createEnhancement
argument_list|(
name|metadata
argument_list|,
name|engine
argument_list|,
name|contentItemId
argument_list|)
decl_stmt|;
comment|//add the Text Annotation Type
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enhancement
argument_list|,
name|Properties
operator|.
name|RDF_TYPE
argument_list|,
name|TechnicalClasses
operator|.
name|ENHANCER_TEXTANNOTATION
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|enhancement
return|;
block|}
comment|/**      * Create a new instance with the types enhancer:Enhancement and      * enhancer:EntityAnnotation in the metadata-graph of the content      * item along with default properties (dc:creator and dc:created) and return      * the UriRef of the extraction so that engines can further add      *      * @param ci the ContentItem being under analysis      * @param engine the Engine performing the analysis      * @return the URI of the new enhancement instance      */
specifier|public
specifier|static
name|UriRef
name|createEntityEnhancement
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|)
block|{
return|return
name|createEntityEnhancement
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|engine
argument_list|,
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Create a new instance with the types enhancer:Enhancement and      * enhancer:EntityAnnotation in the parsed graph along with default properties      * (dc:creator, dc:created and enhancer:extracted-form) and return      * the UriRef of the extraction so that engines can further add.      *      * @param metadata the graph      * @param engine the engine      * @param contentItemId the id      *      * @return the URI of the new enhancement instance      */
specifier|public
specifier|static
name|UriRef
name|createEntityEnhancement
parameter_list|(
name|MGraph
name|metadata
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|,
name|UriRef
name|contentItemId
parameter_list|)
block|{
name|UriRef
name|enhancement
init|=
name|createEnhancement
argument_list|(
name|metadata
argument_list|,
name|engine
argument_list|,
name|contentItemId
argument_list|)
decl_stmt|;
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enhancement
argument_list|,
name|Properties
operator|.
name|RDF_TYPE
argument_list|,
name|TechnicalClasses
operator|.
name|ENHANCER_ENTITYANNOTATION
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|enhancement
return|;
block|}
comment|/**      * Create a new enhancement instance in the metadata-graph of the content      * item along with default properties (dc:creator and dc:created) and return      * the UriRef of the extraction so that engines can further add.      *      * @param ci the ContentItem being under analysis      * @param engine the Engine performing the analysis      *      * @return the URI of the new enhancement instance      */
specifier|protected
specifier|static
name|UriRef
name|createEnhancement
parameter_list|(
name|MGraph
name|metadata
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|,
name|UriRef
name|contentItemId
parameter_list|)
block|{
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|UriRef
name|enhancement
init|=
operator|new
name|UriRef
argument_list|(
literal|"urn:enhancement-"
operator|+
name|EnhancementEngineHelper
operator|.
name|randomUUID
argument_list|()
argument_list|)
decl_stmt|;
comment|//add the Enhancement Type
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enhancement
argument_list|,
name|Properties
operator|.
name|RDF_TYPE
argument_list|,
name|TechnicalClasses
operator|.
name|ENHANCER_ENHANCEMENT
argument_list|)
argument_list|)
expr_stmt|;
comment|//add the extracted from content item
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enhancement
argument_list|,
name|Properties
operator|.
name|ENHANCER_EXTRACTED_FROM
argument_list|,
name|contentItemId
argument_list|)
argument_list|)
expr_stmt|;
comment|// creation date
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enhancement
argument_list|,
name|Properties
operator|.
name|DC_CREATED
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// the engines that extracted the data
comment|// TODO: add some kind of versioning info for the extractor?
comment|// TODO: use a public dereferencing URI instead? that would allow for
comment|// explicit versioning too
comment|/* NOTE (Rupert Westenthaler 2010-05-26):          * The Idea is to use the  ComponentContext in the activate() method of          * an Enhancer to get the bundle name/version and use that as an          * URI for the creator.          * We would need to add getEnhancerID() method to the enhancer interface          * to access this information           */
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|enhancement
argument_list|,
name|Properties
operator|.
name|DC_CREATOR
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|engine
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|enhancement
return|;
block|}
comment|/**      * Create a new extraction instance in the metadata-graph of the content      * item along with default properties (dc:creator and dc:created) and return      * the UriRef of the extraction so that engines can further add      *      * @param ci the ContentItem being under analysis      * @param engine the Engine performing the analysis      * @return the URI of the new extraction instance      * @deprecated      * @see EnhancementEngineHelper#createEntityEnhancement(ContentItem, EnhancementEngine)      * @see EnhancementEngineHelper#createTextEnhancement(ContentItem, EnhancementEngine)      */
annotation|@
name|Deprecated
specifier|public
specifier|static
name|UriRef
name|createNewExtraction
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|EnhancementEngine
name|engine
parameter_list|)
block|{
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|MGraph
name|metadata
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|UriRef
name|extraction
init|=
operator|new
name|UriRef
argument_list|(
literal|"urn:extraction-"
operator|+
name|EnhancementEngineHelper
operator|.
name|randomUUID
argument_list|()
argument_list|)
decl_stmt|;
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|extraction
argument_list|,
name|Properties
operator|.
name|RDF_TYPE
argument_list|,
name|TechnicalClasses
operator|.
name|ENHANCER_EXTRACTION
argument_list|)
argument_list|)
expr_stmt|;
comment|// relate the extraction to the content item
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|extraction
argument_list|,
name|Properties
operator|.
name|ENHANCER_RELATED_CONTENT_ITEM
argument_list|,
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// creation date
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|extraction
argument_list|,
name|Properties
operator|.
name|DC_CREATED
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// the engines that extracted the data
comment|// TODO: add some kind of versioning info for the extractor?
comment|// TODO: use a public dereferencing URI instead? that would allow for
comment|// explicit versioning too
name|metadata
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|extraction
argument_list|,
name|Properties
operator|.
name|DC_CREATOR
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|engine
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|extraction
return|;
block|}
comment|/**      * Random UUID generator with re-seedable RNG for the tests.      *      * @return a new Random UUID      */
specifier|public
specifier|static
name|UUID
name|randomUUID
parameter_list|()
block|{
return|return
operator|new
name|UUID
argument_list|(
name|rng
operator|.
name|nextLong
argument_list|()
argument_list|,
name|rng
operator|.
name|nextLong
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Getter for the first typed literal value of the property for a resource.      *      * @param<T> the java class the literal value needs to be converted to.      * Note that the parsed LiteralFactory needs to support this conversion      * @param graph the graph used to query for the property value      * @param resource the resource      * @param property the property      * @param type the type the literal needs to be converted to      * @param literalFactory the literalFactory      * @return the value      */
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|T
name|get
parameter_list|(
name|TripleCollection
name|graph
parameter_list|,
name|NonLiteral
name|resource
parameter_list|,
name|UriRef
name|property
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|,
name|LiteralFactory
name|literalFactory
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|results
init|=
name|graph
operator|.
name|filter
argument_list|(
name|resource
argument_list|,
name|property
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|results
operator|.
name|hasNext
argument_list|()
condition|)
block|{
while|while
condition|(
name|results
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|result
init|=
name|results
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|getObject
argument_list|()
operator|instanceof
name|TypedLiteral
condition|)
block|{
return|return
name|literalFactory
operator|.
name|createObject
argument_list|(
name|type
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|result
operator|.
name|getObject
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Triple {} does not have a TypedLiteral as object! -> ignore"
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|info
argument_list|(
literal|"No value for {} and property {} had the requested Type {} -> return null"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|resource
block|,
name|property
block|,
name|type
block|}
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"No Triple found for {} and property {}! -> return null"
argument_list|,
name|resource
argument_list|,
name|property
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the typed literal values of the property for a resource      * @param<T> the java class the literal value needs to be converted to.      * Note that the parsed LiteralFactory needs to support this conversion      * @param graph the graph used to query for the property value      * @param resource the resource      * @param property the property      * @param type the type the literal needs to be converted to      * @param literalFactory the literalFactory      * @return the value      */
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|Iterator
argument_list|<
name|T
argument_list|>
name|getValues
parameter_list|(
name|TripleCollection
name|graph
parameter_list|,
name|NonLiteral
name|resource
parameter_list|,
name|UriRef
name|property
parameter_list|,
specifier|final
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|,
specifier|final
name|LiteralFactory
name|literalFactory
parameter_list|)
block|{
specifier|final
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|results
init|=
name|graph
operator|.
name|filter
argument_list|(
name|resource
argument_list|,
name|property
argument_list|,
literal|null
argument_list|)
decl_stmt|;
return|return
operator|new
name|Iterator
argument_list|<
name|T
argument_list|>
argument_list|()
block|{
comment|//TODO: dose not check if the object of the triple is of type UriRef
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|results
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|T
name|next
parameter_list|()
block|{
return|return
name|literalFactory
operator|.
name|createObject
argument_list|(
name|type
argument_list|,
operator|(
name|TypedLiteral
operator|)
name|results
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|results
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
comment|/**      * Getter for the first String literal value the property for a resource      * @param graph the graph used to query for the property value      * @param resource the resource      * @param property the property      * @return the value      */
specifier|public
specifier|static
name|String
name|getString
parameter_list|(
name|TripleCollection
name|graph
parameter_list|,
name|NonLiteral
name|resource
parameter_list|,
name|UriRef
name|property
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|results
init|=
name|graph
operator|.
name|filter
argument_list|(
name|resource
argument_list|,
name|property
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|results
operator|.
name|hasNext
argument_list|()
condition|)
block|{
while|while
condition|(
name|results
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|result
init|=
name|results
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|getObject
argument_list|()
operator|instanceof
name|Literal
condition|)
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|result
operator|.
name|getObject
argument_list|()
operator|)
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Triple {} does not have a literal as object! -> ignore"
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|info
argument_list|(
literal|"No Literal value for {} and property {} -> return null"
argument_list|,
name|resource
argument_list|,
name|property
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"No Triple found for "
operator|+
name|resource
operator|+
literal|" and property "
operator|+
name|property
operator|+
literal|"! -> return null"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the string literal values the property for a resource      * @param graph the graph used to query for the property value      * @param resource the resource      * @param property the property      * @return the value      */
specifier|public
specifier|static
name|Iterator
argument_list|<
name|String
argument_list|>
name|getStrings
parameter_list|(
name|TripleCollection
name|graph
parameter_list|,
name|NonLiteral
name|resource
parameter_list|,
name|UriRef
name|property
parameter_list|)
block|{
specifier|final
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|results
init|=
name|graph
operator|.
name|filter
argument_list|(
name|resource
argument_list|,
name|property
argument_list|,
literal|null
argument_list|)
decl_stmt|;
return|return
operator|new
name|Iterator
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
comment|//TODO: dose not check if the object of the triple is of type UriRef
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|results
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|next
parameter_list|()
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|results
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
operator|)
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|results
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
comment|/**      * Getter for the first value of the data type property for a resource      * @param graph the graph used to query for the property value      * @param resource the resource      * @param property the property      * @return the value      */
specifier|public
specifier|static
name|UriRef
name|getReference
parameter_list|(
name|TripleCollection
name|graph
parameter_list|,
name|NonLiteral
name|resource
parameter_list|,
name|UriRef
name|property
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|results
init|=
name|graph
operator|.
name|filter
argument_list|(
name|resource
argument_list|,
name|property
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|results
operator|.
name|hasNext
argument_list|()
condition|)
block|{
while|while
condition|(
name|results
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|result
init|=
name|results
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|getObject
argument_list|()
operator|instanceof
name|UriRef
condition|)
block|{
return|return
operator|(
name|UriRef
operator|)
name|result
operator|.
name|getObject
argument_list|()
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Triple "
operator|+
name|result
operator|+
literal|" does not have a UriRef as object! -> ignore"
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|info
argument_list|(
literal|"No UriRef value for {} and property {} -> return null"
argument_list|,
name|resource
argument_list|,
name|property
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"No Triple found for {} and property {}! -> return null"
argument_list|,
name|resource
argument_list|,
name|property
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Getter for the values of the data type property for a resource.      *      * @param graph the graph used to query for the property value      * @param resource the resource      * @param property the property      * @return The iterator over all the values (      */
specifier|public
specifier|static
name|Iterator
argument_list|<
name|UriRef
argument_list|>
name|getReferences
parameter_list|(
name|TripleCollection
name|graph
parameter_list|,
name|NonLiteral
name|resource
parameter_list|,
name|UriRef
name|property
parameter_list|)
block|{
specifier|final
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|results
init|=
name|graph
operator|.
name|filter
argument_list|(
name|resource
argument_list|,
name|property
argument_list|,
literal|null
argument_list|)
decl_stmt|;
return|return
operator|new
name|Iterator
argument_list|<
name|UriRef
argument_list|>
argument_list|()
block|{
comment|//TODO: dose not check if the object of the triple is of type UriRef
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|results
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|UriRef
name|next
parameter_list|()
block|{
return|return
operator|(
name|UriRef
operator|)
name|results
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|results
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
comment|/**      * Comparator that allows to sort a list/array of {@link EnhancementEngine}s      * based on there {@link ServiceProperties#ENHANCEMENT_ENGINE_ORDERING}.      */
specifier|public
specifier|static
specifier|final
name|Comparator
argument_list|<
name|EnhancementEngine
argument_list|>
name|EXECUTION_ORDER_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|EnhancementEngine
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|EnhancementEngine
name|engine1
parameter_list|,
name|EnhancementEngine
name|engine2
parameter_list|)
block|{
name|Integer
name|order1
init|=
name|getEngineOrder
argument_list|(
name|engine1
argument_list|)
decl_stmt|;
name|Integer
name|order2
init|=
name|getEngineOrder
argument_list|(
name|engine2
argument_list|)
decl_stmt|;
comment|//start with the highest number finish with the lowest ...
return|return
name|order1
operator|==
name|order2
condition|?
literal|0
else|:
name|order1
operator|<
name|order2
condition|?
literal|1
else|:
operator|-
literal|1
return|;
block|}
block|}
decl_stmt|;
comment|/**      * Gets the {@link ServiceProperties#ENHANCEMENT_ENGINE_ORDERING} value      * for the parsed EnhancementEngine. If the Engine does not implement the      * {@link ServiceProperties} interface or does not provide the      * {@link ServiceProperties#ENHANCEMENT_ENGINE_ORDERING} the       * {@link ServiceProperties#ORDERING_DEFAULT} is returned<p>      * This method is guaranteed to NOT return<code>null</code>.      * @param engine the engine      * @return the ordering      */
specifier|public
specifier|static
name|Integer
name|getEngineOrder
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"getOrder "
operator|+
name|engine
argument_list|)
expr_stmt|;
if|if
condition|(
name|engine
operator|instanceof
name|ServiceProperties
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ... implements ServiceProperties"
argument_list|)
expr_stmt|;
name|Object
name|value
init|=
operator|(
operator|(
name|ServiceProperties
operator|)
name|engine
operator|)
operator|.
name|getServiceProperties
argument_list|()
operator|.
name|get
argument_list|(
name|ServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"> value = "
operator|+
name|value
operator|+
literal|" "
operator|+
name|value
operator|.
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
name|value
operator|instanceof
name|Integer
condition|)
block|{
return|return
operator|(
name|Integer
operator|)
name|value
return|;
block|}
block|}
return|return
name|ServiceProperties
operator|.
name|ORDERING_DEFAULT
return|;
block|}
block|}
end_class

end_unit

