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
name|nlp
package|;
end_package

begin_comment
comment|/**  * Defines NLP processing Roles engines can take. The idea is to use those roles  * to ease the configuration or NLP enhancement chains. Basically users would  * just configure what NLP features the want to use and the NLP chain would  * choose the fitting Engines based on their "service.ranking" values.  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|NlpProcessingRole
block|{
name|LanguageDetection
block|,
name|SentenceDetection
block|,
name|Tokenizing
block|,
name|PartOfSpeachTagging
block|,
name|Chunking
block|,
name|SentimentTagging
block|,
name|Lemmatize
block|}
end_enum

end_unit

