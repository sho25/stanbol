begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/** Copyright (c) 2010, IKS Project All rights reserved.  Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.  Neither the name of the IKS Project nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  */
end_comment

begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * Accept requests for enhancing ContentItems, and processes them either  * synchronously or asynchronously (as decided by the enhancement engines or by  * configuration).  *  * The progress of the enhancement process should be made accessible in the  * ContentItem's metadata.  */
end_comment

begin_interface
specifier|public
interface|interface
name|EnhancementJobManager
block|{
comment|/**      * Create relevant asynchronous requests or enhance content immediately. The      * result is not persisted right now. The caller is responsible to call the      * {@link Store#put(ContentItem)} afterwards in case persistence is      * required.      *      * TODO: define the expected semantics if asynchronous enhancements were to      * get implemented.      *      * @throws EngineException if the enhancement process failed      */
name|void
name|enhanceContent
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
function_decl|;
comment|/**      * Return the unmodifiable list of active registered engine instance that      * can be used by the manager.      */
name|List
argument_list|<
name|EnhancementEngine
argument_list|>
name|getActiveEngines
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

