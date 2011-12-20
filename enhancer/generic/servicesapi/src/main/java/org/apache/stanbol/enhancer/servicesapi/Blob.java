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
name|servicesapi
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

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
comment|/**  * represents a finite sequence of bytes associated to a mime-type  */
end_comment

begin_interface
specifier|public
interface|interface
name|Blob
block|{
comment|/** 	 * Getter for the mime-type of the content. The returned string MUST only 	 * contain the "{type}/{sub-type}". No wildcards MUST BE used. 	 * @return the mime-type of his blog 	 */
name|String
name|getMimeType
parameter_list|()
function_decl|;
comment|/** 	 * @return a stream of the data of this blog 	 */
name|InputStream
name|getStream
parameter_list|()
function_decl|;
comment|/** 	 * Additional parameters parsed with the mime-type. Typically the 'charset' 	 * used to encode text is parsed as a parameter. 	 * @return read only map with additional parameter for the used mime-type. 	 */
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getParameter
parameter_list|()
function_decl|;
comment|/** 	 * The size of the Content in bytes or a negative value if not known 	 */
name|long
name|getContentLength
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

