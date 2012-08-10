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
name|io
package|;
end_package

begin_comment
comment|/**  * Input sources that do not deliver an ontology object, but only a means for consumers to obtain one if they  * wish, should subclass this one.  *   * These input sources should be used whenever it is possible to avoid creating an ontology object, thereby  * saving resources. Examples include cases where the ontology is already stored in Stanbol, or whenever  * loading has to be deferred.  *   * @author alexdma  *   * @param<O>  *            the ontology object  */
end_comment

begin_class
annotation|@
name|SuppressWarnings
argument_list|(
block|{
literal|"unchecked"
block|,
literal|"rawtypes"
block|}
argument_list|)
specifier|public
specifier|abstract
class|class
name|AbstractOntologyReferenceSource
extends|extends
name|AbstractGenericInputSource
block|{
specifier|public
name|AbstractOntologyReferenceSource
parameter_list|(
name|Origin
argument_list|<
name|?
argument_list|>
name|origin
parameter_list|)
block|{
name|this
operator|.
name|origin
operator|=
name|origin
expr_stmt|;
name|this
operator|.
name|rootOntology
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

