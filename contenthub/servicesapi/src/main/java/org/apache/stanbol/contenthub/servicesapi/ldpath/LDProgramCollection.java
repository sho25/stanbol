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
name|contenthub
operator|.
name|servicesapi
operator|.
name|ldpath
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * A collection class for the managed LDPath programs in the Contenthub. Operates on a {@link Map} keeping the  *&lt;name,program> pairs.  *   * @author anil.sinaci  *   */
end_comment

begin_class
specifier|public
class|class
name|LDProgramCollection
block|{
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|nameProgramMap
decl_stmt|;
comment|/**      * Creates an {@link LDProgramCollection} based on a provide {@link Map} keeping&lt;name,program> pairs.      *       * @param nameProgramMap      *            On which the {@link LDProgramCollection} will be initialized.      */
specifier|public
name|LDProgramCollection
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|nameProgramMap
parameter_list|)
block|{
name|this
operator|.
name|nameProgramMap
operator|=
name|nameProgramMap
expr_stmt|;
block|}
comment|/**      * This method returns the list of LDPath programs stored in the scope of Contenthub.      *       * @return {@link List} of {@link LDProgram}s.      */
specifier|public
name|List
argument_list|<
name|LDProgram
argument_list|>
name|asList
parameter_list|()
block|{
name|List
argument_list|<
name|LDProgram
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<
name|LDProgram
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|nameProgramMap
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|list
operator|.
name|add
argument_list|(
operator|new
name|LDProgram
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|list
return|;
block|}
comment|/**      * This method returns the programs stored in the scope of Contenthub as a {@link Map}.      *       * @return {@link Map} keeping the&lt;name,program> pairs.      */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|asMap
parameter_list|()
block|{
return|return
name|this
operator|.
name|nameProgramMap
return|;
block|}
comment|/**      * This method returns the names of LDPath programs stored in the scope of Contenthub.      *       * @return {@link List} of program names.      */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getProgramNames
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|nameProgramMap
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * This method returns the LDPath programs themselves that are stored in the scope of Contenthub.      *       * @return {@link List} of programs.      */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getPrograms
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|nameProgramMap
operator|.
name|values
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

