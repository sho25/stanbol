begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  *  Copyright 2010 reto.  *   *  Licensed under the Apache License, Version 2.0 (the "License");  *  you may not use this file except in compliance with the License.  *  You may obtain a copy of the License at  *   *       http://www.apache.org/licenses/LICENSE-2.0  *   *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|security
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * Services implementing this interface provide additional permissions  * to users with a Web-Id.  *   * Typically this is used to assign permissions to roaming users.  *   * @author reto  */
end_comment

begin_interface
specifier|public
interface|interface
name|WebIdBasedPermissionProvider
block|{
comment|/** 	 * This methods returns string descriptions of the permissions to be granted 	 * to the user with a specified Web-Id. The permissions are described  	 * using the conventional format '("ClassName" "name" "actions")'. 	 * 	 * @param webId the uri identifying the user (aka Web-Id) 	 * @return the string descriptions of the permissions 	 */
name|Collection
argument_list|<
name|String
argument_list|>
name|getPermissions
parameter_list|(
name|IRI
name|webId
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

