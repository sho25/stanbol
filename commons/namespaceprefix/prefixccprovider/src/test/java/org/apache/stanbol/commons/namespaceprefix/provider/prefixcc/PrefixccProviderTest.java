begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|namespaceprefix
operator|.
name|provider
operator|.
name|prefixcc
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
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|TimeUnit
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
name|namespaceprefix
operator|.
name|NamespacePrefixService
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
name|namespaceprefix
operator|.
name|service
operator|.
name|StanbolNamespacePrefixService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
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

begin_class
specifier|public
class|class
name|PrefixccProviderTest
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
name|PrefixccProviderTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|foaf_ns
init|=
literal|"http://xmlns.com/foaf/0.1/"
decl_stmt|;
specifier|private
specifier|static
name|String
name|foaf_pf
init|=
literal|"foaf"
decl_stmt|;
annotation|@
name|Test
specifier|public
name|void
name|test
parameter_list|()
block|{
name|Date
name|date
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|PrefixccProvider
name|pcp
init|=
operator|new
name|PrefixccProvider
argument_list|(
literal|10
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|pcp
operator|.
name|getPrefix
argument_list|(
name|foaf_ns
argument_list|)
argument_list|)
expr_stmt|;
comment|//async loading
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
literal|5
operator|&&
operator|!
name|pcp
operator|.
name|isAvailable
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
literal|1000
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{}
block|}
if|if
condition|(
operator|!
name|pcp
operator|.
name|isAvailable
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to obtain prefix.cc data after 5sec .. skipping further tests"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|//assertMappings
name|Assert
operator|.
name|assertEquals
argument_list|(
name|foaf_pf
argument_list|,
name|pcp
operator|.
name|getPrefix
argument_list|(
name|foaf_ns
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|foaf_ns
argument_list|,
name|pcp
operator|.
name|getNamespace
argument_list|(
name|foaf_pf
argument_list|)
argument_list|)
expr_stmt|;
comment|//assert cache time stamp
name|Date
name|cacheDate
init|=
name|pcp
operator|.
name|getCacheTimeStamp
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|date
operator|.
name|compareTo
argument_list|(
name|cacheDate
argument_list|)
operator|==
operator|-
literal|1
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
operator|new
name|Date
argument_list|()
operator|.
name|compareTo
argument_list|(
name|cacheDate
argument_list|)
operator|>=
literal|0
argument_list|)
expr_stmt|;
comment|//assert close
name|pcp
operator|.
name|close
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|pcp
operator|.
name|isAvailable
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|pcp
operator|.
name|getCacheTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testServiceLoader
parameter_list|()
throws|throws
name|IOException
block|{
comment|//this test works only if online
try|try
block|{
name|PrefixccProvider
operator|.
name|GET_ALL
operator|.
name|openStream
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Unable to connect to http://prefix.cc ... deactivating "
operator|+
name|PrefixccProvider
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"ServiceLoader support test"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|//this test for now does not use predefined mappings
name|URL
name|mappingURL
init|=
name|PrefixccProviderTest
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"testnamespaceprefix.mappings"
argument_list|)
decl_stmt|;
comment|//Assert.assertNotNull(mappingURL);
name|File
name|mappingFile
decl_stmt|;
if|if
condition|(
name|mappingURL
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|mappingFile
operator|=
operator|new
name|File
argument_list|(
name|mappingURL
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|mappingFile
operator|=
operator|new
name|File
argument_list|(
name|mappingURL
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//Assert.assertTrue(mappingFile.isFile());
block|}
else|else
block|{
name|mappingFile
operator|=
operator|new
name|File
argument_list|(
literal|"testnamespaceprefix.mappings"
argument_list|)
expr_stmt|;
block|}
name|NamespacePrefixService
name|service
init|=
operator|new
name|StanbolNamespacePrefixService
argument_list|(
name|mappingFile
argument_list|)
decl_stmt|;
comment|//assertMappings
name|Assert
operator|.
name|assertEquals
argument_list|(
name|foaf_pf
argument_list|,
name|service
operator|.
name|getPrefix
argument_list|(
name|foaf_ns
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|foaf_ns
argument_list|,
name|service
operator|.
name|getNamespace
argument_list|(
name|foaf_pf
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
