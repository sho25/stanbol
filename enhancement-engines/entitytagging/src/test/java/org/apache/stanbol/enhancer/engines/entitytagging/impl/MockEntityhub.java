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
name|engines
operator|.
name|entitytagging
operator|.
name|impl
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServer
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
name|commons
operator|.
name|solr
operator|.
name|IndexReference
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
name|commons
operator|.
name|solr
operator|.
name|managed
operator|.
name|standalone
operator|.
name|StandaloneEmbeddedSolrServerProvider
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
name|entityhub
operator|.
name|core
operator|.
name|model
operator|.
name|EntityImpl
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
name|entityhub
operator|.
name|core
operator|.
name|query
operator|.
name|DefaultQueryFactory
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
name|entityhub
operator|.
name|core
operator|.
name|query
operator|.
name|QueryResultListImpl
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|Entityhub
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|EntityhubConfiguration
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|EntityhubException
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|FieldMapper
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Entity
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|ManagedEntityState
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQuery
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQueryFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|QueryResultList
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|YardException
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYard
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|SolrYardConfig
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
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
comment|/**  * Mocks an Entityhub for the {@link NamedEntityTaggingEngine} for Unit Testing<p>  * This loads the dbpedia default data and wraps it as entityhub.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
class|class
name|MockEntityhub
implements|implements
name|Entityhub
block|{
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
name|MockEntityhub
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TEST_SOLR_CORE_CONFIGURATION
init|=
literal|"dbpedia_26k.solrindex.bz2"
decl_stmt|;
specifier|protected
name|SolrYard
name|yard
decl_stmt|;
specifier|protected
name|MockEntityhub
parameter_list|()
block|{
name|SolrYardConfig
name|config
init|=
operator|new
name|SolrYardConfig
argument_list|(
literal|"dbpedia"
argument_list|,
literal|"dbpedia"
argument_list|)
decl_stmt|;
name|config
operator|.
name|setIndexConfigurationName
argument_list|(
name|TEST_SOLR_CORE_CONFIGURATION
argument_list|)
expr_stmt|;
name|config
operator|.
name|setAllowInitialisation
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|IndexReference
name|solrIndexRef
init|=
name|IndexReference
operator|.
name|parse
argument_list|(
name|config
operator|.
name|getSolrServerLocation
argument_list|()
argument_list|)
decl_stmt|;
name|SolrServer
name|server
init|=
name|StandaloneEmbeddedSolrServerProvider
operator|.
name|getInstance
argument_list|()
operator|.
name|getSolrServer
argument_list|(
name|solrIndexRef
argument_list|,
name|config
operator|.
name|getIndexConfigurationName
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"Unable to initialise SolrServer for testing"
argument_list|,
name|server
argument_list|)
expr_stmt|;
try|try
block|{
name|yard
operator|=
operator|new
name|SolrYard
argument_list|(
name|server
argument_list|,
name|config
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|Representation
name|paris
init|=
name|yard
operator|.
name|getRepresentation
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|)
decl_stmt|;
if|if
condition|(
name|paris
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Initialised Yard does not contain the expected resource dbpedia:Paris!"
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to init Yard!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -       * Only the following two Methods are used by the EntityLinkingengine      * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -       */
annotation|@
name|Override
specifier|public
name|FieldQueryFactory
name|getQueryFactory
parameter_list|()
block|{
return|return
name|DefaultQueryFactory
operator|.
name|getInstance
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|Entity
argument_list|>
name|findEntities
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|EntityhubException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Performing Query: {}"
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|results
init|=
name|yard
operator|.
name|findRepresentation
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"  ... {} results"
argument_list|,
name|results
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|Entity
argument_list|>
name|entities
init|=
operator|new
name|ArrayList
argument_list|<
name|Entity
argument_list|>
argument_list|(
name|results
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Representation
name|r
range|:
name|results
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> {}"
argument_list|,
name|r
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|entities
operator|.
name|add
argument_list|(
operator|new
name|EntityImpl
argument_list|(
literal|"dbpedia"
argument_list|,
name|r
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|QueryResultListImpl
argument_list|<
name|Entity
argument_list|>
argument_list|(
name|results
operator|.
name|getQuery
argument_list|()
argument_list|,
name|entities
argument_list|,
name|Entity
operator|.
name|class
argument_list|)
return|;
block|}
comment|// UNUSED METHODS
annotation|@
name|Override
specifier|public
name|Yard
name|getYard
parameter_list|()
block|{
return|return
name|yard
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|lookupLocalEntity
parameter_list|(
name|String
name|reference
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|lookupLocalEntity
parameter_list|(
name|String
name|reference
parameter_list|,
name|boolean
name|create
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|EntityhubException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|getEntity
parameter_list|(
name|String
name|entityId
parameter_list|)
throws|throws
name|IllegalArgumentException
throws|,
name|EntityhubException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|importEntity
parameter_list|(
name|String
name|reference
parameter_list|)
throws|throws
name|IllegalStateException
throws|,
name|IllegalArgumentException
throws|,
name|EntityhubException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|getMappingById
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|EntityhubException
throws|,
name|IllegalArgumentException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|getMappingBySource
parameter_list|(
name|String
name|source
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|FieldMapper
name|getFieldMappings
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Entity
argument_list|>
name|getMappingsByTarget
parameter_list|(
name|String
name|entityId
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findEntityReferences
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|EntityhubException
throws|,
name|IllegalArgumentException
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|store
parameter_list|(
name|Representation
name|representation
parameter_list|)
throws|throws
name|EntityhubException
throws|,
name|IllegalArgumentException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|delete
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|EntityhubException
throws|,
name|IllegalArgumentException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Entity
name|setState
parameter_list|(
name|String
name|id
parameter_list|,
name|ManagedEntityState
name|state
parameter_list|)
throws|throws
name|EntityhubException
throws|,
name|IllegalArgumentException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|EntityhubConfiguration
name|getConfig
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|deleteAll
parameter_list|()
throws|throws
name|EntityhubException
block|{     }
block|}
end_class

end_unit

