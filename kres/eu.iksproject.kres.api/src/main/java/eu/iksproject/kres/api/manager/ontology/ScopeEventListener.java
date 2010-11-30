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
name|ontology
package|;
end_package

begin_comment
comment|/**  * Objects that want to listen to the registration of ontology scopes should  * implement this interface and add themselves as listener to a scope registry.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ScopeEventListener
block|{
comment|/** 	 * Called<i>after</i> an ontology scope, assuming it is already registered 	 * somewhere, is activated. 	 *  	 * @param scope 	 *            the activated ontology scope 	 */
specifier|public
name|void
name|scopeActivated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
comment|/** 	 * Called<i>after</i> a new ontology scope has been created. 	 *  	 * @param scope 	 *            the created ontology scope 	 */
specifier|public
name|void
name|scopeCreated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
comment|/** 	 * Called<i>after</i> an ontology scope, assuming it is already registered 	 * somewhere, is deactivated. If the deactivation of a scope implies 	 * deregistering of it, a separate event should be fired for deregistration. 	 *  	 * @param scope 	 *            the deactivated ontology scope 	 */
specifier|public
name|void
name|scopeDeactivated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
comment|/** 	 * Called<i>after</i> an ontology scope is removed from the scope registry. 	 *  	 * @param scope 	 *            the deregistered ontology scope 	 */
specifier|public
name|void
name|scopeDeregistered
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
comment|/** 	 * Called<i>after</i> an ontology scope is added to the scope registry. 	 *  	 * @param scope 	 *            the registered ontology scope 	 */
specifier|public
name|void
name|scopeRegistered
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

