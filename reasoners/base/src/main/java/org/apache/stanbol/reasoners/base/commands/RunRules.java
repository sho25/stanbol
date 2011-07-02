begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

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
name|base
operator|.
name|commands
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|apibinding
operator|.
name|OWLManager
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
name|OWLOntologyCreationException
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
name|OWLOntologyManager
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
name|reasoner
operator|.
name|OWLReasoner
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
name|InferredOntologyGenerator
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|ModelFactory
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|owl
operator|.
name|transformation
operator|.
name|JenaToOwlConvert
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
name|OWLOntologySetProvider
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
name|OWLOntologyMerger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|RunRules
block|{
specifier|private
name|OWLOntology
name|swrlontology
decl_stmt|;
specifier|private
name|OWLOntology
name|targetontology
decl_stmt|;
specifier|private
name|OWLOntology
name|workontology
decl_stmt|;
specifier|private
name|OWLReasoner
name|reasoner
decl_stmt|;
specifier|private
name|OWLOntologyManager
name|owlmanager
decl_stmt|;
comment|/**      * Constructor where inputs are the OWLOntology models contains the rules and the target ontology where to perform the reasoning (HermiT).      *      * @param SWRLruleOntology {The OWLOntology contains the SWRL rules.}      * @param targetOntology {The OWLOntology model where to perform the SWRL rule reasoner.}      */
specifier|public
name|RunRules
parameter_list|(
name|OWLOntology
name|SWRLruleOntology
parameter_list|,
name|OWLOntology
name|targetOntology
parameter_list|)
block|{
comment|//        cloneOntology(targetOntology);
name|this
operator|.
name|swrlontology
operator|=
name|SWRLruleOntology
expr_stmt|;
name|this
operator|.
name|targetontology
operator|=
name|targetOntology
expr_stmt|;
name|this
operator|.
name|owlmanager
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
expr_stmt|;
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
name|ontologies
operator|.
name|add
argument_list|(
name|targetontology
argument_list|)
expr_stmt|;
name|ontologies
operator|.
name|add
argument_list|(
name|swrlontology
argument_list|)
expr_stmt|;
return|return
name|ontologies
return|;
block|}
block|}
decl_stmt|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
try|try
block|{
name|this
operator|.
name|workontology
operator|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|owlmanager
argument_list|,
name|targetOntology
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|ex
parameter_list|)
block|{
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RunRules
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"Problem to create mergedontology"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
comment|//Create the reasoner
name|this
operator|.
name|reasoner
operator|=
operator|(
operator|new
name|CreateReasoner
argument_list|(
name|workontology
argument_list|)
operator|)
operator|.
name|getReasoner
argument_list|()
expr_stmt|;
comment|//Prepare the reasoner
comment|//this.reasoner.prepareReasoner();
block|}
comment|/**      * Constructor where the inputs are the OWLOntology models contains the rules, the target ontology where to perform the reasoning and the url of reasoner server end-point.      *      * @param SWRLruleOntology {The OWLOntology contains the SWRL rules.}      * @param targetOntology {The OWLOntology model where to perform the SWRL rule reasoner.}      * @param reasonerurl {The url of reasoner server end-point.}      */
specifier|public
name|RunRules
parameter_list|(
name|OWLOntology
name|SWRLruleOntology
parameter_list|,
name|OWLOntology
name|targetOntology
parameter_list|,
name|URL
name|reasonerurl
parameter_list|)
block|{
name|this
operator|.
name|targetontology
operator|=
name|targetOntology
expr_stmt|;
name|this
operator|.
name|swrlontology
operator|=
name|SWRLruleOntology
expr_stmt|;
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
name|ontologies
operator|.
name|add
argument_list|(
name|targetontology
argument_list|)
expr_stmt|;
name|ontologies
operator|.
name|add
argument_list|(
name|swrlontology
argument_list|)
expr_stmt|;
return|return
name|ontologies
return|;
block|}
block|}
decl_stmt|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
try|try
block|{
name|this
operator|.
name|workontology
operator|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|owlmanager
argument_list|,
name|targetOntology
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|ex
parameter_list|)
block|{
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RunRules
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"Problem to create mergedontology"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
comment|//Create the reasoner
name|this
operator|.
name|reasoner
operator|=
operator|(
operator|new
name|CreateReasoner
argument_list|(
name|workontology
argument_list|,
name|reasonerurl
argument_list|)
operator|)
operator|.
name|getReasoner
argument_list|()
expr_stmt|;
comment|//Prepare the reasoner
comment|//this.reasoner.prepareReasoner();
block|}
comment|/**      * Construct where the inputs are the: Model of type jena.rdf.model.Model that contains the SWRL rules and a target OWLOntology where to perform the reasoning.      *      * @param SWRLruleOntology {The Jena Model contains the SWRL rules.}      * @param targetOntology {The OWLOntology model where to perform the SWRL rule reasoner.}      */
specifier|public
name|RunRules
parameter_list|(
name|Model
name|SWRLruleOntology
parameter_list|,
name|OWLOntology
name|targetOntology
parameter_list|)
block|{
name|JenaToOwlConvert
name|j2o
init|=
operator|new
name|JenaToOwlConvert
argument_list|()
decl_stmt|;
name|OntModel
name|jenamodel
init|=
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|()
decl_stmt|;
name|jenamodel
operator|.
name|add
argument_list|(
name|SWRLruleOntology
argument_list|)
expr_stmt|;
name|OWLOntology
name|swrlowlmodel
init|=
name|j2o
operator|.
name|ModelJenaToOwlConvert
argument_list|(
name|jenamodel
argument_list|,
literal|"RDF/XML"
argument_list|)
decl_stmt|;
name|this
operator|.
name|targetontology
operator|=
name|targetOntology
expr_stmt|;
name|this
operator|.
name|swrlontology
operator|=
name|swrlowlmodel
expr_stmt|;
name|this
operator|.
name|targetontology
operator|=
name|targetOntology
expr_stmt|;
name|this
operator|.
name|owlmanager
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
expr_stmt|;
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
name|ontologies
operator|.
name|add
argument_list|(
name|targetontology
argument_list|)
expr_stmt|;
name|ontologies
operator|.
name|add
argument_list|(
name|swrlontology
argument_list|)
expr_stmt|;
return|return
name|ontologies
return|;
block|}
block|}
decl_stmt|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
try|try
block|{
name|this
operator|.
name|workontology
operator|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|owlmanager
argument_list|,
name|targetOntology
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|ex
parameter_list|)
block|{
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RunRules
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"Problem to create mergedontology"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
comment|//Create the reasoner
name|this
operator|.
name|reasoner
operator|=
operator|(
operator|new
name|CreateReasoner
argument_list|(
name|workontology
argument_list|)
operator|)
operator|.
name|getReasoner
argument_list|()
expr_stmt|;
comment|//Prepare the reasoner
comment|//this.reasoner.prepareReasoner();
block|}
comment|/**      * Construct where the inputs are the: Model of type jena.rdf.model.Model that contains the SWRL rules and a target OWLOntology where to perform the reasoning.      *      * @param SWRLruleOntology {The Jena Model contains the SWRL rules.}      * @param targetOntology {The OWLOntology model where to perform the SWRL rule reasoner.}      * @param reasonerurl {The url of the the reasoner server end-point.}      */
specifier|public
name|RunRules
parameter_list|(
name|Model
name|SWRLruleOntology
parameter_list|,
name|OWLOntology
name|targetOntology
parameter_list|,
name|URL
name|reasonerurl
parameter_list|)
block|{
name|JenaToOwlConvert
name|j2o
init|=
operator|new
name|JenaToOwlConvert
argument_list|()
decl_stmt|;
name|OntModel
name|jenamodel
init|=
name|ModelFactory
operator|.
name|createOntologyModel
argument_list|()
decl_stmt|;
name|jenamodel
operator|.
name|add
argument_list|(
name|SWRLruleOntology
argument_list|)
expr_stmt|;
name|OWLOntology
name|swrlowlmodel
init|=
name|j2o
operator|.
name|ModelJenaToOwlConvert
argument_list|(
name|jenamodel
argument_list|,
literal|"RDF/XML"
argument_list|)
decl_stmt|;
name|this
operator|.
name|targetontology
operator|=
name|targetOntology
expr_stmt|;
name|this
operator|.
name|swrlontology
operator|=
name|swrlowlmodel
expr_stmt|;
name|this
operator|.
name|targetontology
operator|=
name|targetOntology
expr_stmt|;
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
name|ontologies
operator|.
name|add
argument_list|(
name|targetontology
argument_list|)
expr_stmt|;
name|ontologies
operator|.
name|add
argument_list|(
name|swrlontology
argument_list|)
expr_stmt|;
return|return
name|ontologies
return|;
block|}
block|}
decl_stmt|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
try|try
block|{
name|this
operator|.
name|workontology
operator|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|owlmanager
argument_list|,
name|targetOntology
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|ex
parameter_list|)
block|{
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RunRules
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"Problem to create mergedontology"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
comment|//Create the reasoner
name|this
operator|.
name|reasoner
operator|=
operator|(
operator|new
name|CreateReasoner
argument_list|(
name|workontology
argument_list|,
name|reasonerurl
argument_list|)
operator|)
operator|.
name|getReasoner
argument_list|()
expr_stmt|;
comment|//Prepare the reasoner
comment|//this.reasoner.prepareReasoner();
block|}
comment|/**      * This method will run the reasoner and then save the inferred axioms with old axioms in a new ontology      *      * @param newmodel {The OWLOntology model where to save the inference.}      * @return {An OWLOntology object contains the ontology and the inferred axioms.}      */
specifier|public
name|OWLOntology
name|runRulesReasoner
parameter_list|(
specifier|final
name|OWLOntology
name|newmodel
parameter_list|)
block|{
try|try
block|{
name|InferredOntologyGenerator
name|iogpellet
init|=
operator|new
name|InferredOntologyGenerator
argument_list|(
name|reasoner
argument_list|)
decl_stmt|;
name|iogpellet
operator|.
name|fillOntology
argument_list|(
name|newmodel
operator|.
name|getOWLOntologyManager
argument_list|()
argument_list|,
name|newmodel
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|setx
init|=
name|newmodel
operator|.
name|getAxioms
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|OWLAxiom
argument_list|>
name|iter
init|=
name|setx
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLAxiom
name|axiom
init|=
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|axiom
operator|.
name|toString
argument_list|()
operator|.
name|contains
argument_list|(
literal|"Equivalent"
argument_list|)
condition|)
block|{
name|newmodel
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|removeAxiom
argument_list|(
name|newmodel
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
block|}
block|}
specifier|final
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|OWLOntologySetProvider
name|provider
init|=
operator|new
name|OWLOntologySetProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
name|ontologies
operator|.
name|add
argument_list|(
name|targetontology
argument_list|)
expr_stmt|;
name|ontologies
operator|.
name|add
argument_list|(
name|newmodel
argument_list|)
expr_stmt|;
return|return
name|ontologies
return|;
block|}
block|}
decl_stmt|;
name|OWLOntologyMerger
name|merger
init|=
operator|new
name|OWLOntologyMerger
argument_list|(
name|provider
argument_list|)
decl_stmt|;
name|OWLOntology
name|ontologyout
init|=
name|merger
operator|.
name|createMergedOntology
argument_list|(
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|,
name|targetontology
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|ontologyout
return|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|ex
parameter_list|)
block|{
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RunRules
operator|.
name|class
argument_list|)
operator|.
name|error
argument_list|(
literal|"Problem to create out ontology"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * This method will run the reasoner and then save the inferred axion in the  OWLOntology      *      * @return {An OWLOntology object contains the ontology and the inferred axioms.}      */
specifier|public
name|OWLOntology
name|runRulesReasoner
parameter_list|()
block|{
comment|//Create inferred axiom
name|InferredOntologyGenerator
name|ioghermit
init|=
operator|new
name|InferredOntologyGenerator
argument_list|(
name|reasoner
argument_list|)
decl_stmt|;
name|ioghermit
operator|.
name|fillOntology
argument_list|(
name|targetontology
operator|.
name|getOWLOntologyManager
argument_list|()
argument_list|,
name|targetontology
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|setx
init|=
name|targetontology
operator|.
name|getAxioms
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|OWLAxiom
argument_list|>
name|iter
init|=
name|setx
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLAxiom
name|axiom
init|=
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|axiom
operator|.
name|toString
argument_list|()
operator|.
name|contains
argument_list|(
literal|"Equivalent"
argument_list|)
condition|)
block|{
name|targetontology
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|removeAxiom
argument_list|(
name|targetontology
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|targetontology
return|;
block|}
block|}
end_class

end_unit

