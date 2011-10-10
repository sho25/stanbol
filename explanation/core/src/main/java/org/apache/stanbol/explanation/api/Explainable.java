begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
operator|.
name|api
package|;
end_package

begin_comment
comment|/**  * An umbrella and wrapper type for any object that may be backed up by a user-sensitive explanation.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Explainable
parameter_list|<
name|T
parameter_list|>
block|{
specifier|public
specifier|static
specifier|final
name|int
name|FACT
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|KNOWLEDGE_ITEM
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|DISCOURSE_ITEM
init|=
literal|3
decl_stmt|;
comment|/**      *       * @return the referenced object.      */
name|T
name|getItem
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

