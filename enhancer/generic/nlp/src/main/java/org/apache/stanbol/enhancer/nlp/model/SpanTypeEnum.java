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

begin_comment
comment|/**  * Enumeration over different types - or roles - spans defined for an  * {@link AnalysedText} may play.  */
end_comment

begin_enum
specifier|public
enum|enum
name|SpanTypeEnum
block|{
comment|/**      * The Text as a whole      */
name|Text
block|,
comment|/**      * An section of the text (chapter, page, paragraph ...). NOTE: this      * does NOT define types of sections.      */
name|TextSection
block|,
comment|/**      * An Sentence      */
name|Sentence
block|,
comment|/**      * A Chunk (e.g. a Noun Phrase) NOTE: this does NOT define types of      * Chunks      */
name|Chunk
block|,
comment|/**      * A Token (e.g. a noun, verb, punctuation) NOTE: this does NOT define      * types of Tokens      */
name|Token
block|; }
end_enum

end_unit

