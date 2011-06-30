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
name|processor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * Implementors of this Interface can override their default processing order.  * @author cihan  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|ProcessorProperties
block|{
name|String
name|PROCESSING_ORDER
init|=
literal|"org.apache.stanbol.cmsadapter.servicesapi.processor.processing_order"
decl_stmt|;
name|Integer
name|OBJECT_TYPE
init|=
literal|0
decl_stmt|;
name|Integer
name|CMSOBJECT_POST
init|=
literal|30
decl_stmt|;
name|Integer
name|CMSOBJECT_DEFAULT
init|=
literal|20
decl_stmt|;
name|Integer
name|CMSOBJECT_PRE
init|=
literal|10
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|getProcessorProperties
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

