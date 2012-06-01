begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|classification
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
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
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
name|UriRef
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
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|NamespaceEnum
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
name|ClassificationClientHTTP
block|{
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ClassificationClientHTTP
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//NOTE: Defining charset, content-type and SOAP prefix/suffix as
comment|//      constants does make more easy to configure those things
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
literal|"xmlns:clas=\"http://linguagrid.org/v20110204/classification\"><soapenv:Header/><soapenv:Body>"
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
comment|//TODO: This should be configurable
specifier|private
specifier|static
specifier|final
name|int
name|maxResultToReturn
init|=
literal|3
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
comment|//NOTE: the request headers are the same for all request - so they can be
comment|//      initialized in the constructor.
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
specifier|public
name|ClassificationClientHTTP
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
comment|/* 	 * NOTE: parsing/returning a String requires to create in-memory copies 	 *       of the sent/received data. Imaging users that send the text of 	 *       100 pages PDF files to the Stanbol Enhancer. 	 *       Because of that an implementation that directly streams the 	 *       StringEscapeUtils.escapeXml(..) to the request is preferable  	 *        	 *       This will no longer allow to debug the data of the request and 	 *       response. See the commented main method at the end for alternatives 	 */
comment|//	public String doPostRequest(URL url, String body) throws IOException {
comment|//
comment|//		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
comment|//		urlConn.setRequestMethod("POST");
comment|//		urlConn.setDoInput(true);
comment|//		if (null != body) {
comment|//			urlConn.setDoOutput(true);
comment|//		} else {
comment|//			urlConn.setDoOutput(false);
comment|//		}
comment|//		urlConn.setUseCaches(false);
comment|//		String	contentType = "text/xml; charset=utf-8";
comment|//		urlConn.setRequestProperty("Content-Type", contentType);
comment|//		if(this.licenseKey!=null){
comment|//			String encoded = Base64.encode(this.licenseKey.getBytes("UTF-8"));
comment|//			urlConn.setRequestProperty("Authorization", "Basic "+encoded);
comment|//		}
comment|//
comment|//		// send POST output
comment|//		if (null != body) {
comment|//			OutputStreamWriter printout = new OutputStreamWriter(urlConn.getOutputStream(), "UTF-8");
comment|//			printout.write(body);
comment|//			printout.flush();
comment|//			printout.close();
comment|//		}
comment|//
comment|//		//close connection
comment|//		urlConn.disconnect();
comment|//
comment|//		// get response data
comment|//		return IOUtils.toString(urlConn.getInputStream(), "UTF-8");
comment|//	}
comment|//NOTE: forward IOException and SOAPExceptions to allow correct error handling
comment|//      by the EnhancementJobManager.
comment|//      Also RuntimeExceptions MUST NOT be cached out of the same reason!
specifier|public
name|List
argument_list|<
name|Concept
argument_list|>
name|extractConcepts
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
comment|//no text -> no classification
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
comment|//"stream" the request content directly to the buffered writer
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
literal|"<clas:classify>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"<clas:user>wiki</clas:user>"
argument_list|)
expr_stmt|;
comment|//TODO: should the user be configurable?
name|writer
operator|.
name|write
argument_list|(
literal|"<clas:model>"
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
literal|"</clas:model>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"<clas:text>"
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
comment|//write the escaped text directly to the request
name|writer
operator|.
name|write
argument_list|(
literal|"</clas:text>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"</clas:classify>"
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
comment|//NOTE: forward IOException and SOAPExceptions to allow correct error handling
comment|//      by the EnhancementJobManager.
comment|//      Also RuntimeExceptions MUST NOT be cached out of the same reason!
comment|//		try {
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
comment|// NOTE: directly use the InputStream provided by the URLConnection!
comment|//			ByteArrayInputStream stream = new ByteArrayInputStream(responseXml.getBytes("UTF-8"));
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
name|Concept
argument_list|>
name|extractedConcepts
init|=
operator|new
name|Vector
argument_list|<
name|Concept
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
name|HashSet
argument_list|<
name|String
argument_list|>
name|inserted
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
operator|&&
name|i
operator|<
name|maxResultToReturn
condition|;
name|i
operator|++
control|)
block|{
comment|//NOTE: do not catch RuntimeExceptions. Error handling is done by
comment|//      the EnhancementJobManager!
comment|//			try {
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
comment|//NOTE: (rwesten) implemented a mapping from the CELI classification
comment|//      to the Stanbol fise:TopicEnhancements (STANBOL-617) that
comment|//        * one fise:TopicAnnotation is generated per "model"
comment|//        * the whole label string is used as fise:entity-label
comment|//        * the uri of the most specific dbpedia ontology type (see
comment|//          selectClassificationClass) is used as fise:entity-reference
comment|//      This has the intuition that for users it is easier to grasp
comment|//      the meaning of the whole lable, while for machines the link
comment|//      to the most specific dbpedia ontology class is best suited.
name|String
name|model
init|=
name|result
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"label"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|.
name|getTextContent
argument_list|()
decl_stmt|;
name|model
operator|=
name|model
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|model
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|UriRef
name|modelConcept
init|=
name|selectClassificationClass
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|String
name|conf
init|=
name|result
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"score"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|.
name|getTextContent
argument_list|()
decl_stmt|;
name|Double
name|confidence
init|=
operator|new
name|Double
argument_list|(
name|conf
argument_list|)
decl_stmt|;
name|extractedConcepts
operator|.
name|add
argument_list|(
operator|new
name|Concept
argument_list|(
name|model
argument_list|,
name|modelConcept
argument_list|,
name|confidence
argument_list|)
argument_list|)
expr_stmt|;
comment|//			} catch (Exception e) {
comment|//				e.printStackTrace();
comment|//			}
block|}
comment|//		} catch (Exception e) {
comment|//			e.printStackTrace();
comment|//		}
return|return
name|extractedConcepts
return|;
block|}
comment|/**      * TopicClassifications require only a single fise:entity-reference.      * However the CELI classification service delivers<p>      *<code><pre>      *<ns2:label>[Organisation HockeyTeam SportsTeam]</ns2:label>      *</pre></code>      * because of that this method needs to select one of the labels.<p>      * This method currently selects the 2nd token if there are more than one      * concept suggestions included. NOTE that the whole literal is used as      * fise:entity-label!      * @param classificationLabels the label string      * @return the selected label      */
specifier|private
name|UriRef
name|selectClassificationClass
parameter_list|(
name|String
name|classificationLabels
parameter_list|)
block|{
comment|//NOTE: (rwesten) In general it would be better if CELI could provide
comment|//      de-referenceable URLs for those suggestions.
comment|//      If that is possible one would no longer need to link to the
comment|//      most specific dbpedia ontology class for a category e.g.
comment|//          http://dbpedia.org/ontology/HockeyTeam
comment|//      for
comment|//          [Organisation HockeyTeam SportsTeam]
comment|//      but e.g.
comment|//          http://linguagrid.org/category/HockeyTeam
comment|//      meaning the linguagrid could provide categories as skos thesaurus
comment|//      via it's web interface
name|int
name|start
init|=
name|classificationLabels
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'['
condition|?
literal|1
else|:
literal|0
decl_stmt|;
name|int
name|end
init|=
name|classificationLabels
operator|.
name|charAt
argument_list|(
name|classificationLabels
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|']'
condition|?
name|classificationLabels
operator|.
name|length
argument_list|()
operator|-
literal|1
else|:
name|classificationLabels
operator|.
name|length
argument_list|()
decl_stmt|;
name|String
index|[]
name|tmps
init|=
name|classificationLabels
operator|.
name|substring
argument_list|(
name|start
argument_list|,
name|end
argument_list|)
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
return|return
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dbpedia_ont
operator|.
name|getNamespace
argument_list|()
operator|+
comment|//the namespace
operator|(
name|tmps
operator|.
name|length
operator|>
literal|1
condition|?
name|tmps
index|[
literal|1
index|]
else|:
name|tmps
index|[
literal|0
index|]
operator|)
argument_list|)
return|;
comment|//the Class for the label
block|}
comment|//NOTE: If you stream the contents directly to the stream, you can no longer
comment|//      debug the request/response. Because of that it is sometimes
comment|//      helpful to have a main method for those tests
comment|//      An even better variant would be to write a UnitTest for that!!
comment|//      This would be recommended of the called service is still in beta
comment|//      and may change at any time
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
name|String
name|lang
init|=
literal|"fr"
decl_stmt|;
name|String
name|text
init|=
literal|"Brigitte Bardot, née  le 28 septembre "
operator|+
literal|"1934 à Paris, est une actrice de cinéma et chanteuse française."
decl_stmt|;
comment|//For request testing
comment|//Writer request = new StringWriter();
comment|//For response testing
name|HttpURLConnection
name|con
init|=
name|Utils
operator|.
name|createPostRequest
argument_list|(
operator|new
name|URL
argument_list|(
literal|"http://linguagrid.org/LSGrid/ws/dbpedia-classification"
argument_list|)
argument_list|,
name|Collections
operator|.
name|singletonMap
argument_list|(
literal|"Content-Type"
argument_list|,
name|CONTENT_TYPE
argument_list|)
argument_list|)
decl_stmt|;
name|Writer
name|request
init|=
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
decl_stmt|;
comment|//"stream" the request content directly to the buffered writer
name|BufferedWriter
name|writer
init|=
operator|new
name|BufferedWriter
argument_list|(
name|request
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
literal|"<clas:classify>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"<clas:user>wiki</clas:user>"
argument_list|)
expr_stmt|;
comment|//TODO: should the user be configurable?
name|writer
operator|.
name|write
argument_list|(
literal|"<clas:model>"
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
literal|"</clas:model>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"<clas:text>"
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
comment|//write the escaped text directly to the request
name|writer
operator|.
name|write
argument_list|(
literal|"</clas:text>"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|"</clas:classify>"
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
comment|//log the Request (if request testing)
comment|//log.info("Request \n{}",request.toString());
comment|//for response testing we need to call the service
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
name|info
argument_list|(
literal|"Request to took {}ms"
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Response:\n{}"
argument_list|,
name|IOUtils
operator|.
name|toString
argument_list|(
name|stream
argument_list|)
argument_list|)
expr_stmt|;
name|stream
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

