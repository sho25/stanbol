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
name|query
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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

begin_interface
specifier|public
interface|interface
name|QueryResultList
parameter_list|<
name|T
parameter_list|>
extends|extends
name|Iterable
argument_list|<
name|T
argument_list|>
block|{
comment|/**      * Getter for the query of this result set.      * TODO: Change return Value to {@link Query}      * @return the query used to create this result set      */
name|FieldQuery
name|getQuery
parameter_list|()
function_decl|;
comment|/**      * The selected fields of this query      * @return      */
name|Set
argument_list|<
name|String
argument_list|>
name|getSelectedFields
parameter_list|()
function_decl|;
comment|/**      * Iterator over the results of this query      */
name|Iterator
argument_list|<
name|T
argument_list|>
name|iterator
parameter_list|()
function_decl|;
comment|/**      *<code>true</code> if the result set is empty      * @return<code>true</code> if the result set is empty. Otherwise<code>false</code>      */
name|boolean
name|isEmpty
parameter_list|()
function_decl|;
comment|/**      * The size of this result set      * @return      */
name|int
name|size
parameter_list|()
function_decl|;
comment|/**      * The type of the results in the list      * @return the type      */
name|Class
argument_list|<
name|T
argument_list|>
name|getType
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

