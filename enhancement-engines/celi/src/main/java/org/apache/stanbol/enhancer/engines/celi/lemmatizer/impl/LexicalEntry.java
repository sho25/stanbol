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
name|enhancer
operator|.
name|engines
operator|.
name|celi
operator|.
name|lemmatizer
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_class
specifier|public
class|class
name|LexicalEntry
block|{
name|String
name|wordForm
decl_stmt|;
name|int
name|from
decl_stmt|,
name|to
decl_stmt|;
name|List
argument_list|<
name|Reading
argument_list|>
name|termReadings
init|=
literal|null
decl_stmt|;
specifier|public
name|LexicalEntry
parameter_list|(
name|String
name|wordForm
parameter_list|,
name|int
name|from
parameter_list|,
name|int
name|to
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|wordForm
operator|=
name|wordForm
expr_stmt|;
name|this
operator|.
name|from
operator|=
name|from
expr_stmt|;
name|this
operator|.
name|to
operator|=
name|to
expr_stmt|;
block|}
specifier|public
name|String
name|getWordForm
parameter_list|()
block|{
return|return
name|wordForm
return|;
block|}
specifier|public
name|void
name|setWordForm
parameter_list|(
name|String
name|wordForm
parameter_list|)
block|{
name|this
operator|.
name|wordForm
operator|=
name|wordForm
expr_stmt|;
block|}
specifier|public
name|int
name|getFrom
parameter_list|()
block|{
return|return
name|from
return|;
block|}
specifier|public
name|void
name|setFrom
parameter_list|(
name|int
name|from
parameter_list|)
block|{
name|this
operator|.
name|from
operator|=
name|from
expr_stmt|;
block|}
specifier|public
name|int
name|getTo
parameter_list|()
block|{
return|return
name|to
return|;
block|}
specifier|public
name|void
name|setTo
parameter_list|(
name|int
name|to
parameter_list|)
block|{
name|this
operator|.
name|to
operator|=
name|to
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Reading
argument_list|>
name|getTermReadings
parameter_list|()
block|{
return|return
name|termReadings
return|;
block|}
specifier|public
name|void
name|setTermReadings
parameter_list|(
name|List
argument_list|<
name|Reading
argument_list|>
name|termReadings
parameter_list|)
block|{
name|this
operator|.
name|termReadings
operator|=
name|termReadings
expr_stmt|;
block|}
block|}
end_class

end_unit

