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
name|repository
operator|.
name|RepositoryAccessException
import|;
end_import

begin_comment
comment|/**  * Decorated form of {@link Property}. While {@link Property} is completely separated from the repository it  * is generated, {@link DProperty} is able to reconnect to the repository and fetch any data that is not  * present in {@link Property}.</br> Details of when the repository is determined by {@link AdapterMode}s.  * See {@link DObjectAdapter} and {@link AdapterMode} for more details.  *   * @author cihan  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|DProperty
block|{
comment|/**      *       * @return Name of the underlying {@link Property}.      *       */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      *       * @return In which property definition this property is defined, wrapped as {@link DObject} .May return      *         null in<b>TOLERATED_OFFLINE</b> or<b>STRICT_OFFLINE</b> mode.      * @throws RepositoryAccessException      *             if can not access repository in<b>ONLINE</> mode.      */
name|DPropertyDefinition
name|getDefinition
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      *       * @return CMS object to which this property belongs, wrapped as {@link DObject}, .May return null in      *<b>TOLERATED_OFFLINE</b> or<b>STRICT_OFFLINE</b> mode.      * @throws RepositoryAccessException      *             if can not access repository in<b>ONLINE</> mode.      */
name|DObject
name|getSourceObject
parameter_list|()
throws|throws
name|RepositoryAccessException
function_decl|;
comment|/**      *       * @return Property values in string representation.      */
name|List
argument_list|<
name|String
argument_list|>
name|getValue
parameter_list|()
function_decl|;
comment|/**      *       * @return Underlying {@link Property}      */
name|Property
name|getInstance
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

