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
name|model
package|;
end_package

begin_interface
specifier|public
interface|interface
name|Token
extends|extends
name|Span
block|{
comment|/**      * Returns {@link SpanTypeEnum#Token}      * @see Span#getType()      * @see SpanTypeEnum#Token      */
name|SpanTypeEnum
name|getType
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

