begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
operator|.
name|api
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ONManager
import|;
end_import

begin_comment
comment|/**  * This object contains every shared information that other components need to know in order to work. It  * should be the primary object referenced by service components and passed as an argument to non-default  * constructors.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Configuration
block|{
comment|/**      * The key used to configure the path of the ontology network configuration.      */
name|String
name|SCOPE_SHORT_ID
init|=
literal|"org.apache.stanbol.explanation.scopeid"
decl_stmt|;
name|ONManager
name|getOntologyNetworkManager
parameter_list|()
function_decl|;
name|String
name|getScopeID
parameter_list|()
function_decl|;
name|String
name|getScopeShortId
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

