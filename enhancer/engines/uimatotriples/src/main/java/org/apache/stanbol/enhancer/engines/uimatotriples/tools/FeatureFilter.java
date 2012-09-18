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
name|AbstractMap
import|;
end_import

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
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * This class filters caslight Feature by rules.  * @author Mihály Héder  */
end_comment

begin_class
specifier|public
class|class
name|FeatureFilter
block|{
name|String
name|typeName
decl_stmt|;
name|List
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|features
init|=
operator|new
name|ArrayList
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
comment|/**      * Returns the list of Features recognized by this filter.       * @return       */
specifier|public
name|List
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|getFeatures
parameter_list|()
block|{
return|return
name|features
return|;
block|}
comment|/**      * Sets the list of Features recognized by this filter.       * @param features       */
specifier|public
name|void
name|setFeatures
parameter_list|(
name|List
argument_list|<
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
name|features
parameter_list|)
block|{
name|this
operator|.
name|features
operator|=
name|features
expr_stmt|;
block|}
comment|/**      * Returns the FeatureStructure's type name for which this filter was created.      * @return       */
specifier|public
name|String
name|getTypeName
parameter_list|()
block|{
return|return
name|typeName
return|;
block|}
comment|/**      * Sets the FeatureStructure's type name for which this filter was created.      * @param typeName       */
specifier|public
name|void
name|setTypeName
parameter_list|(
name|String
name|typeName
parameter_list|)
block|{
name|this
operator|.
name|typeName
operator|=
name|typeName
expr_stmt|;
block|}
comment|/**      * Adds a Feature filtering rule.      * @param featureName the name of the Feature in question.      * @param featureValue the value of the rule: a regular expression against the value should match.      */
specifier|public
name|void
name|addFeatureFilter
parameter_list|(
name|String
name|featureName
parameter_list|,
name|String
name|featureValue
parameter_list|)
block|{
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
init|=
operator|new
name|AbstractMap
operator|.
name|SimpleEntry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|(
name|featureName
argument_list|,
name|featureValue
argument_list|)
decl_stmt|;
name|features
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks whether a Feature name and Value is allowed by the rules.      * @param name the name of the Feature      * @param value the Value of the Feature      * @return       */
specifier|public
name|boolean
name|checkNameValueAllowed
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
comment|//no rules at all for features -> accept
name|logger
operator|.
name|debug
argument_list|(
literal|"checking name:"
operator|+
name|name
operator|+
literal|" value:"
operator|+
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
name|features
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
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|e
range|:
name|features
control|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"Checking against name:"
operator|+
name|e
operator|.
name|getKey
argument_list|()
operator|+
literal|" value:"
operator|+
name|e
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
if|if
condition|(
name|e
operator|.
name|getValue
argument_list|()
operator|==
literal|null
operator|||
name|e
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//feature name enumerated, no value -> accept
return|return
literal|true
return|;
block|}
else|else
block|{
if|if
condition|(
name|value
operator|.
name|matches
argument_list|(
name|e
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
comment|//value matches on regex
return|return
literal|true
return|;
block|}
else|else
block|{
comment|//value does not match
return|return
literal|false
return|;
block|}
block|}
block|}
block|}
comment|//no rule for this feature -> deny
return|return
literal|false
return|;
block|}
comment|/**      * Check wheter this Feature is allowed to pass the filtering.      * @param name      * @return       */
name|boolean
name|checkNameToPass
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|//no rules at all for features -> print
if|if
condition|(
name|features
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
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|e
range|:
name|features
control|)
block|{
comment|//enumerated
if|if
condition|(
name|e
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|//name there
return|return
literal|true
return|;
block|}
block|}
comment|//not enumerated
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

