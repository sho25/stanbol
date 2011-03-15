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
name|jersey
operator|.
name|resource
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
name|APPLICATION_FORM_URLENCODED
import|;
end_import

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
name|APPLICATION_JSON_TYPE
import|;
end_import

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
name|TEXT_HTML
import|;
end_import

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
name|TEXT_PLAIN
import|;
end_import

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
name|WILDCARD
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
name|ArrayList
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
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
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
name|Consumes
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
name|FormParam
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
name|GET
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
name|POST
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
name|Path
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
name|Produces
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
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Context
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
name|Response
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
name|UriBuilder
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
name|access
operator|.
name|TcManager
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
name|Serializer
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
name|jersey
operator|.
name|cache
operator|.
name|EntityCacheProvider
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
name|EngineException
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
name|EnhancementEngine
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
name|EnhancementJobManager
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
name|InMemoryContentItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONArray
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
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|ImplicitProduces
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|Viewable
import|;
end_import

begin_comment
comment|/**  * RESTful interface to browse the list of available engines and allow to call  * them in a stateless, synchronous way.  *<p>  * If you need the content of the extractions to be stored on the server, use  * the StoreRootResource API instead.  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/engines"
argument_list|)
annotation|@
name|ImplicitProduces
argument_list|(
name|TEXT_HTML
operator|+
literal|";qs=2"
argument_list|)
specifier|public
class|class
name|EnginesRootResource
extends|extends
name|NavigationMixin
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|protected
name|EnhancementJobManager
name|jobManager
decl_stmt|;
specifier|protected
name|TcManager
name|tcManager
decl_stmt|;
specifier|protected
name|Serializer
name|serializer
decl_stmt|;
specifier|protected
name|TripleCollection
name|entityCache
decl_stmt|;
comment|// bind the job manager by looking it up from the servlet request context
specifier|public
name|EnginesRootResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|jobManager
operator|=
operator|(
name|EnhancementJobManager
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|EnhancementJobManager
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|tcManager
operator|=
operator|(
name|TcManager
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|TcManager
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|serializer
operator|=
operator|(
name|Serializer
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|Serializer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|EntityCacheProvider
name|entityCacheProvider
init|=
operator|(
name|EntityCacheProvider
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|EntityCacheProvider
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|entityCacheProvider
operator|!=
literal|null
condition|)
block|{
name|entityCache
operator|=
name|entityCacheProvider
operator|.
name|getEntityCache
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|getActiveEngines
parameter_list|()
block|{
if|if
condition|(
name|jobManager
operator|!=
literal|null
condition|)
block|{
return|return
name|jobManager
operator|.
name|getActiveEngines
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
specifier|private
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|getEngines
parameter_list|()
block|{
if|if
condition|(
name|jobManager
operator|!=
literal|null
condition|)
block|{
return|return
name|jobManager
operator|.
name|getActiveEngines
argument_list|()
return|;
block|}
return|return
operator|new
name|ArrayList
argument_list|<
name|EnhancementEngine
argument_list|>
argument_list|()
return|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|APPLICATION_JSON
argument_list|)
specifier|public
name|JSONArray
name|getEnginesAsJsonArray
parameter_list|()
block|{
name|JSONArray
name|uriArray
init|=
operator|new
name|JSONArray
argument_list|()
decl_stmt|;
for|for
control|(
name|EnhancementEngine
name|engine
range|:
name|getEngines
argument_list|()
control|)
block|{
name|UriBuilder
name|ub
init|=
name|uriInfo
operator|.
name|getAbsolutePathBuilder
argument_list|()
decl_stmt|;
name|URI
name|userUri
init|=
name|ub
operator|.
name|path
argument_list|(
name|makeEngineId
argument_list|(
name|engine
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|uriArray
operator|.
name|put
argument_list|(
name|userUri
operator|.
name|toASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|uriArray
return|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|TEXT_PLAIN
argument_list|)
specifier|public
name|String
name|getEnginesAsString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|EnhancementEngine
name|engine
range|:
name|getEngines
argument_list|()
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|engine
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|makeEngineId
parameter_list|(
name|EnhancementEngine
name|engine
parameter_list|)
block|{
comment|// TODO: add a property on engines to provided custom local ids and make
comment|// this static method a method of the interface EnhancementEngine
name|String
name|engineClassName
init|=
name|engine
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
decl_stmt|;
name|String
name|suffixToRemove
init|=
literal|"EnhancementEngine"
decl_stmt|;
if|if
condition|(
name|engineClassName
operator|.
name|endsWith
argument_list|(
name|suffixToRemove
argument_list|)
condition|)
block|{
name|engineClassName
operator|=
name|engineClassName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|engineClassName
operator|.
name|length
argument_list|()
operator|-
name|suffixToRemove
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|engineClassName
operator|.
name|toLowerCase
argument_list|()
return|;
block|}
comment|/**      * Form-based OpenCalais-compatible interface      *      * TODO: should we parse the OpenCalais paramsXML and find the closest       * Stanbol Enhancer semantics too?      *      * Note: the format parameter is not part of the official API      *      * @throws EngineException if the content is somehow corrupted      * @throws IOException      */
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|enhanceFromForm
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"content"
argument_list|)
name|String
name|content
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"format"
argument_list|)
name|String
name|format
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"ajax"
argument_list|)
name|boolean
name|buildAjaxview
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|EngineException
throws|,
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"enhance from From: "
operator|+
name|content
argument_list|)
expr_stmt|;
name|ContentItem
name|ci
init|=
operator|new
name|InMemoryContentItem
argument_list|(
name|content
operator|.
name|getBytes
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|,
name|TEXT_PLAIN
argument_list|)
decl_stmt|;
return|return
name|enhanceAndBuildResponse
argument_list|(
name|format
argument_list|,
name|headers
argument_list|,
name|ci
argument_list|,
name|buildAjaxview
argument_list|)
return|;
block|}
comment|/**      * Media-Type based handling of the raw POST data.      *      * @param data binary payload to analyze      * @param uri optional URI for the content items (to be used as an      *            identifier in the enhancement graph)      * @throws EngineException if the content is somehow corrupted      * @throws IOException      */
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|WILDCARD
argument_list|)
specifier|public
name|Response
name|enhanceFromData
parameter_list|(
name|byte
index|[]
name|data
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"uri"
argument_list|)
name|String
name|uri
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
throws|throws
name|EngineException
throws|,
name|IOException
block|{
name|String
name|format
init|=
name|TEXT_PLAIN
decl_stmt|;
if|if
condition|(
name|headers
operator|.
name|getMediaType
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|format
operator|=
name|headers
operator|.
name|getMediaType
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|uri
operator|!=
literal|null
operator|&&
name|uri
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// let the store build an internal URI basted on the content
name|uri
operator|=
literal|null
expr_stmt|;
block|}
name|ContentItem
name|ci
init|=
operator|new
name|InMemoryContentItem
argument_list|(
name|uri
argument_list|,
name|data
argument_list|,
name|format
argument_list|)
decl_stmt|;
return|return
name|enhanceAndBuildResponse
argument_list|(
literal|null
argument_list|,
name|headers
argument_list|,
name|ci
argument_list|,
literal|false
argument_list|)
return|;
block|}
specifier|protected
name|Response
name|enhanceAndBuildResponse
parameter_list|(
name|String
name|format
parameter_list|,
name|HttpHeaders
name|headers
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|boolean
name|buildAjaxview
parameter_list|)
throws|throws
name|EngineException
throws|,
name|IOException
block|{
if|if
condition|(
name|jobManager
operator|!=
literal|null
condition|)
block|{
name|jobManager
operator|.
name|enhanceContent
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
name|MGraph
name|graph
init|=
name|ci
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
if|if
condition|(
name|buildAjaxview
condition|)
block|{
name|ContentItemResource
name|contentItemResource
init|=
operator|new
name|ContentItemResource
argument_list|(
literal|null
argument_list|,
name|ci
argument_list|,
name|entityCache
argument_list|,
name|uriInfo
argument_list|,
name|tcManager
argument_list|,
name|serializer
argument_list|)
decl_stmt|;
name|contentItemResource
operator|.
name|setRdfSerializationFormat
argument_list|(
name|format
argument_list|)
expr_stmt|;
name|Viewable
name|ajaxView
init|=
operator|new
name|Viewable
argument_list|(
literal|"/ajax/contentitem"
argument_list|,
name|contentItemResource
argument_list|)
decl_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
name|ajaxView
argument_list|)
operator|.
name|type
argument_list|(
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
if|if
condition|(
name|format
operator|!=
literal|null
condition|)
block|{
comment|// force mimetype from form params
return|return
name|Response
operator|.
name|ok
argument_list|(
name|graph
argument_list|,
name|format
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
if|if
condition|(
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|contains
argument_list|(
name|APPLICATION_JSON_TYPE
argument_list|)
condition|)
block|{
comment|// force RDF JSON media type (TODO: move this logic
return|return
name|Response
operator|.
name|ok
argument_list|(
name|graph
argument_list|,
name|RDF_JSON
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|headers
operator|.
name|getAcceptableMediaTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// use RDF/XML as default format to keep compat with OpenCalais
comment|// clients
return|return
name|Response
operator|.
name|ok
argument_list|(
name|graph
argument_list|,
name|RDF_XML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
comment|// traditional response lookup
return|return
name|Response
operator|.
name|ok
argument_list|(
name|graph
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
end_class

end_unit

