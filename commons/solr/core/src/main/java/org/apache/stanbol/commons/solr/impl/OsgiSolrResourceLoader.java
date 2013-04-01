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
name|Properties
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
name|lucene
operator|.
name|analysis
operator|.
name|util
operator|.
name|CharFilterFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|util
operator|.
name|TokenFilterFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|analysis
operator|.
name|util
operator|.
name|TokenizerFactory
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
name|SolrCore
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
name|SolrServerAdapter
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
name|AbstractAnalyzerFoctoryActivator
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

begin_comment
comment|/**  * Overrides the {@link SolrResourceLoader#findClass(String, Class, String...)}  * method to look for {@link TokenFilterFactory}, {@link CharFilterFactory} and  * {@link TokenizerFactory} registered as OSGI services.<p>  * This is because Solr 4 uses SPI ("META-INF/services" files) to lookup  * those factories and this does not work across bundles in OSGI.<p>  * This {@link SolrResourceLoader} variant is intended to be used together  * with Bundle-Activators based on the {@link AbstractAnalyzerFoctoryActivator}.  *<p> The {@link SolrServerAdapter} does use this class as {@link SolrResourceLoader}  * when creating {@link SolrCore}s.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|OsgiSolrResourceLoader
extends|extends
name|SolrResourceLoader
block|{
comment|/*      * static members form the parent implementation that are not visible to subclasses in a different package      */
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
specifier|protected
specifier|final
name|BundleContext
name|bc
decl_stmt|;
specifier|public
name|OsgiSolrResourceLoader
parameter_list|(
name|BundleContext
name|bc
parameter_list|,
name|String
name|instanceDir
parameter_list|)
block|{
name|super
argument_list|(
name|instanceDir
argument_list|,
name|OsgiSolrResourceLoader
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|bc
operator|=
name|bc
expr_stmt|;
block|}
specifier|public
name|OsgiSolrResourceLoader
parameter_list|(
name|BundleContext
name|bc
parameter_list|,
name|String
name|instanceDir
parameter_list|,
name|ClassLoader
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|instanceDir
argument_list|,
name|parent
argument_list|)
expr_stmt|;
name|this
operator|.
name|bc
operator|=
name|bc
expr_stmt|;
block|}
specifier|public
name|OsgiSolrResourceLoader
parameter_list|(
name|BundleContext
name|bc
parameter_list|,
name|String
name|instanceDir
parameter_list|,
name|ClassLoader
name|parent
parameter_list|,
name|Properties
name|coreProperties
parameter_list|)
block|{
name|super
argument_list|(
name|instanceDir
argument_list|,
name|parent
argument_list|,
name|coreProperties
argument_list|)
expr_stmt|;
name|this
operator|.
name|bc
operator|=
name|bc
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
parameter_list|>
name|Class
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|findClass
parameter_list|(
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
try|try
block|{
name|clazz
operator|=
name|super
operator|.
name|findClass
argument_list|(
name|cname
argument_list|,
name|expectedType
argument_list|,
name|subpackages
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|parentEx
operator|=
name|e
expr_stmt|;
block|}
if|if
condition|(
name|clazz
operator|!=
literal|null
condition|)
block|{
return|return
name|clazz
return|;
block|}
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
try|try
block|{
name|referenced
operator|=
name|bc
operator|.
name|getServiceReferences
argument_list|(
name|expectedType
operator|.
name|getName
argument_list|()
argument_list|,
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
operator|!=
literal|null
condition|)
block|{
name|clazz
operator|=
operator|(
name|Class
argument_list|<
name|?
extends|extends
name|T
argument_list|>
operator|)
name|service
operator|.
name|getClass
argument_list|()
expr_stmt|;
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
comment|//we return the class and do not use the service
return|return
name|clazz
return|;
block|}
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

