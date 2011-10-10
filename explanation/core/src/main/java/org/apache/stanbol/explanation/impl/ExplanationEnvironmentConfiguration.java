begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Dictionary
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
name|Activate
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
name|Deactivate
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
name|Property
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
name|ReferencePolicy
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
name|explanation
operator|.
name|api
operator|.
name|Configuration
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ONManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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
comment|/**  * Default implementation of the explanation environment configuration.  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|Configuration
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ExplanationEnvironmentConfiguration
implements|implements
name|Configuration
block|{
specifier|public
specifier|static
specifier|final
name|String
name|_SCOPE_SHORT_ID_DEFAULT
init|=
literal|"Explanation"
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
annotation|@
name|Reference
argument_list|(
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|)
specifier|private
name|ONManager
name|onm
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Configuration
operator|.
name|SCOPE_SHORT_ID
argument_list|,
name|value
operator|=
name|_SCOPE_SHORT_ID_DEFAULT
argument_list|)
specifier|private
name|String
name|scopeShortID
decl_stmt|;
comment|/**      * This default constructor is<b>only</b> intended to be used by the OSGI environment with Service      * Component Runtime support.      *<p>      * DO NOT USE to manually create instances - the ExplanationGeneratorImpl instances do need to be      * configured! YOU NEED TO USE {@link #ExplanationEnvironmentConfiguration(ONManager, Dictionary)} or its      * overloads, to parse the configuration and then initialise the rule store if running outside an OSGI      * environment.      */
specifier|public
name|ExplanationEnvironmentConfiguration
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * To be invoked by non-OSGi environments.      *       * @param onm      * @param configuration      */
specifier|public
name|ExplanationEnvironmentConfiguration
parameter_list|(
name|ONManager
name|onm
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|onm
operator|=
name|onm
expr_stmt|;
try|try
block|{
name|activate
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
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
literal|"Unable to access servlet context."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Used to configure an instance within an OSGi container.      *       * @throws IOException      *             if there is no valid component context.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|Configuration
operator|.
name|class
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No valid"
operator|+
name|ComponentContext
operator|.
name|class
operator|+
literal|" parsed in activate!"
argument_list|)
throw|;
block|}
name|activate
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called within both OSGi and non-OSGi environments.      *       * @param configuration      * @throws IOException      */
specifier|protected
name|void
name|activate
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|scopeShortID
operator|=
operator|(
name|String
operator|)
name|configuration
operator|.
name|get
argument_list|(
name|Configuration
operator|.
name|SCOPE_SHORT_ID
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|scopeShortID
operator|==
literal|null
condition|)
name|this
operator|.
name|scopeShortID
operator|=
name|_SCOPE_SHORT_ID_DEFAULT
expr_stmt|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|Configuration
operator|.
name|class
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|ONManager
name|getOntologyNetworkManager
parameter_list|()
block|{
return|return
name|onm
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getScopeID
parameter_list|()
block|{
return|return
name|scopeShortID
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getScopeShortId
parameter_list|()
block|{
return|return
name|scopeShortID
return|;
block|}
block|}
end_class

end_unit

