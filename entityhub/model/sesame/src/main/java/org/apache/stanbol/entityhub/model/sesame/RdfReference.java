begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|model
operator|.
name|sesame
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
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|model
operator|.
name|Value
import|;
end_import

begin_comment
comment|/**  * A {@link Reference} implementation backed by a Sesame {@link URI}  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|RdfReference
implements|implements
name|Reference
implements|,
name|RdfWrapper
block|{
specifier|private
specifier|final
name|URI
name|uri
decl_stmt|;
specifier|protected
name|RdfReference
parameter_list|(
name|URI
name|uri
parameter_list|)
block|{
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|uri
operator|.
name|stringValue
argument_list|()
return|;
block|}
comment|/**      * The wrapped Sesame {@link URI}      * @return the URI      */
specifier|public
name|URI
name|getURI
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
annotation|@
name|Override
specifier|public
name|Value
name|getValue
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|uri
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|obj
operator|instanceof
name|Reference
operator|&&
name|getReference
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Reference
operator|)
name|obj
operator|)
operator|.
name|getReference
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|uri
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

