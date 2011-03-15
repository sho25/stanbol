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
name|ontology
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
name|io
operator|.
name|OntologyInputSource
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
name|CoreOntologySpace
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
name|CustomOntologySpace
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
name|OntologyScope
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
name|OntologySpace
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
name|OntologySpaceListener
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
name|ontology
operator|.
name|SessionOntologySpace
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
name|UnmodifiableOntologySpaceException
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
name|IRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
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
comment|/**  * Utility class that generates default implementations of the three types of  * ontology scope.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|OntologySpaceFactoryImpl
implements|implements
name|OntologySpaceFactory
block|{
specifier|protected
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|protected
name|ScopeRegistry
name|registry
decl_stmt|;
specifier|protected
name|OntologyStorage
name|storage
decl_stmt|;
specifier|public
name|OntologySpaceFactoryImpl
parameter_list|(
name|ScopeRegistry
name|registry
parameter_list|,
name|OntologyStorage
name|storage
parameter_list|)
block|{
name|this
operator|.
name|registry
operator|=
name|registry
expr_stmt|;
name|this
operator|.
name|storage
operator|=
name|storage
expr_stmt|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see eu.iksproject.kres.api.manager.ontology.OntologySpaceFactory#createCoreOntologySpace(org.semanticweb.owlapi.model.IRI, eu.iksproject.kres.api.manager.ontology.OntologyInputSource) 	 */
annotation|@
name|Override
specifier|public
name|CoreOntologySpace
name|createCoreOntologySpace
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|OntologyInputSource
name|coreSource
parameter_list|)
block|{
name|CoreOntologySpace
name|s
init|=
operator|new
name|CoreOntologySpaceImpl
argument_list|(
name|scopeID
argument_list|,
name|storage
argument_list|)
decl_stmt|;
name|setupSpace
argument_list|(
name|s
argument_list|,
name|scopeID
argument_list|,
name|coreSource
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see eu.iksproject.kres.api.manager.ontology.OntologySpaceFactory#createCustomOntologySpace(org.semanticweb.owlapi.model.IRI, eu.iksproject.kres.api.manager.ontology.OntologyInputSource) 	 */
annotation|@
name|Override
specifier|public
name|CustomOntologySpace
name|createCustomOntologySpace
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|OntologyInputSource
name|customSource
parameter_list|)
block|{
name|CustomOntologySpace
name|s
init|=
operator|new
name|CustomOntologySpaceImpl
argument_list|(
name|scopeID
argument_list|,
name|storage
argument_list|)
decl_stmt|;
name|setupSpace
argument_list|(
name|s
argument_list|,
name|scopeID
argument_list|,
name|customSource
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see eu.iksproject.kres.api.manager.ontology.OntologySpaceFactory#createSessionOntologySpace(org.semanticweb.owlapi.model.IRI) 	 */
annotation|@
name|Override
specifier|public
name|SessionOntologySpace
name|createSessionOntologySpace
parameter_list|(
name|IRI
name|scopeID
parameter_list|)
block|{
name|SessionOntologySpace
name|s
init|=
operator|new
name|SessionOntologySpaceImpl
argument_list|(
name|scopeID
argument_list|,
name|storage
argument_list|)
decl_stmt|;
comment|// s.setUp();
return|return
name|s
return|;
block|}
specifier|private
name|void
name|setupSpace
parameter_list|(
name|OntologySpace
name|s
parameter_list|,
name|IRI
name|scopeID
parameter_list|,
name|OntologyInputSource
name|rootSource
parameter_list|)
block|{
comment|// FIXME: ensure that this is not null
name|OntologyScope
name|parentScope
init|=
name|registry
operator|.
name|getScope
argument_list|(
name|scopeID
argument_list|)
decl_stmt|;
if|if
condition|(
name|parentScope
operator|!=
literal|null
operator|&&
name|parentScope
operator|instanceof
name|OntologySpaceListener
condition|)
name|s
operator|.
name|addOntologySpaceListener
argument_list|(
operator|(
name|OntologySpaceListener
operator|)
name|parentScope
argument_list|)
expr_stmt|;
comment|// Set the supplied ontology's parent as the root for this space.
try|try
block|{
name|s
operator|.
name|setTopOntology
argument_list|(
name|rootSource
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"KReS :: Ontology space "
operator|+
name|s
operator|.
name|getID
argument_list|()
operator|+
literal|" found locked at creation time!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// s.setUp();
block|}
block|}
end_class

end_unit

