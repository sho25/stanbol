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
name|jcr
operator|.
name|repository
package|;
end_package

begin_class
specifier|public
class|class
name|JCRQueryRepresentation
block|{
specifier|private
name|String
name|queryString
decl_stmt|;
specifier|private
name|String
name|queryType
decl_stmt|;
specifier|public
name|String
name|getQueryString
parameter_list|()
block|{
return|return
name|queryString
return|;
block|}
specifier|public
name|void
name|setQueryString
parameter_list|(
name|String
name|queryString
parameter_list|)
block|{
name|this
operator|.
name|queryString
operator|=
name|queryString
expr_stmt|;
block|}
specifier|public
name|JCRQueryRepresentation
parameter_list|(
name|String
name|queryString
parameter_list|,
name|String
name|queryType
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|queryString
operator|=
name|queryString
expr_stmt|;
name|this
operator|.
name|queryType
operator|=
name|queryType
expr_stmt|;
block|}
specifier|public
name|String
name|getQueryType
parameter_list|()
block|{
return|return
name|queryType
return|;
block|}
specifier|public
name|void
name|setQueryType
parameter_list|(
name|String
name|queryType
parameter_list|)
block|{
name|this
operator|.
name|queryType
operator|=
name|queryType
expr_stmt|;
block|}
block|}
end_class

end_unit

