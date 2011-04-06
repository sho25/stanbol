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
name|opennlp
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
name|String
name|downloadExplanation
parameter_list|)
throws|throws
name|IOException
block|{
comment|// load default OpenNLP models from classpath (embedded in the defaultdata bundle)
specifier|final
name|String
name|resourcePath
init|=
literal|"org/apache/stanbol/defaultdata/opennlp/"
operator|+
name|filename
decl_stmt|;
specifier|final
name|InputStream
name|in
init|=
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|resourcePath
argument_list|)
decl_stmt|;
if|if
condition|(
name|in
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Resource not found in my classpath: "
operator|+
name|resourcePath
argument_list|)
throw|;
block|}
return|return
name|in
return|;
block|}
block|}
end_class

end_unit

