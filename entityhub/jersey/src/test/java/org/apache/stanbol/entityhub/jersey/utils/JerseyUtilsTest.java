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
name|utils
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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

begin_comment
comment|/**  * Tests added mainly in response to the bug reported by STANBOL-727  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|JerseyUtilsTest
block|{
specifier|private
specifier|static
class|class
name|TestMap
extends|extends
name|HashMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
extends|extends
name|Number
argument_list|>
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
block|}
comment|/**      * Tests some combination for Test      */
annotation|@
name|Test
specifier|public
name|void
name|testType
parameter_list|()
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|JerseyUtils
operator|.
name|testType
argument_list|(
name|Map
operator|.
name|class
argument_list|,
name|HashMap
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|JerseyUtils
operator|.
name|testType
argument_list|(
name|Map
operator|.
name|class
argument_list|,
name|HashSet
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|JerseyUtils
operator|.
name|testType
argument_list|(
name|Map
operator|.
name|class
argument_list|,
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
extends|extends
name|Number
argument_list|>
argument_list|>
name|genericMapTest
init|=
operator|new
name|TestMap
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|JerseyUtils
operator|.
name|testType
argument_list|(
name|Map
operator|.
name|class
argument_list|,
name|genericMapTest
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|JerseyUtils
operator|.
name|testType
argument_list|(
name|Set
operator|.
name|class
argument_list|,
name|genericMapTest
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//test a parsed Type
name|Assert
operator|.
name|assertTrue
argument_list|(
name|JerseyUtils
operator|.
name|testType
argument_list|(
name|Map
operator|.
name|class
argument_list|,
name|TestMap
operator|.
name|class
operator|.
name|getGenericSuperclass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testParameterisedType
parameter_list|()
block|{
comment|//NOTE: this can not check for Collection<String>!!
name|Assert
operator|.
name|assertTrue
argument_list|(
name|JerseyUtils
operator|.
name|testParameterizedType
argument_list|(
name|Map
operator|.
name|class
argument_list|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|,
name|Collection
operator|.
name|class
block|}
argument_list|,
name|TestMap
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

