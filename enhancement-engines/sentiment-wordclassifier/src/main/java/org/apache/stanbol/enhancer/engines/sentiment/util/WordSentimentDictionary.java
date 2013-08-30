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
name|sentiment
operator|.
name|util
package|;
end_package

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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|ReadWriteLock
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|ReentrantReadWriteLock
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
name|sentiment
operator|.
name|api
operator|.
name|SentimentClassifier
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
name|nlp
operator|.
name|pos
operator|.
name|LexicalCategory
import|;
end_import

begin_comment
comment|/**  *<code>{Word,Category} -&gt; {Sentiment}</code> Dictionary intended to be  * used by {@link SentimentClassifier} implementation to hold the dictionary.<p>  * This implementation is thread save.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|WordSentimentDictionary
block|{
specifier|private
specifier|final
name|ReadWriteLock
name|lock
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|LexicalCategory
argument_list|,
name|Double
argument_list|>
argument_list|>
name|wordMap
decl_stmt|;
specifier|private
specifier|final
name|Locale
name|locale
decl_stmt|;
specifier|private
name|int
name|sentCount
decl_stmt|;
comment|//the number of wordSentiments
comment|/**      * Create a word sentiment directory for the given locale.      * @param locale the locale used to convert words to lower case. If      *<code>null</code> {@link Locale#ROOT} will be used.      */
specifier|public
name|WordSentimentDictionary
parameter_list|(
name|Locale
name|locale
parameter_list|)
block|{
name|this
operator|.
name|wordMap
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Map
argument_list|<
name|LexicalCategory
argument_list|,
name|Double
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|this
operator|.
name|lock
operator|=
operator|new
name|ReentrantReadWriteLock
argument_list|()
expr_stmt|;
name|this
operator|.
name|locale
operator|=
name|locale
operator|==
literal|null
condition|?
name|Locale
operator|.
name|ROOT
else|:
name|locale
expr_stmt|;
block|}
comment|/**      * Puts (adds/updates) a word (with unknown {@link LexicalCategory})      * to the dictionary      * @param word the word.      * @param sentiment the sentiment value      * @return the old sentiment value or<code>null</code> if none.      */
specifier|public
name|Double
name|updateSentiment
parameter_list|(
name|String
name|word
parameter_list|,
name|Double
name|sentiment
parameter_list|)
block|{
return|return
name|updateSentiment
argument_list|(
literal|null
argument_list|,
name|word
argument_list|,
name|sentiment
argument_list|)
return|;
block|}
comment|/**      * Puts (adds/updates) a word with {@link LexicalCategory} to the dictionary.      * @param cat the {@link LexicalCategory} of the word or<code>null</code> if not known      * @param word the word       * @param sentiment the sentiment value or<code>null</code> to remove this      *     mapping.      * @return the old sentiment value or<code>null</code> if none.      */
specifier|public
name|Double
name|updateSentiment
parameter_list|(
name|LexicalCategory
name|cat
parameter_list|,
name|String
name|word
parameter_list|,
name|Double
name|sentiment
parameter_list|)
block|{
name|word
operator|=
name|word
operator|.
name|toLowerCase
argument_list|(
name|locale
argument_list|)
expr_stmt|;
name|Double
name|old
init|=
literal|null
decl_stmt|;
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|Map
argument_list|<
name|LexicalCategory
argument_list|,
name|Double
argument_list|>
name|entry
init|=
name|wordMap
operator|.
name|get
argument_list|(
name|word
argument_list|)
decl_stmt|;
comment|//most elements (99%) will only have a single value.
comment|//so we use a singleton map as default and create a HashMap for those
comment|//that do have more elements (to save memory)
name|boolean
name|replace
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|entry
operator|==
literal|null
operator|&&
name|sentiment
operator|!=
literal|null
condition|)
block|{
name|entry
operator|=
name|Collections
operator|.
name|singletonMap
argument_list|(
name|cat
argument_list|,
name|sentiment
argument_list|)
expr_stmt|;
name|replace
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|entry
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
comment|//special case
if|if
condition|(
name|sentiment
operator|==
literal|null
condition|)
block|{
name|old
operator|=
name|entry
operator|.
name|get
argument_list|(
name|cat
argument_list|)
expr_stmt|;
if|if
condition|(
name|old
operator|!=
literal|null
condition|)
block|{
comment|//remove
name|entry
operator|=
literal|null
expr_stmt|;
name|replace
operator|=
literal|true
expr_stmt|;
block|}
comment|//not found -> do nothing
block|}
else|else
block|{
comment|//about to add 2nd element
comment|//create a normal HashMap and add the existing value;
name|entry
operator|=
operator|new
name|HashMap
argument_list|<
name|LexicalCategory
argument_list|,
name|Double
argument_list|>
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|replace
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sentiment
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|entry
operator|!=
literal|null
operator|&&
name|entry
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|old
operator|=
name|entry
operator|.
name|remove
argument_list|(
name|cat
argument_list|)
expr_stmt|;
if|if
condition|(
name|old
operator|!=
literal|null
operator|&&
name|entry
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
comment|//only one entry left
comment|//switch back to a singletonMap
name|Entry
argument_list|<
name|LexicalCategory
argument_list|,
name|Double
argument_list|>
name|lastEntry
init|=
name|entry
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|entry
operator|=
name|Collections
operator|.
name|singletonMap
argument_list|(
name|lastEntry
operator|.
name|getKey
argument_list|()
argument_list|,
name|lastEntry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|replace
operator|=
literal|true
expr_stmt|;
block|}
block|}
comment|//else already processed by special case size == 1
block|}
else|else
block|{
name|old
operator|=
name|entry
operator|.
name|put
argument_list|(
name|cat
argument_list|,
name|Double
operator|.
name|valueOf
argument_list|(
name|sentiment
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|//else entry == null and sentiment == null ... nothing to do
if|if
condition|(
name|replace
condition|)
block|{
comment|//we have changed the entry instance and need to put the word
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
name|wordMap
operator|.
name|remove
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|wordMap
operator|.
name|put
argument_list|(
name|word
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|old
operator|==
literal|null
operator|&&
name|sentiment
operator|!=
literal|null
condition|)
block|{
name|sentCount
operator|++
expr_stmt|;
comment|//we added a new sentiment
block|}
elseif|else
if|if
condition|(
name|old
operator|!=
literal|null
operator|&&
name|sentiment
operator|==
literal|null
condition|)
block|{
name|sentCount
operator|--
expr_stmt|;
block|}
comment|//else no change
return|return
name|old
return|;
block|}
comment|/**      * Getter for the sentiment value for the word. If multiple sentiments      * for different {@link LexicalCategory lexical categories} are registered      * for the word this will return the average of those.      * @param word the word      * @return the sentiment or<code>null</code> if not in the dictionary.      */
specifier|public
name|Double
name|getSentiment
parameter_list|(
name|String
name|word
parameter_list|)
block|{
return|return
name|getSentiment
argument_list|(
literal|null
argument_list|,
name|word
argument_list|)
return|;
block|}
comment|/**      * Getter for the sentiment for the parsed word and {@link LexicalCategory}.      * In case the category is<code>null</code> this method might parse an      * average over different sentiments registered for different lexical      * categories.      * @param cat the category      * @param word the word      * @return the sentiment or<code>null</code> if the not in the dictionary.      */
specifier|public
name|Double
name|getSentiment
parameter_list|(
name|LexicalCategory
name|cat
parameter_list|,
name|String
name|word
parameter_list|)
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|Map
argument_list|<
name|LexicalCategory
argument_list|,
name|Double
argument_list|>
name|sentiments
init|=
name|wordMap
operator|.
name|get
argument_list|(
name|word
operator|.
name|toLowerCase
argument_list|(
name|locale
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|sentiments
operator|!=
literal|null
condition|)
block|{
name|Double
name|sentiment
init|=
name|sentiments
operator|.
name|get
argument_list|(
name|cat
argument_list|)
decl_stmt|;
if|if
condition|(
name|sentiment
operator|==
literal|null
operator|&&
name|cat
operator|==
literal|null
operator|&&
operator|!
name|sentiments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|sentiments
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|sentiment
operator|=
name|sentiments
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|double
name|avgSent
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Double
name|sent
range|:
name|sentiments
operator|.
name|values
argument_list|()
control|)
block|{
name|avgSent
operator|=
name|avgSent
operator|+
name|sent
expr_stmt|;
block|}
name|sentiment
operator|=
name|Double
operator|.
name|valueOf
argument_list|(
name|avgSent
operator|/
operator|(
name|double
operator|)
name|sentiments
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sentiment
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**       * The number of words in the dictionary. NOTE that a single word      * might have multiple sentiments for different {@link LexicalCategory}.      * So this value might be lower to {@link #size()}       **/
specifier|public
name|int
name|getWordCount
parameter_list|()
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|wordMap
operator|.
name|size
argument_list|()
return|;
block|}
finally|finally
block|{
name|lock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * The number of word sentiments in the dictionary      * @return      */
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|sentCount
return|;
block|}
comment|/**      * removes all entries of this dictionary.      */
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|wordMap
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

