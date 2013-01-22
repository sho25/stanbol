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
package|;
end_package

begin_comment
comment|/**  * Defines NLP processing Roles engines can take. The idea is to use those roles  * to ease the configuration or NLP enhancement chains. Basically users would  * just configure what NLP features the want to use and the NLP chain would  * choose the fitting Engines based on their "service.ranking" values.  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|NlpProcessingRole
block|{
name|LanguageDetection
block|,
name|SentenceDetection
block|,
name|Tokenizing
block|,
name|PartOfSpeachTagging
block|,
name|Chunking
block|,
name|SentimentTagging
block|,
name|Lemmatize
block|}
end_enum

end_unit

