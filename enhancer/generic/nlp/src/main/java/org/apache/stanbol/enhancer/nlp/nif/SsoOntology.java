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
name|nlp
operator|.
name|nif
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_enum
specifier|public
enum|enum
name|SsoOntology
block|{
comment|/**      * A string that can be considered a phrase consists of at least 2 strings that are words.       */
name|Phrase
block|,
comment|/**      * A string that can be considered a sentence. Sentences with only one word are typed as Word and Sentence and have no disjointness.      */
name|Sentence
block|,
comment|/**      * A string that can be considered a Stop Word, i.e. it does not contain usefull information for search and other tasks.      */
name|StopWord
block|,
comment|/**      * A string that can be considered a word or a punctuation mark, the sentence 'He enters the room.' for example has 5 words. In general, the division into :Words is done by an NLP Tokenizer. Instances of this class should be a string, that is a 'meaningful' unit of characters. The class has not been named 'Token' as the NLP definition of 'Token' is more similar to our definition of :String .       */
name|Word
block|,
name|child
block|,
name|firstWord
block|,
name|lastWord
block|,
comment|/**      * This property (and the others) can be used to traverse :Sentences, it can not be assumed that no gaps between Sentences exist, i.e. string adjacency is not mandatory.       */
name|nextSentence
block|,
name|nextSentenceTrans
block|,
comment|/**      * This property (and the others) can be used to traverse words, it can not be assumed that no gaps between words exist, i.e. string adjacency is not mandatory.       */
name|nextWord
block|,
name|nextWordTrans
block|,
comment|/**      * The link to the OLiA Annotation model.      */
name|oliaLink
block|,
name|parent
block|,
name|previousSentence
block|,
name|previousSentenceTrans
block|,
name|previousWord
block|,
name|previousWordTrans
block|,
name|sentence
block|,
comment|/**      * The lemma of a Word.       */
name|lemma
block|,
comment|/**      * The pos tag of a Word.      */
name|posTag
block|,
comment|/**      * The stem of a Word.      */
name|stem
block|;
specifier|public
specifier|final
specifier|static
name|String
name|NAMESPACE
init|=
literal|"http://nlp2rdf.lod2.eu/schema/sso/"
decl_stmt|;
name|IRI
name|uri
decl_stmt|;
specifier|private
name|SsoOntology
parameter_list|()
block|{
name|uri
operator|=
operator|new
name|IRI
argument_list|(
name|NAMESPACE
operator|+
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getLocalName
parameter_list|()
block|{
return|return
name|name
argument_list|()
return|;
block|}
specifier|public
name|IRI
name|getUri
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|uri
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
block|}
end_enum

end_unit

