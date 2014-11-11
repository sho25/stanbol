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
name|lucenefstlinking
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
name|engines
operator|.
name|entitylinking
operator|.
name|config
operator|.
name|TextProcessingConfig
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
name|ner
operator|.
name|NerTag
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
name|pos
operator|.
name|LexicalCategory
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
name|pos
operator|.
name|Pos
import|;
end_import

begin_enum
specifier|public
enum|enum
name|LinkingModeEnum
block|{
comment|/**      * Links every token in the parsed text      */
name|PLAIN
block|,
comment|/**      * Links only Linkable Tokens (typically all {@link LexicalCategory#Noun}s      * or even only {@link Pos#ProperNoun} - depending on the       * {@link TextProcessingConfig}       */
name|LINKABLE_TOKEN
comment|//,
comment|//    /**
comment|//     * Only {@link NerTag}s are linked with the vocabualry
comment|//     */
comment|//    NER
block|}
end_enum

end_unit

