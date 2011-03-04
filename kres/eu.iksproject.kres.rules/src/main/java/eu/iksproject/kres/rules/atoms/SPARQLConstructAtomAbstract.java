begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|rules
operator|.
name|atoms
package|;
end_package

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|rules
operator|.
name|KReSRuleAtom
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|SPARQLConstructAtomAbstract
implements|implements
name|KReSRuleAtom
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLConstruct
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDelete
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isSPARQLDeleteData
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

