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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ConcurrentModificationException
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_comment
comment|/**  * A {@link Span} that may enclose other Spans. Super type for {@link Chunk}s,  * {@link Sentence}s and {@link AnalysedText}.<p>  * As {@link Span} this is an meta (abstract) type. Implementations of this  * Interface SHOULD BE abstract Classes.   */
end_comment

begin_interface
specifier|public
interface|interface
name|Section
extends|extends
name|Span
block|{
comment|/**      * Iterates over all Span enclosed by this one that are of any of the      * parsed Types.<p>      * Returned Iterators MUST NOT throw {@link ConcurrentModificationException}      * but consider additions of Spans.      * @param types the {@link SpanTypeEnum types} of Spans included      * @return sorted iterator over the selected Spans.      */
name|Iterator
argument_list|<
name|Span
argument_list|>
name|getEnclosed
parameter_list|(
name|Set
argument_list|<
name|SpanTypeEnum
argument_list|>
name|types
parameter_list|)
function_decl|;
comment|/**      * Adds an Token relative to this Sentence      * @param start the start of the token relative to the sentence      * @param end      * @return      */
name|Token
name|addToken
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
function_decl|;
comment|/**      * The Tokens covered by this Sentence.<p>      * Returned Iterators MUST NOT throw {@link ConcurrentModificationException}      * but consider additions of Spans.      * @return the tokens      */
name|Iterator
argument_list|<
name|Token
argument_list|>
name|getTokens
parameter_list|()
function_decl|;
block|}
end_interface

end_unit
