begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|internal
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|SubsumptionBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|web
operator|.
name|ConnectionInfo
import|;
end_import

begin_interface
specifier|public
interface|interface
name|SubsumptionBridgeProcessor
block|{
name|void
name|executePropertyBridges
parameter_list|(
name|List
argument_list|<
name|SubsumptionBridge
argument_list|>
name|bridges
parameter_list|,
name|ConnectionInfo
name|connectionInfo
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

