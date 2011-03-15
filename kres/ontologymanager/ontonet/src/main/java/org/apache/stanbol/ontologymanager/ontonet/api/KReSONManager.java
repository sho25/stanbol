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
name|ontology
operator|.
name|OntologyIndex
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
name|OntologyScopeFactory
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
name|OntologySpaceFactory
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
name|ScopeRegistry
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
name|KReSRegistryLoader
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
name|session
operator|.
name|KReSSessionManager
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
name|store
operator|.
name|api
operator|.
name|OntologyStorage
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
name|OWLDataFactory
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
name|OWLOntologyAlreadyExistsException
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
comment|/**  * A KReS Ontology Network Manager.  *   * @author andrea.nuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|KReSONManager
block|{
comment|/** 	 * Returns the default object that automatically indexes ontologies as they 	 * are loaded within scopes. 	 *  	 * @return the default ontology index. 	 */
specifier|public
name|OntologyIndex
name|getOntologyIndex
parameter_list|()
function_decl|;
comment|/** 	 * Returns the ontology scope factory that was created along with the 	 * manager context. 	 *  	 * @return the default ontology scope factory 	 */
specifier|public
name|OntologyScopeFactory
name|getOntologyScopeFactory
parameter_list|()
function_decl|;
comment|/** 	 * Returns the ontology space factory that was created along with the 	 * manager context. 	 *  	 * @return the default ontology space factory. 	 */
specifier|public
name|OntologySpaceFactory
name|getOntologySpaceFactory
parameter_list|()
function_decl|;
comment|/** 	 * Returns the default ontology storage system for this KReS instance. 	 *  	 * @return the default ontology store. 	 */
specifier|public
name|OntologyStorage
name|getOntologyStore
parameter_list|()
function_decl|;
comment|/** 	 * Returns an OWL Ontology Manager that is never cleared of its ontologies, 	 * so it can be used for caching ontologies without having to reload them 	 * using other managers. It is sufficient to catch 	 * {@link OWLOntologyAlreadyExistsException}s and obtain the ontology with 	 * that same ID from this manager. 	 *  	 * @return the OWL Ontology Manager used for caching ontologies. 	 */
specifier|public
name|OWLOntologyManager
name|getOwlCacheManager
parameter_list|()
function_decl|;
comment|/** 	 * Returns a factory object that can be used for obtaining OWL API objects. 	 *  	 * @return the default OWL data factory 	 */
specifier|public
name|OWLDataFactory
name|getOwlFactory
parameter_list|()
function_decl|;
comment|/** 	 * Returns the default ontology registry loader. 	 *  	 * @return the default ontology registry loader. 	 */
specifier|public
name|KReSRegistryLoader
name|getRegistryLoader
parameter_list|()
function_decl|;
comment|/** 	 * Returns the unique ontology scope registry for this context. 	 *  	 * @return the ontology scope registry. 	 */
specifier|public
name|ScopeRegistry
name|getScopeRegistry
parameter_list|()
function_decl|;
comment|/** 	 * Returns the unique KReS session manager for this context. 	 *  	 * @return the KreS session manager. 	 */
specifier|public
name|KReSSessionManager
name|getSessionManager
parameter_list|()
function_decl|;
comment|/** 	 * Returns the list of IRIs that identify scopes that should be activated on 	 * startup,<i>if they exist</i>. 	 *  	 * @return the list of scope IDs to activate. 	 */
specifier|public
name|String
index|[]
name|getUrisToActivate
parameter_list|()
function_decl|;
comment|/** 	 * Returns the String that represent the namespace used by KReS for its ontologies 	 *  	 * @return the namespace of KReS. 	 */
specifier|public
name|String
name|getKReSNamespace
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

