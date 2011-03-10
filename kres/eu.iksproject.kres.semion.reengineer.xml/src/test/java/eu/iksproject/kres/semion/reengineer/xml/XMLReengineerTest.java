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
name|xml
package|;
end_package

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
name|DataSource
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
name|SemionReengineer
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
name|util
operator|.
name|ReengineerType
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
name|XMLReengineerTest
block|{
specifier|static
name|DataSource
name|dataSource
decl_stmt|;
specifier|static
name|String
name|graphNS
decl_stmt|;
specifier|static
name|IRI
name|outputIRI
decl_stmt|;
specifier|static
name|SemionReengineer
name|xmlExtractor
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setupClass
parameter_list|()
block|{
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
name|dataSource
operator|=
operator|new
name|DataSource
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Object
name|getDataSource
parameter_list|()
block|{
name|InputStream
name|xmlStream
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/META-INF/test/weather.xml"
argument_list|)
decl_stmt|;
return|return
name|xmlStream
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getDataSourceType
parameter_list|()
block|{
return|return
name|ReengineerType
operator|.
name|XML
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getID
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
block|}
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|dataReengineeringTest
parameter_list|()
throws|throws
name|Exception
block|{
name|OWLOntology
name|schemaOntology
init|=
name|xmlExtractor
operator|.
name|schemaReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|)
decl_stmt|;
name|xmlExtractor
operator|.
name|dataReengineering
argument_list|(
name|graphNS
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
name|outputIRI
operator|.
name|toString
argument_list|()
operator|+
literal|"_new"
argument_list|)
argument_list|,
name|dataSource
argument_list|,
name|schemaOntology
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|reengineeringTest
parameter_list|()
throws|throws
name|Exception
block|{
name|xmlExtractor
operator|.
name|reengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|schemaReengineeringTest
parameter_list|()
throws|throws
name|Exception
block|{
name|xmlExtractor
operator|.
name|schemaReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setup
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
name|KReSONManager
name|onManager
init|=
operator|new
name|ONManager
argument_list|(
literal|null
argument_list|,
name|emptyConf
argument_list|)
decl_stmt|;
name|xmlExtractor
operator|=
operator|new
name|XMLExtractor
argument_list|(
operator|new
name|SemionManagerImpl
argument_list|(
name|onManager
argument_list|)
argument_list|,
name|onManager
argument_list|,
name|emptyConf
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|xmlExtractor
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

