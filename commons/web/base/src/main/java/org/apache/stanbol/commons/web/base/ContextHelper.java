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
name|web
operator|.
name|base
package|;
end_package

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

begin_class
specifier|public
class|class
name|ContextHelper
block|{
comment|// TODO: turn the following into a annotation that derives from the JAX-RS @Context annotation
comment|/**      * Fetch an OSGi service instance broadcasted into the OSGi context.      *       * @param<T>      *            the type of the service      * @param clazz      *            the class of the service      * @param context      *            the servlet context      * @return the registered instance of the service (assuming cardinality 1)      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|T
name|getServiceFromContext
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|,
name|ServletContext
name|context
parameter_list|)
block|{
name|BundleContext
name|bundleContext
init|=
operator|(
name|BundleContext
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|BundleContext
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|ServiceReference
name|reference
init|=
name|bundleContext
operator|.
name|getServiceReference
argument_list|(
name|clazz
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
comment|//TODO: returning the service will cause the service reference not to be
comment|//  released bundleContext.ungetService(reference) will not be called!
if|if
condition|(
name|reference
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|T
operator|)
name|bundleContext
operator|.
name|getService
argument_list|(
name|reference
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

