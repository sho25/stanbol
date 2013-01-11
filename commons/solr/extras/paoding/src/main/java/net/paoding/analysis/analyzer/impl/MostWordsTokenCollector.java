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
name|analyzer
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
name|Iterator
import|;
end_import

begin_import
import|import
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|analyzer
operator|.
name|TokenCollector
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|Token
import|;
end_import

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @since 1.1  */
end_comment

begin_class
specifier|public
class|class
name|MostWordsTokenCollector
implements|implements
name|TokenCollector
implements|,
name|Iterator
block|{
specifier|private
name|LinkedToken
name|firstToken
decl_stmt|;
specifier|private
name|LinkedToken
name|lastToken
decl_stmt|;
comment|/** 	 * Collector接口实现。<br> 	 * 构造词语Token对象，并放置在tokens中 	 *  	 */
specifier|public
name|void
name|collect
parameter_list|(
name|String
name|word
parameter_list|,
name|int
name|begin
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|LinkedToken
name|tokenToAdd
init|=
operator|new
name|LinkedToken
argument_list|(
name|word
argument_list|,
name|begin
argument_list|,
name|end
argument_list|)
decl_stmt|;
if|if
condition|(
name|firstToken
operator|==
literal|null
condition|)
block|{
name|firstToken
operator|=
name|tokenToAdd
expr_stmt|;
name|lastToken
operator|=
name|tokenToAdd
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|tokenToAdd
operator|.
name|compareTo
argument_list|(
name|lastToken
argument_list|)
operator|>
literal|0
condition|)
block|{
name|tokenToAdd
operator|.
name|pre
operator|=
name|lastToken
expr_stmt|;
name|lastToken
operator|.
name|next
operator|=
name|tokenToAdd
expr_stmt|;
name|lastToken
operator|=
name|tokenToAdd
expr_stmt|;
comment|//
block|}
else|else
block|{
name|LinkedToken
name|curTokenToTry
init|=
name|lastToken
operator|.
name|pre
decl_stmt|;
while|while
condition|(
name|curTokenToTry
operator|!=
literal|null
operator|&&
name|tokenToAdd
operator|.
name|compareTo
argument_list|(
name|curTokenToTry
argument_list|)
operator|<
literal|0
condition|)
block|{
name|curTokenToTry
operator|=
name|curTokenToTry
operator|.
name|pre
expr_stmt|;
block|}
if|if
condition|(
name|curTokenToTry
operator|==
literal|null
condition|)
block|{
name|firstToken
operator|.
name|pre
operator|=
name|tokenToAdd
expr_stmt|;
name|tokenToAdd
operator|.
name|next
operator|=
name|firstToken
expr_stmt|;
name|firstToken
operator|=
name|tokenToAdd
expr_stmt|;
block|}
else|else
block|{
name|tokenToAdd
operator|.
name|next
operator|=
name|curTokenToTry
operator|.
name|next
expr_stmt|;
name|curTokenToTry
operator|.
name|next
operator|.
name|pre
operator|=
name|tokenToAdd
expr_stmt|;
name|tokenToAdd
operator|.
name|pre
operator|=
name|curTokenToTry
expr_stmt|;
name|curTokenToTry
operator|.
name|next
operator|=
name|tokenToAdd
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|LinkedToken
name|nextLinkedToken
decl_stmt|;
specifier|public
name|Iterator
comment|/*<Token> */
name|iterator
parameter_list|()
block|{
name|nextLinkedToken
operator|=
name|firstToken
expr_stmt|;
name|firstToken
operator|=
literal|null
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|nextLinkedToken
operator|!=
literal|null
return|;
block|}
specifier|public
name|Object
name|next
parameter_list|()
block|{
name|LinkedToken
name|ret
init|=
name|nextLinkedToken
decl_stmt|;
name|nextLinkedToken
operator|=
name|nextLinkedToken
operator|.
name|next
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|void
name|remove
parameter_list|()
block|{ 	}
specifier|private
specifier|static
class|class
name|LinkedToken
extends|extends
name|Token
implements|implements
name|Comparable
block|{
specifier|public
name|LinkedToken
name|pre
decl_stmt|;
specifier|public
name|LinkedToken
name|next
decl_stmt|;
specifier|public
name|LinkedToken
parameter_list|(
name|String
name|word
parameter_list|,
name|int
name|begin
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|super
argument_list|(
name|word
argument_list|,
name|begin
argument_list|,
name|end
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
name|LinkedToken
name|that
init|=
operator|(
name|LinkedToken
operator|)
name|obj
decl_stmt|;
comment|// 简单/单单/简简单单/
if|if
condition|(
name|this
operator|.
name|endOffset
argument_list|()
operator|>
name|that
operator|.
name|endOffset
argument_list|()
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|this
operator|.
name|endOffset
argument_list|()
operator|==
name|that
operator|.
name|endOffset
argument_list|()
condition|)
block|{
return|return
name|that
operator|.
name|startOffset
argument_list|()
operator|-
name|this
operator|.
name|startOffset
argument_list|()
return|;
block|}
return|return
operator|-
literal|1
return|;
block|}
block|}
block|}
end_class

end_unit
