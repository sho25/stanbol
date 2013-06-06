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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @since 2.0.2  *   */
end_comment

begin_class
specifier|public
class|class
name|Difference
block|{
comment|/** 	 * 变更了的 	 *  	 * @return 	 */
specifier|private
name|List
comment|/*<Node> */
name|modified
init|=
operator|new
name|LinkedList
comment|/*<Node> */
argument_list|()
decl_stmt|;
comment|/** 	 * 删除了的 	 *  	 * @return 	 */
specifier|private
name|List
comment|/*<Node> */
name|deleted
init|=
operator|new
name|LinkedList
comment|/*<Node> */
argument_list|()
decl_stmt|;
comment|/** 	 * 新加的 	 *  	 * @return 	 */
specifier|private
name|List
comment|/*<Node> */
name|newcome
init|=
operator|new
name|LinkedList
comment|/*<Node> */
argument_list|()
decl_stmt|;
specifier|private
name|Snapshot
name|older
decl_stmt|;
specifier|private
name|Snapshot
name|younger
decl_stmt|;
specifier|public
name|List
comment|/*<Node> */
name|getModified
parameter_list|()
block|{
return|return
name|modified
return|;
block|}
specifier|public
name|void
name|setModified
parameter_list|(
name|List
comment|/*<Node> */
name|modified
parameter_list|)
block|{
name|this
operator|.
name|modified
operator|=
name|modified
expr_stmt|;
block|}
specifier|public
name|List
comment|/*<Node> */
name|getDeleted
parameter_list|()
block|{
return|return
name|deleted
return|;
block|}
specifier|public
name|void
name|setDeleted
parameter_list|(
name|List
comment|/*<Node> */
name|deleted
parameter_list|)
block|{
name|this
operator|.
name|deleted
operator|=
name|deleted
expr_stmt|;
block|}
specifier|public
name|List
comment|/*<Node> */
name|getNewcome
parameter_list|()
block|{
return|return
name|newcome
return|;
block|}
specifier|public
name|void
name|setNewcome
parameter_list|(
name|List
comment|/*<Node> */
name|newcome
parameter_list|)
block|{
name|this
operator|.
name|newcome
operator|=
name|newcome
expr_stmt|;
block|}
specifier|public
name|Snapshot
name|getOlder
parameter_list|()
block|{
return|return
name|older
return|;
block|}
specifier|public
name|void
name|setOlder
parameter_list|(
name|Snapshot
name|older
parameter_list|)
block|{
name|this
operator|.
name|older
operator|=
name|older
expr_stmt|;
block|}
specifier|public
name|Snapshot
name|getYounger
parameter_list|()
block|{
return|return
name|younger
return|;
block|}
specifier|public
name|void
name|setYounger
parameter_list|(
name|Snapshot
name|younger
parameter_list|)
block|{
name|this
operator|.
name|younger
operator|=
name|younger
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|deleted
operator|.
name|isEmpty
argument_list|()
operator|&&
name|modified
operator|.
name|isEmpty
argument_list|()
operator|&&
name|newcome
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|smodified
init|=
name|ArraysToString
argument_list|(
name|modified
operator|.
name|toArray
argument_list|(
operator|new
name|Node
index|[]
block|{}
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|snewcome
init|=
name|ArraysToString
argument_list|(
name|newcome
operator|.
name|toArray
argument_list|(
operator|new
name|Node
index|[]
block|{}
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|sdeleted
init|=
name|ArraysToString
argument_list|(
name|deleted
operator|.
name|toArray
argument_list|(
operator|new
name|Node
index|[]
block|{}
argument_list|)
argument_list|)
decl_stmt|;
return|return
literal|"modified="
operator|+
name|smodified
operator|+
literal|";newcome="
operator|+
name|snewcome
operator|+
literal|";deleted="
operator|+
name|sdeleted
return|;
block|}
comment|// 低于JDK1.5无Arrays.toString()方法，故有以下方法
specifier|private
specifier|static
name|String
name|ArraysToString
parameter_list|(
name|Object
index|[]
name|a
parameter_list|)
block|{
if|if
condition|(
name|a
operator|==
literal|null
condition|)
return|return
literal|"null"
return|;
name|int
name|iMax
init|=
name|a
operator|.
name|length
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|iMax
operator|==
operator|-
literal|1
condition|)
return|return
literal|"[]"
return|;
name|StringBuffer
name|b
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|b
operator|.
name|append
argument_list|(
literal|'['
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
condition|;
name|i
operator|++
control|)
block|{
name|b
operator|.
name|append
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|a
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|==
name|iMax
condition|)
return|return
name|b
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
operator|.
name|toString
argument_list|()
return|;
name|b
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

