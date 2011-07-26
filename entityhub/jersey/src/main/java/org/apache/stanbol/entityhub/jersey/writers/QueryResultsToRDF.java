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
name|entityhub
operator|.
name|jersey
operator|.
name|writers
package|;
end_package

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
name|LiteralFactory
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
name|Triple
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
name|impl
operator|.
name|TripleImpl
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
name|entityhub
operator|.
name|query
operator|.
name|clerezza
operator|.
name|RdfQueryResultList
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Entity
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|rdf
operator|.
name|RdfResourceEnum
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|QueryResultList
import|;
end_import

begin_class
specifier|final
class|class
name|QueryResultsToRDF
block|{
specifier|private
name|QueryResultsToRDF
parameter_list|()
block|{
comment|/* do not create instances of utility classes */
block|}
comment|/**      * The URI used for the query result list (static for all responses)      */
specifier|static
specifier|final
name|UriRef
name|QUERY_RESULT_LIST
init|=
operator|new
name|UriRef
argument_list|(
name|RdfResourceEnum
operator|.
name|QueryResultSet
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * The property used for all results      */
specifier|static
specifier|final
name|UriRef
name|QUERY_RESULT
init|=
operator|new
name|UriRef
argument_list|(
name|RdfResourceEnum
operator|.
name|queryResult
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * The property used for the JSON serialised FieldQuery (STANBOL-298)      */
specifier|static
specifier|final
name|UriRef
name|FIELD_QUERY
init|=
operator|new
name|UriRef
argument_list|(
name|RdfResourceEnum
operator|.
name|queryResult
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * The LiteralFactory retrieved from {@link EntityToRDF#literalFactory}      */
specifier|static
specifier|final
name|LiteralFactory
name|literalFactory
init|=
name|EntityToRDF
operator|.
name|literalFactory
decl_stmt|;
specifier|static
name|MGraph
name|toRDF
parameter_list|(
name|QueryResultList
argument_list|<
name|?
argument_list|>
name|resultList
parameter_list|)
block|{
specifier|final
name|MGraph
name|resultGraph
decl_stmt|;
name|Class
argument_list|<
name|?
argument_list|>
name|type
init|=
name|resultList
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
name|String
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|resultGraph
operator|=
operator|new
name|SimpleMGraph
argument_list|()
expr_stmt|;
comment|//create a new Graph
for|for
control|(
name|Object
name|result
range|:
name|resultList
control|)
block|{
comment|//add a triple to each reference in the result set
name|resultGraph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|QUERY_RESULT_LIST
argument_list|,
name|FIELD_QUERY
argument_list|,
operator|new
name|UriRef
argument_list|(
name|result
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//first determine the type of the resultList
specifier|final
name|boolean
name|isSignType
decl_stmt|;
if|if
condition|(
name|Representation
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|isSignType
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Representation
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|isSignType
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
comment|//incompatible type -> throw an Exception
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed type "
operator|+
name|type
operator|+
literal|" is not supported"
argument_list|)
throw|;
block|}
comment|//special treatment for RdfQueryResultList for increased performance
if|if
condition|(
name|resultList
operator|instanceof
name|RdfQueryResultList
condition|)
block|{
name|resultGraph
operator|=
operator|(
operator|(
name|RdfQueryResultList
operator|)
name|resultList
operator|)
operator|.
name|getResultGraph
argument_list|()
expr_stmt|;
if|if
condition|(
name|isSignType
condition|)
block|{
comment|//if we build a ResultList for Signs, that we need to do more things
comment|//first remove all triples representing results
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|resultTripleIt
init|=
name|resultGraph
operator|.
name|filter
argument_list|(
name|QUERY_RESULT_LIST
argument_list|,
name|FIELD_QUERY
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|resultTripleIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|resultTripleIt
operator|.
name|next
argument_list|()
expr_stmt|;
name|resultTripleIt
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
comment|//now add the Sign specific triples and add result triples
comment|//to the Sign IDs
for|for
control|(
name|Object
name|result
range|:
name|resultList
control|)
block|{
name|UriRef
name|signId
init|=
operator|new
name|UriRef
argument_list|(
operator|(
operator|(
name|Entity
operator|)
name|result
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
name|EntityToRDF
operator|.
name|addEntityTriplesToGraph
argument_list|(
name|resultGraph
argument_list|,
operator|(
name|Entity
operator|)
name|result
argument_list|)
expr_stmt|;
name|resultGraph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|QUERY_RESULT_LIST
argument_list|,
name|FIELD_QUERY
argument_list|,
name|signId
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|//any other implementation of the QueryResultList interface
name|resultGraph
operator|=
operator|new
name|SimpleMGraph
argument_list|()
expr_stmt|;
comment|//create a new graph
if|if
condition|(
name|Representation
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
for|for
control|(
name|Object
name|result
range|:
name|resultList
control|)
block|{
name|UriRef
name|resultId
decl_stmt|;
if|if
condition|(
operator|!
name|isSignType
condition|)
block|{
name|EntityToRDF
operator|.
name|addRDFTo
argument_list|(
name|resultGraph
argument_list|,
operator|(
name|Representation
operator|)
name|result
argument_list|)
expr_stmt|;
name|resultId
operator|=
operator|new
name|UriRef
argument_list|(
operator|(
operator|(
name|Representation
operator|)
name|result
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|EntityToRDF
operator|.
name|addRDFTo
argument_list|(
name|resultGraph
argument_list|,
operator|(
name|Entity
operator|)
name|result
argument_list|)
expr_stmt|;
name|resultId
operator|=
operator|new
name|UriRef
argument_list|(
operator|(
operator|(
name|Entity
operator|)
name|result
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//Note: In case of Representation this Triple points to
comment|//      the representation. In case of Signs it points to
comment|//      the sign.
name|resultGraph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|QUERY_RESULT_LIST
argument_list|,
name|FIELD_QUERY
argument_list|,
name|resultId
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|resultGraph
return|;
block|}
block|}
end_class

end_unit

