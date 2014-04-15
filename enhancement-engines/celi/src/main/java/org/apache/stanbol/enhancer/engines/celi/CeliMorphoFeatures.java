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
name|celi
package|;
end_package

begin_import
import|import static
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
name|rdf
operator|.
name|Properties
operator|.
name|RDF_TYPE
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
name|Vector
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
name|Language
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
name|PlainLiteralImpl
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
name|enhancer
operator|.
name|engines
operator|.
name|celi
operator|.
name|lemmatizer
operator|.
name|impl
operator|.
name|CeliLemmatizerEnhancementEngine
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
name|celi
operator|.
name|lemmatizer
operator|.
name|impl
operator|.
name|Reading
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
name|model
operator|.
name|Token
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
name|morpho
operator|.
name|Case
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
name|morpho
operator|.
name|CaseTag
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
name|morpho
operator|.
name|Definitness
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
name|morpho
operator|.
name|GenderTag
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
name|morpho
operator|.
name|MorphoFeatures
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
name|morpho
operator|.
name|NumberTag
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
name|morpho
operator|.
name|Person
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
name|morpho
operator|.
name|Tense
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
name|morpho
operator|.
name|TenseTag
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
name|morpho
operator|.
name|VerbMoodTag
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

begin_comment
comment|/**  * Represents a morphological interpretation of a {@link Token word}. Words might have different interpretations (typically depending on the POS) so this Tag allows to add information about all possible interpretations to a single word. This is  * needed if no POS information are present or if POS tags are ambiguous or of low confidence.  *<p>  *<b>TODO</b>s:  *<ul>  *<li>I would like to have {@link Case}, {@link Tense}, ... as own Annotations. However AFAIK those are all grouped to a single interpretation of the Token (driven by the POS tag).</li>  *<li>Maybe add a possibility to add unmapped information as<code>Map&lt;String,List&lt;String&gt;&gt;</code>  *</ul>  *   * @author Alessio Bosca  *   */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|CeliMorphoFeatures
extends|extends
name|MorphoFeatures
block|{
specifier|private
specifier|static
name|CeliTagSetRegistry
name|tagRegistry
init|=
name|CeliTagSetRegistry
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_NUMBER
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/olia.owl#hasNumber"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_GENDER
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/olia.owl#hasGender"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_PERSON
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/olia.owl#hasPerson"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_CASE
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/olia.owl#hasCase"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_DEFINITENESS
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/olia.owl#hasDefiniteness"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_MOOD
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/olia.owl#hasMood"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_TENSE
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://purl.org/olia/olia.owl#hasTense"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|CeliMorphoFeatures
name|parseFrom
parameter_list|(
name|Reading
name|reading
parameter_list|,
name|String
name|lang
parameter_list|)
block|{
if|if
condition|(
name|reading
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|CeliMorphoFeatures
name|morphoFeature
init|=
operator|new
name|CeliMorphoFeatures
argument_list|(
name|reading
operator|.
name|getLemma
argument_list|()
argument_list|)
decl_stmt|;
comment|//parse the key,value pairs of the reading using the language as context
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|entry
range|:
name|reading
operator|.
name|getLexicalFeatures
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|feature
init|=
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|value
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
block|{
if|if
condition|(
name|feature
operator|.
name|equals
argument_list|(
literal|"POS"
argument_list|)
condition|)
block|{
name|morphoFeature
operator|.
name|addPos
argument_list|(
name|tagRegistry
operator|.
name|getPosTag
argument_list|(
name|lang
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|feature
operator|.
name|equals
argument_list|(
literal|"CASE"
argument_list|)
condition|)
block|{
name|morphoFeature
operator|.
name|addCase
argument_list|(
name|tagRegistry
operator|.
name|getCaseTag
argument_list|(
name|lang
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|feature
operator|.
name|equals
argument_list|(
literal|"GENDER"
argument_list|)
condition|)
block|{
name|morphoFeature
operator|.
name|addGender
argument_list|(
name|tagRegistry
operator|.
name|getGenderTag
argument_list|(
name|lang
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|feature
operator|.
name|equals
argument_list|(
literal|"NUMBER"
argument_list|)
condition|)
block|{
name|morphoFeature
operator|.
name|addNumber
argument_list|(
name|tagRegistry
operator|.
name|getNumber
argument_list|(
name|lang
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|feature
operator|.
name|equals
argument_list|(
literal|"PERSON"
argument_list|)
condition|)
block|{
name|morphoFeature
operator|.
name|addPerson
argument_list|(
name|tagRegistry
operator|.
name|getPerson
argument_list|(
name|lang
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|feature
operator|.
name|equals
argument_list|(
literal|"VERB_FORM"
argument_list|)
operator|||
name|feature
operator|.
name|equals
argument_list|(
literal|"VFORM"
argument_list|)
condition|)
block|{
name|morphoFeature
operator|.
name|addVerbForm
argument_list|(
name|tagRegistry
operator|.
name|getVerbMoodTag
argument_list|(
name|lang
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|feature
operator|.
name|equals
argument_list|(
literal|"TENSE"
argument_list|)
operator|||
name|feature
operator|.
name|equals
argument_list|(
literal|"VERB_TENSE"
argument_list|)
condition|)
block|{
name|morphoFeature
operator|.
name|addTense
argument_list|(
name|tagRegistry
operator|.
name|getTenseTag
argument_list|(
name|lang
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|morphoFeature
return|;
block|}
comment|/**      * Use {@link #parseFrom(Reading, String)} to instantiate      * @param lemma      */
specifier|private
name|CeliMorphoFeatures
parameter_list|(
name|String
name|lemma
parameter_list|)
block|{
name|super
argument_list|(
name|lemma
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|Triple
argument_list|>
name|featuresAsTriples
parameter_list|(
name|UriRef
name|textAnnotation
parameter_list|,
name|Language
name|lang
parameter_list|)
block|{
name|Collection
argument_list|<
name|TripleImpl
argument_list|>
name|result
init|=
operator|new
name|Vector
argument_list|<
name|TripleImpl
argument_list|>
argument_list|()
decl_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|CeliLemmatizerEnhancementEngine
operator|.
name|hasLemmaForm
argument_list|,
operator|new
name|PlainLiteralImpl
argument_list|(
name|getLemma
argument_list|()
argument_list|,
name|lang
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|PosTag
name|pos
range|:
name|getPosList
argument_list|()
control|)
block|{
if|if
condition|(
name|pos
operator|.
name|isMapped
argument_list|()
condition|)
block|{
for|for
control|(
name|LexicalCategory
name|cat
range|:
name|pos
operator|.
name|getCategories
argument_list|()
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|RDF_TYPE
argument_list|,
name|cat
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|NumberTag
name|num
range|:
name|getNumberList
argument_list|()
control|)
block|{
if|if
condition|(
name|num
operator|.
name|getNumber
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|HAS_NUMBER
argument_list|,
name|num
operator|.
name|getNumber
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Person
name|pers
range|:
name|getPersonList
argument_list|()
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|HAS_PERSON
argument_list|,
name|pers
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|GenderTag
name|gender
range|:
name|getGenderList
argument_list|()
control|)
block|{
if|if
condition|(
name|gender
operator|.
name|getGender
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|HAS_GENDER
argument_list|,
name|gender
operator|.
name|getGender
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Definitness
name|def
range|:
name|getDefinitnessList
argument_list|()
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|HAS_DEFINITENESS
argument_list|,
name|def
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|CaseTag
name|caseFeat
range|:
name|getCaseList
argument_list|()
control|)
block|{
if|if
condition|(
name|caseFeat
operator|.
name|getCase
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|HAS_CASE
argument_list|,
name|caseFeat
operator|.
name|getCase
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|VerbMoodTag
name|vf
range|:
name|getVerbMoodList
argument_list|()
control|)
block|{
if|if
condition|(
name|vf
operator|.
name|getVerbForm
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|HAS_MOOD
argument_list|,
name|vf
operator|.
name|getVerbForm
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|TenseTag
name|tense
range|:
name|getTenseList
argument_list|()
control|)
block|{
if|if
condition|(
name|tense
operator|.
name|getTense
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|textAnnotation
argument_list|,
name|HAS_TENSE
argument_list|,
name|tense
operator|.
name|getTense
argument_list|()
operator|.
name|getUri
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

