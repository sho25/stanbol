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
name|INTERNAL_SERVER_ERROR
import|;
end_import

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
name|NOT_FOUND
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|OfflineConfiguration
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
name|OntologyCollectorModificationException
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
name|web
operator|.
name|util
operator|.
name|OntologyRenderUtils
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
name|owl
operator|.
name|OWLOntologyManagerFactory
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
name|apibinding
operator|.
name|OWLManager
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
name|io
operator|.
name|RDFXMLOntologyFormat
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
name|AxiomType
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntologySetProvider
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
name|OWLOntologyStorageException
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
name|util
operator|.
name|OWLOntologyMerger
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
comment|/**  * This resource represents ontologies loaded within a scope.  *   * @author alexdma  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontonet/ontology/{scopeid}/{uri:.+}"
argument_list|)
specifier|public
class|class
name|ScopeOntologyResource
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
specifier|public
name|ScopeOntologyResource
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
block|}
comment|/**      * Returns an RDF/XML representation of the ontology identified by logical IRI<code>ontologyid</code>, if      * it is loaded within the scope<code>[baseUri]/scopeid</code>.      *       * @param scopeid      * @param ontologyid      * @param uriInfo      * @return, or a status 404 if either the scope is not registered or the ontology is not loaded within      *          that scope.      */
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
name|getScopeOntology
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
name|PathParam
argument_list|(
literal|"uri"
argument_list|)
name|String
name|ontologyid
parameter_list|,
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Caught request for ontology {} in scope {}"
argument_list|,
name|ontologyid
argument_list|,
name|scopeid
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|ontologyid
operator|.
name|equals
argument_list|(
literal|"all"
argument_list|)
condition|)
block|{
comment|// First of all, it could be a simple request for the space root!
name|String
name|absur
init|=
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Absolute URL Path {}"
argument_list|,
name|absur
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Ontology ID {}"
argument_list|,
name|ontologyid
argument_list|)
expr_stmt|;
name|URI
name|uri
init|=
name|URI
operator|.
name|create
argument_list|(
name|absur
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|absur
operator|.
name|lastIndexOf
argument_list|(
name|ontologyid
argument_list|)
operator|-
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|IRI
name|sciri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|IRI
name|ontiri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ontologyid
argument_list|)
decl_stmt|;
comment|// TODO: hack (ma anche no)
if|if
condition|(
operator|!
name|ontiri
operator|.
name|isAbsolute
argument_list|()
condition|)
name|ontiri
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|absur
argument_list|)
expr_stmt|;
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
name|scopeid
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
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
comment|// First of all, it could be a simple request for the space root!
name|String
name|temp
init|=
name|scopeid
operator|+
literal|"/"
operator|+
name|ontologyid
decl_stmt|;
if|if
condition|(
name|temp
operator|.
name|equals
argument_list|(
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getID
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|asOWLOntology
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|temp
operator|.
name|equals
argument_list|(
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getID
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|asOWLOntology
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
return|;
comment|// } else if (scope.getSessionSpace(IRI.create(temp)) != null) {
comment|// return Response.ok(scope.getSessionSpace(IRI.create(temp)).asOWLOntology()).build();
block|}
comment|/*              * BEGIN debug code, uncomment only for local testing OWLOntology test = null, top = null; test =              * scope.getCustomSpace().getOntology(ontiri); System.out.println("Ontology " + ontiri); for              * (OWLImportsDeclaration imp : test.getImportsDeclarations()) System.out.println("\timports " +              * imp.getIRI()); top = scope.getCoreSpace().getTopOntology();              * System.out.println("Core root for scope " + scopeid); for (OWLImportsDeclaration imp :              * top.getImportsDeclarations()) System.out.println("\timports " + imp.getIRI()); END debug code              */
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|;
comment|// By default, always try retrieving the ontology from the custom space
comment|// first.
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
operator|!=
literal|null
condition|)
name|ont
operator|=
name|space
operator|.
name|getOntology
argument_list|(
name|ontiri
argument_list|)
expr_stmt|;
if|if
condition|(
name|space
operator|==
literal|null
operator|||
name|ont
operator|==
literal|null
condition|)
block|{
name|space
operator|=
name|scope
operator|.
name|getCoreSpace
argument_list|()
expr_stmt|;
if|if
condition|(
name|space
operator|!=
literal|null
condition|)
name|ont
operator|=
name|space
operator|.
name|getOntology
argument_list|(
name|ontiri
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ont
operator|==
literal|null
condition|)
block|{
name|OWLOntologyManager
name|tmpmgr
decl_stmt|;
name|OfflineConfiguration
name|offline
init|=
operator|(
name|OfflineConfiguration
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|OfflineConfiguration
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
decl_stmt|;
if|if
condition|(
name|offline
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"OfflineConfiguration missing in ServletContext"
argument_list|)
throw|;
else|else
name|tmpmgr
operator|=
name|OWLOntologyManagerFactory
operator|.
name|createOWLOntologyManager
argument_list|(
name|offline
operator|.
name|getOntologySourceLocations
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|IRI
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getOntologies
argument_list|(
literal|true
argument_list|)
decl_stmt|;
comment|// scope.getSessionSpace(ontiri).getOntologies(true);
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
return|return
name|ontologies
return|;
block|}
block|}
decl_stmt|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
try|try
block|{
name|ont
operator|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|tmpmgr
argument_list|,
name|ontiri
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
block|}
if|if
condition|(
name|ont
operator|==
literal|null
condition|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|String
name|res
init|=
literal|null
decl_stmt|;
try|try
block|{
name|res
operator|=
name|OntologyRenderUtils
operator|.
name|renderOntology
argument_list|(
name|ont
argument_list|,
operator|new
name|RDFXMLOntologyFormat
argument_list|()
argument_list|,
name|sciri
operator|.
name|toString
argument_list|()
argument_list|,
name|onm
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
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
return|return
name|Response
operator|.
name|ok
argument_list|(
name|res
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|String
name|scopeID
init|=
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|scopeID
operator|=
name|scopeID
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|scopeID
operator|.
name|lastIndexOf
argument_list|(
literal|"/"
argument_list|)
argument_list|)
expr_stmt|;
name|OntologyScope
name|scope
init|=
name|reg
operator|.
name|getScope
argument_list|(
name|scopeID
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
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|customOntologies
init|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getOntologies
argument_list|(
literal|true
argument_list|)
decl_stmt|;
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|coreOntologies
init|=
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getOntologies
argument_list|(
literal|true
argument_list|)
decl_stmt|;
specifier|final
name|Set
argument_list|<
name|OntologySpace
argument_list|>
name|sessionSpaces
init|=
name|scope
operator|.
name|getSessionSpaces
argument_list|()
decl_stmt|;
comment|// Creo un manager per gestire tutte le ontologie
specifier|final
name|OWLOntologyManager
name|man
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
comment|// Creo un set con tutte le ontologie dello scope
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntology
argument_list|>
argument_list|()
decl_stmt|;
comment|// Inserisco le core ontologies
for|for
control|(
name|OWLOntology
name|ontology
range|:
name|coreOntologies
control|)
block|{
name|OWLOntology
name|ont
decl_stmt|;
try|try
block|{
name|ont
operator|=
name|man
operator|.
name|createOntology
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|axioms
init|=
name|ontology
operator|.
name|getAxioms
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLAxiom
name|axiom
range|:
name|axioms
control|)
block|{
if|if
condition|(
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATA_PROPERTY_ASSERTION
argument_list|)
operator|&&
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATATYPE_DEFINITION
argument_list|)
operator|&&
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATA_PROPERTY_DOMAIN
argument_list|)
operator|&&
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATA_PROPERTY_RANGE
argument_list|)
condition|)
block|{
name|man
operator|.
name|addAxiom
argument_list|(
name|ont
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
block|}
block|}
name|ontologies
operator|.
name|add
argument_list|(
name|ont
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
block|}
comment|// Inserisco le custom ontology
for|for
control|(
name|OWLOntology
name|ontology
range|:
name|customOntologies
control|)
block|{
name|OWLOntology
name|ont
decl_stmt|;
try|try
block|{
name|ont
operator|=
name|man
operator|.
name|createOntology
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|axioms
init|=
name|ontology
operator|.
name|getAxioms
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLAxiom
name|axiom
range|:
name|axioms
control|)
block|{
if|if
condition|(
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATA_PROPERTY_ASSERTION
argument_list|)
operator|&&
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATATYPE_DEFINITION
argument_list|)
operator|&&
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATA_PROPERTY_DOMAIN
argument_list|)
operator|&&
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATA_PROPERTY_RANGE
argument_list|)
condition|)
block|{
name|man
operator|.
name|addAxiom
argument_list|(
name|ont
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
block|}
block|}
name|ontologies
operator|.
name|add
argument_list|(
name|ont
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
block|}
comment|// Inserisco le session ontologies;
for|for
control|(
name|OntologySpace
name|ontologySpace
range|:
name|sessionSpaces
control|)
block|{
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|sessionOntologies
init|=
name|ontologySpace
operator|.
name|getOntologies
argument_list|(
literal|true
argument_list|)
decl_stmt|;
for|for
control|(
name|OWLOntology
name|ontology
range|:
name|sessionOntologies
control|)
block|{
name|OWLOntology
name|ont
decl_stmt|;
try|try
block|{
name|ont
operator|=
name|man
operator|.
name|createOntology
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|axioms
init|=
name|ontology
operator|.
name|getAxioms
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLAxiom
name|axiom
range|:
name|axioms
control|)
block|{
if|if
condition|(
operator|!
name|axiom
operator|.
name|isOfType
argument_list|(
name|AxiomType
operator|.
name|DATA_PROPERTY_ASSERTION
argument_list|)
condition|)
block|{
name|man
operator|.
name|addAxiom
argument_list|(
name|ont
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
block|}
block|}
name|ontologies
operator|.
name|add
argument_list|(
name|ont
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
block|}
block|}
return|return
name|ontologies
return|;
block|}
block|}
decl_stmt|;
comment|// Faccio il merger delle ontolgoie
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
name|OWLOntology
name|ontology
decl_stmt|;
try|try
block|{
name|ontology
operator|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|man
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/classify"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
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
argument_list|(
name|ontology
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
comment|/**      * Unloads an ontology from an ontology scope.      *       * @param scopeId      * @param ontologyid      * @param uriInfo      * @param headers      */
annotation|@
name|DELETE
specifier|public
name|void
name|unloadOntology
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"scopeid"
argument_list|)
name|String
name|scopeId
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"uri"
argument_list|)
name|String
name|ontologyid
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
parameter_list|)
block|{
if|if
condition|(
name|ontologyid
operator|!=
literal|null
operator|&&
operator|!
name|ontologyid
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|String
name|scopeURI
init|=
name|uriInfo
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
name|ontologyid
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|IRI
name|scopeIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|uriInfo
operator|.
name|getBaseUri
argument_list|()
operator|+
literal|"ontology/"
operator|+
name|scopeId
argument_list|)
decl_stmt|;
name|IRI
name|ontIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ontologyid
argument_list|)
decl_stmt|;
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
name|scopeId
argument_list|)
decl_stmt|;
name|OntologySpace
name|cs
init|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
decl_stmt|;
if|if
condition|(
name|cs
operator|.
name|hasOntology
argument_list|(
name|ontIri
argument_list|)
condition|)
block|{
try|try
block|{
name|reg
operator|.
name|setScopeActive
argument_list|(
name|scopeId
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|cs
operator|.
name|removeOntology
argument_list|(
name|ontIri
argument_list|)
expr_stmt|;
name|reg
operator|.
name|setScopeActive
argument_list|(
name|scopeId
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OntologyCollectorModificationException
name|e
parameter_list|)
block|{
name|reg
operator|.
name|setScopeActive
argument_list|(
name|scopeId
argument_list|,
literal|true
argument_list|)
expr_stmt|;
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
block|}
block|}
block|}
end_class

end_unit

