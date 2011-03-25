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
name|session
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

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
operator|.
name|SessionOntologySpace
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
comment|/**  * Manages KReS session objects via CRUD-like operations. A  *<code>SessionManager</code> maintains in-memory storage of KReS sessions,  * creates new ones and either destroys or stores existing ones persistently.  * All KReS sessions are managed via unique identifiers of the  *<code>org.semanticweb.owlapi.model.IRI</code> type.<br>  *<br>  * NOTE: implementations should be synchronized, or document whenever they are  * not.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SessionManager
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
comment|/** 	 * Generates a new KReS session and assigns a unique session ID generated 	 * internally. 	 *  	 * @return the generated KReS session 	 */
specifier|public
name|Session
name|createSession
parameter_list|()
function_decl|;
comment|/** 	 * Generates a new KReS session and tries to assign it the supplied session 	 * ID. If a session with that ID is already registered, the new session is 	 *<i>not</i> created and a<code>DuplicateSessionIDException</code> is 	 * thrown. 	 *  	 * @param sessionID 	 *            the IRI that uniquely identifies the session 	 * @return the generated KReS session 	 * @throws DuplicateSessionIDException 	 *             if a KReS session with that sessionID is already registered 	 */
specifier|public
name|Session
name|createSession
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
throws|throws
name|DuplicateSessionIDException
function_decl|;
comment|/** 	 * Deletes the KReS session identified by the supplied sessionID and 	 * releases its resources. 	 *  	 * @param sessionID 	 *            the IRI that uniquely identifies the session 	 */
specifier|public
name|void
name|destroySession
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
function_decl|;
comment|/** 	 * Retrieves the unique KReS session identified by<code>sessionID</code>. 	 *  	 * @param sessionID 	 *            the IRI that uniquely identifies the session 	 * @return the unique KReS session identified by<code>sessionID</code> 	 */
specifier|public
name|Session
name|getSession
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
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
comment|/** 	 * Returns the ontology space associated with this session. 	 *  	 * @return the session space 	 */
specifier|public
name|Set
argument_list|<
name|SessionOntologySpace
argument_list|>
name|getSessionSpaces
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
throws|throws
name|NonReferenceableSessionException
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
comment|/** 	 * Stores the KReS session identified by<code>sessionID</code> using the 	 * output stream<code>out</code>. 	 *  	 * @param sessionID 	 *            the IRI that uniquely identifies the session 	 * @param out 	 *            the output stream to store the session 	 */
specifier|public
name|void
name|storeSession
parameter_list|(
name|IRI
name|sessionID
parameter_list|,
name|OutputStream
name|out
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

