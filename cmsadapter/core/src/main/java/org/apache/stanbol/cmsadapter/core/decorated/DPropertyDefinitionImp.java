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
name|core
operator|.
name|decorated
package|;
end_package

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigInteger
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
name|AnnotationType
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
name|PropType
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
name|PropertyDefinition
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
name|decorated
operator|.
name|DPropertyDefinition
import|;
end_import

begin_class
specifier|public
class|class
name|DPropertyDefinitionImp
implements|implements
name|DPropertyDefinition
block|{
specifier|private
name|PropertyDefinition
name|instance
decl_stmt|;
specifier|public
name|DPropertyDefinitionImp
parameter_list|(
name|PropertyDefinition
name|instance
parameter_list|)
block|{
name|this
operator|.
name|instance
operator|=
name|instance
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getUniqueRef
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getUniqueRef
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getLocalname
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getNamespace
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getNamespace
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|AnnotationType
name|getAnnotations
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getAnnotation
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|BigInteger
name|getCardinality
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getCardinality
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|PropType
name|getPropertyType
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getPropertyType
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getValueConstraints
parameter_list|()
block|{
return|return
name|instance
operator|.
name|getValueConstraint
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|PropertyDefinition
name|getInstance
parameter_list|()
block|{
return|return
name|instance
return|;
block|}
block|}
end_class

end_unit

