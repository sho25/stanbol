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
name|web
operator|.
name|resources
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|CorsHelper
operator|.
name|addCORSOrigin
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|CorsHelper
operator|.
name|enableCORS
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|GET
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|OPTIONS
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Produces
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|QueryParam
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|HttpHeaders
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
operator|.
name|ResponseBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
operator|.
name|Status
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
name|web
operator|.
name|base
operator|.
name|ContextHelper
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
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|BaseStanbolResource
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
name|SearchException
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
name|featured
operator|.
name|SearchResult
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
name|related
operator|.
name|RelatedKeywordSearchManager
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
name|web
operator|.
name|util
operator|.
name|RestUtil
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
name|web
operator|.
name|writers
operator|.
name|SearchResultWriter
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
comment|/**  * This is the web resourceclass which implements the search functionality of Contenthub to look for related  * keywords, given a keyword.  *   * @author anil.sinaci  *   */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/contenthub/search/related"
argument_list|)
specifier|public
class|class
name|RelatedKeywordResource
extends|extends
name|BaseStanbolResource
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RelatedKeywordResource
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|RelatedKeywordSearchManager
name|relatedKeywordSearchManager
decl_stmt|;
specifier|public
name|RelatedKeywordResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|relatedKeywordSearchManager
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|RelatedKeywordSearchManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|OPTIONS
specifier|public
name|Response
name|handleCorsPreflight
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|ResponseBuilder
name|res
init|=
name|Response
operator|.
name|ok
argument_list|()
decl_stmt|;
name|enableCORS
argument_list|(
name|servletContext
argument_list|,
name|res
argument_list|,
name|headers
argument_list|)
expr_stmt|;
return|return
name|res
operator|.
name|build
argument_list|()
return|;
block|}
comment|/**      * HTTP GET method to retrieve related keywords from all resources defined within Contenthub.      *       * @param keyword      *            The keyword whose related keywords will be retrieved.      * @param graphURI      *            URI of the ontology to be used during the step in which related keywords are searched in      *            ontology resources. If this parameter is {@code null}, then no related keywords are returned      *            from ontology resources.      * @param headers      *            HTTP headers      * @return JSON string which is constructed by {@link SearchResultWriter}. {@link SearchResult} returned      *         by {@link RelatedKeywordSearchManager#getRelatedKeywordsFromAllSources(String, String)} only      *         contains related keywords (no resultant documents or facet fields are returned within the      *         {@link SearchResult}).      * @throws SearchException      */
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
specifier|public
specifier|final
name|Response
name|findAllRelatedKeywords
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"keyword"
argument_list|)
name|String
name|keyword
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"graphURI"
argument_list|)
name|String
name|graphURI
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|SearchException
block|{
if|if
condition|(
operator|!
name|RestUtil
operator|.
name|isJSONaccepted
argument_list|(
name|headers
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|keyword
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
if|if
condition|(
name|keyword
operator|==
literal|null
condition|)
block|{
name|String
name|msg
init|=
literal|"RelatedKeywordResource.findAllRelatedKeywords requires \"keyword\" parameter. \"graphURI\" is optional"
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
name|graphURI
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|graphURI
argument_list|)
expr_stmt|;
name|SearchResult
name|searchResult
init|=
name|relatedKeywordSearchManager
operator|.
name|getRelatedKeywordsFromAllSources
argument_list|(
name|keyword
argument_list|,
name|graphURI
argument_list|)
decl_stmt|;
return|return
name|prepareResponse
argument_list|(
name|searchResult
argument_list|,
name|headers
argument_list|)
return|;
block|}
comment|/**      * HTTP GET method to retrieve related keywords from ontology resources. Given the ontology URI, this      * method looks for subsumption/hierarchy relations among the concepts to come up with related keywords.      *       * @param keyword      *            The keyword whose related keywords will be retrieved from ontology resources.      * @param graphURI      *            URI of the ontology in which related keywords will be searched. The ontology should be      *            available in the Contenthub system.      * @param headers      *            HTTP headers      * @return JSON string which is constructed by {@link SearchResultWriter}. {@link SearchResult} returned      *         by {@link RelatedKeywordSearchManager#getRelatedKeywordsFromOntology(String, String)} contains      *         only related keywords from ontology resources. (No resultant documents or facet fields are      *         returned within the {@link SearchResult}).      * @throws SearchException      */
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
annotation|@
name|Path
argument_list|(
literal|"/ontology"
argument_list|)
specifier|public
specifier|final
name|Response
name|findOntologyRelatedKeywords
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"keyword"
argument_list|)
name|String
name|keyword
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"graphURI"
argument_list|)
name|String
name|graphURI
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|SearchException
block|{
if|if
condition|(
operator|!
name|RestUtil
operator|.
name|isJSONaccepted
argument_list|(
name|headers
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|keyword
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
if|if
condition|(
name|keyword
operator|==
literal|null
condition|)
block|{
name|String
name|msg
init|=
literal|"RelatedKeywordResource.findOntologyRelatedKeywords requires \"keyword\" and \"graphURI\" parameters."
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
name|graphURI
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|graphURI
argument_list|)
expr_stmt|;
if|if
condition|(
name|graphURI
operator|==
literal|null
condition|)
block|{
name|String
name|msg
init|=
literal|"RelatedKeywordResource.findOntologyRelatedKeywords requires \"keyword\" and \"graphURI\" parameters."
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
name|SearchResult
name|searchResult
init|=
name|relatedKeywordSearchManager
operator|.
name|getRelatedKeywordsFromOntology
argument_list|(
name|keyword
argument_list|,
name|graphURI
argument_list|)
decl_stmt|;
return|return
name|prepareResponse
argument_list|(
name|searchResult
argument_list|,
name|headers
argument_list|)
return|;
block|}
comment|/**      *       * HTTP GET method to retrieve related keywords from the referenced sites.      *       * @param keyword      *            The keyword whose related keywords will be retrieved from referenced sites.      * @param headers      *            HTTP headers      * @return JSON string which is constructed by {@link SearchResultWriter}. {@link SearchResult} returned      *         by {@link RelatedKeywordSearchManager#getRelatedKeywordsFromReferencedSites(String)} contains      *         only related keywords from referenced sites. (No resultant documents or facet fields are      *         returned within the {@link SearchResult}).      * @throws SearchException      */
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
annotation|@
name|Path
argument_list|(
literal|"/referencedsite"
argument_list|)
specifier|public
specifier|final
name|Response
name|findReferencedSiteRelatedKeywords
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"keyword"
argument_list|)
name|String
name|keyword
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|SearchException
block|{
if|if
condition|(
operator|!
name|RestUtil
operator|.
name|isJSONaccepted
argument_list|(
name|headers
argument_list|)
condition|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|keyword
operator|=
name|RestUtil
operator|.
name|nullify
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
if|if
condition|(
name|keyword
operator|==
literal|null
condition|)
block|{
name|String
name|msg
init|=
literal|"RelatedKeywordResource.findOntologyRelatedKeywords requires a \"keyword\" parameter."
decl_stmt|;
name|log
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|msg
argument_list|)
throw|;
block|}
name|SearchResult
name|searchResult
init|=
name|relatedKeywordSearchManager
operator|.
name|getRelatedKeywordsFromReferencedSites
argument_list|(
name|keyword
argument_list|)
decl_stmt|;
return|return
name|prepareResponse
argument_list|(
name|searchResult
argument_list|,
name|headers
argument_list|)
return|;
block|}
specifier|private
name|Response
name|prepareResponse
parameter_list|(
name|SearchResult
name|searchResult
parameter_list|,
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|ResponseBuilder
name|rb
init|=
name|Response
operator|.
name|ok
argument_list|(
name|searchResult
argument_list|)
decl_stmt|;
name|rb
operator|.
name|header
argument_list|(
name|HttpHeaders
operator|.
name|CONTENT_TYPE
argument_list|,
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
operator|+
literal|"; charset=utf-8"
argument_list|)
expr_stmt|;
name|addCORSOrigin
argument_list|(
name|servletContext
argument_list|,
name|rb
argument_list|,
name|headers
argument_list|)
expr_stmt|;
return|return
name|rb
operator|.
name|build
argument_list|()
return|;
block|}
block|}
end_class

end_unit

