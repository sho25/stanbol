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

begin_comment
comment|/**  * A reference to the content. This allows to {@link #dereference()} the  * content when it is used.  */
end_comment

begin_interface
specifier|public
interface|interface
name|ContentReference
block|{
comment|/**      * The String representation of this reference.      * @return the reference string      */
name|String
name|getReference
parameter_list|()
function_decl|;
comment|/**      * Dereferences this content reference      * @return the referenced {@link ContentSource}      * @throws IOException on any error while dereferencing the source      */
name|ContentSource
name|dereference
parameter_list|()
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

