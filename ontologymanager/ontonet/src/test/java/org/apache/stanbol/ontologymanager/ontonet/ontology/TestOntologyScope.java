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
name|ontology
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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNull
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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
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
name|api
operator|.
name|DuplicateIDException
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
name|BlankOntologySource
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
name|RootOntologySource
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
name|api
operator|.
name|ontology
operator|.
name|OntologyScopeFactory
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
name|ScopeRegistry
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
name|ontology
operator|.
name|CoreOntologySpaceImpl
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
name|ontology
operator|.
name|CustomOntologySpaceImpl
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
name|ontology
operator|.
name|OntologyScopeFactoryImpl
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
name|OWLOntologyManagerFactory
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
name|OWLOntologyCreationException
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

begin_class
specifier|public
class|class
name|TestOntologyScope
block|{
specifier|public
specifier|static
name|IRI
name|baseIri
init|=
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|PEANUTS_MAIN_BASE
argument_list|)
decl_stmt|,
name|baseIri2
init|=
name|IRI
operator|.
name|create
argument_list|(
name|Constants
operator|.
name|PEANUTS_MINOR_BASE
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|String
name|scopeIdBlank
init|=
literal|"WackyRaces"
decl_stmt|,
name|scopeId1
init|=
literal|"Peanuts"
decl_stmt|,
name|scopeId2
init|=
literal|"CalvinAndHobbes"
decl_stmt|;
comment|/**      * An ontology scope that initially contains no ontologies, and is rebuilt from scratch before each test      * method.      */
specifier|private
specifier|static
name|OntologyScope
name|blankScope
decl_stmt|;
specifier|private
specifier|static
name|OntologyScopeFactory
name|factory
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|ONManager
name|onm
decl_stmt|;
specifier|private
specifier|static
name|OntologyInputSource
name|src1
init|=
literal|null
decl_stmt|,
name|src2
init|=
literal|null
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
comment|// An ONManagerImpl with no store and a set namespace.
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|onmconf
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
name|onmconf
operator|.
name|put
argument_list|(
name|ONManager
operator|.
name|ONTOLOGY_NETWORK_NS
argument_list|,
literal|"http://stanbol.apache.org/scope/"
argument_list|)
expr_stmt|;
comment|// The same hashtable can be recycled for the offline configuration.
name|onm
operator|=
operator|new
name|ONManagerImpl
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
operator|new
name|OfflineConfigurationImpl
argument_list|(
name|onmconf
argument_list|)
argument_list|,
name|onmconf
argument_list|)
expr_stmt|;
name|factory
operator|=
name|onm
operator|.
name|getOntologyScopeFactory
argument_list|()
expr_stmt|;
if|if
condition|(
name|factory
operator|==
literal|null
condition|)
name|fail
argument_list|(
literal|"Could not instantiate ontology space factory"
argument_list|)
expr_stmt|;
name|OWLOntologyManager
name|mgr
init|=
name|OWLOntologyManagerFactory
operator|.
name|createOWLOntologyManager
argument_list|(
literal|null
argument_list|)
decl_stmt|;
try|try
block|{
name|src1
operator|=
operator|new
name|RootOntologySource
argument_list|(
name|mgr
operator|.
name|createOntology
argument_list|(
name|baseIri
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|src2
operator|=
operator|new
name|RootOntologySource
argument_list|(
name|mgr
operator|.
name|createOntology
argument_list|(
name|baseIri2
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Could not setup ontology with base IRI "
operator|+
name|Constants
operator|.
name|PEANUTS_MAIN_BASE
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Before
specifier|public
name|void
name|cleanup
parameter_list|()
throws|throws
name|DuplicateIDException
block|{
if|if
condition|(
name|factory
operator|!=
literal|null
condition|)
name|blankScope
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeIdBlank
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests that a scope is generated with the expected identifiers for both itself and its core and custom      * spaces.      */
annotation|@
name|Test
specifier|public
name|void
name|testIdentifiers
parameter_list|()
throws|throws
name|Exception
block|{
name|OntologyScope
name|shouldBeNull
init|=
literal|null
decl_stmt|,
name|shouldBeNotNull
init|=
literal|null
decl_stmt|;
comment|/* First test scope identifiers. */
comment|// Null identifier (invalid)
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
literal|null
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite null scope identifier."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Slash in identifier (invalid)
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
literal|"a0/b1"
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite slash in scope identifier."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|/* Now test namespaces. */
comment|// Null namespace (invalid).
name|factory
operator|.
name|setNamespace
argument_list|(
literal|null
argument_list|)
expr_stmt|;
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeIdBlank
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite null OntoNet namespace."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Namespace with query (invalid).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology/?query=true"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeIdBlank
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite query in OntoNet namespace."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Namespace with fragment (invalid).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology#fragment"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeIdBlank
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite fragment in OntoNet namespace."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Namespace ending with hash (invalid).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology#"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|shouldBeNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeIdBlank
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Expected IllegalArgumentException not thrown despite fragment in OntoNet namespace."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{}
name|assertNull
argument_list|(
name|shouldBeNull
argument_list|)
expr_stmt|;
comment|// Namespace ending with slash (valid).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology/"
argument_list|)
argument_list|)
expr_stmt|;
name|shouldBeNotNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeIdBlank
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|shouldBeNotNull
argument_list|)
expr_stmt|;
name|shouldBeNotNull
operator|=
literal|null
expr_stmt|;
comment|// Namespace ending with neither (valid, should automatically add slash).
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://stanbol.apache.org/ontology"
argument_list|)
argument_list|)
expr_stmt|;
name|shouldBeNotNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeIdBlank
argument_list|,
operator|new
name|BlankOntologySource
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|shouldBeNotNull
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|shouldBeNotNull
operator|.
name|getNamespace
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Now set again the correct namespace.
name|factory
operator|.
name|setNamespace
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|onm
operator|.
name|getOntologyNetworkNamespace
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|shouldBeNotNull
operator|=
literal|null
expr_stmt|;
try|try
block|{
name|shouldBeNotNull
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeId1
argument_list|,
name|src1
argument_list|,
name|src2
argument_list|)
expr_stmt|;
name|shouldBeNotNull
operator|.
name|setUp
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Unexpected DuplicateIDException caught when creating scope "
operator|+
literal|"with non-null parameters in a non-registered environment."
argument_list|)
expr_stmt|;
block|}
name|boolean
name|condition
init|=
name|shouldBeNotNull
operator|.
name|getID
argument_list|()
operator|.
name|equals
argument_list|(
name|scopeId1
argument_list|)
decl_stmt|;
name|condition
operator|&=
name|shouldBeNotNull
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getID
argument_list|()
operator|.
name|equals
argument_list|(
name|scopeId1
operator|+
literal|"/"
operator|+
name|CoreOntologySpaceImpl
operator|.
name|SUFFIX
argument_list|)
expr_stmt|;
name|condition
operator|&=
name|shouldBeNotNull
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getID
argument_list|()
operator|.
name|equals
argument_list|(
name|scopeId1
operator|+
literal|"/"
operator|+
name|CustomOntologySpaceImpl
operator|.
name|SUFFIX
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|condition
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests that creating an ontology scope with null identifier fails to generate the scope at all.      */
annotation|@
name|Test
specifier|public
name|void
name|testNullScopeCreation
parameter_list|()
block|{
name|OntologyScope
name|scope
init|=
literal|null
decl_stmt|;
try|try
block|{
name|scope
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Unexpected DuplicateIDException caught while testing scope creation"
operator|+
literal|" with null parameters."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
comment|// Expected behaviour.
block|}
name|assertNull
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests that an ontology scope is correctly generated with both a core space and a custom space. The      * scope is set up but not registered.      */
annotation|@
name|Test
specifier|public
name|void
name|testScopeSetup
parameter_list|()
block|{
name|OntologyScope
name|scope
init|=
literal|null
decl_stmt|;
try|try
block|{
name|scope
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeId1
argument_list|,
name|src1
argument_list|,
name|src2
argument_list|)
expr_stmt|;
name|scope
operator|.
name|setUp
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Unexpected DuplicateIDException was caught while testing scope "
operator|+
name|e
operator|.
name|getDulicateID
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertNotNull
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests that an ontology scope is correctly generated even when missing a custom space. The scope is set      * up but not registered.      */
annotation|@
name|Test
specifier|public
name|void
name|testScopeSetupNoCustom
parameter_list|()
block|{
name|OntologyScope
name|scope
init|=
literal|null
decl_stmt|;
try|try
block|{
name|scope
operator|=
name|factory
operator|.
name|createOntologyScope
argument_list|(
name|scopeId2
argument_list|,
name|src1
argument_list|)
expr_stmt|;
name|scope
operator|.
name|setUp
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Duplicate ID exception caught for scope iri "
operator|+
name|src1
argument_list|)
expr_stmt|;
block|}
name|assertTrue
argument_list|(
name|scope
operator|!=
literal|null
operator|&&
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|!=
literal|null
operator|&&
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testScopesRendering
parameter_list|()
block|{
name|ScopeRegistry
name|reg
init|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
decl_stmt|;
name|OntologyScopeFactoryImpl
name|scf
init|=
operator|new
name|OntologyScopeFactoryImpl
argument_list|(
name|reg
argument_list|,
name|onm
operator|.
name|getOntologyScopeFactory
argument_list|()
operator|.
name|getNamespace
argument_list|()
argument_list|,
name|onm
operator|.
name|getOntologySpaceFactory
argument_list|()
argument_list|)
decl_stmt|;
name|OntologyScope
name|scope
init|=
literal|null
decl_stmt|,
name|scope2
init|=
literal|null
decl_stmt|;
try|try
block|{
name|scope
operator|=
name|scf
operator|.
name|createOntologyScope
argument_list|(
name|scopeId1
argument_list|,
name|src1
argument_list|,
name|src2
argument_list|)
expr_stmt|;
name|scope2
operator|=
name|scf
operator|.
name|createOntologyScope
argument_list|(
name|scopeId2
argument_list|,
name|src2
argument_list|)
expr_stmt|;
name|scope
operator|.
name|setUp
argument_list|()
expr_stmt|;
name|reg
operator|.
name|registerScope
argument_list|(
name|scope
argument_list|)
expr_stmt|;
name|scope2
operator|.
name|setUp
argument_list|()
expr_stmt|;
name|reg
operator|.
name|registerScope
argument_list|(
name|scope2
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DuplicateIDException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Duplicate ID exception caught on "
operator|+
name|e
operator|.
name|getDulicateID
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// System.err.println(new ScopeSetRenderer().getScopesAsRDF(reg
comment|// .getRegisteredScopes()));
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

