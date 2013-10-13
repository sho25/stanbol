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
name|rules
operator|.
name|web
operator|.
name|writers
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|annotation
operator|.
name|Annotation
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
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
name|MultivaluedMap
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
name|ext
operator|.
name|MessageBodyWriter
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
name|ext
operator|.
name|Provider
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
name|TripleCollection
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
name|serializedform
operator|.
name|Serializer
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
name|serializedform
operator|.
name|SupportedFormat
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
name|rdfjson
operator|.
name|serializer
operator|.
name|RdfJsonSerializingProvider
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
name|owl
operator|.
name|transformation
operator|.
name|OWLAPIToClerezzaConverter
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|Recipe
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
name|base
operator|.
name|api
operator|.
name|Symbols
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
name|util
operator|.
name|RecipeList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|coode
operator|.
name|owlapi
operator|.
name|manchesterowlsyntax
operator|.
name|ManchesterOWLSyntaxOntologyFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|coode
operator|.
name|owlapi
operator|.
name|turtle
operator|.
name|TurtleOntologyFormat
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
name|OWLFunctionalSyntaxOntologyFormat
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
name|OWLXMLOntologyFormat
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
name|OWLOntologyStorageException
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
comment|/**  *   * @author anuzzolese  *  */
end_comment

begin_class
annotation|@
name|Provider
annotation|@
name|Produces
argument_list|(
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
name|MANCHESTER_OWL
block|,
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
block|,
name|KRFormat
operator|.
name|TURTLE
block|,
name|KRFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
class|class
name|RecipeListWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|RecipeList
argument_list|>
block|{
specifier|protected
name|Serializer
name|serializer
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|protected
name|RuleStore
name|ruleStore
decl_stmt|;
specifier|public
name|RecipeListWriter
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
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
name|this
operator|.
name|servletContext
operator|=
name|servletContext
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Setting context to "
operator|+
name|servletContext
argument_list|)
expr_stmt|;
name|serializer
operator|=
operator|(
name|Serializer
operator|)
name|this
operator|.
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|Serializer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|serializer
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Serializer not found in Servlet context."
argument_list|)
expr_stmt|;
name|serializer
operator|=
operator|new
name|Serializer
argument_list|()
expr_stmt|;
block|}
name|ruleStore
operator|=
operator|(
name|RuleStore
operator|)
name|this
operator|.
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|RuleStore
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|serializer
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"RuleStore not found in Servlet context."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|RecipeList
name|arg0
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|arg1
parameter_list|,
name|Type
name|arg2
parameter_list|,
name|Annotation
index|[]
name|arg3
parameter_list|,
name|MediaType
name|arg4
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isWriteable
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
return|return
name|RecipeList
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
name|RecipeList
name|recipeList
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|arg1
parameter_list|,
name|Type
name|arg2
parameter_list|,
name|Annotation
index|[]
name|arg3
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|MultivaluedMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|arg5
parameter_list|,
name|OutputStream
name|out
parameter_list|)
throws|throws
name|IOException
throws|,
name|WebApplicationException
block|{
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
name|log
operator|.
name|debug
argument_list|(
literal|"Rendering the list of recipes."
argument_list|)
expr_stmt|;
name|OWLOntologyManager
name|manager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|factory
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLOntology
name|ontology
decl_stmt|;
try|try
block|{
name|ontology
operator|=
name|manager
operator|.
name|createOntology
argument_list|()
expr_stmt|;
name|String
name|recipeClassURI
init|=
name|Symbols
operator|.
name|Recipe
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|IRI
name|recipeClassIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|recipeClassURI
argument_list|)
decl_stmt|;
name|OWLClass
name|owlRecipeClass
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|recipeClassIRI
argument_list|)
decl_stmt|;
name|String
name|descriptionURI
init|=
name|Symbols
operator|.
name|description
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|IRI
name|descriptionIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|descriptionURI
argument_list|)
decl_stmt|;
name|OWLDataProperty
name|descriptionProperty
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|descriptionIRI
argument_list|)
decl_stmt|;
if|if
condition|(
name|recipeList
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Converting the recipe list to OWL."
argument_list|)
expr_stmt|;
for|for
control|(
name|Recipe
name|recipe
range|:
name|recipeList
control|)
block|{
name|String
name|recipeURI
init|=
name|recipe
operator|.
name|getRecipeID
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|IRI
name|recipeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|recipeURI
argument_list|)
decl_stmt|;
name|OWLIndividual
name|recipeIndividual
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|recipeIRI
argument_list|)
decl_stmt|;
name|OWLAxiom
name|axiom
init|=
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|owlRecipeClass
argument_list|,
name|recipeIndividual
argument_list|)
decl_stmt|;
name|manager
operator|.
name|addAxiom
argument_list|(
name|ontology
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
name|String
name|description
init|=
name|recipe
operator|.
name|getRecipeDescription
argument_list|()
decl_stmt|;
if|if
condition|(
name|description
operator|!=
literal|null
condition|)
block|{
name|axiom
operator|=
name|factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|descriptionProperty
argument_list|,
name|recipeIndividual
argument_list|,
name|description
argument_list|)
expr_stmt|;
name|manager
operator|.
name|addAxiom
argument_list|(
name|ontology
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/*              * Write the ontology with the list of recipes              */
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|RDF_XML
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|RDFXMLOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|OWL_XML
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|OWLXMLOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|MANCHESTER_OWL
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|ManchesterOWLSyntaxOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|OWLFunctionalSyntaxOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|TURTLE
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|TurtleOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|RDF_JSON
argument_list|)
condition|)
block|{
name|TripleCollection
name|mGraph
init|=
name|OWLAPIToClerezzaConverter
operator|.
name|owlOntologyToClerezzaMGraph
argument_list|(
name|ontology
argument_list|)
decl_stmt|;
name|RdfJsonSerializingProvider
name|provider
init|=
operator|new
name|RdfJsonSerializingProvider
argument_list|()
decl_stmt|;
name|provider
operator|.
name|serialize
argument_list|(
name|out
argument_list|,
name|mGraph
argument_list|,
name|SupportedFormat
operator|.
name|RDF_JSON
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e1
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"An error occurred."
argument_list|,
name|e1
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

