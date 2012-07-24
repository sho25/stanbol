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
name|commons
operator|.
name|semanticindex
operator|.
name|index
package|;
end_package

begin_comment
comment|/**  * This enumeration defines the possible states for a {@link SemanticIndex}.  */
end_comment

begin_enum
specifier|public
enum|enum
name|IndexState
block|{
comment|/**      * The index was defined, the configuration is ok, but the contents are not yet indexed and the indexing      * has not yet started. (Intended to be used as default state after creations)      */
name|UNINIT
block|,
comment|/**      * The indexing of content items is currently in progress. This indicates that the index is currently NOT      * active.      */
name|INDEXING
block|,
comment|/**      * The semantic index is available and in sync      */
name|ACTIVE
block|,
comment|/**      * The (re)-indexing of content times is currently in progress. This indicates that the configuration of      * the semantic index was changed in a way that requires to rebuild the whole semantic index. This still      * requires the index to be active - meaning the searches can be performed normally - but recent      * updates/changes to ContentItems might not be reflected. This also indicates that the index will be      * replaced by a different version (maybe with changed fields) in the near future.      */
name|REINDEXING
block|;
specifier|private
specifier|static
specifier|final
name|String
name|prefix
init|=
literal|"http://stanbol.apache.org/ontology/contenthub#indexState_"
decl_stmt|;
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|prefix
operator|+
name|name
argument_list|()
operator|.
name|toLowerCase
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getUri
argument_list|()
return|;
block|}
block|}
end_enum

end_unit

