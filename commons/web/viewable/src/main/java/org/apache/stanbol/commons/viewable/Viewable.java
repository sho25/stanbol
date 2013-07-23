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
name|commons
operator|.
name|viewable
package|;
end_package

begin_comment
comment|/**  * This is a replacement for the jersey Vieable that allows rendering an   * arbitrary object using a Freemarker template specified by path.  *   * Usage of this class promotes a bad programming style where the   * application logic is not clearly separated from the presentation but   * where backend method are called by the presentation layer.  *   * Users should consider migrate to RdfViewable instead where instead of  * an arbitrary Object a GraphNode representing a node in a graph is passed,  * this approach also allows the response to be rendered as RDF.  *   * @deprecated Moved to {@link org.apache.stanbol.commons.web.viewable.Viewable}  */
end_comment

begin_class
annotation|@
name|Deprecated
specifier|public
class|class
name|Viewable
extends|extends
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|viewable
operator|.
name|Viewable
block|{
comment|/**      * This uses the class name of Pojo to prefix the template      *       * @param templatePath the templatePath      * @param graphNode the graphNode with the actual content      */
specifier|public
name|Viewable
parameter_list|(
name|String
name|templatePath
parameter_list|,
name|Object
name|pojo
parameter_list|)
block|{
name|super
argument_list|(
name|templatePath
argument_list|,
name|pojo
argument_list|)
expr_stmt|;
block|}
comment|/**      * With this version of the constructor the templatePath is prefixed with      * the slash-separated class name of clazz.      *       */
specifier|public
name|Viewable
parameter_list|(
specifier|final
name|String
name|templatePath
parameter_list|,
specifier|final
name|Object
name|pojo
parameter_list|,
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
parameter_list|)
block|{
name|super
argument_list|(
name|templatePath
argument_list|,
name|pojo
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

