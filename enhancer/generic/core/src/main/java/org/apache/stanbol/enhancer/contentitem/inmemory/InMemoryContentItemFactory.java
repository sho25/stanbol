begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|contentitem
operator|.
name|inmemory
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
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
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|Blob
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
name|ContentItemFactory
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
name|ContentSink
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
name|ContentSource
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
name|helper
operator|.
name|ContentItemHelper
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
name|impl
operator|.
name|AbstractContentItemFactory
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
name|Constants
import|;
end_import

begin_class
annotation|@
name|Component
argument_list|(
name|inherit
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|ContentItemFactory
operator|.
name|class
argument_list|)
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
comment|//set service ranking to an positive integer so that others do not accitently
comment|//override the default
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
literal|100
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|InMemoryContentItemFactory
extends|extends
name|AbstractContentItemFactory
implements|implements
name|ContentItemFactory
block|{
specifier|private
specifier|static
name|InMemoryContentItemFactory
name|instance
decl_stmt|;
comment|/**      * Getter for the singleton instance of this factory. Within an OSGI       * environment this should not be used as this Factory is also registered      * as OSGI service.      * @return the singleton instance      */
specifier|public
specifier|static
name|InMemoryContentItemFactory
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
block|{
name|instance
operator|=
operator|new
name|InMemoryContentItemFactory
argument_list|()
expr_stmt|;
block|}
return|return
name|instance
return|;
block|}
specifier|public
name|InMemoryContentItemFactory
parameter_list|()
block|{
name|super
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//lazy initialisation makes a lot of sense for in-memory implementations
block|}
annotation|@
name|Override
specifier|protected
name|ContentItem
name|createContentItem
parameter_list|(
name|UriRef
name|id
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
block|{
return|return
operator|new
name|InMemoryContentItem
argument_list|(
name|id
argument_list|,
name|blob
argument_list|,
name|metadata
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|ContentItem
name|createContentItem
parameter_list|(
name|String
name|prefix
parameter_list|,
name|Blob
name|blob
parameter_list|,
name|MGraph
name|metadata
parameter_list|)
block|{
return|return
operator|new
name|InMemoryContentItem
argument_list|(
name|ContentItemHelper
operator|.
name|makeDefaultUri
argument_list|(
name|prefix
argument_list|,
name|blob
argument_list|)
argument_list|,
name|blob
argument_list|,
name|metadata
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Blob
name|createBlob
parameter_list|(
name|ContentSource
name|source
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|source
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentSource MUST NOT be NULL!"
argument_list|)
throw|;
block|}
comment|//use source.getData to avoid making copies of byte arrays
return|return
operator|new
name|InMemoryBlob
argument_list|(
name|source
operator|.
name|getData
argument_list|()
argument_list|,
name|source
operator|.
name|getMediaType
argument_list|()
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|ContentSink
name|createContentSink
parameter_list|(
name|String
name|mediaType
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|InMemoryContentSink
argument_list|(
name|mediaType
argument_list|)
return|;
block|}
specifier|protected
specifier|static
class|class
name|InMemoryContentSink
implements|implements
name|ContentSink
block|{
specifier|private
specifier|final
name|ByteArrayOutputStream
name|out
decl_stmt|;
specifier|private
specifier|final
name|InMemoryBlob
name|blob
decl_stmt|;
specifier|protected
name|InMemoryContentSink
parameter_list|(
name|String
name|mt
parameter_list|)
block|{
name|out
operator|=
operator|new
name|ByteArrayOutputStream
argument_list|()
expr_stmt|;
name|blob
operator|=
operator|new
name|InMemoryBlob
argument_list|(
name|out
argument_list|,
name|mt
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|OutputStream
name|getOutputStream
parameter_list|()
block|{
return|return
name|out
return|;
block|}
annotation|@
name|Override
specifier|public
name|Blob
name|getBlob
parameter_list|()
block|{
return|return
name|blob
return|;
block|}
block|}
block|}
end_class

end_unit
