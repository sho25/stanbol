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
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_enum
specifier|public
enum|enum
name|NumberFeature
block|{
comment|/**      * MULTEXT-East feature Number="count" (Nouns in Serbian, Macedonian, Bulgarian),       * e.g., Bulgarian яка/як, язовира/язовир, яда/яд, юргана/юрган, юбилея/юбилей,       * ъгъла/ъгъл (http://purl.org/olia/mte/multext-east.owl#CountNumber)      */
name|CountNumber
block|,
comment|/**      * Plural is a grammatical number, typically referring to more than one of the referent in the real world.      * In English, nouns, pronouns, and demonstratives inflect for plurality. In many other languages, for      * example German and the various Romance languages, articles and adjectives also inflect for plurality.      */
name|Plural
block|,
comment|/**      * Singular is a grammatical number denoting a unit quantity (as opposed to the plural and other forms).      */
name|Singular
block|,
comment|/**      * A collective number is a number referring to 'a set of things'. Languages that have this feature can      * use it to get a phrase like 'flock of sheeps' by using 'sheep' in collective number.      */
name|Collective
block|,
comment|/**      * Form used in some languages to designate two persons or things.      */
name|Dual
block|,
comment|/**      * Number that specifies 'a few' things.      */
name|Paucal
block|,
comment|/**      * Property related to four elements.      */
name|Quadrial
block|,
comment|/**      * Grammatical number referring to 'three things', as opposed to 'singular' and 'plural'.      */
name|Trial
block|;
specifier|static
specifier|final
name|String
name|OLIA_NAMESPACE
init|=
literal|"http://purl.org/olia/olia.owl#"
decl_stmt|;
name|IRI
name|uri
decl_stmt|;
name|NumberFeature
parameter_list|()
block|{
name|uri
operator|=
operator|new
name|IRI
argument_list|(
name|OLIA_NAMESPACE
operator|+
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|IRI
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

