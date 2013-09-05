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
name|servicesapi
operator|.
name|util
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|util
operator|.
name|OntologyConstants
operator|.
name|NS_ONM
import|;
end_import

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
name|Arrays
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
name|OWLClass
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
name|OWLClassExpression
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
name|OWLDataProperty
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
name|OWLLiteral
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

begin_comment
comment|/**  *<p>  * This is the helper class for parsing the ONM configuration ontology. The configuration ontology should  * import the following:  *</p>  *<ul>  *<li>http://ontologydesignpatterns.org/ont/iks/kres/onm.owl</li>  *</ul>  *   *<p>  * and must use the following vocabs:  *</p>  *<ul>  *<li>http://kres.iks-project.eu/ontology/meta/onm.owl#Scope : defines a scope</li>  *<li>http://kres.iks-project.eu/ontology/meta/onm.owl#activateOnStart : activate the scope on startup</li>  *<li>http://kres.iks-project.eu/ontology/meta/onm.owl#usesCoreOntology : relates a scope to an ontology to  * be added in the core space</li>  *<li>http://kres.iks-project.eu/ontology/meta/onm.owl#usesCoreLibrary : relates a scope to a library of  * ontologies to be added in the core space</li>  *<li>http://kres.iks-project.eu/ontology/meta/onm.owl#usesCustomOntology : relates a scope to an ontology to  * be added in the custom space</li>  *<li>http://kres.iks-project.eu/ontology/meta/onm.owl#usesCustomLibrary : relates scope to a library of  * ontologies to be added in the custom space</li>  *<li>  * http://www.ontologydesignpatterns.org/cpont/codo/coddata.owl#OntologyLibrary : the class of a library</li>  *<li>http://www.ontologydesignpatterns.org/schemas/meta.owl#hasOntology : to relate a library to an ontology  *</li>  *</ul>  *   * @author alexdma  * @author enridaga  */
end_comment

begin_class
specifier|public
class|class
name|OntologyNetworkConfigurationUtils
block|{
specifier|private
specifier|static
name|OWLDataFactory
name|_df
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|EMPTY_IRI_ARRAY
init|=
operator|new
name|String
index|[
literal|0
index|]
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLClass
name|cScope
init|=
name|_df
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|NS_ONM
operator|+
literal|"Scope"
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLClass
name|cLibrary
init|=
name|_df
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.ontologydesignpatterns.org/cpont/codo/coddata.owl#OntologyLibrary"
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLDataProperty
name|activateOnStart
init|=
name|_df
operator|.
name|getOWLDataProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|NS_ONM
operator|+
literal|"activateOnStart"
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLObjectProperty
name|usesCoreOntology
init|=
name|_df
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|NS_ONM
operator|+
literal|"usesCoreOntology"
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLObjectProperty
name|usesCoreLibrary
init|=
name|_df
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|NS_ONM
operator|+
literal|"usesCoreLibrary"
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLObjectProperty
name|usesCustomOntology
init|=
name|_df
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|NS_ONM
operator|+
literal|"usesCustomOntology"
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLObjectProperty
name|usesCustomLibrary
init|=
name|_df
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|NS_ONM
operator|+
literal|"usesCustomLibrary"
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLObjectProperty
name|libraryHasOntology
init|=
name|_df
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|NS_ONM
operator|+
literal|"hasOntology"
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * Get the list of scopes to activate on startup      *       * @param config      * @return      */
specifier|public
specifier|static
name|String
index|[]
name|getScopesToActivate
parameter_list|(
name|OWLOntology
name|config
parameter_list|)
block|{
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|scopes
init|=
name|cScope
operator|.
name|getIndividuals
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|doActivate
init|=
literal|false
decl_stmt|;
for|for
control|(
name|OWLIndividual
name|iScope
range|:
name|scopes
control|)
block|{
name|Set
argument_list|<
name|OWLLiteral
argument_list|>
name|activate
init|=
name|iScope
operator|.
name|getDataPropertyValues
argument_list|(
name|activateOnStart
argument_list|,
name|config
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|OWLLiteral
argument_list|>
name|it
init|=
name|activate
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
operator|&&
operator|!
name|doActivate
condition|)
block|{
name|OWLLiteral
name|l
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|doActivate
operator||=
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|l
operator|.
name|getLiteral
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iScope
operator|.
name|isNamed
argument_list|()
operator|&&
name|doActivate
condition|)
name|result
operator|.
name|add
argument_list|(
operator|(
operator|(
name|OWLNamedIndividual
operator|)
name|iScope
operator|)
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toArray
argument_list|(
name|EMPTY_IRI_ARRAY
argument_list|)
return|;
block|}
comment|/**      * To get all the instances of Scope in this configuration      *       * @param config      * @return      */
specifier|public
specifier|static
name|String
index|[]
name|getScopes
parameter_list|(
name|OWLOntology
name|config
parameter_list|)
block|{
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|scopes
init|=
name|cScope
operator|.
name|getIndividuals
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLIndividual
name|iScope
range|:
name|scopes
control|)
block|{
for|for
control|(
name|OWLClassExpression
name|sce
range|:
name|iScope
operator|.
name|getTypes
argument_list|(
name|config
argument_list|)
control|)
block|{
if|if
condition|(
name|sce
operator|.
name|containsConjunct
argument_list|(
name|cScope
argument_list|)
condition|)
block|{
if|if
condition|(
name|iScope
operator|.
name|isNamed
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|(
operator|(
name|OWLNamedIndividual
operator|)
name|iScope
operator|)
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|result
operator|.
name|toArray
argument_list|(
name|EMPTY_IRI_ARRAY
argument_list|)
return|;
block|}
comment|/**      * Utility method to get all the values of an object property of a Scope      *       * @param ontology      * @param individualIRI      * @param op      * @return      */
specifier|private
specifier|static
name|String
index|[]
name|getScopeObjectPropertyValues
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|,
name|String
name|individualIRI
parameter_list|,
name|OWLObjectProperty
name|op
parameter_list|)
block|{
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|scopes
init|=
name|cScope
operator|.
name|getIndividuals
argument_list|(
name|ontology
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|OWLIndividual
name|iiScope
init|=
literal|null
decl_stmt|;
comment|// Optimised loop.
for|for
control|(
name|OWLIndividual
name|ind
range|:
name|scopes
control|)
block|{
if|if
condition|(
name|ind
operator|.
name|isAnonymous
argument_list|()
condition|)
continue|continue;
if|if
condition|(
operator|(
operator|(
name|OWLNamedIndividual
operator|)
name|ind
operator|)
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|individualIRI
argument_list|)
condition|)
block|{
name|iiScope
operator|=
name|ind
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|iiScope
operator|!=
literal|null
condition|)
block|{          }
for|for
control|(
name|OWLIndividual
name|iScope
range|:
name|scopes
control|)
block|{
if|if
condition|(
name|iScope
operator|.
name|isNamed
argument_list|()
condition|)
block|{
if|if
condition|(
operator|(
operator|(
name|OWLNamedIndividual
operator|)
name|iScope
operator|)
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|individualIRI
argument_list|)
condition|)
block|{
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|values
init|=
name|iScope
operator|.
name|getObjectPropertyValues
argument_list|(
name|op
argument_list|,
name|ontology
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|OWLIndividual
argument_list|>
name|it
init|=
name|values
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLIndividual
name|i
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|i
operator|.
name|isNamed
argument_list|()
condition|)
name|result
operator|.
name|add
argument_list|(
operator|(
operator|(
name|OWLNamedIndividual
operator|)
name|i
operator|)
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|result
operator|.
name|toArray
argument_list|(
name|EMPTY_IRI_ARRAY
argument_list|)
return|;
block|}
comment|/**      * Utility method to get all the values of a property from a Library subject      *       * @param ontology      * @param individualIRI      * @param op      * @return      */
specifier|private
specifier|static
name|String
index|[]
name|getLibraryObjectPropertyValues
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|,
name|String
name|individualIRI
parameter_list|,
name|OWLObjectProperty
name|op
parameter_list|)
block|{
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|scopes
init|=
name|cLibrary
operator|.
name|getIndividuals
argument_list|(
name|ontology
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLIndividual
name|iLibrary
range|:
name|scopes
control|)
block|{
if|if
condition|(
name|iLibrary
operator|.
name|isNamed
argument_list|()
condition|)
block|{
if|if
condition|(
operator|(
operator|(
name|OWLNamedIndividual
operator|)
name|iLibrary
operator|)
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|individualIRI
argument_list|)
condition|)
block|{
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|values
init|=
name|iLibrary
operator|.
name|getObjectPropertyValues
argument_list|(
name|op
argument_list|,
name|ontology
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|OWLIndividual
argument_list|>
name|it
init|=
name|values
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLIndividual
name|i
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|i
operator|.
name|isNamed
argument_list|()
condition|)
name|result
operator|.
name|add
argument_list|(
operator|(
operator|(
name|OWLNamedIndividual
operator|)
name|iLibrary
operator|)
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|result
operator|.
name|toArray
argument_list|(
name|EMPTY_IRI_ARRAY
argument_list|)
return|;
block|}
comment|/**      * Returns all the IRIs to be loaded in the core space of the scope      *       * @param config      * @param scopeIRI      * @return      */
specifier|public
specifier|static
name|String
index|[]
name|getCoreOntologies
parameter_list|(
name|OWLOntology
name|config
parameter_list|,
name|String
name|scopeIRI
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|ontologies
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|ontologies
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|getScopeObjectPropertyValues
argument_list|(
name|config
argument_list|,
name|scopeIRI
argument_list|,
name|usesCoreOntology
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|libraryID
range|:
name|getCoreLibraries
argument_list|(
name|config
argument_list|,
name|scopeIRI
argument_list|)
control|)
block|{
name|ontologies
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|getLibraryObjectPropertyValues
argument_list|(
name|config
argument_list|,
name|libraryID
argument_list|,
name|libraryHasOntology
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ontologies
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|ontologies
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
comment|/**      * Returns all the resources to be part of the Custom space      *       * @param config      * @param scopeIRI      * @return      */
specifier|public
specifier|static
name|String
index|[]
name|getCustomOntologies
parameter_list|(
name|OWLOntology
name|config
parameter_list|,
name|String
name|scopeIRI
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|ontologies
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|ontologies
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|getScopeObjectPropertyValues
argument_list|(
name|config
argument_list|,
name|scopeIRI
argument_list|,
name|usesCustomOntology
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|libraryID
range|:
name|getCustomLibraries
argument_list|(
name|config
argument_list|,
name|scopeIRI
argument_list|)
control|)
block|{
name|ontologies
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|getLibraryObjectPropertyValues
argument_list|(
name|config
argument_list|,
name|libraryID
argument_list|,
name|libraryHasOntology
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ontologies
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|ontologies
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|String
index|[]
name|getCoreLibraries
parameter_list|(
name|OWLOntology
name|config
parameter_list|,
name|String
name|scopeIRI
parameter_list|)
block|{
return|return
name|getScopeObjectPropertyValues
argument_list|(
name|config
argument_list|,
name|scopeIRI
argument_list|,
name|usesCoreLibrary
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|String
index|[]
name|getCustomLibraries
parameter_list|(
name|OWLOntology
name|config
parameter_list|,
name|String
name|scopeIRI
parameter_list|)
block|{
return|return
name|getScopeObjectPropertyValues
argument_list|(
name|config
argument_list|,
name|scopeIRI
argument_list|,
name|usesCustomLibrary
argument_list|)
return|;
block|}
block|}
end_class

end_unit

