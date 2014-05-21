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
name|jersey
operator|.
name|utils
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|QueryParam
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
name|rdf
operator|.
name|core
operator|.
name|TripleCollection
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
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|helper
operator|.
name|ContentItemHelper
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
name|rdf
operator|.
name|ExecutionMetadata
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
name|rdf
operator|.
name|ExecutionPlan
import|;
end_import

begin_comment
comment|/**  * Defines Constants and utilities for using request scoped EnhancementProperties.  * Especially those internally used by the enhancer.jersey module.<p>  * This replaces the {@link EnhancementPropertiesHelper}  * @since 0.12.1  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|RequestPropertiesHelper
block|{
specifier|private
name|RequestPropertiesHelper
parameter_list|()
block|{
comment|/* no instances allowed*/
block|}
comment|/**      * @see ContentItemHelper#REQUEST_PROPERTIES_URI      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|REQUEST_PROPERTIES_URI
init|=
name|ContentItemHelper
operator|.
name|REQUEST_PROPERTIES_URI
decl_stmt|;
comment|/**      * Boolean switch parsed as {@link QueryParam} tha allows to deactivate the      * inclusion of the {@link ContentItem#getMetadata()} in the Response      */
specifier|public
specifier|static
specifier|final
name|String
name|OMIT_METADATA
init|=
literal|"stanbol.enhancer.web.omitMetadata"
decl_stmt|;
comment|/**      * {@link Set Set&lt;String&gt;} containing all the URIs of the      * {@link ContentItem#getPart(UriRef, Class) ContentParts} representing       * RDF data (compatible to Clerezza {@link TripleCollection}). If the       * returned set contains '*' than all such content parts need to be returned.<p>      * NOTE: This can also be used to include the Request Properties      * as "applciation/json" in the Response by adding this      * {@link RequestPropertiesHelper#REQUEST_PROPERTIES_URI uri}.      */
specifier|public
specifier|static
specifier|final
name|String
name|OUTPUT_CONTENT_PART
init|=
literal|"stanbol.enhancer.web.outputContentPart"
decl_stmt|;
comment|/**      * Allows to omit all parsed content parts regardless of the {@link #OUTPUT_CONTENT_PART}      * configuration      */
specifier|public
specifier|static
specifier|final
name|String
name|OMIT_PARSED_CONTENT
init|=
literal|"stanbol.enhancer.web.omitParsed"
decl_stmt|;
comment|/**      * {@link Collection Collection&lt;String&gt;} containing mime types. This      * allows to specify what versions of the parsed content to be included in      * the response. e.g. ["text/*","application/pdf"] would include all text      * formats and PDF.      */
specifier|public
specifier|static
specifier|final
name|String
name|OUTPUT_CONTENT
init|=
literal|"stanbol.enhancer.web.outputContent"
decl_stmt|;
comment|/**      * This allows to copy the {@link ExecutionMetadata} and {@link ExecutionPlan}      * data stored in a {@link ContentItem#getPart(UriRef, Class) contentPart} with      * the URI {@link ExecutionMetadata#CHAIN_EXECUTION} over to the      * {@link ContentItem#getMetadata() metadata} of the content item.<p>      * This feature is intended to allow users to retrieve such meta information      * without the need to use parse Multipart MIME responses.      */
specifier|public
specifier|static
specifier|final
name|String
name|INCLUDE_EXECUTION_METADATA
init|=
literal|"stanbol.enhancer.web.executionmetadata"
decl_stmt|;
comment|/**      * The used format to encode RDF graphs for "multipart/*" responses. This      * needs to be parsed separately, because the Accept header needs to be      * set to "multipart/from-data" in such cases      */
specifier|public
specifier|static
specifier|final
name|String
name|RDF_FORMAT
init|=
literal|"stanbol.enhancer.web.rdfFormat"
decl_stmt|;
comment|/**      * {@link Set Set&lt;String&gt;} containing all the {@link UriRef}s of       * {@link ContentItem#getPart(int, Class) ContentItem.getPart}(uri,{@link Blob})      * that where parsed with the request.      */
specifier|public
specifier|static
specifier|final
name|String
name|PARSED_CONTENT_URIS
init|=
literal|"stanbol.enhancer.web.parsedContentURIs"
decl_stmt|;
specifier|private
specifier|static
name|Object
name|get
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|,
name|String
name|key
parameter_list|)
block|{
return|return
name|reqProp
operator|==
literal|null
condition|?
literal|null
else|:
name|reqProp
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
comment|/**      * Getter for the value of the parsed type for a given key.      * @param reqProp the request properties      * @param key the key      * @param type the type MUST NOT be<code>null</code>      * @return the values or<code>null</code> if the parsed request properties      * where<code>null</code> or the parsed key was not present.      * @throws ClassCastException if the value is not compatible to the      * parsed type      * @throws NullPointerException if the parsed key or type is<code>null</code>      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|T
name|get
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|,
name|String
name|key
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
name|reqProp
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"The parsed type MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|key
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"The parsed key MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|Object
name|value
init|=
name|get
argument_list|(
name|reqProp
argument_list|,
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|value
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
return|return
operator|(
name|T
operator|)
name|value
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"RequestProperties value for key '"
operator|+
name|key
operator|+
literal|"' is not of the expected type "
operator|+
name|type
operator|+
literal|" but was"
operator|+
name|value
operator|.
name|getClass
argument_list|()
argument_list|)
throw|;
block|}
block|}
comment|/**      * Getter for the boolean state based on the value of the parsed key.      * If the value is not of type {@link Boolean} the       * {@link Boolean#parseBoolean(String)} is used on the {@link Object#toString()}      * method of the value.      * @param reqProp the request properties      * @param key the key      * @return the state.<code>false</code> if the parsed request property      * map was<code>null</code> or the key was not present.      * @throw {@link NullPointerException} if<code>null</code> is parsed as key      */
specifier|public
specifier|static
name|boolean
name|getState
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|,
name|String
name|key
parameter_list|)
block|{
if|if
condition|(
name|key
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"The parsed key MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|reqProp
operator|==
literal|null
condition|)
block|{
return|return
name|Boolean
operator|.
name|FALSE
return|;
block|}
else|else
block|{
name|Object
name|state
init|=
name|get
argument_list|(
name|reqProp
argument_list|,
name|key
argument_list|)
decl_stmt|;
return|return
name|state
operator|==
literal|null
condition|?
literal|false
else|:
name|state
operator|instanceof
name|Boolean
condition|?
operator|(
operator|(
name|Boolean
operator|)
name|state
operator|)
operator|.
name|booleanValue
argument_list|()
else|:
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|state
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/**      * Checks the request properties for the {@link #OMIT_PARSED_CONTENT} state      */
specifier|public
specifier|static
name|boolean
name|isOmitParsedContent
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|)
block|{
return|return
name|getState
argument_list|(
name|reqProp
argument_list|,
name|OMIT_PARSED_CONTENT
argument_list|)
return|;
block|}
comment|/**      * Checks the request properties for the {@link #INCLUDE_EXECUTION_METADATA} state      */
specifier|public
specifier|static
name|boolean
name|isIncludeExecutionMetadata
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|)
block|{
return|return
name|getState
argument_list|(
name|reqProp
argument_list|,
name|INCLUDE_EXECUTION_METADATA
argument_list|)
return|;
block|}
comment|/**      * Checks the request properties for the {@link #OMIT_METADATA} state      */
specifier|public
specifier|static
name|boolean
name|isOmitMetadata
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|)
block|{
return|return
name|getState
argument_list|(
name|reqProp
argument_list|,
name|OMIT_METADATA
argument_list|)
return|;
block|}
comment|/**      * Getter for the {@link #PARSED_CONTENT_URIS}      * @param reqProp      * @return      * @throws ClassCastException if the value is not a {@link Collection}      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|getParsedContentURIs
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|)
block|{
return|return
operator|(
name|Collection
argument_list|<
name|String
argument_list|>
operator|)
name|get
argument_list|(
name|reqProp
argument_list|,
name|PARSED_CONTENT_URIS
argument_list|,
name|Collection
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Getter for the {@link #OUTPUT_CONTENT_PART}      * @param reqProp      * @return      * @throws ClassCastException if the value is not an {@link Collection}      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|getOutputContentParts
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|)
block|{
return|return
operator|(
name|Collection
argument_list|<
name|String
argument_list|>
operator|)
name|get
argument_list|(
name|reqProp
argument_list|,
name|OUTPUT_CONTENT_PART
argument_list|,
name|Collection
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Getter for the {@link #OUTPUT_CONTENT}      * @param reqProp      * @return      * @throws ClassCastException if the value is not a {@link Collection}      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|getOutputContent
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|)
block|{
return|return
operator|(
name|Collection
argument_list|<
name|String
argument_list|>
operator|)
name|get
argument_list|(
name|reqProp
argument_list|,
name|OUTPUT_CONTENT
argument_list|,
name|Collection
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Getter for the {@link #RDF_FORMAT}      * @param reqProp      * @return      * @throws ClassCastException if the value is not a {@link String}      */
specifier|public
specifier|static
name|String
name|getRdfFormat
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|reqProp
parameter_list|)
block|{
return|return
name|get
argument_list|(
name|reqProp
argument_list|,
name|RDF_FORMAT
argument_list|,
name|String
operator|.
name|class
argument_list|)
return|;
block|}
block|}
end_class

end_unit

