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
name|mapping
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
name|Rick
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
name|ConfiguredSite
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
comment|/**  * Intended to define the configuration of the fieldMapper.  *   * @author Rupert Westenthaler  * @deprecated unsure - Currently the functionality of this service is part of   * the {@link RickConfig} and the {@link ConfiguredSite} interfaces. Access  * Methods for the {@link FieldMapper} are defined by the {@link Rick} and  * the {@link ReferencedSite} interfaces  */
end_comment

begin_interface
annotation|@
name|Deprecated
specifier|public
interface|interface
name|FieldMapperConfig
block|{
comment|/** 	 * The property used to configure the default mappings used by all  	 * {@link ReferencedSite} instances active within the Rick 	 */
name|String
name|DEFAULT_MAPPINGS
init|=
literal|"eu.iksproject.rick.mapping.default"
decl_stmt|;
comment|/** 	 * The Property used to configure mappings that are only used for 	 * representation of a specific Site. 	 */
name|String
name|SITE_MAPPINGS
init|=
literal|"eu.iksproject.rick.mapping.site"
decl_stmt|;
block|}
end_interface

end_unit

