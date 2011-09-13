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
name|web
operator|.
name|resources
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|DELETE
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|DefaultValue
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|FormParam
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|POST
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|PUT
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|PathParam
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|QueryParam
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|WebApplicationException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|TcManager
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
name|core
operator|.
name|helper
operator|.
name|TcManagerClient
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
name|core
operator|.
name|mapping
operator|.
name|MappingConfigurationImpl
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
name|MappingConfiguration
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
name|CMSObjects
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
name|AdapterMode
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
name|web
operator|.
name|base
operator|.
name|ContextHelper
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
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|BaseStanbolResource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|InvalidSyntaxException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
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
name|ComponentFactory
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
name|ComponentInstance
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
name|OntModel
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
name|OntModelSpec
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
name|rdf
operator|.
name|model
operator|.
name|ModelFactory
import|;
end_import

begin_class
annotation|@
name|Path
argument_list|(
literal|"/cmsadapter/{ontologyURI:.+}/notify"
argument_list|)
specifier|public
class|class
name|NotifyResource
extends|extends
name|BaseStanbolResource
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
name|NotifyResource
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|MappingEngine
name|engine
decl_stmt|;
specifier|private
name|TcManager
name|tcManager
decl_stmt|;
specifier|public
name|NotifyResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
try|try
block|{
name|BundleContext
name|bundleContext
init|=
operator|(
name|BundleContext
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|BundleContext
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|ServiceReference
name|serviceReference
init|=
name|bundleContext
operator|.
name|getServiceReferences
argument_list|(
literal|null
argument_list|,
literal|"(component.factory=org.apache.stanbol.cmsadapter.servicesapi.mapping.MappingEngineFactory)"
argument_list|)
index|[
literal|0
index|]
decl_stmt|;
name|ComponentFactory
name|componentFactory
init|=
operator|(
name|ComponentFactory
operator|)
name|bundleContext
operator|.
name|getService
argument_list|(
name|serviceReference
argument_list|)
decl_stmt|;
name|ComponentInstance
name|componentInstance
init|=
name|componentFactory
operator|.
name|newInstance
argument_list|(
operator|new
name|Hashtable
argument_list|<
name|Object
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
name|this
operator|.
name|engine
operator|=
operator|(
name|MappingEngine
operator|)
name|componentInstance
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|this
operator|.
name|tcManager
operator|=
operator|(
name|TcManager
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|TcManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvalidSyntaxException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Mapping engine instance could not be instantiated"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|POST
specifier|public
name|Response
name|notifyCreate
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyURI"
argument_list|)
name|String
name|ontologyURI
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"createdObjects"
argument_list|)
name|CMSObjects
name|cmsObjects
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"adapterMode"
argument_list|)
name|AdapterMode
name|adapterMode
parameter_list|,
annotation|@
name|DefaultValue
argument_list|(
literal|"true"
argument_list|)
annotation|@
name|QueryParam
argument_list|(
literal|"considerBridges"
argument_list|)
name|boolean
name|considerBridges
parameter_list|)
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|createdObjectList
init|=
name|cmsObjects
operator|.
name|getClassificationObjectOrContentObject
argument_list|()
decl_stmt|;
name|TcManagerClient
name|tcManagerClient
init|=
operator|new
name|TcManagerClient
argument_list|(
name|tcManager
argument_list|)
decl_stmt|;
name|OntModel
name|model
init|=
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|(
name|OntModelSpec
operator|.
name|OWL_DL_MEM
argument_list|,
name|tcManagerClient
operator|.
name|getModel
argument_list|(
name|ontologyURI
argument_list|)
argument_list|)
decl_stmt|;
name|MappingConfiguration
name|conf
init|=
operator|new
name|MappingConfigurationImpl
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setOntModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setOntologyURI
argument_list|(
name|ontologyURI
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setObjects
argument_list|(
call|(
name|List
argument_list|<
name|Object
argument_list|>
call|)
argument_list|(
name|List
argument_list|<
name|?
argument_list|>
argument_list|)
name|createdObjectList
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setAdapterMode
argument_list|(
name|adapterMode
argument_list|)
expr_stmt|;
if|if
condition|(
name|considerBridges
condition|)
block|{
name|conf
operator|.
name|setBridgeDefinitions
argument_list|(
name|OntologyResourceHelper
operator|.
name|getBridgeDefinitions
argument_list|(
name|model
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|engine
operator|.
name|createModel
argument_list|(
name|conf
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * Specified {@link CMSObject}s to this resource will be updated by executing previously defined bridges.      * Bridge definitions are obtained from the ontology model that specified with<i>ontologyURI</i>      *       * @param ontologyURI      * @param cmsObjects      * @return      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|PUT
specifier|public
name|Response
name|notifyUpdate
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyURI"
argument_list|)
name|String
name|ontologyURI
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"updatedObjects"
argument_list|)
name|CMSObjects
name|cmsObjects
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"adapterMode"
argument_list|)
name|AdapterMode
name|adapterMode
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"considerBridges"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
literal|"true"
argument_list|)
name|Boolean
name|considerBridges
parameter_list|)
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|updatedObjectList
init|=
name|cmsObjects
operator|.
name|getClassificationObjectOrContentObject
argument_list|()
decl_stmt|;
name|TcManagerClient
name|tcManagerClient
init|=
operator|new
name|TcManagerClient
argument_list|(
name|tcManager
argument_list|)
decl_stmt|;
name|OntModel
name|model
init|=
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|(
name|OntModelSpec
operator|.
name|OWL_DL_MEM
argument_list|,
name|tcManagerClient
operator|.
name|getModel
argument_list|(
name|ontologyURI
argument_list|)
argument_list|)
decl_stmt|;
name|MappingConfiguration
name|conf
init|=
operator|new
name|MappingConfigurationImpl
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setOntModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setOntologyURI
argument_list|(
name|ontologyURI
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setObjects
argument_list|(
call|(
name|List
argument_list|<
name|Object
argument_list|>
call|)
argument_list|(
name|List
argument_list|<
name|?
argument_list|>
argument_list|)
name|updatedObjectList
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setAdapterMode
argument_list|(
name|adapterMode
argument_list|)
expr_stmt|;
if|if
condition|(
name|considerBridges
condition|)
block|{
name|conf
operator|.
name|setBridgeDefinitions
argument_list|(
name|OntologyResourceHelper
operator|.
name|getBridgeDefinitions
argument_list|(
name|model
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|engine
operator|.
name|updateModel
argument_list|(
name|conf
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * Specified {@link CMSObject}s to this resource will be deleted from the generated ontology.      *       * @param ontologyURI      * @param cmsObjects      * @return      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|DELETE
specifier|public
name|Response
name|notifyDelete
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyURI"
argument_list|)
name|String
name|ontologyURI
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"deletedObjects"
argument_list|)
name|CMSObjects
name|cmsObjects
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"considerBridges"
argument_list|)
annotation|@
name|DefaultValue
argument_list|(
literal|"true"
argument_list|)
name|Boolean
name|considerBridges
parameter_list|)
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|deletedObjectList
init|=
name|cmsObjects
operator|.
name|getClassificationObjectOrContentObject
argument_list|()
decl_stmt|;
name|TcManagerClient
name|tcManagerClient
init|=
operator|new
name|TcManagerClient
argument_list|(
name|tcManager
argument_list|)
decl_stmt|;
name|OntModel
name|model
init|=
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|(
name|OntModelSpec
operator|.
name|OWL_DL_MEM
argument_list|,
name|tcManagerClient
operator|.
name|getModel
argument_list|(
name|ontologyURI
argument_list|)
argument_list|)
decl_stmt|;
name|MappingConfiguration
name|conf
init|=
operator|new
name|MappingConfigurationImpl
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setOntModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setOntologyURI
argument_list|(
name|ontologyURI
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setObjects
argument_list|(
call|(
name|List
argument_list|<
name|Object
argument_list|>
call|)
argument_list|(
name|List
argument_list|<
name|?
argument_list|>
argument_list|)
name|deletedObjectList
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setAdapterMode
argument_list|(
name|AdapterMode
operator|.
name|STRICT_OFFLINE
argument_list|)
expr_stmt|;
if|if
condition|(
name|considerBridges
condition|)
block|{
name|conf
operator|.
name|setBridgeDefinitions
argument_list|(
name|OntologyResourceHelper
operator|.
name|getBridgeDefinitions
argument_list|(
name|model
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|engine
operator|.
name|deleteModel
argument_list|(
name|conf
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
block|}
end_class

end_unit

