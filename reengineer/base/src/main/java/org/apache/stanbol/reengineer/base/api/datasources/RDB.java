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
name|datasources
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|IdentifiedDataSource
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|settings
operator|.
name|ConnectionSettings
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|ReengineerType
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|URIGenerator
import|;
end_import

begin_comment
comment|/**  * An object representing a relational database.  *   * @author andrea.nuzzolese  *   */
end_comment

begin_class
specifier|public
class|class
name|RDB
extends|extends
name|IdentifiedDataSource
block|{
specifier|private
name|ConnectionSettings
name|connectionSettings
decl_stmt|;
comment|/**      * The constructor requires all the parameters in order to establish a connection with the physical DB.      * Those information regarding the connection with the DB are passed to the constructor in the      * {@link ConnectionSettings}.      *       * @param connectionSettings      *            {@link ConnectionSettings}      */
specifier|public
name|RDB
parameter_list|(
name|ConnectionSettings
name|connectionSettings
parameter_list|)
block|{
name|String
name|dbId
init|=
name|connectionSettings
operator|.
name|getUrl
argument_list|()
operator|+
name|connectionSettings
operator|.
name|getServerName
argument_list|()
operator|+
literal|":"
operator|+
name|connectionSettings
operator|.
name|getPortNumber
argument_list|()
operator|+
literal|"/"
operator|+
name|connectionSettings
operator|.
name|getDatabaseName
argument_list|()
decl_stmt|;
name|id
operator|=
name|URIGenerator
operator|.
name|createID
argument_list|(
literal|"urn:datasource-"
argument_list|,
name|dbId
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|connectionSettings
operator|=
name|connectionSettings
expr_stmt|;
block|}
comment|/**      * Return the physical data source. In this specific case, as the data source is an RDB, a      * {@link ConnectionSettings} object containing the information in order to establish a connection with      * the DB via JDBC is returned      *       * @return the information for establishing the connection with the DB      */
annotation|@
name|Override
specifier|public
name|Object
name|getDataSource
parameter_list|()
block|{
return|return
name|connectionSettings
return|;
block|}
comment|/**      * Return the {@code int} representing the data source type in Semion. In the case of relationa databases      * the value returned is {@link ReengineerType.RDB}, namely 0.      *       * @return the value assigned to the relational databases by Semion      */
annotation|@
name|Override
specifier|public
name|int
name|getDataSourceType
parameter_list|()
block|{
return|return
name|ReengineerType
operator|.
name|RDB
return|;
block|}
block|}
end_class

end_unit

