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
comment|/**  * A simple classifier that determines for a given word a positive or negative   * value between -1 and 1. Unknown words will return the value 0.  *<p/>  * This Interface need to be implemented by Sentiment frameworks so that they  * can be used with this engine. Implementations need to be   * {@link BundleContext#registerService(String, Object, java.util.Dictionary)  * registered as OSGI service}.  * @author Sebastian Schaffert  */
end_comment

begin_interface
specifier|public
interface|interface
name|SentimentClassifier
block|{
comment|/**      * Given the word passed as argument, return a value between -1 and 1 indicating its sentiment value from      * very negative to very positive. Unknown words should return the value 0.      *      * @param word      * @return      */
specifier|public
name|double
name|classifyWord
parameter_list|(
name|String
name|word
parameter_list|)
function_decl|;
comment|/**      * Helper method. Return true if the given POS tag indicates an adjective in the language implemented by      * this classifier.      *      * @param posTag      * @return      */
specifier|public
name|boolean
name|isAdjective
parameter_list|(
name|PosTag
name|posTag
parameter_list|)
function_decl|;
comment|/**      * Helper method. Return true if the given POS tag indicates a noun in the language implemented by this      * classifier.      *      * @param posTag      * @return      */
specifier|public
name|boolean
name|isNoun
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

