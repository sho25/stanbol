begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|clerezza
package|;
end_package

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
name|event
operator|.
name|GraphEvent
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
name|event
operator|.
name|GraphListener
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
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|vocabulary
operator|.
name|OWL
import|;
end_import

begin_class
specifier|public
class|class
name|SynchronizerGraphListener
implements|implements
name|GraphListener
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
name|SynchronizerGraphListener
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|ClerezzaStoreSynchronizer
name|synchronizer
decl_stmt|;
specifier|private
name|String
name|graphURI
decl_stmt|;
specifier|public
name|SynchronizerGraphListener
parameter_list|(
name|ClerezzaStoreSynchronizer
name|synchronizer
parameter_list|,
name|String
name|graphURI
parameter_list|)
block|{
name|this
operator|.
name|synchronizer
operator|=
name|synchronizer
expr_stmt|;
name|this
operator|.
name|graphURI
operator|=
name|graphURI
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|graphChanged
parameter_list|(
name|List
argument_list|<
name|GraphEvent
argument_list|>
name|events
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|resourceURIs
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|GraphEvent
name|event
range|:
name|events
control|)
block|{
name|Triple
name|triple
init|=
name|event
operator|.
name|getTriple
argument_list|()
decl_stmt|;
name|UriRef
name|predicate
init|=
name|triple
operator|.
name|getPredicate
argument_list|()
decl_stmt|;
name|NonLiteral
name|subject
init|=
name|triple
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|Resource
name|object
init|=
name|triple
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|predicate
operator|.
name|equals
argument_list|(
name|OWLVocabulary
operator|.
name|RDF_TYPE
argument_list|)
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Listened triple change: {}"
argument_list|,
name|triple
argument_list|)
expr_stmt|;
try|try
block|{
name|resourceURIs
operator|.
name|add
argument_list|(
operator|(
operator|(
name|UriRef
operator|)
name|subject
operator|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
comment|// Blank node, just skipping
name|logger
operator|.
name|info
argument_list|(
literal|"Subject "
operator|+
name|subject
operator|.
name|toString
argument_list|()
operator|+
literal|" is a blanknode"
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|predicate
operator|.
name|equals
argument_list|(
operator|new
name|UriRef
argument_list|(
name|OWL
operator|.
name|imports
operator|.
name|asResource
argument_list|()
operator|.
name|getURI
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Listened triple change: {}"
argument_list|,
name|triple
argument_list|)
expr_stmt|;
try|try
block|{
name|resourceURIs
operator|.
name|add
argument_list|(
operator|(
operator|(
name|UriRef
operator|)
name|object
operator|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Cannot resolve import: {}"
argument_list|,
name|triple
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|resourceURIs
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Listener: "
operator|+
name|this
operator|.
name|toString
argument_list|()
operator|+
literal|"URIs:"
operator|+
name|resourceURIs
argument_list|)
expr_stmt|;
name|synchronizer
operator|.
name|synchronizeResourceOnGraph
argument_list|(
name|graphURI
argument_list|,
name|resourceURIs
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

