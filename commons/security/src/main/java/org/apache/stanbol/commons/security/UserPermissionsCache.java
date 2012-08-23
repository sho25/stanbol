begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one  * or more contributor license agreements.  See the NOTICE file  * distributed with this work for additional information  * regarding copyright ownership.  The ASF licenses this file  * to you under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance  * with the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing,  * software distributed under the License is distributed on an  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY  * KIND, either express or implied.  See the License for the  * specific language governing permissions and limitations  * under the License.  */
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
name|security
operator|.
name|PermissionCollection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|Principal
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * Caches<code>PermissonCollection</code>S of users  *   * @author mir  */
end_comment

begin_class
specifier|public
class|class
name|UserPermissionsCache
block|{
comment|/** 	 * Stores the mapping between a<code>Principal</code> representing the user 	 * and his/her<code>PermissionCollection</code>. 	 */
specifier|private
name|Map
argument_list|<
name|Principal
argument_list|,
name|PermissionCollection
argument_list|>
name|userPermissionsMap
init|=
operator|new
name|HashMap
argument_list|<
name|Principal
argument_list|,
name|PermissionCollection
argument_list|>
argument_list|()
decl_stmt|;
comment|/** 	 * Return the cached<code>PermissionCollection</code> containing the users 	 *<code>Permission</code>S. 	 *  	 * @param user 	 * @return 	 */
specifier|public
name|PermissionCollection
name|getCachedUserPermissions
parameter_list|(
name|Principal
name|user
parameter_list|)
block|{
return|return
name|userPermissionsMap
operator|.
name|get
argument_list|(
name|user
argument_list|)
return|;
block|}
comment|/** 	 * Caches the<code>PermissionCollection</code> for the specified user 	 *  	 * @param user 	 * @param permissions 	 */
specifier|public
name|void
name|cacheUserPermissions
parameter_list|(
name|Principal
name|user
parameter_list|,
name|PermissionCollection
name|permissions
parameter_list|)
block|{
name|userPermissionsMap
operator|.
name|put
argument_list|(
name|user
argument_list|,
name|permissions
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Clears the cache. 	 */
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|userPermissionsMap
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

