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
name|impl
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
name|Random
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
name|io
operator|.
name|RootOntologySource
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
name|SpaceType
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
name|ontonet
operator|.
name|impl
operator|.
name|io
operator|.
name|ClerezzaOntologyStorage
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
name|impl
operator|.
name|util
operator|.
name|StringUtils
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

begin_class
specifier|public
class|class
name|SessionOntologySpaceImpl
extends|extends
name|AbstractOntologySpaceImpl
implements|implements
name|SessionOntologySpace
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SUFFIX
init|=
name|SpaceType
operator|.
name|SESSION
operator|.
name|getIRISuffix
argument_list|()
decl_stmt|;
comment|//	static {
comment|//		SUFFIX = SpaceType.SESSION.getIRISuffix();
comment|//	}
specifier|public
name|SessionOntologySpaceImpl
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|ClerezzaOntologyStorage
name|store
parameter_list|)
block|{
comment|// FIXME : sync session id with session space ID
name|super
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|StringUtils
operator|.
name|stripIRITerminator
argument_list|(
name|scopeID
argument_list|)
operator|+
literal|"/"
operator|+
name|SpaceType
operator|.
name|SESSION
operator|.
name|getIRISuffix
argument_list|()
operator|+
literal|"-"
operator|+
operator|new
name|Random
argument_list|()
operator|.
name|nextLong
argument_list|()
argument_list|)
argument_list|,
name|SpaceType
operator|.
name|SESSION
argument_list|,
name|store
comment|/*, scopeID*/
argument_list|)
expr_stmt|;
name|IRI
name|iri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|StringUtils
operator|.
name|stripIRITerminator
argument_list|(
name|getID
argument_list|()
argument_list|)
operator|+
literal|"/root.owl"
argument_list|)
decl_stmt|;
try|try
block|{
name|setTopOntology
argument_list|(
operator|new
name|RootOntologySource
argument_list|(
name|ontologyManager
operator|.
name|createOntology
argument_list|(
name|iri
argument_list|)
argument_list|,
literal|null
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"KReS :: Could not create session space root ontology "
operator|+
name|iri
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
comment|// Should not happen...
name|log
operator|.
name|error
argument_list|(
literal|"KReS :: Session space ontology "
operator|+
name|iri
operator|+
literal|" was denied modification by the space itself. This should not happen."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|SessionOntologySpaceImpl
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|ClerezzaOntologyStorage
name|store
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|)
block|{
comment|// FIXME : sync session id with session space ID
name|super
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|StringUtils
operator|.
name|stripIRITerminator
argument_list|(
name|scopeID
argument_list|)
operator|+
literal|"/"
operator|+
name|SpaceType
operator|.
name|SESSION
operator|.
name|getIRISuffix
argument_list|()
operator|+
literal|"-"
operator|+
operator|new
name|Random
argument_list|()
operator|.
name|nextLong
argument_list|()
argument_list|)
argument_list|,
name|SpaceType
operator|.
name|SESSION
argument_list|,
name|store
argument_list|,
comment|/*scopeID,*/
name|ontologyManager
argument_list|)
expr_stmt|;
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
name|IRI
name|iri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|StringUtils
operator|.
name|stripIRITerminator
argument_list|(
name|getID
argument_list|()
argument_list|)
operator|+
literal|"/root.owl"
argument_list|)
decl_stmt|;
try|try
block|{
name|setTopOntology
argument_list|(
operator|new
name|RootOntologySource
argument_list|(
name|ontologyManager
operator|.
name|createOntology
argument_list|(
name|iri
argument_list|)
argument_list|,
literal|null
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"KReS :: Could not create session space root ontology "
operator|+
name|iri
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
comment|// Should not happen...
name|log
operator|.
name|error
argument_list|(
literal|"KReS :: Session space ontology "
operator|+
name|iri
operator|+
literal|" was denied modification by the space itself. This should not happen."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Session spaces expose their ontology managers. 	 */
annotation|@
name|Override
specifier|public
name|OWLOntologyManager
name|getOntologyManager
parameter_list|()
block|{
return|return
name|ontologyManager
return|;
block|}
comment|/** 	 * Once it is set up, a session space is write-enabled. 	 */
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|setUp
parameter_list|()
block|{
name|locked
operator|=
literal|false
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|tearDown
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|attachSpace
parameter_list|(
name|OntologySpace
name|space
parameter_list|,
name|boolean
name|skipRoot
parameter_list|)
throws|throws
name|UnmodifiableOntologySpaceException
block|{
if|if
condition|(
operator|!
operator|(
name|space
operator|instanceof
name|SessionOntologySpace
operator|)
condition|)
block|{
name|OWLOntology
name|o
init|=
name|space
operator|.
name|getTopOntology
argument_list|()
decl_stmt|;
comment|// This does the append thingy
name|log
operator|.
name|debug
argument_list|(
literal|"Attaching "
operator|+
name|o
operator|+
literal|" TO "
operator|+
name|getTopOntology
argument_list|()
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
try|try
block|{
comment|// It is in fact the addition of the core space top ontology to the
comment|// custom space, with import statements and all.
name|addOntology
argument_list|(
operator|new
name|RootOntologySource
argument_list|(
name|o
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// log.debug("ok");
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"FAILED"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

