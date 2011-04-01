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
comment|/**  * Implementations of this interface provide algorithms for generating valid  * identifiers for KReS sessions. These algorithms should take into account the  * need for excluding existing session IDs.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SessionIDGenerator
block|{
comment|/** 	 * Generates a new context-free session ID. Whether this causes duplicate 	 * IDs, it should be care of the object that invoked this method to check 	 * it. 	 *  	 * @return the newly generated session ID. 	 */
specifier|public
name|IRI
name|createSessionID
parameter_list|()
function_decl|;
comment|/** 	 * Generates a new session ID that is different from any IRI in the 	 *<code>exclude</code> set. Whether this causes duplicate IDs (supposing 	 * the<code>exclude</code> set does not include all of them), it should be 	 * care of the object that invoked this method to check it. 	 *  	 * @param exclude 	 *            the set of IRIs none of which the generate ID must be equal 	 *            to. 	 * @return the newly generated session ID. 	 */
specifier|public
name|IRI
name|createSessionID
parameter_list|(
name|Set
argument_list|<
name|IRI
argument_list|>
name|exclude
parameter_list|)
function_decl|;
comment|/** 	 * Returns the base IRI for all generated IDs to start with. It should be 	 * used by all<code>createSessionID()</code> methods, or ignore if null. 	 *  	 * @param baseIRI 	 *            the base IRI. 	 */
specifier|public
name|IRI
name|getBaseIRI
parameter_list|()
function_decl|;
comment|/** 	 * Sets the base IRI for all generated IDs to start with. It should be used 	 * by all<code>createSessionID()</code> methods, or ignore if null. 	 *  	 * @param baseIRI 	 *            the base IRI. 	 */
specifier|public
name|void
name|setBaseIRI
parameter_list|(
name|IRI
name|baseIRI
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

