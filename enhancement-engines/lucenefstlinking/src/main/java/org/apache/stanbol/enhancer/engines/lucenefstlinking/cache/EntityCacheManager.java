begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|lucenefstlinking
operator|.
name|cache
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|document
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|search
operator|.
name|SolrIndexSearcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|util
operator|.
name|RefCounted
import|;
end_import

begin_comment
comment|/**  * Manages a Cache for Entity data required for EntityLinking. Cached entity  * data are represented by Lucene {@link Document}s.  *<p>  * This is expected to manage a single {@link EntityCache} for the current  * version of the Lucene index. A 'new' version is expected as soon as  * {@link #getCache(Object)} is called for a different version. In that case  * the current {@link EntityCache} should be cleared and a new empty one  * needs to be created. The new Cache might get autowarmed (if supported and  * configured)  *<p>  * Implementations need to wait with clearing/closing outdated {@link EntityCache}  * instances until the old version is no longer used   * ({@link RefCounted#close()} is called).  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityCacheManager
block|{
comment|/**      * Getter for a reference counting instance of the {@link EntityCache}.      * Callers need to ensure that {@link RefCounted#decref()} is called when the      * do no longer need the obtained DocumentCache instance.      * @param version the version object. Typically the current       * {@link SolrIndexSearcher} instance can be used as version object as a new       * cache instance should be created if a new index searcher was opened by       * the SolrCore.      * @return A counting reference to the EntityCache      */
name|RefCounted
argument_list|<
name|EntityCache
argument_list|>
name|getCache
parameter_list|(
name|Object
name|version
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

