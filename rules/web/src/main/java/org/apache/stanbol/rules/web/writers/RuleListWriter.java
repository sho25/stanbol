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

begin_comment
comment|//import javax.servlet.ServletContext;
end_comment

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

begin_comment
comment|//import javax.ws.rs.core.Context;
end_comment

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
name|commons
operator|.
name|rdf
operator|.
name|Graph
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
name|Property
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
name|Reference
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

begin_comment
comment|//import org.apache.stanbol.rules.base.api.Recipe;
end_comment

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
name|Rule
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
name|RuleList
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
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|Object
operator|.
name|class
argument_list|)
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"javax.ws.rs"
argument_list|,
name|boolValue
operator|=
literal|true
argument_list|)
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
name|RuleListWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|RuleList
argument_list|>
block|{
annotation|@
name|Reference
specifier|protected
name|Serializer
name|serializer
decl_stmt|;
comment|//    protected ServletContext servletContext;
annotation|@
name|Reference
specifier|protected
name|RuleStore
name|ruleStore
decl_stmt|;
comment|//    public RuleListWriter() {
comment|//        Logger log = LoggerFactory.getLogger(getClass());
comment|//        this.servletContext = servletContext;
comment|//        log.info("Setting context to " + servletContext);
comment|//        serializer = (Serializer) this.servletContext.getAttribute(Serializer.class.getName());
comment|//        if (serializer == null) {
comment|//            log.error("Serializer not found in Servlet context.");
comment|//            serializer = new Serializer();
comment|//        }
comment|//        ruleStore = (RuleStore) this.servletContext.getAttribute(RuleStore.class.getName());
comment|//        if (serializer == null) {
comment|//            log.error("RuleStore not found in Servlet context.");
comment|//        }
comment|//    }
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|RuleList
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
name|RuleList
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
name|RuleList
name|ruleList
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
name|ruleClassURI
init|=
name|Symbols
operator|.
name|Rule
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
name|ruleClassIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ruleClassURI
argument_list|)
decl_stmt|;
name|OWLClass
name|owlRuleClass
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|ruleClassIRI
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
name|String
name|hasRuleURI
init|=
name|Symbols
operator|.
name|hasRule
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
name|hasRuleIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|hasRuleURI
argument_list|)
decl_stmt|;
name|OWLObjectProperty
name|hasRule
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|hasRuleIRI
argument_list|)
decl_stmt|;
name|String
name|ruleBodyURI
init|=
name|Symbols
operator|.
name|ruleBody
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
name|ruleBodyIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ruleBodyURI
argument_list|)
decl_stmt|;
name|OWLDataProperty
name|ruleBody
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|ruleBodyIRI
argument_list|)
decl_stmt|;
name|String
name|ruleHeadURI
init|=
name|Symbols
operator|.
name|ruleHead
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
name|ruleHeadIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ruleHeadURI
argument_list|)
decl_stmt|;
name|OWLDataProperty
name|ruleHead
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|ruleHeadIRI
argument_list|)
decl_stmt|;
if|if
condition|(
name|ruleList
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Rule
name|rule
range|:
name|ruleList
control|)
block|{
name|String
name|recipeId
init|=
name|rule
operator|.
name|getRecipe
argument_list|()
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
name|reicpeIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|recipeId
argument_list|)
decl_stmt|;
name|OWLIndividual
name|owlRecipe
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|reicpeIRI
argument_list|)
decl_stmt|;
name|String
name|ruleId
init|=
name|rule
operator|.
name|getRuleID
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
name|ruleIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|ruleId
argument_list|)
decl_stmt|;
name|OWLIndividual
name|owlRule
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|ruleIRI
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
name|owlRecipe
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
name|axiom
operator|=
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|owlRuleClass
argument_list|,
name|owlRule
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
name|axiom
operator|=
name|factory
operator|.
name|getOWLObjectPropertyAssertionAxiom
argument_list|(
name|hasRule
argument_list|,
name|owlRecipe
argument_list|,
name|owlRule
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
name|String
name|recipeDescription
init|=
name|rule
operator|.
name|getRecipe
argument_list|()
operator|.
name|getRecipeDescription
argument_list|()
decl_stmt|;
name|String
name|ruleDescription
init|=
name|rule
operator|.
name|getDescription
argument_list|()
decl_stmt|;
if|if
condition|(
name|recipeDescription
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
name|owlRecipe
argument_list|,
name|recipeDescription
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
if|if
condition|(
name|ruleDescription
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
name|owlRule
argument_list|,
name|ruleDescription
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
name|String
name|ruleContent
init|=
name|rule
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
index|[]
name|parts
init|=
name|ruleContent
operator|.
name|split
argument_list|(
literal|"\\->"
argument_list|)
decl_stmt|;
name|axiom
operator|=
name|factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|ruleBody
argument_list|,
name|owlRule
argument_list|,
name|parts
index|[
literal|0
index|]
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
name|axiom
operator|=
name|factory
operator|.
name|getOWLDataPropertyAssertionAxiom
argument_list|(
name|ruleHead
argument_list|,
name|owlRule
argument_list|,
name|parts
index|[
literal|1
index|]
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
name|Graph
name|mGraph
init|=
name|OWLAPIToClerezzaConverter
operator|.
name|owlOntologyToClerezzaGraph
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

