begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|factstore
operator|.
name|model
package|;
end_package

begin_class
specifier|public
class|class
name|WhereClause
block|{
specifier|private
name|CompareOperator
name|compareOperator
decl_stmt|;
specifier|private
name|String
name|comparedRole
decl_stmt|;
specifier|private
name|String
name|searchedValue
decl_stmt|;
specifier|public
name|CompareOperator
name|getCompareOperator
parameter_list|()
block|{
return|return
name|compareOperator
return|;
block|}
specifier|public
name|void
name|setCompareOperator
parameter_list|(
name|CompareOperator
name|compareOperator
parameter_list|)
block|{
name|this
operator|.
name|compareOperator
operator|=
name|compareOperator
expr_stmt|;
block|}
specifier|public
name|String
name|getComparedRole
parameter_list|()
block|{
return|return
name|comparedRole
return|;
block|}
specifier|public
name|void
name|setComparedRole
parameter_list|(
name|String
name|comparedValue
parameter_list|)
block|{
name|this
operator|.
name|comparedRole
operator|=
name|comparedValue
expr_stmt|;
block|}
specifier|public
name|String
name|getSearchedValue
parameter_list|()
block|{
return|return
name|searchedValue
return|;
block|}
specifier|public
name|void
name|setSearchedValue
parameter_list|(
name|String
name|searchedValue
parameter_list|)
block|{
name|this
operator|.
name|searchedValue
operator|=
name|searchedValue
expr_stmt|;
block|}
block|}
end_class

end_unit

