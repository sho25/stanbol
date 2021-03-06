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
name|morpho
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
name|Token
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
name|servicesapi
operator|.
name|EnhancementEngine
import|;
end_import

begin_comment
comment|/**  * An Number tag typically assigned by a Morphological Analyzer (an  * NLP component) to a {@link Token}<p>  * @author Alessio Bosca  */
end_comment

begin_class
specifier|public
class|class
name|NumberTag
extends|extends
name|Tag
argument_list|<
name|NumberTag
argument_list|>
block|{
specifier|private
specifier|final
name|NumberFeature
name|numberCategory
decl_stmt|;
comment|/**      * Creates a new Number tag for the parsed tag. The created Tag is not      * assigned to any {@link NumberFeature}.<p> This constructor can be used      * by {@link EnhancementEngine}s that encounter an Tag they do not know       * (e.g. that is not defined by the configured {@link TagSet}).<p>      * @param tag the Tag      * @throws IllegalArgumentException if the parsed tag is<code>null</code>      * or empty.      */
specifier|public
name|NumberTag
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
comment|/**      * Creates a NumberFeature tag that is assigned to a {@link NumberFeature}      * @param tag the tag      * @param numberCategory the lexical Number  or<code>null</code> if not known      * @throws IllegalArgumentException if the parsed tag is<code>null</code>      * or empty.      */
specifier|public
name|NumberTag
parameter_list|(
name|String
name|tag
parameter_list|,
name|NumberFeature
name|numberCategory
parameter_list|)
block|{
name|super
argument_list|(
name|tag
argument_list|)
expr_stmt|;
name|this
operator|.
name|numberCategory
operator|=
name|numberCategory
expr_stmt|;
block|}
comment|/**      * Get the Number of this tag (if known)      * @return the NumberFeature or<code>null</code> if not mapped to any      */
specifier|public
name|NumberFeature
name|getNumber
parameter_list|()
block|{
return|return
name|this
operator|.
name|numberCategory
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
literal|"NUMBER %s (%s)"
argument_list|,
name|tag
argument_list|,
name|numberCategory
operator|==
literal|null
condition|?
literal|"none"
else|:
name|numberCategory
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
name|NumberTag
operator|&&
operator|(
name|numberCategory
operator|==
literal|null
operator|&&
operator|(
operator|(
name|NumberTag
operator|)
name|obj
operator|)
operator|.
name|numberCategory
operator|==
literal|null
operator|)
operator|||
operator|(
name|numberCategory
operator|!=
literal|null
operator|&&
name|numberCategory
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|NumberTag
operator|)
name|obj
operator|)
operator|.
name|numberCategory
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

