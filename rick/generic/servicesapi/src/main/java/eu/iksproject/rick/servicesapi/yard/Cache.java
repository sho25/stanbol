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
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|FieldMapper
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
name|Representation
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
name|site
operator|.
name|ReferencedSite
import|;
end_import

begin_comment
comment|/**  * The Cache is a mediator between a {@link Yard} storing the cached information  * and the {@link ReferencedSite} acting as access point to a referenced   * information source.<p>  * This interface provides also access to the configuration of the cache.  * This includes meta data needed to decide if requested information can be   * retrieved by using the cache or if data need to be requested directly by the  * referenced site. This includes mainly cached fields used {@link CacheStrategy}  * for fields as well as cached languages. This may be extended in future  * versions.<p>  * It is important, that the configuration of the cache is stored within the  * {@link Yard} together with all the other cached information, because it MUST  * BE possible to copy&paste cached information to an other RICK instance. This  * requirements enables the initialisation of a Cache only based on the  * information stored within the cache.<p>  * Cache instances should be instantiated automatically by {@link ReferencedSite}s  * based on there configuration. The only parameter needed for the initialisation  * of a {@link Cache} is the id of the yard ({@link Yard#getId()}). However  * note that the Cache is not responsible to create, configure nor activate the  * {@link Yard}. If the {@link Yard} for an cache is not active the cache  * returns<code>false</code> on calls to {@link #isAvailable()}.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Cache
extends|extends
name|Yard
block|{
comment|/** 	 * Key used to store the id of the Yard used as cache 	 */
name|String
name|CACHE_YARD
init|=
literal|"eu.iksproject.rick.yard.cacheYardId"
decl_stmt|;
comment|/** 	 * The ID used to store the configuration used for the creation of the cache. 	 * This configuration can only be changed by updating all the cached items. 	 */
name|String
name|BASE_CONFIGURATION_URI
init|=
literal|"urn:eu.iksproject:rick.yard:cache:config.base"
decl_stmt|;
comment|/** 	 * The ID used to store the configuration used for the creation of 	 * new items in the cache. This configuration is called additional, because 	 * it can only define additional information to be cached. This restriction 	 * is important because the base base configuration is used to determine 	 * if queries can be answered by the cache. So it MUST BE ensured that also 	 * documents updated in the cache confirm to the base configuration. 	 */
name|String
name|ADDITIONAL_CONFIGURATION_URI
init|=
literal|"urn:eu.iksproject:rick.yard:cache:config.additional"
decl_stmt|;
comment|/** 	 * The field used to store the fieldMappings of a CacheConfiguration 	 */
name|String
name|FIELD_MAPPING_CONFIG_FIELD
init|=
literal|"urn:eu.iksproject:rick.yard:cache:fieldMappings"
decl_stmt|;
comment|/** 	 * Returns the cache strategy for the given Field.<p> 	 *<ul> 	 *<li>{@link CacheStrategy#all} indicates that this field is cached for all 	 * Representations within the cache. 	 *<li>{@link CacheStrategy#used} indicates that this field is only present 	 * for some Representations within the cache. 	 *<li>{@link CacheStrategy#none} indicates that this field is ignored by 	 * the cache. 	 *</ul> 	 * @param field the field 	 * @return the cache strategy for this field 	 */
name|CacheStrategy
name|isField
parameter_list|(
name|String
name|field
parameter_list|)
function_decl|;
comment|/** 	 * Returns the cache strategy for the given Language<p> 	 *<ul> 	 *<li>{@link CacheStrategy#all} indicates that this language is cached for all 	 * Representations within the cache. 	 *<li>{@link CacheStrategy#used} indicates that this language is only present 	 * for some Representations within the cache. 	 *<li>{@link CacheStrategy#none} indicates that this language is ignored by 	 * the cache. 	 *</ul> 	 * @param lang the language 	 * @return the cache strategy for this language 	 */
name|CacheStrategy
name|isLanguage
parameter_list|(
name|String
name|lang
parameter_list|)
function_decl|;
comment|/** 	 * They key used to configure the cache strategy 	 */
name|String
name|CACHE_STRATEGY
init|=
literal|"eu.iksproject.rick.yard.cache.strategy"
decl_stmt|;
comment|/** 	 * The strategy used by this Cache.<p> 	 *<li>{@link CacheStrategy#all} indicates that one can assume that this cache  	 * holds at least some data for all Representations of the cached referenced  	 * site. 	 *<li>{@link CacheStrategy#used} indicates that this cache holds only 	 * previously used entities of the referenced site. 	 *<li>{@link CacheStrategy#none} indicates that this cache holds no data 	 * of the ReferencedSite. This value is normally not used by a real cache 	 * implementation. 	 *</ul> 	 * One needs to use {@link #isField(String)} and {@link #isLanguage(String)} 	 * to check how specific fields are treated by the cahce.<p> 	 * e.g. a cache may store dc:titel and dc:author for all documents, but all 	 * other fields only for previously used items. In such cases this method 	 * would return {@link CacheStrategy#all} but a call to  	 *<code>isField("dc:description")</code> would return  	 * {@link CacheStrategy#used}.<p> 	 * This would indicate, that queries for titles and authors could be 	 * executed by using the cache. Queries that also searches for 	 * dc:descriptions would need to be performed directly on the referenced 	 * site. 	 * @return the strategy 	 */
name|CacheStrategy
name|strategy
parameter_list|()
function_decl|;
comment|/** 	 * Getter for the state of the Yard configured to store cached information 	 * @return<code>true</code> if the yard used by this cache is available  	 * (active).  	 */
name|boolean
name|isAvailable
parameter_list|()
function_decl|;
comment|/** 	 * Key used to configure additional mappings for a cache 	 */
name|String
name|ADDITIONAL_MAPPINGS
init|=
literal|"eu.iksproject.rick.yard.cache.additionalMappings"
decl_stmt|;
comment|// Design decision: Base Mappings MUST NOT be configurable via OSGI Properties
comment|//	/**
comment|//	 * Key used to configure the base mappings for a cache
comment|//	 */
comment|//	String BASE_MAPPINGS = "eu.iksproject.rick.yard.cache.baseMappings";
comment|/** 	 * Sets the base mappings to the parsed field mapper and stores the new 	 * configuration to the yard. If the {@link #baseMapper} is<code>null</code> this 	 * method removes any existing configuration from the yard.<p> 	 *<b>NOTE: (Expert Usage only)</b>Take care if using this method! The base 	 * configuration MUST BE in correspondence with the cached data! Typically  	 * this configuration is set by the code that creates a Full-Cache  	 * ({@link CacheStrategy#all}) for an entity source (referenced site) and is 	 * not modified later on. Changes to the base configuration usually requires 	 * all entities in the cache to be updated!<p> 	 * @param fieldMapper the fieldMapper holding the new base field mapping  	 * configuration 	 * @throws YardException on any error while storing the new configuration. 	 * The new configuration is not set if storage is not successful to prevent 	 * an configuration that do not correspond with the cached information in the 	 * yard. 	 */
name|void
name|setBaseMappings
parameter_list|(
name|FieldMapper
name|fieldMapper
parameter_list|)
throws|throws
name|YardException
function_decl|;
comment|/** 	 * Sets the additional field mappings for a cache. This mapping defines 	 * fields that are cached in addition to that defined by the base mapping when 	 * {@link Representation} of entities are stored/updated in the cache.<p> 	 * Changing the additional mappings will only influence documents stored/ 	 * updated in the cache after the change. Already cached documents are not 	 * affected changed. This means, that by changing this configuration a 	 * cache will contain documents written by using different configurations. 	 * To avoid that one needs to delete all documents in the cache.<p> 	 * However changes to this configuration can not affect the base configuration 	 * of the cache, because base mappings cannot be undone by this configuration. 	 *<p> 	 * Note that typically Caches used for {@link ReferencedSite}s using the  	 * {@link CacheStrategy#used} do not define any base configuration. Also note 	 * that setting the additional mappings to<code>null</code> means:<ul> 	 *<li> using the base configuration if one is present or 	 *<li> storing all data of the parsed Representations if no base configuration 	 * is present. 	 *</ul> 	 * @param fieldMapper the fieldMapper holding the new additional field mapping  	 * configuration 	 * @throws YardException on any error while storing the new configuration. 	 * The new configuration is not set if storage is not successful to prevent 	 * an configuration that do not correspond with the cached information in the 	 * yard. 	 */
name|void
name|setAdditionalMappings
parameter_list|(
name|FieldMapper
name|fieldMapper
parameter_list|)
throws|throws
name|YardException
function_decl|;
comment|/** 	 * Getter for the additional mappings used by this Cache. Modifications on the 	 * returned object do not have any influence on the mappings, because this 	 * method returns a clone. Use {@link #setAdditionalMappings(FieldMapper)} to 	 * change the used additional mappings. However make sure you understand the 	 * implications of changing the base mappings as described in the 	 * documentation of the setter method 	 * @return A clone of the additional mappings or<code>null</code> if no 	 * additional mappings are defined 	 */
name|FieldMapper
name|getAdditionalMappings
parameter_list|()
function_decl|;
comment|/** 	 * Getter for the base mappings used by this Cache. Modifications on the 	 * returned object do not have any influence on the mappings, because this 	 * method returns a clone. Use {@link #setBaseMappings(FieldMapper)} to 	 * change the used base mappings. However make sure you understand the 	 * implications of changing the base mappings as described in the 	 * documentation of the setter method 	 * @return A clone of the base mappings or<code>null</code> if no base 	 * mappings are defined 	 */
name|FieldMapper
name|getBaseMappings
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

