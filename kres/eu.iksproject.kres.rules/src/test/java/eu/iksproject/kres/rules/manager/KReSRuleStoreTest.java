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
name|rules
operator|.
name|manager
package|;
end_package

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
name|rules
operator|.
name|RuleStore
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
name|java
operator|.
name|io
operator|.
name|File
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
name|OWLOntology
import|;
end_import

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
name|KReSRuleStoreTest
block|{
specifier|public
name|KReSRuleStoreTest
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
annotation|@
name|Test
specifier|public
name|void
name|testKReSRuleStore
parameter_list|()
block|{
name|RuleStore
name|store
init|=
operator|new
name|KReSRuleStore
argument_list|(
literal|"./src/main/resources/RuleOntology/TestKReSOntologyRules.owl"
argument_list|)
decl_stmt|;
name|OWLOntology
name|owlmodel
init|=
name|store
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|store
operator|.
name|getFilePath
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|owlmodel
operator|!=
literal|null
condition|)
block|{
name|OWLOntologyManager
name|owlmanager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
try|try
block|{
try|try
block|{
name|assertEquals
argument_list|(
name|owlmodel
argument_list|,
name|owlmanager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
operator|new
name|File
argument_list|(
literal|"./src/main/resources/RuleOntology/TestKReSOntologyRules.owl"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|assertEquals
argument_list|(
name|owlmodel
argument_list|,
name|owlmanager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
operator|new
name|File
argument_list|(
literal|"./src/main/resources/RuleOntology/OffLineKReSOntologyRules.owl"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|fail
argument_list|(
literal|"OWLOntologyCreationException catched"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// TODO review the generated test code and remove the default call to fail.
name|fail
argument_list|(
literal|"The test fail for KReSRuleStore"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testKReSRuleStore_2
parameter_list|()
block|{
name|RuleStore
name|store
init|=
operator|new
name|KReSRuleStore
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|OWLOntology
name|owlmodel
init|=
name|store
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"ECCOMI "
operator|+
name|store
operator|.
name|getFilePath
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|owlmodel
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|assertTrue
argument_list|(
operator|!
name|owlmodel
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// TODO review the generated test code and remove the default call to fail.
name|fail
argument_list|(
literal|"The test fail for KReSRuleStore"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

