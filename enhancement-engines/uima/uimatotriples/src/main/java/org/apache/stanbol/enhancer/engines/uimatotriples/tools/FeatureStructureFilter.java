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
name|engines
operator|.
name|uimatotriples
operator|.
name|tools
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|caslight
operator|.
name|Feature
import|;
end_import

begin_comment
comment|/**  * A Holder Class for FeatureStucture filters.  * @author Mihály Héder  */
end_comment

begin_class
specifier|public
class|class
name|FeatureStructureFilter
block|{
name|List
argument_list|<
name|FeatureFilter
argument_list|>
name|filters
init|=
operator|new
name|ArrayList
argument_list|<
name|FeatureFilter
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * Adds a Feature Filter.      * @param tnf       */
specifier|public
name|void
name|addFeatureFilter
parameter_list|(
name|FeatureFilter
name|tnf
parameter_list|)
block|{
name|filters
operator|.
name|add
argument_list|(
name|tnf
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks whether Feature Filters list is empty.      * @return       */
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|filters
operator|.
name|isEmpty
argument_list|()
return|;
block|}
comment|/**      * Checks whether a FeatureStructure is allowed.      * @param typeName the type name of the FeatureStructure      * @param features the features of the FeatureStructure to inspect      * @return true if allowed      */
specifier|public
name|boolean
name|checkFeatureStructureAllowed
parameter_list|(
name|String
name|typeName
parameter_list|,
name|Set
argument_list|<
name|Feature
argument_list|>
name|features
parameter_list|)
block|{
comment|//no rule at all
if|if
condition|(
name|filters
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
for|for
control|(
name|FeatureFilter
name|tnf
range|:
name|filters
control|)
block|{
if|if
condition|(
name|tnf
operator|.
name|getTypeName
argument_list|()
operator|.
name|equals
argument_list|(
name|typeName
argument_list|)
condition|)
block|{
for|for
control|(
name|Feature
name|f
range|:
name|features
control|)
block|{
if|if
condition|(
name|tnf
operator|.
name|checkNameValueAllowed
argument_list|(
name|f
operator|.
name|getName
argument_list|()
argument_list|,
name|f
operator|.
name|getValueAsString
argument_list|()
argument_list|)
condition|)
block|{
comment|//there is a supporting rule
return|return
literal|true
return|;
block|}
block|}
block|}
block|}
comment|//this type is not enumerated
return|return
literal|false
return|;
block|}
comment|/**      * Check whether a Feature of a FeatureStructure should be converted to RDF.      * @param typeName the type name of the FeatureStructure      * @param f the Feature to inspect      * @return       */
specifier|public
name|boolean
name|checkFeatureToConvert
parameter_list|(
name|String
name|typeName
parameter_list|,
name|Feature
name|f
parameter_list|)
block|{
comment|//no rule at all
if|if
condition|(
name|filters
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
for|for
control|(
name|FeatureFilter
name|tnf
range|:
name|filters
control|)
block|{
if|if
condition|(
name|tnf
operator|.
name|getTypeName
argument_list|()
operator|.
name|equals
argument_list|(
name|typeName
argument_list|)
condition|)
block|{
if|if
condition|(
name|tnf
operator|.
name|checkNameToPass
argument_list|(
name|f
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
block|}
comment|//this type is not enumerated
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

