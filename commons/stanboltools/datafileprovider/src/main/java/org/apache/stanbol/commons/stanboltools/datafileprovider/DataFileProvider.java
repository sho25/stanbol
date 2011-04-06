begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
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
name|stanboltools
operator|.
name|datafileprovider
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
comment|/**  * Used to provide read-only data files (indexes, models etc.) from various  * locations (bundle resources, filesystem folders etc.) allowing users to  * overrides default data files with their own.  *   * See STANBOL-146 for requirements.  */
end_comment

begin_interface
specifier|public
interface|interface
name|DataFileProvider
block|{
comment|/** Get the InputStream of the specified      *  data file, according to this provider's      *  priority rules.      *        *  @param bundleSymbolicName can be used to differentiate       *      between files which have the same name      *  @param filename name of the file to open      *  @param downloadExplanation explains how to get the "full"      *      version of the data file, in case we don't find it or in      *      case we supply a default small variant.      * @return InputStream to read the file, must be closed by       *      caller when done      * @throws IOException problem finding or reading the file      */
name|InputStream
name|getInputStream
parameter_list|(
name|String
name|bundleSymbolicName
parameter_list|,
name|String
name|filename
parameter_list|,
name|String
name|downloadExplanation
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

