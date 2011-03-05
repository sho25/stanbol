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
name|clerezza
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|platform
operator|.
name|content
operator|.
name|DiscobitsHandler
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
name|platform
operator|.
name|graphprovider
operator|.
name|content
operator|.
name|ContentGraphProvider
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
name|EntityAlreadyExistsException
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
name|NoSuchEntityException
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
name|core
operator|.
name|impl
operator|.
name|SimpleMGraph
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
name|utils
operator|.
name|GraphNode
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItem
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
name|Store
import|;
end_import

begin_comment
comment|/**  * test  *  * @author andreas  */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|Store
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ClerezzaStore
implements|implements
name|Store
block|{
annotation|@
name|Reference
name|DiscobitsHandler
name|handler
decl_stmt|;
annotation|@
name|Reference
name|ContentGraphProvider
name|cgProvider
decl_stmt|;
annotation|@
name|Reference
name|TcManager
name|tcManager
decl_stmt|;
specifier|public
name|ContentItem
name|create
parameter_list|(
name|String
name|id
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|contentType
parameter_list|)
block|{
comment|// TODO: the semantics of this implementation are wrong: the creation of
comment|// a new content item should not touch the persistence backends. The write
comment|// operation to the backend should only be performed when calling the
comment|// {@link put} method.
name|UriRef
name|uriRef
init|=
operator|new
name|UriRef
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|MGraph
name|metadataGraph
decl_stmt|;
try|try
block|{
name|metadataGraph
operator|=
name|tcManager
operator|.
name|createMGraph
argument_list|(
name|uriRef
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EntityAlreadyExistsException
name|ex
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
name|handler
operator|.
name|put
argument_list|(
operator|new
name|UriRef
argument_list|(
name|id
argument_list|)
argument_list|,
name|MediaType
operator|.
name|valueOf
argument_list|(
name|contentType
argument_list|)
argument_list|,
name|content
argument_list|)
expr_stmt|;
name|ContentItem
name|contentItem
init|=
operator|new
name|ClerezzaContentItem
argument_list|(
operator|new
name|GraphNode
argument_list|(
name|uriRef
argument_list|,
name|cgProvider
operator|.
name|getContentGraph
argument_list|()
argument_list|)
argument_list|,
operator|new
name|SimpleMGraph
argument_list|(
name|metadataGraph
argument_list|)
argument_list|,
name|handler
argument_list|)
decl_stmt|;
return|return
name|contentItem
return|;
block|}
specifier|public
name|String
name|put
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
name|MGraph
name|metadataGraph
init|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|metadataGraph
operator|.
name|clear
argument_list|()
expr_stmt|;
name|metadataGraph
operator|.
name|addAll
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ci
operator|.
name|getId
argument_list|()
return|;
block|}
specifier|public
name|ContentItem
name|get
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|UriRef
name|uriRef
init|=
operator|new
name|UriRef
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|MGraph
name|metadataGraph
decl_stmt|;
try|try
block|{
name|metadataGraph
operator|=
name|tcManager
operator|.
name|getMGraph
argument_list|(
name|uriRef
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchEntityException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Is not a content item"
argument_list|)
throw|;
block|}
name|ContentItem
name|contentItem
init|=
operator|new
name|ClerezzaContentItem
argument_list|(
operator|new
name|GraphNode
argument_list|(
name|uriRef
argument_list|,
name|cgProvider
operator|.
name|getContentGraph
argument_list|()
argument_list|)
argument_list|,
name|metadataGraph
argument_list|,
name|handler
argument_list|)
decl_stmt|;
return|return
name|contentItem
return|;
block|}
specifier|public
name|MGraph
name|getEnhancementGraph
parameter_list|()
block|{
comment|// TODO: implement me: this should return an aggregate graph with all
comment|// the triples of all the content item of this store
return|return
operator|new
name|SimpleMGraph
argument_list|()
return|;
block|}
block|}
end_class

end_unit

