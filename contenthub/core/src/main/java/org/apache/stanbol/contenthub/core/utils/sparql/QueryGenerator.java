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
name|contenthub
operator|.
name|core
operator|.
name|utils
operator|.
name|sparql
package|;
end_package

begin_comment
comment|/**  *   * @author cihan  *   */
end_comment

begin_class
specifier|public
class|class
name|QueryGenerator
block|{
specifier|public
specifier|static
specifier|final
name|String
name|getExternalPlacesQuery
parameter_list|()
block|{
comment|/*          * String query = "PREFIX fise:<http://fise.iks-project.eu/ontology/>\n" +          * "PREFIX pf:<http://jena.hpl.hp.com/ARQ/property#>\n" +          * "PREFIX dc:<http://purl.org/dc/terms/>\n" + "SELECT distinct ?ref \n" + "WHERE {\n" +          * "  ?enhancement a fise:EntityAnnotation .\n" + "  ?enhancement dc:relation ?textEnh.\n" +          * "  ?enhancement fise:entity-label ?label.\n" + "  ?textEnh a fise:TextAnnotation .\n" +          * "  ?enhancement fise:entity-type ?type.\n" + "  ?enhancement2 dc:relation ?textEnh.\n" +          * "  ?enhancement2 fise:entity-type ?type.\n" + "  ?enhancement fise:entity-reference ?ref.\n" +          * "FILTER sameTerm(?type,<http://dbpedia.org/ontology/Place>) }\n" +          * "ORDER BY DESC(?extraction_time)";          */
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX pf:<http://jena.hpl.hp.com/ARQ/property#>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"SELECT distinct ?ref \n"
operator|+
literal|"WHERE {\n"
operator|+
literal|"  ?enhancement a fise:EntityAnnotation .\n"
operator|+
literal|"  ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|"  ?enhancement fise:entity-label ?label.\n"
operator|+
literal|"  ?textEnh a fise:TextAnnotation .\n"
operator|+
literal|"  ?enhancement fise:entity-type ?type.\n"
operator|+
literal|"  ?enhancement fise:entity-reference ?ref.\n"
operator|+
literal|"FILTER sameTerm(?type,<http://dbpedia.org/ontology/Place>) }\n"
operator|+
literal|"ORDER BY DESC(?extraction_time)"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|public
specifier|static
specifier|final
name|String
name|getEnhancementsOfContent
parameter_list|(
name|String
name|contentID
parameter_list|)
block|{
name|String
name|enhancementQuery
init|=
literal|"PREFIX enhancer:<http://fise.iks-project.eu/ontology/> "
operator|+
literal|"SELECT DISTINCT ?enhancement WHERE { "
operator|+
literal|"  ?enhancement enhancer:extracted-from ?enhID . "
operator|+
literal|"  FILTER sameTerm(?enhID,<"
operator|+
name|contentID
operator|+
literal|">) } "
decl_stmt|;
return|return
name|enhancementQuery
return|;
block|}
specifier|public
specifier|static
specifier|final
name|String
name|getRecentlyEnhancedDocuments
parameter_list|(
name|int
name|pageSize
parameter_list|,
name|int
name|offset
parameter_list|)
block|{
name|String
name|query
init|=
literal|"PREFIX enhancer:<http://fise.iks-project.eu/ontology/> "
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/> "
operator|+
literal|"SELECT DISTINCT ?content WHERE { "
operator|+
literal|"  ?enhancement enhancer:extracted-from ?content ."
operator|+
literal|"  ?enhancement dc:created ?extraction_time . } "
operator|+
literal|"ORDER BY DESC(?extraction_time) LIMIT %d OFFSET %d"
decl_stmt|;
return|return
name|String
operator|.
name|format
argument_list|(
name|query
argument_list|,
name|pageSize
argument_list|,
name|offset
argument_list|)
return|;
block|}
block|}
end_class

end_unit

