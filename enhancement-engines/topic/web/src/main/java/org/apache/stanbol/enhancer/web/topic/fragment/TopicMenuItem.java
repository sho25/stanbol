begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright 2013 The Apache Software Foundation.  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
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
name|web
operator|.
name|topic
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
comment|/**  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|NavigationLink
operator|.
name|class
argument_list|)
specifier|public
class|class
name|TopicMenuItem
extends|extends
name|NavigationLink
block|{
specifier|private
specifier|static
specifier|final
name|String
name|htmlDescription
init|=
literal|"<strong>Topic Classification"
operator|+
literal|"endpoint</strong> for Apache Stanbol. This allows to manage and train "
operator|+
literal|"topic classification models for the Topic classification engine of the "
operator|+
literal|"Stanbol Enhancer."
decl_stmt|;
specifier|public
name|TopicMenuItem
parameter_list|()
block|{
name|super
argument_list|(
literal|"topic"
argument_list|,
literal|"/topic"
argument_list|,
name|htmlDescription
argument_list|,
literal|15
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

