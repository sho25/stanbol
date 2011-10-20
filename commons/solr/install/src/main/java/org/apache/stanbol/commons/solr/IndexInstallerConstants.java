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
name|solr
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|solr
operator|.
name|install
operator|.
name|impl
operator|.
name|SolrIndexInstaller
import|;
end_import

begin_comment
comment|/**  * Constants and static configuration used by the {@link SolrIndexInstaller}  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|IndexInstallerConstants
block|{
specifier|private
name|IndexInstallerConstants
parameter_list|()
block|{
comment|/* do not create instances */
block|}
comment|/**      * The schema used for transformed resources.      */
specifier|public
specifier|static
specifier|final
name|String
name|SOLR_INDEX_ARCHIVE_RESOURCE_TYPE
init|=
literal|"solrarchive"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PROPERTY_PREFIX
init|=
literal|"org.apache.stanbol.yard.solr.installer."
decl_stmt|;
comment|/**      * The key used to configure the name of the Index-Archive The default name is      * \"&lt;indexName&gt;.solrarchive\".      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_INDEX_ARCHIVE
init|=
literal|"Index-Archive"
decl_stmt|;
comment|/**      * The key used for the name of the index      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_INDEX_NAME
init|=
name|PROPERTY_PREFIX
operator|+
literal|"index.name"
decl_stmt|;
comment|/**      * The key used for the type of the archive      */
specifier|public
specifier|static
specifier|final
name|String
name|PROPERTY_ARCHIVE_FORMAT
init|=
name|PROPERTY_PREFIX
operator|+
literal|"archive.format"
decl_stmt|;
block|}
end_class

end_unit

