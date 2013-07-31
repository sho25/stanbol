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
name|core
operator|.
name|processor
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespacePrefixProvider
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
name|commons
operator|.
name|namespaceprefix
operator|.
name|impl
operator|.
name|NamespacePrefixProviderImpl
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
name|core
operator|.
name|model
operator|.
name|InMemoryValueFactory
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
name|EntityProcessor
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
name|defaults
operator|.
name|NamespaceEnum
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
name|ValueFactory
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

begin_class
specifier|public
class|class
name|FieldValueFilterTest
block|{
specifier|private
specifier|static
specifier|final
name|String
name|FB
init|=
literal|"http://rdf.freebase.com/ns/"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|TEST_CONFIG
init|=
literal|"prefix.config"
decl_stmt|;
specifier|private
specifier|static
name|ValueFactory
name|vf
init|=
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|NamespacePrefixProvider
name|nsPrefixProvider
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|nsMappings
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
static|static
block|{
name|nsMappings
operator|.
name|put
argument_list|(
literal|"fb"
argument_list|,
name|FB
argument_list|)
expr_stmt|;
name|nsMappings
operator|.
name|put
argument_list|(
literal|"rdf"
argument_list|,
name|NamespaceEnum
operator|.
name|rdf
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
name|nsMappings
operator|.
name|put
argument_list|(
literal|"rdfs"
argument_list|,
name|NamespaceEnum
operator|.
name|rdfs
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
name|nsMappings
operator|.
name|put
argument_list|(
literal|"skos"
argument_list|,
name|NamespaceEnum
operator|.
name|skos
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
name|nsMappings
operator|.
name|put
argument_list|(
literal|"foaf"
argument_list|,
name|NamespaceEnum
operator|.
name|foaf
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|init
parameter_list|()
throws|throws
name|IOException
block|{
name|nsPrefixProvider
operator|=
operator|new
name|NamespacePrefixProviderImpl
argument_list|(
name|nsMappings
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testIncludeExcludeConfig1
parameter_list|()
block|{
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"rdf:type"
argument_list|,
literal|"foaf:Person;skos:Concept;!skos:Concept"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IllegalArgumentException
operator|.
name|class
argument_list|)
specifier|public
name|void
name|testIncludeExcludeConfig2
parameter_list|()
block|{
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"rdf:type"
argument_list|,
literal|"foaf:Person;!skos:Concept;skos:Concept"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testIncludeConfig
parameter_list|()
block|{
name|EntityProcessor
name|filter
init|=
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"rdf:type"
argument_list|,
literal|"foaf:Person"
argument_list|)
decl_stmt|;
name|Representation
name|r
init|=
name|getRepresentation
argument_list|(
name|NamespaceEnum
operator|.
name|foaf
operator|+
literal|"Person"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|=
name|getRepresentation
argument_list|(
name|NamespaceEnum
operator|.
name|skos
operator|+
literal|"Concept"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|=
name|getRepresentation
argument_list|(
name|NamespaceEnum
operator|.
name|skos
operator|+
literal|"Concept"
argument_list|,
name|NamespaceEnum
operator|.
name|foaf
operator|+
literal|"Person"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
comment|//test empty value
name|filter
operator|=
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"skos:releated"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|filter
operator|=
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"skos:releated"
argument_list|,
literal|"null"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|filter
operator|=
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"skos:releated"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testExcludeConfig
parameter_list|()
block|{
name|EntityProcessor
name|filter
init|=
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"rdf:type"
argument_list|,
literal|"*;!foaf:Person"
argument_list|)
decl_stmt|;
name|Representation
name|r
init|=
name|getRepresentation
argument_list|(
name|NamespaceEnum
operator|.
name|foaf
operator|+
literal|"Person"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|=
name|getRepresentation
argument_list|(
name|NamespaceEnum
operator|.
name|skos
operator|+
literal|"Concept"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|=
name|getRepresentation
argument_list|(
name|NamespaceEnum
operator|.
name|skos
operator|+
literal|"Concept"
argument_list|,
name|NamespaceEnum
operator|.
name|foaf
operator|+
literal|"Person"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
comment|//test empty value
name|filter
operator|=
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"skos:releated"
argument_list|,
literal|"*;!null"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|filter
operator|=
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"skos:releated"
argument_list|,
literal|"*;!"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
name|filter
operator|=
operator|new
name|FieldValueFilter
argument_list|(
name|nsPrefixProvider
argument_list|,
literal|"skos:releated"
argument_list|,
literal|"*;!;!foaf:Person"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|filter
operator|.
name|process
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
name|Representation
name|getRepresentation
parameter_list|(
name|String
modifier|...
name|types
parameter_list|)
block|{
name|Representation
name|r
init|=
name|vf
operator|.
name|createRepresentation
argument_list|(
literal|"urn:test"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|type
range|:
name|types
control|)
block|{
name|r
operator|.
name|add
argument_list|(
name|NamespaceEnum
operator|.
name|rdf
operator|+
literal|"type"
argument_list|,
name|vf
operator|.
name|createReference
argument_list|(
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|r
return|;
block|}
block|}
end_class

end_unit

