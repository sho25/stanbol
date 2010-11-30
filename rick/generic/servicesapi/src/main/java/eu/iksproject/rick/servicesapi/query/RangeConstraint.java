begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|query
package|;
end_package

begin_class
specifier|public
class|class
name|RangeConstraint
extends|extends
name|Constraint
block|{
specifier|private
specifier|final
name|Object
name|lowerBound
decl_stmt|;
specifier|private
specifier|final
name|Object
name|upperBound
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|inclusive
decl_stmt|;
specifier|public
name|RangeConstraint
parameter_list|(
name|Object
name|lowerBound
parameter_list|,
name|Object
name|upperBound
parameter_list|,
name|boolean
name|inclusive
parameter_list|)
block|{
name|super
argument_list|(
name|ConstraintType
operator|.
name|range
argument_list|)
expr_stmt|;
if|if
condition|(
name|lowerBound
operator|==
literal|null
operator|&&
name|upperBound
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|" At least one of \"lower bound\" and \"upper bound\" MUST BE defined"
argument_list|)
throw|;
block|}
comment|//TODO eventually check if upper and lower bound are of the same type
name|this
operator|.
name|lowerBound
operator|=
name|lowerBound
expr_stmt|;
name|this
operator|.
name|upperBound
operator|=
name|upperBound
expr_stmt|;
name|this
operator|.
name|inclusive
operator|=
name|inclusive
expr_stmt|;
block|}
comment|/** 	 * @return the lowerBound 	 */
specifier|public
name|Object
name|getLowerBound
parameter_list|()
block|{
return|return
name|lowerBound
return|;
block|}
comment|/** 	 * @return the upperBound 	 */
specifier|public
name|Object
name|getUpperBound
parameter_list|()
block|{
return|return
name|upperBound
return|;
block|}
comment|/** 	 * @return the inclusive 	 */
specifier|public
name|boolean
name|isInclusive
parameter_list|()
block|{
return|return
name|inclusive
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"RangeConstraint[lower=%s|upper=%s|%sclusive]"
argument_list|,
name|lowerBound
operator|!=
literal|null
condition|?
name|lowerBound
else|:
literal|"*"
argument_list|,
name|upperBound
operator|!=
literal|null
condition|?
name|upperBound
else|:
literal|"*"
argument_list|,
name|inclusive
condition|?
literal|"in"
else|:
literal|"ex"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

