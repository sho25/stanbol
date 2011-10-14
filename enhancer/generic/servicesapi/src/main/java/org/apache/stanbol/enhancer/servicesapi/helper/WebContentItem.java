begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|servicesapi
operator|.
name|helper
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
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
name|net
operator|.
name|URLConnection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|SimpleMGraph
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
name|io
operator|.
name|IOUtils
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_comment
comment|/**  * A ContentItem retrieving its content and MediaType by dereferencing a given URI.  *   * After construction the<code>metadata</code> graph is empty.  *  */
end_comment

begin_comment
comment|/*  * The current implementation keeps the content in memory after the firts connection   * to the remote server.   */
end_comment

begin_class
specifier|public
class|class
name|WebContentItem
implements|implements
name|ContentItem
block|{
specifier|private
specifier|final
name|MGraph
name|metadata
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|URL
name|url
decl_stmt|;
specifier|private
name|boolean
name|dereferenced
init|=
literal|false
decl_stmt|;
specifier|private
name|byte
index|[]
name|data
decl_stmt|;
specifier|private
name|String
name|mimeType
decl_stmt|;
comment|/** 	 * Creates an instance for a given URL 	 *  	 * @param url the dereferenceable URI 	 */
specifier|public
name|WebContentItem
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
name|this
operator|.
name|url
operator|=
name|url
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|url
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getStream
parameter_list|()
block|{
if|if
condition|(
operator|!
name|dereferenced
condition|)
block|{
name|dereference
argument_list|()
expr_stmt|;
block|}
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
name|data
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
if|if
condition|(
operator|!
name|dereferenced
condition|)
block|{
name|dereference
argument_list|()
expr_stmt|;
block|}
return|return
name|mimeType
return|;
block|}
annotation|@
name|Override
specifier|public
name|MGraph
name|getMetadata
parameter_list|()
block|{
return|return
name|metadata
return|;
block|}
specifier|private
specifier|synchronized
name|void
name|dereference
parameter_list|()
block|{
comment|//checking again in the synchronized section
if|if
condition|(
operator|!
name|dereferenced
condition|)
block|{
name|URLConnection
name|uc
decl_stmt|;
try|try
block|{
name|uc
operator|=
name|url
operator|.
name|openConnection
argument_list|()
expr_stmt|;
name|data
operator|=
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|uc
operator|.
name|getInputStream
argument_list|()
argument_list|)
expr_stmt|;
name|mimeType
operator|=
name|uc
operator|.
name|getContentType
argument_list|()
expr_stmt|;
if|if
condition|(
name|mimeType
operator|==
literal|null
condition|)
block|{
name|mimeType
operator|=
literal|"application/octet-stream"
expr_stmt|;
block|}
else|else
block|{
comment|// Keep only first part of content-types like text/plain ; charset=UTF-8
name|mimeType
operator|=
name|mimeType
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
name|dereferenced
operator|=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Exception derefereing URI "
operator|+
name|url
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
block|}
end_class

end_unit

