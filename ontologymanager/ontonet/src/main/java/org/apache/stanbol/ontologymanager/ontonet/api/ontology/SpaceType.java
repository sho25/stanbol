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
name|ontonet
operator|.
name|api
operator|.
name|ontology
package|;
end_package

begin_comment
comment|/**  * The possible types of ontology spaces managed by OntoNet.  */
end_comment

begin_enum
specifier|public
enum|enum
name|SpaceType
block|{
comment|/**      * Denotes a core space (1..1). It is instantiated upon creation of the scope.      */
name|CORE
argument_list|(
literal|"core"
argument_list|)
block|,
comment|/**      * Denotes a custom space (0..1).      */
name|CUSTOM
argument_list|(
literal|"custom"
argument_list|)
block|,
comment|/**      * Denotes a session space (0..n).      *       * @deprecated no session spaces should created anymore.      */
name|SESSION
argument_list|(
literal|"session"
argument_list|)
block|;
name|SpaceType
parameter_list|(
name|String
name|suffix
parameter_list|)
block|{
name|this
operator|.
name|suffix
operator|=
name|suffix
expr_stmt|;
block|}
specifier|private
name|String
name|suffix
decl_stmt|;
comment|/**      * Returns the preferred string to be attached to an ontology scope IRI (assuming it ends with a hash or      * slash character) in order to reference the included ontology space.      *       * @return the preferred suffix for this space type.      */
specifier|public
name|String
name|getIRISuffix
parameter_list|()
block|{
return|return
name|suffix
return|;
block|}
block|}
end_enum

end_unit

