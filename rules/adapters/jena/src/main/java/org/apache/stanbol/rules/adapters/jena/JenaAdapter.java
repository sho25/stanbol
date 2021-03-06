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
name|adapters
operator|.
name|jena
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

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
name|Dictionary
import|;
end_import

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|IRI
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
name|ResultSet
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
name|rules
operator|.
name|adapters
operator|.
name|AbstractRuleAdapter
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
name|adapters
operator|.
name|AdaptableAtom
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
name|adapters
operator|.
name|jena
operator|.
name|atoms
operator|.
name|VariableAtom
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
name|Adaptable
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
name|RuleAdaptersFactory
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
name|RuleAtom
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
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|AtomList
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
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|manager
operator|.
name|KB
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
name|RecipeImpl
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
name|parse
operator|.
name|RuleParserImpl
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
name|JenaRuntime
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
name|query
operator|.
name|Query
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
name|query
operator|.
name|QueryExecution
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
name|query
operator|.
name|QueryExecutionFactory
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
name|query
operator|.
name|QueryFactory
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
name|query
operator|.
name|ResultSetFormatter
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
name|query
operator|.
name|Syntax
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
name|InfModel
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
name|ModelFactory
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
name|rdf
operator|.
name|model
operator|.
name|StmtIterator
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
name|reasoner
operator|.
name|ReasonerRegistry
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
name|reasoner
operator|.
name|rulesys
operator|.
name|GenericRuleReasoner
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
name|GenericRuleReasonerFactory
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
name|OWLFBRuleReasonerFactory
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
name|util
operator|.
name|FileManager
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
name|util
operator|.
name|PrintUtil
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
name|ReasonerVocabulary
import|;
end_import

begin_import
import|import
name|com
operator|.
name|ibm
operator|.
name|icu
operator|.
name|util
operator|.
name|Output
import|;
end_import

begin_comment
comment|/**  * The adapter for Jena rules.<br/>  * The output object of this adapter is a {@link com.hp.hpl.jena.reasoner.rulesys.Rule} instance.<br/>  * For that reason the adaptTo method works only if the second argument is  *<code>com.hp.hpl.jena.reasoner.rulesys.Rule.class</code>  *   * @author anuzzolese  *   */
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
name|RuleAdapter
operator|.
name|class
argument_list|)
specifier|public
class|class
name|JenaAdapter
extends|extends
name|AbstractRuleAdapter
block|{
specifier|public
specifier|static
specifier|final
name|String
name|ARTIFACT
init|=
literal|"org.apache.stanbol.rules.adapters.jena.atoms"
decl_stmt|;
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
name|RuleStore
name|ruleStore
decl_stmt|;
annotation|@
name|Reference
name|RuleAdaptersFactory
name|ruleAdaptersFactory
decl_stmt|;
specifier|private
name|ComponentContext
name|componentContext
decl_stmt|;
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|variableMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|protected
parameter_list|<
name|T
parameter_list|>
name|T
name|adaptRecipeTo
parameter_list|(
name|Recipe
name|recipe
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
throws|throws
name|RuleAtomCallExeption
throws|,
name|UnsupportedTypeForExportException
throws|,
name|UnavailableRuleObjectException
block|{
name|List
argument_list|<
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
name|Rule
argument_list|>
name|jenaRules
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|type
operator|==
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
name|Rule
operator|.
name|class
condition|)
block|{
name|RuleList
name|ruleList
init|=
name|recipe
operator|.
name|getRuleList
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Rule
argument_list|>
name|ruleIterator
init|=
name|ruleList
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|jenaRules
operator|=
operator|new
name|ArrayList
argument_list|<
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
name|Rule
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|ruleIterator
operator|.
name|hasNext
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|jenaRules
operator|.
name|add
argument_list|(
operator|(
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
name|Rule
operator|)
name|adaptRuleTo
argument_list|(
name|ruleIterator
operator|.
name|next
argument_list|()
argument_list|,
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|UnsupportedTypeForExportException
argument_list|(
literal|"The Jena Adapter does not support the selected serialization : "
operator|+
name|type
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
throw|;
block|}
return|return
operator|(
name|T
operator|)
name|jenaRules
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|protected
parameter_list|<
name|T
parameter_list|>
name|T
name|adaptRuleTo
parameter_list|(
name|Rule
name|rule
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
throws|throws
name|RuleAtomCallExeption
throws|,
name|UnsupportedTypeForExportException
throws|,
name|UnavailableRuleObjectException
block|{
if|if
condition|(
name|type
operator|==
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
name|Rule
operator|.
name|class
condition|)
block|{
name|AtomList
name|bodyAtomList
init|=
name|rule
operator|.
name|getBody
argument_list|()
decl_stmt|;
name|AtomList
name|headAtomList
init|=
name|rule
operator|.
name|getHead
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|ClauseEntry
argument_list|>
name|headClauseEntries
init|=
operator|new
name|ArrayList
argument_list|<
name|ClauseEntry
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|ClauseEntry
argument_list|>
name|bodyClauseEntries
init|=
operator|new
name|ArrayList
argument_list|<
name|ClauseEntry
argument_list|>
argument_list|()
decl_stmt|;
name|variableMap
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
expr_stmt|;
name|Iterator
argument_list|<
name|RuleAtom
argument_list|>
name|it
init|=
name|headAtomList
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
name|RuleAtom
name|atom
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|ClauseEntry
name|clauseEntry
init|=
name|adaptRuleAtomTo
argument_list|(
name|atom
argument_list|,
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
name|Rule
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|clauseEntry
operator|instanceof
name|HigherOrderClauseEntry
condition|)
block|{
name|List
argument_list|<
name|ClauseEntry
argument_list|>
name|clauseEntries
init|=
operator|(
operator|(
name|HigherOrderClauseEntry
operator|)
name|clauseEntry
operator|)
operator|.
name|getClauseEntries
argument_list|()
decl_stmt|;
for|for
control|(
name|ClauseEntry
name|ce
range|:
name|clauseEntries
control|)
block|{
name|headClauseEntries
operator|.
name|add
argument_list|(
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|headClauseEntries
operator|.
name|add
argument_list|(
name|clauseEntry
argument_list|)
expr_stmt|;
block|}
block|}
name|it
operator|=
name|bodyAtomList
operator|.
name|iterator
argument_list|()
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|RuleAtom
name|atom
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|ClauseEntry
name|clauseEntry
init|=
name|adaptRuleAtomTo
argument_list|(
name|atom
argument_list|,
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
name|Rule
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|clauseEntry
operator|instanceof
name|HigherOrderClauseEntry
condition|)
block|{
name|List
argument_list|<
name|ClauseEntry
argument_list|>
name|clauseEntries
init|=
operator|(
operator|(
name|HigherOrderClauseEntry
operator|)
name|clauseEntry
operator|)
operator|.
name|getClauseEntries
argument_list|()
decl_stmt|;
for|for
control|(
name|ClauseEntry
name|ce
range|:
name|clauseEntries
control|)
block|{
name|bodyClauseEntries
operator|.
name|add
argument_list|(
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|bodyClauseEntries
operator|.
name|add
argument_list|(
name|clauseEntry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|(
name|T
operator|)
operator|new
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
name|Rule
argument_list|(
name|rule
operator|.
name|getRuleName
argument_list|()
argument_list|,
name|headClauseEntries
argument_list|,
name|bodyClauseEntries
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|UnsupportedTypeForExportException
argument_list|(
literal|"The adapter "
operator|+
name|getClass
argument_list|()
operator|+
literal|" does not support type : "
operator|+
name|type
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
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
specifier|protected
parameter_list|<
name|T
parameter_list|>
name|T
name|adaptRuleAtomTo
parameter_list|(
name|RuleAtom
name|ruleAtom
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
throws|throws
name|RuleAtomCallExeption
throws|,
name|UnsupportedTypeForExportException
throws|,
name|UnavailableRuleObjectException
block|{
if|if
condition|(
name|type
operator|==
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
name|Rule
operator|.
name|class
condition|)
block|{
name|String
name|className
init|=
name|ruleAtom
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
decl_stmt|;
name|String
name|canonicalName
init|=
name|ARTIFACT
operator|+
literal|"."
operator|+
name|className
decl_stmt|;
try|try
block|{
name|Class
argument_list|<
name|AdaptableAtom
argument_list|>
name|jenaAtomClass
init|=
operator|(
name|Class
argument_list|<
name|AdaptableAtom
argument_list|>
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|canonicalName
argument_list|)
decl_stmt|;
try|try
block|{
name|AdaptableAtom
name|jenaAtom
init|=
name|jenaAtomClass
operator|.
name|newInstance
argument_list|()
decl_stmt|;
if|if
condition|(
name|jenaAtom
operator|instanceof
name|VariableAtom
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Class equals"
argument_list|)
expr_stmt|;
block|}
name|jenaAtom
operator|.
name|setRuleAdapter
argument_list|(
name|this
argument_list|)
expr_stmt|;
return|return
operator|(
name|T
operator|)
name|jenaAtom
operator|.
name|adapt
argument_list|(
name|ruleAtom
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|InstantiationException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|UnsupportedTypeForExportException
argument_list|(
literal|"The adapter "
operator|+
name|getClass
argument_list|()
operator|+
literal|" does not support type : "
operator|+
name|type
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
throw|;
block|}
return|return
literal|null
return|;
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
name|JenaAdapter
operator|.
name|class
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
name|componentContext
operator|=
name|context
expr_stmt|;
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
comment|/**      * Should be called within both OSGi and non-OSGi environments.      *       * @param configuration      * @throws IOException      */
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
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"The Jena Adapter for Stanbol Rules  is active"
argument_list|,
name|this
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
name|JenaAdapter
operator|.
name|class
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|Class
argument_list|<
name|T
argument_list|>
name|getExportClass
parameter_list|()
block|{
return|return
operator|(
name|Class
argument_list|<
name|T
argument_list|>
operator|)
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
name|Rule
operator|.
name|class
return|;
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|boolean
name|canAdaptTo
parameter_list|(
name|Adaptable
name|adaptable
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
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
name|Rule
operator|.
name|class
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|RuleAdapter
name|ruleAdapter
init|=
operator|new
name|JenaAdapter
argument_list|()
decl_stmt|;
try|try
block|{
name|KB
name|kb
init|=
name|RuleParserImpl
operator|.
name|parse
argument_list|(
literal|"http://sssw.org/2012/rules/"
argument_list|,
operator|new
name|FileInputStream
argument_list|(
literal|"/Users/mac/Documents/CNR/SSSW2012/rules/exercise1"
argument_list|)
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Rules: "
operator|+
name|kb
operator|.
name|getRuleList
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Recipe
name|recipe
init|=
operator|new
name|RecipeImpl
argument_list|(
operator|new
name|IRI
argument_list|(
literal|"http://sssw.org/2012/rules/"
argument_list|)
argument_list|,
literal|"Recipe"
argument_list|,
name|kb
operator|.
name|getRuleList
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
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
name|Rule
argument_list|>
name|jenaRules
init|=
operator|(
name|List
argument_list|<
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
name|Rule
argument_list|>
operator|)
name|ruleAdapter
operator|.
name|adaptTo
argument_list|(
name|recipe
argument_list|,
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
name|Rule
operator|.
name|class
argument_list|)
decl_stmt|;
name|String
name|rules
init|=
literal|"[ Exercise1: (http://dbpedia.org/resource/Madrid http://dbpedia.org/ontology/locationOf ?location) (?location rdf:type http://dbpedia.org/ontology/Museum) (?location http://dbpedia.org/ontology/numberOfVisitors ?visitors) greaterThan(?visitors '2000000'^^http://www.w3.org/2001/XMLSchema#integer) -> (?location rdf:type http://www.mytravels.com/Itinerary/MadridItinerary) ]"
decl_stmt|;
comment|//List<com.hp.hpl.jena.reasoner.rulesys.Rule> jenaRules = com.hp.hpl.jena.reasoner.rulesys.Rule.parseRules(rules);
for|for
control|(
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
name|Rule
name|jenaRule
range|:
name|jenaRules
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|jenaRule
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Model
name|m
init|=
name|ModelFactory
operator|.
name|createDefaultModel
argument_list|()
decl_stmt|;
name|Resource
name|configuration
init|=
name|m
operator|.
name|createResource
argument_list|()
decl_stmt|;
name|configuration
operator|.
name|addProperty
argument_list|(
name|ReasonerVocabulary
operator|.
name|PROPruleMode
argument_list|,
literal|"hybrid"
argument_list|)
expr_stmt|;
comment|//Model model = FileManager.get().loadModel("/Users/mac/Documents/workspaceMyStanbol/sssw2012/events.rdf");
name|Model
name|model
init|=
name|FileManager
operator|.
name|get
argument_list|()
operator|.
name|loadModel
argument_list|(
literal|"/Users/mac/Documents/CNR/SSSW2012/datasets_new/Exercise1.rdf"
argument_list|)
decl_stmt|;
comment|//GenericRuleReasoner reasoner = new GenericRuleReasoner(jenaRules);
comment|//GenericRuleReasoner reasoner = new GenericRuleReasoner(com.hp.hpl.jena.reasoner.rulesys.Rule.parseRules(rules));
name|GenericRuleReasoner
name|reasoner
init|=
operator|new
name|GenericRuleReasoner
argument_list|(
name|jenaRules
argument_list|)
decl_stmt|;
name|reasoner
operator|.
name|setOWLTranslation
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// not needed in RDFS case
name|reasoner
operator|.
name|setTransitiveClosureCaching
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|InfModel
name|infModel
init|=
name|ModelFactory
operator|.
name|createInfModel
argument_list|(
name|reasoner
argument_list|,
name|model
argument_list|)
decl_stmt|;
name|infModel
operator|.
name|prepare
argument_list|()
expr_stmt|;
name|infModel
operator|.
name|getDeductionsModel
argument_list|()
operator|.
name|write
argument_list|(
name|System
operator|.
name|out
argument_list|)
expr_stmt|;
comment|//String sparql = "select * where {?s a<http://www.mytravels.com/Itinerary/MovieCityMuseums> }";
comment|//String sparql = "select * where {?s a<http://www.mytravels.com/Itinerary/CityEventItinerary> }";
name|String
name|sparql
init|=
literal|"select * where {?s a<http://www.mytravels.com/Itinerary/MadridItinerary> }"
decl_stmt|;
comment|//String sparql = "select * where {?s a<http://linkedevents.org/ontology/cazzo> }";
comment|//String sparql = "select * where {?s a<http://www.mytravels.com/Itinerary/MovieCityItinerary> }";
name|Query
name|query
init|=
name|QueryFactory
operator|.
name|create
argument_list|(
name|sparql
argument_list|,
name|Syntax
operator|.
name|syntaxARQ
argument_list|)
decl_stmt|;
name|QueryExecution
name|queryExecution
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|infModel
argument_list|)
decl_stmt|;
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|ResultSet
name|resultSet
init|=
name|queryExecution
operator|.
name|execSelect
argument_list|()
decl_stmt|;
name|ResultSetFormatter
operator|.
name|out
argument_list|(
name|System
operator|.
name|out
argument_list|,
name|resultSet
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuleAtomCallExeption
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnavailableRuleObjectException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedTypeForExportException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|getVariableMap
parameter_list|()
block|{
return|return
name|variableMap
return|;
block|}
block|}
end_class

end_unit

