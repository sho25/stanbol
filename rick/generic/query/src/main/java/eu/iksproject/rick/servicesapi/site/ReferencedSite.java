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

begin_interface
specifier|public
interface|interface
name|ReferencedSite
extends|extends
name|ConfiguredSite
extends|,
name|EntityDereferencer
block|{
comment|//    /**
comment|//     * Whether the parsed entity ID can be dereferenced by this Dereferencer or
comment|//     * not.<br>
comment|//     * The implementation may not directly check if the parsed URI is present by
comment|//     * a query to the site, but only check some patterns of the parsed URI.
comment|//     * @param uri the URI to be checked
comment|//     * @return<code>true</code> of URIs of that kind can be typically dereferenced
comment|//     * by this service instance.
comment|//     */
comment|//    boolean canDereference(String uri);
comment|//    /**
comment|//     * Generic getter for the data of the parsed entity id
comment|//     * @param uri the entity to dereference
comment|//     * @param contentType the content type of the data
comment|//     * @return the data or<code>null</code> if not present or wrong data type
comment|//     * TODO: we should use exceptions instead of returning null!
comment|//     */
comment|//    InputStream dereference(String uri,String contentType) throws IOException;
comment|//    /**
comment|//     * Direct Support for Rdf Triples
comment|//     * TODO: do we need that, or should that be the responsibility of an other
comment|//     * component
comment|//     * TODO: I would like to remove the dependency to {@link TripleCollection} here.
comment|//     *       It would be better to use {@link Representation} instead!
comment|//     * @param uri
comment|//     * @return
comment|//     */
comment|//    Representation dereference(String uri) throws IOException;
block|}
end_interface

end_unit

