begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * XMLUtil.java  *  * Created on November 9, 2007, 1:13 PM  *  * To change this template, choose Tools | Options and locate the template under  * the Source Creation and Management node. Right-click the template and choose  * Open. You can then make changes to the template in the Source Editor.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|store
operator|.
name|rest
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
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
name|StringReader
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
name|Locale
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
name|XMLConstants
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|JAXBContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|Marshaller
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|Unmarshaller
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
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
name|OutputKeys
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
name|Transformer
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
name|TransformerException
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
name|TransformerFactory
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
name|dom
operator|.
name|DOMSource
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
name|StreamResult
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
name|javax
operator|.
name|xml
operator|.
name|validation
operator|.
name|Schema
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|validation
operator|.
name|SchemaFactory
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
name|ontologymanager
operator|.
name|store
operator|.
name|model
operator|.
name|ObjectFactory
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
name|Document
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
name|Node
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
name|InputSource
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

begin_comment
comment|/**  * @author tuncay  */
end_comment

begin_class
specifier|public
class|class
name|XMLUtil
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
name|XMLUtil
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/** Creates a new instance of XMLUtil */
specifier|public
name|XMLUtil
parameter_list|()
block|{}
comment|/** returns null if Node is null */
specifier|public
specifier|static
name|Node
name|extractFromDOMTree
parameter_list|(
name|Node
name|node
parameter_list|)
throws|throws
name|ParserConfigurationException
block|{
if|if
condition|(
name|node
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|DocumentBuilderFactory
name|dbf
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|DocumentBuilder
name|db
init|=
name|dbf
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|theDocument
init|=
name|db
operator|.
name|newDocument
argument_list|()
decl_stmt|;
name|theDocument
operator|.
name|appendChild
argument_list|(
name|theDocument
operator|.
name|importNode
argument_list|(
name|node
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
comment|// logger.info(XMLUtil.convertToString(theDocument));
return|return
operator|(
name|Node
operator|)
name|theDocument
operator|.
name|getDocumentElement
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|parseContent
parameter_list|(
name|byte
index|[]
name|byteContent
parameter_list|)
throws|throws
name|ParserConfigurationException
throws|,
name|SAXException
throws|,
name|IOException
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|doc
init|=
literal|null
decl_stmt|;
name|String
name|content
init|=
operator|new
name|String
argument_list|(
name|byteContent
argument_list|)
decl_stmt|;
name|DocumentBuilderFactory
name|dbf
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// dbf.setIgnoringComments(false);
name|dbf
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// dbf.setNamespaceAware(false);
name|DocumentBuilder
name|docBuilder
init|=
name|dbf
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|StringReader
name|lReader
init|=
operator|new
name|StringReader
argument_list|(
name|content
argument_list|)
decl_stmt|;
name|InputSource
name|inputSource
init|=
operator|new
name|InputSource
argument_list|(
name|lReader
argument_list|)
decl_stmt|;
name|doc
operator|=
name|docBuilder
operator|.
name|parse
argument_list|(
name|inputSource
argument_list|)
expr_stmt|;
return|return
name|doc
return|;
block|}
specifier|public
specifier|static
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|parseContent
parameter_list|(
name|String
name|content
parameter_list|)
throws|throws
name|ParserConfigurationException
throws|,
name|SAXException
throws|,
name|IOException
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|doc
init|=
literal|null
decl_stmt|;
name|DocumentBuilderFactory
name|dbf
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// dbf.setIgnoringComments(false);
name|dbf
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// dbf.setNamespaceAware(false);
name|DocumentBuilder
name|docBuilder
init|=
name|dbf
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|StringReader
name|lReader
init|=
operator|new
name|StringReader
argument_list|(
name|content
argument_list|)
decl_stmt|;
name|InputSource
name|inputSource
init|=
operator|new
name|InputSource
argument_list|(
name|lReader
argument_list|)
decl_stmt|;
name|doc
operator|=
name|docBuilder
operator|.
name|parse
argument_list|(
name|inputSource
argument_list|)
expr_stmt|;
return|return
name|doc
return|;
block|}
specifier|public
specifier|static
name|String
name|convertToString
parameter_list|(
name|Node
name|node
parameter_list|)
throws|throws
name|TransformerException
block|{
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|TransformerFactory
name|transformerFactory
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|Transformer
name|transformer
init|=
name|transformerFactory
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|transformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|INDENT
argument_list|,
literal|"yes"
argument_list|)
expr_stmt|;
name|transformer
operator|.
name|setOutputProperty
argument_list|(
literal|"{http://xml.apache.org/xslt}indent-amount"
argument_list|,
literal|"4"
argument_list|)
expr_stmt|;
name|transformer
operator|.
name|transform
argument_list|(
operator|new
name|DOMSource
argument_list|(
name|node
argument_list|)
argument_list|,
operator|new
name|StreamResult
argument_list|(
name|sw
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|sw
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|byte
index|[]
name|convertToByteArray
parameter_list|(
name|Node
name|node
parameter_list|)
throws|throws
name|TransformerException
block|{
comment|/**          * FIXME: We assume that Transfor deals with encoding/charset internally          */
name|ByteArrayOutputStream
name|bos
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|TransformerFactory
name|transformerFactory
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|Transformer
name|transformer
init|=
name|transformerFactory
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|transformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|INDENT
argument_list|,
literal|"yes"
argument_list|)
expr_stmt|;
name|transformer
operator|.
name|setOutputProperty
argument_list|(
literal|"{http://xml.apache.org/xslt}indent-amount"
argument_list|,
literal|"4"
argument_list|)
expr_stmt|;
name|transformer
operator|.
name|transform
argument_list|(
operator|new
name|DOMSource
argument_list|(
name|node
argument_list|)
argument_list|,
operator|new
name|StreamResult
argument_list|(
name|bos
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|bos
operator|.
name|toByteArray
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Node
name|makeNamespaceUnaware
parameter_list|(
name|Node
name|node
parameter_list|)
throws|throws
name|TransformerException
throws|,
name|ParserConfigurationException
throws|,
name|SAXException
throws|,
name|IOException
block|{
if|if
condition|(
name|node
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|String
name|xmlString
init|=
name|convertToString
argument_list|(
name|node
argument_list|)
decl_stmt|;
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
name|doc
init|=
literal|null
decl_stmt|;
comment|// DocumentBuilderFactoryImpl dbf = new DocumentBuilderFactoryImpl();
name|DocumentBuilderFactory
name|dbf
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// dbf.setNamespaceAware(false);
name|DocumentBuilder
name|docBuilder
init|=
name|dbf
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|StringReader
name|lReader
init|=
operator|new
name|StringReader
argument_list|(
name|xmlString
argument_list|)
decl_stmt|;
name|InputSource
name|inputSource
init|=
operator|new
name|InputSource
argument_list|(
name|lReader
argument_list|)
decl_stmt|;
name|doc
operator|=
name|docBuilder
operator|.
name|parse
argument_list|(
name|inputSource
argument_list|)
expr_stmt|;
return|return
name|doc
return|;
block|}
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parseNamespaceBindings
parameter_list|(
name|String
name|namespaceBindings
parameter_list|)
block|{
if|if
condition|(
name|namespaceBindings
operator|==
literal|null
condition|)
return|return
literal|null
return|;
comment|// remove { and }
name|namespaceBindings
operator|=
name|namespaceBindings
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|namespaceBindings
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|String
index|[]
name|bindings
init|=
name|namespaceBindings
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|namespaces
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
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bindings
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|pair
init|=
name|bindings
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
operator|.
name|split
argument_list|(
literal|"="
argument_list|)
decl_stmt|;
name|String
name|prefix
init|=
name|pair
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|namespace
init|=
name|pair
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// Remove ' and '
comment|// namespace = namespace.substring(1,namespace.length()-1);
name|namespaces
operator|.
name|put
argument_list|(
name|prefix
argument_list|,
name|namespace
argument_list|)
expr_stmt|;
block|}
return|return
name|namespaces
return|;
block|}
specifier|public
specifier|static
name|Document
name|marshall
parameter_list|(
name|Object
name|object
parameter_list|,
name|String
name|context
parameter_list|,
name|String
index|[]
name|schemaLocations
parameter_list|)
block|{
name|Locale
name|oldLocale
init|=
name|Locale
operator|.
name|getDefault
argument_list|()
decl_stmt|;
name|Locale
operator|.
name|setDefault
argument_list|(
operator|new
name|Locale
argument_list|(
literal|"en"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|ClassLoader
name|cl
init|=
name|ObjectFactory
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
decl_stmt|;
name|JAXBContext
name|jc
init|=
name|JAXBContext
operator|.
name|newInstance
argument_list|(
name|context
argument_list|,
name|cl
argument_list|)
decl_stmt|;
name|Marshaller
name|marshaller
init|=
name|jc
operator|.
name|createMarshaller
argument_list|()
decl_stmt|;
comment|// Cihan SchemaFactory.newInstance() do not use current threads
comment|// class loader any more (OSGI problem)
comment|// FIXME How to dynamically change the SchemaFactory implementation
name|SchemaFactory
name|schemaFactory
init|=
name|SchemaFactory
operator|.
name|newInstance
argument_list|(
name|XMLConstants
operator|.
name|W3C_XML_SCHEMA_NS_URI
argument_list|,
literal|"com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory"
argument_list|,
name|XMLUtil
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|StreamSource
argument_list|>
name|streamSourceList
init|=
operator|new
name|Vector
argument_list|<
name|StreamSource
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
name|schemaLocations
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|InputStream
name|is
init|=
name|cl
operator|.
name|getResourceAsStream
argument_list|(
name|schemaLocations
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|StreamSource
name|streamSource
init|=
operator|new
name|StreamSource
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|streamSourceList
operator|.
name|add
argument_list|(
name|streamSource
argument_list|)
expr_stmt|;
block|}
name|StreamSource
name|sources
index|[]
init|=
operator|new
name|StreamSource
index|[
name|streamSourceList
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|Schema
name|schema
init|=
name|schemaFactory
operator|.
name|newSchema
argument_list|(
name|streamSourceList
operator|.
name|toArray
argument_list|(
name|sources
argument_list|)
argument_list|)
decl_stmt|;
name|marshaller
operator|.
name|setSchema
argument_list|(
name|schema
argument_list|)
expr_stmt|;
name|DocumentBuilderFactory
name|dbf
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|dbf
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|DocumentBuilder
name|db
init|=
name|dbf
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|Document
name|doc
init|=
name|db
operator|.
name|newDocument
argument_list|()
decl_stmt|;
name|marshaller
operator|.
name|marshal
argument_list|(
name|object
argument_list|,
name|doc
argument_list|)
expr_stmt|;
name|Locale
operator|.
name|setDefault
argument_list|(
name|oldLocale
argument_list|)
expr_stmt|;
return|return
name|doc
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error "
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
name|Locale
operator|.
name|setDefault
argument_list|(
name|oldLocale
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|Object
name|unmarshall
parameter_list|(
name|String
name|context
parameter_list|,
name|String
index|[]
name|schemaLocations
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|Locale
name|oldLocale
init|=
name|Locale
operator|.
name|getDefault
argument_list|()
decl_stmt|;
name|Locale
operator|.
name|setDefault
argument_list|(
operator|new
name|Locale
argument_list|(
literal|"en"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|ClassLoader
name|cl
init|=
name|ObjectFactory
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
decl_stmt|;
name|JAXBContext
name|jc
init|=
name|JAXBContext
operator|.
name|newInstance
argument_list|(
name|context
argument_list|,
name|cl
argument_list|)
decl_stmt|;
name|Unmarshaller
name|unmarshaller
init|=
name|jc
operator|.
name|createUnmarshaller
argument_list|()
decl_stmt|;
name|SchemaFactory
name|schemaFactory
init|=
name|SchemaFactory
operator|.
name|newInstance
argument_list|(
name|XMLConstants
operator|.
name|W3C_XML_SCHEMA_NS_URI
argument_list|,
literal|"com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory"
argument_list|,
name|XMLUtil
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|StreamSource
argument_list|>
name|streamSourceList
init|=
operator|new
name|Vector
argument_list|<
name|StreamSource
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
name|schemaLocations
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|InputStream
name|is
init|=
name|cl
operator|.
name|getResourceAsStream
argument_list|(
name|schemaLocations
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|StreamSource
name|streamSource
init|=
operator|new
name|StreamSource
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|streamSourceList
operator|.
name|add
argument_list|(
name|streamSource
argument_list|)
expr_stmt|;
block|}
name|StreamSource
name|sources
index|[]
init|=
operator|new
name|StreamSource
index|[
name|streamSourceList
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|Schema
name|schema
init|=
name|schemaFactory
operator|.
name|newSchema
argument_list|(
name|streamSourceList
operator|.
name|toArray
argument_list|(
name|sources
argument_list|)
argument_list|)
decl_stmt|;
name|unmarshaller
operator|.
name|setSchema
argument_list|(
name|schema
argument_list|)
expr_stmt|;
name|Object
name|obj
init|=
name|unmarshaller
operator|.
name|unmarshal
argument_list|(
operator|new
name|StringReader
argument_list|(
name|content
argument_list|)
argument_list|)
decl_stmt|;
name|Locale
operator|.
name|setDefault
argument_list|(
name|oldLocale
argument_list|)
expr_stmt|;
return|return
name|obj
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error "
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
name|Locale
operator|.
name|setDefault
argument_list|(
name|oldLocale
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|String
name|stringToHTMLString
parameter_list|(
name|String
name|string
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
name|string
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
comment|// true if last char was blank
name|boolean
name|lastWasBlankChar
init|=
literal|false
decl_stmt|;
name|int
name|len
init|=
name|string
operator|.
name|length
argument_list|()
decl_stmt|;
name|char
name|c
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
name|len
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|=
name|string
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|==
literal|' '
condition|)
block|{
comment|// blank gets extra work,
comment|// this solves the problem you get if you replace all
comment|// blanks with&nbsp;, if you do that you loss
comment|// word breaking
if|if
condition|(
name|lastWasBlankChar
condition|)
block|{
name|lastWasBlankChar
operator|=
literal|false
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"&nbsp;"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|lastWasBlankChar
operator|=
literal|true
expr_stmt|;
name|sb
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
name|lastWasBlankChar
operator|=
literal|false
expr_stmt|;
comment|//
comment|// HTML Special Chars
if|if
condition|(
name|c
operator|==
literal|'"'
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"&quot;"
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|c
operator|==
literal|'&'
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"&amp;"
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|c
operator|==
literal|'<'
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"&lt;"
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|c
operator|==
literal|'>'
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"&gt;"
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|c
operator|==
literal|'\n'
condition|)
comment|// Handle Newline
name|sb
operator|.
name|append
argument_list|(
literal|"&lt;br/&gt;"
argument_list|)
expr_stmt|;
else|else
block|{
name|int
name|ci
init|=
literal|0xffff
operator|&
name|c
decl_stmt|;
if|if
condition|(
name|ci
operator|<
literal|160
condition|)
comment|// nothing special only 7 Bit
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
else|else
block|{
comment|// Not 7 Bit use the unicode system
name|sb
operator|.
name|append
argument_list|(
literal|"&#"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
operator|new
name|Integer
argument_list|(
name|ci
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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

