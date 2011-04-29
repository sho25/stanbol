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
comment|/**  * Interface used be the {@link RdfIndexer} to check if an entity should be  * indexes or not.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityScoreProvider
extends|extends
name|IndexingComponent
block|{
comment|/**      * Indicates if the data of the Entity are needed for calculating/providing      * the score for Entities. If an implementation returns<code>true</code>       * {@link #process(Representation)} will be called. If<code>false</code>      * is returned the {@link #process(String)} is called.<p>      * Implementors should consider that supporting the String variant will      * improve indexing speed because there is no need to get the actual      * Representation for Entities that need not to be indexed.      * @return if the indexer needs to parse the {@link Representation} for the      * entity.      */
name|boolean
name|needsData
parameter_list|()
function_decl|;
comment|/**      * Method called to evaluate an entity in case {@link #needsData()} returns      *<code>false</code>      * @param id the ID of the entity      * @return The score or<code>null</code> if no score is available for the      * parsed Entity. Values<code>&lt; 0</code> indicate that the entity should      * not be indexed.      * @throws UnsupportedOperationException if<code>{@link #needsData()}</code>      * returns<code>true</code>      */
name|Float
name|process
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|UnsupportedOperationException
function_decl|;
comment|/**      * Method called to evaluate an entity in case {@link #needsData()} returns      *<code>true</code>      * @param entity the {@link Representation} of the entity      * @return The score or<code>null</code> if no score is available for the      * parsed Entity. Values<code>&lt; 0</code> indicate that the entity should      * not be indexed.      * @throws UnsupportedOperationException if<code>{@link #needsData()}</code>      * returns<code>false</code>      */
name|Float
name|process
parameter_list|(
name|Representation
name|entity
parameter_list|)
throws|throws
name|UnsupportedOperationException
function_decl|;
block|}
end_interface

end_unit

