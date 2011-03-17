begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rengineer
operator|.
name|base
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
name|apache
operator|.
name|stanbol
operator|.
name|reengineer
operator|.
name|base
operator|.
name|api
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
name|api
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
name|api
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
name|api
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
name|api
operator|.
name|impl
operator|.
name|SemionManagerImpl
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
name|api
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
name|api
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

begin_class
specifier|public
class|class
name|SemionManagerTest
block|{
specifier|public
specifier|static
name|SemionReengineer
name|semionReengineer
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
name|semionReengineer
operator|=
operator|new
name|SemionReengineer
argument_list|()
block|{
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
comment|// TODO Auto-generated method stub
return|return
literal|null
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
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getReengineerType
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
name|ReengineerType
operator|.
name|XML
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
comment|// TODO Auto-generated method stub
return|return
literal|null
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
name|int
name|dataSourceType
parameter_list|)
block|{
if|if
condition|(
name|dataSourceType
operator|==
name|ReengineerType
operator|.
name|XML
condition|)
block|{
return|return
literal|true
return|;
block|}
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
name|DataSource
name|dataSource
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|false
return|;
block|}
block|}
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|bindTest
parameter_list|()
block|{
name|SemionManager
name|semionManager
init|=
operator|new
name|SemionManagerImpl
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|semionManager
operator|.
name|bindReengineer
argument_list|(
name|semionReengineer
argument_list|)
condition|)
block|{
name|fail
argument_list|(
literal|"Bind test failed for SemionManager"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|unbindByReengineerTypeTest
parameter_list|()
block|{
name|SemionManager
name|semionManager
init|=
operator|new
name|SemionManagerImpl
argument_list|()
decl_stmt|;
name|semionManager
operator|.
name|bindReengineer
argument_list|(
name|semionReengineer
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|semionManager
operator|.
name|unbindReengineer
argument_list|(
name|ReengineerType
operator|.
name|XML
argument_list|)
condition|)
block|{
name|fail
argument_list|(
literal|"Unbind by reengineer type test failed for SemionManager"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|unbindByReengineerInstanceTest
parameter_list|()
block|{
name|SemionManager
name|semionManager
init|=
operator|new
name|SemionManagerImpl
argument_list|()
decl_stmt|;
name|semionManager
operator|.
name|bindReengineer
argument_list|(
name|semionReengineer
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|semionManager
operator|.
name|unbindReengineer
argument_list|(
name|semionReengineer
argument_list|)
condition|)
block|{
name|fail
argument_list|(
literal|"Unbind by reengineer instance test failed for SemionManager"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

