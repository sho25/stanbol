begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|query
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_comment
comment|/**  * RuntimeException that indicated, that the tyoe of the parsed {@link Query}  * is not supported by the {@link QueryService}  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|UnsupportedQueryTypeException
extends|extends
name|IllegalArgumentException
block|{
comment|/**      * Default serial version id      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|UnsupportedQueryTypeException
parameter_list|(
name|String
name|unsupportedQueryType
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|supportedTypes
parameter_list|)
block|{
name|this
argument_list|(
name|unsupportedQueryType
argument_list|,
name|supportedTypes
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|UnsupportedQueryTypeException
parameter_list|(
name|String
name|unsupportedQueryType
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|supportedTypes
parameter_list|,
name|String
name|serviceName
parameter_list|)
block|{
name|super
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Queries of type {} are not supported by {} (supportedTypes: {}"
argument_list|,
name|unsupportedQueryType
argument_list|,
operator|(
name|serviceName
operator|!=
literal|null
condition|?
name|serviceName
else|:
literal|"this query service"
operator|)
argument_list|,
name|supportedTypes
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

