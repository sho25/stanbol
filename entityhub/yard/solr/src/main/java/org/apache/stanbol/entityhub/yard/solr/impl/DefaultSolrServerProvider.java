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
name|yard
operator|.
name|solr
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|EnumSet
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
name|Component
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
name|Service
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
name|SolrServer
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
name|impl
operator|.
name|CommonsHttpSolrServer
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
name|impl
operator|.
name|LBHttpSolrServer
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
name|impl
operator|.
name|StreamingUpdateSolrServer
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
name|yard
operator|.
name|solr
operator|.
name|SolrServerProvider
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

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Implementation of the {@link SolrServerProvider} interface supporting all the types directly supported by  * the SolrJ library. This includes all Clients using an remote SolrServer.  *<p>  * This does not support an embedded SolrServer  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|DefaultSolrServerProvider
implements|implements
name|SolrServerProvider
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DefaultSolrServerProvider
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|SolrServer
name|getSolrServer
parameter_list|(
name|Type
name|type
parameter_list|,
name|String
name|uriOrPath
parameter_list|,
name|String
modifier|...
name|additional
parameter_list|)
throws|throws
name|NullPointerException
throws|,
name|IllegalArgumentException
block|{
if|if
condition|(
name|uriOrPath
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed SolrServer URI MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
name|type
operator|=
name|Type
operator|.
name|HTTP
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|Type
operator|.
name|EMBEDDED
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The EmbeddedSolrServer (type=%s) is not supported by this SolrServerProvider implementation"
argument_list|,
name|type
argument_list|)
argument_list|)
throw|;
block|}
specifier|final
name|URL
name|solrServerURL
decl_stmt|;
try|try
block|{
name|solrServerURL
operator|=
operator|new
name|URL
argument_list|(
name|uriOrPath
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed SolrServer location is not a valid URL"
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
name|type
operator|!=
name|Type
operator|.
name|LOAD_BALANCE
operator|&&
name|additional
operator|!=
literal|null
operator|&&
name|additional
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed SolrServer type \"%s\" does not support multiple SolrServer instaces."
operator|+
literal|"The %s additional SolrServer locations parsed are ignored! (ignored Servers: %s)"
argument_list|,
name|type
argument_list|,
name|additional
operator|.
name|length
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|additional
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|HTTP
case|:
return|return
operator|new
name|CommonsHttpSolrServer
argument_list|(
name|solrServerURL
argument_list|)
return|;
case|case
name|STREAMING
case|:
try|try
block|{
return|return
operator|new
name|StreamingUpdateSolrServer
argument_list|(
name|uriOrPath
argument_list|,
literal|10
argument_list|,
literal|3
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
comment|// URL is already validated before!
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
case|case
name|LOAD_BALANCE
case|:
name|Collection
argument_list|<
name|String
argument_list|>
name|solrServers
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|solrServers
operator|.
name|add
argument_list|(
name|uriOrPath
argument_list|)
expr_stmt|;
comment|// the main server
comment|// check the additional server locations
if|if
condition|(
name|additional
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|serverlocation
range|:
name|additional
control|)
block|{
if|if
condition|(
name|serverlocation
operator|!=
literal|null
condition|)
block|{
try|try
block|{
operator|new
name|URL
argument_list|(
name|serverlocation
argument_list|)
expr_stmt|;
name|solrServers
operator|.
name|add
argument_list|(
name|serverlocation
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed additional SolrServer %s is no valid URL. -> This location is ignored"
argument_list|,
name|serverlocation
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|// else ignore
block|}
block|}
try|try
block|{
return|return
operator|new
name|LBHttpSolrServer
argument_list|(
name|solrServers
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|solrServers
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
comment|// URLs are already validated before!
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
default|default:
comment|// write an error to clearly show that there is something wrong with this implementation
name|log
operator|.
name|error
argument_list|(
literal|"This should never happen, because this class should eighter throw an IllegalArgumentException or support the type "
operator|+
name|type
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Type "
operator|+
name|type
operator|+
literal|" is not supported"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Type
argument_list|>
name|supportedTypes
parameter_list|()
block|{
return|return
name|EnumSet
operator|.
name|of
argument_list|(
name|Type
operator|.
name|HTTP
argument_list|,
name|Type
operator|.
name|LOAD_BALANCE
argument_list|,
name|Type
operator|.
name|STREAMING
argument_list|)
return|;
block|}
block|}
end_class

end_unit

