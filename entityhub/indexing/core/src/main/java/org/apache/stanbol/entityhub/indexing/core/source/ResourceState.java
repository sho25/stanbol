begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_comment
comment|/**  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|source
package|;
end_package

begin_comment
comment|/**  * State of resources managed by the ResourceLoader  * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|ResourceState
block|{
comment|/**      * Resources that are registered but not yet processed      */
name|REGISTERED
block|,
comment|/**      * Resources that are currently processed      */
name|LOADING
block|,
comment|/**      * Resources that where successfully loaded      */
name|LOADED
block|,
comment|/**      * Resources that where ignored      */
name|IGNORED
block|,
comment|/**      * Indicates an Error while processing a resource      */
name|ERROR
block|}
end_enum

end_unit

