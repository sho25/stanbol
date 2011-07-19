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
import|import static
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
operator|.
name|NamingHelper
operator|.
name|UNIQUE_REF
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|ConnectionInfo
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
name|ObjectFactory
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
name|ObjectTypeDefinition
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

begin_class
specifier|public
class|class
name|MockOnlineAccess
implements|implements
name|RepositoryAccess
block|{
specifier|private
name|ObjectFactory
name|of
init|=
operator|new
name|ObjectFactory
argument_list|()
decl_stmt|;
specifier|public
name|MockOnlineAccess
parameter_list|()
block|{      }
annotation|@
name|Override
specifier|public
name|Object
name|getSession
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|CMSObject
argument_list|>
name|getNodeByPath
parameter_list|(
name|String
name|path
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|CMSObject
argument_list|>
name|getNodeById
parameter_list|(
name|String
name|id
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|CMSObject
argument_list|>
name|getNodeByPath
parameter_list|(
name|String
name|path
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|CMSObject
argument_list|>
name|getNodeById
parameter_list|(
name|String
name|id
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|CMSObject
argument_list|>
name|getNodeByName
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|CMSObject
argument_list|>
name|getNodeByName
parameter_list|(
name|String
name|name
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getFirstNodeByPath
parameter_list|(
name|String
name|path
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getFirstNodeById
parameter_list|(
name|String
name|id
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getFirstNodeByPath
parameter_list|(
name|String
name|path
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getFirstNodeById
parameter_list|(
name|String
name|id
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getFirstNodeByName
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getFirstNodeByName
parameter_list|(
name|String
name|name
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|CMSObject
argument_list|>
name|getChildren
parameter_list|(
name|CMSObject
name|node
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
if|if
condition|(
name|node
operator|==
literal|null
operator|||
operator|!
name|node
operator|.
name|getUniqueRef
argument_list|()
operator|.
name|equals
argument_list|(
name|DobjectFactoryImpTest
operator|.
name|PREFIX_ROOT
operator|+
name|UNIQUE_REF
argument_list|)
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
name|CMSObject
name|child1
init|=
operator|new
name|CMSObjectBuilder
argument_list|(
name|DobjectFactoryImpTest
operator|.
name|PREFIX_CHILD_1
argument_list|)
operator|.
name|namespace
argument_list|()
operator|.
name|build
argument_list|()
decl_stmt|;
name|CMSObject
name|child2
init|=
operator|new
name|CMSObjectBuilder
argument_list|(
name|DobjectFactoryImpTest
operator|.
name|PREFIX_CHILD_2
argument_list|)
operator|.
name|namespace
argument_list|()
operator|.
name|build
argument_list|()
decl_stmt|;
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|CMSObject
index|[]
block|{
name|child1
block|,
name|child2
block|}
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|ObjectTypeDefinition
name|getObjectTypeDefinition
parameter_list|(
name|String
name|typeRef
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
if|if
condition|(
name|typeRef
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|RepositoryAccessException
argument_list|(
literal|"Null typeRef"
argument_list|,
literal|null
argument_list|)
throw|;
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|Property
argument_list|>
name|getProperties
parameter_list|(
name|CMSObject
name|node
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|PropertyDefinition
argument_list|>
name|getPropertyDefinitions
parameter_list|(
name|ObjectTypeDefinition
name|instance
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ObjectTypeDefinition
argument_list|>
name|getParentTypeDefinitions
parameter_list|(
name|ObjectTypeDefinition
name|instance
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ObjectTypeDefinition
argument_list|>
name|getChildObjectTypeDefinitions
parameter_list|(
name|ObjectTypeDefinition
name|instance
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getContainerObject
parameter_list|(
name|Property
name|instance
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|PropertyDefinition
name|getPropertyDefinition
parameter_list|(
name|Property
name|instance
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getNamespaceURI
parameter_list|(
name|String
name|prefix
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canRetrieve
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canRetrieve
parameter_list|(
name|Object
name|session
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getParentByNode
parameter_list|(
name|CMSObject
name|instance
parameter_list|,
name|Object
name|session
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
comment|// TODO Auto-generated method stub
if|if
condition|(
name|instance
operator|.
name|getParentRef
argument_list|()
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|RepositoryAccessException
argument_list|(
literal|"No parent"
argument_list|,
literal|null
argument_list|)
throw|;
block|}
else|else
block|{
name|String
name|ref
init|=
name|instance
operator|.
name|getParentRef
argument_list|()
decl_stmt|;
name|CMSObject
name|parent
init|=
name|of
operator|.
name|createCMSObject
argument_list|()
decl_stmt|;
name|parent
operator|.
name|setLocalname
argument_list|(
literal|"localname"
operator|+
name|ref
argument_list|)
expr_stmt|;
name|parent
operator|.
name|setNamespace
argument_list|(
literal|"namespace"
operator|+
name|ref
argument_list|)
expr_stmt|;
name|parent
operator|.
name|setParentRef
argument_list|(
literal|"parent"
operator|+
name|ref
argument_list|)
expr_stmt|;
name|parent
operator|.
name|setUniqueRef
argument_list|(
name|ref
argument_list|)
expr_stmt|;
return|return
name|parent
return|;
block|}
block|}
block|}
end_class

end_unit

