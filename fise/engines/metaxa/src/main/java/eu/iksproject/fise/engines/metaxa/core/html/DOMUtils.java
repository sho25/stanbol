begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|metaxa
operator|.
name|core
operator|.
name|html
package|;
end_package

begin_comment
comment|/*  * Copyright 2002 Sun Microsystems, Inc. All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are met: -  * Redistributions of source code must retain the above copyright notice, this  * list of conditions and the following disclaimer. - Redistribution in binary  * form must reproduce the above copyright notice, this list of conditions and  * the following disclaimer in the documentation and/or other materials provided  * with the distribution.  *   * Neither the name of Sun Microsystems, Inc. or the names of contributors may  * be used to endorse or promote products derived from this software without  * specific prior written permission.  *   * This software is provided "AS IS," without a warranty of any kind. ALL  * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY  * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR  * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE  * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING  * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS  * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,  * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF  * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY  * OF SUCH DAMAGES.  *   * You acknowledge that Software is not designed, licensed or intended for use  * in the design, construction, operation or maintenance of any nuclear  * facility.  */
end_comment

begin_comment
comment|// TODO: this license doesn't look open source to me
end_comment

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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
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
name|UnsupportedEncodingException
import|;
end_import

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
name|Result
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
name|Source
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
name|TransformerConfigurationException
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
name|NamedNodeMap
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
name|w3c
operator|.
name|dom
operator|.
name|NodeList
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
name|SAXParseException
import|;
end_import

begin_comment
comment|/**  *<code>DOMUtils</code> provides convenience methods for working with DOM  * documents.  *   * @author Walter Kasper, DFKI  * @author Joerg Steffen, DFKI  * @version $Id$  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|DOMUtils
block|{
comment|/**      * This creates a new instance of<code>DOMUtils</code>. Not to be used.      */
specifier|private
name|DOMUtils
parameter_list|()
block|{      }
comment|/**      * This prints the specified node and all of its children to System.out.      *       * @param node      *            a DOM<code>Node</code>      */
specifier|public
specifier|static
name|void
name|printDOM
parameter_list|(
name|Node
name|node
parameter_list|)
block|{
name|int
name|type
init|=
name|node
operator|.
name|getNodeType
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
comment|// print the document element
case|case
name|Node
operator|.
name|DOCUMENT_NODE
case|:
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"<?xml version=\"1.0\" ?>"
argument_list|)
expr_stmt|;
name|printDOM
argument_list|(
operator|(
operator|(
name|Document
operator|)
name|node
operator|)
operator|.
name|getDocumentElement
argument_list|()
argument_list|)
expr_stmt|;
break|break;
comment|// print element with attributes
case|case
name|Node
operator|.
name|ELEMENT_NODE
case|:
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"<"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
name|node
operator|.
name|getNodeName
argument_list|()
argument_list|)
expr_stmt|;
name|NamedNodeMap
name|attrs
init|=
name|node
operator|.
name|getAttributes
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
name|attrs
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Node
name|attr
init|=
name|attrs
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|" "
operator|+
name|attr
operator|.
name|getNodeName
argument_list|()
operator|.
name|trim
argument_list|()
operator|+
literal|"=\""
operator|+
name|attr
operator|.
name|getNodeValue
argument_list|()
operator|.
name|trim
argument_list|()
operator|+
literal|"\""
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|">"
argument_list|)
expr_stmt|;
name|NodeList
name|children
init|=
name|node
operator|.
name|getChildNodes
argument_list|()
decl_stmt|;
if|if
condition|(
name|children
operator|!=
literal|null
condition|)
block|{
name|int
name|len
init|=
name|children
operator|.
name|getLength
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
name|len
condition|;
name|i
operator|++
control|)
block|{
name|printDOM
argument_list|(
name|children
operator|.
name|item
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
break|break;
comment|// handle entity reference nodes
case|case
name|Node
operator|.
name|ENTITY_REFERENCE_NODE
case|:
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"&"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
name|node
operator|.
name|getNodeName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
break|break;
comment|// print cdata sections
case|case
name|Node
operator|.
name|CDATA_SECTION_NODE
case|:
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"<![CDATA["
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
name|node
operator|.
name|getNodeValue
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"]]>"
argument_list|)
expr_stmt|;
break|break;
comment|// print text
case|case
name|Node
operator|.
name|TEXT_NODE
case|:
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
name|node
operator|.
name|getNodeValue
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
break|break;
comment|// print processing instruction
case|case
name|Node
operator|.
name|PROCESSING_INSTRUCTION_NODE
case|:
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"<?"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
name|node
operator|.
name|getNodeName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|data
init|=
name|node
operator|.
name|getNodeValue
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
name|data
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"?>"
argument_list|)
expr_stmt|;
break|break;
default|default:
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"unknown type "
operator|+
name|type
argument_list|)
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|type
operator|==
name|Node
operator|.
name|ELEMENT_NODE
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"</"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
name|node
operator|.
name|getNodeName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|'>'
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * This prints the given DOM document to System.out with indentation and      * iso-8859 encoding.      *       * @param doc      *            a DOM<code>Document</code>      */
specifier|public
specifier|static
name|void
name|printXML
parameter_list|(
name|Document
name|doc
parameter_list|)
block|{
try|try
block|{
comment|// prepare the DOM document for writing
name|Source
name|source
init|=
operator|new
name|DOMSource
argument_list|(
name|doc
argument_list|)
decl_stmt|;
comment|// prepare the output
name|Result
name|result
init|=
operator|new
name|StreamResult
argument_list|(
name|System
operator|.
name|out
argument_list|)
decl_stmt|;
comment|// write the DOM document to the file
comment|// get Transformer
name|Transformer
name|xformer
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|xformer
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
name|xformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|ENCODING
argument_list|,
literal|"iso-8859-1"
argument_list|)
expr_stmt|;
name|xformer
operator|.
name|setOutputProperty
argument_list|(
literal|"{http://xml.apache.org/xslt}indent-amount"
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|xformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|METHOD
argument_list|,
literal|"xml"
argument_list|)
expr_stmt|;
comment|// write to System.out
name|xformer
operator|.
name|transform
argument_list|(
name|source
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerConfigurationException
name|tce
parameter_list|)
block|{
comment|// error generated during transformer configuration
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|tce
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
comment|// use the contained exception, if any
name|Throwable
name|x
init|=
name|tce
decl_stmt|;
if|if
condition|(
name|tce
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|x
operator|=
name|tce
operator|.
name|getException
argument_list|()
expr_stmt|;
block|}
name|x
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerException
name|te
parameter_list|)
block|{
comment|// error generated by the transformer
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|te
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
comment|// use the contained exception, if any
name|Throwable
name|x
init|=
name|te
decl_stmt|;
if|if
condition|(
name|te
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|x
operator|=
name|te
operator|.
name|getException
argument_list|()
expr_stmt|;
block|}
name|x
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * This returns a string representation of the given document.      *       * @param doc      *            an XML<code>Document</code>      * @param encoding      *            a<code>String</code> with the encoding to use      * @param docTypeDef      *            a<code>String</code> with the DTD name; use<code>null</code>      *            for no DTD      * @return a<code>String</code> with the XML string      */
specifier|public
specifier|static
name|String
name|getStringFromDoc
parameter_list|(
name|Document
name|doc
parameter_list|,
name|String
name|encoding
parameter_list|,
name|String
name|docTypeDef
parameter_list|)
block|{
try|try
block|{
comment|// use a Transformer for output
name|TransformerFactory
name|tFactory
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|Transformer
name|xformer
init|=
name|tFactory
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|xformer
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
name|xformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|ENCODING
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
name|xformer
operator|.
name|setOutputProperty
argument_list|(
literal|"{http://xml.apache.org/xslt}indent-amount"
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|xformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|METHOD
argument_list|,
literal|"xml"
argument_list|)
expr_stmt|;
if|if
condition|(
literal|null
operator|!=
name|docTypeDef
condition|)
block|{
name|xformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|DOCTYPE_SYSTEM
argument_list|,
name|docTypeDef
argument_list|)
expr_stmt|;
block|}
name|DOMSource
name|source
init|=
operator|new
name|DOMSource
argument_list|(
name|doc
argument_list|)
decl_stmt|;
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|StreamResult
name|result
init|=
operator|new
name|StreamResult
argument_list|(
name|sw
argument_list|)
decl_stmt|;
name|xformer
operator|.
name|transform
argument_list|(
name|source
argument_list|,
name|result
argument_list|)
expr_stmt|;
return|return
name|sw
operator|.
name|toString
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|TransformerConfigurationException
name|tce
parameter_list|)
block|{
comment|// error generated by the parser
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"** Transformer Factory error"
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"   "
operator|+
name|tce
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
comment|// use the contained exception, if any
name|Throwable
name|x
init|=
name|tce
decl_stmt|;
if|if
condition|(
name|tce
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|x
operator|=
name|tce
operator|.
name|getException
argument_list|()
expr_stmt|;
block|}
name|x
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerException
name|te
parameter_list|)
block|{
comment|// error generated by the parser
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"** Transformation error"
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"   "
operator|+
name|te
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
comment|// use the contained exception, if any
name|Throwable
name|x
init|=
name|te
decl_stmt|;
if|if
condition|(
name|te
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|x
operator|=
name|te
operator|.
name|getException
argument_list|()
expr_stmt|;
block|}
name|x
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * This method writes a DOM document to the given output stream.      *       * @param doc      *            a DOM<code>Document</code>      * @param encoding      *            a<code>String</code> with the encoding to use      * @param docTypeDef      *            a<code>String</code> with the DTD name; use<code>null</code>      *            for no DTD      * @param out      *            an<code>OutputStream</code> where to write the DOM document      */
specifier|public
specifier|static
name|void
name|writeXml
parameter_list|(
name|Document
name|doc
parameter_list|,
name|String
name|encoding
parameter_list|,
name|String
name|docTypeDef
parameter_list|,
name|OutputStream
name|out
parameter_list|)
block|{
try|try
block|{
comment|// prepare the DOM document
name|Source
name|source
init|=
operator|new
name|DOMSource
argument_list|(
name|doc
argument_list|)
decl_stmt|;
comment|// prepare the output
name|Result
name|result
init|=
operator|new
name|StreamResult
argument_list|(
name|out
argument_list|)
decl_stmt|;
comment|// write the DOM document to the file
comment|// get Transformer
name|Transformer
name|xformer
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|xformer
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
name|xformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|ENCODING
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
name|xformer
operator|.
name|setOutputProperty
argument_list|(
literal|"{http://xml.apache.org/xslt}indent-amount"
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|xformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|METHOD
argument_list|,
literal|"xml"
argument_list|)
expr_stmt|;
if|if
condition|(
literal|null
operator|!=
name|docTypeDef
condition|)
block|{
name|xformer
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|DOCTYPE_SYSTEM
argument_list|,
name|docTypeDef
argument_list|)
expr_stmt|;
block|}
comment|// write to a file
name|xformer
operator|.
name|transform
argument_list|(
name|source
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerConfigurationException
name|tce
parameter_list|)
block|{
comment|// error generated during transformer configuration
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|tce
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
comment|// use the contained exception, if any
name|Throwable
name|x
init|=
name|tce
decl_stmt|;
if|if
condition|(
name|tce
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|x
operator|=
name|tce
operator|.
name|getException
argument_list|()
expr_stmt|;
block|}
name|x
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerException
name|te
parameter_list|)
block|{
comment|// error generated by the transformer
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|te
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
comment|// use the contained exception, if any
name|Throwable
name|x
init|=
name|te
decl_stmt|;
if|if
condition|(
name|te
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|x
operator|=
name|te
operator|.
name|getException
argument_list|()
expr_stmt|;
block|}
name|x
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * This parses the given XML string and creates a DOM Document.      *       * @param fileName      *            a<code>String</code> with the source file name      * @param encoding      *            a<code>String</code> denoting the encoding of the XML string      * @return Document a DOM<code>Document</code>,<code>null</code> if      *         parsing fails      */
specifier|public
specifier|static
name|Document
name|parse
parameter_list|(
name|String
name|xml
parameter_list|,
name|String
name|encoding
parameter_list|)
block|{
if|if
condition|(
name|encoding
operator|==
literal|null
condition|)
name|encoding
operator|=
literal|"UTF-8"
expr_stmt|;
name|Document
name|document
init|=
literal|null
decl_stmt|;
comment|// initiate DocumentBuilderFactory
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// to get a validating parser
name|factory
operator|.
name|setValidating
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// to get one that understands namespaces
name|factory
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|factory
operator|.
name|setIgnoringElementContentWhitespace
argument_list|(
literal|true
argument_list|)
expr_stmt|;
try|try
block|{
comment|// get DocumentBuilder
name|DocumentBuilder
name|builder
init|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
comment|// parse and load into memory the Document
name|document
operator|=
name|builder
operator|.
name|parse
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|xml
operator|.
name|getBytes
argument_list|(
name|encoding
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|document
return|;
block|}
comment|/**      * This parses the given XML file and creates a DOM Document.      *       * @param fileName      *            a<code>String</code> with the source file name      * @param validation      *            a<code>boolean</code> indicatiing if the parsing uses DTD      *            valudation      * @return Document a DOM<code>Document</code>,<code>null</code> if      *         parsing fails      */
specifier|public
specifier|static
name|Document
name|parse
parameter_list|(
name|String
name|fileName
parameter_list|,
name|boolean
name|validation
parameter_list|)
block|{
name|Document
name|document
init|=
literal|null
decl_stmt|;
comment|// initiate DocumentBuilderFactory
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// to get a validating parser
name|factory
operator|.
name|setValidating
argument_list|(
name|validation
argument_list|)
expr_stmt|;
comment|// to get one that understands namespaces
name|factory
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|factory
operator|.
name|setIgnoringElementContentWhitespace
argument_list|(
literal|true
argument_list|)
expr_stmt|;
try|try
block|{
comment|// get DocumentBuilder
name|DocumentBuilder
name|builder
init|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
comment|// parse and load into memory the Document
name|document
operator|=
name|builder
operator|.
name|parse
argument_list|(
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|document
return|;
block|}
catch|catch
parameter_list|(
name|SAXParseException
name|spe
parameter_list|)
block|{
comment|// error generated by the parser
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Parsing error, line "
operator|+
name|spe
operator|.
name|getLineNumber
argument_list|()
operator|+
literal|", uri "
operator|+
name|spe
operator|.
name|getSystemId
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|" "
operator|+
name|spe
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
comment|// use the contained exception, if any
name|Exception
name|x
init|=
name|spe
decl_stmt|;
if|if
condition|(
name|spe
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|x
operator|=
name|spe
operator|.
name|getException
argument_list|()
expr_stmt|;
block|}
name|x
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|sxe
parameter_list|)
block|{
comment|// error generated during parsing
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|sxe
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
comment|// use the contained exception, if any
name|Exception
name|x
init|=
name|sxe
decl_stmt|;
if|if
condition|(
name|sxe
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|x
operator|=
name|sxe
operator|.
name|getException
argument_list|()
expr_stmt|;
block|}
name|x
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
name|pce
parameter_list|)
block|{
comment|// parser with specified options can't be built
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|pce
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|pce
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ioe
parameter_list|)
block|{
comment|// i/o error
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|ioe
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|ioe
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * This counts the elements in the given document by tag name.      *       * @param tag      *            a<code>String</code> with a tag name      * @param doc      *            a DOM<code>Document</code>      * @return number an<code>int</code> with the number of elements by tag      *         name      */
specifier|public
specifier|static
name|int
name|countByTagName
parameter_list|(
name|String
name|tag
parameter_list|,
name|Document
name|doc
parameter_list|)
block|{
name|NodeList
name|list
init|=
name|doc
operator|.
name|getElementsByTagName
argument_list|(
name|tag
argument_list|)
decl_stmt|;
return|return
name|list
operator|.
name|getLength
argument_list|()
return|;
block|}
comment|/**      * This realizes the<code>indexOf</code> method of the      *<code>java.util.List</code> interface for<code>NodeList</code>.      *       * @param list      *            a<code>NodeList</code> value      * @param node      *            a<code>Node</code> value      * @return an<code>int</code> value, giving the position of      *<code>node</code> in<code>list</code> or -1, if node is not      *         contained in the list      */
specifier|public
specifier|static
name|int
name|indexOf
parameter_list|(
name|NodeList
name|list
parameter_list|,
name|Node
name|node
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|j
init|=
name|list
operator|.
name|getLength
argument_list|()
init|;
name|i
operator|<
name|j
condition|;
operator|++
name|i
control|)
block|{
if|if
condition|(
name|list
operator|.
name|item
argument_list|(
name|i
argument_list|)
operator|==
name|node
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
comment|/**      * This concatenates the string values of all text nodes which are direct      * children of the given node. If<code>node</code> is a text or attribute      * node, its value is returned. Otherwise<code>null</code> is returned      * (improvement potential!).      *       * @param node      *            a<code>Node</code> value      * @return a<code>String</code> with the concatenated text      */
specifier|public
specifier|static
name|String
name|getText
parameter_list|(
name|Node
name|node
parameter_list|)
block|{
name|short
name|nodeType
init|=
name|node
operator|.
name|getNodeType
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|nodeType
operator|==
name|Node
operator|.
name|TEXT_NODE
operator|)
operator|||
operator|(
name|nodeType
operator|==
name|Node
operator|.
name|ATTRIBUTE_NODE
operator|)
operator|||
operator|(
name|nodeType
operator|==
name|Node
operator|.
name|CDATA_SECTION_NODE
operator|)
condition|)
block|{
return|return
name|node
operator|.
name|getNodeValue
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|nodeType
operator|==
name|Node
operator|.
name|ELEMENT_NODE
condition|)
block|{
name|NodeList
name|dtrs
init|=
name|node
operator|.
name|getChildNodes
argument_list|()
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|j
init|=
name|dtrs
operator|.
name|getLength
argument_list|()
init|;
name|i
operator|<
name|j
condition|;
operator|++
name|i
control|)
block|{
name|Node
name|item
init|=
name|dtrs
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|.
name|getNodeType
argument_list|()
operator|==
name|Node
operator|.
name|TEXT_NODE
operator|||
name|item
operator|.
name|getNodeType
argument_list|()
operator|==
name|Node
operator|.
name|CDATA_SECTION_NODE
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|item
operator|.
name|getNodeValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * This selects all direct children of the given element with the given      * name. If the name is<code>null</code>, all children are returned.      *       * @param ele      *            an<code>Element</code> value      * @param name      *            a<code>String</code> with the children's name      * @return a<code>List</code> of<code>Node</code>s with the children      */
specifier|public
specifier|static
name|List
argument_list|<
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
argument_list|>
name|getChildren
parameter_list|(
name|Element
name|ele
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|NodeList
name|dtrs
init|=
name|ele
operator|.
name|getChildNodes
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
argument_list|>
name|eles
init|=
operator|new
name|ArrayList
argument_list|<
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|j
init|=
name|dtrs
operator|.
name|getLength
argument_list|()
init|;
name|i
operator|<
name|j
condition|;
operator|++
name|i
control|)
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
name|item
init|=
name|dtrs
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|item
operator|.
name|getNodeName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|eles
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|eles
return|;
block|}
comment|/**      * This selects all direct children of type 'Element' of the given element.      *       * @param ele      *            an<code>Element</code> value      * @return a<code>List</code> of<code>Elmenet</code>s with the element      *         children      */
specifier|public
specifier|static
name|List
argument_list|<
name|Element
argument_list|>
name|getChildrenElements
parameter_list|(
name|Element
name|ele
parameter_list|)
block|{
name|NodeList
name|dtrs
init|=
name|ele
operator|.
name|getChildNodes
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Element
argument_list|>
name|eles
init|=
operator|new
name|ArrayList
argument_list|<
name|Element
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|j
init|=
name|dtrs
operator|.
name|getLength
argument_list|()
init|;
name|i
operator|<
name|j
condition|;
operator|++
name|i
control|)
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
name|item
init|=
name|dtrs
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|.
name|getNodeType
argument_list|()
operator|==
name|Node
operator|.
name|ELEMENT_NODE
condition|)
block|{
name|eles
operator|.
name|add
argument_list|(
operator|(
name|Element
operator|)
name|item
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|eles
return|;
block|}
comment|/**      * This returns the first child element with the given name found at the      * given element.      *       * @param ele      *            an<code>Element</code> value      * @param name      *            a<code>String</code> with the name of the child element      * @return a<code>Element</code> with the child or<code>null</code> if no      *         such child was found      */
specifier|public
specifier|static
name|Element
name|getFirstChild
parameter_list|(
name|Element
name|ele
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|NodeList
name|dtrs
init|=
name|ele
operator|.
name|getChildNodes
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|iMax
init|=
name|dtrs
operator|.
name|getLength
argument_list|()
init|;
name|i
operator|<
name|iMax
condition|;
operator|++
name|i
control|)
block|{
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
name|item
init|=
name|dtrs
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|.
name|getNodeName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
return|return
operator|(
name|Element
operator|)
name|item
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * This adds a new child with the given name to the given element.      *       * @param ele      *            an<code>Element</code>      * @param name      *            a<code>String</code> with the name of the child      * @return a<code>Element</code> with the newly created child      */
specifier|public
specifier|static
name|Element
name|addChild
parameter_list|(
name|Element
name|ele
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|Element
name|child
init|=
name|ele
operator|.
name|getOwnerDocument
argument_list|()
operator|.
name|createElement
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|ele
operator|.
name|appendChild
argument_list|(
name|child
argument_list|)
expr_stmt|;
return|return
name|child
return|;
block|}
block|}
end_class

end_unit

