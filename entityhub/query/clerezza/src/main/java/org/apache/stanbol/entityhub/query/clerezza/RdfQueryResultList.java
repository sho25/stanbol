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
name|query
operator|.
name|clerezza
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|Set
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
name|stanbol
operator|.
name|entityhub
operator|.
name|model
operator|.
name|clerezza
operator|.
name|RdfRepresentation
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
name|query
operator|.
name|FieldQuery
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
name|util
operator|.
name|ModelUtils
import|;
end_import

begin_class
specifier|public
class|class
name|RdfQueryResultList
implements|implements
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
block|{
specifier|private
specifier|final
name|FieldQuery
name|query
decl_stmt|;
specifier|private
specifier|final
name|Collection
argument_list|<
name|RdfRepresentation
argument_list|>
name|results
decl_stmt|;
specifier|private
specifier|final
name|MGraph
name|resultGraph
decl_stmt|;
specifier|public
name|RdfQueryResultList
parameter_list|(
name|FieldQuery
name|query
parameter_list|,
name|MGraph
name|resultGraph
parameter_list|)
block|{
if|if
condition|(
name|query
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parameter Query MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|resultGraph
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parameter \"MGraph resultGraph\" MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
name|this
operator|.
name|resultGraph
operator|=
name|resultGraph
expr_stmt|;
name|this
operator|.
name|results
operator|=
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|ModelUtils
operator|.
name|asCollection
argument_list|(
name|SparqlQueryUtils
operator|.
name|parseQueryResultsFromMGraph
argument_list|(
name|resultGraph
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|FieldQuery
name|getQuery
parameter_list|()
block|{
return|return
name|query
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|getSelectedFields
parameter_list|()
block|{
return|return
name|query
operator|.
name|getSelectedFields
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|results
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Iterator
argument_list|<
name|Representation
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
operator|new
name|Iterator
argument_list|<
name|Representation
argument_list|>
argument_list|()
block|{
specifier|private
name|Iterator
argument_list|<
name|RdfRepresentation
argument_list|>
name|it
init|=
name|results
operator|.
name|iterator
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|it
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|next
parameter_list|()
block|{
return|return
name|it
operator|.
name|next
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|it
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|int
name|size
parameter_list|()
block|{
return|return
name|results
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Getter for the RDF Graph holding the Results of the Query      * @return the RDF Graph with the Results      */
specifier|public
specifier|final
name|MGraph
name|getResultGraph
parameter_list|()
block|{
return|return
name|resultGraph
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Class
argument_list|<
name|Representation
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|Representation
operator|.
name|class
return|;
block|}
block|}
end_class

end_unit

