begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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

begin_comment
comment|/**  * Ideally this should be a dereferenceable ontology on the web. Given such   * an ontology a class of constant (similar to this) can be generated with  * the org.apache.clerezza:maven-ontologies-plugin  */
end_comment

begin_class
specifier|public
class|class
name|Ontology
block|{
comment|/**      * Resources of this type handle HTTP POST requests with a multipart message      * containing the content to be enhance as one field and optionally the      * requested enhancment chain in the other.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|MultiEnhancer
init|=
operator|new
name|IRI
argument_list|(
literal|"http://example.org/service-description${symbol_pound}MultiEnhancer"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

