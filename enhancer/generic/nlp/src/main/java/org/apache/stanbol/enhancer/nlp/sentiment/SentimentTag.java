begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|sentiment
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|annotation
operator|.
name|Value
import|;
end_import

begin_comment
comment|/**  * The sentiment {@link #POSITIVE} or {@link SentimentTag#NEGATIVE}. The  * value is directly represented by the {@link Value#probability()}.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|SentimentTag
block|{
comment|/**      * A positive sentiment tag      */
specifier|public
specifier|static
specifier|final
name|SentimentTag
name|POSITIVE
init|=
operator|new
name|SentimentTag
argument_list|(
literal|true
argument_list|)
decl_stmt|;
comment|/**      * A negative sentiment tag      */
specifier|public
specifier|static
specifier|final
name|SentimentTag
name|NEGATIVE
init|=
operator|new
name|SentimentTag
argument_list|(
literal|false
argument_list|)
decl_stmt|;
comment|/**      * positive if<code>true</code> otherwise negative.      */
specifier|private
specifier|final
name|boolean
name|positive
decl_stmt|;
comment|/**      * Singleton constructor      */
specifier|private
name|SentimentTag
parameter_list|(
name|boolean
name|positive
parameter_list|)
block|{
name|this
operator|.
name|positive
operator|=
name|positive
expr_stmt|;
block|}
comment|/**      * If the {@link Value#probability() sentiment} is positive      */
specifier|public
specifier|final
name|boolean
name|isPositive
parameter_list|()
block|{
return|return
name|positive
return|;
block|}
comment|/**      * If the {@link Value#probability() sentiment} is negative      */
specifier|public
specifier|final
name|boolean
name|isNegative
parameter_list|()
block|{
return|return
operator|!
name|positive
return|;
block|}
block|}
end_class

end_unit

