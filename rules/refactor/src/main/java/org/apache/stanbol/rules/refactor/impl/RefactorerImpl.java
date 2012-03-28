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
name|refactor
operator|.
name|impl
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
name|util
operator|.
name|Dictionary
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
name|MGraph
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
name|UriRef
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|WeightedTcProvider
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
name|impl
operator|.
name|SimpleMGraph
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
name|sparql
operator|.
name|query
operator|.
name|ConstructQuery
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
name|Activate
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
name|Deactivate
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
name|NoSuchRecipeException
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
name|RecipeConstructionException
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
name|RuleAdapter
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
name|RuleAdapterManager
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
name|RuleAtomCallExeption
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
name|UnavailableRuleObjectException
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
name|UnsupportedTypeForExportException
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
name|arqextention
operator|.
name|CreatePropertyURIStringFromLabel
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
name|arqextention
operator|.
name|CreateStandardLabel
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
name|arqextention
operator|.
name|CreateURI
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
name|refactor
operator|.
name|api
operator|.
name|Refactorer
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
name|refactor
operator|.
name|api
operator|.
name|RefactoringException
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
name|ComponentContext
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
name|reasoner
operator|.
name|Reasoner
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
name|sparql
operator|.
name|function
operator|.
name|FunctionRegistry
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
name|sparql
operator|.
name|pfunction
operator|.
name|PropertyFunctionRegistry
import|;
end_import

begin_comment
comment|/**  * The RefactorerImpl is the concrete implementation of the Refactorer interface defined in the rule APIs of  * Stanbol. A Refacter is able to perform RDF graph refactorings and mappings.  *   * @author anuzzolese  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|Refactorer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|RefactorerImpl
implements|implements
name|Refactorer
block|{
specifier|private
specifier|final
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
annotation|@
name|Reference
specifier|protected
name|RuleStore
name|ruleStore
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|TcManager
name|tcManager
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|WeightedTcProvider
name|weightedTcProvider
decl_stmt|;
annotation|@
name|Reference
specifier|protected
name|RuleAdapterManager
name|ruleAdapterManager
decl_stmt|;
comment|/**      * This default constructor is<b>only</b> intended to be used by the OSGI environment with Service      * Component Runtime support.      *<p>      * DO NOT USE to manually create instances - the RefactorerImpl instances do need to be configured! YOU      * NEED TO USE      * {@link #RefactorerImpl(WeightedTcProvider, Serializer, TcManager, ONManager, SemionManager, RuleStore, Reasoner, Dictionary)}      * or its overloads, to parse the configuration and then initialise the rule store if running outside a      * OSGI environment.      */
specifier|public
name|RefactorerImpl
parameter_list|()
block|{      }
comment|/**      * Basic constructor to be used if outside of an OSGi environment. Invokes default constructor.      *       * @param weightedTcProvider      * @param serializer      * @param tcManager      * @param onManager      * @param semionManager      * @param ruleStore      * @param kReSReasoner      * @param configuration      */
specifier|public
name|RefactorerImpl
parameter_list|(
name|WeightedTcProvider
name|weightedTcProvider
parameter_list|,
name|TcManager
name|tcManager
parameter_list|,
name|RuleStore
name|ruleStore
parameter_list|,
name|RuleAdapterManager
name|ruleAdapterManager
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|weightedTcProvider
operator|=
name|weightedTcProvider
expr_stmt|;
name|this
operator|.
name|tcManager
operator|=
name|tcManager
expr_stmt|;
name|this
operator|.
name|ruleStore
operator|=
name|ruleStore
expr_stmt|;
name|this
operator|.
name|ruleAdapterManager
operator|=
name|ruleAdapterManager
expr_stmt|;
name|activate
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
block|}
comment|/**      * Used to configure an instance within an OSGi container.      *       * @throws IOException      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|getClass
argument_list|()
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No valid"
operator|+
name|ComponentContext
operator|.
name|class
operator|+
literal|" parsed in activate!"
argument_list|)
throw|;
block|}
name|activate
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|activate
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|PropertyFunctionRegistry
operator|.
name|get
argument_list|()
operator|.
name|put
argument_list|(
literal|"http://www.stlab.istc.cnr.it/semion/function#createURI"
argument_list|,
name|CreateURI
operator|.
name|class
argument_list|)
expr_stmt|;
name|FunctionRegistry
operator|.
name|get
argument_list|()
operator|.
name|put
argument_list|(
literal|"http://www.stlab.istc.cnr.it/semion/function#createLabel"
argument_list|,
name|CreateStandardLabel
operator|.
name|class
argument_list|)
expr_stmt|;
name|FunctionRegistry
operator|.
name|get
argument_list|()
operator|.
name|put
argument_list|(
literal|"http://www.stlab.istc.cnr.it/semion/function#propString"
argument_list|,
name|CreatePropertyURIStringFromLabel
operator|.
name|class
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
name|Refactorer
operator|.
name|class
operator|+
literal|"activated."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|getClass
argument_list|()
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|weightedTcProvider
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|tcManager
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|ruleStore
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|MGraph
name|getRefactoredDataSet
parameter_list|(
name|UriRef
name|uriRef
parameter_list|)
block|{
return|return
name|weightedTcProvider
operator|.
name|getMGraph
argument_list|(
name|uriRef
argument_list|)
return|;
block|}
comment|/**      * Execute a sparql construct on Clerezza.      *       * @param sparql      * @param datasetID      * @return      */
specifier|private
name|Graph
name|sparqlConstruct
parameter_list|(
name|ConstructQuery
name|constructQuery
parameter_list|,
name|UriRef
name|datasetID
parameter_list|)
block|{
name|MGraph
name|graph
init|=
name|weightedTcProvider
operator|.
name|getMGraph
argument_list|(
name|datasetID
argument_list|)
decl_stmt|;
return|return
name|sparqlConstruct
argument_list|(
name|constructQuery
argument_list|,
name|graph
argument_list|)
return|;
block|}
specifier|private
name|Graph
name|sparqlConstruct
parameter_list|(
name|ConstructQuery
name|constructQuery
parameter_list|,
name|TripleCollection
name|tripleCollection
parameter_list|)
block|{
return|return
name|tcManager
operator|.
name|executeSparqlQuery
argument_list|(
name|constructQuery
argument_list|,
name|tripleCollection
argument_list|)
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
name|void
name|graphRefactoring
parameter_list|(
name|UriRef
name|refactoredOntologyID
parameter_list|,
name|UriRef
name|datasetID
parameter_list|,
name|UriRef
name|recipeID
parameter_list|)
throws|throws
name|RefactoringException
throws|,
name|NoSuchRecipeException
block|{
name|Recipe
name|recipe
decl_stmt|;
try|try
block|{
try|try
block|{
name|recipe
operator|=
name|ruleStore
operator|.
name|getRecipe
argument_list|(
name|recipeID
argument_list|)
expr_stmt|;
name|RuleAdapter
name|ruleAdapter
init|=
name|ruleAdapterManager
operator|.
name|getAdapter
argument_list|(
name|recipe
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ConstructQuery
argument_list|>
name|constructQueries
init|=
operator|(
name|List
argument_list|<
name|ConstructQuery
argument_list|>
operator|)
name|ruleAdapter
operator|.
name|adaptTo
argument_list|(
name|recipe
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|MGraph
name|mGraph
init|=
name|tcManager
operator|.
name|createMGraph
argument_list|(
name|refactoredOntologyID
argument_list|)
decl_stmt|;
for|for
control|(
name|ConstructQuery
name|constructQuery
range|:
name|constructQueries
control|)
block|{
name|mGraph
operator|.
name|addAll
argument_list|(
name|this
operator|.
name|sparqlConstruct
argument_list|(
name|constructQuery
argument_list|,
name|datasetID
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|RecipeConstructionException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|UnavailableRuleObjectException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|UnsupportedTypeForExportException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|RuleAtomCallExeption
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchRecipeException
name|e1
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"No Such recipe in the Rule Store"
argument_list|,
name|e1
argument_list|)
expr_stmt|;
throw|throw
name|e1
throw|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
name|TripleCollection
name|graphRefactoring
parameter_list|(
name|UriRef
name|graphID
parameter_list|,
name|UriRef
name|recipeID
parameter_list|)
throws|throws
name|RefactoringException
throws|,
name|NoSuchRecipeException
block|{
name|MGraph
name|unionMGraph
init|=
literal|null
decl_stmt|;
comment|// JenaToOwlConvert jenaToOwlConvert = new JenaToOwlConvert();
comment|// OntModel ontModel =
comment|// jenaToOwlConvert.ModelOwlToJenaConvert(inputOntology, "RDF/XML");
name|Recipe
name|recipe
decl_stmt|;
try|try
block|{
name|recipe
operator|=
name|ruleStore
operator|.
name|getRecipe
argument_list|(
name|recipeID
argument_list|)
expr_stmt|;
name|RuleAdapter
name|ruleAdapter
init|=
name|ruleAdapterManager
operator|.
name|getAdapter
argument_list|(
name|recipe
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ConstructQuery
argument_list|>
name|constructQueries
init|=
operator|(
name|List
argument_list|<
name|ConstructQuery
argument_list|>
operator|)
name|ruleAdapter
operator|.
name|adaptTo
argument_list|(
name|recipe
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|unionMGraph
operator|=
operator|new
name|SimpleMGraph
argument_list|()
expr_stmt|;
for|for
control|(
name|ConstructQuery
name|constructQuery
range|:
name|constructQueries
control|)
block|{
name|unionMGraph
operator|.
name|addAll
argument_list|(
name|this
operator|.
name|sparqlConstruct
argument_list|(
name|constructQuery
argument_list|,
name|graphID
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchRecipeException
name|e1
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Refactor : No Such recipe in the Rule Store"
argument_list|,
name|e1
argument_list|)
expr_stmt|;
throw|throw
name|e1
throw|;
block|}
catch|catch
parameter_list|(
name|RecipeConstructionException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|UnavailableRuleObjectException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|UnsupportedTypeForExportException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|RuleAtomCallExeption
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|unionMGraph
operator|.
name|getGraph
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
name|TripleCollection
name|graphRefactoring
parameter_list|(
name|TripleCollection
name|inputGraph
parameter_list|,
name|Recipe
name|recipe
parameter_list|)
throws|throws
name|RefactoringException
block|{
name|RuleAdapter
name|ruleAdapter
decl_stmt|;
try|try
block|{
name|ruleAdapter
operator|=
name|ruleAdapterManager
operator|.
name|getAdapter
argument_list|(
name|recipe
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|ConstructQuery
argument_list|>
name|constructQueries
init|=
operator|(
name|List
argument_list|<
name|ConstructQuery
argument_list|>
operator|)
name|ruleAdapter
operator|.
name|adaptTo
argument_list|(
name|recipe
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"RULE LIST SIZE : "
operator|+
name|constructQueries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|MGraph
name|unionMGraph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
for|for
control|(
name|ConstructQuery
name|constructQuery
range|:
name|constructQueries
control|)
block|{
name|unionMGraph
operator|.
name|addAll
argument_list|(
name|sparqlConstruct
argument_list|(
name|constructQuery
argument_list|,
name|inputGraph
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|unionMGraph
return|;
block|}
catch|catch
parameter_list|(
name|UnavailableRuleObjectException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|UnsupportedTypeForExportException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|RuleAtomCallExeption
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RefactoringException
argument_list|(
literal|"The cause of the refactoring excpetion is: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

