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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
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
name|Set
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
name|TransformerFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|xpath
operator|.
name|XPath
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|xpath
operator|.
name|XPathConstants
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|xpath
operator|.
name|XPathExpressionException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|xpath
operator|.
name|XPathFactory
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
name|DOMException
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
comment|/**  * HtmlExtractionRegistry.java  *   * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  */
end_comment

begin_class
specifier|public
class|class
name|HtmlExtractionRegistry
block|{
comment|/**      * This contains the logger.      */
specifier|private
specifier|static
specifier|final
name|Logger
name|LOG
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|HtmlExtractionRegistry
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|HtmlExtractionComponent
argument_list|>
name|registry
decl_stmt|;
specifier|private
name|HashSet
argument_list|<
name|String
argument_list|>
name|activeExtractors
decl_stmt|;
specifier|public
name|HtmlExtractionRegistry
parameter_list|()
block|{
name|registry
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|HtmlExtractionComponent
argument_list|>
argument_list|()
expr_stmt|;
name|activeExtractors
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
specifier|public
name|HtmlExtractionRegistry
parameter_list|(
name|String
name|configFileName
parameter_list|)
throws|throws
name|InitializationException
block|{
name|this
argument_list|()
expr_stmt|;
name|initialize
argument_list|(
name|configFileName
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|initialize
parameter_list|(
name|String
name|configFileName
parameter_list|)
throws|throws
name|InitializationException
block|{
try|try
block|{
name|XPathFactory
name|factory
init|=
name|XPathFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|XPath
name|xPath
init|=
name|factory
operator|.
name|newXPath
argument_list|()
decl_stmt|;
name|DocumentBuilder
name|parser
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|Document
name|document
init|=
name|parser
operator|.
name|parse
argument_list|(
operator|new
name|InputSource
argument_list|(
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|configFileName
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|Node
name|node
decl_stmt|;
name|NodeList
name|nodes
init|=
operator|(
name|NodeList
operator|)
name|xPath
operator|.
name|evaluate
argument_list|(
literal|"/htmlextractors/extractor"
argument_list|,
name|document
argument_list|,
name|XPathConstants
operator|.
name|NODESET
argument_list|)
decl_stmt|;
if|if
condition|(
name|nodes
operator|!=
literal|null
condition|)
block|{
name|TransformerFactory
name|transFac
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|transFac
operator|.
name|setURIResolver
argument_list|(
operator|new
name|BundleURIResolver
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|,
name|iCnt
init|=
name|nodes
operator|.
name|getLength
argument_list|()
init|;
name|j
operator|<
name|iCnt
condition|;
name|j
operator|++
control|)
block|{
name|Node
name|nd
init|=
name|nodes
operator|.
name|item
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|node
operator|=
operator|(
name|Node
operator|)
name|xPath
operator|.
name|evaluate
argument_list|(
literal|"@id"
argument_list|,
name|nd
argument_list|,
name|XPathConstants
operator|.
name|NODE
argument_list|)
expr_stmt|;
name|String
name|id
init|=
name|node
operator|.
name|getNodeValue
argument_list|()
decl_stmt|;
name|Node
name|srcNode
init|=
operator|(
name|Node
operator|)
name|xPath
operator|.
name|evaluate
argument_list|(
literal|"source"
argument_list|,
name|nd
argument_list|,
name|XPathConstants
operator|.
name|NODE
argument_list|)
decl_stmt|;
if|if
condition|(
name|srcNode
operator|!=
literal|null
condition|)
block|{
name|node
operator|=
operator|(
name|Node
operator|)
name|xPath
operator|.
name|evaluate
argument_list|(
literal|"@type"
argument_list|,
name|srcNode
argument_list|,
name|XPathConstants
operator|.
name|NODE
argument_list|)
expr_stmt|;
name|String
name|srcType
init|=
name|node
operator|.
name|getNodeValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|srcType
operator|.
name|equals
argument_list|(
literal|"xslt"
argument_list|)
condition|)
block|{
name|String
name|rdfFormat
init|=
literal|"rdfxml"
decl_stmt|;
name|Syntax
name|rdfSyntax
init|=
name|Syntax
operator|.
name|RdfXml
decl_stmt|;
name|node
operator|=
operator|(
name|Node
operator|)
name|xPath
operator|.
name|evaluate
argument_list|(
literal|"@syntax"
argument_list|,
name|srcNode
argument_list|,
name|XPathConstants
operator|.
name|NODE
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|!=
literal|null
condition|)
block|{
name|rdfFormat
operator|=
name|node
operator|.
name|getNodeValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|rdfFormat
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"turtle"
argument_list|)
condition|)
block|{
name|rdfSyntax
operator|=
name|Syntax
operator|.
name|Turtle
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|rdfFormat
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"ntriple"
argument_list|)
condition|)
block|{
name|rdfSyntax
operator|=
name|Syntax
operator|.
name|Ntriples
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|rdfFormat
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"n3"
argument_list|)
condition|)
block|{
name|rdfSyntax
operator|=
name|XsltExtractor
operator|.
name|N3
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|rdfFormat
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"rdfxml"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
literal|"Unknown RDF Syntax: "
operator|+
name|rdfFormat
operator|+
literal|" for "
operator|+
name|id
operator|+
literal|" extractor"
argument_list|)
throw|;
block|}
block|}
comment|// TODO: do something about disjunctions of
comment|// Extractors? Assume, only RDFa or Microformats are
comment|// used?
name|String
name|fileName
init|=
name|DOMUtils
operator|.
name|getText
argument_list|(
name|srcNode
argument_list|)
decl_stmt|;
name|XsltExtractor
name|xsltExtractor
init|=
operator|new
name|XsltExtractor
argument_list|(
name|id
argument_list|,
name|fileName
argument_list|,
name|transFac
argument_list|)
decl_stmt|;
name|xsltExtractor
operator|.
name|setSyntax
argument_list|(
name|rdfSyntax
argument_list|)
expr_stmt|;
comment|// name of URI/URL parameter of the script (default
comment|// "uri")
name|node
operator|=
operator|(
name|Node
operator|)
name|xPath
operator|.
name|evaluate
argument_list|(
literal|"@uri"
argument_list|,
name|srcNode
argument_list|,
name|XPathConstants
operator|.
name|NODE
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|!=
literal|null
condition|)
block|{
name|xsltExtractor
operator|.
name|setUriParameter
argument_list|(
name|node
operator|.
name|getNodeValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|registry
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|xsltExtractor
argument_list|)
expr_stmt|;
name|activeExtractors
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|srcType
operator|.
name|equals
argument_list|(
literal|"java"
argument_list|)
condition|)
block|{
name|String
name|clsName
init|=
name|srcNode
operator|.
name|getNodeValue
argument_list|()
decl_stmt|;
name|Object
name|extractor
init|=
name|Class
operator|.
name|forName
argument_list|(
name|clsName
argument_list|)
operator|.
name|newInstance
argument_list|()
decl_stmt|;
if|if
condition|(
name|extractor
operator|instanceof
name|HtmlExtractionComponent
condition|)
block|{
name|registry
operator|.
name|put
argument_list|(
name|id
argument_list|,
operator|(
name|HtmlExtractionComponent
operator|)
name|extractor
argument_list|)
expr_stmt|;
name|activeExtractors
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
literal|"clsName is not an HtmlExtractionComponent"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|LOG
operator|.
name|warn
argument_list|(
literal|"No valid type for extractor found: "
operator|+
name|id
argument_list|)
expr_stmt|;
block|}
name|LOG
operator|.
name|info
argument_list|(
literal|"Extractor for: "
operator|+
name|id
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|XPathExpressionException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|DOMException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
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
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InstantiationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InitializationException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|HtmlExtractionComponent
argument_list|>
name|getRegistry
parameter_list|()
block|{
return|return
name|registry
return|;
block|}
specifier|public
name|void
name|setRegistry
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|HtmlExtractionComponent
argument_list|>
name|registry
parameter_list|)
block|{
name|this
operator|.
name|registry
operator|=
name|registry
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getActiveExtractors
parameter_list|()
block|{
return|return
name|activeExtractors
return|;
block|}
specifier|public
name|void
name|setActiveExtractors
parameter_list|(
name|HashSet
argument_list|<
name|String
argument_list|>
name|activeExtractors
parameter_list|)
block|{
name|this
operator|.
name|activeExtractors
operator|=
name|activeExtractors
expr_stmt|;
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
name|HtmlExtractionRegistry
name|inst
init|=
operator|new
name|HtmlExtractionRegistry
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Active Components: "
operator|+
name|inst
operator|.
name|activeExtractors
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|s
range|:
name|inst
operator|.
name|activeExtractors
control|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|add
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|resourceName
parameter_list|,
name|String
name|type
parameter_list|)
throws|throws
name|InitializationException
block|{     }
specifier|public
name|void
name|remove
parameter_list|(
name|String
name|id
parameter_list|)
block|{     }
specifier|public
name|void
name|activate
parameter_list|(
name|String
name|id
parameter_list|)
block|{     }
specifier|public
name|void
name|deactivate
parameter_list|(
name|String
name|id
parameter_list|)
block|{     }
block|}
end_class

end_unit

