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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|io
package|;
end_package

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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Set
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
name|Graph
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
name|Triple
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
name|utils
operator|.
name|GraphNode
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|Constants
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|MockOsgiContext
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ONManager
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|io
operator|.
name|OntologyInputSource
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|io
operator|.
name|RootOntologyIRISource
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|ontology
operator|.
name|OntologyScope
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|impl
operator|.
name|ONManagerImpl
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|impl
operator|.
name|OfflineConfigurationImpl
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
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

begin_class
specifier|public
class|class
name|TestStorage
block|{
specifier|private
specifier|static
name|ONManager
name|onm
decl_stmt|;
specifier|private
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
name|String
name|scopeId
init|=
literal|"StorageTest"
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
comment|// Empty configurations are fine, but this time we provide a Clerezza context.
name|onm
operator|=
operator|new
name|ONManagerImpl
argument_list|(
name|MockOsgiContext
operator|.
name|tcManager
argument_list|,
name|MockOsgiContext
operator|.
name|tcManager
operator|.
name|getProviderList
argument_list|()
operator|.
name|first
argument_list|()
argument_list|,
operator|new
name|OfflineConfigurationImpl
argument_list|(
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
argument_list|,
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|storageOnScopeCreation
parameter_list|()
throws|throws
name|Exception
block|{
name|assertTrue
argument_list|(
name|onm
operator|.
name|getOntologyStore
argument_list|()
operator|.
name|listGraphs
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|OntologyInputSource
name|ois
init|=
operator|new
name|RootOntologyIRISource
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/ontologies/minorcharacters.owl"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|OntologyScope
name|sc
init|=
name|onm
operator|.
name|getOntologyScopeFactory
argument_list|()
operator|.
name|createOntologyScope
argument_list|(
name|scopeId
argument_list|,
name|ois
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Triple
argument_list|>
name|triples
init|=
operator|new
name|HashSet
argument_list|<
name|Triple
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|IRI
name|iri
range|:
name|onm
operator|.
name|getOntologyStore
argument_list|()
operator|.
name|listGraphs
argument_list|()
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"{}"
argument_list|,
name|iri
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|UriRef
name|entity
init|=
operator|new
name|UriRef
argument_list|(
name|Constants
operator|.
name|PEANUTS_MINOR_BASE
operator|+
literal|"#"
operator|+
name|Constants
operator|.
name|truffles
argument_list|)
decl_stmt|;
name|Graph
name|ctx
init|=
operator|new
name|GraphNode
argument_list|(
name|entity
argument_list|,
name|onm
operator|.
name|getOntologyStore
argument_list|()
operator|.
name|getGraph
argument_list|(
operator|new
name|UriRef
argument_list|(
name|iri
operator|.
name|toString
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|iri
operator|.
name|toString
argument_list|()
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
argument_list|)
argument_list|)
operator|.
name|getNodeContext
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|ctx
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|triples
operator|.
name|add
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|onm
operator|.
name|getOntologyStore
argument_list|()
operator|.
name|listGraphs
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|triples
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
specifier|public
name|void
name|reset
parameter_list|()
block|{
name|MockOsgiContext
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit
