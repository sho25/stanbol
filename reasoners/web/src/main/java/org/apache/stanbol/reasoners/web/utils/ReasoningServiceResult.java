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
name|reasoners
operator|.
name|web
operator|.
name|utils
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
name|commons
operator|.
name|jobs
operator|.
name|api
operator|.
name|JobResult
import|;
end_import

begin_comment
comment|/**  * To represent a result of a reasoning service.  *   * @author enridaga  *  * @param<T>  */
end_comment

begin_class
specifier|public
class|class
name|ReasoningServiceResult
parameter_list|<
name|T
extends|extends
name|Object
parameter_list|>
implements|implements
name|JobResult
block|{
specifier|private
name|T
name|resultObj
decl_stmt|;
specifier|private
name|boolean
name|success
decl_stmt|;
specifier|private
name|String
name|task
decl_stmt|;
specifier|public
name|ReasoningServiceResult
parameter_list|(
name|String
name|task
parameter_list|,
name|boolean
name|success
parameter_list|,
name|T
name|resultObj
parameter_list|)
block|{
name|this
operator|.
name|task
operator|=
name|task
expr_stmt|;
name|this
operator|.
name|resultObj
operator|=
name|resultObj
expr_stmt|;
name|this
operator|.
name|success
operator|=
name|success
expr_stmt|;
block|}
specifier|public
name|ReasoningServiceResult
parameter_list|(
name|String
name|task
parameter_list|,
name|boolean
name|success
parameter_list|)
block|{
name|this
operator|.
name|task
operator|=
name|task
expr_stmt|;
name|this
operator|.
name|resultObj
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|success
operator|=
name|success
expr_stmt|;
block|}
specifier|public
name|ReasoningServiceResult
parameter_list|(
name|String
name|task
parameter_list|,
name|T
name|resultObj
parameter_list|)
block|{
name|this
operator|.
name|task
operator|=
name|task
expr_stmt|;
name|this
operator|.
name|resultObj
operator|=
name|resultObj
expr_stmt|;
name|this
operator|.
name|success
operator|=
literal|true
expr_stmt|;
block|}
specifier|public
name|ReasoningServiceResult
parameter_list|(
name|String
name|task
parameter_list|)
block|{
name|this
operator|.
name|task
operator|=
name|task
expr_stmt|;
name|this
operator|.
name|resultObj
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|success
operator|=
literal|true
expr_stmt|;
block|}
specifier|public
name|T
name|get
parameter_list|()
block|{
return|return
name|this
operator|.
name|resultObj
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSuccess
parameter_list|()
block|{
return|return
name|success
return|;
block|}
specifier|public
name|String
name|getTask
parameter_list|()
block|{
return|return
name|task
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"Task: "
argument_list|)
operator|.
name|append
argument_list|(
name|task
argument_list|)
operator|.
name|append
argument_list|(
literal|". Result: "
argument_list|)
operator|.
name|append
argument_list|(
name|success
argument_list|)
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
expr_stmt|;
if|if
condition|(
name|resultObj
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"Result type is "
argument_list|)
operator|.
name|append
argument_list|(
name|resultObj
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

