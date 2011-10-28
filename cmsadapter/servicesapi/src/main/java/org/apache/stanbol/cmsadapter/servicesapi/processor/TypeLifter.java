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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|processor
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|OntologyResourceHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|MappingEngine
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessException
import|;
end_import

begin_comment
comment|/**  * This interface provides a uniform way to semantically lift node/object type definitions in content  * management systems.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|TypeLifter
block|{
comment|/**      * This method takes all node/object type definitions in the repository and creates related ontological      * resources namely classes, object and data properties. It creates a class for each object/node type and      * object properties for property definitions having types PATH, REFERENCE, etc.; and data properties for      * property definitions takes literal values.      *       * @param mappingEngine      *            is the {@link MappingEngine} instance of which acts as context for the implementations of      *            this interface. It provides context variables such as Session to access repository or      *            {@link OntologyResourceHelper} to create ontology resources.      * @throws RepositoryAccessException      */
name|void
name|liftNodeTypes
parameter_list|(
name|MappingEngine
name|mappingEngine
parameter_list|)
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      * Takes a protocol type e.g JCR/CMIS and returns whether implementation of this interface is capable of      * lifting type definitions through the specified protocol      *       * @param type      *            protocol type e.g JCR/CMIS      * @return      */
name|boolean
name|canLift
parameter_list|(
name|String
name|type
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

