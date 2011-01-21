begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|mapping
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|Entityhub
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|EntityhubConfiguration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|ReferencedSite
import|;
end_import

begin_comment
comment|/**  * Intended to define the configuration of the fieldMapper.  *  * @author Rupert Westenthaler  * @deprecated unsure - Currently the functionality of this service is part of  * the {@link EntityhubConfiguration} and the {@link ConfiguredSite} interfaces.   * Access Methods for the {@link FieldMapper} are defined by the   * {@link Entityhub} and the {@link ReferencedSite} interfaces  */
end_comment

begin_interface
annotation|@
name|Deprecated
specifier|public
interface|interface
name|FieldMapperConfig
block|{
comment|/**      * The property used to configure the default mappings used by all      * {@link ReferencedSite} instances active within the Entityhub      */
name|String
name|DEFAULT_MAPPINGS
init|=
literal|"org.apache.stanbol.entityhub.mapping.default"
decl_stmt|;
comment|/**      * The Property used to configure mappings that are only used for      * representation of a specific Site.      */
name|String
name|SITE_MAPPINGS
init|=
literal|"org.apache.stanbol.entityhub.mapping.site"
decl_stmt|;
block|}
end_interface

end_unit

