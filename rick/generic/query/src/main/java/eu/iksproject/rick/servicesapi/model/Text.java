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

begin_comment
comment|/**  * Defines a natural language text in a given language.  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|Text
block|{
comment|/**      * Getter for the text (not<code>null</code>)      * @return the text      */
name|String
name|getText
parameter_list|()
function_decl|;
comment|/**      * Getter for the language.<code>null</code> indicates, that the text      * is not specific to a language (e.g. the name of a person)      * @return the language      */
name|String
name|getLanguage
parameter_list|()
function_decl|;
comment|/**      * The text without language information - this is the same as returned      * by {@link #getText()}      * @return the text      */
name|String
name|toString
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

