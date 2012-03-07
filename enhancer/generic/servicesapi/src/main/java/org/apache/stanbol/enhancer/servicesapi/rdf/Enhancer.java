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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
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
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_class
specifier|public
class|class
name|Enhancer
block|{
specifier|public
specifier|static
specifier|final
name|UriRef
name|ENHANCEMENT_ENGINE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|enhancer
operator|+
literal|"EnhancementEngine"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|ENHANCEMENT_CHAIN
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|enhancer
operator|+
literal|"EnhancementChain"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|ENHANCER
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|enhancer
operator|+
literal|"Enhancer"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_ENGINE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|enhancer
operator|+
literal|"hasEngine"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_CHAIN
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|enhancer
operator|+
literal|"hasChain"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_DEFAULT_CHAIN
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|enhancer
operator|+
literal|"hasDefaultChain"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit
