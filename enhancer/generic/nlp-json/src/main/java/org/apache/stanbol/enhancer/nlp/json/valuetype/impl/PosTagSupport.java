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
operator|.
name|valuetype
operator|.
name|impl
package|;
end_package

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
name|EnumSet
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
name|JsonUtils
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
name|pos
operator|.
name|LexicalCategory
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
name|pos
operator|.
name|Pos
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
name|pos
operator|.
name|PosTag
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
block|{
name|ValueTypeParser
operator|.
name|class
block|,
name|ValueTypeSerializer
operator|.
name|class
block|}
argument_list|)
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|ValueTypeParser
operator|.
name|PROPERTY_TYPE
argument_list|,
name|value
operator|=
name|PosTagSupport
operator|.
name|TYPE_VALUE
argument_list|)
specifier|public
class|class
name|PosTagSupport
implements|implements
name|ValueTypeParser
argument_list|<
name|PosTag
argument_list|>
implements|,
name|ValueTypeSerializer
argument_list|<
name|PosTag
argument_list|>
block|{
specifier|public
specifier|static
specifier|final
name|String
name|TYPE_VALUE
init|=
literal|"org.apache.stanbol.enhancer.nlp.pos.PosTag"
decl_stmt|;
name|Map
argument_list|<
name|PosTagInfo
argument_list|,
name|PosTag
argument_list|>
name|posTagCache
init|=
name|Collections
operator|.
name|synchronizedMap
argument_list|(
operator|new
name|LinkedHashMap
argument_list|<
name|PosTagInfo
argument_list|,
name|PosTag
argument_list|>
argument_list|(
literal|16
argument_list|,
literal|0.75f
argument_list|,
literal|true
argument_list|)
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|boolean
name|removeEldestEntry
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
argument_list|<
name|PosTagInfo
argument_list|,
name|PosTag
argument_list|>
name|arg0
parameter_list|)
block|{
return|return
name|size
argument_list|()
operator|>
literal|1024
return|;
block|}
block|}
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|PosTag
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|PosTag
operator|.
name|class
return|;
block|}
annotation|@
name|Override
specifier|public
name|PosTag
name|parse
parameter_list|(
name|ObjectNode
name|jValue
parameter_list|)
block|{
name|PosTagInfo
name|tagInfo
init|=
operator|new
name|PosTagInfo
argument_list|()
decl_stmt|;
name|JsonNode
name|tag
init|=
name|jValue
operator|.
name|path
argument_list|(
literal|"tag"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|tag
operator|.
name|isTextual
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to parse PosTag. The value of the "
operator|+
literal|"'tag' field MUST have a textual value (json: "
operator|+
name|jValue
operator|+
literal|")"
argument_list|)
throw|;
block|}
name|tagInfo
operator|.
name|tag
operator|=
name|tag
operator|.
name|getTextValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|jValue
operator|.
name|has
argument_list|(
literal|"lc"
argument_list|)
condition|)
block|{
name|tagInfo
operator|.
name|categories
operator|=
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jValue
argument_list|,
literal|"lc"
argument_list|,
name|LexicalCategory
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tagInfo
operator|.
name|categories
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|LexicalCategory
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|jValue
operator|.
name|has
argument_list|(
literal|"pos"
argument_list|)
condition|)
block|{
name|tagInfo
operator|.
name|pos
operator|=
name|JsonUtils
operator|.
name|parseEnum
argument_list|(
name|jValue
argument_list|,
literal|"pos"
argument_list|,
name|Pos
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tagInfo
operator|.
name|pos
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|Pos
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
name|PosTag
name|posTag
init|=
name|posTagCache
operator|.
name|get
argument_list|(
name|tagInfo
argument_list|)
decl_stmt|;
if|if
condition|(
name|posTag
operator|==
literal|null
condition|)
block|{
name|posTag
operator|=
operator|new
name|PosTag
argument_list|(
name|tagInfo
operator|.
name|tag
argument_list|,
name|tagInfo
operator|.
name|categories
argument_list|,
name|tagInfo
operator|.
name|pos
argument_list|)
expr_stmt|;
name|posTagCache
operator|.
name|put
argument_list|(
name|tagInfo
argument_list|,
name|posTag
argument_list|)
expr_stmt|;
block|}
return|return
name|posTag
return|;
block|}
annotation|@
name|Override
specifier|public
name|ObjectNode
name|serialize
parameter_list|(
name|ObjectMapper
name|mapper
parameter_list|,
name|PosTag
name|value
parameter_list|)
block|{
name|ObjectNode
name|jPosTag
init|=
name|mapper
operator|.
name|createObjectNode
argument_list|()
decl_stmt|;
name|jPosTag
operator|.
name|put
argument_list|(
literal|"tag"
argument_list|,
name|value
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|.
name|getPos
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|jPosTag
operator|.
name|put
argument_list|(
literal|"pos"
argument_list|,
name|value
operator|.
name|getPos
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|value
operator|.
name|getPos
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jPos
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|Pos
name|pos
range|:
name|value
operator|.
name|getPos
argument_list|()
control|)
block|{
name|jPos
operator|.
name|add
argument_list|(
name|pos
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jPosTag
operator|.
name|put
argument_list|(
literal|"pos"
argument_list|,
name|jPos
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|value
operator|.
name|getCategories
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//we need only the categories not covered by Pos elements
name|EnumSet
argument_list|<
name|LexicalCategory
argument_list|>
name|categories
init|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|LexicalCategory
operator|.
name|class
argument_list|)
decl_stmt|;
name|categories
operator|.
name|addAll
argument_list|(
name|value
operator|.
name|getCategories
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Pos
name|pos
range|:
name|value
operator|.
name|getPos
argument_list|()
control|)
block|{
name|categories
operator|.
name|removeAll
argument_list|(
name|pos
operator|.
name|categories
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|categories
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|jPosTag
operator|.
name|put
argument_list|(
literal|"lc"
argument_list|,
name|categories
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|categories
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ArrayNode
name|jCategory
init|=
name|mapper
operator|.
name|createArrayNode
argument_list|()
decl_stmt|;
for|for
control|(
name|LexicalCategory
name|lc
range|:
name|categories
control|)
block|{
name|jCategory
operator|.
name|add
argument_list|(
name|lc
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|jPosTag
operator|.
name|put
argument_list|(
literal|"lc"
argument_list|,
name|jCategory
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|jPosTag
return|;
block|}
specifier|private
class|class
name|PosTagInfo
block|{
specifier|protected
name|String
name|tag
decl_stmt|;
specifier|protected
name|EnumSet
argument_list|<
name|LexicalCategory
argument_list|>
name|categories
decl_stmt|;
specifier|protected
name|EnumSet
argument_list|<
name|Pos
argument_list|>
name|pos
decl_stmt|;
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|tag
operator|.
name|hashCode
argument_list|()
operator|+
operator|(
name|categories
operator|!=
literal|null
condition|?
name|categories
operator|.
name|hashCode
argument_list|()
else|:
literal|0
operator|)
operator|+
operator|(
name|pos
operator|!=
literal|null
condition|?
name|pos
operator|.
name|hashCode
argument_list|()
else|:
literal|0
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|obj
operator|instanceof
name|PosTagInfo
operator|&&
name|tag
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|&&
name|categories
operator|.
name|equals
argument_list|(
name|categories
argument_list|)
operator|&&
name|pos
operator|.
name|equals
argument_list|(
name|pos
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

