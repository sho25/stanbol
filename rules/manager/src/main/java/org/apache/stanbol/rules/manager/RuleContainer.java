begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|manager
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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

begin_class
specifier|public
class|class
name|RuleContainer
block|{
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|semionRules
decl_stmt|;
specifier|public
name|RuleContainer
parameter_list|()
block|{
name|semionRules
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addSemionRule
parameter_list|(
name|String
name|ruleName
parameter_list|,
name|String
name|rule
parameter_list|)
block|{
name|semionRules
operator|.
name|put
argument_list|(
name|ruleName
argument_list|,
name|rule
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getSemionRule
parameter_list|(
name|String
name|ruleName
parameter_list|)
block|{
return|return
name|semionRules
operator|.
name|get
argument_list|(
name|ruleName
argument_list|)
return|;
block|}
specifier|public
name|String
name|removeSemionRule
parameter_list|(
name|String
name|ruleName
parameter_list|)
block|{
return|return
name|semionRules
operator|.
name|remove
argument_list|(
name|ruleName
argument_list|)
return|;
block|}
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|listSemionRuleNames
parameter_list|()
block|{
return|return
name|semionRules
operator|.
name|keySet
argument_list|()
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|listSemionRules
parameter_list|()
block|{
return|return
name|semionRules
operator|.
name|values
argument_list|()
return|;
block|}
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|semionRules
operator|.
name|size
argument_list|()
return|;
block|}
block|}
end_class

end_unit

