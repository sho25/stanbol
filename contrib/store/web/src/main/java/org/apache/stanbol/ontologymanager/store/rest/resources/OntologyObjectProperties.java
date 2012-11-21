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
name|ontologymanager
operator|.
name|store
operator|.
name|rest
operator|.
name|resources
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
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
name|Consumes
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
name|GET
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
name|Produces
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
name|MediaType
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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
operator|.
name|Status
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
name|LockManager
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
name|PersistenceStore
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
name|ResourceManager
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
name|model
operator|.
name|ObjectPropertiesForOntology
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
name|model
operator|.
name|PropertyMetaInformation
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
name|rest
operator|.
name|LockManagerImp
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
name|rest
operator|.
name|ResourceManagerImp
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|ldviewable
operator|.
name|Viewable
import|;
end_import

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontology/{ontologyPath:.+}/objectProperties"
argument_list|)
specifier|public
class|class
name|OntologyObjectProperties
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
name|OntologyObjectProperties
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|VIEWABLE_PATH
init|=
literal|"/org/apache/stanbol/ontologymanager/store/rest/resources/ontologyObjectProperties"
decl_stmt|;
specifier|private
name|PersistenceStore
name|persistenceStore
decl_stmt|;
comment|// HTML View Variable
specifier|private
name|ObjectPropertiesForOntology
name|metadata
decl_stmt|;
specifier|public
name|OntologyObjectProperties
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|this
operator|.
name|persistenceStore
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|PersistenceStore
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_XML
argument_list|)
specifier|public
name|Response
name|retrieveOntologyObjectProperties
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|)
block|{
name|Response
name|response
init|=
literal|null
decl_stmt|;
name|LockManager
name|lockManager
init|=
name|LockManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|lockManager
operator|.
name|obtainReadLockFor
argument_list|(
name|ontologyPath
argument_list|)
expr_stmt|;
try|try
block|{
name|ResourceManager
name|resourceManager
init|=
name|ResourceManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|ontologyURI
init|=
name|resourceManager
operator|.
name|getOntologyURIForPath
argument_list|(
name|ontologyPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|ontologyURI
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|NOT_FOUND
argument_list|)
throw|;
block|}
try|try
block|{
name|ObjectPropertiesForOntology
name|objectPropertiesForOntology
init|=
name|persistenceStore
operator|.
name|retrieveObjectPropertiesOfOntology
argument_list|(
name|ontologyURI
argument_list|)
decl_stmt|;
name|response
operator|=
name|Response
operator|.
name|ok
argument_list|(
name|objectPropertiesForOntology
argument_list|,
name|MediaType
operator|.
name|APPLICATION_XML_TYPE
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error "
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
finally|finally
block|{
name|lockManager
operator|.
name|releaseReadLockFor
argument_list|(
name|ontologyPath
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
annotation|@
name|POST
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_XML
argument_list|)
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|generateDatatypeProperty
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"objectPropertyURI"
argument_list|)
name|String
name|objectPropertyURI
parameter_list|)
block|{
name|Response
name|response
init|=
literal|null
decl_stmt|;
name|LockManager
name|lockManager
init|=
name|LockManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|lockManager
operator|.
name|obtainWriteLockFor
argument_list|(
name|ontologyPath
argument_list|)
expr_stmt|;
try|try
block|{
name|ResourceManager
name|resourceManager
init|=
name|ResourceManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|ontologyURI
init|=
name|resourceManager
operator|.
name|getOntologyURIForPath
argument_list|(
name|ontologyPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|ontologyURI
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|NOT_FOUND
argument_list|)
throw|;
block|}
try|try
block|{
name|PropertyMetaInformation
name|objectPropertyMetaInformation
init|=
name|persistenceStore
operator|.
name|generateObjectPropertyForOntology
argument_list|(
name|ontologyURI
argument_list|,
name|objectPropertyURI
argument_list|)
decl_stmt|;
name|response
operator|=
name|Response
operator|.
name|ok
argument_list|(
name|objectPropertyMetaInformation
argument_list|,
name|MediaType
operator|.
name|APPLICATION_XML_TYPE
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error "
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
finally|finally
block|{
name|lockManager
operator|.
name|releaseWriteLockFor
argument_list|(
name|ontologyPath
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
comment|// HTML View Methods
specifier|public
name|ObjectPropertiesForOntology
name|getMetadata
parameter_list|()
block|{
return|return
name|metadata
return|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|";qs=2"
argument_list|)
specifier|public
name|Viewable
name|getViewable
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|)
block|{
name|Response
name|response
init|=
name|retrieveOntologyObjectProperties
argument_list|(
name|ontologyPath
argument_list|)
decl_stmt|;
name|metadata
operator|=
operator|(
name|ObjectPropertiesForOntology
operator|)
name|response
operator|.
name|getEntity
argument_list|()
expr_stmt|;
return|return
operator|new
name|Viewable
argument_list|(
name|VIEWABLE_PATH
argument_list|,
name|this
argument_list|)
return|;
block|}
annotation|@
name|POST
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|";qs=2"
argument_list|)
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|createAndRedirect
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"objectPropertyURI"
argument_list|)
name|String
name|objectPropertyURI
parameter_list|)
block|{
name|Response
name|response
init|=
name|this
operator|.
name|generateDatatypeProperty
argument_list|(
name|ontologyPath
argument_list|,
name|objectPropertyURI
argument_list|)
decl_stmt|;
name|PropertyMetaInformation
name|pmi
init|=
operator|(
name|PropertyMetaInformation
operator|)
name|response
operator|.
name|getEntity
argument_list|()
decl_stmt|;
try|try
block|{
return|return
name|Response
operator|.
name|seeOther
argument_list|(
name|URI
operator|.
name|create
argument_list|(
name|pmi
operator|.
name|getHref
argument_list|()
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

