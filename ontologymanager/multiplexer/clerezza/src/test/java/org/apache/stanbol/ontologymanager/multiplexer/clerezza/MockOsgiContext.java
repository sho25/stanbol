begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|multiplexer
operator|.
name|clerezza
package|;
end_package

begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
name|TcProvider
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
name|serializedform
operator|.
name|Serializer
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
name|jena
operator|.
name|serializer
operator|.
name|JenaSerializerProvider
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
name|rdfjson
operator|.
name|parser
operator|.
name|RdfJsonParsingProvider
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
name|rdfjson
operator|.
name|serializer
operator|.
name|RdfJsonSerializingProvider
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
name|core
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
name|core
operator|.
name|scope
operator|.
name|ScopeManagerImpl
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
name|multiplexer
operator|.
name|clerezza
operator|.
name|collector
operator|.
name|ClerezzaCollectorFactory
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
name|multiplexer
operator|.
name|clerezza
operator|.
name|ontology
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
name|ontologymanager
operator|.
name|multiplexer
operator|.
name|clerezza
operator|.
name|session
operator|.
name|SessionManagerImpl
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
name|servicesapi
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
name|servicesapi
operator|.
name|ontology
operator|.
name|OntologyProvider
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
name|servicesapi
operator|.
name|scope
operator|.
name|ScopeManager
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
name|servicesapi
operator|.
name|session
operator|.
name|SessionManager
import|;
end_import

begin_comment
comment|/**  * Utility class that provides some objects that would otherwise be provided by SCR reference in an OSGi  * environment. Can be used to simulate OSGi in unit tests.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|MockOsgiContext
block|{
specifier|private
specifier|static
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
decl_stmt|;
specifier|public
specifier|static
name|OfflineConfiguration
name|offline
decl_stmt|;
specifier|public
specifier|static
name|ScopeManager
name|onManager
decl_stmt|;
specifier|public
specifier|static
name|OntologyProvider
argument_list|<
name|TcProvider
argument_list|>
name|ontologyProvider
decl_stmt|;
specifier|public
specifier|static
name|ClerezzaCollectorFactory
name|collectorfactory
decl_stmt|;
specifier|public
specifier|static
name|Parser
name|parser
init|=
name|Parser
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|Serializer
name|serializer
init|=
name|Serializer
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|SessionManager
name|sessionManager
decl_stmt|;
specifier|public
specifier|static
name|TcManager
name|tcManager
init|=
name|TcManager
operator|.
name|getInstance
argument_list|()
decl_stmt|;
static|static
block|{
name|config
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|OfflineConfiguration
operator|.
name|DEFAULT_NS
argument_list|,
literal|"http://stanbol.apache.org/test/"
argument_list|)
expr_stmt|;
name|config
operator|.
name|put
argument_list|(
name|SessionManager
operator|.
name|MAX_ACTIVE_SESSIONS
argument_list|,
literal|"-1"
argument_list|)
expr_stmt|;
name|offline
operator|=
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
expr_stmt|;
name|reset
argument_list|()
expr_stmt|;
block|}
comment|/**      * Sets up a new mock OSGi context and cleans all resources and components.      */
specifier|public
specifier|static
name|void
name|reset
parameter_list|()
block|{
comment|// reset Clerezza objects
name|tcManager
operator|=
operator|new
name|TcManager
argument_list|()
expr_stmt|;
name|tcManager
operator|.
name|addWeightedTcProvider
argument_list|(
operator|new
name|SimpleTcProvider
argument_list|()
argument_list|)
expr_stmt|;
comment|// reset Stanbol objects
name|ontologyProvider
operator|=
operator|new
name|ClerezzaOntologyProvider
argument_list|(
name|tcManager
argument_list|,
name|offline
argument_list|,
name|parser
argument_list|)
expr_stmt|;
name|collectorfactory
operator|=
operator|new
name|ClerezzaCollectorFactory
argument_list|(
name|ontologyProvider
argument_list|,
name|config
argument_list|)
expr_stmt|;
name|resetManagers
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|resetManagers
parameter_list|()
block|{
comment|// PersistentCollectorFactory factory = new ClerezzaCollectorFactory(ontologyProvider, config);
name|onManager
operator|=
operator|new
name|ScopeManagerImpl
argument_list|(
name|ontologyProvider
argument_list|,
name|offline
argument_list|,
name|collectorfactory
argument_list|,
name|collectorfactory
argument_list|,
name|config
argument_list|)
expr_stmt|;
name|sessionManager
operator|=
operator|new
name|SessionManagerImpl
argument_list|(
name|ontologyProvider
argument_list|,
name|offline
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

