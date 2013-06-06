begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (c) 2011 Salzburg Research.  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|template
operator|.
name|model
operator|.
name|freemarker
package|;
end_package

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|TemplateModel
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Stack
import|;
end_import

begin_comment
comment|/**  * Add file description here!  *<p/>  * Author: Sebastian Schaffert  */
end_comment

begin_class
specifier|public
class|class
name|TemplateStackModel
implements|implements
name|TemplateModel
block|{
specifier|private
name|Stack
argument_list|<
name|TemplateModel
argument_list|>
name|stack
decl_stmt|;
specifier|public
name|TemplateStackModel
parameter_list|()
block|{
name|stack
operator|=
operator|new
name|Stack
argument_list|<
name|TemplateModel
argument_list|>
argument_list|()
expr_stmt|;
block|}
specifier|public
name|TemplateModel
name|push
parameter_list|(
name|TemplateModel
name|item
parameter_list|)
block|{
return|return
name|stack
operator|.
name|push
argument_list|(
name|item
argument_list|)
return|;
block|}
specifier|public
name|TemplateModel
name|pop
parameter_list|()
block|{
return|return
name|stack
operator|.
name|pop
argument_list|()
return|;
block|}
specifier|public
name|TemplateModel
name|peek
parameter_list|()
block|{
return|return
name|stack
operator|.
name|peek
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|empty
parameter_list|()
block|{
return|return
name|stack
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit

