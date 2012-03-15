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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|response
operator|.
name|FacetField
operator|.
name|Count
import|;
end_import

begin_comment
comment|/**  * This interface defines the structure of facets that are obtained from underlying Solr index for the search  * operation  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|FacetResult
block|{
comment|/**      * Returns the full name of the facet.      *       * @return      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Returns the name of the facet to be used in the HTML interface.      *       * @return      */
name|String
name|getHtmlName
parameter_list|()
function_decl|;
comment|/**      * Returns values regarding this facet in a {@link List} of {@link Count}s.      *       * @return      */
name|List
argument_list|<
name|Count
argument_list|>
name|getValues
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

