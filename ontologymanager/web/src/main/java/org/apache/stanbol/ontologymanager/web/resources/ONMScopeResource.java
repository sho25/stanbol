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
name|web
operator|.
name|resources
package|;
end_package

begin_import
import|import static
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
operator|.
name|*
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
name|HttpHeaders
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
name|UriInfo
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
name|format
operator|.
name|KRFormat
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
name|OntologyScopeFactory
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
name|registry
operator|.
name|api
operator|.
name|RegistryLoader
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
name|registry
operator|.
name|api
operator|.
name|RegistryManager
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
name|registry
operator|.
name|impl
operator|.
name|RegistryLoaderImpl
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
name|registry
operator|.
name|io
operator|.
name|RegistryIRISource
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
name|Path
argument_list|(
literal|"/ontonet/ontology/{scopeid}"
argument_list|)
specifier|public
class|class
name|ONMScopeResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
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
comment|/*      * Placeholder for the ONManager to be fetched from the servlet context.      */
specifier|protected
name|ONManager
name|onm
decl_stmt|;
comment|/*      * Placeholder for the ONManager to be fetched from the servlet context.      */
specifier|protected
name|RegistryManager
name|regMgr
decl_stmt|;
specifier|protected
name|RegistryLoader
name|loader
decl_stmt|;
specifier|protected
name|ClerezzaOntologyStorage
name|storage
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|public
name|ONMScopeResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|this
operator|.
name|servletContext
operator|=
name|servletContext
expr_stmt|;
name|this
operator|.
name|onm
operator|=
operator|(
name|ONManager
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ONManager
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|regMgr
operator|=
operator|(
name|RegistryManager
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|RegistryManager
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
expr_stmt|;
name|loader
operator|=
operator|new
name|RegistryLoaderImpl
argument_list|(
name|regMgr
argument_list|,
name|onm
argument_list|)
expr_stmt|;
name|this
operator|.
name|storage
operator|=
operator|(
name|ClerezzaOntologyStorage
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|ClerezzaOntologyStorage
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
expr_stmt|;
block|}
annotation|@
name|DELETE
specifier|public
name|void
name|deregisterScope
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"scopeid"
argument_list|)
name|String
name|scopeid
parameter_list|,
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScope
name|scope
init|=
name|reg
operator|.
name|getScope
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|scope
operator|==
literal|null
condition|)
return|return;
name|reg
operator|.
name|deregisterScope
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|value
operator|=
block|{
name|KRFormat
operator|.
name|RDF_XML
block|,
name|KRFormat
operator|.
name|OWL_XML
block|,
name|KRFormat
operator|.
name|TURTLE
block|,
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
block|,
name|KRFormat
operator|.
name|MANCHESTER_OWL
block|,
name|KRFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
name|Response
name|getTopOntology
parameter_list|(
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScope
name|scope
init|=
name|reg
operator|.
name|getScope
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|scope
operator|==
literal|null
condition|)
return|return
name|Response
operator|.
name|status
argument_list|(
literal|404
argument_list|)
operator|.
name|build
argument_list|()
return|;
name|OntologySpace
name|cs
init|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
decl_stmt|;
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|cs
operator|!=
literal|null
condition|)
name|ont
operator|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getTopOntology
argument_list|()
expr_stmt|;
if|if
condition|(
name|ont
operator|==
literal|null
condition|)
name|ont
operator|=
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getTopOntology
argument_list|()
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
name|ont
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|POST
comment|// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
specifier|public
name|Response
name|loadCustomOntology
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"scopeid"
argument_list|)
name|String
name|scopeid
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"location"
argument_list|)
name|String
name|physIri
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"registry"
argument_list|)
name|boolean
name|asRegistry
parameter_list|,
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|IRI
name|scopeiri
init|=
literal|null
decl_stmt|;
name|IRI
name|ontoiri
init|=
literal|null
decl_stmt|;
try|try
block|{
name|scopeiri
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
name|ontoiri
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|physIri
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
comment|// Malformed IRI, throw bad request.
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|ex
argument_list|,
name|BAD_REQUEST
argument_list|)
throw|;
block|}
if|if
condition|(
name|reg
operator|.
name|containsScope
argument_list|(
name|scopeiri
argument_list|)
condition|)
block|{
name|OntologyScope
name|scope
init|=
name|reg
operator|.
name|getScope
argument_list|(
name|scopeiri
argument_list|)
decl_stmt|;
try|try
block|{
name|OntologyInputSource
name|src
init|=
operator|new
name|RootOntologyIRISource
argument_list|(
name|ontoiri
argument_list|)
decl_stmt|;
name|OntologySpace
name|space
init|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
decl_stmt|;
if|if
condition|(
name|space
operator|==
literal|null
condition|)
block|{
name|space
operator|=
name|onm
operator|.
name|getOntologySpaceFactory
argument_list|()
operator|.
name|createCustomOntologySpace
argument_list|(
name|scopeiri
argument_list|,
name|src
argument_list|)
expr_stmt|;
name|scope
operator|.
name|setCustomSpace
argument_list|(
name|space
argument_list|)
expr_stmt|;
comment|// space.setUp();
block|}
else|else
name|space
operator|.
name|addOntology
argument_list|(
name|src
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
else|else
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|NOT_FOUND
argument_list|)
throw|;
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
comment|/**      * At least one between corereg and coreont must be present. Registry iris supersede ontology iris.      *       * @param scopeid      * @param coreRegistry      *            a. If it is a well-formed IRI it supersedes<code>coreOntology</code>.      * @param coreOntology      * @param customRegistry      *            a. If it is a well-formed IRI it supersedes<code>customOntology</code>.      * @param customOntology      * @param activate      *            if true, the new scope will be activated upon creation.      * @param uriInfo      * @param headers      * @return      */
annotation|@
name|PUT
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|WILDCARD
argument_list|)
specifier|public
name|Response
name|registerScope
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"scopeid"
argument_list|)
name|String
name|scopeid
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"corereg"
argument_list|)
name|String
name|coreRegistry
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"coreont"
argument_list|)
name|String
name|coreOntology
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"customreg"
argument_list|)
name|String
name|customRegistry
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"customont"
argument_list|)
name|String
name|customOntology
parameter_list|,
annotation|@
name|DefaultValue
argument_list|(
literal|"false"
argument_list|)
annotation|@
name|QueryParam
argument_list|(
literal|"activate"
argument_list|)
name|String
name|activate
parameter_list|,
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScopeFactory
name|f
init|=
name|onm
operator|.
name|getOntologyScopeFactory
argument_list|()
decl_stmt|;
name|OntologyScope
name|scope
decl_stmt|;
name|OntologyInputSource
name|coreSrc
init|=
literal|null
decl_stmt|,
name|custSrc
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|coreOntology
operator|==
literal|null
operator|&&
name|coreRegistry
operator|==
literal|null
condition|)
block|{
name|coreSrc
operator|=
operator|new
name|BlankOntologySource
argument_list|()
expr_stmt|;
block|}
comment|// First thing, check the core source.
try|try
block|{
name|coreSrc
operator|=
operator|new
name|RegistryIRISource
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|coreRegistry
argument_list|)
argument_list|,
name|onm
operator|.
name|getOwlCacheManager
argument_list|()
argument_list|,
name|loader
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
comment|// Bad or not supplied core registry, try the ontology.
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
name|coreOntology
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e2
parameter_list|)
block|{
comment|// If this fails too, throw a bad request.
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e2
argument_list|,
name|BAD_REQUEST
argument_list|)
throw|;
block|}
block|}
comment|// Don't bother if no custom was supplied at all...
if|if
condition|(
name|customOntology
operator|!=
literal|null
operator|||
name|customRegistry
operator|!=
literal|null
condition|)
block|{
comment|// ...but if it was, be prepared to throw exceptions.
try|try
block|{
name|custSrc
operator|=
operator|new
name|RegistryIRISource
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|customRegistry
argument_list|)
argument_list|,
name|onm
operator|.
name|getOwlCacheManager
argument_list|()
argument_list|,
name|loader
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
comment|// Bad or not supplied custom registry, try the ontology.
try|try
block|{
name|custSrc
operator|=
operator|new
name|RootOntologyIRISource
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|customOntology
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e2
parameter_list|)
block|{
comment|// If this fails too, throw a bad request.
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e2
argument_list|,
name|BAD_REQUEST
argument_list|)
throw|;
block|}
block|}
block|}
comment|// Now the creation.
try|try
block|{
name|IRI
name|scopeId
init|=
name|IRI
operator|.
name|create
argument_list|(
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
decl_stmt|;
comment|// Invoke the appropriate factory method depending on the
comment|// availability of a custom source.
name|scope
operator|=
operator|(
name|custSrc
operator|!=
literal|null
operator|)
condition|?
name|f
operator|.
name|createOntologyScope
argument_list|(
name|scopeId
argument_list|,
name|coreSrc
argument_list|,
name|custSrc
argument_list|)
else|:
name|f
operator|.
name|createOntologyScope
argument_list|(
name|scopeId
argument_list|,
name|coreSrc
argument_list|)
expr_stmt|;
comment|// Setup and register the scope. If no custom space was set, it will
comment|// still be open for modification.
name|scope
operator|.
name|setUp
argument_list|()
expr_stmt|;
name|reg
operator|.
name|registerScope
argument_list|(
name|scope
argument_list|)
expr_stmt|;
name|boolean
name|activateBool
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|activate
operator|!=
literal|null
operator|&&
operator|!
name|activate
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|activateBool
operator|=
name|Boolean
operator|.
name|valueOf
argument_list|(
name|activate
argument_list|)
expr_stmt|;
block|}
name|reg
operator|.
name|setScopeActive
argument_list|(
name|scopeId
argument_list|,
name|activateBool
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|CONFLICT
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|ex
argument_list|,
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
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

