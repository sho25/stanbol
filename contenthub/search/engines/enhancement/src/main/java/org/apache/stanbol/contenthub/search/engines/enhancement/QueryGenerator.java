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
name|search
operator|.
name|engines
operator|.
name|enhancement
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
name|keywordBasedAnnotationQuery
parameter_list|(
name|String
name|keyword
parameter_list|)
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX pf:<http://jena.hpl.hp.com/ARQ/property#>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX crv:<http://cms.item#>\n"
operator|+
literal|"SELECT distinct ?label ?score ?document ?type ?text ?ref ?refscore ?path\n"
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
literal|"  ?textEnh fise:extracted-from ?document .\n"
operator|+
literal|"  ?enhancement fise:entity-type ?type.\n"
operator|+
literal|"  ?enhancement fise:entity-reference ?ref.\n"
operator|+
literal|"  ?enhancement fise:confidence ?refscore.\n"
operator|+
literal|"  ?textEnh fise:selection-context ?text.\n"
operator|+
literal|"  (?label ?score)  pf:textMatch '+"
operator|+
name|normalizeKeyword
argument_list|(
name|keyword
argument_list|)
operator|+
literal|"'. \n"
operator|+
literal|"OPTIONAL {?enhancement crv:path ?path} \n"
operator|+
literal|"}\n"
operator|+
literal|"ORDER BY DESC(?extraction_time)"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
name|String
name|normalizeKeyword
parameter_list|(
name|String
name|keyword
parameter_list|)
block|{
return|return
name|keyword
operator|.
name|replace
argument_list|(
literal|"'"
argument_list|,
literal|""
argument_list|)
return|;
block|}
block|}
end_class

end_unit

