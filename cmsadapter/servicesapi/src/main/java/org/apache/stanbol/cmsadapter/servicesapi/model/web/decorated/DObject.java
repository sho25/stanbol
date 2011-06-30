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
name|repository
operator|.
name|RepositoryAccessException
import|;
end_import

begin_comment
comment|/**  * Decorated form of {@link CMSObject}. While {@link CMSObject} is completely separated from the repository it  * is generated, DObject is able to reconnect to the repository and fetch any data that is not present in  * {@link CMSObject}.</br> Details of when the repository is determined by {@link AdapterMode}s. See  * {@link DObjectAdapter} and {@link AdapterMode} for more details.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|DObject
block|{
comment|/**      *       * @return Unique identifier of underlying CMSObject.      */
name|String
name|getID
parameter_list|()
function_decl|;
comment|/**      *       * @return Path of the underlying CMSObject, If more than one path is       * present returns the first path.      */
name|String
name|getPath
parameter_list|()
function_decl|;
comment|/**      *       * @return Localname of the underlying CMSObject.      */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      *       * @return Namespace of the underlying CMSObject.      */
name|String
name|getNamespace
parameter_list|()
function_decl|;
comment|/**      *       * @return Underlying CMSObject.      */
name|CMSObject
name|getInstance
parameter_list|()
function_decl|;
comment|/**      *       * @return Direct children of the CMS object, wrapped as {@link DObject}s .May return null in<b>TOLERATED_OFFLINE</b> or<b>STRICT_OFFLINE</b> mode.      * @throws RepositoryAccessException      *              If repository can not be accessed in<b>ONLINE</b> mode.      */
name|List
argument_list|<
name|DObject
argument_list|>
name|getChildren
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      * Fetches parent of the item from CMS repository.      *       * @return parent of the object wrapped as {@link DObject} .May return null in<b>TOLERATED_OFFLINE</b> or<b>STRICT_OFFLINE</b> mode.      * @throws RepositoryAccessException      *             If repository can not be accessed in<b>ONLINE</b> mode.      */
name|DObject
name|getParent
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      * Fetches object type of the item from CMS repository.      *       * @return Object type of the object wrapped as {@link DObjectType} .May return null in<b>TOLERATED_OFFLINE</b> or<b>STRICT_OFFLINE</b> mode.      * @throws RepositoryAccessException      *             If repository can not be accessed in<b>ONLINE</b> mode.      */
name|DObjectType
name|getObjectType
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      *       * @return Properties of the CMS object wrapped as {@link DProperty} .May return null in<b>TOLERATED_OFFLINE</b> or<b>STRICT_OFFLINE</b> mode.      * @throws RepositoryAccessException      *              If repository can not be accessed in<b>ONLINE</b> mode.      */
name|List
argument_list|<
name|DProperty
argument_list|>
name|getProperties
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
block|}
end_interface

end_unit

