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

begin_class
annotation|@
name|Path
argument_list|(
literal|"/ontology/{ontologyPath:.+}/individuals/{individualPath:.+}/types"
argument_list|)
specifier|public
class|class
name|ParticularIndividualTypes
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
name|ParticularIndividualTypes
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|PersistenceStore
name|persistenceStore
decl_stmt|;
specifier|public
name|ParticularIndividualTypes
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
comment|// FIXME Handle PS errors properly, correct return values
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|APPLICATION_FORM_URLENCODED
argument_list|)
specifier|public
name|Response
name|addType
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
literal|"individualPath"
argument_list|)
name|String
name|individualPath
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"containerClassURIs"
argument_list|)
name|List
argument_list|<
name|String
argument_list|>
name|containerClassURIs
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
name|individualURI
init|=
name|resourceManager
operator|.
name|getResourceURIForPath
argument_list|(
name|ontologyPath
argument_list|,
name|individualPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|individualURI
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
else|else
block|{
for|for
control|(
name|String
name|containerClassURI
range|:
name|containerClassURIs
control|)
block|{
try|try
block|{
name|persistenceStore
operator|.
name|addContainerClassForIndividual
argument_list|(
name|individualURI
argument_list|,
name|containerClassURI
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
literal|"/ontologymanager/store{containerClassPath:.+}"
argument_list|)
specifier|public
name|Response
name|addType
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
literal|"individualPath"
argument_list|)
name|String
name|individualPath
parameter_list|,
annotation|@
name|PathParam
argument_list|(
literal|"containerClassPath"
argument_list|)
name|String
name|containerClassPath
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
name|individualURI
init|=
name|resourceManager
operator|.
name|getResourceURIForPath
argument_list|(
name|ontologyPath
argument_list|,
name|individualPath
argument_list|)
decl_stmt|;
name|String
name|containerClassURI
init|=
name|resourceManager
operator|.
name|convertEntityRelativePathToURI
argument_list|(
name|containerClassPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|individualURI
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
else|else
block|{
try|try
block|{
name|persistenceStore
operator|.
name|deleteContainerClassForIndividual
argument_list|(
name|individualURI
argument_list|,
name|containerClassURI
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
block|}
end_class

end_unit

