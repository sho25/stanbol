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
name|fail
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
name|OWLOntologyManager
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
name|manager
operator|.
name|ontology
operator|.
name|OntologyIndex
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
name|ontology
operator|.
name|OntologyScopeFactory
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
name|ontology
operator|.
name|OntologySpaceFactory
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
name|ontology
operator|.
name|ScopeRegistry
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
name|registry
operator|.
name|KReSRegistryLoader
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
name|session
operator|.
name|KReSSessionManager
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
name|api
operator|.
name|storage
operator|.
name|OntologyStorage
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
name|KReSONManager
name|onManager
decl_stmt|;
specifier|static
name|String
name|graphNS
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
name|setup
parameter_list|()
block|{
name|onManager
operator|=
operator|new
name|KReSONManager
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getUrisToActivate
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|KReSSessionManager
name|getSessionManager
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|ScopeRegistry
name|getScopeRegistry
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|KReSRegistryLoader
name|getRegistryLoader
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLDataFactory
name|getOwlFactory
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntologyManager
name|getOwlCacheManager
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|OntologyStorage
name|getOntologyStore
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|OntologySpaceFactory
name|getOntologySpaceFactory
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|OntologyScopeFactory
name|getOntologyScopeFactory
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|OntologyIndex
name|getOntologyIndex
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getKReSNamespace
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
block|}
expr_stmt|;
name|dbExtractor
operator|=
operator|new
name|DBExtractor
argument_list|()
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
name|Test
specifier|public
name|void
name|testSchemaReengineering
parameter_list|()
block|{
name|OWLOntology
name|ontology
init|=
name|dbExtractor
operator|.
name|schemaReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
literal|null
argument_list|)
decl_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testDataReengineering
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
try|try
block|{
name|OWLOntology
name|ontology
init|=
name|dbExtractor
operator|.
name|dataReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
literal|null
argument_list|,
name|dbExtractor
operator|.
name|schemaReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
literal|null
argument_list|)
argument_list|)
decl_stmt|;
block|}
catch|catch
parameter_list|(
name|ReengineeringException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Some errors occur with dataReengineering of DBExtractor."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testReengineering
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
try|try
block|{
name|OWLOntology
name|ontology
init|=
name|dbExtractor
operator|.
name|reengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
literal|null
argument_list|)
decl_stmt|;
block|}
catch|catch
parameter_list|(
name|ReengineeringException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Some errors occur with reengineering of DBExtractor."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

