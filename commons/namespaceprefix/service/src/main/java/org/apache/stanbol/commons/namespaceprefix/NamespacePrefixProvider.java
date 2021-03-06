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
name|commons
operator|.
name|namespaceprefix
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

begin_interface
specifier|public
interface|interface
name|NamespacePrefixProvider
block|{
comment|/**      * Getter for the namespace for the parsed prefix      * @param prefix the prefix. '' for the default namepace      * @return the namespace or<code>null</code> if the prefix is not known      */
name|String
name|getNamespace
parameter_list|(
name|String
name|prefix
parameter_list|)
function_decl|;
comment|/**      * Getter for the prefix for a namespace. Note that a namespace might be       * mapped to multiple prefixes      * @param namespace the namespace      * @return the prefix or<code>null</code>      */
name|String
name|getPrefix
parameter_list|(
name|String
name|namespace
parameter_list|)
function_decl|;
comment|/**      * Getter for all prefixes for the parsed namespace      * @param namespace the namespace      * @return the prefixes. An empty list if none      */
name|List
argument_list|<
name|String
argument_list|>
name|getPrefixes
parameter_list|(
name|String
name|namespace
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

