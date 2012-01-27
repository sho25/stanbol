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
name|web
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|HttpHeaders
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_comment
comment|/**  * Utility class for REST services  */
end_comment

begin_class
specifier|public
class|class
name|RestUtil
block|{
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|supportedMediaTypes
decl_stmt|;
static|static
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|types
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|types
operator|.
name|add
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
expr_stmt|;
name|supportedMediaTypes
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|types
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param parameter      *            parameter to be checked      * @return<code>null</code> if parameter has an empty content, otherwise trimmed<code>parameter</code>      */
specifier|public
specifier|static
name|String
name|nullify
parameter_list|(
name|String
name|parameter
parameter_list|)
block|{
if|if
condition|(
name|parameter
operator|!=
literal|null
condition|)
block|{
name|parameter
operator|=
name|parameter
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|parameter
operator|.
name|isEmpty
argument_list|()
operator|||
name|parameter
operator|.
name|equals
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|parameter
operator|=
literal|null
expr_stmt|;
block|}
block|}
return|return
name|parameter
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isJSONaccepted
parameter_list|(
name|HttpHeaders
name|headers
parameter_list|)
block|{
if|if
condition|(
operator|!
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|MediaType
name|accepted
range|:
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|accepted
operator|.
name|isWildcardType
argument_list|()
condition|)
block|{
if|if
condition|(
name|accepted
operator|.
name|isCompatible
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isHTMLaccepted
parameter_list|(
name|HttpHeaders
name|headers
parameter_list|)
block|{
if|if
condition|(
operator|!
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|MediaType
name|accepted
range|:
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|accepted
operator|.
name|isWildcardType
argument_list|()
condition|)
block|{
if|if
condition|(
name|accepted
operator|.
name|isCompatible
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML_TYPE
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
specifier|static
name|MediaType
name|getAcceptedMediaType
parameter_list|(
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|MediaType
name|acceptedMediaType
init|=
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
decl_stmt|;
comment|// default
if|if
condition|(
operator|!
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|MediaType
name|accepted
range|:
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|accepted
operator|.
name|isWildcardType
argument_list|()
condition|)
block|{
if|if
condition|(
name|accepted
operator|.
name|isCompatible
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML_TYPE
argument_list|)
condition|)
block|{
name|acceptedMediaType
operator|=
name|MediaType
operator|.
name|TEXT_HTML_TYPE
expr_stmt|;
break|break;
block|}
elseif|else
if|if
condition|(
name|accepted
operator|.
name|isCompatible
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
condition|)
block|{
name|acceptedMediaType
operator|=
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
return|return
name|acceptedMediaType
return|;
block|}
block|}
end_class

end_unit

