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
name|testing
operator|.
name|http
package|;
end_package

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
name|URL
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
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|servlet
operator|.
name|RequestDispatcher
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
name|ServletContext
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
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|BundleContext
import|;
end_import

begin_comment
comment|/**  * Simple Mock class that can be used to mock the ServletContext in tests.  *   *<p>  * By default the attribute map contains a {@link org.apache.stanbol.commons.testing.http.BundleContextMock}  * under the name "org.osgi.framework.BundleContext".  *   * @author Fabian Christ  */
end_comment

begin_class
specifier|public
class|class
name|ServletContextMock
implements|implements
name|ServletContext
block|{
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|attributeMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|ServletContextMock
parameter_list|()
block|{
name|this
operator|.
name|attributeMap
operator|.
name|put
argument_list|(
name|BundleContext
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
operator|new
name|BundleContextMock
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Object
name|getAttribute
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|this
operator|.
name|attributeMap
operator|.
name|get
argument_list|(
name|name
argument_list|)
return|;
block|}
specifier|public
name|Enumeration
name|getAttributeNames
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|enumeration
argument_list|(
name|this
operator|.
name|attributeMap
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|putAttribute
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|attributeMap
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ServletContext
name|getContext
parameter_list|(
name|String
name|uripath
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getInitParameter
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|Enumeration
name|getInitParameterNames
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|int
name|getMajorVersion
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|0
return|;
block|}
specifier|public
name|String
name|getMimeType
parameter_list|(
name|String
name|file
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|int
name|getMinorVersion
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|0
return|;
block|}
specifier|public
name|RequestDispatcher
name|getNamedDispatcher
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getRealPath
parameter_list|(
name|String
name|path
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|RequestDispatcher
name|getRequestDispatcher
parameter_list|(
name|String
name|path
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|URL
name|getResource
parameter_list|(
name|String
name|path
parameter_list|)
throws|throws
name|MalformedURLException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|InputStream
name|getResourceAsStream
parameter_list|(
name|String
name|path
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|Set
name|getResourcePaths
parameter_list|(
name|String
name|path
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getServerInfo
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|Servlet
name|getServlet
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|ServletException
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getServletContextName
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|Enumeration
name|getServletNames
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|Enumeration
name|getServlets
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
specifier|public
name|void
name|log
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
specifier|public
name|void
name|log
parameter_list|(
name|Exception
name|exception
parameter_list|,
name|String
name|msg
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
specifier|public
name|void
name|log
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|throwable
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
specifier|public
name|void
name|removeAttribute
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
specifier|public
name|void
name|setAttribute
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|object
parameter_list|)
block|{
name|this
operator|.
name|putAttribute
argument_list|(
name|name
argument_list|,
name|object
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

