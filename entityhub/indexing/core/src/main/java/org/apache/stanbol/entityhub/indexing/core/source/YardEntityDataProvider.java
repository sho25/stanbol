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
name|indexing
operator|.
name|core
operator|.
name|source
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityDataProvider
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
name|indexing
operator|.
name|core
operator|.
name|IndexingDestination
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
name|indexing
operator|.
name|core
operator|.
name|config
operator|.
name|IndexingConfig
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
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
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
name|servicesapi
operator|.
name|yard
operator|.
name|YardException
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
comment|/**  * {@link EntityDataProvider} implementation based on a {@link Yard}.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|YardEntityDataProvider
implements|implements
name|EntityDataProvider
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|YardEntityDataProvider
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * If the {@link IndexingConfig#getIndexingDestination()} is used as      * source.      */
specifier|public
specifier|static
specifier|final
name|String
name|PARAM_DESTINATION
init|=
literal|"destination"
decl_stmt|;
specifier|private
name|IndexingConfig
name|indexingConfig
decl_stmt|;
specifier|protected
name|Yard
name|yard
decl_stmt|;
specifier|protected
name|IndexingDestination
name|indexingDest
decl_stmt|;
specifier|public
name|YardEntityDataProvider
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|YardEntityDataProvider
parameter_list|(
name|Yard
name|yard
parameter_list|)
block|{
name|this
operator|.
name|yard
operator|=
name|yard
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
block|{
name|indexingConfig
operator|=
operator|(
name|IndexingConfig
operator|)
name|config
operator|.
name|get
argument_list|(
name|IndexingConfig
operator|.
name|KEY_INDEXING_CONFIG
argument_list|)
expr_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
operator|&&
name|indexingConfig
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"If the Yard is not parsed in"
operator|+
literal|"the constructor the parsed configuration MUST contain a"
operator|+
literal|"IndexingConfig!"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
block|{
return|return
name|yard
operator|==
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|initialise
parameter_list|()
block|{
name|indexingDest
operator|=
name|indexingConfig
operator|.
name|getIndexingDestination
argument_list|()
expr_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|indexingDest
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The IndexingConfig set in the"
operator|+
literal|"configuration does not provide a valid indexing destination!"
argument_list|)
throw|;
block|}
name|yard
operator|=
name|indexingDest
operator|.
name|getYard
argument_list|()
expr_stmt|;
if|if
condition|(
name|yard
operator|==
literal|null
condition|)
block|{
comment|//TODO: we need to check if we might get in trouble because of the
comment|//      initialisation order. In this case we will need to use
comment|//      lazy initialisation on the first call to getEntityData
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to retieve Yard from the"
operator|+
literal|"IndexingDestination!"
argument_list|)
throw|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{
name|yard
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|getEntityData
parameter_list|(
name|String
name|id
parameter_list|)
block|{
try|try
block|{
return|return
name|yard
operator|.
name|getRepresentation
argument_list|(
name|id
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|YardException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to get Representation '"
operator|+
name|id
operator|+
literal|"' from Yard"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to get Representation '"
operator|+
name|id
operator|+
literal|"' from Yard"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

