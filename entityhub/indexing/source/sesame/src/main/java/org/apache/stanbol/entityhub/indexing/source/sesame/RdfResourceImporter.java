begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|source
operator|.
name|sesame
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
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|source
operator|.
name|ResourceImporter
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|source
operator|.
name|ResourceState
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|repository
operator|.
name|Repository
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|repository
operator|.
name|RepositoryConnection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|repository
operator|.
name|RepositoryException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|rio
operator|.
name|RDFFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|rio
operator|.
name|RDFParseException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|rio
operator|.
name|Rio
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
specifier|public
class|class
name|RdfResourceImporter
implements|implements
name|ResourceImporter
block|{
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RdfResourceImporter
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Charset
name|UTF8
init|=
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
decl_stmt|;
specifier|private
name|Repository
name|repository
decl_stmt|;
specifier|private
name|Resource
index|[]
name|contexts
decl_stmt|;
specifier|private
name|String
name|baseUri
decl_stmt|;
specifier|public
name|RdfResourceImporter
parameter_list|(
name|Repository
name|repository
parameter_list|,
name|String
name|baseUri
parameter_list|,
name|Resource
modifier|...
name|contexts
parameter_list|)
block|{
if|if
condition|(
name|repository
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed Repository MUST NOT be NULL"
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|repository
operator|.
name|isInitialized
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The parsed Repository MUST BE initialised!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|repository
operator|=
name|repository
expr_stmt|;
if|if
condition|(
name|baseUri
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed base URI MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|baseUri
operator|=
name|baseUri
expr_stmt|;
name|this
operator|.
name|contexts
operator|=
name|contexts
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|ResourceState
name|importResource
parameter_list|(
name|InputStream
name|is
parameter_list|,
name|String
name|resourceName
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"> importing {}:"
argument_list|,
name|resourceName
argument_list|)
expr_stmt|;
name|RDFFormat
name|rdfFormat
init|=
name|Rio
operator|.
name|getParserFormatForFileName
argument_list|(
name|resourceName
argument_list|)
decl_stmt|;
if|if
condition|(
name|rdfFormat
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"  ... unable to detect RDF format for {}"
argument_list|,
name|resourceName
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"  ... resource '{}' will not be imported"
argument_list|,
name|resourceName
argument_list|)
expr_stmt|;
return|return
name|ResourceState
operator|.
name|IGNORED
return|;
block|}
else|else
block|{
name|RepositoryConnection
name|con
init|=
literal|null
decl_stmt|;
try|try
block|{
name|con
operator|=
name|repository
operator|.
name|getConnection
argument_list|()
expr_stmt|;
name|con
operator|.
name|begin
argument_list|()
expr_stmt|;
name|con
operator|.
name|add
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|,
name|UTF8
argument_list|)
argument_list|,
name|baseUri
argument_list|,
name|rdfFormat
argument_list|,
name|contexts
argument_list|)
expr_stmt|;
name|con
operator|.
name|commit
argument_list|()
expr_stmt|;
return|return
name|ResourceState
operator|.
name|LOADED
return|;
block|}
catch|catch
parameter_list|(
name|RDFParseException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"  ... unable to parser RDF file "
operator|+
name|resourceName
operator|+
literal|" (format: "
operator|+
name|rdfFormat
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|ResourceState
operator|.
name|ERROR
return|;
block|}
catch|catch
parameter_list|(
name|RepositoryException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Repository Exception while "
operator|+
name|resourceName
operator|+
literal|"!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|con
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|con
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RepositoryException
name|e1
parameter_list|)
block|{
comment|/* ignore */
block|}
block|}
block|}
block|}
block|}
block|}
end_class

end_unit
