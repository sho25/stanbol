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
comment|/**  * Thrown whenever an illegal operation that modifies an ontology space is detected and denied.  */
end_comment

begin_class
specifier|public
class|class
name|OntologyCollectorModificationException
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
operator|-
literal|5147080356192253724L
decl_stmt|;
specifier|protected
name|OntologyCollector
name|collector
decl_stmt|;
comment|/**      * Creates a new instance of OntologySpaceModificationException.      *       * @param space      *            the ontology space whose modification was attempted.      */
specifier|public
name|OntologyCollectorModificationException
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|)
block|{
name|this
operator|.
name|collector
operator|=
name|collector
expr_stmt|;
block|}
comment|/**      * Creates a new instance of OntologySpaceModificationException.      *       * @param space      *            the ontology space whose modification was attempted.      */
specifier|public
name|OntologyCollectorModificationException
parameter_list|(
name|OntologyCollector
name|collector
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|this
argument_list|(
name|collector
argument_list|)
expr_stmt|;
name|initCause
argument_list|(
name|cause
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the ontology space that threw the exception (presumably after a failed modification attempt).      *       * @return the ontology space on which the exception was thrown.      */
specifier|public
name|OntologyCollector
name|getOntologyCollector
parameter_list|()
block|{
return|return
name|collector
return|;
block|}
block|}
end_class

end_unit

