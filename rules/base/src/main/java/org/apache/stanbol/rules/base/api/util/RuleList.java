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
name|Collection
import|;
end_import

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
name|util
operator|.
name|RuleIterator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
specifier|public
class|class
name|RuleList
implements|implements
name|Collection
argument_list|<
name|Rule
argument_list|>
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
name|Rule
index|[]
name|kReSRules
decl_stmt|;
specifier|public
name|RuleList
parameter_list|()
block|{  	}
specifier|public
name|boolean
name|add
parameter_list|(
name|Rule
name|semionRule
parameter_list|)
block|{
if|if
condition|(
name|kReSRules
operator|==
literal|null
condition|)
block|{
name|kReSRules
operator|=
operator|new
name|Rule
index|[
literal|1
index|]
expr_stmt|;
name|kReSRules
index|[
literal|0
index|]
operator|=
name|semionRule
expr_stmt|;
block|}
else|else
block|{
name|Rule
index|[]
name|semionRulesCopy
init|=
operator|new
name|Rule
index|[
name|kReSRules
operator|.
name|length
operator|+
literal|1
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|kReSRules
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
argument_list|,
literal|0
argument_list|,
name|kReSRules
operator|.
name|length
argument_list|)
expr_stmt|;
name|semionRulesCopy
index|[
name|semionRulesCopy
operator|.
name|length
operator|-
literal|1
index|]
operator|=
name|semionRule
expr_stmt|;
name|kReSRules
operator|=
name|semionRulesCopy
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Added rule "
operator|+
name|semionRule
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Added rule "
operator|+
name|semionRule
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|addToHead
parameter_list|(
name|Rule
name|semionRule
parameter_list|)
block|{
if|if
condition|(
name|kReSRules
operator|==
literal|null
condition|)
block|{
name|kReSRules
operator|=
operator|new
name|Rule
index|[
literal|1
index|]
expr_stmt|;
name|kReSRules
index|[
literal|0
index|]
operator|=
name|semionRule
expr_stmt|;
block|}
else|else
block|{
name|Rule
index|[]
name|semionRulesCopy
init|=
operator|new
name|Rule
index|[
name|kReSRules
operator|.
name|length
operator|+
literal|1
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|kReSRules
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
argument_list|,
literal|1
argument_list|,
name|kReSRules
operator|.
name|length
argument_list|)
expr_stmt|;
name|semionRulesCopy
index|[
literal|0
index|]
operator|=
name|semionRule
expr_stmt|;
name|kReSRules
operator|=
name|semionRulesCopy
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Added to HEAD rule "
operator|+
name|semionRule
argument_list|,
name|this
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Added to HEAD rule "
operator|+
name|semionRule
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|addAll
parameter_list|(
name|Collection
argument_list|<
name|?
extends|extends
name|Rule
argument_list|>
name|c
parameter_list|)
block|{
name|Rule
index|[]
name|collectionOfSemionRules
init|=
operator|new
name|Rule
index|[
name|c
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|collectionOfSemionRules
operator|=
name|c
operator|.
name|toArray
argument_list|(
name|collectionOfSemionRules
argument_list|)
expr_stmt|;
if|if
condition|(
name|kReSRules
operator|==
literal|null
condition|)
block|{
name|kReSRules
operator|=
name|collectionOfSemionRules
expr_stmt|;
block|}
else|else
block|{
name|Rule
index|[]
name|semionRulesCopy
init|=
operator|new
name|Rule
index|[
name|kReSRules
operator|.
name|length
operator|+
name|collectionOfSemionRules
operator|.
name|length
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|kReSRules
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
argument_list|,
literal|0
argument_list|,
name|kReSRules
operator|.
name|length
argument_list|)
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|collectionOfSemionRules
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
argument_list|,
name|kReSRules
operator|.
name|length
argument_list|,
name|collectionOfSemionRules
operator|.
name|length
argument_list|)
expr_stmt|;
name|kReSRules
operator|=
name|semionRulesCopy
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Added all rules : "
operator|+
name|c
argument_list|,
name|this
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|/** 	 * To clear the collection 	 */
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|this
operator|.
name|kReSRules
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|boolean
name|contains
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
for|for
control|(
name|Rule
name|semionRule
range|:
name|kReSRules
control|)
block|{
if|if
condition|(
name|semionRule
operator|.
name|equals
argument_list|(
name|o
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|containsAll
parameter_list|(
name|Collection
argument_list|<
name|?
argument_list|>
name|c
parameter_list|)
block|{
for|for
control|(
name|Object
name|o
range|:
name|c
control|)
block|{
for|for
control|(
name|Rule
name|semionRule
range|:
name|kReSRules
control|)
block|{
if|if
condition|(
operator|!
name|semionRule
operator|.
name|equals
argument_list|(
name|o
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
else|else
block|{
break|break;
block|}
block|}
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
if|if
condition|(
name|kReSRules
operator|==
literal|null
operator|||
operator|(
name|kReSRules
operator|.
name|length
operator|==
literal|1
operator|&&
name|kReSRules
index|[
literal|0
index|]
operator|==
literal|null
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
name|Iterator
argument_list|<
name|Rule
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
operator|new
name|RuleIterator
argument_list|(
name|this
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|remove
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
name|boolean
name|removed
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|kReSRules
operator|.
name|length
operator|&&
operator|!
name|removed
condition|;
name|i
operator|++
control|)
block|{
name|Rule
name|semionRule
init|=
name|kReSRules
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|semionRule
operator|.
name|equals
argument_list|(
name|o
argument_list|)
condition|)
block|{
name|Rule
index|[]
name|semionRulesCopy
init|=
operator|new
name|Rule
index|[
name|kReSRules
operator|.
name|length
operator|-
literal|1
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|kReSRules
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
argument_list|,
literal|0
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|kReSRules
argument_list|,
name|i
operator|+
literal|1
argument_list|,
name|semionRulesCopy
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
operator|.
name|length
operator|-
name|i
argument_list|)
expr_stmt|;
name|kReSRules
operator|=
name|semionRulesCopy
expr_stmt|;
name|removed
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|removed
return|;
block|}
specifier|public
name|boolean
name|removeAll
parameter_list|(
name|Collection
argument_list|<
name|?
argument_list|>
name|c
parameter_list|)
block|{
if|if
condition|(
name|contains
argument_list|(
name|c
argument_list|)
condition|)
block|{
for|for
control|(
name|Object
name|o
range|:
name|c
control|)
block|{
name|boolean
name|removed
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|kReSRules
operator|.
name|length
operator|&&
operator|!
name|removed
condition|;
name|i
operator|++
control|)
block|{
name|Rule
name|semionRule
init|=
name|kReSRules
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|semionRule
operator|.
name|equals
argument_list|(
name|o
argument_list|)
condition|)
block|{
name|Rule
index|[]
name|semionRulesCopy
init|=
operator|new
name|Rule
index|[
name|kReSRules
operator|.
name|length
operator|-
literal|1
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|kReSRules
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
argument_list|,
literal|0
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|kReSRules
argument_list|,
name|i
operator|+
literal|1
argument_list|,
name|semionRulesCopy
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
operator|.
name|length
operator|-
name|i
argument_list|)
expr_stmt|;
name|kReSRules
operator|=
name|semionRulesCopy
expr_stmt|;
name|removed
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
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
name|boolean
name|retainAll
parameter_list|(
name|Collection
argument_list|<
name|?
argument_list|>
name|c
parameter_list|)
block|{
name|Rule
index|[]
name|semionRulesCopy
init|=
literal|null
decl_stmt|;
name|Rule
index|[]
name|semionRulesTMP
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Object
name|o
range|:
name|c
control|)
block|{
if|if
condition|(
name|o
operator|instanceof
name|Rule
condition|)
block|{
if|if
condition|(
name|contains
argument_list|(
name|o
argument_list|)
condition|)
block|{
if|if
condition|(
name|semionRulesCopy
operator|==
literal|null
condition|)
block|{
name|semionRulesCopy
operator|=
operator|new
name|Rule
index|[
literal|1
index|]
expr_stmt|;
name|semionRulesCopy
index|[
literal|0
index|]
operator|=
operator|(
name|Rule
operator|)
name|o
expr_stmt|;
block|}
else|else
block|{
name|semionRulesTMP
operator|=
operator|new
name|Rule
index|[
name|semionRulesCopy
operator|.
name|length
operator|+
literal|1
index|]
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|semionRulesCopy
argument_list|,
literal|0
argument_list|,
name|semionRulesTMP
argument_list|,
literal|0
argument_list|,
name|semionRulesCopy
operator|.
name|length
argument_list|)
expr_stmt|;
name|semionRulesTMP
index|[
name|semionRulesTMP
operator|.
name|length
operator|-
literal|1
index|]
operator|=
operator|(
name|Rule
operator|)
name|o
expr_stmt|;
name|semionRulesCopy
operator|=
name|semionRulesTMP
expr_stmt|;
block|}
block|}
block|}
block|}
name|kReSRules
operator|=
name|semionRulesCopy
expr_stmt|;
return|return
literal|true
return|;
block|}
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|kReSRules
operator|.
name|length
return|;
block|}
specifier|public
name|Object
index|[]
name|toArray
parameter_list|()
block|{
return|return
name|kReSRules
return|;
block|}
specifier|public
parameter_list|<
name|T
parameter_list|>
name|T
index|[]
name|toArray
parameter_list|(
name|T
index|[]
name|a
parameter_list|)
block|{
return|return
operator|(
name|T
index|[]
operator|)
name|kReSRules
return|;
block|}
block|}
end_class

end_unit

