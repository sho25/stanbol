begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
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
name|util
operator|.
name|Dictionary
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
name|TcProvider
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|serializedform
operator|.
name|Parser
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
name|sparql
operator|.
name|QueryEngine
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
name|sparql
operator|.
name|JenaSparqlEngine
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
name|simple
operator|.
name|storage
operator|.
name|SimpleTcProvider
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
name|core
operator|.
name|OfflineConfigurationImpl
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
name|core
operator|.
name|scope
operator|.
name|ScopeManagerImpl
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
name|multiplexer
operator|.
name|clerezza
operator|.
name|collector
operator|.
name|ClerezzaCollectorFactory
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
name|multiplexer
operator|.
name|clerezza
operator|.
name|ontology
operator|.
name|ClerezzaOntologyProvider
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
name|servicesapi
operator|.
name|OfflineConfiguration
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
name|servicesapi
operator|.
name|ontology
operator|.
name|OntologyProvider
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
name|servicesapi
operator|.
name|scope
operator|.
name|ScopeManager
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
name|impl
operator|.
name|ReengineerManagerImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
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

begin_class
specifier|public
class|class
name|DBExtractorTest
block|{
specifier|static
name|DBExtractor
name|dbExtractor
decl_stmt|;
specifier|static
name|String
name|graphNS
decl_stmt|;
specifier|static
name|ScopeManager
name|onManager
decl_stmt|;
specifier|static
name|IRI
name|outputIRI
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setupClass
parameter_list|()
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|emptyConf
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
class|class
name|SpecialTcManager
extends|extends
name|TcManager
block|{
specifier|public
name|SpecialTcManager
parameter_list|(
name|QueryEngine
name|qe
parameter_list|,
name|WeightedTcProvider
name|wtcp
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|bindQueryEngine
argument_list|(
name|qe
argument_list|)
expr_stmt|;
name|bindWeightedTcProvider
argument_list|(
name|wtcp
argument_list|)
expr_stmt|;
block|}
block|}
name|QueryEngine
name|qe
init|=
operator|new
name|JenaSparqlEngine
argument_list|()
decl_stmt|;
name|WeightedTcProvider
name|wtcp
init|=
operator|new
name|SimpleTcProvider
argument_list|()
decl_stmt|;
name|TcManager
name|tcm
init|=
operator|new
name|SpecialTcManager
argument_list|(
name|qe
argument_list|,
name|wtcp
argument_list|)
decl_stmt|;
name|OfflineConfiguration
name|offline
init|=
operator|new
name|OfflineConfigurationImpl
argument_list|(
name|emptyConf
argument_list|)
decl_stmt|;
name|OntologyProvider
argument_list|<
name|TcProvider
argument_list|>
name|ontologyProvider
init|=
operator|new
name|ClerezzaOntologyProvider
argument_list|(
name|tcm
argument_list|,
name|offline
argument_list|,
operator|new
name|Parser
argument_list|()
argument_list|)
decl_stmt|;
comment|// Two different ontology storages, the same sparql engine and tcprovider
name|ClerezzaCollectorFactory
name|sf
init|=
operator|new
name|ClerezzaCollectorFactory
argument_list|(
name|ontologyProvider
argument_list|,
name|emptyConf
argument_list|)
decl_stmt|;
name|onManager
operator|=
operator|new
name|ScopeManagerImpl
argument_list|(
name|ontologyProvider
argument_list|,
name|offline
argument_list|,
name|sf
argument_list|,
name|sf
argument_list|,
name|emptyConf
argument_list|)
expr_stmt|;
name|dbExtractor
operator|=
operator|new
name|DBExtractor
argument_list|(
operator|new
name|ReengineerManagerImpl
argument_list|(
name|emptyConf
argument_list|)
argument_list|,
name|onManager
argument_list|,
name|tcm
argument_list|,
name|wtcp
argument_list|,
name|emptyConf
argument_list|)
expr_stmt|;
name|graphNS
operator|=
literal|"http://kres.iks-project.eu/reengineering/test"
expr_stmt|;
name|outputIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setup
parameter_list|()
block|{      }
annotation|@
name|Before
specifier|public
name|void
name|tearDown
parameter_list|()
block|{      }
annotation|@
name|Test
specifier|public
name|void
name|testDataReengineering
parameter_list|()
block|{
comment|// graphNS = "http://kres.iks-project.eu/reengineering/test";
comment|// outputIRI = IRI.create(graphNS);
comment|// try {
comment|// OWLOntology ontology = dbExtractor.dataReengineering(graphNS,
comment|// outputIRI, null, dbExtractor.schemaReengineering(graphNS,
comment|// outputIRI, null));
comment|// } catch (ReengineeringException e) {
comment|// fail("Some errors occur with dataReengineering of DBExtractor.");
comment|// }
block|}
annotation|@
name|Test
specifier|public
name|void
name|testReengineering
parameter_list|()
block|{
comment|// graphNS = "http://kres.iks-project.eu/reengineering/test";
comment|// outputIRI = IRI.create(graphNS);
comment|// try {
comment|// OWLOntology ontology = dbExtractor.reengineering(graphNS,
comment|// outputIRI, null);
comment|// } catch (ReengineeringException e) {
comment|// fail("Some errors occur with reengineering of DBExtractor.");
comment|// }
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSchemaReengineering
parameter_list|()
block|{
comment|// OWLOntology ontology = dbExtractor.schemaReengineering(graphNS,
comment|// outputIRI, null);
block|}
block|}
end_class

end_unit

