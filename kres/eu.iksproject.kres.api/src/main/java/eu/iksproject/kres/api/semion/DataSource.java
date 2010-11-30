begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|semion
package|;
end_package

begin_comment
comment|/**  * A {@code DataSource} object represents a physical non-RDF data source in Semion.  *<br>  *<br>  * Supported data sources are:  *<ul>  *<li>Relational databases  *<li>XML  *<li>iCalendar  *<li>RSS  *</ul>  *   *    * @author andrea.nuzzolese  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|DataSource
block|{
comment|/** 	 * Get the ID of the data source as it is represented in Semion 	 * @return the {@link String} representing the ID of the physical data source in Semion 	 */
specifier|public
name|String
name|getID
parameter_list|()
function_decl|;
comment|/** 	 * As a {@code DataSource} is only a representation of the data source in Semion, a method that returns the physical 	 * data source is provided. 	 *  	 * @return the physical data source 	 */
specifier|public
name|Object
name|getDataSource
parameter_list|()
function_decl|;
comment|/** 	 * Data sources that Semion is able to manage have an integer that identifies the type of the data source. 	 *  	 * Valid values are: 	 *<ul> 	 *<li> 0 - Relational Databases 	 *<li> 1 - XML 	 *<li> 2 - iCalendar 	 *<li> 3 - RSS 	 *</ul> 	 *  	 * @return the data source type 	 */
specifier|public
name|int
name|getDataSourceType
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

