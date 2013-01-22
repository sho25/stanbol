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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

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
name|EnumSet
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|SortedMap
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
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|impl
operator|.
name|SectionImpl
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
name|impl
operator|.
name|SpanImpl
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
name|servicesapi
operator|.
name|Blob
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
name|servicesapi
operator|.
name|ContentItem
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
name|servicesapi
operator|.
name|EngineException
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
name|servicesapi
operator|.
name|EnhancementEngine
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
name|servicesapi
operator|.
name|NoSuchPartException
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

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|helpers
operator|.
name|SubstituteLoggerFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|ibm
operator|.
name|icu
operator|.
name|lang
operator|.
name|UCharacter
operator|.
name|SentenceBreak
import|;
end_import

begin_class
specifier|public
class|class
name|AnalysedTextUtils
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|AnalysedTextUtils
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Getter for the {@link AnalysedText} content part of the parsed      * ContentItem.<p>      * This assumes that the AnalysedText is registered by using      * {@link AnalysedText#ANALYSED_TEXT_URI}. Otherwise it will not find it.      * @param ci The {@link ContentItem}      * @return the {@link AnalysedText} or<code>null</code> if not present.      * @throws ClassCastException if a content part is registered with      * {@link AnalysedText#ANALYSED_TEXT_URI} but its type is not compatible      * to {@link AnalysedText}.      */
specifier|public
specifier|static
name|AnalysedText
name|getAnalysedText
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|ci
operator|.
name|getPart
argument_list|(
name|AnalysedText
operator|.
name|ANALYSED_TEXT_URI
argument_list|,
name|AnalysedText
operator|.
name|class
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchPartException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
finally|finally
block|{
name|ci
operator|.
name|getLock
argument_list|()
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Copies the elements of the parsed iterator to a list.      * @param iterator the iterator      * @return the List with all spans of the Iterators      */
specifier|public
specifier|static
parameter_list|<
name|T
extends|extends
name|Span
parameter_list|>
name|List
argument_list|<
name|T
argument_list|>
name|asList
parameter_list|(
name|Iterator
argument_list|<
name|T
argument_list|>
name|it
parameter_list|)
block|{
if|if
condition|(
name|it
operator|==
literal|null
operator|||
operator|!
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
else|else
block|{
name|List
argument_list|<
name|T
argument_list|>
name|spans
init|=
operator|new
name|ArrayList
argument_list|<
name|T
argument_list|>
argument_list|()
decl_stmt|;
name|appandToList
argument_list|(
name|it
argument_list|,
name|spans
argument_list|)
expr_stmt|;
return|return
name|spans
return|;
block|}
block|}
comment|/**      * Appends the elements provided by the parsed Iterator to the list.      * @param it the Iterator      * @param list the List      * @throws NullPointerException if the parsed List is<code>null</code>      */
specifier|public
specifier|static
parameter_list|<
name|T
extends|extends
name|Span
parameter_list|>
name|void
name|appandToList
parameter_list|(
name|Iterator
argument_list|<
name|T
argument_list|>
name|it
parameter_list|,
name|List
argument_list|<
name|?
super|super
name|T
argument_list|>
name|list
parameter_list|)
block|{
if|if
condition|(
name|it
operator|!=
literal|null
condition|)
block|{
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|list
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Copies the elements of the parsed iterator(s) to a {@link SortedSet}. As      * {@link Span} implements {@link Comparable} the Spans within the resulting      * set will have the same order as returned by the methods of {@link AnalysedText}      * @param it the iterator(s)      * @return the {@link SortedSet} containing all Spans of the iterators      */
specifier|public
specifier|static
parameter_list|<
name|T
extends|extends
name|Span
parameter_list|>
name|SortedSet
argument_list|<
name|T
argument_list|>
name|asSet
parameter_list|(
name|Iterator
argument_list|<
name|T
argument_list|>
name|it
parameter_list|)
block|{
name|SortedSet
argument_list|<
name|T
argument_list|>
name|spans
init|=
operator|new
name|TreeSet
argument_list|<
name|T
argument_list|>
argument_list|()
decl_stmt|;
name|addToSet
argument_list|(
name|it
argument_list|,
name|spans
argument_list|)
expr_stmt|;
return|return
name|spans
return|;
block|}
comment|/**      * Adds the Spans of the parsed Iterator to the parsed Set      * @param it the Iterator      * @param set the set      * @throws NullPointerException if the parsed List is<code>null</code>      */
specifier|public
specifier|static
parameter_list|<
name|T
extends|extends
name|Span
parameter_list|>
name|void
name|addToSet
parameter_list|(
name|Iterator
argument_list|<
name|T
argument_list|>
name|it
parameter_list|,
name|Set
argument_list|<
name|?
super|super
name|T
argument_list|>
name|set
parameter_list|)
block|{
if|if
condition|(
name|it
operator|!=
literal|null
condition|)
block|{
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|set
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Iterates over two levels of the Span hierarchy (e.g. all Tokens of a      * Sentence that are within a Chunk). The returned Iterator is a live      * view on the {@link AnalysedText} (being the context of the enclosing      * Span).<p>      * Usage Example      *<code><pre>      *     Sentence sentence; //The currently processed Sentence      *     Iterator&lt;Span&gt; tokens = AnalysedTextUtils.getSpansInSpans(      *         sentence,      *         {@link SpanTypeEnum#Chunk SpanTypeEnum.Chunk}      *         {@link SpanTypeEnum#Token SpanTypeEnum.Token}      *     while(tokens.hasNext()){      *         Token token = (Token)tokens.next();      *         // process only tokens within a chunk      *     }      *</pre></code>      * @param section       * @param level1 the {@link SpanTypeEnum} for the first Level. MUST be      * a Type that is a {@link Section} (e.g. Chunk or Sentence).      * @param level2      * @return      * @throws IllegalArgumentException if {@link SpanTypeEnum#Token} is parsed      * as<code>level1</code> span type.      */
specifier|public
specifier|static
name|Iterator
argument_list|<
name|Span
argument_list|>
name|getSpansInSpans
parameter_list|(
name|Section
name|section
parameter_list|,
name|SpanTypeEnum
name|level1
parameter_list|,
specifier|final
name|SpanTypeEnum
name|level2
parameter_list|)
block|{
if|if
condition|(
name|level1
operator|==
name|SpanTypeEnum
operator|.
name|Token
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The SpanType for level1 MUST refer to a Section "
operator|+
literal|"(Chunk, Sentence, TextSection or Text)"
argument_list|)
throw|;
block|}
specifier|final
name|Iterator
argument_list|<
name|Span
argument_list|>
name|level1It
init|=
name|section
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|of
argument_list|(
name|level1
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|new
name|Iterator
argument_list|<
name|Span
argument_list|>
argument_list|()
block|{
name|Iterator
argument_list|<
name|Span
argument_list|>
name|level2It
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
if|if
condition|(
name|level2It
operator|!=
literal|null
operator|&&
name|level2It
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
while|while
condition|(
name|level1It
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|level2It
operator|=
operator|(
operator|(
name|Section
operator|)
name|level1It
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getEnclosed
argument_list|(
name|EnumSet
operator|.
name|of
argument_list|(
name|level2
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|level2It
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Span
name|next
parameter_list|()
block|{
name|hasNext
argument_list|()
expr_stmt|;
comment|//ensure hasNext is called on multiple calls to next()
return|return
name|level2It
operator|.
name|next
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|level2It
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
comment|// NOTE: No longer used ... keep for now in case that we need this functionality.
comment|//    public static Set<Span> getEnclosed(SortedSet<Span> sortedSet, Span span){
comment|//        if(span.getType() == SpanTypeEnum.Token){
comment|//            log.warn("Span {} with SpanType {} parsed to getEnclosing(..). Returned Set will "
comment|//                    + "contain the parsed span!");
comment|//        }
comment|//        return sortedSet.subSet(new SubSetHelperSpan(span.getStart(), span.getEnd()),
comment|//            new SubSetHelperSpan(span.getEnd()));
comment|//    }
comment|//    public static<T> Map<Span,T> getEnclosed(SortedMap<Span,T> sortedSet, Span span){
comment|//        if(span.getType() == SpanTypeEnum.Token){
comment|//            log.warn("Span {} with SpanType {} parsed to getEnclosing(..). Returned Set will "
comment|//                    + "contain the parsed span!");
comment|//        }
comment|//        return sortedSet.subMap(new SubSetHelperSpan(span.getStart(), span.getEnd()),
comment|//            new SubSetHelperSpan(span.getEnd()));
comment|//    }
comment|//
comment|//    /**
comment|//     * Internal helper class used for building {@link SortedSet#subSet(Object, Object)}.
comment|//     *
comment|//     * @author Rupert Westenthaler
comment|//     *
comment|//     */
comment|//    private static class SubSetHelperSpan extends SpanImpl implements Span {
comment|//        /**
comment|//         * Create the start constraint for {@link SortedSet#subSet(Object, Object)}
comment|//         * @param start
comment|//         * @param end
comment|//         */
comment|//        protected SubSetHelperSpan(int start,int end){
comment|//            super(SpanTypeEnum.Text, //lowest pos type
comment|//                start,end);
comment|//        }
comment|//        /**
comment|//         * Creates the end constraint for {@link SortedSet#subSet(Object, Object)}
comment|//         * @param pos
comment|//         */
comment|//        protected SubSetHelperSpan(int pos){
comment|//            super(SpanTypeEnum.Token, //highest pos type,
comment|//                pos,Integer.MAX_VALUE);
comment|//        }
comment|//    }
block|}
end_class

end_unit

