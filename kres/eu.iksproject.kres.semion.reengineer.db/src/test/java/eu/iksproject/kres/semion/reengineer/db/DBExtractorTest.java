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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|*
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
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|KReSONManager
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
name|api
operator|.
name|semion
operator|.
name|ReengineeringException
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
name|manager
operator|.
name|ONManager
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
name|semion
operator|.
name|manager
operator|.
name|SemionManagerImpl
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
name|KReSONManager
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
name|onManager
operator|=
operator|new
name|ONManager
argument_list|(
literal|null
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
name|SemionManagerImpl
argument_list|(
name|onManager
argument_list|)
argument_list|,
name|onManager
argument_list|,
operator|new
name|TcManager
argument_list|()
argument_list|,
literal|null
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
block|{ 		 	}
annotation|@
name|Before
specifier|public
name|void
name|tearDown
parameter_list|()
block|{ 		 	}
annotation|@
name|Test
specifier|public
name|void
name|testDataReengineering
parameter_list|()
block|{
comment|//		graphNS = "http://kres.iks-project.eu/reengineering/test";
comment|//		outputIRI = IRI.create(graphNS);
comment|//		try {
comment|//			OWLOntology ontology = dbExtractor.dataReengineering(graphNS,
comment|//					outputIRI, null, dbExtractor.schemaReengineering(graphNS,
comment|//							outputIRI, null));
comment|//		} catch (ReengineeringException e) {
comment|//			fail("Some errors occur with dataReengineering of DBExtractor.");
comment|//		}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testReengineering
parameter_list|()
block|{
comment|//		graphNS = "http://kres.iks-project.eu/reengineering/test";
comment|//		outputIRI = IRI.create(graphNS);
comment|//		try {
comment|//			OWLOntology ontology = dbExtractor.reengineering(graphNS,
comment|//					outputIRI, null);
comment|//		} catch (ReengineeringException e) {
comment|//			fail("Some errors occur with reengineering of DBExtractor.");
comment|//		}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSchemaReengineering
parameter_list|()
block|{
comment|//		OWLOntology ontology = dbExtractor.schemaReengineering(graphNS,
comment|//				outputIRI, null);
block|}
block|}
end_class

end_unit

