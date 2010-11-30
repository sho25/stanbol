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
name|model
package|;
end_package

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Sign
operator|.
name|SignTypeEnum
import|;
end_import

begin_comment
comment|/**  * FactoryInterface for {@link Text} and {@link Reference} instances  * TODO: Still not sure if we need that  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|ValueFactory
block|{
comment|/** 	 * Creates a Text instance without an language 	 * @param value The value if the text. Implementations might support special 	 * support for specific classes. As an default the {@link Object#toString()} 	 * method is used to get the lexical form of the text from the parsed value 	 * and<code>null</code> should be used as language.  	 * @return the Text instance for the parsed object 	 * @throws UnsupportedTypeException if the type of the parsed object is not 	 * can not be used to create Text instances 	 */
specifier|public
name|Text
name|createText
parameter_list|(
name|Object
name|value
parameter_list|)
throws|throws
name|UnsupportedTypeException
function_decl|;
comment|/** 	 * Creates a Text instance for a language 	 * @param text the text 	 * @param language the language or<code>null</code>. 	 * @return the Text instance 	 */
specifier|public
name|Text
name|createText
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|language
parameter_list|)
function_decl|;
comment|/** 	 * Creates a reference instance for the parsed value. Implementations might  	 * support special support for specific classes. As an default the  	 * {@link Object#toString()} method is used to get the unicode representation 	 * of the reference. 	 * @param value the unicode representation of the reference 	 * @return the reference instance 	 * @throws UnsupportedTypeException if the type of the parsed object can  	 * not be converted to a Reference. 	 */
specifier|public
name|Reference
name|createReference
parameter_list|(
name|Object
name|value
parameter_list|)
throws|throws
name|UnsupportedTypeException
function_decl|;
comment|/** 	 * Creates an empty representation instance of with the type {@link SignTypeEnum#Sign} 	 * for the parsed ID 	 * @param id The id of the representation 	 * @return the representation 	 */
specifier|public
name|Representation
name|createRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|//	/**
comment|//	 * Creates a value of the parsed data type for the parsed object
comment|//	 * @param dataTypeUri the data type of the created object
comment|//	 * @param value the source object
comment|//	 * @return the value or<code>null</code> if the parsed value can not be
comment|//	 * converted.
comment|//	 * @throws UnsupportedTypeException if the type of the parsed object is
comment|//	 * not compatible to the requested dataType
comment|//	 * @throws UnsupportedDataTypeException Implementation need to ensure support
comment|//	 * for data types specified in {@link DataTypeEnum}. However implementations
comment|//	 * may support additional data types. When using such types one needs to
comment|//	 * check for this Exception.
comment|//	 */
comment|//	public Object createValue(String dataTypeUri,Object value) throws UnsupportedTypeException, UnsupportedDataTypeException;
block|}
end_interface

end_unit

