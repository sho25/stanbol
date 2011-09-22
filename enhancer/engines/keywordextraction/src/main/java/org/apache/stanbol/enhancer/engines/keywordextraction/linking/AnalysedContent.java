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
name|enhancer
operator|.
name|engines
operator|.
name|keywordextraction
operator|.
name|linking
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|opennlp
operator|.
name|tools
operator|.
name|util
operator|.
name|Span
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
name|commons
operator|.
name|opennlp
operator|.
name|TextAnalyzer
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
name|commons
operator|.
name|opennlp
operator|.
name|TextAnalyzer
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
name|commons
operator|.
name|opennlp
operator|.
name|TextAnalyzer
operator|.
name|AnalysedText
operator|.
name|Token
import|;
end_import

begin_comment
comment|/**  * Represents the already with NLP tools analysed content to be linked with  * Entities of an {@link EntitySearcher}.<p>  * Note that for the linking process it is only required that the text is  * tokenized. All other features (sentence detection, POS tags and Chunks) are  * optional but do improve the performance and to an smaller amount also the  * results of the linking process.<p>  * TODO:<ul>  *<li> Find a better Name  *<li> The API is not optimal. In general the {@link TextAnalyzer} and the  * {@link AnalysedContent} interface do not play well together :(  *</ul>  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|AnalysedContent
block|{
comment|/**      * Getter for the Iterator over the analysed sentences. This Method      * is expected to return always the same Iterator instance.      * @return the iterator over the analysed sentences      */
specifier|public
name|Iterator
argument_list|<
name|AnalysedText
argument_list|>
name|getAnalysedText
parameter_list|()
function_decl|;
comment|/**      * Called to check if a {@link Token} should be used to search for      * Concepts within the Taxonomy based on the POS tag of the Token.      * @param posTag the POS tag to check      * @return<code>true</code> if Tokens with this POS tag should be      * included in searches. Otherwise<code>false</code>.  If this information       * is not available (e.g. no set of Tags that need to be processed is defined)       * this Method MUST return<code>null</code>      */
specifier|public
name|Boolean
name|processPOS
parameter_list|(
name|String
name|posTag
parameter_list|)
function_decl|;
comment|/**      * Called to check if a chunk should be used to search for Concepts.      * @param chunkTag the tag (type) of the chunk      * @return<code>true</code> if chunks with this tag (type) should be      * processed (used to search for matches of concepts) and<code>false</code>      * if not. If this information is not available (e.g. no set of Tags that      * need to be processed is defined) this Method MUST return<code>null</code>      */
specifier|public
name|Boolean
name|processChunk
parameter_list|(
name|String
name|chunkTag
parameter_list|)
function_decl|;
comment|/**      * Tokenizes the parsed label      * @param label the label to tokenize      * @return the spans of the tokens      */
specifier|public
name|String
index|[]
name|tokenize
parameter_list|(
name|String
name|label
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

