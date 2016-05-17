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
name|entitycomention
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
name|Iterator
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
name|Graph
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
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|Literal
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
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|entitycomention
operator|.
name|CoMentionConstants
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
name|engines
operator|.
name|entitycomention
operator|.
name|EntityCoMentionEngine
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
name|engines
operator|.
name|entitylinking
operator|.
name|Entity
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|EntityLinkerConfig
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
name|engines
operator|.
name|entitylinking
operator|.
name|impl
operator|.
name|EntityLinker
import|;
end_import

begin_comment
comment|/**  * {@link Entity} implementation used by the {@link EntityCoMentionEngine}. It  * overrides the {@link #getText(IRI)} and {@link #getReferences(IRI)}  * methods to use the a different labelField if   * {@link CoMentionConstants#CO_MENTION_LABEL_FIELD} is parsed as parameter.  * This allows the {@link EntityLinker} to use different properties for different  * Entities when linking against the {@link InMemoryEntityIndex}.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|EntityMention
extends|extends
name|Entity
block|{
comment|/**      * The label field of this Entity      */
specifier|private
specifier|final
name|IRI
name|nameField
decl_stmt|;
comment|/**      * The type field of this Entity      */
specifier|private
specifier|final
name|IRI
name|typeField
decl_stmt|;
comment|/**      * The start/end char indexes char index of the first mention      */
specifier|private
specifier|final
name|Integer
index|[]
name|span
decl_stmt|;
specifier|private
specifier|static
name|int
name|CO_MENTION_FIELD_HASH
init|=
name|CoMentionConstants
operator|.
name|CO_MENTION_LABEL_FIELD
operator|.
name|hashCode
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|int
name|CO_MENTION_TYPE_HASH
init|=
name|CoMentionConstants
operator|.
name|CO_MENTION_TYPE_FIELD
operator|.
name|hashCode
argument_list|()
decl_stmt|;
comment|/**      * Creates a new MentionEntity for the parsed parameters      * @param uri the {@link IRI} of the Entity       * @param data the {@link Graph} with the data for the Entity      * @param labelField the {@link IRI} of the property holding the      * labels of this Entity. This property will be used for all calls to      * {@link #getText(IRI)} and {@link #getReferences(IRI)} if      * {@link CoMentionConstants#CO_MENTION_LABEL_FIELD} is parsed as parameter      * @param span the start/end char indexes of the mention      */
specifier|public
name|EntityMention
parameter_list|(
name|IRI
name|uri
parameter_list|,
name|Graph
name|data
parameter_list|,
name|IRI
name|labelField
parameter_list|,
name|IRI
name|typeField
parameter_list|,
name|Integer
index|[]
name|span
parameter_list|)
block|{
name|super
argument_list|(
name|uri
argument_list|,
name|data
argument_list|)
expr_stmt|;
if|if
condition|(
name|labelField
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The LabelField MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|nameField
operator|=
name|labelField
expr_stmt|;
if|if
condition|(
name|typeField
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The TypeFeild MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|typeField
operator|=
name|typeField
expr_stmt|;
if|if
condition|(
name|span
operator|!=
literal|null
operator|&&
operator|(
name|span
operator|.
name|length
operator|!=
literal|2
operator|||
name|span
index|[
literal|0
index|]
operator|==
literal|null
operator|||
name|span
index|[
literal|1
index|]
operator|==
literal|null
operator|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"If a span is parsed the length of the Array MUST BE 2 "
operator|+
literal|"AND start, end MUST NOT be NULL (parsed: "
operator|+
name|span
operator|+
literal|")!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|span
operator|=
name|span
expr_stmt|;
block|}
comment|/**      * Wrapps the parsed Entity and redirects calls to       * {@link CoMentionConstants#CO_MENTION_LABEL_FIELD} to the parsed labelField      * @param entity the Entity to wrap      * @param labelField the {@link IRI} of the property holding the      * labels of this Entity. This property will be used for all calls to      * {@link #getText(IRI)} and {@link #getReferences(IRI)} if      * {@link CoMentionConstants#CO_MENTION_LABEL_FIELD} is parsed as parameter      * @param index the char index of the initial mention in the document      */
specifier|public
name|EntityMention
parameter_list|(
name|Entity
name|entity
parameter_list|,
name|IRI
name|labelField
parameter_list|,
name|IRI
name|typeField
parameter_list|,
name|Integer
index|[]
name|span
parameter_list|)
block|{
name|this
argument_list|(
name|entity
operator|.
name|getUri
argument_list|()
argument_list|,
name|entity
operator|.
name|getData
argument_list|()
argument_list|,
name|labelField
argument_list|,
name|typeField
argument_list|,
name|span
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Iterator
argument_list|<
name|Literal
argument_list|>
name|getText
parameter_list|(
name|IRI
name|field
parameter_list|)
block|{
if|if
condition|(
name|CO_MENTION_FIELD_HASH
operator|==
name|field
operator|.
name|hashCode
argument_list|()
operator|&&
comment|//avoid calling equals
name|CoMentionConstants
operator|.
name|CO_MENTION_LABEL_FIELD
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
name|super
operator|.
name|getText
argument_list|(
name|nameField
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|CO_MENTION_TYPE_HASH
operator|==
name|field
operator|.
name|hashCode
argument_list|()
operator|&&
comment|//avoid calling equals
name|CoMentionConstants
operator|.
name|CO_MENTION_TYPE_FIELD
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
name|super
operator|.
name|getText
argument_list|(
name|typeField
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|getText
argument_list|(
name|field
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Iterator
argument_list|<
name|IRI
argument_list|>
name|getReferences
parameter_list|(
name|IRI
name|field
parameter_list|)
block|{
if|if
condition|(
name|CO_MENTION_FIELD_HASH
operator|==
name|field
operator|.
name|hashCode
argument_list|()
operator|&&
comment|//avoid calling equals
name|CoMentionConstants
operator|.
name|CO_MENTION_LABEL_FIELD
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
name|super
operator|.
name|getReferences
argument_list|(
name|nameField
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|CO_MENTION_TYPE_HASH
operator|==
name|field
operator|.
name|hashCode
argument_list|()
operator|&&
comment|//avoid calling equals
name|CoMentionConstants
operator|.
name|CO_MENTION_TYPE_FIELD
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
name|super
operator|.
name|getReferences
argument_list|(
name|typeField
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|getReferences
argument_list|(
name|field
argument_list|)
return|;
block|}
block|}
comment|/**      * Checks if this mention does have a span assigned. EntityMentions without      * a span are considered to be valid from the begin of the document. Examples      * could be manually tagged entities or entities extracted from the metadata      * of an document.      * @return if this entity has a span or not.      */
specifier|public
name|boolean
name|hasSpan
parameter_list|()
block|{
return|return
name|span
operator|!=
literal|null
return|;
block|}
comment|/**      * The start of the span selected by this mention or<code>null</code> if this      * mention does not have a span assigned.      * @return the start char position of the mention or<code>null</code> if none      */
specifier|public
name|Integer
name|getStart
parameter_list|()
block|{
return|return
name|span
operator|!=
literal|null
condition|?
name|span
index|[
literal|0
index|]
else|:
literal|null
return|;
block|}
comment|/**      * The end of the span selected by this mention or<code>null</code> if this      * mention does not have a span assigned.      * @return the end char position of the mention or<code>null</code> if none      */
specifier|public
name|Integer
name|getEnd
parameter_list|()
block|{
return|return
name|span
operator|!=
literal|null
condition|?
name|span
index|[
literal|1
index|]
else|:
literal|null
return|;
block|}
comment|/**      * The field used to obtain the names of the entities. For EntityMentions      * this is set on a per instance base, as the field my differ between      * different {@link EntityMention}s      * @return the field (property) used to obtain the labels of this mention      * @see EntityLinkerConfig#getNameField()      */
specifier|public
name|IRI
name|getNameField
parameter_list|()
block|{
return|return
name|nameField
return|;
block|}
comment|/**      * The field used to obtain the types of entities. For EntityMentions      * this is set on a per instance base, as the field my differ between      * different {@link EntityMention}s      * @return the field (property) used to obtain the type of this mention      * @see EntityLinkerConfig#getTypeField()      */
specifier|public
name|IRI
name|getTypeField
parameter_list|()
block|{
return|return
name|typeField
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|EntityMention
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getId
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" [labels: "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|IteratorUtils
operator|.
name|toList
argument_list|(
name|getText
argument_list|(
name|nameField
argument_list|)
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasSpan
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" | span:["
argument_list|)
operator|.
name|append
argument_list|(
name|getStart
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|','
argument_list|)
operator|.
name|append
argument_list|(
name|getEnd
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

