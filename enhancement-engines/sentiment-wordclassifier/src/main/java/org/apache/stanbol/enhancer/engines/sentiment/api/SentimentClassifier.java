begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (c) 2012 Sebastian Schaffert  *  *  Licensed under the Apache License, Version 2.0 (the "License");  *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
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
name|api
package|;
end_package

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
name|PosTag
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_comment
comment|/**  * A simple classifier that determines for a given word a positive or negative   * value between -1 and 1. Unknown words will return the value 0.  *<p/>  * This Interface need to be implemented by Sentiment frameworks so that they  * can be used with this engine. Implementations need to be   * {@link BundleContext#registerService(String, Object, java.util.Dictionary)  * registered as OSGI service}.  * @see LexicalCategoryClassifier  *   * @author Sebastian Schaffert  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|SentimentClassifier
block|{
comment|/**      * Given the word passed as argument, return a value between -1 and 1 indicating its sentiment value from      * very negative to very positive. Unknown words should return the value 0.      *      * @param cat the lexical category of the word (see       *<a href="https://issues.apache.org/jira/browse/STANBOL-1151">STANBOL-1151</a>)      * @param word the word      * @return      */
specifier|public
name|double
name|classifyWord
parameter_list|(
name|LexicalCategory
name|cat
parameter_list|,
name|String
name|word
parameter_list|)
function_decl|;
comment|/**      * Getter for the LexicalCategories for the parsed {@link PosTag}. Used      * to lookup the lexical categories for the       * {@link #classifyWord(LexicalCategory, String)} lookups.<p>      * Simple implementations might return {@link PosTag#getCategories()}. But      * as some {@link PosTag} instances might only define the literal      * {@link PosTag#getTag()} value this method might also implement its own      * mappings.      * @param posTag the posTag      * @return the categories       */
specifier|public
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|getCategories
parameter_list|(
name|PosTag
name|posTag
parameter_list|)
function_decl|;
comment|/**      * The language of this WordClassifier      * @return the language      */
specifier|public
name|String
name|getLanguage
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

