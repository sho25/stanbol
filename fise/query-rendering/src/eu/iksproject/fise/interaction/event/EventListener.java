begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|interaction
operator|.
name|event
package|;
end_package

begin_comment
comment|/*  * Copyright 2010  * German Research Center for Artificial Intelligence (DFKI)  * Department of Intelligent User Interfaces  * Germany  *  *     http://www.dfki.de/web/forschung/iui  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  *  * Authors:  *     Sebastian Germesin  *     Massimo Romanelli  *     Tilman Becker  */
end_comment

begin_interface
specifier|public
interface|interface
name|EventListener
block|{
name|void
name|eventOccurred
parameter_list|(
name|Event
name|e
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

