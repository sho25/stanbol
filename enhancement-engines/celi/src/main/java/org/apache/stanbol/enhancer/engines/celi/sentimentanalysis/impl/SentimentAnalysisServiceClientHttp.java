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
name|sentimentanalysis
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
name|InputStreamReader
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
name|SentimentAnalysisServiceClientHttp
block|{
specifier|static
specifier|public
specifier|final
name|String
name|positive
init|=
literal|"POSITIVE"
decl_stmt|,
name|negative
init|=
literal|"NEGATIVE"
decl_stmt|;
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
comment|/** 	 * The XML version, encoding; SOAP envelope, heder and starting element of the body; 	 * processTextRequest and text starting element. 	 */
specifier|private
specifier|static
specifier|final
name|String
name|SOAP_PREFIX
init|=
literal|"<?xml version=\"1.0\" encoding=\""
operator|+
name|UTF8
operator|.
name|name
argument_list|()
operator|+
literal|"\"?>"
operator|+
literal|"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
operator|+
literal|"xmlns:sen=\"http://SentimentAnalysis.service.celi.it/\"><soapenv:Header/>"
operator|+
literal|"<soapenv:Body>"
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
specifier|final
name|URL
name|serviceEP
decl_stmt|;
specifier|private
specifier|final
name|String
name|licenseKey
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
specifier|private
name|int
name|connectionTimeout
decl_stmt|;
specifier|public
name|SentimentAnalysisServiceClientHttp
parameter_list|(
name|URL
name|serviceUrl
parameter_list|,
name|String
name|licenseKey
parameter_list|,
name|int
name|connectionTimeout
parameter_list|)
block|{
name|this
operator|.
name|serviceEP
operator|=
name|serviceUrl
expr_stmt|;
name|this
operator|.
name|licenseKey
operator|=
name|licenseKey
expr_stmt|;
name|this
operator|.
name|connectionTimeout
operator|=
name|connectionTimeout
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
name|this
operator|.
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
specifier|public
name|List
argument_list|<
name|SentimentExpression
argument_list|>
name|extractSentimentExpressions
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|lang
parameter_list|)
throws|throws
name|SOAPException
throws|,
name|IOException
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
comment|//no text -> no extractions
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
argument_list|,
name|connectionTimeout
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
literal|"<sen:analyzeText><arg0><![CDATA["
operator|+
name|text
operator|+
literal|"]]></arg0><arg1>"
operator|+
name|lang
operator|+
literal|"</arg1></sen:analyzeText>"
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
operator|new
name|InputStreamReader
argument_list|(
name|stream
argument_list|,
name|UTF8
argument_list|)
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
comment|//extract the results
name|List
argument_list|<
name|SentimentExpression
argument_list|>
name|sentExpressions
init|=
operator|new
name|Vector
argument_list|<
name|SentimentExpression
argument_list|>
argument_list|()
decl_stmt|;
name|NodeList
name|nlist
init|=
name|soapBody
operator|.
name|getElementsByTagName
argument_list|(
literal|"relation"
argument_list|)
decl_stmt|;
name|String
name|snippetStr
init|=
literal|null
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
name|Element
name|relation
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
name|sentimentType
init|=
name|relation
operator|.
name|getAttribute
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
name|int
name|startSnippet
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|relation
operator|.
name|getAttribute
argument_list|(
literal|"start"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|endSnippet
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|relation
operator|.
name|getAttribute
argument_list|(
literal|"end"
argument_list|)
argument_list|)
decl_stmt|;
name|NodeList
name|snippet
init|=
name|relation
operator|.
name|getElementsByTagName
argument_list|(
literal|"snippet"
argument_list|)
decl_stmt|;
if|if
condition|(
name|snippet
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|snippetStr
operator|=
name|snippet
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|.
name|getTextContent
argument_list|()
expr_stmt|;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|arguments
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|NodeList
name|argsList
init|=
name|relation
operator|.
name|getElementsByTagName
argument_list|(
literal|"arguments"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|x
init|=
literal|0
init|;
name|x
operator|<
name|argsList
operator|.
name|getLength
argument_list|()
condition|;
name|x
operator|++
control|)
block|{
name|NodeList
name|lemmas
init|=
operator|(
operator|(
name|Element
operator|)
name|argsList
operator|.
name|item
argument_list|(
name|x
argument_list|)
operator|)
operator|.
name|getElementsByTagName
argument_list|(
literal|"lemma"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|y
init|=
literal|0
init|;
name|y
operator|<
name|lemmas
operator|.
name|getLength
argument_list|()
condition|;
name|y
operator|++
control|)
block|{
name|Element
name|lemma
init|=
operator|(
name|Element
operator|)
name|lemmas
operator|.
name|item
argument_list|(
name|y
argument_list|)
decl_stmt|;
name|String
name|lemmaStr
init|=
name|lemma
operator|.
name|getAttribute
argument_list|(
literal|"lemma"
argument_list|)
decl_stmt|;
if|if
condition|(
name|lemmaStr
operator|!=
literal|null
operator|&&
name|lemmaStr
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|arguments
operator|.
name|add
argument_list|(
name|lemmaStr
argument_list|)
expr_stmt|;
block|}
block|}
name|SentimentExpression
name|expr
init|=
operator|new
name|SentimentExpression
argument_list|(
name|sentimentType
argument_list|,
name|snippetStr
argument_list|,
name|startSnippet
argument_list|,
name|endSnippet
argument_list|,
name|arguments
argument_list|)
decl_stmt|;
name|sentExpressions
operator|.
name|add
argument_list|(
name|expr
argument_list|)
expr_stmt|;
block|}
return|return
name|sentExpressions
return|;
block|}
block|}
end_class

end_unit

