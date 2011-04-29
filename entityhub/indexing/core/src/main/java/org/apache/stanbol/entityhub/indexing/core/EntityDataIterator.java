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
operator|.
name|Representation
import|;
end_import

begin_comment
comment|/**  * Interface used to iterate over all entities.  * By calling {@link #next()} one can iterate over the IDs of the Entities.  * The data ({@link Representation}) of the current entity are available by  * calling {@link #getRepresentation()}.<p>  * This interface is intended for data source that prefer to read entity  * information as a stream (e.g. from an tab separated text file) and therefore  * can not provide an implementation of the {@link EntityDataProvider} interface.  * @see EntityDataProvider   * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityDataIterator
extends|extends
name|Iterator
argument_list|<
name|String
argument_list|>
block|{
comment|/**      * Getter for the Representation of the current active Element. This is the      * Representation of the Entity with the ID returned for the previous      * call to {@link #next()}. This method does not change the current element      * of the iteration.      * @return the Representation for the entity with the ID returned by       * {@link #next()}      */
name|Representation
name|getRepresentation
parameter_list|()
function_decl|;
comment|/**      * Close the iteration.      */
name|void
name|close
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

