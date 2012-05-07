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
name|enhancer
operator|.
name|servicesapi
operator|.
name|impl
package|;
end_package

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
name|enhancer
operator|.
name|servicesapi
operator|.
name|Chain
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
name|Constants
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
name|ConfigurationException
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

begin_comment
comment|/**  * Abstract base implementation that reads the of the   * {@link Chain#PROPERTY_NAME} from configuration parsed in   * the activate methods and implements the {@link #getName()} method. If the   * name is missing or empty a {@link ConfigurationException} is thrown.<p>  * In addition this Class defines a {@link Service}, {@link Component} and   * {@link Property} annotations for<ul>  *<li> EnhancementEngine#PROPERTY_NAME  *<li> {@link Constants#SERVICE_RANKING}  *</ul>  * This annotations can be {@link Component#inherit()} to sub classes and would  * allow users to specify the name and the ranking of an engine by using e.g. the  * Apache Felix Webconsole.<p>  * @author Rupert Westenthaler  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Chain
operator|.
name|PROPERTY_NAME
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
block|}
argument_list|)
annotation|@
name|Service
specifier|public
specifier|abstract
class|class
name|AbstractChain
implements|implements
name|Chain
block|{
specifier|private
name|String
name|name
decl_stmt|;
comment|/**      * The {@link ComponentContext} set in the {@link #activate(ComponentContext)}      * and reset in the {@link #deactivate(ComponentContext)} method.      */
specifier|protected
name|ComponentContext
name|context
decl_stmt|;
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|this
operator|.
name|context
operator|=
name|ctx
expr_stmt|;
name|Object
name|value
init|=
name|ctx
operator|.
name|getProperties
argument_list|()
operator|.
name|get
argument_list|(
name|PROPERTY_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|String
condition|)
block|{
name|name
operator|=
operator|(
name|String
operator|)
name|value
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|name
operator|=
literal|null
expr_stmt|;
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_NAME
argument_list|,
literal|"The configured"
operator|+
literal|"name of a Chain MUST NOT be empty!"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|PROPERTY_NAME
argument_list|,
name|value
operator|==
literal|null
condition|?
literal|"The name is a required property!"
else|:
literal|"The name of a Chain MUST be an non empty String "
operator|+
literal|"(type: "
operator|+
name|value
operator|.
name|getClass
argument_list|()
operator|+
literal|" value: "
operator|+
name|value
operator|+
literal|")"
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|ctx
parameter_list|)
block|{
name|this
operator|.
name|context
operator|=
literal|null
expr_stmt|;
name|name
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
block|}
end_class

end_unit
