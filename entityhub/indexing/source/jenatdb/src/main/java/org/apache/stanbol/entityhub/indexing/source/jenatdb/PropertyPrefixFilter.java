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
name|entityhub
operator|.
name|indexing
operator|.
name|source
operator|.
name|jenatdb
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
name|Comparator
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
name|List
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
name|TreeMap
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
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespaceMappingUtils
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
name|namespaceprefix
operator|.
name|NamespacePrefixProvider
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
name|namespaceprefix
operator|.
name|NamespacePrefixService
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|config
operator|.
name|IndexingConfig
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

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|graph
operator|.
name|Node
import|;
end_import

begin_class
specifier|public
class|class
name|PropertyPrefixFilter
implements|implements
name|RdfImportFilter
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
name|PropertyPrefixFilter
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Links to a file that defines included& excluded properties (one per line)<p>      *<b>Syntax</b>      *<ul>      *<li>Lines starting with '#' are ignored      *<li>'!{prefix}' will exclude all properties starting with the {prefix}.      *<li>'{prefix}' will include all properties starting with {prefix}      *<li>'*' will include all properties not explicitly excluded      *<li> Namespace prefixes are supported!      *<li> '{prefix}*' is also supported. However all {prefix} values are      * interpreted like that.      *</ul>      *<b>NOTES</b>: (1) Longer prefixes are matched first. (1) All processed       * values are stored in-memory. That means that matching prefixes are only       * calculate on the first appearance of an property.       */
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_PROPERTY_FILTERS
init|=
literal|"if-property-filter"
decl_stmt|;
specifier|public
name|PropertyPrefixFilter
parameter_list|()
block|{}
comment|/**      * For unit tests only      * @param nsPrefixService      * @param lines      */
specifier|protected
name|PropertyPrefixFilter
parameter_list|(
name|NamespacePrefixProvider
name|nsPrefixService
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|lines
parameter_list|)
block|{
name|parsePropertyPrefixConfig
argument_list|(
name|nsPrefixService
argument_list|,
name|lines
argument_list|)
expr_stmt|;
block|}
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|propertyPrefixMap
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|propertyMap
decl_stmt|;
specifier|private
name|boolean
name|includeAll
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
block|{
name|IndexingConfig
name|indexingConfig
init|=
operator|(
name|IndexingConfig
operator|)
name|config
operator|.
name|get
argument_list|(
name|IndexingConfig
operator|.
name|KEY_INDEXING_CONFIG
argument_list|)
decl_stmt|;
name|NamespacePrefixService
name|nsPrefixService
init|=
name|indexingConfig
operator|.
name|getNamespacePrefixService
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Configure {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|)
expr_stmt|;
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|PARAM_PROPERTY_FILTERS
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|propertyPrefixMap
operator|=
name|Collections
operator|.
name|emptyMap
argument_list|()
expr_stmt|;
name|propertyMap
operator|=
name|Collections
operator|.
name|emptyMap
argument_list|()
expr_stmt|;
name|includeAll
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> property Prefix Filters"
argument_list|)
expr_stmt|;
comment|//ensure that longer prefixes are first
name|File
name|propertyPrefixConfig
init|=
name|indexingConfig
operator|.
name|getConfigFile
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|lines
decl_stmt|;
name|InputStream
name|in
init|=
literal|null
decl_stmt|;
try|try
block|{
name|in
operator|=
operator|new
name|FileInputStream
argument_list|(
name|propertyPrefixConfig
argument_list|)
expr_stmt|;
name|lines
operator|=
name|IOUtils
operator|.
name|readLines
argument_list|(
name|in
argument_list|,
literal|"UTF-8"
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
name|IllegalArgumentException
argument_list|(
literal|"Unable to read property filter configuration "
operator|+
literal|"from the configured File "
operator|+
name|propertyPrefixConfig
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
name|IOUtils
operator|.
name|closeQuietly
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
name|parsePropertyPrefixConfig
argument_list|(
name|nsPrefixService
argument_list|,
name|lines
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param nsPrefixService      * @param propertyPrefixConfig      */
specifier|private
name|void
name|parsePropertyPrefixConfig
parameter_list|(
name|NamespacePrefixProvider
name|nsPrefixService
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|lines
parameter_list|)
block|{
name|propertyPrefixMap
operator|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|String
name|o1
parameter_list|,
name|String
name|o2
parameter_list|)
block|{
name|int
name|length
init|=
name|o2
operator|.
name|length
argument_list|()
operator|-
name|o1
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|length
operator|!=
literal|0
condition|)
block|{
return|return
name|length
return|;
block|}
else|else
block|{
return|return
name|o1
operator|.
name|compareTo
argument_list|(
name|o2
argument_list|)
return|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|propertyMap
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
argument_list|()
expr_stmt|;
name|includeAll
operator|=
name|lines
operator|.
name|remove
argument_list|(
literal|"*"
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"    - includeAll: {}"
argument_list|,
name|includeAll
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|line
range|:
name|lines
control|)
block|{
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
literal|"#"
argument_list|)
operator|||
name|line
operator|.
name|isEmpty
argument_list|()
operator|||
name|line
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
continue|continue;
comment|//ignore comment, empty lines and multiple '*'
block|}
name|boolean
name|exclude
init|=
name|line
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'!'
decl_stmt|;
name|String
name|prefix
init|=
name|exclude
condition|?
name|line
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
else|:
name|line
decl_stmt|;
name|prefix
operator|=
name|prefix
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|includeAll
operator|&&
operator|!
name|exclude
condition|)
block|{
continue|continue;
comment|//ignore includes if * is active
block|}
name|String
name|uri
decl_stmt|;
name|String
name|nsPrefix
init|=
name|NamespaceMappingUtils
operator|.
name|getPrefix
argument_list|(
name|prefix
argument_list|)
decl_stmt|;
if|if
condition|(
name|nsPrefix
operator|!=
literal|null
condition|)
block|{
name|String
name|ns
init|=
name|nsPrefixService
operator|.
name|getNamespace
argument_list|(
name|nsPrefix
argument_list|)
decl_stmt|;
if|if
condition|(
name|ns
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to resolve namesoace prefix used by '"
operator|+
name|prefix
operator|+
literal|"' by using the NamespacePrefixService!"
argument_list|)
throw|;
block|}
name|uri
operator|=
operator|new
name|StringBuilder
argument_list|(
name|ns
argument_list|)
operator|.
name|append
argument_list|(
name|prefix
argument_list|,
name|nsPrefix
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|,
name|prefix
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|uri
operator|=
name|prefix
expr_stmt|;
block|}
if|if
condition|(
name|uri
operator|.
name|charAt
argument_list|(
name|uri
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'*'
condition|)
block|{
name|uri
operator|=
name|uri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|uri
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|info
argument_list|(
literal|"    - '{}' {}"
argument_list|,
name|uri
argument_list|,
name|exclude
condition|?
literal|"excluded"
else|:
literal|"included"
argument_list|)
expr_stmt|;
name|propertyPrefixMap
operator|.
name|put
argument_list|(
name|uri
argument_list|,
operator|!
name|exclude
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|initialise
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Node
name|s
parameter_list|,
name|Node
name|p
parameter_list|,
name|Node
name|o
parameter_list|)
block|{
if|if
condition|(
name|p
operator|.
name|isURI
argument_list|()
condition|)
block|{
if|if
condition|(
name|includeAll
operator|&&
name|propertyPrefixMap
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
name|String
name|property
init|=
name|p
operator|.
name|getURI
argument_list|()
decl_stmt|;
name|Boolean
name|state
init|=
name|propertyMap
operator|.
name|get
argument_list|(
name|property
argument_list|)
decl_stmt|;
if|if
condition|(
name|state
operator|!=
literal|null
condition|)
block|{
return|return
name|state
return|;
block|}
comment|//first time we encounter this property ... need to calculate
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|entry
range|:
name|propertyPrefixMap
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|property
operator|.
name|startsWith
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|propertyMap
operator|.
name|put
argument_list|(
name|property
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|entry
operator|.
name|getValue
argument_list|()
return|;
block|}
block|}
comment|//no match ... set based on includeAll
name|propertyMap
operator|.
name|put
argument_list|(
name|property
argument_list|,
name|includeAll
argument_list|)
expr_stmt|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

