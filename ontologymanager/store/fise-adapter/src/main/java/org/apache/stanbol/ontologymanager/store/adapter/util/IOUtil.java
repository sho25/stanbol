begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|store
operator|.
name|adapter
operator|.
name|util
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
name|FileOutputStream
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
name|InputStream
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
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|IOUtil
block|{
specifier|private
name|IOUtil
parameter_list|()
block|{}
specifier|private
specifier|static
class|class
name|Holder
block|{
specifier|private
specifier|static
specifier|final
name|IOUtil
name|INSTANCE
init|=
operator|new
name|IOUtil
argument_list|()
decl_stmt|;
block|}
specifier|public
specifier|static
name|IOUtil
name|getInstance
parameter_list|()
block|{
return|return
name|Holder
operator|.
name|INSTANCE
return|;
block|}
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|IOUtil
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
name|byte
index|[]
name|getBytesFromFile
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStream
name|is
init|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|long
name|length
init|=
name|file
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|length
operator|>
name|Integer
operator|.
name|MAX_VALUE
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Content too long for file: {}"
argument_list|,
name|file
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|byte
index|[]
name|bytes
init|=
operator|new
name|byte
index|[
operator|(
name|int
operator|)
name|length
index|]
decl_stmt|;
name|int
name|offset
init|=
literal|0
decl_stmt|;
name|int
name|numRead
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|offset
operator|<
name|bytes
operator|.
name|length
operator|&&
operator|(
name|numRead
operator|=
name|is
operator|.
name|read
argument_list|(
name|bytes
argument_list|,
name|offset
argument_list|,
name|bytes
operator|.
name|length
operator|-
name|offset
argument_list|)
operator|)
operator|>=
literal|0
condition|)
block|{
name|offset
operator|+=
name|numRead
expr_stmt|;
block|}
if|if
condition|(
name|offset
operator|<
name|bytes
operator|.
name|length
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Could not completely read file "
operator|+
name|file
operator|.
name|getName
argument_list|()
argument_list|)
throw|;
block|}
name|is
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|bytes
return|;
block|}
specifier|public
name|void
name|writeBytesToFile
parameter_list|(
specifier|final
name|File
name|file
parameter_list|,
specifier|final
name|byte
index|[]
name|bytes
parameter_list|)
throws|throws
name|IOException
block|{
name|FileOutputStream
name|fos
init|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
for|for
control|(
name|byte
name|b
range|:
name|bytes
control|)
block|{
name|fos
operator|.
name|write
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
name|fos
operator|.
name|flush
argument_list|()
expr_stmt|;
name|fos
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|convertStreamToString
parameter_list|(
specifier|final
name|InputStream
name|is
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|is
operator|!=
literal|null
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
name|line
decl_stmt|;
try|try
block|{
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|line
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|is
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|""
return|;
block|}
block|}
block|}
end_class

end_unit

