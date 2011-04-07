begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * FileUtil.java  *  * Created on November 15, 2007, 3:34 PM  *  * To change this template, choose Tools | Options and locate the template under  * the Source Creation and Management node. Right-click the template and choose  * Open. You can then make changes to the template in the Source Editor.  */
end_comment

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
name|rest
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
name|BufferedWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataInputStream
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
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
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
name|net
operator|.
name|URLConnection
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

begin_comment
comment|/**  *   * @author tuncay  */
end_comment

begin_class
specifier|public
class|class
name|FileUtil
block|{
specifier|private
specifier|static
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FileUtil
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|byte
index|[]
name|getBytesFromFile
parameter_list|(
name|String
name|fileURI
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|fileURI
argument_list|)
decl_stmt|;
return|return
name|getBytes
argument_list|(
name|file
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|String
name|readWholeFile
parameter_list|(
name|URI
name|uri
parameter_list|)
throws|throws
name|IOException
block|{
name|BufferedReader
name|buf
decl_stmt|;
name|StringBuffer
name|rules
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|FileInputStream
name|fis
init|=
operator|new
name|FileInputStream
argument_list|(
operator|new
name|File
argument_list|(
name|uri
argument_list|)
argument_list|)
decl_stmt|;
name|InputStreamReader
name|inputStreamReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|fis
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|buf
operator|=
operator|new
name|BufferedReader
argument_list|(
name|inputStreamReader
argument_list|)
expr_stmt|;
name|String
name|temp
decl_stmt|;
while|while
condition|(
operator|(
name|temp
operator|=
name|buf
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
name|rules
operator|.
name|append
argument_list|(
name|temp
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|buf
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|rules
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|readWholeFile
parameter_list|(
name|String
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
name|BufferedReader
name|buf
decl_stmt|;
name|StringBuffer
name|rules
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|FileInputStream
name|fis
init|=
operator|new
name|FileInputStream
argument_list|(
name|filePath
argument_list|)
decl_stmt|;
name|InputStreamReader
name|inputStreamReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|fis
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|buf
operator|=
operator|new
name|BufferedReader
argument_list|(
name|inputStreamReader
argument_list|)
expr_stmt|;
name|String
name|temp
decl_stmt|;
while|while
condition|(
operator|(
name|temp
operator|=
name|buf
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
name|rules
operator|.
name|append
argument_list|(
name|temp
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|buf
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|rules
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|readWholeFile
parameter_list|(
name|InputStream
name|is
parameter_list|)
throws|throws
name|IOException
block|{
name|BufferedReader
name|buf
decl_stmt|;
name|StringBuffer
name|rules
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|InputStreamReader
name|inputStreamReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|buf
operator|=
operator|new
name|BufferedReader
argument_list|(
name|inputStreamReader
argument_list|)
expr_stmt|;
name|String
name|temp
decl_stmt|;
while|while
condition|(
operator|(
name|temp
operator|=
name|buf
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
name|rules
operator|.
name|append
argument_list|(
name|temp
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|buf
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|rules
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|appendToFile
parameter_list|(
name|String
name|filePath
parameter_list|,
name|String
name|content
parameter_list|)
throws|throws
name|IOException
block|{
name|PrintWriter
name|outFile
init|=
operator|new
name|PrintWriter
argument_list|(
name|filePath
argument_list|)
decl_stmt|;
comment|// opens file
name|outFile
operator|.
name|print
argument_list|(
name|content
argument_list|)
expr_stmt|;
comment|// writes to file
name|outFile
operator|.
name|flush
argument_list|()
expr_stmt|;
name|outFile
operator|.
name|close
argument_list|()
expr_stmt|;
comment|// closes the file
block|}
comment|/*      * public static String readWholeFile(String filePath, String encoding) throws IOException {      * BufferedReader buf; StringBuffer rules=new StringBuffer(); FileInputStream fis = new      * FileInputStream(filePath); InputStreamReader inputStreamReader = new InputStreamReader(fis, encoding);      * buf=new BufferedReader(inputStreamReader); String temp; while((temp=buf.readLine())!=null)      * rules.append(temp).append("\n"); buf.close(); return rules.toString(); }      */
comment|/*      * public static String readWebAppFile(String filePath, String encoding) throws IOException {      * BufferedReader buf; StringBuffer rules=new StringBuffer(); InputStream is =      * Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath); InputStreamReader      * inputStreamReader = new InputStreamReader(is, encoding); buf=new BufferedReader(inputStreamReader);      * String temp; while((temp=buf.readLine())!=null) rules.append(temp).append("\n"); buf.close(); return      * rules.toString(); }      */
specifier|public
specifier|static
name|byte
index|[]
name|readFromURI
parameter_list|(
name|URI
name|uri
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|uri
operator|.
name|toString
argument_list|()
operator|.
name|contains
argument_list|(
literal|"http:"
argument_list|)
condition|)
block|{
name|URL
name|url
init|=
name|uri
operator|.
name|toURL
argument_list|()
decl_stmt|;
name|URLConnection
name|urlConnection
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|int
name|length
init|=
name|urlConnection
operator|.
name|getContentLength
argument_list|()
decl_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"length of content in URL = "
operator|+
name|length
argument_list|)
expr_stmt|;
if|if
condition|(
name|length
operator|>
operator|-
literal|1
condition|)
block|{
name|byte
index|[]
name|pureContent
init|=
operator|new
name|byte
index|[
name|length
index|]
decl_stmt|;
name|DataInputStream
name|dis
init|=
operator|new
name|DataInputStream
argument_list|(
name|urlConnection
operator|.
name|getInputStream
argument_list|()
argument_list|)
decl_stmt|;
name|dis
operator|.
name|readFully
argument_list|(
name|pureContent
argument_list|,
literal|0
argument_list|,
name|length
argument_list|)
expr_stmt|;
name|dis
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|pureContent
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to determine the content-length of the document pointed at "
operator|+
name|url
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
block|}
else|else
block|{
return|return
name|readWholeFile
argument_list|(
name|uri
argument_list|)
operator|.
name|getBytes
argument_list|(
literal|"UTF-8"
argument_list|)
return|;
block|}
block|}
specifier|private
specifier|static
name|byte
index|[]
name|getBytes
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
comment|// logger.debug("\nFileInputStream is " + file);
comment|// Get the size of the file
name|long
name|length
init|=
name|file
operator|.
name|length
argument_list|()
decl_stmt|;
comment|// logger.debug("Length of " + file + " is " + length + "\n");
if|if
condition|(
name|length
operator|>
name|Integer
operator|.
name|MAX_VALUE
condition|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"File is too large to process"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Create the byte array to hold the data
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
comment|// Read in the bytes
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
operator|(
name|offset
operator|<
name|bytes
operator|.
name|length
operator|)
operator|&&
operator|(
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
operator|)
condition|)
block|{
name|offset
operator|+=
name|numRead
expr_stmt|;
block|}
comment|// Ensure all the bytes have been read in
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
comment|/**      * Constructs a new file with the given content and filePath. If a file with the same name already exists,      * simply overwrites the content.      *       * @author Gunes      * @param filePath      * @param content      *            : expressed as byte[]      * @throws IOException      *             : [approved by gunes] when the file cannot be created. possible causes: 1) invalid      *             filePath, 2) already existing file cannot be deleted due to read-write locks      */
specifier|public
specifier|static
name|void
name|constructNewFile
parameter_list|(
name|String
name|filePath
parameter_list|,
name|byte
index|[]
name|content
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|filePath
argument_list|)
decl_stmt|;
name|file
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
name|file
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|FileOutputStream
name|fos
init|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|fos
operator|.
name|write
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|fos
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|writeToFile
parameter_list|(
name|File
name|file
parameter_list|,
name|String
name|content
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
name|OutputStreamWriter
name|outStreamWriter
init|=
operator|new
name|OutputStreamWriter
argument_list|(
name|fos
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|BufferedWriter
name|bufferedWriter
init|=
operator|new
name|BufferedWriter
argument_list|(
name|outStreamWriter
argument_list|)
decl_stmt|;
name|bufferedWriter
operator|.
name|write
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|bufferedWriter
operator|.
name|flush
argument_list|()
expr_stmt|;
name|bufferedWriter
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

