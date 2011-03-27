begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reengineer
operator|.
name|base
operator|.
name|api
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
name|ByteArrayInputStream
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
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|MessageDigest
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|NoSuchAlgorithmException
import|;
end_import

begin_class
specifier|public
class|class
name|URIGenerator
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SHA1
init|=
literal|"SHA1"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|MIN_BUF_SIZE
init|=
literal|8
operator|*
literal|1024
decl_stmt|;
comment|// 8 kB
specifier|public
specifier|static
specifier|final
name|int
name|MAX_BUF_SIZE
init|=
literal|64
operator|*
literal|1024
decl_stmt|;
comment|// 64 kB
specifier|private
specifier|static
specifier|final
name|char
index|[]
name|HEX_DIGITS
init|=
literal|"0123456789abcdef"
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|String
name|createID
parameter_list|(
name|String
name|baseUri
parameter_list|,
name|byte
index|[]
name|content
parameter_list|)
block|{
comment|// calculate an ID based on the digest of the content
name|String
name|hexDigest
init|=
literal|""
decl_stmt|;
if|if
condition|(
operator|!
name|baseUri
operator|.
name|startsWith
argument_list|(
literal|"urn:"
argument_list|)
operator|&&
operator|!
name|baseUri
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|baseUri
operator|=
name|baseUri
operator|+
literal|"/"
expr_stmt|;
block|}
try|try
block|{
name|hexDigest
operator|=
name|streamDigest
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|content
argument_list|)
argument_list|,
literal|null
argument_list|,
name|SHA1
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// this is not going to happen since output stream is null and the
comment|// input data is already loaded in memory
block|}
return|return
name|baseUri
operator|+
name|SHA1
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"-"
operator|+
name|hexDigest
return|;
block|}
specifier|public
specifier|static
name|String
name|streamDigest
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|OutputStream
name|out
parameter_list|,
name|String
name|digestAlgorithm
parameter_list|)
throws|throws
name|IOException
block|{
name|MessageDigest
name|digest
decl_stmt|;
try|try
block|{
name|digest
operator|=
name|MessageDigest
operator|.
name|getInstance
argument_list|(
name|digestAlgorithm
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchAlgorithmException
name|e
parameter_list|)
block|{
throw|throw
operator|(
name|IOException
operator|)
operator|new
name|IOException
argument_list|()
operator|.
name|initCause
argument_list|(
name|e
argument_list|)
throw|;
block|}
name|int
name|size
init|=
name|in
operator|.
name|available
argument_list|()
decl_stmt|;
if|if
condition|(
name|size
operator|==
literal|0
condition|)
block|{
name|size
operator|=
name|MAX_BUF_SIZE
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|size
operator|<
name|MIN_BUF_SIZE
condition|)
block|{
name|size
operator|=
name|MIN_BUF_SIZE
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|size
operator|>
name|MAX_BUF_SIZE
condition|)
block|{
name|size
operator|=
name|MAX_BUF_SIZE
expr_stmt|;
block|}
name|byte
index|[]
name|buf
init|=
operator|new
name|byte
index|[
name|size
index|]
decl_stmt|;
comment|/*          * Copy and digest.          */
name|int
name|n
decl_stmt|;
while|while
condition|(
operator|(
name|n
operator|=
name|in
operator|.
name|read
argument_list|(
name|buf
argument_list|)
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
if|if
condition|(
name|out
operator|!=
literal|null
condition|)
block|{
name|out
operator|.
name|write
argument_list|(
name|buf
argument_list|,
literal|0
argument_list|,
name|n
argument_list|)
expr_stmt|;
block|}
name|digest
operator|.
name|update
argument_list|(
name|buf
argument_list|,
literal|0
argument_list|,
name|n
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|out
operator|!=
literal|null
condition|)
block|{
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
return|return
name|toHexString
argument_list|(
name|digest
operator|.
name|digest
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|toHexString
parameter_list|(
name|byte
index|[]
name|data
parameter_list|)
block|{
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|(
literal|2
operator|*
name|data
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|byte
name|b
range|:
name|data
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|HEX_DIGITS
index|[
operator|(
literal|0xF0
operator|&
name|b
operator|)
operator|>>
literal|4
index|]
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|HEX_DIGITS
index|[
literal|0x0F
operator|&
name|b
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|buf
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

