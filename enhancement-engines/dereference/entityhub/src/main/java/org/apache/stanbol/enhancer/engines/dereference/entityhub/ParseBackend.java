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
name|engines
operator|.
name|dereference
operator|.
name|entityhub
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|ldpath
operator|.
name|backend
operator|.
name|AbstractBackend
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|EntityhubException
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|ValueFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQuery
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|QueryResultList
import|;
end_import

begin_class
specifier|final
class|class
name|ParseBackend
parameter_list|<
name|T
parameter_list|>
extends|extends
name|AbstractBackend
block|{
comment|/**      *       */
specifier|private
specifier|final
name|ValueFactory
name|valueFactory
decl_stmt|;
comment|/**      * @param trackingDereferencerBase      */
specifier|public
name|ParseBackend
parameter_list|(
name|ValueFactory
name|vf
parameter_list|)
block|{
name|this
operator|.
name|valueFactory
operator|=
name|vf
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|query
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|EntityhubException
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Not expected to be called"
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|protected
name|ValueFactory
name|getValueFactory
parameter_list|()
block|{
return|return
name|valueFactory
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Representation
name|getRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|EntityhubException
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Not expected to be called"
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|protected
name|FieldQuery
name|createQuery
parameter_list|()
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Not expected to be called"
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

