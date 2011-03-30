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
name|concurrent
operator|.
name|locks
operator|.
name|Lock
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
name|access
operator|.
name|LockableMGraph
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
name|access
operator|.
name|NoSuchEntityException
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
name|access
operator|.
name|TcManager
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
name|FilterTriple
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
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|jena
operator|.
name|facade
operator|.
name|JenaGraph
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
name|Activate
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
name|Deactivate
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
name|Property
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
name|Reference
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
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|store
operator|.
name|api
operator|.
name|ResourceManager
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
name|api
operator|.
name|StoreSynchronizer
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
name|ontology
operator|.
name|DatatypeProperty
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
name|ontology
operator|.
name|Individual
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
name|ontology
operator|.
name|ObjectProperty
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
name|ontology
operator|.
name|OntClass
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
name|ontology
operator|.
name|OntModel
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
name|ontology
operator|.
name|OntModelSpec
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
name|rdf
operator|.
name|model
operator|.
name|Model
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
name|rdf
operator|.
name|model
operator|.
name|ModelFactory
import|;
end_import

begin_comment
comment|/**  * Synchronizer for {@link ResourceManager}. Uses a {@link TcManager} to access graphs stored by Clerezza.  *   * @author Cihan  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|factory
operator|=
literal|"org.apache.stanbol.ontologymanager.store.StoreSynchronizerFactory"
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|ClerezzaStoreSynchronizer
implements|implements
name|StoreSynchronizer
block|{
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ClerezzaStoreSynchronizer
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"org.apache.stanbol.ontologymanager.store.ResourceManager"
argument_list|)
specifier|private
name|ResourceManager
name|resourceManager
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|TcManager
name|tcManager
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|UriRef
argument_list|,
name|GraphListener
argument_list|>
name|listeningMGraph
init|=
operator|new
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|GraphListener
argument_list|>
argument_list|()
decl_stmt|;
annotation|@
name|Activate
specifier|public
name|void
name|activate
parameter_list|(
specifier|final
name|Map
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|properties
parameter_list|)
block|{
comment|// this.persistenceProvider = (IJenaPersistenceProvider) properties
comment|// .get(IJenaPersistenceProvider.class.getName());
name|this
operator|.
name|resourceManager
operator|=
operator|(
name|ResourceManager
operator|)
name|properties
operator|.
name|get
argument_list|(
name|ResourceManager
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|// FIXME Is it necessary to listen Immutable Graphs
comment|// Add Listener to existing MGraphs
for|for
control|(
specifier|final
name|UriRef
name|graphURI
range|:
name|tcManager
operator|.
name|listMGraphs
argument_list|()
control|)
block|{
name|MGraph
name|graph
init|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
name|graphURI
argument_list|)
decl_stmt|;
name|registerOntologyIfNotExist
argument_list|(
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|listeningMGraph
operator|.
name|containsKey
argument_list|(
name|graphURI
argument_list|)
operator|&&
operator|!
name|graph
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|GraphListener
name|listener
init|=
operator|new
name|SynchronizerGraphListener
argument_list|(
name|this
argument_list|,
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
name|graph
operator|.
name|addGraphListener
argument_list|(
name|listener
argument_list|,
operator|new
name|FilterTriple
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|,
literal|100
argument_list|)
expr_stmt|;
name|listeningMGraph
operator|.
name|put
argument_list|(
name|graphURI
argument_list|,
name|listener
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Added listener to the mgraph  "
operator|+
name|graphURI
operator|.
name|toString
argument_list|()
operator|+
literal|" : "
operator|+
name|listener
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Deactivate
specifier|public
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|cc
parameter_list|)
block|{
comment|// Synchronize before deactivating
name|synchronizeAll
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// Unregister GraphListeners
for|for
control|(
name|UriRef
name|graphUri
range|:
name|listeningMGraph
operator|.
name|keySet
argument_list|()
control|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Removing graph listener "
operator|+
name|listeningMGraph
operator|.
name|get
argument_list|(
name|graphUri
argument_list|)
operator|+
literal|" on "
operator|+
name|graphUri
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|tcManager
operator|.
name|getMGraph
argument_list|(
name|graphUri
argument_list|)
operator|.
name|removeGraphListener
argument_list|(
name|listeningMGraph
operator|.
name|get
argument_list|(
name|graphUri
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|listeningMGraph
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|clear
parameter_list|()
block|{
comment|// Unregister GraphListeners
for|for
control|(
name|UriRef
name|graphUri
range|:
name|listeningMGraph
operator|.
name|keySet
argument_list|()
control|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Removing graph listener "
operator|+
name|listeningMGraph
operator|.
name|get
argument_list|(
name|graphUri
argument_list|)
operator|+
literal|" on "
operator|+
name|graphUri
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|tcManager
operator|.
name|getMGraph
argument_list|(
name|graphUri
argument_list|)
operator|.
name|removeGraphListener
argument_list|(
name|listeningMGraph
operator|.
name|get
argument_list|(
name|graphUri
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|listeningMGraph
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|synchronizeAll
parameter_list|(
name|boolean
name|force
parameter_list|)
block|{
synchronized|synchronized
init|(
name|tcManager
init|)
block|{
if|if
condition|(
name|force
condition|)
block|{
for|for
control|(
name|UriRef
name|graphURI
range|:
name|tcManager
operator|.
name|listMGraphs
argument_list|()
control|)
block|{
name|String
name|ontologyURI
init|=
name|resourceManager
operator|.
name|getOntologyFullPath
argument_list|(
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ontologyURI
operator|==
literal|null
condition|)
block|{
name|resourceManager
operator|.
name|registerOntology
argument_list|(
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|synchronizeGraph
argument_list|(
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Process Removed MGraphs
name|List
argument_list|<
name|UriRef
argument_list|>
name|toBedeleted
init|=
operator|new
name|ArrayList
argument_list|<
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|UriRef
name|graphURI
range|:
name|listeningMGraph
operator|.
name|keySet
argument_list|()
control|)
block|{
try|try
block|{
name|MGraph
name|graph
init|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
name|graphURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|graph
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|graph
operator|.
name|removeGraphListener
argument_list|(
name|listeningMGraph
operator|.
name|get
argument_list|(
name|graphURI
argument_list|)
argument_list|)
expr_stmt|;
name|toBedeleted
operator|.
name|add
argument_list|(
name|graphURI
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchEntityException
name|e
parameter_list|)
block|{
name|toBedeleted
operator|.
name|add
argument_list|(
name|graphURI
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|UriRef
name|graphURI
range|:
name|toBedeleted
control|)
block|{
name|listeningMGraph
operator|.
name|remove
argument_list|(
name|graphURI
argument_list|)
expr_stmt|;
name|resourceManager
operator|.
name|removeOntology
argument_list|(
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Stopped Listening MGraph: "
operator|+
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Stopped Listening MGraph: "
operator|+
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Process Added MGraphs
for|for
control|(
specifier|final
name|UriRef
name|graphURI
range|:
name|tcManager
operator|.
name|listMGraphs
argument_list|()
control|)
block|{
name|MGraph
name|graph
init|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
name|graphURI
argument_list|)
decl_stmt|;
name|registerOntologyIfNotExist
argument_list|(
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|listeningMGraph
operator|.
name|containsKey
argument_list|(
name|graphURI
argument_list|)
operator|&&
operator|!
name|graph
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|GraphListener
name|listener
init|=
operator|new
name|SynchronizerGraphListener
argument_list|(
name|this
argument_list|,
name|graphURI
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
name|graph
operator|.
name|addGraphListener
argument_list|(
name|listener
argument_list|,
operator|new
name|FilterTriple
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|,
literal|100
argument_list|)
expr_stmt|;
name|listeningMGraph
operator|.
name|put
argument_list|(
name|graphURI
argument_list|,
name|listener
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Added listener to the mgraph  "
operator|+
name|graphURI
operator|.
name|toString
argument_list|()
operator|+
literal|" : "
operator|+
name|listener
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|synchronizeGraph
parameter_list|(
name|String
name|graphURI
parameter_list|)
block|{
name|LockableMGraph
name|mgraph
init|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
operator|new
name|UriRef
argument_list|(
name|graphURI
argument_list|)
argument_list|)
decl_stmt|;
name|Lock
name|lock
init|=
name|mgraph
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
decl_stmt|;
name|lock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|JenaGraph
name|jgraph
init|=
operator|new
name|JenaGraph
argument_list|(
name|mgraph
argument_list|)
decl_stmt|;
name|Model
name|model
init|=
name|ModelFactory
operator|.
name|createModelForGraph
argument_list|(
name|jgraph
argument_list|)
decl_stmt|;
name|GraphSynchronizer
name|es
init|=
operator|new
name|GraphSynchronizer
argument_list|(
name|resourceManager
argument_list|,
name|model
argument_list|,
name|graphURI
argument_list|)
decl_stmt|;
name|es
operator|.
name|synchronize
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|synchronizeResourceOnGraph
parameter_list|(
name|String
name|graphURI
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|resourceURIs
parameter_list|)
block|{
name|LockableMGraph
name|graph
init|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
operator|new
name|UriRef
argument_list|(
name|graphURI
argument_list|)
argument_list|)
decl_stmt|;
name|Lock
name|lock
init|=
name|graph
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
decl_stmt|;
name|lock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|JenaGraph
name|jgraph
init|=
operator|new
name|JenaGraph
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|OntModel
name|ontology
init|=
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|(
name|OntModelSpec
operator|.
name|OWL_DL_MEM
argument_list|,
name|ModelFactory
operator|.
name|createModelForGraph
argument_list|(
name|jgraph
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|resourceURI
range|:
name|resourceURIs
control|)
block|{
name|OntClass
name|klazz
init|=
name|ontology
operator|.
name|getOntClass
argument_list|(
name|resourceURI
argument_list|)
decl_stmt|;
name|ObjectProperty
name|objectProp
init|=
name|ontology
operator|.
name|getObjectProperty
argument_list|(
name|resourceURI
argument_list|)
decl_stmt|;
name|DatatypeProperty
name|datatypeProp
init|=
name|ontology
operator|.
name|getDatatypeProperty
argument_list|(
name|resourceURI
argument_list|)
decl_stmt|;
name|Individual
name|individual
init|=
name|ontology
operator|.
name|getIndividual
argument_list|(
name|resourceURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|klazz
operator|!=
literal|null
condition|)
block|{
name|resourceManager
operator|.
name|registerClass
argument_list|(
name|graphURI
argument_list|,
name|resourceURI
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Added Class "
operator|+
name|resourceURI
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|objectProp
operator|!=
literal|null
condition|)
block|{
name|resourceManager
operator|.
name|registerObjectProperty
argument_list|(
name|graphURI
argument_list|,
name|resourceURI
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Added ObjectProperty "
operator|+
name|resourceURI
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|datatypeProp
operator|!=
literal|null
condition|)
block|{
name|resourceManager
operator|.
name|registerDatatypeProperty
argument_list|(
name|graphURI
argument_list|,
name|resourceURI
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Added DataProperty "
operator|+
name|resourceURI
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|individual
operator|!=
literal|null
condition|)
block|{
name|resourceManager
operator|.
name|registerIndividual
argument_list|(
name|graphURI
argument_list|,
name|resourceURI
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Added Individual"
operator|+
name|resourceURI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Not found, delete if the resource belongs to this graph
name|String
name|ontologyURI
init|=
name|resourceManager
operator|.
name|resolveOntologyURIFromResourceURI
argument_list|(
name|resourceURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|ontologyURI
operator|!=
literal|null
operator|&&
name|ontologyURI
operator|.
name|equals
argument_list|(
name|graphURI
argument_list|)
condition|)
block|{
name|resourceManager
operator|.
name|removeResource
argument_list|(
name|resourceURI
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Removed Resource"
operator|+
name|resourceURI
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|registerOntologyIfNotExist
parameter_list|(
name|String
name|graphURI
parameter_list|)
block|{
name|String
name|ontologyURI
init|=
name|resourceManager
operator|.
name|getOntologyFullPath
argument_list|(
name|graphURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|ontologyURI
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Registering ontology: "
operator|+
name|graphURI
argument_list|)
expr_stmt|;
name|resourceManager
operator|.
name|registerOntology
argument_list|(
name|graphURI
argument_list|)
expr_stmt|;
name|synchronizeGraph
argument_list|(
name|graphURI
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|bindTcManager
parameter_list|(
name|TcManager
name|tcManager
parameter_list|)
block|{
name|this
operator|.
name|tcManager
operator|=
name|tcManager
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindTcManager
parameter_list|(
name|TcManager
name|tcManager
parameter_list|)
block|{
synchronized|synchronized
init|(
name|tcManager
init|)
block|{
name|this
operator|.
name|tcManager
operator|=
literal|null
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

