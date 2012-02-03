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
package|;
end_package

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
name|servicesapi
operator|.
name|EngineException
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
name|topic
operator|.
name|training
operator|.
name|TrainingSet
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
name|topic
operator|.
name|training
operator|.
name|TrainingSetException
import|;
end_import

begin_comment
comment|/**  * Service interface for suggesting hierarchical concepts from a specific scheme (a.k.a. taxonomy, thesaurus  * or concepts hierarchy) from the text content of a document or part of a document.  */
end_comment

begin_interface
specifier|public
interface|interface
name|TopicClassifier
block|{
comment|/**      * @return the short id identifying this classifier / scheme: can be used as URL path component to publish      *         the service.      */
specifier|public
name|String
name|getSchemeId
parameter_list|()
function_decl|;
comment|/**      * @return list of language codes for text that can be automatically classified by the service.      */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getAcceptedLanguages
parameter_list|()
function_decl|;
comment|/**      * Perform automated text categorization based on statistical occurrences of words in the given text.      *       * @param text      *            the text content to analyze      * @return the most likely concepts related to the text      * @throws EngineException      */
name|List
argument_list|<
name|TopicSuggestion
argument_list|>
name|suggestTopics
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|ClassifierException
function_decl|;
comment|/**      * @return the set of ids of concepts directly broader than      * @param id      */
name|Set
argument_list|<
name|String
argument_list|>
name|getNarrowerConcepts
parameter_list|(
name|String
name|broadConceptId
parameter_list|)
throws|throws
name|ClassifierException
function_decl|;
comment|/**      * @return the set of ids of concepts directly narrower than      * @param id      */
name|Set
argument_list|<
name|String
argument_list|>
name|getBroaderConcepts
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|ClassifierException
function_decl|;
comment|/**      * @return the set of ids of concepts without broader concepts.      */
name|Set
argument_list|<
name|String
argument_list|>
name|getRootConcepts
parameter_list|()
throws|throws
name|ClassifierException
function_decl|;
comment|/**      * @return true if the classifier model can be updated with the {@code addConcept} / {@code removeConcept}      *         / {@code updateModel} / methods.      */
name|boolean
name|isUpdatable
parameter_list|()
function_decl|;
comment|/**      * Register a topic and set it's ancestors in the taxonomy. Warning: re-adding an already existing topic      * can delete the underlying statistical model. Calling {@code updateModel} is necessary to rebuild the      * statistical model based on the hierarchical structure of the concepts and the registered training set.      *       * @param conceptUri      *            the new concept identifier      * @param primaryTopicUri      *            optional identifier of a resource best describing that concept.      * @param broaderConcepts      *            list of directly broader concepts in the thesaurus      */
name|void
name|addConcept
parameter_list|(
name|String
name|conceptUri
parameter_list|,
name|String
name|primaryTopicUri
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|broaderConcepts
parameter_list|)
throws|throws
name|ClassifierException
function_decl|;
comment|/**      * Register a topic and set it's ancestors in the taxonomy. Warning: re-adding an already existing topic      * can delete the underlying statistical model. Calling {@code updateModel} is necessary to rebuild the      * statistical model based on the hierarchical structure of the concepts and the registered training set.      *       * @param conceptUri      *            the new concept identifier      * @param broaderConcepts      *            list of directly broader concepts in the thesaurus      */
name|void
name|addConcept
parameter_list|(
name|String
name|conceptUri
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|broaderConcepts
parameter_list|)
throws|throws
name|ClassifierException
function_decl|;
comment|/**      * Remove a topic from the thesaurus. WARNING: it is the caller responsibility to recursively remove or      * update any narrower topic that might hold a reference on this topic. Once the tree is updated,      * {@code updateModel} should be called to re-align the statistical model to match the new hierarchy by      * drawing examples from the dataset.      *       * @param conceptUri      *            if of the topic to remove from the model      */
name|void
name|removeConcept
parameter_list|(
name|String
name|conceptUri
parameter_list|)
throws|throws
name|ClassifierException
function_decl|;
comment|/**      * Register a training set to use to build the statistical model of the classifier.      */
name|void
name|setTrainingSet
parameter_list|(
name|TrainingSet
name|trainingSet
parameter_list|)
function_decl|;
comment|/**      * Update (incrementally or from scratch) the statistical model of the classifier. Note: depending on the      * size of the dataset and the number of concepts to update, this process can take a long time and should      * probably be wrapped in a dedicated thread if called by a the user interface layer.      *       * @return the number of updated concepts      */
name|int
name|updateModel
parameter_list|(
name|boolean
name|incremental
parameter_list|)
throws|throws
name|TrainingSetException
throws|,
name|ClassifierException
function_decl|;
comment|/**      * Perform k-fold cross validation of the model to compute estimates of the precision, recall and f1      * score.      *       * @return number of updated concepts      */
specifier|public
name|int
name|updatePerformanceEstimates
parameter_list|(
name|boolean
name|incremental
parameter_list|)
throws|throws
name|ClassifierException
throws|,
name|TrainingSetException
function_decl|;
comment|/**      * Tell the classifier which slice of data to keep aside while training for model evaluation using k-folds      * cross validation.      *       * http://en.wikipedia.org/wiki/Cross-validation_%28statistics%29#K-fold_cross-validation      *       * @param foldIndex      *            the fold id used as a training set for this classifier instance.      * @param foldCount      *            the number of folds used in the cross validation process (typically 3 or 5). Set to 0 to      *            disable cross validation for this classifier.      */
name|void
name|setCrossValidationInfo
parameter_list|(
name|int
name|foldIndex
parameter_list|,
name|int
name|foldCount
parameter_list|)
function_decl|;
comment|/**      * Get a classification report with various accuracy metrics (precision, recall and f1-score) along with      * the example ids of some mistakes (false positives or false negatives).      */
name|ClassificationReport
name|getPerformanceEstimates
parameter_list|(
name|String
name|topic
parameter_list|)
throws|throws
name|ClassifierException
function_decl|;
block|}
end_interface

end_unit

