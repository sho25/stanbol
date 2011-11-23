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
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

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
name|InputStream
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
name|ArchiveException
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
name|commons
operator|.
name|compress
operator|.
name|archivers
operator|.
name|ArchiveStreamFactory
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
name|core
operator|.
name|SolrCore
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
name|SolrConstants
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
name|utils
operator|.
name|ConfigUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
specifier|public
class|class
name|ManagementUtils
block|{
comment|/**      * The logger      */
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ManagementUtils
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Substitutes ${property.name} with the values retrieved via<ul>      *<li> {@link BundleContext#getProperty(String)} or      *<li> {@link System#getProperty(String, String)} if the parsed      * {@link BundleContext} is<code>null</code>      *</ul>      * Substitutes with an empty string if the property is not present. If      * the substitution does not end with {@link File#separatorChar}, than it is      * appended to allow easily creating paths relative to root directory available      * as property regardless if the property includes/excludes the final      * separator char.      *<p>      * Nested substitutions are NOT supported. However multiple substitutions are supported.      *<p>      * If someone knows a default implementation feel free to replace!      *       * @param value      *            the value to substitute      * @param bundleContext      *            If not<code>null</code> the {@link BundleContext#getProperty(String)} is used instead of      *            the {@link System#getProperty(String)}. By that it is possible to use OSGI only properties      *            for substitution.      * @return the substituted value      */
specifier|public
specifier|static
name|String
name|substituteProperty
parameter_list|(
name|String
name|value
parameter_list|,
name|BundleContext
name|bundleContext
parameter_list|)
block|{
name|int
name|prevAt
init|=
literal|0
decl_stmt|;
name|int
name|foundAt
init|=
literal|0
decl_stmt|;
name|StringBuilder
name|substitution
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|foundAt
operator|=
name|value
operator|.
name|indexOf
argument_list|(
literal|"${"
argument_list|,
name|prevAt
argument_list|)
operator|)
operator|>=
name|prevAt
condition|)
block|{
name|substitution
operator|.
name|append
argument_list|(
name|value
operator|.
name|substring
argument_list|(
name|prevAt
argument_list|,
name|foundAt
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|propertyName
init|=
name|value
operator|.
name|substring
argument_list|(
name|foundAt
operator|+
literal|2
argument_list|,
name|value
operator|.
name|indexOf
argument_list|(
literal|'}'
argument_list|,
name|foundAt
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|propertyValue
init|=
name|bundleContext
operator|==
literal|null
condition|?
comment|// if no bundleContext is available
name|System
operator|.
name|getProperty
argument_list|(
name|propertyName
argument_list|)
else|:
comment|// use the System properties
name|bundleContext
operator|.
name|getProperty
argument_list|(
name|propertyName
argument_list|)
decl_stmt|;
if|if
condition|(
name|propertyValue
operator|!=
literal|null
condition|)
block|{
name|substitution
operator|.
name|append
argument_list|(
name|propertyValue
argument_list|)
expr_stmt|;
if|if
condition|(
name|propertyValue
operator|.
name|charAt
argument_list|(
name|propertyValue
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|!=
name|File
operator|.
name|separatorChar
condition|)
block|{
name|substitution
operator|.
name|append
argument_list|(
name|File
operator|.
name|separatorChar
argument_list|)
expr_stmt|;
block|}
block|}
comment|//else nothing to append
name|prevAt
operator|=
name|foundAt
operator|+
name|propertyName
operator|.
name|length
argument_list|()
operator|+
literal|3
expr_stmt|;
comment|// +3 -> "${}".length
block|}
name|substitution
operator|.
name|append
argument_list|(
name|value
operator|.
name|substring
argument_list|(
name|prevAt
argument_list|,
name|value
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|substitution
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * An instance of the {@link ArchiveStreamFactory}      */
specifier|public
specifier|static
specifier|final
name|ArchiveStreamFactory
name|archiveStreamFactory
init|=
operator|new
name|ArchiveStreamFactory
argument_list|()
decl_stmt|;
comment|/**      * Tries to create an {@link ArchiveInputStream} based on the parsed {@link InputStream}.      * First the provided resource name is used to detect the type of the archive.      * if that does not work, or the parsed resource name is<code>null</code> the      * stream is created by using the auto-detection of the archive type.      * @param resourceName the name of the resource or<code>null</code>      * @param is the {@link InputStream}      * @return the {@link ArchiveInputStream}      * @throws ArchiveException if the {@link InputStream} does not represented any      * supported Archive type      */
specifier|public
specifier|static
name|ArchiveInputStream
name|getArchiveInputStream
parameter_list|(
name|String
name|resourceName
parameter_list|,
name|InputStream
name|is
parameter_list|)
throws|throws
name|ArchiveException
block|{
if|if
condition|(
name|is
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|resourceName
operator|!=
literal|null
condition|)
block|{
try|try
block|{
return|return
name|archiveStreamFactory
operator|.
name|createArchiveInputStream
argument_list|(
name|resourceName
argument_list|,
name|is
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ArchiveException
name|e
parameter_list|)
block|{
comment|//ignore
block|}
block|}
return|return
name|archiveStreamFactory
operator|.
name|createArchiveInputStream
argument_list|(
operator|new
name|BufferedInputStream
argument_list|(
name|is
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Getter for the name of the index within the current       * {@link IndexMetadata#getArchive() archive} set to load the index data      * from. If no archive is set (e.g. if the {@link ArchiveInputStream} was      * directly parsed, than the {@link IndexMetadata#getIndexName() index name}      * directly is used as default.      * @param metadata the {@link IndexMetadata}      * @return the name of the index within the indexArchive used to load the      * data from. In other words the relative path to the index data within the      * index archive.      */
specifier|public
specifier|static
name|String
name|getArchiveCoreName
parameter_list|(
specifier|final
name|IndexMetadata
name|metadata
parameter_list|)
block|{
name|String
name|name
init|=
name|metadata
operator|.
name|getIndexName
argument_list|()
decl_stmt|;
name|String
name|archiveCoreName
init|=
name|metadata
operator|.
name|getArchive
argument_list|()
decl_stmt|;
if|if
condition|(
name|archiveCoreName
operator|==
literal|null
condition|)
block|{
name|archiveCoreName
operator|=
name|name
expr_stmt|;
block|}
else|else
block|{
comment|//the name of the core in the archive MUST BE the same as
comment|//the name of the archive excluding .solrindex.{archive-format}
name|int
name|split
init|=
name|archiveCoreName
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|split
operator|>
literal|0
condition|)
block|{
name|archiveCoreName
operator|=
name|archiveCoreName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|split
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|archiveCoreName
return|;
block|}
comment|//    /**
comment|//     * Parses the name of the Core from an IndexReference (file url, file path,
comment|//     * index name or server:indexname)
comment|//     * @param indexRef the parsed indexRef
comment|//     * @return
comment|//     */
comment|//    public static String getCoreNameForIndexRef(String indexRef,String serverName) {
comment|//
comment|//        String[] parsedRef = ConfigUtils.parseSolrServerReference(indexRef);
comment|//        String coreName;
comment|//        if(parsedRef[0] != null&& !parsedRef[0].equals(serverName)){
comment|//            coreName = null; //other server
comment|//        } else {
comment|//            coreName = parsedRef[1];
comment|//            if(coreName == null || coreName.isEmpty()){
comment|//                log.warn("The parsed index reference '"+indexRef+"' does not define a valid core name!");
comment|//            }
comment|//        }
comment|//        return coreName;
comment|//    }
comment|/**      * Creates and initialises a {@link IndexMetadata} instance based on the      * parsed {@link SolrCore}      * @param core the {@link SolrCore}      * @param serverName the name of the server      * @return the initialised {@link IndexMetadata}      */
specifier|public
specifier|static
name|IndexMetadata
name|getMetadata
parameter_list|(
name|SolrCore
name|core
parameter_list|,
name|String
name|serverName
parameter_list|)
block|{
if|if
condition|(
name|core
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|IndexMetadata
name|metadata
init|=
operator|new
name|IndexMetadata
argument_list|()
decl_stmt|;
if|if
condition|(
name|serverName
operator|!=
literal|null
condition|)
block|{
name|metadata
operator|.
name|setServerName
argument_list|(
name|serverName
argument_list|)
expr_stmt|;
block|}
name|metadata
operator|.
name|setSynchronized
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|updateMetadata
argument_list|(
name|metadata
argument_list|,
name|core
argument_list|)
expr_stmt|;
return|return
name|metadata
return|;
block|}
comment|/**      * Updates the parsed {@link IndexMetadata} instance based on the      * properties of the parsed {@link SolrCore}.<p>      * This sets the state, index name and the directory.      * @param metadata the {@link IndexMetadata} to update      * @param core the core      */
specifier|public
specifier|static
name|void
name|updateMetadata
parameter_list|(
name|IndexMetadata
name|metadata
parameter_list|,
name|SolrCore
name|core
parameter_list|)
block|{
if|if
condition|(
name|metadata
operator|==
literal|null
operator|||
name|core
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|metadata
operator|.
name|setState
argument_list|(
name|ManagedIndexState
operator|.
name|ACTIVE
argument_list|)
expr_stmt|;
name|metadata
operator|.
name|setIndexName
argument_list|(
name|core
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|metadata
operator|.
name|setDirectory
argument_list|(
name|core
operator|.
name|getCoreDescriptor
argument_list|()
operator|.
name|getInstanceDir
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Updates the parsed metadata based on the properties of the       * {@link ServiceReference}.<p>      * This updates the index name, server name, and the directory value based      * on the corresponding keys as defined in {@link SolrConstants}      * @param metadata the metadata to update      * @param coreRef the ServiceReference used to update the metadata      */
specifier|public
specifier|static
name|void
name|updateMetadata
parameter_list|(
name|IndexMetadata
name|metadata
parameter_list|,
name|ServiceReference
name|coreRef
parameter_list|)
block|{
if|if
condition|(
name|metadata
operator|==
literal|null
operator|||
name|coreRef
operator|==
literal|null
condition|)
block|{
return|return;
block|}
comment|//        if(SolrCore.class.getName().equals(coreRef.getProperty(Constants.OBJECTCLASS))){
name|String
name|value
init|=
operator|(
name|String
operator|)
name|coreRef
operator|.
name|getProperty
argument_list|(
name|SolrConstants
operator|.
name|PROPERTY_CORE_DIR
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|metadata
operator|.
name|setDirectory
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
name|value
operator|=
operator|(
name|String
operator|)
name|coreRef
operator|.
name|getProperty
argument_list|(
name|SolrConstants
operator|.
name|PROPERTY_CORE_NAME
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|metadata
operator|.
name|setIndexName
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
name|value
operator|=
operator|(
name|String
operator|)
name|coreRef
operator|.
name|getProperty
argument_list|(
name|SolrConstants
operator|.
name|PROPERTY_SERVER_NAME
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|metadata
operator|.
name|setServerName
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
comment|//        } //else parsed service Reference does not refer to a SolrCore
block|}
block|}
end_class

end_unit

