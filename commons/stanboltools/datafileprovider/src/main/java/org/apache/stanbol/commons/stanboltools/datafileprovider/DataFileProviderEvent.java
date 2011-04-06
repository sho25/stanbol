begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|stanboltools
operator|.
name|datafileprovider
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_comment
comment|/** Used to record a log of {@link DataFileProvider} operations */
end_comment

begin_class
specifier|public
class|class
name|DataFileProviderEvent
block|{
specifier|private
specifier|final
name|Date
name|timestamp
decl_stmt|;
specifier|private
specifier|final
name|String
name|bundleSymbolicName
decl_stmt|;
specifier|private
specifier|final
name|String
name|filename
decl_stmt|;
specifier|private
specifier|final
name|String
name|downloadExplanation
decl_stmt|;
specifier|private
specifier|final
name|String
name|loadingClass
decl_stmt|;
specifier|private
specifier|final
name|String
name|actualFileLocation
decl_stmt|;
specifier|public
name|DataFileProviderEvent
parameter_list|(
name|String
name|bundleSymbolicName
parameter_list|,
name|String
name|filename
parameter_list|,
name|String
name|downloadExplanation
parameter_list|,
name|String
name|loadingClass
parameter_list|,
name|String
name|actualFileLocation
parameter_list|)
block|{
name|this
operator|.
name|timestamp
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
name|this
operator|.
name|bundleSymbolicName
operator|=
name|bundleSymbolicName
expr_stmt|;
name|this
operator|.
name|filename
operator|=
name|filename
expr_stmt|;
name|this
operator|.
name|downloadExplanation
operator|=
name|downloadExplanation
expr_stmt|;
name|this
operator|.
name|loadingClass
operator|=
name|loadingClass
expr_stmt|;
name|this
operator|.
name|actualFileLocation
operator|=
name|actualFileLocation
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", bundleSymbolicName="
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|bundleSymbolicName
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", filename="
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|filename
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", loadingClass="
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|loadingClass
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", actualFileLocation="
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|actualFileLocation
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** @return the timestamp of this event */
specifier|public
name|Date
name|getTimestamp
parameter_list|()
block|{
return|return
name|timestamp
return|;
block|}
comment|/** @return the bundle symbolic name that was passed to the DataFileProvider */
specifier|public
name|String
name|getBundleSymbolicName
parameter_list|()
block|{
return|return
name|bundleSymbolicName
return|;
block|}
comment|/** @return the filename that was passed to the DataFileProvider */
specifier|public
name|String
name|getFilename
parameter_list|()
block|{
return|return
name|filename
return|;
block|}
comment|/** @return the download explanation that was passed to the DataFileProvider */
specifier|public
name|String
name|getDownloadExplanation
parameter_list|()
block|{
return|return
name|downloadExplanation
return|;
block|}
comment|/** @return the name of the class which provided the file */
specifier|public
name|String
name|getLoadingClass
parameter_list|()
block|{
return|return
name|loadingClass
return|;
block|}
comment|/** @return the actual location of the file that was loaded, empty if file was not found */
specifier|public
name|String
name|getActualFileLocation
parameter_list|()
block|{
return|return
name|actualFileLocation
return|;
block|}
block|}
end_class

end_unit

