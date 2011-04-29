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
name|Map
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
name|indexing
operator|.
name|core
operator|.
name|impl
operator|.
name|IndexerImpl
import|;
end_import

begin_comment
comment|/**  * Parent Interface defining the indexing work flow methods for all kinds of  * data sources used for the Indexer.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|IndexingComponent
block|{
comment|/**      * Setter for the configuration      * @param config the configuration      */
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
function_decl|;
comment|/**      * Used by the {@link IndexerImpl} to check if this source needs to be      * {@link #initialise()}.      * @return If<code>true</code> is returned the {@link IndexerImpl} will call      * {@link #initialise()} during the initialisation phase of the indexing      * process.      */
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
function_decl|;
comment|/**      * Initialise the IndexingSource. This should be used to perform       * time consuming initialisations.      */
specifier|public
name|void
name|initialise
parameter_list|()
function_decl|;
comment|/**      * Called by the {@link IndexerImpl} as soon as this source is no longer needed      * for the indexing process.      */
specifier|public
name|void
name|close
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

