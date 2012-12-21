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
name|entitylinking
package|;
end_package

begin_comment
comment|/**  * Interface used by the {@link EntityhubLinkingEngine} to tokenize labels of  * Entities suggested by the EntitySearcher  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|LabelTokenizer
block|{
comment|/**      * Key used to configure the languages supported for tokenizing labels.      * If not present the assumption is that the tokenizer supports all languages.      */
name|String
name|SUPPORTED_LANUAGES
init|=
literal|"enhancer.engines.entitylinking.labeltokenizer.languages"
decl_stmt|;
comment|/**      * Tokenizes the parsed label in the parsed language      * @param label the label      * @param language the language of the lable or<code>null</code> if      * not known      * @return the tokenized label      */
name|String
index|[]
name|tokenize
parameter_list|(
name|String
name|label
parameter_list|,
name|String
name|language
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

