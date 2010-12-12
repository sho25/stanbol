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
comment|/**  * a street line segment. Includes house number information for the beginning  * and end of the line as well as right and left hand side of the line.  *  * @author marc@geonames  *  */
end_comment

begin_class
specifier|public
class|class
name|StreetSegment
extends|extends
name|PostalCode
block|{
specifier|private
name|double
index|[]
name|latArray
decl_stmt|;
specifier|private
name|double
index|[]
name|lngArray
decl_stmt|;
comment|/**      * census feature class codes see      * http://www.geonames.org/maps/Census-Feature-Class-Codes.txt      */
specifier|private
name|String
name|cfcc
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
comment|/**      * from address left      */
specifier|private
name|String
name|fraddl
decl_stmt|;
comment|/**      * from address right      */
specifier|private
name|String
name|fraddr
decl_stmt|;
comment|/**      * to address left      */
specifier|private
name|String
name|toaddl
decl_stmt|;
comment|/**      * to address right      */
specifier|private
name|String
name|toaddr
decl_stmt|;
comment|/**      * @return the latArray      */
specifier|public
name|double
index|[]
name|getLatArray
parameter_list|()
block|{
return|return
name|latArray
return|;
block|}
comment|/**      * @param latArray      *            the latArray to set      */
specifier|public
name|void
name|setLatArray
parameter_list|(
name|double
index|[]
name|latArray
parameter_list|)
block|{
name|this
operator|.
name|latArray
operator|=
name|latArray
expr_stmt|;
block|}
comment|/**      * @return the lngArray      */
specifier|public
name|double
index|[]
name|getLngArray
parameter_list|()
block|{
return|return
name|lngArray
return|;
block|}
comment|/**      * @param lngArray      *            the lngArray to set      */
specifier|public
name|void
name|setLngArray
parameter_list|(
name|double
index|[]
name|lngArray
parameter_list|)
block|{
name|this
operator|.
name|lngArray
operator|=
name|lngArray
expr_stmt|;
block|}
comment|/**      * @return the cfcc      */
specifier|public
name|String
name|getCfcc
parameter_list|()
block|{
return|return
name|cfcc
return|;
block|}
comment|/**      * @param cfcc      *            the cfcc to set      */
specifier|public
name|void
name|setCfcc
parameter_list|(
name|String
name|cfcc
parameter_list|)
block|{
name|this
operator|.
name|cfcc
operator|=
name|cfcc
expr_stmt|;
block|}
comment|/**      * @return the name      */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**      * @param name      *            the name to set      */
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
comment|/**      * @return the fraddl      */
specifier|public
name|String
name|getFraddl
parameter_list|()
block|{
return|return
name|fraddl
return|;
block|}
comment|/**      * @param fraddl      *            the fraddl to set      */
specifier|public
name|void
name|setFraddl
parameter_list|(
name|String
name|fraddl
parameter_list|)
block|{
name|this
operator|.
name|fraddl
operator|=
name|fraddl
expr_stmt|;
block|}
comment|/**      * @return the fraddr      */
specifier|public
name|String
name|getFraddr
parameter_list|()
block|{
return|return
name|fraddr
return|;
block|}
comment|/**      * @param fraddr      *            the fraddr to set      */
specifier|public
name|void
name|setFraddr
parameter_list|(
name|String
name|fraddr
parameter_list|)
block|{
name|this
operator|.
name|fraddr
operator|=
name|fraddr
expr_stmt|;
block|}
comment|/**      * @return the toaddl      */
specifier|public
name|String
name|getToaddl
parameter_list|()
block|{
return|return
name|toaddl
return|;
block|}
comment|/**      * @param toaddl      *            the toaddl to set      */
specifier|public
name|void
name|setToaddl
parameter_list|(
name|String
name|toaddl
parameter_list|)
block|{
name|this
operator|.
name|toaddl
operator|=
name|toaddl
expr_stmt|;
block|}
comment|/**      * @return the toaddr      */
specifier|public
name|String
name|getToaddr
parameter_list|()
block|{
return|return
name|toaddr
return|;
block|}
comment|/**      * @param toaddr      *            the toaddr to set      */
specifier|public
name|void
name|setToaddr
parameter_list|(
name|String
name|toaddr
parameter_list|)
block|{
name|this
operator|.
name|toaddr
operator|=
name|toaddr
expr_stmt|;
block|}
block|}
end_class

end_unit

