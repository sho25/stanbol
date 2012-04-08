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
name|clerezza
package|;
end_package

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
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|impl
operator|.
name|ContentItemImpl
import|;
end_import

begin_comment
comment|/**  *  * @author andreas  */
end_comment

begin_class
specifier|public
class|class
name|ClerezzaContentItem
extends|extends
name|ContentItemImpl
block|{
specifier|public
name|ClerezzaContentItem
parameter_list|(
name|GraphNode
name|idNode
parameter_list|,
name|MGraph
name|metadataGraph
parameter_list|,
name|DiscobitsHandler
name|handler
parameter_list|)
block|{
name|super
argument_list|(
operator|(
name|UriRef
operator|)
name|idNode
operator|.
name|getNode
argument_list|()
argument_list|,
operator|new
name|ClerezzaBlob
argument_list|(
name|handler
argument_list|,
name|idNode
argument_list|)
argument_list|,
name|metadataGraph
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

