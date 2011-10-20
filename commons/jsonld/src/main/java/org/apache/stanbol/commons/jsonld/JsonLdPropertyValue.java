begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
package|;
end_package

begin_class
specifier|public
class|class
name|JsonLdPropertyValue
block|{
specifier|private
name|Object
name|value
decl_stmt|;
specifier|private
name|String
name|type
decl_stmt|;
specifier|private
name|String
name|language
decl_stmt|;
specifier|public
name|JsonLdPropertyValue
parameter_list|()
block|{              }
specifier|public
name|JsonLdPropertyValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|JsonLdIRI
condition|)
block|{
name|JsonLdIRI
name|iriValue
init|=
operator|(
name|JsonLdIRI
operator|)
name|value
decl_stmt|;
name|this
operator|.
name|value
operator|=
name|iriValue
operator|.
name|getIRI
argument_list|()
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|JsonLdCommon
operator|.
name|IRI
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
block|}
specifier|public
name|Object
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
specifier|public
name|String
name|getLiteralValue
parameter_list|()
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
return|;
block|}
specifier|public
name|void
name|setValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getLanguage
parameter_list|()
block|{
return|return
name|language
return|;
block|}
specifier|public
name|void
name|setLanguage
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|this
operator|.
name|language
operator|=
name|language
expr_stmt|;
block|}
block|}
end_class

end_unit
