begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|solr
operator|.
name|managed
package|;
end_package

begin_class
specifier|public
specifier|final
class|class
name|ManagedIndexConstants
block|{
specifier|private
name|ManagedIndexConstants
parameter_list|()
block|{
comment|/*do not create instances*/
block|}
comment|/**      * Key used to configure the names for the Index-Archive(s) to be used for      * this core. Multiple archives can be parsed by using , as separator.<p>      * Note: that a single Archive can also be provided as second parameter.       * If this is the case it will override this value if present in the third       * parameter of       * {@link ManagedSolrServer#createSolrIndex(String, String, java.util.Properties) createIndex} and      * {@link ManagedSolrServer#updateIndex(String, String, java.util.Properties) updateIndex}.      */
specifier|public
specifier|static
specifier|final
name|String
name|INDEX_ARCHIVES
init|=
literal|"Index-Archive"
decl_stmt|;
comment|/**      * Key used to specify if an index is synchronized with the provided      * Index-Archive. If<code>false</code> the index will be initialised      * by the provided Archive and than stay independent (until explicit calls      * to {@link ManagedSolrServer#updateIndex(String, String, java.util.Properties)}.      * If synchronised the index will stay connected with the Archive.      * So deleting the archive will also cause the index to get inactive and       * making the Index-Archive available again (e.g. a newer version) will      * case an re-initalisation of the index based on the new archive.      */
specifier|public
specifier|static
specifier|final
name|String
name|SYNCHRONIZED
init|=
literal|"Synchronized"
decl_stmt|;
comment|/**      * The name of the index. Note that the name can be also specified as the      * first parameter of       * {@link ManagedSolrServer#createSolrIndex(String, String, java.util.Properties) createIndex} and      * {@link ManagedSolrServer#updateIndex(String, String, java.util.Properties) updateIndex}.      * If this is the case it will override the value provided in the property      * file.      */
specifier|public
specifier|static
specifier|final
name|String
name|INDEX_NAME
init|=
literal|"Index-Name"
decl_stmt|;
comment|/**      * The name of the server to install this index to. If present the      * server will check that the name corresponds to its own name.      */
specifier|public
specifier|static
specifier|final
name|String
name|SERVER_NAME
init|=
literal|"Server-Name"
decl_stmt|;
block|}
end_class

end_unit

