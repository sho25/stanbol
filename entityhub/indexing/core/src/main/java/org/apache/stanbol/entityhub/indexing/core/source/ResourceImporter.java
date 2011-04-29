begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|source
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

begin_comment
comment|/**  * The processor used by the resource loader to load registered resources  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ResourceImporter
block|{
comment|/**      * Processes an resource and returns the new state for that resource      * @param is the stream to read the resource from      * @param resourceName the name of the resource      * @return the State of the resource after the processing      * @throws IOException On any error while reading the resource. Throwing      * an IOException will set the state or the resource to      * {@link ResourceState#ERROR}      */
name|ResourceState
name|importResource
parameter_list|(
name|InputStream
name|is
parameter_list|,
name|String
name|resourceName
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

