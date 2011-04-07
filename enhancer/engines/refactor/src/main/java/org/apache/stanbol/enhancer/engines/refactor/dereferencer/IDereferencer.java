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
name|engines
operator|.
name|refactor
operator|.
name|dereferencer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
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
comment|/**  *   * @author andrea.nuzzolese  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|IDereferencer
block|{
comment|/** 	 *  	 * The resolve method dereferences location and returns input streams. 	 * Locations can be local to the file system or remote URIs. 	 *  	 * @param location 	 * @return {@link InputStream} if the location is resolved. Otherwise a {@link FileNotFoundException} is thrown. 	 * @throws FileNotFoundException 	 */
name|InputStream
name|resolve
parameter_list|(
name|String
name|location
parameter_list|)
throws|throws
name|FileNotFoundException
function_decl|;
block|}
end_interface

end_unit

