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
name|cmsadapter
operator|.
name|core
operator|.
name|mapping
package|;
end_package

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
name|concurrent
operator|.
name|CopyOnWriteArrayList
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
name|ReferenceCardinality
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
name|ReferencePolicy
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
name|ReferenceStrategy
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFBridgeException
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFMapper
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|ConnectionInfo
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccess
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessException
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessManager
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
comment|/**  * This component keeps track of {@link RDFBridge}s and {@link RDFMapper}s in the environment and it provides  * a method to submit RDF data to be annotated according to<code>RDFBridge</code>s.<code>RDFMapper</code>s  * update repository based on the annotated RDF.  *   * @author suat  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|RDFBridgeManager
operator|.
name|class
argument_list|)
specifier|public
class|class
name|RDFBridgeManager
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
name|RDFBridgeManager
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|referenceInterface
operator|=
name|RDFBridge
operator|.
name|class
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|bind
operator|=
literal|"bindRDFBridge"
argument_list|,
name|unbind
operator|=
literal|"unbindRDFBridge"
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|)
name|List
argument_list|<
name|RDFBridge
argument_list|>
name|rdfBridges
init|=
operator|new
name|CopyOnWriteArrayList
argument_list|<
name|RDFBridge
argument_list|>
argument_list|()
decl_stmt|;
annotation|@
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_MULTIPLE
argument_list|,
name|referenceInterface
operator|=
name|RDFMapper
operator|.
name|class
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|bind
operator|=
literal|"bindRDFMapper"
argument_list|,
name|unbind
operator|=
literal|"unbindRDFMapper"
argument_list|,
name|strategy
operator|=
name|ReferenceStrategy
operator|.
name|EVENT
argument_list|)
name|List
argument_list|<
name|RDFMapper
argument_list|>
name|rdfMappers
init|=
operator|new
name|CopyOnWriteArrayList
argument_list|<
name|RDFMapper
argument_list|>
argument_list|()
decl_stmt|;
annotation|@
name|Reference
name|RepositoryAccessManager
name|accessManager
decl_stmt|;
specifier|public
name|void
name|storeRDFToRepository
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|,
name|MGraph
name|rawRDFData
parameter_list|)
throws|throws
name|RepositoryAccessException
throws|,
name|RDFBridgeException
block|{
if|if
condition|(
name|rdfBridges
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"There is no RDF Bridge to execute"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// According to connection type get RDF mapper, repository accessor, session
name|RDFMapper
name|mapper
init|=
name|getRDFMapper
argument_list|(
name|connectionInfo
argument_list|)
decl_stmt|;
name|RepositoryAccess
name|repositoryAccess
init|=
name|accessManager
operator|.
name|getRepositoryAccessor
argument_list|(
name|connectionInfo
argument_list|)
decl_stmt|;
name|Object
name|session
init|=
name|repositoryAccess
operator|.
name|getSession
argument_list|(
name|connectionInfo
argument_list|)
decl_stmt|;
comment|// Annotate raw RDF with CMS vocabulary annotations according to bridges
name|CMSVocabularyAnnotator
name|annotator
init|=
operator|new
name|CMSVocabularyAnnotator
argument_list|()
decl_stmt|;
name|annotator
operator|.
name|addAnnotationsToGraph
argument_list|(
name|rdfBridges
argument_list|,
name|rawRDFData
argument_list|)
expr_stmt|;
comment|// Store annotated RDF in repository
name|mapper
operator|.
name|storeRDFinRepository
argument_list|(
name|session
argument_list|,
name|rawRDFData
argument_list|)
expr_stmt|;
block|}
specifier|private
name|RDFMapper
name|getRDFMapper
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
block|{
name|RDFMapper
name|mapper
init|=
literal|null
decl_stmt|;
name|String
name|type
init|=
name|connectionInfo
operator|.
name|getConnectionType
argument_list|()
decl_stmt|;
for|for
control|(
name|RDFMapper
name|rdfMapper
range|:
name|rdfMappers
control|)
block|{
if|if
condition|(
name|rdfMapper
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|.
name|startsWith
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|mapper
operator|=
operator|(
name|RDFMapper
operator|)
name|rdfMapper
expr_stmt|;
block|}
block|}
if|if
condition|(
name|mapper
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Failed to retrieve RDFMapper for connection type: {}"
argument_list|,
name|type
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Failed to retrieve RDFMapper for connection type: "
operator|+
name|type
argument_list|)
throw|;
block|}
return|return
name|mapper
return|;
block|}
specifier|protected
name|void
name|bindRDFBridge
parameter_list|(
name|RDFBridge
name|rdfBridge
parameter_list|)
block|{
name|rdfBridges
operator|.
name|add
argument_list|(
name|rdfBridge
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindRDFBridge
parameter_list|(
name|RDFBridge
name|rdfBridge
parameter_list|)
block|{
name|rdfBridges
operator|.
name|remove
argument_list|(
name|rdfBridge
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|bindRDFMapper
parameter_list|(
name|RDFMapper
name|rdfMapper
parameter_list|)
block|{
name|rdfMappers
operator|.
name|add
argument_list|(
name|rdfMapper
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|unbindRDFMapper
parameter_list|(
name|RDFMapper
name|rdfMapper
parameter_list|)
block|{
name|rdfMappers
operator|.
name|remove
argument_list|(
name|rdfMapper
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

