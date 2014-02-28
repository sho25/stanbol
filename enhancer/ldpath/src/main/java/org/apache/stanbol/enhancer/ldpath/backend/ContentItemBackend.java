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
name|ldpath
operator|.
name|backend
package|;
end_package

begin_import
import|import static
name|java
operator|.
name|util
operator|.
name|Collections
operator|.
name|emptyMap
import|;
end_import

begin_import
import|import static
name|java
operator|.
name|util
operator|.
name|Collections
operator|.
name|unmodifiableMap
import|;
end_import

begin_import
import|import static
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
operator|.
name|getContentParts
import|;
end_import

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigDecimal
import|;
end_import

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigInteger
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
name|Date
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
name|Locale
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ThreadPoolExecutor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|Lock
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
name|MGraph
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
name|Resource
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
name|clerezza
operator|.
name|rdf
operator|.
name|utils
operator|.
name|UnionMGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|marmotta
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
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
name|commons
operator|.
name|ldpath
operator|.
name|clerezza
operator|.
name|ClerezzaBackend
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
comment|/**  * Basically a {@link ClerezzaBackend} over {@link ContentItem#getMetadata()}  * that ensures read locks to be used for queries on subjects and objects.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|ContentItemBackend
implements|implements
name|RDFBackend
argument_list|<
name|Resource
argument_list|>
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ContentItemBackend
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
name|EMPTY_INCLUDED
init|=
name|emptyMap
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|ContentItem
name|ci
decl_stmt|;
specifier|private
specifier|final
name|Lock
name|readLock
decl_stmt|;
specifier|private
specifier|final
name|ClerezzaBackend
name|backend
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
name|included
decl_stmt|;
comment|/**      * Creates a {@link RDFBackend} over the {@link ContentItem#getMetadata()      * metadata} of the parsed content item.      * @param ci the content item      */
specifier|public
name|ContentItemBackend
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
name|this
argument_list|(
name|ci
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a {@link RDFBackend} over the {@link ContentItem#getMetadata()      * metadata} and all {@link ContentItem#getPart(int, Class) content parts}      * compatible to {@link TripleCollection}       * @param ci the content item      * @param includeAdditionalMetadata if<code>true</code> the {@link RDFBackend}      * will also include RDF data stored in content parts      */
specifier|public
name|ContentItemBackend
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|boolean
name|includeAdditionalMetadata
parameter_list|)
block|{
name|included
operator|=
name|includeAdditionalMetadata
condition|?
name|unmodifiableMap
argument_list|(
name|getContentParts
argument_list|(
name|ci
argument_list|,
name|TripleCollection
operator|.
name|class
argument_list|)
argument_list|)
else|:
name|EMPTY_INCLUDED
expr_stmt|;
name|MGraph
name|graph
decl_stmt|;
if|if
condition|(
name|included
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|graph
operator|=
name|ci
operator|.
name|getMetadata
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|TripleCollection
index|[]
name|tcs
init|=
operator|new
name|TripleCollection
index|[
name|included
operator|.
name|size
argument_list|()
operator|+
literal|1
index|]
decl_stmt|;
name|tcs
index|[
literal|0
index|]
operator|=
name|ci
operator|.
name|getMetadata
argument_list|()
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|included
operator|.
name|values
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|,
literal|0
argument_list|,
name|tcs
argument_list|,
literal|1
argument_list|,
name|included
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|graph
operator|=
operator|new
name|UnionMGraph
argument_list|(
name|tcs
argument_list|)
expr_stmt|;
block|}
name|backend
operator|=
operator|new
name|ClerezzaBackend
argument_list|(
name|graph
argument_list|)
expr_stmt|;
name|this
operator|.
name|ci
operator|=
name|ci
expr_stmt|;
name|this
operator|.
name|readLock
operator|=
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
expr_stmt|;
block|}
comment|/**      * Creates a {@link RDFBackend} over the {@link ContentItem#getMetadata()      * metadata} and RDF data stored in content parts with the parsed URIs.      * If no content part for a parsed URI exists or its type is not compatible      * to {@link TripleCollection} it will be not included.      * @param ci the content item      * @param includedMetadata the URIs for the content parts to include      */
specifier|public
name|ContentItemBackend
parameter_list|(
name|ContentItem
name|ci
parameter_list|,
name|Set
argument_list|<
name|UriRef
argument_list|>
name|includedMetadata
parameter_list|)
block|{
name|Map
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
name|included
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|UriRef
name|ref
range|:
name|includedMetadata
control|)
block|{
try|try
block|{
name|TripleCollection
name|metadata
init|=
name|ci
operator|.
name|getPart
argument_list|(
name|ref
argument_list|,
name|TripleCollection
operator|.
name|class
argument_list|)
decl_stmt|;
name|included
operator|.
name|put
argument_list|(
name|ref
argument_list|,
name|metadata
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to add requested Metadata-ContentPart "
operator|+
name|ref
operator|+
literal|" to"
operator|+
literal|"ContentItemBackend "
operator|+
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|this
operator|.
name|included
operator|=
name|unmodifiableMap
argument_list|(
name|included
argument_list|)
expr_stmt|;
name|MGraph
name|graph
decl_stmt|;
if|if
condition|(
operator|!
name|included
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|graph
operator|=
name|ci
operator|.
name|getMetadata
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|TripleCollection
index|[]
name|tcs
init|=
operator|new
name|TripleCollection
index|[
name|included
operator|.
name|size
argument_list|()
operator|+
literal|1
index|]
decl_stmt|;
name|tcs
index|[
literal|0
index|]
operator|=
name|ci
operator|.
name|getMetadata
argument_list|()
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|tcs
argument_list|,
literal|1
argument_list|,
name|included
operator|.
name|values
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|,
literal|0
argument_list|,
name|included
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|graph
operator|=
operator|new
name|UnionMGraph
argument_list|(
name|tcs
argument_list|)
expr_stmt|;
block|}
name|backend
operator|=
operator|new
name|ClerezzaBackend
argument_list|(
name|graph
argument_list|)
expr_stmt|;
name|this
operator|.
name|ci
operator|=
name|ci
expr_stmt|;
name|this
operator|.
name|readLock
operator|=
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Resource
argument_list|>
name|listObjects
parameter_list|(
name|Resource
name|subject
parameter_list|,
name|Resource
name|property
parameter_list|)
block|{
name|readLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|backend
operator|.
name|listObjects
argument_list|(
name|subject
argument_list|,
name|property
argument_list|)
return|;
block|}
finally|finally
block|{
name|readLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Resource
argument_list|>
name|listSubjects
parameter_list|(
name|Resource
name|property
parameter_list|,
name|Resource
name|object
parameter_list|)
block|{
name|readLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|backend
operator|.
name|listSubjects
argument_list|(
name|property
argument_list|,
name|object
argument_list|)
return|;
block|}
finally|finally
block|{
name|readLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the content item      * @return the content item      */
specifier|public
name|ContentItem
name|getContentItem
parameter_list|()
block|{
return|return
name|ci
return|;
block|}
comment|/**      * Getter for the read-only map of the content parts included in this      * RDF backend      * @return the content parts included in this {@link RDFBackend}      */
specifier|public
name|Map
argument_list|<
name|UriRef
argument_list|,
name|TripleCollection
argument_list|>
name|getIncludedMetadata
parameter_list|()
block|{
return|return
name|included
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isLiteral
parameter_list|(
name|Resource
name|n
parameter_list|)
block|{
return|return
name|backend
operator|.
name|isLiteral
argument_list|(
name|n
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isURI
parameter_list|(
name|Resource
name|n
parameter_list|)
block|{
return|return
name|backend
operator|.
name|isURI
argument_list|(
name|n
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isBlank
parameter_list|(
name|Resource
name|n
parameter_list|)
block|{
return|return
name|backend
operator|.
name|isBlank
argument_list|(
name|n
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Locale
name|getLiteralLanguage
parameter_list|(
name|Resource
name|n
parameter_list|)
block|{
return|return
name|backend
operator|.
name|getLiteralLanguage
argument_list|(
name|n
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|URI
name|getLiteralType
parameter_list|(
name|Resource
name|n
parameter_list|)
block|{
return|return
name|backend
operator|.
name|getLiteralType
argument_list|(
name|n
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|createLiteral
parameter_list|(
name|String
name|content
parameter_list|)
block|{
return|return
name|backend
operator|.
name|createLiteral
argument_list|(
name|content
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|createLiteral
parameter_list|(
name|String
name|content
parameter_list|,
name|Locale
name|language
parameter_list|,
name|URI
name|type
parameter_list|)
block|{
return|return
name|backend
operator|.
name|createLiteral
argument_list|(
name|content
argument_list|,
name|language
argument_list|,
name|type
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|createURI
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
return|return
name|backend
operator|.
name|createURI
argument_list|(
name|uri
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|stringValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|stringValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Double
name|doubleValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|doubleValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Long
name|longValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|longValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|booleanValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|booleanValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Date
name|dateTimeValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|dateTimeValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Date
name|dateValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|dateValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Date
name|timeValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|timeValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Float
name|floatValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|floatValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Integer
name|intValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|intValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|BigInteger
name|integerValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|integerValue
argument_list|(
name|node
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|BigDecimal
name|decimalValue
parameter_list|(
name|Resource
name|node
parameter_list|)
block|{
return|return
name|backend
operator|.
name|decimalValue
argument_list|(
name|node
argument_list|)
return|;
block|}
comment|/* NO SUPPORT FOR THREADING REQUIRED */
annotation|@
name|Override
specifier|public
name|boolean
name|supportsThreading
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|ThreadPoolExecutor
name|getThreadPool
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

