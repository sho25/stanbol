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
name|dbpspotlight
operator|.
name|model
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
name|engines
operator|.
name|dbpspotlight
operator|.
name|utils
operator|.
name|XMLParser
operator|.
name|getElementsByTagName
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|RDFTerm
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
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_comment
comment|/**  * Contains a result given by DBPedia Spotlight..  *   *   * @author<a href="mailto:iavor.jelev@babelmonkeys.com">Iavor Jelev</a>  */
end_comment

begin_class
specifier|public
class|class
name|Annotation
block|{
comment|/* 	 * TODO (Note by rwesten 2012-08-22)  	 *  	 * Added here functionality to extract DBpedia 	 * Ontoloty types for Annotations. This is mainly to 	 * choose the best dc:type for fise:TextAnnotations 	 * created for Annotation. 	 *  	 * This is based on the assumption that the most generic 	 * dbpedia type is always the last one in the returned list. 	 *  	 * In addition "DBpedia:TopicalConcept" is ignored first 	 * as it seams not to be used by dbpedia.org and second 	 * because it is always parsed last (even after schema 	 * and freebase types) and would therefore be considered 	 * as the most generic dbpedia type. 	 *  	 * I do not like this solution and would like to find 	 * a better solution for that 	 */
comment|/** 	 * Allows to add DBpedia Ontology types that should be 	 * ignored by {@link #getDbpediaTypeNames()}.<p> 	 * Introduced this to ignore the "TopicalConcept" 	 * type. 	 */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|IGNORED_DBP_TYPES
decl_stmt|;
static|static
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|ignored
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|ignored
operator|.
name|add
argument_list|(
literal|"DBpedia:TopicalConcept"
argument_list|)
expr_stmt|;
name|IGNORED_DBP_TYPES
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|ignored
argument_list|)
expr_stmt|;
block|}
specifier|public
name|RDFTerm
name|uri
decl_stmt|;
comment|//TODO: change this to a list with the parsed types
comment|//      Processing of XML results should be done during parsing
specifier|public
name|String
name|types
decl_stmt|;
specifier|public
name|Integer
name|support
decl_stmt|;
comment|//NOTE rwesten: changed this to embed a SurfaceFrom so that i
comment|//     can reuse code for creating fise:TextAnnotations
specifier|public
name|SurfaceForm
name|surfaceForm
decl_stmt|;
specifier|public
name|Double
name|similarityScore
decl_stmt|;
specifier|public
name|Double
name|percentageOfSecondRank
decl_stmt|;
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getTypeNames
parameter_list|()
block|{
if|if
condition|(
name|types
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|t
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|String
index|[]
name|typex
init|=
name|types
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|type
range|:
name|typex
control|)
block|{
comment|// make the returned types referenceable
name|String
name|deref
init|=
name|type
operator|.
name|replace
argument_list|(
literal|"DBpedia:"
argument_list|,
literal|"http://dbpedia.org/ontology/"
argument_list|)
operator|.
name|replace
argument_list|(
literal|"Freebase:"
argument_list|,
literal|"http://www.freebase.com/schema"
argument_list|)
operator|.
name|replace
argument_list|(
literal|"Schema:"
argument_list|,
literal|"http://www.schema.org/"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|deref
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|t
operator|.
name|add
argument_list|(
name|deref
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|t
return|;
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
comment|/** 	 * Getter for the dbpedia ontology types excluding {@link #IGNORED_DBP_TYPES} 	 * @return the types or an empty list if none 	 */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getDbpediaTypeNames
parameter_list|()
block|{
if|if
condition|(
name|types
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|t
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|String
index|[]
name|typex
init|=
name|types
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|type
range|:
name|typex
control|)
block|{
if|if
condition|(
operator|!
name|IGNORED_DBP_TYPES
operator|.
name|contains
argument_list|(
name|type
argument_list|)
operator|&&
name|type
operator|.
name|startsWith
argument_list|(
literal|"DBpedia:"
argument_list|)
condition|)
block|{
name|t
operator|.
name|add
argument_list|(
name|type
operator|.
name|replace
argument_list|(
literal|"DBpedia:"
argument_list|,
literal|"http://dbpedia.org/ontology/"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|t
return|;
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"[uri=%s, support=%i, types=%s, surfaceForm=\"%s\", similarityScore=%d, percentageOfSecondRank=%d]"
argument_list|,
name|uri
argument_list|,
name|support
argument_list|,
name|types
argument_list|,
name|surfaceForm
argument_list|,
name|similarityScore
argument_list|,
name|percentageOfSecondRank
argument_list|)
return|;
block|}
comment|/** 	 * This method parses allAnnotations from the parsed XML {@link Document} 	 *  	 * @param xmlDoc 	 *            A XML document containing annotations. 	 * @return a Collection<DBPSLAnnotation> with all annotations 	 */
specifier|public
specifier|static
name|Collection
argument_list|<
name|Annotation
argument_list|>
name|parseAnnotations
parameter_list|(
name|Document
name|xmlDoc
parameter_list|)
block|{
name|NodeList
name|nList
init|=
name|getElementsByTagName
argument_list|(
name|xmlDoc
argument_list|,
literal|"RDFTerm"
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|Annotation
argument_list|>
name|dbpslAnnos
init|=
operator|new
name|HashSet
argument_list|<
name|Annotation
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|temp
init|=
literal|0
init|;
name|temp
operator|<
name|nList
operator|.
name|getLength
argument_list|()
condition|;
name|temp
operator|++
control|)
block|{
name|Annotation
name|dbpslann
init|=
operator|new
name|Annotation
argument_list|()
decl_stmt|;
name|Element
name|node
init|=
operator|(
name|Element
operator|)
name|nList
operator|.
name|item
argument_list|(
name|temp
argument_list|)
decl_stmt|;
name|dbpslann
operator|.
name|uri
operator|=
operator|new
name|IRI
argument_list|(
name|node
operator|.
name|getAttribute
argument_list|(
literal|"URI"
argument_list|)
argument_list|)
expr_stmt|;
name|dbpslann
operator|.
name|support
operator|=
operator|(
operator|new
name|Integer
argument_list|(
name|node
operator|.
name|getAttribute
argument_list|(
literal|"support"
argument_list|)
argument_list|)
operator|)
operator|.
name|intValue
argument_list|()
expr_stmt|;
name|dbpslann
operator|.
name|types
operator|=
name|node
operator|.
name|getAttribute
argument_list|(
literal|"types"
argument_list|)
expr_stmt|;
name|dbpslann
operator|.
name|surfaceForm
operator|=
operator|new
name|SurfaceForm
argument_list|(
operator|new
name|Integer
argument_list|(
name|node
operator|.
name|getAttribute
argument_list|(
literal|"offset"
argument_list|)
argument_list|)
argument_list|,
name|node
operator|.
name|getAttribute
argument_list|(
literal|"surfaceForm"
argument_list|)
argument_list|)
expr_stmt|;
comment|//set the type of the surface form
name|List
argument_list|<
name|String
argument_list|>
name|dbpediaTypes
init|=
name|dbpslann
operator|.
name|getDbpediaTypeNames
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|dbpediaTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//set the last type in the list - the most general one - as type
comment|//for the surface form
name|dbpslann
operator|.
name|surfaceForm
operator|.
name|type
operator|=
name|dbpediaTypes
operator|.
name|get
argument_list|(
name|dbpediaTypes
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|dbpslann
operator|.
name|similarityScore
operator|=
operator|(
operator|new
name|Double
argument_list|(
name|node
operator|.
name|getAttribute
argument_list|(
literal|"similarityScore"
argument_list|)
argument_list|)
operator|)
operator|.
name|doubleValue
argument_list|()
expr_stmt|;
name|dbpslann
operator|.
name|percentageOfSecondRank
operator|=
operator|(
operator|new
name|Double
argument_list|(
name|node
operator|.
name|getAttribute
argument_list|(
literal|"percentageOfSecondRank"
argument_list|)
argument_list|)
operator|)
operator|.
name|doubleValue
argument_list|()
expr_stmt|;
name|dbpslAnnos
operator|.
name|add
argument_list|(
name|dbpslann
argument_list|)
expr_stmt|;
block|}
return|return
name|dbpslAnnos
return|;
block|}
block|}
end_class

end_unit

