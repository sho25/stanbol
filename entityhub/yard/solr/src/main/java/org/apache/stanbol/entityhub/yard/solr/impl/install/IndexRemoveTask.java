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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|impl
operator|.
name|install
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|installer
operator|.
name|api
operator|.
name|tasks
operator|.
name|InstallTask
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|installer
operator|.
name|api
operator|.
name|tasks
operator|.
name|InstallationContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|sling
operator|.
name|installer
operator|.
name|api
operator|.
name|tasks
operator|.
name|TaskResourceGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|client
operator|.
name|solrj
operator|.
name|embedded
operator|.
name|EmbeddedSolrServer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|core
operator|.
name|CoreContainer
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
name|entityhub
operator|.
name|yard
operator|.
name|solr
operator|.
name|SolrDirectoryManager
import|;
end_import

begin_comment
comment|/**  * TODO: To remove a SolrIndex one would need first to close the SolrCore or shutdown the SolrContainer. This  * is currently not possible be cause the current architecture was not intended to support that.  *<p>  * To implement this one would need access to the CoreContainer with the core running on top of the Core to  * remove. Than one would need to call {@link CoreContainer#remove(String)} with the core and  * {@link CoreContainer#persist()} to remove the core also from the solr.xml. After that one can remove the  * files from the disk.  *<p>  * This would still have the problem that other components using an {@link EmbeddedSolrServer} that is based  * on this core would not be notified about such a change.  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
class|class
name|IndexRemoveTask
extends|extends
name|InstallTask
block|{
comment|/**      * Use 11 because the RemoveConfiguration uses 10 and we need to ensure that the files are removed after      * the services are shut down.      */
specifier|private
specifier|static
specifier|final
name|String
name|CONFIG_INSTALL_ORDER
init|=
literal|"11-"
decl_stmt|;
specifier|public
name|IndexRemoveTask
parameter_list|(
name|TaskResourceGroup
name|trg
parameter_list|,
name|SolrDirectoryManager
name|solrDirectoryManager
parameter_list|)
block|{
name|super
argument_list|(
name|trg
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|(
name|InstallationContext
name|ctx
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"TODO: Not yet implemented :("
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getSortKey
parameter_list|()
block|{
return|return
name|CONFIG_INSTALL_ORDER
operator|+
name|getResource
argument_list|()
operator|.
name|getEntityId
argument_list|()
return|;
block|}
block|}
end_class

end_unit

