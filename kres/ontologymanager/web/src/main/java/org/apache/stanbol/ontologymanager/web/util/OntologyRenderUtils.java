begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|web
operator|.
name|util
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ONManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|coode
operator|.
name|owlapi
operator|.
name|manchesterowlsyntax
operator|.
name|ManchesterOWLSyntaxOntologyFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|coode
operator|.
name|owlapi
operator|.
name|turtle
operator|.
name|TurtleOntologyFormat
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
name|OWLFunctionalSyntaxOntologyFormat
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
name|OWLXMLOntologyFormat
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
name|OWLOntologyFormat
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
name|apache
operator|.
name|stanbol
operator|.
name|kres
operator|.
name|jersey
operator|.
name|format
operator|.
name|KRFormat
import|;
end_import

begin_comment
comment|/**  * Contains hacks to regular ontology renderers with replacements for input statements.  *   * @author alessandro  *  */
end_comment

begin_class
specifier|public
class|class
name|OntologyRenderUtils
block|{
comment|/** 	 * TODO : make a writer for this. 	 *  	 * @param ont 	 * @param format 	 * @return 	 * @throws OWLOntologyStorageException 	 */
specifier|public
specifier|static
name|String
name|renderOntology
parameter_list|(
name|OWLOntology
name|ont
parameter_list|,
name|OWLOntologyFormat
name|format
parameter_list|,
name|String
name|rewritePrefix
parameter_list|,
name|ONManager
name|onm
parameter_list|)
throws|throws
name|OWLOntologyStorageException
block|{
name|OWLOntologyManager
name|tmpmgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|df
init|=
name|tmpmgr
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
comment|// Now the hack
name|OWLOntology
name|o2
init|=
literal|null
decl_stmt|;
name|OWLOntology
name|copy
init|=
literal|null
decl_stmt|;
name|OWLOntologyManager
name|origMgr
init|=
name|ont
operator|.
name|getOWLOntologyManager
argument_list|()
decl_stmt|;
try|try
block|{
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|copy
operator|=
name|tmpmgr
operator|.
name|createOntology
argument_list|(
name|ont
operator|.
name|getOntologyID
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLAxiom
name|ax
range|:
name|ont
operator|.
name|getAxioms
argument_list|()
control|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|copy
argument_list|,
name|ax
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|OWLImportsDeclaration
name|imp
range|:
name|ont
operator|.
name|getImportsDeclarations
argument_list|()
control|)
block|{
name|OWLOntology
name|oi
init|=
name|origMgr
operator|.
name|getImportedOntology
argument_list|(
name|imp
argument_list|)
decl_stmt|;
if|if
condition|(
name|oi
operator|==
literal|null
condition|)
name|oi
operator|=
name|onm
operator|.
name|getOwlCacheManager
argument_list|()
operator|.
name|getImportedOntology
argument_list|(
name|imp
argument_list|)
expr_stmt|;
name|String
name|impiri
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|rewritePrefix
operator|!=
literal|null
condition|)
name|impiri
operator|+=
name|rewritePrefix
operator|+
literal|"/"
expr_stmt|;
if|if
condition|(
name|oi
operator|==
literal|null
condition|)
comment|// Proprio non riesci a ottenerla questa ontologia? Rinuncia
continue|continue;
if|if
condition|(
name|oi
operator|.
name|isAnonymous
argument_list|()
condition|)
name|impiri
operator|=
name|imp
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
else|else
name|impiri
operator|+=
name|oi
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
expr_stmt|;
name|OWLImportsDeclaration
name|im
init|=
name|df
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|impiri
argument_list|)
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|copy
argument_list|,
name|im
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|tmpmgr
operator|.
name|applyChanges
argument_list|(
name|changes
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e1
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e1
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|copy
operator|!=
literal|null
condition|)
name|o2
operator|=
name|copy
expr_stmt|;
else|else
name|o2
operator|=
name|ont
expr_stmt|;
name|StringDocumentTarget
name|tgt
init|=
operator|new
name|StringDocumentTarget
argument_list|()
decl_stmt|;
name|tmpmgr
operator|.
name|saveOntology
argument_list|(
name|o2
argument_list|,
name|format
argument_list|,
name|tgt
argument_list|)
expr_stmt|;
return|return
name|tgt
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|renderOntology
parameter_list|(
name|OWLOntology
name|ont
parameter_list|,
name|String
name|format
parameter_list|,
name|String
name|rewritePrefix
parameter_list|,
name|ONManager
name|onm
parameter_list|)
throws|throws
name|OWLOntologyStorageException
block|{
name|OWLOntologyManager
name|tmpmgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|df
init|=
name|tmpmgr
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
name|StringDocumentTarget
name|tgt
init|=
operator|new
name|StringDocumentTarget
argument_list|()
decl_stmt|;
comment|// Now the hack
name|OWLOntology
name|o2
init|=
literal|null
decl_stmt|;
name|OWLOntology
name|copy
init|=
literal|null
decl_stmt|;
name|OWLOntologyManager
name|origMgr
init|=
name|ont
operator|.
name|getOWLOntologyManager
argument_list|()
decl_stmt|;
try|try
block|{
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|copy
operator|=
name|tmpmgr
operator|.
name|createOntology
argument_list|(
name|ont
operator|.
name|getOntologyID
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLAxiom
name|ax
range|:
name|ont
operator|.
name|getAxioms
argument_list|()
control|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|copy
argument_list|,
name|ax
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|OWLImportsDeclaration
name|imp
range|:
name|ont
operator|.
name|getImportsDeclarations
argument_list|()
control|)
block|{
name|OWLOntology
name|oi
init|=
name|origMgr
operator|.
name|getImportedOntology
argument_list|(
name|imp
argument_list|)
decl_stmt|;
if|if
condition|(
name|oi
operator|==
literal|null
condition|)
name|oi
operator|=
name|onm
operator|.
name|getOwlCacheManager
argument_list|()
operator|.
name|getImportedOntology
argument_list|(
name|imp
argument_list|)
expr_stmt|;
name|String
name|impiri
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|rewritePrefix
operator|!=
literal|null
condition|)
name|impiri
operator|+=
name|rewritePrefix
operator|+
literal|"/"
expr_stmt|;
if|if
condition|(
name|oi
operator|==
literal|null
condition|)
comment|// Proprio non riesci a ottenerla questa ontologia? Rinuncia
continue|continue;
if|if
condition|(
name|oi
operator|.
name|isAnonymous
argument_list|()
condition|)
name|impiri
operator|=
name|imp
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
else|else
name|impiri
operator|+=
name|oi
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
expr_stmt|;
name|OWLImportsDeclaration
name|im
init|=
name|df
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|impiri
argument_list|)
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|copy
argument_list|,
name|im
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|tmpmgr
operator|.
name|applyChanges
argument_list|(
name|changes
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e1
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e1
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|copy
operator|!=
literal|null
condition|)
name|o2
operator|=
name|copy
expr_stmt|;
else|else
name|o2
operator|=
name|ont
expr_stmt|;
if|if
condition|(
name|format
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|RDF_XML
argument_list|)
condition|)
block|{
try|try
block|{
name|tmpmgr
operator|.
name|saveOntology
argument_list|(
name|o2
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
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|format
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|OWL_XML
argument_list|)
condition|)
block|{
try|try
block|{
name|tmpmgr
operator|.
name|saveOntology
argument_list|(
name|o2
argument_list|,
operator|new
name|OWLXMLOntologyFormat
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
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|format
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|MANCHESTER_OWL
argument_list|)
condition|)
block|{
try|try
block|{
name|tmpmgr
operator|.
name|saveOntology
argument_list|(
name|o2
argument_list|,
operator|new
name|ManchesterOWLSyntaxOntologyFormat
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
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|format
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|FUNCTIONAL_OWL
argument_list|)
condition|)
block|{
try|try
block|{
name|tmpmgr
operator|.
name|saveOntology
argument_list|(
name|o2
argument_list|,
operator|new
name|OWLFunctionalSyntaxOntologyFormat
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
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|format
operator|.
name|equals
argument_list|(
name|KRFormat
operator|.
name|TURTLE
argument_list|)
condition|)
block|{
try|try
block|{
name|tmpmgr
operator|.
name|saveOntology
argument_list|(
name|o2
argument_list|,
operator|new
name|TurtleOntologyFormat
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
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|tgt
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

