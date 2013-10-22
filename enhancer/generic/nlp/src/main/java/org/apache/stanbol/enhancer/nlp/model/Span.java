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
name|model
package|;
end_package

begin_import
import|import
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
name|model
operator|.
name|annotation
operator|.
name|Annotated
import|;
end_import

begin_comment
comment|/**  * Represents a {@link #getSpan() span} [{@link #getStart() start},  * {@link #getEnd() end}] within the {@link #getContext() text}. Spans also have  * an assigned {@link #getType() type}. Possible types are defined within the  * {@link SpanTypeEnum}.<p>  * This is an meta (abstract) type. Implementations of this Interface   * SHOULD BE abstract Classes.  */
end_comment

begin_interface
specifier|public
interface|interface
name|Span
extends|extends
name|Annotated
extends|,
name|Comparable
argument_list|<
name|Span
argument_list|>
block|{
comment|/**      * The type of the Span      * @return      */
name|SpanTypeEnum
name|getType
parameter_list|()
function_decl|;
comment|/**      * The start index of this span This is the absolute offset from the      * {@link #getContext()}{@link AnalysedText#getText() .getText()}      */
name|int
name|getStart
parameter_list|()
function_decl|;
comment|/**      * The end index of this span. This is the absolute offset from the      * {@link #getContext()}{@link AnalysedText#getText() .getText()}      * @return      */
name|int
name|getEnd
parameter_list|()
function_decl|;
comment|/**      * The {@link AnalysedText} this Span was added to.      * @return the AnalysedText representing the context of this Span      */
name|AnalysedText
name|getContext
parameter_list|()
function_decl|;
comment|/**      * The section of the text selected by this span      * @return the selected section of the text      */
name|String
name|getSpan
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

