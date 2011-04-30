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
name|repository
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
name|ConnectionInfo
import|;
end_import

begin_interface
specifier|public
interface|interface
name|RepositoryAccessManager
block|{
name|RepositoryAccess
name|getRepositoryAccessor
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
function_decl|;
name|RepositoryAccess
name|getRepositoryAccess
parameter_list|(
name|Object
name|session
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

