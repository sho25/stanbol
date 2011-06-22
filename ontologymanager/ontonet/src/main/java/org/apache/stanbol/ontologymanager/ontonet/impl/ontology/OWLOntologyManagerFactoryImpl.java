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
name|net
operator|.
name|URI
import|;
end_import

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
name|ontology
operator|.
name|OWLOntologyManagerFactory
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

begin_comment
comment|/**  * FIXME: decide on this class either implementing an interface or providing static methods.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|OWLOntologyManagerFactoryImpl
implements|implements
name|OWLOntologyManagerFactory
block|{
specifier|private
name|List
argument_list|<
name|OWLOntologyIRIMapper
argument_list|>
name|iriMappers
decl_stmt|;
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
name|OWLOntologyManagerFactoryImpl
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param dirs      */
specifier|public
name|OWLOntologyManagerFactoryImpl
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|dirs
parameter_list|)
block|{
if|if
condition|(
name|dirs
operator|!=
literal|null
condition|)
block|{
name|iriMappers
operator|=
operator|new
name|ArrayList
argument_list|<
name|OWLOntologyIRIMapper
argument_list|>
argument_list|(
name|dirs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|path
range|:
name|dirs
control|)
block|{
name|File
name|dir
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|path
operator|.
name|startsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
try|try
block|{
name|dir
operator|=
operator|new
name|File
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
name|path
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// Don't give up. It could still an absolute path.
block|}
block|}
else|else
try|try
block|{
name|dir
operator|=
operator|new
name|File
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
try|try
block|{
name|dir
operator|=
operator|new
name|File
argument_list|(
name|URI
operator|.
name|create
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e2
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to obtain a path for {}"
argument_list|,
name|dir
argument_list|,
name|e2
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|dir
operator|!=
literal|null
operator|&&
name|dir
operator|.
name|isDirectory
argument_list|()
condition|)
name|iriMappers
operator|.
name|add
argument_list|(
operator|new
name|AutoIRIMapper
argument_list|(
name|dir
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
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
name|getLocalIRIMapperList
argument_list|()
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
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|OWLOntologyIRIMapper
argument_list|>
name|getLocalIRIMapperList
parameter_list|()
block|{
return|return
name|iriMappers
return|;
block|}
block|}
end_class

end_unit

