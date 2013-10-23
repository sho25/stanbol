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
comment|/**  * Defines a natural language text in a given language.<p>  * The text MUST NOT be<code>null</code> nor an empty {@link String}. The  * language may be<code>null</code> (indicating the default language) or any  * kind of value. It is recommended to use ISO 639-1 codes (two Letter codes).  * By definition it is also allowed to use empty strings as language. However  * implementations of this interface are free to convert the empty language to  *<code>null</code>.<p>  * Implementations of that interface MUST BE immutable  *   * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|Text
block|{
comment|/**      * Getter for the text (not<code>null</code> nor empty)      * @return the text      */
name|String
name|getText
parameter_list|()
function_decl|;
comment|/**      * Getter for the language.<code>null</code> or an empty string indicate,       * that the text is not specific to a language (e.g. the name of a person).      * Note that implementation can change empty values to<code>null</code> but.      * @return the language      */
name|String
name|getLanguage
parameter_list|()
function_decl|;
comment|/**      * The text without language information - this is the same as returned      * by {@link #getText()}.      * @return the text      */
name|String
name|toString
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

