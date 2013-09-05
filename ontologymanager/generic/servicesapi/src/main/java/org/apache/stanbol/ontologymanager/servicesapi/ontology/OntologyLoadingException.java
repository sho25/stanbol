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
name|servicesapi
operator|.
name|ontology
package|;
end_package

begin_comment
comment|/**  * A runtime exception denoting that trying to load an ontology into the Ontology Manager has caused an  * undesired status. The reason is most likely to be found in the cause registered with this exception.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|OntologyLoadingException
extends|extends
name|RuntimeException
block|{
comment|/**      *       */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|8496827319814210715L
decl_stmt|;
comment|/**      * Creates a new instance of {@link OntologyLoadingException}.      *       * @param cause      *            the throwable that caused this exception to be thrown.      */
specifier|public
name|OntologyLoadingException
parameter_list|(
name|Throwable
name|cause
parameter_list|)
block|{
name|initCause
argument_list|(
name|cause
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new instance of {@link OntologyLoadingException}.      *       * @param message      *            the exception message.      */
specifier|public
name|OntologyLoadingException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

