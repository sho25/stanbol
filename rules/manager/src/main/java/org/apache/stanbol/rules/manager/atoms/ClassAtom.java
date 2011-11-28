begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|manager
operator|.
name|atoms
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
name|SPARQLObject
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
name|URIResource
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
name|SPARQLNot
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
name|SPARQLTriple
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
name|SWRLAtom
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
name|SWRLIArgument
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
name|graph
operator|.
name|Node
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
name|Model
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
name|Resource
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
name|reasoner
operator|.
name|TriplePattern
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
name|reasoner
operator|.
name|rulesys
operator|.
name|ClauseEntry
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
name|vocabulary
operator|.
name|RDF
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
name|SWRL
import|;
end_import

begin_class
specifier|public
class|class
name|ClassAtom
extends|extends
name|CoreAtom
block|{
specifier|private
name|URIResource
name|classResource
decl_stmt|;
specifier|private
name|URIResource
name|argument1
decl_stmt|;
specifier|public
name|ClassAtom
parameter_list|(
name|URIResource
name|classResource
parameter_list|,
name|URIResource
name|argument1
parameter_list|)
block|{
name|this
operator|.
name|classResource
operator|=
name|classResource
expr_stmt|;
name|this
operator|.
name|argument1
operator|=
name|argument1
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|SPARQLObject
name|toSPARQL
parameter_list|()
block|{
name|String
name|argument1SPARQL
init|=
literal|null
decl_stmt|;
name|String
name|argument2SPARQL
init|=
literal|null
decl_stmt|;
name|boolean
name|negativeArg
init|=
literal|false
decl_stmt|;
name|boolean
name|negativeClass
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|argument1
operator|.
name|toString
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|argument1SPARQL
operator|=
literal|"?"
operator|+
name|argument1
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|VariableAtom
name|variable
init|=
operator|(
name|VariableAtom
operator|)
name|argument1
decl_stmt|;
name|negativeArg
operator|=
name|variable
operator|.
name|isNegative
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|argument1SPARQL
operator|=
name|argument1
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|classResource
operator|.
name|toString
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|argument2SPARQL
operator|=
literal|"?"
operator|+
name|classResource
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|VariableAtom
name|variable
init|=
operator|(
name|VariableAtom
operator|)
name|classResource
decl_stmt|;
name|negativeClass
operator|=
name|variable
operator|.
name|isNegative
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|argument2SPARQL
operator|=
name|classResource
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|negativeArg
operator|||
name|negativeClass
condition|)
block|{
name|String
name|optional
init|=
name|argument1SPARQL
operator|+
literal|"<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> "
operator|+
name|argument2SPARQL
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|filters
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|negativeArg
condition|)
block|{
name|filters
operator|.
name|add
argument_list|(
literal|"!bound("
operator|+
name|argument1SPARQL
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|negativeClass
condition|)
block|{
name|filters
operator|.
name|add
argument_list|(
literal|"!bound("
operator|+
name|argument2SPARQL
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|filterArray
init|=
operator|new
name|String
index|[
name|filters
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|filterArray
operator|=
name|filters
operator|.
name|toArray
argument_list|(
name|filterArray
argument_list|)
expr_stmt|;
return|return
operator|new
name|SPARQLNot
argument_list|(
name|optional
argument_list|,
name|filterArray
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|SPARQLTriple
argument_list|(
name|argument1SPARQL
operator|+
literal|"<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> "
operator|+
name|argument2SPARQL
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|toSWRL
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
name|Resource
name|classAtom
init|=
name|model
operator|.
name|createResource
argument_list|(
name|SWRL
operator|.
name|ClassAtom
argument_list|)
decl_stmt|;
name|Resource
name|classPredicate
init|=
name|model
operator|.
name|createResource
argument_list|(
name|classResource
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|classAtom
operator|.
name|addProperty
argument_list|(
name|SWRL
operator|.
name|classPredicate
argument_list|,
name|classPredicate
argument_list|)
expr_stmt|;
name|Resource
name|argumentResource
init|=
name|argument1
operator|.
name|createJenaResource
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|classAtom
operator|.
name|addProperty
argument_list|(
name|SWRL
operator|.
name|argument1
argument_list|,
name|argumentResource
argument_list|)
expr_stmt|;
return|return
name|classAtom
return|;
block|}
specifier|public
name|SWRLAtom
name|toSWRL
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|)
block|{
name|OWLClass
name|classPredicate
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|classResource
operator|.
name|getURI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|SWRLIArgument
name|argumentResource
decl_stmt|;
if|if
condition|(
name|argument1
operator|instanceof
name|ResourceAtom
condition|)
block|{
name|OWLIndividual
name|owlIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|argument1
operator|.
name|getURI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|argumentResource
operator|=
name|factory
operator|.
name|getSWRLIndividualArgument
argument_list|(
name|owlIndividual
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|argumentResource
operator|=
name|factory
operator|.
name|getSWRLVariable
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|argument1
operator|.
name|getURI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|factory
operator|.
name|getSWRLClassAtom
argument_list|(
name|classPredicate
argument_list|,
name|argumentResource
argument_list|)
return|;
block|}
specifier|public
name|URIResource
name|getClassResource
parameter_list|()
block|{
return|return
name|classResource
return|;
block|}
specifier|public
name|URIResource
name|getArgument1
parameter_list|()
block|{
return|return
name|argument1
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|argument1
operator|.
name|toString
argument_list|()
operator|+
literal|" is an individual of the class "
operator|+
name|classResource
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toKReSSyntax
parameter_list|()
block|{
name|String
name|arg1
init|=
literal|null
decl_stmt|;
name|String
name|arg2
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|argument1
operator|.
name|toString
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|arg1
operator|=
literal|"?"
operator|+
name|argument1
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|VariableAtom
name|variable
init|=
operator|(
name|VariableAtom
operator|)
name|argument1
decl_stmt|;
if|if
condition|(
name|variable
operator|.
name|isNegative
argument_list|()
condition|)
block|{
name|arg1
operator|=
literal|"notex("
operator|+
name|arg1
operator|+
literal|")"
expr_stmt|;
block|}
block|}
else|else
block|{
name|arg1
operator|=
name|argument1
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|classResource
operator|.
name|toString
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|arg2
operator|=
literal|"?"
operator|+
name|classResource
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|VariableAtom
name|variable
init|=
operator|(
name|VariableAtom
operator|)
name|classResource
decl_stmt|;
if|if
condition|(
name|variable
operator|.
name|isNegative
argument_list|()
condition|)
block|{
name|arg2
operator|=
literal|"notex("
operator|+
name|arg2
operator|+
literal|")"
expr_stmt|;
block|}
block|}
else|else
block|{
name|arg2
operator|=
name|classResource
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
return|return
literal|"is("
operator|+
name|arg2
operator|+
literal|", "
operator|+
name|arg1
operator|+
literal|")"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLConstruct
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDelete
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDeleteData
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|ClauseEntry
name|toJenaClauseEntry
parameter_list|()
block|{
name|String
name|subject
init|=
name|argument1
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|subject
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|subject
operator|=
name|subject
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|String
name|object
init|=
name|classResource
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|object
operator|.
name|startsWith
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
condition|)
block|{
name|object
operator|=
name|subject
operator|.
name|replace
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|ClauseEntry
name|clauseEntry
init|=
operator|new
name|TriplePattern
argument_list|(
name|Node
operator|.
name|createVariable
argument_list|(
name|subject
argument_list|)
argument_list|,
name|RDF
operator|.
name|type
operator|.
name|asNode
argument_list|()
argument_list|,
name|Node
operator|.
name|createURI
argument_list|(
name|object
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|clauseEntry
return|;
block|}
block|}
end_class

end_unit

