begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|site
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
name|util
operator|.
name|Dictionary
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|Rick
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|yard
operator|.
name|Yard
import|;
end_import

begin_comment
comment|/**  * The {@link ConfiguredSite} defines what external data sources (sites) can  * be used to access entities. Sites are identified and registers with the  * {@link ReferencedSiteManager}. The baseURI is therefore the ID of a Site. Site   * Implementations are responsible for the access and the retrieval of data.<p>   * Users typically need not to border with this Interface, because they use  * the {@link Rick} to access Symbols, map Entities or retrieve Representations.  * The {@link Rick} is responsible to forward requests to the {@link Yard} (local  * cache) or directly to the corresponding {@link EntityDereferencer}.   * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityDereferencer
block|{
comment|/** 	 * The key used to define the baseUri for instances of this Service. 	 * This constants actually uses the value of {@link ReferencedSite#ACCESS_URI} 	 */
name|String
name|ACCESS_URI
init|=
name|ConfiguredSite
operator|.
name|ACCESS_URI
decl_stmt|;
comment|/** 	 * The base uri used to access this site 	 * @return 	 */
name|String
name|getAccessUri
parameter_list|()
function_decl|;
comment|/** 	 * Whether the parsed entity ID can be dereferenced by this Dereferencer or 	 * not.<br> 	 * The implementation may not directly check if the parsed URI is present by 	 * a query to the site, but only check some patterns of the parsed URI.  	 * @param uri the URI to be checked 	 * @return<code>true</code> of URIs of that kind can be typically dereferenced 	 * by this service instance.  	 */
name|boolean
name|canDereference
parameter_list|(
name|String
name|uri
parameter_list|)
function_decl|;
comment|/** 	 * Generic getter for the data of the parsed entity id 	 * @param uri the entity to dereference 	 * @param contentType the content type of the data 	 * @return the data or<code>null</code> if not present or wrong data type 	 * TODO: we should use exceptions instead of returning null! 	 */
name|InputStream
name|dereference
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|contentType
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/** 	 * Dereferences the Representation of the referred Entity 	 * @param uri the uri of the referred entity 	 * @return the representation of<code>null</code> if no Entity was found 	 * for the parsed entity reference. 	 */
name|Representation
name|dereference
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|//	/**
comment|//	 * NOTE Moved to ReferencedSite
comment|//	 * @return
comment|//	 */
comment|//	Dictionary<String, ?> getSiteConfiguration();
block|}
end_interface

end_unit

