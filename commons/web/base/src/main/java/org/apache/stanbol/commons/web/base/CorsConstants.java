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

begin_class
specifier|public
class|class
name|CorsConstants
block|{
specifier|public
specifier|static
specifier|final
name|String
name|CORS_ORIGIN
init|=
literal|"org.apache.stanbol.commons.web.cors.origin"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CORS_ACCESS_CONTROL_EXPOSE_HEADERS
init|=
literal|"org.apache.stanbol.commons.web.cors.access_control_expose_headers"
decl_stmt|;
block|}
end_class

end_unit

