begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
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
name|io
operator|.
name|RDFXMLOntologyFormat
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
name|io
operator|.
name|StringDocumentTarget
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
name|AddAxiom
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
name|model
operator|.
name|OWLOntologyStorageException
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
name|UnknownOWLOntologyException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|ontology
operator|.
name|OntologyInputSource
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
operator|.
name|io
operator|.
name|RootOntologySource
import|;
end_import

begin_comment
comment|/**  * A set of static utility methods for managing ontologies in KReS.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|OntologyUtils
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|OntologyUtils
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// /**
comment|// * Creates an ontology with the specified IRI and only the import
comment|// statements
comment|// * for linking to all the ontologies in the subtrees set. Useful for
comment|// running
comment|// * reasoners on an ontology set, as reasoners are instantiated on a single
comment|// * ontology.
comment|// *
comment|// * @param rootIri
comment|// * @param subtrees
comment|// * @return
comment|// */
comment|// public static OWLOntology buildImportTree(IRI rootIri,
comment|// Set<OWLOntology> subtrees, OWLOntologyManager mgr) {
comment|// OWLOntology root = null;
comment|// try {
comment|// root = rootIri != null ? mgr.createOntology(rootIri) : mgr
comment|// .createOntology();
comment|// } catch (OWLOntologyAlreadyExistsException e) {
comment|// root = mgr.getOntology(rootIri);
comment|// } catch (OWLOntologyCreationException e) {
comment|// e.printStackTrace();
comment|// return root;
comment|// }
comment|// return buildImportTree(root, subtrees, mgr);
comment|// }
specifier|public
specifier|static
name|OWLOntology
name|appendOntology
parameter_list|(
name|OntologyInputSource
name|parentSrc
parameter_list|,
name|OntologyInputSource
name|childSrc
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|)
block|{
return|return
name|appendOntology
argument_list|(
name|parentSrc
argument_list|,
name|childSrc
argument_list|,
name|ontologyManager
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|OWLOntology
name|appendOntology
parameter_list|(
name|OntologyInputSource
name|parentSrc
parameter_list|,
name|OntologyInputSource
name|childSrc
parameter_list|)
block|{
return|return
name|appendOntology
argument_list|(
name|parentSrc
argument_list|,
name|childSrc
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|OWLOntology
name|appendOntology
parameter_list|(
name|OntologyInputSource
name|parentSrc
parameter_list|,
name|OntologyInputSource
name|childSrc
parameter_list|,
name|IRI
name|rewritePrefix
parameter_list|)
block|{
return|return
name|appendOntology
argument_list|(
name|parentSrc
argument_list|,
name|childSrc
argument_list|,
literal|null
argument_list|,
name|rewritePrefix
argument_list|)
return|;
block|}
comment|/** 	 * This method appends one ontology (the child) to another (the parent) by 	 * proceeding as follows. If a physical URI can be obtained from the child 	 * source, an import statement using that physical URI will be added to the 	 * parent ontology, otherwise all the axioms from the child ontology will be 	 * copied to the parent.<br> 	 * Note: the ontology manager will not load additional ontologies. 	 *  	 * @param parentSrc 	 *            must exist! 	 * @param childSrc 	 * @param ontologyManager 	 *            can be null (e.g. when one does not want changes to be 	 *            immediately reflected in their ontology manager), in which 	 *            case a temporary ontology manager will be used. 	 * @param rewritePrefix 	 *            . if not null, import statements will be generated in the form 	 *<tt>rewritePrefix/child_ontology_logical_IRI</tt>. It can be 	 *            used for relocating the ontology document file elsewhere. 	 * @return the parent with the appended child 	 */
specifier|public
specifier|static
name|OWLOntology
name|appendOntology
parameter_list|(
name|OntologyInputSource
name|parentSrc
parameter_list|,
name|OntologyInputSource
name|childSrc
parameter_list|,
name|OWLOntologyManager
name|ontologyManager
parameter_list|,
name|IRI
name|rewritePrefix
parameter_list|)
block|{
if|if
condition|(
name|ontologyManager
operator|==
literal|null
condition|)
name|ontologyManager
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
expr_stmt|;
name|OWLDataFactory
name|factory
init|=
name|ontologyManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|OWLOntology
name|oParent
init|=
name|parentSrc
operator|.
name|getRootOntology
argument_list|()
decl_stmt|;
name|OWLOntology
name|oChild
init|=
name|childSrc
operator|.
name|getRootOntology
argument_list|()
decl_stmt|;
comment|// Named ontology with a provided absolute prefix. Use name and prefix
comment|// for creating an new import statement.
if|if
condition|(
operator|!
name|oChild
operator|.
name|isAnonymous
argument_list|()
operator|&&
name|rewritePrefix
operator|!=
literal|null
comment|/*&& rewritePrefix.isAbsolute() */
condition|)
block|{
name|IRI
name|impIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|rewritePrefix
operator|+
literal|"/"
operator|+
name|oChild
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
argument_list|)
decl_stmt|;
name|OWLImportsDeclaration
name|imp
init|=
name|factory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|impIri
argument_list|)
decl_stmt|;
name|ontologyManager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddImport
argument_list|(
name|oParent
argument_list|,
name|imp
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Anonymous, with physicalIRI. A plain import statement is added.
elseif|else
if|if
condition|(
name|childSrc
operator|.
name|hasPhysicalIRI
argument_list|()
condition|)
block|{
name|OWLImportsDeclaration
name|imp
init|=
name|factory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|childSrc
operator|.
name|getPhysicalIRI
argument_list|()
argument_list|)
decl_stmt|;
name|ontologyManager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddImport
argument_list|(
name|oParent
argument_list|,
name|imp
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Anonymous and no physical IRI (e.g. in memory). Copy all axioms and
comment|// import statements.
else|else
block|{
name|ontologyManager
operator|.
name|addAxioms
argument_list|(
name|oParent
argument_list|,
name|oChild
operator|.
name|getAxioms
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLImportsDeclaration
name|imp
range|:
name|oChild
operator|.
name|getImportsDeclarations
argument_list|()
control|)
name|ontologyManager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddImport
argument_list|(
name|oParent
argument_list|,
name|factory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|imp
operator|.
name|getIRI
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|oParent
return|;
block|}
specifier|public
specifier|static
name|OWLOntology
name|buildImportTree
parameter_list|(
name|OntologyInputSource
name|rootSrc
parameter_list|,
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|subtrees
parameter_list|)
block|{
return|return
name|buildImportTree
argument_list|(
name|rootSrc
operator|.
name|getRootOntology
argument_list|()
argument_list|,
name|subtrees
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param rootSrc 	 * @param subtrees 	 * @param mgr 	 * @return 	 */
specifier|public
specifier|static
name|OWLOntology
name|buildImportTree
parameter_list|(
name|OntologyInputSource
name|rootSrc
parameter_list|,
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|subtrees
parameter_list|,
name|OWLOntologyManager
name|mgr
parameter_list|)
block|{
return|return
name|buildImportTree
argument_list|(
name|rootSrc
operator|.
name|getRootOntology
argument_list|()
argument_list|,
name|subtrees
argument_list|,
name|mgr
argument_list|)
return|;
block|}
comment|/** 	 * Non-recursively adds import statements to the root ontology so that it is 	 * directly linked to all the ontologies in the subtrees set. 	 *  	 * @param root 	 *            the ontology to which import subtrees should be appended. If 	 *            null, a runtime exception will be thrown. 	 * @param subtrees 	 *            the set of target ontologies for import statements. These can 	 *            in turn be importing other ontologies, hence the 	 *&quot;subtree&quot; notation. A single statement will be added 	 *            for each member of this set. 	 * @return the same input ontology as defined in<code>root</code>, but with 	 *         the added import statements. 	 */
specifier|public
specifier|static
name|OWLOntology
name|buildImportTree
parameter_list|(
name|OWLOntology
name|root
parameter_list|,
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|subtrees
parameter_list|)
block|{
return|return
name|buildImportTree
argument_list|(
name|root
argument_list|,
name|subtrees
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Non-recursively adds import statements to the root ontology so that it is 	 * directly linked to all the ontologies in the subtrees set. 	 *  	 * @param parent 	 *            the ontology to which import subtrees should be appended. If 	 *            null, a runtime exception will be thrown. 	 * @param subtrees 	 *            the set of target ontologies for import statements. These can 	 *            in turn be importing other ontologies, hence the 	 *&quot;subtree&quot; notation. A single statement will be added 	 *            for each member of this set. 	 * @param mgr 	 *            the OWL ontology manager to use for constructing the import 	 *            tree. If null, an internal one will be used instead, otherwise 	 *            an existing ontology manager can be used e.g. for extracting 	 *            import statements from its IRI mappers or known ontologies. 	 *            Note that the supplied manager will<i>never</i> try to load 	 *            any ontologies, even when they are unknown. 	 * @return the same input ontology as defined in<code>root</code>, but with 	 *         the added import statements. 	 */
specifier|public
specifier|static
name|OWLOntology
name|buildImportTree
parameter_list|(
name|OWLOntology
name|parent
parameter_list|,
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|subtrees
parameter_list|,
name|OWLOntologyManager
name|mgr
parameter_list|)
block|{
if|if
condition|(
name|parent
operator|==
literal|null
condition|)
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"Cannot append import trees to a nonexistent ontology."
argument_list|)
throw|;
comment|// If no manager was supplied, use a temporary one.
if|if
condition|(
name|mgr
operator|==
literal|null
condition|)
name|mgr
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
expr_stmt|;
name|OWLDataFactory
name|owlFactory
init|=
name|mgr
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|changes
init|=
operator|new
name|LinkedList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLOntology
name|o
range|:
name|subtrees
control|)
block|{
name|IRI
name|importIri
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|/* 				 * First query the manager, as it could know the physical 				 * location of anonymous ontologies, if previously loaded or 				 * IRI-mapped. 				 */
name|importIri
operator|=
name|mgr
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnknownOWLOntologyException
name|ex
parameter_list|)
block|{
comment|/* 				 * Otherwise, ask the ontology itself (the location of an 				 * anonymous ontology may have been known at creation/loading 				 * time, even if another manager built it.) 				 */
name|importIri
operator|=
name|o
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getDefaultDocumentIRI
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"KReS :: Exception caught during tree building. Skipping import of ontology "
operator|+
name|o
operator|.
name|getOntologyID
argument_list|()
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
comment|/* 				 * It is still possible that an imported ontology is anonymous 				 * but has no physical document IRI (for example, because it was 				 * only generated in-memory but not stored). In this case it is 				 * necessary (and generally safe) to copy all its axioms and 				 * import statements to the parent ontology, or else it is lost. 				 */
if|if
condition|(
name|o
operator|.
name|isAnonymous
argument_list|()
operator|&&
name|importIri
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"KReS :: [NONFATAL] Anonymous import target "
operator|+
name|o
operator|.
name|getOntologyID
argument_list|()
operator|+
literal|" not mapped to physical IRI. Will add extracted axioms to parent ontology."
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLImportsDeclaration
name|im
range|:
name|o
operator|.
name|getImportsDeclarations
argument_list|()
control|)
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|parent
argument_list|,
name|im
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLAxiom
name|im
range|:
name|o
operator|.
name|getAxioms
argument_list|()
control|)
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|parent
argument_list|,
name|im
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|importIri
operator|!=
literal|null
condition|)
block|{
comment|// An anonymous ontology can still be imported if it has a
comment|// valid document IRI.
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|parent
argument_list|,
name|owlFactory
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|importIri
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// End subtrees cycle.
comment|// All possible error causes should have been dealt with by now, but we
comment|// apply the changes one by one, just in case.
for|for
control|(
name|OWLOntologyChange
name|im
range|:
name|changes
control|)
try|try
block|{
name|mgr
operator|.
name|applyChange
argument_list|(
name|im
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"KReS :: Exception caught during tree building. Skipping import"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
continue|continue;
block|}
comment|// mgr.applyChanges(changes);
return|return
name|parent
return|;
block|}
specifier|public
specifier|static
name|OWLOntology
name|buildImportTree
parameter_list|(
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|subtrees
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
return|return
name|buildImportTree
argument_list|(
name|subtrees
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|OWLOntology
name|buildImportTree
parameter_list|(
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|subtrees
parameter_list|,
name|OWLOntologyManager
name|mgr
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
return|return
name|buildImportTree
argument_list|(
operator|new
name|RootOntologySource
argument_list|(
name|mgr
operator|.
name|createOntology
argument_list|()
argument_list|)
argument_list|,
name|subtrees
argument_list|,
name|mgr
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|void
name|printOntology
parameter_list|(
name|OWLOntology
name|o
parameter_list|,
name|PrintStream
name|printer
parameter_list|)
block|{
name|OWLOntologyManager
name|mgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|StringDocumentTarget
name|tgt
init|=
operator|new
name|StringDocumentTarget
argument_list|()
decl_stmt|;
try|try
block|{
name|mgr
operator|.
name|saveOntology
argument_list|(
name|o
argument_list|,
operator|new
name|RDFXMLOntologyFormat
argument_list|()
argument_list|,
name|tgt
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|(
name|printer
argument_list|)
expr_stmt|;
block|}
name|printer
operator|.
name|println
argument_list|(
name|tgt
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

