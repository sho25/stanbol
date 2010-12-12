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
comment|/**  * a postal code  *  * @author marc@geonames  *  */
end_comment

begin_class
specifier|public
class|class
name|PostalCode
block|{
specifier|private
name|String
name|postalCode
decl_stmt|;
specifier|private
name|String
name|placeName
decl_stmt|;
specifier|private
name|String
name|countryCode
decl_stmt|;
specifier|private
name|double
name|latitude
decl_stmt|;
specifier|private
name|double
name|longitude
decl_stmt|;
specifier|private
name|String
name|adminName1
decl_stmt|;
specifier|private
name|String
name|adminCode1
decl_stmt|;
specifier|private
name|String
name|adminName2
decl_stmt|;
specifier|private
name|String
name|adminCode2
decl_stmt|;
specifier|private
name|double
name|distance
decl_stmt|;
comment|/**      * @return Returns the distance.      */
specifier|public
name|double
name|getDistance
parameter_list|()
block|{
return|return
name|distance
return|;
block|}
comment|/**      * @param distance      *            The distance to set.      */
specifier|public
name|void
name|setDistance
parameter_list|(
name|double
name|distance
parameter_list|)
block|{
name|this
operator|.
name|distance
operator|=
name|distance
expr_stmt|;
block|}
comment|/**      * @return Returns the adminCode1.      */
specifier|public
name|String
name|getAdminCode1
parameter_list|()
block|{
return|return
name|adminCode1
return|;
block|}
comment|/**      * @param adminCode1      *            The adminCode1 to set.      */
specifier|public
name|void
name|setAdminCode1
parameter_list|(
name|String
name|adminCode1
parameter_list|)
block|{
name|this
operator|.
name|adminCode1
operator|=
name|adminCode1
expr_stmt|;
block|}
comment|/**      * @return Returns the adminCode2.      */
specifier|public
name|String
name|getAdminCode2
parameter_list|()
block|{
return|return
name|adminCode2
return|;
block|}
comment|/**      * @param adminCode2      *            The adminCode2 to set.      */
specifier|public
name|void
name|setAdminCode2
parameter_list|(
name|String
name|adminCode2
parameter_list|)
block|{
name|this
operator|.
name|adminCode2
operator|=
name|adminCode2
expr_stmt|;
block|}
comment|/**      * @return Returns the adminName1.      */
specifier|public
name|String
name|getAdminName1
parameter_list|()
block|{
return|return
name|adminName1
return|;
block|}
comment|/**      * @param adminName1      *            The adminName1 to set.      */
specifier|public
name|void
name|setAdminName1
parameter_list|(
name|String
name|adminName1
parameter_list|)
block|{
name|this
operator|.
name|adminName1
operator|=
name|adminName1
expr_stmt|;
block|}
comment|/**      * @return Returns the adminName2.      */
specifier|public
name|String
name|getAdminName2
parameter_list|()
block|{
return|return
name|adminName2
return|;
block|}
comment|/**      * @param adminName2      *            The adminName2 to set.      */
specifier|public
name|void
name|setAdminName2
parameter_list|(
name|String
name|adminName2
parameter_list|)
block|{
name|this
operator|.
name|adminName2
operator|=
name|adminName2
expr_stmt|;
block|}
comment|/**      * @return Returns the ISO 3166-1-alpha-2 countryCode.      */
specifier|public
name|String
name|getCountryCode
parameter_list|()
block|{
return|return
name|countryCode
return|;
block|}
comment|/**      * @param countryCode      *            The ISO 3166-1-alpha-2 countryCode to set.      */
specifier|public
name|void
name|setCountryCode
parameter_list|(
name|String
name|countryCode
parameter_list|)
block|{
name|this
operator|.
name|countryCode
operator|=
name|countryCode
expr_stmt|;
block|}
comment|/**      * latitude in WGS84      *      * @return Returns the latitude.      */
specifier|public
name|double
name|getLatitude
parameter_list|()
block|{
return|return
name|latitude
return|;
block|}
comment|/**      * @param latitude      *            The latitude to set.      */
specifier|public
name|void
name|setLatitude
parameter_list|(
name|double
name|latitude
parameter_list|)
block|{
name|this
operator|.
name|latitude
operator|=
name|latitude
expr_stmt|;
block|}
comment|/**      * longitude in WGS84      *      * @return Returns the longitude.      */
specifier|public
name|double
name|getLongitude
parameter_list|()
block|{
return|return
name|longitude
return|;
block|}
comment|/**      * @param longitude      *            The longitude to set.      */
specifier|public
name|void
name|setLongitude
parameter_list|(
name|double
name|longitude
parameter_list|)
block|{
name|this
operator|.
name|longitude
operator|=
name|longitude
expr_stmt|;
block|}
comment|/**      * @return Returns the placeName.      */
specifier|public
name|String
name|getPlaceName
parameter_list|()
block|{
return|return
name|placeName
return|;
block|}
comment|/**      * @param placeName      *            The placeName to set.      */
specifier|public
name|void
name|setPlaceName
parameter_list|(
name|String
name|placeName
parameter_list|)
block|{
name|this
operator|.
name|placeName
operator|=
name|placeName
expr_stmt|;
block|}
comment|/**      * @return Returns the postalCode.      */
specifier|public
name|String
name|getPostalCode
parameter_list|()
block|{
return|return
name|postalCode
return|;
block|}
comment|/**      * @param postalCode      *            The postalCode to set.      */
specifier|public
name|void
name|setPostalCode
parameter_list|(
name|String
name|postalCode
parameter_list|)
block|{
name|this
operator|.
name|postalCode
operator|=
name|postalCode
expr_stmt|;
block|}
block|}
end_class

end_unit

