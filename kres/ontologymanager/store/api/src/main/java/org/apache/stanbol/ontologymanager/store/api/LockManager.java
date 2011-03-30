begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|store
operator|.
name|api
package|;
end_package

begin_comment
comment|/**  * Single writer, multiple readers lock implementation for persistence store  *   * @author Cihan  */
end_comment

begin_interface
specifier|public
interface|interface
name|LockManager
block|{
specifier|public
specifier|static
name|String
name|GLOBAL_SPACE
init|=
literal|"GLOBAL_SPACE"
decl_stmt|;
comment|/**      * Obtain a read lock for specified ontology      *       * @param ontologyURI      *            URI of the ontology      */
specifier|public
specifier|abstract
name|void
name|obtainReadLockFor
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Release read lock for specified ontology      *       * @param ontologyURI      *            URI of the ontology      */
specifier|public
specifier|abstract
name|void
name|releaseReadLockFor
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Obtain a write lock for specified ontology      *       * @param ontologyURI      *            URI of the ontology      */
specifier|public
specifier|abstract
name|void
name|obtainWriteLockFor
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Release write lock for specified ontology      *       * @param ontologyURI      */
specifier|public
specifier|abstract
name|void
name|releaseWriteLockFor
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

