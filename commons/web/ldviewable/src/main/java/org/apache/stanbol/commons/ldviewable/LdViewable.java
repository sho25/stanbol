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
name|commons
operator|.
name|ldviewable
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
name|utils
operator|.
name|GraphNode
import|;
end_import

begin_comment
comment|/**  * An LdViewable is a GraphNode associated with a template path. The template   * path will be attempted to be resolved based on the accepted target formats  * to create a representation of the GraphNode.   *  */
end_comment

begin_class
specifier|public
class|class
name|LdViewable
block|{
comment|/** 	 *  	 * @param templatePath the templatePath 	 * @param graphNode the graphNode with the actual content 	 */
specifier|public
name|LdViewable
parameter_list|(
specifier|final
name|String
name|templatePath
parameter_list|,
specifier|final
name|GraphNode
name|graphNode
parameter_list|)
block|{
name|this
operator|.
name|templatePath
operator|=
name|templatePath
expr_stmt|;
name|this
operator|.
name|graphNode
operator|=
name|graphNode
expr_stmt|;
block|}
comment|/** 	 * With this version of the constructor the templatePath is prefixed with 	 * the slash-separated package name of the given Class. 	 *  	 */
specifier|public
name|LdViewable
parameter_list|(
specifier|final
name|String
name|templatePath
parameter_list|,
specifier|final
name|GraphNode
name|graphNode
parameter_list|,
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
parameter_list|)
block|{
specifier|final
name|String
name|slahSeparatedPacakgeName
init|=
name|clazz
operator|.
name|getPackage
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
literal|'/'
argument_list|)
decl_stmt|;
if|if
condition|(
name|templatePath
operator|.
name|startsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|this
operator|.
name|templatePath
operator|=
name|slahSeparatedPacakgeName
operator|+
name|templatePath
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|templatePath
operator|=
name|slahSeparatedPacakgeName
operator|+
literal|'/'
operator|+
name|templatePath
expr_stmt|;
block|}
name|this
operator|.
name|graphNode
operator|=
name|graphNode
expr_stmt|;
block|}
specifier|private
name|String
name|templatePath
decl_stmt|;
specifier|private
name|GraphNode
name|graphNode
decl_stmt|;
specifier|public
name|String
name|getTemplatePath
parameter_list|()
block|{
return|return
name|templatePath
return|;
block|}
specifier|public
name|GraphNode
name|getGraphNode
parameter_list|()
block|{
return|return
name|graphNode
return|;
block|}
block|}
end_class

end_unit
