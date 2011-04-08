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
name|store
operator|.
name|rest
operator|.
name|resources
package|;
end_package

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
operator|.
name|TEXT_HTML
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
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
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Consumes
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|DELETE
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|FormParam
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|GET
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|POST
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Produces
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|WebApplicationException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Response
operator|.
name|Status
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|UriInfo
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|ContextHelper
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|BaseStanbolResource
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
name|store
operator|.
name|api
operator|.
name|LockManager
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
name|store
operator|.
name|api
operator|.
name|PersistenceStore
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
name|store
operator|.
name|api
operator|.
name|ResourceManager
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
name|store
operator|.
name|model
operator|.
name|AdministeredOntologies
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
name|store
operator|.
name|model
operator|.
name|OntologyMetaInformation
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
name|store
operator|.
name|rest
operator|.
name|LockManagerImp
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
name|store
operator|.
name|rest
operator|.
name|ResourceManagerImp
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
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|Viewable
import|;
end_import

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontology"
argument_list|)
specifier|public
class|class
name|Ontologies
extends|extends
name|BaseStanbolResource
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
name|Ontologies
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|VIEWABLE_PATH
init|=
literal|"/org/apache/stanbol/ontologymanager/store/rest/resources/ontologies"
decl_stmt|;
specifier|private
name|PersistenceStore
name|persistenceStore
decl_stmt|;
specifier|public
name|Ontologies
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|this
operator|.
name|persistenceStore
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|PersistenceStore
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
block|{
name|MediaType
operator|.
name|APPLICATION_XML
block|}
argument_list|)
specifier|public
name|Object
name|getClichedMessage
parameter_list|()
block|{
name|LockManager
name|lockManager
init|=
name|LockManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|lockManager
operator|.
name|obtainReadLockFor
argument_list|(
name|LockManagerImp
operator|.
name|GLOBAL_SPACE
argument_list|)
expr_stmt|;
name|Response
name|response
init|=
literal|null
decl_stmt|;
try|try
block|{
name|AdministeredOntologies
name|administeredOntologies
init|=
name|persistenceStore
operator|.
name|retrieveAdministeredOntologies
argument_list|()
decl_stmt|;
name|response
operator|=
name|Response
operator|.
name|ok
argument_list|(
name|administeredOntologies
argument_list|,
name|MediaType
operator|.
name|APPLICATION_XML_TYPE
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
finally|finally
block|{
name|lockManager
operator|.
name|releaseReadLockFor
argument_list|(
name|LockManagerImp
operator|.
name|GLOBAL_SPACE
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
literal|"application/x-www-form-urlencoded"
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|APPLICATION_XML
argument_list|)
specifier|public
name|Response
name|saveOntology
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"ontologyURI"
argument_list|)
name|String
name|ontologyURI
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"ontologyContent"
argument_list|)
name|String
name|ontologyContent
parameter_list|)
block|{
name|Response
name|response
init|=
literal|null
decl_stmt|;
name|LockManager
name|lockManager
init|=
name|LockManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|lockManager
operator|.
name|obtainReadLockFor
argument_list|(
name|LockManagerImp
operator|.
name|GLOBAL_SPACE
argument_list|)
expr_stmt|;
name|lockManager
operator|.
name|obtainWriteLockFor
argument_list|(
name|ontologyURI
argument_list|)
expr_stmt|;
try|try
block|{
name|OntologyMetaInformation
name|ontologyMetaInformation
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|ontologyContent
operator|!=
literal|null
operator|&&
operator|!
name|ontologyContent
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ontologyMetaInformation
operator|=
name|persistenceStore
operator|.
name|saveOntology
argument_list|(
name|ontologyContent
argument_list|,
name|ontologyURI
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|ontologyURI
operator|!=
literal|null
operator|&&
operator|!
name|ontologyURI
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|ontologyMetaInformation
operator|=
name|persistenceStore
operator|.
name|saveOntology
argument_list|(
operator|new
name|URL
argument_list|(
name|ontologyURI
argument_list|)
argument_list|,
name|ontologyURI
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|e
argument_list|,
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
throw|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Ontology Content or URI can not be both null"
argument_list|)
argument_list|,
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
throw|;
block|}
name|response
operator|=
name|Response
operator|.
name|ok
argument_list|(
name|ontologyMetaInformation
argument_list|,
name|MediaType
operator|.
name|APPLICATION_XML_TYPE
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error "
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
finally|finally
block|{
name|lockManager
operator|.
name|releaseReadLockFor
argument_list|(
name|LockManagerImp
operator|.
name|GLOBAL_SPACE
argument_list|)
expr_stmt|;
name|lockManager
operator|.
name|releaseWriteLockFor
argument_list|(
name|ontologyURI
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
comment|// The Java method will process HTTP DELETE requests
annotation|@
name|DELETE
specifier|public
name|void
name|delete
parameter_list|()
block|{
name|LockManager
name|lockManager
init|=
name|LockManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|lockManager
operator|.
name|obtainWriteLockFor
argument_list|(
name|LockManagerImp
operator|.
name|GLOBAL_SPACE
argument_list|)
expr_stmt|;
try|try
block|{
name|persistenceStore
operator|.
name|clearPersistenceStore
argument_list|()
expr_stmt|;
name|ResourceManager
name|resourceManager
init|=
name|ResourceManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|resourceManager
operator|.
name|clearResourceManager
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error "
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
finally|finally
block|{
name|lockManager
operator|.
name|releaseWriteLockFor
argument_list|(
name|LockManagerImp
operator|.
name|GLOBAL_SPACE
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Methods for HTML View
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|TEXT_HTML
operator|+
literal|";qs=2"
argument_list|)
specifier|public
name|Viewable
name|getViewable
parameter_list|(
annotation|@
name|Context
name|UriInfo
name|uriInfo
parameter_list|)
block|{
return|return
operator|new
name|Viewable
argument_list|(
name|VIEWABLE_PATH
argument_list|,
name|this
argument_list|)
return|;
block|}
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|";qs=2"
argument_list|)
specifier|public
name|Response
name|createAndRedirect
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"ontologyURI"
argument_list|)
name|String
name|ontologyURI
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"ontologyContent"
argument_list|)
name|String
name|ontologyContent
parameter_list|)
block|{
name|Response
name|response
init|=
name|this
operator|.
name|saveOntology
argument_list|(
name|ontologyURI
argument_list|,
name|ontologyContent
argument_list|)
decl_stmt|;
name|OntologyMetaInformation
name|ont
init|=
operator|(
operator|(
name|OntologyMetaInformation
operator|)
name|response
operator|.
name|getEntity
argument_list|()
operator|)
decl_stmt|;
try|try
block|{
return|return
name|Response
operator|.
name|seeOther
argument_list|(
name|URI
operator|.
name|create
argument_list|(
name|ont
operator|.
name|getHref
argument_list|()
argument_list|)
argument_list|)
operator|.
name|type
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
operator|.
name|header
argument_list|(
literal|"Accept"
argument_list|,
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error "
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
specifier|public
name|List
argument_list|<
name|OntologyMetaInformation
argument_list|>
name|getOntologies
parameter_list|()
block|{
name|LockManager
name|lockManager
init|=
name|LockManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|lockManager
operator|.
name|obtainReadLockFor
argument_list|(
name|LockManagerImp
operator|.
name|GLOBAL_SPACE
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|OntologyMetaInformation
argument_list|>
name|onts
init|=
operator|new
name|ArrayList
argument_list|<
name|OntologyMetaInformation
argument_list|>
argument_list|()
decl_stmt|;
try|try
block|{
name|onts
operator|=
name|persistenceStore
operator|.
name|retrieveAdministeredOntologies
argument_list|()
operator|.
name|getOntologyMetaInformation
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error "
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
name|lockManager
operator|.
name|releaseReadLockFor
argument_list|(
name|LockManagerImp
operator|.
name|GLOBAL_SPACE
argument_list|)
expr_stmt|;
block|}
return|return
name|onts
return|;
block|}
block|}
end_class

end_unit

