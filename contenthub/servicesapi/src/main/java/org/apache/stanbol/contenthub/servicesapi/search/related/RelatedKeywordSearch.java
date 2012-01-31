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
name|related
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
name|java
operator|.
name|util
operator|.
name|Map
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|SearchException
import|;
end_import

begin_comment
comment|/**  * Related keyword searcher for a given query keyword.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RelatedKeywordSearch
block|{
comment|/**      * This method searches for related keywords for the given<code>keyword</code>.      *       * @param keyword      *            The query keyword for which related keywords will be obtained.      * @return a {@link Map} containing the related keywords. Keys of this map represents sources/categories      *         of the related keywords. Values of the map keeps {@link List} of {@link RelatedKeyword}s.      * @throws SearchException      */
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
name|search
parameter_list|(
name|String
name|keyword
parameter_list|)
throws|throws
name|SearchException
function_decl|;
comment|/**      * This method searches for related keywords for the given<code>keyword</code>. It also takes URI of an      * ontology which will be used as a related keyword source while searching through ontology resources.      *       * @param keyword      *            The query keyword for which related keywords will be obtained.      * @param ontologyURI      *            URI of the ontology in which related keyword will be searched      * @return a {@link Map} containing the related keywords. Keys of this map represents sources/categories      *         of the related keywords. Values of the map keeps {@link List} of {@link RelatedKeyword}s.      * @throws SearchException      */
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
throws|throws
name|SearchException
function_decl|;
block|}
end_interface

end_unit

