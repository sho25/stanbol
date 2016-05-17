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
name|kuromoji
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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|stanbol
operator|.
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
operator|.
name|DataFileProvider
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
comment|/** DataFileProvider that looks in our class resources */
end_comment

begin_class
specifier|public
class|class
name|ClasspathDataFileProvider
implements|implements
name|DataFileProvider
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
comment|/*      * NOTE: This path needs to be the same as path configured for the       *   'org.apache.stanbol:org.apache.stanbol.commons.solr.extras.gosen'      *   bundle      */
specifier|public
specifier|static
specifier|final
name|String
name|RESOURCE_BASE_PATH
init|=
literal|"datafiles/"
decl_stmt|;
specifier|private
specifier|final
name|String
name|symbolicName
decl_stmt|;
name|ClasspathDataFileProvider
parameter_list|(
name|String
name|bundleSymbolicName
parameter_list|)
block|{
name|symbolicName
operator|=
name|bundleSymbolicName
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getInputStream
parameter_list|(
name|String
name|bundleSymbolicName
parameter_list|,
name|String
name|filename
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|comments
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|URL
name|dataFile
init|=
name|getDataFile
argument_list|(
name|bundleSymbolicName
argument_list|,
name|filename
argument_list|)
decl_stmt|;
comment|// Returning null is fine - if we don't have the data file, another
comment|// provider might supply it
return|return
name|dataFile
operator|!=
literal|null
condition|?
name|dataFile
operator|.
name|openStream
argument_list|()
else|:
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAvailable
parameter_list|(
name|String
name|bundleSymbolicName
parameter_list|,
name|String
name|filename
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|comments
parameter_list|)
block|{
return|return
name|getDataFile
argument_list|(
name|bundleSymbolicName
argument_list|,
name|filename
argument_list|)
operator|!=
literal|null
return|;
block|}
comment|/**      * @param bundleSymbolicName      * @param filename      * @return      */
specifier|private
name|URL
name|getDataFile
parameter_list|(
name|String
name|bundleSymbolicName
parameter_list|,
name|String
name|filename
parameter_list|)
block|{
comment|//If the symbolic name is not null check that is equals to the symbolic
comment|//name used to create this classpath data file provider
if|if
condition|(
name|bundleSymbolicName
operator|!=
literal|null
operator|&&
operator|!
name|symbolicName
operator|.
name|equals
argument_list|(
name|bundleSymbolicName
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Requested bundleSymbolicName {} does not match mine ({}), request ignored"
argument_list|,
name|bundleSymbolicName
argument_list|,
name|symbolicName
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// load default OpenNLP models from classpath (embedded in the defaultdata bundle)
specifier|final
name|String
name|resourcePath
init|=
name|RESOURCE_BASE_PATH
operator|+
name|filename
decl_stmt|;
specifier|final
name|URL
name|dataFile
init|=
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
name|resourcePath
argument_list|)
decl_stmt|;
comment|//log.debug("RDFTerm {} found: {}", (in == null ? "NOT" : ""), resourcePath);
return|return
name|dataFile
return|;
block|}
block|}
end_class

end_unit

