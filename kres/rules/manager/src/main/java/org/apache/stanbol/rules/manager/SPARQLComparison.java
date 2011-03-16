begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|manager
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
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|SPARQLObject
import|;
end_import

begin_class
specifier|public
class|class
name|SPARQLComparison
implements|implements
name|SPARQLObject
block|{
specifier|private
name|String
name|filter
decl_stmt|;
specifier|public
name|SPARQLComparison
parameter_list|(
name|String
name|filter
parameter_list|)
block|{
name|this
operator|.
name|filter
operator|=
name|filter
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getObject
parameter_list|()
block|{
return|return
name|filter
return|;
block|}
block|}
end_class

end_unit

