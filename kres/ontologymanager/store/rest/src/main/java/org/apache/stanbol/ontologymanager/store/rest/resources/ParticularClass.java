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
name|DefaultValue
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
name|PathParam
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
name|QueryParam
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
name|model
operator|.
name|ClassContext
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
name|ImplicitProduces
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

begin_comment
comment|//@Component
end_comment

begin_comment
comment|//@Service(value = Object.class)
end_comment

begin_comment
comment|//@Property(name = "javax.ws.rs", boolValue = true)
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontologies/{ontologyPath:.+}/classes/{classPath:.+}/"
argument_list|)
annotation|@
name|ImplicitProduces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|";qs=2.0"
argument_list|)
specifier|public
class|class
name|ParticularClass
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
name|ParticularClass
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
literal|"/org/apache/stanbol/ontologymanager/store/rest/resources/particularClass"
decl_stmt|;
specifier|private
name|PersistenceStore
name|persistenceStore
decl_stmt|;
comment|// View Variables
specifier|private
name|ClassContext
name|metadata
decl_stmt|;
specifier|public
name|ParticularClass
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
operator|(
name|PersistenceStore
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|PersistenceStore
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// The Java method will process HTTP GET requests
annotation|@
name|GET
comment|// The Java method will produce content identified by the MIME Media
comment|// type "application/xml"
annotation|@
name|Produces
argument_list|(
literal|"application/xml"
argument_list|)
specifier|public
name|Response
name|retrieveClassContext
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"classPath"
argument_list|)
name|String
name|classPath
parameter_list|,
annotation|@
name|DefaultValue
argument_list|(
literal|"false"
argument_list|)
annotation|@
name|QueryParam
argument_list|(
literal|"withInferredAxioms"
argument_list|)
name|boolean
name|withInferredAxioms
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
name|ontologyPath
argument_list|)
expr_stmt|;
try|try
block|{
name|ResourceManagerImp
name|resourceManager
init|=
name|ResourceManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|classURI
init|=
name|resourceManager
operator|.
name|getResourceURIForPath
argument_list|(
name|ontologyPath
argument_list|,
name|classPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|classURI
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Response
operator|.
name|Status
operator|.
name|NOT_FOUND
argument_list|)
throw|;
block|}
try|try
block|{
name|ClassContext
name|classContext
init|=
name|persistenceStore
operator|.
name|generateClassContext
argument_list|(
name|classURI
argument_list|,
name|withInferredAxioms
argument_list|)
decl_stmt|;
name|response
operator|=
name|Response
operator|.
name|ok
argument_list|(
name|classContext
argument_list|,
name|MediaType
operator|.
name|APPLICATION_XML
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
block|}
finally|finally
block|{
name|lockManager
operator|.
name|releaseReadLockFor
argument_list|(
name|ontologyPath
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
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"classPath"
argument_list|)
name|String
name|classPath
parameter_list|)
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
name|ontologyPath
argument_list|)
expr_stmt|;
try|try
block|{
name|ResourceManagerImp
name|resourceManager
init|=
name|ResourceManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|resourceURI
init|=
name|resourceManager
operator|.
name|getResourceURIForPath
argument_list|(
name|ontologyPath
argument_list|,
name|classPath
argument_list|)
decl_stmt|;
name|persistenceStore
operator|.
name|deleteResource
argument_list|(
name|resourceURI
argument_list|)
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
name|ontologyPath
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|POST
annotation|@
name|Path
argument_list|(
literal|"/unionClasses"
argument_list|)
specifier|public
name|Response
name|addUnionClasses
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"classPath"
argument_list|)
name|String
name|classPath
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"unionClassURIs"
argument_list|)
name|List
argument_list|<
name|String
argument_list|>
name|unionClassURIs
parameter_list|)
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
name|ontologyPath
argument_list|)
expr_stmt|;
try|try
block|{
name|ResourceManagerImp
name|resourceManager
init|=
name|ResourceManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|classURI
init|=
name|resourceManager
operator|.
name|getResourceURIForPath
argument_list|(
name|ontologyPath
argument_list|,
name|classPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|classURI
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
throw|;
block|}
else|else
block|{
for|for
control|(
name|String
name|unionClassURI
range|:
name|unionClassURIs
control|)
block|{
try|try
block|{
name|persistenceStore
operator|.
name|addUnionClass
argument_list|(
name|classURI
argument_list|,
name|unionClassURI
argument_list|)
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
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
block|}
block|}
finally|finally
block|{
name|lockManager
operator|.
name|releaseWriteLockFor
argument_list|(
name|ontologyPath
argument_list|)
expr_stmt|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|DELETE
annotation|@
name|Path
argument_list|(
literal|"/unionClasses/{unionClassPath:.+}"
argument_list|)
specifier|public
name|Response
name|removeUnionClass
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"classPath"
argument_list|)
name|String
name|classPath
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"unionClassPath"
argument_list|)
name|String
name|unionClassPath
parameter_list|)
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
name|ontologyPath
argument_list|)
expr_stmt|;
try|try
block|{
name|ResourceManagerImp
name|resourceManager
init|=
name|ResourceManagerImp
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|classURI
init|=
name|resourceManager
operator|.
name|getResourceURIForPath
argument_list|(
name|ontologyPath
argument_list|,
name|classPath
argument_list|)
decl_stmt|;
name|String
name|unionClassURI
init|=
name|resourceManager
operator|.
name|convertEntityRelativePathToURI
argument_list|(
name|unionClassPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|classURI
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|WebApplicationException
argument_list|(
name|Status
operator|.
name|NOT_FOUND
argument_list|)
throw|;
block|}
else|else
block|{
try|try
block|{
name|persistenceStore
operator|.
name|deleteUnionClass
argument_list|(
name|classURI
argument_list|,
name|unionClassURI
argument_list|)
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
name|e
argument_list|,
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
throw|;
block|}
block|}
block|}
finally|finally
block|{          }
return|return
name|Response
operator|.
name|ok
argument_list|()
operator|.
name|build
argument_list|()
return|;
block|}
comment|// HTML View Methods
specifier|public
name|ClassContext
name|getMetadata
parameter_list|()
block|{
return|return
name|metadata
return|;
block|}
annotation|@
name|GET
annotation|@
name|Produces
argument_list|(
name|MediaType
operator|.
name|TEXT_HTML
argument_list|)
specifier|public
name|Viewable
name|getViewable
parameter_list|(
annotation|@
name|PathParam
argument_list|(
literal|"ontologyPath"
argument_list|)
name|String
name|ontologyPath
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"classPath"
argument_list|)
name|String
name|classPath
parameter_list|,
annotation|@
name|DefaultValue
argument_list|(
literal|"false"
argument_list|)
annotation|@
name|QueryParam
argument_list|(
literal|"withInferredAxioms"
argument_list|)
name|boolean
name|withInferredAxioms
parameter_list|)
block|{
name|metadata
operator|=
operator|(
name|ClassContext
operator|)
name|retrieveClassContext
argument_list|(
name|ontologyPath
argument_list|,
name|classPath
argument_list|,
name|withInferredAxioms
argument_list|)
operator|.
name|getEntity
argument_list|()
expr_stmt|;
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
block|}
end_class

end_unit

