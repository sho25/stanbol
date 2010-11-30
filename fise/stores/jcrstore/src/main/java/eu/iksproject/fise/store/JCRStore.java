begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|store
package|;
end_package

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
name|Map
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|ItemExistsException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|LoginException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|NodeIterator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|PathNotFoundException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|Repository
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|RepositoryException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|Session
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|SimpleCredentials
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|lock
operator|.
name|LockException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|nodetype
operator|.
name|ConstraintViolationException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|query
operator|.
name|InvalidQueryException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|query
operator|.
name|Query
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|query
operator|.
name|QueryManager
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|query
operator|.
name|QueryResult
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|version
operator|.
name|VersionException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|WeightedTcProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|fise
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|Store
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|store
operator|.
name|jcr
operator|.
name|JCRContentItem
import|;
end_import

begin_comment
comment|/**  * JCR Store for standalone FISE server  *   * @scr.property name="service.ranking" type=Integer value=10000  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|false
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|JCRStore
implements|implements
name|Store
block|{
specifier|private
specifier|final
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
literal|"eu.iksproject.fise.store.JCRStore"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|FISE_ROOT_NODE
init|=
literal|"fise"
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|ContentItem
argument_list|>
name|data
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ContentItem
argument_list|>
argument_list|()
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|WeightedTcProvider
name|tcProvider
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|Repository
name|repository
decl_stmt|;
specifier|private
specifier|static
name|Session
name|session
init|=
literal|null
decl_stmt|;
specifier|public
name|ContentItem
name|create
parameter_list|(
name|String
name|id
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|)
block|{
specifier|final
name|MGraph
name|g
init|=
name|tcProvider
operator|.
name|createMGraph
argument_list|(
operator|new
name|UriRef
argument_list|(
name|id
argument_list|)
argument_list|)
decl_stmt|;
name|ContentItem
name|node
decl_stmt|;
try|try
block|{
name|node
operator|=
operator|new
name|JCRContentItem
argument_list|(
name|id
argument_list|,
name|content
argument_list|,
name|mimeType
argument_list|,
name|g
argument_list|,
name|getParentNode
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
catch|catch
parameter_list|(
name|ItemExistsException
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
catch|catch
parameter_list|(
name|PathNotFoundException
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
catch|catch
parameter_list|(
name|VersionException
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
catch|catch
parameter_list|(
name|ConstraintViolationException
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
catch|catch
parameter_list|(
name|LockException
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
catch|catch
parameter_list|(
name|RepositoryException
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
return|return
literal|null
return|;
block|}
specifier|public
name|ContentItem
name|get
parameter_list|(
name|String
name|id
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|findNodeById
argument_list|(
name|id
argument_list|,
name|getParentNode
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|JCRContentItem
argument_list|(
name|id
argument_list|,
name|getParentNode
argument_list|()
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|RepositoryException
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
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|put
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
name|byte
index|[]
name|bytes
init|=
operator|new
name|byte
index|[
literal|0
index|]
decl_stmt|;
comment|// ci.getStream();
name|ContentItem
name|result
decl_stmt|;
try|try
block|{
name|result
operator|=
operator|new
name|JCRContentItem
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|,
name|bytes
argument_list|,
name|ci
operator|.
name|getMimeType
argument_list|()
argument_list|,
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|,
name|getParentNode
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|result
operator|.
name|getId
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ItemExistsException
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
catch|catch
parameter_list|(
name|PathNotFoundException
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
catch|catch
parameter_list|(
name|VersionException
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
catch|catch
parameter_list|(
name|ConstraintViolationException
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
catch|catch
parameter_list|(
name|LockException
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
catch|catch
parameter_list|(
name|RepositoryException
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
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|Node
name|findNodeById
parameter_list|(
name|String
name|id
parameter_list|,
name|Node
name|parent
parameter_list|)
throws|throws
name|RepositoryException
throws|,
name|InvalidQueryException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"entering findByNode with id "
operator|+
name|id
argument_list|)
expr_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
operator|&&
name|id
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|QueryManager
name|queryManager
init|=
name|parent
operator|.
name|getSession
argument_list|()
operator|.
name|getWorkspace
argument_list|()
operator|.
name|getQueryManager
argument_list|()
decl_stmt|;
name|String
name|queryString
init|=
literal|"/jcr:root/"
operator|+
name|parent
operator|.
name|getPath
argument_list|()
operator|+
literal|"//*[@"
operator|+
name|JCRContentItem
operator|.
name|FISE_ID_PROP
operator|+
literal|"= '"
operator|+
name|id
operator|+
literal|"']"
decl_stmt|;
name|Query
name|query
init|=
name|queryManager
operator|.
name|createQuery
argument_list|(
name|queryString
argument_list|,
literal|"xpath"
argument_list|)
decl_stmt|;
name|QueryResult
name|results
init|=
name|query
operator|.
name|execute
argument_list|()
decl_stmt|;
name|NodeIterator
name|r
init|=
name|results
operator|.
name|getNodes
argument_list|()
decl_stmt|;
while|while
condition|(
name|r
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"found node for id "
operator|+
name|id
argument_list|)
expr_stmt|;
return|return
name|r
operator|.
name|nextNode
argument_list|()
return|;
block|}
block|}
name|log
operator|.
name|info
argument_list|(
literal|"found NO node for id "
operator|+
name|id
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|private
name|Node
name|getParentNode
parameter_list|()
throws|throws
name|RepositoryException
block|{
name|Session
name|session
init|=
name|getSession
argument_list|()
decl_stmt|;
name|Node
name|rootNode
init|=
name|session
operator|.
name|getRootNode
argument_list|()
decl_stmt|;
if|if
condition|(
name|rootNode
operator|.
name|hasNode
argument_list|(
name|FISE_ROOT_NODE
argument_list|)
condition|)
block|{
return|return
name|rootNode
operator|.
name|getNode
argument_list|(
name|FISE_ROOT_NODE
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|rootNode
operator|.
name|addNode
argument_list|(
name|FISE_ROOT_NODE
argument_list|)
return|;
block|}
block|}
specifier|private
name|Session
name|getSession
parameter_list|()
block|{
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
block|{
return|return
name|session
return|;
block|}
try|try
block|{
return|return
name|repository
operator|.
name|login
argument_list|(
operator|new
name|SimpleCredentials
argument_list|(
literal|"admin"
argument_list|,
literal|"admin"
operator|.
name|toCharArray
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|LoginException
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
catch|catch
parameter_list|(
name|RepositoryException
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
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

