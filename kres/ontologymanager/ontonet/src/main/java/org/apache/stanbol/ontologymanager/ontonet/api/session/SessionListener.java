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

begin_comment
comment|/**  * Objects that want to listen to events affecting KReS sessions should  * implement this interface and add themselves as listener to a manager.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SessionListener
block|{
comment|/** 	 * Called whenever an event affecting a KReS session is fired. 	 *  	 * @param event 	 *            the session event. 	 */
specifier|public
name|void
name|sessionChanged
parameter_list|(
name|SessionEvent
name|event
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

