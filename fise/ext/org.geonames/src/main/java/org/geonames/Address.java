begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright 2008 Marc Wick, geonames.org  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|geonames
package|;
end_package

begin_comment
comment|/**  * a street address  *  * @author Mark Thomas  *  */
end_comment

begin_class
specifier|public
class|class
name|Address
extends|extends
name|PostalCode
block|{
specifier|private
name|String
name|street
decl_stmt|;
specifier|private
name|String
name|streetNumber
decl_stmt|;
comment|/**      * @return the street      */
specifier|public
name|String
name|getStreet
parameter_list|()
block|{
return|return
name|street
return|;
block|}
comment|/**      * @param street      *            the street to set      */
specifier|public
name|void
name|setStreet
parameter_list|(
name|String
name|street
parameter_list|)
block|{
name|this
operator|.
name|street
operator|=
name|street
expr_stmt|;
block|}
comment|/**      * @return the streetNumber      */
specifier|public
name|String
name|getStreetNumber
parameter_list|()
block|{
return|return
name|streetNumber
return|;
block|}
comment|/**      * @param streetNumber      *            the streetNumber to set      */
specifier|public
name|void
name|setStreetNumber
parameter_list|(
name|String
name|streetNumber
parameter_list|)
block|{
name|this
operator|.
name|streetNumber
operator|=
name|streetNumber
expr_stmt|;
block|}
block|}
end_class

end_unit

