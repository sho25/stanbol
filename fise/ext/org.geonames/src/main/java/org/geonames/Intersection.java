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
comment|/**  * an intersection between two streets  *  * @author Mark Thomas  *  */
end_comment

begin_class
specifier|public
class|class
name|Intersection
block|{
specifier|private
name|String
name|street2
decl_stmt|;
specifier|private
name|Address
name|address
init|=
operator|new
name|Address
argument_list|()
decl_stmt|;
specifier|public
name|double
name|getDistance
parameter_list|()
block|{
return|return
name|address
operator|.
name|getDistance
argument_list|()
return|;
block|}
specifier|public
name|void
name|setDistance
parameter_list|(
name|double
name|d
parameter_list|)
block|{
name|address
operator|.
name|setDistance
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getAdminCode1
parameter_list|()
block|{
return|return
name|address
operator|.
name|getAdminCode1
argument_list|()
return|;
block|}
specifier|public
name|void
name|setAdminCode1
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|address
operator|.
name|setAdminCode1
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getAdminName1
parameter_list|()
block|{
return|return
name|address
operator|.
name|getAdminName1
argument_list|()
return|;
block|}
specifier|public
name|void
name|setAdminName1
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|address
operator|.
name|setAdminName1
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getAdminName2
parameter_list|()
block|{
return|return
name|address
operator|.
name|getAdminName2
argument_list|()
return|;
block|}
specifier|public
name|void
name|setAdminName2
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|address
operator|.
name|setAdminName2
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getCountryCode
parameter_list|()
block|{
return|return
name|address
operator|.
name|getCountryCode
argument_list|()
return|;
block|}
specifier|public
name|void
name|setCountryCode
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|address
operator|.
name|setCountryCode
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
specifier|public
name|double
name|getLatitude
parameter_list|()
block|{
return|return
name|address
operator|.
name|getLatitude
argument_list|()
return|;
block|}
specifier|public
name|void
name|setLatitude
parameter_list|(
name|double
name|d
parameter_list|)
block|{
name|address
operator|.
name|setLatitude
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
specifier|public
name|double
name|getLongitude
parameter_list|()
block|{
return|return
name|address
operator|.
name|getLongitude
argument_list|()
return|;
block|}
specifier|public
name|void
name|setLongitude
parameter_list|(
name|double
name|d
parameter_list|)
block|{
name|address
operator|.
name|setLongitude
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getPlaceName
parameter_list|()
block|{
return|return
name|address
operator|.
name|getPlaceName
argument_list|()
return|;
block|}
specifier|public
name|void
name|setPlaceName
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|address
operator|.
name|setPlaceName
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getPostalCode
parameter_list|()
block|{
return|return
name|address
operator|.
name|getPostalCode
argument_list|()
return|;
block|}
specifier|public
name|void
name|setPostalCode
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|address
operator|.
name|setPostalCode
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getStreet1
parameter_list|()
block|{
return|return
name|address
operator|.
name|getStreet
argument_list|()
return|;
block|}
specifier|public
name|void
name|setStreet1
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|address
operator|.
name|setStreet
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getStreet2
parameter_list|()
block|{
return|return
name|street2
return|;
block|}
specifier|public
name|void
name|setStreet2
parameter_list|(
name|String
name|street2
parameter_list|)
block|{
name|this
operator|.
name|street2
operator|=
name|street2
expr_stmt|;
block|}
block|}
end_class

end_unit

