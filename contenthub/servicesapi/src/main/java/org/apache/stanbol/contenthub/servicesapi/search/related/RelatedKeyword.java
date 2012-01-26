begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|related
package|;
end_package

begin_interface
specifier|public
interface|interface
name|RelatedKeyword
block|{
name|String
name|getKeyword
parameter_list|()
function_decl|;
name|double
name|getScore
parameter_list|()
function_decl|;
name|String
name|getSource
parameter_list|()
function_decl|;
comment|/*      * To enumerate the source for a related keyword       */
specifier|public
enum|enum
name|Source
block|{
name|UNKNOWN
argument_list|(
literal|"Unknown"
argument_list|)
block|,
name|WORDNET
argument_list|(
literal|"Wordnet"
argument_list|)
block|,
name|ONTOLOGY
argument_list|(
literal|"Ontology"
argument_list|)
block|;
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
specifier|private
name|Source
parameter_list|(
name|String
name|n
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|n
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|toString
parameter_list|()
block|{
return|return
name|this
operator|.
name|name
return|;
block|}
block|}
block|}
end_interface

end_unit

