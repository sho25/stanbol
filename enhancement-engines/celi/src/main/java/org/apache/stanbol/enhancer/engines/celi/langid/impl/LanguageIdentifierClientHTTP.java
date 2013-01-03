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
name|celi
operator|.
name|langid
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
name|BufferedWriter
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
name|io
operator|.
name|OutputStreamWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpURLConnection
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|soap
operator|.
name|MessageFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|soap
operator|.
name|SOAPBody
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|soap
operator|.
name|SOAPException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|soap
operator|.
name|SOAPMessage
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|soap
operator|.
name|SOAPPart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|stream
operator|.
name|StreamSource
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
name|util
operator|.
name|Base64
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
name|commons
operator|.
name|lang
operator|.
name|StringEscapeUtils
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
name|celi
operator|.
name|utils
operator|.
name|Utils
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

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_class
specifier|public
class|class
name|LanguageIdentifierClientHTTP
block|{
comment|/**      * The UTF-8 {@link Charset}      */
specifier|private
specifier|static
specifier|final
name|Charset
name|UTF8
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
comment|/**      * The content type "text/xml; charset={@link #UTF8}"      */
specifier|private
specifier|static
specifier|final
name|String
name|CONTENT_TYPE
init|=
literal|"text/xml; charset="
operator|+
name|UTF8
operator|.
name|name
argument_list|()
decl_stmt|;
comment|/**      * The XML version, encoding; SOAP envelope, heder and starting element of the body;      * processTextRequest and text starting element.      */
specifier|private
specifier|static
specifier|final
name|String
name|SOAP_PREFIX
init|=
literal|"<soapenv:Envelope "
operator|+
literal|"xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
operator|+
literal|"xmlns:lan=\"http://research.celi.it/LanguageIdentifierWS\">"
operator|+
literal|"<soapenv:Header/><soapenv:Body>"
decl_stmt|;
comment|/**      * closes the text, processTextRequest, SOAP body and envelope      */
specifier|private
specifier|static
specifier|final
name|String
name|SOAP_SUFFIX
init|=
literal|"</soapenv:Body></soapenv:Envelope>"
decl_stmt|;
specifier|private
name|URL
name|serviceEP
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|requestHeaders
decl_stmt|;
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
name|LanguageIdentifierClientHTTP
parameter_list|(
name|URL
name|serviceUrl
parameter_list|,
name|String
name|licenseKey
parameter_list|)
block|{
name|this
operator|.
name|serviceEP
operator|=
name|serviceUrl
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|headers
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|headers
operator|.
name|put
argument_list|(
literal|"Content-Type"
argument_list|,
name|CONTENT_TYPE
argument_list|)
expr_stmt|;
if|if
condition|(
name|licenseKey
operator|!=
literal|null
condition|)
block|{
name|String
name|encoded
init|=
name|Base64
operator|.
name|encode
argument_list|(
name|licenseKey
operator|.
name|getBytes
argument_list|(
name|UTF8
argument_list|)
argument_list|)
decl_stmt|;
name|headers
operator|.
name|put
argument_list|(
literal|"Authorization"
argument_list|,
literal|"Basic "
operator|+
name|encoded
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|requestHeaders
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|headers
argument_list|)
expr_stmt|;
block|}
comment|//NOTE (rwesten): I rather do the error handling in the EnhancementEngine!
specifier|public
name|List
argument_list|<
name|GuessedLanguage
argument_list|>
name|guessQueryLanguage
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|IOException
throws|,
name|SOAPException
block|{
if|if
condition|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// no text
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
comment|//no language
block|}
comment|//create the POST request
name|HttpURLConnection
name|con
init|=
name|Utils
operator|.
name|createPostRequest
argument_list|(
name|serviceEP
argument_list|,
name|requestHeaders
argument_list|)
decl_stmt|;
comment|//write content
name|BufferedWriter
name|writer
init|=
operator|new
name|BufferedWriter
argument_list|(
operator|new
name|OutputStreamWriter
argument_list|(
name|con
operator|.
name|getOutputStream
argument_list|()
argument_list|,
name|UTF8
argument_list|)
argument_list|)
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|SOAP_PREFIX
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"<lan:guessQueryLanguage><textToGuess>"
argument_list|)
expr_stmt|;
name|StringEscapeUtils
operator|.
name|escapeXml
argument_list|(
name|writer
argument_list|,
name|text
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"</textToGuess></lan:guessQueryLanguage>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|SOAP_SUFFIX
argument_list|)
expr_stmt|;
name|writer
operator|.
name|close
argument_list|()
expr_stmt|;
comment|//Call the service
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|InputStream
name|stream
init|=
name|con
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Request to {} took {}ms"
argument_list|,
name|serviceEP
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
argument_list|)
expr_stmt|;
comment|// Create SoapMessage and parse the results
name|MessageFactory
name|msgFactory
init|=
name|MessageFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|SOAPMessage
name|message
init|=
name|msgFactory
operator|.
name|createMessage
argument_list|()
decl_stmt|;
name|SOAPPart
name|soapPart
init|=
name|message
operator|.
name|getSOAPPart
argument_list|()
decl_stmt|;
comment|// Load the SOAP text into a stream source
name|StreamSource
name|source
init|=
operator|new
name|StreamSource
argument_list|(
name|stream
argument_list|)
decl_stmt|;
comment|// Set contents of message
name|soapPart
operator|.
name|setContent
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|SOAPBody
name|soapBody
init|=
name|message
operator|.
name|getSOAPBody
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|GuessedLanguage
argument_list|>
name|guesses
init|=
operator|new
name|Vector
argument_list|<
name|GuessedLanguage
argument_list|>
argument_list|()
decl_stmt|;
name|NodeList
name|nlist
init|=
name|soapBody
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"return"
argument_list|)
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
name|nlist
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
name|Element
name|result
init|=
operator|(
name|Element
operator|)
name|nlist
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|lang
init|=
name|result
operator|.
name|getAttribute
argument_list|(
literal|"language"
argument_list|)
decl_stmt|;
name|double
name|d
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|result
operator|.
name|getAttribute
argument_list|(
literal|"guessConfidence"
argument_list|)
argument_list|)
decl_stmt|;
name|guesses
operator|.
name|add
argument_list|(
operator|new
name|GuessedLanguage
argument_list|(
name|lang
argument_list|,
name|d
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|guesses
return|;
block|}
comment|//NOTE (rwesten): I rather do the error handling in the EnhancementEngine!
specifier|public
name|List
argument_list|<
name|GuessedLanguage
argument_list|>
name|guessLanguage
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|IOException
throws|,
name|SOAPException
block|{
if|if
condition|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//no text -> no language
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
comment|//create the POST request
name|HttpURLConnection
name|con
init|=
name|Utils
operator|.
name|createPostRequest
argument_list|(
name|serviceEP
argument_list|,
name|requestHeaders
argument_list|)
decl_stmt|;
comment|//write content
name|BufferedWriter
name|writer
init|=
operator|new
name|BufferedWriter
argument_list|(
operator|new
name|OutputStreamWriter
argument_list|(
name|con
operator|.
name|getOutputStream
argument_list|()
argument_list|,
name|UTF8
argument_list|)
argument_list|)
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|SOAP_PREFIX
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"<lan:guessLanguage><textToGuess>"
argument_list|)
expr_stmt|;
name|StringEscapeUtils
operator|.
name|escapeXml
argument_list|(
name|writer
argument_list|,
name|text
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"</textToGuess></lan:guessLanguage>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|SOAP_SUFFIX
argument_list|)
expr_stmt|;
name|writer
operator|.
name|close
argument_list|()
expr_stmt|;
comment|//Call the service
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|InputStream
name|stream
init|=
name|con
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Request to {} took {}ms"
argument_list|,
name|serviceEP
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
argument_list|)
expr_stmt|;
comment|// Create SoapMessage and parse the results
name|MessageFactory
name|msgFactory
init|=
name|MessageFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|SOAPMessage
name|message
init|=
name|msgFactory
operator|.
name|createMessage
argument_list|()
decl_stmt|;
name|SOAPPart
name|soapPart
init|=
name|message
operator|.
name|getSOAPPart
argument_list|()
decl_stmt|;
comment|// Load the SOAP text into a stream source
name|StreamSource
name|source
init|=
operator|new
name|StreamSource
argument_list|(
name|stream
argument_list|)
decl_stmt|;
comment|// Set contents of message
name|soapPart
operator|.
name|setContent
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|SOAPBody
name|soapBody
init|=
name|message
operator|.
name|getSOAPBody
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|GuessedLanguage
argument_list|>
name|guesses
init|=
operator|new
name|Vector
argument_list|<
name|GuessedLanguage
argument_list|>
argument_list|()
decl_stmt|;
name|NodeList
name|nlist
init|=
name|soapBody
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"return"
argument_list|)
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
name|nlist
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
name|Element
name|result
init|=
operator|(
name|Element
operator|)
name|nlist
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|lang
init|=
name|result
operator|.
name|getAttribute
argument_list|(
literal|"language"
argument_list|)
decl_stmt|;
name|double
name|d
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|result
operator|.
name|getAttribute
argument_list|(
literal|"guessConfidence"
argument_list|)
argument_list|)
decl_stmt|;
name|guesses
operator|.
name|add
argument_list|(
operator|new
name|GuessedLanguage
argument_list|(
name|lang
argument_list|,
name|d
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|guesses
return|;
block|}
block|}
end_class

end_unit

