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
name|servicesapi
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * Stanbol Enhancer components might implement this interface to parse additional  * properties to other components.  *  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|ServiceProperties
block|{
comment|/**      * Getter for the properties defined by this service.      * @return An unmodifiable map of properties defined by this service      */
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getServiceProperties
parameter_list|()
function_decl|;
comment|//TODO review the definition of constants
comment|/**      * Property Key used to define the order in which {@link EnhancementEngine}s are      * called by the {@link EnhancementJobManager}. This property expects a      * single {@link Integer} as value      */
name|String
name|ENHANCEMENT_ENGINE_ORDERING
init|=
literal|"org.apache.stanbol.enhancer.engine.order"
decl_stmt|;
comment|/**      * Ordering values>= this value indicate, that an enhancement engine      * dose some pre processing on the content      */
name|Integer
name|ORDERING_PRE_PROCESSING
init|=
literal|200
decl_stmt|;
comment|/**      * Ordering values< {@link ServiceProperties#ORDERING_PRE_PROCESSING} and      *>= this value indicate, that an enhancement engine performs operations      * that are only dependent on the parsed content.<p>      *<b>NOTE:</b> the NLP processing specific orderings that are defined      * within this span      * @see #ORDERING_NLP_LANGAUGE_DETECTION      * @see #ORDERING_NLP_SENTENCE_DETECTION      * @see #ORDERING_NLP_TOKENIZING      * @see #ORDERING_NLP_POS      * @see #ORDERING_NLP_CHUNK      * @See #ORDERING_NLP_LEMMATIZE      */
name|Integer
name|ORDERING_CONTENT_EXTRACTION
init|=
literal|100
decl_stmt|;
comment|/**      * Ordering values< {@link ServiceProperties#ORDERING_CONTENT_EXTRACTION}      * and>= this value indicate, that an enhancement engine performs operations      * on extracted features of the content. It can also extract additional      * enhancement by using the content, but such features might not be      * available to other engines using this ordering range      */
name|Integer
name|ORDERING_EXTRACTION_ENHANCEMENT
init|=
literal|1
decl_stmt|;
comment|/**      * The default ordering uses {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT}      * -1 . So by default EnhancementEngines are called after all engines that      * use an value within the ordering range defined by      * {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT}      */
name|Integer
name|ORDERING_DEFAULT
init|=
literal|0
decl_stmt|;
comment|/**      * Ordering values< {@link ServiceProperties#ORDERING_DEFAULT} and>= this      * value indicate that an enhancement engine performs post processing      * operations on existing enhancements.      */
name|Integer
name|ORDERING_POST_PROCESSING
init|=
operator|-
literal|100
decl_stmt|;
comment|/* -------      * NLP processing orderings (all within the ORDERING_CONTENT_EXTRACTION range      * -------      */
comment|/**      * Ordering values< {@link #ORDERING_PRE_PROCESSING} and>=      * {@link #ORDERING_NLP_LANGAUGE_DETECTION} are reserved for engines that detect      * the language of an content      */
name|Integer
name|ORDERING_NLP_LANGAUGE_DETECTION
init|=
name|ServiceProperties
operator|.
name|ORDERING_CONTENT_EXTRACTION
operator|+
literal|90
decl_stmt|;
comment|/**      * Ordering values< {@link #ORDERING_NLP_LANGAUGE_DETECTION} and>=      * {@link #ORDERING_NLP_SENTENCE_DETECTION} are reserved for engines that extract      * sections within the text content      */
name|Integer
name|ORDERING_NLP_SENTENCE_DETECTION
init|=
name|ServiceProperties
operator|.
name|ORDERING_CONTENT_EXTRACTION
operator|+
literal|80
decl_stmt|;
comment|/**      * Ordering values< {@link #ORDERING_NLP_SENTENCE_DETECTION} and>=      * {@link #ORDERING_NLP_TOKENIZING} are reserved for engines that tokenize      * the text      */
name|Integer
name|ORDERING_NLP_TOKENIZING
init|=
name|ServiceProperties
operator|.
name|ORDERING_CONTENT_EXTRACTION
operator|+
literal|70
decl_stmt|;
comment|/**      * Ordering values< {@link #ORDERING_NLP_TOKENIZING} and>=      * {@link #ORDERING_NLP_POS} are reserved for engines that perform      * POS (Part of Speech) tagging      */
name|Integer
name|ORDERING_NLP_POS
init|=
name|ServiceProperties
operator|.
name|ORDERING_CONTENT_EXTRACTION
operator|+
literal|60
decl_stmt|;
comment|/**      * Ordering values< {@link #ORDERING_NLP_POS} and>=      * {@link #ORDERING_NLP_CHUNK} are reserved for engines that annotate      * Chunks (such as Noun Phrases) in an text.      */
name|Integer
name|ORDERING_NLP_CHUNK
init|=
name|ServiceProperties
operator|.
name|ORDERING_CONTENT_EXTRACTION
operator|+
literal|50
decl_stmt|;
comment|/**      * Ordering values< {@link #ORDERING_NLP_CHUNK} and>=      * {@link #ORDERING_NLP_LEMMATIZE} are reserved for engines that lemmatize      * texts.<p>      */
name|Integer
name|ORDERING_NLP_LEMMATIZE
init|=
name|ServiceProperties
operator|.
name|ORDERING_CONTENT_EXTRACTION
operator|+
literal|40
decl_stmt|;
comment|/**      * Ordering values< {@link #ORDERING_NLP_LEMMATIZE} and>=      * {@link #ORDERING_NLP_NER} are reserved for engines that do perform       * Named Entity Recognition (NER)<p>      */
name|Integer
name|ORDERING_NLP_NER
init|=
name|ServiceProperties
operator|.
name|ORDERING_CONTENT_EXTRACTION
operator|+
literal|30
decl_stmt|;
block|}
end_interface

end_unit

