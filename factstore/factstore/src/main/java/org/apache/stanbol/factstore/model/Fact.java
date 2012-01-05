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
name|factstore
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
name|HashMap
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
name|Map
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
name|jsonld
operator|.
name|JsonLd
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
name|jsonld
operator|.
name|JsonLdCommon
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
name|jsonld
operator|.
name|JsonLdProperty
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
name|jsonld
operator|.
name|JsonLdPropertyValue
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
name|jsonld
operator|.
name|JsonLdResource
import|;
end_import

begin_class
specifier|public
class|class
name|Fact
block|{
specifier|private
name|String
name|factSchemaURN
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|roleMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|FactContext
name|context
decl_stmt|;
specifier|public
name|String
name|getFactSchemaURN
parameter_list|()
block|{
return|return
name|factSchemaURN
return|;
block|}
specifier|public
name|void
name|setFactSchemaURN
parameter_list|(
name|String
name|factSchemaURN
parameter_list|)
block|{
name|this
operator|.
name|factSchemaURN
operator|=
name|factSchemaURN
expr_stmt|;
block|}
specifier|public
name|void
name|addRole
parameter_list|(
name|String
name|role
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|roleMap
operator|.
name|put
argument_list|(
name|role
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getRoles
parameter_list|()
block|{
return|return
name|this
operator|.
name|roleMap
operator|.
name|keySet
argument_list|()
return|;
block|}
specifier|public
name|String
name|getValueOfRole
parameter_list|(
name|String
name|role
parameter_list|)
block|{
return|return
name|this
operator|.
name|roleMap
operator|.
name|get
argument_list|(
name|role
argument_list|)
return|;
block|}
specifier|public
name|FactContext
name|getContext
parameter_list|()
block|{
return|return
name|context
return|;
block|}
specifier|public
name|void
name|setContext
parameter_list|(
name|FactContext
name|context
parameter_list|)
block|{
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
block|}
specifier|public
specifier|static
name|Fact
name|factFromJsonLd
parameter_list|(
name|JsonLd
name|jsonLd
parameter_list|)
block|{
name|Fact
name|fact
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|jsonLd
operator|.
name|getResourceSubjects
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|String
name|subject
init|=
name|jsonLd
operator|.
name|getResourceSubjects
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|fact
operator|=
name|resourceToFact
argument_list|(
name|jsonLd
argument_list|,
name|jsonLd
operator|.
name|getResource
argument_list|(
name|subject
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|fact
return|;
block|}
specifier|public
specifier|static
name|Set
argument_list|<
name|Fact
argument_list|>
name|factsFromJsonLd
parameter_list|(
name|JsonLd
name|jsonLd
parameter_list|)
block|{
name|Set
argument_list|<
name|Fact
argument_list|>
name|facts
init|=
operator|new
name|HashSet
argument_list|<
name|Fact
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|jsonLd
operator|.
name|getResourceSubjects
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
for|for
control|(
name|String
name|subject
range|:
name|jsonLd
operator|.
name|getResourceSubjects
argument_list|()
control|)
block|{
name|Fact
name|fact
init|=
name|resourceToFact
argument_list|(
name|jsonLd
argument_list|,
name|jsonLd
operator|.
name|getResource
argument_list|(
name|subject
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|fact
operator|!=
literal|null
condition|)
block|{
name|facts
operator|.
name|add
argument_list|(
name|fact
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|facts
return|;
block|}
specifier|private
specifier|static
name|Fact
name|resourceToFact
parameter_list|(
name|JsonLd
name|jsonLd
parameter_list|,
name|JsonLdResource
name|resource
parameter_list|)
block|{
name|Fact
name|fact
init|=
literal|null
decl_stmt|;
name|String
name|schemaURN
init|=
name|jsonLd
operator|.
name|unCURIE
argument_list|(
name|resource
operator|.
name|getProfile
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|schemaURN
operator|!=
literal|null
operator|&&
operator|!
name|schemaURN
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|JsonLdProperty
argument_list|>
name|propMap
init|=
name|resource
operator|.
name|getPropertyMap
argument_list|()
decl_stmt|;
if|if
condition|(
name|propMap
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|fact
operator|=
operator|new
name|Fact
argument_list|()
expr_stmt|;
name|fact
operator|.
name|setFactSchemaURN
argument_list|(
name|schemaURN
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|role
range|:
name|propMap
operator|.
name|keySet
argument_list|()
control|)
block|{
name|JsonLdProperty
name|jldProperty
init|=
name|propMap
operator|.
name|get
argument_list|(
name|role
argument_list|)
decl_stmt|;
if|if
condition|(
name|jldProperty
operator|.
name|isSingleValued
argument_list|()
condition|)
block|{
name|JsonLdPropertyValue
name|jldValue
init|=
name|jldProperty
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|jldValue
operator|.
name|getType
argument_list|()
operator|!=
literal|null
operator|&&
name|jldValue
operator|.
name|getType
argument_list|()
operator|.
name|equals
argument_list|(
name|JsonLdCommon
operator|.
name|IRI
argument_list|)
condition|)
block|{
name|fact
operator|.
name|addRole
argument_list|(
name|role
argument_list|,
name|jsonLd
operator|.
name|unCURIE
argument_list|(
name|jldValue
operator|.
name|getLiteralValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fact
operator|.
name|addRole
argument_list|(
name|role
argument_list|,
name|jldValue
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// TODO Implement multi-valued properties when converting JSON-LD to fact
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Sorry, this is not implemented yet!"
argument_list|)
throw|;
block|}
block|}
block|}
block|}
return|return
name|fact
return|;
block|}
specifier|public
name|JsonLd
name|factToJsonLd
parameter_list|()
block|{
name|JsonLd
name|jsonLd
init|=
operator|new
name|JsonLd
argument_list|()
decl_stmt|;
name|JsonLdResource
name|subject
init|=
operator|new
name|JsonLdResource
argument_list|()
decl_stmt|;
name|subject
operator|.
name|setProfile
argument_list|(
name|this
operator|.
name|factSchemaURN
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|role
range|:
name|this
operator|.
name|roleMap
operator|.
name|keySet
argument_list|()
control|)
block|{
name|subject
operator|.
name|putProperty
argument_list|(
name|role
argument_list|,
name|this
operator|.
name|roleMap
operator|.
name|get
argument_list|(
name|role
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jsonLd
operator|.
name|put
argument_list|(
name|subject
argument_list|)
expr_stmt|;
return|return
name|jsonLd
return|;
block|}
block|}
end_class

end_unit

