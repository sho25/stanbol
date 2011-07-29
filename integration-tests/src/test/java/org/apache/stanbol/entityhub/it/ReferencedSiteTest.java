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
name|it
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|it
operator|.
name|DbpediaDefaultdataConstants
operator|.
name|DBPEDIA_DEFAULTDATA_OPTIONAL_FIELDS
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|it
operator|.
name|DbpediaDefaultdataConstants
operator|.
name|DBPEDIA_DEFAULTDATA_REQUIRED_FIELDS
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|it
operator|.
name|DbpediaDefaultdataConstants
operator|.
name|DBPEDIA_SITE_ID
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|it
operator|.
name|DbpediaDefaultdataConstants
operator|.
name|DBPEDIA_SITE_PATH
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|test
operator|.
name|it
operator|.
name|AssertEntityhubJson
operator|.
name|assertEntity
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|test
operator|.
name|it
operator|.
name|AssertEntityhubJson
operator|.
name|assertRepresentation
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
name|RequestExecutor
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
name|it
operator|.
name|query
operator|.
name|DbpediaQueryTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONObject
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
comment|/**  * Tests of the ReferencedSite RESTful service of the Entityhub based on the  * DBpedia.org default data set.<p>  * Note that the tests for the query interfaces are defined by   * {@link DbpediaQueryTest} because they are shared with  * {@link SitesManagerTest}.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ReferencedSiteTest
extends|extends
name|DbpediaQueryTest
block|{
comment|/**      * Executes the {@link DbpediaQueryTest} on the 'dbpedia' referenced      * site (assuming the default dataset      */
specifier|public
name|ReferencedSiteTest
parameter_list|()
block|{
name|super
argument_list|(
name|DBPEDIA_SITE_PATH
argument_list|,
name|DBPEDIA_SITE_ID
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests retrieval of Entities      * @throws IOException      * @throws JSONException      */
annotation|@
name|Test
specifier|public
name|void
name|testRetrievel
parameter_list|()
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|String
name|id
init|=
literal|"http://dbpedia.org/resource/Paris"
decl_stmt|;
name|RequestExecutor
name|re
init|=
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
name|DBPEDIA_SITE_PATH
operator|+
literal|"/entity"
argument_list|,
literal|"id"
argument_list|,
name|id
argument_list|)
operator|.
name|withHeader
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
argument_list|)
decl_stmt|;
name|re
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|JSONObject
name|jEntity
init|=
name|assertEntity
argument_list|(
name|re
operator|.
name|getContent
argument_list|()
argument_list|,
name|id
argument_list|,
name|DBPEDIA_SITE_ID
argument_list|)
decl_stmt|;
name|assertRepresentation
argument_list|(
name|jEntity
operator|.
name|getJSONObject
argument_list|(
literal|"representation"
argument_list|)
argument_list|,
name|DBPEDIA_DEFAULTDATA_REQUIRED_FIELDS
argument_list|,
name|DBPEDIA_DEFAULTDATA_OPTIONAL_FIELDS
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

