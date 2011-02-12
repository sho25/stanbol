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
name|mapping
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|Entityhub
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|EntityhubConfiguration
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|ConfiguredSite
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|ReferencedSite
import|;
end_import

begin_comment
comment|/**  * Intended to define the configuration of the fieldMapper.  *  * @author Rupert Westenthaler  * @deprecated unsure - Currently the functionality of this service is part of  * the {@link EntityhubConfiguration} and the {@link ConfiguredSite} interfaces.   * Access Methods for the {@link FieldMapper} are defined by the   * {@link Entityhub} and the {@link ReferencedSite} interfaces  */
end_comment

begin_interface
annotation|@
name|Deprecated
specifier|public
interface|interface
name|FieldMapperConfig
block|{
comment|/**      * The property used to configure the default mappings used by all      * {@link ReferencedSite} instances active within the Entityhub      */
name|String
name|DEFAULT_MAPPINGS
init|=
literal|"org.apache.stanbol.entityhub.mapping.default"
decl_stmt|;
comment|/**      * The Property used to configure mappings that are only used for      * representation of a specific Site.      */
name|String
name|SITE_MAPPINGS
init|=
literal|"org.apache.stanbol.entityhub.mapping.site"
decl_stmt|;
block|}
end_interface

end_unit

