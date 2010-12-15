begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|jersey
operator|.
name|writers
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|annotation
operator|.
name|Annotation
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
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
name|MultivaluedMap
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
name|ext
operator|.
name|MessageBodyWriter
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
name|ext
operator|.
name|Provider
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
name|serializedform
operator|.
name|Serializer
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
name|clerezza
operator|.
name|rdf
operator|.
name|rdfjson
operator|.
name|serializer
operator|.
name|RdfJsonSerializingProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|coode
operator|.
name|owlapi
operator|.
name|manchesterowlsyntax
operator|.
name|ManchesterOWLSyntaxOntologyFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|coode
operator|.
name|owlapi
operator|.
name|turtle
operator|.
name|TurtleOntologyFormat
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
name|io
operator|.
name|OWLFunctionalSyntaxOntologyFormat
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
name|io
operator|.
name|OWLXMLOntologyFormat
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
name|io
operator|.
name|RDFXMLOntologyFormat
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
name|model
operator|.
name|OWLOntologyStorageException
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
name|kres
operator|.
name|api
operator|.
name|format
operator|.
name|KReSFormat
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|shared
operator|.
name|transformation
operator|.
name|OWLAPIToClerezzaConverter
import|;
end_import

begin_class
annotation|@
name|Provider
annotation|@
name|Produces
argument_list|(
block|{
name|KReSFormat
operator|.
name|RDF_XML
block|,
name|KReSFormat
operator|.
name|OWL_XML
block|,
name|KReSFormat
operator|.
name|MANCHESTER_OWL
block|,
name|KReSFormat
operator|.
name|FUNCTIONAL_OWL
block|,
name|KReSFormat
operator|.
name|TURTLE
block|,
name|KReSFormat
operator|.
name|RDF_JSON
block|}
argument_list|)
specifier|public
class|class
name|OWLOntologyWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|OWLOntology
argument_list|>
block|{
specifier|protected
name|Serializer
name|serializer
decl_stmt|;
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
specifier|public
name|OWLOntologyWriter
parameter_list|(
annotation|@
name|Context
name|ServletContext
name|servletContext
parameter_list|)
block|{
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
name|this
operator|.
name|servletContext
operator|=
name|servletContext
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Setting context to "
operator|+
name|servletContext
argument_list|)
expr_stmt|;
name|serializer
operator|=
operator|(
name|Serializer
operator|)
name|this
operator|.
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|Serializer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|serializer
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Serializer not found in Servlet context."
argument_list|)
expr_stmt|;
name|serializer
operator|=
operator|new
name|Serializer
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|OWLOntology
name|arg0
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|arg1
parameter_list|,
name|Type
name|arg2
parameter_list|,
name|Annotation
index|[]
name|arg3
parameter_list|,
name|MediaType
name|arg4
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isWriteable
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
return|return
name|OWLOntology
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|arg1
parameter_list|,
name|Type
name|arg2
parameter_list|,
name|Annotation
index|[]
name|arg3
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|MultivaluedMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|arg5
parameter_list|,
name|OutputStream
name|out
parameter_list|)
throws|throws
name|IOException
throws|,
name|WebApplicationException
block|{
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
name|OWLOntologyManager
name|manager
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Rendering ontology "
operator|+
name|ontology
operator|.
name|getOntologyID
argument_list|()
operator|+
literal|"to KReS format "
operator|+
name|mediaType
argument_list|)
expr_stmt|;
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KReSFormat
operator|.
name|RDF_XML
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|RDFXMLOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KReSFormat
operator|.
name|OWL_XML
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|OWLXMLOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KReSFormat
operator|.
name|MANCHESTER_OWL
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|ManchesterOWLSyntaxOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KReSFormat
operator|.
name|FUNCTIONAL_OWL
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|OWLFunctionalSyntaxOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KReSFormat
operator|.
name|TURTLE
argument_list|)
condition|)
block|{
try|try
block|{
name|manager
operator|.
name|saveOntology
argument_list|(
name|ontology
argument_list|,
operator|new
name|TurtleOntologyFormat
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyStorageException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to store ontology for rendering."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mediaType
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|KReSFormat
operator|.
name|RDF_JSON
argument_list|)
condition|)
block|{
name|MGraph
name|mGraph
init|=
name|OWLAPIToClerezzaConverter
operator|.
name|owlOntologyToClerezzaMGraph
argument_list|(
name|ontology
argument_list|)
decl_stmt|;
name|RdfJsonSerializingProvider
name|provider
init|=
operator|new
name|RdfJsonSerializingProvider
argument_list|()
decl_stmt|;
name|provider
operator|.
name|serialize
argument_list|(
name|out
argument_list|,
name|mGraph
argument_list|,
name|SupportedFormat
operator|.
name|RDF_JSON
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

