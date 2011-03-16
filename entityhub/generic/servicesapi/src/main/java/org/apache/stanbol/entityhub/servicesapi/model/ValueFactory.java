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

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Sign
operator|.
name|SignTypeEnum
import|;
end_import

begin_comment
comment|/**  * FactoryInterface for {@link Text} and {@link Reference} instances  * TODO: Still not sure if we need that  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|ValueFactory
block|{
comment|/**      * Creates a Text instance without an language      * @param value The value if the text. Implementations might support special      * support for specific classes. As an default the {@link Object#toString()}      * method is used to get the lexical form of the text from the parsed value      * and<code>null</code> should be used as language.      * @return the Text instance for the parsed object      * @throws UnsupportedTypeException if the type of the parsed object is not      * can not be used to create Text instances      * @throws IllegalArgumentException If<code>null</code> is parsed or if the       * parsed instance type is supported, but      * the parsed instance can not be used to create a text instance      */
name|Text
name|createText
parameter_list|(
name|Object
name|value
parameter_list|)
throws|throws
name|UnsupportedTypeException
throws|,
name|IllegalArgumentException
function_decl|;
comment|/**      * Creates a Text instance for a language      * @param text the text      * @param language the language or<code>null</code>.      * @return the Text instance      * @throws IllegalArgumentException if<code>null</code> is parsed as text or      * if the parsed parameter can not be used to create a Text instance      */
name|Text
name|createText
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|language
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
comment|/**      * Creates a reference instance for the parsed value. Implementations might      * support special support for specific classes. As an default the      * {@link Object#toString()} method is used to get the unicode representation      * of the reference.<p>      * Implementation MUST support at least the following types:<ul>      *<li> {@link String}: The parsed string need not be be checked for a valid      *      IRI, URI or URL in any form. However in case of an empty String a       *      {@link IllegalArgumentException} MUST BE thrown)      *<li> {@link URI}: Any valid URI MUST BE accepted      *<li> {@link URL}: any valid URL MUST BE accepted      *</ul>      * @param value the unicode representation of the reference      * @return the reference instance      * @throws UnsupportedTypeException if the type of the parsed object can      * not be converted to a Reference.      * @throws IllegalArgumentException if the parsed value is<code>null</code>       * or if the parsed value can not be used to      * create a valid Reference (e.g. when parsing an empty String)      */
name|Reference
name|createReference
parameter_list|(
name|Object
name|value
parameter_list|)
throws|throws
name|UnsupportedTypeException
throws|,
name|IllegalArgumentException
function_decl|;
comment|/**      * Creates an empty representation instance of with the type {@link SignTypeEnum#Sign}      * for the parsed ID. The id MUST not be<code>null</code> nor empty      * @param id The id of the representation      * @return the representation      * @throws IllegalArgumentException if<code>null</code> or an empty string       * is parsed as ID      */
name|Representation
name|createRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
comment|//    /**
comment|//     * Creates a value of the parsed data type for the parsed object
comment|//     * @param dataTypeUri the data type of the created object
comment|//     * @param value the source object
comment|//     * @return the value or<code>null</code> if the parsed value can not be
comment|//     * converted.
comment|//     * @throws UnsupportedTypeException if the type of the parsed object is
comment|//     * not compatible to the requested dataType
comment|//     * @throws UnsupportedDataTypeException Implementation need to ensure support
comment|//     * for data types specified in {@link DataTypeEnum}. However implementations
comment|//     * may support additional data types. When using such types one needs to
comment|//     * check for this Exception.
comment|//     */
comment|//    public Object createValue(String dataTypeUri,Object value) throws UnsupportedTypeException, UnsupportedDataTypeException;
block|}
end_interface

end_unit

