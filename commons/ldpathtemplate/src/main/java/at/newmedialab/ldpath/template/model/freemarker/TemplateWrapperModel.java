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
name|AdapterTemplateModel
import|;
end_import

begin_comment
comment|/**  * Add file description here!  *<p/>  * Author: Sebastian Schaffert  */
end_comment

begin_class
specifier|public
class|class
name|TemplateWrapperModel
parameter_list|<
name|T
parameter_list|>
implements|implements
name|AdapterTemplateModel
block|{
specifier|private
name|T
name|object
decl_stmt|;
specifier|public
name|TemplateWrapperModel
parameter_list|(
name|T
name|object
parameter_list|)
block|{
name|this
operator|.
name|object
operator|=
name|object
expr_stmt|;
block|}
comment|/**      * Retrieves the underlying object, or some other object semantically      * equivalent to its value narrowed by the class hint.      *      * @param hint the desired class of the returned value. An implementation      *             should make reasonable effort to retrieve an object of the requested      *             class, but if that is impossible, it must at least return the underlying      *             object as-is. As a minimal requirement, an implementation must always      *             return the exact underlying object when      *<tt>hint.isInstance(underlyingObject) == true</tt> holds. When called      *             with<tt>java.lang.Object.class</tt>, it should return a generic Java      *             object (i.e. if the model is wrapping a scripting lanugage object that is      *             further wrapping a Java object, the deepest underlying Java object should      *             be returned).      * @return the underlying object, or its value accommodated for the hint      *         class.      */
annotation|@
name|Override
specifier|public
name|T
name|getAdaptedObject
parameter_list|(
name|Class
name|hint
parameter_list|)
block|{
return|return
name|object
return|;
block|}
block|}
end_class

end_unit

