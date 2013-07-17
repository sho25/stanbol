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
name|engines
operator|.
name|tika
operator|.
name|handler
package|;
end_package

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
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|tika
operator|.
name|sax
operator|.
name|ContentHandlerDecorator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|Attributes
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|ContentHandler
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|Locator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|helpers
operator|.
name|DefaultHandler
import|;
end_import

begin_comment
comment|/**  * Similar to {@link ContentHandlerDecorator} - as it processed the exact same  * methods - but supports forwarding such calls to several parsed {@link ContentHandler}s  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|MultiHandler
extends|extends
name|DefaultHandler
block|{
name|List
argument_list|<
name|ContentHandler
argument_list|>
name|handlers
decl_stmt|;
specifier|public
name|MultiHandler
parameter_list|(
name|ContentHandler
modifier|...
name|handlers
parameter_list|)
block|{
if|if
condition|(
name|handlers
operator|==
literal|null
operator|||
name|handlers
operator|.
name|length
operator|<
literal|1
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentHandler array MUST NOT be NULL or empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|handlers
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|handlers
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|handlers
operator|.
name|contains
argument_list|(
literal|null
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Tha parsed ContentHandlers array MUST NOT contain an NULL entry!"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|startPrefixMapping
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|uri
parameter_list|)
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|startPrefixMapping
argument_list|(
name|prefix
argument_list|,
name|uri
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|endPrefixMapping
parameter_list|(
name|String
name|prefix
parameter_list|)
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|endPrefixMapping
argument_list|(
name|prefix
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|processingInstruction
parameter_list|(
name|String
name|target
parameter_list|,
name|String
name|data
parameter_list|)
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|processingInstruction
argument_list|(
name|target
argument_list|,
name|data
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|setDocumentLocator
parameter_list|(
name|Locator
name|locator
parameter_list|)
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|setDocumentLocator
argument_list|(
name|locator
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|startDocument
parameter_list|()
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|startDocument
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|endDocument
parameter_list|()
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|endDocument
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|startElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|localName
parameter_list|,
name|String
name|name
parameter_list|,
name|Attributes
name|atts
parameter_list|)
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|startElement
argument_list|(
name|uri
argument_list|,
name|localName
argument_list|,
name|name
argument_list|,
name|atts
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|endElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|localName
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|endElement
argument_list|(
name|uri
argument_list|,
name|localName
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|characters
parameter_list|(
name|char
index|[]
name|ch
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|length
parameter_list|)
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|characters
argument_list|(
name|ch
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|ignorableWhitespace
parameter_list|(
name|char
index|[]
name|ch
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|length
parameter_list|)
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|ignorableWhitespace
argument_list|(
name|ch
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|skippedEntity
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|SAXException
block|{
for|for
control|(
name|ContentHandler
name|handler
range|:
name|handlers
control|)
block|{
name|handler
operator|.
name|skippedEntity
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

