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
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLAxiom
import|;
end_import

begin_comment
comment|/**  * A factory for explanation information objects.  *   * @author alexdma  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ExplanationGenerator
block|{
comment|/**      * Creates a new explanation.      *       * @param item      * @param type      * @param grounds      * @return      */
name|Explanation
name|createExplanation
parameter_list|(
name|Explainable
argument_list|<
name|?
argument_list|>
name|item
parameter_list|,
name|ExplanationTypes
name|type
parameter_list|,
name|Set
argument_list|<
name|?
extends|extends
name|OWLAxiom
argument_list|>
name|grounds
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

