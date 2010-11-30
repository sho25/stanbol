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
package|;
end_package

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
name|Map
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
name|BNode
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
name|LiteralFactory
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
name|MGraph
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
name|NonLiteral
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
name|Resource
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
name|Triple
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
name|PlainLiteralImpl
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
name|TripleImpl
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
name|TypedLiteralImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|ontoware
operator|.
name|aifbcommons
operator|.
name|collection
operator|.
name|ClosableIterator
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
name|Statement
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
name|BlankNode
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
name|DatatypeLiteral
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
name|Node
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
name|PlainLiteral
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
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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
name|MetaxaCore
import|;
end_import

begin_import
import|import
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
name|RDF2GoUtils
import|;
end_import

begin_import
import|import
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
operator|.
name|BundleURIResolver
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|EngineException
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|ServiceProperties
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
import|;
end_import

begin_comment
comment|/**  * {@link MetaxaEngine}  *  * @author Joerg Steffen, DFKI  * @version $Id$  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|MetaxaEngine
implements|implements
name|EnhancementEngine
implements|,
name|ServiceProperties
block|{
comment|/**    * This contains the logger.    */
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|MetaxaEngine
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**    * The default value for the Execution of this Engine. Currently set to    * {@link ServiceProperties#ORDERING_PRE_PROCESSING}    */
specifier|public
specifier|static
specifier|final
name|Integer
name|defaultOrder
init|=
name|ServiceProperties
operator|.
name|ORDERING_PRE_PROCESSING
decl_stmt|;
comment|/**    * This contains the Aperture extractor.    */
specifier|private
name|MetaxaCore
name|extractor
decl_stmt|;
comment|/**    * The activate method.    *    * @param ce    *          the {@link ComponentContext}    * @throws IOException    *           if initializing fails    */
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
throws|throws
name|IOException
block|{
try|try
block|{
name|this
operator|.
name|extractor
operator|=
operator|new
name|MetaxaCore
argument_list|(
literal|"extractionregistry.xml"
argument_list|)
expr_stmt|;
name|BundleURIResolver
operator|.
name|BUNDLE
operator|=
name|ce
operator|.
name|getBundleContext
argument_list|()
operator|.
name|getBundle
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/**    * The deactivate method.    *    * @param ce    *          the {@link ComponentContext}    */
specifier|protected
name|void
name|deactivate
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|ComponentContext
name|ce
parameter_list|)
block|{
name|this
operator|.
name|extractor
operator|=
literal|null
expr_stmt|;
block|}
comment|/**    * {@inheritDoc}    */
specifier|public
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
name|String
name|mimeType
init|=
name|ci
operator|.
name|getMimeType
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|,
literal|2
argument_list|)
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|extractor
operator|.
name|isSupported
argument_list|(
name|mimeType
argument_list|)
condition|)
block|{
return|return
name|ENHANCE_SYNCHRONOUS
return|;
block|}
return|return
name|CANNOT_ENHANCE
return|;
block|}
comment|/**    * {@inheritDoc}    */
specifier|public
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
block|{
try|try
block|{
comment|// get the model where to add the statements
name|MGraph
name|g
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
comment|// create enhancement
name|UriRef
name|textEnhancement
init|=
name|EnhancementEngineHelper
operator|.
name|createTextEnhancement
argument_list|(
name|ci
argument_list|,
name|this
argument_list|)
decl_stmt|;
comment|// set confidence value to 1.0
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|g
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textEnhancement
argument_list|,
name|Properties
operator|.
name|FISE_CONFIDENCE
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
literal|1.0
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// get model from the extraction
name|Model
name|m
init|=
name|this
operator|.
name|extractor
operator|.
name|extract
argument_list|(
name|ci
operator|.
name|getStream
argument_list|()
argument_list|,
name|ci
operator|.
name|getId
argument_list|()
argument_list|,
name|ci
operator|.
name|getMimeType
argument_list|()
argument_list|)
decl_stmt|;
comment|// add the statements from this model to the Metadata model
if|if
condition|(
literal|null
operator|!=
name|m
condition|)
block|{
comment|/*             String text = MetaxaCore.getText(m);             log.info(text);              */
name|RDF2GoUtils
operator|.
name|urifyBlankNodes
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|HashMap
argument_list|<
name|BlankNode
argument_list|,
name|BNode
argument_list|>
name|blankNodeMap
init|=
operator|new
name|HashMap
argument_list|<
name|BlankNode
argument_list|,
name|BNode
argument_list|>
argument_list|()
decl_stmt|;
name|ClosableIterator
argument_list|<
name|Statement
argument_list|>
name|it
init|=
name|m
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Statement
name|oneStmt
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|NonLiteral
name|fiseSubject
init|=
literal|null
decl_stmt|;
name|UriRef
name|fisePredicate
init|=
literal|null
decl_stmt|;
name|Resource
name|fiseObject
init|=
literal|null
decl_stmt|;
name|fiseSubject
operator|=
operator|(
name|NonLiteral
operator|)
name|asFiseResource
argument_list|(
name|oneStmt
operator|.
name|getSubject
argument_list|()
argument_list|,
name|blankNodeMap
argument_list|)
expr_stmt|;
name|fisePredicate
operator|=
operator|(
name|UriRef
operator|)
name|asFiseResource
argument_list|(
name|oneStmt
operator|.
name|getPredicate
argument_list|()
argument_list|,
name|blankNodeMap
argument_list|)
expr_stmt|;
name|fiseObject
operator|=
name|asFiseResource
argument_list|(
name|oneStmt
operator|.
name|getObject
argument_list|()
argument_list|,
name|blankNodeMap
argument_list|)
expr_stmt|;
if|if
condition|(
literal|null
operator|!=
name|fiseSubject
operator|&&
literal|null
operator|!=
name|fisePredicate
operator|&&
literal|null
operator|!=
name|fiseObject
condition|)
block|{
name|Triple
name|t
init|=
operator|new
name|TripleImpl
argument_list|(
name|fiseSubject
argument_list|,
name|fisePredicate
argument_list|,
name|fiseObject
argument_list|)
decl_stmt|;
name|g
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"added "
operator|+
name|t
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|it
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|ExtractorException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|EngineException
argument_list|(
name|e
operator|.
name|getLocalizedMessage
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
name|EngineException
argument_list|(
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**    * This converts the given RDF2Go node into a corresponding FISE object.     *    * @param node a {@link Node}    * @return a {@link Resource}    */
specifier|public
specifier|static
name|Resource
name|asFiseResource
parameter_list|(
name|Node
name|node
parameter_list|,
name|HashMap
argument_list|<
name|BlankNode
argument_list|,
name|BNode
argument_list|>
name|blankNodeMap
parameter_list|)
block|{
if|if
condition|(
name|node
operator|instanceof
name|URI
condition|)
block|{
return|return
operator|new
name|UriRef
argument_list|(
name|node
operator|.
name|asURI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|node
operator|instanceof
name|BlankNode
condition|)
block|{
name|BNode
name|bNode
init|=
name|blankNodeMap
operator|.
name|get
argument_list|(
name|node
argument_list|)
decl_stmt|;
if|if
condition|(
name|bNode
operator|==
literal|null
condition|)
block|{
name|bNode
operator|=
operator|new
name|BNode
argument_list|()
expr_stmt|;
name|blankNodeMap
operator|.
name|put
argument_list|(
name|node
operator|.
name|asBlankNode
argument_list|()
argument_list|,
name|bNode
argument_list|)
expr_stmt|;
block|}
return|return
name|bNode
return|;
block|}
elseif|else
if|if
condition|(
name|node
operator|instanceof
name|DatatypeLiteral
condition|)
block|{
name|DatatypeLiteral
name|dtl
init|=
name|node
operator|.
name|asDatatypeLiteral
argument_list|()
decl_stmt|;
return|return
operator|new
name|TypedLiteralImpl
argument_list|(
name|dtl
operator|.
name|getValue
argument_list|()
argument_list|,
operator|new
name|UriRef
argument_list|(
name|dtl
operator|.
name|getDatatype
argument_list|()
operator|.
name|asURI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|node
operator|instanceof
name|PlainLiteral
condition|)
block|{
return|return
operator|new
name|PlainLiteralImpl
argument_list|(
name|node
operator|.
name|asLiteral
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getServiceProperties
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|Collections
operator|.
name|singletonMap
argument_list|(
name|ServiceProperties
operator|.
name|ENHANCEMENT_ENGINE_ORDERING
argument_list|,
operator|(
name|Object
operator|)
name|defaultOrder
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

