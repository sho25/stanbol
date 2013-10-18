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
name|model
operator|.
name|sesame
package|;
end_package

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Value
import|;
end_import

begin_comment
comment|/**  * Interface that allows access to the wrapped Sesame {@link Value}  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|RdfWrapper
block|{
comment|/**      * Getter for the wrapped Sesame {@link Value}      * @return the value      */
name|Value
name|getValue
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

