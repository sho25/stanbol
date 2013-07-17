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
name|nlp
operator|.
name|json
operator|.
name|valuetype
package|;
end_package

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|node
operator|.
name|ObjectNode
import|;
end_import

begin_comment
comment|/**  * Interface allowing to extend how Classes used as generic type for  * {@link org.apache.stanbol.enhancer.nlp.model.annotation.Value}s are parsed from JSON  *<p>  * Implementation MUST register itself as OSGI services AND also provide  * a<code>META-INF/services/org.apache.stanbol.enhancer.nlp.json.valuetype.ValueTypeParser</code>   * file required for the {@link java.util.ServiceLoader} utility.   * @param<T>  */
end_comment

begin_interface
specifier|public
interface|interface
name|ValueTypeParser
parameter_list|<
name|T
parameter_list|>
block|{
name|String
name|PROPERTY_TYPE
init|=
literal|"type"
decl_stmt|;
name|Class
argument_list|<
name|T
argument_list|>
name|getType
parameter_list|()
function_decl|;
name|T
name|parse
parameter_list|(
name|ObjectNode
name|jAnnotation
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

