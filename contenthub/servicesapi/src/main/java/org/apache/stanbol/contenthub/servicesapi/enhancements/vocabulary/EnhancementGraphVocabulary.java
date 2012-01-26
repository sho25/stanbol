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
name|contenthub
operator|.
name|servicesapi
operator|.
name|enhancements
operator|.
name|vocabulary
package|;
end_package

begin_comment
comment|/**  * All enhancements gathered from all submitted documents are hold inside a single graph, called Enhancement  * Graph. This class provides specific endpoints and field names about the Enhancement Graph.  *   * @author anil.sinaci  *   */
end_comment

begin_class
specifier|public
class|class
name|EnhancementGraphVocabulary
block|{
comment|/**      * The URI of the global enhancement graph. All enhancements are stored in this graph.      */
specifier|public
specifier|static
specifier|final
name|String
name|ENHANCEMENTS_GRAPH_URI
init|=
literal|"org.apache.stanbol.enhancer.standalone.store.enhancements"
decl_stmt|;
block|}
end_class

end_unit

