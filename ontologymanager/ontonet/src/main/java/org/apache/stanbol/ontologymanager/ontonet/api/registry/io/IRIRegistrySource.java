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
name|ontonet
operator|.
name|api
operator|.
name|registry
operator|.
name|io
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
name|io
operator|.
name|Reader
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

begin_class
specifier|public
class|class
name|IRIRegistrySource
implements|implements
name|XDRegistrySource
block|{
specifier|protected
name|IRI
name|iri
decl_stmt|;
specifier|public
name|IRIRegistrySource
parameter_list|(
name|IRI
name|physicalIRI
parameter_list|)
block|{
if|if
condition|(
name|physicalIRI
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Cannot instantiate IRI registry source on null IRI."
argument_list|)
throw|;
name|this
operator|.
name|iri
operator|=
name|physicalIRI
expr_stmt|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see org.stlab.xd.registry.io.XDRegistrySource#getInputStream() 	 */
annotation|@
name|Override
specifier|public
name|InputStream
name|getInputStream
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see org.stlab.xd.registry.io.XDRegistrySource#getPhysicalIRI() 	 */
annotation|@
name|Override
specifier|public
name|IRI
name|getPhysicalIRI
parameter_list|()
block|{
return|return
name|iri
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see org.stlab.xd.registry.io.XDRegistrySource#getReader() 	 */
annotation|@
name|Override
specifier|public
name|Reader
name|getReader
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
comment|/* 	 * (non-Javadoc) 	 * @see org.stlab.xd.registry.io.XDRegistrySource#isInputStreamAvailable() 	 */
annotation|@
name|Override
specifier|public
name|boolean
name|isInputStreamAvailable
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isReaderAvailable
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

