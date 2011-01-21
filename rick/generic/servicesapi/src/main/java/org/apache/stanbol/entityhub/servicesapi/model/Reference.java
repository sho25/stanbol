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
name|servicesapi
operator|.
name|model
package|;
end_package

begin_comment
comment|/**  * Defines a reference to an other entity<p>  * Implementations of that interface MUST BE immutable  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Reference
block|{
comment|/**      * Getter for the reference (not<code>null</code>)      * @return the reference      */
name|String
name|getReference
parameter_list|()
function_decl|;
comment|/**      * The lexical representation of the reference (usually the same value      * as returned by {@link #getReference()}      * @return the reference      */
name|String
name|toString
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

