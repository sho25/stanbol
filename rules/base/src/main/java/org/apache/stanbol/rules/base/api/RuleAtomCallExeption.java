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
name|base
operator|.
name|api
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|RuleAtom
import|;
end_import

begin_comment
comment|/**  * A {@link RuleAtomCallExeption} is thrown when an adapter is not able to adapt a rule atom of the rule.  *   * @author mac  *   */
end_comment

begin_class
specifier|public
class|class
name|RuleAtomCallExeption
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
literal|1L
decl_stmt|;
specifier|protected
name|Class
argument_list|<
name|?
argument_list|>
name|atomClass
decl_stmt|;
comment|/**      * Creates a new instance of RuleAtomCallExeption.      *       * @param the      *            {@link RuleAtom} {@link Class}.      */
specifier|public
name|RuleAtomCallExeption
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|atomClass
parameter_list|)
block|{
name|this
operator|.
name|atomClass
operator|=
name|atomClass
expr_stmt|;
block|}
comment|/**      * Returns the {@link Class} of the atom that generated the exeption.      *       * @return the atom {@link Class}      */
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
name|getAtomClass
parameter_list|()
block|{
return|return
name|atomClass
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
literal|"The adapter does not provide an implementation for the atom: "
operator|+
name|atomClass
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" "
return|;
block|}
block|}
end_class

end_unit

