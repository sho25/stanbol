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
operator|.
name|support
operator|.
name|detection
package|;
end_package

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @since 2.0.2  *   */
end_comment

begin_class
specifier|public
class|class
name|Node
implements|implements
name|Comparable
argument_list|<
name|Node
argument_list|>
block|{
name|String
name|path
decl_stmt|;
name|boolean
name|isFile
decl_stmt|;
specifier|public
name|Node
parameter_list|()
block|{ 	}
specifier|public
name|Node
parameter_list|(
name|String
name|path
parameter_list|,
name|boolean
name|isFile
parameter_list|)
block|{
name|this
operator|.
name|path
operator|=
name|path
expr_stmt|;
name|this
operator|.
name|isFile
operator|=
name|isFile
expr_stmt|;
block|}
comment|/** 	 * 返回结点路径 	 *<p> 	 * 如果该结点为根，则返回根的绝对路径<br> 	 * 如果该结点为根下的目录或文件，则返回其相对与根的路径<br> 	 *  	 * @return 	 */
specifier|public
name|String
name|getPath
parameter_list|()
block|{
return|return
name|path
return|;
block|}
comment|/** 	 * 该结点当时的属性：是否为文件 	 *  	 * @return 	 */
specifier|public
name|boolean
name|isFile
parameter_list|()
block|{
return|return
name|isFile
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|path
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
specifier|final
name|int
name|prime
init|=
literal|31
decl_stmt|;
name|int
name|result
init|=
literal|1
decl_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
operator|(
operator|(
name|path
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|path
operator|.
name|hashCode
argument_list|()
operator|)
expr_stmt|;
return|return
name|result
return|;
block|}
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
name|this
operator|==
name|obj
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|obj
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getClass
argument_list|()
operator|!=
name|obj
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
specifier|final
name|Node
name|other
init|=
operator|(
name|Node
operator|)
name|obj
decl_stmt|;
if|if
condition|(
name|path
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|path
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|path
operator|.
name|equals
argument_list|(
name|other
operator|.
name|path
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Node
name|o
parameter_list|)
block|{
comment|//path
if|if
condition|(
name|this
operator|.
name|path
operator|!=
literal|null
operator|&&
name|o
operator|.
name|path
operator|!=
literal|null
condition|)
block|{
name|int
name|cmp
init|=
name|this
operator|.
name|path
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
block|}
else|else
block|{
if|if
condition|(
name|this
operator|.
name|path
operator|!=
literal|null
operator|&&
name|o
operator|.
name|path
operator|==
literal|null
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|this
operator|.
name|path
operator|==
literal|null
operator|&&
name|o
operator|.
name|path
operator|!=
literal|null
condition|)
return|return
operator|-
literal|1
return|;
block|}
comment|//isfile
if|if
condition|(
name|this
operator|.
name|isFile
operator|&&
operator|!
name|o
operator|.
name|isFile
condition|)
return|return
literal|1
return|;
if|if
condition|(
operator|!
name|this
operator|.
name|isFile
operator|&&
name|o
operator|.
name|isFile
condition|)
return|return
operator|-
literal|1
return|;
return|return
literal|0
return|;
block|}
block|}
end_class

end_unit

