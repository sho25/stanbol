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
name|impl
package|;
end_package

begin_import
import|import static
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
operator|.
name|PROPERTY_ANALYZER_FACTORY_NAME
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|common
operator|.
name|SolrException
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
name|SolrResourceLoader
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
name|utils
operator|.
name|RegisteredSolrAnalyzerFactory
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
name|InvalidSyntaxException
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
specifier|final
class|class
name|OsgiResourceLoaderUtil
block|{
comment|/**     * Restrict instantiation     */
specifier|private
name|OsgiResourceLoaderUtil
parameter_list|()
block|{}
comment|/*         * static members form the SolrResourceLoader that are not visible to this         * class         */
specifier|static
specifier|final
name|String
name|project
init|=
literal|"solr"
decl_stmt|;
specifier|static
specifier|final
name|String
name|base
init|=
literal|"org.apache"
operator|+
literal|"."
operator|+
name|project
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Pattern
name|legacyAnalysisPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"((\\Q"
operator|+
name|base
operator|+
literal|".analysis.\\E)|(\\Q"
operator|+
name|project
operator|+
literal|".\\E))([\\p{L}_$][\\p{L}\\p{N}_$]+?)(TokenFilter|Filter|Tokenizer|CharFilter)Factory"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|OsgiResourceLoaderUtil
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Finds classes by using {@link RegisteredSolrAnalyzerFactory} with a       * filter over {@link SolrConstants#PROPERTY_ANALYZER_FACTORY_NAME}.      * @param bc the {@link BundleContext} used for the search      * @param cname the cname as parsed to {@link SolrResourceLoader#findClass(String, Class, String...)}      * @param expectedType the expected type as parsed to {@link SolrResourceLoader#findClass(String, Class, String...)}      * @param subpackages the subpackages as parsed to {@link SolrResourceLoader#findClass(String, Class, String...)}      * @return the class      */
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|Class
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|findOsgiClass
parameter_list|(
name|BundleContext
name|bc
parameter_list|,
name|String
name|cname
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|expectedType
parameter_list|,
name|String
modifier|...
name|subpackages
parameter_list|)
block|{
name|Class
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|clazz
init|=
literal|null
decl_stmt|;
name|RuntimeException
name|parentEx
init|=
literal|null
decl_stmt|;
specifier|final
name|Matcher
name|m
init|=
name|legacyAnalysisPattern
operator|.
name|matcher
argument_list|(
name|cname
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|matches
argument_list|()
condition|)
block|{
specifier|final
name|String
name|name
init|=
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
decl_stmt|;
name|log
operator|.
name|trace
argument_list|(
literal|"Trying to load class from analysis SPI using name='{}'"
argument_list|,
name|name
argument_list|)
expr_stmt|;
name|ServiceReference
index|[]
name|referenced
decl_stmt|;
name|String
name|filter
decl_stmt|;
try|try
block|{
name|filter
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"(%s=%s)"
argument_list|,
name|PROPERTY_ANALYZER_FACTORY_NAME
argument_list|,
name|name
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
argument_list|)
expr_stmt|;
name|referenced
operator|=
name|bc
operator|.
name|getServiceReferences
argument_list|(
name|RegisteredSolrAnalyzerFactory
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
name|filter
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvalidSyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create Filter for Service with name '"
operator|+
name|name
operator|+
literal|"'!"
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
name|referenced
operator|!=
literal|null
operator|&&
name|referenced
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|Object
name|service
init|=
name|bc
operator|.
name|getService
argument_list|(
name|referenced
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|service
operator|instanceof
name|RegisteredSolrAnalyzerFactory
condition|)
block|{
comment|//TODO: we could check the type here
name|clazz
operator|=
operator|(
operator|(
name|RegisteredSolrAnalyzerFactory
operator|)
name|service
operator|)
operator|.
name|getFactoryClass
argument_list|()
expr_stmt|;
comment|//we do not use a service so immediately unget it
name|bc
operator|.
name|ungetService
argument_list|(
name|referenced
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
return|return
name|clazz
return|;
block|}
block|}
else|else
block|{
name|parentEx
operator|=
operator|new
name|SolrException
argument_list|(
name|SolrException
operator|.
name|ErrorCode
operator|.
name|SERVER_ERROR
argument_list|,
literal|"Error loading Class '"
operator|+
name|cname
operator|+
literal|"' via OSGI service Registry by using filter '"
operator|+
name|filter
operator|+
literal|"'!"
argument_list|,
name|parentEx
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|parentEx
operator|!=
literal|null
condition|)
block|{
throw|throw
name|parentEx
throw|;
block|}
else|else
block|{
throw|throw
operator|new
name|SolrException
argument_list|(
name|SolrException
operator|.
name|ErrorCode
operator|.
name|SERVER_ERROR
argument_list|,
literal|"Error loading class '"
operator|+
name|cname
operator|+
literal|"'"
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

