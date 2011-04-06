begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|impl
package|;
end_package

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
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServlet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
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
name|Properties
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
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
name|stanbol
operator|.
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProviderEvent
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
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProviderLog
import|;
end_import

begin_comment
comment|/** (Felix) OSGi console plugin that displays DataFileProvider events */
end_comment

begin_class
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|javax
operator|.
name|servlet
operator|.
name|Servlet
operator|.
name|class
argument_list|)
annotation|@
name|Properties
argument_list|(
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"felix.webconsole.label"
argument_list|,
name|value
operator|=
literal|"stanbol_datafileprovider"
argument_list|,
name|propertyPrivate
operator|=
literal|true
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"felix.webconsole.title"
argument_list|,
name|value
operator|=
literal|"Stanbol Data File Provider"
argument_list|,
name|propertyPrivate
operator|=
literal|true
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|WebConsolePlugin
extends|extends
name|HttpServlet
block|{
annotation|@
name|Reference
specifier|private
name|DataFileProviderLog
name|dataFileProviderLog
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|void
name|doGet
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
specifier|final
name|PrintWriter
name|pw
init|=
name|response
operator|.
name|getWriter
argument_list|()
decl_stmt|;
name|pw
operator|.
name|println
argument_list|(
literal|"<p class='statline ui-state-highlight'>"
argument_list|)
expr_stmt|;
name|pw
operator|.
name|println
argument_list|(
literal|"Last "
operator|+
name|dataFileProviderLog
operator|.
name|size
argument_list|()
operator|+
literal|" DataFileProvider events"
argument_list|)
expr_stmt|;
name|pw
operator|.
name|println
argument_list|(
literal|"</p>"
argument_list|)
expr_stmt|;
name|pw
operator|.
name|println
argument_list|(
literal|"<table class='nicetable'>"
argument_list|)
expr_stmt|;
specifier|final
name|String
index|[]
name|labels
init|=
block|{
literal|"timestamp"
block|,
literal|"bundle/filename"
block|,
literal|"actual location/download info"
block|}
decl_stmt|;
name|pw
operator|.
name|println
argument_list|(
literal|"<thead><tr>"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|label
range|:
name|labels
control|)
block|{
name|cell
argument_list|(
literal|"th"
argument_list|,
name|pw
argument_list|,
literal|null
argument_list|,
name|label
argument_list|)
expr_stmt|;
block|}
name|pw
operator|.
name|println
argument_list|(
literal|"</tr></thead><tbody>"
argument_list|)
expr_stmt|;
specifier|final
name|SimpleDateFormat
name|fmt
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyy/MM/dd HH:mm:ss"
argument_list|)
decl_stmt|;
for|for
control|(
name|DataFileProviderEvent
name|e
range|:
name|dataFileProviderLog
control|)
block|{
name|pw
operator|.
name|println
argument_list|(
literal|"<tr>"
argument_list|)
expr_stmt|;
name|cell
argument_list|(
name|pw
argument_list|,
literal|null
argument_list|,
name|fmt
operator|.
name|format
argument_list|(
name|e
operator|.
name|getTimestamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|cell
argument_list|(
name|pw
argument_list|,
literal|null
argument_list|,
name|e
operator|.
name|getBundleSymbolicName
argument_list|()
argument_list|,
literal|"b"
argument_list|,
name|e
operator|.
name|getFilename
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|comment
range|:
name|e
operator|.
name|getComments
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<br/>"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|comment
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|comment
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|cell
argument_list|(
name|pw
argument_list|,
literal|null
argument_list|,
name|e
operator|.
name|getActualFileLocation
argument_list|()
argument_list|,
literal|"i"
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|pw
operator|.
name|println
argument_list|(
literal|"</tr>"
argument_list|)
expr_stmt|;
block|}
name|pw
operator|.
name|println
argument_list|(
literal|"</tbody></table>"
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|void
name|cell
parameter_list|(
name|PrintWriter
name|pw
parameter_list|,
name|String
modifier|...
name|content
parameter_list|)
block|{
name|cell
argument_list|(
literal|"td"
argument_list|,
name|pw
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
comment|/** content parameters: tags at even indexes, content at odd indexes */
specifier|private
specifier|static
name|void
name|cell
parameter_list|(
name|String
name|tag
parameter_list|,
name|PrintWriter
name|pw
parameter_list|,
name|String
modifier|...
name|content
parameter_list|)
block|{
name|pw
operator|.
name|print
argument_list|(
literal|"<"
argument_list|)
expr_stmt|;
name|pw
operator|.
name|print
argument_list|(
name|tag
argument_list|)
expr_stmt|;
name|pw
operator|.
name|print
argument_list|(
literal|">"
argument_list|)
expr_stmt|;
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
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
name|content
operator|.
name|length
condition|;
name|i
operator|+=
literal|2
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<br/>\n"
argument_list|)
expr_stmt|;
block|}
specifier|final
name|String
name|lineTag
init|=
name|content
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|lineTag
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<"
argument_list|)
operator|.
name|append
argument_list|(
name|lineTag
argument_list|)
operator|.
name|append
argument_list|(
literal|">"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|content
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|lineTag
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"</"
argument_list|)
operator|.
name|append
argument_list|(
name|lineTag
argument_list|)
operator|.
name|append
argument_list|(
literal|">"
argument_list|)
expr_stmt|;
block|}
block|}
name|pw
operator|.
name|print
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|pw
operator|.
name|print
argument_list|(
literal|"<"
argument_list|)
expr_stmt|;
name|pw
operator|.
name|print
argument_list|(
name|tag
argument_list|)
expr_stmt|;
name|pw
operator|.
name|print
argument_list|(
literal|">"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

