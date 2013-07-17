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
name|enhancer
operator|.
name|engines
operator|.
name|geonames
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONArray
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jettison
operator|.
name|json
operator|.
name|JSONObject
import|;
end_import

begin_class
specifier|public
class|class
name|Toponym
block|{
comment|/**      * JSON property names  keys used for Toponyms by geonames.org      * (may not be complete)      *      * @author Rupert Westenthaler      */
enum|enum
name|ToponymProperty
block|{
name|geonameId
block|,
name|alternateNames
block|,
name|adminCode1
block|,
name|adminCode2
block|,
name|adminCode3
block|,
name|adminCode4
block|,
name|adminName1
block|,
name|adminName2
block|,
name|adminName3
block|,
name|adminName4
block|,
name|timezone
block|,
comment|/**          * The name determined from the alternate names based on the parsed          * language in the request          */
name|name
block|,
comment|/**          * This is the official name (preferred label          */
name|toponymName
block|,
name|population
block|,
name|lat
block|,
name|lng
block|,
name|countryCode
block|,
name|countryName
block|,
name|score
block|,
name|fcode
block|,
name|fcodeNmae
block|,
name|fcl
block|,
name|fclName
block|,
name|elevation
block|}
specifier|private
name|JSONObject
name|data
decl_stmt|;
specifier|public
name|Toponym
parameter_list|(
name|JSONObject
name|jsonData
parameter_list|)
block|{
if|if
condition|(
name|jsonData
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"The parsed JSON object MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|data
operator|=
name|jsonData
expr_stmt|;
block|}
comment|/**      * @return the ISO 3166-1-alpha-2 countryCode.      */
specifier|public
name|String
name|getCountryCode
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|countryCode
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|countryCode
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the elevation in meter.      */
specifier|public
name|Integer
name|getElevation
parameter_list|()
block|{
try|try
block|{
if|if
condition|(
name|data
operator|.
name|has
argument_list|(
name|ToponymProperty
operator|.
name|elevation
operator|.
name|name
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|data
operator|.
name|getInt
argument_list|(
name|ToponymProperty
operator|.
name|elevation
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|elevation
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * the feature class {@link FeatureClass}      *      * @return the featureClass.      *      * @see<a href="http://www.geonames.org/export/codes.html">GeoNames Feature      *      Codes</a>      */
specifier|public
name|FeatureClass
name|getFeatureClass
parameter_list|()
block|{
try|try
block|{
name|String
name|fc
init|=
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|fcl
operator|.
name|name
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|fc
operator|==
literal|null
condition|?
literal|null
else|:
name|FeatureClass
operator|.
name|valueOf
argument_list|(
name|fc
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|fcl
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the featureCode.      *      * @see<a href="http://www.geonames.org/export/codes.html">GeoNames Feature      *      Codes</a>      */
specifier|public
name|String
name|getFeatureCode
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|fcode
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|fcode
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * latitude in decimal degrees (wgs84)      *      * @return the latitude.      */
specifier|public
name|double
name|getLatitude
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getDouble
argument_list|(
name|ToponymProperty
operator|.
name|lat
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|lat
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * longitude in decimal degrees (wgs84)      *      * @return the longitude.      */
specifier|public
name|double
name|getLongitude
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getDouble
argument_list|(
name|ToponymProperty
operator|.
name|lng
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|lng
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the name.      */
specifier|public
name|String
name|getName
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|name
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|name
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the population.      */
specifier|public
name|Long
name|getPopulation
parameter_list|()
block|{
try|try
block|{
if|if
condition|(
name|data
operator|.
name|has
argument_list|(
name|ToponymProperty
operator|.
name|population
operator|.
name|name
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|data
operator|.
name|getLong
argument_list|(
name|ToponymProperty
operator|.
name|population
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|population
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the geoNameId.      */
specifier|public
name|int
name|getGeoNameId
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getInt
argument_list|(
name|ToponymProperty
operator|.
name|geonameId
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|geonameId
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the featureClassName.      */
specifier|public
name|String
name|getFeatureClassName
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|fclName
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|fclName
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the featureCodeName.      */
specifier|public
name|String
name|getFeatureCodeName
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|fcodeNmae
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|fcodeNmae
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the countryName.      */
specifier|public
name|String
name|getCountryName
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|countryName
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|countryName
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * alternate names of this place as a list of string arrays with two entries      * the first entry is the label and the second represents the language      *      * @return the alternateNames as comma separated list      */
specifier|public
name|List
argument_list|<
name|String
index|[]
argument_list|>
name|getAlternateNames
parameter_list|()
block|{
try|try
block|{
if|if
condition|(
name|data
operator|.
name|has
argument_list|(
name|ToponymProperty
operator|.
name|alternateNames
operator|.
name|name
argument_list|()
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|String
index|[]
argument_list|>
name|parsedNames
init|=
operator|new
name|ArrayList
argument_list|<
name|String
index|[]
argument_list|>
argument_list|()
decl_stmt|;
name|JSONArray
name|altNames
init|=
name|data
operator|.
name|getJSONArray
argument_list|(
name|ToponymProperty
operator|.
name|alternateNames
operator|.
name|name
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|altNames
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JSONObject
name|altName
init|=
name|altNames
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|altName
operator|.
name|has
argument_list|(
literal|"name"
argument_list|)
condition|)
block|{
name|parsedNames
operator|.
name|add
argument_list|(
operator|new
name|String
index|[]
block|{
name|altName
operator|.
name|getString
argument_list|(
literal|"name"
argument_list|)
block|,
name|altName
operator|.
name|has
argument_list|(
literal|"lang"
argument_list|)
condition|?
name|altName
operator|.
name|getString
argument_list|(
literal|"lang"
argument_list|)
else|:
literal|null
block|}
argument_list|)
expr_stmt|;
block|}
comment|// else ignore alternate names without a name
block|}
return|return
name|parsedNames
return|;
block|}
else|else
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|alternateNames
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|data
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * @return the adminCode1      */
specifier|public
name|String
name|getAdminCode1
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|adminCode1
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|adminCode1
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the adminCode2      */
specifier|public
name|String
name|getAdminCode2
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|adminCode2
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|adminCode2
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the adminCode3      */
specifier|public
name|String
name|getAdminCode3
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|adminCode3
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|adminCode3
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the adminCode4      */
specifier|public
name|String
name|getAdminCode4
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|adminCode4
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|adminCode4
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * The time zone is a complex Object encoded like      *<code><pre>      * timezone: {      *     dstOffset: -5      *     gmtOffset: -6      *     timeZoneId: "America/Chicago"      * }      *</pre></code>      * This mehtod does not further parse this data.      *      * @return the {@link JSONObject} with the time zone information      */
specifier|public
name|JSONObject
name|getTimezone
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getJSONObject
argument_list|(
name|ToponymProperty
operator|.
name|timezone
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|timezone
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the adminName1      */
specifier|public
name|String
name|getAdminName1
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|adminName1
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|adminName1
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the adminName2      */
specifier|public
name|String
name|getAdminName2
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|adminName2
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|adminName2
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the adminName3      */
specifier|public
name|String
name|getAdminName3
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|adminName3
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|adminName3
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * @return the adminName4      */
specifier|public
name|String
name|getAdminName4
parameter_list|()
block|{
try|try
block|{
return|return
name|data
operator|.
name|getString
argument_list|(
name|ToponymProperty
operator|.
name|adminName4
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|adminName4
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
specifier|public
name|Double
name|getScore
parameter_list|()
block|{
try|try
block|{
if|if
condition|(
name|data
operator|.
name|has
argument_list|(
name|ToponymProperty
operator|.
name|score
operator|.
name|name
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|data
operator|.
name|getDouble
argument_list|(
name|ToponymProperty
operator|.
name|score
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse %s form %s"
argument_list|,
name|ToponymProperty
operator|.
name|score
argument_list|,
name|data
argument_list|)
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

