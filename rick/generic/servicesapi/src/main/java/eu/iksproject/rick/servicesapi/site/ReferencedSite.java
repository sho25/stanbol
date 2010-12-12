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
name|mapping
operator|.
name|FieldMapper
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
name|mapping
operator|.
name|FieldMapping
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
name|model
operator|.
name|Sign
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
name|query
operator|.
name|Constraint
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
name|query
operator|.
name|FieldQuery
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
name|query
operator|.
name|FieldQueryFactory
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
name|query
operator|.
name|QueryResultList
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
name|Cache
import|;
end_import

begin_interface
specifier|public
interface|interface
name|ReferencedSite
extends|extends
name|ConfiguredSite
block|{
comment|/**      * Searches for entities based on the parsed {@link FieldQuery} and returns      * the references (ids). Note that selected fields of the query are ignored.      * @param query the query      * @return the references of the found entities      * @throws ReferencedSiteException If the request can not be executed both on      * the {@link Cache} and by using the {@link EntityDereferencer}/      * {@link EntitySearcher} accessing the remote site. For errors with the      * remote site the cause will always be a Yard Exceptions. Errors for remote      * Sites are usually IOExceptions.      */
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|findReferences
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|ReferencedSiteException
function_decl|;
comment|/**      * Searches for entities based on the parsed {@link FieldQuery} and returns      * representations as defined by the selected fields of the query. Note that      * if the query defines also {@link Constraint}s for selected fields, that      * the returned representation will only contain values selected by such      * constraints.      * @param query the query      * @return the found entities as representation containing only the selected      * fields and there values.      * @throws ReferencedSiteException If the request can not be executed both on      * the {@link Cache} and by using the {@link EntityDereferencer}/      * {@link EntitySearcher} accessing the remote site. For errors with the      * remote site the cause will always be a Yard Exceptions. Errors for remote      * Sites are usually IOExceptions.      */
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|find
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|ReferencedSiteException
function_decl|;
comment|/**      * Searches for Signs based on the parsed {@link FieldQuery} and returns      * the selected Signs including the whole representation. Note that selected      * fields of the query are ignored.      * @param query the query      * @return All Entities selected by the Query.      * @throws ReferencedSiteException If the request can not be executed both on      * the {@link Cache} and by using the {@link EntityDereferencer}/      * {@link EntitySearcher} accessing the remote site. For errors with the      * remote site the cause will always be a Yard Exceptions. Errors for remote      * Sites are usually IOExceptions.      */
name|QueryResultList
argument_list|<
name|Sign
argument_list|>
name|findSigns
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|ReferencedSiteException
function_decl|;
comment|/**      * Getter for the Sign by the id      * @param id the id of the entity      * @return the entity or<code>null</code> if not found      * @throws ReferencedSiteException If the request can not be executed both on      * the {@link Cache} and by using the {@link EntityDereferencer}/      * {@link EntitySearcher} accessing the remote site. For errors with the      * remote site the cause will always be a Yard Exceptions. Errors for remote      * Sites are usually IOExceptions.      */
name|Sign
name|getSign
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|ReferencedSiteException
function_decl|;
comment|/**      * Getter for the Content of the Entity      * @param id the id of the Entity      * @param contentType the requested contentType      * @return the content or<code>null</code> if no entity with the parsed id      * was found or the parsed ContentType is not supported for this Entity      * @throws ReferencedSiteException If the request can not be executed both on      * the {@link Cache} and by using the {@link EntityDereferencer}/      * {@link EntitySearcher} accessing the remote site. For errors with the      * remote site the cause will always be a Yard Exceptions. Errors for remote      * Sites are usually IOExceptions.      */
name|InputStream
name|getContent
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|contentType
parameter_list|)
throws|throws
name|ReferencedSiteException
function_decl|;
comment|/**      * Getter for the FieldMappings configured for this Site      * @return The {@link FieldMapping} present for this Site.      */
name|FieldMapper
name|getFieldMapper
parameter_list|()
function_decl|;
comment|/**      * Getter for the QueryFactory implementation preferable used with this Site.      * Note that Site MUST support query instances regardless of there specific      * implementation. However specific implementations might have performance      * advantages for query processing and may be even execution. Therefore      * if one creates queries that are specifically executed on this specific      * site, that it is best practice to use the instance provided by this      * method.      * @return The query factory of this site.      */
name|FieldQueryFactory
name|getQueryFactory
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

