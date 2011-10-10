begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
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
name|Deactivate
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
name|Reference
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
name|ReferenceCardinality
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
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProvider
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
name|explanation
operator|.
name|api
operator|.
name|Configuration
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
name|explanation
operator|.
name|api
operator|.
name|Explainable
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
name|explanation
operator|.
name|api
operator|.
name|Explanation
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
name|explanation
operator|.
name|api
operator|.
name|ExplanationGenerator
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
name|explanation
operator|.
name|api
operator|.
name|ExplanationTypes
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
name|explanation
operator|.
name|api
operator|.
name|KnowledgeItem
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
name|DuplicateIDException
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
name|ONManager
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
name|BlankOntologySource
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
name|io
operator|.
name|RootOntologyIRISource
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
name|OWLAxiom
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
argument_list|(
name|ExplanationGenerator
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ExplanationGeneratorImpl
implements|implements
name|ExplanationGenerator
block|{
specifier|private
specifier|static
specifier|final
name|String
name|_EXPLANATION_SCHEMA
init|=
literal|"http://ontologydesignpatterns.org/schemas/explanationschema.owl"
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|MANDATORY_UNARY
argument_list|)
specifier|private
name|Configuration
name|config
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|ONManager
name|onm
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|DataFileProvider
name|dataFileProvider
decl_stmt|;
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
comment|/**      * This default constructor is<b>only</b> intended to be used by the OSGI environment with Service      * Component Runtime support.      *<p>      * DO NOT USE to manually create instances - the ExplanationGeneratorImpl instances do need to be      * configured! YOU NEED TO USE {@link #ExplanationGeneratorImpl(ONManager, DataFileProvider, Dictionary)}      * or its overloads, to parse the configuration and then initialise the rule store if running outside an      * OSGI environment.      */
specifier|public
name|ExplanationGeneratorImpl
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * To be invoked by non-OSGi environments.      *       * @param onManager      * @param config      * @param dataFileProvider      * @param configuration      */
specifier|public
name|ExplanationGeneratorImpl
parameter_list|(
name|Configuration
name|config
parameter_list|,
name|DataFileProvider
name|dataFileProvider
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
name|this
operator|.
name|onm
operator|=
name|config
operator|.
name|getOntologyNetworkManager
argument_list|()
expr_stmt|;
name|this
operator|.
name|dataFileProvider
operator|=
name|dataFileProvider
expr_stmt|;
try|try
block|{
name|activate
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to access servlet context."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Used to configure an instance within an OSGi container.      *       * @throws IOException      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|ExplanationGeneratorImpl
operator|.
name|class
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No valid"
operator|+
name|ComponentContext
operator|.
name|class
operator|+
literal|" parsed in activate!"
argument_list|)
throw|;
block|}
name|activate
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called within both OSGi and non-OSGi environments.      *       * @param configuration      * @throws IOException      */
specifier|protected
name|void
name|activate
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
throws|throws
name|IOException
block|{
comment|// OntoNetSimulator sim = new OntoNetSimulator();
comment|// Create and register the scope for explanations.
name|String
name|scopeid
init|=
name|config
operator|.
name|getScopeID
argument_list|()
decl_stmt|;
name|OntologyScope
name|scope
init|=
literal|null
decl_stmt|;
try|try
block|{
name|OntologyInputSource
name|coreSrc
decl_stmt|;
try|try
block|{
name|coreSrc
operator|=
operator|new
name|RootOntologyIRISource
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|_EXPLANATION_SCHEMA
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|coreSrc
operator|=
operator|new
name|BlankOntologySource
argument_list|()
expr_stmt|;
block|}
name|scope
operator|=
name|onm
operator|.
name|getOntologyScopeFactory
argument_list|()
operator|.
name|createOntologyScope
argument_list|(
name|scopeid
argument_list|,
name|coreSrc
argument_list|)
expr_stmt|;
name|onm
operator|.
name|getScopeRegistry
argument_list|()
operator|.
name|registerScope
argument_list|(
name|scope
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Cannot create scope {}. A scope with this ID is already registered."
argument_list|,
name|scopeid
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|scope
operator|!=
literal|null
condition|)
block|{
try|try
block|{
if|if
condition|(
name|dataFileProvider
operator|!=
literal|null
condition|)
name|dataFileProvider
operator|.
name|getInputStream
argument_list|(
literal|null
argument_list|,
literal|"organizationalhierarchy.owl"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"FAiled to get file"
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Explanation Generator activated."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Explanation
name|createExplanation
parameter_list|(
name|Explainable
argument_list|<
name|?
argument_list|>
name|item
parameter_list|,
name|ExplanationTypes
name|type
parameter_list|,
name|Set
argument_list|<
name|?
extends|extends
name|OWLAxiom
argument_list|>
name|grounds
parameter_list|)
block|{
name|Explanation
name|result
init|=
operator|new
name|ExplanationImpl
argument_list|(
name|item
argument_list|,
name|type
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|KNOWLEDGE_OBJECT_SYNOPSIS
case|:
if|if
condition|(
operator|!
operator|(
name|item
operator|instanceof
name|KnowledgeItem
operator|)
condition|)
break|break;
name|KnowledgeItem
name|ki
init|=
operator|(
name|KnowledgeItem
operator|)
name|item
decl_stmt|;
break|break;
case|case
name|UI_FEEDBACK_JUSTIFICATION
case|:
break|break;
block|}
return|return
name|result
return|;
block|}
comment|/**      * Deactivation of the ONManagerImpl resets all its resources.      */
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|ExplanationGeneratorImpl
operator|.
name|class
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

