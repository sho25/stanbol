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
name|OWLOntologyManager
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
name|CoreOntologySpace
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
name|CustomOntologySpace
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
name|SpaceType
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
name|UnmodifiableOntologySpaceException
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
name|OntologyStorage
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
name|manager
operator|.
name|io
operator|.
name|RootOntologySource
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
name|manager
operator|.
name|util
operator|.
name|StringUtils
import|;
end_import

begin_class
specifier|public
class|class
name|CustomOntologySpaceImpl
extends|extends
name|AbstractOntologySpaceImpl
implements|implements
name|CustomOntologySpace
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SUFFIX
init|=
name|SpaceType
operator|.
name|CUSTOM
operator|.
name|getIRISuffix
argument_list|()
decl_stmt|;
comment|//	static {
comment|//		SUFFIX = SpaceType.CUSTOM.getIRISuffix();
comment|//	}
specifier|public
name|CustomOntologySpaceImpl
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|OntologyStorage
name|storage
parameter_list|)
block|{
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
name|CUSTOM
operator|.
name|getIRISuffix
argument_list|()
argument_list|)
argument_list|,
name|SpaceType
operator|.
name|CUSTOM
comment|/*, scopeID*/
argument_list|,
name|storage
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CustomOntologySpaceImpl
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|OntologyStorage
name|storage
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|)
block|{
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
name|CUSTOM
operator|.
name|getIRISuffix
argument_list|()
argument_list|)
argument_list|,
name|SpaceType
operator|.
name|CUSTOM
argument_list|,
name|storage
argument_list|,
comment|/*scopeID,*/
name|ontologyManager
argument_list|)
expr_stmt|;
block|}
comment|//	public CustomOntologySpaceImpl(IRI scopeID, OntologyInputSource topOntology) {
comment|//	super(IRI.create(StringUtils.stripIRITerminator(scopeID) + "/"
comment|//			+ SpaceType.CUSTOM.getIRISuffix()), SpaceType.CUSTOM, scopeID,
comment|//			topOntology);
comment|//}
comment|//
comment|//public CustomOntologySpaceImpl(IRI scopeID,
comment|//		OntologyInputSource topOntology, OWLOntologyManager ontologyManager) {
comment|//	super(IRI.create(StringUtils.stripIRITerminator(scopeID) + "/"
comment|//			+ SpaceType.CUSTOM.getIRISuffix()), SpaceType.CUSTOM, scopeID,
comment|//			ontologyManager, topOntology);
comment|//}
annotation|@
name|Override
specifier|public
name|void
name|attachCoreSpace
parameter_list|(
name|CoreOntologySpace
name|coreSpace
parameter_list|,
name|boolean
name|skipRoot
parameter_list|)
throws|throws
name|UnmodifiableOntologySpaceException
block|{
name|OWLOntology
name|o
init|=
name|coreSpace
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
comment|/** 	 * Once it is set up, a custom space is write-locked. 	 */
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
literal|true
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
name|locked
operator|=
literal|false
expr_stmt|;
block|}
block|}
end_class

end_unit

