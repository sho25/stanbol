begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one  * or more contributor license agreements.  See the NOTICE file  * distributed with this work for additional information  * regarding copyright ownership.  The ASF licenses this file  * to you under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance  * with the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing,  * software distributed under the License is distributed on an  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY  * KIND, either express or implied.  See the License for the  * specific language governing permissions and limitations  * under the License.  */
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
name|security
operator|.
name|auth
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
name|security
operator|.
name|Principal
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedActionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|PrivilegedExceptionAction
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|SortedSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|Subject
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|Filter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterChain
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterConfig
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
name|ServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletResponse
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
name|ReferenceCardinality
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
name|ReferencePolicy
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
name|security
operator|.
name|UserUtil
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

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|Filter
operator|.
name|class
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
literal|"pattern"
argument_list|,
name|value
operator|=
literal|".*"
argument_list|)
block|,
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"service.ranking"
argument_list|,
name|intValue
operator|=
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
block|}
argument_list|)
annotation|@
name|Reference
argument_list|(
name|name
operator|=
literal|"weightedAuthenticationMethod"
argument_list|,
name|cardinality
operator|=
name|ReferenceCardinality
operator|.
name|MANDATORY_MULTIPLE
argument_list|,
name|policy
operator|=
name|ReferencePolicy
operator|.
name|DYNAMIC
argument_list|,
name|referenceInterface
operator|=
name|WeightedAuthenticationMethod
operator|.
name|class
argument_list|)
specifier|public
class|class
name|AuthenticatingFilter
implements|implements
name|Filter
block|{
specifier|private
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|AuthenticatingFilter
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|SortedSet
argument_list|<
name|WeightedAuthenticationMethod
argument_list|>
name|methodList
init|=
operator|new
name|TreeSet
argument_list|<
name|WeightedAuthenticationMethod
argument_list|>
argument_list|(
operator|new
name|WeightedAuthMethodComparator
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
name|Subject
name|getSubject
parameter_list|()
block|{
name|Subject
name|subject
init|=
name|UserUtil
operator|.
name|getCurrentSubject
argument_list|()
decl_stmt|;
if|if
condition|(
name|subject
operator|==
literal|null
condition|)
block|{
name|subject
operator|=
operator|new
name|Subject
argument_list|()
expr_stmt|;
block|}
return|return
name|subject
return|;
block|}
comment|/** 	 * Registers a<code>WeightedAuthenticationMethod</code> 	 * 	 * @param method the method to be registered 	 */
specifier|protected
name|void
name|bindWeightedAuthenticationMethod
parameter_list|(
name|WeightedAuthenticationMethod
name|method
parameter_list|)
block|{
name|methodList
operator|.
name|add
argument_list|(
name|method
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Unregister a<code>WeightedAuthenticationMethod</code> 	 * 	 * @param method the method to be unregistered 	 */
specifier|protected
name|void
name|unbindWeightedAuthenticationMethod
parameter_list|(
name|WeightedAuthenticationMethod
name|method
parameter_list|)
block|{
name|methodList
operator|.
name|remove
argument_list|(
name|method
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Compares the WeightedAuthenticationMethods, descending for weight and ascending by name 	 */
specifier|static
class|class
name|WeightedAuthMethodComparator
implements|implements
name|Comparator
argument_list|<
name|WeightedAuthenticationMethod
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|WeightedAuthenticationMethod
name|o1
parameter_list|,
name|WeightedAuthenticationMethod
name|o2
parameter_list|)
block|{
name|int
name|o1Weight
init|=
name|o1
operator|.
name|getWeight
argument_list|()
decl_stmt|;
name|int
name|o2Weight
init|=
name|o2
operator|.
name|getWeight
argument_list|()
decl_stmt|;
if|if
condition|(
name|o1Weight
operator|!=
name|o2Weight
condition|)
block|{
return|return
name|o2Weight
operator|-
name|o1Weight
return|;
block|}
return|return
name|o1
operator|.
name|getClass
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o2
operator|.
name|getClass
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|private
name|void
name|writeLoginResponse
parameter_list|(
specifier|final
name|HttpServletRequest
name|request
parameter_list|,
specifier|final
name|HttpServletResponse
name|response
parameter_list|,
name|Throwable
name|e
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
for|for
control|(
name|AuthenticationMethod
name|authMethod
range|:
name|methodList
control|)
block|{
if|if
condition|(
name|authMethod
operator|.
name|writeLoginResponse
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|e
argument_list|)
condition|)
block|{
break|break;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|filterConfig
parameter_list|)
throws|throws
name|ServletException
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
specifier|public
name|void
name|doFilter
parameter_list|(
specifier|final
name|ServletRequest
name|servletRequest
parameter_list|,
specifier|final
name|ServletResponse
name|servletResponse
parameter_list|,
specifier|final
name|FilterChain
name|chain
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"filtering request"
argument_list|)
expr_stmt|;
specifier|final
name|HttpServletRequest
name|request
init|=
operator|(
name|HttpServletRequest
operator|)
name|servletRequest
decl_stmt|;
specifier|final
name|HttpServletResponse
name|response
init|=
operator|(
name|HttpServletResponse
operator|)
name|servletResponse
decl_stmt|;
specifier|final
name|Subject
name|subject
init|=
name|getSubject
argument_list|()
decl_stmt|;
block|{
name|AuthenticationMethod
name|authenticationMethod
init|=
literal|null
decl_stmt|;
try|try
block|{
for|for
control|(
name|Iterator
argument_list|<
name|WeightedAuthenticationMethod
argument_list|>
name|it
init|=
name|methodList
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|authenticationMethod
operator|=
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|authenticationMethod
operator|.
name|authenticate
argument_list|(
name|request
argument_list|,
name|subject
argument_list|)
condition|)
block|{
break|break;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|LoginException
name|ex
parameter_list|)
block|{
if|if
condition|(
operator|!
name|authenticationMethod
operator|.
name|writeLoginResponse
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|ex
argument_list|)
condition|)
block|{
name|writeLoginResponse
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
block|}
name|Set
argument_list|<
name|Principal
argument_list|>
name|principals
init|=
name|subject
operator|.
name|getPrincipals
argument_list|()
decl_stmt|;
if|if
condition|(
name|principals
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|principals
operator|.
name|add
argument_list|(
name|UserUtil
operator|.
name|ANONYMOUS
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|Subject
operator|.
name|doAsPrivileged
argument_list|(
name|subject
argument_list|,
operator|new
name|PrivilegedExceptionAction
argument_list|<
name|Object
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Object
name|run
parameter_list|()
throws|throws
name|Exception
block|{
name|chain
operator|.
name|doFilter
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PrivilegedActionException
name|e
parameter_list|)
block|{
name|Throwable
name|cause
init|=
name|e
operator|.
name|getCause
argument_list|()
decl_stmt|;
if|if
condition|(
name|cause
operator|instanceof
name|ServletException
condition|)
block|{
throw|throw
operator|(
name|ServletException
operator|)
name|cause
throw|;
block|}
if|if
condition|(
name|cause
operator|instanceof
name|RuntimeException
condition|)
block|{
throw|throw
operator|(
name|RuntimeException
operator|)
name|cause
throw|;
block|}
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|debug
argument_list|(
literal|"SecurityException: {}"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|writeLoginResponse
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|destroy
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
block|}
block|}
end_class

end_unit

