begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *   */
end_comment

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
name|test
operator|.
name|query
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
name|HashSet
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
name|JSONArray
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

begin_class
specifier|public
class|class
name|FieldQueryTestCase
extends|extends
name|QueryTestCase
block|{
specifier|private
name|String
name|query
decl_stmt|;
comment|/**      * Typically used to test invalid formatted queries to return an      * status other than 200      * @param query the query      * @param expectedStatus the expected status      */
specifier|public
name|FieldQueryTestCase
parameter_list|(
name|String
name|query
parameter_list|,
name|int
name|expectedStatus
parameter_list|)
block|{
name|this
argument_list|(
name|query
argument_list|,
name|expectedStatus
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a FieldQueryTest for the parsed field query and in addition says      * if the query is expected to provide results or not. This is typically used      * to test queries that do not have results or that do expect results but      * one does not want/need to check the ids of the returned entities.<p>      * NOTE:<ul>      *<li> This assumes that the server needs to return "200 ok" on the query request.      *<li> The constructor parses the parsed query to get the selected fields.      * This is used during test execution to check if the returned results do      * only contain fields selected by the query.      *</ul>      * @param query the query      * @param expectesResults If results are expected or not      */
specifier|public
name|FieldQueryTestCase
parameter_list|(
name|String
name|query
parameter_list|,
name|boolean
name|expectesResults
parameter_list|)
block|{
name|this
argument_list|(
name|query
argument_list|,
name|expectesResults
condition|?
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a FieldQueryTest for the parsed field query that tests the results       * of the query for the expected results      * NOTE:<ul>      *<li> This assumes that the server needs to return "200 ok" on the query request.      *<li> The constructor parses the parsed query to get the selected fields.      * This is used during test execution to check if the returned results do      * only contain fields selected by the query.      *</ul>      * @param query the query      * @param expectedResultIds Entities that MUST BE in the returned results.      * However there might be additional results that are not in this collection      */
specifier|public
name|FieldQueryTestCase
parameter_list|(
name|String
name|query
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|expectedResultIds
parameter_list|)
block|{
name|this
argument_list|(
name|query
argument_list|,
name|expectedResultIds
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a FieldQueryTest for the parsed field query that tests the results       * of the query for the expected results and each result to provide       * information for the required fields.<p>      * NOTE:<ul>      *<li> This assumes that the server needs to return "200 ok" on the query request.      *<li> The constructor parses the parsed query to get the selected fields.      * This is used during test execution to check if the returned results do      * only contain fields selected by the query.      *</ul>      * @param query the query      * @param expectedResultIds Entities that MUST BE in the returned results.      * However there might be additional results that are not in this collection      * @param requiredFields Fields the MUST BE present for each result of the      * query.      */
specifier|public
name|FieldQueryTestCase
parameter_list|(
name|String
name|query
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|expectedResultIds
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|requiredFields
parameter_list|)
block|{
name|this
argument_list|(
name|query
argument_list|,
literal|200
argument_list|,
name|expectedResultIds
argument_list|,
name|requiredFields
argument_list|)
expr_stmt|;
block|}
specifier|private
name|FieldQueryTestCase
parameter_list|(
name|String
name|query
parameter_list|,
name|int
name|expectedStatus
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|expectedResultIds
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|requiredFields
parameter_list|)
block|{
name|super
argument_list|(
name|expectedStatus
argument_list|,
name|expectedResultIds
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|//TODO: add support for prohibitedResultIds
if|if
condition|(
name|query
operator|==
literal|null
operator|||
name|query
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The parsed Query MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
if|if
condition|(
name|expectsSuccess
argument_list|()
condition|)
block|{
comment|//parse the selected fields from the query and add them to the allowed fields
try|try
block|{
name|JSONObject
name|jQuery
init|=
operator|new
name|JSONObject
argument_list|(
name|query
argument_list|)
decl_stmt|;
comment|//reset the parsed query for better debugging
name|this
operator|.
name|query
operator|=
name|jQuery
operator|.
name|toString
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|JSONArray
name|selected
init|=
name|jQuery
operator|.
name|optJSONArray
argument_list|(
literal|"selected"
argument_list|)
decl_stmt|;
if|if
condition|(
name|selected
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|selected
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|addAllowedField
argument_list|(
name|selected
operator|.
name|getString
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Tests that expect Results MUST parse a valid FieldQuery!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
comment|//now set the required fields
name|setRequiredFields
argument_list|(
name|requiredFields
argument_list|)
expr_stmt|;
block|}
comment|// else do not parse the query, because it might be invalid JSON
name|setHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getContent
parameter_list|()
block|{
return|return
name|query
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getServicePath
parameter_list|()
block|{
return|return
literal|"/query"
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|FieldQueryTestCase
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|":\n"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" field query:\n"
argument_list|)
operator|.
name|append
argument_list|(
name|query
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|super
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

