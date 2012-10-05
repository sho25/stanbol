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
name|io
package|;
end_package

begin_comment
comment|/**  * The abstract implementation of the {@link OntologyInputSource} interface which is inherited by all concrete  * implementations.  *   * @author alexdma  *   * @param<O>  *            the ontology returned by this input source.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractGenericInputSource
parameter_list|<
name|O
parameter_list|>
implements|implements
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
operator|.
name|OntologyInputSource
argument_list|<
name|O
argument_list|>
block|{
comment|/**      * Where the ontology object was retrieved from.      */
specifier|protected
name|Origin
argument_list|<
name|?
argument_list|>
name|origin
init|=
literal|null
decl_stmt|;
specifier|protected
name|O
name|rootOntology
init|=
literal|null
decl_stmt|;
comment|/**      * This method is used to remind developers to bind a physical reference to the      * {@link OntologyInputSource} if intending to do so.      *       * Implementations should assign a value to {@link #origin}.      *       * @param origin      *            where the ontology object was obtained from.      */
specifier|protected
name|void
name|bindPhysicalOrigin
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
block|}
comment|/**      * This method is used to remind developers to bind a root ontology to the {@link OntologyInputSource} if      * intending to do so.      *       * Implementation should assign a value to {@link #rootOntology}.      *       * @param ontology      *            the root ontology.      */
specifier|protected
name|void
name|bindRootOntology
parameter_list|(
name|O
name|ontology
parameter_list|)
block|{
name|this
operator|.
name|rootOntology
operator|=
name|ontology
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
operator|)
condition|)
return|return
literal|false
return|;
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
name|src
init|=
operator|(
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
operator|)
name|obj
decl_stmt|;
return|return
name|this
operator|.
name|origin
operator|.
name|equals
argument_list|(
name|src
operator|.
name|getOrigin
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|rootOntology
operator|.
name|equals
argument_list|(
name|src
operator|.
name|getRootOntology
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Origin
argument_list|<
name|?
argument_list|>
name|getOrigin
parameter_list|()
block|{
return|return
name|origin
return|;
block|}
annotation|@
name|Override
specifier|public
name|O
name|getRootOntology
parameter_list|()
block|{
return|return
name|rootOntology
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasOrigin
parameter_list|()
block|{
return|return
name|origin
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasRootOntology
parameter_list|()
block|{
return|return
name|rootOntology
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|abstract
name|String
name|toString
parameter_list|()
function_decl|;
block|}
end_class

end_unit

