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
name|ontology
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologyIRIMapper
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
comment|/**  * Generates new instances of {@link OWLOntologyManager} with optional offline support already enabled for  * each of them.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|OWLOntologyManagerFactory
block|{
comment|/**      * Returns the list of local IRI mappers that are automatically bound to a newly created      * {@link OWLOntologyManager} if set to support offline mode. The IRI mappers are typically applied in      * order, therefore mappers at the end of the list may supersede those at its beginning.      *       * @return the list of local IRI mappers, in the order they are applied.      */
name|List
argument_list|<
name|OWLOntologyIRIMapper
argument_list|>
name|getLocalIRIMapperList
parameter_list|()
function_decl|;
comment|/**      * Creates a new instance of {@link OWLOntologyManager}, with optional offline support.      *       * @param withOfflineSupport      *            if true, the local IRI mappers obtained by calling {@link #getLocalIRIMapperList()} will be      *            applied to the new ontology manager.      * @return a new OWL ontology manager.      */
name|OWLOntologyManager
name|createOntologyManager
parameter_list|(
name|boolean
name|withOfflineSupport
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

