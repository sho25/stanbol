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
operator|.
name|standalone
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ServiceLoader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|compress
operator|.
name|archivers
operator|.
name|ArchiveInputStream
import|;
end_import

begin_import
import|import
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
operator|.
name|IndexMetadata
import|;
end_import

begin_import
import|import
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
operator|.
name|ManagedIndexState
import|;
end_import

begin_import
import|import
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
operator|.
name|ManagedSolrServer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_comment
comment|/**  * Wrapper of the the {@link StandaloneManagedSolrServer#getManagedServer()}  * that provides a public default constructor as needed by the  * {@link ServiceLoader} utility.<p>  * Basically this Wrapper allows to initialise the default managed Solr  * server by calling  *<pre><code>  *    {@link ServiceLoader}&lt;ManagedSolrServer&gt; loader =   *        {@link ServiceLoader#load(Class) ServiceLoader.load}({@link ManagedSolrServer}.class};  *    {@link Iterator} it = {@link ServiceLoader#iterator() loader.iterator()};  *    {@link ManagedSolrServer} defaultServer;  *    if(it.hasNext()){  *      defaultServer = it.next();  *    }  *</code></pre>  * @author westei  *  */
end_comment

begin_class
specifier|public
class|class
name|DefaultStandaloneManagedSolrServerWrapper
implements|implements
name|ManagedSolrServer
block|{
name|ManagedSolrServer
name|defaultServer
decl_stmt|;
specifier|public
name|DefaultStandaloneManagedSolrServerWrapper
parameter_list|()
block|{
name|defaultServer
operator|=
name|StandaloneManagedSolrServer
operator|.
name|getManagedServer
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|IndexMetadata
name|createSolrIndex
parameter_list|(
name|String
name|coreName
parameter_list|,
name|String
name|indexPath
parameter_list|,
name|Properties
name|properties
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|defaultServer
operator|.
name|createSolrIndex
argument_list|(
name|coreName
argument_list|,
name|indexPath
argument_list|,
name|properties
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|File
name|getManagedDirectory
parameter_list|()
block|{
return|return
name|defaultServer
operator|.
name|getManagedDirectory
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getServerName
parameter_list|()
block|{
return|return
name|defaultServer
operator|.
name|getServerName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|File
name|getSolrIndexDirectory
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|defaultServer
operator|.
name|getSolrIndexDirectory
argument_list|(
name|name
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isManagedIndex
parameter_list|(
name|String
name|solrIndexName
parameter_list|)
block|{
return|return
name|defaultServer
operator|.
name|isManagedIndex
argument_list|(
name|solrIndexName
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeIndex
parameter_list|(
name|String
name|name
parameter_list|,
name|boolean
name|deleteFiles
parameter_list|)
block|{
name|defaultServer
operator|.
name|removeIndex
argument_list|(
name|name
argument_list|,
name|deleteFiles
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|IndexMetadata
name|updateIndex
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|resourceName
parameter_list|,
name|Properties
name|properties
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|defaultServer
operator|.
name|updateIndex
argument_list|(
name|name
argument_list|,
name|resourceName
argument_list|,
name|properties
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|IndexMetadata
name|activateIndex
parameter_list|(
name|String
name|indexName
parameter_list|)
throws|throws
name|IOException
throws|,
name|SAXException
block|{
return|return
name|defaultServer
operator|.
name|activateIndex
argument_list|(
name|indexName
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|IndexMetadata
name|createSolrIndex
parameter_list|(
name|String
name|indexName
parameter_list|,
name|ArchiveInputStream
name|ais
parameter_list|)
throws|throws
name|IOException
throws|,
name|SAXException
block|{
return|return
name|defaultServer
operator|.
name|createSolrIndex
argument_list|(
name|indexName
argument_list|,
name|ais
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|IndexMetadata
name|deactivateIndex
parameter_list|(
name|String
name|indexName
parameter_list|)
block|{
return|return
name|defaultServer
operator|.
name|deactivateIndex
argument_list|(
name|indexName
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|IndexMetadata
name|getIndexMetadata
parameter_list|(
name|String
name|indexName
parameter_list|)
block|{
return|return
name|defaultServer
operator|.
name|getIndexMetadata
argument_list|(
name|indexName
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|ManagedIndexState
name|getIndexState
parameter_list|(
name|String
name|indexName
parameter_list|)
block|{
return|return
name|defaultServer
operator|.
name|getIndexState
argument_list|(
name|indexName
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|IndexMetadata
argument_list|>
name|getIndexes
parameter_list|(
name|ManagedIndexState
name|state
parameter_list|)
block|{
return|return
name|defaultServer
operator|.
name|getIndexes
argument_list|(
name|state
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|IndexMetadata
name|updateIndex
parameter_list|(
name|String
name|indexName
parameter_list|,
name|ArchiveInputStream
name|ais
parameter_list|)
throws|throws
name|IOException
throws|,
name|SAXException
block|{
return|return
name|defaultServer
operator|.
name|updateIndex
argument_list|(
name|indexName
argument_list|,
name|ais
argument_list|)
return|;
block|}
block|}
end_class

end_unit

