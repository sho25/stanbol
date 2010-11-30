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
comment|/**  * gmtOffset and dstOffset are computed on the server with the  * {@link java.util.TimeZone} and included in the web service as not all  * geonames users are using java.  *   * @author marc  *   */
end_comment

begin_class
specifier|public
class|class
name|Timezone
block|{
specifier|private
name|String
name|timezoneId
decl_stmt|;
specifier|private
name|double
name|gmtOffset
decl_stmt|;
specifier|private
name|double
name|dstOffset
decl_stmt|;
comment|/** 	 * the dstOffset as of first of July of current year 	 *  	 * @return the dstOffset 	 */
specifier|public
name|double
name|getDstOffset
parameter_list|()
block|{
return|return
name|dstOffset
return|;
block|}
comment|/** 	 * @param dstOffset 	 *            the dstOffset to set 	 */
specifier|public
name|void
name|setDstOffset
parameter_list|(
name|double
name|dstOffset
parameter_list|)
block|{
name|this
operator|.
name|dstOffset
operator|=
name|dstOffset
expr_stmt|;
block|}
comment|/** 	 * the gmtOffset as of first of January of current year 	 *  	 * @return the gmtOffset 	 */
specifier|public
name|double
name|getGmtOffset
parameter_list|()
block|{
return|return
name|gmtOffset
return|;
block|}
comment|/** 	 * @param gmtOffset 	 *            the gmtOffset to set 	 */
specifier|public
name|void
name|setGmtOffset
parameter_list|(
name|double
name|gmtOffset
parameter_list|)
block|{
name|this
operator|.
name|gmtOffset
operator|=
name|gmtOffset
expr_stmt|;
block|}
comment|/** 	 * the timezoneId (example : "Pacific/Honolulu") 	 *  	 * see also {@link java.util.TimeZone} and 	 * http://www.twinsun.com/tz/tz-link.htm 	 *  	 * @return the timezoneId 	 */
specifier|public
name|String
name|getTimezoneId
parameter_list|()
block|{
return|return
name|timezoneId
return|;
block|}
comment|/** 	 * @param timezoneId 	 *            the timezoneId to set 	 */
specifier|public
name|void
name|setTimezoneId
parameter_list|(
name|String
name|timezoneId
parameter_list|)
block|{
name|this
operator|.
name|timezoneId
operator|=
name|timezoneId
expr_stmt|;
block|}
block|}
end_class

end_unit

