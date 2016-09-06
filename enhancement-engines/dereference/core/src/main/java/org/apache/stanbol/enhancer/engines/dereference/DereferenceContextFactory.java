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
name|enhancer
operator|.
name|engines
operator|.
name|dereference
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * Factory used by the {@link EntityDereferenceEngine} to create  * {@link DereferenceContext} instances for enhancement requests.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|DereferenceContextFactory
block|{
comment|/**      * Creates a Dereference Context for the given DereferenceEngine configuration      * and the request specific enhancement properties      * @param engine the engine the context is built for      * @param enhancementProperties the request specific enhancement properties      * @return the dereference context      * @throws DereferenceConfigurationException if the request specific configuration      * is invalid or not supported.      */
name|DereferenceContext
name|createContext
parameter_list|(
name|EntityDereferenceEngine
name|engine
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|enhancementProperties
parameter_list|)
throws|throws
name|DereferenceConfigurationException
function_decl|;
block|}
end_interface

end_unit

