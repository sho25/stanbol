begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|kres
operator|.
name|jersey
operator|.
name|processors
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
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|ext
operator|.
name|Provider
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

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|api
operator|.
name|view
operator|.
name|Viewable
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|spi
operator|.
name|resource
operator|.
name|Singleton
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jersey
operator|.
name|spi
operator|.
name|template
operator|.
name|ViewProcessor
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|cache
operator|.
name|ClassTemplateLoader
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|Template
import|;
end_import

begin_comment
comment|/**  * Match a Viewable-named view with a Freemarker template.  *  * This class is based on the following original implementation:  * http://github.com/cwinters/jersey-freemarker/  *  *<p>  * You can configure the location of your templates with the context param  * 'freemarker.template.path'. If not assigned we'll use a default of  *<tt>WEB-INF/templates</tt>. Note that this uses Freemarker's  * {@link freemarker.cache.WebappTemplateLoader} to load/cache the templates, so  * check its docs (or crank up the logging under the 'freemarker.cache' package)  * if your templates aren't getting loaded.  *</p>  *  *<p>  * This will put your Viewable's model object in the template variable "it",  * unless the model is a Map. If so, the values will be assigned to the template  * assuming the map is of type<tt>Map&lt;String,Object></tt>.  *</p>  *  *<p>  * There are a number of methods you can override to change the behavior, such  * as handling processing exceptions, changing the default template extension,  * or adding variables to be assigned to every template context.  *</p>  *  * @author Chris Winters<chris@cwinters.com> // original code  * @author Olivier Grisel<ogrisel@nuxeo.com> // ViewProcessor refactoring  */
end_comment

begin_class
annotation|@
name|Singleton
annotation|@
name|Provider
specifier|public
class|class
name|KReSViewProcessor
implements|implements
name|ViewProcessor
argument_list|<
name|Template
argument_list|>
block|{
specifier|public
specifier|static
specifier|final
name|String
name|FREEMARKER_TEMPLATE_PATH_INIT_PARAM
init|=
literal|"kres.freemarker.template.path"
decl_stmt|;
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|protected
name|Configuration
name|freemarkerConfig
decl_stmt|;
specifier|protected
name|String
name|rootPath
decl_stmt|;
annotation|@
name|Context
specifier|protected
name|ServletContext
name|context
decl_stmt|;
specifier|public
name|KReSViewProcessor
parameter_list|()
block|{     }
comment|/**      * @return extension for templates, ".ftl" by default; if we don't see this      *         at the end of your view we'll append it so we can find the      *         template resource      */
specifier|protected
name|String
name|getDefaultExtension
parameter_list|()
block|{
return|return
literal|".ftl"
return|;
block|}
comment|/**      * Define additional variables to make available to the template.      *      * @param viewableVariables variables provided by whomever generated the      *            viewable object; these are provided for reference only, there      *            will be no effect if you modify this map      * @return new variables for the template context, which will override any      *         defaults provided      */
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getVariablesForTemplate
parameter_list|(
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|viewableVariables
parameter_list|)
block|{
return|return
name|Collections
operator|.
name|emptyMap
argument_list|()
return|;
block|}
comment|/**      * Catch any exception generated during template processing.      *      * @param t throwable caught      * @param templatePath path of template we're executing      * @param templateContext context use when evaluating this template      * @param out output stream from servlet container      * @throws IOException on any write errors, or if you want to rethrow      */
specifier|protected
name|void
name|onProcessException
parameter_list|(
specifier|final
name|Throwable
name|t
parameter_list|,
specifier|final
name|Template
name|template
parameter_list|,
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|templateContext
parameter_list|,
specifier|final
name|OutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Error processing freemarker template @ "
operator|+
name|template
operator|.
name|getName
argument_list|()
operator|+
literal|": "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|"<pre>"
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|printStackTrace
argument_list|(
operator|new
name|PrintStream
argument_list|(
name|out
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|"</pre>"
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Modify freemarker configuration after we've created it and applied any      * settings from 'freemarker.properties' on the classpath.      *      * @param config configuration we've created so far      * @param context servlet context used to create the configuration      */
specifier|protected
name|void
name|assignFreemarkerConfig
parameter_list|(
specifier|final
name|Configuration
name|config
parameter_list|,
specifier|final
name|ServletContext
name|context
parameter_list|)
block|{
comment|// TODO read those parameters from context instead of hardcoding them
comment|// don't always put a ',' in numbers (e.g., id=2000 vs id=2,000)
name|config
operator|.
name|setNumberFormat
argument_list|(
literal|"0"
argument_list|)
expr_stmt|;
comment|// don't look for list.en.ftl when list.ftl requested
name|config
operator|.
name|setLocalizedLookup
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// don't cache for more that 2s
name|config
operator|.
name|setTemplateUpdateDelay
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Assigned default freemarker configuration"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|Configuration
name|getConfig
parameter_list|()
block|{
if|if
condition|(
name|freemarkerConfig
operator|==
literal|null
condition|)
block|{
comment|// deferred initialization of the freemarker config to ensure that
comment|// the injected ServletContext is fully functional and for some
comment|// reason the #getInitParam access does not work hence using the
comment|// #getAttribute access after servlet init.
name|Configuration
name|config
init|=
operator|new
name|Configuration
argument_list|()
decl_stmt|;
if|if
condition|(
name|context
operator|!=
literal|null
condition|)
block|{
name|rootPath
operator|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|FREEMARKER_TEMPLATE_PATH_INIT_PARAM
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|rootPath
operator|==
literal|null
operator|||
name|rootPath
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"No 'freemarker.template.path' context-param, "
operator|+
literal|"defaulting to '/WEB-INF/templates'"
argument_list|)
expr_stmt|;
name|rootPath
operator|=
literal|"/WEB-INF/templates"
expr_stmt|;
block|}
name|rootPath
operator|=
name|rootPath
operator|.
name|replaceAll
argument_list|(
literal|"/$"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|config
operator|.
name|setTemplateLoader
argument_list|(
operator|new
name|ClassTemplateLoader
argument_list|(
name|getClass
argument_list|()
argument_list|,
name|rootPath
argument_list|)
argument_list|)
expr_stmt|;
comment|// TODO: make the usage of a freemaker properties file an explicit
comment|// parameter declared in the servlet context instead of magic
comment|// classloading auto-detect. That way the application could
comment|// explicitly override the defaults
specifier|final
name|InputStream
name|fmProps
init|=
name|context
operator|.
name|getResourceAsStream
argument_list|(
literal|"freemarker.properties"
argument_list|)
decl_stmt|;
name|boolean
name|loadDefaults
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|fmProps
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|config
operator|.
name|setSettings
argument_list|(
name|fmProps
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Assigned freemarker configuration from 'freemarker.properties'"
argument_list|)
expr_stmt|;
name|loadDefaults
operator|=
literal|false
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Failed to load/assign freemarker.properties, will"
operator|+
literal|" use default settings instead: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|loadDefaults
condition|)
block|{
name|assignFreemarkerConfig
argument_list|(
name|config
argument_list|,
name|context
argument_list|)
expr_stmt|;
block|}
name|freemarkerConfig
operator|=
name|config
expr_stmt|;
block|}
return|return
name|freemarkerConfig
return|;
block|}
specifier|public
name|Template
name|resolve
parameter_list|(
specifier|final
name|String
name|path
parameter_list|)
block|{
comment|// accept both '/path/to/template' and '/path/to/template.ftl'
specifier|final
name|String
name|defaultExtension
init|=
name|getDefaultExtension
argument_list|()
decl_stmt|;
specifier|final
name|String
name|filePath
init|=
name|path
operator|.
name|endsWith
argument_list|(
name|defaultExtension
argument_list|)
condition|?
name|path
else|:
name|path
operator|+
name|defaultExtension
decl_stmt|;
try|try
block|{
return|return
name|getConfig
argument_list|()
operator|.
name|getTemplate
argument_list|(
name|filePath
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Failed to load freemaker template: "
operator|+
name|rootPath
operator|+
name|filePath
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|void
name|writeTo
parameter_list|(
name|Template
name|template
parameter_list|,
name|Viewable
name|viewable
parameter_list|,
name|OutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
comment|// send status + headers
name|Object
name|model
init|=
name|viewable
operator|.
name|getModel
argument_list|()
decl_stmt|;
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|vars
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|model
operator|instanceof
name|Map
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
condition|)
block|{
name|vars
operator|.
name|putAll
argument_list|(
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|model
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|vars
operator|.
name|put
argument_list|(
literal|"it"
argument_list|,
name|model
argument_list|)
expr_stmt|;
block|}
comment|// override custom variables if any
name|vars
operator|.
name|putAll
argument_list|(
name|getVariablesForTemplate
argument_list|(
name|vars
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|OutputStreamWriter
name|writer
init|=
operator|new
name|OutputStreamWriter
argument_list|(
name|out
argument_list|)
decl_stmt|;
try|try
block|{
name|template
operator|.
name|process
argument_list|(
name|vars
argument_list|,
name|writer
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|onProcessException
argument_list|(
name|t
argument_list|,
name|template
argument_list|,
name|vars
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

