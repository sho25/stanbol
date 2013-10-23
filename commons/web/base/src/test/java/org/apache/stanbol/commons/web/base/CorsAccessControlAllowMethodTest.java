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
name|web
operator|.
name|base
package|;
end_package

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|HttpMethod
operator|.
name|GET
import|;
end_import

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|HttpMethod
operator|.
name|OPTIONS
import|;
end_import

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|HttpMethod
operator|.
name|POST
import|;
end_import

begin_import
import|import static
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|HttpMethod
operator|.
name|PUT
import|;
end_import

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
name|Collections
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
name|HttpHeaders
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
name|MultivaluedMap
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
name|Response
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
name|Response
operator|.
name|ResponseBuilder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|wink
operator|.
name|common
operator|.
name|internal
operator|.
name|MultivaluedMapImpl
import|;
end_import

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
name|junit
operator|.
name|Test
import|;
end_import

begin_comment
comment|/**  * Tests issue reported/fix for STANBOL-616  *   * @author Rupert Westenthaler  *   */
end_comment

begin_class
specifier|public
class|class
name|CorsAccessControlAllowMethodTest
block|{
comment|/*@Test     public void testAccessControlAllowMethodTest() {         ServletContext context = new MockServletContext();         context.setAttribute(CorsConstants.CORS_ORIGIN, Collections.singleton("*"));         MultivaluedMap<String,String> header = new MultivaluedMapImpl();         header.add("Origin", "https://issues.apache.org/jira/browse/STANBOL-616");         header.put("Access-Control-Request-Headers", Arrays.asList("Origin", "Content-Type", "Accept"));         header.add("Access-Control-Request-Method", "PUT");         HttpHeaders requestHeaders = new MockHttpHeaders(header);          ResponseBuilder builder = Response.ok("Test");         CorsHelper.enableCORS(context, builder, requestHeaders, OPTIONS, GET, POST, PUT);         Response response = builder.build();         MultivaluedMap<String,Object> metadata = response.getMetadata();         Assert.assertTrue("'Access-Control-Allow-Headers' expected",             metadata.containsKey("Access-Control-Allow-Headers"));         String value = (String) metadata.getFirst("Access-Control-Allow-Headers");         Assert.assertTrue("'Access-Control-Allow-Headers' does not contain the expected values",             value.contains("Origin")&& value.contains("Content-Type")&& value.contains("Accept"));         Assert.assertTrue("'Access-Control-Allow-Origin' expected",             metadata.containsKey("Access-Control-Allow-Origin"));         Assert.assertEquals("'Access-Control-Allow-Origin' does not have the expected value '*'", "*",             metadata.getFirst("Access-Control-Allow-Origin"));         Assert.assertTrue("'Access-Control-Allow-Methods' expected",             metadata.containsKey("Access-Control-Allow-Methods"));         value = (String) metadata.getFirst("Access-Control-Allow-Methods");         Assert.assertTrue("'Access-Control-Allow-Methods' does not contain the expected values",             value.contains(OPTIONS)&& value.contains(GET)&& value.contains(POST)&& value.contains(PUT));         Assert.assertTrue("'Access-Control-Expose-Headers' expected",             metadata.containsKey("Access-Control-Expose-Headers"));         value = (String) metadata.getFirst("Access-Control-Expose-Headers");         Assert.assertTrue("'Access-Control-Expose-Headers' does not contain the expected valur 'Location'",             value.contains("Location"));     }*/
block|}
end_class

end_unit

