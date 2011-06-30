begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|processor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|MappingEngine
import|;
end_import

begin_comment
comment|/**  * An extractor which is responsible for creation and deletion of triples.  * With the same list of CMS objects and mapping environment a processor is expected to  * be able to delete all the triples it generated after successive calls of   * {@link #createObjects(List, MappingEngine)} and {@linkplain #deleteObjects(List, MappingEngine)}   * @author cihan  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Processor
block|{
comment|/**      * Method for determining if the processor can process the specified CMS object.      * @param cmsObject      * @return true if the CMS object can be processed.      */
name|Boolean
name|canProcess
parameter_list|(
name|Object
name|cmsObject
parameter_list|)
function_decl|;
comment|/**      * Creates extracted triples from the provided CMS objects.       * The ontology should be available through<b>engine</b> parameter.       * @param objects a list of CMS objects to process      * @param engine       */
name|void
name|createObjects
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|objects
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
function_decl|;
comment|/**      * Deletes previously extracted triples from the provided CMS objects, by this processor.      * @param objects a list of CMS objects to process      * @param engine      */
name|void
name|deleteObjects
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|objects
parameter_list|,
name|MappingEngine
name|engine
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

