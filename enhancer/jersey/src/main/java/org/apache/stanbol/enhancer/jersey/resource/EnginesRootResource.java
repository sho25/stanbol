begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|jersey
operator|.
name|resource
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Context
import|;
end_import

begin_comment
comment|/**  * This provides backward compatibility for the "/engines" endpoint that was  * used to enhance content parsed to the Stanbol Enhancer before the   * implementation of the "/enhancer" RESTful interface defined by  * STANBOL-431.<p>  * This provides the same interface as for "/enhancer" by sub-classing the  * {@link EnhancerRootResource}.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/engines"
argument_list|)
specifier|public
specifier|final
class|class
name|EnginesRootResource
extends|extends
name|AbstractEnhancerUiResource
block|{
specifier|public
name|EnginesRootResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|super
argument_list|(
literal|null
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

