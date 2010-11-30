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
name|site
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
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
name|CacheStrategy
import|;
end_import

begin_comment
comment|/**  * This interface defines the getter as well as the property keys for the  * configuration of a {@link ReferencedSite}.<p>  *   * TODO: No Idea how to handle that in an OSGI context.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ConfiguredSite
block|{
comment|/** 	 * The key to be used for the site id 	 */
name|String
name|ID
init|=
literal|"eu.iksproject.rick.site.id"
decl_stmt|;
comment|/** 	 * Getter for the id of this site 	 * @return 	 */
name|String
name|getId
parameter_list|()
function_decl|;
comment|/** 	 * The key to be used for the name of the site 	 */
name|String
name|NAME
init|=
literal|"eu.iksproject.rick.site.name"
decl_stmt|;
comment|/** 	 * The preferred name of this site (if not present use the id) 	 * @return the name (or if not defined the id) of the site 	 */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/** 	 * The key to be used for the site description 	 */
name|String
name|DESCRIPTION
init|=
literal|"eu.iksproject.rick.site.description"
decl_stmt|;
comment|/** 	 * Getter for the default short description of this site. 	 * @return The description or<code>null</code> if non is defined 	 */
name|String
name|getDescription
parameter_list|()
function_decl|;
comment|/** 	 * Key used for the configuration of the AccessURI  for a site 	 */
name|String
name|ACCESS_URI
init|=
literal|"eu.iksproject.rick.site.accessUri"
decl_stmt|;
comment|/** 	 * The URI used to access the Data of this Site. This is usually a different  	 * URI as the ID of the site.<p> 	 *  	 * To give some Examples:<p> 	 *  	 * symbol.label: DBPedia<br> 	 * symbol.id: http://dbpedia.org<br> 	 * site.acessUri: http://dbpedia.org/resource/<p> 	 *  	 * symbol.label: Freebase<br> 	 * symbol.id: http://www.freebase.com<br> 	 * site.acessUri: http://rdf.freebase.com/<p> 	 *  	 * @return the accessURI for the data of the referenced site 	 */
name|String
name|getAccessUri
parameter_list|()
function_decl|;
comment|/** 	 * Key used for the configuration of the name of the dereferencer type to be 	 * used for this site 	 */
name|String
name|DEREFERENCER_TYPE
init|=
literal|"eu.iksproject.rick.site.dereferencerType"
decl_stmt|;
comment|/** 	 * The name of the {@link EntityDereferencer} to be used for accessing 	 * representations of entities managed by this Site 	 * TODO: need to be reviewed how to link dereferencing functionality to 	 * configured Sites. 	 * @return 	 */
name|String
name|getDereferencerType
parameter_list|()
function_decl|;
comment|/** 	 * Key used for the configuration of the default {@link SymbolState} for a site 	 */
name|String
name|DEFAULT_SYMBOL_STATE
init|=
literal|"eu.iksproject.rick.site.defaultSymbolState"
decl_stmt|;
comment|/** 	 * The initial state if a {@link Symbol} is created for an entity managed 	 * by this site 	 * @return the default state for new symbols 	 */
name|Symbol
operator|.
name|SymbolState
name|getDefaultSymbolState
parameter_list|()
function_decl|;
comment|/** 	 * Key used for the configuration of the default {@link EntityMapping} state  	 * ({@link EntityMapping.MappingState} for a site 	 */
name|String
name|DEFAULT_MAPEED_ENTITY_STATE
init|=
literal|"eu.iksproject.rick.site.defaultMappedEntityState"
decl_stmt|;
comment|/** 	 * The initial state for mappings of entities managed by this site 	 * @return the default state for mappings to entities of this site 	 */
name|EntityMapping
operator|.
name|MappingState
name|getDefaultMappedEntityState
parameter_list|()
function_decl|;
comment|/** 	 * Key used for the configuration of the default expiration duration for entities and 	 * data for a site 	 */
name|String
name|DEFAULT_EXPIRE_DURATION
init|=
literal|"eu.iksproject.rick.site.defaultExpireDuration"
decl_stmt|;
comment|/** 	 * Return the duration in milliseconds or values<= 0 if mappings to entities 	 * of this Site do not expire.  	 * @return the duration in milliseconds or values<=0 if not applicable.  	 */
name|long
name|getDefaultExpireDuration
parameter_list|()
function_decl|;
comment|/** 	 * Key used for the configuration of the default expiration duration for entities and 	 * data for a site 	 */
name|String
name|CACHE_STRATEGY
init|=
literal|"eu.iksproject.rick.site.cacheStrategy"
decl_stmt|;
comment|/** 	 * The cache strategy used by for this site to be used. 	 * @return the cache strategy 	 */
name|CacheStrategy
name|getCacheStrategy
parameter_list|()
function_decl|;
comment|/** 	 * Key used for the configuration of prefixes used by Entities managed by this Site 	 */
name|String
name|ENTITY_PREFIX
init|=
literal|"eu.iksproject.rick.site.entityPrefix"
decl_stmt|;
comment|/** 	 * Getter for the prefixes of entities managed by this Site 	 * @return the entity prefixes. In case there are non an empty array is returned. 	 */
name|String
index|[]
name|getEntityPrefixes
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

