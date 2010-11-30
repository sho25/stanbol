begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|core
operator|.
name|model
package|;
end_package

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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
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
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Sign
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_class
specifier|public
class|class
name|DefaultSignImpl
implements|implements
name|Sign
block|{
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DefaultSignImpl
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|final
name|Representation
name|representation
decl_stmt|;
specifier|protected
specifier|final
name|String
name|TYPE
init|=
name|RdfResourceEnum
operator|.
name|signType
operator|.
name|getUri
argument_list|()
decl_stmt|;
specifier|protected
specifier|final
name|String
name|signSite
decl_stmt|;
comment|//	public DefaultSignImpl(Representation representation) {
comment|//		if(representation == null){
comment|//			throw new IllegalArgumentException("NULL value ist not allowed for the Representation");
comment|//		}
comment|//		if(representation.getFirstReference(SIGN_SITE) == null){
comment|//			throw new IllegalStateException("Parsed Representation does not define the required field"+SIGN_SITE+"!");
comment|//		}
comment|//		this.representation = representation;
comment|//	}
specifier|public
name|DefaultSignImpl
parameter_list|(
name|String
name|siteId
parameter_list|,
name|Representation
name|representation
parameter_list|)
block|{
if|if
condition|(
name|representation
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"NULL value ist not allowed for the Representation"
argument_list|)
throw|;
block|}
if|if
condition|(
name|siteId
operator|==
literal|null
operator|||
name|siteId
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Parsed SiteId MUST NOT be NULL nor empty!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|signSite
operator|=
name|siteId
expr_stmt|;
name|this
operator|.
name|representation
operator|=
name|representation
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getSignSite
parameter_list|()
block|{
return|return
name|signSite
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|representation
operator|.
name|getId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Representation
name|getRepresentation
parameter_list|()
block|{
return|return
name|representation
return|;
block|}
comment|//	@Override
comment|//	public SignTypeEnum getType() {
comment|//		Reference ref = representation.getFirstReference(TYPE);
comment|//		if(ref == null){
comment|//			return DEFAULT_SIGN_TYPE;
comment|//		} else {
comment|//			SignTypeEnum type = ModelUtils.getSignType(ref.getReference());
comment|//			if(type == null){
comment|//				log.warn("Sign "+getId()+" is set to an unknown SignType "+ref.getReference()+"! -> return default type (value is not reseted)");
comment|//				return DEFAULT_SIGN_TYPE;
comment|//			} else {
comment|//				return type;
comment|//			}
comment|//		}
comment|//	}
block|}
end_class

end_unit

