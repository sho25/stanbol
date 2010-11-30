begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|autotagging
operator|.
name|jena
package|;
end_package

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_class
specifier|public
class|class
name|ResourceInfo
block|{
specifier|public
specifier|final
name|Resource
name|resource
decl_stmt|;
specifier|public
specifier|final
name|Double
name|score
decl_stmt|;
specifier|public
name|ResourceInfo
parameter_list|(
name|Resource
name|resource
parameter_list|,
name|Double
name|score
parameter_list|)
block|{
name|this
operator|.
name|resource
operator|=
name|resource
expr_stmt|;
name|this
operator|.
name|score
operator|=
name|score
expr_stmt|;
block|}
block|}
end_class

end_unit

