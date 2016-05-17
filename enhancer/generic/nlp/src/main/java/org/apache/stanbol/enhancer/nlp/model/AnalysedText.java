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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ConcurrentModificationException
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
name|Blob
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
name|ContentItem
import|;
end_import

begin_comment
comment|/**  * Provides access to NLP processing results of the<code>text/plain</code>  * {@link Blob} of an ContentItem. Intended to be  * {@link ContentItem#addPart(org.apache.clerezza.commons.rdf.IRI, Object) added  * as ContentPart} by using {@link #ANALYSED_TEXT_URI}.  * @see ContentItem#addPart(IRI, Object)  */
end_comment

begin_interface
specifier|public
interface|interface
name|AnalysedText
extends|extends
name|Section
block|{
comment|/**      * The {@link IRI} used to register the {@link AnalysedText} instance      * as {@link ContentItem#addPart(org.apache.clerezza.commons.rdf.IRI, Object)       * ContentPart} to the {@link ContentItem}      */
specifier|public
specifier|static
specifier|final
name|IRI
name|ANALYSED_TEXT_URI
init|=
operator|new
name|IRI
argument_list|(
literal|"urn:stanbol.enhancer:nlp.analysedText"
argument_list|)
decl_stmt|;
comment|/**      * Returns {@link SpanTypeEnum#Text}      * @see Span#getType()      * @see SpanTypeEnum#Text      */
name|SpanTypeEnum
name|getType
parameter_list|()
function_decl|;
comment|/**      * Adds an Sentence      * @param start the start index      * @param end the end index      * @return the Sentence      */
name|Sentence
name|addSentence
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
function_decl|;
comment|/**      * Adds an Chunk      * @param start the start of the chunk      * @param end      * @return      */
name|Chunk
name|addChunk
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
function_decl|;
comment|/**      * All sentences of the Analysed texts.<p>      * Returned Iterators MUST NOT throw {@link ConcurrentModificationException}      * but consider additions of Spans.      * @return      */
name|Iterator
argument_list|<
name|Sentence
argument_list|>
name|getSentences
parameter_list|()
function_decl|;
comment|/**      * All Chunks of this analysed text.<p>      * Returned Iterators MUST NOT throw {@link ConcurrentModificationException}      * but consider additions of Spans.      * @return the chunks      */
name|Iterator
argument_list|<
name|Chunk
argument_list|>
name|getChunks
parameter_list|()
function_decl|;
comment|/**      * Getter for the text.      * @return       */
name|CharSequence
name|getText
parameter_list|()
function_decl|;
comment|/**      * The analysed {@link Blob}. Typically {@link Blob#getMimeType()} will be      *<code>text/plain</code>.      * @return the analysed {@link Blob} instance.      */
name|Blob
name|getBlob
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

