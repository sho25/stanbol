begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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
name|util
operator|.
name|ArrayList
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
name|LinkedList
import|;
end_import

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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|AddImport
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
name|OWLDataFactory
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
name|OWLImportsDeclaration
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
name|OWLOntologyChange
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
name|InferredAxiomGenerator
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
name|InferredClassAssertionAxiomGenerator
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
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|util
operator|.
name|InferredSubClassAxiomGenerator
import|;
end_import

begin_comment
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|RunReasoner
block|{
specifier|private
name|OWLOntology
name|owlmodel
decl_stmt|;
specifier|private
name|OWLReasoner
name|loadreasoner
decl_stmt|;
specifier|private
name|OWLOntologyManager
name|owlmanager
decl_stmt|;
comment|/**      * This create an object where to perform reasoner tasks as consistency check, classification and general inference.      *      * @param reasoner {The OWLReasoner object containing the ontology to be inferred.}      */
specifier|public
name|RunReasoner
parameter_list|(
name|OWLReasoner
name|reasoner
parameter_list|)
block|{
name|this
operator|.
name|owlmodel
operator|=
name|reasoner
operator|.
name|getRootOntology
argument_list|()
expr_stmt|;
name|this
operator|.
name|owlmanager
operator|=
name|owlmodel
operator|.
name|getOWLOntologyManager
argument_list|()
expr_stmt|;
comment|//Create the reasoner
name|this
operator|.
name|loadreasoner
operator|=
name|reasoner
expr_stmt|;
comment|//Prepare the reasoner
comment|//this.loadreasoner.prepareReasoner();
block|}
comment|/**      * To create a list of imported ontlogy to be added as import declarations      *      * @param inowl {Input ontology where to get the import declarations}      * @return {A list of declarations}      */
specifier|private
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|createImportList
parameter_list|(
name|OWLOntology
name|inowl
parameter_list|,
name|OWLOntology
name|toadd
parameter_list|)
block|{
name|Iterator
argument_list|<
name|OWLOntology
argument_list|>
name|importedonto
init|=
name|inowl
operator|.
name|getDirectImports
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|additions
init|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|auxfactory
init|=
name|inowl
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
while|while
condition|(
name|importedonto
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLOntology
name|auxonto
init|=
name|importedonto
operator|.
name|next
argument_list|()
decl_stmt|;
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|toadd
argument_list|,
name|auxfactory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|auxonto
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|auxonto
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|additions
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|Iterator
argument_list|<
name|OWLImportsDeclaration
argument_list|>
name|importedontob
init|=
name|inowl
operator|.
name|getImportsDeclarations
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|additions
operator|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
expr_stmt|;
name|auxfactory
operator|=
name|inowl
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
expr_stmt|;
while|while
condition|(
name|importedontob
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OWLImportsDeclaration
name|auxontob
init|=
name|importedontob
operator|.
name|next
argument_list|()
decl_stmt|;
name|additions
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|toadd
argument_list|,
name|auxontob
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|additions
return|;
block|}
comment|/**      * This method will check the consistence of the ontology.      *      * @return {A boolean that is true if the ontology is consistence otherwise the value is false.}      */
specifier|public
name|boolean
name|isConsistent
parameter_list|()
block|{
name|boolean
name|ok
init|=
literal|false
decl_stmt|;
name|ok
operator|=
name|loadreasoner
operator|.
name|isConsistent
argument_list|()
expr_stmt|;
return|return
operator|(
name|ok
operator|)
return|;
block|}
comment|/**      * This method will get class and sub-class to classify an individual.      *      * @param inferredAxiomsOntology {It is an object of type OWLOntology where to put the inferred axioms.}      * @return {This is an object of type OWLOntology that contains the inferred Axioms.}      */
specifier|public
name|OWLOntology
name|runClassifyInference
parameter_list|(
name|OWLOntology
name|inferredAxiomsOntology
parameter_list|)
block|{
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
init|=
operator|new
name|ArrayList
argument_list|<
name|InferredAxiomGenerator
argument_list|<
name|?
extends|extends
name|OWLAxiom
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|generators
operator|.
name|add
argument_list|(
operator|new
name|InferredSubClassAxiomGenerator
argument_list|()
argument_list|)
expr_stmt|;
name|generators
operator|.
name|add
argument_list|(
operator|new
name|InferredClassAssertionAxiomGenerator
argument_list|()
argument_list|)
expr_stmt|;
name|InferredOntologyGenerator
name|ioghermit
init|=
operator|new
name|InferredOntologyGenerator
argument_list|(
name|loadreasoner
argument_list|,
name|generators
argument_list|)
decl_stmt|;
name|ioghermit
operator|.
name|fillOntology
argument_list|(
name|owlmanager
argument_list|,
name|inferredAxiomsOntology
argument_list|)
expr_stmt|;
return|return
name|inferredAxiomsOntology
return|;
block|}
comment|/**      * This method will get class and sub-class to classify an individual.      *      * @return {Return an ontology with the new inference axioms inside the ontology specified in the KReSCreateReasoner object.}      */
specifier|public
name|OWLOntology
name|runClassifyInference
parameter_list|()
block|{
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
init|=
operator|new
name|ArrayList
argument_list|<
name|InferredAxiomGenerator
argument_list|<
name|?
extends|extends
name|OWLAxiom
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|generators
operator|.
name|add
argument_list|(
operator|new
name|InferredSubClassAxiomGenerator
argument_list|()
argument_list|)
expr_stmt|;
name|generators
operator|.
name|add
argument_list|(
operator|new
name|InferredClassAssertionAxiomGenerator
argument_list|()
argument_list|)
expr_stmt|;
name|InferredOntologyGenerator
name|ioghermit
init|=
operator|new
name|InferredOntologyGenerator
argument_list|(
name|loadreasoner
argument_list|,
name|generators
argument_list|)
decl_stmt|;
name|ioghermit
operator|.
name|fillOntology
argument_list|(
name|owlmanager
argument_list|,
name|owlmodel
argument_list|)
expr_stmt|;
return|return
name|owlmodel
return|;
block|}
comment|/**     * This method will perform a general inference with the object properties specified in the ontology given in the KReSCreateREasoner.     *     * @return {Return an ontology with the new inference axioms inside the ontology specified in the KReSCreateReasoner object.}     */
specifier|public
name|OWLOntology
name|runGeneralInference
parameter_list|()
block|{
name|InferredOntologyGenerator
name|ioghermit
init|=
operator|new
name|InferredOntologyGenerator
argument_list|(
name|loadreasoner
argument_list|)
decl_stmt|;
name|ioghermit
operator|.
name|fillOntology
argument_list|(
name|owlmanager
argument_list|,
name|owlmodel
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|setx
init|=
name|owlmodel
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
name|owlmodel
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|removeAxiom
argument_list|(
name|owlmodel
argument_list|,
name|axiom
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|owlmodel
return|;
block|}
comment|/**      * This method will perform a general inference with the object properties specified in the ontology and will insert the inferences in a new ontology.      *      * @param newmodel {An OWLOntology objject where to insert the inferences.}      * @return {This is an object of type OWLOntology that contains the inferred Axioms.}      */
specifier|public
name|OWLOntology
name|runGeneralInference
parameter_list|(
name|OWLOntology
name|newmodel
parameter_list|)
block|{
name|InferredOntologyGenerator
name|ioghermit
init|=
operator|new
name|InferredOntologyGenerator
argument_list|(
name|loadreasoner
argument_list|)
decl_stmt|;
name|ioghermit
operator|.
name|fillOntology
argument_list|(
name|owlmanager
argument_list|,
name|newmodel
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|additions
init|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|additions
operator|=
name|createImportList
argument_list|(
name|owlmodel
argument_list|,
name|newmodel
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|additions
operator|.
name|isEmpty
argument_list|()
condition|)
name|newmodel
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|applyChanges
argument_list|(
name|additions
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
return|return
name|newmodel
return|;
block|}
block|}
end_class

end_unit

