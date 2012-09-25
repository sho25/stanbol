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
name|collector
package|;
end_package

begin_comment
comment|/**  * Indicates an attempt to illegally create a resource by assigning it an IRI that already identifies another  * known resource. This exception typically results in the new resource not being created at all.<br>  *<br>  * This exception can be subclassed for managing specific resource type-based behaviour (e.g. scopes, spaces  * or sessions).  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|DuplicateIDException
extends|extends
name|Exception
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|802959772682427494L
decl_stmt|;
comment|/**      * The duplicate identifier      */
specifier|protected
name|String
name|dupe
decl_stmt|;
comment|/**      * Returns the IRI that identifies the existing resource. This can be use to obtain the resource itself by      * passing it onto appropriate managers.      *       * @return the duplicate identifier      */
specifier|public
name|String
name|getDuplicateID
parameter_list|()
block|{
return|return
name|dupe
return|;
block|}
comment|/**      * Creates a new instance of DuplicateIDException.      *       * @param dupe      *            the duplicate ID.      */
specifier|public
name|DuplicateIDException
parameter_list|(
name|String
name|dupe
parameter_list|)
block|{
name|this
operator|.
name|dupe
operator|=
name|dupe
expr_stmt|;
block|}
comment|/**      * Creates a new instance of DuplicateIDException.      *       * @param dupe      *            the duplicate ID.      * @param message      *            the detail message.      */
specifier|public
name|DuplicateIDException
parameter_list|(
name|String
name|dupe
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|this
operator|.
name|dupe
operator|=
name|dupe
expr_stmt|;
block|}
comment|/**      * Creates a new instance of DuplicateIDException.      *       * @param dupe      *            the duplicate ID.      * @param cause      *            the throwable that caused this exception to be thrown.      */
specifier|public
name|DuplicateIDException
parameter_list|(
name|String
name|dupe
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|this
argument_list|(
name|dupe
argument_list|)
expr_stmt|;
name|initCause
argument_list|(
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

