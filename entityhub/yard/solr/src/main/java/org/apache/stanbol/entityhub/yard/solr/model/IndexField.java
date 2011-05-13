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
name|yard
operator|.
name|solr
operator|.
name|model
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
name|HashSet
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

begin_comment
comment|/**  * Represents a logical field within the index.  *<p>  * A logical field consists of the following parts:  *<ul>  *<li>The path, a list of path elements (URIs parsed as String)  *<li>The {@link IndexDataType}  *<li>The language  *</ul>  *<p>  * Logical fields are than mapped with an 1..n mapping to actual fields in the Index Documents. This  * functionality is provided by the {@link FieldMapper}  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
class|class
name|IndexField
block|{
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|path
decl_stmt|;
specifier|private
specifier|final
name|IndexDataType
name|indexType
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|languages
decl_stmt|;
specifier|private
specifier|final
name|int
name|hash
decl_stmt|;
comment|/**      * Constructs a new IndexField      *       * @param path      * @param indexType      * @param language      * @throws IllegalArgumentException      */
specifier|public
name|IndexField
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|path
parameter_list|,
name|IndexDataType
name|indexType
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|validatePath
argument_list|(
name|path
argument_list|)
expr_stmt|;
comment|// we need to create a new list, to ensure, that no one can change this member!
name|this
operator|.
name|path
operator|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|indexType
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|indexType
operator|=
name|IndexDataType
operator|.
name|DEFAULT
expr_stmt|;
comment|// the type representing no pre- nor suffix
block|}
else|else
block|{
name|this
operator|.
name|indexType
operator|=
name|indexType
expr_stmt|;
block|}
if|if
condition|(
name|languages
operator|==
literal|null
operator|||
name|languages
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|this
operator|.
name|languages
operator|=
name|Collections
operator|.
name|emptySet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|languageSet
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
name|String
name|language
range|:
name|languages
control|)
block|{
if|if
condition|(
name|language
operator|==
literal|null
operator|||
name|language
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|languageSet
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|// interpret empty as default language
block|}
else|else
block|{
name|languageSet
operator|.
name|add
argument_list|(
name|language
argument_list|)
expr_stmt|;
block|}
block|}
name|this
operator|.
name|languages
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|languageSet
argument_list|)
expr_stmt|;
block|}
comment|// calculate the hash of is immutable class only once
name|hash
operator|=
name|this
operator|.
name|path
operator|.
name|hashCode
argument_list|()
operator|+
name|this
operator|.
name|indexType
operator|.
name|hashCode
argument_list|()
operator|+
name|this
operator|.
name|languages
operator|.
name|hashCode
argument_list|()
expr_stmt|;
block|}
comment|/**      * Checks if the path is not<code>null</code>, empty and does not contain a<code>null</code> or empty      * element.      *       * @param path      *            the path to validate      * @throws IllegalArgumentException      *             if the parsed path in not valid      */
specifier|public
specifier|static
name|void
name|validatePath
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|path
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|path
operator|==
literal|null
operator|||
name|path
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parameter path MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
if|if
condition|(
name|path
operator|.
name|contains
argument_list|(
literal|null
argument_list|)
operator|||
name|path
operator|.
name|contains
argument_list|(
literal|""
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"The parsed path MUST NOT contain a NULL value or an empty element (path=%s)"
argument_list|,
name|path
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * Getter for the Path      *       * @return the path. Unmodifiable list, guaranteed to contain at lest one element. All elements are      *         guaranteed NOT<code>null</code> and NOT empty.      */
specifier|public
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|getPath
parameter_list|()
block|{
return|return
name|path
return|;
block|}
comment|/**      * Getter for the index data type      *       * @return the index data type. Guaranteed to be NOT<code>null</code>      */
specifier|public
specifier|final
name|IndexDataType
name|getDataType
parameter_list|()
block|{
return|return
name|indexType
return|;
block|}
comment|/**      * Checks if this field defines any language      *       * @return<code>true</code> if a language is defined for this field. Note that<code>true</code> is      *         returned if the language is<code>null</code>.      */
specifier|public
specifier|final
name|boolean
name|hasLanguage
parameter_list|()
block|{
return|return
operator|!
name|languages
operator|.
name|isEmpty
argument_list|()
return|;
block|}
comment|/**      * Getter for the Languages.      *       * @return the languages. Unmodifiable collection, guaranteed to contain at least one element. May contain      *         the<code>null</code> value (used for the default language).      */
specifier|public
specifier|final
name|Collection
argument_list|<
name|String
argument_list|>
name|getLanguages
parameter_list|()
block|{
return|return
name|languages
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
name|IndexField
operator|&&
operator|(
operator|(
name|IndexField
operator|)
name|obj
operator|)
operator|.
name|path
operator|.
name|equals
argument_list|(
name|path
argument_list|)
operator|&&
operator|(
operator|(
name|IndexField
operator|)
name|obj
operator|)
operator|.
name|indexType
operator|.
name|equals
argument_list|(
name|indexType
argument_list|)
operator|&&
operator|(
operator|(
name|IndexField
operator|)
name|obj
operator|)
operator|.
name|languages
operator|.
name|equals
argument_list|(
name|languages
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
name|hash
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
literal|"IndexField[path: %s|type: %s"
argument_list|,
name|path
argument_list|,
name|indexType
argument_list|)
operator|+
operator|(
name|hasLanguage
argument_list|()
condition|?
name|String
operator|.
name|format
argument_list|(
literal|"|languages: %s]"
argument_list|,
name|languages
argument_list|)
else|:
literal|"]"
operator|)
return|;
block|}
block|}
end_class

end_unit

