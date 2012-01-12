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
name|topic
package|;
end_package

begin_comment
comment|/**  * Data transfer object to report estimated classification performance of a classifier.  *   * TODO: explain the metrics and give links to wikipedia  */
end_comment

begin_class
specifier|public
class|class
name|ClassificationPerformance
block|{
specifier|public
specifier|final
name|float
name|precision
decl_stmt|;
specifier|public
specifier|final
name|float
name|recall
decl_stmt|;
specifier|public
specifier|final
name|float
name|f1
decl_stmt|;
comment|// TODO: include ids of badly classified positive and negative examples?
specifier|public
name|ClassificationPerformance
parameter_list|(
name|float
name|precision
parameter_list|,
name|float
name|recall
parameter_list|,
name|float
name|f1
parameter_list|)
block|{
name|this
operator|.
name|precision
operator|=
name|precision
expr_stmt|;
name|this
operator|.
name|recall
operator|=
name|recall
expr_stmt|;
name|this
operator|.
name|f1
operator|=
name|f1
expr_stmt|;
block|}
block|}
end_class

end_unit

