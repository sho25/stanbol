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
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Section
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Sentence
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|Token
import|;
end_import

begin_comment
comment|/**  * Provides callbacks form the {@link EntityLinker} about the currently  * processed Tokens.   * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|LinkingStateAware
block|{
comment|/**      * Callback notifying that the {@link EntityLinker} has completed the      * linking for the parsed {@link Section} (as {@link Sentence} in case      * sentence annotations are present in the {@link AnalysedText}).      * @param sentence the completed section      */
name|void
name|endSection
parameter_list|(
name|Section
name|sentence
parameter_list|)
function_decl|;
comment|/**      * Callback notifying that the {@link EntityLinker} has started to link a      * new section of the text      * @param sentence the completed section      */
name|void
name|startSection
parameter_list|(
name|Section
name|sentence
parameter_list|)
function_decl|;
comment|/**      * The next {@link Token} to be processed by the {@link EntityLinker}      * @param token the token that will be processed next      */
name|void
name|startToken
parameter_list|(
name|Token
name|token
parameter_list|)
function_decl|;
comment|/**      * The next {@link Token} to be processed by the {@link EntityLinker}      * @param token the token that will be processed next      */
name|void
name|endToken
parameter_list|(
name|Token
name|token
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

