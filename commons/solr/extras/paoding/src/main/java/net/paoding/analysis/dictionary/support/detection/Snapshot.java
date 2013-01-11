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
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileFilter
import|;
end_import

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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

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
name|Map
import|;
end_import

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @since 2.0.2  *   */
end_comment

begin_class
specifier|public
class|class
name|Snapshot
block|{
comment|// 此次快照版本，使用时间表示
specifier|private
name|long
name|version
decl_stmt|;
comment|// 根地址，绝对地址，使用/作为目录分隔符
specifier|private
name|String
name|root
decl_stmt|;
comment|// String为相对根的地址，使用/作为目录分隔符
specifier|private
name|Map
comment|/*<String, InnerNode>*/
name|nodesMap
init|=
operator|new
name|HashMap
comment|/*<String, InnerNode>*/
argument_list|()
decl_stmt|;
comment|//
specifier|private
name|InnerNode
index|[]
name|nodes
decl_stmt|;
comment|//checksum of this snapshot
specifier|private
name|String
name|checksum
decl_stmt|;
specifier|private
name|Snapshot
parameter_list|()
block|{ 	}
specifier|public
specifier|static
name|Snapshot
name|flash
parameter_list|(
name|String
name|root
parameter_list|,
name|FileFilter
name|filter
parameter_list|)
block|{
return|return
name|flash
argument_list|(
operator|new
name|File
argument_list|(
name|root
argument_list|)
argument_list|,
name|filter
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Snapshot
name|flash
parameter_list|(
name|File
name|rootFile
parameter_list|,
name|FileFilter
name|filter
parameter_list|)
block|{
name|Snapshot
name|snapshot
init|=
operator|new
name|Snapshot
argument_list|()
decl_stmt|;
name|snapshot
operator|.
name|implFlash
argument_list|(
name|rootFile
argument_list|,
name|filter
argument_list|)
expr_stmt|;
return|return
name|snapshot
return|;
block|}
specifier|private
name|void
name|implFlash
parameter_list|(
name|File
name|rootFile
parameter_list|,
name|FileFilter
name|filter
parameter_list|)
block|{
name|version
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
name|root
operator|=
name|rootFile
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|replace
argument_list|(
literal|'\\'
argument_list|,
literal|'/'
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|rootFile
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|// do nothing, maybe the file has been deleted
name|nodes
operator|=
operator|new
name|InnerNode
index|[
literal|0
index|]
expr_stmt|;
block|}
else|else
block|{
name|InnerNode
name|rootNode
init|=
operator|new
name|InnerNode
argument_list|()
decl_stmt|;
name|rootNode
operator|.
name|path
operator|=
name|root
expr_stmt|;
name|rootNode
operator|.
name|isFile
operator|=
name|rootFile
operator|.
name|isFile
argument_list|()
expr_stmt|;
name|rootNode
operator|.
name|lastModified
operator|=
name|rootFile
operator|.
name|lastModified
argument_list|()
expr_stmt|;
name|nodesMap
operator|.
name|put
argument_list|(
name|root
argument_list|,
name|rootNode
argument_list|)
expr_stmt|;
if|if
condition|(
name|rootFile
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|LinkedList
comment|/*<File>*/
name|files
init|=
name|getPosterity
argument_list|(
name|rootFile
argument_list|,
name|filter
argument_list|)
decl_stmt|;
name|nodes
operator|=
operator|new
name|InnerNode
index|[
name|files
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
name|Iterator
comment|/*<File>*/
name|iter
init|=
name|files
operator|.
name|iterator
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nodes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|File
name|f
init|=
operator|(
name|File
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|path
init|=
name|f
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|substring
argument_list|(
name|this
operator|.
name|root
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
decl_stmt|;
name|path
operator|=
name|path
operator|.
name|replace
argument_list|(
literal|'\\'
argument_list|,
literal|'/'
argument_list|)
expr_stmt|;
name|InnerNode
name|node
init|=
operator|new
name|InnerNode
argument_list|()
decl_stmt|;
name|node
operator|.
name|path
operator|=
name|path
expr_stmt|;
name|node
operator|.
name|isFile
operator|=
name|f
operator|.
name|isFile
argument_list|()
expr_stmt|;
name|node
operator|.
name|lastModified
operator|=
name|f
operator|.
name|lastModified
argument_list|()
expr_stmt|;
name|int
name|index
init|=
name|path
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
decl_stmt|;
name|node
operator|.
name|parent
operator|=
name|index
operator|==
operator|-
literal|1
condition|?
name|root
else|:
name|path
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
expr_stmt|;
name|nodes
index|[
name|i
index|]
operator|=
name|node
expr_stmt|;
name|nodesMap
operator|.
name|put
argument_list|(
name|path
argument_list|,
name|node
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|//sort node for checksum
name|Arrays
operator|.
name|sort
argument_list|(
name|nodes
argument_list|)
expr_stmt|;
name|checksum
operator|=
literal|null
expr_stmt|;
block|}
comment|/** 	 * build checksum of snapshot 	 *  	 * @return checksum of current snapshot 	 */
specifier|private
name|void
name|buildCheckSum
parameter_list|()
block|{
name|short
name|checksum
init|=
operator|-
literal|631
decl_stmt|;
name|short
name|multiplier
init|=
literal|1
decl_stmt|;
name|String
name|ENCODING
init|=
literal|"UTF-8"
decl_stmt|;
name|StringBuilder
name|value
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nodes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|value
operator|.
name|append
argument_list|(
name|nodes
index|[
name|i
index|]
operator|.
name|path
argument_list|)
expr_stmt|;
name|value
operator|.
name|append
argument_list|(
name|nodes
index|[
name|i
index|]
operator|.
name|isFile
argument_list|)
expr_stmt|;
name|value
operator|.
name|append
argument_list|(
name|nodes
index|[
name|i
index|]
operator|.
name|parent
argument_list|)
expr_stmt|;
name|value
operator|.
name|append
argument_list|(
name|nodes
index|[
name|i
index|]
operator|.
name|lastModified
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|byte
index|[]
name|data
init|=
name|value
operator|.
name|toString
argument_list|()
operator|.
name|getBytes
argument_list|(
name|ENCODING
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|b
init|=
literal|0
init|;
name|b
operator|<
name|data
operator|.
name|length
condition|;
operator|++
name|b
control|)
name|checksum
operator|+=
name|data
index|[
name|b
index|]
operator|*
name|multiplier
operator|++
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
name|ex
parameter_list|)
block|{  		}
name|this
operator|.
name|checksum
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|checksum
argument_list|)
expr_stmt|;
block|}
specifier|public
name|long
name|getVersion
parameter_list|()
block|{
return|return
name|version
return|;
block|}
specifier|public
name|void
name|setVersion
parameter_list|(
name|long
name|version
parameter_list|)
block|{
name|this
operator|.
name|version
operator|=
name|version
expr_stmt|;
block|}
specifier|public
name|String
name|getRoot
parameter_list|()
block|{
return|return
name|root
return|;
block|}
specifier|public
name|void
name|setRoot
parameter_list|(
name|String
name|root
parameter_list|)
block|{
name|this
operator|.
name|root
operator|=
name|root
expr_stmt|;
block|}
comment|//get checksum in lazy mode
specifier|public
name|String
name|getCheckSum
parameter_list|()
block|{
if|if
condition|(
name|checksum
operator|==
literal|null
condition|)
name|buildCheckSum
argument_list|()
expr_stmt|;
return|return
name|checksum
return|;
block|}
specifier|public
name|Difference
name|diff
parameter_list|(
name|Snapshot
name|that
parameter_list|)
block|{
name|Snapshot
name|older
init|=
name|that
decl_stmt|;
name|Snapshot
name|younger
init|=
name|this
decl_stmt|;
if|if
condition|(
name|that
operator|.
name|version
operator|>
name|this
operator|.
name|version
condition|)
block|{
name|older
operator|=
name|this
expr_stmt|;
name|younger
operator|=
name|that
expr_stmt|;
block|}
name|Difference
name|diff
init|=
operator|new
name|Difference
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|younger
operator|.
name|root
operator|.
name|equals
argument_list|(
name|older
operator|.
name|root
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"the snaps should be same root"
argument_list|)
throw|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|older
operator|.
name|nodes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|InnerNode
name|olderNode
init|=
name|older
operator|.
name|nodes
index|[
name|i
index|]
decl_stmt|;
name|InnerNode
name|yongerNode
init|=
operator|(
name|InnerNode
operator|)
name|younger
operator|.
name|nodesMap
operator|.
name|get
argument_list|(
operator|(
name|String
operator|)
name|olderNode
operator|.
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|yongerNode
operator|==
literal|null
condition|)
block|{
name|diff
operator|.
name|getDeleted
argument_list|()
operator|.
name|add
argument_list|(
name|olderNode
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|yongerNode
operator|.
name|lastModified
operator|!=
name|olderNode
operator|.
name|lastModified
condition|)
block|{
name|diff
operator|.
name|getModified
argument_list|()
operator|.
name|add
argument_list|(
name|olderNode
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|younger
operator|.
name|nodes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|InnerNode
name|yongerNode
init|=
name|younger
operator|.
name|nodes
index|[
name|i
index|]
decl_stmt|;
name|InnerNode
name|olderNode
init|=
operator|(
name|InnerNode
operator|)
name|older
operator|.
name|nodesMap
operator|.
name|get
argument_list|(
operator|(
name|String
operator|)
name|yongerNode
operator|.
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|olderNode
operator|==
literal|null
condition|)
block|{
name|diff
operator|.
name|getNewcome
argument_list|()
operator|.
name|add
argument_list|(
name|yongerNode
argument_list|)
expr_stmt|;
block|}
block|}
name|diff
operator|.
name|setOlder
argument_list|(
name|older
argument_list|)
expr_stmt|;
name|diff
operator|.
name|setYounger
argument_list|(
name|younger
argument_list|)
expr_stmt|;
return|return
name|diff
return|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
throws|throws
name|InterruptedException
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
literal|"dic"
argument_list|)
decl_stmt|;
name|Snapshot
name|snapshot1
init|=
name|Snapshot
operator|.
name|flash
argument_list|(
name|f
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"----"
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|3000
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"----"
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|3000
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"----"
argument_list|)
expr_stmt|;
name|Snapshot
name|snapshot2
init|=
name|Snapshot
operator|.
name|flash
argument_list|(
name|f
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Difference
name|diff
init|=
name|snapshot2
operator|.
name|diff
argument_list|(
name|snapshot1
argument_list|)
decl_stmt|;
name|String
name|deleted
init|=
name|ArraysToString
argument_list|(
name|diff
operator|.
name|getDeleted
argument_list|()
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"deleted: "
operator|+
name|deleted
argument_list|)
expr_stmt|;
name|String
name|modified
init|=
name|ArraysToString
argument_list|(
name|diff
operator|.
name|getModified
argument_list|()
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"modified: "
operator|+
name|modified
argument_list|)
expr_stmt|;
name|String
name|newcome
init|=
name|ArraysToString
argument_list|(
name|diff
operator|.
name|getNewcome
argument_list|()
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"newcome: "
operator|+
name|newcome
argument_list|)
expr_stmt|;
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
comment|// --------------------------------------------
specifier|private
name|LinkedList
comment|/*<File>*/
name|getPosterity
parameter_list|(
name|File
name|root
parameter_list|,
name|FileFilter
name|filter
parameter_list|)
block|{
name|ArrayList
comment|/*<File>*/
name|dirs
init|=
operator|new
name|ArrayList
comment|/*<File>*/
argument_list|()
decl_stmt|;
name|LinkedList
comment|/*<File>*/
name|files
init|=
operator|new
name|LinkedList
comment|/*<File>*/
argument_list|()
decl_stmt|;
name|dirs
operator|.
name|add
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|int
name|index
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|index
operator|<
name|dirs
operator|.
name|size
argument_list|()
condition|)
block|{
name|File
name|cur
init|=
operator|(
name|File
operator|)
name|dirs
operator|.
name|get
argument_list|(
name|index
operator|++
argument_list|)
decl_stmt|;
name|File
index|[]
name|children
init|=
name|cur
operator|.
name|listFiles
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|children
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|File
name|f
init|=
name|children
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|filter
operator|==
literal|null
operator|||
name|filter
operator|.
name|accept
argument_list|(
name|f
argument_list|)
condition|)
block|{
if|if
condition|(
name|f
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|dirs
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|files
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|files
return|;
block|}
class|class
name|InnerNode
extends|extends
name|Node
block|{
name|String
name|parent
decl_stmt|;
name|long
name|lastModified
decl_stmt|;
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|Node
name|o
parameter_list|)
block|{
comment|// super compare
name|int
name|result
init|=
name|super
operator|.
name|compareTo
argument_list|(
name|o
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|0
condition|)
return|return
name|result
return|;
if|if
condition|(
name|o
operator|instanceof
name|InnerNode
condition|)
block|{
name|InnerNode
name|node
init|=
operator|(
name|InnerNode
operator|)
name|o
decl_stmt|;
comment|// parent
if|if
condition|(
name|this
operator|.
name|parent
operator|!=
literal|null
operator|&&
name|node
operator|.
name|parent
operator|!=
literal|null
condition|)
block|{
name|int
name|cmp
init|=
name|this
operator|.
name|parent
operator|.
name|compareTo
argument_list|(
name|node
operator|.
name|parent
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
name|parent
operator|!=
literal|null
operator|&&
name|node
operator|.
name|parent
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
name|parent
operator|==
literal|null
operator|&&
name|node
operator|.
name|parent
operator|!=
literal|null
condition|)
return|return
operator|-
literal|1
return|;
block|}
comment|// lastModified
if|if
condition|(
name|this
operator|.
name|lastModified
operator|>
name|node
operator|.
name|lastModified
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|this
operator|.
name|lastModified
operator|<
name|node
operator|.
name|lastModified
condition|)
return|return
operator|-
literal|1
return|;
block|}
return|return
literal|0
return|;
block|}
block|}
block|}
end_class

end_unit
