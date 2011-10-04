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
name|search
operator|.
name|engine
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * This interface provides search engine specific properties.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|EngineProperties
block|{
comment|/**      * The name of the property indicating the processing order of the {@link SearchEngine}, in case there are      * multiple number of {@link SearchEngine}s.      */
name|String
name|PROCESSING_ORDER
init|=
literal|"org.apache.stanbol.search.servicesapi.engine.processing_order"
decl_stmt|;
comment|/**      * Possible value of the property indicating the processing order of the {@link SearchEngine}, in case      * there are multiple number of {@link SearchEngine}s.      */
name|Integer
name|PROCESSING_POST
init|=
literal|200
decl_stmt|;
comment|/**      * Possible value of the property indicating the processing order of the {@link SearchEngine}, in case      * there are multiple number of {@link SearchEngine}s.      */
name|Integer
name|PROCESSING_DEFAULT
init|=
literal|100
decl_stmt|;
comment|/**      * Possible value of the property indicating the processing order of the {@link SearchEngine}, in case      * there are multiple number of {@link SearchEngine}s.      */
name|Integer
name|PROCESSING_PRE
init|=
literal|0
decl_stmt|;
comment|/**      * Retrieves all properties of a {@link SearchEngine}.      *       * @return Map of key:value pairs      */
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getEngineProperties
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

