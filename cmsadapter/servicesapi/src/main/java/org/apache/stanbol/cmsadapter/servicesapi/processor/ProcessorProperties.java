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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|processor
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
comment|/**  * Implementors of this Interface can override their default processing order.  * @author cihan  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ProcessorProperties
block|{
name|String
name|PROCESSING_ORDER
init|=
literal|"org.apache.stanbol.cmsadapter.servicesapi.processor.processing_order"
decl_stmt|;
name|Integer
name|OBJECT_TYPE
init|=
literal|0
decl_stmt|;
name|Integer
name|CMSOBJECT_POST
init|=
literal|30
decl_stmt|;
name|Integer
name|CMSOBJECT_DEFAULT
init|=
literal|20
decl_stmt|;
name|Integer
name|CMSOBJECT_PRE
init|=
literal|10
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getProcessorProperties
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

