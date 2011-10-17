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
name|session
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
name|Dictionary
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
name|Set
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
name|OntologySpaceFactory
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
name|api
operator|.
name|session
operator|.
name|NonReferenceableSessionException
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
name|session
operator|.
name|Session
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
name|session
operator|.
name|Session
operator|.
name|State
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
name|session
operator|.
name|SessionManager
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
name|TestSessions
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
name|scopeId1
init|=
literal|"Ranma12"
decl_stmt|,
name|scopeId2
init|=
literal|"HokutoNoKen"
decl_stmt|,
name|scopeId3
init|=
literal|"Doraemon"
decl_stmt|;
specifier|private
specifier|static
name|OntologyScopeFactory
name|scopeFactory
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|ScopeRegistry
name|scopeRegistry
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|SessionManager
name|sesmgr
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|OntologySpaceFactory
name|spaceFactory
init|=
literal|null
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
comment|// An ONManagerImpl with no store and default settings
name|ONManager
name|onm
init|=
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
decl_stmt|;
name|sesmgr
operator|=
name|onm
operator|.
name|getSessionManager
argument_list|()
expr_stmt|;
name|scopeFactory
operator|=
name|onm
operator|.
name|getOntologyScopeFactory
argument_list|()
expr_stmt|;
name|spaceFactory
operator|=
name|onm
operator|.
name|getOntologySpaceFactory
argument_list|()
expr_stmt|;
name|scopeRegistry
operator|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
expr_stmt|;
name|assertNotNull
argument_list|(
name|spaceFactory
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|scopeFactory
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
name|Test
specifier|public
name|void
name|testCreateSessionSpaceManual
parameter_list|()
throws|throws
name|Exception
block|{
name|OntologyScope
name|scope
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|// we don't register it
name|scope
operator|=
name|scopeFactory
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
name|getDuplicateID
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|assertNotNull
argument_list|(
name|scope
argument_list|)
expr_stmt|;
name|Session
name|ses
init|=
name|sesmgr
operator|.
name|createSession
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|scope
operator|.
name|getSessionSpaces
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|scope
operator|.
name|addSessionSpace
argument_list|(
name|spaceFactory
operator|.
name|createSessionOntologySpace
argument_list|(
name|scopeId1
argument_list|)
argument_list|,
name|ses
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|scope
operator|.
name|getSessionSpaces
argument_list|()
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
name|testCreateSessionSpaceAutomatic
parameter_list|()
block|{
name|OntologyScope
name|scope1
init|=
literal|null
decl_stmt|,
name|scope2
init|=
literal|null
decl_stmt|,
name|scope3
init|=
literal|null
decl_stmt|;
try|try
block|{
name|scope1
operator|=
name|scopeFactory
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
name|scopeRegistry
operator|.
name|registerScope
argument_list|(
name|scope1
argument_list|)
expr_stmt|;
name|scope2
operator|=
name|scopeFactory
operator|.
name|createOntologyScope
argument_list|(
name|scopeId2
argument_list|,
name|src2
argument_list|,
name|src1
argument_list|)
expr_stmt|;
name|scopeRegistry
operator|.
name|registerScope
argument_list|(
name|scope2
argument_list|)
expr_stmt|;
name|scope3
operator|=
name|scopeFactory
operator|.
name|createOntologyScope
argument_list|(
name|scopeId3
argument_list|,
name|src2
argument_list|,
name|src2
argument_list|)
expr_stmt|;
name|scopeRegistry
operator|.
name|registerScope
argument_list|(
name|scope3
argument_list|)
expr_stmt|;
comment|// We do all activations after registering, otherwise the component
comment|// property value will override these activations.
name|scopeRegistry
operator|.
name|setScopeActive
argument_list|(
name|scopeId1
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|scopeRegistry
operator|.
name|setScopeActive
argument_list|(
name|scopeId2
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|scopeRegistry
operator|.
name|setScopeActive
argument_list|(
name|scopeId3
argument_list|,
literal|true
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
literal|"Unexpected DuplicateIDException was caught while testing scope "
operator|+
name|e
operator|.
name|getDuplicateID
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Session
name|ses
init|=
name|sesmgr
operator|.
name|createSession
argument_list|()
decl_stmt|;
name|String
name|sesid
init|=
name|ses
operator|.
name|getID
argument_list|()
decl_stmt|;
comment|// FIXME replace with proper tests
comment|// assertFalse(scope1.getSessionSpaces().isEmpty());
comment|// assertNotNull(scope1.getSessionSpace(sesid));
comment|// assertFalse(scope3.getSessionSpaces().isEmpty());
comment|// assertNull(scope2.getSessionSpace(sesid));
comment|// assertNotNull(scope3.getSessionSpace(sesid));
block|}
annotation|@
name|Test
specifier|public
name|void
name|testRegisterSession
parameter_list|()
block|{
name|int
name|before
init|=
name|sesmgr
operator|.
name|getRegisteredSessionIDs
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
name|Session
name|ses
init|=
name|sesmgr
operator|.
name|createSession
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|ses
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|before
operator|+
literal|1
argument_list|,
name|sesmgr
operator|.
name|getRegisteredSessionIDs
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSessionCreationDestruction
parameter_list|()
block|{
name|int
name|size
init|=
literal|100
decl_stmt|;
name|int
name|initialSize
init|=
name|sesmgr
operator|.
name|getRegisteredSessionIDs
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Session
argument_list|>
name|sessions
init|=
operator|new
name|HashSet
argument_list|<
name|Session
argument_list|>
argument_list|()
decl_stmt|;
comment|// Create and open many sessions.
synchronized|synchronized
init|(
name|sesmgr
init|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|size
condition|;
name|i
operator|++
control|)
block|{
name|Session
name|ses
init|=
name|sesmgr
operator|.
name|createSession
argument_list|()
decl_stmt|;
try|try
block|{
name|ses
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NonReferenceableSessionException
name|e
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Test method tried to open nonreferenceable session."
argument_list|)
expr_stmt|;
block|}
name|sessions
operator|.
name|add
argument_list|(
name|ses
argument_list|)
expr_stmt|;
block|}
comment|// Check that 500 sessions have been created
name|assertEquals
argument_list|(
name|initialSize
operator|+
name|size
argument_list|,
name|sesmgr
operator|.
name|getRegisteredSessionIDs
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|boolean
name|open
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Session
name|ses
range|:
name|sessions
control|)
name|open
operator|&=
name|ses
operator|.
name|getSessionState
argument_list|()
operator|==
name|State
operator|.
name|ACTIVE
expr_stmt|;
comment|// Check that all created sessions have been opened
name|assertTrue
argument_list|(
name|open
argument_list|)
expr_stmt|;
comment|// Kill 'em all, to quote Metallica
synchronized|synchronized
init|(
name|sesmgr
init|)
block|{
for|for
control|(
name|Session
name|ses
range|:
name|sessions
control|)
name|sesmgr
operator|.
name|destroySession
argument_list|(
name|ses
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|initialSize
argument_list|,
name|sesmgr
operator|.
name|getRegisteredSessionIDs
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Check that they are all zombies
name|boolean
name|zombi
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Session
name|ses
range|:
name|sessions
control|)
name|zombi
operator|&=
name|ses
operator|.
name|getSessionState
argument_list|()
operator|==
name|State
operator|.
name|ZOMBIE
expr_stmt|;
name|assertTrue
argument_list|(
name|zombi
argument_list|)
expr_stmt|;
comment|// Try to resurrect them (hopefully failing)
name|boolean
name|resurrect
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Session
name|ses
range|:
name|sessions
control|)
try|try
block|{
name|ses
operator|.
name|open
argument_list|()
expr_stmt|;
name|resurrect
operator||=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NonReferenceableSessionException
name|e
parameter_list|)
block|{
name|resurrect
operator||=
literal|false
expr_stmt|;
continue|continue;
block|}
name|assertFalse
argument_list|(
name|resurrect
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

