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

begin_interface
specifier|public
interface|interface
name|XDRegistrySource
block|{
comment|/** 	 * Each invocation will return a new InputStream. 	 *  	 * @return 	 */
name|InputStream
name|getInputStream
parameter_list|()
function_decl|;
name|IRI
name|getPhysicalIRI
parameter_list|()
function_decl|;
comment|/** 	 * Each invocation will return a new Reader. 	 *  	 * @return 	 */
name|Reader
name|getReader
parameter_list|()
function_decl|;
name|boolean
name|isInputStreamAvailable
parameter_list|()
function_decl|;
name|boolean
name|isReaderAvailable
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

