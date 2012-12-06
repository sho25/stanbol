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
name|namespaceprefix
operator|.
name|provider
operator|.
name|prefixcc
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
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Executors
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ScheduledExecutorService
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ScheduledFuture
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|TimeUnit
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|TimeoutException
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
name|namespaceprefix
operator|.
name|NamespacePrefixProvider
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
name|namespaceprefix
operator|.
name|impl
operator|.
name|NamespacePrefixProviderImpl
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
name|PrefixccProvider
implements|implements
name|NamespacePrefixProvider
block|{
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
name|PrefixccProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|URL
name|GET_ALL
decl_stmt|;
static|static
block|{
try|try
block|{
name|GET_ALL
operator|=
operator|new
name|URL
argument_list|(
literal|"http://prefix.cc/popular/all.file.txt"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unable to create http://prefix.cc URL"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|private
specifier|final
name|ScheduledExecutorService
name|scheduler
init|=
name|Executors
operator|.
name|newScheduledThreadPool
argument_list|(
literal|1
argument_list|)
decl_stmt|;
specifier|private
name|NamespacePrefixProvider
name|cache
decl_stmt|;
specifier|private
name|long
name|cacheStamp
decl_stmt|;
comment|/**      * Intended to be used by the {@link ServiceLoader} utility.      * uses 1 hour delay and DOES a sync initial load of the mappings      * before returning.      */
specifier|public
name|PrefixccProvider
parameter_list|()
block|{
comment|//by default update once every hour
name|this
argument_list|(
literal|1
argument_list|,
name|TimeUnit
operator|.
name|HOURS
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a prefix.cc provider with the specified delay. The initial      * load of the mappings is done immediately but asynchronously. That means      * that the mappings will not be available when the constructor returns.<p>      * While this implementation does not restrict configured delays expected       * values are in the era of hours.      * @param delay the delay      * @param unit the unit of the delay.      */
specifier|public
name|PrefixccProvider
parameter_list|(
name|int
name|delay
parameter_list|,
name|TimeUnit
name|unit
parameter_list|)
block|{
name|this
argument_list|(
name|delay
argument_list|,
name|unit
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a prefix.cc provider. If syncInitialLoad is enabled the initial      * load of the data is done before the constructor returns. Otherwise      * mappings are loaded asynchronously as specified by the parsed delay.<p>      * While this implementation does not restrict configured delays expected       * values are in the era of hours.      * @param delay the delay      * @param unit the time unit of the delay      * @param syncInitialLoad if<code>true</code> mappings are loaded before      * the constructor returns. Otherwise mappings are loaded asynchronously      */
specifier|public
name|PrefixccProvider
parameter_list|(
name|int
name|delay
parameter_list|,
name|TimeUnit
name|unit
parameter_list|,
name|boolean
name|syncInitialLoad
parameter_list|)
block|{
if|if
condition|(
name|delay
operator|<=
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed delay '"
operator|+
name|delay
operator|+
literal|"' MUST NOT be<= 0"
argument_list|)
throw|;
block|}
if|if
condition|(
name|unit
operator|==
literal|null
condition|)
block|{
name|unit
operator|=
name|TimeUnit
operator|.
name|SECONDS
expr_stmt|;
block|}
name|int
name|initialDelay
decl_stmt|;
if|if
condition|(
name|syncInitialLoad
condition|)
block|{
name|loadMappings
argument_list|()
expr_stmt|;
name|initialDelay
operator|=
name|delay
expr_stmt|;
block|}
else|else
block|{
name|initialDelay
operator|=
literal|0
expr_stmt|;
block|}
name|scheduler
operator|.
name|scheduleWithFixedDelay
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|loadMappings
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|,
name|initialDelay
argument_list|,
name|delay
argument_list|,
name|unit
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|final
name|void
name|loadMappings
parameter_list|()
block|{
try|try
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Load Namespace Prefix Mappings form {}"
argument_list|,
name|GET_ALL
argument_list|)
expr_stmt|;
name|cache
operator|=
operator|new
name|NamespacePrefixProviderImpl
argument_list|(
name|GET_ALL
operator|.
name|openStream
argument_list|()
argument_list|)
expr_stmt|;
name|cacheStamp
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"  ... completed"
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
name|warn
argument_list|(
literal|"Unable to load prefix.cc NamespaceMappings (Message: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|,
name|e
argument_list|)
expr_stmt|;
empty_stmt|;
block|}
block|}
comment|/**      * deletes the local cahe and stops the periodical updates of the cache      */
specifier|public
name|void
name|close
parameter_list|()
block|{
name|scheduler
operator|.
name|shutdown
argument_list|()
expr_stmt|;
name|cache
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * If prefix.cc data are available      * @return      */
specifier|public
name|boolean
name|isAvailable
parameter_list|()
block|{
return|return
name|cache
operator|!=
literal|null
return|;
block|}
comment|/**      * The Date where the locally cached data where synced the last time with      * prefix.cc. Will return<code>null</code> if no data where received from      * prefix.cc (<code>{@link #isAvailable()} == false</code>)      * @return the date where the local cache was received from prefix.cc      */
specifier|public
name|Date
name|getCacheTimeStamp
parameter_list|()
block|{
if|if
condition|(
name|cache
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|Date
argument_list|(
name|cacheStamp
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|void
name|finalize
parameter_list|()
throws|throws
name|Throwable
block|{
name|close
argument_list|()
expr_stmt|;
name|super
operator|.
name|finalize
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getNamespace
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
name|NamespacePrefixProvider
name|npp
init|=
name|cache
decl_stmt|;
return|return
name|npp
operator|==
literal|null
condition|?
literal|null
else|:
name|npp
operator|.
name|getNamespace
argument_list|(
name|prefix
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPrefix
parameter_list|(
name|String
name|namespace
parameter_list|)
block|{
name|NamespacePrefixProvider
name|npp
init|=
name|cache
decl_stmt|;
return|return
name|npp
operator|==
literal|null
condition|?
literal|null
else|:
name|npp
operator|.
name|getPrefix
argument_list|(
name|namespace
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getPrefixes
parameter_list|(
name|String
name|namespace
parameter_list|)
block|{
name|NamespacePrefixProvider
name|npp
init|=
name|cache
decl_stmt|;
return|return
name|npp
operator|==
literal|null
condition|?
literal|null
else|:
name|npp
operator|.
name|getPrefixes
argument_list|(
name|namespace
argument_list|)
return|;
block|}
block|}
end_class

end_unit

