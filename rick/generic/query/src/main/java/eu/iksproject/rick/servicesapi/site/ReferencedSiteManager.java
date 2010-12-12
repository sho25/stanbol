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
name|util
operator|.
name|Collection
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

begin_interface
specifier|public
interface|interface
name|ReferencedSiteManager
block|{
comment|/**      * Returns if a site with the parsed id is referenced      * @param baseUri the base URI      * @return<code>true</code> if a site with the parsed ID is present.      * Otherwise<code>false</code>.      */
name|boolean
name|isReferred
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * Getter for the referenced site based on the id      * @param baseUri the base URI of the referred Site      * @return the {@link ReferencedSite} or<code>null</code> if no site is      * present for the parsed base ID.      */
name|ReferencedSite
name|getReferencedSite
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
comment|/**      * TODO's:      *<ul>      *<li> use the ReferenceManager specific Exception to this Method      *<li> maybe use an own data structure instead of the generic Dictionary class      *<li> review the API based creation of Sites      *</ul>      * @param baseUri      * @param properties      */
name|void
name|addReferredSite
parameter_list|(
name|String
name|baseUri
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|?
argument_list|>
name|properties
parameter_list|)
function_decl|;
comment|/**      * Getter for Sites that manages entities with the given ID. A Site can      * define a list of prefixes of Entities ID it manages. This method can      * be used to retrieve all the Site that may be able to dereference the      * parsed entity id      * @param entityUri the ID of the entity      * @return A list of referenced sites that may manage the entity in question.      */
name|Collection
argument_list|<
name|ReferencedSite
argument_list|>
name|getReferencedSitesByEntityPrefix
parameter_list|(
name|String
name|entityUri
parameter_list|)
function_decl|;
comment|/*      * NOTE: We need a way to add/remove referred sites. But this may be done      * via the OSGI ManagedServiceFactory interface. Implementations of the      * Site Interface would than also implement the ManagedService interface      */
block|}
end_interface

end_unit

