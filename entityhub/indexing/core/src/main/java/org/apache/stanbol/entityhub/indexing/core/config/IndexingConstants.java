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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|config
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_comment
comment|/**  * Constants defines/used for Indexing.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|IndexingConstants
block|{
name|String
name|KEY_NAME
init|=
literal|"name"
decl_stmt|;
name|String
name|KEY_DESCRIPTION
init|=
literal|"description"
decl_stmt|;
name|String
name|KEY_ENTITY_DATA_ITERABLE
init|=
literal|"entityDataIterable"
decl_stmt|;
name|String
name|KEY_ENTITY_DATA_PROVIDER
init|=
literal|"entityDataProvider"
decl_stmt|;
name|String
name|KEY_ENTITY_ID_ITERATOR
init|=
literal|"entityIdIterator"
decl_stmt|;
name|String
name|KEY_ENTITY_SCORE_PROVIDER
init|=
literal|"entityScoreProvider"
decl_stmt|;
name|String
name|KEY_INDEXING_DESTINATION
init|=
literal|"indexingDestination"
decl_stmt|;
name|String
name|KEY_INDEX_FIELD_CONFIG
init|=
literal|"fieldConfiguration"
decl_stmt|;
comment|/**      * Name of the file relative to the destination folder used to store      * the IDs of indexed Entities.      */
name|String
name|KEX_INDEXED_ENTITIES_FILE
init|=
literal|"indexedEntitiesFile"
decl_stmt|;
comment|/**      * usage:<br>      *<pre>      * {class1},name:{name1};{class2},name:{name2};...      *</pre>      * The class implementing the normaliser and the name of the configuration      * file stored within /config/normaliser/{name}.properties      */
name|String
name|KEY_SCORE_NORMALIZER
init|=
literal|"scoreNormalizer"
decl_stmt|;
name|String
name|KEY_ENTITY_PROCESSOR
init|=
literal|"entityProcessor"
decl_stmt|;
name|String
name|KEY_ENTITY_POST_PROCESSOR
init|=
literal|"entityPostProcessor"
decl_stmt|;
name|String
name|KEY_FAIL_ON_ERROR_LOADING_RESOURCE
init|=
literal|"failOnErrorLoadingResource"
decl_stmt|;
block|}
end_interface

end_unit

