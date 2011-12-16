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
name|core
operator|.
name|utils
package|;
end_package

begin_comment
comment|/**  * This class contains methods to manage ids of content items<br>  * TODO: This class may be merged with  * {@link org.apache.stanbol.enhancer.servicesapi.helper.ContentItemHelper}  *   * @author suat  *   */
end_comment

begin_class
specifier|public
class|class
name|ContentItemIDOrganizer
block|{
specifier|public
specifier|static
specifier|final
name|String
name|CONTENT_ITEM_URI_PREFIX
init|=
literal|"urn:content-item-"
decl_stmt|;
specifier|public
specifier|static
name|String
name|attachBaseURI
parameter_list|(
name|String
name|id
parameter_list|)
block|{
if|if
condition|(
operator|!
name|id
operator|.
name|startsWith
argument_list|(
literal|"urn:"
argument_list|)
condition|)
block|{
name|id
operator|=
name|CONTENT_ITEM_URI_PREFIX
operator|+
name|id
expr_stmt|;
block|}
return|return
name|id
return|;
block|}
specifier|public
specifier|static
name|String
name|detachBaseURI
parameter_list|(
name|String
name|id
parameter_list|)
block|{
if|if
condition|(
name|id
operator|.
name|startsWith
argument_list|(
name|CONTENT_ITEM_URI_PREFIX
argument_list|)
condition|)
block|{
name|id
operator|=
name|id
operator|.
name|replaceFirst
argument_list|(
name|CONTENT_ITEM_URI_PREFIX
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|id
return|;
block|}
block|}
end_class

end_unit

