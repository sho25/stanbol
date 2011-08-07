begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|web
operator|.
name|resources
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Graph
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
name|impl
operator|.
name|SimpleMGraph
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
name|serializedform
operator|.
name|Parser
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
name|serializedform
operator|.
name|SupportedFormat
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
name|cmsadapter
operator|.
name|core
operator|.
name|mapping
operator|.
name|RDFBridgeManager
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|RDFBridgeException
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|ConnectionInfo
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccessException
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
comment|/**  * This resource is currently used to pass RDF data to CMS Adapter so that RDF data will be annotated with  * "CMS vocabulary" annotations according to {@link RDFBridge}s. Afterwards, this annotated RDF is transformed  * into nodes/object in the repository.  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/cmsadapter/rdfmap"
argument_list|)
specifier|public
class|class
name|RDFMapperResource
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
name|RDFMapperResource
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Parser
name|clerezzaParser
decl_stmt|;
specifier|private
name|RDFBridgeManager
name|bridgeManager
decl_stmt|;
specifier|public
name|RDFMapperResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|context
parameter_list|)
block|{
name|clerezzaParser
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|Parser
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|bridgeManager
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|RDFBridgeManager
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
comment|/**      * This service takes credentials as {@link ConnectionInfo} object to access repository and an RDF data      * first to be annotated and then stored in repository based on the annotations.      *       * @param connectionInfo      *            is the object that holds all necessary information to connect repository. Example connection      *            info XML:<br>      *<br>      *       *<pre>      *&lt;?xml version="1.0" encoding="UTF-8"?>      *&lt;connectionInfo      *     xmlns="web.model.servicesapi.cmsadapter.stanbol.apache.org">      *&lt;repositoryURL>rmi://localhost:1099/crx&lt;/repositoryURL>      *&lt;workspaceName>demo&lt;/workspaceName>      *&lt;username>admin&lt;/username>      *&lt;password>admin&lt;/password>      *&lt;connectionType>JCR&lt;/connectionType>      *&lt;/connectionInfo>      *</pre>      *       *<br>      * @param serializedGraph      *            is the serialized RDF graph that is desired to transformed into repository objects      *       *            TODO: It would be wise to get as MGraph in request data. Before that connection info should      *            be get in a different way.      */
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
name|mapRDF
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"connectionInfo"
argument_list|)
name|ConnectionInfo
name|connectionInfo
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"serializedGraph"
argument_list|)
name|String
name|serializedGraph
parameter_list|)
block|{
if|if
condition|(
name|connectionInfo
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is no valid connection info specified"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"There is no valid connection info specified"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
if|if
condition|(
name|serializedGraph
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"There is no valid RDF data specified"
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|BAD_REQUEST
argument_list|)
operator|.
name|entity
argument_list|(
literal|"There is no valid RDF data specified"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
name|Graph
name|g
init|=
name|clerezzaParser
operator|.
name|parse
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|serializedGraph
operator|.
name|getBytes
argument_list|()
argument_list|)
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
decl_stmt|;
name|MGraph
name|annotatedGraph
init|=
operator|new
name|SimpleMGraph
argument_list|(
name|g
operator|.
name|iterator
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|bridgeManager
operator|.
name|storeRDFToRepository
argument_list|(
name|connectionInfo
argument_list|,
name|annotatedGraph
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryAccessException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
literal|"Failed to obtain a session from repository"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
literal|"Failed to obtain a session from repository"
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|RDFBridgeException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|warn
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
name|Status
operator|.
name|INTERNAL_SERVER_ERROR
argument_list|)
operator|.
name|entity
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
return|;
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

