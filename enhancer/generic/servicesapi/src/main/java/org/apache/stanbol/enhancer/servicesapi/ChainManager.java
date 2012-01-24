begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
import|;
end_import

begin_comment
comment|/**  * Interface that allows to lookup {@link ServiceReference} and  * {@link Chain} services based on the name.  */
end_comment

begin_interface
specifier|public
interface|interface
name|ChainManager
block|{
comment|/**      * The name of the default fault {@link Chain} as used by the algorithm to      * determine the Chain returned by {@link #getDefault()}.<p>      * See the specification of enhancement chains for details.      */
name|String
name|DEFAULT_CHAIN_NAME
init|=
literal|"default"
decl_stmt|;
comment|/**      * Getter for the names of all currently active enhancement chains. This is       * a snapshot and this set will change if {@link Chain}s become       * active/inactive.      *<p>      * Users of this method should keep in mind to check if the      * {@link ServiceReference}s and/or {@link Chain}s retrieved      * by the names in the returned set may no longer be available. Therefore      * it is strongly recommended to checks for<code>null</code> values on      * results of subsequent calls to {@link #getReference(String)} or      * {@link #getChain(String)}.       * @return the set with all names of currently active chains.      */
name|Set
argument_list|<
name|String
argument_list|>
name|getActiveChainNames
parameter_list|()
function_decl|;
comment|/**      * Getter for the ServiceReference of the Chain for the parsed      * name      * @param name The name - MUST NOT be<code>null</code> empty and tracked      * by this tracker      * @return the {@link ServiceReference} or<code>null</code> if no Chain      * with the given name is active      * @throws IllegalArgumentException if the parsed name is<code>null</code>,      * empty or not tracked by this tracker instance.      */
name|ServiceReference
name|getReference
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
comment|/**      * Getter for all ServiceReferences of the Chains registered for      * the parsed name. The list of references is sorted by       * {@link Constants#SERVICE_RANKING}.      * @param name The name - MUST NOT be<code>null</code> empty and tracked      * by this tracker      * @return the list of {@link ServiceReference}s sorted by       * {@link Constants#SERVICE_RANKING} with the highest ranking in the first      * position. If no chain for the parsed name is active an empty list is      * returned.      * with the given name is active      * @throws IllegalArgumentException if the parsed name is<code>null</code>,      * empty or not tracked by this tracker instance.      */
name|List
argument_list|<
name|ServiceReference
argument_list|>
name|getReferences
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
comment|/**      * Getter for the Chain with the highest {@link Constants#SERVICE_RANKING}      * registered for the parsed name.      * @param name the name of the Chain      * @return the Chain or<code>null</code> if no Chain with this name is      * registered as OSGI service.      * @throws IllegalArgumentException if<code>null</code> or an empty String      * is parsed as name.      */
name|Chain
name|getChain
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
comment|/**      * Getter for the {@link Chain} service for the parsed      * service Reference. This method allows to also retrieve the service for      * other chains than the one with the highest service ranking by using      *<code><pre>      *     for(ServiceReference chainRef : tracker.getReferences("test")){      *         Chain chain = tracker.getChain(chainRef)      *         if(chain != null) { //may become inactive in the meantime      *             //start the catastrophic chain of events that caused the Big Bang      *         }      *     }      *</pre></code>      * @param chainReference the service reference for a tracked chain      * @return the referenced {@link Chain} or<code>null</code>      * if no longer available.      */
name|Chain
name|getChain
parameter_list|(
name|ServiceReference
name|chainReference
parameter_list|)
function_decl|;
comment|/**      * Checks if at least a single Chain with the parsed name is currently      * registered as OSGI service.      * @param name the name      * @return the state      * @throws IllegalArgumentException if<code>null</code> or an empty String      * is parsed as name.      */
name|boolean
name|isChain
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
comment|/**      * Getter for the default Chain. This is the Chain that MUST BE used to      * enhance {@link ContentItem} if the no Chain was explicitly parsed in the      * enhancement request.<p>      * The default Chain is the Chain with the value of the property       * {@link Chain#PROPERTY_NAME} is equals to {@link #DEFAULT_CHAIN_NAME} and       * the highest<code>{@link Constants#SERVICE_RANKING}</code>.      * If no Chain with the name "default" exists the Chain with the highest       * service ranking (regardless of its name) is considered the default Chain.      * @return the default Chain or<code>null</code> if no Chain is available      */
name|Chain
name|getDefault
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

