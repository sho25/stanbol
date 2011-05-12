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
name|ontonet
operator|.
name|impl
operator|.
name|ontology
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|conf
operator|.
name|OfflineConfiguration
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
name|OWLOntologyIRIMapper
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
name|AutoIRIMapper
import|;
end_import

begin_comment
comment|/**  * FIXME: decide on this class either implementing an interface or providing static methods.  *   * @author alessandro  *  */
end_comment

begin_class
specifier|public
class|class
name|OntologyManagerFactory
block|{
specifier|private
name|OfflineConfiguration
name|config
decl_stmt|;
specifier|private
name|OWLOntologyIRIMapper
index|[]
name|iriMappers
init|=
operator|new
name|OWLOntologyIRIMapper
index|[
literal|0
index|]
decl_stmt|;
specifier|public
name|OntologyManagerFactory
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|OntologyManagerFactory
parameter_list|(
name|OfflineConfiguration
name|config
parameter_list|)
block|{
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|config
operator|!=
literal|null
condition|)
block|{
comment|// Create IRI mappers to reuse for all ontology managers.
name|iriMappers
operator|=
operator|new
name|OWLOntologyIRIMapper
index|[
name|this
operator|.
name|config
operator|.
name|getDirectories
argument_list|()
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
name|Iterator
argument_list|<
name|File
argument_list|>
name|it
init|=
name|this
operator|.
name|config
operator|.
name|getDirectories
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|j
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|iriMappers
index|[
name|j
operator|++
index|]
operator|=
operator|new
name|AutoIRIMapper
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|OWLOntologyManager
name|createOntologyManager
parameter_list|(
name|boolean
name|withOfflineSupport
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
if|if
condition|(
name|withOfflineSupport
condition|)
for|for
control|(
name|OWLOntologyIRIMapper
name|mapper
range|:
name|iriMappers
control|)
name|mgr
operator|.
name|addIRIMapper
argument_list|(
name|mapper
argument_list|)
expr_stmt|;
return|return
name|mgr
return|;
block|}
block|}
end_class

end_unit

