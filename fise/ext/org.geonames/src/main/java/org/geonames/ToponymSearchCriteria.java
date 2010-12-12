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
comment|/**  * search criteria for web services returning toponyms.  *  * The string parameters do not have to be utf8 encoded. The encoding is done  * transparently in the call to the web service.  *  * The main parameter for the search over all fields is the 'q' parameter.  *  * @see WebService#search  *  * @see<a href="http://www.geonames.org/export/geonames-search.html">search webservice documentation< /a>  *  * @author marc@geonames  *  */
end_comment

begin_class
specifier|public
class|class
name|ToponymSearchCriteria
block|{
specifier|private
name|String
name|q
decl_stmt|;
specifier|private
name|String
name|countryCode
decl_stmt|;
specifier|private
name|String
name|continentCode
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|nameEquals
decl_stmt|;
specifier|private
name|String
name|nameStartsWith
decl_stmt|;
specifier|private
name|String
name|tag
decl_stmt|;
specifier|private
name|String
name|language
decl_stmt|;
specifier|private
name|Style
name|style
decl_stmt|;
specifier|private
name|FeatureClass
name|featureClass
decl_stmt|;
specifier|private
name|String
index|[]
name|featureCodes
decl_stmt|;
specifier|private
name|String
name|adminCode1
decl_stmt|;
specifier|private
name|String
name|adminCode2
decl_stmt|;
specifier|private
name|String
name|adminCode3
decl_stmt|;
specifier|private
name|String
name|adminCode4
decl_stmt|;
specifier|private
name|int
name|maxRows
decl_stmt|;
specifier|private
name|int
name|startRow
decl_stmt|;
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
throws|throws
name|InvalidParameterException
block|{
if|if
condition|(
name|countryCode
operator|!=
literal|null
operator|&&
name|countryCode
operator|.
name|length
argument_list|()
operator|!=
literal|2
condition|)
block|{
throw|throw
operator|new
name|InvalidParameterException
argument_list|(
literal|"invalid country code "
operator|+
name|countryCode
argument_list|)
throw|;
block|}
name|this
operator|.
name|countryCode
operator|=
name|countryCode
expr_stmt|;
block|}
comment|/**      * @return the continentCode      */
specifier|public
name|String
name|getContinentCode
parameter_list|()
block|{
return|return
name|continentCode
return|;
block|}
comment|/**      * @param continentCode the continentCode to set      */
specifier|public
name|void
name|setContinentCode
parameter_list|(
name|String
name|continentCode
parameter_list|)
block|{
name|this
operator|.
name|continentCode
operator|=
name|continentCode
expr_stmt|;
block|}
comment|/**      * @return Returns the nameEquals.      */
specifier|public
name|String
name|getNameEquals
parameter_list|()
block|{
return|return
name|nameEquals
return|;
block|}
comment|/**      * @param nameEquals      *            The nameEquals to set.      */
specifier|public
name|void
name|setNameEquals
parameter_list|(
name|String
name|exactName
parameter_list|)
block|{
name|this
operator|.
name|nameEquals
operator|=
name|exactName
expr_stmt|;
block|}
comment|/**      * @return Returns the featureCodes.      */
specifier|public
name|String
index|[]
name|getFeatureCodes
parameter_list|()
block|{
return|return
name|featureCodes
return|;
block|}
comment|/**      * @param featureCodes      *            The featureCodes to set.      */
specifier|public
name|void
name|setFeatureCodes
parameter_list|(
name|String
index|[]
name|featureCodes
parameter_list|)
block|{
name|this
operator|.
name|featureCodes
operator|=
name|featureCodes
expr_stmt|;
block|}
specifier|public
name|void
name|setFeatureCode
parameter_list|(
name|String
name|featureCode
parameter_list|)
block|{
name|this
operator|.
name|featureCodes
operator|=
operator|new
name|String
index|[]
block|{
name|featureCode
block|}
expr_stmt|;
block|}
comment|/**      * @return Returns the language.      */
specifier|public
name|String
name|getLanguage
parameter_list|()
block|{
return|return
name|language
return|;
block|}
comment|/**      * @param language      *            The language to set.      */
specifier|public
name|void
name|setLanguage
parameter_list|(
name|String
name|language
parameter_list|)
block|{
name|this
operator|.
name|language
operator|=
name|language
expr_stmt|;
block|}
comment|/**      * @return Returns the maxRows.      */
specifier|public
name|int
name|getMaxRows
parameter_list|()
block|{
return|return
name|maxRows
return|;
block|}
comment|/**      * @param maxRows      *            The maxRows to set.      */
specifier|public
name|void
name|setMaxRows
parameter_list|(
name|int
name|maxRows
parameter_list|)
block|{
name|this
operator|.
name|maxRows
operator|=
name|maxRows
expr_stmt|;
block|}
comment|/**      * @return Returns the name.      */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**      * search over the name field only.      *      * @param name      *            The name to set.      */
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
comment|/**      * @return Returns the q.      */
specifier|public
name|String
name|getQ
parameter_list|()
block|{
return|return
name|q
return|;
block|}
comment|/**      * The main search term. The search is executed over all fields (place name,      * country name, admin names, etc)      *      * @param q      *            The q to set.      */
specifier|public
name|void
name|setQ
parameter_list|(
name|String
name|q
parameter_list|)
block|{
name|this
operator|.
name|q
operator|=
name|q
expr_stmt|;
block|}
comment|/**      * @return Returns the startRow.      */
specifier|public
name|int
name|getStartRow
parameter_list|()
block|{
return|return
name|startRow
return|;
block|}
comment|/**      * @param startRow      *            The startRow to set.      */
specifier|public
name|void
name|setStartRow
parameter_list|(
name|int
name|startRow
parameter_list|)
block|{
name|this
operator|.
name|startRow
operator|=
name|startRow
expr_stmt|;
block|}
comment|/**      * @return Returns the style.      */
specifier|public
name|Style
name|getStyle
parameter_list|()
block|{
return|return
name|style
return|;
block|}
comment|/**      * @param style      *            The style to set.      */
specifier|public
name|void
name|setStyle
parameter_list|(
name|Style
name|style
parameter_list|)
block|{
name|this
operator|.
name|style
operator|=
name|style
expr_stmt|;
block|}
comment|/**      * @return Returns the tag.      */
specifier|public
name|String
name|getTag
parameter_list|()
block|{
return|return
name|tag
return|;
block|}
comment|/**      * @param tag      *            The tag to set.      */
specifier|public
name|void
name|setTag
parameter_list|(
name|String
name|tag
parameter_list|)
block|{
name|this
operator|.
name|tag
operator|=
name|tag
expr_stmt|;
block|}
comment|/**      * @return Returns the nameStartsWith.      */
specifier|public
name|String
name|getNameStartsWith
parameter_list|()
block|{
return|return
name|nameStartsWith
return|;
block|}
comment|/**      * @param nameStartsWith      *            The nameStartsWith to set.      */
specifier|public
name|void
name|setNameStartsWith
parameter_list|(
name|String
name|nameStartsWith
parameter_list|)
block|{
name|this
operator|.
name|nameStartsWith
operator|=
name|nameStartsWith
expr_stmt|;
block|}
comment|/**      * @return the featureClass      */
specifier|public
name|FeatureClass
name|getFeatureClass
parameter_list|()
block|{
return|return
name|featureClass
return|;
block|}
comment|/**      * @param featureClass      *            the featureClass to set      */
specifier|public
name|void
name|setFeatureClass
parameter_list|(
name|FeatureClass
name|featureClass
parameter_list|)
block|{
name|this
operator|.
name|featureClass
operator|=
name|featureClass
expr_stmt|;
block|}
comment|/**      * @return the adminCode1      */
specifier|public
name|String
name|getAdminCode1
parameter_list|()
block|{
return|return
name|adminCode1
return|;
block|}
comment|/**      * @param adminCode1      *            the adminCode1 to set      */
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
comment|/**      * @return the adminCode2      */
specifier|public
name|String
name|getAdminCode2
parameter_list|()
block|{
return|return
name|adminCode2
return|;
block|}
comment|/**      * @param adminCode2      *            the adminCode2 to set      */
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
comment|/**      * @return the adminCode3      */
specifier|public
name|String
name|getAdminCode3
parameter_list|()
block|{
return|return
name|adminCode3
return|;
block|}
comment|/**      * @param adminCode3      *            the adminCode3 to set      */
specifier|public
name|void
name|setAdminCode3
parameter_list|(
name|String
name|adminCode3
parameter_list|)
block|{
name|this
operator|.
name|adminCode3
operator|=
name|adminCode3
expr_stmt|;
block|}
specifier|public
name|String
name|getAdminCode4
parameter_list|()
block|{
return|return
name|adminCode4
return|;
block|}
specifier|public
name|void
name|setAdminCode4
parameter_list|(
name|String
name|adminCode4
parameter_list|)
block|{
name|this
operator|.
name|adminCode4
operator|=
name|adminCode4
expr_stmt|;
block|}
block|}
end_class

end_unit

