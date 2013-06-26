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
name|EntityhubException
import|;
end_import

begin_comment
comment|/**  * Replaces the ReferencedSiteException in Entityhub versions later than  * 0.10.0-incubationg. See   *<a href="https://issues.apache.org/jira/browse/STANBOL-674">STANBOL-674</a>  * for details.<p>  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|SiteException
extends|extends
name|EntityhubException
block|{
comment|/**      * Default serial version id      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|SiteException
parameter_list|(
name|String
name|reason
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|reason
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SiteException
parameter_list|(
name|String
name|reason
parameter_list|)
block|{
name|super
argument_list|(
name|reason
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

