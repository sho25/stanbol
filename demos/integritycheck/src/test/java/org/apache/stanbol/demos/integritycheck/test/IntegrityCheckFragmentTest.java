begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|demos
operator|.
name|integritycheck
operator|.
name|test
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|demos
operator|.
name|integritycheck
operator|.
name|IntegrityCheckFragment
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
name|Test
import|;
end_import

begin_comment
comment|/**  * @author enridaga  *  */
end_comment

begin_class
specifier|public
class|class
name|IntegrityCheckFragmentTest
block|{
comment|/** 	 * @throws java.lang.Exception 	 */
annotation|@
name|Before
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{ 	}
comment|/** 	 * TODO This is a stub test. Useful tests for WebFragments/Templates? 	 * Test method for {@link org.apache.stanbol.demos.integritycheck.IntegrityCheckFragment#getName()}. 	 */
annotation|@
name|Test
specifier|public
specifier|final
name|void
name|testGetName
parameter_list|()
block|{
name|IntegrityCheckFragment
name|fragment
init|=
operator|new
name|IntegrityCheckFragment
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|fragment
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"integritycheck"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

