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
name|Representation
operator|.
name|RepresentationTypeEnum
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
comment|/**      * Creates a Text instance without an language      * @param value The value if the text. Implementations might support special      * support for specific classes. As an default the {@link Object#toString()}      * method is used to get the lexical form of the text from the parsed value      * and<code>null</code> should be used as language.      * @return the Text instance for the parsed object      */
name|Text
name|createText
parameter_list|(
name|Object
name|value
parameter_list|)
function_decl|;
comment|/**      * Creates a Text instance for a language      * @param text the text      * @param language the language or<code>null</code>.      * @return the Text instance      */
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
comment|/**      * Creates a reference instance for the parsed value. Implementations might      * support special support for specific classes. As an default the      * {@link Object#toString()} method is used to get the unicode representation      * of the reference.      * @param value the unicode representation of the reference      * @return the reference instance      */
name|Reference
name|createReference
parameter_list|(
name|Object
name|value
parameter_list|)
function_decl|;
comment|/**      * Creates an empty representation instance of with the type {@link RepresentationTypeEnum#Entity}      * for the parsed ID      * @param id The id of the representation      * @return the representation      */
name|Representation
name|createRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Creates an empty representation instance      * @param id The id      * @param type The type. If<code>null</code> the type is set to {@link RepresentationTypeEnum#Entity}      * @return the representation      */
name|Representation
name|createRepresentation
parameter_list|(
name|String
name|id
parameter_list|,
name|RepresentationTypeEnum
name|type
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

