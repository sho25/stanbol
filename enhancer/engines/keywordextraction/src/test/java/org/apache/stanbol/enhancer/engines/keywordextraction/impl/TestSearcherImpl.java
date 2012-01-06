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
name|keywordextraction
operator|.
name|impl
package|;
end_package

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
name|Collection
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
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|SortedMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|tokenize
operator|.
name|Tokenizer
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
name|keywordextraction
operator|.
name|linking
operator|.
name|EntitySearcher
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

begin_class
specifier|public
class|class
name|TestSearcherImpl
implements|implements
name|EntitySearcher
block|{
specifier|private
specifier|final
name|String
name|nameField
decl_stmt|;
specifier|private
specifier|final
name|Tokenizer
name|tokenizer
decl_stmt|;
specifier|private
name|SortedMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Representation
argument_list|>
argument_list|>
name|data
init|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|Representation
argument_list|>
argument_list|>
argument_list|(
name|String
operator|.
name|CASE_INSENSITIVE_ORDER
argument_list|)
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Representation
argument_list|>
name|entities
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Representation
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|TestSearcherImpl
parameter_list|(
name|String
name|nameField
parameter_list|,
name|Tokenizer
name|tokenizer
parameter_list|)
block|{
name|this
operator|.
name|nameField
operator|=
name|nameField
expr_stmt|;
name|this
operator|.
name|tokenizer
operator|=
name|tokenizer
expr_stmt|;
block|}
specifier|public
name|void
name|addEntity
parameter_list|(
name|Representation
name|rep
parameter_list|)
block|{
name|entities
operator|.
name|put
argument_list|(
name|rep
operator|.
name|getId
argument_list|()
argument_list|,
name|rep
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Text
argument_list|>
name|labels
init|=
name|rep
operator|.
name|getText
argument_list|(
name|nameField
argument_list|)
decl_stmt|;
while|while
condition|(
name|labels
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Text
name|label
init|=
name|labels
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|token
range|:
name|tokenizer
operator|.
name|tokenize
argument_list|(
name|label
operator|.
name|getText
argument_list|()
argument_list|)
control|)
block|{
name|Collection
argument_list|<
name|Representation
argument_list|>
name|values
init|=
name|data
operator|.
name|get
argument_list|(
name|token
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
condition|)
block|{
name|values
operator|=
operator|new
name|ArrayList
argument_list|<
name|Representation
argument_list|>
argument_list|()
expr_stmt|;
name|data
operator|.
name|put
argument_list|(
name|label
operator|.
name|getText
argument_list|()
argument_list|,
name|values
argument_list|)
expr_stmt|;
block|}
name|values
operator|.
name|add
argument_list|(
name|rep
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|get
parameter_list|(
name|String
name|id
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|includeFields
parameter_list|)
throws|throws
name|IllegalStateException
block|{
return|return
name|entities
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|lookup
parameter_list|(
name|String
name|field
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|includeFields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|search
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
throws|throws
name|IllegalStateException
block|{
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
name|nameField
argument_list|)
condition|)
block|{
comment|//we do not need sorting
comment|//Representation needs to implement equals, therefore results filters multiple matches
name|Set
argument_list|<
name|Representation
argument_list|>
name|results
init|=
operator|new
name|HashSet
argument_list|<
name|Representation
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|term
range|:
name|search
control|)
block|{
comment|//TODO: adding 'zzz' to the parsed term is no good solution for
comment|//      searching ...
for|for
control|(
name|Collection
argument_list|<
name|Representation
argument_list|>
name|termResults
range|:
name|data
operator|.
name|subMap
argument_list|(
name|term
argument_list|,
name|term
operator|+
literal|"zzz"
argument_list|)
operator|.
name|values
argument_list|()
control|)
block|{
name|results
operator|.
name|addAll
argument_list|(
name|termResults
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|results
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Lookup is only supported for the nameField '"
operator|+
name|nameField
operator|+
literal|"' parsed to the constructor"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|supportsOfflineMode
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

