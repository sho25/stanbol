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
name|BufferedWriter
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
name|ValueTypeSerializer
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
name|ValueTypeSerializerRegistry
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
name|JsonGenerator
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

begin_comment
comment|/**  * Serializes an AnalysedText instance as JSON  * @author Rupert Westenthaler  *  */
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
name|IGNORE
argument_list|)
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|AnalyzedTextSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|AnalyzedTextSerializer
block|{
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
name|AnalyzedTextSerializer
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
comment|/**      * Can be used when running outside of OSGI to obtain the default (singelton)      * instance.      * @return      */
specifier|public
specifier|static
specifier|final
name|AnalyzedTextSerializer
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
name|AnalyzedTextSerializer
argument_list|(
name|ValueTypeSerializerRegistry
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
name|AnalyzedTextSerializer
parameter_list|()
block|{}
comment|/**      * Constructs a new Serializer instance for the parsed {@link ValueTypeSerializerRegistry}      * instance. Typically this constructor should not be used as usages within      * an OSGI environment MUST lookup the service via the service registry.      * Usages outside an OSGI environment should prefer to use the      * {@link #getDefaultInstance()} instance to obtain the singleton instance.      * @param vtsr      */
specifier|public
name|AnalyzedTextSerializer
parameter_list|(
name|ValueTypeSerializerRegistry
name|vtsr
parameter_list|)
block|{
if|if
condition|(
name|vtsr
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ValueTypeSerializerRegistry MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|valueTypeSerializerRegistry
operator|=
name|vtsr
expr_stmt|;
block|}
annotation|@
name|Reference
specifier|protected
name|ValueTypeSerializerRegistry
name|valueTypeSerializerRegistry
decl_stmt|;
comment|/**      * Serializes the parsed {@link AnalysedText} to the {@link OutputStream} by      * using the {@link Charset}.      * @param at the {@link AnalysedText} to serialize      * @param out the {@link OutputStream}       * @param charset the {@link Charset}. UTF-8 is used as default if<code>null</code>      * is parsed      */
specifier|public
name|void
name|serialize
parameter_list|(
name|AnalysedText
name|at
parameter_list|,
name|OutputStream
name|out
parameter_list|,
name|Charset
name|charset
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|at
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed AnalysedText MUST NOT be NULL!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|out
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed OutputStream MUST NOT be NULL"
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
name|JsonFactory
name|jsonFactory
init|=
name|mapper
operator|.
name|getJsonFactory
argument_list|()
decl_stmt|;
name|JsonGenerator
name|jg
init|=
name|jsonFactory
operator|.
name|createJsonGenerator
argument_list|(
operator|new
name|OutputStreamWriter
argument_list|(
name|out
argument_list|,
name|charset
argument_list|)
argument_list|)
decl_stmt|;
name|jg
operator|.
name|useDefaultPrettyPrinter
argument_list|()
expr_stmt|;
name|jg
operator|.
name|writeStartObject
argument_list|()
expr_stmt|;
name|jg
operator|.
name|writeArrayFieldStart
argument_list|(
literal|"spans"
argument_list|)
expr_stmt|;
name|jg
operator|.
name|writeTree
argument_list|(
name|writeSpan
argument_list|(
name|at
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Span
argument_list|>
name|it
init|=
name|at
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|allOf
argument_list|(
name|SpanTypeEnum
operator|.
name|class
argument_list|)
argument_list|)
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|jg
operator|.
name|writeTree
argument_list|(
name|writeSpan
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jg
operator|.
name|writeEndArray
argument_list|()
expr_stmt|;
name|jg
operator|.
name|writeEndObject
argument_list|()
expr_stmt|;
name|jg
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|private
name|ObjectNode
name|writeSpan
parameter_list|(
name|Span
name|span
parameter_list|)
throws|throws
name|IOException
block|{
name|ObjectNode
name|jSpan
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jSpan
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|span
operator|.
name|getType
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|jSpan
operator|.
name|put
argument_list|(
literal|"start"
argument_list|,
name|span
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|jSpan
operator|.
name|put
argument_list|(
literal|"end"
argument_list|,
name|span
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|key
range|:
name|span
operator|.
name|getKeys
argument_list|()
control|)
block|{
name|List
argument_list|<
name|Value
argument_list|<
name|?
argument_list|>
argument_list|>
name|values
init|=
name|span
operator|.
name|getValues
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|jSpan
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|writeValue
argument_list|(
name|values
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ArrayNode
name|jValues
init|=
name|jSpan
operator|.
name|putArray
argument_list|(
name|key
argument_list|)
decl_stmt|;
for|for
control|(
name|Value
argument_list|<
name|?
argument_list|>
name|value
range|:
name|values
control|)
block|{
name|jValues
operator|.
name|add
argument_list|(
name|writeValue
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jSpan
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|jValues
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|jSpan
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
block|{
literal|"rawtypes"
block|,
literal|"unchecked"
block|}
argument_list|)
specifier|private
name|ObjectNode
name|writeValue
parameter_list|(
name|Value
argument_list|<
name|?
argument_list|>
name|value
parameter_list|)
block|{
name|ObjectNode
name|jValue
decl_stmt|;
name|Class
argument_list|<
name|?
argument_list|>
name|valueType
init|=
name|value
operator|.
name|value
argument_list|()
operator|.
name|getClass
argument_list|()
decl_stmt|;
name|ValueTypeSerializer
name|vts
init|=
name|valueTypeSerializerRegistry
operator|.
name|getSerializer
argument_list|(
name|valueType
argument_list|)
decl_stmt|;
if|if
condition|(
name|vts
operator|!=
literal|null
condition|)
block|{
name|jValue
operator|=
name|vts
operator|.
name|serialize
argument_list|(
name|mapper
argument_list|,
name|value
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
comment|//TODO assert that jValue does not define "class" and "prob"!
block|}
else|else
block|{
comment|//use the default binding and the "data" field
name|jValue
operator|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
expr_stmt|;
name|jValue
operator|.
name|put
argument_list|(
literal|"value"
argument_list|,
name|mapper
operator|.
name|valueToTree
argument_list|(
name|value
operator|.
name|value
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jValue
operator|.
name|put
argument_list|(
literal|"class"
argument_list|,
name|valueType
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|.
name|probability
argument_list|()
operator|!=
name|Value
operator|.
name|UNKNOWN_PROBABILITY
condition|)
block|{
name|jValue
operator|.
name|put
argument_list|(
literal|"prob"
argument_list|,
name|value
operator|.
name|probability
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|jValue
return|;
block|}
block|}
end_class

end_unit

