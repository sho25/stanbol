begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|jersey
operator|.
name|resource
package|;
end_package

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
name|sparql
operator|.
name|ParseException
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
name|sparql
operator|.
name|QueryParser
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
name|sparql
operator|.
name|query
operator|.
name|ConstructQuery
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
name|sparql
operator|.
name|query
operator|.
name|DescribeQuery
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
name|sparql
operator|.
name|query
operator|.
name|Query
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
name|servicesapi
operator|.
name|SparqlQueryEngine
operator|.
name|SparqlQueryEngineException
import|;
end_import

begin_comment
comment|/**  * Implementation of a SPARQL endpoint as defined by the W3C:  *  * http://www.w3.org/TR/rdf-sparql-protocol/  *  * (not 100% compliant yet, please report bugs/missing features in the issue  * tracker).  *  * If the "query" parameter is not present, then fallback to display and HTML  * view with an ajax-ified form to test the SPARQL endpoint from the browser.  */
end_comment

begin_class
annotation|@
name|Path
argument_list|(
literal|"/sparql"
argument_list|)
specifier|public
class|class
name|SparqlQueryResource
extends|extends
name|NavigationMixin
block|{
specifier|protected
name|Store
name|store
decl_stmt|;
specifier|protected
name|TcManager
name|tcManager
decl_stmt|;
specifier|public
name|SparqlQueryResource
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
name|store
operator|=
operator|(
name|Store
operator|)
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|Store
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|GET
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
block|{
name|MediaType
operator|.
name|TEXT_HTML
operator|+
literal|";qs=2"
block|,
literal|"application/sparql-results+xml"
block|,
literal|"application/rdf+xml"
block|,
name|MediaType
operator|.
name|APPLICATION_XML
block|}
argument_list|)
specifier|public
name|Object
name|sparql
parameter_list|(
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"query"
argument_list|)
name|String
name|sparqlQuery
parameter_list|,
annotation|@
name|Deprecated
annotation|@
name|QueryParam
argument_list|(
name|value
operator|=
literal|"q"
argument_list|)
name|String
name|q
parameter_list|)
throws|throws
name|SparqlQueryEngineException
throws|,
name|ParseException
block|{
if|if
condition|(
name|q
operator|!=
literal|null
condition|)
block|{
comment|// compat with old REST API that was not respecting the SPARQL RDF
comment|// protocol
name|sparqlQuery
operator|=
name|q
expr_stmt|;
block|}
if|if
condition|(
name|sparqlQuery
operator|==
literal|null
condition|)
block|{
return|return
name|Response
operator|.
name|ok
argument_list|(
operator|new
name|Viewable
argument_list|(
literal|"index"
argument_list|,
name|this
argument_list|)
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
name|Query
name|query
init|=
name|QueryParser
operator|.
name|getInstance
argument_list|()
operator|.
name|parse
argument_list|(
name|sparqlQuery
argument_list|)
decl_stmt|;
name|String
name|mediaType
init|=
literal|"application/sparql-results+xml"
decl_stmt|;
if|if
condition|(
name|query
operator|instanceof
name|DescribeQuery
operator|||
name|query
operator|instanceof
name|ConstructQuery
condition|)
block|{
name|mediaType
operator|=
literal|"application/rdf+xml"
expr_stmt|;
block|}
name|Object
name|result
init|=
name|tcManager
operator|.
name|executeSparqlQuery
argument_list|(
name|query
argument_list|,
name|store
operator|.
name|getEnhancementGraph
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|Response
operator|.
name|ok
argument_list|(
name|result
argument_list|,
name|mediaType
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
name|Produces
argument_list|(
block|{
literal|"application/sparql-results+xml"
block|,
literal|"application/rdf+xml"
block|,
name|MediaType
operator|.
name|APPLICATION_XML
block|}
argument_list|)
specifier|public
name|Object
name|postSparql
parameter_list|(
annotation|@
name|FormParam
argument_list|(
literal|"query"
argument_list|)
name|String
name|sparqlQuery
parameter_list|)
throws|throws
name|SparqlQueryEngineException
throws|,
name|ParseException
block|{
return|return
name|sparql
argument_list|(
name|sparqlQuery
argument_list|,
literal|null
argument_list|)
return|;
block|}
block|}
end_class

end_unit

