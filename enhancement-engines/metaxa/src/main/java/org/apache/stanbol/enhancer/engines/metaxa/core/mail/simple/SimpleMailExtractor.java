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
name|engines
operator|.
name|metaxa
operator|.
name|core
operator|.
name|mail
operator|.
name|simple
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
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
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
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|javax
operator|.
name|activation
operator|.
name|DataHandler
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Address
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|BodyPart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Message
operator|.
name|RecipientType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|MessagingException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Multipart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Part
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|AddressException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|ContentType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|InternetAddress
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeMessage
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeUtility
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
name|engines
operator|.
name|metaxa
operator|.
name|core
operator|.
name|html
operator|.
name|HtmlTextExtractUtil
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
name|engines
operator|.
name|metaxa
operator|.
name|core
operator|.
name|html
operator|.
name|InitializationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|exception
operator|.
name|ModelException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|Syntax
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|impl
operator|.
name|URIGenerator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|node
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|model
operator|.
name|node
operator|.
name|impl
operator|.
name|URIImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|rdf2go
operator|.
name|vocabulary
operator|.
name|RDF
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|Extractor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|ExtractorException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|mime
operator|.
name|MailUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|rdf
operator|.
name|RDFContainer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|rdf
operator|.
name|RDFContainerFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|rdf
operator|.
name|impl
operator|.
name|RDFContainerFactoryImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|vocabulary
operator|.
name|NFO
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|vocabulary
operator|.
name|NIE
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|vocabulary
operator|.
name|NMO
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
comment|/**  * An Extractor implementation for message/rfc822-style messages.  *   *<p>  * Only typical body parts are processed during full-text extraction. Attachments are only listed but not  * further handled. In case of mails in HTML format, the full HTML is included in the extracted data as value  * of the<code>nmo:htmlMessageContent</code> property. The plain text (extract) is represented by the  *<code>nmo:plainTextMessageContent</code> property and as value of the<code>nie:plainTextContent</code>  * property for compliance with the representation from other extractors.  *   *   */
end_comment

begin_class
specifier|public
class|class
name|SimpleMailExtractor
implements|implements
name|Extractor
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SimpleMailExtractor
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|void
name|extract
parameter_list|(
name|URI
name|id
parameter_list|,
name|InputStream
name|stream
parameter_list|,
name|Charset
name|charset
parameter_list|,
name|String
name|mimeType
parameter_list|,
name|RDFContainer
name|result
parameter_list|)
throws|throws
name|ExtractorException
block|{
try|try
block|{
comment|// parse the stream
name|MimeMessage
name|message
init|=
operator|new
name|MimeMessage
argument_list|(
literal|null
argument_list|,
name|stream
argument_list|)
decl_stmt|;
name|result
operator|.
name|add
argument_list|(
name|RDF
operator|.
name|type
argument_list|,
name|NMO
operator|.
name|Email
argument_list|)
expr_stmt|;
comment|// extract the full-text
name|StringBuilder
name|buffer
init|=
operator|new
name|StringBuilder
argument_list|(
literal|10000
argument_list|)
decl_stmt|;
name|processMessage
argument_list|(
name|message
argument_list|,
name|buffer
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|String
name|text
init|=
name|buffer
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|NMO
operator|.
name|plainTextMessageContent
argument_list|,
name|text
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
name|NIE
operator|.
name|plainTextContent
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
comment|// extract other metadata
name|String
name|title
init|=
name|message
operator|.
name|getSubject
argument_list|()
decl_stmt|;
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
name|title
operator|=
name|title
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|title
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|NMO
operator|.
name|messageSubject
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
block|}
try|try
block|{
name|copyAddress
argument_list|(
name|message
operator|.
name|getFrom
argument_list|()
argument_list|,
name|NMO
operator|.
name|from
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|AddressException
name|e
parameter_list|)
block|{
comment|// ignore
block|}
name|copyAddress
argument_list|(
name|getRecipients
argument_list|(
name|message
argument_list|,
name|RecipientType
operator|.
name|TO
argument_list|)
argument_list|,
name|NMO
operator|.
name|to
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|copyAddress
argument_list|(
name|getRecipients
argument_list|(
name|message
argument_list|,
name|RecipientType
operator|.
name|CC
argument_list|)
argument_list|,
name|NMO
operator|.
name|cc
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|copyAddress
argument_list|(
name|getRecipients
argument_list|(
name|message
argument_list|,
name|RecipientType
operator|.
name|BCC
argument_list|)
argument_list|,
name|NMO
operator|.
name|bcc
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|MailUtil
operator|.
name|getDates
argument_list|(
name|message
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MessagingException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ExtractorException
argument_list|(
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ExtractorException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
comment|// the top level message
specifier|protected
name|void
name|processMessage
parameter_list|(
name|MimeMessage
name|msg
parameter_list|,
name|StringBuilder
name|buffer
parameter_list|,
name|RDFContainer
name|rdf
parameter_list|)
throws|throws
name|MessagingException
throws|,
name|IOException
throws|,
name|ExtractorException
block|{
if|if
condition|(
name|msg
operator|.
name|isMimeType
argument_list|(
literal|"text/plain"
argument_list|)
condition|)
block|{
name|processContent
argument_list|(
name|msg
operator|.
name|getContent
argument_list|()
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|msg
operator|.
name|isMimeType
argument_list|(
literal|"text/html"
argument_list|)
condition|)
block|{
name|String
name|encoding
init|=
name|getContentEncoding
argument_list|(
operator|new
name|ContentType
argument_list|(
name|msg
operator|.
name|getContentType
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|logger
operator|.
name|debug
argument_list|(
literal|"HTML encoding: {}"
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
if|if
condition|(
name|msg
operator|.
name|getContent
argument_list|()
operator|instanceof
name|String
condition|)
block|{
name|String
name|text
init|=
name|extractTextFromHtml
argument_list|(
operator|(
operator|(
name|String
operator|)
name|msg
operator|.
name|getContent
argument_list|()
operator|)
operator|.
name|trim
argument_list|()
argument_list|,
name|encoding
argument_list|,
name|rdf
argument_list|)
decl_stmt|;
name|rdf
operator|.
name|add
argument_list|(
name|NMO
operator|.
name|htmlMessageContent
argument_list|,
operator|(
name|String
operator|)
name|msg
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
name|processContent
argument_list|(
name|text
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|processContent
argument_list|(
name|msg
operator|.
name|getContent
argument_list|()
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|processContent
argument_list|(
name|msg
operator|.
name|getContent
argument_list|()
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
block|}
comment|// the recursive part
specifier|protected
name|void
name|processContent
parameter_list|(
name|Object
name|content
parameter_list|,
name|StringBuilder
name|buffer
parameter_list|,
name|RDFContainer
name|rdf
parameter_list|)
throws|throws
name|MessagingException
throws|,
name|IOException
throws|,
name|ExtractorException
block|{
if|if
condition|(
name|content
operator|instanceof
name|String
condition|)
block|{
name|buffer
operator|.
name|append
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|buffer
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|content
operator|instanceof
name|BodyPart
condition|)
block|{
name|BodyPart
name|bodyPart
init|=
operator|(
name|BodyPart
operator|)
name|content
decl_stmt|;
name|DataHandler
name|handler
init|=
name|bodyPart
operator|.
name|getDataHandler
argument_list|()
decl_stmt|;
name|String
name|encoding
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|handler
operator|!=
literal|null
condition|)
block|{
name|encoding
operator|=
name|MimeUtility
operator|.
name|getEncoding
argument_list|(
name|handler
argument_list|)
expr_stmt|;
block|}
name|String
name|fileName
init|=
name|bodyPart
operator|.
name|getFileName
argument_list|()
decl_stmt|;
name|String
name|contentType
init|=
name|bodyPart
operator|.
name|getContentType
argument_list|()
decl_stmt|;
if|if
condition|(
name|fileName
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|fileName
operator|=
name|MimeUtility
operator|.
name|decodeWord
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MessagingException
name|e
parameter_list|)
block|{
comment|// happens on unencoded file names! so just ignore it and leave the file name as it is
block|}
name|URI
name|attachURI
init|=
name|URIGenerator
operator|.
name|createNewRandomUniqueURI
argument_list|()
decl_stmt|;
name|rdf
operator|.
name|add
argument_list|(
name|NMO
operator|.
name|hasAttachment
argument_list|,
name|attachURI
argument_list|)
expr_stmt|;
name|Model
name|m
init|=
name|rdf
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|m
operator|.
name|addStatement
argument_list|(
name|attachURI
argument_list|,
name|RDF
operator|.
name|type
argument_list|,
name|NFO
operator|.
name|Attachment
argument_list|)
expr_stmt|;
name|m
operator|.
name|addStatement
argument_list|(
name|attachURI
argument_list|,
name|NFO
operator|.
name|fileName
argument_list|,
name|fileName
argument_list|)
expr_stmt|;
if|if
condition|(
name|handler
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|encoding
operator|!=
literal|null
condition|)
block|{
name|m
operator|.
name|addStatement
argument_list|(
name|attachURI
argument_list|,
name|NFO
operator|.
name|encoding
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|contentType
operator|!=
literal|null
condition|)
block|{
name|contentType
operator|=
operator|(
operator|new
name|ContentType
argument_list|(
name|contentType
argument_list|)
operator|)
operator|.
name|getBaseType
argument_list|()
expr_stmt|;
name|m
operator|.
name|addStatement
argument_list|(
name|attachURI
argument_list|,
name|NIE
operator|.
name|mimeType
argument_list|,
name|contentType
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// TODO: encoding?
block|}
comment|// append the content, if any
name|content
operator|=
name|bodyPart
operator|.
name|getContent
argument_list|()
expr_stmt|;
comment|// remove any html markup if necessary
if|if
condition|(
name|contentType
operator|!=
literal|null
operator|&&
name|content
operator|instanceof
name|String
condition|)
block|{
name|contentType
operator|=
name|contentType
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
if|if
condition|(
name|contentType
operator|.
name|indexOf
argument_list|(
literal|"text/html"
argument_list|)
operator|>=
literal|0
condition|)
block|{
if|if
condition|(
name|encoding
operator|!=
literal|null
condition|)
block|{
name|encoding
operator|=
name|MimeUtility
operator|.
name|javaCharset
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
block|}
name|content
operator|=
name|extractTextFromHtml
argument_list|(
operator|(
name|String
operator|)
name|content
argument_list|,
name|encoding
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
block|}
name|processContent
argument_list|(
name|content
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|content
operator|instanceof
name|Multipart
condition|)
block|{
name|Multipart
name|multipart
init|=
operator|(
name|Multipart
operator|)
name|content
decl_stmt|;
name|String
name|subType
init|=
literal|null
decl_stmt|;
name|String
name|contentType
init|=
name|multipart
operator|.
name|getContentType
argument_list|()
decl_stmt|;
if|if
condition|(
name|contentType
operator|!=
literal|null
condition|)
block|{
name|ContentType
name|ct
init|=
operator|new
name|ContentType
argument_list|(
name|contentType
argument_list|)
decl_stmt|;
name|subType
operator|=
name|ct
operator|.
name|getSubType
argument_list|()
expr_stmt|;
if|if
condition|(
name|subType
operator|!=
literal|null
condition|)
block|{
name|subType
operator|=
name|subType
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"alternative"
operator|.
name|equals
argument_list|(
name|subType
argument_list|)
condition|)
block|{
name|handleAlternativePart
argument_list|(
name|multipart
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"signed"
operator|.
name|equals
argument_list|(
name|subType
argument_list|)
condition|)
block|{
name|handleProtectedPart
argument_list|(
name|multipart
argument_list|,
literal|0
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"encrypted"
operator|.
name|equals
argument_list|(
name|subType
argument_list|)
condition|)
block|{
name|handleProtectedPart
argument_list|(
name|multipart
argument_list|,
literal|1
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// handles multipart/mixed, /digest, /related, /parallel, /report and unknown subtypes
name|handleMixedPart
argument_list|(
name|multipart
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|protected
name|void
name|handleAlternativePart
parameter_list|(
name|Multipart
name|multipart
parameter_list|,
name|StringBuilder
name|buffer
parameter_list|,
name|RDFContainer
name|rdf
parameter_list|)
throws|throws
name|MessagingException
throws|,
name|IOException
throws|,
name|ExtractorException
block|{
comment|// find the first text/plain part or else the first text/html part
name|boolean
name|isHtml
init|=
literal|false
decl_stmt|;
name|int
name|idx
init|=
name|getPartWithMimeType
argument_list|(
name|multipart
argument_list|,
literal|"text/plain"
argument_list|)
decl_stmt|;
name|int
name|idxh
init|=
name|getPartWithMimeType
argument_list|(
name|multipart
argument_list|,
literal|"text/html"
argument_list|)
decl_stmt|;
if|if
condition|(
name|idx
operator|<
literal|0
condition|)
block|{
name|isHtml
operator|=
literal|true
expr_stmt|;
block|}
comment|// add nmo:htmlMessageContent property
if|if
condition|(
name|idxh
operator|>=
literal|0
condition|)
block|{
name|Object
name|html
init|=
name|multipart
operator|.
name|getBodyPart
argument_list|(
name|idxh
argument_list|)
operator|.
name|getContent
argument_list|()
decl_stmt|;
if|if
condition|(
name|html
operator|!=
literal|null
operator|&&
name|html
operator|instanceof
name|String
condition|)
block|{
name|rdf
operator|.
name|add
argument_list|(
name|NMO
operator|.
name|htmlMessageContent
argument_list|,
operator|(
name|String
operator|)
name|html
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|idx
operator|>=
literal|0
condition|)
block|{
name|Object
name|content
init|=
name|multipart
operator|.
name|getBodyPart
argument_list|(
name|idx
argument_list|)
operator|.
name|getContent
argument_list|()
decl_stmt|;
if|if
condition|(
name|content
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|content
operator|instanceof
name|String
operator|&&
name|isHtml
condition|)
block|{
name|String
name|encoding
init|=
name|getEncoding
argument_list|(
name|multipart
operator|.
name|getBodyPart
argument_list|(
name|idx
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|encoding
operator|!=
literal|null
condition|)
block|{
name|encoding
operator|=
name|MimeUtility
operator|.
name|javaCharset
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
block|}
name|content
operator|=
name|extractTextFromHtml
argument_list|(
operator|(
name|String
operator|)
name|content
argument_list|,
name|encoding
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
name|processContent
argument_list|(
name|content
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|protected
name|void
name|handleMixedPart
parameter_list|(
name|Multipart
name|multipart
parameter_list|,
name|StringBuilder
name|buffer
parameter_list|,
name|RDFContainer
name|rdf
parameter_list|)
throws|throws
name|MessagingException
throws|,
name|IOException
throws|,
name|ExtractorException
block|{
name|int
name|count
init|=
name|multipart
operator|.
name|getCount
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
name|count
condition|;
name|i
operator|++
control|)
block|{
name|processContent
argument_list|(
name|multipart
operator|.
name|getBodyPart
argument_list|(
name|i
argument_list|)
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|handleProtectedPart
parameter_list|(
name|Multipart
name|multipart
parameter_list|,
name|int
name|index
parameter_list|,
name|StringBuilder
name|buffer
parameter_list|,
name|RDFContainer
name|rdf
parameter_list|)
throws|throws
name|MessagingException
throws|,
name|IOException
throws|,
name|ExtractorException
block|{
if|if
condition|(
name|index
operator|<
name|multipart
operator|.
name|getCount
argument_list|()
condition|)
block|{
name|processContent
argument_list|(
name|multipart
operator|.
name|getBodyPart
argument_list|(
name|index
argument_list|)
argument_list|,
name|buffer
argument_list|,
name|rdf
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|int
name|getPartWithMimeType
parameter_list|(
name|Multipart
name|multipart
parameter_list|,
name|String
name|mimeType
parameter_list|)
throws|throws
name|MessagingException
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|multipart
operator|.
name|getCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BodyPart
name|bodyPart
init|=
name|multipart
operator|.
name|getBodyPart
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|mimeType
operator|.
name|equalsIgnoreCase
argument_list|(
name|getMimeType
argument_list|(
name|bodyPart
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|i
return|;
block|}
block|}
return|return
operator|-
literal|1
return|;
block|}
specifier|protected
name|String
name|getContentEncoding
parameter_list|(
name|ContentType
name|contentType
parameter_list|)
block|{
if|if
condition|(
name|contentType
operator|!=
literal|null
condition|)
block|{
return|return
name|contentType
operator|.
name|getParameter
argument_list|(
literal|"charset"
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|protected
name|String
name|getEncoding
parameter_list|(
name|Part
name|mailPart
parameter_list|)
throws|throws
name|MessagingException
block|{
name|DataHandler
name|handler
init|=
name|mailPart
operator|.
name|getDataHandler
argument_list|()
decl_stmt|;
if|if
condition|(
name|handler
operator|!=
literal|null
condition|)
block|{
return|return
name|MimeUtility
operator|.
name|getEncoding
argument_list|(
name|handler
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|protected
name|String
name|getMimeType
parameter_list|(
name|Part
name|mailPart
parameter_list|)
throws|throws
name|MessagingException
block|{
name|String
name|contentType
init|=
name|mailPart
operator|.
name|getContentType
argument_list|()
decl_stmt|;
if|if
condition|(
name|contentType
operator|!=
literal|null
condition|)
block|{
name|ContentType
name|ct
init|=
operator|new
name|ContentType
argument_list|(
name|contentType
argument_list|)
decl_stmt|;
return|return
name|ct
operator|.
name|getBaseType
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|protected
name|String
name|extractTextFromHtml
parameter_list|(
name|String
name|string
parameter_list|,
name|String
name|charset
parameter_list|,
name|RDFContainer
name|rdf
parameter_list|)
throws|throws
name|ExtractorException
block|{
comment|// parse the HTML and extract full-text and metadata
name|HtmlTextExtractUtil
name|extractor
decl_stmt|;
try|try
block|{
name|extractor
operator|=
operator|new
name|HtmlTextExtractUtil
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InitializationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ExtractorException
argument_list|(
literal|"Could not initialize HtmlExtractor: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
name|InputStream
name|stream
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|string
operator|.
name|getBytes
argument_list|()
argument_list|)
decl_stmt|;
name|RDFContainerFactory
name|containerFactory
init|=
operator|new
name|RDFContainerFactoryImpl
argument_list|()
decl_stmt|;
name|URI
name|id
init|=
name|rdf
operator|.
name|getDescribedUri
argument_list|()
decl_stmt|;
name|RDFContainer
name|result
init|=
name|containerFactory
operator|.
name|getRDFContainer
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|extractor
operator|.
name|extract
argument_list|(
name|id
argument_list|,
name|charset
argument_list|,
name|stream
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|Model
name|meta
init|=
name|result
operator|.
name|getModel
argument_list|()
decl_stmt|;
comment|// append metadata and full-text to a string buffer
name|StringBuilder
name|buffer
init|=
operator|new
name|StringBuilder
argument_list|(
literal|32
operator|*
literal|1024
argument_list|)
decl_stmt|;
name|append
argument_list|(
name|buffer
argument_list|,
name|extractor
operator|.
name|getTitle
argument_list|(
name|meta
argument_list|)
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|buffer
argument_list|,
name|extractor
operator|.
name|getAuthor
argument_list|(
name|meta
argument_list|)
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|buffer
argument_list|,
name|extractor
operator|.
name|getDescription
argument_list|(
name|meta
argument_list|)
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|keywords
init|=
name|extractor
operator|.
name|getKeywords
argument_list|(
name|meta
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|kw
range|:
name|keywords
control|)
block|{
name|append
argument_list|(
name|buffer
argument_list|,
name|kw
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
block|}
name|buffer
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|append
argument_list|(
name|buffer
argument_list|,
name|extractor
operator|.
name|getText
argument_list|(
name|meta
argument_list|)
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
name|logger
operator|.
name|debug
argument_list|(
literal|"text extracted:\n{}"
argument_list|,
name|buffer
argument_list|)
expr_stmt|;
name|meta
operator|.
name|close
argument_list|()
expr_stmt|;
comment|// return the buffer's content
return|return
name|buffer
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|protected
name|void
name|append
parameter_list|(
name|StringBuilder
name|buffer
parameter_list|,
name|String
name|text
parameter_list|,
name|String
name|sep
parameter_list|)
block|{
if|if
condition|(
name|text
operator|!=
literal|null
condition|)
block|{
name|buffer
operator|.
name|append
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|buffer
operator|.
name|append
argument_list|(
name|sep
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|Address
index|[]
name|getRecipients
parameter_list|(
name|MimeMessage
name|message
parameter_list|,
name|RecipientType
name|type
parameter_list|)
throws|throws
name|MessagingException
block|{
name|Address
index|[]
name|result
init|=
literal|null
decl_stmt|;
try|try
block|{
name|result
operator|=
name|message
operator|.
name|getRecipients
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|AddressException
name|e
parameter_list|)
block|{
comment|// ignore
block|}
return|return
name|result
return|;
block|}
specifier|protected
name|void
name|copyAddress
parameter_list|(
name|Object
name|address
parameter_list|,
name|URI
name|predicate
parameter_list|,
name|RDFContainer
name|result
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|address
operator|instanceof
name|InternetAddress
condition|)
block|{
name|MailUtil
operator|.
name|addAddressMetadata
argument_list|(
operator|(
name|InternetAddress
operator|)
name|address
argument_list|,
name|predicate
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|address
operator|instanceof
name|InternetAddress
index|[]
condition|)
block|{
name|InternetAddress
index|[]
name|array
init|=
operator|(
name|InternetAddress
index|[]
operator|)
name|address
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
name|array
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|MailUtil
operator|.
name|addAddressMetadata
argument_list|(
name|array
index|[
name|i
index|]
argument_list|,
name|predicate
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|ModelException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"ModelException while adding address metadata"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
throws|throws
name|Exception
block|{
name|int
name|argv
init|=
literal|0
decl_stmt|;
name|SimpleMailExtractor
name|extractor
init|=
operator|new
name|SimpleMailExtractor
argument_list|()
decl_stmt|;
name|RDFContainerFactory
name|rdfFactory
init|=
operator|new
name|RDFContainerFactoryImpl
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|argv
init|;
name|i
operator|<
name|args
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|args
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|InputStream
name|in
init|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|URI
name|uri
init|=
operator|new
name|URIImpl
argument_list|(
name|file
operator|.
name|toURI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|RDFContainer
name|rdfContainer
init|=
name|rdfFactory
operator|.
name|getRDFContainer
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|extractor
operator|.
name|extract
argument_list|(
name|uri
argument_list|,
name|in
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|rdfContainer
argument_list|)
expr_stmt|;
name|Model
name|model
init|=
name|rdfContainer
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|model
operator|.
name|writeTo
argument_list|(
name|System
operator|.
name|out
argument_list|,
name|Syntax
operator|.
name|RdfXml
argument_list|)
expr_stmt|;
name|model
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
