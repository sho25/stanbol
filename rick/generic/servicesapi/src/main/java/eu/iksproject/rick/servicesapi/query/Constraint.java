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

begin_comment
comment|/**  * Abstract base class for all types of Constraints.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Constraint
block|{
comment|/**      * Defines the enumeration of available Constraints.      * TODO Maybe we need a more "extensible" way to define different constraints      * in future      * @author Rupert Westenthaler      *      */
specifier|public
specifier|static
enum|enum
name|ConstraintType
block|{
comment|// NOTE (2010-Nov-09,rw) Because a reference constraint is now a special kind of
comment|//                       a value constraint.
comment|//        /**
comment|//         * Constraints a field to have a specific reference
comment|//         */
comment|//        reference,
comment|/**          * Constraints the value and possible the dataType          */
name|value
block|,
comment|/**          * Constraints a field to have a value within a range          */
name|range
block|,
comment|/**          * Constraints a field to have a lexical value          */
name|text
comment|//TODO: value Type for checking non lexical values
block|}
specifier|private
specifier|final
name|ConstraintType
name|type
decl_stmt|;
specifier|protected
name|Constraint
parameter_list|(
name|ConstraintType
name|type
parameter_list|)
block|{
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
literal|"The ConstraintType MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
comment|/**      * Getter for the type of the constraint.      * @return The type of the constraint      */
specifier|public
specifier|final
name|ConstraintType
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
block|}
end_class

end_unit

