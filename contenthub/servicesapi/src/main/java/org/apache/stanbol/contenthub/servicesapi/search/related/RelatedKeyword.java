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
name|search
operator|.
name|related
package|;
end_package

begin_comment
comment|/**  * This interface defines the structure of a keyword which is related to the query term of a search operation.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RelatedKeyword
block|{
comment|/**      * @return Lexical value of the related keyword.      */
name|String
name|getKeyword
parameter_list|()
function_decl|;
comment|/**      * @return Score of the related keyword      */
name|double
name|getScore
parameter_list|()
function_decl|;
comment|/**      * @return Source of the related keyword      */
name|String
name|getSource
parameter_list|()
function_decl|;
comment|/**      * To enumerate the source for a related keyword      */
specifier|public
enum|enum
name|Source
block|{
name|UNKNOWN
argument_list|(
literal|"Unknown"
argument_list|)
block|,
name|WORDNET
argument_list|(
literal|"Wordnet"
argument_list|)
block|,
name|ONTOLOGY
argument_list|(
literal|"Ontology"
argument_list|)
block|;
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
specifier|private
name|Source
parameter_list|(
name|String
name|n
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|n
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
name|this
operator|.
name|name
return|;
block|}
block|}
block|}
end_interface

end_unit

