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
name|filewords
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

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
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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

begin_import
import|import
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|knife
operator|.
name|CharSet
import|;
end_import

begin_comment
comment|/**  *   * @author Zhiliang Wang [qieqie.wang@gmail.com]  *   * @since 1.0  *   */
end_comment

begin_class
specifier|public
class|class
name|FileWordsReader
block|{
specifier|public
specifier|static
name|Map
comment|/*<String, Set<Word>>*/
name|readWords
parameter_list|(
name|String
name|fileOrDirectory
parameter_list|,
name|String
name|charsetName
parameter_list|,
name|int
name|maxWordLen
parameter_list|)
throws|throws
name|IOException
block|{
name|SimpleReadListener
name|l
init|=
operator|new
name|SimpleReadListener
argument_list|()
decl_stmt|;
name|readWords
argument_list|(
name|fileOrDirectory
argument_list|,
name|l
argument_list|,
name|charsetName
argument_list|,
name|maxWordLen
argument_list|)
expr_stmt|;
return|return
name|l
operator|.
name|getResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Map
comment|/*<String, Collection<Word>>*/
name|readWords
parameter_list|(
name|String
name|fileOrDirectory
parameter_list|,
name|String
name|charsetName
parameter_list|,
name|int
name|maxWordLen
parameter_list|,
name|Class
name|collectionClass
parameter_list|,
name|String
name|ext
parameter_list|)
throws|throws
name|IOException
block|{
name|SimpleReadListener2
name|l
init|=
operator|new
name|SimpleReadListener2
argument_list|(
name|collectionClass
argument_list|,
name|ext
argument_list|)
decl_stmt|;
name|readWords
argument_list|(
name|fileOrDirectory
argument_list|,
name|l
argument_list|,
name|charsetName
argument_list|,
name|maxWordLen
argument_list|)
expr_stmt|;
return|return
name|l
operator|.
name|getResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|readWords
parameter_list|(
name|String
name|fileOrDirectory
parameter_list|,
name|ReadListener
name|l
parameter_list|,
name|String
name|charsetName
parameter_list|,
name|int
name|maxWordLen
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|file
decl_stmt|;
if|if
condition|(
name|fileOrDirectory
operator|.
name|startsWith
argument_list|(
literal|"classpath:"
argument_list|)
condition|)
block|{
name|String
name|name
init|=
name|fileOrDirectory
operator|.
name|substring
argument_list|(
literal|"classpath:"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|URL
name|url
init|=
name|FileWordsReader
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|FileNotFoundException
argument_list|(
literal|"file \""
operator|+
name|name
operator|+
literal|"\" not found in classpath!"
argument_list|)
throw|;
block|}
name|file
operator|=
operator|new
name|File
argument_list|(
name|getUrlPath
argument_list|(
name|url
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|file
operator|=
operator|new
name|File
argument_list|(
name|fileOrDirectory
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|FileNotFoundException
argument_list|(
literal|"file \""
operator|+
name|fileOrDirectory
operator|+
literal|"\" not found!"
argument_list|)
throw|;
block|}
block|}
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
name|dics
init|=
operator|new
name|LinkedList
comment|/*<File>*/
argument_list|()
decl_stmt|;
name|String
name|dir
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|dirs
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|dir
operator|=
name|file
operator|.
name|getAbsolutePath
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|dics
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|dir
operator|=
name|file
operator|.
name|getParentFile
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
expr_stmt|;
block|}
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
name|files
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
name|files
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
name|files
index|[
name|i
index|]
decl_stmt|;
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
name|dics
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|Iterator
name|iter
init|=
name|dics
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
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
name|name
init|=
name|f
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|substring
argument_list|(
name|dir
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
decl_stmt|;
name|name
operator|=
name|name
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
name|l
operator|.
name|onFileBegin
argument_list|(
name|name
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|f
argument_list|)
argument_list|,
name|charsetName
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|word
decl_stmt|;
name|boolean
name|firstInDic
init|=
literal|true
decl_stmt|;
while|while
condition|(
operator|(
name|word
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|firstInDic
condition|)
block|{
name|firstInDic
operator|=
literal|false
expr_stmt|;
comment|// ref:http://www.w3.org/International/questions/qa-utf8-bom
comment|// ZERO WIDTH NO-BREAK SPACE
comment|// notepad将文件保存为unitcode或utf-8时会在文件开头保存bom字符串
comment|// notepad根据是否有bom来识别该文件是否是utf-8编码存储的。
comment|// 庖丁字典需要将这个字符从词典中去掉
if|if
condition|(
name|word
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|CharSet
operator|.
name|isBom
argument_list|(
name|word
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
block|{
name|word
operator|=
name|word
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
comment|// maximum word length limitation
if|if
condition|(
name|maxWordLen
operator|<=
literal|0
operator|||
name|word
operator|.
name|length
argument_list|()
operator|<=
name|maxWordLen
condition|)
name|l
operator|.
name|onWord
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
name|l
operator|.
name|onFileEnd
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|String
name|getUrlPath
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
if|if
condition|(
name|url
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|String
name|urlPath
init|=
literal|null
decl_stmt|;
try|try
block|{
name|urlPath
operator|=
name|url
operator|.
name|toURI
argument_list|()
operator|.
name|getPath
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{ 		}
if|if
condition|(
name|urlPath
operator|==
literal|null
condition|)
block|{
name|urlPath
operator|=
name|url
operator|.
name|getFile
argument_list|()
expr_stmt|;
block|}
return|return
name|urlPath
return|;
block|}
block|}
end_class

end_unit

