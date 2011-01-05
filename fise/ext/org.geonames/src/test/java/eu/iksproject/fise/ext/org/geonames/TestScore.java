begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|ext
operator|.
name|org
operator|.
name|geonames
package|;
end_package

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
name|SocketTimeoutException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|UnknownHostException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|geonames
operator|.
name|Style
import|;
end_import

begin_import
import|import
name|org
operator|.
name|geonames
operator|.
name|Toponym
import|;
end_import

begin_import
import|import
name|org
operator|.
name|geonames
operator|.
name|ToponymSearchCriteria
import|;
end_import

begin_import
import|import
name|org
operator|.
name|geonames
operator|.
name|ToponymSearchResult
import|;
end_import

begin_import
import|import
name|org
operator|.
name|geonames
operator|.
name|WebService
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
name|assertEquals
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
name|assertNotNull
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
name|assertTrue
import|;
end_import

begin_comment
comment|/**  * This test only correct values for score (set/getScore). An extension to the  * web service client for geonames.org implemented to be able to write  * fise:confidence values for fise:EntityAnnotations.  *   * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|TestScore
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
name|TestScore
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Test
specifier|public
name|void
name|testSearch
parameter_list|()
throws|throws
name|Exception
block|{
name|ToponymSearchCriteria
name|searchCriteria
init|=
operator|new
name|ToponymSearchCriteria
argument_list|()
decl_stmt|;
name|searchCriteria
operator|.
name|setName
argument_list|(
literal|"Zealand"
argument_list|)
expr_stmt|;
name|searchCriteria
operator|.
name|setStyle
argument_list|(
name|Style
operator|.
name|FULL
argument_list|)
expr_stmt|;
name|searchCriteria
operator|.
name|setMaxRows
argument_list|(
literal|5
argument_list|)
expr_stmt|;
try|try
block|{
name|ToponymSearchResult
name|searchResult
init|=
name|WebService
operator|.
name|search
argument_list|(
name|searchCriteria
argument_list|)
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Toponym
name|toponym
range|:
name|searchResult
operator|.
name|getToponyms
argument_list|()
control|)
block|{
name|i
operator|++
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Result "
operator|+
name|i
operator|+
literal|" "
operator|+
name|toponym
operator|.
name|getGeoNameId
argument_list|()
operator|+
literal|" score= "
operator|+
name|toponym
operator|.
name|getScore
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|toponym
operator|.
name|getScore
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|toponym
operator|.
name|getScore
argument_list|()
operator|>=
name|Double
operator|.
name|valueOf
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|toponym
operator|.
name|getScore
argument_list|()
operator|<=
name|Double
operator|.
name|valueOf
argument_list|(
literal|100
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|instanceof
name|UnknownHostException
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to test LocationEnhancemetEngine when offline! -> skipping this test"
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|instanceof
name|SocketTimeoutException
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Seams like the geonames.org webservice is currently unavailable -> skipping this test"
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getMessage
argument_list|()
operator|.
name|contains
argument_list|(
literal|"overloaded with requests"
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Seams like the geonames.org webservice is currently unavailable -> skipping this test"
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
name|e
throw|;
block|}
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testHierarchy
parameter_list|()
throws|throws
name|Exception
block|{
name|ToponymSearchCriteria
name|searchCriteria
init|=
operator|new
name|ToponymSearchCriteria
argument_list|()
decl_stmt|;
name|searchCriteria
operator|.
name|setName
argument_list|(
literal|"New York"
argument_list|)
expr_stmt|;
name|searchCriteria
operator|.
name|setStyle
argument_list|(
name|Style
operator|.
name|FULL
argument_list|)
expr_stmt|;
name|searchCriteria
operator|.
name|setMaxRows
argument_list|(
literal|1
argument_list|)
expr_stmt|;
try|try
block|{
name|ToponymSearchResult
name|searchResult
init|=
name|WebService
operator|.
name|search
argument_list|(
name|searchCriteria
argument_list|)
decl_stmt|;
name|int
name|testGeonamesId
init|=
name|searchResult
operator|.
name|getToponyms
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|.
name|getGeoNameId
argument_list|()
decl_stmt|;
for|for
control|(
name|Toponym
name|hierarchy
range|:
name|WebService
operator|.
name|hierarchy
argument_list|(
name|testGeonamesId
argument_list|,
literal|null
argument_list|,
name|Style
operator|.
name|FULL
argument_list|)
control|)
block|{
comment|// this service does not provide an score, so test if 1.0 is
comment|// returned
name|assertNotNull
argument_list|(
name|hierarchy
operator|.
name|getScore
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|hierarchy
operator|.
name|getScore
argument_list|()
argument_list|,
name|Double
operator|.
name|valueOf
argument_list|(
literal|1.0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|instanceof
name|UnknownHostException
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to test LocationEnhancemetEngine when offline! -> skipping this test"
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|instanceof
name|SocketTimeoutException
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Seams like the geonames.org webservice is currently unavailable -> skipping this test"
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getMessage
argument_list|()
operator|.
name|contains
argument_list|(
literal|"overloaded with requests"
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Seams like the geonames.org webservice is currently unavailable -> skipping this test"
argument_list|,
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
name|e
throw|;
block|}
block|}
block|}
block|}
end_class

end_unit

