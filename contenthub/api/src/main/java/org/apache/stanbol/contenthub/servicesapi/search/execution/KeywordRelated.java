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
name|execution
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

begin_comment
comment|/**  * Each {@link ClassResource} is linked with a {@link Keyword} regardless of this resource is extracted by  * class subsumption relation or keyword based text search. This interface provides the function to access the  * keywords which cause the resource to exist in the {@link SearchContext}.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|KeywordRelated
extends|extends
name|Scored
block|{
comment|/**      * Retrieves the related {@link Keyword}s.      *       * @return @{link Keyword}s which cause this resource to occur in the search result by means of the      *         {@link SearchContext}.      */
name|List
argument_list|<
name|Keyword
argument_list|>
name|getRelatedKeywords
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

