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
name|opencalais
operator|.
name|impl
package|;
end_package

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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|LiteralFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|TcManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|TripleImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|EngineException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|InMemoryContentItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assume
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/** This class provides JUnit tests for OpenCalaisEngine.  *   * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  */
end_comment

begin_class
specifier|public
class|class
name|TestOpenCalaisEngine
block|{
comment|/**    * This contains the logger.    */
specifier|private
specifier|static
specifier|final
name|Logger
name|LOG
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|TestOpenCalaisEngine
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|OpenCalaisEngine
name|calaisExtractor
decl_stmt|;
specifier|private
specifier|static
name|String
name|TEST_LICENSE_KEY
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|OpenCalaisEngine
operator|.
name|LICENSE_KEY
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|TEST_TEXT
init|=
literal|"Israeli PM Netanyahu pulls out of US nuclear summit\nIsraeli PM Benjamin Netanyahu has cancelled a visit to the US where he was to attend a summit on nuclear security, Israeli officials say. Mr Netanyahu made the decision after learning that Egypt and Turkey intended to raise the issue of Israel's presumed nuclear arsenal, the officials said. Mr Obama is due to host dozens of world leaders at the two-day conference, which begins in Washington on Monday. Israel has never confirmed or denied that it possesses atomic weapons. Israel's Intelligence and Atomic Energy Minister Dan Meridor will take Netanyahu's place in the nuclear summit, Israeli radio said. More than 40 countries are expected at the meeting, which will focus on preventing the spread of nuclear weapons to militant groups."
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|oneTimeSetup
parameter_list|()
throws|throws
name|ConfigurationException
block|{
name|calaisExtractor
operator|=
operator|new
name|OpenCalaisEngine
argument_list|()
expr_stmt|;
name|calaisExtractor
operator|.
name|setCalaisTypeMap
argument_list|(
operator|new
name|HashMap
argument_list|<
name|UriRef
argument_list|,
name|UriRef
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|calaisExtractor
operator|.
name|tcManager
operator|=
name|TcManager
operator|.
name|getInstance
argument_list|()
expr_stmt|;
if|if
condition|(
name|TEST_LICENSE_KEY
operator|!=
literal|null
operator|&&
name|TEST_LICENSE_KEY
operator|.
name|matches
argument_list|(
literal|"\\w+"
argument_list|)
condition|)
block|{
name|calaisExtractor
operator|.
name|setLicenseKey
argument_list|(
name|TEST_LICENSE_KEY
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|ContentItem
name|wrapAsContentItem
parameter_list|(
specifier|final
name|String
name|text
parameter_list|)
block|{
return|return
operator|new
name|InMemoryContentItem
argument_list|(
operator|(
name|UriRef
operator|)
literal|null
argument_list|,
name|text
argument_list|,
literal|"text/plain"
argument_list|)
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEntityExtraction
parameter_list|()
block|{
name|String
name|testFile
init|=
literal|"calaisresult.owl"
decl_stmt|;
name|String
name|format
init|=
literal|"application/rdf+xml"
decl_stmt|;
name|InputStream
name|in
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|testFile
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"failed to load resource "
operator|+
name|testFile
argument_list|,
name|in
argument_list|)
expr_stmt|;
name|MGraph
name|model
init|=
name|calaisExtractor
operator|.
name|readModel
argument_list|(
name|in
argument_list|,
name|format
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"model reader failed with format: "
operator|+
name|format
argument_list|,
name|model
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|CalaisEntityOccurrence
argument_list|>
name|entities
init|=
name|calaisExtractor
operator|.
name|queryModel
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|LOG
operator|.
name|info
argument_list|(
literal|"Found entities: {}"
argument_list|,
name|entities
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|LOG
operator|.
name|debug
argument_list|(
literal|"Entities:\n{}"
argument_list|,
name|entities
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"No entities found!"
argument_list|,
name|entities
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testCalaisConnection
parameter_list|()
block|{
name|Assume
operator|.
name|assumeNotNull
argument_list|(
name|calaisExtractor
operator|.
name|getLicenseKey
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|ContentItem
name|ci
init|=
name|wrapAsContentItem
argument_list|(
name|TEST_TEXT
argument_list|)
decl_stmt|;
name|ci
operator|.
name|getMetadata
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|ci
operator|.
name|getUri
argument_list|()
argument_list|,
name|Properties
operator|.
name|DC_LANGUAGE
argument_list|,
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createTypedLiteral
argument_list|(
literal|"en"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|MGraph
name|model
init|=
name|calaisExtractor
operator|.
name|getCalaisAnalysis
argument_list|(
name|TEST_TEXT
argument_list|,
literal|"text/plain"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
literal|"No model"
argument_list|,
name|model
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|CalaisEntityOccurrence
argument_list|>
name|entities
init|=
name|calaisExtractor
operator|.
name|queryModel
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|LOG
operator|.
name|info
argument_list|(
literal|"Found entities: {}"
argument_list|,
name|entities
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|LOG
operator|.
name|debug
argument_list|(
literal|"Entities:\n{}"
argument_list|,
name|entities
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"No entities found!"
argument_list|,
name|entities
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EngineException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
literal|"Connection problem: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// problem with license keys for testing?
comment|// ask user to supply it as system property or whatever?
block|}
end_class

end_unit

