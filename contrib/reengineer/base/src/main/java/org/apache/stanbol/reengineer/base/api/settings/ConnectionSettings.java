begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|settings
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_comment
comment|/**  * A {@code ConnectionSettings} contains all the information that are needed in order to open a connection  * with a relational database through JDBC.  *   * @author andrea.nuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ConnectionSettings
extends|extends
name|Serializable
block|{
comment|/**      * Get the URL of the connection.      *       * @return the URL of the connection as a {@link String}.      */
name|String
name|getUrl
parameter_list|()
function_decl|;
comment|/**      * Get the name of the server on which the DB is running.      *       * @return the name of the server as a {@link String}.      */
name|String
name|getServerName
parameter_list|()
function_decl|;
comment|/**      * Get the port of the server on which the DB is running.      *       * @return the port of the server as a {@link String}.      */
name|String
name|getPortNumber
parameter_list|()
function_decl|;
comment|/**      * Get the name of the database.      *       * @return the port of the server as a {@link String}.      */
name|String
name|getDatabaseName
parameter_list|()
function_decl|;
comment|/**      * Get the user name for the autenthication.      *       * @return the user name as a {@link String}.      */
name|String
name|getUserName
parameter_list|()
function_decl|;
comment|/**      * Get the password for the autenthication.      *       * @return the password as a {@link String}.      */
name|String
name|getPassword
parameter_list|()
function_decl|;
comment|/**      * Get the select method for querying.      *       * @return the select method as a {@link String}.      */
name|String
name|getSelectMethod
parameter_list|()
function_decl|;
comment|/**      * Get the JDBC driver of the database.      *       * @return the JDBC driver as a {@link String}.      */
name|String
name|getJDBCDriver
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

