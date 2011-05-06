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
name|ChildObjectDefinition
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
name|model
operator|.
name|web
operator|.
name|decorated
operator|.
name|AdapterMode
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
name|DChildObjectType
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

begin_class
specifier|public
class|class
name|DObjectFactoryImp
implements|implements
name|DObjectAdapter
block|{
specifier|private
name|RepositoryAccess
name|access
decl_stmt|;
specifier|private
name|Object
name|session
decl_stmt|;
specifier|private
name|AdapterMode
name|mode
decl_stmt|;
specifier|public
name|DObjectFactoryImp
parameter_list|(
name|RepositoryAccess
name|access
parameter_list|,
name|Object
name|session
parameter_list|)
block|{
name|this
operator|.
name|access
operator|=
name|access
expr_stmt|;
name|this
operator|.
name|session
operator|=
name|session
expr_stmt|;
name|this
operator|.
name|mode
operator|=
name|AdapterMode
operator|.
name|ONLINE
expr_stmt|;
block|}
specifier|public
name|DObjectFactoryImp
parameter_list|(
name|RepositoryAccess
name|access
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|this
operator|.
name|access
operator|=
name|access
expr_stmt|;
name|this
operator|.
name|session
operator|=
name|access
operator|.
name|getSession
argument_list|(
name|connectionInfo
argument_list|)
expr_stmt|;
name|this
operator|.
name|mode
operator|=
name|AdapterMode
operator|.
name|ONLINE
expr_stmt|;
block|}
specifier|public
name|DObjectFactoryImp
parameter_list|(
name|RepositoryAccess
name|access
parameter_list|,
name|Object
name|session
parameter_list|,
name|AdapterMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|access
operator|=
name|access
expr_stmt|;
name|this
operator|.
name|session
operator|=
name|session
expr_stmt|;
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
block|}
specifier|public
name|DObjectFactoryImp
parameter_list|(
name|RepositoryAccess
name|access
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|,
name|AdapterMode
name|mode
parameter_list|)
throws|throws
name|RepositoryAccessException
block|{
name|this
operator|.
name|access
operator|=
name|access
expr_stmt|;
name|this
operator|.
name|session
operator|=
name|access
operator|.
name|getSession
argument_list|(
name|connectionInfo
argument_list|)
expr_stmt|;
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|DObject
name|wrapAsDObject
parameter_list|(
name|CMSObject
name|node
parameter_list|)
block|{
return|return
operator|new
name|DObjectImp
argument_list|(
name|node
argument_list|,
name|this
argument_list|,
name|access
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|DObjectType
name|wrapAsDObjectType
parameter_list|(
name|ObjectTypeDefinition
name|definition
parameter_list|)
block|{
return|return
operator|new
name|DObjectTypeImp
argument_list|(
name|definition
argument_list|,
name|this
argument_list|,
name|access
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|DPropertyDefinition
name|wrapAsDPropertyDefinition
parameter_list|(
name|PropertyDefinition
name|propertyDefinition
parameter_list|)
block|{
return|return
operator|new
name|DPropertyDefinitionImp
argument_list|(
name|propertyDefinition
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|DChildObjectType
name|wrapAsDChildObjectType
parameter_list|(
name|ChildObjectDefinition
name|childObjectDefinition
parameter_list|)
block|{
return|return
operator|new
name|DChildObjectTypeImp
argument_list|(
name|childObjectDefinition
argument_list|,
name|this
argument_list|,
name|access
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|DProperty
name|wrapAsDProperty
parameter_list|(
name|Property
name|property
parameter_list|)
block|{
return|return
operator|new
name|DPropertyImp
argument_list|(
name|property
argument_list|,
name|this
argument_list|,
name|access
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getSession
parameter_list|()
block|{
return|return
name|session
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setMode
parameter_list|(
name|AdapterMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|AdapterMode
name|getMode
parameter_list|()
block|{
return|return
name|mode
return|;
block|}
block|}
end_class

end_unit

