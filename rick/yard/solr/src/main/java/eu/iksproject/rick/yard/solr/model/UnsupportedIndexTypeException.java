begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|yard
operator|.
name|solr
operator|.
name|model
package|;
end_package

begin_comment
comment|/**  * Thrown when the index value type is not compatible with the converter  *  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|UnsupportedIndexTypeException
extends|extends
name|RuntimeException
block|{
comment|/**      * default serial version UID      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/**      * Constructs the exception to be thrown when a an IndexType is not supported      * by the current configuration of the {@link IndexValueFactory}      *      * @param indexType the unsupported<code>IndexType</code>      */
specifier|public
name|UnsupportedIndexTypeException
parameter_list|(
name|IndexDataType
name|indexType
parameter_list|)
block|{
name|super
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"No Converter for IndexType %s registered!"
argument_list|,
name|indexType
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs the exception to be thrown if a converter does not support the      * {@link IndexDataType} of the parsed {@link IndexValue}.      * @param converter the converter (implement the {@link TypeConverter#toString()} method!)      * @param type the unsupported {@link IndexDataType}      */
specifier|public
name|UnsupportedIndexTypeException
parameter_list|(
name|TypeConverter
argument_list|<
name|?
argument_list|>
name|converter
parameter_list|,
name|IndexDataType
name|type
parameter_list|)
block|{
name|super
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s does not support the IndexType %s!"
argument_list|,
name|converter
argument_list|,
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

