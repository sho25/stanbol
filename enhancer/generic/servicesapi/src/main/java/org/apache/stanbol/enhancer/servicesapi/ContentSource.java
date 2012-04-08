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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * The content source representing the data and optionally the media type  * and file name. This interface is only used to parse the content  * when creating a {@link ContentItem}. To obtain the content of a  * ContentItem the {@link Blob} interface is used<p>  * NOTE that {@link #getStream()} can typically only be called a single time.  * Multiple calls will throw an {@link IllegalArgumentException}.  * @see Blob  */
end_comment

begin_interface
specifier|public
interface|interface
name|ContentSource
block|{
comment|/**      * Getter for the data as stream. This method might only work a single time so      * multiple calls might result in {@link IllegalStateException}s.<p>      * {@link ContentItem}/{@link Blob} implementations that keep the      * content in memory should preferable use {@link #getData()} to      * obtain the content from the source.      * @return the data.      * @throws IllegalStateException if the stream is already consumed and      * can not be re-created.      * @see #getStream()      */
name|InputStream
name|getStream
parameter_list|()
function_decl|;
comment|/**      * Getter for the data as byte array.<p>      * NOTE that his method will load      * the content in-memory. However using this method instead of      * {@link #getStream()} might preserve holding multiple in-memory version      * of the same content in cases where both the {@link ContentSource}      * and the {@link ContentItem} are internally using an byte array to      * hold the content.<p>       * As a rule of thumb this method should only be      * used by in-memory {@link ContentItem}/{@link Blob} implementations.      * @return the content as byte array.      * @throws IOException On any error while reading the data from the source.      * @throws IllegalStateException If the {@link #getStream()} was already      * consumed when calling this method.      * @see #getStream()      */
name|byte
index|[]
name|getData
parameter_list|()
throws|throws
name|IOException
function_decl|;
comment|/**      * An valid media type as defined by       *<a href="http://tools.ietf.org/html/rfc2046">RFC2046</a>.      * "application/octet-stream" if unknown      * @return The media type or<code>null</code> if unknown      */
name|String
name|getMediaType
parameter_list|()
function_decl|;
comment|/**      * The original file name.      * @return the name of the file or<code>null</code> if not known      */
name|String
name|getFileName
parameter_list|()
function_decl|;
comment|/**      * Getter for additional header information about the ContentSource. The      * returned Map MUST NOT be<code>null</code> and MAY be read-only.      * @return additional header information.      */
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|getHeaders
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

