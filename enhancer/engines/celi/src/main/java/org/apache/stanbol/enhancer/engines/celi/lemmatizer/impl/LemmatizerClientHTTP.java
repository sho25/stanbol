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
name|lemmatizer
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|LemmatizerClientHTTP
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
specifier|private
specifier|static
specifier|final
name|String
name|SOAP_REQUEST_PREFIX
init|=
literal|"<soapenv:Envelope "
operator|+
literal|"xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
operator|+
literal|"xmlns:mor=\"http://research.celi.it/MorphologicalAnalyzer\">"
operator|+
literal|"<soapenv:Header/><soapenv:Body>"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SOAP_REQUEST_SUFFIX
init|=
literal|"</soapenv:Body></soapenv:Envelope>"
decl_stmt|;
specifier|private
name|URL
name|serviceEP
decl_stmt|;
specifier|private
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
specifier|public
name|LemmatizerClientHTTP
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
name|this
operator|.
name|licenseKey
operator|=
name|licenseKey
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
name|LexicalEntry
argument_list|>
name|performMorfologicalAnalysis
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|lang
parameter_list|)
throws|throws
name|IOException
throws|,
name|SOAPException
block|{
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
comment|//write the SOAP envelope, header and start the body
name|writer
operator|.
name|write
argument_list|(
name|SOAP_REQUEST_PREFIX
argument_list|)
expr_stmt|;
comment|//write the data (language and text)
name|writer
operator|.
name|write
argument_list|(
literal|"<mor:inputText lang=\""
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|lang
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"\" text=\""
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
literal|"\"/>"
argument_list|)
expr_stmt|;
comment|//close the SOAP body and envelope
name|writer
operator|.
name|write
argument_list|(
name|SOAP_REQUEST_SUFFIX
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
if|if
condition|(
name|log
operator|.
name|isTraceEnabled
argument_list|()
condition|)
block|{
comment|//log the response if trace is enabled
name|String
name|soapResponse
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|stream
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|log
operator|.
name|trace
argument_list|(
literal|"SoapResponse: \n{}\n"
argument_list|,
name|soapResponse
argument_list|)
expr_stmt|;
name|stream
operator|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|soapResponse
operator|.
name|getBytes
argument_list|(
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Create SoapMessage
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
name|LexicalEntry
argument_list|>
name|lista
init|=
operator|new
name|Vector
argument_list|<
name|LexicalEntry
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
literal|"LexicalEntry"
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
name|wordForm
init|=
name|result
operator|.
name|getAttribute
argument_list|(
literal|"WordForm"
argument_list|)
decl_stmt|;
name|int
name|from
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|result
operator|.
name|getAttribute
argument_list|(
literal|"OffsetFrom"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|to
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|result
operator|.
name|getAttribute
argument_list|(
literal|"OffsetTo"
argument_list|)
argument_list|)
decl_stmt|;
name|LexicalEntry
name|le
init|=
operator|new
name|LexicalEntry
argument_list|(
name|wordForm
argument_list|,
name|from
argument_list|,
name|to
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Reading
argument_list|>
name|readings
init|=
operator|new
name|Vector
argument_list|<
name|Reading
argument_list|>
argument_list|()
decl_stmt|;
name|NodeList
name|lemmasList
init|=
name|result
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Lemma"
argument_list|)
decl_stmt|;
if|if
condition|(
name|lemmasList
operator|!=
literal|null
operator|&&
name|lemmasList
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|lemmasList
operator|.
name|getLength
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|Element
name|lemmaElm
init|=
operator|(
name|Element
operator|)
name|lemmasList
operator|.
name|item
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|String
name|lemma
init|=
name|lemmaElm
operator|.
name|getTextContent
argument_list|()
decl_stmt|;
name|NodeList
name|features
init|=
operator|(
operator|(
name|Element
operator|)
name|lemmaElm
operator|.
name|getParentNode
argument_list|()
operator|)
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"LexicalFeature"
argument_list|)
decl_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|featuresMap
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|k
init|=
literal|0
init|;
name|features
operator|!=
literal|null
operator|&&
name|k
operator|<
name|features
operator|.
name|getLength
argument_list|()
condition|;
name|k
operator|++
control|)
block|{
name|Element
name|feat
init|=
operator|(
name|Element
operator|)
name|features
operator|.
name|item
argument_list|(
name|k
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|feat
operator|.
name|getAttribute
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|feat
operator|.
name|getTextContent
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|values
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|featuresMap
operator|.
name|containsKey
argument_list|(
name|name
argument_list|)
condition|)
name|values
operator|=
name|featuresMap
operator|.
name|get
argument_list|(
name|name
argument_list|)
expr_stmt|;
else|else
name|values
operator|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|featuresMap
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|values
argument_list|)
expr_stmt|;
block|}
name|Reading
name|r
init|=
operator|new
name|Reading
argument_list|(
name|lemma
argument_list|,
name|featuresMap
argument_list|)
decl_stmt|;
name|readings
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
block|}
name|le
operator|.
name|setTermReadings
argument_list|(
name|readings
argument_list|)
expr_stmt|;
name|lista
operator|.
name|add
argument_list|(
name|le
argument_list|)
expr_stmt|;
block|}
return|return
name|lista
return|;
block|}
specifier|public
name|String
name|lemmatizeContents
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
comment|//write the SOAP envelope, header and start the body
name|writer
operator|.
name|write
argument_list|(
name|SOAP_REQUEST_PREFIX
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"<mor:inputText lang=\""
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|lang
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"\" text=\""
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
literal|"\"/>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|SOAP_REQUEST_SUFFIX
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
comment|// Create SoapMessage
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
name|StringBuilder
name|buff
init|=
operator|new
name|StringBuilder
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
literal|"LexicalEntry"
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
name|NodeList
name|lemmasList
init|=
name|result
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Lemma"
argument_list|)
decl_stmt|;
if|if
condition|(
name|lemmasList
operator|!=
literal|null
operator|&&
name|lemmasList
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|HashSet
argument_list|<
name|String
argument_list|>
name|lemmas
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|lemmasList
operator|.
name|getLength
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|lemmas
operator|.
name|add
argument_list|(
name|lemmasList
operator|.
name|item
argument_list|(
name|j
argument_list|)
operator|.
name|getTextContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|lemma
range|:
name|lemmas
control|)
block|{
name|buff
operator|.
name|append
argument_list|(
name|lemma
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|buff
operator|.
name|append
argument_list|(
name|result
operator|.
name|getAttributeNS
argument_list|(
literal|"*"
argument_list|,
literal|"WordForm"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|buff
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
return|;
block|}
block|}
end_class

end_unit

