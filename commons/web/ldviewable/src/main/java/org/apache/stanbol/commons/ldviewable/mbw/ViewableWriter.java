begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
name|ldviewable
operator|.
name|mbw
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
name|Writer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|annotation
operator|.
name|Annotation
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
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
name|Produces
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
name|WebApplicationException
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
name|MediaType
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
name|MultivaluedMap
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
name|MessageBodyWriter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|ldpathtemplate
operator|.
name|LdRenderer
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
name|ldviewable
operator|.
name|Viewable
import|;
end_import

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|ViewableWriter
operator|.
name|class
argument_list|)
annotation|@
name|Produces
argument_list|(
literal|"text/html"
argument_list|)
specifier|public
class|class
name|ViewableWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|Viewable
argument_list|>
block|{
annotation|@
name|Reference
specifier|private
name|LdRenderer
name|ldRenderer
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|isWriteable
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
return|return
name|Viewable
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|type
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|(
name|Viewable
name|t
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|)
block|{
return|return
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeTo
parameter_list|(
specifier|final
name|Viewable
name|t
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|type
parameter_list|,
name|Type
name|genericType
parameter_list|,
name|Annotation
index|[]
name|annotations
parameter_list|,
name|MediaType
name|mediaType
parameter_list|,
name|MultivaluedMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|httpHeaders
parameter_list|,
name|OutputStream
name|entityStream
parameter_list|)
throws|throws
name|IOException
throws|,
name|WebApplicationException
block|{
name|Writer
name|out
init|=
operator|new
name|OutputStreamWriter
argument_list|(
name|entityStream
argument_list|,
literal|"utf-8"
argument_list|)
decl_stmt|;
name|ldRenderer
operator|.
name|renderPojo
argument_list|(
operator|new
name|Wrapper
argument_list|(
name|t
operator|.
name|getPojo
argument_list|()
argument_list|)
argument_list|,
literal|"html/"
operator|+
name|t
operator|.
name|getTemplatePath
argument_list|()
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
specifier|static
specifier|public
class|class
name|Wrapper
block|{
specifier|private
name|Object
name|wrapped
decl_stmt|;
specifier|public
name|Wrapper
parameter_list|(
name|Object
name|wrapped
parameter_list|)
block|{
name|this
operator|.
name|wrapped
operator|=
name|wrapped
expr_stmt|;
block|}
specifier|public
name|Object
name|getIt
parameter_list|()
block|{
return|return
name|wrapped
return|;
block|}
block|}
block|}
end_class

begin_comment
comment|//TODO check if some frremarker config settings should be taken from there
end_comment

begin_comment
comment|//public class FreemarkerViewProcessor implements ViewProcessor<Template> {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private final Logger log = LoggerFactory.getLogger(getClass());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    protected Configuration freemarkerConfig;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    protected TemplateLoader templateLoader;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    @Context
end_comment

begin_comment
comment|//    protected ServletContext context;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public FreemarkerViewProcessor(TemplateLoader templateLoader) {
end_comment

begin_comment
comment|//        this.templateLoader = templateLoader;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * @return extension for templates, ".ftl" by default; if we don't see this
end_comment

begin_comment
comment|//     *         at the end of your view we'll append it so we can find the
end_comment

begin_comment
comment|//     *         template resource
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    protected String getDefaultExtension() {
end_comment

begin_comment
comment|//        return ".ftl";
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Define additional variables to make available to the template.
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param viewableVariables variables provided by whomever generated the
end_comment

begin_comment
comment|//     *            viewable object; these are provided for reference only, there
end_comment

begin_comment
comment|//     *            will be no effect if you modify this map
end_comment

begin_comment
comment|//     * @return new variables for the template context, which will override any
end_comment

begin_comment
comment|//     *         defaults provided
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    protected Map<String, Object> getVariablesForTemplate(
end_comment

begin_comment
comment|//            final Map<String, Object> viewableVariables) {
end_comment

begin_comment
comment|//        return Collections.emptyMap();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Catch any exception generated during template processing.
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param t throwable caught
end_comment

begin_comment
comment|//     * @param templatePath path of template we're executing
end_comment

begin_comment
comment|//     * @param templateContext context use when evaluating this template
end_comment

begin_comment
comment|//     * @param out output stream from servlet container
end_comment

begin_comment
comment|//     * @throws IOException on any write errors, or if you want to rethrow
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    protected void onProcessException(final Throwable t,
end_comment

begin_comment
comment|//            final Template template, final Map<String, Object> templateContext,
end_comment

begin_comment
comment|//            final OutputStream out) throws IOException {
end_comment

begin_comment
comment|//        log.error("Error processing freemarker template @ "
end_comment

begin_comment
comment|//                + template.getName() + ": " + t.getMessage(), t);
end_comment

begin_comment
comment|//        out.write("<pre>".getBytes());
end_comment

begin_comment
comment|//        t.printStackTrace(new PrintStream(out));
end_comment

begin_comment
comment|//        out.write("</pre>".getBytes());
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Modify freemarker configuration after we've created it and applied any
end_comment

begin_comment
comment|//     * settings from 'freemarker.properties' on the classpath.
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param config configuration we've created so far
end_comment

begin_comment
comment|//     * @param context servlet context used to create the configuration
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    protected void assignFreemarkerConfig(final Configuration config,
end_comment

begin_comment
comment|//            final ServletContext context) {
end_comment

begin_comment
comment|//        // TODO read those parameters from context instead of hardcoding them
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // don't always put a ',' in numbers (e.g., id=2000 vs id=2,000)
end_comment

begin_comment
comment|//        config.setNumberFormat("0");
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // don't look for list.en.ftl when list.ftl requested
end_comment

begin_comment
comment|//        config.setLocalizedLookup(false);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // don't cache for more that 2s
end_comment

begin_comment
comment|//        config.setTemplateUpdateDelay(2);
end_comment

begin_comment
comment|//        config.setDefaultEncoding("utf-8");
end_comment

begin_comment
comment|//        config.setOutputEncoding("utf-8");
end_comment

begin_comment
comment|//        log.info("Assigned default freemarker configuration");
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    protected Configuration getConfig() {
end_comment

begin_comment
comment|//        if (freemarkerConfig == null) {
end_comment

begin_comment
comment|//            // deferred initialization of the freemarker config to ensure that
end_comment

begin_comment
comment|//            // the injected ServletContext is fully functional
end_comment

begin_comment
comment|//            Configuration config = new Configuration();
end_comment

begin_comment
comment|//            config.setTemplateLoader(templateLoader);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            // TODO: make the usage of a freemaker properties file an explicit
end_comment

begin_comment
comment|//            // parameter declared in the servlet context instead of magic
end_comment

begin_comment
comment|//            // classloading auto-detect. That way the application could
end_comment

begin_comment
comment|//            // explicitly override the defaults
end_comment

begin_comment
comment|//            final InputStream fmProps = context.getResourceAsStream("freemarker.properties");
end_comment

begin_comment
comment|//            boolean loadDefaults = true;
end_comment

begin_comment
comment|//            if (fmProps != null) {
end_comment

begin_comment
comment|//                try {
end_comment

begin_comment
comment|//                    config.setSettings(fmProps);
end_comment

begin_comment
comment|//                    log.info("Assigned freemarker configuration from 'freemarker.properties'");
end_comment

begin_comment
comment|//                    loadDefaults = false;
end_comment

begin_comment
comment|//                } catch (Throwable t) {
end_comment

begin_comment
comment|//                    log.warn(
end_comment

begin_comment
comment|//                            "Failed to load/assign freemarker.properties, will"
end_comment

begin_comment
comment|//                                    + " use default settings instead: "
end_comment

begin_comment
comment|//                                    + t.getMessage(), t);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            if (loadDefaults) {
end_comment

begin_comment
comment|//                assignFreemarkerConfig(config, context);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            freemarkerConfig = config;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        return freemarkerConfig;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public Template resolve(final String path) {
end_comment

begin_comment
comment|//        // accept both '/path/to/template' and '/path/to/template.ftl'
end_comment

begin_comment
comment|//        final String defaultExtension = getDefaultExtension();
end_comment

begin_comment
comment|//        final String filePath = path.endsWith(defaultExtension) ? path : path
end_comment

begin_comment
comment|//                + defaultExtension;
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            return getConfig().getTemplate(filePath);
end_comment

begin_comment
comment|//        } catch (IOException e) {
end_comment

begin_comment
comment|//            log.error("Failed to load freemaker template: " + filePath);
end_comment

begin_comment
comment|//            return null;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    @SuppressWarnings("unchecked")
end_comment

begin_comment
comment|//    public void writeTo(Template template, Viewable viewable, OutputStream out)
end_comment

begin_comment
comment|//            throws IOException {
end_comment

begin_comment
comment|//        out.flush(); // send status + headers
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        Object model = viewable.getModel();
end_comment

begin_comment
comment|//        final Map<String, Object> vars = new HashMap<String, Object>();
end_comment

begin_comment
comment|//        if (model instanceof Map<?, ?>) {
end_comment

begin_comment
comment|//            vars.putAll((Map<String, Object>) model);
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            vars.put("it", model);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        // override custom variables if any
end_comment

begin_comment
comment|//        vars.putAll(getVariablesForTemplate(vars));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        final OutputStreamWriter writer = new OutputStreamWriter(out,"utf-8");
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            template.process(vars, writer);
end_comment

begin_comment
comment|//        } catch (Throwable t) {
end_comment

begin_comment
comment|//            onProcessException(t, template, vars, out);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//}
end_comment

end_unit

