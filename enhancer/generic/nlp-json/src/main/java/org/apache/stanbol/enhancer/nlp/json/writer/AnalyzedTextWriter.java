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
name|nlp
operator|.
name|json
operator|.
name|writer
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
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|IllegalCharsetNameException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|UnsupportedCharsetException
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
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|json
operator|.
name|AnalyzedTextSerializer
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
name|enhancer
operator|.
name|nlp
operator|.
name|json
operator|.
name|valuetype
operator|.
name|ValueTypeSerializer
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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

begin_comment
comment|/**  * JAX-RS {@link MessageBodyWriter} for {@link AnalysedText} that works  * within Apache Stanbol<code>commons.web.base</code> as well as outside  * of an OSGI environment.<p>  * This implementation depends on the {@link AnalyzedTextSerializer} service.  * This dependency is initialised as follows:<ul>  *<li> via a {@link Reference} annotation on the member  *<li> via the {@link ServletContext} by using the {@link AnalyzedTextSerializer}  * class name as attribute name  *<li> via a OSGI {@link BundleContext} by obtaining the {@link BundleContext}  * from the {@link ServletContext} by using the {@link BundleContext} class name  * as attribute name. This is the way Stanbol currently uses)  *<li> via the {@link AnalyzedTextSerializer#getDefaultInstance()}. This is  * the expected way to initialize outside an OSGI environment.  *</ul>  * Users can also directly set the {@link #serializer} instance in sub-classes.  * To access the {@link #serializer} the {@link #getSerializer()} method should   * be used.  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Provider
annotation|@
name|Produces
argument_list|(
name|value
operator|=
name|MediaType
operator|.
name|APPLICATION_JSON
argument_list|)
specifier|public
class|class
name|AnalyzedTextWriter
implements|implements
name|MessageBodyWriter
argument_list|<
name|AnalysedText
argument_list|>
block|{
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|AnalyzedTextWriter
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Context
specifier|protected
name|ServletContext
name|servletContext
decl_stmt|;
comment|/**      * The serializer (might be lazy initialised in case injection via      * {@link Reference} does not work      */
annotation|@
name|Reference
specifier|protected
name|AnalyzedTextSerializer
name|serializer
decl_stmt|;
comment|/**      * Getter for the {@link AnalyzedTextSerializer}. If {@link #serializer} is       * not yet initialised (meaning that the {@link Reference} annotation has      * no effect) this tries to (1) get the service via the {@link #servletContext}       * (2) get a {@link BundleContext} via the {@link #servletContext} and than the      * service from the {@link BundleContext} and (3) obtain the default instance      * using {@link AnalyzedTextSerializer#getDefaultInstance()}.<p>      * When running within OSGI (3) could be problematic as some       * {@link ValueTypeSerializer} might not get registered through to       * classpath issues.      * @return the {@link AnalyzedTextSerializer} instance      */
specifier|protected
specifier|final
name|AnalyzedTextSerializer
name|getSerializer
parameter_list|()
block|{
if|if
condition|(
name|serializer
operator|==
literal|null
condition|)
block|{
synchronized|synchronized
init|(
name|this
init|)
block|{
if|if
condition|(
name|serializer
operator|!=
literal|null
condition|)
block|{
comment|//check again because of concurrency
return|return
name|serializer
return|;
block|}
comment|//(1) try to init directly get the service via the servlet context
name|Object
name|s
init|=
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|AnalyzedTextSerializer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
operator|&&
name|s
operator|instanceof
name|AnalyzedTextSerializer
condition|)
block|{
name|serializer
operator|=
operator|(
name|AnalyzedTextSerializer
operator|)
name|s
expr_stmt|;
return|return
name|serializer
return|;
block|}
comment|//(2) try to init via BundleContext available in the servlet context
name|Object
name|bc
init|=
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|BundleContext
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|bc
operator|!=
literal|null
operator|&&
name|bc
operator|instanceof
name|BundleContext
condition|)
block|{
name|ServiceReference
name|reference
init|=
operator|(
operator|(
name|BundleContext
operator|)
name|bc
operator|)
operator|.
name|getServiceReference
argument_list|(
name|AnalyzedTextSerializer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|reference
operator|!=
literal|null
condition|)
block|{
name|serializer
operator|=
call|(
name|AnalyzedTextSerializer
call|)
argument_list|(
operator|(
name|BundleContext
operator|)
name|bc
argument_list|)
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
expr_stmt|;
return|return
name|serializer
return|;
block|}
block|}
comment|//(3) get the default instance
name|serializer
operator|=
name|AnalyzedTextSerializer
operator|.
name|getDefaultInstance
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|serializer
return|;
block|}
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
name|AnalysedText
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
name|AnalysedText
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
name|AnalysedText
name|at
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
name|String
name|charsetName
init|=
name|mediaType
operator|.
name|getParameters
argument_list|()
operator|.
name|get
argument_list|(
literal|"charset"
argument_list|)
decl_stmt|;
name|Charset
name|charset
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|charsetName
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|charset
operator|=
name|Charset
operator|.
name|forName
argument_list|(
name|charsetName
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalCharsetNameException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to use charset defined by the parsed MediaType '"
operator|+
name|mediaType
operator|+
literal|"! Fallback to default (UTF-8)."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedCharsetException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Charset defined by the parsed MediaType '"
operator|+
name|mediaType
operator|+
literal|" is not supported! Fallback to default (UTF-8)."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|getSerializer
argument_list|()
operator|.
name|serialize
argument_list|(
name|at
argument_list|,
name|entityStream
argument_list|,
name|charset
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

