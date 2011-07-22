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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|registry
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|registry
operator|.
name|models
operator|.
name|Library
import|;
end_import

begin_comment
comment|/**  * Thrown whenever there is a request for the contents of an ontology library which have not been loaded yet  * (e.g. due to lazy loading policies). Developers who catch this exception may, for example, decide to load  * the library contents.  */
end_comment

begin_class
specifier|public
class|class
name|LibraryContentNotLoadedException
extends|extends
name|RegistryContentException
block|{
comment|/**      *       */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|4442769260608567120L
decl_stmt|;
specifier|private
name|Library
name|library
decl_stmt|;
comment|/**      * Creates a new instance of {@link LibraryContentNotLoadedException}.      *       * @param library      *            the ontology library that caused the exception.      */
specifier|public
name|LibraryContentNotLoadedException
parameter_list|(
name|Library
name|library
parameter_list|)
block|{
name|super
argument_list|(
name|library
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|library
operator|=
name|library
expr_stmt|;
block|}
comment|/**      * Returns the library whose content was requested that is not loaded yet.      *       * @return the ontology library that caused the exception.      */
specifier|public
name|Library
name|getLibrary
parameter_list|()
block|{
return|return
name|library
return|;
block|}
block|}
end_class

end_unit

