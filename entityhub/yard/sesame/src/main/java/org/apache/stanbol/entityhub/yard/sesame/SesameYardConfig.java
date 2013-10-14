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
name|entityhub
operator|.
name|yard
operator|.
name|sesame
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
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
name|entityhub
operator|.
name|core
operator|.
name|yard
operator|.
name|AbstractYard
operator|.
name|YardConfig
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
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

begin_class
specifier|public
class|class
name|SesameYardConfig
extends|extends
name|YardConfig
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SesameYardConfig
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|SesameYardConfig
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|super
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SesameYardConfig
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
throws|throws
name|ConfigurationException
throws|,
name|IllegalArgumentException
block|{
name|super
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
comment|/**      * Getter for the {@link SesameYard#CONTEXT_ENABLED} state      * @return the state or the {@link SesameYard#DEFAULT_CONTEXT_ENABLED default}      * if not present in the config.       */
specifier|public
name|boolean
name|isContextEnabled
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|SesameYard
operator|.
name|CONTEXT_ENABLED
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Boolean
condition|)
block|{
return|return
operator|(
operator|(
name|Boolean
operator|)
name|value
operator|)
operator|.
name|booleanValue
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
return|return
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|SesameYard
operator|.
name|DEFAULT_CONTEXT_ENABLED
return|;
block|}
block|}
comment|/**      * Setter for the {@link SesameYard#CONTEXT_ENABLED} state      * @param state the state or<code>null</code> to remove the config (reset to       * the {@link SesameYard#DEFAULT_CONTEXT_ENABLED default})      */
specifier|public
name|void
name|setContextEnabled
parameter_list|(
name|Boolean
name|state
parameter_list|)
block|{
if|if
condition|(
name|state
operator|!=
literal|null
condition|)
block|{
name|config
operator|.
name|put
argument_list|(
name|SesameYard
operator|.
name|CONTEXT_ENABLED
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|remove
argument_list|(
name|SesameYard
operator|.
name|CONTEXT_ENABLED
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Setter for the Contexts      * @param contexts      */
specifier|public
name|void
name|setContexts
parameter_list|(
name|String
index|[]
name|contexts
parameter_list|)
block|{
if|if
condition|(
name|contexts
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SesameYard
operator|.
name|CONTEXT_URI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|SesameYard
operator|.
name|CONTEXT_URI
argument_list|,
name|contexts
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Getter for the {@link SesameYard#CONTEXT_URI} property.      * @return the contexts or an empty array if none      */
specifier|public
name|String
index|[]
name|getContexts
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|SesameYard
operator|.
name|CONTEXT_URI
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|values
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|String
name|str
init|=
operator|(
operator|(
name|String
operator|)
name|value
operator|)
operator|.
name|trim
argument_list|()
decl_stmt|;
return|return
operator|new
name|String
index|[]
block|{
name|str
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|str
block|}
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|String
index|[]
block|{}
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|String
index|[]
condition|)
block|{
name|values
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
operator|(
operator|(
name|String
index|[]
operator|)
name|value
operator|)
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|str
range|:
operator|(
name|String
index|[]
operator|)
name|value
control|)
block|{
name|str
operator|=
name|str
operator|!=
literal|null
condition|?
name|str
operator|.
name|trim
argument_list|()
else|:
name|str
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
name|str
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|str
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|Iterable
argument_list|<
name|?
argument_list|>
condition|)
block|{
name|values
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
operator|(
operator|(
name|String
index|[]
operator|)
name|value
operator|)
operator|.
name|length
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|o
operator|:
operator|(
name|String
index|[]
operator|)
name|value
control|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
name|values
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|str
init|=
name|o
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|values
operator|.
name|add
argument_list|(
name|str
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|str
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Illegal '{}' value '{}' (type: '{}')! Supported: String, String[] and Iterables"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|SesameYard
operator|.
name|CONTEXT_URI
block|,
name|value
block|,
name|value
operator|.
name|getClass
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"   ... return empty context array as fallback!"
argument_list|)
expr_stmt|;
return|return
operator|new
name|String
index|[]
block|{}
return|;
block|}
return|return
name|values
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|values
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
end_class

begin_comment
comment|/**      * Setter for the {@link SesameYard#INCLUDE_INFERRED} state      * @param state the state or<code>null</code> to remove the config (reset to       * the {@link SesameYard#DEFAULT_INCLUDE_INFERRED default})      */
end_comment

begin_function
specifier|public
name|void
name|setIncludeInferred
parameter_list|(
name|Boolean
name|state
parameter_list|)
block|{
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
name|config
operator|.
name|remove
argument_list|(
name|SesameYard
operator|.
name|INCLUDE_INFERRED
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|config
operator|.
name|put
argument_list|(
name|SesameYard
operator|.
name|INCLUDE_INFERRED
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
block|}
end_function

begin_comment
comment|/**      * Getter for the {@link SesameYard#INCLUDE_INFERRED} state.      * @return the state or {@link SesameYard#DEFAULT_INCLUDE_INFERRED} if not      * present in the configuration.      */
end_comment

begin_function
specifier|public
name|boolean
name|isIncludeInferred
parameter_list|()
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|SesameYard
operator|.
name|INCLUDE_INFERRED
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|Boolean
condition|)
block|{
return|return
operator|(
operator|(
name|Boolean
operator|)
name|value
operator|)
operator|.
name|booleanValue
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
return|return
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|SesameYard
operator|.
name|DEFAULT_INCLUDE_INFERRED
return|;
block|}
block|}
end_function

begin_function
annotation|@
name|Override
specifier|protected
name|void
name|validateConfig
parameter_list|()
throws|throws
name|ConfigurationException
block|{
name|Object
name|value
init|=
name|config
operator|.
name|get
argument_list|(
name|SesameYard
operator|.
name|CONTEXT_URI
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|value
operator|==
literal|null
operator|||
name|value
operator|instanceof
name|String
operator|||
name|value
operator|instanceof
name|String
index|[]
operator|||
name|value
operator|instanceof
name|Iterable
argument_list|<
name|?
argument_list|>
operator|)
condition|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|SesameYard
operator|.
name|CONTEXT_URI
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"Illegal '%s' value '%s' (type: '%s')! Supported: String, String[] and Iterables"
argument_list|,
name|SesameYard
operator|.
name|CONTEXT_URI
argument_list|,
name|value
argument_list|,
name|value
operator|.
name|getClass
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
block|}
end_function

unit|}
end_unit

