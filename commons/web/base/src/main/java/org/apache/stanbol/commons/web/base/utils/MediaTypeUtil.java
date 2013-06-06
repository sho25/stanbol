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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|utils
package|;
end_package

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
operator|.
name|APPLICATION_JSON
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|N3
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|N_TRIPLE
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|RDF_JSON
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|RDF_XML
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|TURTLE
import|;
end_import

begin_import
import|import static
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
name|serializedform
operator|.
name|SupportedFormat
operator|.
name|X_TURTLE
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
name|Collection
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
name|HashSet
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
name|core
operator|.
name|HttpHeaders
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
name|core
operator|.
name|MediaType
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|MediaTypeUtil
block|{
specifier|private
name|MediaTypeUtil
parameter_list|()
block|{}
comment|/**      * Unmodifiable Set with the Media Types supported RDF serializations      */
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|SUPPORTED_RDF_TYPES
init|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|APPLICATION_JSON
argument_list|,
name|RDF_XML
argument_list|,
name|N3
argument_list|,
name|TURTLE
argument_list|,
name|X_TURTLE
argument_list|,
name|RDF_JSON
argument_list|,
name|N_TRIPLE
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * THe default RDF Type {@link MediaType#APPLICATION_JSON_TYPE} for JSON-LD      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|DEFAULT_RDF_TYPE
init|=
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
decl_stmt|;
comment|/**      * Searches the Header for acceptable media types and returns the first found      * that is not the wildcard type. If no one is found the parsed default type      * is returned.      *      * @param headers the request headers      * @param defaultType the default type if no or only the wildcard type was found in      * the header      * @return the acceptable media type      */
specifier|public
specifier|static
name|MediaType
name|getAcceptableMediaType
parameter_list|(
name|HttpHeaders
name|headers
parameter_list|,
name|MediaType
name|defaultType
parameter_list|)
block|{
name|MediaType
name|acceptedMediaType
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|MediaType
name|accepted
range|:
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|accepted
operator|.
name|isWildcardType
argument_list|()
condition|)
block|{
name|acceptedMediaType
operator|=
name|accepted
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|acceptedMediaType
operator|==
literal|null
condition|)
block|{
name|acceptedMediaType
operator|=
name|defaultType
expr_stmt|;
block|}
return|return
name|acceptedMediaType
return|;
block|}
comment|/**      * Checks the parsed MediaTypes for supported types. WildCards are not      * supported by this method. If no supported is found the defaultType      * is returned      * @param headers the headers of the request      * @param supported the supported types      * @param defaultType the default type used of no match is found      * @return the first supported media type part of the header or the default      * type       */
specifier|public
specifier|static
name|MediaType
name|getAcceptableMediaType
parameter_list|(
name|HttpHeaders
name|headers
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|supported
parameter_list|,
name|MediaType
name|defaultType
parameter_list|)
block|{
name|MediaType
name|acceptedMediaType
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|MediaType
name|accepted
range|:
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|accepted
operator|.
name|isWildcardType
argument_list|()
operator|&&
name|supported
operator|.
name|contains
argument_list|(
name|accepted
operator|.
name|getType
argument_list|()
operator|+
literal|'/'
operator|+
name|accepted
operator|.
name|getSubtype
argument_list|()
argument_list|)
condition|)
block|{
name|acceptedMediaType
operator|=
name|accepted
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|acceptedMediaType
operator|==
literal|null
condition|)
block|{
name|acceptedMediaType
operator|=
name|defaultType
expr_stmt|;
block|}
return|return
name|acceptedMediaType
return|;
block|}
comment|/**      * Checks if the parse mediaType is compatible with one of the Accept headers.      * Fully supports wildcard for both parsed Accept headers AND the parsed      * {@link MediaType}      * @param headers      * @param mediaType      * @return      */
specifier|public
specifier|static
name|boolean
name|isAcceptableMediaType
parameter_list|(
name|HttpHeaders
name|headers
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
if|if
condition|(
operator|!
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|MediaType
name|accepted
range|:
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
control|)
block|{
comment|//if one of the types is wildcard or types are equals AND
comment|// one of the subtypes is wildcard or subtypes are equals
if|if
condition|(
operator|(
name|accepted
operator|.
name|isWildcardType
argument_list|()
operator|||
name|mediaType
operator|.
name|isWildcardType
argument_list|()
operator|||
name|accepted
operator|.
name|getType
argument_list|()
operator|.
name|equals
argument_list|(
name|mediaType
operator|.
name|getType
argument_list|()
argument_list|)
operator|)
operator|&&
operator|(
name|accepted
operator|.
name|isWildcardSubtype
argument_list|()
operator|||
name|mediaType
operator|.
name|isWildcardSubtype
argument_list|()
operator|||
name|accepted
operator|.
name|getSubtype
argument_list|()
operator|.
name|equals
argument_list|(
name|mediaType
operator|.
name|getSubtype
argument_list|()
argument_list|)
operator|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isAcceptableMediaType
parameter_list|(
name|MediaType
name|mediaType
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|supported
parameter_list|)
block|{
if|if
condition|(
name|supported
operator|==
literal|null
operator|||
name|mediaType
operator|==
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
elseif|else
if|if
condition|(
name|supported
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|mediaType
operator|.
name|isWildcardType
argument_list|()
operator|&&
name|mediaType
operator|.
name|isWildcardSubtype
argument_list|()
return|;
block|}
else|else
block|{
name|String
name|type
init|=
name|mediaType
operator|.
name|getType
argument_list|()
operator|+
literal|'/'
operator|+
name|mediaType
operator|.
name|getSubtype
argument_list|()
decl_stmt|;
return|return
name|supported
operator|.
name|contains
argument_list|(
name|type
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

