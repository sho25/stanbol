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
name|caslight
package|;
end_package

begin_comment
comment|/**  * This class represents a UIMA Feature. This class uses generics, so any kind of objects can be feature values.   *   * @author Mihály Héder<mihaly.heder@sztaki.hu>  */
end_comment

begin_class
specifier|public
class|class
name|Feature
parameter_list|<
name|E
parameter_list|>
block|{
name|E
name|value
decl_stmt|;
name|String
name|name
decl_stmt|;
comment|/**      * Constructs a Feature      * @param name name of the feature      * @param value value of the feature      */
specifier|public
name|Feature
parameter_list|(
name|String
name|name
parameter_list|,
name|E
name|value
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Returns the name of this Feature.      * @return The feature Name      */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**      * Sets the name of this feature.      * @param name The feature name.      */
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
comment|/**      * Returns the feature value as String, by calling its toString method.      * @return       */
specifier|public
name|String
name|getValueAsString
parameter_list|()
block|{
return|return
name|value
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Returns the feature value as Integer, if applicable.       * Performs an Integer.parseInt call on the object stored as value.      * @return       */
specifier|public
name|int
name|getValueAsInteger
parameter_list|()
block|{
return|return
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Sets the value of this Feature.      * @param value       */
specifier|public
name|void
name|setValue
parameter_list|(
name|E
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Returns the value of this feature.      * @return       */
specifier|public
name|E
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**      * A custom toString method which prints the name, class of the value, and value of the Feature.      * @return       */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"Feature name:"
operator|+
name|name
operator|+
literal|" type:"
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" value:"
operator|+
name|value
return|;
block|}
block|}
end_class

end_unit

