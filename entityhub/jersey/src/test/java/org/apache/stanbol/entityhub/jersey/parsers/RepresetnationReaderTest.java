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
name|jersey
operator|.
name|parsers
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|ext
operator|.
name|RuntimeDelegate
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
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|representation
operator|.
name|Form
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|server
operator|.
name|impl
operator|.
name|provider
operator|.
name|RuntimeDelegateImpl
import|;
end_import

begin_class
specifier|public
class|class
name|RepresetnationReaderTest
block|{
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RepresetnationReaderTest
operator|.
name|class
argument_list|)
decl_stmt|;
name|RepresentationReader
name|reader
init|=
operator|new
name|RepresentationReader
argument_list|()
decl_stmt|;
comment|/**      * Tests the bug reported by STANBOL-727      */
annotation|@
name|Test
specifier|public
name|void
name|testIsReadable
parameter_list|()
block|{
name|RuntimeDelegate
operator|.
name|setInstance
argument_list|(
operator|new
name|RuntimeDelegateImpl
argument_list|()
argument_list|)
expr_stmt|;
comment|//NOTE the use of com.sun.* API for unit testing
name|Class
argument_list|<
name|Form
argument_list|>
name|formClass
init|=
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|representation
operator|.
name|Form
operator|.
name|class
decl_stmt|;
name|boolean
name|state
init|=
name|reader
operator|.
name|isReadable
argument_list|(
name|formClass
argument_list|,
name|formClass
argument_list|,
literal|null
argument_list|,
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED_TYPE
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|state
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

