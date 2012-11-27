begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|it
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|NlpProcessingStressTest
extends|extends
name|MultiThreadedTestBase
block|{
specifier|public
specifier|static
specifier|final
name|String
name|PROPER_NOUN_LINKING_CHAIN
init|=
literal|"dbpedia-proper-noun"
decl_stmt|;
specifier|public
name|NlpProcessingStressTest
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testProperNounLinking
parameter_list|()
throws|throws
name|Exception
block|{
name|TestSettings
name|settings
init|=
operator|new
name|TestSettings
argument_list|()
decl_stmt|;
name|settings
operator|.
name|setChain
argument_list|(
name|PROPER_NOUN_LINKING_CHAIN
argument_list|)
expr_stmt|;
comment|//use the default for the rest of the tests
name|performTest
argument_list|(
name|settings
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

