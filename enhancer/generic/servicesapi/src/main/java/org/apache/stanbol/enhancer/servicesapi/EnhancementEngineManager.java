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
comment|/**  * Interface that allows to lookup {@link ServiceReference} and  * {@link EnhancementEngine} services based on the name.  */
end_comment

begin_interface
specifier|public
interface|interface
name|EnhancementEngineManager
block|{
comment|/**      * Getter for the ServiceReference of the EnhancementEngine for the parsed      * name      * @param name The name - MUST NOT be<code>null</code> empty and tracked      * by this tracker      * @return the {@link ServiceReference} or<code>null</code> if no Engine      * with the given name is active      * @throws IllegalArgumentException if the parsed name is<code>null</code>,      * empty or not tracked by this tracker instance.      */
name|ServiceReference
name|getReference
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
comment|/**      * Getter for all ServiceReferences of the EnhancementEngines registered for      * the parsed name. The list of references is sorted by       * {@link Constants#SERVICE_RANKING}.      * @param name The name - MUST NOT be<code>null</code> empty and tracked      * by this tracker      * @return the list of {@link ServiceReference}s sorted by       * {@link Constants#SERVICE_RANKING} with the highest ranking in the first      * position. If no engine for the parsed name is active an empty list is      * returned.      * with the given name is active      * @throws IllegalArgumentException if the parsed name is<code>null</code>,      * empty or not tracked by this tracker instance.      */
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
comment|/**      * Getter for the EnhancementEngine for the parsed name      * @param name The name - MUST NOT be<code>null</code> empty and tracked      * by this tracker      * @return The {@link EnhancementEngine} or<code>null</code> if no Engine      * with the given name is active      * @throws IllegalArgumentException if the parsed name is<code>null</code>,      * empty or not tracked by this tracker instance.      */
name|EnhancementEngine
name|getEngine
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
comment|/**      * Checks if an {@link EnhancementEngine} with the parsed name is active      * @param name the name      * @return the state      * @throws IllegalArgumentException if<code>null</code> or an empty String      * is parsed as name.      */
name|boolean
name|isEngine
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
comment|/**      * Getter for all active and tracked engine names. This is a snapshot and      * this set will change if {@link EnhancementEngine}s become active/inactive.      *<p>      * Users of this method should keep in mind to check if the      * {@link ServiceReference}s and/or {@link EnhancementEngine}s retrieved      * by the names in the returned set may no longer be available. Therefore      * it is strongly recommended to checks for<code>null</code> values on      * results of subsequent calls to {@link #getReference(String)} or      * {@link #getEngine(String)}.       * @return the set with all names of currently active engines.      */
name|Set
argument_list|<
name|String
argument_list|>
name|getActiveEngineNames
parameter_list|()
function_decl|;
comment|/**      * Getter for the {@link EnhancementEngine} service for the parsed      * service Reference. This method allows to also retrieve the service for      * other engines than the one with the highest service ranking by using      *<code><pre>      *     for(ServiceReference engineRef : tracker.getReferences("test")){      *         EnhancementEngine engine = tracker.getEngine(engineRef)      *         if(engine != null) { //may become inactive in the meantime      *             //save the world by using this engine!      *         }      *     }      *</pre></code>      * @param engineReference the service reference for an engine tracked by this      * component      * @return the referenced {@link EnhancementEngine} or<code>null</code>      * if no longer available.      */
name|EnhancementEngine
name|getEngine
parameter_list|(
name|ServiceReference
name|engineReference
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

