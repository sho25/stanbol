begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
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
name|java
operator|.
name|net
operator|.
name|URI
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
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|junit
operator|.
name|annotations
operator|.
name|TestReference
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
name|contenthub
operator|.
name|crawler
operator|.
name|cnn
operator|.
name|CNNImporter
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

begin_class
specifier|public
class|class
name|CNNTest
block|{
annotation|@
name|TestReference
specifier|private
name|CNNImporter
name|cNNImporter
decl_stmt|;
annotation|@
name|Test
specifier|public
name|void
name|testImportCNNNews
parameter_list|()
block|{
comment|//		Map<URI, String> res = cNNImporter.importCNNNews("paris", 1, false);
comment|//		assertNotNull("abc"+res.values(),res);
block|}
block|}
end_class

end_unit

