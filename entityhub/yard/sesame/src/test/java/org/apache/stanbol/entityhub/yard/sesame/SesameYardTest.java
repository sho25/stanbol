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
name|entityhub
operator|.
name|yard
operator|.
name|sesame
package|;
end_package

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
name|Reference
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
name|rdf
operator|.
name|RdfResourceEnum
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
name|yard
operator|.
name|Yard
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
name|yard
operator|.
name|YardException
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
name|yard
operator|.
name|YardTest
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
name|yard
operator|.
name|sesame
operator|.
name|SesameYard
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
name|yard
operator|.
name|sesame
operator|.
name|SesameYardConfig
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|AfterClass
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
name|openrdf
operator|.
name|repository
operator|.
name|RepositoryException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|repository
operator|.
name|sail
operator|.
name|SailRepository
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openrdf
operator|.
name|sail
operator|.
name|memory
operator|.
name|MemoryStore
import|;
end_import

begin_class
specifier|public
class|class
name|SesameYardTest
extends|extends
name|YardTest
block|{
specifier|private
specifier|static
name|SailRepository
name|repo
decl_stmt|;
specifier|private
specifier|static
name|SesameYard
name|yard
decl_stmt|;
annotation|@
name|BeforeClass
specifier|public
specifier|static
specifier|final
name|void
name|initYard
parameter_list|()
throws|throws
name|RepositoryException
block|{
name|SesameYardConfig
name|config
init|=
operator|new
name|SesameYardConfig
argument_list|(
literal|"testYardId"
argument_list|)
decl_stmt|;
name|config
operator|.
name|setName
argument_list|(
literal|"Sesame Yard Test"
argument_list|)
expr_stmt|;
name|config
operator|.
name|setDescription
argument_list|(
literal|"The Sesame Yard instance used to execute the Unit Tests defined for the Yard Interface"
argument_list|)
expr_stmt|;
name|repo
operator|=
operator|new
name|SailRepository
argument_list|(
operator|new
name|MemoryStore
argument_list|()
argument_list|)
expr_stmt|;
name|repo
operator|.
name|initialize
argument_list|()
expr_stmt|;
name|yard
operator|=
operator|new
name|SesameYard
argument_list|(
name|repo
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|Yard
name|getYard
parameter_list|()
block|{
return|return
name|yard
return|;
block|}
comment|/**      * The Clerezza Yard uses the Statement<br>      *<code>representationId -> rdf:type -> Representation</code><br>      * to identify that an UriRef in the RDF graph (MGraph) represents a      * Representation. This Triple is added when a Representation is stored and      * removed if retrieved from the Yard.<p>      * This tests if this functions as expected      * @throws YardException      */
annotation|@
name|Test
specifier|public
name|void
name|testRemovalOfTypeRepresentationStatement
parameter_list|()
throws|throws
name|YardException
block|{
name|Yard
name|yard
init|=
name|getYard
argument_list|()
decl_stmt|;
name|ValueFactory
name|vf
init|=
name|yard
operator|.
name|getValueFactory
argument_list|()
decl_stmt|;
name|Reference
name|representationType
init|=
name|vf
operator|.
name|createReference
argument_list|(
name|RdfResourceEnum
operator|.
name|Representation
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|Representation
name|test
init|=
name|create
argument_list|()
decl_stmt|;
comment|//the rdf:type Representation MUST NOT be within the Representation
name|Assert
operator|.
name|assertFalse
argument_list|(
name|test
operator|.
name|get
argument_list|(
name|NamespaceEnum
operator|.
name|rdf
operator|+
literal|"type"
argument_list|)
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|//now add the statement and see if an IllegalStateException is thrown
comment|/*          * The triple within this Statement is internally used to "mark" the          * URI of the Representation as           */
name|test
operator|.
name|add
argument_list|(
name|NamespaceEnum
operator|.
name|rdf
operator|+
literal|"type"
argument_list|,
name|representationType
argument_list|)
expr_stmt|;
block|}
comment|/**      * This Method removes all Representations create via {@link #create()} or      * {@link #create(String, boolean)} from the tested {@link Yard}.      * It also removes all Representations there ID was manually added to the      * {@link #representationIds} list.      * @throws RepositoryException       */
annotation|@
name|AfterClass
specifier|public
specifier|static
specifier|final
name|void
name|clearUpRepresentations
parameter_list|()
throws|throws
name|YardException
throws|,
name|RepositoryException
block|{
name|yard
operator|.
name|remove
argument_list|(
name|representationIds
argument_list|)
expr_stmt|;
name|yard
operator|.
name|close
argument_list|()
expr_stmt|;
name|repo
operator|.
name|shutDown
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit
