begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|shared
operator|.
name|dependency
operator|.
name|hermit
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|AfterClass
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

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|CheckHermiTTest
block|{
specifier|public
name|CheckHermiTTest
parameter_list|()
block|{     }
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpClass
parameter_list|()
throws|throws
name|Exception
block|{     }
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|tearDownClass
parameter_list|()
throws|throws
name|Exception
block|{     }
annotation|@
name|Before
specifier|public
name|void
name|setUp
parameter_list|()
block|{     }
annotation|@
name|After
specifier|public
name|void
name|tearDown
parameter_list|()
block|{     }
comment|/**      * Test of getCkHermiT method, of class CheckHermiT.      */
annotation|@
name|Test
specifier|public
name|void
name|testGetCkHermiT
parameter_list|()
block|{
name|CheckHermiT
name|check
init|=
operator|new
name|CheckHermiT
argument_list|()
decl_stmt|;
if|if
condition|(
name|check
operator|.
name|getCkHermiT
argument_list|()
condition|)
block|{
name|assertEquals
argument_list|(
name|check
operator|.
name|getCkHermiT
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fail
argument_list|(
literal|"Problem with Hermit"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

