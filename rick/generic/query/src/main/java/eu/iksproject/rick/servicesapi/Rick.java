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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|EntityMapping
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Symbol
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
import|;
end_import

begin_comment
comment|/**  *<p>The Rick manages all management specific metadata for the<b>R</b>eference  *<b>I</b>nfrastrucutre for<b>C</b>ontent and<b>K</b>nowledge.</p>  *<p>It allows to register/create  *<ul>  *<li>{@link Symbol}: Entities as defined by the local CMS</li>  *<li>{@link EntityMapping}: External Entities that are aligned to {@link Symbol}  *      used by the local CMS</li>  *<li> {@link Sites}: External sites that manage entities. Such sites also define   *      default valued for MappedEntities and Symbols created based on Entities  *      they manage</li>  *</ul>  *   * TODO's<ul>  *<li> Query Interface for Symbols and Entities. Probably a   *       field -> value pattern based query language would be a good start  *       (e.g. label="Par*" type="ns:Location")  *<li> How to deal with content (references, base62 encoded Strings,  *       {@link InputStream}s ...  *<li> Serialising of Representations to different Formats (especially RDF and  *       JSON)  *</ul>  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Rick
block|{
comment|/** 	 * Getter for the Yard storing the data (Symbols, MappedEntities) of the Rick. 	 * @return The yard instance used to store the data of the Rick - the RickYard 	 */
specifier|public
name|Yard
name|getRickYard
parameter_list|()
function_decl|;
comment|/** 	 * Getter for an Symbol for an entity (representation). If no {@link EntityMapping} 	 * is available for the parsed entity ID than it is checked of a new Symbol 	 * can be created for this entity. (If that is possible depends of a site is 	 * configured for the parsed entity ID, the setting as configured for the site 	 * if the site is currently available and/or the site is cached in a local 	 * {@link Yard}, ...). This Method is equivalent to {@link Rick#getSymbol(String, boolean)} 	 * if<code>true</code> is parsed as second parameter. 	 * @param reference the ID of the entity 	 * @return the symbol representing the parsed entity or<code>null</code> if 	 * no symbol for the parsed entity is available nor can be created. 	 */
name|Symbol
name|getSymbol
parameter_list|(
name|String
name|reference
parameter_list|)
function_decl|;
comment|/** 	 * Getter for an Symbol for an entity (representation). 	 * @param reference the id of the entity 	 * @param create if<code>true</code> the {@link Rick} will try to create a 	 * {@link Symbol} if necessary 	 * @return the symbol or<code>null</code> if not present 	 */
name|Symbol
name|getSymbol
parameter_list|(
name|String
name|reference
parameter_list|,
name|boolean
name|create
parameter_list|)
function_decl|;
comment|/** 	 * Getter for a MappedEntity based on the ID 	 * @param id the id of the mapped entity 	 * @return the MappedEntity or<code>null</code> if none was found 	 */
name|EntityMapping
name|getMappingById
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/** 	 * Getter for all mappings for a entity 	 * TODO: check if an Entity can be mapped to more than one Symbol 	 * @param reference the ID of the referred entity 	 * @return Iterator over all the Mappings defined for this entity 	 */
name|EntityMapping
name|getMappingByEntity
parameter_list|(
name|String
name|reference
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

