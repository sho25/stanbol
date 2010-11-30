begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|session
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_interface
specifier|public
interface|interface
name|SessionListenable
block|{
comment|/** 	 * Adds the given SessionListener to the pool of registered listeners. 	 *  	 * @param listener 	 *            the session listener to be added 	 */
specifier|public
name|void
name|addSessionListener
parameter_list|(
name|SessionListener
name|listener
parameter_list|)
function_decl|;
comment|/** 	 * Clears the pool of registered session listeners. 	 */
specifier|public
name|void
name|clearSessionListeners
parameter_list|()
function_decl|;
comment|/** 	 * Returns all the registered session listeners. It is up to developers to 	 * decide whether implementations should return sets (unordered but without 	 * redundancy), lists (e.g. in the order they wer registered but potentially 	 * redundant) or other data structures that implement {@link Collection}. 	 *  	 * @return a collection of registered session listeners. 	 */
specifier|public
name|Collection
argument_list|<
name|SessionListener
argument_list|>
name|getSessionListeners
parameter_list|()
function_decl|;
comment|/** 	 * Removes the given SessionListener from the pool of active listeners. 	 *  	 * @param listener 	 *            the session listener to be removed 	 */
specifier|public
name|void
name|removeSessionListener
parameter_list|(
name|SessionListener
name|listener
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

