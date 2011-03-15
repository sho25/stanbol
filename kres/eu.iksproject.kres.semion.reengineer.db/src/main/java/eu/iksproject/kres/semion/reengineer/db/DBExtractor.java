begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|semion
operator|.
name|reengineer
operator|.
name|db
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
name|Dictionary
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
name|access
operator|.
name|WeightedTcProvider
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
name|ontonet
operator|.
name|api
operator|.
name|DuplicateIDException
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
name|ontonet
operator|.
name|api
operator|.
name|KReSONManager
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
name|ontonet
operator|.
name|api
operator|.
name|io
operator|.
name|RootOntologyIRISource
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyScope
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyScopeFactory
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologySpaceFactory
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
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|ScopeRegistry
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
name|ontonet
operator|.
name|api
operator|.
name|session
operator|.
name|KReSSession
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
name|ontonet
operator|.
name|api
operator|.
name|session
operator|.
name|KReSSessionManager
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
name|reengineer
operator|.
name|base
operator|.
name|DataSource
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
name|reengineer
operator|.
name|base
operator|.
name|ReengineeringException
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
name|reengineer
operator|.
name|base
operator|.
name|SemionManager
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
name|reengineer
operator|.
name|base
operator|.
name|SemionReengineer
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
name|reengineer
operator|.
name|base
operator|.
name|settings
operator|.
name|ConnectionSettings
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
name|reengineer
operator|.
name|base
operator|.
name|util
operator|.
name|ReengineerType
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
name|reengineer
operator|.
name|base
operator|.
name|util
operator|.
name|UnsupportedReengineerException
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|apibinding
operator|.
name|OWLManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLDataFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntology
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologyCreationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologyManager
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
name|kres
operator|.
name|ontologies
operator|.
name|DBS_L1
import|;
end_import

begin_comment
comment|/**  * The {@code DBExtractor} is an implementation of the {@link SemionReengineer} for relational databases.  *   * @author andrea.nuzzolese  *   */
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
argument_list|(
name|SemionReengineer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|DBExtractor
implements|implements
name|SemionReengineer
block|{
specifier|public
specifier|static
specifier|final
name|String
name|_DB_DATA_REENGINEERING_SESSION_DEFAULT
init|=
literal|"/db-data-reengineering-session"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_DB_DATA_REENGINEERING_SESSION_SPACE_DEFAULT
init|=
literal|"/db-data-reengineering-session-space"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_DB_REENGINEERING_SESSION_SPACE_DEFAULT
init|=
literal|"/db-schema-reengineering-session-space"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_DB_SCHEMA_REENGINEERING_ONTOLOGY_SPACE_DEFAULT
init|=
literal|"/db-schema-reengineering-ontology-space"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_DB_SCHEMA_REENGINEERING_SESSION_DEFAULT
init|=
literal|"/db-schema-reengineering-session"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_HOST_NAME_AND_PORT_DEFAULT
init|=
literal|"localhost:8080"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|_REENGINEERING_SCOPE_DEFAULT
init|=
literal|"db_reengineering"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|_DB_DATA_REENGINEERING_SESSION_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|DB_DATA_REENGINEERING_SESSION
init|=
literal|"eu.iksproject.kres.semion.reengineer.db.data"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|_DB_DATA_REENGINEERING_SESSION_SPACE_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|DB_DATA_REENGINEERING_SESSION_SPACE
init|=
literal|"eu.iksproject.kres.semion.reengineer.space.db.data"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|_DB_REENGINEERING_SESSION_SPACE_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|DB_REENGINEERING_SESSION_SPACE
init|=
literal|"http://kres.iks-project.eu/space/reengineering/db"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|_DB_SCHEMA_REENGINEERING_ONTOLOGY_SPACE_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|DB_SCHEMA_REENGINEERING_ONTOLOGY_SPACE
init|=
literal|"eu.iksproject.kres.semion.reengineer.ontology.space.db"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|_DB_SCHEMA_REENGINEERING_SESSION_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|DB_SCHEMA_REENGINEERING_SESSION
init|=
literal|"eu.iksproject.kres.semion.reengineer.db.schema"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|_HOST_NAME_AND_PORT_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|HOST_NAME_AND_PORT
init|=
literal|"host.name.port"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
name|_REENGINEERING_SCOPE_DEFAULT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|REENGINEERING_SCOPE
init|=
literal|"db.reengineering.scope"
decl_stmt|;
name|ConnectionSettings
name|connectionSettings
decl_stmt|;
name|String
name|databaseURI
decl_stmt|;
specifier|private
name|IRI
name|kReSSessionID
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
annotation|@
name|Reference
name|KReSONManager
name|onManager
decl_stmt|;
annotation|@
name|Reference
name|SemionManager
name|reengineeringManager
decl_stmt|;
specifier|private
name|IRI
name|reengineeringScopeIRI
decl_stmt|;
specifier|private
name|IRI
name|reengineeringSpaceIRI
decl_stmt|;
name|MGraph
name|schemaGraph
decl_stmt|;
specifier|protected
name|OntologyScope
name|scope
decl_stmt|;
annotation|@
name|Reference
name|TcManager
name|tcManager
decl_stmt|;
annotation|@
name|Reference
name|WeightedTcProvider
name|weightedTcProvider
decl_stmt|;
comment|/**      * This default constructor is<b>only</b> intended to be used by the OSGI environment with Service      * Component Runtime support.      *<p>      * DO NOT USE to manually create instances - the DBExtractor instances do need to be configured! YOU NEED      * TO USE {@link #DBExtractor(KReSONManager)} or its overloads, to parse the configuration and then      * initialise the rule store if running outside a OSGI environment.      */
specifier|public
name|DBExtractor
parameter_list|()
block|{      }
comment|/**      *       * Create a new {@link DBExtractor} that is formally a {@link SemionReengineer}.      *       */
specifier|public
name|DBExtractor
parameter_list|(
name|SemionManager
name|reengineeringManager
parameter_list|,
name|KReSONManager
name|onManager
parameter_list|,
name|TcManager
name|tcManager
parameter_list|,
name|WeightedTcProvider
name|weightedTcProvider
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|reengineeringManager
operator|=
name|reengineeringManager
expr_stmt|;
name|this
operator|.
name|onManager
operator|=
name|onManager
expr_stmt|;
name|this
operator|.
name|tcManager
operator|=
name|tcManager
expr_stmt|;
name|this
operator|.
name|weightedTcProvider
operator|=
name|weightedTcProvider
expr_stmt|;
name|activate
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
block|}
comment|/**      * Create a new {@link DBExtractor} that is formally a {@link SemionReengineer}.      *       * @param databaseURI      *            {@link String}      * @param schemaGraph      *            {@link MGraph}      * @param connectionSettings      *            {@link ConnectionSettings}      */
specifier|public
name|DBExtractor
parameter_list|(
name|SemionManager
name|reengineeringManager
parameter_list|,
name|KReSONManager
name|onManager
parameter_list|,
name|TcManager
name|tcManager
parameter_list|,
name|WeightedTcProvider
name|weightedTcProvider
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|,
name|String
name|databaseURI
parameter_list|,
name|MGraph
name|schemaGraph
parameter_list|,
name|ConnectionSettings
name|connectionSettings
parameter_list|)
block|{
comment|// Copy code from overloaded constructor, except that the call to
comment|// activate() goes at the end.
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|reengineeringManager
operator|=
name|reengineeringManager
expr_stmt|;
name|this
operator|.
name|onManager
operator|=
name|onManager
expr_stmt|;
name|this
operator|.
name|tcManager
operator|=
name|tcManager
expr_stmt|;
name|this
operator|.
name|weightedTcProvider
operator|=
name|weightedTcProvider
expr_stmt|;
name|this
operator|.
name|databaseURI
operator|=
name|databaseURI
expr_stmt|;
name|this
operator|.
name|schemaGraph
operator|=
name|schemaGraph
expr_stmt|;
name|this
operator|.
name|connectionSettings
operator|=
name|connectionSettings
expr_stmt|;
name|activate
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
block|}
comment|/**      * Used to configure an instance within an OSGi container.      *       * @throws IOException      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|DBExtractor
operator|.
name|class
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No valid"
operator|+
name|ComponentContext
operator|.
name|class
operator|+
literal|" parsed in activate!"
argument_list|)
throw|;
block|}
name|activate
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|activate
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|String
name|reengineeringScopeID
init|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|REENGINEERING_SCOPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|reengineeringScopeID
operator|==
literal|null
condition|)
name|reengineeringScopeID
operator|=
name|_REENGINEERING_SCOPE_DEFAULT
expr_stmt|;
name|String
name|hostNameAndPort
init|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|HOST_NAME_AND_PORT
argument_list|)
decl_stmt|;
if|if
condition|(
name|hostNameAndPort
operator|==
literal|null
condition|)
name|hostNameAndPort
operator|=
name|_HOST_NAME_AND_PORT_DEFAULT
expr_stmt|;
comment|// TODO: Manage the other properties
name|hostNameAndPort
operator|=
literal|"http://"
operator|+
name|hostNameAndPort
expr_stmt|;
name|reengineeringScopeIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|hostNameAndPort
operator|+
literal|"/kres/ontology/"
operator|+
name|reengineeringScopeID
argument_list|)
expr_stmt|;
name|reengineeringSpaceIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|DB_REENGINEERING_SESSION_SPACE
argument_list|)
expr_stmt|;
name|reengineeringManager
operator|.
name|bindReengineer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|KReSSessionManager
name|kReSSessionManager
init|=
name|onManager
operator|.
name|getSessionManager
argument_list|()
decl_stmt|;
name|KReSSession
name|kReSSession
init|=
name|kReSSessionManager
operator|.
name|createSession
argument_list|()
decl_stmt|;
name|kReSSessionID
operator|=
name|kReSSession
operator|.
name|getID
argument_list|()
expr_stmt|;
name|OntologyScopeFactory
name|ontologyScopeFactory
init|=
name|onManager
operator|.
name|getOntologyScopeFactory
argument_list|()
decl_stmt|;
name|ScopeRegistry
name|scopeRegistry
init|=
name|onManager
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologySpaceFactory
name|ontologySpaceFactory
init|=
name|onManager
operator|.
name|getOntologySpaceFactory
argument_list|()
decl_stmt|;
name|scope
operator|=
literal|null
expr_stmt|;
try|try
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Semion DBExtractor : created scope with IRI "
operator|+
name|REENGINEERING_SCOPE
argument_list|)
expr_stmt|;
name|IRI
name|iri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|DBS_L1
operator|.
name|URI
argument_list|)
decl_stmt|;
name|OWLOntologyManager
name|ontologyManager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLOntology
name|owlOntology
init|=
name|ontologyManager
operator|.
name|createOntology
argument_list|(
name|iri
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Created ONTOLOGY OWL"
argument_list|)
expr_stmt|;
name|scope
operator|=
name|ontologyScopeFactory
operator|.
name|createOntologyScope
argument_list|(
name|reengineeringScopeIRI
argument_list|,
operator|new
name|RootOntologyIRISource
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|DBS_L1
operator|.
name|URI
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// scope.setUp();
name|scopeRegistry
operator|.
name|registerScope
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Semion DBExtractor : already existing scope for IRI "
operator|+
name|REENGINEERING_SCOPE
argument_list|)
expr_stmt|;
name|scope
operator|=
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|reengineeringScopeIRI
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Semion DBExtractor : No OntologyInputSource for ONManager."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|scope
operator|!=
literal|null
condition|)
block|{
name|scope
operator|.
name|addSessionSpace
argument_list|(
name|ontologySpaceFactory
operator|.
name|createSessionOntologySpace
argument_list|(
name|reengineeringSpaceIRI
argument_list|)
argument_list|,
name|kReSSession
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|scopeRegistry
operator|.
name|setScopeActive
argument_list|(
name|reengineeringScopeIRI
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"Activated KReS Semion RDB Reengineer"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canPerformReengineering
parameter_list|(
name|DataSource
name|dataSource
parameter_list|)
block|{
if|if
condition|(
name|dataSource
operator|.
name|getDataSourceType
argument_list|()
operator|==
name|getReengineerType
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canPerformReengineering
parameter_list|(
name|int
name|dataSourceType
parameter_list|)
block|{
if|if
condition|(
name|dataSourceType
operator|==
name|getReengineerType
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canPerformReengineering
parameter_list|(
name|OWLOntology
name|schemaOntology
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canPerformReengineering
parameter_list|(
name|String
name|dataSourceType
parameter_list|)
throws|throws
name|UnsupportedReengineerException
block|{
return|return
name|canPerformReengineering
argument_list|(
name|ReengineerType
operator|.
name|getType
argument_list|(
name|dataSourceType
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|dataReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|,
name|OWLOntology
name|schemaOntology
parameter_list|)
throws|throws
name|ReengineeringException
block|{
name|SemionDBDataTransformer
name|semionDBDataTransformer
init|=
operator|new
name|SemionDBDataTransformer
argument_list|(
name|onManager
argument_list|,
name|schemaOntology
argument_list|)
decl_stmt|;
return|return
name|semionDBDataTransformer
operator|.
name|transformData
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|)
return|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|DBExtractor
operator|.
name|class
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
name|reengineeringManager
operator|.
name|unbindReengineer
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getReengineerType
parameter_list|()
block|{
return|return
name|ReengineerType
operator|.
name|RDB
return|;
block|}
specifier|private
name|OntologyScope
name|getScope
parameter_list|()
block|{
name|OntologyScope
name|ontologyScope
init|=
literal|null
decl_stmt|;
name|ScopeRegistry
name|scopeRegistry
init|=
name|onManager
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
if|if
condition|(
name|scopeRegistry
operator|.
name|isScopeActive
argument_list|(
name|reengineeringScopeIRI
argument_list|)
condition|)
block|{
name|ontologyScope
operator|=
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|reengineeringScopeIRI
argument_list|)
expr_stmt|;
block|}
return|return
name|ontologyScope
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|reengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|)
throws|throws
name|ReengineeringException
block|{
name|IRI
name|schemaIRI
decl_stmt|;
if|if
condition|(
name|outputIRI
operator|!=
literal|null
condition|)
block|{
name|schemaIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|outputIRI
operator|.
name|toString
argument_list|()
operator|+
literal|"/schema"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|schemaIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
literal|"/schema"
argument_list|)
expr_stmt|;
block|}
name|OWLOntology
name|schemaOntology
init|=
name|schemaReengineering
argument_list|(
name|graphNS
operator|+
literal|"/schema"
argument_list|,
name|schemaIRI
argument_list|,
name|dataSource
argument_list|)
decl_stmt|;
return|return
name|dataReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|,
name|schemaOntology
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|schemaReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|)
block|{
name|OWLOntology
name|schemaOntology
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|outputIRI
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Semion DBExtractor : starting to generate RDF graph with URI "
operator|+
name|outputIRI
operator|.
name|toString
argument_list|()
operator|+
literal|" of a db schema "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Semion DBExtractor : starting to generate RDF graph of a db schema "
argument_list|)
expr_stmt|;
block|}
name|OntologyScope
name|reengineeringScope
init|=
name|getScope
argument_list|()
decl_stmt|;
if|if
condition|(
name|reengineeringScope
operator|!=
literal|null
condition|)
block|{
name|ConnectionSettings
name|connectionSettings
init|=
operator|(
name|ConnectionSettings
operator|)
name|dataSource
operator|.
name|getDataSource
argument_list|()
decl_stmt|;
name|SemionDBSchemaGenerator
name|schemaGenerator
init|=
operator|new
name|SemionDBSchemaGenerator
argument_list|(
name|outputIRI
argument_list|,
name|connectionSettings
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"OWL MANAGER IN SEMION: "
operator|+
name|onManager
argument_list|)
expr_stmt|;
name|OWLOntologyManager
name|ontologyManager
init|=
name|onManager
operator|.
name|getOwlCacheManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|dataFactory
init|=
name|onManager
operator|.
name|getOwlFactory
argument_list|()
decl_stmt|;
name|schemaOntology
operator|=
name|schemaGenerator
operator|.
name|getSchema
argument_list|(
name|ontologyManager
argument_list|,
name|dataFactory
argument_list|)
expr_stmt|;
if|if
condition|(
name|outputIRI
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Created graph with URI "
operator|+
name|outputIRI
operator|.
name|toString
argument_list|()
operator|+
literal|" of DB Schema."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Created graph of DB Schema."
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|schemaOntology
return|;
block|}
block|}
end_class

end_unit

