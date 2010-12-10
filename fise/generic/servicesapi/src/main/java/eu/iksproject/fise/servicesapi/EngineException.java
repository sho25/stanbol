begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
package|;
end_package

begin_comment
comment|/**  * Base exception thrown by EnhancementEngine implementations when they fail to  * process the provided content item.  *<p>  * If the failure is imputable to a malformed input in the  * {@link ContentItem#getStream()} or {@link ContentItem#getMetadata()} one  * should throw the subclass {@link InvalidContentException} instead.  *  * @author ogrisel  */
end_comment

begin_class
specifier|public
class|class
name|EngineException
extends|extends
name|Exception
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|EngineException
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
specifier|public
name|EngineException
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
specifier|public
name|EngineException
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
specifier|public
name|EngineException
parameter_list|(
name|EnhancementEngine
name|ee
parameter_list|,
name|ContentItem
name|ci
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"'%s' failed to process content item '%s' with type '%s': %s"
argument_list|,
name|ee
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|ci
operator|.
name|getId
argument_list|()
argument_list|,
name|ci
operator|.
name|getMimeType
argument_list|()
argument_list|,
name|cause
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

