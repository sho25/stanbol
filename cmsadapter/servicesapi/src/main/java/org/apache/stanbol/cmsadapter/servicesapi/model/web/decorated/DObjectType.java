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
name|chemistry
operator|.
name|opencmis
operator|.
name|commons
operator|.
name|definitions
operator|.
name|TypeDefinition
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
name|repository
operator|.
name|RepositoryAccessException
import|;
end_import

begin_comment
comment|/**  * Decorated form of {@link ObjectTypeDefinition}. While {@link ObjectTypeDefinition} is completely separated  * from the repository it is generated, {@link DObjectType} is able to reconnect to the repository and fetch  * any data that is not present in {@link ObjectTypeDefinition}.</br> Details of when the repository is  * determined by {@link AdapterMode}s. See {@link DObjectAdapter} and {@link AdapterMode} for more details.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|DObjectType
block|{
comment|/**      *       * @return Unique identifier of underlying {@link ObjectTypeDefinition}      */
name|String
name|getID
parameter_list|()
function_decl|;
comment|/**      *       * @return Localname of underlying {@link ObjectTypeDefinition}      *       */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      *       * @return Namespace of underlying {@link ObjectTypeDefinition}      */
name|String
name|getNamespace
parameter_list|()
function_decl|;
comment|/**      *       * @return Property definitions of underlying {@link ObjectTypeDefinition}, wrapped as {@link DPropertyDefinition}       * @throws RepositoryAccessException      *             If repository can not be accessed in<b>ONLINE</b> mode.      */
name|List
argument_list|<
name|DPropertyDefinition
argument_list|>
name|getPropertyDefinitions
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      *       * @return Parent type definitions of underlying {@link ObjectTypeDefinition}, wrapped as {@link DObjectType}       * @throws RepositoryAccessException      *             If repository can not be accessed in<b>ONLINE</b> mode.      */
name|List
argument_list|<
name|DObjectType
argument_list|>
name|getParentDefinitions
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      *       * @return Child type definitions of underlying {@link ObjectTypeDefinition}, wrapped as {@link DObjectType}       * @throws RepositoryAccessException      *             If repository can not be accessed in<b>ONLINE</b> mode.      */
name|List
argument_list|<
name|DObjectType
argument_list|>
name|getChildDefinitions
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      *       * @return Underlying {@link ObjectTypeDefinition}      */
name|ObjectTypeDefinition
name|getInstance
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

