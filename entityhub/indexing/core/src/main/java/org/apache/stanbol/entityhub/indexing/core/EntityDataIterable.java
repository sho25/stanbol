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
name|indexing
operator|.
name|core
package|;
end_package

begin_comment
comment|/**  * Interface used to create an instance of an {@link EntityDataIterator}  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityDataIterable
extends|extends
name|IndexingComponent
block|{
comment|/**      * Returns an iterator over all Representations of the Entities.      * @return A new instance of an {@link EntityDataIterator}      */
name|EntityDataIterator
name|entityDataIterator
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

