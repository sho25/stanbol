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
name|Dictionary
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
name|yard
operator|.
name|Yard
import|;
end_import

begin_comment
comment|/**  * Interface that defines the target for indexing.   * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|IndexingDestination
extends|extends
name|IndexingComponent
block|{
comment|/**      * Getter for the Yard to store the indexed Entities      * @return the yard      */
name|Yard
name|getYard
parameter_list|()
function_decl|;
comment|/**      * Called after the indexing is completed to allow some post processing and      * packaging the stored data and writing of the OSGI configuration used to      * initialise the Yard.      */
name|void
name|finalise
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

