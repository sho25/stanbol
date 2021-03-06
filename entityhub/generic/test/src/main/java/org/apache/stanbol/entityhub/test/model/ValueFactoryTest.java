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
name|test
operator|.
name|model
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
name|assertNotNull
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
name|assertNotSame
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
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|UnsupportedTypeException
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
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
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

begin_comment
comment|/**  * General tests for instantiation of model instances by using the different value factory methods. This also  * tests the construction of implementation of {@link Reference}, {@link Text} and {@link Representation}. For  * the immutable {@link Text} and {@link Reference} this tests are sufficient. For Representations there is an  * own Test class  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ValueFactoryTest
block|{
comment|/**      * Subclasses must implement this method and provide a {@link ValueFactory} instance that is used to test      * the actual model implementation      *       * @return the {@link ValueFactory} for the Entityhub model implementation to be tested      */
specifier|protected
specifier|abstract
name|ValueFactory
name|getValueFactory
parameter_list|()
function_decl|;
comment|/**      * Returns an instance of a unsupported Type to be parsed to {@link ValueFactory#createReference(Object)}.      * Used to check if this Method correctly throws an {@link UnsupportedTypeException}      *       * @return an instance of an unsupported type or<code>null</code> if all types are supported      */
specifier|protected
specifier|abstract
name|Object
name|getUnsupportedReferenceType
parameter_list|()
function_decl|;
comment|/**      * Returns an instance of a unsupported Type to be parsed to {@link ValueFactory#createText(Object)}. Used      * to check if this Method correctly throws an {@link UnsupportedTypeException}      *       * @return an instance of an unsupported type or<code>null</code> if all types are supported      */
specifier|protected
specifier|abstract
name|Object
name|getUnsupportedTextType
parameter_list|()
function_decl|;
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testNullReference
parameter_list|()
block|{
name|testRef
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|UnsupportedTypeException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testUnsupportedReferenceType
parameter_list|()
block|{
name|Object
name|unsupported
init|=
name|getUnsupportedReferenceType
argument_list|()
decl_stmt|;
if|if
condition|(
name|unsupported
operator|!=
literal|null
condition|)
block|{
name|testRef
argument_list|(
name|unsupported
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// no unsupported types ... this test is not necessary
comment|// -> create a dummy exception
comment|// TODO: is there a way to deactivate a test if not valid
throw|throw
operator|new
name|UnsupportedTypeException
argument_list|(
name|Object
operator|.
name|class
argument_list|,
literal|"dummy exception to successfully complete this unnecessary test"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testEmptyStringReference
parameter_list|()
block|{
name|testRef
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testStringReference
parameter_list|()
block|{
name|Object
name|refObject
init|=
literal|"urn:test.1"
decl_stmt|;
name|Reference
name|ref
init|=
name|testRef
argument_list|(
name|refObject
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|ref
operator|.
name|getReference
argument_list|()
argument_list|,
name|refObject
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIRIerence
parameter_list|()
throws|throws
name|URISyntaxException
block|{
name|URI
name|refObject
init|=
operator|new
name|URI
argument_list|(
literal|"http://www.test.org/uriTest"
argument_list|)
decl_stmt|;
name|Reference
name|ref
init|=
name|testRef
argument_list|(
name|refObject
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|ref
operator|.
name|getReference
argument_list|()
argument_list|,
name|refObject
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testURLReference
parameter_list|()
throws|throws
name|MalformedURLException
block|{
name|URL
name|refObject
init|=
operator|new
name|URL
argument_list|(
literal|"http://www.test.org/urlTest"
argument_list|)
decl_stmt|;
name|Reference
name|ref
init|=
name|testRef
argument_list|(
name|refObject
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|ref
operator|.
name|getReference
argument_list|()
argument_list|,
name|refObject
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testNullText
parameter_list|()
block|{
name|testText
argument_list|(
literal|null
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testNullLanguage
parameter_list|()
block|{
name|testText
argument_list|(
literal|"test"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|UnsupportedTypeException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testUnsupportedTextType
parameter_list|()
block|{
name|Object
name|unsupported
init|=
name|getUnsupportedTextType
argument_list|()
decl_stmt|;
if|if
condition|(
name|unsupported
operator|!=
literal|null
condition|)
block|{
name|getValueFactory
argument_list|()
operator|.
name|createText
argument_list|(
name|unsupported
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// no unsupported types ... this test is not necessary
comment|// -> create a dummy exception
comment|// TODO: is there a way to deactivate a test if not valid
throw|throw
operator|new
name|UnsupportedTypeException
argument_list|(
name|Object
operator|.
name|class
argument_list|,
literal|"dummy exception to successfully complete this unnecessary test"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testNormalText
parameter_list|()
block|{
name|testText
argument_list|(
literal|"test"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Some Systems use an empty string for the default language, other use<code>null</code>. Text does      * currently not define that<code>null</code> need to be used as default language. However it does define      * that<code>null</code> is a valid value for the language!      *<p>      * Based on that Entityhub allows implementations to convert an empty language to<code>null</code> but      * does NOT allow to to convert<code>null</code> to an empty string.      *<p>      * This test currently assures, that parsing an empty string as language results in an empty string OR      *<code>null</code>. It also tests that parsing an empty string as language does not result in an      * Exception.      */
annotation|@
name|Test
specifier|public
name|void
name|testEmptyLanguageText
parameter_list|()
block|{
name|testText
argument_list|(
literal|"test"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
comment|/**      * One can not create a Representation with<code>null</code> as ID. NOTE: automatic generation of IDs is      * supported by the {@link Yard#create()} but not by the {@link Representation} itself.      */
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testNullIdRepresentation
parameter_list|()
block|{
name|testRepresentation
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * One can not create a Representation with an emtpy ID      */
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testEmptyIdRepresentation
parameter_list|()
block|{
name|testRepresentation
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMultipleInstanceForSameID
parameter_list|()
block|{
name|Representation
name|rep
init|=
name|testRepresentation
argument_list|(
literal|"urn:testSameId"
argument_list|)
decl_stmt|;
name|Representation
name|rep1
init|=
name|testRepresentation
argument_list|(
literal|"urn:testSameId"
argument_list|)
decl_stmt|;
comment|// check that multiple calls with the same ID create different instances
comment|// -> this is very important to allow mapping of Representations (e.g.
comment|// when they are stored within a cache
name|assertNotSame
argument_list|(
name|rep
argument_list|,
name|rep1
argument_list|)
expr_stmt|;
comment|// if an ID is parsed, than the two instance should be equal
name|assertTrue
argument_list|(
name|rep
operator|.
name|equals
argument_list|(
name|rep1
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|rep
operator|.
name|hashCode
argument_list|()
operator|==
name|rep1
operator|.
name|hashCode
argument_list|()
argument_list|)
expr_stmt|;
comment|// check the hash code
block|}
specifier|private
name|Representation
name|testRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|ValueFactory
name|vf
init|=
name|getValueFactory
argument_list|()
decl_stmt|;
name|Representation
name|rep
init|=
name|vf
operator|.
name|createRepresentation
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|rep
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|rep
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
block|{
name|assertEquals
argument_list|(
name|rep
operator|.
name|getId
argument_list|()
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
return|return
name|rep
return|;
block|}
comment|/**      * Internally used to create and text {@link Text}s for the different tests      *       * @param textString      *            the natural language text as string      * @param language      *            the language      * @return the created {@link Text} instance that can be used to perform further tests.      */
specifier|private
name|Text
name|testText
parameter_list|(
name|String
name|textString
parameter_list|,
name|String
name|language
parameter_list|)
block|{
name|ValueFactory
name|vf
init|=
name|getValueFactory
argument_list|()
decl_stmt|;
name|Text
name|text
init|=
name|vf
operator|.
name|createText
argument_list|(
name|textString
argument_list|,
name|language
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|text
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|text
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|text
operator|.
name|getText
argument_list|()
argument_list|,
name|textString
argument_list|)
expr_stmt|;
if|if
condition|(
name|language
operator|==
literal|null
condition|)
block|{
name|assertTrue
argument_list|(
name|text
operator|.
name|getLanguage
argument_list|()
operator|==
literal|null
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|language
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// implementations are free to change an empty language string to null
comment|// NOTE that it is not allowed to change NULL to an empty String!
name|assertTrue
argument_list|(
name|text
operator|.
name|getLanguage
argument_list|()
operator|==
literal|null
operator|||
name|text
operator|.
name|getLanguage
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|assertNotNull
argument_list|(
name|text
operator|.
name|getLanguage
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|text
operator|.
name|getLanguage
argument_list|()
argument_list|,
name|language
argument_list|)
expr_stmt|;
block|}
return|return
name|text
return|;
block|}
comment|/**      * Internally used to create and test {@link Reference}s for the different tests      *       * @param refObject      *            the object representing the reference      * @return the created {@link Reference} that can be used to perform further tests.      */
specifier|private
name|Reference
name|testRef
parameter_list|(
name|Object
name|refObject
parameter_list|)
block|{
name|ValueFactory
name|vf
init|=
name|getValueFactory
argument_list|()
decl_stmt|;
name|Reference
name|ref
init|=
name|vf
operator|.
name|createReference
argument_list|(
name|refObject
argument_list|)
decl_stmt|;
comment|// check not null
name|assertNotNull
argument_list|(
name|ref
argument_list|)
expr_stmt|;
comment|// check reference is not null
name|assertNotNull
argument_list|(
name|ref
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ref
return|;
block|}
block|}
end_class

end_unit

