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
name|impl
operator|.
name|clerezza
package|;
end_package

begin_import
import|import static
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
operator|.
name|*
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
name|assertTrue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|net
operator|.
name|URL
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
name|ParentPathInputSource
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
name|OntologySpace
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
name|owl
operator|.
name|PhonyIRIMapper
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
name|apibinding
operator|.
name|OWLManager
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
name|OWLImportsDeclaration
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
name|OWLOntologyLoaderListener
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
name|OWLOntologyManager
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
name|TestOntologyCollectors
block|{
specifier|private
name|IRI
name|scopeNs
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontologies/"
argument_list|)
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
annotation|@
name|After
specifier|public
name|void
name|cleanup
parameter_list|()
block|{
name|reset
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|sessionMergesOntologies
parameter_list|()
throws|throws
name|Exception
block|{
comment|// TODO after merging is implemented
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|sessionPreservesImports
parameter_list|()
throws|throws
name|Exception
block|{      }
annotation|@
name|Test
specifier|public
name|void
name|spaceMergesOntologies
parameter_list|()
throws|throws
name|Exception
block|{
comment|// TODO after merging is implemented
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|spacePreservesImports
parameter_list|()
throws|throws
name|Exception
block|{
name|InputStream
name|content
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/ontologies/characters_all.owl"
argument_list|)
decl_stmt|;
name|URL
name|url
init|=
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/ontologies/characters_all.owl"
argument_list|)
decl_stmt|;
name|OWLOntologyManager
name|mgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
name|mgr
operator|.
name|addOntologyLoaderListener
argument_list|(
operator|new
name|OWLOntologyLoaderListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|startedLoadingOntology
parameter_list|(
name|LoadingStartedEvent
name|arg0
parameter_list|)
block|{}
annotation|@
name|Override
specifier|public
name|void
name|finishedLoadingOntology
parameter_list|(
name|LoadingFinishedEvent
name|arg0
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
operator|(
name|arg0
operator|.
name|isSuccessful
argument_list|()
condition|?
literal|"Loaded"
else|:
literal|"Failed"
operator|)
operator|+
operator|(
name|arg0
operator|.
name|isImported
argument_list|()
condition|?
literal|" imported "
else|:
literal|" "
operator|)
operator|+
literal|"ontology "
operator|+
name|arg0
operator|.
name|getDocumentIRI
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|mgr
operator|.
name|addIRIMapper
argument_list|(
operator|new
name|PhonyIRIMapper
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|url
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|OntologyInputSource
argument_list|<
name|OWLOntology
argument_list|>
name|src
init|=
operator|new
name|ParentPathInputSource
argument_list|(
name|f
argument_list|,
name|mgr
argument_list|)
decl_stmt|;
comment|// OntologyInputSource<OWLOntology> src = new RootOntologyIRISource(IRI.create(f), mgr);
comment|// OntologyInputSource<OWLOntology> src = new OntologyContentInputSource(content,mgr);
name|OWLOntology
name|original
init|=
name|src
operator|.
name|getRootOntology
argument_list|()
decl_stmt|;
name|OntologySpace
name|spc
init|=
operator|new
name|CustomOntologySpaceImpl
argument_list|(
literal|"Test"
argument_list|,
name|scopeNs
argument_list|,
name|ontologyProvider
argument_list|)
decl_stmt|;
name|spc
operator|.
name|addOntology
argument_list|(
name|src
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

