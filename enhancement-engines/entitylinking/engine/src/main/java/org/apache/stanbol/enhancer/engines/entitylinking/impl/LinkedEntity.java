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
name|engines
operator|.
name|entitylinking
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
name|ArrayList
import|;
end_import

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
name|Collections
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
name|Set
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
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
name|Token
import|;
end_import

begin_comment
comment|/**  * The occurrence of an detected Entity within the content.<p>  * Note that this class already stores the information in a structure as needed  * to write Enhancements as defined by the upcoming 2nd version of the  * Apache Stanbol Enhancement Structure (EntityAnnotation, TextOccurrence and  * EntitySuggestion). However it can also be used to write  * TextAnnotations and EntityAnnotations as defined by the 1st version  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|LinkedEntity
block|{
comment|/**      * An mention of an linked entity within the text      * @author Rupert Westenthaler      *      */
specifier|public
specifier|final
class|class
name|Occurrence
block|{
comment|/**          * The maximum number of chars until that the current sentence is used          * as context for TextOcccurrences. If the sentence is longer a area of          * {@link #CONTEXT_TOKEN_COUNT} before and after the current selected          * text is used as context.<p>          * This is especially important in case no sentence detector is available          * for the current content. Because in this case the whole text is          * parsed as a single Sentence.          * TODO: Maybe find a more clever way to determine the context          */
specifier|public
specifier|static
specifier|final
name|int
name|MAX_CONTEXT_LENGTH
init|=
literal|200
decl_stmt|;
comment|/**          * The number of tokens surrounding the current selected text used to          * calculate the context if the current sentence is longer than          * {@link #MAX_CONTEXT_LENGTH} chars.<p>          * This is especially important in case no sentence detector is available          * for the current content. Because in this case the whole text is          * parsed as a single Sentence.          * TODO: Maybe find a more clever way to determine the context          */
specifier|public
specifier|static
specifier|final
name|int
name|CONTEXT_TOKEN_COUNT
init|=
literal|5
decl_stmt|;
specifier|private
specifier|final
name|int
name|start
decl_stmt|;
specifier|private
specifier|final
name|int
name|end
decl_stmt|;
specifier|private
specifier|final
name|String
name|context
decl_stmt|;
specifier|private
name|Occurrence
parameter_list|(
name|Section
name|sentence
parameter_list|,
name|Token
name|token
parameter_list|)
block|{
name|this
argument_list|(
name|sentence
argument_list|,
name|token
argument_list|,
name|token
argument_list|)
expr_stmt|;
block|}
specifier|private
name|Occurrence
parameter_list|(
name|Section
name|sentence
parameter_list|,
name|Token
name|start
parameter_list|,
name|Token
name|end
parameter_list|)
block|{
name|this
operator|.
name|start
operator|=
name|start
operator|.
name|getStart
argument_list|()
expr_stmt|;
name|this
operator|.
name|end
operator|=
name|end
operator|.
name|getEnd
argument_list|()
expr_stmt|;
name|String
name|context
init|=
name|sentence
operator|.
name|getSpan
argument_list|()
decl_stmt|;
if|if
condition|(
name|context
operator|.
name|length
argument_list|()
operator|>
name|MAX_CONTEXT_LENGTH
condition|)
block|{
name|context
operator|=
name|start
operator|.
name|getContext
argument_list|()
operator|.
name|getSpan
argument_list|()
operator|.
name|substring
argument_list|(
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|this
operator|.
name|start
operator|-
name|CONTEXT_TOKEN_COUNT
argument_list|)
argument_list|,
name|Math
operator|.
name|min
argument_list|(
name|this
operator|.
name|end
operator|+
name|CONTEXT_TOKEN_COUNT
argument_list|,
name|start
operator|.
name|getContext
argument_list|()
operator|.
name|getEnd
argument_list|()
argument_list|)
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
block|}
comment|/**          * The context (surrounding text) of the occurrence.          * @return          */
specifier|public
name|String
name|getContext
parameter_list|()
block|{
return|return
name|context
return|;
block|}
comment|/**          * The start index of the occurrence          * @return the start index relative to the start of the text           */
specifier|public
name|int
name|getStart
parameter_list|()
block|{
return|return
name|start
return|;
block|}
comment|/**          * the end index of the occurrence          * @return the end index relative to the start of the text          */
specifier|public
name|int
name|getEnd
parameter_list|()
block|{
return|return
name|end
return|;
block|}
comment|/**          * The selected text of this occurrence. Actually returns the value          * of {@link LinkedEntity#getSelectedText()}, because th          * @return          */
specifier|public
name|String
name|getSelectedText
parameter_list|()
block|{
return|return
name|LinkedEntity
operator|.
name|this
operator|.
name|getSelectedText
argument_list|()
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
name|start
operator|+
literal|","
operator|+
name|end
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|context
operator|.
name|hashCode
argument_list|()
operator|+
name|start
operator|+
name|end
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|arg0
parameter_list|)
block|{
return|return
name|arg0
operator|instanceof
name|Occurrence
operator|&&
operator|(
operator|(
name|Occurrence
operator|)
name|arg0
operator|)
operator|.
name|start
operator|==
name|start
operator|&&
operator|(
operator|(
name|Occurrence
operator|)
name|arg0
operator|)
operator|.
name|end
operator|==
name|end
operator|&&
operator|(
operator|(
name|Occurrence
operator|)
name|arg0
operator|)
operator|.
name|context
operator|.
name|equals
argument_list|(
name|context
argument_list|)
return|;
block|}
block|}
specifier|private
specifier|final
name|String
name|selectedText
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|IRI
argument_list|>
name|types
decl_stmt|;
specifier|private
specifier|final
name|List
argument_list|<
name|Suggestion
argument_list|>
name|suggestions
decl_stmt|;
specifier|private
specifier|final
name|Collection
argument_list|<
name|Occurrence
argument_list|>
name|occurrences
init|=
operator|new
name|ArrayList
argument_list|<
name|Occurrence
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Collection
argument_list|<
name|Occurrence
argument_list|>
name|unmodOccurrences
init|=
name|Collections
operator|.
name|unmodifiableCollection
argument_list|(
name|occurrences
argument_list|)
decl_stmt|;
comment|/**      * Creates a new LinkedEntity for the parsed parameters      * @param selectedText the selected text      * @param suggestions the entity suggestions      * @param types the types of the linked entity.       */
specifier|protected
name|LinkedEntity
parameter_list|(
name|String
name|selectedText
parameter_list|,
name|List
argument_list|<
name|Suggestion
argument_list|>
name|suggestions
parameter_list|,
name|Set
argument_list|<
name|IRI
argument_list|>
name|types
parameter_list|)
block|{
name|this
operator|.
name|suggestions
operator|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|suggestions
argument_list|)
expr_stmt|;
name|this
operator|.
name|selectedText
operator|=
name|selectedText
expr_stmt|;
name|this
operator|.
name|types
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|types
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new Linked Entity including the first {@link Occurrence}      * @param section the sentence (context) for the occurrence.      * @param startToken the index of the start token      * @param tokenSpan the number of token included in this span      * @param suggestions the entity suggestions      * @param types the types of the linked entity.       */
specifier|protected
name|LinkedEntity
parameter_list|(
name|Section
name|section
parameter_list|,
name|Token
name|startToken
parameter_list|,
name|Token
name|endToken
parameter_list|,
name|List
argument_list|<
name|Suggestion
argument_list|>
name|suggestions
parameter_list|,
name|Set
argument_list|<
name|IRI
argument_list|>
name|types
parameter_list|)
block|{
name|this
argument_list|(
name|startToken
operator|.
name|getSpan
argument_list|()
operator|.
name|substring
argument_list|(
name|startToken
operator|.
name|getStart
argument_list|()
argument_list|,
name|endToken
operator|.
name|getEnd
argument_list|()
argument_list|)
argument_list|,
name|suggestions
argument_list|,
name|types
argument_list|)
expr_stmt|;
name|addOccurrence
argument_list|(
name|section
argument_list|,
name|startToken
argument_list|,
name|endToken
argument_list|)
expr_stmt|;
block|}
comment|/**      * Getter for the selected text      * @return the selected text      */
specifier|public
name|String
name|getSelectedText
parameter_list|()
block|{
return|return
name|selectedText
return|;
block|}
comment|/**      * Getter for read only list of types      * @return the types      */
specifier|public
name|Set
argument_list|<
name|IRI
argument_list|>
name|getTypes
parameter_list|()
block|{
return|return
name|types
return|;
block|}
comment|/**      * Adds an new Occurrence      * @param sentence the analysed sentence      * @param startToken the start token      * @param tokenSpan the number of tokens included in this span      * @return the new Occurrence also added to {@link #getOccurrences()}      */
specifier|protected
name|Occurrence
name|addOccurrence
parameter_list|(
name|Section
name|section
parameter_list|,
name|Token
name|startToken
parameter_list|,
name|Token
name|tokenSpan
parameter_list|)
block|{
name|Occurrence
name|o
init|=
operator|new
name|Occurrence
argument_list|(
name|section
argument_list|,
name|startToken
argument_list|,
name|tokenSpan
argument_list|)
decl_stmt|;
name|occurrences
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
return|return
name|o
return|;
block|}
comment|/**      * Getter for the read only list of Occurrences      * @return the occurrences      */
specifier|public
name|Collection
argument_list|<
name|Occurrence
argument_list|>
name|getOccurrences
parameter_list|()
block|{
return|return
name|unmodOccurrences
return|;
block|}
comment|/**      * Getter for the read only list of Suggestions      * @return the suggestions      */
specifier|public
name|List
argument_list|<
name|Suggestion
argument_list|>
name|getSuggestions
parameter_list|()
block|{
return|return
name|suggestions
return|;
block|}
comment|/**      * Getter for the Score      * @return The score of the first element in {@link #getSuggestions()} or       *<code>0</code> if there are no suggestions      */
specifier|public
name|double
name|getScore
parameter_list|()
block|{
return|return
name|suggestions
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0f
else|:
name|suggestions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getScore
argument_list|()
return|;
block|}
comment|/**      * Only considers the {@link #getSelectedText()}, because it is assumed that      * for the same selected text there MUST BE always the same suggestions with      * the same types and occurrences.      */
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|selectedText
operator|.
name|hashCode
argument_list|()
return|;
block|}
comment|/**      * Only considers the {@link #getSelectedText()}, because it is assumed that      * for the same selected text there MUST BE always the same suggestions with      * the same types and occurrences.      */
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|arg0
parameter_list|)
block|{
return|return
name|arg0
operator|instanceof
name|LinkedEntity
operator|&&
operator|(
operator|(
name|LinkedEntity
operator|)
name|arg0
operator|)
operator|.
name|selectedText
operator|.
name|equals
argument_list|(
name|selectedText
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
name|selectedText
operator|+
literal|'@'
operator|+
name|occurrences
operator|+
literal|"->"
operator|+
name|suggestions
return|;
block|}
block|}
end_class

end_unit

