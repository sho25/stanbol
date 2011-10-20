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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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

begin_class
specifier|public
class|class
name|JsonLdProperty
block|{
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|List
argument_list|<
name|JsonLdPropertyValue
argument_list|>
name|values
init|=
operator|new
name|ArrayList
argument_list|<
name|JsonLdPropertyValue
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|type
decl_stmt|;
specifier|public
name|JsonLdProperty
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
name|JsonLdProperty
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|values
operator|.
name|add
argument_list|(
operator|new
name|JsonLdPropertyValue
argument_list|(
name|value
argument_list|)
argument_list|)
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
specifier|public
name|void
name|addValue
parameter_list|(
name|JsonLdPropertyValue
name|value
parameter_list|)
block|{
name|this
operator|.
name|values
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addSingleValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|values
operator|.
name|add
argument_list|(
operator|new
name|JsonLdPropertyValue
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|JsonLdPropertyValue
argument_list|>
name|getValues
parameter_list|()
block|{
return|return
name|this
operator|.
name|values
return|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
comment|// Compute the type by examine the value types
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|JsonLdPropertyValue
name|value
range|:
name|this
operator|.
name|values
control|)
block|{
if|if
condition|(
name|value
operator|.
name|getType
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|this
operator|.
name|type
operator|=
name|value
operator|.
name|getType
argument_list|()
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|this
operator|.
name|type
operator|.
name|equals
argument_list|(
name|value
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|type
operator|=
literal|null
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
else|else
block|{
comment|// If any value has another type than specified
comment|// by this type, we return NULL because
comment|// it's a multityped property.
for|for
control|(
name|JsonLdPropertyValue
name|value
range|:
name|this
operator|.
name|values
control|)
block|{
if|if
condition|(
name|value
operator|.
name|getType
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|type
operator|.
name|equals
argument_list|(
name|value
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|type
operator|=
literal|null
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
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
name|boolean
name|isSingleValued
parameter_list|()
block|{
return|return
name|this
operator|.
name|values
operator|.
name|size
argument_list|()
operator|==
literal|1
return|;
block|}
block|}
end_class

end_unit
