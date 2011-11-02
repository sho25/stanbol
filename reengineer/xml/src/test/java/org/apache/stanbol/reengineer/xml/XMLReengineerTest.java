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
name|reengineer
operator|.
name|xml
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
name|assertNotNull
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
name|Dictionary
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
name|access
operator|.
name|WeightedTcProvider
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
name|serializedform
operator|.
name|Parser
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
name|sparql
operator|.
name|QueryEngine
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
name|jena
operator|.
name|sparql
operator|.
name|JenaSparqlEngine
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
name|simple
operator|.
name|storage
operator|.
name|SimpleTcProvider
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
name|OfflineConfiguration
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
name|clerezza
operator|.
name|ClerezzaOntologyProvider
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|DataSource
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|Reengineer
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|ReengineerType
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
name|reengineer
operator|.
name|base
operator|.
name|impl
operator|.
name|ReengineerManagerImpl
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
name|Before
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntology
import|;
end_import

begin_class
specifier|public
class|class
name|XMLReengineerTest
block|{
specifier|private
specifier|static
name|DataSource
name|dataSource
decl_stmt|;
specifier|private
specifier|static
name|String
name|graphNS
decl_stmt|;
specifier|private
specifier|static
name|OfflineConfiguration
name|offline
decl_stmt|;
specifier|private
specifier|static
name|IRI
name|outputIRI
decl_stmt|;
specifier|private
specifier|static
name|Reengineer
name|xmlExtractor
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setupClass
parameter_list|()
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|conf
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|conf
operator|.
name|put
argument_list|(
name|OfflineConfiguration
operator|.
name|ONTOLOGY_PATHS
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"/meta"
block|}
argument_list|)
expr_stmt|;
name|offline
operator|=
operator|new
name|OfflineConfigurationImpl
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|graphNS
operator|=
literal|"http://kres.iks-project.eu/reengineering/test"
expr_stmt|;
name|outputIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
argument_list|)
expr_stmt|;
name|dataSource
operator|=
operator|new
name|DataSource
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Object
name|getDataSource
parameter_list|()
block|{
name|InputStream
name|xmlStream
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/xml/weather.xml"
argument_list|)
decl_stmt|;
return|return
name|xmlStream
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getDataSourceType
parameter_list|()
block|{
return|return
name|ReengineerType
operator|.
name|XML
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getID
parameter_list|()
block|{
comment|// Not going to check ID
return|return
literal|null
return|;
block|}
block|}
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|dataReengineeringTest
parameter_list|()
throws|throws
name|Exception
block|{
name|OWLOntology
name|schemaOntology
init|=
name|xmlExtractor
operator|.
name|schemaReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|schemaOntology
argument_list|)
expr_stmt|;
name|OWLOntology
name|reengineered
init|=
name|xmlExtractor
operator|.
name|dataReengineering
argument_list|(
name|graphNS
argument_list|,
name|IRI
operator|.
name|create
argument_list|(
name|outputIRI
operator|.
name|toString
argument_list|()
operator|+
literal|"_new"
argument_list|)
argument_list|,
name|dataSource
argument_list|,
name|schemaOntology
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|reengineered
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|reengineeringTest
parameter_list|()
throws|throws
name|Exception
block|{
name|OWLOntology
name|reengineered
init|=
name|xmlExtractor
operator|.
name|reengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|reengineered
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|schemaReengineeringTest
parameter_list|()
throws|throws
name|Exception
block|{
name|OWLOntology
name|schemaOntology
init|=
name|xmlExtractor
operator|.
name|schemaReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|schemaOntology
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
specifier|public
name|void
name|setup
parameter_list|()
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|emptyConf
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
class|class
name|SpecialTcManager
extends|extends
name|TcManager
block|{
specifier|public
name|SpecialTcManager
parameter_list|(
name|QueryEngine
name|qe
parameter_list|,
name|WeightedTcProvider
name|wtcp
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|bindQueryEngine
argument_list|(
name|qe
argument_list|)
expr_stmt|;
name|bindWeightedTcProvider
argument_list|(
name|wtcp
argument_list|)
expr_stmt|;
block|}
block|}
name|QueryEngine
name|qe
init|=
operator|new
name|JenaSparqlEngine
argument_list|()
decl_stmt|;
name|WeightedTcProvider
name|wtcp
init|=
operator|new
name|SimpleTcProvider
argument_list|()
decl_stmt|;
name|TcManager
name|tcm
init|=
operator|new
name|SpecialTcManager
argument_list|(
name|qe
argument_list|,
name|wtcp
argument_list|)
decl_stmt|;
comment|// Two different ontology storages, the same sparql engine and tcprovider
name|ONManager
name|onManager
init|=
operator|new
name|ONManagerImpl
argument_list|(
operator|new
name|ClerezzaOntologyProvider
argument_list|(
name|tcm
argument_list|,
name|offline
argument_list|,
operator|new
name|Parser
argument_list|()
argument_list|)
argument_list|,
name|offline
argument_list|,
name|emptyConf
argument_list|)
decl_stmt|;
name|xmlExtractor
operator|=
operator|new
name|XMLExtractor
argument_list|(
operator|new
name|ReengineerManagerImpl
argument_list|(
name|emptyConf
argument_list|)
argument_list|,
name|onManager
argument_list|,
name|emptyConf
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|xmlExtractor
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

