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
name|base
operator|.
name|api
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|Rule
import|;
end_import

begin_class
specifier|public
class|class
name|RuleIterator
implements|implements
name|Iterator
argument_list|<
name|Rule
argument_list|>
block|{
specifier|private
name|int
name|currentIndex
decl_stmt|;
specifier|private
name|int
name|listSize
decl_stmt|;
specifier|private
name|Rule
index|[]
name|semionRules
decl_stmt|;
specifier|public
name|RuleIterator
parameter_list|(
name|RuleList
name|semionRuleList
parameter_list|)
block|{
name|this
operator|.
name|listSize
operator|=
name|semionRuleList
operator|.
name|size
argument_list|()
expr_stmt|;
name|this
operator|.
name|semionRules
operator|=
operator|new
name|Rule
index|[
name|listSize
index|]
expr_stmt|;
name|this
operator|.
name|semionRules
operator|=
name|semionRuleList
operator|.
name|toArray
argument_list|(
name|this
operator|.
name|semionRules
argument_list|)
expr_stmt|;
name|this
operator|.
name|currentIndex
operator|=
literal|0
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
if|if
condition|(
name|currentIndex
operator|<
operator|(
name|listSize
operator|)
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|public
name|Rule
name|next
parameter_list|()
block|{
name|Rule
name|semionRule
init|=
name|semionRules
index|[
name|currentIndex
index|]
decl_stmt|;
name|currentIndex
operator|++
expr_stmt|;
return|return
name|semionRule
return|;
block|}
specifier|public
name|void
name|remove
parameter_list|()
block|{ 		 	}
block|}
end_class

end_unit

