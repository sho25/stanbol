begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|RuleStore
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
name|rules
operator|.
name|manager
operator|.
name|changes
operator|.
name|AddRecipe
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
name|rules
operator|.
name|manager
operator|.
name|changes
operator|.
name|GetRecipe
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
name|rules
operator|.
name|manager
operator|.
name|changes
operator|.
name|RemoveRecipe
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
name|rules
operator|.
name|manager
operator|.
name|changes
operator|.
name|RuleStoreImpl
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
name|model
operator|.
name|AddImport
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
name|OWLDataFactory
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
name|OWLIndividual
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
name|OWLIndividualAxiom
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
name|OWLNamedIndividual
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
name|OWLObjectProperty
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
name|OWLOntologyChange
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

begin_comment
comment|/**  *   * @author elvio, andrea.nuzzolese  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/recipe"
argument_list|)
comment|// /{uri:.+}")
comment|// @ImplicitProduces(MediaType.TEXT_HTML + ";qs=2")
specifier|public
class|class
name|RecipeResource
extends|extends
name|BaseStanbolResource
block|{
specifier|protected
name|ONManager
name|onm
decl_stmt|;
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
specifier|private
name|RuleStoreImpl
name|kresRuleStore
decl_stmt|;
comment|/**      * To get the RuleStoreImpl where are stored the rules and the recipes      *       * @param servletContext      *            {To get the context where the REST service is running.}      */
specifier|public
name|RecipeResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|this
operator|.
name|kresRuleStore
operator|=
operator|(
name|RuleStoreImpl
operator|)
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|RuleStore
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
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
comment|/**      * Get a recipe with its rules from the rule base (that is the ontology that contains the rules and the      * recipe).      *       * @param uri      *            {A string contains the IRI full name of the recipe.}      * @return Return:<br/>      *         200 The recipe is retrieved (import declarations point to KReS Services)<br/>      *         404 The recipe does not exists in the manager<br/>      *         500 Some error occurred      *       */
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/{uri:.+}"
argument_list|)
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
name|RDF_JSON
block|,
name|KRFormat
operator|.
name|OWL_XML
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
name|TURTLE
block|}
argument_list|)
specifier|public
name|Response
name|getRecipe
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"uri"
argument_list|)
name|String
name|uri
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
try|try
block|{
name|GetRecipe
name|rule
init|=
operator|new
name|GetRecipe
argument_list|(
name|kresRuleStore
argument_list|)
decl_stmt|;
empty_stmt|;
comment|// String ID =
comment|// kresRuleStore.getOntology().getOntologyID().toString().replace(">","").replace("<","")+"#";
if|if
condition|(
name|uri
operator|.
name|equals
argument_list|(
literal|"all"
argument_list|)
condition|)
block|{
name|Vector
argument_list|<
name|IRI
argument_list|>
name|recipe
init|=
name|rule
operator|.
name|getGeneralRecipes
argument_list|()
decl_stmt|;
if|if
condition|(
name|recipe
operator|==
literal|null
condition|)
block|{
comment|// The recipe does not exists in the manager
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
comment|// The recipe is retrieved (import declarations point to
comment|// KReS Services)
name|OWLOntology
name|onto
init|=
name|kresRuleStore
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|OWLOntology
name|newmodel
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|createOntology
argument_list|(
name|onto
operator|.
name|getOntologyID
argument_list|()
argument_list|)
decl_stmt|;
name|OWLDataFactory
name|factory
init|=
name|onto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|OWLOntology
argument_list|>
name|importedonto
init|=
name|onto
operator|.
name|getDirectImports
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|additions
init|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|auxfactory
init|=
name|onto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
while|while
condition|(
name|importedonto
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLOntology
name|auxonto
init|=
name|importedonto
operator|.
name|next
argument_list|()
decl_stmt|;
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|newmodel
argument_list|,
name|auxfactory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|auxonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|auxonto
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|additions
operator|.
name|isEmpty
argument_list|()
condition|)
name|newmodel
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|applyChanges
argument_list|(
name|additions
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|recipe
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|OWLNamedIndividual
name|ind
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|recipe
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLIndividualAxiom
argument_list|>
name|ax
init|=
name|onto
operator|.
name|getAxioms
argument_list|(
name|ind
argument_list|)
decl_stmt|;
name|newmodel
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|addAxioms
argument_list|(
name|newmodel
argument_list|,
name|ax
argument_list|)
expr_stmt|;
block|}
comment|// try {
comment|// OWLManager.createOWLOntologyManager().saveOntology(
comment|// newmodel,
comment|// newmodel.getOWLOntologyManager()
comment|// .getOntologyFormat(newmodel),
comment|// System.out);
comment|// } catch (OWLOntologyStorageException e) {
comment|// // TODO Auto-generated catch block
comment|// e.printStackTrace();
comment|// }
return|return
name|Response
operator|.
name|ok
argument_list|(
name|newmodel
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|String
argument_list|>
name|recipe
init|=
name|rule
operator|.
name|getRecipe
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|uri
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|recipe
operator|==
literal|null
condition|)
block|{
comment|// The recipe deos not exists in the manager
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
comment|// The recipe is retrieved (import declarations point to
comment|// KReS Services)
name|OWLOntology
name|onto
init|=
name|kresRuleStore
operator|.
name|getOntology
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|factory
init|=
name|onto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLObjectProperty
name|prop
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/rmi.owl#hasRule"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|ind
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|uri
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|value
init|=
name|ind
operator|.
name|getObjectPropertyValues
argument_list|(
name|prop
argument_list|,
name|onto
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLIndividualAxiom
argument_list|>
name|ax
init|=
name|onto
operator|.
name|getAxioms
argument_list|(
name|ind
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|OWLIndividual
argument_list|>
name|iter
init|=
name|value
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|OWLOntology
name|newmodel
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|createOntology
argument_list|(
name|onto
operator|.
name|getOntologyID
argument_list|()
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|OWLOntology
argument_list|>
name|importedonto
init|=
name|onto
operator|.
name|getDirectImports
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|additions
init|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|auxfactory
init|=
name|onto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
while|while
condition|(
name|importedonto
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLOntology
name|auxonto
init|=
name|importedonto
operator|.
name|next
argument_list|()
decl_stmt|;
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|newmodel
argument_list|,
name|auxfactory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|auxonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|auxonto
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|additions
operator|.
name|isEmpty
argument_list|()
condition|)
name|newmodel
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|applyChanges
argument_list|(
name|additions
argument_list|)
expr_stmt|;
name|newmodel
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|addAxioms
argument_list|(
name|newmodel
argument_list|,
name|ax
argument_list|)
expr_stmt|;
while|while
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ind
operator|=
operator|(
name|OWLNamedIndividual
operator|)
name|iter
operator|.
name|next
argument_list|()
expr_stmt|;
name|ax
operator|=
name|onto
operator|.
name|getAxioms
argument_list|(
name|ind
argument_list|)
expr_stmt|;
name|newmodel
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|addAxioms
argument_list|(
name|newmodel
argument_list|,
name|ax
argument_list|)
expr_stmt|;
block|}
comment|// try {
comment|// OWLManager.createOWLOntologyManager().saveOntology(
comment|// newmodel,
comment|// newmodel.getOWLOntologyManager()
comment|// .getOntologyFormat(newmodel),
comment|// System.out);
comment|// } catch (OWLOntologyStorageException e) {
comment|// // TODO Auto-generated catch block
comment|// e.printStackTrace();
comment|// }
return|return
name|Response
operator|.
name|ok
argument_list|(
name|newmodel
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// Some error occurred
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
comment|/**      * To add a recipe without rules.      *       * @param recipe      *            {A string contains the IRI of the recipe to be added}      * @param description      *            {A string contains a description of the rule}      * @return Return:<br/>      *         200 The recipe has been added<br/>      *         409 The recipe has not been added<br/>      *         500 Some error occurred      */
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
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
name|TURTLE
block|,
name|KRFormat
operator|.
name|OWL_XML
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
name|addRecipe
parameter_list|(
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"recipe"
argument_list|)
name|String
name|recipe
parameter_list|,
annotation|@
name|FormParam
argument_list|(
name|value
operator|=
literal|"description"
argument_list|)
name|String
name|description
parameter_list|)
block|{
try|try
block|{
name|AddRecipe
name|instance
init|=
operator|new
name|AddRecipe
argument_list|(
name|kresRuleStore
argument_list|)
decl_stmt|;
comment|// String ID =
comment|// kresRuleStore.getOntology().getOntologyID().toString().replace(">","").replace("<","")+"#";
name|boolean
name|ok
init|=
name|instance
operator|.
name|addSimpleRecipe
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|recipe
argument_list|)
argument_list|,
name|description
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|ok
condition|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|CONFLICT
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|kresRuleStore
operator|.
name|saveOntology
argument_list|()
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|OK
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
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
name|e
argument_list|,
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
comment|/**      * To delete a recipe      *       * @param recipe      *            {A tring contains an IRI of the recipe}      * @return 200 The recipe has been deleted<br/>      *         409 The recipe has not been deleted<br/>      *         500 Some error occurred      */
annotation|@
name|DELETE
comment|// @Consumes(MediaType.TEXT_PLAIN)
annotation|@
name|Produces
argument_list|(
literal|"text/plain"
argument_list|)
specifier|public
name|Response
name|removeRecipe
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"recipe"
argument_list|)
name|String
name|recipe
parameter_list|)
block|{
try|try
block|{
name|RemoveRecipe
name|instance
init|=
operator|new
name|RemoveRecipe
argument_list|(
name|kresRuleStore
argument_list|)
decl_stmt|;
comment|// String ID =
comment|// kresRuleStore.getOntology().getOntologyID().toString().replace(">","").replace("<","")+"#";
name|boolean
name|ok
init|=
name|instance
operator|.
name|removeRecipe
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|recipe
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|ok
condition|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|CONFLICT
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
name|kresRuleStore
operator|.
name|saveOntology
argument_list|()
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
name|e
argument_list|,
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

