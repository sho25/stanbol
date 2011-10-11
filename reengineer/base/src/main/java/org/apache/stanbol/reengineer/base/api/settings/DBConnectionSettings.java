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

begin_class
specifier|public
class|class
name|DBConnectionSettings
implements|implements
name|ConnectionSettings
block|{
comment|/**      *       */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|1114642049806268057L
decl_stmt|;
specifier|private
name|String
name|url
decl_stmt|;
specifier|private
name|String
name|serverName
decl_stmt|;
specifier|private
name|String
name|portNumber
decl_stmt|;
specifier|private
name|String
name|databaseName
decl_stmt|;
specifier|private
name|String
name|userName
decl_stmt|;
specifier|private
name|String
name|password
decl_stmt|;
specifier|private
name|String
name|selectMethod
decl_stmt|;
specifier|private
name|String
name|jdbcDriver
decl_stmt|;
specifier|public
name|DBConnectionSettings
parameter_list|()
block|{      }
comment|/**      *       * Create a new {@link DBConnectionSettings} that contain all the information that enable to Semion to      * open a connection with the specified database.      *       * @param url      *            {@link String}      * @param serverName      *            {@link String}      * @param portNumber      *            {@link String}      * @param databaseName      *            {@link String}      * @param userName      *            {@link String}      * @param password      *            {@link String}      * @param selectMethod      *            {@link String}      * @param jdbcDriver      *            {@link String}      */
specifier|public
name|DBConnectionSettings
parameter_list|(
name|String
name|url
parameter_list|,
name|String
name|serverName
parameter_list|,
name|String
name|portNumber
parameter_list|,
name|String
name|databaseName
parameter_list|,
name|String
name|userName
parameter_list|,
name|String
name|password
parameter_list|,
name|String
name|selectMethod
parameter_list|,
name|String
name|jdbcDriver
parameter_list|)
block|{
name|this
operator|.
name|url
operator|=
name|url
expr_stmt|;
name|this
operator|.
name|serverName
operator|=
name|serverName
expr_stmt|;
name|this
operator|.
name|portNumber
operator|=
name|portNumber
expr_stmt|;
name|this
operator|.
name|databaseName
operator|=
name|databaseName
expr_stmt|;
name|this
operator|.
name|userName
operator|=
name|userName
expr_stmt|;
name|this
operator|.
name|password
operator|=
name|password
expr_stmt|;
name|this
operator|.
name|selectMethod
operator|=
name|selectMethod
expr_stmt|;
name|this
operator|.
name|jdbcDriver
operator|=
name|jdbcDriver
expr_stmt|;
block|}
specifier|public
name|String
name|getUrl
parameter_list|()
block|{
return|return
name|url
return|;
block|}
specifier|public
name|String
name|getServerName
parameter_list|()
block|{
return|return
name|serverName
return|;
block|}
specifier|public
name|String
name|getPortNumber
parameter_list|()
block|{
return|return
name|portNumber
return|;
block|}
specifier|public
name|String
name|getDatabaseName
parameter_list|()
block|{
return|return
name|databaseName
return|;
block|}
specifier|public
name|String
name|getUserName
parameter_list|()
block|{
return|return
name|userName
return|;
block|}
specifier|public
name|String
name|getPassword
parameter_list|()
block|{
return|return
name|password
return|;
block|}
specifier|public
name|String
name|getSelectMethod
parameter_list|()
block|{
return|return
name|selectMethod
return|;
block|}
specifier|public
name|String
name|getJDBCDriver
parameter_list|()
block|{
return|return
name|jdbcDriver
return|;
block|}
block|}
end_class

end_unit

