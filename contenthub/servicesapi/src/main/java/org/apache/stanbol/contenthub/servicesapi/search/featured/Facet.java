begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright 2012 The Apache Software Foundation.  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|featured
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
comment|/**  * A {@link Facet} is an aspect by which the {@link ConstrainedDocumentSet} can be narrowed. Facets correspond  * to properties of documents and they are used in the search operations in {@link FeaturedSearch} of  * Contenthub. Facets are considered as equal if their default labels are the same. Default labels of facets  * are obtained by providing<code>null</code> to {@link #getLabel(Locale)}.  */
end_comment

begin_interface
specifier|public
interface|interface
name|Facet
block|{
comment|/**      * This methods return all possible values regarding this facet wrapped in a {@link Set} of      * {@link Constraint}s. Constraints are used to filter search results based on certain values of facets.      *       * @return a {@link Set} of constraints that reduce the document to a non-empty set      */
name|Set
argument_list|<
name|Constraint
argument_list|>
name|getConstraints
parameter_list|()
function_decl|;
comment|/**      * Returns a label for this {@link Facet} based on the given {@link Locale} preference.      *       * @param locale      *            the desired {@link Locale} or<code>null</code> if no preference.      * @return a label for this facet      */
name|String
name|getLabel
parameter_list|(
name|Locale
name|locale
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

