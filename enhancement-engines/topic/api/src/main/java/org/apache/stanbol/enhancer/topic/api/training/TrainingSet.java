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
name|enhancer
operator|.
name|topic
operator|.
name|api
operator|.
name|training
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
name|topic
operator|.
name|api
operator|.
name|Batch
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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

begin_comment
comment|/**  * Source of categorized text documents that can be used to build a the statistical model of a  * TopicClassifier.  */
end_comment

begin_interface
specifier|public
interface|interface
name|TrainingSet
block|{
comment|/**      * The short name of the training set. Can be used as the URI component to identify the training set in      * the Web management interface or in RDF descriptions of the service.      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * @return true if the training set can be updated using the {@code registerExample} API. If false that      *         means that the component is a view on a remote datasource that has its own API for updates      *         (e.g. the document repository of a CMS).      */
name|boolean
name|isUpdatable
parameter_list|()
throws|throws
name|TrainingSetException
function_decl|;
comment|/**      * Register some text content to be used as an example of document that should be positively classified as      * topics by the model.      *       * @param id      *            Unique identifier of the example to create or override. If null, a new example with a      *            generated id will be created.      * @param text      *            Text content of the example. If null the example with the matching id will be deleted.      *       * @param topics      *            The list of all the topics the example should be classified as.      * @return the id of the registered example (can be automatically generated)      */
name|String
name|registerExample
parameter_list|(
name|String
name|exampleId
parameter_list|,
name|String
name|text
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|topics
parameter_list|)
throws|throws
name|TrainingSetException
function_decl|;
comment|/**      * Fetch examples representative of the set of topics passed as argument so as to be able to build a      * statistical model.      *       * @param topics      *            list of admissible topics to search examples for: each example in the batch will be      *            classified in at list one of the requested topics. This list would typically comprise a      *            topic along with it's direct narrower descendants (and maybe level 2 descendants too).      * @param offset      *            marker value to fetch the next batch. Pass null to fetch the first batch.      * @return a batch of example suitable for training a classifier model for the requested topics.      */
name|Batch
argument_list|<
name|Example
argument_list|>
name|getPositiveExamples
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|topics
parameter_list|,
name|Object
name|offset
parameter_list|)
throws|throws
name|TrainingSetException
function_decl|;
comment|/**      * Fetch examples representative of any document not specifically classified in one of the passed topics.      * This can be useful to train a statistical model for a classifier of those topics to negatively weight      * generic features (term occurrences) and limit the number of false positives in the classification. It      * is up to the classifier model to decide to use such negative examples or not at training time.      *       * @param topics      *            list of non-admissible topics to search example for: each example in the batch must no be      *            classified in any of the passed topics.      * @param offset      *            marker value to fetch the next batch. Pass null to fetch the first batch.      * @return a batch of examples suitable for training (negative-refinement) a classifier model for the      *         requested topics.      */
name|Batch
argument_list|<
name|Example
argument_list|>
name|getNegativeExamples
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|topics
parameter_list|,
name|Object
name|offset
parameter_list|)
throws|throws
name|TrainingSetException
function_decl|;
comment|/**      * Number of examples to fetch at once.      */
name|void
name|setBatchSize
parameter_list|(
name|int
name|batchSize
parameter_list|)
function_decl|;
comment|/**      * Method to tell the classifier if topic model should be updated if there exists examples classified in      * one of those topics that has changed.      *       * @param topics      *            topics to check      * @param referenceDate      *            look for changes after that date      * @return true if one of the passed topics has changed since the last date      */
name|boolean
name|hasChangedSince
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|topics
parameter_list|,
name|Date
name|referenceDate
parameter_list|)
throws|throws
name|TrainingSetException
function_decl|;
comment|/**      * Trigger optimization of the underlying index.       */
name|void
name|optimize
parameter_list|()
throws|throws
name|TrainingSetException
function_decl|;
block|}
end_interface

end_unit

