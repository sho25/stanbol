begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

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
name|NavigableMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|NoSuchElementException
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|SortedSet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|collections
operator|.
name|IteratorUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|collections
operator|.
name|Predicate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|collections
operator|.
name|functors
operator|.
name|InstanceofPredicate
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Chunk
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Section
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Sentence
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Span
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|SpanTypeEnum
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Token
import|;
end_import

begin_comment
comment|/**  * A Span that contains other spans  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|SectionImpl
extends|extends
name|SpanImpl
implements|implements
name|Section
block|{
comment|/**      * Allows to create a SectionImpl without setting the AnalysedText context.      * {@link #setContext(AnalysedTextImpl)} needs to be called before using      * this instance.<p>      * NOTE: this constructor is needed to instantiate {@link AnalysedTextImpl}.      * @param type the type. MUST NOT be<code>null</code> nor {@link SpanTypeEnum#Token}      * @param start      * @param end      */
specifier|public
name|SectionImpl
parameter_list|(
name|SpanTypeEnum
name|type
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|super
argument_list|(
name|type
argument_list|,
name|start
argument_list|,
name|end
argument_list|)
expr_stmt|;
assert|assert
name|type
operator|!=
name|SpanTypeEnum
operator|.
name|Token
operator|:
literal|"The SpanType 'Token' is NOT a Section - can not cover other spans!"
assert|;
block|}
comment|//    public SectionImpl(AnalysedTextImpl at, SpanTypeEnum type,int start,int end) {
comment|//        this(at,type,null,start,end);
comment|//    }
specifier|public
name|SectionImpl
parameter_list|(
name|AnalysedTextImpl
name|at
parameter_list|,
name|SpanTypeEnum
name|type
parameter_list|,
name|Span
name|relativeTo
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|super
argument_list|(
name|at
argument_list|,
name|type
argument_list|,
name|relativeTo
argument_list|,
name|start
argument_list|,
name|end
argument_list|)
expr_stmt|;
assert|assert
name|type
operator|!=
name|SpanTypeEnum
operator|.
name|Token
operator|:
literal|"The SpanType 'Token' is NOT a Section - can not cover other spans!"
assert|;
block|}
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|Iterator
argument_list|<
name|Span
argument_list|>
name|getEnclosed
parameter_list|(
specifier|final
name|Set
argument_list|<
name|SpanTypeEnum
argument_list|>
name|types
parameter_list|)
block|{
return|return
name|IteratorUtils
operator|.
name|filteredIterator
argument_list|(
name|getIterator
argument_list|()
argument_list|,
operator|new
name|Predicate
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|evaluate
parameter_list|(
name|Object
name|span
parameter_list|)
block|{
return|return
name|types
operator|.
name|contains
argument_list|(
operator|(
operator|(
name|Span
operator|)
name|span
operator|)
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|Iterator
argument_list|<
name|Span
argument_list|>
name|getEnclosed
parameter_list|(
specifier|final
name|Set
argument_list|<
name|SpanTypeEnum
argument_list|>
name|types
parameter_list|,
name|int
name|startOffset
parameter_list|,
name|int
name|endOffset
parameter_list|)
block|{
if|if
condition|(
name|startOffset
operator|>=
operator|(
name|span
index|[
literal|1
index|]
operator|-
name|span
index|[
literal|0
index|]
operator|)
condition|)
block|{
comment|//start is outside the span
return|return
name|Collections
operator|.
expr|<
name|Span
operator|>
name|emptySet
argument_list|()
operator|.
name|iterator
argument_list|()
return|;
block|}
name|int
name|startIdx
init|=
name|startOffset
operator|<
literal|0
condition|?
name|span
index|[
literal|0
index|]
else|:
operator|(
name|span
index|[
literal|0
index|]
operator|+
name|startOffset
operator|)
decl_stmt|;
name|int
name|endIdx
init|=
name|span
index|[
literal|0
index|]
operator|+
name|endOffset
decl_stmt|;
if|if
condition|(
name|endIdx
operator|<=
name|startIdx
condition|)
block|{
return|return
name|Collections
operator|.
expr|<
name|Span
operator|>
name|emptySet
argument_list|()
operator|.
name|iterator
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|endIdx
operator|>
name|span
index|[
literal|1
index|]
condition|)
block|{
name|endIdx
operator|=
name|span
index|[
literal|1
index|]
expr_stmt|;
block|}
return|return
name|IteratorUtils
operator|.
name|filteredIterator
argument_list|(
name|getIterator
argument_list|(
operator|new
name|SubSetHelperSpan
argument_list|(
name|startIdx
argument_list|,
name|endIdx
argument_list|)
argument_list|)
argument_list|,
operator|new
name|Predicate
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|evaluate
parameter_list|(
name|Object
name|span
parameter_list|)
block|{
return|return
name|types
operator|.
name|contains
argument_list|(
operator|(
operator|(
name|Span
operator|)
name|span
operator|)
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
return|;
block|}
comment|/**      * Iterator that does not throw {@link ConcurrentModificationException} but      * considers modifications to the underlying set by using the      * {@link NavigableMap#higherKey(Object)} method for iterating over the      * Elements!<p>      * This allows to add new {@link Span}s to the {@link Section} while      * iterating (e.g. add {@link Token}s and/or {@link Chunk}s while iterating      * over the {@link Sentence}s of an {@link AnalysedText})      * @return the iterator      */
specifier|protected
name|Iterator
argument_list|<
name|Span
argument_list|>
name|getIterator
parameter_list|()
block|{
return|return
name|getIterator
argument_list|(
literal|null
argument_list|)
return|;
block|}
comment|/**      * Iterator that does not throw {@link ConcurrentModificationException} but      * considers modifications to the underlying set by using the      * {@link NavigableMap#higherKey(Object)} method for iterating over the      * Elements!<p>      * This allows to add new {@link Span}s to the {@link Section} while      * iterating (e.g. add {@link Token}s and/or {@link Chunk}s while iterating      * over the {@link Sentence}s of an {@link AnalysedText})      * @param section the (sub-)section of the current section to iterate or      *<code>null</code> to iterate the whole section.      * @return the iterator      */
specifier|protected
name|Iterator
argument_list|<
name|Span
argument_list|>
name|getIterator
parameter_list|(
specifier|final
name|SubSetHelperSpan
name|section
parameter_list|)
block|{
comment|//create a virtual Span with the end of the section to iterate over
specifier|final
name|Span
name|end
init|=
operator|new
name|SubSetHelperSpan
argument_list|(
name|section
operator|==
literal|null
condition|?
name|getEnd
argument_list|()
else|:
comment|//if no section is defined use the parent
name|section
operator|.
name|getEnd
argument_list|()
argument_list|)
decl_stmt|;
comment|//use the end of the desired section
return|return
operator|new
name|Iterator
argument_list|<
name|Span
argument_list|>
argument_list|()
block|{
name|boolean
name|init
init|=
literal|false
decl_stmt|;
name|boolean
name|removed
init|=
literal|true
decl_stmt|;
comment|//init with the first span of the iterator
specifier|private
name|Span
name|span
init|=
name|section
operator|==
literal|null
condition|?
name|SectionImpl
operator|.
name|this
else|:
name|section
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|getNext
argument_list|()
operator|!=
literal|null
return|;
block|}
specifier|private
name|Span
name|getNext
parameter_list|()
block|{
name|Span
name|next
init|=
name|context
operator|.
name|spans
operator|.
name|higherKey
argument_list|(
name|span
argument_list|)
decl_stmt|;
return|return
name|next
operator|==
literal|null
operator|||
name|next
operator|.
name|compareTo
argument_list|(
name|end
argument_list|)
operator|>=
literal|0
condition|?
literal|null
else|:
name|next
return|;
block|}
annotation|@
name|Override
specifier|public
name|Span
name|next
parameter_list|()
block|{
name|init
operator|=
literal|true
expr_stmt|;
name|span
operator|=
name|getNext
argument_list|()
expr_stmt|;
name|removed
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|span
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NoSuchElementException
argument_list|()
throw|;
block|}
return|return
name|span
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
if|if
condition|(
operator|!
name|init
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"remove can not be called before the first call to next"
argument_list|)
throw|;
block|}
if|if
condition|(
name|removed
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"the current Span was already removed!"
argument_list|)
throw|;
block|}
name|context
operator|.
name|spans
operator|.
name|remove
argument_list|(
name|span
argument_list|)
expr_stmt|;
name|removed
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|;
block|}
comment|/**      * Adds a Token<b>relative</b> to the current Span. Negative values for start and      * end are allowed (e.g. to add a Token that starts some characters before      * this one.<p>      * Users that want to use<b>absolute</b> indexes need to use      *<code><pre>      *     Span span; //any type of Span (Token, Chunk, Sentence ...)      *     span.getContext().addToken(absoluteStart, absoluteEnd)      *</pre></code>      * @param start the start relative to this Span      * @param end the end relative to this span      * @return the created and added token      */
specifier|public
name|Token
name|addToken
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
block|{
return|return
name|register
argument_list|(
operator|new
name|TokenImpl
argument_list|(
name|context
argument_list|,
name|this
argument_list|,
name|start
argument_list|,
name|end
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Registers the parsed - newly created token - with the {@link #getContext()}.      * If the parsed {@link Span} already exists (an other Span instance with the      * same values for {@link Span#getType()}, {@link Span#getStart()} and       * {@link Span#getEnd()}) than the already present instance is returned      * instead of the parsed one. In case the parsed Token does not already      * exist the parsed instance is registered with the context and      * returned.<p>      * Typical usage:<pre><code>      *     public add{something}(int start, int end){      *         return register(new {somthing}Impl(context, this,start,end));      *     }      *</code></pre>      * {something} ... the Span type (Token, Chunk, Sentence ...)<p>      * @param span the Span instance to register      * @return the parsed or an already existing instance      */
specifier|protected
parameter_list|<
name|T
extends|extends
name|Span
parameter_list|>
name|T
name|register
parameter_list|(
name|T
name|span
parameter_list|)
block|{
comment|//check if this token already exists
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|T
name|current
init|=
operator|(
name|T
operator|)
name|context
operator|.
name|spans
operator|.
name|get
argument_list|(
name|span
argument_list|)
decl_stmt|;
comment|//NOTE: type safety is ensured by the SpanTypeEnum in combination with the
comment|//      Compareable implementation of SpanImpl.
if|if
condition|(
name|current
operator|==
literal|null
condition|)
block|{
comment|//add the new one
name|context
operator|.
name|spans
operator|.
name|put
argument_list|(
name|span
argument_list|,
name|span
argument_list|)
expr_stmt|;
return|return
name|span
return|;
block|}
else|else
block|{
comment|//else return the already contained token
return|return
name|current
return|;
block|}
block|}
specifier|public
name|Iterator
argument_list|<
name|Token
argument_list|>
name|getTokens
parameter_list|()
block|{
return|return
name|filter
argument_list|(
name|Token
operator|.
name|class
argument_list|)
return|;
block|}
comment|/**      * Internal helper to generate correctly generic typed {@link Iterator}s for      * filtered {@link Span} types      * @param interfaze the Span interface e.g. {@link Token}      * @param clazz the actual Span implementation e.g. {@link TokenImpl}      * @return the {@link Iterator} of type {interface} iterating over       * {implementation} instances (e.g.       *<code>{@link Iterator}&lt;{@link Token}&gt;</code> returning       *<code>{@link TokenImpl}</code> instances on calls to {@link Iterator#next()}      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|protected
parameter_list|<
name|T
extends|extends
name|Span
parameter_list|>
name|Iterator
argument_list|<
name|T
argument_list|>
name|filter
parameter_list|(
specifier|final
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
return|return
name|IteratorUtils
operator|.
name|filteredIterator
argument_list|(
name|getIterator
argument_list|()
argument_list|,
operator|new
name|InstanceofPredicate
argument_list|(
name|clazz
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Internal helper class used for building {@link SortedSet#subSet(Object, Object)}.      *       * @author Rupert Westenthaler      *      */
class|class
name|SubSetHelperSpan
extends|extends
name|SpanImpl
implements|implements
name|Span
block|{
comment|/**          * Create the start constraint for {@link SortedSet#subSet(Object, Object)}          * @param start          * @param end          */
specifier|protected
name|SubSetHelperSpan
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|super
argument_list|(
name|SpanTypeEnum
operator|.
name|Text
argument_list|,
comment|//lowest pos type
name|start
argument_list|,
name|end
argument_list|)
expr_stmt|;
name|setContext
argument_list|(
name|SectionImpl
operator|.
name|this
operator|.
name|context
argument_list|)
expr_stmt|;
block|}
comment|/**          * Creates the end constraint for {@link SortedSet#subSet(Object, Object)}          * @param pos          */
specifier|protected
name|SubSetHelperSpan
parameter_list|(
name|int
name|pos
parameter_list|)
block|{
name|super
argument_list|(
name|SpanTypeEnum
operator|.
name|Token
argument_list|,
comment|//highest pos type,
name|pos
argument_list|,
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
expr_stmt|;
name|setContext
argument_list|(
name|SectionImpl
operator|.
name|this
operator|.
name|context
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

