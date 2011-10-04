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
name|contenthub
operator|.
name|search
operator|.
name|engines
operator|.
name|solr
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|HashMap
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
name|Reference
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
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrQuery
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
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|SolrServerException
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
name|response
operator|.
name|QueryResponse
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
name|common
operator|.
name|SolrDocument
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
name|common
operator|.
name|SolrDocumentList
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
name|SolrDirectoryManager
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
name|SolrServerProviderManager
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
name|SolrServerTypeEnum
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
name|contenthub
operator|.
name|core
operator|.
name|search
operator|.
name|execution
operator|.
name|SearchContextImpl
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|engine
operator|.
name|EngineProperties
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|engine
operator|.
name|SearchEngine
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|engine
operator|.
name|SearchEngineException
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
operator|.
name|ClassResource
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
operator|.
name|IndividualResource
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
operator|.
name|Keyword
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
operator|.
name|QueryKeyword
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
operator|.
name|SearchContext
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
operator|.
name|SearchContextFactory
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|vocabulary
operator|.
name|SearchVocabulary
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|store
operator|.
name|vocabulary
operator|.
name|SolrVocabulary
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

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|RDFNode
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|ResIterator
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Statement
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|impl
operator|.
name|Util
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|util
operator|.
name|iterator
operator|.
name|Filter
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|vocabulary
operator|.
name|RDF
import|;
end_import

begin_comment
comment|/**  *   * @author anil.pacaci  *   */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
specifier|public
class|class
name|SolrSearchEngine
implements|implements
name|SearchEngine
implements|,
name|EngineProperties
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SolrSearchEngine
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|properties
decl_stmt|;
static|static
block|{
name|properties
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
name|properties
operator|.
name|put
argument_list|(
name|PROCESSING_ORDER
argument_list|,
name|PROCESSING_POST
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|final
specifier|static
name|String
name|SERVER_NAME
init|=
literal|"contenthub"
decl_stmt|;
comment|/**      * for EmbeddedSolr instance, it is tried to obtained EmbbeddedSolr at activator if it can get the      * instance, server becomes null and this SolrSearchEngine does not work      */
specifier|private
name|SolrServer
name|server
decl_stmt|;
annotation|@
name|Reference
name|SolrServerProviderManager
name|solrServerProviderManager
decl_stmt|;
annotation|@
name|Reference
name|SolrDirectoryManager
name|solrDirectoryManager
decl_stmt|;
annotation|@
name|Activate
comment|/**      * Tries to connect EmbeddedSolr at startup, if can not, server becomes null and no query is executed on server      * @param cc      */
specifier|public
name|void
name|activate
parameter_list|(
name|ComponentContext
name|cc
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|solrDirectoryManager
operator|!=
literal|null
condition|)
block|{
name|File
name|indexDirectory
init|=
name|solrDirectoryManager
operator|.
name|getSolrIndexDirectory
argument_list|(
name|SERVER_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|indexDirectory
operator|==
literal|null
condition|)
block|{
name|indexDirectory
operator|=
name|solrDirectoryManager
operator|.
name|createSolrDirectory
argument_list|(
name|SERVER_NAME
argument_list|,
name|SERVER_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|String
name|serverLocation
init|=
name|indexDirectory
operator|.
name|toString
argument_list|()
decl_stmt|;
name|server
operator|=
name|solrServerProviderManager
operator|.
name|getSolrServer
argument_list|(
name|SolrServerTypeEnum
operator|.
name|EMBEDDED
argument_list|,
name|serverLocation
argument_list|)
expr_stmt|;
block|}
name|logger
operator|.
name|warn
argument_list|(
literal|"Could not get the EmbeddedSolr Instance since there is no SolrDirectoryManager"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Could not get the EmbeddedSolr Instance at location : {}"
argument_list|,
name|SERVER_NAME
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|server
operator|=
literal|null
expr_stmt|;
block|}
block|}
annotation|@
name|Override
comment|/**      * gets the keywords from search context and then queries solr with these keywords and facet constraints,      * when it found any result, adds to searchContext as documentResouce.      * After searching for all keywords, omits the results founded by other engines and having non matching field constraints       */
specifier|public
name|void
name|search
parameter_list|(
name|SearchContext
name|searchContext
parameter_list|)
throws|throws
name|SearchEngineException
block|{
if|if
condition|(
name|server
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"No EmbeddedSolr, so SolrSearchEngine does not work"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|QueryKeyword
name|qk
range|:
name|searchContext
operator|.
name|getQueryKeyWords
argument_list|()
control|)
block|{
name|searchForKeyword
argument_list|(
name|qk
argument_list|,
name|searchContext
argument_list|)
expr_stmt|;
for|for
control|(
name|Keyword
name|kw
range|:
name|qk
operator|.
name|getRelatedKeywords
argument_list|()
control|)
block|{
name|searchForKeyword
argument_list|(
name|kw
argument_list|,
name|searchContext
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|searchContext
operator|.
name|getConstraints
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|searchContext
operator|.
name|getConstraints
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|omitNonMatchingResult
argument_list|(
name|searchContext
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * @param kw      *            the keyword to use in query      * @param searchContext      */
specifier|private
name|void
name|searchForKeyword
parameter_list|(
name|Keyword
name|kw
parameter_list|,
name|SearchContext
name|searchContext
parameter_list|)
block|{
name|String
name|keyword
init|=
name|kw
operator|.
name|getKeyword
argument_list|()
decl_stmt|;
name|SolrQuery
name|query
init|=
name|SolrSearchEngineHelper
operator|.
name|keywordQueryWithFacets
argument_list|(
name|keyword
argument_list|,
name|searchContext
operator|.
name|getConstraints
argument_list|()
argument_list|)
decl_stmt|;
comment|// Finding document resources by querying keyword with the facets
try|try
block|{
name|QueryResponse
name|solrResult
init|=
name|server
operator|.
name|query
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|processSolrResult
argument_list|(
name|searchContext
argument_list|,
name|kw
argument_list|,
name|solrResult
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SolrServerException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Server could not be queried"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// Finding document resources by querying related Class Resources about
comment|// the keyword with facets
name|List
argument_list|<
name|ClassResource
argument_list|>
name|classResources
init|=
name|kw
operator|.
name|getRelatedClassResources
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|classResources
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|ClassResource
name|classResource
init|=
name|classResources
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|classURI
init|=
name|classResource
operator|.
name|getClassURI
argument_list|()
decl_stmt|;
name|String
name|className
init|=
name|classURI
operator|.
name|substring
argument_list|(
name|Util
operator|.
name|splitNamespace
argument_list|(
name|classURI
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|className
operator|!=
literal|null
condition|)
block|{
name|query
operator|=
name|SolrSearchEngineHelper
operator|.
name|keywordQueryWithFacets
argument_list|(
name|className
argument_list|,
name|searchContext
operator|.
name|getConstraints
argument_list|()
argument_list|)
expr_stmt|;
name|QueryResponse
name|solrResult
init|=
name|server
operator|.
name|query
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|processSolrResult
argument_list|(
name|searchContext
argument_list|,
name|kw
argument_list|,
name|solrResult
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Name of class could not be extracted from from class Resource : "
argument_list|,
name|classResource
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error while querying solr with relatedClass resources"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// Finding document resources by querying related Individual Resources
comment|// about the keyword with facets
name|List
argument_list|<
name|IndividualResource
argument_list|>
name|individualResources
init|=
name|kw
operator|.
name|getRelatedIndividualResources
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|individualResources
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|IndividualResource
name|individualResource
init|=
name|individualResources
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|individualURI
init|=
name|individualResource
operator|.
name|getIndividualURI
argument_list|()
decl_stmt|;
name|String
name|individualName
init|=
name|individualURI
operator|.
name|substring
argument_list|(
name|Util
operator|.
name|splitNamespace
argument_list|(
name|individualURI
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|individualName
operator|!=
literal|null
condition|)
block|{
name|query
operator|=
name|SolrSearchEngineHelper
operator|.
name|keywordQueryWithFacets
argument_list|(
name|individualName
argument_list|,
name|searchContext
operator|.
name|getConstraints
argument_list|()
argument_list|)
expr_stmt|;
name|QueryResponse
name|solrResult
init|=
name|server
operator|.
name|query
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|processSolrResult
argument_list|(
name|searchContext
argument_list|,
name|kw
argument_list|,
name|solrResult
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Name of individual could not be extracted from individual Resource : "
argument_list|,
name|individualResource
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error while querying solr with relatedIndividual resources"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|processSolrResult
parameter_list|(
name|SearchContext
name|searchContext
parameter_list|,
name|Keyword
name|keyword
parameter_list|,
name|QueryResponse
name|solrResult
parameter_list|)
block|{
name|SearchContextFactory
name|factory
init|=
name|searchContext
operator|.
name|getFactory
argument_list|()
decl_stmt|;
name|SolrDocumentList
name|resultDocuments
init|=
name|solrResult
operator|.
name|getResults
argument_list|()
decl_stmt|;
name|Double
name|maxScore
init|=
name|resultDocuments
operator|.
name|getMaxScore
argument_list|()
operator|.
name|doubleValue
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|resultDocuments
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|SolrDocument
name|resultDoc
init|=
name|resultDocuments
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|double
name|score
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|resultDoc
operator|.
name|getFieldValue
argument_list|(
name|SolrSearchEngineHelper
operator|.
name|SCORE_FIELD
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
operator|/
name|maxScore
decl_stmt|;
name|score
operator|=
name|score
operator|>
literal|1.0
condition|?
literal|1.0
else|:
name|score
expr_stmt|;
name|String
name|contenthubId
init|=
operator|(
name|String
operator|)
name|resultDoc
operator|.
name|getFieldValue
argument_list|(
name|SolrVocabulary
operator|.
name|SOLR_FIELD_NAME_ID
argument_list|)
decl_stmt|;
comment|/*              * String cmsId = (String) resultDoc.getFieldValue(SolrSearchEngineHelper.CMSID_FIELD); cmsId =              * cmsId == null ? "" : cmsId;              */
name|String
name|selectionText
init|=
operator|(
name|String
operator|)
name|resultDoc
operator|.
name|getFieldValue
argument_list|(
name|SolrVocabulary
operator|.
name|SOLR_FIELD_NAME_CONTENT
argument_list|)
decl_stmt|;
comment|// score of the keyword is used as a weight for newly found document
name|factory
operator|.
name|createDocumentResource
argument_list|(
name|contenthubId
argument_list|,
literal|1.0
argument_list|,
name|keyword
operator|.
name|getScore
argument_list|()
operator|*
name|score
argument_list|,
name|keyword
argument_list|,
name|selectionText
comment|/* , cmsId */
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|omitNonMatchingResult
parameter_list|(
name|SearchContext
name|searchContext
parameter_list|)
block|{
name|OntModel
name|contextModel
init|=
operator|(
name|SearchContextImpl
operator|)
name|searchContext
decl_stmt|;
name|ResIterator
name|docResources
init|=
name|contextModel
operator|.
name|listResourcesWithProperty
argument_list|(
name|RDF
operator|.
name|type
argument_list|,
name|SearchVocabulary
operator|.
name|DOCUMENT_RESOURCE
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|solrResultIds
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|SolrQuery
name|query
init|=
name|SolrSearchEngineHelper
operator|.
name|keywordQueryWithFacets
argument_list|(
literal|"*:*"
argument_list|,
name|searchContext
operator|.
name|getConstraints
argument_list|()
argument_list|)
decl_stmt|;
name|QueryResponse
name|solrResult
init|=
literal|null
decl_stmt|;
try|try
block|{
name|solrResult
operator|=
name|server
operator|.
name|query
argument_list|(
name|query
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SolrServerException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Error while querying with query : {} "
argument_list|,
name|query
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// means query is done successfully, no problem about server or query
if|if
condition|(
name|solrResult
operator|!=
literal|null
condition|)
block|{
name|SolrDocumentList
name|resultDocuments
init|=
name|solrResult
operator|.
name|getResults
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|resultDocuments
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|SolrDocument
name|resultDocument
init|=
name|resultDocuments
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|docID
init|=
operator|(
name|String
operator|)
name|resultDocument
operator|.
name|getFieldValue
argument_list|(
literal|"id"
argument_list|)
decl_stmt|;
if|if
condition|(
name|docID
operator|!=
literal|null
operator|&&
operator|!
name|docID
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|solrResultIds
operator|.
name|add
argument_list|(
name|docID
argument_list|)
expr_stmt|;
block|}
block|}
block|}
while|while
condition|(
name|docResources
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|docResource
init|=
name|docResources
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|relatedDocument
init|=
name|docResource
operator|.
name|getProperty
argument_list|(
name|SearchVocabulary
operator|.
name|RELATED_DOCUMENT
argument_list|)
operator|.
name|getObject
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|solrResultIds
operator|.
name|contains
argument_list|(
name|relatedDocument
argument_list|)
condition|)
block|{
comment|/*                  * means that documentResource is related with such a document that document does not apply                  * facet constraints so that document resource and its external resources will be omitted from                  * searchContext                  */
name|Filter
argument_list|<
name|Statement
argument_list|>
name|f
init|=
operator|new
name|Filter
argument_list|<
name|Statement
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Statement
name|s
parameter_list|)
block|{
return|return
name|s
operator|.
name|getSubject
argument_list|()
operator|.
name|getPropertyResourceValue
argument_list|(
name|RDF
operator|.
name|type
argument_list|)
operator|.
name|equals
argument_list|(
name|SearchVocabulary
operator|.
name|EXTERNAL_RESOURCE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
name|List
argument_list|<
name|Statement
argument_list|>
name|omitEntity
init|=
name|contextModel
operator|.
name|listStatements
argument_list|(
literal|null
argument_list|,
name|SearchVocabulary
operator|.
name|RELATED_DOCUMENT
argument_list|,
name|docResource
argument_list|)
operator|.
name|filterKeep
argument_list|(
name|f
argument_list|)
operator|.
name|toList
argument_list|()
decl_stmt|;
comment|// now all statements whose subject is document will be omitted
name|List
argument_list|<
name|Statement
argument_list|>
name|omitDocument
init|=
name|contextModel
operator|.
name|listStatements
argument_list|(
name|docResource
argument_list|,
literal|null
argument_list|,
operator|(
name|RDFNode
operator|)
literal|null
argument_list|)
operator|.
name|toList
argument_list|()
decl_stmt|;
try|try
block|{
name|contextModel
operator|.
name|remove
argument_list|(
name|omitEntity
argument_list|)
expr_stmt|;
name|contextModel
operator|.
name|remove
argument_list|(
name|omitDocument
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Resources could not be omitted from search context"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getEngineProperties
parameter_list|()
block|{
return|return
name|properties
return|;
block|}
block|}
end_class

end_unit

