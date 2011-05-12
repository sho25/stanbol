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
name|registry
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
name|registry
operator|.
name|models
operator|.
name|Registry
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
name|registry
operator|.
name|models
operator|.
name|RegistryContentException
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
name|registry
operator|.
name|models
operator|.
name|RegistryItem
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
name|registry
operator|.
name|models
operator|.
name|RegistryLibrary
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
name|OWLOntology
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
name|OWLOntologyCreationException
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
name|OWLOntologyManager
import|;
end_import

begin_comment
comment|/**  * A registry loader is a toolkit for loading all ontologies indexed by an ontology registry, or those  * referenced by one of the libraries within a registry.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RegistryLoader
block|{
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|gatherOntologies
parameter_list|(
name|RegistryItem
name|registryItem
parameter_list|,
name|OWLOntologyManager
name|manager
parameter_list|,
name|boolean
name|recurseRegistries
parameter_list|)
throws|throws
name|OWLOntologyCreationException
function_decl|;
name|RegistryLibrary
name|getLibrary
parameter_list|(
name|Registry
name|reg
parameter_list|,
name|IRI
name|libraryID
parameter_list|)
function_decl|;
name|Object
name|getParent
parameter_list|(
name|Object
name|child
parameter_list|)
function_decl|;
name|boolean
name|hasChildren
parameter_list|(
name|Object
name|parent
parameter_list|)
function_decl|;
name|boolean
name|hasLibrary
parameter_list|(
name|Registry
name|reg
parameter_list|,
name|IRI
name|libraryID
parameter_list|)
function_decl|;
name|void
name|loadLocations
parameter_list|()
throws|throws
name|RegistryContentException
function_decl|;
comment|/**      * The ontology at<code>physicalIRI</code> may in turn include more than one registry.      *       * @param physicalIRI      * @return      */
name|Set
argument_list|<
name|Registry
argument_list|>
name|loadRegistriesEager
parameter_list|(
name|IRI
name|physicalIRI
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

