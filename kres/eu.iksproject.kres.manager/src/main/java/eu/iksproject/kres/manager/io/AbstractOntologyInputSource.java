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
name|manager
operator|.
name|ontology
operator|.
name|OntologyInputSource
import|;
end_import

begin_comment
comment|/**  * Abstract implementation of {@link OntologyInputSource} with the basic methods  * for obtaining root ontologies and their physical IRIs where applicable.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractOntologyInputSource
implements|implements
name|OntologyInputSource
block|{
specifier|protected
name|IRI
name|physicalIri
init|=
literal|null
decl_stmt|;
specifier|protected
name|OWLOntology
name|rootOntology
init|=
literal|null
decl_stmt|;
comment|/* 	 * (non-Javadoc) 	 * @see java.lang.Object#equals(java.lang.Object) 	 */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|OntologyInputSource
operator|)
condition|)
return|return
literal|false
return|;
name|OntologyInputSource
name|src
init|=
operator|(
name|OntologyInputSource
operator|)
name|obj
decl_stmt|;
return|return
name|this
operator|.
name|physicalIri
operator|.
name|equals
argument_list|(
name|src
operator|.
name|getPhysicalIRI
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|rootOntology
operator|.
name|equals
argument_list|(
name|src
operator|.
name|getRootOntology
argument_list|()
argument_list|)
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see eu.iksproject.kres.api.manager.ontology.OntologyInputSource#getPhysicalIRI() 	 */
annotation|@
name|Override
specifier|public
name|IRI
name|getPhysicalIRI
parameter_list|()
block|{
return|return
name|physicalIri
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see eu.iksproject.kres.api.manager.ontology.OntologyInputSource#getRootOntology() 	 */
annotation|@
name|Override
specifier|public
name|OWLOntology
name|getRootOntology
parameter_list|()
block|{
return|return
name|rootOntology
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see eu.iksproject.kres.api.manager.ontology.OntologyInputSource#hasPhysicalIRI() 	 */
annotation|@
name|Override
specifier|public
name|boolean
name|hasPhysicalIRI
parameter_list|()
block|{
return|return
name|physicalIri
operator|!=
literal|null
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see eu.iksproject.kres.api.manager.ontology.OntologyInputSource#hasRootOntology() 	 */
annotation|@
name|Override
specifier|public
name|boolean
name|hasRootOntology
parameter_list|()
block|{
return|return
name|rootOntology
operator|!=
literal|null
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see java.lang.Object#toString() 	 */
annotation|@
name|Override
specifier|public
specifier|abstract
name|String
name|toString
parameter_list|()
function_decl|;
block|}
end_class

end_unit

