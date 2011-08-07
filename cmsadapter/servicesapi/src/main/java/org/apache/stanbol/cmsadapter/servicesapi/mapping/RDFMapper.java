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
name|servicesapi
operator|.
name|mapping
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
name|MGraph
import|;
end_import

begin_comment
comment|/**  * Goal of this interface is to provide a uniform mechanism to store RDF data to JCR or CMIS repositories  * based on cms vocabulary annotations on top of the raw RDF.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RDFMapper
block|{
comment|/**      * This method stores the data passed within an {@link MGraph} to repository according      * "CMS vocabulary annotations".      *       * @param session      *            This is a session object which is used to interact with JCR or CMIS repositories      * @param annotatedGraph      *            This {@link MGraph} object is an enhanced version of raw RDF data with "CMS vocabulary"      *            annotations according to {@link RDFBridge}s.      * @throws RDFBridgeException      */
name|void
name|storeRDFinRepository
parameter_list|(
name|Object
name|session
parameter_list|,
name|MGraph
name|annotatedGraph
parameter_list|)
throws|throws
name|RDFBridgeException
function_decl|;
block|}
end_interface

end_unit

