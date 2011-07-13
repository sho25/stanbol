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
name|helper
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Proxy
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
name|LiteralFactory
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
name|MGraph
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
name|NonLiteral
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
name|servicesapi
operator|.
name|helper
operator|.
name|RdfEntity
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
name|servicesapi
operator|.
name|helper
operator|.
name|RdfEntityFactory
import|;
end_import

begin_class
specifier|public
class|class
name|SimpleRdfEntityFactory
extends|extends
name|RdfEntityFactory
block|{
specifier|private
specifier|final
name|MGraph
name|graph
decl_stmt|;
specifier|private
specifier|final
name|LiteralFactory
name|literalFactory
decl_stmt|;
specifier|public
name|SimpleRdfEntityFactory
parameter_list|(
name|MGraph
name|graph
parameter_list|)
block|{
if|if
condition|(
name|graph
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The MGraph parsed as parameter MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|graph
operator|=
name|graph
expr_stmt|;
name|literalFactory
operator|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
parameter_list|<
name|T
extends|extends
name|RdfEntity
parameter_list|>
name|T
name|getProxy
parameter_list|(
name|NonLiteral
name|rdfNode
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
modifier|...
name|additionalInterfaces
parameter_list|)
block|{
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|interfaces
init|=
operator|new
name|Class
argument_list|<
name|?
argument_list|>
index|[
name|additionalInterfaces
operator|.
name|length
operator|+
literal|1
index|]
decl_stmt|;
name|interfaces
index|[
literal|0
index|]
operator|=
name|type
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|additionalInterfaces
argument_list|,
literal|0
argument_list|,
name|interfaces
argument_list|,
literal|1
argument_list|,
name|additionalInterfaces
operator|.
name|length
argument_list|)
expr_stmt|;
comment|//Class<?> proxy = Proxy.getProxyClass(WrapperFactory.class.getClassLoader(), interfaces);
name|Object
name|instance
init|=
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|SimpleRdfEntityFactory
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|,
name|interfaces
argument_list|,
operator|new
name|RdfProxyInvocationHandler
argument_list|(
name|this
argument_list|,
name|rdfNode
argument_list|,
name|interfaces
argument_list|,
name|literalFactory
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|(
name|T
operator|)
name|instance
return|;
block|}
specifier|protected
name|MGraph
name|getGraph
parameter_list|()
block|{
return|return
name|graph
return|;
block|}
block|}
end_class

end_unit

