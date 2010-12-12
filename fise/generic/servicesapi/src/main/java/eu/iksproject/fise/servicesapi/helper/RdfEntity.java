begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
package|;
end_package

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
name|BNode
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
name|NonLiteral
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

begin_comment
comment|/**  * Super interface for all interfaces using the {@link RdfEntityFactory} to  * create proxy objects.  *  * @author Rupert Westenthaler  */
end_comment

begin_interface
specifier|public
interface|interface
name|RdfEntity
block|{
comment|/**      * Getter for the RDF node represented by the proxy.      *      * @return the node representing the proxy. Typically an {@link UriRef} but      * could be also a {@link BNode}      */
name|NonLiteral
name|getId
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

