begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|interaction
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Random
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|codec
operator|.
name|binary
operator|.
name|Base64
import|;
end_import

begin_comment
comment|/**  *<p>Title: Client HTTP Request class</p>  *<p>Description: this class helps to send POST HTTP requests with various form data,  * including files. Cookies can be added to be included in the request.</p>  *  * @author Vlad Patryshev, Fabian Christ  * @version 1.0  */
end_comment

begin_class
specifier|public
class|class
name|ClientHttpRequest
block|{
name|URLConnection
name|connection
decl_stmt|;
name|OutputStream
name|os
init|=
literal|null
decl_stmt|;
name|Map
name|cookies
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
specifier|protected
name|void
name|connect
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|os
operator|==
literal|null
condition|)
block|{
name|os
operator|=
name|connection
operator|.
name|getOutputStream
argument_list|()
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|write
parameter_list|(
name|char
name|c
parameter_list|)
throws|throws
name|IOException
block|{
name|connect
argument_list|()
expr_stmt|;
name|os
operator|.
name|write
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|write
parameter_list|(
name|String
name|s
parameter_list|)
throws|throws
name|IOException
block|{
name|connect
argument_list|()
expr_stmt|;
name|os
operator|.
name|write
argument_list|(
name|s
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|newline
parameter_list|()
throws|throws
name|IOException
block|{
name|connect
argument_list|()
expr_stmt|;
name|write
argument_list|(
literal|"\r\n"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|writeln
parameter_list|(
name|String
name|s
parameter_list|)
throws|throws
name|IOException
block|{
name|connect
argument_list|()
expr_stmt|;
name|write
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|newline
argument_list|()
expr_stmt|;
block|}
specifier|private
specifier|static
name|Random
name|random
init|=
operator|new
name|Random
argument_list|()
decl_stmt|;
specifier|protected
specifier|static
name|String
name|randomString
parameter_list|()
block|{
return|return
name|Long
operator|.
name|toString
argument_list|(
name|random
operator|.
name|nextLong
argument_list|()
argument_list|,
literal|36
argument_list|)
return|;
block|}
name|String
name|boundary
init|=
literal|"---------------------------"
operator|+
name|randomString
argument_list|()
operator|+
name|randomString
argument_list|()
operator|+
name|randomString
argument_list|()
decl_stmt|;
specifier|private
name|void
name|boundary
parameter_list|()
throws|throws
name|IOException
block|{
name|write
argument_list|(
literal|"--"
argument_list|)
expr_stmt|;
name|write
argument_list|(
name|boundary
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new multipart POST HTTP request on a freshly opened URLConnection      *      * @param connection an already open URL connection      *      * @throws IOException      */
specifier|public
name|ClientHttpRequest
parameter_list|(
name|URLConnection
name|connection
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|connection
operator|=
name|connection
expr_stmt|;
name|connection
operator|.
name|setDoOutput
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"multipart/form-data; boundary="
operator|+
name|boundary
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ClientHttpRequest
parameter_list|(
name|URLConnection
name|connection
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|pw
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|connection
operator|=
name|connection
expr_stmt|;
name|connection
operator|.
name|setDoOutput
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"multipart/form-data; boundary="
operator|+
name|boundary
argument_list|)
expr_stmt|;
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"Authorization "
argument_list|,
literal|"Basic "
operator|+
name|Base64
operator|.
name|encodeBase64
argument_list|(
operator|(
name|name
operator|+
literal|":"
operator|+
name|pw
operator|)
operator|.
name|getBytes
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new multipart POST HTTP request for a specified URL      *      * @param url the URL to send request to      *      * @throws IOException      */
specifier|public
name|ClientHttpRequest
parameter_list|(
name|URL
name|url
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|url
operator|.
name|openConnection
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new multipart POST HTTP request for a specified URL string      *      * @param urlString the string representation of the URL to send request to      *      * @throws IOException      */
specifier|public
name|ClientHttpRequest
parameter_list|(
name|String
name|urlString
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
operator|new
name|URL
argument_list|(
name|urlString
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|postCookies
parameter_list|()
block|{
name|StringBuffer
name|cookieList
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|cookies
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Map
operator|.
name|Entry
name|entry
init|=
call|(
name|Map
operator|.
name|Entry
call|)
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
name|cookieList
operator|.
name|append
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"="
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|cookieList
operator|.
name|append
argument_list|(
literal|"; "
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|cookieList
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"Cookie"
argument_list|,
name|cookieList
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Adds a cookie to the request.      *      * @param name cookie name      * @param value cookie value      *      * @throws IOException      */
specifier|public
name|void
name|setCookie
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
throws|throws
name|IOException
block|{
name|cookies
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds cookies to the request.      *      * @param cookies the cookie "name-to-value" map      *      * @throws IOException      */
specifier|public
name|void
name|setCookies
parameter_list|(
name|Map
name|cookies
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|cookies
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|this
operator|.
name|cookies
operator|.
name|putAll
argument_list|(
name|cookies
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds cookies to the request.      *      * @param cookies array of cookie names and values (cookies[2*i] is a name, cookies[2*i + 1] is a value)      *      * @throws IOException      */
specifier|public
name|void
name|setCookies
parameter_list|(
name|String
index|[]
name|cookies
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|cookies
operator|==
literal|null
condition|)
block|{
return|return;
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
name|cookies
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|+=
literal|2
control|)
block|{
name|setCookie
argument_list|(
name|cookies
index|[
name|i
index|]
argument_list|,
name|cookies
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|writeName
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|IOException
block|{
name|newline
argument_list|()
expr_stmt|;
name|write
argument_list|(
literal|"Content-Disposition: form-data; name=\""
argument_list|)
expr_stmt|;
name|write
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|write
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds a string parameter to the request.      *      * @param name parameter name      * @param value parameter value      *      * @throws IOException      */
specifier|public
name|void
name|setParameter
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
throws|throws
name|IOException
block|{
name|boundary
argument_list|()
expr_stmt|;
name|writeName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|newline
argument_list|()
expr_stmt|;
name|newline
argument_list|()
expr_stmt|;
name|writeln
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|void
name|pipe
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|OutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|byte
index|[]
name|buf
init|=
operator|new
name|byte
index|[
literal|500000
index|]
decl_stmt|;
name|int
name|navailable
decl_stmt|;
synchronized|synchronized
init|(
name|in
init|)
block|{
name|int
name|nread
decl_stmt|;
name|int
name|total
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|nread
operator|=
name|in
operator|.
name|read
argument_list|(
name|buf
argument_list|,
literal|0
argument_list|,
name|buf
operator|.
name|length
argument_list|)
operator|)
operator|>=
literal|0
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
name|nread
argument_list|)
expr_stmt|;
name|total
operator|+=
name|nread
expr_stmt|;
block|}
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|buf
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Adds a file parameter to the request.      *      * @param name parameter name      * @param filename the name of the file      * @param is input stream to read the contents of the file from      *      * @throws IOException      */
specifier|public
name|void
name|setParameter
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|filename
parameter_list|,
name|InputStream
name|is
parameter_list|)
throws|throws
name|IOException
block|{
name|boundary
argument_list|()
expr_stmt|;
name|writeName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|write
argument_list|(
literal|"; filename=\""
argument_list|)
expr_stmt|;
name|write
argument_list|(
name|filename
argument_list|)
expr_stmt|;
name|write
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
name|newline
argument_list|()
expr_stmt|;
name|write
argument_list|(
literal|"Content-Type: "
argument_list|)
expr_stmt|;
name|String
name|type
init|=
name|connection
operator|.
name|guessContentTypeFromName
argument_list|(
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
name|type
operator|=
literal|"application/octet-stream"
expr_stmt|;
block|}
name|writeln
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|newline
argument_list|()
expr_stmt|;
name|pipe
argument_list|(
name|is
argument_list|,
name|os
argument_list|)
expr_stmt|;
name|newline
argument_list|()
expr_stmt|;
block|}
comment|/**      * Adds a file parameter to the request.      *      * @param name parameter name      * @param file the file to upload      *      * @throws IOException      */
specifier|public
name|void
name|setParameter
parameter_list|(
name|String
name|name
parameter_list|,
name|File
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|setParameter
argument_list|(
name|name
argument_list|,
name|file
operator|.
name|getPath
argument_list|()
argument_list|,
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds a parameter to the request; if the parameter is a File, the file is uploaded,      * otherwise the string value of the parameter is passed in the request.      *      * @param name parameter name      * @param object parameter value, a File or anything else that can be stringified      *      * @throws IOException      */
specifier|public
name|void
name|setParameter
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|object
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|object
operator|instanceof
name|File
condition|)
block|{
name|setParameter
argument_list|(
name|name
argument_list|,
operator|(
name|File
operator|)
name|object
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setParameter
argument_list|(
name|name
argument_list|,
name|object
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Adds parameters to the request.      *      * @param parameters "name-to-value" map of parameters;      * if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request      *      * @throws IOException      */
specifier|public
name|void
name|setParameters
parameter_list|(
name|Map
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|parameters
operator|==
literal|null
condition|)
block|{
return|return;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|parameters
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Map
operator|.
name|Entry
name|entry
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|setParameter
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Adds parameters to the request.      *      * @param parameters array of parameter names and values (parameters[2*i] is a name,      * parameters[2*i + 1] is a value); if a value is a file, the file is uploaded,      * otherwise it is stringified and sent in the request      *      * @throws IOException      */
specifier|public
name|void
name|setParameters
parameter_list|(
name|Object
index|[]
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|parameters
operator|==
literal|null
condition|)
block|{
return|return;
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
name|parameters
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|+=
literal|2
control|)
block|{
name|setParameter
argument_list|(
name|parameters
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|,
name|parameters
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Posts the requests to the server, with all the cookies and parameters that were added.      *      * @return input stream with the server response      *      * @throws IOException      */
specifier|public
name|InputStream
name|post
parameter_list|()
throws|throws
name|IOException
block|{
name|boundary
argument_list|()
expr_stmt|;
name|writeln
argument_list|(
literal|"--"
argument_list|)
expr_stmt|;
name|os
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|connection
operator|.
name|getInputStream
argument_list|()
return|;
block|}
comment|/**      * Posts the requests to the server, with all the cookies and parameters      * that were added before (if any), and with parameters that are passed in the argument.      *      * @param parameters request parameters      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameters      */
specifier|public
name|InputStream
name|post
parameter_list|(
name|Map
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
name|setParameters
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
return|return
name|post
argument_list|()
return|;
block|}
comment|/**      * Posts the requests to the server, with all the cookies and parameters      * that were added before (if any), and with parameters that are passed in the argument.      *      * @param parameters request parameters      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameters      */
specifier|public
name|InputStream
name|post
parameter_list|(
name|Object
index|[]
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
name|setParameters
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
return|return
name|post
argument_list|()
return|;
block|}
comment|/**      * Posts the requests to the server, with all the cookies and parameters      * that were added before (if any), and with cookies and parameters that are passed in the arguments.      *      * @param cookies request cookies      * @param parameters request parameters      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameters      * @see setCookies      */
specifier|public
name|InputStream
name|post
parameter_list|(
name|Map
name|cookies
parameter_list|,
name|Map
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
name|setCookies
argument_list|(
name|cookies
argument_list|)
expr_stmt|;
name|setParameters
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
return|return
name|post
argument_list|()
return|;
block|}
comment|/**      * Posts the requests to the server, with all the cookies and parameters      * that were added before (if any), and with cookies and parameters that are passed in the arguments.      *      * @param cookies request cookies      * @param parameters request parameters      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameters      * @see setCookies      */
specifier|public
name|InputStream
name|post
parameter_list|(
name|String
index|[]
name|cookies
parameter_list|,
name|Object
index|[]
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
name|setCookies
argument_list|(
name|cookies
argument_list|)
expr_stmt|;
name|setParameters
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
return|return
name|post
argument_list|()
return|;
block|}
comment|/**      * Post the POST request to the server, with the specified parameter.      *      * @param name parameter name      * @param value parameter value      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameter      */
specifier|public
name|InputStream
name|post
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|value
parameter_list|)
throws|throws
name|IOException
block|{
name|setParameter
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
return|return
name|post
argument_list|()
return|;
block|}
comment|/**      * Post the POST request to the server, with the specified parameters.      *      * @param name1 first parameter name      * @param value1 first parameter value      * @param name2 second parameter name      * @param value2 second parameter value      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameter      */
specifier|public
name|InputStream
name|post
parameter_list|(
name|String
name|name1
parameter_list|,
name|Object
name|value1
parameter_list|,
name|String
name|name2
parameter_list|,
name|Object
name|value2
parameter_list|)
throws|throws
name|IOException
block|{
name|setParameter
argument_list|(
name|name1
argument_list|,
name|value1
argument_list|)
expr_stmt|;
return|return
name|post
argument_list|(
name|name2
argument_list|,
name|value2
argument_list|)
return|;
block|}
comment|/**      * Post the POST request to the server, with the specified parameters.      *      * @param name1 first parameter name      * @param value1 first parameter value      * @param name2 second parameter name      * @param value2 second parameter value      * @param name3 third parameter name      * @param value3 third parameter value      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameter      */
specifier|public
name|InputStream
name|post
parameter_list|(
name|String
name|name1
parameter_list|,
name|Object
name|value1
parameter_list|,
name|String
name|name2
parameter_list|,
name|Object
name|value2
parameter_list|,
name|String
name|name3
parameter_list|,
name|Object
name|value3
parameter_list|)
throws|throws
name|IOException
block|{
name|setParameter
argument_list|(
name|name1
argument_list|,
name|value1
argument_list|)
expr_stmt|;
return|return
name|post
argument_list|(
name|name2
argument_list|,
name|value2
argument_list|,
name|name3
argument_list|,
name|value3
argument_list|)
return|;
block|}
comment|/**      * Post the POST request to the server, with the specified parameters.      *      * @param name1 first parameter name      * @param value1 first parameter value      * @param name2 second parameter name      * @param value2 second parameter value      * @param name3 third parameter name      * @param value3 third parameter value      * @param name4 fourth parameter name      * @param value4 fourth parameter value      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameter      */
specifier|public
name|InputStream
name|post
parameter_list|(
name|String
name|name1
parameter_list|,
name|Object
name|value1
parameter_list|,
name|String
name|name2
parameter_list|,
name|Object
name|value2
parameter_list|,
name|String
name|name3
parameter_list|,
name|Object
name|value3
parameter_list|,
name|String
name|name4
parameter_list|,
name|Object
name|value4
parameter_list|)
throws|throws
name|IOException
block|{
name|setParameter
argument_list|(
name|name1
argument_list|,
name|value1
argument_list|)
expr_stmt|;
return|return
name|post
argument_list|(
name|name2
argument_list|,
name|value2
argument_list|,
name|name3
argument_list|,
name|value3
argument_list|,
name|name4
argument_list|,
name|value4
argument_list|)
return|;
block|}
comment|/**      * Posts a new request to specified URL, with parameters that are passed in the argument.      *      * @param parameters request parameters      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameters      */
specifier|public
specifier|static
name|InputStream
name|post
parameter_list|(
name|URL
name|url
parameter_list|,
name|Map
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|ClientHttpRequest
argument_list|(
name|url
argument_list|)
operator|.
name|post
argument_list|(
name|parameters
argument_list|)
return|;
block|}
comment|/**      * Posts a new request to specified URL, with parameters that are passed in the argument.      *      * @param parameters request parameters      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameters      */
specifier|public
specifier|static
name|InputStream
name|post
parameter_list|(
name|URL
name|url
parameter_list|,
name|Object
index|[]
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|ClientHttpRequest
argument_list|(
name|url
argument_list|)
operator|.
name|post
argument_list|(
name|parameters
argument_list|)
return|;
block|}
comment|/**      * Posts a new request to specified URL, with cookies and parameters that are passed in the argument.      *      * @param cookies request cookies      * @param parameters request parameters      *      * @return input stream with the server response      *      * @throws IOException      * @see setCookies      * @see setParameters      */
specifier|public
specifier|static
name|InputStream
name|post
parameter_list|(
name|URL
name|url
parameter_list|,
name|Map
name|cookies
parameter_list|,
name|Map
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|ClientHttpRequest
argument_list|(
name|url
argument_list|)
operator|.
name|post
argument_list|(
name|cookies
argument_list|,
name|parameters
argument_list|)
return|;
block|}
comment|/**      * Posts a new request to specified URL, with cookies and parameters that are passed in the argument.      *      * @param cookies request cookies      * @param parameters request parameters      *      * @return input stream with the server response      *      * @throws IOException      * @see setCookies      * @see setParameters      */
specifier|public
specifier|static
name|InputStream
name|post
parameter_list|(
name|URL
name|url
parameter_list|,
name|String
index|[]
name|cookies
parameter_list|,
name|Object
index|[]
name|parameters
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|ClientHttpRequest
argument_list|(
name|url
argument_list|)
operator|.
name|post
argument_list|(
name|cookies
argument_list|,
name|parameters
argument_list|)
return|;
block|}
comment|/**      * Post the POST request specified URL, with the specified parameter.      *      * @param name parameter name      * @param value parameter value      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameter      */
specifier|public
specifier|static
name|InputStream
name|post
parameter_list|(
name|URL
name|url
parameter_list|,
name|String
name|name1
parameter_list|,
name|Object
name|value1
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|ClientHttpRequest
argument_list|(
name|url
argument_list|)
operator|.
name|post
argument_list|(
name|name1
argument_list|,
name|value1
argument_list|)
return|;
block|}
comment|/**      * Post the POST request to specified URL, with the specified parameters.      *      * @param name1 first parameter name      * @param value1 first parameter value      * @param name2 second parameter name      * @param value2 second parameter value      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameter      */
specifier|public
specifier|static
name|InputStream
name|post
parameter_list|(
name|URL
name|url
parameter_list|,
name|String
name|name1
parameter_list|,
name|Object
name|value1
parameter_list|,
name|String
name|name2
parameter_list|,
name|Object
name|value2
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|ClientHttpRequest
argument_list|(
name|url
argument_list|)
operator|.
name|post
argument_list|(
name|name1
argument_list|,
name|value1
argument_list|,
name|name2
argument_list|,
name|value2
argument_list|)
return|;
block|}
comment|/**      * Post the POST request to specified URL, with the specified parameters.      *      * @param name1 first parameter name      * @param value1 first parameter value      * @param name2 second parameter name      * @param value2 second parameter value      * @param name3 third parameter name      * @param value3 third parameter value      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameter      */
specifier|public
specifier|static
name|InputStream
name|post
parameter_list|(
name|URL
name|url
parameter_list|,
name|String
name|name1
parameter_list|,
name|Object
name|value1
parameter_list|,
name|String
name|name2
parameter_list|,
name|Object
name|value2
parameter_list|,
name|String
name|name3
parameter_list|,
name|Object
name|value3
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|ClientHttpRequest
argument_list|(
name|url
argument_list|)
operator|.
name|post
argument_list|(
name|name1
argument_list|,
name|value1
argument_list|,
name|name2
argument_list|,
name|value2
argument_list|,
name|name3
argument_list|,
name|value3
argument_list|)
return|;
block|}
comment|/**      * Post the POST request to specified URL, with the specified parameters.      *      * @param name1 first parameter name      * @param value1 first parameter value      * @param name2 second parameter name      * @param value2 second parameter value      * @param name3 third parameter name      * @param value3 third parameter value      * @param name4 fourth parameter name      * @param value4 fourth parameter value      *      * @return input stream with the server response      *      * @throws IOException      * @see setParameter      */
specifier|public
specifier|static
name|InputStream
name|post
parameter_list|(
name|URL
name|url
parameter_list|,
name|String
name|name1
parameter_list|,
name|Object
name|value1
parameter_list|,
name|String
name|name2
parameter_list|,
name|Object
name|value2
parameter_list|,
name|String
name|name3
parameter_list|,
name|Object
name|value3
parameter_list|,
name|String
name|name4
parameter_list|,
name|Object
name|value4
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|ClientHttpRequest
argument_list|(
name|url
argument_list|)
operator|.
name|post
argument_list|(
name|name1
argument_list|,
name|value1
argument_list|,
name|name2
argument_list|,
name|value2
argument_list|,
name|name3
argument_list|,
name|value3
argument_list|,
name|name4
argument_list|,
name|value4
argument_list|)
return|;
block|}
block|}
end_class

end_unit

