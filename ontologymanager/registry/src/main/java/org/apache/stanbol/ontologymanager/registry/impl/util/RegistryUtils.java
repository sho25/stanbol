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
name|registry
operator|.
name|impl
operator|.
name|util
package|;
end_package

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
name|stanbol
operator|.
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|Library
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
name|model
operator|.
name|Registry
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
name|model
operator|.
name|RegistryItem
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
name|model
operator|.
name|RegistryItem
operator|.
name|Type
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
name|model
operator|.
name|RegistryOntology
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
name|xd
operator|.
name|vocabulary
operator|.
name|CODOVocabulary
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
name|OWLAxiomVisitor
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
name|OWLClassAssertionAxiom
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
name|OWLObjectPropertyAssertionAxiom
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
name|OWLObjectPropertyExpression
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
name|util
operator|.
name|OWLAxiomVisitorAdapter
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
specifier|public
class|class
name|RegistryUtils
block|{
specifier|private
specifier|static
specifier|final
name|OWLClass
name|cRegistryLibrary
decl_stmt|,
name|cOntology
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLObjectProperty
name|hasPart
decl_stmt|,
name|hasOntology
decl_stmt|,
name|isPartOf
decl_stmt|,
name|isOntologyOf
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RegistryUtils
operator|.
name|class
argument_list|)
decl_stmt|;
static|static
block|{
name|OWLDataFactory
name|factory
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|cOntology
operator|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|CODK_Ontology
argument_list|)
argument_list|)
expr_stmt|;
name|cRegistryLibrary
operator|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|CODD_OntologyLibrary
argument_list|)
argument_list|)
expr_stmt|;
name|isPartOf
operator|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|PARTOF_IsPartOf
argument_list|)
argument_list|)
expr_stmt|;
name|isOntologyOf
operator|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|ODPM_IsOntologyOf
argument_list|)
argument_list|)
expr_stmt|;
name|hasPart
operator|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|PARTOF_HasPart
argument_list|)
argument_list|)
expr_stmt|;
name|hasOntology
operator|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|CODOVocabulary
operator|.
name|ODPM_HasOntology
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Utility method to recurse into registry items.      *       * TODO: move this to main?      *       * @param item      * @param ontologyId      * @return      */
specifier|public
specifier|static
name|boolean
name|containsOntologyRecursive
parameter_list|(
name|RegistryItem
name|item
parameter_list|,
name|IRI
name|ontologyId
parameter_list|)
block|{
name|boolean
name|result
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|item
operator|instanceof
name|RegistryOntology
condition|)
block|{
comment|// An Ontology MUST have a non-null URI.
try|try
block|{
name|IRI
name|iri
init|=
name|item
operator|.
name|getIRI
argument_list|()
decl_stmt|;
name|result
operator||=
name|iri
operator|.
name|equals
argument_list|(
name|ontologyId
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|item
operator|instanceof
name|Library
operator|||
name|item
operator|instanceof
name|Registry
condition|)
comment|// Inspect children
for|for
control|(
name|RegistryItem
name|child
range|:
operator|(
operator|(
name|RegistryItem
operator|)
name|item
operator|)
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|result
operator||=
name|containsOntologyRecursive
argument_list|(
name|child
argument_list|,
name|ontologyId
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
condition|)
break|break;
block|}
return|return
name|result
return|;
block|}
annotation|@
name|Deprecated
specifier|public
specifier|static
name|Type
name|getType
parameter_list|(
specifier|final
name|OWLIndividual
name|ind
parameter_list|,
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
parameter_list|)
block|{
comment|// 0 is for library, 1 is for ontology (more in the future?)
specifier|final
name|int
index|[]
name|pointsFor
init|=
operator|new
name|int
index|[]
block|{
literal|0
block|,
literal|0
block|}
decl_stmt|;
specifier|final
name|int
index|[]
name|pointsAgainst
init|=
operator|new
name|int
index|[]
block|{
literal|0
block|,
literal|0
block|}
decl_stmt|;
name|OWLAxiomVisitor
name|v
init|=
operator|new
name|OWLAxiomVisitorAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|visit
parameter_list|(
name|OWLClassAssertionAxiom
name|axiom
parameter_list|)
block|{
if|if
condition|(
name|ind
operator|.
name|equals
argument_list|(
name|axiom
operator|.
name|getIndividual
argument_list|()
argument_list|)
condition|)
block|{
name|OWLClassExpression
name|type
init|=
name|axiom
operator|.
name|getClassExpression
argument_list|()
decl_stmt|;
if|if
condition|(
name|cRegistryLibrary
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|pointsFor
index|[
literal|0
index|]
operator|++
expr_stmt|;
name|pointsAgainst
index|[
literal|1
index|]
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|cOntology
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|pointsFor
index|[
literal|1
index|]
operator|++
expr_stmt|;
name|pointsAgainst
index|[
literal|0
index|]
operator|++
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|visit
parameter_list|(
name|OWLObjectPropertyAssertionAxiom
name|axiom
parameter_list|)
block|{
name|OWLObjectPropertyExpression
name|prop
init|=
name|axiom
operator|.
name|getProperty
argument_list|()
decl_stmt|;
if|if
condition|(
name|ind
operator|.
name|equals
argument_list|(
name|axiom
operator|.
name|getSubject
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|hasOntology
operator|.
name|equals
argument_list|(
name|prop
argument_list|)
condition|)
block|{
name|pointsFor
index|[
literal|0
index|]
operator|++
expr_stmt|;
name|pointsAgainst
index|[
literal|1
index|]
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|isOntologyOf
operator|.
name|equals
argument_list|(
name|prop
argument_list|)
condition|)
block|{
name|pointsFor
index|[
literal|1
index|]
operator|++
expr_stmt|;
name|pointsAgainst
index|[
literal|0
index|]
operator|++
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|ind
operator|.
name|equals
argument_list|(
name|axiom
operator|.
name|getObject
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|isOntologyOf
operator|.
name|equals
argument_list|(
name|prop
argument_list|)
condition|)
block|{
name|pointsFor
index|[
literal|0
index|]
operator|++
expr_stmt|;
name|pointsAgainst
index|[
literal|1
index|]
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|hasOntology
operator|.
name|equals
argument_list|(
name|prop
argument_list|)
condition|)
block|{
name|pointsFor
index|[
literal|1
index|]
operator|++
expr_stmt|;
name|pointsAgainst
index|[
literal|0
index|]
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
decl_stmt|;
comment|// TODO use this strategy in the single pass algorithm for constructing the model.
for|for
control|(
name|OWLOntology
name|o
range|:
name|ontologies
control|)
for|for
control|(
name|OWLAxiom
name|ax
range|:
name|o
operator|.
name|getAxioms
argument_list|()
control|)
name|ax
operator|.
name|accept
argument_list|(
name|v
argument_list|)
expr_stmt|;
if|if
condition|(
name|pointsFor
index|[
literal|0
index|]
operator|>
literal|0
operator|&&
name|pointsAgainst
index|[
literal|0
index|]
operator|==
literal|0
condition|)
return|return
name|Type
operator|.
name|LIBRARY
return|;
if|if
condition|(
name|pointsFor
index|[
literal|1
index|]
operator|>
literal|0
operator|&&
name|pointsAgainst
index|[
literal|1
index|]
operator|==
literal|0
condition|)
return|return
name|Type
operator|.
name|ONTOLOGY
return|;
comment|// Cannot determine registries, since they have no associated individual.
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

