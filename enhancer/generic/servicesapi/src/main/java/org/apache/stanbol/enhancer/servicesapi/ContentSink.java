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
name|servicesapi
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_comment
comment|/**  * A {@link ContentSink} allows to stream content to a {@link Blob}. This  * allows to "stream" content that is created by the Stanbol Enhancer (e.g.  * the plain text extracted from an PDF) directly to the Blob and therefore  * greatly reduces the memory footprint if dealing with very large   * content.<p>  *<b>IMPORTANT NOTE:</b> Do not parse the {@link Blob} of a {@link ContentSink}  * to any other components until all the data are written to the   * {@link OutputStream}, because this may cause that other components to read  * partial data when calling {@link Blob#getStream()}!<br>  * This feature is intended to reduce the memory footprint and not to support  * concurrent writing and reading of data as supported by pipes. However  * if you can come-up with a nice use case that would require this ability  * let us know on the stanbol-dev mailing list or by creating an   *<a href=https://issues.apache.org/jira/browse/STANBOL>issue</a>.   */
end_comment

begin_interface
specifier|public
interface|interface
name|ContentSink
block|{
comment|/**      * The output stream - sink - for the content. Multiple calls to       * this method will return the same {@link OutputStream}.<p>      * Users need to ensure that the provided stream is correctly closed      * after all the content was written to it.      * @return the output stream used to stream the content to the      * {@link Blob}      */
name|OutputStream
name|getOutputStream
parameter_list|()
function_decl|;
comment|/**      * The - initially empty - Blob. if this Blob is shared with other       * components (e.g. added to an {@link ContentItem}) before all data are       * written to the sink, than other engines will be able to access it      * even while data are still added to blob.      * @return The blob.      */
name|Blob
name|getBlob
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

