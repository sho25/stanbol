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
name|reasoners
operator|.
name|web
operator|.
name|input
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningServiceInputManager
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningServiceInputProvider
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
comment|/**  *   * Simple implementation of {@see ReasoningServiceInputManager}.  * This class embeds a list of {@see ReasoningServiceInputProvider}s;   * When the {@see #getInputData(Class)} method is called, it iterates over all   * providers which support the given Class as type of the collection item.  *   * @author enridaga  *  */
end_comment

begin_class
specifier|public
class|class
name|SimpleInputManager
implements|implements
name|ReasoningServiceInputManager
block|{
specifier|private
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SimpleInputManager
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|List
argument_list|<
name|ReasoningServiceInputProvider
argument_list|>
name|providers
decl_stmt|;
comment|/**      * Constructor      */
specifier|public
name|SimpleInputManager
parameter_list|()
block|{
name|this
operator|.
name|providers
operator|=
operator|new
name|ArrayList
argument_list|<
name|ReasoningServiceInputProvider
argument_list|>
argument_list|()
expr_stmt|;
block|}
comment|/**      * Adds a {@see ReasoningServiceInputProvider}      *       * @param provider      */
annotation|@
name|Override
specifier|public
name|void
name|addInputProvider
parameter_list|(
name|ReasoningServiceInputProvider
name|provider
parameter_list|)
block|{
name|providers
operator|.
name|add
argument_list|(
name|provider
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes a {@see ReasoningServiceInputProvider}      *       * @param provider      */
annotation|@
name|Override
specifier|public
name|void
name|removeInputProvider
parameter_list|(
name|ReasoningServiceInputProvider
name|provider
parameter_list|)
block|{
name|providers
operator|.
name|remove
argument_list|(
name|provider
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the Iterator which will embed all available data to be processed.      * If a provider cannot adapt to "type", it is ignored.      *       * @param type      */
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|Iterator
argument_list|<
name|T
argument_list|>
name|getInputData
parameter_list|(
specifier|final
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
specifier|final
name|List
argument_list|<
name|ReasoningServiceInputProvider
argument_list|>
name|fProviders
init|=
name|getProviders
argument_list|()
decl_stmt|;
return|return
operator|new
name|Iterator
argument_list|<
name|T
argument_list|>
argument_list|()
block|{
specifier|private
name|Iterator
argument_list|<
name|T
argument_list|>
name|current
init|=
literal|null
decl_stmt|;
specifier|private
name|Iterator
argument_list|<
name|ReasoningServiceInputProvider
argument_list|>
name|pIterator
init|=
name|fProviders
operator|.
name|iterator
argument_list|()
decl_stmt|;
comment|/**              * Set the next provider as the current one. Returns true if a non-empty iterator have been set in              * the current variable, false if no (more) providers are available.              *               * @return              */
specifier|private
name|boolean
name|nextProvider
parameter_list|()
block|{
if|if
condition|(
name|pIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ReasoningServiceInputProvider
name|provider
init|=
name|pIterator
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|provider
operator|.
name|adaptTo
argument_list|(
name|type
argument_list|)
condition|)
block|{
comment|// If this provider can adapt
try|try
block|{
name|current
operator|=
name|provider
operator|.
name|getInput
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// This is bad, but we can go on to the next :)
name|logger
operator|.
name|error
argument_list|(
literal|"Cannot get input from provider"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|nextProvider
argument_list|()
return|;
block|}
comment|// If is empty, try the next
if|if
condition|(
name|current
operator|.
name|hasNext
argument_list|()
operator|==
literal|false
condition|)
block|{
return|return
name|nextProvider
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
else|else
block|{
comment|// If this provider cannot adapt, try the next
return|return
name|nextProvider
argument_list|()
return|;
block|}
block|}
else|else
block|{
comment|// No providers anymore
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
if|if
condition|(
name|current
operator|==
literal|null
condition|)
block|{
comment|// initialize the iterator
if|if
condition|(
name|nextProvider
argument_list|()
condition|)
block|{
return|return
name|current
operator|.
name|hasNext
argument_list|()
return|;
block|}
else|else
block|{
comment|// No provider available, iterator is empty
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|current
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
comment|// If the current iterator has finished, try the next
if|if
condition|(
name|nextProvider
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|T
name|next
parameter_list|()
block|{
if|if
condition|(
name|current
operator|==
literal|null
condition|)
block|{
comment|// initialize the iterator
if|if
condition|(
name|nextProvider
argument_list|()
condition|)
block|{
return|return
name|current
operator|.
name|next
argument_list|()
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Iterator has no more items"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|current
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
name|current
operator|.
name|next
argument_list|()
return|;
block|}
else|else
block|{
comment|// This has finished, try the next (if any)
if|if
condition|(
name|nextProvider
argument_list|()
condition|)
block|{
return|return
name|current
operator|.
name|next
argument_list|()
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Iterator has no more items"
argument_list|)
throw|;
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|()
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
block|}
return|;
block|}
comment|/**      * Get the unmodifiable list of {@see ReasoningServiceInputProvider}      *       * @return List<ReasoningServiceInputProvider>      */
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ReasoningServiceInputProvider
argument_list|>
name|getProviders
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|providers
argument_list|)
return|;
block|}
block|}
end_class

end_unit
