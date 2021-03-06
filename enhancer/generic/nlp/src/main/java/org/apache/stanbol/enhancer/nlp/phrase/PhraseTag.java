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
name|phrase
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
name|tag
operator|.
name|Tag
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
name|tag
operator|.
name|TagSet
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
name|pos
operator|.
name|LexicalCategory
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

begin_class
specifier|public
class|class
name|PhraseTag
extends|extends
name|Tag
argument_list|<
name|PhraseTag
argument_list|>
block|{
specifier|private
specifier|final
name|LexicalCategory
name|category
decl_stmt|;
comment|/**      * Creates a new Phrase tag for the parsed tag. The created Tag is not      * assigned to any {@link LexicalCategory}.<p> This constructor can be used      * by {@link EnhancementEngine}s that encounter an Tag they do not know       * (e.g. that is not defined by the configured {@link TagSet}).<p>      * @param tag the Tag      * @throws IllegalArgumentException if the parsed tag is<code>null</code>      * or empty.      */
specifier|public
name|PhraseTag
parameter_list|(
name|String
name|tag
parameter_list|)
block|{
name|this
argument_list|(
name|tag
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a PhraseTag that is assigned to a {@link LexicalCategory}      * @param tag the tag      * @param category the lexical category or<code>null</code> if not known      * @throws IllegalArgumentException if the parsed tag is<code>null</code>      * or empty.      */
specifier|public
name|PhraseTag
parameter_list|(
name|String
name|tag
parameter_list|,
name|LexicalCategory
name|category
parameter_list|)
block|{
name|super
argument_list|(
name|tag
argument_list|)
expr_stmt|;
name|this
operator|.
name|category
operator|=
name|category
expr_stmt|;
block|}
comment|/**      * The LecxialCategory of this tag (if known)      * @return the category or<code>null</code> if not mapped to any      */
specifier|public
name|LexicalCategory
name|getCategory
parameter_list|()
block|{
return|return
name|category
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
literal|"Phrase %s (%s)"
argument_list|,
name|tag
argument_list|,
name|category
operator|==
literal|null
condition|?
literal|"none"
else|:
name|category
operator|.
name|name
argument_list|()
argument_list|)
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
name|tag
operator|.
name|hashCode
argument_list|()
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
name|super
operator|.
name|equals
argument_list|(
name|obj
argument_list|)
operator|&&
name|obj
operator|instanceof
name|PhraseTag
operator|&&
operator|(
name|category
operator|==
literal|null
operator|&&
operator|(
operator|(
name|PhraseTag
operator|)
name|obj
operator|)
operator|.
name|category
operator|==
literal|null
operator|)
operator|||
operator|(
name|category
operator|!=
literal|null
operator|&&
name|category
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|PhraseTag
operator|)
name|obj
operator|)
operator|.
name|category
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

