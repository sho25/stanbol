begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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
name|servicesapi
operator|.
name|helper
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
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|LinkedHashMap
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
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|io
operator|.
name|IOUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|Blob
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|NoSuchPartException
import|;
end_import

begin_comment
comment|/**  * Helper class to factorize common code for ContentItem handling.  *  * @author ogrisel  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ContentItemHelper
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|ContentItemHelper
parameter_list|()
block|{}
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_CONTENT_ITEM_PREFIX
init|=
literal|"urn:content-item-"
decl_stmt|;
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
specifier|final
name|String
name|UTF8
init|=
literal|"UTF-8"
decl_stmt|;
comment|// TODO: instead of using a static helper, build an OSGi component with a
comment|// configurable site-wide URI namespace for ids that are local to the
comment|// server.
comment|/**      * Check that ContentItem#getId returns a valid URI or make an urn out of      * it.      */
specifier|public
specifier|static
name|IRI
name|ensureUri
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
name|String
name|uri
init|=
name|ci
operator|.
name|getUri
argument_list|()
operator|.
name|getUnicodeString
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|uri
operator|.
name|startsWith
argument_list|(
literal|"http://"
argument_list|)
operator|&&
operator|!
name|uri
operator|.
name|startsWith
argument_list|(
literal|"urn:"
argument_list|)
condition|)
block|{
name|uri
operator|=
literal|"urn:"
operator|+
name|urlEncode
argument_list|(
name|uri
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|IRI
argument_list|(
name|uri
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|urlEncode
parameter_list|(
name|String
name|uriPart
parameter_list|)
block|{
try|try
block|{
return|return
name|URLEncoder
operator|.
name|encode
argument_list|(
name|uriPart
argument_list|,
literal|"UTF-8"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
comment|// will never happen since every unicode symbol can be encoded
comment|// to UTF-8
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Pass the binary content from in to out (if not null) while computing the      * digest. Digest can typically be used to build ContentItem ids that map      * the binary content of the array.      *      * @param in stream to read the data from      * @param out optional output stream to      * @param digestAlgorithm MD5 or SHA1 for instance      * @return an hexadecimal representation of the digest      * @throws IOException      */
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
specifier|public
specifier|static
name|IRI
name|makeDefaultUrn
parameter_list|(
name|Blob
name|blob
parameter_list|)
block|{
return|return
name|makeDefaultUri
argument_list|(
name|DEFAULT_CONTENT_ITEM_PREFIX
argument_list|,
name|blob
operator|.
name|getStream
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|IRI
name|makeDefaultUrn
parameter_list|(
name|InputStream
name|in
parameter_list|)
block|{
return|return
name|makeDefaultUri
argument_list|(
name|DEFAULT_CONTENT_ITEM_PREFIX
argument_list|,
name|in
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|IRI
name|makeDefaultUrn
parameter_list|(
name|byte
index|[]
name|data
parameter_list|)
block|{
return|return
name|makeDefaultUri
argument_list|(
name|DEFAULT_CONTENT_ITEM_PREFIX
argument_list|,
operator|new
name|ByteArrayInputStream
argument_list|(
name|data
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|IRI
name|makeDefaultUri
parameter_list|(
name|String
name|baseUri
parameter_list|,
name|Blob
name|blob
parameter_list|)
block|{
return|return
name|makeDefaultUri
argument_list|(
name|baseUri
argument_list|,
name|blob
operator|.
name|getStream
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|IRI
name|makeDefaultUri
parameter_list|(
name|String
name|baseUri
parameter_list|,
name|byte
index|[]
name|data
parameter_list|)
block|{
return|return
name|makeDefaultUri
argument_list|(
name|baseUri
argument_list|,
operator|new
name|ByteArrayInputStream
argument_list|(
name|data
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|IRI
name|makeDefaultUri
parameter_list|(
name|String
name|baseUri
parameter_list|,
name|InputStream
name|in
parameter_list|)
block|{
comment|// calculate an ID based on the digest of the content
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
operator|+=
literal|"/"
expr_stmt|;
block|}
name|String
name|hexDigest
decl_stmt|;
try|try
block|{
name|hexDigest
operator|=
name|streamDigest
argument_list|(
name|in
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
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to read content for calculating"
operator|+
literal|"the hexDigest of the parsed content as used for the default URI"
operator|+
literal|"of an ContentItem!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|in
argument_list|)
expr_stmt|;
return|return
operator|new
name|IRI
argument_list|(
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
argument_list|)
return|;
block|}
comment|/**      * This parses and validates the mime-type and parameters from the      * parsed mimetype string based on the definition as defined in      *<a href="http://www.ietf.org/rfc/rfc2046.txt">rfc2046</a>.       *<p>      * The mime-type is stored as value for the<code>null</code>      * key. Parameter keys are converted to lower case. Values are stored as      * defined in the parsed media type. Parameters with empty key, empty or no      * values are ignored.      * @param mimeTypeString the media type formatted as defined by       *<a href="http://www.ietf.org/rfc/rfc2046.txt">rfc2046</a>      * @return A map containing the mime-type under the<code>null</code> key and       * all parameters with lower case keys and values.      * @throws IllegalArgumentException if the parsed mimeTypeString is      *<code>null</code>, empty or the parsed mime-type is empty, does not define      * non empty '{type}/{sub-type}' or uses a wildcard for the type or sub-type.      */
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parseMimeType
parameter_list|(
name|String
name|mimeTypeString
parameter_list|)
block|{
name|String
name|mimeType
decl_stmt|;
if|if
condition|(
name|mimeTypeString
operator|==
literal|null
operator|||
name|mimeTypeString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed mime-type MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parsed
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|StringTokenizer
name|tokens
init|=
operator|new
name|StringTokenizer
argument_list|(
name|mimeTypeString
argument_list|,
literal|";"
argument_list|)
decl_stmt|;
name|mimeType
operator|=
name|tokens
operator|.
name|nextToken
argument_list|()
expr_stmt|;
comment|//the first token is the mimeType
if|if
condition|(
name|mimeType
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed mime-type MUST NOT be empty"
operator|+
literal|"(mimeType='"
operator|+
name|mimeType
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|mimeType
operator|.
name|indexOf
argument_list|(
literal|'*'
argument_list|)
operator|>=
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed mime-type MUST NOT use"
operator|+
literal|"Wildcards (mimeType='"
operator|+
name|mimeType
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
name|String
index|[]
name|typeSubType
init|=
name|mimeType
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
if|if
condition|(
name|typeSubType
operator|.
name|length
operator|!=
literal|2
operator|||
name|typeSubType
index|[
literal|0
index|]
operator|.
name|isEmpty
argument_list|()
operator|||
name|typeSubType
index|[
literal|1
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed mime-type MUST define '{type}/{sub-type}'"
operator|+
literal|"and both MUST NOT be empty(mimeType='"
operator|+
name|mimeType
operator|+
literal|"')!"
argument_list|)
throw|;
block|}
name|parsed
operator|.
name|put
argument_list|(
literal|null
argument_list|,
name|mimeType
argument_list|)
expr_stmt|;
while|while
condition|(
name|tokens
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
comment|//parse the parameters (if any)
name|String
name|parameter
init|=
name|tokens
operator|.
name|nextToken
argument_list|()
decl_stmt|;
comment|//check if the parameter is valid formated and has a non empty value
name|int
name|nameValueSeparator
init|=
name|parameter
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
decl_stmt|;
if|if
condition|(
name|nameValueSeparator
operator|>
literal|0
operator|&&
name|parameter
operator|.
name|length
argument_list|()
operator|>
name|nameValueSeparator
operator|+
literal|2
condition|)
block|{
comment|//keys are case insensitive (we use lower case)
name|String
name|key
init|=
name|parameter
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|nameValueSeparator
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|parsed
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
comment|//do not override existing keys
name|parsed
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|parameter
operator|.
name|substring
argument_list|(
name|nameValueSeparator
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|parsed
return|;
block|}
comment|/**      * Searches an {@link ContentItem#getPart(IRI, Class) content part}      * of the type {@link Blob} with one of the the parsed mimeTypes.<p>      * NOTE:<ul>      *<li> MimeTypes are converted to lower case before compared with      * the entries of the parsed set. Therefore it is important that the parsed      * set only contains lower case values!      *<li> A read lock on the parsed {@link ContentItem} is applied while      * searching for a fitting {@link Blob}      *</ul><p>      * In contrast to the contentPart related methods of the {@link ContentItem}      * this method does NOT throw {@link NoSuchPartException}.      * @param ci the contentITem      * @param mimeTypes List of possible mimeTypes      * @return the {@link IRI URI} and the {@link Blob content} of the content       * part or<code>null</code> if not found      * @throws IllegalArgumentException If the parsed {@link ContentItem} is      *<code>null</code> or the parsed Set with the mimeTypes is<code>null</code>      * or {@link Set#isEmpty() empty}.      */
specifier|public
specifier|static
name|Entry
argument_list|<
name|IRI
argument_list|,
name|Blob
argument_list|>
name|getBlob
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|mimeTypes
parameter_list|)
block|{
if|if
condition|(
name|ci
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentItem MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|mimeTypes
operator|==
literal|null
operator|||
name|mimeTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Set with mime type  MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|IRI
name|cpUri
init|=
literal|null
decl_stmt|;
name|int
name|index
init|=
literal|0
decl_stmt|;
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
do|do
block|{
try|try
block|{
name|cpUri
operator|=
name|ci
operator|.
name|getPartUri
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|index
operator|++
expr_stmt|;
try|try
block|{
name|Blob
name|blob
init|=
name|ci
operator|.
name|getPart
argument_list|(
name|cpUri
argument_list|,
name|Blob
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|mimeTypes
operator|.
name|contains
argument_list|(
name|blob
operator|.
name|getMimeType
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Collections
operator|.
name|singletonMap
argument_list|(
name|cpUri
argument_list|,
name|blob
argument_list|)
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
return|;
block|}
comment|// else no match
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
comment|// not a Blob -> ignore!
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e
parameter_list|)
block|{
name|cpUri
operator|=
literal|null
expr_stmt|;
comment|// no more parts
block|}
block|}
do|while
condition|(
name|cpUri
operator|!=
literal|null
condition|)
do|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
comment|// not found
block|}
comment|/**      * Returns a Map with the current content parts of the parsed type. future       * changes to the contentParts of the content item will NOT be reflected      * within the returned map. The ordering of the {@link Iterator}s over the       * returned map is consistent with the ordering of the contentPart within the      * {@link ContentItem}.<p> When parsing {@link Object} as class the number      * of the element will be equals to the index of that content part.<p>      * In contrast to the contentPart related methods of the {@link ContentItem}      * this method does NOT throw {@link NoSuchPartException}.      * @param ci the content item      * @param clazz the class of the content part      * @return the Map with the {@link IRI id}s and the content as entries.      */
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|Map
argument_list|<
name|IRI
argument_list|,
name|T
argument_list|>
name|getContentParts
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
if|if
condition|(
name|ci
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentItem MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|LinkedHashMap
argument_list|<
name|IRI
argument_list|,
name|T
argument_list|>
name|blobs
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|IRI
argument_list|,
name|T
argument_list|>
argument_list|()
decl_stmt|;
name|IRI
name|cpUri
init|=
literal|null
decl_stmt|;
name|int
name|index
init|=
literal|0
decl_stmt|;
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
do|do
block|{
try|try
block|{
name|cpUri
operator|=
name|ci
operator|.
name|getPartUri
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|index
operator|++
expr_stmt|;
try|try
block|{
name|blobs
operator|.
name|put
argument_list|(
name|cpUri
argument_list|,
name|ci
operator|.
name|getPart
argument_list|(
name|cpUri
argument_list|,
name|clazz
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassCastException
name|e
parameter_list|)
block|{
comment|//not of type T -> skip
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e
parameter_list|)
block|{
name|cpUri
operator|=
literal|null
expr_stmt|;
comment|// no more parts
block|}
block|}
do|while
condition|(
name|cpUri
operator|!=
literal|null
condition|)
do|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
return|return
name|blobs
return|;
block|}
comment|/**      * Getter for the Text of an {@link Blob}. This method respects the      * "charset" if present in the {@link Blob#getParameter() parameter} of the      * Blob.      * @param blob the {@link Blob}. MUST NOT be<code>null</code>.      * @return the text      * @throws IOException on any exception while reading from the      * {@link InputStream} provided by the Blob.      * @throws IllegalArgumentException if the parsed Blob is<code>null</code>      */
specifier|public
specifier|static
name|String
name|getText
parameter_list|(
name|Blob
name|blob
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|blob
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Blob MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|String
name|charset
init|=
name|blob
operator|.
name|getParameter
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
decl_stmt|;
return|return
name|IOUtils
operator|.
name|toString
argument_list|(
name|blob
operator|.
name|getStream
argument_list|()
argument_list|,
name|charset
operator|!=
literal|null
condition|?
name|charset
else|:
name|UTF8
argument_list|)
return|;
block|}
comment|/**      * Creates the "{type}/{subtime}; [{param}={value}]+" mime type representation      * for the {@link Blob#getMimeType()} and {@link Blob#getParameter()} values      * @param blob the Blob      * @return the mime type with parameters (e.g.<code>      * text/plain;charset=UTF-8</code>)      */
specifier|public
specifier|static
name|String
name|getMimeTypeWithParameters
parameter_list|(
name|Blob
name|blob
parameter_list|)
block|{
name|StringBuilder
name|mimeType
init|=
operator|new
name|StringBuilder
argument_list|(
name|blob
operator|.
name|getMimeType
argument_list|()
argument_list|)
decl_stmt|;
comment|//ensure parameters are preserved
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|param
range|:
name|blob
operator|.
name|getParameter
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|mimeType
operator|.
name|append
argument_list|(
literal|"; "
argument_list|)
operator|.
name|append
argument_list|(
name|param
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|'='
argument_list|)
operator|.
name|append
argument_list|(
name|param
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|mimeType
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/*      * EnhancementProperties support for 0.12 (see STANBOL-1280)       */
comment|/**      * URI used to register an {@link ContentItem#getPart(int, Class) contentPart}      * of the type {@link Map Map&lt;String,Objext&gt;} containing the      * EnhancementEngine properties<p>      * @since 0.12.1      */
specifier|public
specifier|static
specifier|final
name|IRI
name|REQUEST_PROPERTIES_URI
init|=
operator|new
name|IRI
argument_list|(
literal|"urn:apache.org:stanbol.enhancer:request.properties"
argument_list|)
decl_stmt|;
comment|/**      * URI used to register the {@link #REQUEST_PROPERTIES_URI} until      *<code>0.12.0</code>      */
annotation|@
name|Deprecated
specifier|private
specifier|static
specifier|final
name|IRI
name|WEB_ENHANCEMENT_PROPERTIES_URI
init|=
operator|new
name|IRI
argument_list|(
literal|"urn:apache.org:stanbol.web:enhancement.properties"
argument_list|)
decl_stmt|;
comment|/**      * Getter for the content part holding the request scoped EnhancementProperties.      * @param ci the content item      * @return the content part or<code>null</code> if not present.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getRequestPropertiesContentPart
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
if|if
condition|(
name|ci
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentItem MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|ci
operator|.
name|getPart
argument_list|(
name|REQUEST_PROPERTIES_URI
argument_list|,
name|Map
operator|.
name|class
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e
parameter_list|)
block|{
comment|//fallback to support pre 0.12.1 modules (remove with 1.0.0)
try|try
block|{
return|return
name|ci
operator|.
name|getPart
argument_list|(
name|WEB_ENHANCEMENT_PROPERTIES_URI
argument_list|,
name|Map
operator|.
name|class
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e2
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Initialises the ContentPart holding the request scoped EnhancementProperties.      * If the content part is already present it will just return the existing. If      * not it will register an empty one. The content part is registered with      * the URI {@link #REQUEST_PROPERTIES_URI}      * @param ci the contentItem MUST NOT be NULL      * @return the enhancement properties      * @throws IllegalArgumentException if<code>null</code> is parsed as {@link ContentItem}.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|initRequestPropertiesContentPart
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
if|if
condition|(
name|ci
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentItem MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|enhancementProperties
decl_stmt|;
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|enhancementProperties
operator|=
name|ci
operator|.
name|getPart
argument_list|(
name|REQUEST_PROPERTIES_URI
argument_list|,
name|Map
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e
parameter_list|)
block|{
name|enhancementProperties
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|enhancementProperties
operator|==
literal|null
condition|)
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
comment|//check again ... maybe an other thread has added this part
name|enhancementProperties
operator|=
name|ci
operator|.
name|getPart
argument_list|(
name|REQUEST_PROPERTIES_URI
argument_list|,
name|Map
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e
parameter_list|)
block|{
comment|//fallback to support pre 0.12.1 modules (remove with 1.0.0)
try|try
block|{
comment|//NOTE: if the old is present we register it also with the new URI
name|enhancementProperties
operator|=
name|ci
operator|.
name|getPart
argument_list|(
name|WEB_ENHANCEMENT_PROPERTIES_URI
argument_list|,
name|Map
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e2
parameter_list|)
block|{
comment|/*ignore*/
block|}
comment|//END fallback
if|if
condition|(
name|enhancementProperties
operator|==
literal|null
condition|)
block|{
comment|//the old is not present
name|enhancementProperties
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
comment|//create
block|}
name|ci
operator|.
name|addPart
argument_list|(
name|REQUEST_PROPERTIES_URI
argument_list|,
name|enhancementProperties
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|//else was already present
return|return
name|enhancementProperties
return|;
block|}
comment|/**      * Sets a request scoped EnhancementProperty to the parsed contentItem. If      *<code>null</code> is parsed as value the property is removed.<p>      * This Method will retrieve the RequestProperties contentPart from the      * parsed {@link ContentItem} and adds the parsed property by applying the      *<code>[{engine-name}:]{key}</code> encoding to the key.<p>      * This method acquires a write lock on the ContentItem when writing      * the enhancement property.      * @param ci the ContentItem to set the enhancement property. MUST NOT be      *<code>null</code>      * @param engineName the engine or<code>null</code> to set the property for the      * chain.      * @param key the key of the property. MUST NOT be<code>null</code>      * @param value the value or<code>null</code> to remove the property      * @return the old value or<code>null</code> if the property was not present      * @throws IllegalArgumentException if<code>null</code> is parsed as      * {@link ContentItem} or key.      */
specifier|public
specifier|static
name|Object
name|setRequestProperty
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|String
name|engineName
parameter_list|,
name|String
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
if|if
condition|(
name|ci
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ContentItem MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|key
operator|==
literal|null
operator|||
name|key
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Enhancement Property key MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|enhProp
init|=
name|initRequestPropertiesContentPart
argument_list|(
name|ci
argument_list|)
decl_stmt|;
if|if
condition|(
name|engineName
operator|!=
literal|null
condition|)
block|{
name|key
operator|=
operator|new
name|StringBuilder
argument_list|(
name|engineName
argument_list|)
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
operator|.
name|append
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
return|return
name|enhProp
operator|.
name|remove
argument_list|(
name|key
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|enhProp
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
return|;
block|}
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

