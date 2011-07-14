begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|source
operator|.
name|jenatdb
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|FilenameUtils
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityDataIterable
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityDataIterator
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityDataProvider
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityIterator
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|EntityIterator
operator|.
name|EntityScore
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|config
operator|.
name|IndexingConfig
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Reference
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|AfterClass
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|*
import|;
end_import

begin_class
specifier|public
class|class
name|RdfIndexingSourceTest
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RdfIndexingSourceTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|long
name|NUMBER_OF_ENTITIES_EXPECTED
init|=
literal|5
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CONFIG_ROOT
init|=
name|FilenameUtils
operator|.
name|separatorsToSystem
argument_list|(
literal|"testConfigs/"
argument_list|)
decl_stmt|;
comment|/**      * mvn copies the resources in "src/test/resources" to target/test-classes.      * This folder is than used as classpath.<p>      * "/target/test-files/" does not exist, but is created by the      * {@link IndexingConfig}.      */
specifier|private
specifier|static
specifier|final
name|String
name|TEST_ROOT
init|=
name|FilenameUtils
operator|.
name|separatorsToSystem
argument_list|(
literal|"/target/test-files"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|userDir
decl_stmt|;
specifier|private
specifier|static
name|String
name|testRoot
decl_stmt|;
comment|/**      * The methods resets the "user.dir" system property      */
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|initTestRootFolder
parameter_list|()
block|{
name|String
name|baseDir
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"basedir"
argument_list|)
decl_stmt|;
if|if
condition|(
name|baseDir
operator|==
literal|null
condition|)
block|{
name|baseDir
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.dir"
argument_list|)
expr_stmt|;
block|}
comment|//store the current user.dir
name|userDir
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.dir"
argument_list|)
expr_stmt|;
name|testRoot
operator|=
name|baseDir
operator|+
name|TEST_ROOT
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"ConfigTest Root : "
operator|+
name|testRoot
argument_list|)
expr_stmt|;
comment|//set the user.dir to the testRoot (needed to test loading of missing
comment|//configurations via classpath
comment|//store the current user.dir and reset it after the tests
name|System
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|testRoot
argument_list|)
expr_stmt|;
block|}
comment|/**      * resets the "user.dir" system property the the original value      */
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|cleanup
parameter_list|()
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"user.dir"
argument_list|,
name|userDir
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEntityDataIterable
parameter_list|()
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|CONFIG_ROOT
operator|+
literal|"iterable"
argument_list|)
decl_stmt|;
name|EntityDataIterable
name|iterable
init|=
name|config
operator|.
name|getDataInterable
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|iterable
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|iterable
operator|.
name|getClass
argument_list|()
argument_list|,
name|RdfIndexingSource
operator|.
name|class
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|iterable
operator|.
name|needsInitialisation
argument_list|()
argument_list|)
expr_stmt|;
name|iterable
operator|.
name|initialise
argument_list|()
expr_stmt|;
name|EntityDataIterator
name|it
init|=
name|iterable
operator|.
name|entityDataIterator
argument_list|()
decl_stmt|;
name|long
name|count
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|entity
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"validate Entity "
operator|+
name|entity
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|entity
argument_list|)
expr_stmt|;
name|validateRepresentation
argument_list|(
name|it
operator|.
name|getRepresentation
argument_list|()
argument_list|,
name|entity
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
comment|//check if all entities where indexed
comment|//this checks if more entities are indexed as listed by the
comment|//textEntityIDs.txt file
name|assertTrue
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"> %s Entities expected but only %s processed!"
argument_list|,
name|NUMBER_OF_ENTITIES_EXPECTED
argument_list|,
name|count
argument_list|)
argument_list|,
name|NUMBER_OF_ENTITIES_EXPECTED
operator|<=
name|count
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testEntityDataProvider
parameter_list|()
block|{
name|IndexingConfig
name|config
init|=
operator|new
name|IndexingConfig
argument_list|(
name|CONFIG_ROOT
operator|+
literal|"provider"
argument_list|)
decl_stmt|;
name|EntityIterator
name|entityIdIterator
init|=
name|config
operator|.
name|getEntityIdIterator
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
literal|"Unable to perform test whithout EntityIterator"
argument_list|,
name|entityIdIterator
argument_list|)
expr_stmt|;
if|if
condition|(
name|entityIdIterator
operator|.
name|needsInitialisation
argument_list|()
condition|)
block|{
name|entityIdIterator
operator|.
name|initialise
argument_list|()
expr_stmt|;
block|}
name|EntityDataProvider
name|dataProvider
init|=
name|config
operator|.
name|getEntityDataProvider
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|dataProvider
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|dataProvider
operator|.
name|needsInitialisation
argument_list|()
argument_list|)
expr_stmt|;
comment|//there are test data to load
name|dataProvider
operator|.
name|initialise
argument_list|()
expr_stmt|;
name|assertEquals
argument_list|(
name|dataProvider
operator|.
name|getClass
argument_list|()
argument_list|,
name|RdfIndexingSource
operator|.
name|class
argument_list|)
expr_stmt|;
name|long
name|count
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|entityIdIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|EntityScore
name|entityScore
init|=
name|entityIdIterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|entityScore
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|entityScore
operator|.
name|id
argument_list|)
expr_stmt|;
name|validateRepresentation
argument_list|(
name|dataProvider
operator|.
name|getEntityData
argument_list|(
name|entityScore
operator|.
name|id
argument_list|)
argument_list|,
name|entityScore
operator|.
name|id
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
comment|//check if all entities where found
name|assertEquals
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s Entities expected but %s processed!"
argument_list|,
name|NUMBER_OF_ENTITIES_EXPECTED
argument_list|,
name|count
argument_list|)
argument_list|,
name|NUMBER_OF_ENTITIES_EXPECTED
argument_list|,
name|count
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param it      * @param entity      */
specifier|private
name|void
name|validateRepresentation
parameter_list|(
name|Representation
name|rep
parameter_list|,
name|String
name|id
parameter_list|)
block|{
name|assertNotNull
argument_list|(
literal|"Representation for Entity with ID "
operator|+
name|id
operator|+
literal|" is null"
argument_list|,
name|rep
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|id
argument_list|,
name|rep
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
comment|//check if multiple languages are parsed correctly
name|testText
argument_list|(
name|rep
argument_list|)
expr_stmt|;
comment|//TODO: need to add XSD dataTypes to the test data
comment|//testValue(rep, Double.class);
name|testReference
argument_list|(
name|rep
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|testText
parameter_list|(
name|Representation
name|rep
parameter_list|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|rep
operator|.
name|getFieldNames
argument_list|()
init|;
name|fields
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|field
init|=
name|fields
operator|.
name|next
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Text
argument_list|>
name|values
init|=
name|rep
operator|.
name|getText
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|//            assertTrue(values.hasNext());
while|while
condition|(
name|values
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Text
name|text
init|=
name|values
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|String
name|lang
init|=
name|text
operator|.
name|getLanguage
argument_list|()
decl_stmt|;
comment|//log.info(text.getText()+" | "+text.getLanguage()+" | "+text.getText().endsWith("@"+lang));
comment|//this texts that the text does not contain the @{lang} as added by
comment|//the toString method of the RDF Literal java class
name|assertFalse
argument_list|(
literal|"Labels MUST NOT end with the Language! value="
operator|+
name|text
operator|.
name|getText
argument_list|()
argument_list|,
name|text
operator|.
name|getText
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"@"
operator|+
name|lang
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
parameter_list|<
name|T
parameter_list|>
name|void
name|testValue
parameter_list|(
name|Representation
name|rep
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|rep
operator|.
name|getFieldNames
argument_list|()
init|;
name|fields
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|field
init|=
name|fields
operator|.
name|next
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|T
argument_list|>
name|values
init|=
name|rep
operator|.
name|get
argument_list|(
name|field
argument_list|,
name|type
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|values
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|values
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|T
name|value
init|=
name|values
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|testReference
parameter_list|(
name|Representation
name|rep
parameter_list|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|rep
operator|.
name|getFieldNames
argument_list|()
init|;
name|fields
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|field
init|=
name|fields
operator|.
name|next
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Reference
argument_list|>
name|values
init|=
name|rep
operator|.
name|getReferences
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|//            assertTrue(values.hasNext());
while|while
condition|(
name|values
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Reference
name|ref
init|=
name|values
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|ref
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

