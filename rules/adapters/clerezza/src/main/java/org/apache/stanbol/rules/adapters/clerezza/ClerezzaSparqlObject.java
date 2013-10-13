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
name|Expression
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
name|TriplePattern
import|;
end_import

begin_comment
comment|/**  *   * This object represents either a {@link TriplePattern} or an {@link Expression} or a {@link UriRef}  * internally to the Clerezza adpter.  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|ClerezzaSparqlObject
block|{
specifier|private
name|TriplePattern
name|triplePattern
decl_stmt|;
specifier|private
name|Expression
name|expression
decl_stmt|;
specifier|private
name|UriRef
name|uriRef
decl_stmt|;
specifier|public
name|ClerezzaSparqlObject
parameter_list|(
name|TriplePattern
name|triplePattern
parameter_list|)
block|{
name|this
operator|.
name|triplePattern
operator|=
name|triplePattern
expr_stmt|;
block|}
specifier|public
name|ClerezzaSparqlObject
parameter_list|(
name|Expression
name|expression
parameter_list|)
block|{
name|this
operator|.
name|expression
operator|=
name|expression
expr_stmt|;
block|}
specifier|public
name|ClerezzaSparqlObject
parameter_list|(
name|UriRef
name|uriRef
parameter_list|)
block|{
name|this
operator|.
name|uriRef
operator|=
name|uriRef
expr_stmt|;
block|}
comment|/**      * It returns the actual Clerezza value.<br/>      *       * It can be:      *<ul>      *<li>a {@link TriplePattern}      *<li>an {@link Expression}      *<li>a {@link UriRef}      *       * @return the object that can be in turn a {@link TriplePattern}, an {@link Expression}, and a      *         {@link UriRef}      */
specifier|public
name|Object
name|getClerezzaObject
parameter_list|()
block|{
if|if
condition|(
name|triplePattern
operator|!=
literal|null
condition|)
block|{
return|return
name|triplePattern
return|;
block|}
elseif|else
if|if
condition|(
name|expression
operator|!=
literal|null
condition|)
block|{
return|return
name|expression
return|;
block|}
else|else
block|{
return|return
name|uriRef
return|;
block|}
block|}
block|}
end_class

end_unit

