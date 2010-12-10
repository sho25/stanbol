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
name|BufferedInputStream
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
name|ArrayList
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

begin_comment
comment|/**  * IksHtmlExtractor.java  *   * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  *   */
end_comment

begin_class
specifier|public
class|class
name|IksHtmlExtractor
implements|implements
name|Extractor
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
name|IksHtmlExtractor
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|String
name|DEFAULT_CONFIGURATION
init|=
literal|"htmlextractors.xml"
decl_stmt|;
specifier|private
specifier|static
name|HtmlParser
name|htmlParser
init|=
operator|new
name|HtmlParser
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|HtmlExtractionRegistry
name|registry
init|=
operator|new
name|HtmlExtractionRegistry
argument_list|()
decl_stmt|;
static|static
block|{
try|try
block|{
name|registry
operator|.
name|initialize
argument_list|(
name|DEFAULT_CONFIGURATION
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InitializationException
name|e
parameter_list|)
block|{
name|LOG
operator|.
name|error
argument_list|(
literal|"Registration Initalization Error: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|IksHtmlExtractor
parameter_list|()
block|{     }
specifier|public
name|IksHtmlExtractor
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
name|this
operator|.
name|registry
operator|=
operator|new
name|HtmlExtractionRegistry
argument_list|(
name|configFileName
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|extract
parameter_list|(
name|URI
name|id
parameter_list|,
name|InputStream
name|input
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
name|String
name|encoding
decl_stmt|;
if|if
condition|(
name|charset
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|input
operator|.
name|markSupported
argument_list|()
condition|)
block|{
name|input
operator|=
operator|new
name|BufferedInputStream
argument_list|(
name|input
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|encoding
operator|=
name|CharsetRecognizer
operator|.
name|detect
argument_list|(
name|input
argument_list|,
literal|"html"
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOG
operator|.
name|error
argument_list|(
literal|"Charset detection problem: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|ExtractorException
argument_list|(
literal|"Charset detection problem: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|encoding
operator|=
name|charset
operator|.
name|name
argument_list|()
expr_stmt|;
block|}
name|Document
name|doc
init|=
name|htmlParser
operator|.
name|getDOM
argument_list|(
name|input
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
comment|/*          * This solves namespace problem but makes it difficult to handle normal          * HTML and namespaced XHTML documents on a par. Rather avoid namespaces          * in transformers for HTML elements! Problem remains that scripts then          * cannot be tested offline Way out might be to use disjunctions in          * scripts or ignore namespace by checking local-name() only          * (match=*[local-name() = 'xxx']) Are Microformats, RDFa, ... only used          * in XHTML? That would make the decision easier! Also have to solve the          * problem how to connect/map SemanticDesktop ontologies with those from          * the extractors String docText = DOMUtils.getStringFromDoc(doc,          * "UTF-8", null); logger.info(docText); doc = DOMUtils.parse(docText,          * "UTF-8");          */
name|HashMap
argument_list|<
name|String
argument_list|,
name|HtmlExtractionComponent
argument_list|>
name|extractors
init|=
name|registry
operator|.
name|getRegistry
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formats
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|long
name|modelSize
init|=
name|result
operator|.
name|getModel
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|registry
operator|.
name|getActiveExtractors
argument_list|()
control|)
block|{
name|LOG
operator|.
name|info
argument_list|(
literal|"Extractor: "
operator|+
name|s
argument_list|)
expr_stmt|;
name|HtmlExtractionComponent
name|extractor
init|=
name|extractors
operator|.
name|get
argument_list|(
name|s
argument_list|)
decl_stmt|;
comment|// TODO: Handle dependencies between Microformat extractors, e.g.
comment|// formats used also in other formats
if|if
condition|(
name|extractor
operator|!=
literal|null
condition|)
block|{
name|extractor
operator|.
name|extract
argument_list|(
name|id
operator|.
name|toString
argument_list|()
argument_list|,
name|doc
argument_list|,
literal|null
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|long
name|tmpSize
init|=
name|result
operator|.
name|getModel
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
if|if
condition|(
name|modelSize
operator|<
name|tmpSize
condition|)
block|{
name|LOG
operator|.
name|info
argument_list|(
operator|(
name|tmpSize
operator|-
name|modelSize
operator|)
operator|+
literal|" Statements added: "
operator|+
name|s
argument_list|)
expr_stmt|;
name|modelSize
operator|=
name|tmpSize
expr_stmt|;
block|}
block|}
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
name|IksHtmlExtractor
name|inst
init|=
operator|new
name|IksHtmlExtractor
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
name|input
init|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|Charset
name|charset
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|String
name|mimeType
init|=
literal|"text/html"
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
name|container
init|=
name|rdfFactory
operator|.
name|getRDFContainer
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|inst
operator|.
name|extract
argument_list|(
name|uri
argument_list|,
name|input
argument_list|,
name|charset
argument_list|,
name|mimeType
argument_list|,
name|container
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Model for "
operator|+
name|args
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|container
operator|.
name|getModel
argument_list|()
operator|.
name|writeTo
argument_list|(
name|System
operator|.
name|out
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|container
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

