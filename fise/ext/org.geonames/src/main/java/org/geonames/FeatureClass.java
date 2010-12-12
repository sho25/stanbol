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
comment|/**  * Enumeration for the GeoNames feature classes A,H,L,P,R,S,T,U,V  *  * @author marc  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|FeatureClass
block|{
comment|/**      * Administrative Boundary Features      */
name|A
block|,
comment|/**      * Hydrographic Features      */
name|H
block|,
comment|/**      * Area Features      */
name|L
block|,
comment|/**      * Populated Place Features      */
name|P
block|,
comment|/**      * Road / Railroad Features      */
name|R
block|,
comment|/**      * Spot Features      */
name|S
block|,
comment|/**      * Hypsographic Features      */
name|T
block|,
comment|/**      * Undersea Features      */
name|U
block|,
comment|/**      * Vegetation Features      */
name|V
block|;
specifier|public
specifier|static
name|FeatureClass
name|fromValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|valueOf
argument_list|(
name|value
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

