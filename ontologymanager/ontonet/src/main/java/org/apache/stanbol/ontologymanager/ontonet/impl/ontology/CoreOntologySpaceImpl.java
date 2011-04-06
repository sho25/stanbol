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
name|OWLOntologyManager
import|;
end_import

begin_class
specifier|public
class|class
name|CoreOntologySpaceImpl
extends|extends
name|AbstractOntologySpaceImpl
implements|implements
name|CoreOntologySpace
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SUFFIX
init|=
name|SpaceType
operator|.
name|CORE
operator|.
name|getIRISuffix
argument_list|()
decl_stmt|;
comment|//	static {
comment|//		SUFFIX = SpaceType.CORE.getIRISuffix();
comment|//	}
specifier|public
name|CoreOntologySpaceImpl
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|ClerezzaOntologyStorage
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
name|CORE
operator|.
name|getIRISuffix
argument_list|()
argument_list|)
argument_list|,
name|SpaceType
operator|.
name|CORE
comment|/*, scopeID*/
argument_list|,
name|storage
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CoreOntologySpaceImpl
parameter_list|(
name|IRI
name|scopeID
parameter_list|,
name|ClerezzaOntologyStorage
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
name|CORE
operator|.
name|getIRISuffix
argument_list|()
argument_list|)
argument_list|,
name|SpaceType
operator|.
name|CORE
argument_list|,
comment|/*scopeID,*/
name|storage
argument_list|,
name|ontologyManager
argument_list|)
expr_stmt|;
block|}
comment|//	public CoreOntologySpaceImpl(IRI scopeID, OntologyInputSource topOntology) {
comment|//		super(IRI.create(StringUtils.stripIRITerminator(scopeID) + "/"
comment|//				+ SpaceType.CORE.getIRISuffix()), SpaceType.CORE, scopeID,
comment|//				topOntology);
comment|//	}
comment|//
comment|//	public CoreOntologySpaceImpl(IRI scopeID, OntologyInputSource topOntology,
comment|//			OWLOntologyManager ontologyManager) {
comment|//		super(IRI.create(StringUtils.stripIRITerminator(scopeID) + "/"
comment|//				+ SpaceType.CORE.getIRISuffix()), SpaceType.CORE, scopeID,
comment|//				ontologyManager, topOntology);
comment|//	}
comment|/** 	 * When set up, a core space is write-locked. 	 */
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
comment|/** 	 * When torn down, a core space releases its write-lock. 	 */
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

