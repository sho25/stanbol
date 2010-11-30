begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
operator|.
name|io
package|;
end_package

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|apibinding
operator|.
name|OWLManager
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
comment|/**  * An input source that provides the OWL Ontology loaded from the supplied  * physical IRI, as well as the physical IRI itself for consumers that need to  * load the ontology themselves.<br>  *<br>  * For convenience, an existing OWL ontology manager can be supplied for loading  * the ontology.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|RootOntologyIRISource
extends|extends
name|AbstractOntologyInputSource
block|{
specifier|public
name|RootOntologyIRISource
parameter_list|(
name|IRI
name|rootPhysicalIri
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|this
argument_list|(
name|rootPhysicalIri
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|RootOntologyIRISource
parameter_list|(
name|IRI
name|rootPhysicalIri
parameter_list|,
name|OWLOntologyManager
name|manager
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|physicalIri
operator|=
name|rootPhysicalIri
expr_stmt|;
name|rootOntology
operator|=
name|manager
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|rootPhysicalIri
argument_list|)
expr_stmt|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see eu.iksproject.kres.manager.io.AbstractOntologyInputSource#toString() 	 */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"ROOT_ONT_IRI<"
operator|+
name|getPhysicalIRI
argument_list|()
operator|+
literal|">"
return|;
block|}
block|}
end_class

end_unit

