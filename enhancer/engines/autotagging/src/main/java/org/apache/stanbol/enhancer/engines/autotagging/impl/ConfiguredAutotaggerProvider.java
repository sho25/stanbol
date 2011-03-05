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
name|engines
operator|.
name|autotagging
operator|.
name|impl
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
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|FileUtils
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
name|lucene
operator|.
name|store
operator|.
name|FSDirectory
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
name|autotagging
operator|.
name|Autotagger
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
name|autotagging
operator|.
name|jena
operator|.
name|ModelIndexer
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
name|engines
operator|.
name|autotagging
operator|.
name|AutotaggerProvider
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
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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
comment|/**  * Simple OSGi component to look up a configured autotagger instance.  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|ConfiguredAutotaggerProvider
implements|implements
name|AutotaggerProvider
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
name|ConfiguredAutotaggerProvider
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|""
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|LUCENE_INDEX_PATH
init|=
literal|"org.apache.stanbol.enhancer.engines.autotagging.indexPath"
decl_stmt|;
specifier|private
name|Autotagger
name|autotagger
decl_stmt|;
specifier|protected
name|BundleContext
name|bundleContext
decl_stmt|;
specifier|public
name|Autotagger
name|getAutotagger
parameter_list|()
block|{
return|return
name|autotagger
return|;
block|}
specifier|public
name|BundleContext
name|getBundleContext
parameter_list|()
block|{
return|return
name|bundleContext
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
throws|throws
name|IOException
block|{
name|bundleContext
operator|=
name|ce
operator|.
name|getBundleContext
argument_list|()
expr_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|properties
init|=
name|ce
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|String
name|directoryPath
init|=
name|properties
operator|.
name|get
argument_list|(
name|LUCENE_INDEX_PATH
argument_list|)
decl_stmt|;
if|if
condition|(
name|directoryPath
operator|==
literal|null
operator|||
name|directoryPath
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|//TODO: replace naming with stanbol enhancer
name|File
name|dataFolder
init|=
name|bundleContext
operator|.
name|getDataFile
argument_list|(
literal|"enhancer-engines-autotagging"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|dataFolder
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|FileUtils
operator|.
name|deleteQuietly
argument_list|(
name|dataFolder
argument_list|)
expr_stmt|;
name|dataFolder
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
block|}
name|directoryPath
operator|=
name|ModelIndexer
operator|.
name|buildDefaultIndex
argument_list|(
name|dataFolder
argument_list|,
literal|false
argument_list|)
operator|.
name|getAbsolutePath
argument_list|()
expr_stmt|;
block|}
name|File
name|directory
init|=
operator|new
name|File
argument_list|(
name|directoryPath
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|directory
operator|.
name|exists
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Lucene index directory not found: "
operator|+
name|directory
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
throw|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"Loading the autotagging index from {}"
argument_list|,
name|directoryPath
argument_list|)
expr_stmt|;
name|autotagger
operator|=
operator|new
name|Autotagger
argument_list|(
name|FSDirectory
operator|.
name|open
argument_list|(
name|directory
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ce
parameter_list|)
block|{
name|autotagger
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

