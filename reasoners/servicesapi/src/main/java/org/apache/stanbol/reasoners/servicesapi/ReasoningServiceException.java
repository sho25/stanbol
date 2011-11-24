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
name|reasoners
operator|.
name|servicesapi
package|;
end_package

begin_comment
comment|/**  * Generic exception  *   * @author enridaga  *  */
end_comment

begin_class
specifier|public
class|class
name|ReasoningServiceException
extends|extends
name|Exception
block|{
specifier|public
name|ReasoningServiceException
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|super
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ReasoningServiceException
parameter_list|()
block|{     }
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|6196336593868459278L
decl_stmt|;
block|}
end_class

end_unit

