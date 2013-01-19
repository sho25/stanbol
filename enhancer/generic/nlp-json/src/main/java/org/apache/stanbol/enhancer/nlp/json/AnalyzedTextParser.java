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
name|nlp
operator|.
name|json
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
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
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
name|ArrayList
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
name|EnumSet
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
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|Reference
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
name|enhancer
operator|.
name|nlp
operator|.
name|json
operator|.
name|valuetype
operator|.
name|ValueTypeParser
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
name|nlp
operator|.
name|json
operator|.
name|valuetype
operator|.
name|ValueTypeParserRegistry
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
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|nlp
operator|.
name|model
operator|.
name|AnalysedTextFactory
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
name|nlp
operator|.
name|model
operator|.
name|Span
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
name|nlp
operator|.
name|model
operator|.
name|Span
operator|.
name|SpanTypeEnum
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
name|nlp
operator|.
name|model
operator|.
name|annotation
operator|.
name|Value
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
name|codehaus
operator|.
name|jackson
operator|.
name|JsonFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonParseException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonParser
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonToken
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|io
operator|.
name|SerializedString
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|map
operator|.
name|JsonMappingException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|map
operator|.
name|ObjectMapper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|node
operator|.
name|ArrayNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|node
operator|.
name|ObjectNode
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
name|IGNORE
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|AnalyzedTextParser
operator|.
name|class
argument_list|)
specifier|public
class|class
name|AnalyzedTextParser
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
name|AnalyzedTextParser
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
specifier|static
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
name|AnalyzedTextParser
name|defaultInstance
decl_stmt|;
specifier|protected
name|ObjectMapper
name|mapper
init|=
operator|new
name|ObjectMapper
argument_list|()
decl_stmt|;
comment|/**      * Can be used when running outside of OSGI to obtain the default (singleton)      * instance.      * @return      */
specifier|public
specifier|static
specifier|final
name|AnalyzedTextParser
name|getDefaultInstance
parameter_list|()
block|{
if|if
condition|(
name|defaultInstance
operator|==
literal|null
condition|)
block|{
name|defaultInstance
operator|=
operator|new
name|AnalyzedTextParser
argument_list|(
name|ValueTypeParserRegistry
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|defaultInstance
return|;
block|}
comment|/**      * Default constructor used by OSGI      */
specifier|public
name|AnalyzedTextParser
parameter_list|()
block|{}
comment|/**      * Constructs a new Parser instance for the parsed {@link ValueTypeParserRegistry}      * instance. Typically this constructor should not be used as usages within      * an OSGI environment MUST lookup the service via the service registry.      * Usages outside an OSGI environment should prefer to use the      * {@link #getDefaultInstance()} instance to obtain the singleton instance.      * @param vtsr      */
specifier|public
name|AnalyzedTextParser
parameter_list|(
name|ValueTypeParserRegistry
name|vtpr
parameter_list|)
block|{
if|if
condition|(
name|vtpr
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ValueTypeParserRegistry MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|valueTypeParserRegistry
operator|=
name|vtpr
expr_stmt|;
block|}
annotation|@
name|Reference
specifier|protected
name|ValueTypeParserRegistry
name|valueTypeParserRegistry
decl_stmt|;
comment|/**      * Parses {@link AnalysedText} {@link Span}s including annotations from the       * {@link InputStream}. The {@link AnalysedText} instance that is going to      * be enrichted with the parsed data needs to be parsed. In the simplest case      * the caller can create an empty instance by using a       * {@link AnalysedTextFactory}.      * @param in The stream to read the data from      * @param charset the {@link Charset} used by the stream      * @param at The {@link AnalysedText} instance used to add the data to      * @return the parsed {@link AnalysedText} instance enrichted with the      * information parsed from the Stream      * @throws IOException on any Error while reading or parsing the data      * from the Stream      */
specifier|public
name|AnalysedText
name|parse
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|Charset
name|charset
parameter_list|,
specifier|final
name|AnalysedText
name|at
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|in
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed InputStream MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|charset
operator|==
literal|null
condition|)
block|{
name|charset
operator|=
name|UTF8
expr_stmt|;
block|}
name|JsonParser
name|parser
init|=
name|mapper
operator|.
name|getJsonFactory
argument_list|()
operator|.
name|createJsonParser
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|in
argument_list|,
name|charset
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|parser
operator|.
name|nextToken
argument_list|()
operator|!=
name|JsonToken
operator|.
name|START_OBJECT
condition|)
block|{
comment|//start object
throw|throw
operator|new
name|IOException
argument_list|(
literal|"JSON serialized AnalyzedTexts MUST use a JSON Object as Root!"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|parser
operator|.
name|nextFieldName
argument_list|(
operator|new
name|SerializedString
argument_list|(
literal|"spans"
argument_list|)
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"JSON serialized AnalyzedText MUST define the 'spans' field as first entry "
operator|+
literal|"in the root JSON object!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|parser
operator|.
name|nextValue
argument_list|()
operator|!=
name|JsonToken
operator|.
name|START_ARRAY
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"The value of the 'span' field MUST BE an Json Array!"
argument_list|)
throw|;
block|}
name|boolean
name|first
init|=
literal|true
decl_stmt|;
while|while
condition|(
name|parser
operator|.
name|nextValue
argument_list|()
operator|==
name|JsonToken
operator|.
name|START_OBJECT
condition|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|parseAnalyzedTextSpan
argument_list|(
name|parser
operator|.
name|readValueAsTree
argument_list|()
argument_list|,
name|at
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|parseSpan
argument_list|(
name|at
argument_list|,
name|parser
operator|.
name|readValueAsTree
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|at
return|;
block|}
specifier|private
name|void
name|parseAnalyzedTextSpan
parameter_list|(
name|JsonNode
name|node
parameter_list|,
name|AnalysedText
name|at
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|node
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|ObjectNode
name|jSpan
init|=
operator|(
name|ObjectNode
operator|)
name|node
decl_stmt|;
name|int
index|[]
name|spanPos
init|=
operator|new
name|int
index|[]
block|{
operator|-
literal|1
block|,
operator|-
literal|1
block|}
decl_stmt|;
name|Collection
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
argument_list|>
name|jAnnotations
init|=
operator|new
name|ArrayList
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
argument_list|>
argument_list|(
literal|4
argument_list|)
decl_stmt|;
name|SpanTypeEnum
name|spanType
init|=
name|parseSpanData
argument_list|(
name|jSpan
argument_list|,
name|spanPos
argument_list|,
name|jAnnotations
argument_list|)
decl_stmt|;
if|if
condition|(
name|spanType
operator|!=
name|SpanTypeEnum
operator|.
name|Text
operator|||
name|spanPos
index|[
literal|0
index|]
operator|!=
literal|0
operator|||
name|spanPos
index|[
literal|1
index|]
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"The AnalyzedText span MUST have the SpanType 'text', a "
operator|+
literal|"start position of '0' and an end position (ignored, json: "
operator|+
name|jSpan
argument_list|)
throw|;
block|}
if|if
condition|(
name|at
operator|.
name|getEnd
argument_list|()
operator|!=
name|spanPos
index|[
literal|1
index|]
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"The size of the local text '"
operator|+
name|at
operator|.
name|getEnd
argument_list|()
operator|+
literal|"' does not "
operator|+
literal|"match the span of the parsed AnalyzedText ["
operator|+
name|spanPos
index|[
literal|0
index|]
operator|+
literal|","
operator|+
name|spanPos
index|[
literal|1
index|]
operator|+
literal|"]!"
argument_list|)
throw|;
block|}
name|parseAnnotations
argument_list|(
name|at
argument_list|,
name|jAnnotations
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Unable to parse AnalyzedText span form JsonNode "
operator|+
name|node
operator|+
literal|" (expected JSON object)!"
argument_list|)
throw|;
block|}
block|}
specifier|private
name|void
name|parseSpan
parameter_list|(
name|AnalysedText
name|at
parameter_list|,
name|JsonNode
name|node
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|node
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|ObjectNode
name|jSpan
init|=
operator|(
name|ObjectNode
operator|)
name|node
decl_stmt|;
name|int
index|[]
name|spanPos
init|=
operator|new
name|int
index|[]
block|{
operator|-
literal|1
block|,
operator|-
literal|1
block|}
decl_stmt|;
name|Collection
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
argument_list|>
name|jAnnotations
init|=
operator|new
name|ArrayList
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
argument_list|>
argument_list|(
literal|4
argument_list|)
decl_stmt|;
name|SpanTypeEnum
name|spanType
init|=
name|parseSpanData
argument_list|(
name|jSpan
argument_list|,
name|spanPos
argument_list|,
name|jAnnotations
argument_list|)
decl_stmt|;
if|if
condition|(
name|spanType
operator|==
literal|null
operator|||
name|spanPos
index|[
literal|0
index|]
operator|<
literal|0
operator|||
name|spanPos
index|[
literal|1
index|]
operator|<
literal|0
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Illegal or missing span type, start and/or end position (ignored, json: "
operator|+
name|jSpan
argument_list|)
expr_stmt|;
return|return;
block|}
comment|//now create the Span
name|Span
name|span
decl_stmt|;
switch|switch
condition|(
name|spanType
condition|)
block|{
case|case
name|Text
case|:
name|log
operator|.
name|warn
argument_list|(
literal|"Encounterd 'Text' span that is not the first span in the "
operator|+
literal|"'spans' array (ignored, json: "
operator|+
name|node
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return;
case|case
name|TextSection
case|:
name|log
operator|.
name|warn
argument_list|(
literal|"Encountered 'TextSection' span. This SpanTypeEnum entry "
operator|+
literal|"is currently unused. If this is no longer the case please "
operator|+
literal|"update this implementation (ignored, json: "
operator|+
name|node
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return;
case|case
name|Sentence
case|:
name|span
operator|=
name|at
operator|.
name|addSentence
argument_list|(
name|spanPos
index|[
literal|0
index|]
argument_list|,
name|spanPos
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
break|break;
case|case
name|Chunk
case|:
name|span
operator|=
name|at
operator|.
name|addChunk
argument_list|(
name|spanPos
index|[
literal|0
index|]
argument_list|,
name|spanPos
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
break|break;
case|case
name|Token
case|:
name|span
operator|=
name|at
operator|.
name|addToken
argument_list|(
name|spanPos
index|[
literal|0
index|]
argument_list|,
name|spanPos
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
break|break;
default|default:
name|log
operator|.
name|warn
argument_list|(
literal|"Unsupported SpanTypeEnum  '"
operator|+
name|spanType
operator|+
literal|"'!. Please "
operator|+
literal|"update this implementation (ignored, json: "
operator|+
name|node
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
operator|!
name|jAnnotations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|parseAnnotations
argument_list|(
name|span
argument_list|,
name|jAnnotations
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse Span form JsonNode "
operator|+
name|node
operator|+
literal|" (expected JSON object)!"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param jSpan      * @param spanPos      * @param jAnnotations      * @return the type of the parsed span      */
specifier|private
name|SpanTypeEnum
name|parseSpanData
parameter_list|(
name|ObjectNode
name|jSpan
parameter_list|,
name|int
index|[]
name|spanPos
parameter_list|,
name|Collection
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
argument_list|>
name|jAnnotations
parameter_list|)
block|{
name|SpanTypeEnum
name|spanType
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
argument_list|>
name|fields
init|=
name|jSpan
operator|.
name|getFields
argument_list|()
init|;
name|fields
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
name|field
init|=
name|fields
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"type"
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|field
operator|.
name|getValue
argument_list|()
operator|.
name|isTextual
argument_list|()
condition|)
block|{
name|spanType
operator|=
name|SpanTypeEnum
operator|.
name|valueOf
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
operator|.
name|getTextValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|field
operator|.
name|getValue
argument_list|()
operator|.
name|isInt
argument_list|()
condition|)
block|{
name|spanType
operator|=
name|SpanTypeEnum
operator|.
name|values
argument_list|()
index|[
name|field
operator|.
name|getValue
argument_list|()
operator|.
name|getIntValue
argument_list|()
index|]
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse SpanType form JSON field "
operator|+
name|field
operator|+
literal|" (ignored, json: "
operator|+
name|jSpan
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"start"
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|field
operator|.
name|getValue
argument_list|()
operator|.
name|isInt
argument_list|()
condition|)
block|{
name|spanPos
index|[
literal|0
index|]
operator|=
name|field
operator|.
name|getValue
argument_list|()
operator|.
name|getIntValue
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse span start position form JSON field "
operator|+
name|field
operator|+
literal|" (ignored, json: "
operator|+
name|jSpan
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"end"
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|field
operator|.
name|getValue
argument_list|()
operator|.
name|isInt
argument_list|()
condition|)
block|{
name|spanPos
index|[
literal|1
index|]
operator|=
name|field
operator|.
name|getValue
argument_list|()
operator|.
name|getIntValue
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse span end position form JSON field "
operator|+
name|field
operator|+
literal|" (ignored, json: "
operator|+
name|jSpan
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
name|jAnnotations
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|spanType
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Missing required field 'type' defining the type of the Span!"
argument_list|)
expr_stmt|;
block|}
return|return
name|spanType
return|;
block|}
specifier|private
name|void
name|parseAnnotations
parameter_list|(
name|Span
name|span
parameter_list|,
name|Collection
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
argument_list|>
name|jAnnotations
parameter_list|)
throws|throws
name|IOException
block|{
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|JsonNode
argument_list|>
name|jAnnotation
range|:
name|jAnnotations
control|)
block|{
if|if
condition|(
name|jAnnotation
operator|.
name|getValue
argument_list|()
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|parseAnnotation
argument_list|(
name|span
argument_list|,
name|jAnnotation
operator|.
name|getKey
argument_list|()
argument_list|,
operator|(
name|ObjectNode
operator|)
name|jAnnotation
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|jAnnotation
operator|.
name|getValue
argument_list|()
operator|.
name|isArray
argument_list|()
condition|)
block|{
name|ArrayNode
name|jValues
init|=
operator|(
name|ArrayNode
operator|)
name|jAnnotation
operator|.
name|getValue
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|jValues
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JsonNode
name|jValue
init|=
name|jValues
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|jValue
operator|.
name|isObject
argument_list|()
condition|)
block|{
name|parseAnnotation
argument_list|(
name|span
argument_list|,
name|jAnnotation
operator|.
name|getKey
argument_list|()
argument_list|,
operator|(
name|ObjectNode
operator|)
name|jValue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"unable to parse the {} value of the annotation {} "
operator|+
literal|"because value is no JSON object (ignored, json: {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|i
block|,
name|jAnnotation
operator|.
name|getKey
argument_list|()
block|,
name|jAnnotation
operator|.
name|getValue
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"unable to parse Annotation {} because value is no JSON object (ignored, json: {}"
argument_list|,
name|jAnnotation
operator|.
name|getKey
argument_list|()
argument_list|,
name|jAnnotation
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|parseAnnotation
parameter_list|(
name|Span
name|span
parameter_list|,
name|String
name|key
parameter_list|,
name|ObjectNode
name|jValue
parameter_list|)
throws|throws
name|IOException
block|{
name|JsonNode
name|jClass
init|=
name|jValue
operator|.
name|path
argument_list|(
literal|"class"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|jClass
operator|.
name|isTextual
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"unable to parse Annotation {} because 'class' field "
operator|+
literal|"is not set or not a stringis no JSON object (ignored, json: {}"
argument_list|,
name|key
argument_list|,
name|jValue
argument_list|)
expr_stmt|;
return|return;
block|}
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
decl_stmt|;
try|try
block|{
name|clazz
operator|=
name|AnalyzedTextParser
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|loadClass
argument_list|(
name|jClass
operator|.
name|getTextValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse Annotation "
operator|+
name|key
operator|+
literal|" because the 'class' "
operator|+
name|jClass
operator|.
name|getTextValue
argument_list|()
operator|+
literal|" of the "
operator|+
literal|"the value can not be resolved (ignored, json: "
operator|+
name|jValue
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
name|ValueTypeParser
argument_list|<
name|?
argument_list|>
name|parser
init|=
name|this
operator|.
name|valueTypeParserRegistry
operator|.
name|getParser
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
name|Object
name|value
decl_stmt|;
if|if
condition|(
name|parser
operator|!=
literal|null
condition|)
block|{
name|value
operator|=
name|parser
operator|.
name|parse
argument_list|(
name|jValue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JsonNode
name|valueNode
init|=
name|jValue
operator|.
name|path
argument_list|(
literal|"value"
argument_list|)
decl_stmt|;
if|if
condition|(
name|valueNode
operator|.
name|isMissingNode
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"unable to parse value for annotation {} because the "
operator|+
literal|"field 'value' is not present (ignored, json: {}"
argument_list|,
name|key
argument_list|,
name|jValue
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
try|try
block|{
name|value
operator|=
name|mapper
operator|.
name|treeToValue
argument_list|(
name|valueNode
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JsonParseException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"unable to parse value for annotation "
operator|+
name|key
operator|+
literal|"because the value can"
operator|+
literal|"not be converted to the class "
operator|+
name|clazz
operator|.
name|getName
argument_list|()
operator|+
literal|"(ignored, json: "
operator|+
name|jValue
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
catch|catch
parameter_list|(
name|JsonMappingException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"unable to parse value for annotation "
operator|+
name|key
operator|+
literal|"because the value can"
operator|+
literal|"not be converted to the class "
operator|+
name|clazz
operator|.
name|getName
argument_list|()
operator|+
literal|"(ignored, json: "
operator|+
name|jValue
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
block|}
name|JsonNode
name|jProb
init|=
name|jValue
operator|.
name|path
argument_list|(
literal|"prob"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|jProb
operator|.
name|isDouble
argument_list|()
condition|)
block|{
name|span
operator|.
name|addValue
argument_list|(
name|key
argument_list|,
name|Value
operator|.
name|value
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|span
operator|.
name|addValue
argument_list|(
name|key
argument_list|,
name|Value
operator|.
name|value
argument_list|(
name|value
argument_list|,
name|jProb
operator|.
name|getDoubleValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Parses the SpanType for the parsed {@link ObjectNode} representing a {@link Span}      * @param jSpan the JSON root node of the span      * @return the type or<code>null</code> if the information is missing      */
specifier|private
name|SpanTypeEnum
name|parseSpanType
parameter_list|(
name|ObjectNode
name|jSpan
parameter_list|)
block|{
name|EnumSet
argument_list|<
name|SpanTypeEnum
argument_list|>
name|spanTypes
init|=
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jSpan
argument_list|,
literal|"type"
argument_list|,
name|SpanTypeEnum
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|spanTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse Span with missing 'type' (json: "
operator|+
name|jSpan
operator|+
literal|")!"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|spanTypes
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Found Span with multiple 'types' (Json:"
operator|+
name|jSpan
operator|+
literal|")!"
argument_list|)
expr_stmt|;
block|}
return|return
name|spanTypes
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
return|;
block|}
block|}
end_class

end_unit

