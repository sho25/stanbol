begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to you under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License. You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|jsonld
operator|.
name|clerezza
package|;
end_package

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
name|OutputStream
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
name|Writer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|Dictionary
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
name|Graph
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
name|serializedform
operator|.
name|SerializingProvider
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
name|serializedform
operator|.
name|SupportedFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|ConfigurationPolicy
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|PropertyOption
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|JsonLdError
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|JsonLdOptions
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|JsonLdProcessor
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|utils
operator|.
name|JsonUtils
import|;
end_import

begin_comment
comment|/**  * A {@link org.apache.clerezza.rdf.core.serializedform.SerializingProvider} for   * JSON-LD (application/ld+json) based on the java-jsonld library  *   * @author Rupert Westenthaler  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|policy
operator|=
name|ConfigurationPolicy
operator|.
name|OPTIONAL
argument_list|)
annotation|@
name|Service
annotation|@
name|SupportedFormat
argument_list|(
name|value
operator|=
block|{
literal|"application/ld+json"
block|,
literal|"application/json"
block|}
argument_list|)
specifier|public
class|class
name|JsonLdSerializingProvider
implements|implements
name|SerializingProvider
block|{
specifier|private
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Charset
name|UTF8
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|MODE_EXPAND
init|=
literal|"expand"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|MODE_FLATTEN
init|=
literal|"flatten"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|MODE_COMPACT
init|=
literal|"compact"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|""
argument_list|,
name|options
operator|=
block|{
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|"%mode.option.none"
argument_list|,
name|name
operator|=
literal|""
argument_list|)
block|,
comment|//none (keep the default)
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|"%mode.option.flatten"
argument_list|,
name|name
operator|=
literal|"flatten"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|"%mode.option.compact"
argument_list|,
name|name
operator|=
literal|"compact"
argument_list|)
block|,
annotation|@
name|PropertyOption
argument_list|(
name|value
operator|=
literal|"%mode.option.expand"
argument_list|,
name|name
operator|=
name|MODE_EXPAND
argument_list|)
block|}
argument_list|)
specifier|private
specifier|static
specifier|final
name|String
name|PROP_MODE
init|=
literal|"mode"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|boolValue
operator|=
literal|false
argument_list|)
specifier|private
specifier|static
specifier|final
name|String
name|PROP_USE_RDF_TYPE
init|=
literal|"useRdfTye"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|boolValue
operator|=
literal|false
argument_list|)
specifier|private
specifier|static
specifier|final
name|String
name|PROP_USE_NATIVE_TYPES
init|=
literal|"useNativeTypes"
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|boolValue
operator|=
literal|true
argument_list|)
specifier|private
specifier|static
specifier|final
name|String
name|PROP_PRETTY_PRINT
init|=
literal|"prettyPrint"
decl_stmt|;
comment|//TODO: make configurable or read the whole prefix.cc list from a file and
comment|//      search for really used namespaces while parsing the triples in the
comment|//      ClerezzaRDFParser
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|DEFAULT_NAMESPACES
decl_stmt|;
static|static
block|{
comment|//core ontologies, top from prefixcc and some stanbol specific
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|ns
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|//core schemas
name|ns
operator|.
name|put
argument_list|(
literal|"xsd"
argument_list|,
literal|"http://www.w3.org/2001/XMLSchema#"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"owl"
argument_list|,
literal|"http://www.w3.org/2002/07/owl#"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"rdf"
argument_list|,
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"rdfs"
argument_list|,
literal|"http://www.w3.org/2000/01/rdf-schema#"
argument_list|)
expr_stmt|;
comment|//well known ontologies
name|ns
operator|.
name|put
argument_list|(
literal|"skos"
argument_list|,
literal|"http://www.w3.org/2004/02/skos/core#"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"geo"
argument_list|,
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"dc"
argument_list|,
literal|"http://purl.org/dc/elements/1.1/"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"foaf"
argument_list|,
literal|"http://xmlns.com/foaf/0.1/"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"ma"
argument_list|,
literal|"http://www.w3.org/ns/ma-ont#"
argument_list|)
expr_stmt|;
comment|//big datasets
name|ns
operator|.
name|put
argument_list|(
literal|"dbo"
argument_list|,
literal|"http://dbpedia.org/ontology/"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"dbp"
argument_list|,
literal|"http://dbpedia.org/property/"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"yago"
argument_list|,
literal|"http://yago-knowledge.org/resource/"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"fb"
argument_list|,
literal|"http://rdf.freebase.com/ns/"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"geonames"
argument_list|,
literal|"http://www.geonames.org/ontology#"
argument_list|)
expr_stmt|;
comment|//stanbol specific
name|ns
operator|.
name|put
argument_list|(
literal|"fise"
argument_list|,
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"enhancer"
argument_list|,
literal|"http://stanbol.apache.org/ontology/enhancer/enhancer#"
argument_list|)
expr_stmt|;
name|ns
operator|.
name|put
argument_list|(
literal|"entityhub"
argument_list|,
literal|"http://stanbol.apache.org/ontology/entityhub/entityhub#"
argument_list|)
expr_stmt|;
name|DEFAULT_NAMESPACES
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|ns
argument_list|)
expr_stmt|;
block|}
specifier|private
name|JsonLdOptions
name|opts
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|mode
decl_stmt|;
specifier|private
name|boolean
name|prettyPrint
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|serialize
parameter_list|(
name|OutputStream
name|serializedGraph
parameter_list|,
name|Graph
name|tc
parameter_list|,
name|String
name|formatIdentifier
parameter_list|)
block|{
name|ClerezzaRDFParser
name|serializer
init|=
operator|new
name|ClerezzaRDFParser
argument_list|()
decl_stmt|;
try|try
block|{
name|long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Object
name|output
init|=
name|JsonLdProcessor
operator|.
name|fromRDF
argument_list|(
name|tc
argument_list|,
name|serializer
argument_list|)
decl_stmt|;
if|if
condition|(
name|MODE_EXPAND
operator|.
name|equalsIgnoreCase
argument_list|(
name|mode
argument_list|)
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|" - mode: {}"
argument_list|,
name|MODE_EXPAND
argument_list|)
expr_stmt|;
name|output
operator|=
name|JsonLdProcessor
operator|.
name|expand
argument_list|(
name|output
argument_list|,
name|opts
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|MODE_FLATTEN
operator|.
name|equalsIgnoreCase
argument_list|(
name|mode
argument_list|)
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|" - mode: {}"
argument_list|,
name|MODE_FLATTEN
argument_list|)
expr_stmt|;
comment|// TODO: Allow inframe config
specifier|final
name|Object
name|inframe
init|=
literal|null
decl_stmt|;
name|output
operator|=
name|JsonLdProcessor
operator|.
name|flatten
argument_list|(
name|output
argument_list|,
name|inframe
argument_list|,
name|opts
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|MODE_COMPACT
operator|.
name|equalsIgnoreCase
argument_list|(
name|mode
argument_list|)
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|" - mode: {}"
argument_list|,
name|MODE_COMPACT
argument_list|)
expr_stmt|;
comment|//TODO: collect namespaces used in the triples in the ClerezzaRDFParser
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|localCtx
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|localCtx
operator|.
name|put
argument_list|(
literal|"@context"
argument_list|,
name|DEFAULT_NAMESPACES
argument_list|)
expr_stmt|;
name|output
operator|=
name|JsonLdProcessor
operator|.
name|compact
argument_list|(
name|output
argument_list|,
name|localCtx
argument_list|,
name|opts
argument_list|)
expr_stmt|;
block|}
name|Writer
name|writer
init|=
operator|new
name|OutputStreamWriter
argument_list|(
name|serializedGraph
argument_list|,
name|UTF8
argument_list|)
decl_stmt|;
name|logger
operator|.
name|debug
argument_list|(
literal|" - prettyPrint: {}"
argument_list|,
name|prettyPrint
argument_list|)
expr_stmt|;
if|if
condition|(
name|prettyPrint
condition|)
block|{
name|JsonUtils
operator|.
name|writePrettyPrint
argument_list|(
name|writer
argument_list|,
name|output
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JsonUtils
operator|.
name|write
argument_list|(
name|writer
argument_list|,
name|output
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|logger
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|" - serialized {} triples in {}ms"
argument_list|,
name|serializer
operator|.
name|getCount
argument_list|()
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|start
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|JsonLdError
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|opts
operator|=
operator|new
name|JsonLdOptions
argument_list|()
expr_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
decl_stmt|;
comment|//boolean properties
name|opts
operator|.
name|setUseRdfType
argument_list|(
name|getState
argument_list|(
name|config
operator|.
name|get
argument_list|(
name|PROP_USE_RDF_TYPE
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|opts
operator|.
name|setUseNativeTypes
argument_list|(
name|getState
argument_list|(
name|config
operator|.
name|get
argument_list|(
name|PROP_USE_NATIVE_TYPES
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|prettyPrint
operator|=
name|getState
argument_list|(
name|config
operator|.
name|get
argument_list|(
name|PROP_PRETTY_PRINT
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|//parse the string mode
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|PROP_MODE
argument_list|)
decl_stmt|;
name|mode
operator|=
name|value
operator|==
literal|null
condition|?
literal|null
else|:
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|opts
operator|=
literal|null
expr_stmt|;
name|mode
operator|=
literal|null
expr_stmt|;
name|prettyPrint
operator|=
literal|false
expr_stmt|;
block|}
comment|/**      * @param value      */
specifier|private
name|boolean
name|getState
parameter_list|(
name|Object
name|value
parameter_list|,
name|boolean
name|defaultState
parameter_list|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|Boolean
condition|)
block|{
return|return
operator|(
operator|(
name|Boolean
operator|)
name|value
operator|)
operator|.
name|booleanValue
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
return|return
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|defaultState
return|;
block|}
block|}
block|}
end_class

end_unit

