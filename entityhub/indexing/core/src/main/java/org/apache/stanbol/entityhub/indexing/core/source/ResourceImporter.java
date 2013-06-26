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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|source
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_comment
comment|/**  * The processor used by the resource loader to load registered resources  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ResourceImporter
block|{
comment|/**      * Processes an resource and returns the new state for that resource      * @param is the stream to read the resource from      * @param resourceName the name of the resource      * @return the State of the resource after the processing      * @throws IOException On any error while reading the resource. Throwing      * an IOException will set the state or the resource to      * {@link ResourceState#ERROR}      */
name|ResourceState
name|importResource
parameter_list|(
name|InputStream
name|is
parameter_list|,
name|String
name|resourceName
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

