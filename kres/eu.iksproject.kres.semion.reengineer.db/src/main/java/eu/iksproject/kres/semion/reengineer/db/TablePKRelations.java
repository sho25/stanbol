begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|semion
operator|.
name|reengineer
operator|.
name|db
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_comment
comment|/**  *   * Utility class for representing and managing in Semion primary and foreign keys of relatinal databases.  *   * @author andrea.nuzzolese  *  */
end_comment

begin_class
specifier|public
class|class
name|TablePKRelations
block|{
name|String
name|fkTableName
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|pkColumns
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fkColumns
decl_stmt|;
name|String
name|pkTable
decl_stmt|;
comment|/** 	 * Create a new {@code TablePKRelations} 	 *  	 * @param fkTableName 	 * @param fkColumns 	 * @param pkColumns 	 * @param pkTable 	 */
specifier|public
name|TablePKRelations
parameter_list|(
name|String
name|fkTableName
parameter_list|,
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fkColumns
parameter_list|,
name|ArrayList
argument_list|<
name|String
argument_list|>
name|pkColumns
parameter_list|,
name|String
name|pkTable
parameter_list|)
block|{
name|this
operator|.
name|fkTableName
operator|=
name|fkTableName
expr_stmt|;
name|this
operator|.
name|fkColumns
operator|=
name|fkColumns
expr_stmt|;
name|this
operator|.
name|pkColumns
operator|=
name|pkColumns
expr_stmt|;
name|this
operator|.
name|pkTable
operator|=
name|pkTable
expr_stmt|;
block|}
specifier|public
name|String
name|getFkTableName
parameter_list|()
block|{
return|return
name|fkTableName
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getFkColumns
parameter_list|()
block|{
return|return
name|fkColumns
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getPkColumns
parameter_list|()
block|{
return|return
name|pkColumns
return|;
block|}
specifier|public
name|String
name|getPkTable
parameter_list|()
block|{
return|return
name|pkTable
return|;
block|}
specifier|public
name|void
name|setPkColumns
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|pkColumns
parameter_list|)
block|{
name|this
operator|.
name|pkColumns
operator|=
name|pkColumns
expr_stmt|;
block|}
specifier|public
name|void
name|setFkColumns
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fkColumns
parameter_list|)
block|{
name|this
operator|.
name|fkColumns
operator|=
name|fkColumns
expr_stmt|;
block|}
block|}
end_class

end_unit

