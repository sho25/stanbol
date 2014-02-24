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
name|lucenefstlinking
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
name|Comparator
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
name|rdf
operator|.
name|core
operator|.
name|Literal
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
name|impl
operator|.
name|Suggestion
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
comment|/**  * Represents a Entity that Matches somewhere in the tagged text.  *<p>  * Matches are generated for {@link #id Lucene Document IDs} and  * {@link #uri Solr Document ids} (the URI of the matching entity). On the  * first access to the {@link #getLabels() labels}, {@link #getTypes() types}   * or {@link #getRedirects()} all those information are lazily retrieved by   * accessing the data stored in the index. The {@link FieldLoader} instance  * parsed in the constructor is used to load those information.  * Typically this is implemented by the {@link MatchPool} instance used to  * instantiate Match instances.  *   *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|Match
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|Match
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Lucene document id      */
specifier|public
specifier|final
name|int
name|id
decl_stmt|;
specifier|private
name|FieldLoader
name|fieldLoader
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|FieldType
argument_list|,
name|Object
argument_list|>
name|values
decl_stmt|;
specifier|private
name|boolean
name|error
init|=
literal|false
decl_stmt|;
specifier|private
name|Literal
name|matchLabel
decl_stmt|;
comment|/**      * The score of the Match      */
specifier|private
name|double
name|score
decl_stmt|;
name|Match
parameter_list|(
name|int
name|id
parameter_list|,
name|FieldLoader
name|fieldLoader
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|fieldLoader
operator|=
name|fieldLoader
expr_stmt|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|getValue
argument_list|(
name|FieldType
operator|.
name|id
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|Literal
argument_list|>
name|getLabels
parameter_list|()
block|{
return|return
name|getValues
argument_list|(
name|FieldType
operator|.
name|label
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|UriRef
argument_list|>
name|getTypes
parameter_list|()
block|{
return|return
name|getValues
argument_list|(
name|FieldType
operator|.
name|type
argument_list|)
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|UriRef
argument_list|>
name|getRedirects
parameter_list|()
block|{
return|return
name|getValues
argument_list|(
name|FieldType
operator|.
name|redirect
argument_list|)
return|;
block|}
specifier|public
name|Double
name|getRanking
parameter_list|()
block|{
return|return
name|getValue
argument_list|(
name|FieldType
operator|.
name|ranking
argument_list|)
return|;
block|}
specifier|private
parameter_list|<
name|T
parameter_list|>
name|Collection
argument_list|<
name|T
argument_list|>
name|getValues
parameter_list|(
name|FieldType
name|type
parameter_list|)
block|{
if|if
condition|(
operator|!
name|type
operator|.
name|isMultivalued
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed field Type '"
operator|+
name|type
operator|+
literal|"' is not multi valued!"
argument_list|)
throw|;
block|}
name|Object
name|value
init|=
name|getValue
argument_list|(
name|type
argument_list|)
decl_stmt|;
return|return
name|value
operator|==
literal|null
condition|?
name|Collections
operator|.
name|EMPTY_SET
else|:
operator|(
name|Collection
argument_list|<
name|T
argument_list|>
operator|)
name|value
return|;
block|}
specifier|private
parameter_list|<
name|T
parameter_list|>
name|T
name|getValue
parameter_list|(
name|FieldType
name|type
parameter_list|)
block|{
if|if
condition|(
name|error
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|values
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|values
operator|=
name|fieldLoader
operator|.
name|load
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to load Entity for Lucene DocId '"
operator|+
name|id
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|error
operator|=
literal|true
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Error while loading Entity for Lucene DocId '"
operator|+
name|id
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|error
operator|=
literal|true
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
return|return
operator|(
name|T
operator|)
name|values
operator|.
name|get
argument_list|(
name|type
argument_list|)
return|;
block|}
specifier|public
name|void
name|setMatch
parameter_list|(
name|double
name|score
parameter_list|,
name|Literal
name|matchLabel
parameter_list|)
block|{
name|this
operator|.
name|score
operator|=
name|score
expr_stmt|;
name|this
operator|.
name|matchLabel
operator|=
name|matchLabel
expr_stmt|;
block|}
comment|/**      * Allows to update the {@link #getScore() score} without changing the      * {@link #getMatchLabel() match}.      * @param score the new score      */
specifier|public
name|void
name|updateScore
parameter_list|(
name|double
name|score
parameter_list|)
block|{
name|this
operator|.
name|score
operator|=
name|score
expr_stmt|;
block|}
comment|/**      * The score       * @return the score      */
specifier|public
name|double
name|getScore
parameter_list|()
block|{
return|return
name|score
return|;
block|}
specifier|public
name|Literal
name|getMatchLabel
parameter_list|()
block|{
return|return
name|matchLabel
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|id
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|instanceof
name|Match
operator|&&
name|id
operator|==
operator|(
operator|(
name|Match
operator|)
name|o
operator|)
operator|.
name|id
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|uri
init|=
name|getUri
argument_list|()
decl_stmt|;
return|return
name|uri
operator|!=
literal|null
condition|?
name|uri
else|:
literal|"Match[id: "
operator|+
name|id
operator|+
literal|"|(uri unknown)]"
return|;
block|}
specifier|static
enum|enum
name|FieldType
block|{
name|id
parameter_list|(
name|String
operator|.
name|class
parameter_list|)
operator|,
constructor|label(Literal.class
operator|,
constructor|true
block|)
enum|,
name|type
argument_list|(
name|UriRef
operator|.
name|class
argument_list|,
literal|true
argument_list|)
operator|,
name|redirect
argument_list|(
name|UriRef
operator|.
name|class
argument_list|,
literal|true
argument_list|)
operator|,
name|ranking
argument_list|(
name|Double
operator|.
name|class
argument_list|)
enum|;
name|Class
argument_list|<
name|?
argument_list|>
name|valueType
decl_stmt|;
name|boolean
name|multivalued
decl_stmt|;
name|FieldType
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|FieldType
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|boolean
name|multivalued
parameter_list|)
block|{
name|this
operator|.
name|valueType
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|multivalued
operator|=
name|multivalued
expr_stmt|;
block|}
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
name|getValueType
parameter_list|()
block|{
return|return
name|valueType
return|;
block|}
specifier|public
name|boolean
name|isMultivalued
parameter_list|()
block|{
return|return
name|multivalued
return|;
block|}
block|}
end_class

begin_interface
specifier|static
interface|interface
name|FieldLoader
block|{
name|Map
argument_list|<
name|FieldType
argument_list|,
name|Object
argument_list|>
name|load
parameter_list|(
name|int
name|id
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

begin_comment
comment|/**      * Compares {@link Match} instances based on the {@link Match#getScore()}      */
end_comment

begin_decl_stmt
specifier|public
specifier|static
specifier|final
name|Comparator
argument_list|<
name|Match
argument_list|>
name|SCORE_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|Match
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Match
name|a
parameter_list|,
name|Match
name|b
parameter_list|)
block|{
return|return
name|Double
operator|.
name|compare
argument_list|(
name|b
operator|.
name|score
argument_list|,
name|a
operator|.
name|score
argument_list|)
return|;
comment|//higher first
block|}
block|}
decl_stmt|;
end_decl_stmt

begin_comment
comment|/**      * Compares {@link Match} instances based on the {@link Match#getRanking()}.      *<code>null</code> values are assumed to be the smallest.      */
end_comment

begin_decl_stmt
specifier|public
specifier|static
specifier|final
name|Comparator
argument_list|<
name|Match
argument_list|>
name|ENTITY_RANK_COMPARATOR
init|=
operator|new
name|Comparator
argument_list|<
name|Match
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Match
name|arg0
parameter_list|,
name|Match
name|arg1
parameter_list|)
block|{
name|Double
name|r1
init|=
name|arg0
operator|.
name|getRanking
argument_list|()
decl_stmt|;
name|Double
name|r2
init|=
name|arg1
operator|.
name|getRanking
argument_list|()
decl_stmt|;
return|return
name|r2
operator|==
literal|null
condition|?
name|r1
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|r1
operator|==
literal|null
condition|?
literal|1
else|:
name|r2
operator|.
name|compareTo
argument_list|(
name|r1
argument_list|)
return|;
block|}
block|}
decl_stmt|;
end_decl_stmt

unit|}
end_unit

