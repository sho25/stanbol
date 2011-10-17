begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
operator|.
name|api
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
comment|/**  * A knowledge object that incorporates a description, or justification, for another knowledge object, be it a  * content item, fact, event or collection thereof.  *   * @author alexdma  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|Explanation
block|{
comment|/**      *       * @return the object being explained.      */
name|Explainable
argument_list|<
name|?
argument_list|>
name|getObject
parameter_list|()
function_decl|;
comment|/**      *       * @return the items that can be assembled for rendering the explanation.      */
name|Collection
argument_list|<
name|?
argument_list|>
name|getGrounding
parameter_list|()
function_decl|;
comment|/**      *       * @return the nature of this explanation object.      */
name|ExplanationTypes
name|getType
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

