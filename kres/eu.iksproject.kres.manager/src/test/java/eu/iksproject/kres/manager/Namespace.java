begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
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
name|apibinding
operator|.
name|OWLManager
import|;
end_import

begin_class
specifier|public
class|class
name|Namespace
block|{
specifier|static
name|ONManager
name|onManager
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUp
parameter_list|()
block|{
name|onManager
operator|=
name|ONManager
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
specifier|static
name|void
name|getNamespace
parameter_list|()
block|{ 		 	}
block|}
end_class

end_unit

