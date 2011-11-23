begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
package|;
end_package

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
comment|/**  * Callback used in case tracked DataFiles become available/unavailable.  * @author westei  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|DataFileListener
block|{
comment|/**      * Notifies that a requested resource is now available and provides the      * name and optionally the InputStream for that resource.      * @param resourceName the name of the now available resource      * @param is Optionally the InputStream for that resource. If<code>null</code>      * receiver of this notification need to requested the resource with the      * parsed name by the DataFileProvider.      * @return If<code>true</code> the registration for this event is       * automatically removed. Otherwise the registration is kept and can be used       * to track if the resource get unavailable.       */
name|boolean
name|available
parameter_list|(
name|String
name|resourceName
parameter_list|,
name|InputStream
name|is
parameter_list|)
function_decl|;
comment|/**       * Called as soon as a previous available resource is no longer available       * @param resource The name of the unavailable resource       * @return If<code>true</code> the registration for this event is       * automatically removed. Otherwise the component receiving this call needs       * to remove the registration them self if it dose not want to retrieve      * further events (such as if the resource becomes available again)      **/
name|boolean
name|unavailable
parameter_list|(
name|String
name|resource
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

