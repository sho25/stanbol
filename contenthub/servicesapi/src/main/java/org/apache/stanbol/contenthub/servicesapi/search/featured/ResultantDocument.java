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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|featured
package|;
end_package

begin_comment
comment|/**  * This interface contains "getter" methods for a search result that can be passed in a {@link SearchResult}  * object. A resultant document corresponds to a content item stored in Contenthub.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ResultantDocument
block|{
comment|/**      * Returns the URI of the content item corresponding to this search result.      *       * @return URI of the search result      */
name|String
name|getLocalId
parameter_list|()
function_decl|;
comment|/**      * Returns the dereferencable URI of the content item corresponding to this search result. This URI of the      * HTML interface of the content item.      *       * @return Dereferencable URI of the search result      */
name|String
name|getDereferencableURI
parameter_list|()
function_decl|;
comment|/**      * Returns the mime type of the content item corresponding to this search result      *       * @return Mime type of the search result      */
name|String
name|getMimetype
parameter_list|()
function_decl|;
comment|/**      * Returns the count of the enhancements of the content item corresponding to this search result      *       * @return Enhancement count of the search result      */
name|long
name|getEnhancementCount
parameter_list|()
function_decl|;
comment|/**      * Returns the title of the content item corresponding to the this search result      *       * @return Title of the search result      */
name|String
name|getTitle
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

