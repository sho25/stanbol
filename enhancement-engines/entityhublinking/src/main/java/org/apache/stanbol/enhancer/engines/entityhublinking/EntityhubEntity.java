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
name|enhancer
operator|.
name|engines
operator|.
name|entityhublinking
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
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|entitylinking
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
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
name|model
operator|.
name|clerezza
operator|.
name|RdfValueFactory
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
name|Text
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

begin_class
specifier|public
class|class
name|EntityhubEntity
extends|extends
name|Entity
block|{
specifier|private
specifier|static
name|RdfValueFactory
name|vf
init|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|UriRef
name|entityRanking
init|=
operator|new
name|UriRef
argument_list|(
name|RdfResourceEnum
operator|.
name|entityRank
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
name|EntityhubEntity
parameter_list|(
name|Representation
name|rep
parameter_list|,
name|Set
argument_list|<
name|UriRef
argument_list|>
name|fields
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|languages
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|UriRef
argument_list|(
name|rep
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
name|toGraph
argument_list|(
name|rep
argument_list|,
name|fields
argument_list|,
name|languages
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Float
name|getEntityRanking
parameter_list|()
block|{
return|return
name|EnhancementEngineHelper
operator|.
name|get
argument_list|(
name|data
argument_list|,
name|uri
argument_list|,
name|entityRanking
argument_list|,
name|Float
operator|.
name|class
argument_list|,
name|lf
argument_list|)
return|;
block|}
comment|/**      * Converts {@link Representation}s to RDF ({@link TripleCollection}) and      * also filter literals with languages other than the parsed one      * @param rep      * @param languages      * @return      */
specifier|private
specifier|static
name|TripleCollection
name|toGraph
parameter_list|(
name|Representation
name|rep
parameter_list|,
name|Set
argument_list|<
name|UriRef
argument_list|>
name|includeFields
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|languages
parameter_list|)
block|{
if|if
condition|(
name|rep
operator|instanceof
name|RdfRepresentation
condition|)
block|{
return|return
operator|(
operator|(
name|RdfRepresentation
operator|)
name|rep
operator|)
operator|.
name|getRdfGraph
argument_list|()
return|;
block|}
else|else
block|{
comment|//create the Clerezza Represenation
name|RdfRepresentation
name|clerezzaRep
init|=
name|vf
operator|.
name|createRepresentation
argument_list|(
name|rep
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
comment|//Copy all values field by field
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|rep
operator|.
name|getFieldNames
argument_list|()
init|;
name|fields
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|field
init|=
name|fields
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|includeFields
operator|==
literal|null
operator|||
name|includeFields
operator|.
name|contains
argument_list|(
name|field
argument_list|)
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|Object
argument_list|>
name|fieldValues
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|)
init|;
name|fieldValues
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|value
init|=
name|fieldValues
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|languages
operator|==
literal|null
operator|||
comment|//we need not to filter languages
operator|!
operator|(
name|value
operator|instanceof
name|Text
operator|)
operator|||
comment|//filter only Text values
name|languages
operator|.
name|contains
argument_list|(
operator|(
operator|(
name|Text
operator|)
name|value
operator|)
operator|.
name|getLanguage
argument_list|()
argument_list|)
condition|)
block|{
name|clerezzaRep
operator|.
name|add
argument_list|(
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|clerezzaRep
operator|.
name|getRdfGraph
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

