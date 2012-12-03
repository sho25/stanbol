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
name|enhancer
operator|.
name|topic
operator|.
name|training
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringUtils
import|;
end_import

begin_comment
comment|/**  * Data transfer object to pack the items of a multi-label text classification training set.  */
end_comment

begin_class
specifier|public
class|class
name|Example
block|{
comment|/**      * Unique id of the document      */
specifier|public
specifier|final
name|String
name|id
decl_stmt|;
comment|/**      * Identifier of the labels (categories, topics, tags...) of the document. This is the target signal to      * predict.      *       * In practice this is expected to be a collection of String items but we do not enforce the cast to avoid      * the GC overhead and be able to work with the native data-structures returned by SolrJ.      */
specifier|public
specifier|final
name|Collection
argument_list|<
name|Object
argument_list|>
name|labels
decl_stmt|;
comment|/**      * Text fields of the document (could be headers, paragraphs, text extractions of PDF files...). Any      * markup is assumed to have been cleaned up in some preprocessing step.      *       * In practice this is expected to be a collection of String items but we do not enforce the cast to avoid      * the GC overhead and be able to work with the native data-structures returned by SolrJ.      */
specifier|public
specifier|final
name|Collection
argument_list|<
name|Object
argument_list|>
name|contents
decl_stmt|;
specifier|public
name|Example
parameter_list|(
name|String
name|id
parameter_list|,
name|Collection
argument_list|<
name|Object
argument_list|>
name|labelValues
parameter_list|,
name|Collection
argument_list|<
name|Object
argument_list|>
name|textValues
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|labels
operator|=
name|labelValues
expr_stmt|;
name|this
operator|.
name|contents
operator|=
name|textValues
expr_stmt|;
block|}
comment|/**      * @return concatenated version of the content fields.      */
specifier|public
name|String
name|getContentString
parameter_list|()
block|{
return|return
name|StringUtils
operator|.
name|join
argument_list|(
name|contents
argument_list|,
literal|"\n\n"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

