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
name|core
operator|.
name|search
operator|.
name|execution
package|;
end_package

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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|UUID
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
name|DocumentResource
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
name|ExternalResource
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
name|enhanced
operator|.
name|EnhGraph
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
name|graph
operator|.
name|Node
import|;
end_import

begin_comment
comment|/**  *   * @author cihan  *   */
end_comment

begin_class
specifier|public
class|class
name|SearchContextFactoryImpl
implements|implements
name|SearchContextFactory
block|{
specifier|private
specifier|static
specifier|final
name|double
name|MAX_SCORE
init|=
literal|1.0
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|double
name|MAX_WEIGHT
init|=
literal|1.0
decl_stmt|;
specifier|private
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SearchContextFactoryImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|SearchContext
name|searchContext
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|QueryKeywordImpl
argument_list|>
name|queryKeywords
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|QueryKeywordImpl
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|KeywordImpl
argument_list|>
name|keywords
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|KeywordImpl
argument_list|>
argument_list|()
decl_stmt|;
comment|// DocumentURI -> DocumentResource
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|DocumentResourceImpl
argument_list|>
name|documents
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|DocumentResourceImpl
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|ExternalResourceImpl
argument_list|>
name|externals
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ExternalResourceImpl
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Keyword
argument_list|,
name|String
argument_list|>
name|inverseKeywords
init|=
operator|new
name|HashMap
argument_list|<
name|Keyword
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|QueryKeyword
argument_list|,
name|String
argument_list|>
name|inverseQueryKeywords
init|=
operator|new
name|HashMap
argument_list|<
name|QueryKeyword
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|ClassResourceImpl
argument_list|>
name|inverseClassResources
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ClassResourceImpl
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|IndividualResourceImpl
argument_list|>
name|inverseIndividualResources
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|IndividualResourceImpl
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|keywordName
parameter_list|(
name|String
name|keyword
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|SearchVocabulary
operator|.
name|getUri
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"individuals/"
argument_list|)
operator|.
name|append
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|String
name|className
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|SearchVocabulary
operator|.
name|getUri
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"relatedClasses/"
argument_list|)
operator|.
name|append
argument_list|(
name|UUID
operator|.
name|randomUUID
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|String
name|individualName
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|SearchVocabulary
operator|.
name|getUri
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"relatedIndividuals/"
argument_list|)
operator|.
name|append
argument_list|(
name|UUID
operator|.
name|randomUUID
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|protected
name|String
name|documentName
parameter_list|(
name|String
name|documentURI
parameter_list|,
name|Keyword
name|keyword
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|SearchVocabulary
operator|.
name|getUri
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"relatedDocuments/"
argument_list|)
operator|.
name|append
argument_list|(
name|documentURI
operator|.
name|substring
argument_list|(
name|documentURI
operator|.
name|lastIndexOf
argument_list|(
literal|"/"
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|protected
name|String
name|externalName
parameter_list|(
name|String
name|externalURI
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|SearchVocabulary
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"externalEntity/"
argument_list|)
operator|.
name|append
argument_list|(
name|externalURI
operator|.
name|replace
argument_list|(
literal|"http://"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|//
specifier|protected
name|QueryKeywordImpl
name|getQueryKeyword
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
if|if
condition|(
name|queryKeywords
operator|.
name|containsKey
argument_list|(
name|uri
argument_list|)
condition|)
block|{
return|return
name|queryKeywords
operator|.
name|get
argument_list|(
name|uri
argument_list|)
return|;
block|}
else|else
block|{
comment|// FIXME search for already existing keywords in model
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Query Keyword is not present in factory: "
operator|+
name|uri
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|KeywordImpl
name|getKeyword
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
if|if
condition|(
name|queryKeywords
operator|.
name|containsKey
argument_list|(
name|uri
argument_list|)
condition|)
block|{
return|return
name|queryKeywords
operator|.
name|get
argument_list|(
name|uri
argument_list|)
return|;
block|}
else|else
block|{
if|if
condition|(
name|keywords
operator|.
name|containsKey
argument_list|(
name|uri
argument_list|)
condition|)
block|{
return|return
name|keywords
operator|.
name|get
argument_list|(
name|uri
argument_list|)
return|;
block|}
else|else
block|{
comment|// FIXME search for already existing keywords in model
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Keyword is not present in factory: "
operator|+
name|uri
argument_list|)
throw|;
block|}
block|}
block|}
comment|// FIXME make not found check first
specifier|protected
name|String
name|getQueryKeywordURI
parameter_list|(
name|QueryKeyword
name|keyword
parameter_list|)
block|{
if|if
condition|(
name|inverseQueryKeywords
operator|.
name|containsKey
argument_list|(
name|keyword
argument_list|)
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"Found query keyword {} in context factory"
argument_list|,
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Query Keyword {} not found in context factory. Creating an instance ..."
argument_list|,
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
name|createQueryKeyword
argument_list|(
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|inverseQueryKeywords
operator|.
name|get
argument_list|(
name|keyword
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getKeywordURI
parameter_list|(
name|Keyword
name|keyword
parameter_list|)
block|{
if|if
condition|(
name|keyword
operator|instanceof
name|QueryKeyword
condition|)
block|{
if|if
condition|(
name|inverseQueryKeywords
operator|.
name|containsKey
argument_list|(
name|keyword
argument_list|)
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"Found keyword {} in context factory"
argument_list|,
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Keyword {} not found in context factory. Creating an instance ..."
argument_list|,
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
name|createQueryKeyword
argument_list|(
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|inverseQueryKeywords
operator|.
name|get
argument_list|(
name|keyword
argument_list|)
return|;
block|}
else|else
block|{
if|if
condition|(
name|inverseKeywords
operator|.
name|containsKey
argument_list|(
name|keyword
argument_list|)
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"Found keyword {} in context factory"
argument_list|,
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Keyword {} not found in context factory. Creating an instance ..."
argument_list|,
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|)
expr_stmt|;
name|createKeyword
argument_list|(
name|keyword
operator|.
name|getKeyword
argument_list|()
argument_list|,
name|keyword
operator|.
name|getScore
argument_list|()
argument_list|,
name|keyword
operator|.
name|getRelatedQueryKeyword
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|inverseKeywords
operator|.
name|get
argument_list|(
name|keyword
argument_list|)
return|;
block|}
block|}
specifier|protected
name|ClassResourceImpl
name|getClassResource
parameter_list|(
name|String
name|classURI
parameter_list|)
block|{
if|if
condition|(
name|inverseClassResources
operator|.
name|containsKey
argument_list|(
name|classURI
argument_list|)
condition|)
block|{
return|return
name|inverseClassResources
operator|.
name|get
argument_list|(
name|classURI
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Class resource with uri "
operator|+
name|classURI
operator|+
literal|"not found "
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|IndividualResourceImpl
name|getIndividualResource
parameter_list|(
name|String
name|individualURI
parameter_list|)
block|{
if|if
condition|(
name|inverseIndividualResources
operator|.
name|containsKey
argument_list|(
name|individualURI
argument_list|)
condition|)
block|{
return|return
name|inverseIndividualResources
operator|.
name|get
argument_list|(
name|individualURI
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Individual resource with uri "
operator|+
name|individualURI
operator|+
literal|"not found "
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|DocumentResourceImpl
name|getDocumentResource
parameter_list|(
name|String
name|documentURI
parameter_list|)
block|{
if|if
condition|(
name|documents
operator|.
name|containsKey
argument_list|(
name|documentURI
argument_list|)
condition|)
block|{
return|return
name|documents
operator|.
name|get
argument_list|(
name|documentURI
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Document resource with uri "
operator|+
name|documentURI
operator|+
literal|"not found "
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|ExternalResourceImpl
name|getExternalResource
parameter_list|(
name|String
name|externalResourceURI
parameter_list|)
block|{
if|if
condition|(
name|externals
operator|.
name|containsKey
argument_list|(
name|externalResourceURI
argument_list|)
condition|)
block|{
return|return
name|externals
operator|.
name|get
argument_list|(
name|externalResourceURI
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"External resource with uri "
operator|+
name|externalResourceURI
operator|+
literal|"not found"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|QueryKeyword
name|createQueryKeyword
parameter_list|(
name|String
name|keyword
parameter_list|)
block|{
name|Node
name|n
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|keywordName
argument_list|(
name|keyword
argument_list|)
argument_list|)
decl_stmt|;
name|QueryKeywordImpl
name|k
init|=
operator|new
name|QueryKeywordImpl
argument_list|(
name|n
argument_list|,
operator|(
name|EnhGraph
operator|)
name|searchContext
argument_list|,
name|keyword
argument_list|,
name|MAX_SCORE
argument_list|,
name|MAX_WEIGHT
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|queryKeywords
operator|.
name|put
argument_list|(
name|k
operator|.
name|getURI
argument_list|()
argument_list|,
name|k
argument_list|)
expr_stmt|;
name|inverseQueryKeywords
operator|.
name|put
argument_list|(
name|k
argument_list|,
name|k
operator|.
name|getURI
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|k
return|;
block|}
annotation|@
name|Override
specifier|public
name|Keyword
name|createKeyword
parameter_list|(
name|String
name|keyword
parameter_list|,
name|double
name|score
parameter_list|,
name|QueryKeyword
name|queryKeyword
parameter_list|)
block|{
name|Node
name|n
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|keywordName
argument_list|(
name|keyword
argument_list|)
argument_list|)
decl_stmt|;
name|KeywordImpl
name|k
init|=
operator|new
name|KeywordImpl
argument_list|(
name|n
argument_list|,
operator|(
name|EnhGraph
operator|)
name|searchContext
argument_list|,
name|keyword
argument_list|,
name|MAX_WEIGHT
argument_list|,
name|score
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|keywords
operator|.
name|put
argument_list|(
name|k
operator|.
name|getURI
argument_list|()
argument_list|,
name|k
argument_list|)
expr_stmt|;
name|inverseKeywords
operator|.
name|put
argument_list|(
name|k
argument_list|,
name|k
operator|.
name|getURI
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|queryKeyword
operator|!=
literal|null
condition|)
block|{
name|queryKeyword
operator|.
name|addRelatedKeyword
argument_list|(
name|k
argument_list|)
expr_stmt|;
block|}
return|return
name|k
return|;
block|}
annotation|@
name|Override
specifier|public
name|ClassResource
name|createClassResource
parameter_list|(
name|String
name|classURI
parameter_list|,
name|double
name|weight
parameter_list|,
name|double
name|score
parameter_list|,
name|Keyword
name|relatedKeyword
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|inverseClassResources
operator|.
name|containsKey
argument_list|(
name|classURI
argument_list|)
condition|)
block|{
name|ClassResource
name|cr
init|=
name|inverseClassResources
operator|.
name|get
argument_list|(
name|classURI
argument_list|)
decl_stmt|;
name|cr
operator|.
name|updateScore
argument_list|(
name|score
argument_list|,
name|weight
argument_list|)
expr_stmt|;
return|return
name|cr
return|;
block|}
else|else
block|{
name|Node
name|n
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|className
argument_list|()
argument_list|)
decl_stmt|;
name|ClassResourceImpl
name|cri
init|=
operator|new
name|ClassResourceImpl
argument_list|(
name|n
argument_list|,
operator|(
name|EnhGraph
operator|)
name|searchContext
argument_list|,
name|weight
argument_list|,
name|score
argument_list|,
name|relatedKeyword
argument_list|,
name|classURI
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|cri
operator|.
name|setDereferenceableURI
argument_list|(
name|resolveReference
argument_list|(
name|classURI
argument_list|)
argument_list|)
expr_stmt|;
name|inverseClassResources
operator|.
name|put
argument_list|(
name|classURI
argument_list|,
name|cri
argument_list|)
expr_stmt|;
return|return
name|cri
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|IndividualResource
name|createIndividualResource
parameter_list|(
name|String
name|individualURI
parameter_list|,
name|double
name|weight
parameter_list|,
name|double
name|score
parameter_list|,
name|Keyword
name|relatedKeyword
parameter_list|)
block|{
if|if
condition|(
name|inverseIndividualResources
operator|.
name|containsKey
argument_list|(
name|individualURI
argument_list|)
condition|)
block|{
name|IndividualResource
name|ir
init|=
name|inverseIndividualResources
operator|.
name|get
argument_list|(
name|individualURI
argument_list|)
decl_stmt|;
name|ir
operator|.
name|updateScore
argument_list|(
name|score
argument_list|,
name|weight
argument_list|)
expr_stmt|;
return|return
name|ir
return|;
block|}
else|else
block|{
name|Node
name|n
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|individualName
argument_list|()
argument_list|)
decl_stmt|;
name|IndividualResourceImpl
name|iri
init|=
operator|new
name|IndividualResourceImpl
argument_list|(
name|n
argument_list|,
operator|(
name|EnhGraph
operator|)
name|searchContext
argument_list|,
name|weight
argument_list|,
name|score
argument_list|,
name|relatedKeyword
argument_list|,
name|individualURI
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|iri
operator|.
name|setDereferenceableURI
argument_list|(
name|resolveReference
argument_list|(
name|individualURI
argument_list|)
argument_list|)
expr_stmt|;
name|inverseIndividualResources
operator|.
name|put
argument_list|(
name|individualURI
argument_list|,
name|iri
argument_list|)
expr_stmt|;
return|return
name|iri
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|DocumentResource
name|createDocumentResource
parameter_list|(
name|String
name|documentURI
parameter_list|,
name|double
name|weight
parameter_list|,
name|double
name|score
parameter_list|,
name|Keyword
name|relatedKeyword
parameter_list|,
name|String
name|relatedText
comment|/*                                                                       * , String contentRepositoryItem                                                                       */
parameter_list|)
block|{
name|String
name|key
init|=
name|documentName
argument_list|(
name|documentURI
argument_list|,
name|relatedKeyword
argument_list|)
decl_stmt|;
if|if
condition|(
name|documents
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|DocumentResource
name|dr
init|=
name|documents
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|dr
operator|.
name|updateScore
argument_list|(
name|score
argument_list|,
name|weight
argument_list|)
expr_stmt|;
return|return
name|dr
return|;
block|}
else|else
block|{
name|Node
name|n
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|DocumentResourceImpl
name|dri
init|=
operator|new
name|DocumentResourceImpl
argument_list|(
name|n
argument_list|,
operator|(
name|EnhGraph
operator|)
name|searchContext
argument_list|,
name|documentURI
argument_list|,
name|weight
argument_list|,
name|score
argument_list|,
name|relatedKeyword
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|dri
operator|.
name|setRelatedText
argument_list|(
name|relatedText
argument_list|)
expr_stmt|;
comment|/* dri.setRelatedContentRepositoryItem(contentRepositoryItem); */
name|documents
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|dri
argument_list|)
expr_stmt|;
return|return
name|dri
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|ExternalResource
name|createExternalResource
parameter_list|(
name|String
name|reference
parameter_list|,
name|double
name|weight
parameter_list|,
name|double
name|score
parameter_list|,
name|Keyword
name|relatedKeyword
parameter_list|)
block|{
name|String
name|key
init|=
name|externalName
argument_list|(
name|reference
argument_list|)
decl_stmt|;
if|if
condition|(
name|documents
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|ExternalResourceImpl
name|er
init|=
name|externals
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|er
operator|.
name|updateScore
argument_list|(
name|score
argument_list|,
name|weight
argument_list|)
expr_stmt|;
return|return
name|er
return|;
block|}
else|else
block|{
name|Node
name|n
init|=
name|Node
operator|.
name|createURI
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|ExternalResourceImpl
name|eri
init|=
operator|new
name|ExternalResourceImpl
argument_list|(
name|n
argument_list|,
operator|(
name|EnhGraph
operator|)
name|searchContext
argument_list|,
name|weight
argument_list|,
name|score
argument_list|,
name|reference
argument_list|,
name|relatedKeyword
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|eri
operator|.
name|setDereferenceableURI
argument_list|(
name|reference
argument_list|)
expr_stmt|;
name|externals
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|eri
argument_list|)
expr_stmt|;
return|return
name|eri
return|;
block|}
block|}
specifier|private
name|String
name|resolveReference
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
return|return
name|uri
return|;
comment|/*          * String path = null; if (resourceManager != null&& uri != null) { path =          * resourceManager.getResourceFullPath(uri); } if (path == null || path.isEmpty()) { path = uri; }          */
comment|// TODO : that should return the dereferenceable uri of resource in ontology store
block|}
annotation|@
name|Override
specifier|public
name|void
name|setSearchContext
parameter_list|(
name|SearchContext
name|searchContext
parameter_list|)
block|{
name|this
operator|.
name|searchContext
operator|=
name|searchContext
expr_stmt|;
block|}
block|}
end_class

end_unit

