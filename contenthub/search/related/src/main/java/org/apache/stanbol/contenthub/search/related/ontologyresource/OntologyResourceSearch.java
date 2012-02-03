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
name|related
operator|.
name|ontologyresource
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
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
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|TcManager
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
name|rdf
operator|.
name|jena
operator|.
name|facade
operator|.
name|JenaGraph
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
name|stanbol
operator|.
name|contenthub
operator|.
name|search
operator|.
name|related
operator|.
name|RelatedKeywordImpl
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
name|related
operator|.
name|RelatedKeyword
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
name|RelatedKeywordSearch
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
name|ontology
operator|.
name|OntModelSpec
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
name|query
operator|.
name|Query
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
name|query
operator|.
name|QueryExecution
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
name|query
operator|.
name|QueryExecutionFactory
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
name|query
operator|.
name|QuerySolution
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
name|query
operator|.
name|ResultSet
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
name|query
operator|.
name|larq
operator|.
name|IndexBuilderString
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
name|query
operator|.
name|larq
operator|.
name|IndexLARQ
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
name|query
operator|.
name|larq
operator|.
name|LARQ
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
name|Model
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
name|ModelFactory
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
name|Property
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
name|ResourceFactory
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

begin_comment
comment|/**  *   * @author cihan  *   */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
specifier|public
class|class
name|OntologyResourceSearch
implements|implements
name|RelatedKeywordSearch
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
name|OntologyResourceSearch
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|IndexBuilderString
name|userGraphIndexBuilder
decl_stmt|;
specifier|private
name|OntModel
name|userOntology
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|TcManager
name|tcManager
decl_stmt|;
comment|/**      * Search for the given keyword in the external ontology as OWL individual, OWL class or CMS object by      * appending a "*" character at the end of the keyword if there is not any      */
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
name|search
parameter_list|(
name|String
name|keyword
parameter_list|,
name|String
name|ontologyURI
parameter_list|)
throws|throws
name|SearchException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
name|relatedKeywordsMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|ontologyURI
operator|!=
literal|null
operator|&&
operator|!
name|ontologyURI
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
name|relatedKeywords
init|=
operator|new
name|ArrayList
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|()
decl_stmt|;
comment|// index of the search model is obtained and checked
name|indexOntology
argument_list|(
name|ontologyURI
argument_list|)
expr_stmt|;
name|IndexLARQ
name|index
init|=
name|userGraphIndexBuilder
operator|.
name|getIndex
argument_list|()
decl_stmt|;
comment|// classes
name|logger
operator|.
name|debug
argument_list|(
literal|"Processing classes for related keywords"
argument_list|)
expr_stmt|;
name|Query
name|query
init|=
name|QueryFactory
operator|.
name|getClassQuery
argument_list|(
name|keyword
argument_list|)
decl_stmt|;
name|QueryExecution
name|classQExec
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|userOntology
argument_list|)
decl_stmt|;
name|LARQ
operator|.
name|setDefaultIndex
argument_list|(
name|classQExec
operator|.
name|getContext
argument_list|()
argument_list|,
name|index
argument_list|)
expr_stmt|;
name|ResultSet
name|result
init|=
name|classQExec
operator|.
name|execSelect
argument_list|()
decl_stmt|;
name|processClassResultSet
argument_list|(
name|result
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
comment|// individuals
name|logger
operator|.
name|debug
argument_list|(
literal|"Processing individuals for related keywords"
argument_list|)
expr_stmt|;
name|query
operator|=
name|QueryFactory
operator|.
name|getIndividualQuery
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
name|QueryExecution
name|individualQExec
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|userOntology
argument_list|)
decl_stmt|;
name|LARQ
operator|.
name|setDefaultIndex
argument_list|(
name|individualQExec
operator|.
name|getContext
argument_list|()
argument_list|,
name|index
argument_list|)
expr_stmt|;
name|result
operator|=
name|individualQExec
operator|.
name|execSelect
argument_list|()
expr_stmt|;
name|processIndividualResultSet
argument_list|(
name|result
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
comment|// CMS Objects
name|logger
operator|.
name|debug
argument_list|(
literal|"Processing CMS objects for related keywords"
argument_list|)
expr_stmt|;
name|query
operator|=
name|QueryFactory
operator|.
name|getCMSObjectQuery
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
name|QueryExecution
name|cmsObjectQueryExec
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|userOntology
argument_list|)
decl_stmt|;
name|LARQ
operator|.
name|setDefaultIndex
argument_list|(
name|cmsObjectQueryExec
operator|.
name|getContext
argument_list|()
argument_list|,
name|index
argument_list|)
expr_stmt|;
name|result
operator|=
name|cmsObjectQueryExec
operator|.
name|execSelect
argument_list|()
expr_stmt|;
name|processCMSObjectResultSet
argument_list|(
name|result
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
name|relatedKeywordsMap
operator|.
name|put
argument_list|(
name|RelatedKeyword
operator|.
name|Source
operator|.
name|ONTOLOGY
operator|.
name|toString
argument_list|()
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
return|return
name|relatedKeywordsMap
return|;
block|}
specifier|private
name|void
name|indexOntology
parameter_list|(
name|String
name|userOntologyURI
parameter_list|)
throws|throws
name|SearchException
block|{
name|OntModel
name|userGraph
init|=
literal|null
decl_stmt|;
name|MGraph
name|mgraph
init|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
operator|new
name|UriRef
argument_list|(
name|userOntologyURI
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|mgraph
operator|!=
literal|null
condition|)
block|{
name|JenaGraph
name|jenaGraph
init|=
operator|new
name|JenaGraph
argument_list|(
name|mgraph
argument_list|)
decl_stmt|;
name|Model
name|model
init|=
name|ModelFactory
operator|.
name|createModelForGraph
argument_list|(
name|jenaGraph
argument_list|)
decl_stmt|;
name|userGraph
operator|=
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|(
name|OntModelSpec
operator|.
name|OWL_DL_MEM_RDFS_INF
argument_list|)
expr_stmt|;
name|userGraph
operator|.
name|add
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|IndexingHelper
operator|.
name|addIndexPropertyToOntResources
argument_list|(
name|userGraph
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|SearchException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"MGraph having URI %s could obtained through TcManager"
argument_list|,
name|userOntologyURI
argument_list|)
argument_list|)
throw|;
block|}
name|this
operator|.
name|userOntology
operator|=
name|userGraph
expr_stmt|;
name|this
operator|.
name|userGraphIndexBuilder
operator|=
operator|new
name|IndexBuilderString
argument_list|(
name|IndexingHelper
operator|.
name|HAS_LOCAL_NAME
argument_list|)
expr_stmt|;
name|this
operator|.
name|userGraphIndexBuilder
operator|.
name|indexStatements
argument_list|(
name|userGraph
operator|.
name|listStatements
argument_list|()
argument_list|)
expr_stmt|;
comment|// Do not forget to set the default index just before the query execution according to its context
comment|// LARQ.setDefaultIndex(index);
block|}
specifier|private
name|void
name|processClassResultSet
parameter_list|(
name|ResultSet
name|result
parameter_list|,
name|String
name|keyword
parameter_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
name|relatedKeywords
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|results
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|result
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|resultBinding
init|=
name|result
operator|.
name|nextSolution
argument_list|()
decl_stmt|;
name|RDFNode
name|rdfNode
init|=
name|resultBinding
operator|.
name|get
argument_list|(
literal|"class"
argument_list|)
decl_stmt|;
name|double
name|score
init|=
name|resultBinding
operator|.
name|getLiteral
argument_list|(
literal|"score"
argument_list|)
operator|.
name|getDouble
argument_list|()
decl_stmt|;
if|if
condition|(
name|rdfNode
operator|.
name|isURIResource
argument_list|()
condition|)
block|{
name|String
name|uri
init|=
name|rdfNode
operator|.
name|asResource
argument_list|()
operator|.
name|getURI
argument_list|()
decl_stmt|;
name|results
operator|.
name|put
argument_list|(
name|uri
argument_list|,
name|score
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|uri
range|:
name|results
operator|.
name|keySet
argument_list|()
control|)
block|{
name|double
name|score
init|=
name|results
operator|.
name|get
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|ClosureHelper
operator|.
name|getInstance
argument_list|(
name|userOntology
argument_list|)
operator|.
name|computeClassClosure
argument_list|(
name|uri
argument_list|,
literal|6
argument_list|,
name|score
argument_list|,
literal|1.5
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|processIndividualResultSet
parameter_list|(
name|ResultSet
name|result
parameter_list|,
name|String
name|keyword
parameter_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
name|relatedKeywords
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|results
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|result
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|resultBinding
init|=
name|result
operator|.
name|nextSolution
argument_list|()
decl_stmt|;
name|RDFNode
name|rdfNode
init|=
name|resultBinding
operator|.
name|get
argument_list|(
literal|"individual"
argument_list|)
decl_stmt|;
name|double
name|score
init|=
name|resultBinding
operator|.
name|getLiteral
argument_list|(
literal|"score"
argument_list|)
operator|.
name|getDouble
argument_list|()
decl_stmt|;
if|if
condition|(
name|rdfNode
operator|.
name|isURIResource
argument_list|()
condition|)
block|{
name|String
name|uri
init|=
name|rdfNode
operator|.
name|asResource
argument_list|()
operator|.
name|getURI
argument_list|()
decl_stmt|;
name|results
operator|.
name|put
argument_list|(
name|uri
argument_list|,
name|score
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|uri
range|:
name|results
operator|.
name|keySet
argument_list|()
control|)
block|{
name|double
name|score
init|=
name|results
operator|.
name|get
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|ClosureHelper
operator|.
name|getInstance
argument_list|(
name|userOntology
argument_list|)
operator|.
name|computeIndividualClosure
argument_list|(
name|uri
argument_list|,
literal|6
argument_list|,
name|score
argument_list|,
literal|1.5
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Process the SPARQL result passed in<code>result</code> parameter and computes closures for them.      */
specifier|private
name|void
name|processCMSObjectResultSet
parameter_list|(
name|ResultSet
name|result
parameter_list|,
name|String
name|keyword
parameter_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
name|relatedKeywords
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|results
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|result
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|resultBinding
init|=
name|result
operator|.
name|nextSolution
argument_list|()
decl_stmt|;
name|RDFNode
name|rdfNode
init|=
name|resultBinding
operator|.
name|get
argument_list|(
literal|"cmsobject"
argument_list|)
decl_stmt|;
name|double
name|score
init|=
name|resultBinding
operator|.
name|getLiteral
argument_list|(
literal|"score"
argument_list|)
operator|.
name|getDouble
argument_list|()
decl_stmt|;
if|if
condition|(
name|rdfNode
operator|.
name|isURIResource
argument_list|()
condition|)
block|{
name|String
name|uri
init|=
name|rdfNode
operator|.
name|asResource
argument_list|()
operator|.
name|getURI
argument_list|()
decl_stmt|;
name|results
operator|.
name|put
argument_list|(
name|uri
argument_list|,
name|score
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*          * Look up for each URI returned in SPARQL results for a name which is kept in          * "http://www.apache.org/stanbol/cms#name" property. If there is a corresponding name, create a new          * keyword with this name and compute closures for the corresponding context resources.          */
name|Property
name|subsumptionProp
init|=
name|ResourceFactory
operator|.
name|createProperty
argument_list|(
literal|"http://www.apache.org/stanbol/cms#parentRef"
argument_list|)
decl_stmt|;
name|Property
name|nameProp
init|=
name|ResourceFactory
operator|.
name|createProperty
argument_list|(
literal|"http://www.apache.org/stanbol/cms#name"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|uri
range|:
name|results
operator|.
name|keySet
argument_list|()
control|)
block|{
name|Resource
name|keywordResource
init|=
name|ResourceFactory
operator|.
name|createResource
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Statement
argument_list|>
name|nameStatements
init|=
name|userOntology
operator|.
name|listStatements
argument_list|(
name|keywordResource
argument_list|,
name|nameProp
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
if|if
condition|(
name|nameStatements
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
name|matchedResourceName
init|=
name|nameStatements
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getString
argument_list|()
decl_stmt|;
name|double
name|initialScore
init|=
name|results
operator|.
name|get
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|relatedKeywords
operator|.
name|add
argument_list|(
operator|new
name|RelatedKeywordImpl
argument_list|(
name|matchedResourceName
argument_list|,
name|initialScore
argument_list|,
name|RelatedKeyword
operator|.
name|Source
operator|.
name|ONTOLOGY
argument_list|)
argument_list|)
expr_stmt|;
name|ClosureHelper
operator|.
name|getInstance
argument_list|(
name|userOntology
argument_list|)
operator|.
name|computeClosureWithProperty
argument_list|(
name|keywordResource
argument_list|,
name|subsumptionProp
argument_list|,
literal|2
argument_list|,
name|initialScore
argument_list|,
literal|1.5
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
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
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
name|search
parameter_list|(
name|String
name|keyword
parameter_list|)
throws|throws
name|SearchException
block|{
comment|// TODO Cannot search without an ontology URI. A default one maybe?
return|return
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
argument_list|>
argument_list|()
return|;
block|}
block|}
end_class

end_unit
