begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one  * or more contributor license agreements.  See the NOTICE file  * distributed with this work for additional information  * regarding copyright ownership.  The ASF licenses this file  * to you under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance  * with the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing,  * software distributed under the License is distributed on an  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY  * KIND, either express or implied.  See the License for the  * specific language governing permissions and limitations  * under the License.  */
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
name|security
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|junit
operator|.
name|*
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
name|permissionadmin
operator|.
name|PermissionInfo
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
name|commons
operator|.
name|rdf
operator|.
name|ImmutableGraph
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
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
operator|.
name|simple
operator|.
name|SimpleGraph
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
name|stanbol
operator|.
name|commons
operator|.
name|security
operator|.
name|PermissionDefinitions
import|;
end_import

begin_comment
comment|/**  *   * @author clemens  */
end_comment

begin_class
specifier|public
class|class
name|PermissionDefinitionsTest
block|{
specifier|private
name|PermissionDefinitions
name|permissionDefinitions
decl_stmt|;
specifier|private
name|PermissionInfo
index|[]
name|allPermissions
decl_stmt|;
specifier|private
name|PermissionInfo
index|[]
name|nullPermission
decl_stmt|;
specifier|public
name|PermissionDefinitionsTest
parameter_list|()
block|{ 	}
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setUpClass
parameter_list|()
throws|throws
name|Exception
block|{ 	}
annotation|@
name|AfterClass
specifier|public
specifier|static
name|void
name|tearDownClass
parameter_list|()
throws|throws
name|Exception
block|{ 	}
annotation|@
name|Before
specifier|public
name|void
name|setUp
parameter_list|()
block|{
specifier|final
name|ImmutableGraph
name|graph
init|=
name|Parser
operator|.
name|getInstance
argument_list|()
operator|.
name|parse
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"systemgraph.nt"
argument_list|)
argument_list|,
literal|"text/rdf+n3"
argument_list|)
decl_stmt|;
name|this
operator|.
name|permissionDefinitions
operator|=
operator|new
name|PermissionDefinitions
argument_list|(
operator|new
name|SimpleGraph
argument_list|(
name|graph
operator|.
name|iterator
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|allPermissions
operator|=
operator|new
name|PermissionInfo
index|[]
block|{
operator|new
name|PermissionInfo
argument_list|(
literal|"(java.io.FilePermission \"file:///home/foo/-\" \"read,write,delete\")"
argument_list|)
block|,
operator|new
name|PermissionInfo
argument_list|(
literal|"(java.io.FilePermission \"file:///home/foo/*\" \"read,write\")"
argument_list|)
block|,
operator|new
name|PermissionInfo
argument_list|(
literal|"(java.io.FilePermission \"file:///home/*\" \"read,write\")"
argument_list|)
block|}
expr_stmt|;
name|this
operator|.
name|nullPermission
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testAllowedBundle
parameter_list|()
block|{
name|PermissionInfo
index|[]
name|permInfos
init|=
name|this
operator|.
name|permissionDefinitions
operator|.
name|retrievePermissions
argument_list|(
literal|"file:///home/foo/foobar/testbundle-1.0-SNAPSHOT.jar"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|PermissionInfo
argument_list|>
name|permInfoList
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|permInfos
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|PermissionInfo
argument_list|>
name|expectedPermInfos
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|allPermissions
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|permInfoList
operator|.
name|containsAll
argument_list|(
name|expectedPermInfos
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|expectedPermInfos
operator|.
name|containsAll
argument_list|(
name|permInfoList
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testUnknownBundle
parameter_list|()
block|{
name|Assert
operator|.
name|assertNotSame
argument_list|(
name|allPermissions
argument_list|,
name|this
operator|.
name|permissionDefinitions
operator|.
name|retrievePermissions
argument_list|(
literal|"file:///foo.jar"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|nullPermission
argument_list|,
name|this
operator|.
name|permissionDefinitions
operator|.
name|retrievePermissions
argument_list|(
literal|"file:///foo.jar"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

