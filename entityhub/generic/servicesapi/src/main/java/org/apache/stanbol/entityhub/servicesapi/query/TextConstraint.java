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
name|servicesapi
operator|.
name|query
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
name|Arrays
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
name|LinkedHashSet
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
name|Set
import|;
end_import

begin_class
specifier|public
class|class
name|TextConstraint
extends|extends
name|Constraint
block|{
specifier|public
enum|enum
name|PatternType
block|{
comment|/**          * Simple checks if the parsed constraint equals the value          */
name|none
block|,
comment|/**          * All kind of REGEX Patterns          */
name|regex
block|,
comment|/**          * WildCard based queries using * and ?          */
name|wildcard
comment|//TODO maybe add Prefix as additional type
block|}
specifier|private
specifier|final
name|PatternType
name|wildcardType
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|languages
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|texts
decl_stmt|;
comment|/**      * If enabled the proximity of query terms will be used for ranking the       * results.      */
specifier|private
name|boolean
name|proximityRanking
decl_stmt|;
comment|/**      * Creates a TextConstraint for multiple texts and languages. Parsed texts      * are connected using OR and may appear in any of the parsed languages.      * @param text the texts or<code>null</code> to search for any text in active languages      * @param languages the set of active languages      */
specifier|public
name|TextConstraint
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|text
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
name|this
argument_list|(
name|text
argument_list|,
name|PatternType
operator|.
name|none
argument_list|,
literal|false
argument_list|,
name|languages
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a TextConstraint for a text and languages.      * @param text the text or<code>null</code> to search for any text in active languages      * @param languages the set of active languages.      */
specifier|public
name|TextConstraint
parameter_list|(
name|String
name|text
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
name|this
argument_list|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Collections
operator|.
name|singletonList
argument_list|(
name|text
argument_list|)
argument_list|,
name|PatternType
operator|.
name|none
argument_list|,
literal|false
argument_list|,
name|languages
argument_list|)
expr_stmt|;
block|}
specifier|public
name|TextConstraint
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|text
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
name|this
argument_list|(
name|text
argument_list|,
name|PatternType
operator|.
name|none
argument_list|,
name|caseSensitive
argument_list|,
name|languages
argument_list|)
expr_stmt|;
block|}
specifier|public
name|TextConstraint
parameter_list|(
name|String
name|text
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
name|this
argument_list|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Collections
operator|.
name|singletonList
argument_list|(
name|text
argument_list|)
argument_list|,
name|PatternType
operator|.
name|none
argument_list|,
name|caseSensitive
argument_list|,
name|languages
argument_list|)
expr_stmt|;
block|}
specifier|public
name|TextConstraint
parameter_list|(
name|String
name|text
parameter_list|,
name|PatternType
name|wildcardType
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
name|this
argument_list|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Collections
operator|.
name|singletonList
argument_list|(
name|text
argument_list|)
argument_list|,
name|wildcardType
argument_list|,
name|caseSensitive
argument_list|,
name|languages
argument_list|)
expr_stmt|;
block|}
specifier|public
name|TextConstraint
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|text
parameter_list|,
name|PatternType
name|wildcardType
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
name|super
argument_list|(
name|ConstraintType
operator|.
name|text
argument_list|)
expr_stmt|;
comment|//create a local copy and filter null and empty elements
if|if
condition|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|texts
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|List
argument_list|<
name|String
argument_list|>
name|processedText
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|text
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|constraints
init|=
name|processedText
operator|.
name|iterator
argument_list|()
init|;
name|constraints
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|constraint
init|=
name|constraints
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|constraint
operator|==
literal|null
operator|||
name|constraint
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|constraints
operator|.
name|remove
argument_list|()
expr_stmt|;
comment|//remove null and empty elements
block|}
block|}
if|if
condition|(
name|processedText
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|texts
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|texts
operator|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|processedText
argument_list|)
expr_stmt|;
block|}
block|}
comment|//check that we have at least a text or a language
if|if
condition|(
name|this
operator|.
name|texts
operator|==
literal|null
operator|&&
operator|(
name|languages
operator|==
literal|null
operator|||
name|languages
operator|.
name|length
operator|<
literal|1
operator|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Text Constraint MUST define a non empty text OR a non empty list of language constraints"
argument_list|)
throw|;
block|}
if|if
condition|(
name|wildcardType
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|wildcardType
operator|=
name|PatternType
operator|.
name|none
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|wildcardType
operator|=
name|wildcardType
expr_stmt|;
block|}
if|if
condition|(
name|languages
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|languages
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|/*              * Implementation NOTE:              *   We need to use a LinkedHashSet here to              *    1) ensure that there are no duplicates and              *    2) ensure ordering of the parsed constraints              *   Both is important: Duplicates might result in necessary calculations              *   and ordering might be important for users that expect that the              *   language parsed first is used as the preferred one              */
name|this
operator|.
name|languages
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
operator|new
name|LinkedHashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|languages
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
block|}
comment|/**      * The pattern type to be used for this query.      * @return the wildcardType      */
specifier|public
specifier|final
name|PatternType
name|getPatternType
parameter_list|()
block|{
return|return
name|wildcardType
return|;
block|}
comment|/**      * The set of languages for this query.      * @return the languages      */
specifier|public
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|getLanguages
parameter_list|()
block|{
return|return
name|languages
return|;
block|}
comment|/**      * If the query is case sensitive      * @return the caseSensitive state      */
specifier|public
specifier|final
name|boolean
name|isCaseSensitive
parameter_list|()
block|{
return|return
name|caseSensitive
return|;
block|}
comment|/**      * Getter for the text constraints. Multiple constraints need to be connected      * with OR. For AND simple post all required words in a single String.      * @return the text constraints      */
specifier|public
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|getTexts
parameter_list|()
block|{
return|return
name|texts
return|;
block|}
comment|/**      * Getter for the first text constraint. If multiple constrains are set only      * the first one will be returned.      * @return the fist text constraint (of possible multiple text constraints)      * @deprecated       */
annotation|@
name|Deprecated
specifier|public
specifier|final
name|String
name|getText
parameter_list|()
block|{
return|return
name|texts
operator|==
literal|null
operator|||
name|texts
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|texts
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/**      * Getter for the Term Proximity state. If enabled the proximity of the      * parsed terms should be used to rank search results.      * @return the termProximity or<code>null</code> if not specified      */
specifier|public
name|Boolean
name|isProximityRanking
parameter_list|()
block|{
return|return
name|proximityRanking
return|;
block|}
comment|/**      * Setter for the proximity ranking state. If enabled the proximity of the      * parsed terms should be used to rank search results.      * @param state the proximity ranking state to set      */
specifier|public
name|void
name|setProximityRanking
parameter_list|(
name|boolean
name|state
parameter_list|)
block|{
name|this
operator|.
name|proximityRanking
operator|=
name|state
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"TextConstraint[value=%s|%s|case %sensitive|languages:%s]"
argument_list|,
name|texts
argument_list|,
name|wildcardType
operator|.
name|name
argument_list|()
argument_list|,
name|caseSensitive
condition|?
literal|""
else|:
literal|"in"
argument_list|,
name|languages
argument_list|)
return|;
block|}
block|}
end_class

end_unit

