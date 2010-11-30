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
operator|.
name|yard
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_comment
comment|/**  * Manages the different active Yards   * TODO: Who is responsible of initialising the RickYard (the yard storing  * Symbols and Mapped Entities)?  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|YardManager
block|{
comment|/** 	 * Getter for the IDs of Yards currently managed by this Manager 	 * @return the Ids of the currently active Yards 	 */
name|Collection
argument_list|<
name|String
argument_list|>
name|getYardIDs
parameter_list|()
function_decl|;
comment|/** 	 * Returns if there is a Yard for the parsed ID 	 * @param id the id 	 * @return<code>true</code> if a {@link Yard} with the parsed ID is managed 	 * by this YardManager. 	 */
name|boolean
name|isYard
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/** 	 * Getter for the Yard based on the parsed Id 	 * @param id the ID 	 * @return The Yard or<code>null</code> if no Yard with the parsed ID is 	 * active. 	 */
name|Yard
name|getYard
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

