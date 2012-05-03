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
name|Resource
import|;
end_import

begin_comment
comment|/**  * {@link Constraint} is used while doing a search using {@link FeaturedSearch} of Contenthub. It provides  * specifying additional restrictions other than the original query keyword for the search operation. A  * constraint requires a document property to have a certain value. A document property corresponds to a  * {@link Facet} and constraints are parts of facets. A constraint represents one of the possible values of a  * facet.  *   *<p>  * For example, assume a document has a property<b>places</b> indicating the related places with the  * document. This property corresponds to a facet and assume that this facet has two values<b>Paris</b> and  *<b>London</b>. Each such value of a facet is represented with a separate constraint.  *</p>  */
end_comment

begin_interface
specifier|public
interface|interface
name|Constraint
block|{
comment|/**      * @return the {@link Facet} this constraint relates to.      */
name|Facet
name|getFacet
parameter_list|()
function_decl|;
comment|/**      * This method returns the value of this {@link Constraint}. Documents matching this constraint have the      * value to be returned as value of one of their properties. That certain document property corresponds to      * the {@link Facet} which is associated with this constraint.      *       * @return the value of this constraint as a {@link Resource}.      */
name|String
name|getValue
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

