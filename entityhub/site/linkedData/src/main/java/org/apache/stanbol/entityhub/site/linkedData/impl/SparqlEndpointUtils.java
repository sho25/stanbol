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
name|site
operator|.
name|linkedData
operator|.
name|impl
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLConnection
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|UriBuilder
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|SparqlEndpointUtils
block|{
specifier|private
name|SparqlEndpointUtils
parameter_list|()
block|{
comment|/* Do not create instances of utility classes*/
block|}
specifier|public
specifier|static
specifier|final
name|String
name|SPARQL_RESULT_JSON
init|=
literal|"application/sparql-results+json"
decl_stmt|;
comment|/**      * Sends an SPARQL Request to the accessUri. Please note that based on the      * type of the SPARQL query different content are supported by the Site      * @param accessUri the uri of the SPARQL endpoint      * @param contentType the contentType of the returned RDF graph      * @param query the SPARQL Construct query      * @return the results as input stream      * @throws IOException      * @throws MalformedURLException      */
specifier|public
specifier|static
name|InputStream
name|sendSparqlRequest
parameter_list|(
name|String
name|accessUri
parameter_list|,
name|String
name|query
parameter_list|,
name|String
name|contentType
parameter_list|)
throws|throws
name|IOException
throws|,
name|MalformedURLException
block|{
specifier|final
name|URI
name|dereferenceUri
init|=
name|UriBuilder
operator|.
name|fromUri
argument_list|(
name|accessUri
argument_list|)
operator|.
name|queryParam
argument_list|(
literal|"query"
argument_list|,
literal|"{query}"
argument_list|)
operator|.
name|queryParam
argument_list|(
literal|"format"
argument_list|,
literal|"{format}"
argument_list|)
operator|.
name|build
argument_list|(
name|query
argument_list|,
name|contentType
argument_list|)
decl_stmt|;
specifier|final
name|URLConnection
name|con
init|=
name|dereferenceUri
operator|.
name|toURL
argument_list|()
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|con
operator|.
name|addRequestProperty
argument_list|(
literal|"Accept"
argument_list|,
name|contentType
argument_list|)
expr_stmt|;
return|return
name|con
operator|.
name|getInputStream
argument_list|()
return|;
block|}
block|}
end_class

end_unit

