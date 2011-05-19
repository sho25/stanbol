begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|DObjectType
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
name|DObjectImp
implements|implements
name|DObject
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
name|DObjectImp
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|CMSObject
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
name|RepositoryAccess
name|offlineAccess
decl_stmt|;
specifier|private
name|List
argument_list|<
name|DObject
argument_list|>
name|children
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|DProperty
argument_list|>
name|properties
init|=
literal|null
decl_stmt|;
specifier|private
name|DObject
name|parent
init|=
literal|null
decl_stmt|;
specifier|private
name|DObjectType
name|objectType
init|=
literal|null
decl_stmt|;
specifier|public
name|DObjectImp
parameter_list|(
name|CMSObject
name|instance
parameter_list|,
name|DObjectAdapter
name|factory
parameter_list|,
name|RepositoryAccess
name|access
parameter_list|,
name|RepositoryAccess
name|offlineAccess
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
name|factory
expr_stmt|;
name|this
operator|.
name|access
operator|=
name|access
expr_stmt|;
name|this
operator|.
name|offlineAccess
operator|=
name|offlineAccess
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getID
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getUniqueRef
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPath
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getPath
argument_list|()
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
annotation|@
name|Override
specifier|public
name|String
name|getNamespace
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getNamespace
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|DObject
argument_list|>
name|getChildren
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
if|if
condition|(
name|children
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
name|children
operator|=
name|getChildrenOnline
argument_list|()
expr_stmt|;
break|break;
case|case
name|TOLERATED_OFFLINE
case|:
name|children
operator|=
name|getChildrenTOffline
argument_list|()
expr_stmt|;
break|break;
case|case
name|STRICT_OFFLINE
case|:
name|children
operator|=
name|getChildrenSOffline
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
return|return
name|children
return|;
block|}
specifier|private
name|List
argument_list|<
name|DObject
argument_list|>
name|getChildrenOnline
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
name|List
argument_list|<
name|CMSObject
argument_list|>
name|nodes
init|=
name|access
operator|.
name|getChildren
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
name|wrapAsDObject
argument_list|(
name|nodes
argument_list|)
return|;
block|}
specifier|private
name|List
argument_list|<
name|DObject
argument_list|>
name|getChildrenTOffline
parameter_list|()
block|{
try|try
block|{
return|return
name|getChildrenOnline
argument_list|()
return|;
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
literal|"Error accesing repository at fetching children of {}. Tyring offline"
argument_list|,
name|instance
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|getChildrenSOffline
argument_list|()
return|;
block|}
block|}
specifier|private
name|List
argument_list|<
name|DObject
argument_list|>
name|getChildrenSOffline
parameter_list|()
block|{
return|return
name|wrapAsDObject
argument_list|(
name|instance
operator|.
name|getChildren
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|DObject
name|getParent
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
if|if
condition|(
name|parent
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
name|parent
operator|=
name|factory
operator|.
name|wrapAsDObject
argument_list|(
name|access
operator|.
name|getParentByNode
argument_list|(
name|instance
argument_list|,
name|factory
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|TOLERATED_OFFLINE
case|:
try|try
block|{
name|parent
operator|=
name|factory
operator|.
name|wrapAsDObject
argument_list|(
name|access
operator|.
name|getParentByNode
argument_list|(
name|instance
argument_list|,
name|factory
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
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
literal|"Can not access repository at fetching parent of {}."
argument_list|,
name|instance
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|parent
operator|=
name|factory
operator|.
name|wrapAsDObject
argument_list|(
name|offlineAccess
operator|.
name|getParentByNode
argument_list|(
name|instance
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|STRICT_OFFLINE
case|:
name|parent
operator|=
name|factory
operator|.
name|wrapAsDObject
argument_list|(
name|access
operator|.
name|getParentByNode
argument_list|(
name|instance
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
return|return
name|parent
return|;
block|}
annotation|@
name|Override
specifier|public
name|DObjectType
name|getObjectType
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
if|if
condition|(
name|objectType
operator|==
literal|null
condition|)
block|{
name|String
name|typeRef
init|=
name|instance
operator|.
name|getObjectTypeRef
argument_list|()
decl_stmt|;
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
name|objectType
operator|=
name|factory
operator|.
name|wrapAsDObjectType
argument_list|(
name|access
operator|.
name|getObjectTypeDefinition
argument_list|(
name|typeRef
argument_list|,
name|factory
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|TOLERATED_OFFLINE
case|:
try|try
block|{
name|objectType
operator|=
name|factory
operator|.
name|wrapAsDObjectType
argument_list|(
name|access
operator|.
name|getObjectTypeDefinition
argument_list|(
name|typeRef
argument_list|,
name|factory
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
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
literal|"Can not access repository at fetching object type of {}."
argument_list|,
name|instance
operator|.
name|getPath
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
name|objectType
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|DProperty
argument_list|>
name|getProperties
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
if|if
condition|(
name|properties
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
name|properties
operator|=
name|getPropertiesOnline
argument_list|()
expr_stmt|;
break|break;
case|case
name|TOLERATED_OFFLINE
case|:
try|try
block|{
name|properties
operator|=
name|getPropertiesOnline
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
literal|"Can not access repository at fetching properties of {}."
argument_list|,
name|instance
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|properties
operator|=
name|wrapAsDProperty
argument_list|(
name|instance
operator|.
name|getProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|STRICT_OFFLINE
case|:
name|properties
operator|=
name|wrapAsDProperty
argument_list|(
name|instance
operator|.
name|getProperty
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
return|return
name|properties
return|;
block|}
annotation|@
name|Override
specifier|public
name|CMSObject
name|getInstance
parameter_list|()
block|{
return|return
name|this
operator|.
name|instance
return|;
block|}
specifier|private
name|List
argument_list|<
name|DProperty
argument_list|>
name|getPropertiesOnline
parameter_list|()
throws|throws
name|RepositoryAccessException
block|{
name|List
argument_list|<
name|Property
argument_list|>
name|props
init|=
name|access
operator|.
name|getProperties
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
name|wrapAsDProperty
argument_list|(
name|props
argument_list|)
return|;
block|}
specifier|private
name|List
argument_list|<
name|DObject
argument_list|>
name|wrapAsDObject
parameter_list|(
name|List
argument_list|<
name|CMSObject
argument_list|>
name|cmsObjects
parameter_list|)
block|{
name|List
argument_list|<
name|DObject
argument_list|>
name|wrappeds
init|=
operator|new
name|ArrayList
argument_list|<
name|DObject
argument_list|>
argument_list|(
name|cmsObjects
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|CMSObject
name|node
range|:
name|cmsObjects
control|)
block|{
name|wrappeds
operator|.
name|add
argument_list|(
name|this
operator|.
name|factory
operator|.
name|wrapAsDObject
argument_list|(
name|node
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|wrappeds
return|;
block|}
specifier|private
name|List
argument_list|<
name|DProperty
argument_list|>
name|wrapAsDProperty
parameter_list|(
name|List
argument_list|<
name|Property
argument_list|>
name|props
parameter_list|)
block|{
name|List
argument_list|<
name|DProperty
argument_list|>
name|properties
init|=
operator|new
name|ArrayList
argument_list|<
name|DProperty
argument_list|>
argument_list|(
name|props
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Property
name|prop
range|:
name|props
control|)
block|{
name|properties
operator|.
name|add
argument_list|(
name|factory
operator|.
name|wrapAsDProperty
argument_list|(
name|prop
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|properties
return|;
block|}
block|}
end_class

end_unit

