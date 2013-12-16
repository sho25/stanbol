begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|web
operator|.
name|impl
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
name|Date
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
name|List
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
name|WebApplicationException
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
operator|.
name|Status
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
name|ReferenceCardinality
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
name|core
operator|.
name|utils
operator|.
name|TimeUtils
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
name|servicesapi
operator|.
name|defaults
operator|.
name|DataTypeEnum
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
name|servicesapi
operator|.
name|model
operator|.
name|Entity
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
name|servicesapi
operator|.
name|model
operator|.
name|Reference
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
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|servicesapi
operator|.
name|model
operator|.
name|Text
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
name|servicesapi
operator|.
name|query
operator|.
name|QueryResultList
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
name|web
operator|.
name|ModelWriter
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
name|web
operator|.
name|fieldquery
operator|.
name|FieldQueryToJsonUtils
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
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
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
name|JSONObject
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
name|cm
operator|.
name|ConfigurationException
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

begin_comment
comment|/**  * Component that supports serialising Entityhub Model classes as   * {@link MediaType#APPLICATION_JSON}.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
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
specifier|public
class|class
name|JsonModelWriter
implements|implements
name|ModelWriter
block|{
comment|/**      * The list of supported media types as returned by {@link #supportedMediaTypes()}      * containing only {@link MediaType#APPLICATION_JSON_TYPE}      */
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|MediaType
argument_list|>
name|SUPPORTED_MEDIA_TYPES
init|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
argument_list|)
decl_stmt|;
comment|/**      * Allows to enable pretty format and setting the indent. If %lt;= 0       * pretty format is deactivated.      */
annotation|@
name|Property
argument_list|(
name|intValue
operator|=
name|JsonModelWriter
operator|.
name|DEFAULT_INDENT
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|PROEPRTY_INDENT
init|=
literal|"entityhub.web.writer.json.indent"
decl_stmt|;
comment|/**      * The default for {@link #PROEPRTY_INDENT} is<code>-1</code>.      */
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_INDENT
init|=
operator|-
literal|1
decl_stmt|;
comment|/**      * The optional {@link NamespacePrefixService} is used for serialising      * {@link QueryResultList}s that do use namespace prefixes      */
annotation|@
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
name|Reference
argument_list|(
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|OPTIONAL_UNARY
argument_list|)
specifier|protected
name|NamespacePrefixService
name|nsPrefixService
decl_stmt|;
specifier|private
name|int
name|indent
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Object
name|value
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROEPRTY_INDENT
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Number
condition|)
block|{
name|indent
operator|=
operator|(
operator|(
name|Number
operator|)
name|value
operator|)
operator|.
name|intValue
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|indent
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROEPRTY_INDENT
argument_list|,
literal|"The parsed indent MUST BE an Integer number (values<= 0 "
operator|+
literal|"will deactivate pretty format)"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|indent
operator|=
name|DEFAULT_INDENT
expr_stmt|;
block|}
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
name|indent
operator|=
operator|-
literal|1
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|?
extends|extends
name|Representation
argument_list|>
name|getNativeType
parameter_list|()
block|{
return|return
literal|null
return|;
comment|//no native type
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|MediaType
argument_list|>
name|supportedMediaTypes
parameter_list|()
block|{
return|return
name|SUPPORTED_MEDIA_TYPES
return|;
block|}
annotation|@
name|Override
specifier|public
name|MediaType
name|getBestMediaType
parameter_list|(
name|MediaType
name|mediaType
parameter_list|)
block|{
return|return
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
operator|.
name|isCompatible
argument_list|(
name|mediaType
argument_list|)
condition|?
name|MediaType
operator|.
name|APPLICATION_JSON_TYPE
else|:
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|write
parameter_list|(
name|Representation
name|rep
parameter_list|,
name|OutputStream
name|out
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
throws|throws
name|WebApplicationException
throws|,
name|IOException
block|{
try|try
block|{
name|writeJsonObject
argument_list|(
name|toJSON
argument_list|(
name|rep
argument_list|)
argument_list|,
name|out
argument_list|,
name|getCharset
argument_list|(
name|mediaType
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|write
parameter_list|(
name|Entity
name|entity
parameter_list|,
name|OutputStream
name|out
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
throws|throws
name|WebApplicationException
throws|,
name|IOException
block|{
try|try
block|{
name|writeJsonObject
argument_list|(
name|toJSON
argument_list|(
name|entity
argument_list|)
argument_list|,
name|out
argument_list|,
name|getCharset
argument_list|(
name|mediaType
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|write
parameter_list|(
name|QueryResultList
argument_list|<
name|?
argument_list|>
name|result
parameter_list|,
name|OutputStream
name|out
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
throws|throws
name|WebApplicationException
throws|,
name|IOException
block|{
try|try
block|{
name|writeJsonObject
argument_list|(
name|toJSON
argument_list|(
name|result
argument_list|,
name|nsPrefixService
argument_list|)
argument_list|,
name|out
argument_list|,
name|getCharset
argument_list|(
name|mediaType
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
specifier|private
name|JSONObject
name|toJSON
parameter_list|(
name|Entity
name|entity
parameter_list|)
throws|throws
name|JSONException
block|{
return|return
name|convertEntityToJSON
argument_list|(
name|entity
argument_list|)
return|;
block|}
comment|/**      * Writes the {@link JSONObject} to the stream      * @param jObject the object to write      * @param out the output stream      * @param charset the charset      * @throws IOException      * @throws JSONException      */
specifier|private
name|void
name|writeJsonObject
parameter_list|(
name|JSONObject
name|jObject
parameter_list|,
name|OutputStream
name|out
parameter_list|,
name|String
name|charset
parameter_list|)
throws|throws
name|IOException
throws|,
name|JSONException
block|{
name|IOUtils
operator|.
name|write
argument_list|(
name|indent
operator|>
literal|0
condition|?
name|jObject
operator|.
name|toString
argument_list|(
name|indent
argument_list|)
else|:
name|jObject
operator|.
name|toString
argument_list|()
argument_list|,
name|out
argument_list|,
name|charset
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param mediaType      * @return      */
specifier|private
name|String
name|getCharset
parameter_list|(
name|MediaType
name|mediaType
parameter_list|)
block|{
name|String
name|charset
init|=
name|mediaType
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
decl_stmt|;
if|if
condition|(
name|charset
operator|==
literal|null
condition|)
block|{
name|charset
operator|=
name|ModelWriter
operator|.
name|DEFAULT_CHARSET
expr_stmt|;
block|}
return|return
name|charset
return|;
block|}
comment|/**      * @param entity      * @return      * @throws JSONException      */
specifier|private
name|JSONObject
name|convertEntityToJSON
parameter_list|(
name|Entity
name|entity
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jSign
decl_stmt|;
name|jSign
operator|=
operator|new
name|JSONObject
argument_list|()
expr_stmt|;
name|jSign
operator|.
name|put
argument_list|(
literal|"id"
argument_list|,
name|entity
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|jSign
operator|.
name|put
argument_list|(
literal|"site"
argument_list|,
name|entity
operator|.
name|getSite
argument_list|()
argument_list|)
expr_stmt|;
comment|//        Representation rep = sign.getRepresentation();
name|jSign
operator|.
name|put
argument_list|(
literal|"representation"
argument_list|,
name|toJSON
argument_list|(
name|entity
operator|.
name|getRepresentation
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|jSign
operator|.
name|put
argument_list|(
literal|"metadata"
argument_list|,
name|toJSON
argument_list|(
name|entity
operator|.
name|getMetadata
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|jSign
return|;
block|}
comment|/**      * Converts the {@link Representation} to JSON      *      * @param jSign      * @param rep      * @throws JSONException      */
specifier|private
name|JSONObject
name|toJSON
parameter_list|(
name|Representation
name|rep
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jRep
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
name|jRep
operator|.
name|put
argument_list|(
literal|"id"
argument_list|,
name|rep
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|rep
operator|.
name|getFieldNames
argument_list|()
init|;
name|fields
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|field
init|=
name|fields
operator|.
name|next
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Object
argument_list|>
name|values
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|jRep
operator|.
name|put
argument_list|(
name|field
argument_list|,
name|convertFieldValuesToJSON
argument_list|(
name|values
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|jRep
return|;
block|}
comment|/**      * @param values Iterator over all the values to add      * @return The {@link JSONArray} with all the values as {@link JSONObject}      * @throws JSONException      */
specifier|private
name|JSONArray
name|convertFieldValuesToJSON
parameter_list|(
name|Iterator
argument_list|<
name|?
argument_list|>
name|values
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONArray
name|jValues
init|=
operator|new
name|JSONArray
argument_list|()
decl_stmt|;
while|while
condition|(
name|values
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|jValues
operator|.
name|put
argument_list|(
name|convertFieldValueToJSON
argument_list|(
name|values
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|jValues
return|;
block|}
comment|/**      * The value to write. Special support for  {@link Reference} and {@link Text}.      * The {@link #toString()} Method is used to write the "value" key.      *      * @param value the value      * @return the {@link JSONObject} representing the value      * @throws JSONException      */
specifier|private
name|JSONObject
name|convertFieldValueToJSON
parameter_list|(
name|Object
name|value
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jValue
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Reference
condition|)
block|{
name|jValue
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
literal|"reference"
argument_list|)
expr_stmt|;
name|jValue
operator|.
name|put
argument_list|(
literal|"xsd:datatype"
argument_list|,
name|DataTypeEnum
operator|.
name|AnyUri
operator|.
name|getShortName
argument_list|()
argument_list|)
expr_stmt|;
name|jValue
operator|.
name|put
argument_list|(
literal|"value"
argument_list|,
operator|(
operator|(
name|Reference
operator|)
name|value
operator|)
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Text
condition|)
block|{
name|jValue
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
literal|"text"
argument_list|)
expr_stmt|;
name|jValue
operator|.
name|put
argument_list|(
literal|"xml:lang"
argument_list|,
operator|(
operator|(
name|Text
operator|)
name|value
operator|)
operator|.
name|getLanguage
argument_list|()
argument_list|)
expr_stmt|;
name|jValue
operator|.
name|put
argument_list|(
literal|"value"
argument_list|,
operator|(
operator|(
name|Text
operator|)
name|value
operator|)
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Date
condition|)
block|{
name|jValue
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
literal|"value"
argument_list|)
expr_stmt|;
name|jValue
operator|.
name|put
argument_list|(
literal|"value"
argument_list|,
name|TimeUtils
operator|.
name|toString
argument_list|(
name|DataTypeEnum
operator|.
name|DateTime
argument_list|,
operator|(
name|Date
operator|)
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|jValue
operator|.
name|put
argument_list|(
literal|"xsd:datatype"
argument_list|,
name|DataTypeEnum
operator|.
name|DateTime
operator|.
name|getShortName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|jValue
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
literal|"value"
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|DataTypeEnum
argument_list|>
name|dataTypes
init|=
name|DataTypeEnum
operator|.
name|getPrimaryDataTypes
argument_list|(
name|value
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|dataTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|jValue
operator|.
name|put
argument_list|(
literal|"xsd:datatype"
argument_list|,
name|dataTypes
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|.
name|getShortName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|jValue
operator|.
name|put
argument_list|(
literal|"xsd:datatype"
argument_list|,
name|DataTypeEnum
operator|.
name|String
operator|.
name|getShortName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jValue
operator|.
name|put
argument_list|(
literal|"value"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
return|return
name|jValue
return|;
block|}
specifier|private
parameter_list|<
name|T
parameter_list|>
name|JSONObject
name|toJSON
parameter_list|(
name|QueryResultList
argument_list|<
name|?
argument_list|>
name|resultList
parameter_list|,
name|NamespacePrefixService
name|nsPrefixService
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONObject
name|jResultList
init|=
operator|new
name|JSONObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|resultList
operator|.
name|getQuery
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|jResultList
operator|.
name|put
argument_list|(
literal|"query"
argument_list|,
name|FieldQueryToJsonUtils
operator|.
name|toJSON
argument_list|(
name|resultList
operator|.
name|getQuery
argument_list|()
argument_list|,
name|nsPrefixService
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jResultList
operator|.
name|put
argument_list|(
literal|"results"
argument_list|,
name|convertResultsToJSON
argument_list|(
name|resultList
argument_list|,
name|resultList
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|jResultList
return|;
block|}
specifier|private
parameter_list|<
name|T
parameter_list|>
name|JSONArray
name|convertResultsToJSON
parameter_list|(
name|Iterable
argument_list|<
name|?
argument_list|>
name|results
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|)
throws|throws
name|JSONException
block|{
name|JSONArray
name|jResults
init|=
operator|new
name|JSONArray
argument_list|()
decl_stmt|;
if|if
condition|(
name|String
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
for|for
control|(
name|Object
name|result
range|:
name|results
control|)
block|{
name|jResults
operator|.
name|put
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|Representation
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
for|for
control|(
name|Object
name|result
range|:
name|results
control|)
block|{
name|jResults
operator|.
name|put
argument_list|(
name|toJSON
argument_list|(
operator|(
name|Representation
operator|)
name|result
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|Entity
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
condition|)
block|{
for|for
control|(
name|Object
name|result
range|:
name|results
control|)
block|{
name|jResults
operator|.
name|put
argument_list|(
name|toJSON
argument_list|(
operator|(
name|Entity
operator|)
name|result
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|jResults
return|;
block|}
block|}
end_class

end_unit

