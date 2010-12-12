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
name|List
import|;
end_import

begin_comment
comment|/**  * a toponym search result as returned by the geonames webservice.  *  * @author marc@geonames  *  */
end_comment

begin_class
specifier|public
class|class
name|ToponymSearchResult
block|{
name|List
argument_list|<
name|Toponym
argument_list|>
name|toponyms
init|=
operator|new
name|ArrayList
argument_list|<
name|Toponym
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|totalResultsCount
decl_stmt|;
name|Style
name|style
decl_stmt|;
comment|/**      * @return Returns the toponyms.      */
specifier|public
name|List
argument_list|<
name|Toponym
argument_list|>
name|getToponyms
parameter_list|()
block|{
return|return
name|toponyms
return|;
block|}
comment|/**      * @param toponyms      *            The toponyms to set.      */
specifier|public
name|void
name|setToponyms
parameter_list|(
name|List
argument_list|<
name|Toponym
argument_list|>
name|toponyms
parameter_list|)
block|{
name|this
operator|.
name|toponyms
operator|=
name|toponyms
expr_stmt|;
block|}
comment|/**      * @return Returns the totalResultsCount.      */
specifier|public
name|int
name|getTotalResultsCount
parameter_list|()
block|{
return|return
name|totalResultsCount
return|;
block|}
comment|/**      * @param totalResultsCount      *            The totalResultsCount to set.      */
specifier|public
name|void
name|setTotalResultsCount
parameter_list|(
name|int
name|totalResultsCount
parameter_list|)
block|{
name|this
operator|.
name|totalResultsCount
operator|=
name|totalResultsCount
expr_stmt|;
block|}
comment|/**      * @return the style      */
specifier|public
name|Style
name|getStyle
parameter_list|()
block|{
return|return
name|style
return|;
block|}
comment|/**      * @param style the style to set      */
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
block|}
end_class

end_unit

