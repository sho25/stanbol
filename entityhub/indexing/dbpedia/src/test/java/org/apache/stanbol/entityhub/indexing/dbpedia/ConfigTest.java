begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|dbpedia
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityIterator
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|IndexerFactory
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityIterator
operator|.
name|EntityScore
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|config
operator|.
name|IndexingConfig
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|normaliser
operator|.
name|ScoreNormaliser
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
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

begin_class
specifier|public
class|class
name|ConfigTest
block|{
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ConfigTest
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * mvn copies the resources in "src/test/resources" to target/test-classes.      */
specifier|private
specifier|static
specifier|final
name|String
name|TEST_ROOT
init|=
literal|"/target/test-classes"
decl_stmt|;
specifier|private
specifier|static
name|String
name|userDir
decl_stmt|;
specifier|private
specifier|static
name|String
name|testRoot
decl_stmt|;
comment|/**      * The methods resets the "user.dir" system property      */
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|initTestRootFolder
parameter_list|()
block|{
name|String
name|baseDir
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"basedir"
argument_list|)
decl_stmt|;
if|if
condition|(
name|baseDir
operator|==
literal|null
condition|)
block|{
name|baseDir
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.dir"
argument_list|)
expr_stmt|;
block|}
comment|//store the current user.dir
name|userDir
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.dir"
argument_list|)
expr_stmt|;
name|testRoot
operator|=
name|baseDir
operator|+
name|TEST_ROOT
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"ConfigTest Root : "
operator|+
name|testRoot
argument_list|)
expr_stmt|;
comment|//set the user.dir to the testRoot (needed to test loading of missing
comment|//configurations via classpath
comment|//store the current user.dir and reset it after the tests
name|System
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|testRoot
argument_list|)
expr_stmt|;
block|}
comment|/**      * resets the "user.dir" system property the the original value      */
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|cleanup
parameter_list|()
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|userDir
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEntityIdIteratorConfig
parameter_list|()
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|()
decl_stmt|;
name|EntityIterator
name|iterator
init|=
name|config
operator|.
name|getEntityIdIterator
argument_list|()
decl_stmt|;
name|ScoreNormaliser
name|normaliser
init|=
name|config
operator|.
name|getNormaliser
argument_list|()
decl_stmt|;
if|if
condition|(
name|iterator
operator|.
name|needsInitialisation
argument_list|()
condition|)
block|{
name|iterator
operator|.
name|initialise
argument_list|()
expr_stmt|;
block|}
name|float
name|lastScore
init|=
name|Float
operator|.
name|MAX_VALUE
decl_stmt|;
name|float
name|lastNormalisedScore
init|=
literal|1f
decl_stmt|;
while|while
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|EntityScore
name|entity
init|=
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|entity
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|entity
operator|.
name|id
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|entity
operator|.
name|score
argument_list|)
expr_stmt|;
comment|//log.info("Entity: {}",entity);
name|assertTrue
argument_list|(
name|entity
operator|.
name|id
operator|.
name|startsWith
argument_list|(
literal|"http://dbpedia.org/resource/"
argument_list|)
argument_list|)
expr_stmt|;
name|float
name|score
init|=
name|entity
operator|.
name|score
operator|.
name|floatValue
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|score
operator|>
literal|0
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|score
operator|<=
name|lastScore
argument_list|)
expr_stmt|;
name|lastScore
operator|=
name|score
expr_stmt|;
name|Float
name|normalisedScore
init|=
name|normaliser
operator|.
name|normalise
argument_list|(
name|entity
operator|.
name|score
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|normalisedScore
argument_list|)
expr_stmt|;
name|float
name|nScore
init|=
name|normalisedScore
operator|.
name|floatValue
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|nScore
operator|<=
name|lastNormalisedScore
argument_list|)
expr_stmt|;
if|if
condition|(
name|score
operator|<
literal|2
condition|)
block|{
comment|//the value of "min-score" in minincoming
name|log
operator|.
name|info
argument_list|(
literal|"score="
operator|+
name|score
operator|+
literal|" nScore="
operator|+
name|nScore
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|nScore
operator|<
literal|0
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
name|assertTrue
argument_list|(
name|nScore
operator|>
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

