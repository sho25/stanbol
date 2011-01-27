begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (c) 2010, IKS Project All rights reserved.  Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.  Neither the name of the IKS Project nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|ContentItemHelper
import|;
end_import

begin_comment
comment|/**  * Store and retrieve ContentItem instances.  *  * Incomplete CRUD for now, we don't need it for our  * initial use cases.  */
end_comment

begin_interface
specifier|public
interface|interface
name|Store
block|{
comment|/**      * Creates a {@link ContentItem} item based on supplied data,      * using an implementation that suits this store.      *<p>      * Calling this method creates an empty data transfer object in      * memory suitable for later saving using the {@link Store#put(ContentItem)} method.      * The Store state is unchanged by the call to the      * {@link #create(String, byte[], String)} method.      *      * @param id The value to use {@link ContentItem#getId}. If<code>null</code>      * is parsed as id, an id need to be computed based on the parsed content (      * e.g. calculating the stream digest (see also      * {@link ContentItemHelper#streamDigest(java.io.InputStream, java.io.OutputStream, String)})      * @param content the binary content      * @param contentType The Mime-Type of the binary data      * @return the {@link ContentItem} that was created      */
name|ContentItem
name|create
parameter_list|(
name|String
name|id
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|contentType
parameter_list|)
function_decl|;
comment|/** Store supplied {@link ContentItem} and return its id, which      *  is assigned by the store if not defined yet.      *      *  If the {@link ContentItem} already exists, it is overwritten.      */
name|String
name|put
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
function_decl|;
comment|/** Get a {@link ContentItem} by id, null if non-existing */
name|ContentItem
name|get
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Get the graph of triples of enhancements related to the content item from      * this store      */
name|MGraph
name|getEnhancementGraph
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

