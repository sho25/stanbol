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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_comment
comment|/**  * Enumeration representing the different genders of words based on the<a  * href="http://purl.org/olia/olia.owl">OLIA</a> Ontology  *   */
end_comment

begin_enum
specifier|public
enum|enum
name|Gender
block|{
comment|/**      * One of the two grammatical genders, or classes of nouns, the other being inanimate. Membership in the      * animate grammatical class is largely based on meanings, in that living things, including humans,      * animals, spirits, trees, and most plants are included in the animate class of nouns      */
name|Animate
argument_list|(
literal|"AnimateGender"
argument_list|)
block|,
comment|/**      * Common is an optional attribute for nouns in EAGLES. The Common gender contrasts with Neuter in a      * two-gender system e.g. Danish, Dutch. This value is also used for articles, pronouns and determiners      * especially for Danish.      */
name|Common
argument_list|(
literal|"CommonGender"
argument_list|)
block|,
comment|/**      * Feminine gender is a grammatical gender that marks nouns, articles, pronouns, etc. that have human or      * animal female referents, and often marks nouns that have referents that do not carry distinctions of      * sex.      */
name|Feminine
block|,
comment|/**      * One of the two grammatical genders, or noun classes, of Nishnaabemwin, the other being animate.      * Membership in the inanimate grammatical class is largely based on meaning, in that non-living things,      * such as objects of manufacture and natural 'non-living' things are included in it      */
name|Inanimate
argument_list|(
literal|"InanimateGender"
argument_list|)
block|,
comment|/**      * Masculine gender is a grammatical gender that marks nouns, articles, pronouns, etc. having human or      * animal male referents, and often marks nouns having referents that do not have distinctions of sex.      */
name|Masculine
block|,
comment|/**      * Neuter gender is a grammatical gender that includes those nouns, articles, pronouns, etc. having      * referents which do not have distinctions of sex, and often includes some which do have a natural sex      * distinction.      */
name|Neuter
block|;
specifier|static
specifier|final
name|String
name|OLIA_NAMESPACE
init|=
literal|"http://purl.org/olia/olia.owl#"
decl_stmt|;
name|UriRef
name|uri
decl_stmt|;
name|Gender
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|Gender
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|uri
operator|=
operator|new
name|UriRef
argument_list|(
name|OLIA_NAMESPACE
operator|+
operator|(
name|name
operator|==
literal|null
condition|?
name|name
argument_list|()
else|:
name|name
operator|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|UriRef
name|getUri
parameter_list|()
block|{
return|return
name|uri
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
name|uri
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
block|}
end_enum

end_unit

