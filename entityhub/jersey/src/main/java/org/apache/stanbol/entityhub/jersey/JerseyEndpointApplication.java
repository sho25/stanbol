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
name|jersey
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|Application
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
name|jersey
operator|.
name|resource
operator|.
name|ReferencedSiteRootResource
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
name|jersey
operator|.
name|resource
operator|.
name|EntityMappingResource
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
name|jersey
operator|.
name|resource
operator|.
name|EntityhubRootResource
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
name|jersey
operator|.
name|resource
operator|.
name|SymbolResource
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
name|jersey
operator|.
name|resource
operator|.
name|SiteManagerRootResource
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
name|jersey
operator|.
name|writers
operator|.
name|JettisonWriter
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
name|jersey
operator|.
name|writers
operator|.
name|QueryResultListWriter
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
name|jersey
operator|.
name|writers
operator|.
name|SignWriter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Statically define the list of available resources and providers to be used by  * the FISE JAX-RS Endpoint.  *  * The jersey auto-scan mechanism does not seem to work when deployed through  * OSGi's HttpService initialization.  *  * In the future this class might get refactored as an OSGi service to allow for  * dynamic configuration and deployment of additional JAX-RS resources and  * providers.  */
end_comment

begin_class
specifier|public
class|class
name|JerseyEndpointApplication
extends|extends
name|Application
block|{
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
name|JerseyEndpointApplication
parameter_list|()
block|{
name|log
operator|.
name|info
argument_list|(
literal|"JerseyEndpointApplication instanceiated"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|getClasses
parameter_list|()
block|{
name|log
operator|.
name|info
argument_list|(
literal|"JerseyEndpointApplication getClasses called ..."
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|classes
init|=
operator|new
name|HashSet
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
comment|// resources
name|classes
operator|.
name|add
argument_list|(
name|EntityhubRootResource
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|EntityMappingResource
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|ReferencedSiteRootResource
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|SiteManagerRootResource
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|SymbolResource
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// message body writers
name|classes
operator|.
name|add
argument_list|(
name|QueryResultListWriter
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|SignWriter
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//TODO: somehow writing of Json has not worked because of
comment|//      A message body writer for Java class org.codehaus.jettison.json.JSONArray,
comment|//     and Java type class org.codehaus.jettison.json.JSONArray, and MIME media
comment|//     type application/json was not found
comment|//     As a workaround I have implemented this small workaround!
name|classes
operator|.
name|add
argument_list|(
name|JettisonWriter
operator|.
name|class
argument_list|)
expr_stmt|;
return|return
name|classes
return|;
block|}
comment|//    @Override
comment|//    public Set<Object> getSingletons() {
comment|//        Set<Object> singletons = new HashSet<Object>();
comment|//        // view processors
comment|//        singletons.add(new FreemarkerViewProcessor());
comment|//        return singletons;
comment|//    }
block|}
end_class

end_unit

