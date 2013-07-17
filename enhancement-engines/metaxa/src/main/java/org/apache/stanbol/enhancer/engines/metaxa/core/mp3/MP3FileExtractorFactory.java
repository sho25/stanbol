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
name|engines
operator|.
name|metaxa
operator|.
name|core
operator|.
name|mp3
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|FileExtractor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|FileExtractorFactory
import|;
end_import

begin_comment
comment|/**  * A FileExtractorFactory implementation for the MP3FileExtractors  */
end_comment

begin_class
specifier|public
class|class
name|MP3FileExtractorFactory
implements|implements
name|FileExtractorFactory
block|{
specifier|private
specifier|static
specifier|final
name|Set
name|MIME_TYPES
init|=
name|Collections
operator|.
name|singleton
argument_list|(
literal|"audio/mpeg"
argument_list|)
decl_stmt|;
comment|/**      * @see FileExtractorFactory#get()      */
specifier|public
name|FileExtractor
name|get
parameter_list|()
block|{
return|return
operator|new
name|MP3FileExtractor
argument_list|()
return|;
block|}
comment|/**      * @see FileExtractorFactory#getSupportedMimeTypes()      */
specifier|public
name|Set
name|getSupportedMimeTypes
parameter_list|()
block|{
return|return
name|MIME_TYPES
return|;
block|}
block|}
end_class

end_unit

