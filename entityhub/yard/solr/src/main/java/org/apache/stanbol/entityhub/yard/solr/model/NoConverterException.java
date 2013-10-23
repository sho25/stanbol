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

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
import|;
end_import

begin_comment
comment|/**  * This exception is thrown when no adapter is available to do a required java-object to {@link IndexValue} or  * {@link IndexValue} to java-object adapter is registered to the used {@link IndexValueFactory}.  *   * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|NoConverterException
extends|extends
name|RuntimeException
block|{
comment|/**      * default serialVersionUID      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/**      * Create an instance of<code>NoAdapterException</code> indicating that no adapter is available for the      * type.      *       * @param type      *            the type for which no adapter is available      */
specifier|public
name|NoConverterException
parameter_list|(
name|Type
name|type
parameter_list|)
block|{
name|super
argument_list|(
literal|"No adapter available for type "
operator|+
name|type
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

