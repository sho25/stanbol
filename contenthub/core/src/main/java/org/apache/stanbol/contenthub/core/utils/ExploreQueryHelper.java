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
name|contenthub
operator|.
name|core
operator|.
name|utils
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * Includes static methods that returns SPARQL query strings Queries are  * executed on graph of entities to find their types and extract semantic  * information according to entity type's  *   * @author srdc  *   */
end_comment

begin_class
specifier|public
class|class
name|ExploreQueryHelper
block|{
specifier|public
specifier|final
specifier|static
name|String
index|[]
name|placeTypedProperties
init|=
block|{
literal|"country"
block|,
literal|"largestCity"
block|,
literal|"city"
block|,
literal|"state"
block|,
literal|"capital"
block|,
literal|"isPartOf"
block|,
literal|"part"
block|,
literal|"deathPlace"
block|,
literal|"birthPlace"
block|,
literal|"location"
block|}
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|String
index|[]
name|personTypedProperties
init|=
block|{
literal|"leader"
block|,
literal|"leaderName"
block|,
literal|"child"
block|,
literal|"spouse"
block|,
literal|"partner"
block|,
literal|"president"
block|}
decl_stmt|;
specifier|public
specifier|final
specifier|static
name|String
index|[]
name|organizationTypedProperties
init|=
block|{
literal|"leaderParty"
block|,
literal|"affiliation"
block|,
literal|"team"
block|,
literal|"party"
block|,
literal|"otherParty"
block|,
literal|"associatedBand"
block|}
decl_stmt|;
comment|/** 	 * Used to find all rdf:type's of the entity 	 *  	 * @return is SPARQL query finds rdf:type's of an entity 	 */
specifier|public
specifier|final
specifier|static
name|String
name|entityTypeExtracterQuery
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX j.3:<http://www.iks-project.eu/ontology/rick/model/>\n"
operator|+
literal|"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
operator|+
literal|"SELECT DISTINCT ?type\n"
operator|+
literal|"WHERE {\n"
operator|+
literal|"?entity j.3:about ?description.\n"
operator|+
literal|"?description rdf:type ?type\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
comment|/** 	 * Creates a query which finds place type entities;<br> country<br> capital<br> 	 * largestCity<br> isPartOf<br> part<br> birthPlace<br> deathPlace<br> location<br> ... 	 * optionally 	 *  	 * @return resulted query 	 */
specifier|public
specifier|final
specifier|static
name|String
name|relatedPlaceQuery
parameter_list|()
block|{
name|StringBuilder
name|query
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
argument_list|)
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"PREFIX dbp.ont:<http://dbpedia.org/ontology/>\n"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"PREFIX about.ns:<http://www.iks-project.eu/ontology/rick/model/>\n"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"SELECT DISTINCT "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|placeTypedProperties
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|query
operator|.
name|append
argument_list|(
literal|" ?"
operator|+
name|placeTypedProperties
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|query
operator|.
name|append
argument_list|(
literal|" \n"
operator|+
literal|"WHERE {\n ?entity about.ns:about ?description .\n"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|placeTypedProperties
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|var
init|=
name|placeTypedProperties
index|[
name|i
index|]
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"OPTIONAL { ?description dbp.ont:"
operator|+
name|var
operator|+
literal|" ?"
operator|+
name|var
operator|+
literal|" }\n"
argument_list|)
expr_stmt|;
block|}
name|query
operator|.
name|append
argument_list|(
literal|"}\n"
argument_list|)
expr_stmt|;
return|return
name|query
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** 	 * creates a query that finds the person typed entities; 	 *<br> president 	 *<br> spouse 	 *<br> leader 	 *<br> ... optionally 	 * @return resulted query string 	 */
specifier|public
specifier|final
specifier|static
name|String
name|relatedPersonQuery
parameter_list|()
block|{
name|StringBuilder
name|query
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
argument_list|)
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"PREFIX dbp.ont:<http://dbpedia.org/ontology/>\n"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"PREFIX about.ns:<http://www.iks-project.eu/ontology/rick/model/>\n"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"SELECT DISTINCT "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|personTypedProperties
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|query
operator|.
name|append
argument_list|(
literal|" ?"
operator|+
name|personTypedProperties
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|query
operator|.
name|append
argument_list|(
literal|" \n"
operator|+
literal|"WHERE {\n ?entity about.ns:about ?description .\n"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|personTypedProperties
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|var
init|=
name|personTypedProperties
index|[
name|i
index|]
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"OPTIONAL { ?description dbp.ont:"
operator|+
name|var
operator|+
literal|" ?"
operator|+
name|var
operator|+
literal|" }\n"
argument_list|)
expr_stmt|;
block|}
name|query
operator|.
name|append
argument_list|(
literal|"}\n"
argument_list|)
expr_stmt|;
return|return
name|query
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** 	 * creates a query that finds organization typed related entities; 	 *<br> associatedBand 	 *<br> team 	 *<br> party 	 *<br> ... optionally 	 * @return resulted query String 	 */
specifier|public
specifier|final
specifier|static
name|String
name|relatedOrganizationQuery
parameter_list|()
block|{
name|StringBuilder
name|query
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
argument_list|)
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"PREFIX dbp.ont:<http://dbpedia.org/ontology/>\n"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"PREFIX about.ns:<http://www.iks-project.eu/ontology/rick/model/>\n"
argument_list|)
expr_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"SELECT DISTINCT "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|personTypedProperties
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|query
operator|.
name|append
argument_list|(
literal|" ?"
operator|+
name|personTypedProperties
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|query
operator|.
name|append
argument_list|(
literal|" \n"
operator|+
literal|"WHERE {\n ?entity about.ns:about ?description .\n"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|personTypedProperties
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|var
init|=
name|personTypedProperties
index|[
name|i
index|]
decl_stmt|;
name|query
operator|.
name|append
argument_list|(
literal|"OPTIONAL { ?description dbp.ont:"
operator|+
name|var
operator|+
literal|" ?"
operator|+
name|var
operator|+
literal|" }\n"
argument_list|)
expr_stmt|;
block|}
name|query
operator|.
name|append
argument_list|(
literal|"}\n"
argument_list|)
expr_stmt|;
return|return
name|query
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

