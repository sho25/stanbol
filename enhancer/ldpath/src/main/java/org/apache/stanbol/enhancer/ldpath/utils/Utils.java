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
name|ldpath
operator|.
name|utils
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|ldpath
operator|.
name|EnhancerLDPath
operator|.
name|getConfig
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Resource
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
name|impl
operator|.
name|SimpleMGraph
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
name|ldpath
operator|.
name|clerezza
operator|.
name|ClerezzaBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|selectors
operator|.
name|NodeSelector
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|parser
operator|.
name|ParseException
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|parser
operator|.
name|RdfPathParser
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|Utils
block|{
specifier|private
name|Utils
parameter_list|()
block|{}
empty_stmt|;
specifier|public
specifier|static
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|EMPTY_BACKEND
decl_stmt|;
comment|/**      * Returns an empty {@link RDFBackend} instance intended to be used to create      * {@link RdfPathParser} instances<p>      * {@link RDFBackend} has currently two distinct roles<ol>      *<li> to traverse the graph ( basically the       * {@link RDFBackend#listObjects(Object, Object)} and      * {@link RDFBackend#listSubjects(Object, Object)} methods)      *<li> to create Nodes and convert Nodes      *</ol>      * The {@link RdfPathParser} while requiring an {@link RDFBackend} instance      * depends only on the 2nd role. Therefore the data managed by the      * {@link RDFBackend} instance are of no importance.<p>      * The {@link RDFBackend} provided by this constant is intended to be only      * used for the 2nd purpose and does contain no information!      *<li>      */
specifier|public
specifier|static
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
name|getEmptyBackend
parameter_list|()
block|{
if|if
condition|(
name|EMPTY_BACKEND
operator|==
literal|null
condition|)
block|{
name|EMPTY_BACKEND
operator|=
operator|new
name|ClerezzaBackend
argument_list|(
operator|new
name|SimpleMGraph
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|EMPTY_BACKEND
return|;
block|}
specifier|public
specifier|static
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|parseSelector
parameter_list|(
name|String
name|path
parameter_list|)
throws|throws
name|ParseException
block|{
return|return
name|parseSelector
argument_list|(
name|path
argument_list|,
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
operator|)
literal|null
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|NodeSelector
argument_list|<
name|Resource
argument_list|>
name|parseSelector
parameter_list|(
name|String
name|path
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|additionalNamespaceMappings
parameter_list|)
throws|throws
name|ParseException
block|{
name|RdfPathParser
argument_list|<
name|Resource
argument_list|>
name|parser
init|=
operator|new
name|RdfPathParser
argument_list|<
name|Resource
argument_list|>
argument_list|(
name|getEmptyBackend
argument_list|()
argument_list|,
name|getConfig
argument_list|()
argument_list|,
operator|new
name|StringReader
argument_list|(
name|path
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|parser
operator|.
name|parseSelector
argument_list|(
name|additionalNamespaceMappings
argument_list|)
return|;
block|}
block|}
end_class

end_unit
