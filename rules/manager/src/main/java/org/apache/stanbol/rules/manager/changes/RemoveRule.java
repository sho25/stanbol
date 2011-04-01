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
name|rules
operator|.
name|manager
operator|.
name|changes
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Vector
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Level
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Logger
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|RuleStore
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
name|IRI
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
name|OWLClass
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
name|OWLNamedIndividual
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
name|OWLObjectProperty
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
name|util
operator|.
name|OWLEntityRemover
import|;
end_import

begin_comment
comment|/**  * This class will remove a rule from the KReSRuleStore used as input.<br/>  * The KReSRuleStore object used as input is not changed and to get the new modified KReSRuleStore there is the method getStore().<br/>  * If the rule name or IRI is not already inside the KReSRuleStore an error is lunched and the process stopped.  *  */
end_comment

begin_class
specifier|public
class|class
name|RemoveRule
block|{
specifier|private
name|OWLOntology
name|owlmodel
decl_stmt|;
specifier|private
name|OWLOntologyManager
name|owlmanager
decl_stmt|;
specifier|private
name|OWLDataFactory
name|factory
decl_stmt|;
specifier|private
name|String
name|owlIDrmi
decl_stmt|;
specifier|private
name|String
name|owlID
decl_stmt|;
specifier|private
name|RuleStore
name|storeaux
decl_stmt|;
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
comment|/**      * To clone ontology with all its axioms and imports declaration      *      * @param inowl {The onotlogy to be cloned}      * @return {An ontology with the same characteristics}      */
specifier|private
name|void
name|cloneOntology
parameter_list|(
name|OWLOntology
name|inowl
parameter_list|)
block|{
comment|//Clone the targetontology
try|try
block|{
name|this
operator|.
name|owlmodel
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|createOntology
argument_list|(
name|inowl
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
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
comment|//Add axioms
name|owlmanager
operator|.
name|addAxioms
argument_list|(
name|owlmodel
argument_list|,
name|inowl
operator|.
name|getAxioms
argument_list|()
argument_list|)
expr_stmt|;
comment|//Add import declaration
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|additions
init|=
name|createImportList
argument_list|(
name|inowl
argument_list|,
name|owlmodel
argument_list|)
decl_stmt|;
if|if
condition|(
name|additions
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
name|owlmanager
operator|.
name|applyChanges
argument_list|(
name|additions
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**     * Constructor, the input is a KReSRuleStore object.<br/>     * N.B. To get the new KReSRuleStore object there is the method getStore();     * @param store {The KReSRuleStore where there are the added rules and recipes.}     */
specifier|public
name|RemoveRule
parameter_list|(
name|RuleStore
name|store
parameter_list|)
block|{
name|this
operator|.
name|storeaux
operator|=
name|store
expr_stmt|;
name|cloneOntology
argument_list|(
name|storeaux
operator|.
name|getOntology
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|factory
operator|=
name|owlmanager
operator|.
name|getOWLDataFactory
argument_list|()
expr_stmt|;
name|this
operator|.
name|owlIDrmi
operator|=
literal|"http://kres.iks-project.eu/ontology/meta/rmi.owl#"
expr_stmt|;
name|this
operator|.
name|owlID
operator|=
name|owlmodel
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
operator|+
literal|"#"
expr_stmt|;
block|}
comment|/**     * Constructor, the input is a KReSRuleStore object.<br/>     * N.B. To get the new KReSRuleStore object there is the method getStore();     * @param store {The KReSRuleStore where there are the added rules and recipes.}     */
specifier|public
name|RemoveRule
parameter_list|(
name|RuleStore
name|store
parameter_list|,
name|String
name|owlid
parameter_list|)
block|{
name|this
operator|.
name|storeaux
operator|=
name|store
expr_stmt|;
name|cloneOntology
argument_list|(
name|storeaux
operator|.
name|getOntology
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|factory
operator|=
name|owlmanager
operator|.
name|getOWLDataFactory
argument_list|()
expr_stmt|;
name|this
operator|.
name|owlID
operator|=
name|owlid
expr_stmt|;
name|this
operator|.
name|owlIDrmi
operator|=
literal|"http://kres.iks-project.eu/ontology/meta/rmi.owl#"
expr_stmt|;
block|}
comment|/**     * To remove a rule with a given name.     *     * @param ruleName {The rule string name.}     * @return {Return true is the process finished without errors.}     */
specifier|public
name|boolean
name|removeRule
parameter_list|(
name|String
name|ruleName
parameter_list|)
block|{
name|boolean
name|ok
init|=
literal|false
decl_stmt|;
name|OWLClass
name|ontocls
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|owlIDrmi
operator|+
literal|"KReSRule"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|ontoind
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|owlID
operator|+
name|ruleName
argument_list|)
argument_list|)
decl_stmt|;
name|OWLObjectProperty
name|hasrule
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|owlIDrmi
operator|+
literal|"hasRule"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLEntityRemover
name|remover
init|=
operator|new
name|OWLEntityRemover
argument_list|(
name|owlmanager
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
name|owlmodel
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|owlmodel
operator|.
name|containsAxiom
argument_list|(
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|ontocls
argument_list|,
name|ontoind
argument_list|)
argument_list|)
condition|)
block|{
name|ontoind
operator|.
name|accept
argument_list|(
name|remover
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|list
init|=
name|remover
operator|.
name|getChanges
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|OWLOntologyChange
argument_list|>
name|iter
init|=
name|list
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|usage
init|=
operator|new
name|Vector
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
name|ax
init|=
name|iter
operator|.
name|next
argument_list|()
operator|.
name|getAxiom
argument_list|()
decl_stmt|;
if|if
condition|(
name|ax
operator|.
name|getObjectPropertiesInSignature
argument_list|()
operator|.
name|contains
argument_list|(
name|hasrule
argument_list|)
condition|)
block|{
name|usage
operator|.
name|add
argument_list|(
name|Arrays
operator|.
name|toString
argument_list|(
name|ax
operator|.
name|getIndividualsInSignature
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|usage
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ok
operator|=
literal|true
expr_stmt|;
name|owlmanager
operator|.
name|applyChanges
argument_list|(
name|remover
operator|.
name|getChanges
argument_list|()
argument_list|)
expr_stmt|;
name|remover
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The rule cannot be deleted because is used by some recipes. Pleas check the following recipes:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|usage
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|ok
operator|=
literal|false
expr_stmt|;
return|return
operator|(
name|ok
operator|)
return|;
block|}
if|if
condition|(
name|owlmodel
operator|.
name|containsAxiom
argument_list|(
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|ontocls
argument_list|,
name|ontoind
argument_list|)
argument_list|)
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Some error occurs during deletion."
argument_list|)
expr_stmt|;
name|ok
operator|=
literal|false
expr_stmt|;
return|return
operator|(
name|ok
operator|)
return|;
block|}
else|else
block|{
name|ok
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The rule with name "
operator|+
name|ruleName
operator|+
literal|" is not inside the ontology. Pleas check the name."
argument_list|)
expr_stmt|;
name|ok
operator|=
literal|false
expr_stmt|;
return|return
operator|(
name|ok
operator|)
return|;
block|}
if|if
condition|(
name|ok
condition|)
name|this
operator|.
name|storeaux
operator|.
name|setStore
argument_list|(
name|owlmodel
argument_list|)
expr_stmt|;
return|return
name|ok
return|;
block|}
comment|/**     * To remove a rule with a given IRI.     *     * @param ruleName {The rule complete IRI.}     * @return {Return true is the process finished without errors.}     */
specifier|public
name|boolean
name|removeRule
parameter_list|(
name|IRI
name|ruleName
parameter_list|)
block|{
name|boolean
name|ok
init|=
literal|false
decl_stmt|;
name|OWLClass
name|ontocls
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|owlIDrmi
operator|+
literal|"KReSRule"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|ontoind
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|ruleName
argument_list|)
decl_stmt|;
name|OWLObjectProperty
name|hasrule
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|owlIDrmi
operator|+
literal|"hasRule"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLEntityRemover
name|remover
init|=
operator|new
name|OWLEntityRemover
argument_list|(
name|owlmanager
argument_list|,
name|Collections
operator|.
name|singleton
argument_list|(
name|owlmodel
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|owlmodel
operator|.
name|containsAxiom
argument_list|(
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|ontocls
argument_list|,
name|ontoind
argument_list|)
argument_list|)
condition|)
block|{
name|ontoind
operator|.
name|accept
argument_list|(
name|remover
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|list
init|=
name|remover
operator|.
name|getChanges
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|OWLOntologyChange
argument_list|>
name|iter
init|=
name|list
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|usage
init|=
operator|new
name|Vector
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
name|ax
init|=
name|iter
operator|.
name|next
argument_list|()
operator|.
name|getAxiom
argument_list|()
decl_stmt|;
if|if
condition|(
name|ax
operator|.
name|getObjectPropertiesInSignature
argument_list|()
operator|.
name|contains
argument_list|(
name|hasrule
argument_list|)
condition|)
block|{
name|usage
operator|.
name|add
argument_list|(
name|Arrays
operator|.
name|toString
argument_list|(
name|ax
operator|.
name|getIndividualsInSignature
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|usage
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ok
operator|=
literal|true
expr_stmt|;
name|owlmanager
operator|.
name|applyChanges
argument_list|(
name|remover
operator|.
name|getChanges
argument_list|()
argument_list|)
expr_stmt|;
name|remover
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The rule cannot be deleted because is used by some recipes. Pleas check the following recipes:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|usage
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|ok
operator|=
literal|false
expr_stmt|;
return|return
operator|(
name|ok
operator|)
return|;
block|}
if|if
condition|(
name|owlmodel
operator|.
name|containsAxiom
argument_list|(
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|ontocls
argument_list|,
name|ontoind
argument_list|)
argument_list|)
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Some error occurs during deletion."
argument_list|)
expr_stmt|;
name|ok
operator|=
literal|false
expr_stmt|;
return|return
operator|(
name|ok
operator|)
return|;
block|}
else|else
block|{
name|ok
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The rule with name "
operator|+
name|ruleName
operator|+
literal|" is not inside the ontology. Pleas check the name."
argument_list|)
expr_stmt|;
name|ok
operator|=
literal|false
expr_stmt|;
return|return
operator|(
name|ok
operator|)
return|;
block|}
if|if
condition|(
name|ok
condition|)
name|this
operator|.
name|storeaux
operator|.
name|setStore
argument_list|(
name|owlmodel
argument_list|)
expr_stmt|;
return|return
name|ok
return|;
block|}
comment|/**      * Get the KReSRuleStore filled with rules and recipes     *      * @return {A KReSRuleStore object with the stored rules and recipes.}      */
specifier|public
name|RuleStore
name|getStore
parameter_list|()
block|{
return|return
name|this
operator|.
name|storeaux
return|;
block|}
comment|/**       * Delete a single rule from a recipe       * @param ruleName {The IRI of the rule}       * @param recipeName {The IRI of the recipe}       * @return {True if the operation works.}       */
specifier|public
name|boolean
name|removeRuleFromRecipe
parameter_list|(
name|IRI
name|ruleName
parameter_list|,
name|IRI
name|recipeName
parameter_list|)
block|{
name|boolean
name|ok
init|=
literal|false
decl_stmt|;
name|OWLClass
name|ontoclsrule
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|owlIDrmi
operator|+
literal|"KReSRule"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLClass
name|ontoclsrecipe
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|owlIDrmi
operator|+
literal|"Recipe"
argument_list|)
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|rule
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|ruleName
argument_list|)
decl_stmt|;
name|OWLNamedIndividual
name|recipe
init|=
name|factory
operator|.
name|getOWLNamedIndividual
argument_list|(
name|recipeName
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|owlmodel
operator|.
name|containsAxiom
argument_list|(
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|ontoclsrule
argument_list|,
name|rule
argument_list|)
argument_list|)
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The rule with name "
operator|+
name|ruleName
operator|+
literal|" is not inside the ontology. Pleas check the name."
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|owlmodel
operator|.
name|containsAxiom
argument_list|(
name|factory
operator|.
name|getOWLClassAssertionAxiom
argument_list|(
name|ontoclsrecipe
argument_list|,
name|recipe
argument_list|)
argument_list|)
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The rule with name "
operator|+
name|recipeName
operator|+
literal|" is not inside the ontology. Pleas check the name."
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|false
return|;
block|}
comment|//Get the recipe
name|GetRecipe
name|getrecipe
init|=
operator|new
name|GetRecipe
argument_list|(
name|storeaux
argument_list|)
decl_stmt|;
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|String
argument_list|>
name|map
init|=
name|getrecipe
operator|.
name|getRecipe
argument_list|(
name|recipeName
argument_list|)
decl_stmt|;
name|String
index|[]
name|sequence
init|=
name|map
operator|.
name|get
argument_list|(
name|recipeName
argument_list|)
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|IRI
argument_list|>
name|ruleseq
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|seq
range|:
name|sequence
control|)
name|ruleseq
operator|.
name|add
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|seq
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|desc
init|=
name|getrecipe
operator|.
name|getDescription
argument_list|(
name|recipeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|desc
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Description for "
operator|+
name|recipeName
operator|+
literal|" not found"
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
if|if
condition|(
name|ruleseq
operator|.
name|contains
argument_list|(
name|ruleName
argument_list|)
condition|)
name|ruleseq
operator|.
name|remove
argument_list|(
name|ruleseq
operator|.
name|indexOf
argument_list|(
name|ruleName
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The rule with name "
operator|+
name|ruleName
operator|+
literal|" is not inside the ontology. Pleas check the name."
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
comment|//Remove the old recipe
name|RemoveRecipe
name|remove
decl_stmt|;
try|try
block|{
name|remove
operator|=
operator|new
name|RemoveRecipe
argument_list|(
name|storeaux
argument_list|)
expr_stmt|;
name|ok
operator|=
name|remove
operator|.
name|removeRecipe
argument_list|(
name|recipeName
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|Logger
operator|.
name|getLogger
argument_list|(
name|RemoveRule
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|log
argument_list|(
name|Level
operator|.
name|SEVERE
argument_list|,
literal|null
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|ok
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Some errors occured when delete "
operator|+
name|ruleName
operator|+
literal|" in recipe "
operator|+
name|recipeName
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
comment|//Add the recipe with without the specified rule
name|AddRecipe
name|newadd
init|=
operator|new
name|AddRecipe
argument_list|(
name|storeaux
argument_list|)
decl_stmt|;
if|if
condition|(
name|ruleseq
operator|.
name|isEmpty
argument_list|()
condition|)
name|ok
operator|=
name|newadd
operator|.
name|addSimpleRecipe
argument_list|(
name|recipeName
argument_list|,
name|desc
argument_list|)
expr_stmt|;
else|else
name|ok
operator|=
name|newadd
operator|.
name|addRecipe
argument_list|(
name|recipeName
argument_list|,
name|ruleseq
argument_list|,
name|desc
argument_list|)
expr_stmt|;
if|if
condition|(
name|ok
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

