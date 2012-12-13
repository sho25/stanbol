begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|geonames
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
name|java
operator|.
name|util
operator|.
name|StringTokenizer
import|;
end_import

begin_class
specifier|public
class|class
name|LineTokenizer
implements|implements
name|Iterator
argument_list|<
name|String
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|String
name|DELIM
init|=
literal|"\t"
decl_stmt|;
specifier|private
specifier|final
name|StringTokenizer
name|t
decl_stmt|;
specifier|private
name|boolean
name|prevElementWasNull
init|=
literal|true
decl_stmt|;
specifier|public
name|LineTokenizer
parameter_list|(
name|String
name|data
parameter_list|)
block|{
name|t
operator|=
operator|new
name|StringTokenizer
argument_list|(
name|data
argument_list|,
name|DELIM
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|t
operator|.
name|hasMoreTokens
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|next
parameter_list|()
block|{
if|if
condition|(
operator|!
name|prevElementWasNull
condition|)
block|{
name|t
operator|.
name|nextElement
argument_list|()
expr_stmt|;
comment|//dump the delim
block|}
if|if
condition|(
operator|!
name|t
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
comment|//this indicated, that the current Element is
comment|// - the last Element
comment|// - and is null
name|prevElementWasNull
operator|=
literal|true
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
name|String
name|act
init|=
name|t
operator|.
name|nextToken
argument_list|()
decl_stmt|;
if|if
condition|(
name|DELIM
operator|.
name|equals
argument_list|(
name|act
argument_list|)
condition|)
block|{
name|prevElementWasNull
operator|=
literal|true
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
name|prevElementWasNull
operator|=
literal|false
expr_stmt|;
return|return
name|act
return|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
block|}
end_class

end_unit
