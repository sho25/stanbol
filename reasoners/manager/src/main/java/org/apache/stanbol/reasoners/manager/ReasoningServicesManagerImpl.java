begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|manager
package|;
end_package

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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningService
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|ReasoningServicesManager
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
name|reasoners
operator|.
name|servicesapi
operator|.
name|UnboundReasoningServiceException
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
comment|/**  * @scr.component immediate="true"  * @scr.service  * @scr.reference name="ReasoningService"  *                interface="org.apache.stanbol.reasoners.servicesapi.ReasoningService" cardinality="0..n"  *                policy="dynamic")  */
end_comment

begin_class
specifier|public
class|class
name|ReasoningServicesManagerImpl
implements|implements
name|ReasoningServicesManager
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
name|ReasoningServicesManagerImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
argument_list|>
name|services
init|=
operator|new
name|HashSet
argument_list|<
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|void
name|bindReasoningService
parameter_list|(
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|service
parameter_list|)
block|{
name|services
operator|.
name|add
argument_list|(
name|service
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Reasoning service {} added to path {}"
argument_list|,
name|service
argument_list|,
name|service
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"{} services bound."
argument_list|,
name|services
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|unbindReasoningService
parameter_list|(
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|service
parameter_list|)
block|{
name|services
operator|.
name|remove
argument_list|(
name|service
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Reasoning service {} removed from path {}"
argument_list|,
name|service
argument_list|,
name|service
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"{} services bound."
argument_list|,
name|services
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @see org.apache.stanbol.reasoners.web.resources.ReasoningServices#size()      */
annotation|@
name|Override
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|services
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|get
parameter_list|(
name|String
name|path
parameter_list|)
throws|throws
name|UnboundReasoningServiceException
block|{
for|for
control|(
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|service
range|:
name|services
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Does service {} match path {}?"
argument_list|,
name|service
argument_list|,
name|path
argument_list|)
expr_stmt|;
if|if
condition|(
name|service
operator|.
name|getPath
argument_list|()
operator|.
name|equals
argument_list|(
name|path
argument_list|)
condition|)
block|{
return|return
name|service
return|;
block|}
block|}
throw|throw
operator|new
name|UnboundReasoningServiceException
argument_list|()
throw|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|ReasoningService
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
argument_list|>
name|asUnmodifiableSet
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|services
argument_list|)
return|;
block|}
block|}
end_class

end_unit

