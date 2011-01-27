begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (c) 2010, IKS Project All rights reserved.  Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.  Neither the name of the IKS Project nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  */
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
name|servicesapi
package|;
end_package

begin_comment
comment|/**  * Interface to internal or external semantic enhancement engines. There will  * usually be several of those, that the EnhancementJobManager uses to enhance  * content items.  */
end_comment

begin_interface
specifier|public
interface|interface
name|EnhancementEngine
block|{
comment|/**      * Return value for {@link #canEnhance}, meaning this engine cannot enhance      * supplied {@link ContentItem}      */
name|int
name|CANNOT_ENHANCE
init|=
literal|0
decl_stmt|;
comment|/**      * Return value for {@link #canEnhance}, meaning this engine can enhance      * supplied {@link ContentItem}, and suggests enhancing it synchronously      * instead of queuing a request for enhancement.      */
name|int
name|ENHANCE_SYNCHRONOUS
init|=
literal|1
decl_stmt|;
comment|/**      * Return value for {@link #canEnhance}, meaning this engine can enhance      * supplied {@link ContentItem}, and suggests queuing a request for      * enhancement instead of enhancing it synchronously.      */
name|int
name|ENHANCE_ASYNC
init|=
literal|1
decl_stmt|;
comment|/**      * Indicate if this engine can enhance supplied ContentItem, and if it      * suggests enhancing it synchronously or asynchronously. The      * {@link EnhancementJobManager} can force sync/async mode if desired, it is      * just a suggestion from the engine.      *      * @throws EngineException if the introspecting process of the content item      *             fails      */
name|int
name|canEnhance
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
function_decl|;
comment|/**      * Compute enhancements for supplied ContentItem. The results of the process      * are expected to be stored in the metadata of the content item.      *      * The client (usually an {@link EnhancementJobManager}) should take care of      * persistent storage of the enhanced {@link ContentItem}.      *      * @throws EngineException if the underlying process failed to work as      *             expected      */
name|void
name|computeEnhancements
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
throws|throws
name|EngineException
function_decl|;
block|}
end_interface

end_unit

