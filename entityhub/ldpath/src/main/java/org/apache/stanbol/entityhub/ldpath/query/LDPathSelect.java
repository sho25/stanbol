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
name|ldpath
operator|.
name|query
package|;
end_package

begin_comment
comment|/**  * Interface that allows to get the LDPath program to select values for  * QueryResults.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|LDPathSelect
block|{
comment|/**      * The LDPath program used to select values for query results or      *<code>null</code> if none      * @return the LDPath program or<code>null</code> if none.      */
specifier|public
name|String
name|getLDPathSelect
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

