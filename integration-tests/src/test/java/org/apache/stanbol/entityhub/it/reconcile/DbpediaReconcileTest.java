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
name|it
operator|.
name|reconcile
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
name|util
operator|.
name|Collections
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
name|test
operator|.
name|it
operator|.
name|EntityhubTestBase
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
name|DbpediaReconcileTest
extends|extends
name|EntityhubTestBase
block|{
specifier|public
specifier|static
specifier|final
name|String
name|SIMPLE_QUERIES
init|=
literal|"{\"q0\":{\"query\":\"Paris\",\"limit\":3},"
operator|+
literal|"\"q1\":{\"query\":\"London\",\"limit\":3}}"
decl_stmt|;
specifier|public
name|DbpediaReconcileTest
parameter_list|()
block|{
name|super
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"dbpedia"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests if adding a Standard Reconciliation services works.      * @throws IOException      */
annotation|@
name|Test
specifier|public
name|void
name|testInitialization
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|callback
init|=
literal|"jsonp1361172630576"
decl_stmt|;
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
literal|"/entityhub/site/dbpedia/reconcile"
argument_list|,
literal|"callback"
argument_list|,
name|callback
argument_list|)
argument_list|)
comment|//callback("name":"{human readable name}")
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentContains
argument_list|(
name|callback
operator|+
literal|"({\"name\":\""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSimpleReconciliation
parameter_list|()
throws|throws
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildGetRequest
argument_list|(
literal|"/entityhub/site/dbpedia/reconcile"
argument_list|,
literal|"queries"
argument_list|,
name|SIMPLE_QUERIES
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentContains
argument_list|(
literal|"\"q1\":{\"result\":"
argument_list|,
literal|"\"q0\":{\"result\":"
argument_list|,
literal|"\"id\":\"http:\\/\\/dbpedia.org\\/resource\\/Paris\""
argument_list|,
literal|"\"name\":\"Paris\""
argument_list|,
literal|"\"id\":\"http:\\/\\/dbpedia.org\\/resource\\/London\""
argument_list|,
literal|"\"name\":\"London\""
argument_list|,
literal|"\"type\":[\""
argument_list|,
literal|"\"http:\\/\\/dbpedia.org\\/ontology\\/Place\""
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

