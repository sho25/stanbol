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
name|java
operator|.
name|util
operator|.
name|Collection
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
name|mapping
operator|.
name|FieldMapping
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
name|model
operator|.
name|EntityMapping
operator|.
name|MappingState
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
comment|/**  * Provides the Configuration needed by the {@link Rick}.<p>  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|RickConfiguration
block|{
comment|/** 	 * The key used to configure the ID of the RICK 	 */
name|String
name|ID
init|=
literal|"eu.iksprojct.rick.id"
decl_stmt|;
comment|/** 	 * The ID of the Rick. This ID is used as origin (sign site) for all symbols 	 * and mapped entities created by the Rick 	 * @return the ID of the Rick 	 */
name|String
name|getID
parameter_list|()
function_decl|;
comment|/** 	 * The property used to configure the prefix used for {@link Symbol}s and 	 * {@link EntityMapping}s created by the Rick 	 */
name|String
name|PREFIX
init|=
literal|"eu.iksproject.rick.prefix"
decl_stmt|;
comment|/** 	 * Getter for the Prefix to be used for all {@link Symbol}s and {@link EntityMapping}s 	 * created by the {@link Rick} 	 * @return The prefix for {@link Symbol}s and {@link EntityMapping}s 	 */
name|String
name|getRickPrefix
parameter_list|()
function_decl|;
comment|/** 	 * The key used to configure the name of the RICK 	 */
name|String
name|NAME
init|=
literal|"eu.iksprojct.rick.name"
decl_stmt|;
comment|/** 	 * The human readable name of this RICK instance. Typically used as label  	 * in addition/instead of the ID. 	 * @return the Name (or the ID in case no name is defined) 	 */
name|String
name|getName
parameter_list|()
function_decl|;
comment|/** 	 * The key used to configure the description of the RICK 	 */
name|String
name|DESCRIPTION
init|=
literal|"eu.iksprojct.rick.description"
decl_stmt|;
comment|/** 	 * The human readable description to provide some background information about 	 * this RICK instance. 	 * @return the description or<code>null</code> if none is defined/configured. 	 */
name|String
name|getDescription
parameter_list|()
function_decl|;
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
literal|"rickYard"
decl_stmt|;
comment|/** 	 * This is the ID of the Yard used by the Rick to store its data 	 * @return the Rick-Yard id 	 */
name|String
name|getRickYardId
parameter_list|()
function_decl|;
comment|/** 	 * The property used to configure the field mappings for the RICK 	 */
name|String
name|FIELD_MAPPINGS
init|=
literal|"eu.iksproject.rick.mapping.rick"
decl_stmt|;
comment|/** 	 * Getter for the FieldMapping configuration of the Rick. These Mappings are 	 * used for every {@link ReferencedSite} of the Rick.<br> 	 * Note that {@link FieldMapping#parseFieldMapping(String)} is used to 	 * parsed the values returned by this Method 	 * @return the configured mappings for the Rick 	 */
name|Collection
argument_list|<
name|String
argument_list|>
name|getFieldMappingConfig
parameter_list|()
function_decl|;
comment|/** 	 * The property used to configure the initial state for new {@link EntityMapping}s 	 */
name|String
name|DEFAULT_MAPPING_STATE
init|=
literal|"eu.iksproject.rick.defaultMappingState"
decl_stmt|;
comment|/** 	 * The initial (default) state for new {@link EntityMapping}s 	 * @return the default state for new {@link EntityMapping}s 	 */
name|MappingState
name|getDefaultMappingState
parameter_list|()
function_decl|;
comment|/** 	 * The property used to configure the initial state for new {@link Symbol}s 	 */
name|String
name|DEFAULT_SYMBOL_STATE
init|=
literal|"eu.iksproject.rick.defaultSymbolState"
decl_stmt|;
comment|/** 	 * The initial (default) state for new {@link Symbol}s 	 * @return the default state for new {@link Symbol}s 	 */
name|Symbol
operator|.
name|SymbolState
name|getDefaultSymbolState
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

