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
name|clerezza
operator|.
name|atoms
package|;
end_package

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
name|ResourceOrVariable
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
name|UriRefOrVariable
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
name|Variable
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
name|impl
operator|.
name|SimpleTriplePattern
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
name|AbstractAdaptableAtom
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
name|clerezza
operator|.
name|ClerezzaSparqlObject
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
name|atoms
operator|.
name|IObjectAtom
import|;
end_import

begin_comment
comment|/**  * It adapts any IndividualPropertyAtom to a simple triple pattern in Clerezza.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|IndividualPropertyAtom
extends|extends
name|AbstractAdaptableAtom
block|{
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
name|T
name|adapt
parameter_list|(
name|RuleAtom
name|ruleAtom
parameter_list|)
throws|throws
name|RuleAtomCallExeption
throws|,
name|UnavailableRuleObjectException
throws|,
name|UnsupportedTypeForExportException
block|{
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
operator|.
name|IndividualPropertyAtom
name|tmp
init|=
operator|(
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
operator|.
name|IndividualPropertyAtom
operator|)
name|ruleAtom
decl_stmt|;
name|IObjectAtom
name|argument1
init|=
name|tmp
operator|.
name|getArgument1
argument_list|()
decl_stmt|;
name|IObjectAtom
name|objectProperty
init|=
name|tmp
operator|.
name|getObjectProperty
argument_list|()
decl_stmt|;
name|IObjectAtom
name|argument2
init|=
name|tmp
operator|.
name|getArgument2
argument_list|()
decl_stmt|;
name|ClerezzaSparqlObject
name|argument1CSO
init|=
operator|(
name|ClerezzaSparqlObject
operator|)
name|adapter
operator|.
name|adaptTo
argument_list|(
name|argument1
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|ClerezzaSparqlObject
name|datatypePropertyCSO
init|=
operator|(
name|ClerezzaSparqlObject
operator|)
name|adapter
operator|.
name|adaptTo
argument_list|(
name|objectProperty
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|ClerezzaSparqlObject
name|argument2CSO
init|=
operator|(
name|ClerezzaSparqlObject
operator|)
name|adapter
operator|.
name|adaptTo
argument_list|(
name|argument2
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|Object
name|arg1
init|=
name|argument1CSO
operator|.
name|getClerezzaObject
argument_list|()
decl_stmt|;
name|Object
name|dt
init|=
name|datatypePropertyCSO
operator|.
name|getClerezzaObject
argument_list|()
decl_stmt|;
name|Object
name|arg2
init|=
name|argument2CSO
operator|.
name|getClerezzaObject
argument_list|()
decl_stmt|;
name|UriRefOrVariable
name|subject
decl_stmt|;
name|UriRefOrVariable
name|predicate
decl_stmt|;
name|ResourceOrVariable
name|object
decl_stmt|;
if|if
condition|(
name|arg1
operator|instanceof
name|Variable
condition|)
block|{
name|subject
operator|=
operator|new
name|UriRefOrVariable
argument_list|(
operator|(
name|Variable
operator|)
name|arg1
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg1
operator|instanceof
name|IRI
condition|)
block|{
name|subject
operator|=
operator|new
name|UriRefOrVariable
argument_list|(
operator|(
name|IRI
operator|)
name|arg1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|RuleAtomCallExeption
argument_list|(
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
if|if
condition|(
name|dt
operator|instanceof
name|Variable
condition|)
block|{
name|predicate
operator|=
operator|new
name|UriRefOrVariable
argument_list|(
operator|(
name|Variable
operator|)
name|dt
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dt
operator|instanceof
name|IRI
condition|)
block|{
name|predicate
operator|=
operator|new
name|UriRefOrVariable
argument_list|(
operator|(
name|IRI
operator|)
name|dt
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|RuleAtomCallExeption
argument_list|(
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
if|if
condition|(
name|arg2
operator|instanceof
name|Variable
condition|)
block|{
name|object
operator|=
operator|new
name|UriRefOrVariable
argument_list|(
operator|(
name|Variable
operator|)
name|arg2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dt
operator|instanceof
name|IRI
condition|)
block|{
name|object
operator|=
operator|new
name|UriRefOrVariable
argument_list|(
operator|(
name|IRI
operator|)
name|arg2
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|RuleAtomCallExeption
argument_list|(
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
return|return
operator|(
name|T
operator|)
operator|new
name|ClerezzaSparqlObject
argument_list|(
operator|new
name|SimpleTriplePattern
argument_list|(
name|subject
argument_list|,
name|predicate
argument_list|,
name|object
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

