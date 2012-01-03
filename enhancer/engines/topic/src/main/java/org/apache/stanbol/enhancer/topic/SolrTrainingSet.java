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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ConfigurationPolicy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|InvalidSyntaxException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Implementation of the {@code TrainingSet} interface that uses a Solr Core as backend to store and retrieve  * the text examples used to train a classifier.  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|metatype
operator|=
literal|true
argument_list|,
name|immediate
operator|=
literal|true
argument_list|,
name|configurationFactory
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|REQUIRE
argument_list|)
annotation|@
name|Service
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SolrTrainingSet
operator|.
name|TRAINING_SET_ID
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SolrTrainingSet
operator|.
name|SOLR_CORE
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SolrTrainingSet
operator|.
name|EXAMPLE_ID_FIELD
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SolrTrainingSet
operator|.
name|EXAMPLE_TEXT_FIELD
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SolrTrainingSet
operator|.
name|TOPICS_URI_FIELD
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|SolrTrainingSet
operator|.
name|MODIFICATION_DATE_FIELD
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|SolrTrainingSet
extends|extends
name|ConfiguredSolrCoreTracker
implements|implements
name|TrainingSet
block|{
specifier|public
specifier|static
specifier|final
name|String
name|TRAINING_SET_ID
init|=
literal|"org.apache.stanbol.enhancer.topic.trainingset.id"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_CORE
init|=
literal|"org.apache.stanbol.enhancer.engine.topic.solrCore"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TOPICS_URI_FIELD
init|=
literal|"org.apache.stanbol.enhancer.engine.topic.topicUriField"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EXAMPLE_ID_FIELD
init|=
literal|"org.apache.stanbol.enhancer.engine.topic.exampleIdField"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EXAMPLE_TEXT_FIELD
init|=
literal|"org.apache.stanbol.enhancer.engine.topic.exampleTextField"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|MODIFICATION_DATE_FIELD
init|=
literal|"org.apache.stanbol.enhancer.engine.topic.modificiationDateField"
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SolrTrainingSet
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
name|String
name|trainingSetId
decl_stmt|;
specifier|protected
name|String
name|topicUriField
decl_stmt|;
specifier|protected
name|String
name|modificationDateField
decl_stmt|;
comment|// TODO: make me configurable using an OSGi property
specifier|protected
name|int
name|batchSize
init|=
literal|100
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|ConfigurationException
throws|,
name|InvalidSyntaxException
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
name|context
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
name|configure
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|public
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
if|if
condition|(
name|indexTracker
operator|!=
literal|null
condition|)
block|{
name|indexTracker
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|configure
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|trainingSetId
operator|=
name|getRequiredStringParam
argument_list|(
name|config
argument_list|,
name|TRAINING_SET_ID
argument_list|)
expr_stmt|;
name|topicUriField
operator|=
name|getRequiredStringParam
argument_list|(
name|config
argument_list|,
name|TOPICS_URI_FIELD
argument_list|)
expr_stmt|;
name|modificationDateField
operator|=
name|getRequiredStringParam
argument_list|(
name|config
argument_list|,
name|MODIFICATION_DATE_FIELD
argument_list|)
expr_stmt|;
name|configureSolrCore
argument_list|(
name|config
argument_list|,
name|SOLR_CORE
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|ConfiguredSolrCoreTracker
name|fromParameters
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|ConfiguredSolrCoreTracker
name|engine
init|=
operator|new
name|SolrTrainingSet
argument_list|()
decl_stmt|;
name|engine
operator|.
name|configure
argument_list|(
name|config
argument_list|)
expr_stmt|;
return|return
name|engine
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isUpdatable
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
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
block|{
comment|// TODO
return|return
name|exampleId
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getUpdatedTopics
parameter_list|(
name|Calendar
name|lastModificationDate
parameter_list|)
throws|throws
name|TrainingSetException
block|{
comment|// TODO
return|return
name|Collections
operator|.
name|emptySet
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Batch
argument_list|<
name|String
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
block|{
comment|// TODO
return|return
operator|new
name|Batch
argument_list|<
name|String
argument_list|>
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Batch
argument_list|<
name|String
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
block|{
comment|// TODO
return|return
operator|new
name|Batch
argument_list|<
name|String
argument_list|>
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setBatchSize
parameter_list|(
name|int
name|batchSize
parameter_list|)
block|{
name|this
operator|.
name|batchSize
operator|=
name|batchSize
expr_stmt|;
block|}
block|}
end_class

end_unit

