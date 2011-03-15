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
name|ontonet
operator|.
name|api
operator|.
name|ontology
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * Represents an ontology network that is used by KReS for modelling a given  * knowledge component or domain, e.g. workflows, organisations, devices,  * content or business domain.<br>  *<br>  * Each ontology scope comprises in turn a number of ontology spaces of three  * kinds.  *<ul>  *<li>Exactly one core space, which defines the immutable components of the  * scope.  *<li>At most one custom space, which contains user-defined components.  *<li>Zero or more session spaces, which contains (potentially volatile)  * components specific for user sessions.  *</ul>  * An ontology scope can thus be seen as a fa&ccedil;ade for ontology spaces.  *   *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OntologyScope
extends|extends
name|ScopeOntologyListenable
block|{
comment|/** 	 * Adds a new ontology space to the list of user session spaces for this 	 * scope. 	 *  	 * @param sessionSpace 	 *            the ontology space to be added. 	 */
specifier|public
name|void
name|addSessionSpace
parameter_list|(
name|OntologySpace
name|sessionSpace
parameter_list|,
name|IRI
name|sessionID
parameter_list|)
function_decl|;
comment|/** 	 * Returns the core ontology space for this ontology scope. The core space 	 * should never be null for any scope. 	 *  	 * @return the core ontology space 	 */
specifier|public
name|OntologySpace
name|getCoreSpace
parameter_list|()
function_decl|;
comment|/** 	 * Returns the custom ontology space for this ontology scope. 	 *  	 * @return the custom ontology space, or null if no custom space is 	 *         registered for this scope. 	 */
specifier|public
name|OntologySpace
name|getCustomSpace
parameter_list|()
function_decl|;
comment|/** 	 * Returns an object that uniquely identifies this ontology scope. 	 *  	 * TODO : check if we'd rather use another class for identifiers. 	 *  	 * @return the unique identifier for this ontology scope 	 */
specifier|public
name|IRI
name|getID
parameter_list|()
function_decl|;
comment|/** 	 * Return the ontology space for this scope that is identified by the 	 * supplied IRI. 	 *  	 * @param sessionID 	 *            the unique identifier of the KReS session. 	 * @return the ontology space identified by<code>sessionID</code>, or null 	 *         if no such space is registered for this scope and session. 	 */
specifier|public
name|SessionOntologySpace
name|getSessionSpace
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
function_decl|;
comment|/** 	 * Returns all the active ontology spaces for this scope. 	 *  	 * @return a set of active ontology spaces for this scope. 	 */
specifier|public
name|Set
argument_list|<
name|OntologySpace
argument_list|>
name|getSessionSpaces
parameter_list|()
function_decl|;
comment|/** 	 * Sets an ontology space as the custom space for this scope. 	 *  	 * @param customSpace 	 *            the custom ontology space. 	 * @throws UnmodifiableOntologySpaceException 	 *             if either the scope or the supplied space are locked. 	 */
specifier|public
name|void
name|setCustomSpace
parameter_list|(
name|OntologySpace
name|customSpace
parameter_list|)
throws|throws
name|UnmodifiableOntologySpaceException
function_decl|;
comment|/** 	 * Performs the operations required for activating the ontology scope. It 	 * should be possible to perform them<i>after</i> the constructor has been 	 * invoked.<br> 	 *<br> 	 * When the core ontology space is created for this scope, this should be 	 * set in the scope constructor. It can be changed in the 	 *<code>setUp()</code> method though. 	 */
specifier|public
name|void
name|setUp
parameter_list|()
function_decl|;
comment|/** 	 * Performs whatever operations are required for making sure the custom 	 * space of this scope is aware of changes occurring in its core space, that 	 * all session spaces are aware of changes in the custom space, and so on. 	 * Typically, this includes updating all import statements in the top 	 * ontologies for each space.<br> 	 *<br> 	 * This method is not intended for usage by ontology managers. Since its 	 * invocation is supposed to be automatic, it should be invoked by whatever 	 * classes are responsible for listening to changes in an ontology 	 * scope/space. In the default implementation, it is the scope itself, yet 	 * the method is left public in order to allow for external controllers. 	 */
specifier|public
name|void
name|synchronizeSpaces
parameter_list|()
function_decl|;
comment|/** 	 * Performs the operations required for deactivating the ontology scope. In 	 * general, this is not equivalent to finalizing the object for garbage 	 * collection. It should be possible to activate the same ontology scope 	 * again if need be. 	 */
specifier|public
name|void
name|tearDown
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

