begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|yard
operator|.
name|solr
operator|.
name|model
package|;
end_package

begin_class
specifier|public
specifier|final
class|class
name|IndexValue
block|{
specifier|private
specifier|final
name|String
name|value
decl_stmt|;
specifier|private
specifier|final
name|IndexDataType
name|type
decl_stmt|;
specifier|private
specifier|final
name|String
name|language
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|hasLanguage
decl_stmt|;
comment|/**      * Calculate the hash only once      */
specifier|private
specifier|final
name|int
name|_hash
decl_stmt|;
specifier|public
name|IndexValue
parameter_list|(
name|String
name|value
parameter_list|,
name|IndexDataType
name|type
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
name|type
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|IndexValue
parameter_list|(
name|String
name|value
parameter_list|,
name|IndexDataType
name|type
parameter_list|,
name|String
name|language
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
name|type
argument_list|,
name|language
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|private
name|IndexValue
parameter_list|(
name|String
name|value
parameter_list|,
name|IndexDataType
name|type
parameter_list|,
name|String
name|language
parameter_list|,
name|boolean
name|hasLanguage
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The value MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The IndexType MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|hasLanguage
operator|=
name|hasLanguage
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|hasLanguage
condition|)
block|{
name|this
operator|.
name|language
operator|=
name|language
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|language
operator|=
literal|null
expr_stmt|;
block|}
name|this
operator|.
name|_hash
operator|=
name|type
operator|.
name|hashCode
argument_list|()
operator|+
name|value
operator|.
name|hashCode
argument_list|()
operator|+
operator|(
name|language
operator|!=
literal|null
condition|?
name|language
operator|.
name|hashCode
argument_list|()
else|:
literal|0
operator|)
expr_stmt|;
block|}
specifier|public
specifier|final
name|String
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
specifier|public
specifier|final
name|IndexDataType
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
specifier|final
name|String
name|getLanguage
parameter_list|()
block|{
return|return
name|language
return|;
block|}
specifier|public
specifier|final
name|boolean
name|hasLanguage
parameter_list|()
block|{
return|return
name|hasLanguage
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|_hash
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
operator|!=
literal|null
operator|&&
name|obj
operator|instanceof
name|IndexValue
operator|&&
operator|(
operator|(
name|IndexValue
operator|)
name|obj
operator|)
operator|.
name|value
operator|.
name|equals
argument_list|(
name|value
argument_list|)
operator|&&
operator|(
operator|(
name|IndexValue
operator|)
name|obj
operator|)
operator|.
name|type
operator|.
name|equals
argument_list|(
name|type
argument_list|)
operator|&&
operator|(
operator|(
name|IndexValue
operator|)
name|obj
operator|)
operator|.
name|hasLanguage
operator|==
name|hasLanguage
operator|&&
operator|(
operator|(
name|language
operator|==
literal|null
operator|&&
operator|(
operator|(
name|IndexValue
operator|)
name|obj
operator|)
operator|.
name|language
operator|==
literal|null
operator|)
operator|||
operator|(
name|language
operator|!=
literal|null
operator|&&
name|language
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|IndexValue
operator|)
name|obj
operator|)
operator|.
name|language
argument_list|)
operator|)
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|value
operator|+
operator|(
name|language
operator|!=
literal|null
condition|?
operator|(
literal|"@"
operator|+
name|language
operator|)
else|:
literal|""
operator|)
operator|+
literal|"^^"
operator|+
name|type
return|;
block|}
block|}
end_class

end_unit

