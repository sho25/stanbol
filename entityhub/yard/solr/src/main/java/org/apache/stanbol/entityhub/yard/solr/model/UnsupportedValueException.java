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
name|yard
operator|.
name|solr
operator|.
name|model
package|;
end_package

begin_comment
comment|/**  * Thrown when a parsed object value can not be converted by the converter  *  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|UnsupportedValueException
extends|extends
name|RuntimeException
block|{
comment|/**      * default serial version UID      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/**      * Constructs the exception to be thrown if a converter does not support the      * the parsed value {@link IndexValue}.      * @param converter the converter (implement the {@link TypeConverter#toString()} method!)      * @param type the IndexDataType      * @param value the value      */
specifier|public
name|UnsupportedValueException
parameter_list|(
name|TypeConverter
argument_list|<
name|?
argument_list|>
name|converter
parameter_list|,
name|IndexDataType
name|type
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
argument_list|(
name|converter
argument_list|,
name|type
argument_list|,
name|value
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs the exception to be thrown if a converter does not support the      * the parsed value {@link IndexValue}.      * @param converter the converter (implement the {@link TypeConverter#toString()} method!)      * @param type the IndexDataType      * @param value the value      * @param cause the cause      */
specifier|public
name|UnsupportedValueException
parameter_list|(
name|TypeConverter
argument_list|<
name|?
argument_list|>
name|converter
parameter_list|,
name|IndexDataType
name|type
parameter_list|,
name|Object
name|value
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
literal|"%s does not support the parsed value %s! Value is not compatible with the parsed IndexDataType %s"
argument_list|,
name|converter
argument_list|,
name|value
argument_list|,
name|type
argument_list|)
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

