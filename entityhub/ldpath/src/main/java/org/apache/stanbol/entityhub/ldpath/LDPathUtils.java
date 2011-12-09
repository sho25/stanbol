begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|ldpath
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
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|model
operator|.
name|programs
operator|.
name|Program
import|;
end_import

begin_class
specifier|public
class|class
name|LDPathUtils
block|{
comment|/**      * Utility method that creates a reader over the parsed String using UTF-8       * as encoding.<p> This is necessary because currently LDPath only accepts      * Reader as parameter for parsing {@link Program}s      * Note that it is not necessary to call {@link InputStream#close()} on the      * returned {@link InputStreamReader} because this is backed by an in-memory      * {@link ByteArrayInputStream}.      * @param string the string to be read by the Reader      * @return A reader over the parsed string      * @throws IllegalStateException if 'utf-8' is not supported      * @throws IllegalArgumentException if<code>null</code> is parsed as string      */
specifier|public
specifier|static
specifier|final
name|Reader
name|getReader
parameter_list|(
name|String
name|string
parameter_list|)
block|{
if|if
condition|(
name|string
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed string MUST NOT be NULL!"
argument_list|)
throw|;
block|}
try|try
block|{
return|return
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|string
operator|.
name|getBytes
argument_list|(
literal|"utf-8"
argument_list|)
argument_list|)
argument_list|,
literal|"utf-8"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Encoding 'utf-8' is not supported by this system!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

