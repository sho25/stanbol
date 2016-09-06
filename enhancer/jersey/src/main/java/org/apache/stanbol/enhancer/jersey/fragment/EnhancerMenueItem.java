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
name|fragment
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|NavigationLink
import|;
end_import

begin_comment
comment|/**  * The menue item for the Stanbol Enhancer component  */
end_comment

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|value
operator|=
name|NavigationLink
operator|.
name|class
argument_list|)
specifier|public
class|class
name|EnhancerMenueItem
extends|extends
name|NavigationLink
block|{
specifier|private
specifier|static
specifier|final
name|String
name|htmlDescription
init|=
literal|"This is a<strong>stateless interface</strong> to allow clients to submit"
operator|+
literal|"content to<strong>analyze</strong> by the<code>EnhancementEngine</code>s"
operator|+
literal|"and get the resulting<strong>RDF enhancements</strong> at once without"
operator|+
literal|"storing anything on the server-side."
decl_stmt|;
specifier|public
name|EnhancerMenueItem
parameter_list|()
block|{
name|super
argument_list|(
literal|"enhancer"
argument_list|,
literal|"/enhancer"
argument_list|,
name|htmlDescription
argument_list|,
literal|10
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

