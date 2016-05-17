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
name|engines
operator|.
name|celi
package|;
end_package

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
name|IRI
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
name|service
operator|.
name|component
operator|.
name|ComponentContext
import|;
end_import

begin_interface
specifier|public
interface|interface
name|CeliConstants
block|{
comment|/**      * Property used to provide the license key for the CELI service to all the      * CELI engines. Keys need to be configured in the form '{user-name}:{password}'<p>      * License keys are read from:<ol>      *<li> {@link ComponentContext#getProperties()} - engine configuration:       * This can be used to configure a specific keys for single Engine      *<li> {@link BundleContext#getProperty(String)} - system configuration:      * This can be used to configure the key within the "sling.properties" file      * or as a system property when starting the Stanbol instance.      *</ol>      *<b>Note</b><ul>      *<li> License keys configures like that will be used by all CELI engines       * that do not provide there own key.      *<li> If the License key is configured via a System property it can be      * also accessed by other components.      *</ul>      */
name|String
name|CELI_LICENSE
init|=
literal|"celi.license"
decl_stmt|;
comment|/**      * If this property is present and set to "true" engines will allow to use      * the test account. This allows to test the CELI engines without requesting      * an account on<a href="http://www.linguagrid.org">linguagrid.org</a><p>      * NOTES:<ul>      *<li> This can be parsed as configuration for a specific CELI engine, as      * OSGI framework property or System property. If a {@link #CELI_LICENSE} is      * present this property will be ignored.      *<li> The test account does not require to configure a {@link #CELI_LICENSE}      *<li>Requests are limited to 100 requests per day and IP address.      *</ul>      */
name|String
name|CELI_TEST_ACCOUNT
init|=
literal|"celi.testaccount"
decl_stmt|;
name|String
name|CELI_CONNECTION_TIMEOUT
init|=
literal|"celi.connectionTimeout"
decl_stmt|;
comment|/**      * The default connection timeout for HTTP connections (30sec)      */
name|int
name|DEFAULT_CONECTION_TIMEOUT
init|=
literal|30
decl_stmt|;
comment|/**      * Concept used to annotate sentiment expressions within text      *  TODO: Find standard ontology for reference or check if it is OK to define new properties in the FISE namespace      */
name|IRI
name|SENTIMENT_EXPRESSION
init|=
operator|new
name|IRI
argument_list|(
literal|"http://fise.iks-project.eu/ontology/Sentiment Expression"
argument_list|)
decl_stmt|;
comment|/**      * Datatype property (targets double literals) used to represent the polarity of a sentiment expression      *  TODO: Find standard ontology for reference or check if it is OK to define new properties in the FISE namespace      */
name|IRI
name|HAS_SENTIMENT_EXPRESSION_POLARITY
init|=
operator|new
name|IRI
argument_list|(
literal|"http://fise.iks-project.eu/ontology/hasSentimentPolarityValue"
argument_list|)
decl_stmt|;
block|}
end_interface

end_unit

