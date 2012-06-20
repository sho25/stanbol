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
name|commons
operator|.
name|owl
operator|.
name|util
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
name|MGraph
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
name|serializedform
operator|.
name|ParsingProvider
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
name|parser
operator|.
name|JenaParserProvider
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

begin_class
specifier|public
class|class
name|TestOWLUtils
block|{
annotation|@
name|BeforeClass
specifier|public
specifier|static
name|void
name|setupTests
parameter_list|()
throws|throws
name|Exception
block|{
name|TcManager
operator|.
name|getInstance
argument_list|()
operator|.
name|addWeightedTcProvider
argument_list|(
operator|new
name|SimpleTcProvider
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|ParsingProvider
name|pp
init|=
operator|new
name|JenaParserProvider
argument_list|()
decl_stmt|;
specifier|private
name|UriRef
name|uri
init|=
operator|new
name|UriRef
argument_list|(
literal|"ontonet:http://stanbol.apache.org/prova"
argument_list|)
decl_stmt|;
annotation|@
name|Test
specifier|public
name|void
name|namedUriRef
parameter_list|()
throws|throws
name|Exception
block|{
name|InputStream
name|inputStream
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/owl/maincharacters.owl"
argument_list|)
decl_stmt|;
name|MGraph
name|mg
init|=
name|TcManager
operator|.
name|getInstance
argument_list|()
operator|.
name|createMGraph
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|pp
operator|.
name|parse
argument_list|(
name|mg
argument_list|,
name|inputStream
argument_list|,
literal|"application/rdf+xml"
argument_list|,
name|uri
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|mg
operator|.
name|getGraph
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|namelessUriRef
parameter_list|()
throws|throws
name|Exception
block|{
name|InputStream
name|inputStream
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"/owl/nameless_ontology.owl"
argument_list|)
decl_stmt|;
name|MGraph
name|mg
init|=
name|TcManager
operator|.
name|getInstance
argument_list|()
operator|.
name|createMGraph
argument_list|(
name|uri
argument_list|)
decl_stmt|;
name|pp
operator|.
name|parse
argument_list|(
name|mg
argument_list|,
name|inputStream
argument_list|,
literal|"application/rdf+xml"
argument_list|,
name|uri
argument_list|)
expr_stmt|;
comment|// No longer null!
name|assertNotNull
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|mg
operator|.
name|getGraph
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
specifier|public
name|void
name|reset
parameter_list|()
throws|throws
name|Exception
block|{
name|TcManager
operator|.
name|getInstance
argument_list|()
operator|.
name|deleteTripleCollection
argument_list|(
name|uri
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

