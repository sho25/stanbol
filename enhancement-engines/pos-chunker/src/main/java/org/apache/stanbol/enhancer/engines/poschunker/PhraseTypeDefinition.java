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
name|engines
operator|.
name|poschunker
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
name|Set
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

begin_class
specifier|public
class|class
name|PhraseTypeDefinition
block|{
specifier|protected
specifier|final
name|LexicalCategory
name|phraseType
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|startTypes
decl_stmt|;
specifier|protected
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|readOnlyStartTypes
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|continuationTypes
decl_stmt|;
specifier|protected
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|readOnlyContinuationTypes
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|requiredTypes
decl_stmt|;
specifier|protected
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|readOnlyRequiredTypes
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|endTypes
decl_stmt|;
specifier|protected
specifier|final
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|readOnlyEndTypes
decl_stmt|;
specifier|public
name|PhraseTypeDefinition
parameter_list|(
name|LexicalCategory
name|phraseType
parameter_list|)
block|{
if|if
condition|(
name|phraseType
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed PhraseType MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|phraseType
operator|=
name|phraseType
expr_stmt|;
name|startTypes
operator|=
name|EnumSet
operator|.
name|of
argument_list|(
name|phraseType
argument_list|)
expr_stmt|;
name|readOnlyStartTypes
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|startTypes
argument_list|)
expr_stmt|;
name|continuationTypes
operator|=
name|EnumSet
operator|.
name|of
argument_list|(
name|phraseType
argument_list|)
expr_stmt|;
name|readOnlyContinuationTypes
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|continuationTypes
argument_list|)
expr_stmt|;
name|requiredTypes
operator|=
name|EnumSet
operator|.
name|of
argument_list|(
name|phraseType
argument_list|)
expr_stmt|;
name|readOnlyRequiredTypes
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|requiredTypes
argument_list|)
expr_stmt|;
name|endTypes
operator|=
name|EnumSet
operator|.
name|of
argument_list|(
name|phraseType
argument_list|)
expr_stmt|;
name|readOnlyEndTypes
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|startTypes
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|addStartType
parameter_list|(
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
return|return
name|add
argument_list|(
name|startTypes
argument_list|,
name|types
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|addContinuationType
parameter_list|(
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
return|return
name|add
argument_list|(
name|continuationTypes
argument_list|,
name|types
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|addRequiredType
parameter_list|(
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
return|return
name|add
argument_list|(
name|requiredTypes
argument_list|,
name|types
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|addEndType
parameter_list|(
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
return|return
name|add
argument_list|(
name|endTypes
argument_list|,
name|types
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|removeStartType
parameter_list|(
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
return|return
name|remove
argument_list|(
name|startTypes
argument_list|,
name|types
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|removeContinuationType
parameter_list|(
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
return|return
name|remove
argument_list|(
name|continuationTypes
argument_list|,
name|types
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|removeRequiredType
parameter_list|(
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
return|return
name|remove
argument_list|(
name|requiredTypes
argument_list|,
name|types
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|removeEndType
parameter_list|(
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
return|return
name|remove
argument_list|(
name|endTypes
argument_list|,
name|types
argument_list|)
return|;
block|}
comment|/**      * Getter for the type of this phrase definition      * @return      */
specifier|public
name|LexicalCategory
name|getPhraseType
parameter_list|()
block|{
return|return
name|phraseType
return|;
block|}
comment|/**      * Getter for the read only set with the start types      * @return the read only set with {@link LexicalCategory LexicalCategories}      * that can start a phrase of that type      */
specifier|public
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|getStartType
parameter_list|()
block|{
return|return
name|readOnlyStartTypes
return|;
block|}
comment|/**      * Getter for the read only set with the continuation types      * @return the read only set with {@link LexicalCategory LexicalCategories}      * that can continue a phrase of that type      */
specifier|public
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|getContinuationType
parameter_list|()
block|{
return|return
name|readOnlyContinuationTypes
return|;
block|}
comment|/**      * Getter for the read only set with the required types      * @return the read only set with {@link LexicalCategory LexicalCategories}      * that MUST occur within a phrase of that type      */
specifier|public
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|getRequiredType
parameter_list|()
block|{
return|return
name|readOnlyRequiredTypes
return|;
block|}
comment|/**      * Getter for the read only set with the end types.      * @return the read only set with {@link LexicalCategory LexicalCategories}      * that can end a phrase of that type      */
specifier|public
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|getEndType
parameter_list|()
block|{
return|return
name|readOnlyEndTypes
return|;
block|}
specifier|private
name|boolean
name|add
parameter_list|(
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|set
parameter_list|,
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
name|boolean
name|changed
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|types
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|LexicalCategory
name|type
range|:
name|types
control|)
block|{
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|set
operator|.
name|add
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|changed
return|;
block|}
specifier|private
name|boolean
name|remove
parameter_list|(
name|Set
argument_list|<
name|LexicalCategory
argument_list|>
name|set
parameter_list|,
name|LexicalCategory
modifier|...
name|types
parameter_list|)
block|{
name|boolean
name|changed
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|types
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|LexicalCategory
name|type
range|:
name|types
control|)
block|{
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|set
operator|.
name|remove
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|changed
return|;
block|}
block|}
end_class

end_unit

