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
name|servicesapi
operator|.
name|model
operator|.
name|web
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
name|repository
operator|.
name|RepositoryAccessException
import|;
end_import

begin_interface
specifier|public
interface|interface
name|DChildObjectType
block|{
name|boolean
name|isRequired
parameter_list|()
function_decl|;
comment|/**      *       * @return Allowed object type declerations, null if in<b>STRICT_OFFLINE</b> mode.      * @throws RepositoryAccessException if can not access CMS repository in<b>ONLINE</b> mode.      */
name|DObjectType
name|getAllowedObjectDefinitions
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
name|ChildObjectDefinition
name|getInstance
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

