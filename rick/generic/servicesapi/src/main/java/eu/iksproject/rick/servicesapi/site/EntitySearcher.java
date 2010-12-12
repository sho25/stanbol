begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|site
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
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQuery
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|QueryResultList
import|;
end_import

begin_comment
comment|/**  * Interface used to provide service/technology specific implementation of the  * search interface provided by {@link ReferencedSite}.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntitySearcher
block|{
comment|/**      * The key used to define the baseUri of the query service used for the      * implementation of this interface.<br>      * This constants actually uses the value of {@link ConfiguredSite#QUERY_URI}      */
name|String
name|QUERY_URI
init|=
name|ConfiguredSite
operator|.
name|QUERY_URI
decl_stmt|;
comment|/**      * Searches for Entities based on the parsed {@link FieldQuery}      * @param query the query      * @return the result of the query      */
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findEntities
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Searches for Entities based on the parsed {@link FieldQuery} and returns      * for each entity an Representation over the selected fields and values      * @param query the query      * @return the found entities as representation containing only the selected      * fields and there values.      */
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

