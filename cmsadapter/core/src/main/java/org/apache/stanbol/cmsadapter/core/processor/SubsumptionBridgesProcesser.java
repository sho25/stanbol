begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
specifier|public
class|class
name|SubsumptionBridgesProcesser
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
name|SubsumptionBridgesProcesser
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
name|object2dobject
argument_list|(
name|objects
argument_list|,
name|engine
argument_list|)
decl_stmt|;
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
name|DObjectAdapter
name|adapter
init|=
name|engine
operator|.
name|getDObjectAdapter
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|SubsumptionBridge
argument_list|>
name|subsumptionBridges
init|=
name|MappingModelParser
operator|.
name|getSubsumptionBridges
argument_list|(
name|engine
operator|.
name|getBridgeDefinitions
argument_list|()
argument_list|)
decl_stmt|;
name|Object
name|session
init|=
name|engine
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|RepositoryAccess
name|accessor
init|=
name|engine
operator|.
name|getRepositoryAccess
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
name|SubsumptionBridge
name|sb
range|:
name|subsumptionBridges
control|)
block|{
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
name|sb
operator|.
name|getSubjectQuery
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
literal|"Failed to obtain CMS Objects for query {}"
argument_list|,
name|sb
operator|.
name|getSubjectQuery
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
name|sb
operator|.
name|getSubjectQuery
argument_list|()
argument_list|)
condition|)
block|{
try|try
block|{
name|processSubsumptionBridgeCreate
argument_list|(
name|sb
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
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
specifier|private
name|void
name|processSubsumptionBridgeCreate
parameter_list|(
name|SubsumptionBridge
name|s
parameter_list|,
name|DObject
name|parentObject
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
name|parentObject
operator|.
name|getInstance
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|parentClass
operator|!=
literal|null
condition|)
block|{
name|processSubsumptionBridgeCreate
argument_list|(
name|s
operator|.
name|getPredicateName
argument_list|()
argument_list|,
name|parentObject
argument_list|,
name|engine
argument_list|,
name|parentClass
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to create OntClass for CMS Object {} while processing bridges for creation"
argument_list|,
name|parentObject
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|processSubsumptionBridgeCreate
parameter_list|(
name|String
name|predicateName
parameter_list|,
name|DObject
name|parentObject
parameter_list|,
name|MappingEngine
name|engine
parameter_list|,
name|OntClass
name|parentClass
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
if|if
condition|(
name|predicateName
operator|.
name|equals
argument_list|(
literal|"child"
argument_list|)
condition|)
block|{
comment|// find all child nodes of the parentMode
for|for
control|(
name|DObject
name|childObject
range|:
name|parentObject
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|OntClass
name|childClass
init|=
name|orh
operator|.
name|createOntClassByCMSObject
argument_list|(
name|childObject
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
literal|"Failed to create OntClass for child object {} while processing CMS Object"
argument_list|,
name|childObject
operator|.
name|getName
argument_list|()
argument_list|,
name|parentObject
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// find the ranges of the predicate whose subject is parentNode
for|for
control|(
name|DProperty
name|property
range|:
name|parentObject
operator|.
name|getProperties
argument_list|()
control|)
block|{
name|DPropertyDefinition
name|propDef
init|=
name|property
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
literal|"Property definition could not be got for property {}"
argument_list|,
name|property
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|propName
init|=
name|propDef
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|propName
operator|.
name|equals
argument_list|(
name|predicateName
argument_list|)
operator|||
name|propName
operator|.
name|contains
argument_list|(
name|predicateName
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|referencedObjects
init|=
name|propertyBridgeProcessor
operator|.
name|resolveReferenceNodes
argument_list|(
name|property
argument_list|,
name|engine
argument_list|)
decl_stmt|;
for|for
control|(
name|CMSObject
name|o
range|:
name|referencedObjects
control|)
block|{
name|OntClass
name|childClass
init|=
name|orh
operator|.
name|createOntClassByCMSObject
argument_list|(
name|o
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
literal|"Failed to create OntClass for referenced object {} while processing {}"
argument_list|,
name|o
operator|.
name|getLocalname
argument_list|()
argument_list|,
name|parentObject
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
break|break;
block|}
elseif|else
if|if
condition|(
name|propName
operator|.
name|contentEquals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Properties added to nt:unstructured types (* named properties) are not handled yet"
argument_list|)
expr_stmt|;
block|}
block|}
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
name|DObject
argument_list|>
name|cmsObjects
init|=
name|object2dobject
argument_list|(
name|objects
argument_list|,
name|engine
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|SubsumptionBridge
argument_list|>
name|subsumptionBridges
init|=
name|MappingModelParser
operator|.
name|getSubsumptionBridges
argument_list|(
name|engine
operator|.
name|getBridgeDefinitions
argument_list|()
argument_list|)
decl_stmt|;
name|OntologyResourceHelper
name|orh
init|=
name|engine
operator|.
name|getOntologyResourceHelper
argument_list|()
decl_stmt|;
for|for
control|(
name|SubsumptionBridge
name|sb
range|:
name|subsumptionBridges
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
name|sb
operator|.
name|getSubjectQuery
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
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|canProcess
parameter_list|(
name|Object
name|object
parameter_list|,
name|Object
name|session
parameter_list|)
block|{
return|return
name|object
operator|instanceof
name|ClassificationObject
return|;
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
name|object2dobject
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
argument_list|,
literal|null
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

