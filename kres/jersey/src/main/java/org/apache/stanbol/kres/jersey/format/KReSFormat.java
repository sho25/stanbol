begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
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
package|;
end_package

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

begin_class
specifier|public
class|class
name|KReSFormat
extends|extends
name|MediaType
block|{
specifier|public
specifier|static
specifier|final
name|String
name|RDF_XML
init|=
literal|"application/rdf+xml"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|MediaType
name|RDF_XML_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"rdf+xml"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|OWL_XML
init|=
literal|"application/owl+xml"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|MediaType
name|OWL_XML_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"owl+xml"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|MANCHESTER_OWL
init|=
literal|"application/manchester+owl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|MediaType
name|MANCHESTER_OWL_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"manchester+xml"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|FUNCTIONAL_OWL
init|=
literal|"application/functional+owl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|MediaType
name|FUNCTIONAL_OWL_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"functional+xml"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TURTLE
init|=
literal|"application/turtle"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|MediaType
name|TURTLE_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"turtle"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RDF_JSON
init|=
literal|"application/rdf+json"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|MediaType
name|RDF_JSON_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"rdf+json"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

