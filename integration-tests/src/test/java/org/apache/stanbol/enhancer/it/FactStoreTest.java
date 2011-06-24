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
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|FileUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpGet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpPut
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
name|commons
operator|.
name|testing
operator|.
name|http
operator|.
name|Request
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
name|commons
operator|.
name|testing
operator|.
name|stanbol
operator|.
name|StanbolTestBase
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

begin_class
specifier|public
class|class
name|FactStoreTest
extends|extends
name|StanbolTestBase
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FactStoreTest
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|cleanDatabase
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|workingDirName
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"jar.executor.workingdirectory"
argument_list|)
decl_stmt|;
if|if
condition|(
name|workingDirName
operator|!=
literal|null
condition|)
block|{
name|File
name|workingDir
init|=
operator|new
name|File
argument_list|(
name|workingDirName
argument_list|)
decl_stmt|;
name|File
name|factstore
init|=
operator|new
name|File
argument_list|(
name|workingDir
argument_list|,
literal|"factstore"
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Deleting integration test FactStore "
operator|+
name|factstore
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
name|FileUtils
operator|.
name|deleteDirectory
argument_list|(
name|factstore
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|maximumSchemaURNLength
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.test.de/this/urn/is/a/bit/too/long/to/be/used/in/this/fact/store/implementation/with/derby"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http:\\/\\/iks-project.eu\\/ont\\/\",\"#types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|400
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|createSimpleFactSchema
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/TestFactSchema"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http:\\/\\/iks-project.eu\\/ont\\/\",\"#types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|createURNFactSchema
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.iks-project.eu/ont/test"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http:\\/\\/iks-project.eu\\/ont\\/\",\"#types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|getFactSchemaByURN
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.iks-project.eu/ont/test2"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http:\\/\\/iks-project.eu\\/ont\\/\",\"#types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpGet
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.iks-project.eu/ont/test2"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|String
name|actual
init|=
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"#types\":{\"organization\":\"http:\\/\\/iks-project.eu\\/ont\\/organization\",\"person\":\"http:\\/\\/iks-project.eu\\/ont\\/person\"}}}"
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|doubleCreateFactSchema
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.iks-project.eu/ont/double"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"iks\":\"http:\\/\\/iks-project.eu\\/ont\\/\",\"#types\":{\"organization\":\"iks:organization\",\"person\":\"iks:person\"}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|409
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|createSchemaMultiTypes
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.schema.org/attendees"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"#types\":{\"organization\":\"http:\\/\\/iks-project.eu\\/ont\\/organization\",\"person\":[\"http:\\/\\/iks-project.eu\\/ont\\/person\",\"http:\\/\\/www.schema.org\\/Person\"]}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|getSchemaMultiTypes
parameter_list|()
throws|throws
name|Exception
block|{
name|Request
name|r1
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpPut
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.schema.org/Event.attendees"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withContent
argument_list|(
literal|"{\"@context\":{\"#types\":{\"organization\":\"http:\\/\\/iks-project.eu\\/ont\\/organization\",\"person\":[\"http:\\/\\/iks-project.eu\\/ont\\/person\",\"http:\\/\\/www.schema.org\\/Person\"]}}}"
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|r1
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|201
argument_list|)
expr_stmt|;
name|Request
name|r2
init|=
name|builder
operator|.
name|buildOtherRequest
argument_list|(
operator|new
name|HttpGet
argument_list|(
name|builder
operator|.
name|buildUrl
argument_list|(
literal|"/factstore/facts/"
operator|+
name|encodeURI
argument_list|(
literal|"http://www.schema.org/Event.attendees"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
decl_stmt|;
name|String
name|actual
init|=
name|executor
operator|.
name|execute
argument_list|(
name|r2
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
literal|"{\"@context\":{\"#types\":{\"organization\":\"http:\\/\\/iks-project.eu\\/ont\\/organization\",\"person\":[\"http:\\/\\/iks-project.eu\\/ont\\/person\",\"http:\\/\\/www.schema.org\\/Person\"]}}}"
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|encodeURI
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|StringBuilder
name|o
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|char
name|ch
range|:
name|s
operator|.
name|toCharArray
argument_list|()
control|)
block|{
if|if
condition|(
name|isUnsafe
argument_list|(
name|ch
argument_list|)
condition|)
block|{
name|o
operator|.
name|append
argument_list|(
literal|'%'
argument_list|)
expr_stmt|;
name|o
operator|.
name|append
argument_list|(
name|toHex
argument_list|(
name|ch
operator|/
literal|16
argument_list|)
argument_list|)
expr_stmt|;
name|o
operator|.
name|append
argument_list|(
name|toHex
argument_list|(
name|ch
operator|%
literal|16
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
name|o
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
block|}
return|return
name|o
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|char
name|toHex
parameter_list|(
name|int
name|ch
parameter_list|)
block|{
return|return
call|(
name|char
call|)
argument_list|(
name|ch
operator|<
literal|10
condition|?
literal|'0'
operator|+
name|ch
else|:
literal|'A'
operator|+
name|ch
operator|-
literal|10
argument_list|)
return|;
block|}
specifier|private
name|boolean
name|isUnsafe
parameter_list|(
name|char
name|ch
parameter_list|)
block|{
if|if
condition|(
name|ch
operator|>
literal|128
operator|||
name|ch
operator|<
literal|0
condition|)
return|return
literal|true
return|;
return|return
literal|" %$&+,/:;=?@<>#%"
operator|.
name|indexOf
argument_list|(
name|ch
argument_list|)
operator|>=
literal|0
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
name|void
name|toConsole
parameter_list|(
name|String
name|actual
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|actual
argument_list|)
expr_stmt|;
name|String
name|s
init|=
name|actual
decl_stmt|;
name|s
operator|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"\\\\\\\\"
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\""
argument_list|,
literal|"\\\""
argument_list|)
expr_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"\\n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

