begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|hermit
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|reasoners
operator|.
name|owlapi
operator|.
name|AbstractOWLApiReasoningService
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
name|reasoners
operator|.
name|owlapi
operator|.
name|OWLApiReasoningService
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|HermiT
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|HermiT
operator|.
name|Reasoner
operator|.
name|ReasonerFactory
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
name|reasoner
operator|.
name|OWLReasoner
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
comment|/**  * This class is the implementation of {@see OWLApiReasoningService} using the HermiT reasoner  *   * @author enridaga  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|HermitReasoningService
extends|extends
name|AbstractOWLApiReasoningService
implements|implements
name|OWLApiReasoningService
block|{
specifier|private
specifier|final
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
specifier|public
specifier|static
specifier|final
name|String
name|_DEFAULT_PATH
init|=
literal|"owl2"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ReasoningService
operator|.
name|SERVICE_PATH
argument_list|,
name|value
operator|=
name|_DEFAULT_PATH
argument_list|)
specifier|private
name|String
name|path
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|OWLReasoner
name|getReasoner
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Creating HermiT reasoner: {}"
argument_list|,
name|ontology
argument_list|)
expr_stmt|;
name|Configuration
name|config
init|=
operator|new
name|Configuration
argument_list|()
decl_stmt|;
name|config
operator|.
name|ignoreUnsupportedDatatypes
operator|=
literal|true
expr_stmt|;
comment|// This must be true!
name|config
operator|.
name|throwInconsistentOntologyException
operator|=
literal|true
expr_stmt|;
comment|// This must be true!
comment|//config.monitor = new Debugger(null, false);
name|log
operator|.
name|debug
argument_list|(
literal|"Configuration: {}, debugger {}"
argument_list|,
name|config
argument_list|,
name|config
operator|.
name|monitor
argument_list|)
expr_stmt|;
name|ReasonerFactory
name|risfactory
init|=
operator|new
name|ReasonerFactory
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"factory: {}"
argument_list|,
name|risfactory
argument_list|)
expr_stmt|;
name|OWLReasoner
name|reasoner
init|=
literal|null
decl_stmt|;
name|reasoner
operator|=
name|risfactory
operator|.
name|createReasoner
argument_list|(
name|ontology
argument_list|,
name|config
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Reasoner : {}"
argument_list|,
name|reasoner
argument_list|)
expr_stmt|;
if|if
condition|(
name|reasoner
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Cannot create the reasner!!"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot create the reasoner"
argument_list|)
throw|;
block|}
return|return
name|reasoner
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPath
parameter_list|()
block|{
return|return
name|path
return|;
block|}
annotation|@
name|Activate
specifier|public
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|this
operator|.
name|path
operator|=
operator|(
name|String
operator|)
name|context
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|ReasoningService
operator|.
name|SERVICE_PATH
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

