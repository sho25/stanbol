begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
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
name|Map
import|;
end_import

begin_comment
comment|/**  * FISE components might implement this interface to parse additional  * properties to other components.  *  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|ServiceProperties
block|{
comment|/** 	 * Getter for the properties defined by this service. 	 * @return An unmodifiable map of properties defined by this service 	 */
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getServiceProperties
parameter_list|()
function_decl|;
comment|//TODO review the definition of constants
comment|/** 	 * Property Key used to define the order in which {@link EnhancementEngine}s are 	 * called by the {@link EnhancementJobManager}. This property expects a 	 * single {@link Integer} as value 	 */
name|String
name|ENHANCEMENT_ENGINE_ORDERING
init|=
literal|"eu.iksproject.fise.engine.order"
decl_stmt|;
comment|/** 	 * Ordering values>= this value indicate, that an enhancement engine 	 * dose some pre processing on the content 	 */
name|Integer
name|ORDERING_PRE_PROCESSING
init|=
literal|200
decl_stmt|;
comment|/**   	 * Ordering values< {@link ServiceProperties#ORDERING_PRE_PROCESSING} and 	 *>= this value indicate, that an enhancement engine performs operations 	 * that are only dependent on the parsed content. 	 */
name|Integer
name|ORDERING_CONTENT_EXTRACTION
init|=
literal|100
decl_stmt|;
comment|/** 	 * Ordering values< {@link ServiceProperties#ORDERING_CONTENT_EXTRACTION} 	 * and>= this value indicate, that an enhancement engine performs operations 	 * on extracted features of the content. It can also extract additional 	 * enhancement by using the content, but such features might not be  	 * available to other engines using this ordering range 	 */
name|Integer
name|ORDERING_EXTRACTION_ENHANCEMENT
init|=
literal|1
decl_stmt|;
comment|/** 	 * The default ordering uses {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT} 	 * -1 . So by default EnhancementEngines are called after all engines that 	 * use an value within the ordering range defined by 	 * {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT} 	 */
name|Integer
name|ORDERING_DEFAULT
init|=
literal|0
decl_stmt|;
comment|/** 	 * Ordering values< {@link ServiceProperties#ORDERING_DEFAULT} and>= this 	 * value indicate that an enhancement engine performs post processing  	 * operations on existing enhancements. 	 */
name|Integer
name|ORDERING_POST_PROCESSING
init|=
operator|-
literal|100
decl_stmt|;
block|}
end_interface

end_unit

