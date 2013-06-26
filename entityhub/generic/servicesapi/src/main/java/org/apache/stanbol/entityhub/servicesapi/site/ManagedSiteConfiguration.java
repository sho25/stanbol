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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
package|;
end_package

begin_interface
specifier|public
interface|interface
name|ManagedSiteConfiguration
extends|extends
name|SiteConfiguration
block|{
comment|/**      * The key used for the configuration of the id for the yard used as to      * manage the Entity data.      */
name|String
name|YARD_ID
init|=
literal|"org.apache.stanbol.entityhub.site.yardId"
decl_stmt|;
comment|/**      * The name of the Yard used by the {@link ManagedSite} to store the      * Entity data. A {@link ManagedSite} will only be available if the      * {@link org.apache.stanbol.entityhub.servicesapi.yard.Yard}       * with this ID is also available os OSGI service.      * @return the ID of the Yard used to store entity data of this managed site.      */
name|String
name|getYardId
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

