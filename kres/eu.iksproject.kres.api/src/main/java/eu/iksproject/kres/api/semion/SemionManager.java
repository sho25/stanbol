begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|semion
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|TcManager
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
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|semion
operator|.
name|util
operator|.
name|SemionStructuredDataSource
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|storage
operator|.
name|NoSuchOntologyInStoreException
import|;
end_import

begin_comment
comment|/**  *   * The {@code SemionManager} is responsible of the coordination of all the tasks performed by Semion in KReS  *   * @author andrea.nuzzolese  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|SemionManager
block|{
comment|/** 	 * The {@link SemionManager} can add a new reengineer to the list of available reengineers. This is performed through the method 	 * {@cod bindReengineer}. 	 *  	 * @param semionReengineer {@link SemionReengineer} 	 * @return true if the reengineer is bound, false otherwise 	 */
specifier|public
name|boolean
name|bindReengineer
parameter_list|(
name|SemionReengineer
name|semionReengineer
parameter_list|)
function_decl|;
comment|/** 	 * The {@link SemionManager} can remove a reengineer from the list of available reengineers. This is performed through the method 	 * {@cod unbindReengineer}. 	 *  	 * @param semionReengineer {@link SemionReengineer} 	 * @return true if the reengineer is unbound, false otherwise 	 */
specifier|public
name|boolean
name|unbindReengineer
parameter_list|(
name|SemionReengineer
name|semionReengineer
parameter_list|)
function_decl|;
comment|/** 	 * The {@link SemionManager} can remove a reengineer from the list of available reengineers. This is performed through the method 	 * {@cod unbindReengineer}. 	 *  	 * @param reenginnerType {@code int} 	 * @return true if the reengineer is unbound, false otherwise 	 */
specifier|public
name|boolean
name|unbindReengineer
parameter_list|(
name|int
name|reenginnerType
parameter_list|)
function_decl|;
comment|/** 	 * The {@link SemionManager} can register a single instance of {@link SemionRefactorer}. 	 *  	 * @param semionRefactorer {@link SemionRefactorer} 	 */
specifier|public
name|void
name|registerRefactorer
parameter_list|(
name|SemionRefactorer
name|semionRefactorer
parameter_list|)
function_decl|;
comment|/** 	 * Unregisters the instance of {@link SemionRefactorer}. After the call of this method Semion has no refactorer. 	 */
specifier|public
name|void
name|unregisterRefactorer
parameter_list|()
function_decl|;
comment|/** 	 * The instance of the refactored is returned back if it exists. 	 *  	 * @return the active {@link SemionRefactorer} 	 */
specifier|public
name|SemionRefactorer
name|getRegisteredRefactorer
parameter_list|()
function_decl|;
comment|/** 	 * Gets the active reengineers of KReS. 	 *  	 * @return the {@link Collection< SemionReengineer>} of active reengineers.  	 */
specifier|public
name|Collection
argument_list|<
name|SemionReengineer
argument_list|>
name|listReengineers
parameter_list|()
function_decl|;
comment|/** 	 * Gets the number of active reengineers. 	 *  	 * @return the number of active reengineers. 	 */
specifier|public
name|int
name|countReengineers
parameter_list|()
function_decl|;
specifier|public
name|OWLOntology
name|performReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|)
throws|throws
name|ReengineeringException
function_decl|;
specifier|public
name|OWLOntology
name|performSchemaReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|)
throws|throws
name|ReengineeringException
function_decl|;
specifier|public
name|OWLOntology
name|performDataReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|,
name|IRI
name|schemaOntologyIRI
parameter_list|)
throws|throws
name|ReengineeringException
throws|,
name|NoSuchOntologyInStoreException
function_decl|;
specifier|public
name|OWLOntology
name|performDataReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|,
name|OWLOntology
name|schemaOntology
parameter_list|)
throws|throws
name|ReengineeringException
function_decl|;
block|}
end_interface

end_unit

