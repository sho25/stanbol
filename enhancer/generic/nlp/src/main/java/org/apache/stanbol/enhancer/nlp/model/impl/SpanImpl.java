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
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|ref
operator|.
name|SoftReference
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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

begin_comment
comment|/**  * A span selected in the given Text. This uses {@link SoftReference}s for  * holding the {@link #getSpan()} text to allow the Garbage Collector to   * free up memory for large texts. In addition the span text is lazzy initialised  * on the first call to {@link #getSpan()}.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|SpanImpl
extends|extends
name|AnnotatedImpl
implements|implements
name|Span
block|{
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SpanImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|final
name|int
index|[]
name|span
decl_stmt|;
comment|/**      * Lazzy initialised {@link SoftReference} to the text      */
specifier|private
name|SoftReference
argument_list|<
name|String
argument_list|>
name|textReference
init|=
literal|null
decl_stmt|;
specifier|protected
name|AnalysedTextImpl
name|context
decl_stmt|;
specifier|protected
specifier|final
name|SpanTypeEnum
name|type
decl_stmt|;
comment|/**      * Allows to create a SpanImpl without the {@link #getContext()}. The      * context MUST BE set by using {@link #setContext(AnalysedTextImpl)} before      * using this span.      * @param type      * @param start      * @param end      */
specifier|protected
name|SpanImpl
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
assert|assert
name|type
operator|!=
literal|null
operator|:
literal|"The parsed SpanType MUST NOT be NULL!"
assert|;
if|if
condition|(
name|start
operator|<
literal|0
operator|||
name|end
operator|<
name|start
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Illegal span ["
operator|+
name|start
operator|+
literal|','
operator|+
name|end
operator|+
literal|']'
argument_list|)
throw|;
block|}
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|this
operator|.
name|span
operator|=
operator|new
name|int
index|[]
block|{
name|start
block|,
name|end
block|}
expr_stmt|;
block|}
comment|//    protected SpanImpl(AnalysedTextImpl analysedText, SpanTypeEnum type, int start,int end) {
comment|//        this(analysedText,type,null,start,end);
comment|//    }
specifier|protected
name|SpanImpl
parameter_list|(
name|AnalysedTextImpl
name|analysedText
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
name|this
argument_list|(
name|type
argument_list|,
name|relativeTo
operator|==
literal|null
condition|?
name|start
else|:
name|relativeTo
operator|.
name|getStart
argument_list|()
operator|+
name|start
argument_list|,
name|relativeTo
operator|==
literal|null
condition|?
name|end
else|:
name|relativeTo
operator|.
name|getStart
argument_list|()
operator|+
name|end
argument_list|)
expr_stmt|;
name|setContext
argument_list|(
name|analysedText
argument_list|)
expr_stmt|;
comment|//check that Spans that are created relative to an other do not cross
comment|//the borders of that span
if|if
condition|(
name|relativeTo
operator|!=
literal|null
operator|&&
name|relativeTo
operator|.
name|getEnd
argument_list|()
operator|<
name|getEnd
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Illegal span ["
operator|+
name|start
operator|+
literal|','
operator|+
name|end
operator|+
literal|"] for "
operator|+
name|type
operator|+
literal|" relative to "
operator|+
name|relativeTo
operator|+
literal|" : Span of the "
operator|+
literal|" contained Token MUST NOT extend the others!"
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|void
name|setContext
parameter_list|(
name|AnalysedTextImpl
name|analysedText
parameter_list|)
block|{
assert|assert
name|analysedText
operator|!=
literal|null
operator|:
literal|"The parsed AnalysedText MUST NOT be NULL!"
assert|;
name|this
operator|.
name|context
operator|=
name|analysedText
expr_stmt|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.enhancer.nlp.model.impl.Span#getType()      */
annotation|@
name|Override
specifier|public
name|SpanTypeEnum
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.enhancer.nlp.model.impl.Span#getStart()      */
annotation|@
name|Override
specifier|public
name|int
name|getStart
parameter_list|()
block|{
return|return
name|span
index|[
literal|0
index|]
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.enhancer.nlp.model.impl.Span#getEnd()      */
annotation|@
name|Override
specifier|public
name|int
name|getEnd
parameter_list|()
block|{
return|return
name|span
index|[
literal|1
index|]
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.enhancer.nlp.model.impl.Span#getText()      */
annotation|@
name|Override
specifier|public
specifier|final
name|AnalysedTextImpl
name|getContext
parameter_list|()
block|{
return|return
name|context
return|;
block|}
comment|/* (non-Javadoc)      * @see org.apache.stanbol.enhancer.nlp.model.impl.Span#getSpan()      */
annotation|@
name|Override
specifier|public
name|String
name|getSpan
parameter_list|()
block|{
name|String
name|spanText
init|=
name|textReference
operator|==
literal|null
condition|?
literal|null
else|:
name|textReference
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|spanText
operator|==
literal|null
condition|)
block|{
name|spanText
operator|=
name|getContext
argument_list|()
operator|.
name|getText
argument_list|()
operator|.
name|subSequence
argument_list|(
name|span
index|[
literal|0
index|]
argument_list|,
name|span
index|[
literal|1
index|]
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|textReference
operator|=
operator|new
name|SoftReference
argument_list|<
name|String
argument_list|>
argument_list|(
name|spanText
argument_list|)
expr_stmt|;
block|}
return|return
name|spanText
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
comment|//include the SpanTypeEnum in the hash
return|return
name|Arrays
operator|.
name|hashCode
argument_list|(
name|span
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|obj
operator|instanceof
name|SpanImpl
operator|&&
name|getType
argument_list|()
operator|==
operator|(
operator|(
name|Span
operator|)
name|obj
operator|)
operator|.
name|getType
argument_list|()
operator|&&
name|Arrays
operator|.
name|equals
argument_list|(
name|this
operator|.
name|span
argument_list|,
operator|(
operator|(
name|SpanImpl
operator|)
name|obj
operator|)
operator|.
name|span
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s: %s"
argument_list|,
name|type
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|span
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|Span
name|o
parameter_list|)
block|{
if|if
condition|(
name|context
operator|!=
literal|null
operator|&&
name|o
operator|.
name|getContext
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|context
operator|.
name|equals
argument_list|(
name|o
operator|.
name|getContext
argument_list|()
argument_list|)
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Comparing Spans with different Context. This is not an "
operator|+
literal|"intended usage of this class as start|end|type parameters "
operator|+
literal|"do not have a natural oder over different texts."
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"This will sort Spans based on start|end|type parameters "
operator|+
literal|"regardless that the might be over different texts!"
argument_list|)
expr_stmt|;
comment|//TODO consider throwing an IllegalStateExcetion!
block|}
comment|//Compare Integers ASC (used here three times)
comment|//    (x< y) ? -1 : ((x == y) ? 0 : 1);
name|int
name|start
init|=
operator|(
name|span
index|[
literal|0
index|]
operator|<
name|o
operator|.
name|getStart
argument_list|()
operator|)
condition|?
operator|-
literal|1
else|:
operator|(
operator|(
name|span
index|[
literal|0
index|]
operator|==
name|o
operator|.
name|getStart
argument_list|()
operator|)
condition|?
literal|0
else|:
literal|1
operator|)
decl_stmt|;
if|if
condition|(
name|start
operator|==
literal|0
condition|)
block|{
comment|//sort end in DESC order
name|int
name|end
init|=
operator|(
name|span
index|[
literal|1
index|]
operator|<
name|o
operator|.
name|getEnd
argument_list|()
operator|)
condition|?
literal|1
else|:
operator|(
operator|(
name|span
index|[
literal|1
index|]
operator|==
name|o
operator|.
name|getEnd
argument_list|()
operator|)
condition|?
literal|0
else|:
operator|-
literal|1
operator|)
decl_stmt|;
comment|//if start AND end is the same compare based on the span type
comment|//Natural order of span types is defined by the Enum.ordinal()
name|int
name|o1
init|=
name|getType
argument_list|()
operator|.
name|ordinal
argument_list|()
decl_stmt|;
name|int
name|o2
init|=
name|o
operator|.
name|getType
argument_list|()
operator|.
name|ordinal
argument_list|()
decl_stmt|;
return|return
name|end
operator|!=
literal|0
condition|?
name|end
else|:
operator|(
name|o1
operator|<
name|o2
operator|)
condition|?
operator|-
literal|1
else|:
operator|(
operator|(
name|o1
operator|==
name|o2
operator|)
condition|?
literal|0
else|:
literal|1
operator|)
return|;
block|}
else|else
block|{
return|return
name|start
return|;
block|}
block|}
block|}
end_class

end_unit
