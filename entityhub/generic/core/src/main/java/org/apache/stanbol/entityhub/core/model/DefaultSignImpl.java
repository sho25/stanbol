begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
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
name|Sign
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
comment|//    private final String TYPE = RdfResourceEnum.signType.getUri();
specifier|private
specifier|final
name|String
name|signSite
decl_stmt|;
comment|//    public DefaultSignImpl(Representation representation) {
comment|//        if(representation == null){
comment|//            throw new IllegalArgumentException("NULL value ist not allowed for the Representation");
comment|//        }
comment|//        if(representation.getFirstReference(SIGN_SITE) == null){
comment|//            throw new IllegalStateException("Parsed Representation does not define the required field"+SIGN_SITE+"!");
comment|//        }
comment|//        this.representation = representation;
comment|//    }
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
comment|//    @Override
comment|//    public SignTypeEnum getType() {
comment|//        Reference ref = representation.getFirstReference(TYPE);
comment|//        if(ref == null){
comment|//            return DEFAULT_SIGN_TYPE;
comment|//        } else {
comment|//            SignTypeEnum type = ModelUtils.getSignType(ref.getReference());
comment|//            if(type == null){
comment|//                log.warn("Sign "+getId()+" is set to an unknown SignType "+ref.getReference()+"! -> return default type (value is not reseted)");
comment|//                return DEFAULT_SIGN_TYPE;
comment|//            } else {
comment|//                return type;
comment|//            }
comment|//        }
comment|//    }
block|}
end_class

end_unit

