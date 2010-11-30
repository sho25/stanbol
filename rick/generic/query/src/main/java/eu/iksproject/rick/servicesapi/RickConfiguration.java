begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
package|;
end_package

begin_comment
comment|/**  * Provides the Configuration needed by the {@link Rick}.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|RickConfiguration
block|{
comment|/** 	 * The property used to configure the id of the RickYard 	 */
name|String
name|RICK_YARD_ID
init|=
literal|"eu.iksproject.rick.yard.rickYardId"
decl_stmt|;
comment|/** 	 * The default ID for the Yard used for the Rick 	 */
name|String
name|DEFAULT_RICK_YARD_ID
init|=
literal|"urn:eu.iksproject:rick:rickYard"
decl_stmt|;
comment|/** 	 * This is the ID of the Yard used by the Rick to store its data 	 * @return the Rick-Yard id 	 */
name|String
name|getRickYardId
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

