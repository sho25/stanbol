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
name|sparql
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
name|rdf
operator|.
name|core
operator|.
name|BNode
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
name|sparql
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
comment|/**  * It adapts any BlankNodeAtom to a simple triple pattern in Clerezza.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|BlankNodeAtom
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
name|BlankNodeAtom
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
name|BlankNodeAtom
operator|)
name|ruleAtom
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
name|IObjectAtom
name|argument2UriResource
init|=
name|tmp
operator|.
name|getArgument2
argument_list|()
decl_stmt|;
name|IObjectAtom
name|argument1UriResource
init|=
name|tmp
operator|.
name|getArgument1
argument_list|()
decl_stmt|;
name|ClerezzaSparqlObject
name|subjectCSO
init|=
operator|(
name|ClerezzaSparqlObject
operator|)
name|adapter
operator|.
name|adaptTo
argument_list|(
name|argument2UriResource
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|ClerezzaSparqlObject
name|predicateCSO
init|=
operator|(
name|ClerezzaSparqlObject
operator|)
name|adapter
operator|.
name|adaptTo
argument_list|(
name|argument1UriResource
argument_list|,
name|ConstructQuery
operator|.
name|class
argument_list|)
decl_stmt|;
name|subject
operator|=
operator|new
name|UriRefOrVariable
argument_list|(
operator|(
name|UriRef
operator|)
name|subjectCSO
operator|.
name|getClerezzaObject
argument_list|()
argument_list|)
expr_stmt|;
name|predicate
operator|=
operator|new
name|UriRefOrVariable
argument_list|(
operator|(
name|UriRef
operator|)
name|predicateCSO
operator|.
name|getClerezzaObject
argument_list|()
argument_list|)
expr_stmt|;
name|object
operator|=
operator|new
name|ResourceOrVariable
argument_list|(
operator|new
name|BNode
argument_list|()
argument_list|)
expr_stmt|;
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

