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
name|it
package|;
end_package

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
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|NameValuePair
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|utils
operator|.
name|URLEncodedUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|message
operator|.
name|BasicNameValuePair
import|;
end_import

begin_class
specifier|public
class|class
name|MultipartContentItemTestUtils
block|{
specifier|public
specifier|static
name|String
name|getHTMLContent
parameter_list|(
name|String
modifier|...
name|content
parameter_list|)
block|{
if|if
condition|(
name|content
operator|==
literal|null
operator|||
name|content
operator|.
name|length
operator|<
literal|2
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed content MUST have at lest two elements"
argument_list|)
throw|;
block|}
name|StringBuilder
name|c
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
operator|+
literal|"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" "
operator|+
literal|"lang=\"en\" dir=\"ltr\">\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<head>\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<meta http-equiv=\"Content-Type\" content=\"text/html; "
operator|+
literal|"charset=utf-8\" />\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<title>"
argument_list|)
operator|.
name|append
argument_list|(
name|content
index|[
literal|0
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"</title>\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<meta http-equiv=\"Content-Type\" content=\"text/html; "
operator|+
literal|"charset=utf-8\" />\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<style type=\"text/css\">"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"#headbox {\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"    background: none repeat scroll 0 0 white;\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"    border-bottom: 3px solid black;\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"    width: 100%;\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"}\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"</style>\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"</head>\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<body>\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<div class=\"content\">\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"<h2>"
argument_list|)
operator|.
name|append
argument_list|(
name|content
index|[
literal|0
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"</h2>\n"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|content
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|.
name|append
argument_list|(
literal|"<p>"
argument_list|)
operator|.
name|append
argument_list|(
name|content
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"</p>\n"
argument_list|)
expr_stmt|;
block|}
name|c
operator|.
name|append
argument_list|(
literal|"</div>\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"</body>\n"
argument_list|)
expr_stmt|;
name|c
operator|.
name|append
argument_list|(
literal|"</html>\n"
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Build an path from the supplied path and       * query parameters.      *      * @param queryParameters an even number of Strings, each pair      * of values represents the key and value of a query parameter.      * Keys and values are encoded by this method.      */
specifier|public
specifier|static
name|String
name|buildPathWithParams
parameter_list|(
name|String
name|path
parameter_list|,
name|String
modifier|...
name|queryParameters
parameter_list|)
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|queryParameters
operator|==
literal|null
operator|||
name|queryParameters
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|queryParameters
operator|.
name|length
operator|%
literal|2
operator|!=
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Invalid number of queryParameters arguments ("
operator|+
name|queryParameters
operator|.
name|length
operator|+
literal|"), must be even"
argument_list|)
throw|;
block|}
else|else
block|{
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|p
init|=
operator|new
name|ArrayList
argument_list|<
name|NameValuePair
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|queryParameters
operator|.
name|length
condition|;
name|i
operator|+=
literal|2
control|)
block|{
name|p
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|queryParameters
index|[
name|i
index|]
argument_list|,
name|queryParameters
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"?"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|URLEncodedUtils
operator|.
name|format
argument_list|(
name|p
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

