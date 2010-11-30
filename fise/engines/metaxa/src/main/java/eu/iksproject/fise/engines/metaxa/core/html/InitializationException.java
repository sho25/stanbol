begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|metaxa
operator|.
name|core
operator|.
name|html
package|;
end_package

begin_comment
comment|/**  *<code>InitializationException</code> is thrown when an initialization step  * fails.  *   * @author Joerg Steffen, DFKI  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|InitializationException
extends|extends
name|Exception
block|{
comment|/**      * This creates a new instance of<code>InitializationException</code> with      * null as its detail message. The cause is not initialized.      */
specifier|public
name|InitializationException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * This creates a new instance of<code>InitializationException</code> with      * the given detail message. The cause is not initialized.      *       * @param message      *            a<code>String</code> with the detail message      */
specifier|public
name|InitializationException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/**      * This creates a new instance of<code>InitializationException</code> with      * the specified cause and a detail message of (cause==null ? null :      * cause.toString()) (which typically contains the class and detail message      * of cause).      *       * @param cause      *            a<code>Throwable</code> with the cause of the exception      *            (which is saved for later retrieval by the {@link #getCause()}      *            method). (A<tt>null</tt> value is permitted, and indicates      *            that the cause is nonexistent or unknown.)      */
specifier|public
name|InitializationException
parameter_list|(
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|cause
argument_list|)
expr_stmt|;
block|}
comment|/**      * This creates a new instance of<code>InitializationException</code> with      * the given detail message and the given cause.      *       * @param message      *            a<code>String</code> with the detail message      * @param cause      *            a<code>Throwable</code> with the cause of the exception      *            (which is saved for later retrieval by the {@link #getCause()}      *            method). (A<tt>null</tt> value is permitted, and indicates      *            that the cause is nonexistent or unknown.)      */
specifier|public
name|InitializationException
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

