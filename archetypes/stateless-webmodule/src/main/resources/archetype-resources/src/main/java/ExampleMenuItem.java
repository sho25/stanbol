begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_expr_stmt
unit|#
name|set
argument_list|(
name|$symbol_pound
operator|=
literal|'#'
argument_list|)
expr|#
name|set
argument_list|(
name|$symbol_dollar
operator|=
literal|'$'
argument_list|)
expr|#
name|set
argument_list|(
name|$symbol_escape
operator|=
literal|'\' )
package|package
name|$
block|{
package|package
block|}
end_expr_stmt

begin_empty_stmt
empty_stmt|;
end_empty_stmt

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

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|NavigationLink
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ExampleMenuItem
extends|extends
name|NavigationLink
block|{
specifier|public
name|ExampleMenuItem
parameter_list|()
block|{
name|super
argument_list|(
literal|"${artifactId}/"
argument_list|,
literal|"Example: ${artifactId}"
argument_list|,
literal|"An stateless service example"
argument_list|,
literal|300
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

