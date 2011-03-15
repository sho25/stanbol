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
comment|/**  * The Converter Interface used by the {@link IndexValueFactory}.  * @author Rupert Westenthaler  *  * @param<T> the generic type of the java-object that can be converted to  * {@link IndexValue}s.  */
end_comment

begin_interface
specifier|public
interface|interface
name|TypeConverter
parameter_list|<
name|T
parameter_list|>
block|{
comment|/**      * Converts the parsed java instance to an index value      * @param value the java instance      * @return the index value representing the parsed java instance      */
name|IndexValue
name|createIndexValue
parameter_list|(
name|T
name|value
parameter_list|)
function_decl|;
comment|/**      * Creates an java instance representing the parsed<code>IndexValue</code>      * @param value the index value      * @return the java instance representing the parsed index value      * @throws if the<code>IndexType</code> of the parsed value is not compatible      * with this converter.      */
name|T
name|createObject
parameter_list|(
name|IndexValue
name|value
parameter_list|)
throws|throws
name|UnsupportedIndexTypeException
throws|,
name|UnsupportedValueException
function_decl|;
comment|/**      * Creates an java instance representing the parsed value as returned by the      * index.      * @param type the index data type of the value. MUST NOT be<code>null</code>      * @param value the value within the index. If<code>null</code> this method       * returns<code>null</code>.      * @param lang the language      * @return the java instance representing the parsed index value      * @throws UnsupportedValueException if the value can not be processed by the      * Converter      * @throws NullPointerException of the parsed {@link IndexDataType} is       *<code>null</code>      */
name|T
name|createObject
parameter_list|(
name|IndexDataType
name|type
parameter_list|,
name|Object
name|value
parameter_list|,
name|String
name|lang
parameter_list|)
throws|throws
name|UnsupportedIndexTypeException
throws|,
name|UnsupportedValueException
throws|,
name|NullPointerException
function_decl|;
comment|/**      * Getter for the java type      * @return the java class of the instances created by this converter      */
name|Class
argument_list|<
name|T
argument_list|>
name|getJavaType
parameter_list|()
function_decl|;
comment|/**      * Getter for the index type      * @return the index type of index values created by this converter      */
name|IndexDataType
name|getIndexType
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

