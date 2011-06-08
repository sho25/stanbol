begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (c) 2006 - 2008 Aduna and Deutsches Forschungszentrum fuer Kuenstliche Intelligenz DFKI GmbH.  * All rights reserved.  *   * Licensed under the Aperture BSD-style license.  */
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

