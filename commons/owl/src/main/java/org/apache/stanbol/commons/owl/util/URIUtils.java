begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|owl
operator|.
name|util
package|;
end_package

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
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * A collection of utility methods for manipulating strings that can be converted to URIs or IRIs.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|URIUtils
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|URIUtils
parameter_list|()
block|{}
comment|/**      * Converts a IRI to an IRI.      *       * @param uri      *            the IRI to convert      * @return the IRI form of the IRI      */
specifier|public
specifier|static
name|IRI
name|createIRI
parameter_list|(
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
name|uri
parameter_list|)
block|{
return|return
name|IRI
operator|.
name|create
argument_list|(
name|uri
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Converts an IRI to a IRI.      *       * @param uri      *            the IRI to convert      * @return the IRI form of the IRI      */
specifier|public
specifier|static
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
name|createIRI
parameter_list|(
name|IRI
name|uri
parameter_list|)
block|{
return|return
operator|new
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
argument_list|(
name|uri
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * URL-decodes any terminating hash characters ("%23"). Any non-terminating URL-encoded hashes will be      * left as they are (since there should be no intermediate hashes in a URL).      *       * @param iri      *            the IRI to desanitize      * @return the desanitized IRI      */
specifier|public
specifier|static
name|IRI
name|desanitize
parameter_list|(
name|IRI
name|iri
parameter_list|)
block|{
return|return
name|IRI
operator|.
name|create
argument_list|(
name|desanitize
argument_list|(
name|iri
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * URL-decodes any terminating hash characters ("%23"). Any non-terminating URL-encoded hashes will be      * left as they are (since there should be no intermediate hashes in a URL).      *       * @param iri      *            the IRI in string form to desanitize      * @return the desanitized IRI in string form      */
specifier|public
specifier|static
name|String
name|desanitize
parameter_list|(
name|String
name|iri
parameter_list|)
block|{
if|if
condition|(
name|iri
operator|==
literal|null
operator|||
name|iri
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot desanitize null IRI."
argument_list|)
throw|;
while|while
condition|(
name|iri
operator|.
name|endsWith
argument_list|(
literal|"%23"
argument_list|)
condition|)
name|iri
operator|=
name|iri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|iri
operator|.
name|length
argument_list|()
operator|-
literal|"%23"
operator|.
name|length
argument_list|()
argument_list|)
operator|+
literal|"#"
expr_stmt|;
return|return
name|iri
return|;
block|}
comment|/**      * Replaces terminating hash ('#') characters with their URL-encoded versions ("%23"). Any non-terminating      * hashes will be left as they are (though they are not to be expected if the IRI denotes an URL).      *       * @param iri      *            the IRI to sanitize      * @return the sanitized IRI      */
specifier|public
specifier|static
name|IRI
name|sanitize
parameter_list|(
name|IRI
name|iri
parameter_list|)
block|{
return|return
name|IRI
operator|.
name|create
argument_list|(
name|sanitize
argument_list|(
name|iri
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Replaces terminating hash ('#') characters with their URL-encoded versions ("%23"). Any non-terminating      * hashes will be left as they are (though they are not to be expected if the string denotes an URL).      *       * @param iri      *            the IRI in string form to sanitize      * @return the sanitized IRI in string form      */
specifier|public
specifier|static
name|String
name|sanitize
parameter_list|(
name|String
name|iri
parameter_list|)
block|{
if|if
condition|(
name|iri
operator|==
literal|null
operator|||
name|iri
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot sanitize null IRI."
argument_list|)
throw|;
while|while
condition|(
name|iri
operator|.
name|endsWith
argument_list|(
literal|"#"
argument_list|)
condition|)
name|iri
operator|=
name|iri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|iri
operator|.
name|length
argument_list|()
operator|-
literal|"#"
operator|.
name|length
argument_list|()
argument_list|)
operator|+
literal|"%23"
expr_stmt|;
return|return
name|iri
return|;
block|}
comment|/**      * Removes either the fragment, or query, or last path component from a URI, whatever it finds first.      *       * @param iri      * @return      */
specifier|public
specifier|static
name|IRI
name|upOne
parameter_list|(
name|IRI
name|iri
parameter_list|)
block|{
return|return
name|upOne
argument_list|(
name|iri
operator|.
name|toURI
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Removes either the fragment, or query, or last path component from a URI, whatever it finds first.      *       * @param uri      * @return      */
specifier|public
specifier|static
name|IRI
name|upOne
parameter_list|(
name|URI
name|uri
parameter_list|)
block|{
name|int
name|index
init|=
operator|-
literal|1
decl_stmt|;
name|String
name|tmpstr
init|=
name|uri
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// Strip the fragment
name|String
name|frag
init|=
name|uri
operator|.
name|getFragment
argument_list|()
decl_stmt|;
if|if
condition|(
name|frag
operator|!=
literal|null
operator|&&
operator|!
name|frag
operator|.
name|isEmpty
argument_list|()
condition|)
name|index
operator|=
name|tmpstr
operator|.
name|length
argument_list|()
operator|-
name|frag
operator|.
name|length
argument_list|()
operator|-
literal|1
expr_stmt|;
else|else
block|{
comment|// Strip the query
name|frag
operator|=
name|uri
operator|.
name|getQuery
argument_list|()
expr_stmt|;
if|if
condition|(
name|frag
operator|!=
literal|null
operator|&&
operator|!
name|frag
operator|.
name|isEmpty
argument_list|()
condition|)
name|index
operator|=
name|tmpstr
operator|.
name|length
argument_list|()
operator|-
name|frag
operator|.
name|length
argument_list|()
operator|-
literal|1
expr_stmt|;
else|else
block|{
comment|// Strip the slash part
name|frag
operator|=
name|uri
operator|.
name|getPath
argument_list|()
expr_stmt|;
if|if
condition|(
name|frag
operator|!=
literal|null
operator|&&
operator|!
name|frag
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|int
name|i
init|=
name|frag
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
decl_stmt|;
name|boolean
name|trimslash
init|=
literal|false
decl_stmt|;
comment|// If it ends with a slash, remove that too
if|if
condition|(
name|i
operator|==
name|frag
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
block|{
name|trimslash
operator|=
literal|true
expr_stmt|;
name|frag
operator|=
name|frag
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
name|index
operator|=
name|tmpstr
operator|.
name|length
argument_list|()
operator|-
name|frag
operator|.
name|length
argument_list|()
operator|+
name|frag
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
operator|+
operator|(
name|trimslash
condition|?
operator|-
literal|1
else|:
literal|0
operator|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
return|return
name|IRI
operator|.
name|create
argument_list|(
name|tmpstr
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
argument_list|)
return|;
else|else
return|return
name|IRI
operator|.
name|create
argument_list|(
name|uri
argument_list|)
return|;
block|}
block|}
end_class

end_unit

