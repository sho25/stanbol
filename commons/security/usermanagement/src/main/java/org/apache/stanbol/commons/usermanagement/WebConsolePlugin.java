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
name|usermanagement
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
name|URL
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|Servlet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
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
name|core
operator|.
name|serializedform
operator|.
name|SupportedFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|commons
operator|.
name|ldpathtemplate
operator|.
name|LdRenderer
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
name|commons
operator|.
name|usermanagement
operator|.
name|resource
operator|.
name|UserResource
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

begin_class
annotation|@
name|Component
annotation|@
name|Service
argument_list|(
name|Servlet
operator|.
name|class
argument_list|)
annotation|@
name|Properties
argument_list|(
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"felix.webconsole.label"
argument_list|,
name|value
operator|=
literal|"usermanagement"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"felix.webconsole.title"
argument_list|,
name|value
operator|=
literal|"User Management"
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|WebConsolePlugin
extends|extends
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|webconsole
operator|.
name|AbstractWebConsolePlugin
block|{
specifier|private
specifier|static
specifier|final
name|String
name|STATIC_PREFIX
init|=
literal|"/usermanagement/res/"
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|UserResource
name|userManager
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|LdRenderer
name|ldRenderer
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|Serializer
name|serializer
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"User Management"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|LABEL
init|=
literal|"usermanagement"
decl_stmt|;
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|LABEL
return|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|NAME
return|;
block|}
specifier|protected
name|void
name|renderContent
parameter_list|(
name|HttpServletRequest
name|req
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
comment|//TODO enhance LDPath template to support rdf:Lists and return list
name|ldRenderer
operator|.
name|render
argument_list|(
name|userManager
operator|.
name|getUserType
argument_list|()
argument_list|,
literal|"html/org/apache/stanbol/commons/usermanagement/webConsole.ftl"
argument_list|,
name|response
operator|.
name|getWriter
argument_list|()
argument_list|)
expr_stmt|;
comment|// serializer.serialize(System.out, userManager.getUserType().getGraph(), SupportedFormat.TURTLE);
comment|// log me for debug!
block|}
annotation|@
name|Override
specifier|protected
name|String
index|[]
name|getCssReferences
parameter_list|()
block|{
name|String
index|[]
name|result
init|=
operator|new
name|String
index|[
literal|1
index|]
decl_stmt|;
name|result
index|[
literal|0
index|]
operator|=
literal|"usermanagement/res/static/user-management/styles/webconsole.css"
expr_stmt|;
return|return
name|result
return|;
block|}
specifier|public
name|void
name|activateBundle
parameter_list|(
name|BundleContext
name|bundleContext
parameter_list|)
block|{
name|super
operator|.
name|activate
argument_list|(
name|bundleContext
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|deactivate
parameter_list|()
block|{
name|super
operator|.
name|deactivate
argument_list|()
expr_stmt|;
block|}
comment|/**      * The felix webconsole way for returning static resources      */
specifier|public
name|URL
name|getResource
parameter_list|(
name|String
name|path
parameter_list|)
block|{
if|if
condition|(
name|path
operator|.
name|startsWith
argument_list|(
name|STATIC_PREFIX
argument_list|)
condition|)
block|{
comment|//we just get the resources from the same place as stanbol expectes them to be
comment|//i.e. the resources will be available below
comment|//http://<host>/<path/to/webconsole>/usermangement/res/
comment|//by virtuel of this felix webconsole method
comment|//as well as below
comment|//http://<host>/<path/to/stanbol>/,
comment|//e.g. with the default config below http://localhost:8080/
return|return
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/META-INF/resources/"
operator|+
name|path
operator|.
name|substring
argument_list|(
name|STATIC_PREFIX
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

