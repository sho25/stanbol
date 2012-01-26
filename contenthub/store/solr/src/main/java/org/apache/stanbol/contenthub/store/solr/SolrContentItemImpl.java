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
name|store
operator|.
name|solr
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
name|contenthub
operator|.
name|store
operator|.
name|solr
operator|.
name|util
operator|.
name|ContentItemIDOrganizer
operator|.
name|CONTENT_ITEM_URI_PREFIX
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
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|store
operator|.
name|solr
operator|.
name|SolrContentItem
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
name|store
operator|.
name|solr
operator|.
name|util
operator|.
name|ContentItemIDOrganizer
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
name|helper
operator|.
name|ContentItemImpl
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
name|InMemoryBlob
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
comment|/**  *   * @author meric  * @author anil.sinaci  *   */
end_comment

begin_class
specifier|public
class|class
name|SolrContentItemImpl
extends|extends
name|ContentItemImpl
implements|implements
name|SolrContentItem
block|{
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
name|SolrContentItem
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * TODO: sync access to {@link #constraints} using}      */
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|constraints
decl_stmt|;
specifier|private
name|String
name|title
decl_stmt|;
specifier|public
name|SolrContentItemImpl
parameter_list|(
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimetype
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|content
argument_list|,
name|mimetype
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SolrContentItemImpl
parameter_list|(
name|String
name|id
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
name|content
argument_list|,
name|mimeType
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SolrContentItemImpl
parameter_list|(
name|String
name|id
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|,
name|MGraph
name|metadata
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|constraints
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
literal|""
argument_list|,
name|content
argument_list|,
name|mimeType
argument_list|,
name|metadata
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SolrContentItemImpl
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|title
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|,
name|MGraph
name|metadata
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|constraints
parameter_list|)
block|{
name|super
argument_list|(
name|id
operator|==
literal|null
condition|?
name|ContentItemHelper
operator|.
name|makeDefaultUri
argument_list|(
name|CONTENT_ITEM_URI_PREFIX
argument_list|,
name|content
argument_list|)
else|:
operator|new
name|UriRef
argument_list|(
name|ContentItemIDOrganizer
operator|.
name|attachBaseURI
argument_list|(
name|id
argument_list|)
argument_list|)
argument_list|,
operator|new
name|InMemoryBlob
argument_list|(
name|content
argument_list|,
name|mimeType
argument_list|)
argument_list|,
name|metadata
operator|==
literal|null
condition|?
operator|new
name|SimpleMGraph
argument_list|()
else|:
name|metadata
argument_list|)
expr_stmt|;
if|if
condition|(
name|metadata
operator|==
literal|null
condition|)
block|{
name|metadata
operator|=
operator|new
name|SimpleMGraph
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|constraints
operator|==
literal|null
condition|)
block|{
name|constraints
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|title
operator|=
name|title
expr_stmt|;
name|this
operator|.
name|constraints
operator|=
name|constraints
expr_stmt|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Object
argument_list|>
argument_list|>
name|getConstraints
parameter_list|()
block|{
comment|// TODO: sync access to constraints via #readLock and #writeLocck
return|return
name|constraints
return|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
if|if
condition|(
name|title
operator|!=
literal|null
operator|&&
operator|!
name|title
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|title
return|;
block|}
return|return
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

