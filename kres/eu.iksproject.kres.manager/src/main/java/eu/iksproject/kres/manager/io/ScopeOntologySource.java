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
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * An ontology source that rewrites the physical IRI by appending the logical  * one to the scope ID. If the ontology is anonymous, the original physical IRI  * is retained.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|ScopeOntologySource
extends|extends
name|AbstractOntologyInputSource
block|{
specifier|public
name|ScopeOntologySource
parameter_list|(
name|IRI
name|scopeIri
parameter_list|,
name|OWLOntology
name|ontology
parameter_list|,
name|IRI
name|origin
parameter_list|)
block|{
name|rootOntology
operator|=
name|ontology
expr_stmt|;
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ScopeOntologySource
operator|.
name|class
argument_list|)
operator|.
name|debug
argument_list|(
literal|"[KReS] :: REWRITING "
operator|+
name|origin
operator|+
literal|" TO "
operator|+
name|scopeIri
operator|+
literal|"/"
operator|+
name|ontology
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
expr_stmt|;
name|physicalIri
operator|=
operator|!
name|ontology
operator|.
name|isAnonymous
argument_list|()
condition|?
name|IRI
operator|.
name|create
argument_list|(
name|scopeIri
operator|+
literal|"/"
operator|+
name|ontology
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
else|:
name|origin
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"SCOPE_ONT_IRI<"
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

