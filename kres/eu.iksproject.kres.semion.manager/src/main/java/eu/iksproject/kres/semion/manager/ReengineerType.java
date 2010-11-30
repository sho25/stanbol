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
name|manager
package|;
end_package

begin_comment
comment|/**  * The ReenginnerType class allows to declare the type of data source that a concrete SemionReengineer  * is able to manage. The type is represented as an {@code int}  *<br>  *<br>  *   * Valid values are:  *<ul>  *<li> 0 - Relational Databases  *<li> 1 - XML  *<li> 2 - iCalendar  *<li> 3 - RSS  *</ul>  *   *   * @author andrea.nuzzolese  *  */
end_comment

begin_class
specifier|public
class|class
name|ReengineerType
block|{
comment|/** 	 * 0 - Relational Databases 	 */
specifier|public
specifier|static
name|int
name|RDB
init|=
literal|0
decl_stmt|;
comment|/** 	 * 1 - XML 	 */
specifier|public
specifier|static
name|int
name|XML
init|=
literal|1
decl_stmt|;
comment|/** 	 * 2 - iCalendar 	 */
specifier|public
specifier|static
name|int
name|I_CALENDAR
init|=
literal|2
decl_stmt|;
comment|/** 	 * 3 - RSS 	 */
specifier|public
specifier|static
name|int
name|RSS
init|=
literal|3
decl_stmt|;
comment|/** 	 * Static method that enables to know the the type of a data source supported by the reengineer in a human-readable string 	 * format 	 *  	 * @param type {@code int} 	 * @return the string representing the data source type supported by the reengineer 	 */
specifier|public
specifier|static
name|String
name|getTypeString
parameter_list|(
name|int
name|type
parameter_list|)
block|{
name|String
name|typeString
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
literal|0
case|:
name|typeString
operator|=
literal|"Relational Databases"
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|typeString
operator|=
literal|"XML"
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|typeString
operator|=
literal|"i-calendar"
expr_stmt|;
break|break;
case|case
literal|3
case|:
name|typeString
operator|=
literal|"RSS"
expr_stmt|;
break|break;
block|}
return|return
name|typeString
return|;
block|}
block|}
end_class

end_unit

