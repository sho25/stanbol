begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|owlapi
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
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|servicesapi
operator|.
name|InconsistentInputException
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningService
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningServiceException
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
name|OWLOntology
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
name|SWRLRule
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
name|util
operator|.
name|InferredAxiomGenerator
import|;
end_import

begin_comment
comment|/**  * Interface for any OWLApi based reasoning service  */
end_comment

begin_interface
specifier|public
interface|interface
name|OWLApiReasoningService
extends|extends
name|ReasoningService
argument_list|<
name|OWLOntology
argument_list|,
name|SWRLRule
argument_list|,
name|OWLAxiom
argument_list|>
block|{
specifier|public
specifier|abstract
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|run
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|,
name|List
argument_list|<
name|SWRLRule
argument_list|>
name|rules
parameter_list|,
name|List
argument_list|<
name|InferredAxiomGenerator
argument_list|<
name|?
extends|extends
name|OWLAxiom
argument_list|>
argument_list|>
name|generators
parameter_list|)
throws|throws
name|ReasoningServiceException
throws|,
name|InconsistentInputException
function_decl|;
specifier|public
specifier|abstract
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|run
parameter_list|(
name|OWLOntology
name|input
parameter_list|,
name|List
argument_list|<
name|InferredAxiomGenerator
argument_list|<
name|?
extends|extends
name|OWLAxiom
argument_list|>
argument_list|>
name|generators
parameter_list|)
throws|throws
name|ReasoningServiceException
throws|,
name|InconsistentInputException
function_decl|;
block|}
end_interface

end_unit

