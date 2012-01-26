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
name|commons
operator|.
name|io
operator|.
name|FilenameUtils
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
name|related
operator|.
name|RelatedKeyword
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
name|Individual
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
name|OntClass
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
name|OntResource
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
name|vocabulary
operator|.
name|OWL
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
name|OWL2
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
name|RDFS
import|;
end_import

begin_comment
comment|/**  *   * @author cihan  *   */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ClosureHelper
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
name|ClosureHelper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|OntModel
name|model
decl_stmt|;
specifier|private
name|ClosureHelper
parameter_list|(
name|OntModel
name|userOntology
parameter_list|)
block|{
name|this
operator|.
name|model
operator|=
name|userOntology
expr_stmt|;
block|}
specifier|public
specifier|static
name|ClosureHelper
name|getInstance
parameter_list|(
name|OntModel
name|userOntology
parameter_list|)
block|{
return|return
operator|new
name|ClosureHelper
argument_list|(
name|userOntology
argument_list|)
return|;
block|}
specifier|public
name|List
argument_list|<
name|RelatedKeyword
argument_list|>
name|computeClassClosure
parameter_list|(
name|String
name|classURI
parameter_list|,
name|int
name|maxDepth
parameter_list|,
name|double
name|initialScore
parameter_list|,
name|double
name|degradingCoefficient
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
name|long
name|t1
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|relatedClassURIs
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|computeSuperClassClosure
argument_list|(
name|classURI
argument_list|,
name|maxDepth
argument_list|,
name|initialScore
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
name|computeSubClassClosure
argument_list|(
name|classURI
argument_list|,
name|maxDepth
argument_list|,
name|initialScore
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|relatedClassURI
range|:
name|relatedClassURIs
control|)
block|{
name|computeInstanceClosure
argument_list|(
name|relatedClassURI
argument_list|,
name|initialScore
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Computed class closure of {} in {} miliseconds"
argument_list|,
name|classURI
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|t1
argument_list|)
expr_stmt|;
return|return
name|relatedKeywords
return|;
block|}
specifier|public
specifier|final
name|void
name|computeIndividualClosure
parameter_list|(
name|String
name|individualURI
parameter_list|,
name|int
name|maxDepth
parameter_list|,
name|double
name|initialScore
parameter_list|,
name|double
name|degradingCoefficient
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
name|long
name|t1
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Individual
name|ind
init|=
name|model
operator|.
name|getIndividual
argument_list|(
name|individualURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|ind
operator|!=
literal|null
operator|&&
name|ind
operator|.
name|isURIResource
argument_list|()
condition|)
block|{
for|for
control|(
name|OntClass
name|ontClass
range|:
name|ind
operator|.
name|listOntClasses
argument_list|(
literal|true
argument_list|)
operator|.
name|toSet
argument_list|()
control|)
block|{
if|if
condition|(
name|ontClass
operator|!=
literal|null
operator|&&
name|ontClass
operator|.
name|isURIResource
argument_list|()
condition|)
block|{
name|computeClassClosure
argument_list|(
name|ontClass
operator|.
name|getURI
argument_list|()
argument_list|,
literal|6
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
name|log
operator|.
name|debug
argument_list|(
literal|"Computed individual closure of {} in {} miliseconds"
argument_list|,
name|individualURI
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|t1
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|computeSuperClassClosure
parameter_list|(
name|String
name|classURI
parameter_list|,
name|int
name|maxDepth
parameter_list|,
name|double
name|initialScore
parameter_list|,
name|double
name|degradingCoefficient
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
name|OntClass
name|ontClass
init|=
name|model
operator|.
name|getOntClass
argument_list|(
name|classURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|ontClass
operator|==
literal|null
operator|||
name|ontClass
operator|.
name|isAnon
argument_list|()
operator|||
name|isClassNotValid
argument_list|(
name|ontClass
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Can not find class with uri {}, skipping ..."
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|maxDepth
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Max depth reached not examining the resource {}"
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
name|String
name|rkw
init|=
name|ontClass
operator|.
name|getLocalName
argument_list|()
decl_stmt|;
name|relatedKeywords
operator|.
name|add
argument_list|(
operator|new
name|RelatedKeywordImpl
argument_list|(
name|rkw
argument_list|,
name|initialScore
argument_list|,
literal|"Ontology"
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Added {} as a related keyword to {} by super class relation"
argument_list|,
name|rkw
argument_list|,
name|keyword
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Computing super class closure of {} "
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|OntClass
argument_list|>
name|superClasses
init|=
name|ontClass
operator|.
name|listSuperClasses
argument_list|(
literal|true
argument_list|)
operator|.
name|toList
argument_list|()
decl_stmt|;
for|for
control|(
name|OntClass
name|superClass
range|:
name|superClasses
control|)
block|{
name|computeSuperClassClosure
argument_list|(
name|superClass
operator|.
name|getURI
argument_list|()
argument_list|,
name|maxDepth
operator|-
literal|1
argument_list|,
name|initialScore
operator|/
name|degradingCoefficient
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|computeSubClassClosure
parameter_list|(
name|String
name|classURI
parameter_list|,
name|int
name|maxDepth
parameter_list|,
name|double
name|initialScore
parameter_list|,
name|double
name|degradingCoefficient
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
name|OntClass
name|ontClass
init|=
name|model
operator|.
name|getOntClass
argument_list|(
name|classURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|ontClass
operator|==
literal|null
operator|||
name|ontClass
operator|.
name|isAnon
argument_list|()
operator|||
name|isClassNotValid
argument_list|(
name|ontClass
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Can not find class with uri {}, skipping ..."
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|maxDepth
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Max depth reached not examining the resource {}"
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
name|String
name|rkw
init|=
name|ontClass
operator|.
name|getLocalName
argument_list|()
decl_stmt|;
name|relatedKeywords
operator|.
name|add
argument_list|(
operator|new
name|RelatedKeywordImpl
argument_list|(
name|rkw
argument_list|,
name|initialScore
argument_list|,
literal|"Ontology"
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Added {} as related keyword to {} by sub class relation"
argument_list|,
name|classURI
argument_list|,
name|keyword
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Computing sub class closure of {} "
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|OntClass
argument_list|>
name|subClasses
init|=
name|ontClass
operator|.
name|listSubClasses
argument_list|(
literal|true
argument_list|)
operator|.
name|toList
argument_list|()
decl_stmt|;
for|for
control|(
name|OntClass
name|subClass
range|:
name|subClasses
control|)
block|{
name|computeSubClassClosure
argument_list|(
name|subClass
operator|.
name|getURI
argument_list|()
argument_list|,
name|maxDepth
operator|-
literal|1
argument_list|,
name|initialScore
operator|/
name|degradingCoefficient
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|computeInstanceClosure
parameter_list|(
name|String
name|classURI
parameter_list|,
name|double
name|initialScore
parameter_list|,
name|double
name|degradingCoefficient
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
name|OntClass
name|ontClass
init|=
name|model
operator|.
name|getOntClass
argument_list|(
name|classURI
argument_list|)
decl_stmt|;
if|if
condition|(
name|ontClass
operator|==
literal|null
operator|||
name|ontClass
operator|.
name|isAnon
argument_list|()
operator|||
name|isClassNotValid
argument_list|(
name|ontClass
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Can not find class with uri {}, skipping ..."
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Computing instance closure of class {} "
argument_list|,
name|classURI
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|?
extends|extends
name|OntResource
argument_list|>
name|instances
init|=
name|ontClass
operator|.
name|listInstances
argument_list|(
literal|true
argument_list|)
operator|.
name|toList
argument_list|()
decl_stmt|;
for|for
control|(
name|OntResource
name|instance
range|:
name|instances
control|)
block|{
if|if
condition|(
name|instance
operator|==
literal|null
operator|||
name|instance
operator|.
name|isAnon
argument_list|()
operator|||
operator|!
name|instance
operator|.
name|isIndividual
argument_list|()
condition|)
block|{
continue|continue;
block|}
else|else
block|{
name|Individual
name|individual
init|=
name|instance
operator|.
name|asIndividual
argument_list|()
decl_stmt|;
name|String
name|rkw
init|=
name|individual
operator|.
name|getLocalName
argument_list|()
decl_stmt|;
name|relatedKeywords
operator|.
name|add
argument_list|(
operator|new
name|RelatedKeywordImpl
argument_list|(
name|rkw
argument_list|,
name|initialScore
operator|/
name|degradingCoefficient
argument_list|,
literal|"Ontology"
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Added {} as a relate keyword to {} "
argument_list|,
name|rkw
argument_list|,
name|keyword
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
name|boolean
name|isClassNotValid
parameter_list|(
name|OntClass
name|klass
parameter_list|)
block|{
name|String
name|uri
init|=
name|klass
operator|.
name|getURI
argument_list|()
decl_stmt|;
return|return
name|uri
operator|.
name|contains
argument_list|(
name|RDF
operator|.
name|getURI
argument_list|()
argument_list|)
operator|||
name|uri
operator|.
name|contains
argument_list|(
name|RDFS
operator|.
name|getURI
argument_list|()
argument_list|)
operator|||
name|uri
operator|.
name|contains
argument_list|(
name|OWL
operator|.
name|getURI
argument_list|()
argument_list|)
operator|||
name|uri
operator|.
name|contains
argument_list|(
name|OWL2
operator|.
name|getURI
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|computeClosureWithProperty
parameter_list|(
name|Resource
name|sourceURI
parameter_list|,
name|Property
name|subsumptionProperty
parameter_list|,
name|int
name|maxDepth
parameter_list|,
name|double
name|initialScore
parameter_list|,
name|double
name|degradingCoefficient
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
name|computeSubclosureWithProperty
argument_list|(
name|sourceURI
argument_list|,
name|subsumptionProperty
argument_list|,
name|maxDepth
argument_list|,
name|initialScore
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
name|computeSuperclosureWithProperty
argument_list|(
name|sourceURI
argument_list|,
name|subsumptionProperty
argument_list|,
name|maxDepth
argument_list|,
name|initialScore
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|computeSubclosureWithProperty
parameter_list|(
name|Resource
name|uri
parameter_list|,
name|Property
name|subsumptionProperty
parameter_list|,
name|int
name|depth
parameter_list|,
name|double
name|initialScore
parameter_list|,
name|double
name|degradingCoefficient
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
if|if
condition|(
name|depth
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Max depth reached not examining the resource {}"
argument_list|,
name|uri
operator|.
name|getURI
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Computing sub concepts of {} "
argument_list|,
name|uri
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Statement
argument_list|>
name|children
init|=
name|model
operator|.
name|listStatements
argument_list|(
literal|null
argument_list|,
name|subsumptionProperty
argument_list|,
name|uri
argument_list|)
operator|.
name|toSet
argument_list|()
decl_stmt|;
name|double
name|score
init|=
name|initialScore
operator|/
name|degradingCoefficient
decl_stmt|;
for|for
control|(
name|Statement
name|childStatement
range|:
name|children
control|)
block|{
name|Resource
name|subject
init|=
name|childStatement
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|String
name|childName
init|=
name|IndexingHelper
operator|.
name|getCMSObjectName
argument_list|(
name|subject
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|childName
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|childName
operator|=
name|cropFileExtensionFromKeyword
argument_list|(
name|childName
argument_list|)
expr_stmt|;
name|relatedKeywords
operator|.
name|add
argument_list|(
operator|new
name|RelatedKeywordImpl
argument_list|(
name|childName
argument_list|,
name|score
argument_list|,
literal|"Ontology"
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Added {} as a related keyword"
argument_list|,
name|childName
argument_list|)
expr_stmt|;
name|computeSubclosureWithProperty
argument_list|(
name|subject
argument_list|,
name|subsumptionProperty
argument_list|,
name|depth
operator|-
literal|1
argument_list|,
name|score
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
name|void
name|computeSuperclosureWithProperty
parameter_list|(
name|Resource
name|uri
parameter_list|,
name|Property
name|subsumptionProperty
parameter_list|,
name|int
name|depth
parameter_list|,
name|double
name|initialScore
parameter_list|,
name|double
name|degradingCoefficient
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
if|if
condition|(
name|depth
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Max depth reached not examining the resource {}"
argument_list|,
name|uri
operator|.
name|getURI
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Computing parent concepts of {} "
argument_list|,
name|uri
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Statement
argument_list|>
name|parents
init|=
name|model
operator|.
name|listStatements
argument_list|(
name|uri
argument_list|,
name|subsumptionProperty
argument_list|,
operator|(
name|RDFNode
operator|)
literal|null
argument_list|)
operator|.
name|toSet
argument_list|()
decl_stmt|;
name|double
name|score
init|=
name|initialScore
operator|/
name|degradingCoefficient
decl_stmt|;
for|for
control|(
name|Statement
name|parentStatement
range|:
name|parents
control|)
block|{
name|Resource
name|object
init|=
name|parentStatement
operator|.
name|getResource
argument_list|()
decl_stmt|;
name|String
name|parentName
init|=
name|IndexingHelper
operator|.
name|getCMSObjectName
argument_list|(
name|object
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|parentName
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|parentName
operator|=
name|cropFileExtensionFromKeyword
argument_list|(
name|parentName
argument_list|)
expr_stmt|;
name|relatedKeywords
operator|.
name|add
argument_list|(
operator|new
name|RelatedKeywordImpl
argument_list|(
name|parentName
argument_list|,
name|score
argument_list|,
literal|"Ontology"
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Added {} as related keyword to "
argument_list|,
name|parentName
argument_list|,
name|keyword
argument_list|)
expr_stmt|;
name|computeSuperclosureWithProperty
argument_list|(
name|object
argument_list|,
name|subsumptionProperty
argument_list|,
name|depth
operator|-
literal|1
argument_list|,
name|score
argument_list|,
name|degradingCoefficient
argument_list|,
name|keyword
argument_list|,
name|relatedKeywords
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * As name of the resources in the CMS Adapter generated ontology would most likely be file names, file      * extension are cropped using the Apache Commons IO library.      */
specifier|public
specifier|static
name|String
name|cropFileExtensionFromKeyword
parameter_list|(
name|String
name|keyword
parameter_list|)
block|{
return|return
name|FilenameUtils
operator|.
name|removeExtension
argument_list|(
name|keyword
argument_list|)
return|;
block|}
block|}
end_class

end_unit

