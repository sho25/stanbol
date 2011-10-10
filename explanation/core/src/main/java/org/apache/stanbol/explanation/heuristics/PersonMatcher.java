begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
operator|.
name|heuristics
package|;
end_package

begin_class
specifier|public
class|class
name|PersonMatcher
implements|implements
name|Matcher
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|matches
parameter_list|(
name|Entity
name|arg0
parameter_list|,
name|Entity
name|arg1
parameter_list|)
block|{
for|for
control|(
name|Identifier
name|id0
range|:
name|arg0
operator|.
name|getIDs
argument_list|()
control|)
for|for
control|(
name|Identifier
name|id1
range|:
name|arg1
operator|.
name|getIDs
argument_list|()
control|)
switch|switch
condition|(
name|id1
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|CMS_USERNAME
case|:
return|return
name|id0
operator|.
name|getType
argument_list|()
operator|==
name|IDTypes
operator|.
name|CMS_USERNAME
operator|&&
name|id0
operator|.
name|equals
argument_list|(
name|id1
argument_list|)
return|;
case|case
name|FOAF_ID
case|:
return|return
name|id0
operator|.
name|getType
argument_list|()
operator|==
name|IDTypes
operator|.
name|FOAF_ID
operator|&&
name|id0
operator|.
name|equals
argument_list|(
name|id1
argument_list|)
return|;
case|case
name|SSN
case|:
return|return
name|id0
operator|.
name|getType
argument_list|()
operator|==
name|IDTypes
operator|.
name|SSN
operator|&&
name|id0
operator|.
name|equals
argument_list|(
name|id1
argument_list|)
return|;
default|default:
return|return
literal|false
return|;
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

