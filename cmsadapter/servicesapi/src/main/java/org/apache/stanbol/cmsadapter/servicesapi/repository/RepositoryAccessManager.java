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

begin_comment
comment|/**  * This class is responsible for retrieving a suitable accessor when a   * session or connection description is given.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RepositoryAccessManager
block|{
comment|/**      *       * @param connectionInfo      * @return Any suitable {@link RepositoryAccess} instance that can connect to the      * CMS repository described in<b>connectionInfo</b> parameter or null if no suitable       * accessor can be found.       */
name|RepositoryAccess
name|getRepositoryAccessor
parameter_list|(
name|ConnectionInfo
name|connectionInfo
parameter_list|)
function_decl|;
comment|/**      *       * @param session      * @return Any suitable {@link RepositoryAccess} instance that can connect to the      * CMS repository through session given in<b>session</b> parameter or null if no suitable       * accessor can be found.       */
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

