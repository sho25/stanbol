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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
package|;
end_package

begin_comment
comment|/**  * Indicates, that the requested type is not supported.<p>  * The definition of the model requires some types to be supported.  * Implementation may support additional types. Components that use a specific  * implementation may therefore use types that are not required to be supported.  * However such components should also be able to deal with this kind of  * exceptions.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|UnsupportedTypeException
extends|extends
name|IllegalArgumentException
block|{
comment|/**      * uses the default serialVersionUID      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|UnsupportedTypeException
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|String
name|dataType
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
name|dataType
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|UnsupportedTypeException
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|String
name|dataType
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Values of Type \"%s\" are not supported for data type \"%s\""
argument_list|,
name|type
argument_list|,
name|dataType
argument_list|)
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

