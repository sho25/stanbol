begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|cmis
operator|.
name|repository
package|;
end_package

begin_comment
comment|//Used for properties like cmis:localname, cmis:localnamespace
end_comment

begin_enum
specifier|public
enum|enum
name|CMISProperty
block|{
comment|//    LOCAL_NAME("cmis:localname"),
comment|//    LOCAL_NAMESPACE("cmis:localnamespace"),
name|PATH
argument_list|(
literal|"cmis:path"
argument_list|)
block|,
name|ID
argument_list|(
literal|"cmis:id"
argument_list|)
block|;
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
name|CMISProperty
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
block|}
end_enum

end_unit

