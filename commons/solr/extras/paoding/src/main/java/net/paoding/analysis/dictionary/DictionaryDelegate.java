begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  * Copyright 2007 The Apache Software Foundation  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|dictionary
package|;
end_package

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *  * @since 1.1  */
end_comment

begin_class
specifier|public
class|class
name|DictionaryDelegate
implements|implements
name|Dictionary
block|{
specifier|private
name|Dictionary
name|target
decl_stmt|;
specifier|public
name|DictionaryDelegate
parameter_list|()
block|{ 	}
specifier|public
name|DictionaryDelegate
parameter_list|(
name|Dictionary
name|target
parameter_list|)
block|{
name|this
operator|.
name|target
operator|=
name|target
expr_stmt|;
block|}
specifier|public
name|Dictionary
name|getTarget
parameter_list|()
block|{
return|return
name|target
return|;
block|}
specifier|public
name|void
name|setTarget
parameter_list|(
name|Dictionary
name|target
parameter_list|)
block|{
name|this
operator|.
name|target
operator|=
name|target
expr_stmt|;
block|}
specifier|public
name|Word
name|get
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|target
operator|.
name|get
argument_list|(
name|index
argument_list|)
return|;
block|}
specifier|public
name|Hit
name|search
parameter_list|(
name|CharSequence
name|input
parameter_list|,
name|int
name|offset
parameter_list|,
name|int
name|count
parameter_list|)
block|{
return|return
name|target
operator|.
name|search
argument_list|(
name|input
argument_list|,
name|offset
argument_list|,
name|count
argument_list|)
return|;
block|}
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|target
operator|.
name|size
argument_list|()
return|;
block|}
block|}
end_class

end_unit
