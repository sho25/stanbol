begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|OntologyResourceHelper
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|BridgeDefinitions
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|ConnectionInfo
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|AdapterMode
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_comment
comment|/**  * {@link MappingConfiguration} describes an environment where an ontology extraction operation from CMS takes  * place.  *   * @author Suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|MappingConfiguration
block|{
comment|/**      * Changes ontology model that will be generated by the extraction process.      * Using this function after the processors started may result in loss of triples.      * @param ontModel      *            New Ontology      */
name|void
name|setOntModel
parameter_list|(
name|OntModel
name|ontModel
parameter_list|)
function_decl|;
comment|/**      * Method to retrieve ontology model, although modifications to ontology through this model is discouraged      * (see {@link OntologyResourceHelper}), any processor can access and modify this model.      *       * @return Ontology that is generated through extraction process.      */
name|OntModel
name|getOntModel
parameter_list|()
function_decl|;
comment|/**      * Method to change or set {@link BridgeDefinitions} of the configuration.      *       * @param bridgeDefinitions      *            {@link BridgeDefinitions} to be used in the extraction process.      */
name|void
name|setBridgeDefinitions
parameter_list|(
name|BridgeDefinitions
name|bridgeDefinitions
parameter_list|)
function_decl|;
comment|/**      * Method to retrieve {@link BridgeDefinitions} to be used in the extraction process.      *       * @return {@link BridgeDefinitions} instance.      */
name|BridgeDefinitions
name|getBridgeDefinitions
parameter_list|()
function_decl|;
comment|/**      * Method to change or set {@link AdapterMode} of the configuration. {@link AdapterMode}s are used to tune      * when to access a remote repository during extraction process. For detailed explanation see      * {@link AdapterMode}      *       * @param adapterMode      */
name|void
name|setAdapterMode
parameter_list|(
name|AdapterMode
name|adapterMode
parameter_list|)
function_decl|;
comment|/**      * Method to retrieve {@link AdapterMode} of the configuration.      *       * @return      */
name|AdapterMode
name|getAdapterMode
parameter_list|()
function_decl|;
comment|/**      * Method to change or set the URI of the ontology being generated. Changing this configuration during      * extraction process may result in inconsistent ontology. The extracted ontology will be automatically      * saved to persistence store by by this URI.      *       * @param ontologyURI      *            URI of the ontology.      */
name|void
name|setOntologyURI
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * Method to retrieve URI of the ontology.      *       * @return URI of the ontology.      */
name|String
name|getOntologyURI
parameter_list|()
function_decl|;
comment|/**      * Method to change or set {@link ConnectionInfo} of the mapping configuration. ConnectionInfo is used for      * accessing the content repository in<b>TOLERATED OFFLINE</b> and<b>ONLINE</b> {@link AdapterMode}s.      *       * @param connectionInfo      */
name|void
name|setConnectionInfo
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
function_decl|;
comment|/**      * Method to retrieve {@link ConnectionInfo} of the mapping configuration.      *       * @return      */
name|ConnectionInfo
name|getConnectionInfo
parameter_list|()
function_decl|;
comment|/**      * Method to change or set CMS Objects of the mapping configuration. If {@link AdapterMode} is      *<b>ONLINE</b> there is no need to set these objects. They will be retrieved from a CMS repository using      * {@link ConnectionInfo} supplied to this configuration.</br>An Interface for a common CMS Object is not yet      * defined. So {@link Object} is used as type.      *       * @param objects      *            A list of objects to be processed by processors during the extraction.      */
name|void
name|setObjects
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|objects
parameter_list|)
function_decl|;
comment|/**      * Method to retrieve CMS OBjects of the configuration.      * @return List of CMS objects       */
name|List
argument_list|<
name|Object
argument_list|>
name|getObjects
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

