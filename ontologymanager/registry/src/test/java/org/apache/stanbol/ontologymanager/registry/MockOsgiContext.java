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
name|ontologymanager
operator|.
name|registry
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
name|access
operator|.
name|TcManager
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
name|serializedform
operator|.
name|Parser
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
name|serializedform
operator|.
name|Serializer
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
name|simple
operator|.
name|storage
operator|.
name|SimpleTcProvider
import|;
end_import

begin_class
specifier|public
class|class
name|MockOsgiContext
block|{
specifier|public
specifier|static
name|Parser
name|parser
init|=
name|Parser
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|TcManager
name|tcManager
decl_stmt|;
specifier|public
specifier|static
name|Serializer
name|serializer
init|=
name|Serializer
operator|.
name|getInstance
argument_list|()
decl_stmt|;
static|static
block|{
name|reset
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|reset
parameter_list|()
block|{
name|tcManager
operator|=
operator|new
name|TcManager
argument_list|()
expr_stmt|;
name|tcManager
operator|.
name|addWeightedTcProvider
argument_list|(
operator|new
name|SimpleTcProvider
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

