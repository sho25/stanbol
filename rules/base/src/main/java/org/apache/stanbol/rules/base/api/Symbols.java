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
name|rules
operator|.
name|base
operator|.
name|api
package|;
end_package

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
comment|/**  * It provides static methods in order to represent properties, classes and the namespace used in order to  * represent rules in the store  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|Symbols
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|Symbols
parameter_list|()
block|{}
specifier|public
specifier|static
name|String
name|NS
init|=
literal|"http://incubator.apache.org/stanbol/rules/"
decl_stmt|;
specifier|public
specifier|static
name|String
name|variablesPrefix
init|=
literal|"http://incubator.apache.org/stanbol/rules/variables/"
decl_stmt|;
specifier|public
specifier|static
name|IRI
name|description
init|=
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.org/stanbol/rules/description"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|IRI
name|hasRule
init|=
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.org/stanbol/rules/hasRule"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|IRI
name|ruleName
init|=
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.org/stanbol/rules/ruleName"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|IRI
name|ruleBody
init|=
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.org/stanbol/rules/ruleBody"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|IRI
name|ruleHead
init|=
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.org/stanbol/rules/ruleHead"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|IRI
name|Recipe
init|=
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.org/stanbol/rules/Recipe"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|IRI
name|Rule
init|=
operator|new
name|IRI
argument_list|(
literal|"http://incubator.apache.org/stanbol/rules/Rule"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

