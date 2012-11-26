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

begin_interface
specifier|public
interface|interface
name|Sentence
extends|extends
name|Section
block|{
comment|/**      * Returns {@link SpanTypeEnum#Sentence}      * @see Span#getType()      * @see SpanTypeEnum#Sentence      */
name|SpanTypeEnum
name|getType
parameter_list|()
function_decl|;
comment|/**      * Adds an Chunk relative to this Sentence      * @param start the start of the chunk relative to the sentence      * @param end      * @return      */
name|Chunk
name|addChunk
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
function_decl|;
comment|/**      * The Chunks covered by this Sentence<p>      * Returned Iterators MUST NOT throw {@link ConcurrentModificationException}      * but consider additions of Spans.      * @return the chunks      */
name|Iterator
argument_list|<
name|Chunk
argument_list|>
name|getChunks
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

