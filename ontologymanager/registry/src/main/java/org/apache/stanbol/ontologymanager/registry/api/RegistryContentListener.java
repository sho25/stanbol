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
name|ontologymanager
operator|.
name|registry
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
name|stanbol
operator|.
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|RegistryItem
import|;
end_import

begin_comment
comment|/**  * Object that react to direct content requests to ontology registry items will implement this interface.  */
end_comment

begin_interface
specifier|public
interface|interface
name|RegistryContentListener
block|{
comment|/**      * Called when a registry item receives a request for some resource in its content, regardless whether the      * actual resource has been previously loaded.      *       * @param requestTarget      *            the registry item that was requested.      */
name|void
name|registryContentRequested
parameter_list|(
name|RegistryItem
name|requestTarget
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
