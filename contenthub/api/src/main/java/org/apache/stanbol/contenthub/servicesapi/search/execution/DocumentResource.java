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
name|engine
operator|.
name|SearchEngine
import|;
end_import

begin_comment
comment|/**  * The interface to represent a document resource. A {@link DocumentResource} represents a document which is  * found as a result of a search operation by a {@link SearchEngine}. If a {@link SearchEngine} finds a  * document as a result of its operation, it adds a corresponding {@link DocumentResource} to the  * {@link SearchContext}.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|DocumentResource
extends|extends
name|KeywordRelated
block|{
comment|/**      * Retrieves the URI of the document. Each document is uniquely identified by its URI inside the Stanbol      * Contenthub.      *       * @return The URI of the document.      */
name|String
name|getDocumentURI
parameter_list|()
function_decl|;
comment|/**      * Retrieves the text of this document, if it has a text based content.      *       * @return The text of the document.      */
name|String
name|getRelatedText
parameter_list|()
function_decl|;
comment|/**      * Sets the text of this document.      *       * @param selectionText      *            The text of this document.      */
name|void
name|setRelatedText
parameter_list|(
name|String
name|selectionText
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

