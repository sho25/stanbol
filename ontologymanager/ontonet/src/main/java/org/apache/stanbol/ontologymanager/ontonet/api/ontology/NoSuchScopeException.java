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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ontology
package|;
end_package

begin_comment
comment|/**  * Thrown whenever an operation on a scope that has not been registered is  * thrown.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|NoSuchScopeException
extends|extends
name|RuntimeException
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|6339531579406287445L
decl_stmt|;
specifier|private
name|String
name|scopeID
init|=
literal|null
decl_stmt|;
specifier|public
name|NoSuchScopeException
parameter_list|(
name|String
name|scopeID
parameter_list|)
block|{
name|this
operator|.
name|scopeID
operator|=
name|scopeID
expr_stmt|;
block|}
specifier|public
name|String
name|getScopeId
parameter_list|()
block|{
return|return
name|scopeID
return|;
block|}
block|}
end_class

end_unit

