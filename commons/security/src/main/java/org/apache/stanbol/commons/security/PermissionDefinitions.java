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
name|Iterator
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
name|osgi
operator|.
name|service
operator|.
name|permissionadmin
operator|.
name|PermissionInfo
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
name|rdf
operator|.
name|core
operator|.
name|Literal
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
name|rdf
operator|.
name|core
operator|.
name|MGraph
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
name|rdf
operator|.
name|core
operator|.
name|NonLiteral
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
name|rdf
operator|.
name|core
operator|.
name|Triple
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
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|rdf
operator|.
name|ontologies
operator|.
name|OSGI
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
name|rdf
operator|.
name|ontologies
operator|.
name|PERMISSION
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
name|rdf
operator|.
name|ontologies
operator|.
name|SIOC
import|;
end_import

begin_comment
comment|/**  * Provides utility methods to extract infomation for the permission assignment.  *   * @author clemens  */
end_comment

begin_class
class|class
name|PermissionDefinitions
block|{
specifier|private
name|MGraph
name|systemGraph
decl_stmt|;
name|PermissionDefinitions
parameter_list|(
name|MGraph
name|systeGraph
parameter_list|)
block|{
name|this
operator|.
name|systemGraph
operator|=
name|systeGraph
expr_stmt|;
block|}
comment|/** 	 * Returns the permissions of a specified location. 	 * I.e. the permissions of all permission assignments matching<code>location</code>. 	 *  	 * @param location	the location of a bundle 	 * @return an array with<code>PermissionInfo</code> elements 	 */
name|PermissionInfo
index|[]
name|retrievePermissions
parameter_list|(
name|String
name|location
parameter_list|)
block|{
name|List
argument_list|<
name|PermissionInfo
argument_list|>
name|permInfoList
init|=
operator|new
name|ArrayList
argument_list|<
name|PermissionInfo
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|ownerTriples
init|=
name|systemGraph
operator|.
name|filter
argument_list|(
operator|new
name|UriRef
argument_list|(
name|location
argument_list|)
argument_list|,
name|OSGI
operator|.
name|owner
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|ownerTriples
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|NonLiteral
name|user
init|=
operator|(
name|NonLiteral
operator|)
name|ownerTriples
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|lookForPermissions
argument_list|(
name|user
argument_list|,
name|permInfoList
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|permInfoList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|permInfoList
operator|.
name|toArray
argument_list|(
operator|new
name|PermissionInfo
index|[
name|permInfoList
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
comment|/** 	 * Look for all permissions of a role and add them to a list. 	 * And if the role has another role, then execute this function recursively, 	 * until all permissions are found. 	 *  	 * @param role	a<code>NonLiteral</code> which is either a user or a role 	 * @param permInfoList	a list with all the added permissions of this bundle 	 */
specifier|private
name|void
name|lookForPermissions
parameter_list|(
name|NonLiteral
name|role
parameter_list|,
name|List
argument_list|<
name|PermissionInfo
argument_list|>
name|permInfoList
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|permissionTriples
init|=
name|systemGraph
operator|.
name|filter
argument_list|(
name|role
argument_list|,
name|PERMISSION
operator|.
name|hasPermission
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|permissionTriples
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|NonLiteral
name|permission
init|=
operator|(
name|NonLiteral
operator|)
name|permissionTriples
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|javaPermissionTriples
init|=
name|systemGraph
operator|.
name|filter
argument_list|(
name|permission
argument_list|,
name|PERMISSION
operator|.
name|javaPermissionEntry
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|javaPermissionTriples
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|t
init|=
name|javaPermissionTriples
operator|.
name|next
argument_list|()
decl_stmt|;
name|Literal
name|permEntry
init|=
operator|(
name|Literal
operator|)
name|t
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|permInfoList
operator|.
name|add
argument_list|(
operator|new
name|PermissionInfo
argument_list|(
name|permEntry
operator|.
name|getLexicalForm
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|roleTriples
init|=
name|systemGraph
operator|.
name|filter
argument_list|(
name|role
argument_list|,
name|SIOC
operator|.
name|has_function
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|roleTriples
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|NonLiteral
name|anotherRole
init|=
operator|(
name|NonLiteral
operator|)
name|roleTriples
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|this
operator|.
name|lookForPermissions
argument_list|(
name|anotherRole
argument_list|,
name|permInfoList
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
