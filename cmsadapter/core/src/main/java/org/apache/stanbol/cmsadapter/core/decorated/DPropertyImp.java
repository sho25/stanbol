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
name|cmsadapter
operator|.
name|core
operator|.
name|decorated
package|;
end_package

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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|CMSObject
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|Property
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|PropertyDefinition
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DObject
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DObjectAdapter
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DProperty
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|DPropertyDefinition
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccess
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessException
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
name|DPropertyImp
implements|implements
name|DProperty
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
name|DPropertyImp
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Property
name|instance
decl_stmt|;
specifier|private
name|DObjectAdapter
name|factory
decl_stmt|;
specifier|private
name|RepositoryAccess
name|access
decl_stmt|;
specifier|private
name|DObject
name|sourceObject
decl_stmt|;
specifier|private
name|DPropertyDefinition
name|propertyDefinition
decl_stmt|;
specifier|public
name|DPropertyImp
parameter_list|(
name|Property
name|instance
parameter_list|,
name|DObjectAdapter
name|adapter
parameter_list|,
name|RepositoryAccess
name|access
parameter_list|)
block|{
name|this
operator|.
name|instance
operator|=
name|instance
expr_stmt|;
name|this
operator|.
name|factory
operator|=
name|adapter
expr_stmt|;
name|this
operator|.
name|access
operator|=
name|access
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|DPropertyDefinition
name|getDefinition
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
if|if
condition|(
name|propertyDefinition
operator|==
literal|null
condition|)
block|{
switch|switch
condition|(
name|factory
operator|.
name|getMode
argument_list|()
condition|)
block|{
case|case
name|ONLINE
case|:
name|propertyDefinition
operator|=
name|getPropertyDefinitionOnline
argument_list|()
expr_stmt|;
break|break;
case|case
name|TOLERATED_OFFLINE
case|:
try|try
block|{
name|propertyDefinition
operator|=
name|getPropertyDefinitionOnline
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Can not access repository at fetching source object of property {}"
argument_list|,
name|instance
operator|.
name|getLocalname
argument_list|()
argument_list|)
expr_stmt|;
name|propertyDefinition
operator|=
name|factory
operator|.
name|wrapAsDPropertyDefinition
argument_list|(
name|instance
operator|.
name|getPropertyDefinition
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|STRICT_OFFLINE
case|:
name|propertyDefinition
operator|=
name|factory
operator|.
name|wrapAsDPropertyDefinition
argument_list|(
name|instance
operator|.
name|getPropertyDefinition
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|propertyDefinition
return|;
block|}
specifier|private
name|DPropertyDefinition
name|getPropertyDefinitionOnline
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
name|PropertyDefinition
name|propDef
init|=
name|access
operator|.
name|getPropertyDefinition
argument_list|(
name|instance
argument_list|,
name|factory
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|wrapAsDPropertyDefinition
argument_list|(
name|propDef
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|DObject
name|getSourceObject
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
if|if
condition|(
name|sourceObject
operator|==
literal|null
condition|)
block|{
switch|switch
condition|(
name|factory
operator|.
name|getMode
argument_list|()
condition|)
block|{
case|case
name|ONLINE
case|:
name|sourceObject
operator|=
name|getSourceObjectOnline
argument_list|()
expr_stmt|;
break|break;
case|case
name|TOLERATED_OFFLINE
case|:
try|try
block|{
name|sourceObject
operator|=
name|getSourceObjectOnline
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Can not access repository at fetching source object of property {}"
argument_list|,
name|instance
operator|.
name|getLocalname
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|STRICT_OFFLINE
case|:
break|break;
block|}
block|}
return|return
name|sourceObject
return|;
block|}
specifier|private
name|DObject
name|getSourceObjectOnline
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
name|CMSObject
name|source
init|=
name|access
operator|.
name|getContainerObject
argument_list|(
name|instance
argument_list|,
name|factory
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|wrapAsDObject
argument_list|(
name|source
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getValue
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getValue
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Property
name|getInstance
parameter_list|()
block|{
return|return
name|instance
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getLocalname
argument_list|()
return|;
block|}
block|}
end_class

end_unit

