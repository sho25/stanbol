begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|model
operator|.
name|sesame
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
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
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|Set
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Reference
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Text
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|ValueFactory
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
name|entityhub
operator|.
name|test
operator|.
name|model
operator|.
name|RepresentationTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|BNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Literal
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|impl
operator|.
name|BNodeImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|impl
operator|.
name|LiteralImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|impl
operator|.
name|URIImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|vocabulary
operator|.
name|DCTERMS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|vocabulary
operator|.
name|RDF
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|vocabulary
operator|.
name|RDFS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|vocabulary
operator|.
name|SKOS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|vocabulary
operator|.
name|XMLSchema
import|;
end_import

begin_class
specifier|public
class|class
name|RdfRepresentationTest
extends|extends
name|RepresentationTest
block|{
specifier|protected
name|RdfValueFactory
name|valueFactory
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|init
parameter_list|()
block|{
name|this
operator|.
name|valueFactory
operator|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|Object
name|getUnsupportedValueInstance
parameter_list|()
block|{
return|return
literal|null
return|;
comment|//indicates that all kinds of Objects are supported!
block|}
annotation|@
name|Override
specifier|protected
name|ValueFactory
name|getValueFactory
parameter_list|()
block|{
return|return
name|valueFactory
return|;
block|}
comment|/*--------------------------------------------------------------------------      * Additional Tests for special Features of the Clerezza based implementation      *       * This includes mainly support for additional types like PlainLiteral,      * TypedLiteral, UriRefs. The conversion to such types as well as getter for      * such types.      *--------------------------------------------------------------------------      */
comment|/**      * {@link PlainLiteral} is used for natural language text in the Clerezza      * RDF API. This tests if adding {@link PlainLiteral}s to the      * {@link Representation#add(String, Object)} method makes them available      * as {@link Text} instances via the {@link Representation} API (e.g.       * {@link Representation#get(String, String...)}).      */
annotation|@
name|Test
specifier|public
name|void
name|testPlainLiteralToTextConversion
parameter_list|()
block|{
name|String
name|field
init|=
literal|"urn:test.RdfRepresentation:test.field"
decl_stmt|;
name|Literal
name|noLangLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
literal|"A plain literal without Language"
argument_list|)
decl_stmt|;
name|Literal
name|enLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
literal|"An english literal"
argument_list|,
literal|"en"
argument_list|)
decl_stmt|;
name|Literal
name|deLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
literal|"Ein Deutsches Literal"
argument_list|,
literal|"de"
argument_list|)
decl_stmt|;
name|Literal
name|deATLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
literal|"Ein Topfen Verband hilft bei Zerrungen"
argument_list|,
literal|"de-AT"
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|Literal
argument_list|>
name|plainLiterals
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|noLangLiteral
argument_list|,
name|enLiteral
argument_list|,
name|deLiteral
argument_list|,
name|deATLiteral
argument_list|)
decl_stmt|;
name|Representation
name|rep
init|=
name|createRepresentation
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|rep
operator|.
name|add
argument_list|(
name|field
argument_list|,
name|plainLiterals
argument_list|)
expr_stmt|;
comment|//now test, that the Plain Literals are available as natural language
comment|//tests via the Representation Interface!
comment|//1) one without a language
name|Iterator
argument_list|<
name|Text
argument_list|>
name|noLangaugeTexts
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
operator|(
name|String
operator|)
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|noLangaugeTexts
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|Text
name|noLanguageText
init|=
name|noLangaugeTexts
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|noLangLiteral
operator|.
name|getLabel
argument_list|()
argument_list|,
name|noLanguageText
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|noLanguageText
operator|.
name|getLanguage
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|noLangaugeTexts
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|//only a single result
comment|//2) one with a language
name|Iterator
argument_list|<
name|Text
argument_list|>
name|enLangaugeTexts
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
literal|"en"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|enLangaugeTexts
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|Text
name|enLangageText
init|=
name|enLangaugeTexts
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|enLiteral
operator|.
name|getLabel
argument_list|()
argument_list|,
name|enLangageText
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|enLiteral
operator|.
name|getLanguage
argument_list|()
argument_list|,
name|enLangageText
operator|.
name|getLanguage
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|enLangaugeTexts
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|//only a single result
comment|//3) test to get all natural language values
name|Set
argument_list|<
name|String
argument_list|>
name|stringValues
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Literal
name|plainLiteral
range|:
name|plainLiterals
control|)
block|{
name|stringValues
operator|.
name|add
argument_list|(
name|plainLiteral
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Iterator
argument_list|<
name|Text
argument_list|>
name|texts
init|=
name|rep
operator|.
name|getText
argument_list|(
name|field
argument_list|)
decl_stmt|;
while|while
condition|(
name|texts
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|assertTrue
argument_list|(
name|stringValues
operator|.
name|remove
argument_list|(
name|texts
operator|.
name|next
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|stringValues
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * {@link TypedLiteral}s are used to represent literal values for different      * xsd dataTypes within Clerezza. This method tests of {@link TypedLiteral}s      * with the data type xsd:string are correctly treated like {@link String}      * values. This tests especially if they are treated as natural language      * texts without language.      */
annotation|@
name|Test
specifier|public
name|void
name|testTypedLiteralToTextConversion
parameter_list|()
block|{
name|String
name|field
init|=
literal|"urn:test.RdfRepresentation:test.field"
decl_stmt|;
name|Literal
name|stringLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
literal|"This is a stirng value"
argument_list|,
name|XMLSchema
operator|.
name|STRING
argument_list|)
decl_stmt|;
comment|//also add an integer to test that other typed literals are not used as texts
name|Literal
name|integerLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
literal|5
argument_list|)
decl_stmt|;
name|Representation
name|rep
init|=
name|createRepresentation
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|rep
operator|.
name|add
argument_list|(
name|field
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|stringLiteral
argument_list|,
name|integerLiteral
argument_list|)
argument_list|)
expr_stmt|;
comment|//test if the literal is returned when asking for natural language text without language
name|Iterator
argument_list|<
name|Text
argument_list|>
name|noLangTexts
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
operator|(
name|String
operator|)
literal|null
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|noLangTexts
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|stringLiteral
operator|.
name|getLabel
argument_list|()
argument_list|,
name|noLangTexts
operator|.
name|next
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|noLangTexts
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|//test that string literals are returned when asking for all natural language text values
name|Iterator
argument_list|<
name|Text
argument_list|>
name|texts
init|=
name|rep
operator|.
name|getText
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|texts
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|stringLiteral
operator|.
name|getLabel
argument_list|()
argument_list|,
name|texts
operator|.
name|next
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|texts
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * {@link TypedLiteral}s are used to represent literal values for different      * xsd dataTypes within Clerezza. This method tests if xsd dataTypes are      * converted to the corresponding java types.       * This is dependent on the {@link LiteralFactory} implementation used by      * the {@link RdfRepresentation} implementation.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Test
specifier|public
name|void
name|testTypedLiteralToValueConversion
parameter_list|()
block|{
name|String
name|field
init|=
literal|"urn:test.RdfRepresentation:test.field"
decl_stmt|;
name|Integer
name|integerValue
init|=
literal|5
decl_stmt|;
name|Literal
name|integerLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
name|integerValue
argument_list|)
decl_stmt|;
name|Date
name|dateValue
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|Literal
name|dateLiteeral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
name|dateValue
argument_list|)
decl_stmt|;
name|Double
name|doubleValue
init|=
name|Math
operator|.
name|PI
decl_stmt|;
name|Literal
name|doubleLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
name|doubleValue
argument_list|)
decl_stmt|;
name|String
name|stringValue
init|=
literal|"This is a string literal value"
decl_stmt|;
name|Literal
name|stringLiteral
init|=
name|valueFactory
operator|.
name|getSesameFactory
argument_list|()
operator|.
name|createLiteral
argument_list|(
name|stringValue
argument_list|,
name|XMLSchema
operator|.
name|STRING
argument_list|)
decl_stmt|;
name|Representation
name|rep
init|=
name|createRepresentation
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|Literal
argument_list|>
name|typedLiterals
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|integerLiteral
argument_list|,
name|doubleLiteral
argument_list|,
name|stringLiteral
argument_list|,
name|dateLiteeral
argument_list|)
decl_stmt|;
name|rep
operator|.
name|add
argument_list|(
name|field
argument_list|,
name|typedLiterals
argument_list|)
expr_stmt|;
comment|//now check that such values are available via Sesame Literal
name|Iterator
argument_list|<
name|Literal
argument_list|>
name|typedLiteralValues
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
name|Literal
operator|.
name|class
argument_list|)
decl_stmt|;
name|int
name|size
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|typedLiteralValues
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Literal
name|next
init|=
name|typedLiteralValues
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|typedLiterals
operator|.
name|contains
argument_list|(
name|next
argument_list|)
argument_list|)
expr_stmt|;
name|size
operator|++
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|typedLiterals
operator|.
name|size
argument_list|()
operator|==
name|size
argument_list|)
expr_stmt|;
comment|//now check that the values are available via the java object types
comment|//1) integer
name|Iterator
argument_list|<
name|Integer
argument_list|>
name|intValues
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
name|Integer
operator|.
name|class
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|intValues
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|integerValue
argument_list|,
name|intValues
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|intValues
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|//2) double
name|Iterator
argument_list|<
name|Double
argument_list|>
name|doubleValues
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
name|Double
operator|.
name|class
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|doubleValues
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|doubleValue
argument_list|,
name|doubleValues
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|doubleValues
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|//3) string
name|Iterator
argument_list|<
name|String
argument_list|>
name|stringValues
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|stringValues
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|value
init|=
name|stringValues
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|stringValue
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|stringValues
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|//4) date
name|Iterator
argument_list|<
name|Date
argument_list|>
name|dateValues
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
name|Date
operator|.
name|class
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|dateValues
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|dateValue
argument_list|,
name|dateValues
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|dateValues
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test for STANBOL-1301      */
annotation|@
name|Test
specifier|public
name|void
name|testBNodeFiltering
parameter_list|()
block|{
name|URI
name|concept
init|=
operator|new
name|URIImpl
argument_list|(
literal|"http://example.org/mySkos#Concept123"
argument_list|)
decl_stmt|;
name|Representation
name|r
init|=
name|createRepresentation
argument_list|(
name|concept
operator|.
name|stringValue
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|r
operator|instanceof
name|RdfRepresentation
argument_list|)
expr_stmt|;
name|RdfRepresentation
name|rep
init|=
operator|(
name|RdfRepresentation
operator|)
name|r
decl_stmt|;
comment|//add the example as listed in STANBOL-1301 to directly to the
comment|//Sesame Model backing the created Representation
name|Model
name|m
init|=
name|rep
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|m
operator|.
name|add
argument_list|(
name|concept
argument_list|,
name|RDF
operator|.
name|TYPE
argument_list|,
name|SKOS
operator|.
name|CONCEPT
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|concept
argument_list|,
name|DCTERMS
operator|.
name|IDENTIFIER
argument_list|,
operator|new
name|LiteralImpl
argument_list|(
literal|"123"
argument_list|)
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|concept
argument_list|,
name|SKOS
operator|.
name|PREF_LABEL
argument_list|,
operator|new
name|LiteralImpl
argument_list|(
literal|"Concept123"
argument_list|,
literal|"en"
argument_list|)
argument_list|)
expr_stmt|;
name|BNode
name|note1
init|=
operator|new
name|BNodeImpl
argument_list|(
literal|"5d8580be71044a88bcfe9852d1e9cfb6node17c4j452vx19576"
argument_list|)
decl_stmt|;
name|m
operator|.
name|add
argument_list|(
name|concept
argument_list|,
name|SKOS
operator|.
name|SCOPE_NOTE
argument_list|,
name|note1
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|note1
argument_list|,
name|DCTERMS
operator|.
name|CREATOR
argument_list|,
operator|new
name|LiteralImpl
argument_list|(
literal|"User1"
argument_list|)
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|note1
argument_list|,
name|DCTERMS
operator|.
name|CREATED
argument_list|,
operator|new
name|LiteralImpl
argument_list|(
literal|"2013-03-03T02:02:02Z"
argument_list|,
name|XMLSchema
operator|.
name|DATETIME
argument_list|)
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|note1
argument_list|,
name|RDFS
operator|.
name|COMMENT
argument_list|,
operator|new
name|LiteralImpl
argument_list|(
literal|"The scope of this example global"
argument_list|,
literal|"en"
argument_list|)
argument_list|)
expr_stmt|;
name|BNode
name|note2
init|=
operator|new
name|BNodeImpl
argument_list|(
literal|"5d8580be71044a88bcfe9852d1e9cfb6node17c4j452vx19634"
argument_list|)
decl_stmt|;
name|m
operator|.
name|add
argument_list|(
name|concept
argument_list|,
name|SKOS
operator|.
name|SCOPE_NOTE
argument_list|,
name|note2
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|note2
argument_list|,
name|DCTERMS
operator|.
name|CREATOR
argument_list|,
operator|new
name|LiteralImpl
argument_list|(
literal|"User2"
argument_list|)
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|note2
argument_list|,
name|DCTERMS
operator|.
name|CREATED
argument_list|,
operator|new
name|LiteralImpl
argument_list|(
literal|"2013-03-03T04:04:04Z"
argument_list|,
name|XMLSchema
operator|.
name|DATETIME
argument_list|)
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|note2
argument_list|,
name|RDFS
operator|.
name|COMMENT
argument_list|,
operator|new
name|LiteralImpl
argument_list|(
literal|"Der Geltungsbereich ist Global"
argument_list|,
literal|"de"
argument_list|)
argument_list|)
expr_stmt|;
comment|//now assert that BNodes are not reported via the Representation API
name|Iterator
argument_list|<
name|Object
argument_list|>
name|scopeNotes
init|=
name|rep
operator|.
name|get
argument_list|(
name|SKOS
operator|.
name|SCOPE_NOTE
operator|.
name|stringValue
argument_list|()
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|scopeNotes
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Reference
argument_list|>
name|scopeNoteRefs
init|=
name|rep
operator|.
name|getReferences
argument_list|(
name|SKOS
operator|.
name|SCOPE_NOTE
operator|.
name|stringValue
argument_list|()
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|scopeNoteRefs
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//TODO add tests for adding Integers, Doubles, ... and getting TypedLiterals
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|RdfRepresentationTest
name|test
init|=
operator|new
name|RdfRepresentationTest
argument_list|()
decl_stmt|;
name|test
operator|.
name|init
argument_list|()
expr_stmt|;
name|test
operator|.
name|testTypedLiteralToValueConversion
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

