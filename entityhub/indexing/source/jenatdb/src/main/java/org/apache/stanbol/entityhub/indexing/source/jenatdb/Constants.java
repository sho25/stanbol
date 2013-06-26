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
name|source
operator|.
name|jenatdb
package|;
end_package

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
name|tdb
operator|.
name|store
operator|.
name|DatasetGraphTDB
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|Constants
block|{
specifier|private
name|Constants
parameter_list|()
block|{}
comment|/**      * The default name of the folder used to initialise the       * {@link DatasetGraphTDB Jena TDB dataset}.      */
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_MODEL_DIRECTORY
init|=
literal|"tdb"
decl_stmt|;
comment|/**      * Parameter used to configure the name of the directory used to store the      * RDF model (a Jena TDB dataset). The default name is      * {@link #DEFAULT_MODEL_DIRECTORY}      */
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_MODEL_DIRECTORY
init|=
literal|"model"
decl_stmt|;
block|}
end_class

end_unit

