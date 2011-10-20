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
package|;
end_package

begin_comment
comment|/**  * SolrServer types defined here to avoid java dependencies to the according java classes  *   * @author Rupert Westenthaler  *   */
end_comment

begin_enum
specifier|public
enum|enum
name|SolrServerTypeEnum
block|{
comment|/**      * Uses an embedded SolrServer that runs within the same virtual machine      */
name|EMBEDDED
block|,
comment|/**      * The default type that can be used for query and updates      */
name|HTTP
block|,
comment|/**      * This server is preferable used for updates      */
name|STREAMING
block|,
comment|/**      * This allows to use load balancing on multiple SolrServers via a round robin algorithm.      */
name|LOAD_BALANCE
block|}
end_enum

end_unit

