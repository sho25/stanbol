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
name|langdetect
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|io
operator|.
name|IOUtils
import|;
end_import

begin_import
import|import
name|com
operator|.
name|cybozu
operator|.
name|labs
operator|.
name|langdetect
operator|.
name|Detector
import|;
end_import

begin_import
import|import
name|com
operator|.
name|cybozu
operator|.
name|labs
operator|.
name|langdetect
operator|.
name|DetectorFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|cybozu
operator|.
name|labs
operator|.
name|langdetect
operator|.
name|LangDetectException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|cybozu
operator|.
name|labs
operator|.
name|langdetect
operator|.
name|Language
import|;
end_import

begin_comment
comment|/**  * Standalone version of the Language Identifier  * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  *   */
end_comment

begin_class
specifier|public
class|class
name|LanguageIdentifier
block|{
specifier|public
name|LanguageIdentifier
parameter_list|()
throws|throws
name|LangDetectException
block|{
name|DetectorFactory
operator|.
name|clear
argument_list|()
expr_stmt|;
try|try
block|{
name|DetectorFactory
operator|.
name|loadProfile
argument_list|(
name|loadProfiles
argument_list|(
literal|"profiles"
argument_list|,
literal|"profiles.cfg"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|LangDetectException
argument_list|(
literal|null
argument_list|,
literal|"Error in Initialization: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
comment|/**      * Load the profiles from the classpath      * @param folder where the profiles are      * @param configFile specifies which language profiles should be used      * @return a list of profiles      * @throws Exception      */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|loadProfiles
parameter_list|(
name|String
name|folder
parameter_list|,
name|String
name|configFile
parameter_list|)
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|String
argument_list|>
name|profiles
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|java
operator|.
name|util
operator|.
name|Properties
name|props
init|=
operator|new
name|java
operator|.
name|util
operator|.
name|Properties
argument_list|()
decl_stmt|;
name|props
operator|.
name|load
argument_list|(
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|configFile
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|languages
init|=
name|props
operator|.
name|getProperty
argument_list|(
literal|"languages"
argument_list|)
decl_stmt|;
if|if
condition|(
name|languages
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"No languages defined"
argument_list|)
throw|;
block|}
for|for
control|(
name|String
name|lang
range|:
name|languages
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|String
name|profileFile
init|=
name|folder
operator|+
literal|"/"
operator|+
name|lang
decl_stmt|;
name|InputStream
name|is
init|=
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|profileFile
argument_list|)
decl_stmt|;
try|try
block|{
name|String
name|profile
init|=
name|IOUtils
operator|.
name|toString
argument_list|(
name|is
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
if|if
condition|(
name|profile
operator|!=
literal|null
operator|&&
name|profile
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|profiles
operator|.
name|add
argument_list|(
name|profile
argument_list|)
expr_stmt|;
block|}
name|is
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|profiles
return|;
block|}
specifier|public
name|String
name|getLanguage
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|LangDetectException
block|{
name|Detector
name|detector
init|=
name|DetectorFactory
operator|.
name|create
argument_list|()
decl_stmt|;
name|detector
operator|.
name|append
argument_list|(
name|text
argument_list|)
expr_stmt|;
return|return
name|detector
operator|.
name|detect
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|Language
argument_list|>
name|getLanguages
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|LangDetectException
block|{
name|Detector
name|detector
init|=
name|DetectorFactory
operator|.
name|create
argument_list|()
decl_stmt|;
name|detector
operator|.
name|append
argument_list|(
name|text
argument_list|)
expr_stmt|;
return|return
name|detector
operator|.
name|getProbabilities
argument_list|()
return|;
block|}
block|}
end_class

end_unit

