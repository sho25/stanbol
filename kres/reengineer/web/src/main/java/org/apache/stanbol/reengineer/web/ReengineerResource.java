begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reengineer
operator|.
name|web
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
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
name|HttpHeaders
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
name|Literal
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
name|LiteralFactory
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
name|TcManager
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
name|impl
operator|.
name|TripleImpl
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
name|impl
operator|.
name|io
operator|.
name|ClerezzaOntologyStorage
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|DataSource
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|ReengineeringException
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|ReengineerManager
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|Reengineer
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|datasources
operator|.
name|DataSourceFactory
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|datasources
operator|.
name|InvalidDataSourceForTypeSelectedException
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|datasources
operator|.
name|NoSuchDataSourceExpection
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|datasources
operator|.
name|RDB
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|settings
operator|.
name|ConnectionSettings
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|settings
operator|.
name|DBConnectionSettings
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|ReengineerType
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|UnsupportedReengineerException
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
name|OWLOntology
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
name|resource
operator|.
name|NavigationMixin
import|;
end_import

begin_class
annotation|@
name|Path
argument_list|(
literal|"/reengineer"
argument_list|)
annotation|@
name|ImplicitProduces
argument_list|(
literal|"text/html"
argument_list|)
specifier|public
class|class
name|ReengineerResource
extends|extends
name|NavigationMixin
block|{
specifier|protected
name|ReengineerManager
name|reengineeringManager
decl_stmt|;
specifier|protected
name|TcManager
name|tcManager
decl_stmt|;
specifier|protected
name|ClerezzaOntologyStorage
name|storage
decl_stmt|;
specifier|private
specifier|final
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
name|ReengineerResource
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
name|tcManager
operator|=
operator|(
name|TcManager
operator|)
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|TcManager
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|storage
operator|=
operator|(
name|ClerezzaOntologyStorage
operator|)
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|ClerezzaOntologyStorage
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|reengineeringManager
operator|=
call|(
name|ReengineerManager
call|)
argument_list|(
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|ReengineerManager
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|reengineeringManager
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"ReengineeringManager missing in ServletContext"
argument_list|)
throw|;
block|}
block|}
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|MULTIPART_FORM_DATA
argument_list|)
specifier|public
name|Response
name|reengineering
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"output-graph"
argument_list|)
name|String
name|outputGraph
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"input-type"
argument_list|)
name|String
name|inputType
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"input"
argument_list|)
name|InputStream
name|input
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|HttpServletRequest
name|httpServletRequest
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Reengineering: "
operator|+
name|inputType
argument_list|)
expr_stmt|;
name|int
name|reengineerType
init|=
operator|-
literal|1
decl_stmt|;
try|try
block|{
name|reengineerType
operator|=
name|ReengineerType
operator|.
name|getType
argument_list|(
name|inputType
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedReengineerException
name|e
parameter_list|)
block|{
name|Response
operator|.
name|status
argument_list|(
literal|404
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
try|try
block|{
name|DataSource
name|dataSource
init|=
name|DataSourceFactory
operator|.
name|createDataSource
argument_list|(
name|reengineerType
argument_list|,
name|input
argument_list|)
decl_stmt|;
try|try
block|{
name|OWLOntology
name|ontology
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"STORE PROVIDER : "
operator|+
name|storage
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"OUTGRAPH: "
operator|+
name|outputGraph
argument_list|)
expr_stmt|;
name|String
name|servletPath
init|=
name|httpServletRequest
operator|.
name|getLocalAddr
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"SERVER PATH : "
operator|+
name|servletPath
argument_list|)
expr_stmt|;
name|servletPath
operator|=
literal|"http://"
operator|+
name|servletPath
operator|+
literal|"/kres/graphs/"
operator|+
name|outputGraph
operator|+
literal|":"
operator|+
name|httpServletRequest
operator|.
name|getLocalPort
argument_list|()
expr_stmt|;
if|if
condition|(
name|outputGraph
operator|==
literal|null
operator|||
name|outputGraph
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|ontology
operator|=
name|reengineeringManager
operator|.
name|performReengineering
argument_list|(
name|servletPath
argument_list|,
literal|null
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
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
else|else
block|{
name|ontology
operator|=
name|reengineeringManager
operator|.
name|performReengineering
argument_list|(
name|servletPath
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
name|outputGraph
argument_list|)
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
name|storage
operator|.
name|store
argument_list|(
name|ontology
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
name|ontology
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|ReengineeringException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchDataSourceExpection
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|415
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InvalidDataSourceForTypeSelectedException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|204
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
annotation|@
name|POST
annotation|@
name|Consumes
argument_list|(
name|MediaType
operator|.
name|MULTIPART_FORM_DATA
argument_list|)
annotation|@
name|Path
argument_list|(
literal|"/schema"
argument_list|)
specifier|public
name|Response
name|schemaReengineering
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"output-graph"
argument_list|)
name|String
name|outputGraph
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"input-type"
argument_list|)
name|String
name|inputType
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"input"
argument_list|)
name|InputStream
name|input
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|HttpServletRequest
name|httpServletRequest
parameter_list|)
block|{
name|int
name|reengineerType
init|=
operator|-
literal|1
decl_stmt|;
try|try
block|{
name|reengineerType
operator|=
name|ReengineerType
operator|.
name|getType
argument_list|(
name|inputType
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedReengineerException
name|e
parameter_list|)
block|{
name|Response
operator|.
name|status
argument_list|(
literal|404
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
try|try
block|{
name|DataSource
name|dataSource
init|=
name|DataSourceFactory
operator|.
name|createDataSource
argument_list|(
name|reengineerType
argument_list|,
name|input
argument_list|)
decl_stmt|;
try|try
block|{
name|OWLOntology
name|ontology
decl_stmt|;
name|String
name|servletPath
init|=
name|httpServletRequest
operator|.
name|getLocalAddr
argument_list|()
decl_stmt|;
name|servletPath
operator|=
literal|"http://"
operator|+
name|servletPath
operator|+
literal|"/kres/graphs/"
operator|+
name|outputGraph
operator|+
literal|":"
operator|+
name|httpServletRequest
operator|.
name|getLocalPort
argument_list|()
expr_stmt|;
if|if
condition|(
name|outputGraph
operator|==
literal|null
condition|)
block|{
name|ontology
operator|=
name|reengineeringManager
operator|.
name|performSchemaReengineering
argument_list|(
name|servletPath
argument_list|,
literal|null
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
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
else|else
block|{
name|ontology
operator|=
name|reengineeringManager
operator|.
name|performSchemaReengineering
argument_list|(
name|servletPath
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
name|outputGraph
argument_list|)
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
name|ontology
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|ReengineeringException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchDataSourceExpection
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|415
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InvalidDataSourceForTypeSelectedException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|204
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/reengineers"
argument_list|)
specifier|public
name|Response
name|listReengineers
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
name|Collection
argument_list|<
name|Reengineer
argument_list|>
name|reengineers
init|=
name|reengineeringManager
operator|.
name|listReengineers
argument_list|()
decl_stmt|;
name|MGraph
name|mGraph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|UriRef
name|semionRef
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://semion.kres.iksproject.eu#Semion"
argument_list|)
decl_stmt|;
for|for
control|(
name|Reengineer
name|semionReengineer
range|:
name|reengineers
control|)
block|{
name|UriRef
name|hasReengineer
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://semion.kres.iksproject.eu#hasReengineer"
argument_list|)
decl_stmt|;
name|Literal
name|reenginnerLiteral
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createTypedLiteral
argument_list|(
name|semionReengineer
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
decl_stmt|;
name|mGraph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|semionRef
argument_list|,
name|hasReengineer
argument_list|,
name|reenginnerLiteral
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|Response
operator|.
name|ok
argument_list|(
name|mGraph
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
annotation|@
name|GET
annotation|@
name|Path
argument_list|(
literal|"/reengineers/count"
argument_list|)
specifier|public
name|Response
name|countReengineers
parameter_list|(
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
name|reengineeringManager
operator|.
name|countReengineers
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
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
name|Path
argument_list|(
literal|"/db/schema"
argument_list|)
specifier|public
name|Response
name|reengineeringDBSchema
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"output-graph"
argument_list|)
name|String
name|outputGraph
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"db"
argument_list|)
name|String
name|physicalDBName
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"jdbc"
argument_list|)
name|String
name|jdbcDriver
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"protocol"
argument_list|)
name|String
name|protocol
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"host"
argument_list|)
name|String
name|host
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"port"
argument_list|)
name|String
name|port
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"username"
argument_list|)
name|String
name|username
parameter_list|,
annotation|@
name|FormParam
argument_list|(
literal|"password"
argument_list|)
name|String
name|password
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|HttpServletRequest
name|httpServletRequest
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"There are "
operator|+
name|tcManager
operator|.
name|listMGraphs
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" mGraphs"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"There are "
operator|+
name|tcManager
operator|.
name|listMGraphs
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" mGraphs"
argument_list|)
expr_stmt|;
comment|//UriRef uri = ContentItemHelper.makeDefaultUri(databaseURI, databaseURI.getBytes());
name|ConnectionSettings
name|connectionSettings
init|=
operator|new
name|DBConnectionSettings
argument_list|(
name|protocol
argument_list|,
name|host
argument_list|,
name|port
argument_list|,
name|physicalDBName
argument_list|,
name|username
argument_list|,
name|password
argument_list|,
literal|null
argument_list|,
name|jdbcDriver
argument_list|)
decl_stmt|;
name|DataSource
name|dataSource
init|=
operator|new
name|RDB
argument_list|(
name|connectionSettings
argument_list|)
decl_stmt|;
name|String
name|servletPath
init|=
name|httpServletRequest
operator|.
name|getLocalAddr
argument_list|()
decl_stmt|;
name|servletPath
operator|=
literal|"http://"
operator|+
name|servletPath
operator|+
literal|"/kres/graphs/"
operator|+
name|outputGraph
operator|+
literal|":"
operator|+
name|httpServletRequest
operator|.
name|getLocalPort
argument_list|()
expr_stmt|;
if|if
condition|(
name|outputGraph
operator|!=
literal|null
operator|&&
operator|!
name|outputGraph
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|OWLOntology
name|ontology
decl_stmt|;
try|try
block|{
name|ontology
operator|=
name|reengineeringManager
operator|.
name|performSchemaReengineering
argument_list|(
name|servletPath
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
name|outputGraph
argument_list|)
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
comment|/*MediaType mediaType = headers.getMediaType(); 				String res = OntologyRenderUtils.renderOntology(ontology, mediaType.getType());*/
return|return
name|Response
operator|.
name|ok
argument_list|(
name|ontology
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ReengineeringException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
try|try
block|{
name|reengineeringManager
operator|.
name|performSchemaReengineering
argument_list|(
name|servletPath
argument_list|,
literal|null
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
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
catch|catch
parameter_list|(
name|ReengineeringException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
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
name|Path
argument_list|(
literal|"/db"
argument_list|)
specifier|public
name|Response
name|reengineeringDB
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
literal|"db"
argument_list|)
name|String
name|physicalDBName
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"jdbc"
argument_list|)
name|String
name|jdbcDriver
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"protocol"
argument_list|)
name|String
name|protocol
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"host"
argument_list|)
name|String
name|host
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"port"
argument_list|)
name|String
name|port
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"username"
argument_list|)
name|String
name|username
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"password"
argument_list|)
name|String
name|password
parameter_list|,
annotation|@
name|QueryParam
argument_list|(
literal|"output-graph"
argument_list|)
name|String
name|outputGraph
parameter_list|,
annotation|@
name|Context
name|HttpHeaders
name|headers
parameter_list|,
annotation|@
name|Context
name|HttpServletRequest
name|httpServletRequest
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"There are "
operator|+
name|tcManager
operator|.
name|listMGraphs
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" mGraphs"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"There are "
operator|+
name|tcManager
operator|.
name|listMGraphs
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" mGraphs"
argument_list|)
expr_stmt|;
comment|//UriRef uri = ContentItemHelper.makeDefaultUri(databaseURI, databaseURI.getBytes());
name|ConnectionSettings
name|connectionSettings
init|=
operator|new
name|DBConnectionSettings
argument_list|(
name|protocol
argument_list|,
name|host
argument_list|,
name|port
argument_list|,
name|physicalDBName
argument_list|,
name|username
argument_list|,
name|password
argument_list|,
literal|null
argument_list|,
name|jdbcDriver
argument_list|)
decl_stmt|;
name|DataSource
name|dataSource
init|=
operator|new
name|RDB
argument_list|(
name|connectionSettings
argument_list|)
decl_stmt|;
name|String
name|servletPath
init|=
name|httpServletRequest
operator|.
name|getLocalAddr
argument_list|()
decl_stmt|;
name|servletPath
operator|=
literal|"http://"
operator|+
name|servletPath
operator|+
literal|"/kres/graphs/"
operator|+
name|outputGraph
operator|+
literal|":"
operator|+
name|httpServletRequest
operator|.
name|getLocalPort
argument_list|()
expr_stmt|;
if|if
condition|(
name|outputGraph
operator|!=
literal|null
operator|&&
operator|!
name|outputGraph
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|OWLOntology
name|ontology
decl_stmt|;
try|try
block|{
name|ontology
operator|=
name|reengineeringManager
operator|.
name|performReengineering
argument_list|(
name|servletPath
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
name|outputGraph
argument_list|)
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
name|ontology
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ReengineeringException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
else|else
block|{
try|try
block|{
name|reengineeringManager
operator|.
name|performReengineering
argument_list|(
name|servletPath
argument_list|,
literal|null
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
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
catch|catch
parameter_list|(
name|ReengineeringException
name|e
parameter_list|)
block|{
return|return
name|Response
operator|.
name|status
argument_list|(
literal|500
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
block|}
block|}
block|}
end_class

end_unit

