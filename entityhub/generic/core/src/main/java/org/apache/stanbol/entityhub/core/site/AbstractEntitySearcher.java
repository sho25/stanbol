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
name|entityhub
operator|.
name|core
operator|.
name|site
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|EntityDereferencer
operator|.
name|ACCESS_URI
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|EntitySearcher
operator|.
name|QUERY_URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|EntityDereferencer
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|SiteConfiguration
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|EntitySearcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_class
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|QUERY_URI
argument_list|)
specifier|public
specifier|abstract
class|class
name|AbstractEntitySearcher
implements|implements
name|EntitySearcher
block|{
specifier|protected
specifier|final
name|Logger
name|log
decl_stmt|;
specifier|protected
name|AbstractEntitySearcher
parameter_list|(
name|Logger
name|log
parameter_list|)
block|{
name|this
operator|.
name|log
operator|=
name|log
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"create instance of "
operator|+
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|queryUri
decl_stmt|;
specifier|private
name|String
name|baseUri
decl_stmt|;
specifier|private
name|Dictionary
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|config
decl_stmt|;
specifier|private
name|ComponentContext
name|context
decl_stmt|;
specifier|protected
specifier|final
name|String
name|getQueryUri
parameter_list|()
block|{
return|return
name|queryUri
return|;
block|}
comment|/**      * Getter for the base URI to be used for parsing relative URIs in responses      * @return      */
specifier|protected
name|String
name|getBaseUri
parameter_list|()
block|{
return|return
name|baseUri
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"in "
operator|+
name|AbstractEntitySearcher
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
comment|// TODO handle updates to the configuration
if|if
condition|(
name|context
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getProperties
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|properties
init|=
name|context
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|Object
name|queryUriObject
init|=
name|properties
operator|.
name|get
argument_list|(
name|QUERY_URI
argument_list|)
decl_stmt|;
name|Object
name|accessUriObject
init|=
name|properties
operator|.
name|get
argument_list|(
name|ACCESS_URI
argument_list|)
decl_stmt|;
comment|//use as an fallback
if|if
condition|(
name|queryUriObject
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|queryUri
operator|=
name|queryUriObject
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|//now set the new config
block|}
elseif|else
if|if
condition|(
name|accessUriObject
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Using AccessUri as fallback for missing QueryUri Proerty (accessUri="
operator|+
name|accessUriObject
argument_list|)
expr_stmt|;
name|this
operator|.
name|queryUri
operator|=
name|accessUriObject
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The property "
operator|+
name|EntitySearcher
operator|.
name|QUERY_URI
operator|+
literal|" must be defined"
argument_list|)
throw|;
block|}
name|this
operator|.
name|baseUri
operator|=
name|extractBaseUri
argument_list|(
name|queryUri
argument_list|)
expr_stmt|;
name|this
operator|.
name|config
operator|=
name|properties
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The property "
operator|+
name|EntitySearcher
operator|.
name|QUERY_URI
operator|+
literal|" must be defined"
argument_list|)
throw|;
block|}
block|}
comment|/**      * computes the base URL based on service URLs      * @param the URL of the remote service      * @return the base URL used to parse relative URIs in responses.      */
specifier|protected
specifier|static
name|String
name|extractBaseUri
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
comment|//extract the namepsace from the query URI to use it fore parsing
comment|//responses with relative URIs
name|String
name|baseUri
decl_stmt|;
name|int
name|index
init|=
name|Math
operator|.
name|max
argument_list|(
name|uri
operator|.
name|lastIndexOf
argument_list|(
literal|'#'
argument_list|)
argument_list|,
name|uri
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|protIndex
init|=
name|uri
operator|.
name|indexOf
argument_list|(
literal|"://"
argument_list|)
operator|+
literal|3
decl_stmt|;
comment|//do not convert http://www.example.org
if|if
condition|(
name|protIndex
operator|<
literal|0
condition|)
block|{
name|protIndex
operator|=
literal|0
expr_stmt|;
block|}
comment|//do not convert if the parsed uri does not contain a local name
if|if
condition|(
name|index
operator|>
name|protIndex
operator|&&
name|index
operator|+
literal|1
operator|<
name|uri
operator|.
name|length
argument_list|()
condition|)
block|{
name|baseUri
operator|=
name|uri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
operator|(
name|uri
operator|.
name|charAt
argument_list|(
name|uri
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'/'
operator|||
name|uri
operator|.
name|charAt
argument_list|(
name|uri
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'#'
operator|)
condition|)
block|{
name|baseUri
operator|=
name|uri
operator|+
literal|'/'
expr_stmt|;
comment|//add a tailing '/' to Uris like http://www.example.org
block|}
else|else
block|{
name|baseUri
operator|=
name|uri
expr_stmt|;
block|}
block|}
return|return
name|baseUri
return|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"in "
operator|+
name|AbstractEntitySearcher
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|config
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|queryUri
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|baseUri
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * The OSGI configuration as provided by the activate method      * @return      */
specifier|protected
specifier|final
name|Dictionary
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|getSiteConfiguration
parameter_list|()
block|{
return|return
name|config
return|;
block|}
specifier|protected
specifier|final
name|ComponentContext
name|getComponentContext
parameter_list|()
block|{
return|return
name|context
return|;
block|}
block|}
end_class

end_unit

