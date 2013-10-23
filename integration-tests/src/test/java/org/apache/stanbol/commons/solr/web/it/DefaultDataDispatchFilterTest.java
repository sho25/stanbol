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
name|commons
operator|.
name|solr
operator|.
name|web
operator|.
name|it
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
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|ClientProtocolException
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
name|DefaultDataDispatchFilterTest
extends|extends
name|SolrDispatchFilterComponentTestBase
block|{
comment|/**      * the prefix of the managed solr server (part of the config for the launchers)      */
specifier|private
specifier|static
specifier|final
name|String
name|PREFIX
init|=
literal|"/solr/default/"
decl_stmt|;
comment|/**      * the name of the default data index (part of the data.defaultdata bundle)      */
specifier|private
specifier|static
specifier|final
name|String
name|CORE_NAME
init|=
literal|"dbpedia"
decl_stmt|;
specifier|public
name|DefaultDataDispatchFilterTest
parameter_list|()
block|{
name|super
argument_list|(
name|PREFIX
argument_list|,
name|CORE_NAME
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testSimpleSolrSelect
parameter_list|()
throws|throws
name|ClientProtocolException
throws|,
name|IOException
block|{
name|executor
operator|.
name|execute
argument_list|(
name|builder
operator|.
name|buildPostRequest
argument_list|(
name|getCorePath
argument_list|()
operator|+
literal|"select?q="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
literal|"@en/rdfs\\:label/:Paris"
argument_list|,
literal|"UTF8"
argument_list|)
argument_list|)
argument_list|)
operator|.
name|assertStatus
argument_list|(
literal|200
argument_list|)
operator|.
name|assertContentRegexp
argument_list|(
literal|"http://dbpedia.org/resource/Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Saint-Germain_F.C."
argument_list|,
literal|"http://dbpedia.org/resource/University_of_Paris"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Opera"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_Hilton"
argument_list|,
literal|"http://dbpedia.org/resource/Paris_M%C3%A9tro"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

