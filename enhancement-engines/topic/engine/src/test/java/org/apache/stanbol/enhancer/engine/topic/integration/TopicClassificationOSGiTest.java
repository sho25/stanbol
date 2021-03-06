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
name|enhancer
operator|.
name|engine
operator|.
name|topic
operator|.
name|integration
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|CoreOptions
operator|.
name|equinox
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|CoreOptions
operator|.
name|felix
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|CoreOptions
operator|.
name|junitBundles
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|CoreOptions
operator|.
name|mavenBundle
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|CoreOptions
operator|.
name|options
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|CoreOptions
operator|.
name|systemProperty
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
name|javax
operator|.
name|inject
operator|.
name|Inject
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
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
name|enhancer
operator|.
name|engine
operator|.
name|topic
operator|.
name|TopicClassificationEngine
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
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
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|Option
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
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
name|cm
operator|.
name|Configuration
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
name|cm
operator|.
name|ConfigurationAdmin
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
name|http
operator|.
name|HttpService
import|;
end_import

begin_comment
comment|// Disabled integration test because SCR configuration factory init is crashing
end_comment

begin_comment
comment|//@RunWith(JUnit4TestRunner.class)
end_comment

begin_comment
comment|//@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
end_comment

begin_class
specifier|public
class|class
name|TopicClassificationOSGiTest
block|{
annotation|@
name|Inject
name|BundleContext
name|context
decl_stmt|;
comment|// inject http service to ensure that jetty init thread is finished before tearing down otherwise the test
comment|// harness will crash
annotation|@
name|Inject
name|HttpService
name|httpService
decl_stmt|;
annotation|@
name|Before
specifier|public
name|void
name|registerSolrCore
parameter_list|()
throws|throws
name|Exception
block|{
comment|// TODO
block|}
annotation|@
name|org
operator|.
name|ops4j
operator|.
name|pax
operator|.
name|exam
operator|.
name|junit
operator|.
name|Configuration
argument_list|()
specifier|public
name|Option
index|[]
name|config
parameter_list|()
block|{
return|return
name|options
argument_list|(
name|systemProperty
argument_list|(
literal|"org.osgi.service.http.port"
argument_list|)
operator|.
name|value
argument_list|(
literal|"8181"
argument_list|)
argument_list|,
name|systemProperty
argument_list|(
literal|"org.ops4j.pax.logging.DefaultServiceLog.level"
argument_list|)
operator|.
name|value
argument_list|(
literal|"WARN"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"commons-codec"
argument_list|,
literal|"commons-codec"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.httpcomponents"
argument_list|,
literal|"httpcore-osgi"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"commons-io"
argument_list|,
literal|"commons-io"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
comment|// for some reason: versionAsInProject does not work for the following:
name|mavenBundle
argument_list|(
literal|"org.apache.clerezza.ext"
argument_list|,
literal|"com.ibm.icu"
argument_list|)
operator|.
name|version
argument_list|(
literal|"0.5-incubating-SNAPSHOT"
argument_list|)
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.wymiwyg"
argument_list|,
literal|"wymiwyg-commons-core"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.commons"
argument_list|,
literal|"commons-compress"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.felix"
argument_list|,
literal|"org.apache.felix.configadmin"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.felix"
argument_list|,
literal|"org.apache.felix.http.jetty"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.felix"
argument_list|,
literal|"org.apache.felix.scr"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.stanbol"
argument_list|,
literal|"org.apache.stanbol.commons.stanboltools.datafileprovider"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.stanbol"
argument_list|,
literal|"org.apache.stanbol.commons.solr.core"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.stanbol"
argument_list|,
literal|"org.apache.stanbol.commons.solr.managed"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.clerezza"
argument_list|,
literal|"utils"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.clerezza"
argument_list|,
literal|"rdf.core"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|mavenBundle
argument_list|(
literal|"org.apache.stanbol"
argument_list|,
literal|"org.apache.stanbol.enhancer.servicesapi"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
comment|// TODO: instead of deploying a previous version of the bundle built by maven, find a way to wrap
comment|// the engine class as a bundle directly in this test runtime.
name|mavenBundle
argument_list|(
literal|"org.apache.stanbol"
argument_list|,
literal|"org.apache.stanbol.enhancer.engine.topic"
argument_list|)
operator|.
name|versionAsInProject
argument_list|()
argument_list|,
name|junitBundles
argument_list|()
argument_list|,
name|felix
argument_list|()
argument_list|,
name|equinox
argument_list|()
argument_list|)
return|;
comment|// Note: the equinox tests can only be run if the test container is switched to the slower non-native,
comment|// implementation
block|}
comment|// Disabled integration test because SCR configuration factory init is crashing
comment|// @Test
specifier|public
name|void
name|testTopicClassification
parameter_list|()
throws|throws
name|Exception
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Running test on bundle: "
operator|+
name|context
operator|.
name|getBundle
argument_list|()
argument_list|)
expr_stmt|;
name|ServiceReference
name|reference
init|=
name|context
operator|.
name|getServiceReference
argument_list|(
name|ConfigurationAdmin
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|ConfigurationAdmin
name|configAdmin
init|=
operator|(
name|ConfigurationAdmin
operator|)
name|context
operator|.
name|getService
argument_list|(
name|reference
argument_list|)
decl_stmt|;
name|Configuration
name|config
init|=
name|configAdmin
operator|.
name|createFactoryConfiguration
argument_list|(
name|TopicClassificationEngine
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|Dictionary
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parameters
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|parameters
operator|.
name|put
argument_list|(
name|EnhancementEngine
operator|.
name|PROPERTY_NAME
argument_list|,
literal|"testclassifier"
argument_list|)
expr_stmt|;
comment|// TODO: put the coreId of the solr server registered in @Before
name|config
operator|.
name|update
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
comment|// TODO: use a service track to wait for the registration of the service
name|ServiceReference
name|topicEngineReference
init|=
name|context
operator|.
name|getServiceReference
argument_list|(
name|TopicClassificationEngine
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|TestCase
operator|.
name|assertNotNull
argument_list|(
name|topicEngineReference
argument_list|)
expr_stmt|;
name|TopicClassificationEngine
name|engine
init|=
operator|(
name|TopicClassificationEngine
operator|)
name|context
operator|.
name|getService
argument_list|(
name|topicEngineReference
argument_list|)
decl_stmt|;
name|TestCase
operator|.
name|assertNotNull
argument_list|(
name|engine
argument_list|)
expr_stmt|;
comment|// TODO: test classification here
block|}
block|}
end_class

end_unit

