begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|core
operator|.
name|processor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|ReferencePolicy
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|MappingModelParser
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|OntologyResourceHelper
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|MappingEngine
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|BridgeDefinitions
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|ConceptBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|PropertyBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|SubsumptionBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|AnnotationType
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|CMSObject
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|ClassificationObject
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DObject
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DObjectAdapter
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DProperty
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DPropertyDefinition
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|processor
operator|.
name|BaseProcessor
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|processor
operator|.
name|Processor
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|processor
operator|.
name|ProcessorProperties
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccess
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessException
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

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntClass
import|;
end_import

begin_comment
comment|/**  * This processor can process {@link ClassificationObject}s. On  * {@link #createDecoratedObjects(List, MappingEngine)} call, for each CMS Object of type  * {@link ClassificationObject} an OWL Class is created. Also if there is a property mapping defined in  * {@link BridgeDefinitions} then an instance of {@link PropertyProcesser} will be used to process these  * definitions.<br/>  * If there is a {@link SubsumptionBridge} service available, children of any {@link ClassificationObject}  * will be processed by subsumption processer.  *   * On {@link #deleteObjects(List, MappingEngine)} call, for each CMS Object of the type  * {@link ClassificationObject} the previously created resource is found and all the triples of which the  * resource is the subject is deleted.  *   * @author Suat  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|MANDATORY_MULTIPLE
argument_list|,
name|referenceInterface
operator|=
name|Processor
operator|.
name|class
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|bind
operator|=
literal|"bindProcessor"
argument_list|,
name|unbind
operator|=
literal|"unbindProcessor"
argument_list|,
name|name
operator|=
literal|"processor"
argument_list|)
specifier|public
class|class
name|ClassificationObjectProcesser
extends|extends
name|BaseProcessor
implements|implements
name|Processor
implements|,
name|ProcessorProperties
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ClassificationObjectProcesser
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
decl_stmt|;
static|static
block|{
name|properties
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|PROCESSING_ORDER
argument_list|,
name|CMSOBJECT_PRE
argument_list|)
expr_stmt|;
block|}
specifier|private
name|SubsumptionBridgesProcesser
name|subsumptionBridgeProcessor
decl_stmt|;
specifier|private
name|PropertyProcesser
name|propertyBridgeProcessor
init|=
operator|new
name|PropertyProcesser
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|createObjects
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|objects
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
block|{
name|List
argument_list|<
name|DObject
argument_list|>
name|cmsObjects
init|=
name|cmsObject2dobject
argument_list|(
name|objects
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|createDecoratedObjects
argument_list|(
name|cmsObjects
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|createDecoratedObjects
parameter_list|(
name|List
argument_list|<
name|DObject
argument_list|>
name|cmsObjects
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
block|{
comment|// if there is bridge definitions try to fetch concept bridges
if|if
condition|(
name|engine
operator|.
name|getBridgeDefinitions
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|ConceptBridge
argument_list|>
name|conceptBridges
init|=
name|MappingModelParser
operator|.
name|getConceptBridges
argument_list|(
name|engine
operator|.
name|getBridgeDefinitions
argument_list|()
argument_list|)
decl_stmt|;
name|RepositoryAccess
name|accessor
init|=
name|engine
operator|.
name|getRepositoryAccess
argument_list|()
decl_stmt|;
name|Object
name|session
init|=
name|engine
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|DObjectAdapter
name|adapter
init|=
name|engine
operator|.
name|getDObjectAdapter
argument_list|()
decl_stmt|;
name|boolean
name|emptyList
init|=
operator|(
name|cmsObjects
operator|==
literal|null
operator|||
name|cmsObjects
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
decl_stmt|;
for|for
control|(
name|ConceptBridge
name|cb
range|:
name|conceptBridges
control|)
block|{
comment|// cms objects will be null in the case of initial bridge execution or update of bridge
comment|// definitions i.e when a BridgeDefinitionsResource service is called
if|if
condition|(
name|emptyList
condition|)
block|{
try|try
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|retrievedObjects
init|=
name|accessor
operator|.
name|getNodeByPath
argument_list|(
name|cb
operator|.
name|getQuery
argument_list|()
argument_list|,
name|session
argument_list|)
decl_stmt|;
name|cmsObjects
operator|=
operator|new
name|ArrayList
argument_list|<
name|DObject
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|CMSObject
name|o
range|:
name|retrievedObjects
control|)
block|{
name|cmsObjects
operator|.
name|add
argument_list|(
name|adapter
operator|.
name|wrapAsDObject
argument_list|(
name|o
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error at processor"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to obtain CMS Objects for query {}"
argument_list|,
name|cb
operator|.
name|getQuery
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
block|}
for|for
control|(
name|DObject
name|cmsObject
range|:
name|cmsObjects
control|)
block|{
if|if
condition|(
name|matches
argument_list|(
name|cmsObject
operator|.
name|getPath
argument_list|()
argument_list|,
name|cb
operator|.
name|getQuery
argument_list|()
argument_list|)
condition|)
block|{
try|try
block|{
name|processConceptBridgeCreate
argument_list|(
name|cb
argument_list|,
name|cmsObject
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to process CMS Object {}"
argument_list|,
name|cmsObject
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
else|else
block|{
comment|// work without bridge definitions
for|for
control|(
name|DObject
name|cmsObject
range|:
name|cmsObjects
control|)
block|{
if|if
condition|(
name|canProcess
argument_list|(
name|cmsObject
operator|.
name|getInstance
argument_list|()
argument_list|)
condition|)
block|{
try|try
block|{
name|OntClass
name|parentClass
init|=
name|processObject
argument_list|(
name|cmsObject
argument_list|,
name|engine
argument_list|)
decl_stmt|;
if|if
condition|(
name|parentClass
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
name|processProperties
argument_list|(
name|cmsObject
argument_list|,
name|parentClass
argument_list|,
name|engine
argument_list|)
expr_stmt|;
comment|// process children
name|List
argument_list|<
name|DObject
argument_list|>
name|children
init|=
name|cmsObject
operator|.
name|getChildren
argument_list|()
decl_stmt|;
name|createDecoratedObjects
argument_list|(
name|children
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to process CMS Object {}"
argument_list|,
name|cmsObject
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|logger
operator|.
name|warn
argument_list|(
literal|"Message: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
specifier|private
name|OntClass
name|processObject
parameter_list|(
name|DObject
name|cmsObject
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|OntologyResourceHelper
name|orh
init|=
name|engine
operator|.
name|getOntologyResourceHelper
argument_list|()
decl_stmt|;
name|OntClass
name|parentClass
init|=
name|orh
operator|.
name|createOntClassByCMSObject
argument_list|(
name|cmsObject
operator|.
name|getInstance
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|parentClass
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed create class from CMS Object {}"
argument_list|,
name|cmsObject
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|List
argument_list|<
name|DObject
argument_list|>
name|children
init|=
name|cmsObject
operator|.
name|getChildren
argument_list|()
decl_stmt|;
if|if
condition|(
name|children
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|DObject
name|child
range|:
name|children
control|)
block|{
name|OntClass
name|childClass
init|=
name|orh
operator|.
name|createOntClassByCMSObject
argument_list|(
name|child
operator|.
name|getInstance
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|childClass
operator|!=
literal|null
condition|)
block|{
name|orh
operator|.
name|addSubsumptionAssertion
argument_list|(
name|parentClass
argument_list|,
name|childClass
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to create OntClass for child object {} while processing object {}"
argument_list|,
name|child
operator|.
name|getName
argument_list|()
argument_list|,
name|cmsObject
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|parentClass
return|;
block|}
specifier|private
name|void
name|processProperties
parameter_list|(
name|DObject
name|cmsObject
parameter_list|,
name|OntClass
name|subjectClass
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
for|for
control|(
name|DProperty
name|prop
range|:
name|cmsObject
operator|.
name|getProperties
argument_list|()
control|)
block|{
name|DPropertyDefinition
name|propDef
init|=
name|prop
operator|.
name|getDefinition
argument_list|()
decl_stmt|;
comment|// propDef returns null if a * named property comes
comment|// TODO after handling * named properties, remove the null check
if|if
condition|(
name|propDef
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to get property definition for property {}"
argument_list|,
name|prop
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|AnnotationType
name|annotationType
init|=
name|propDef
operator|.
name|getAnnotations
argument_list|()
decl_stmt|;
name|propertyBridgeProcessor
operator|.
name|processClassificationObjectProperty
argument_list|(
name|prop
argument_list|,
name|annotationType
argument_list|,
name|subjectClass
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|processConceptBridgeCreate
parameter_list|(
name|ConceptBridge
name|conceptBridge
parameter_list|,
name|DObject
name|cmsObject
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|OntClass
name|parentClass
init|=
name|processObject
argument_list|(
name|cmsObject
argument_list|,
name|engine
argument_list|)
decl_stmt|;
if|if
condition|(
name|parentClass
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|processInnerBridges
argument_list|(
name|conceptBridge
argument_list|,
name|cmsObject
argument_list|,
name|parentClass
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|processInnerBridges
parameter_list|(
name|ConceptBridge
name|conceptBridge
parameter_list|,
name|DObject
name|cmsObject
parameter_list|,
name|OntClass
name|parentClass
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// If SubsumptionBridgeExists....
if|if
condition|(
name|subsumptionBridgeProcessor
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|SubsumptionBridge
argument_list|>
name|subsumptionBridgeList
init|=
name|conceptBridge
operator|.
name|getSubsumptionBridge
argument_list|()
decl_stmt|;
for|for
control|(
name|SubsumptionBridge
name|bridge
range|:
name|subsumptionBridgeList
control|)
block|{
comment|/*                  * as subsumption assertions are already deleted before, we directly call create method to                  * prevent deletion of newly added assertions                  */
name|subsumptionBridgeProcessor
operator|.
name|processSubsumptionBridgeCreate
argument_list|(
name|bridge
operator|.
name|getPredicateName
argument_list|()
argument_list|,
name|cmsObject
argument_list|,
name|engine
argument_list|,
name|parentClass
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is no valid subsumption bridge processor"
argument_list|)
expr_stmt|;
block|}
comment|// If PropertyBridges Exist.....
if|if
condition|(
name|propertyBridgeProcessor
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|PropertyBridge
argument_list|>
name|propertyBridgeList
init|=
name|conceptBridge
operator|.
name|getPropertyBridge
argument_list|()
decl_stmt|;
for|for
control|(
name|PropertyBridge
name|bridge
range|:
name|propertyBridgeList
control|)
block|{
comment|// create subsumptionrelationships....
name|propertyBridgeProcessor
operator|.
name|processConceptPropertyBridgeCreate
argument_list|(
name|parentClass
argument_list|,
name|bridge
argument_list|,
name|cmsObject
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is no valid property bridge processor"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|deleteObjects
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|objects
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
block|{
name|List
argument_list|<
name|DObject
argument_list|>
name|cmsObjects
init|=
name|cmsObject2dobject
argument_list|(
name|objects
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|deleteDecoratedObjects
argument_list|(
name|cmsObjects
argument_list|,
name|engine
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|deleteDecoratedObjects
parameter_list|(
name|List
argument_list|<
name|DObject
argument_list|>
name|cmsObjects
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
block|{
name|OntologyResourceHelper
name|orh
init|=
name|engine
operator|.
name|getOntologyResourceHelper
argument_list|()
decl_stmt|;
comment|// if there is bridge definitions try to fetch concept bridges
if|if
condition|(
name|engine
operator|.
name|getBridgeDefinitions
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|ConceptBridge
argument_list|>
name|conceptBridges
init|=
name|MappingModelParser
operator|.
name|getConceptBridges
argument_list|(
name|engine
operator|.
name|getBridgeDefinitions
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|ConceptBridge
name|cb
range|:
name|conceptBridges
control|)
block|{
for|for
control|(
name|DObject
name|cmsObject
range|:
name|cmsObjects
control|)
block|{
if|if
condition|(
name|matches
argument_list|(
name|cmsObject
operator|.
name|getPath
argument_list|()
argument_list|,
name|cb
operator|.
name|getQuery
argument_list|()
argument_list|)
condition|)
block|{
name|orh
operator|.
name|deleteStatementsByReference
argument_list|(
name|cmsObject
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
else|else
block|{
for|for
control|(
name|DObject
name|cmsObject
range|:
name|cmsObjects
control|)
block|{
if|if
condition|(
name|canProcess
argument_list|(
name|cmsObject
operator|.
name|getInstance
argument_list|()
argument_list|)
condition|)
block|{
name|orh
operator|.
name|deleteStatementsByReference
argument_list|(
name|cmsObject
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|canProcess
parameter_list|(
name|Object
name|cmsObject
parameter_list|)
block|{
return|return
name|cmsObject
operator|instanceof
name|ClassificationObject
return|;
block|}
specifier|protected
name|void
name|bindProcessor
parameter_list|(
name|Processor
name|processor
parameter_list|)
block|{
name|String
name|processorName
init|=
name|processor
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|processorName
operator|.
name|contentEquals
argument_list|(
name|SubsumptionBridgesProcesser
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|subsumptionBridgeProcessor
operator|=
operator|(
name|SubsumptionBridgesProcesser
operator|)
name|processor
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|unbindProcessor
parameter_list|(
name|Processor
name|processor
parameter_list|)
block|{
name|this
operator|.
name|subsumptionBridgeProcessor
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getProcessorProperties
parameter_list|()
block|{
return|return
name|properties
return|;
block|}
specifier|private
name|List
argument_list|<
name|DObject
argument_list|>
name|cmsObject2dobject
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|objects
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
block|{
name|List
argument_list|<
name|DObject
argument_list|>
name|dObjects
init|=
operator|new
name|ArrayList
argument_list|<
name|DObject
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|objects
operator|!=
literal|null
condition|)
block|{
name|DObjectAdapter
name|adapter
init|=
name|engine
operator|.
name|getDObjectAdapter
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|o
range|:
name|objects
control|)
block|{
if|if
condition|(
name|canProcess
argument_list|(
name|o
argument_list|)
condition|)
block|{
name|dObjects
operator|.
name|add
argument_list|(
name|adapter
operator|.
name|wrapAsDObject
argument_list|(
operator|(
name|CMSObject
operator|)
name|o
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|dObjects
return|;
block|}
block|}
end_class

end_unit

