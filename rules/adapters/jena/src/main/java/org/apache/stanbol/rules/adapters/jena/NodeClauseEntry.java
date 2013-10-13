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
name|rules
operator|.
name|adapters
operator|.
name|jena
package|;
end_package

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|graph
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|reasoner
operator|.
name|rulesys
operator|.
name|ClauseEntry
import|;
end_import

begin_comment
comment|/**  * A wrapper in order to treat a node as a {@link ClauseEntry}  *   * @author anuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|NodeClauseEntry
implements|implements
name|ClauseEntry
block|{
specifier|private
name|Node
name|node
decl_stmt|;
specifier|public
name|NodeClauseEntry
parameter_list|(
name|Node
name|node
parameter_list|)
block|{
name|this
operator|.
name|node
operator|=
name|node
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|sameAs
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|false
return|;
block|}
comment|/**      * Get the node that is an instance of the class {@link Node}.      *       * @return the node      */
specifier|public
name|Node
name|getNode
parameter_list|()
block|{
return|return
name|node
return|;
block|}
block|}
end_class

end_unit

