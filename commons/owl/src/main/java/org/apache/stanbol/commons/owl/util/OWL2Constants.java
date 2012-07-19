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
name|commons
operator|.
name|owl
operator|.
name|util
package|;
end_package

begin_comment
comment|/**  * A collection of OWL 2 vocabulary terms that integrates those provided by Clerezza.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|OWL2Constants
block|{
comment|/**      * The namespace for the OWL language vocabulary.      */
specifier|public
specifier|static
specifier|final
name|String
name|_OWL_NS
init|=
literal|"http://www.w3.org/2002/07/owl#"
decl_stmt|;
comment|/**      * The owl:versionIRI annotation property that applies to resources of type owl:Ontology in OWL 2.      */
specifier|public
specifier|static
specifier|final
name|String
name|OWL_VERSION_IRI
init|=
name|_OWL_NS
operator|+
literal|"versionIRI"
decl_stmt|;
block|}
end_class

end_unit

