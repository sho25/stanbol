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

begin_interface
specifier|public
interface|interface
name|MappingConfiguration
block|{
name|void
name|setOntModel
parameter_list|(
name|OntModel
name|ontModel
parameter_list|)
function_decl|;
name|OntModel
name|getOntModel
parameter_list|()
function_decl|;
name|void
name|setBridgeDefinitions
parameter_list|(
name|BridgeDefinitions
name|bridgeDefinitions
parameter_list|)
function_decl|;
name|BridgeDefinitions
name|getBridgeDefinitions
parameter_list|()
function_decl|;
name|void
name|setAdapterMode
parameter_list|(
name|AdapterMode
name|adapterMode
parameter_list|)
function_decl|;
name|AdapterMode
name|getAdapterMode
parameter_list|()
function_decl|;
name|void
name|setOntologyURI
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
name|String
name|getOntologyURI
parameter_list|()
function_decl|;
name|void
name|setConnectionInfo
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
function_decl|;
name|ConnectionInfo
name|getConnectionInfo
parameter_list|()
function_decl|;
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

