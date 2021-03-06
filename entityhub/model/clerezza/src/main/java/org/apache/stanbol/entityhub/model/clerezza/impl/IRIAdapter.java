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
name|model
operator|.
name|clerezza
operator|.
name|impl
package|;
end_package

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
name|URISyntaxException
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
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|util
operator|.
name|AdaptingIterator
operator|.
name|Adapter
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
specifier|public
class|class
name|IRIAdapter
parameter_list|<
name|A
parameter_list|>
implements|implements
name|Adapter
argument_list|<
name|IRI
argument_list|,
name|A
argument_list|>
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|IRIAdapter
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
specifier|final
name|A
name|adapt
parameter_list|(
name|IRI
name|value
parameter_list|,
name|Class
argument_list|<
name|A
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|URI
operator|.
name|class
argument_list|)
condition|)
block|{
try|try
block|{
return|return
operator|(
name|A
operator|)
operator|new
name|URI
argument_list|(
name|value
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse an URI for IRI "
operator|+
name|value
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|URL
operator|.
name|class
argument_list|)
condition|)
block|{
try|try
block|{
return|return
operator|(
name|A
operator|)
operator|new
name|URL
argument_list|(
name|value
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse an URL for IRI "
operator|+
name|value
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|String
operator|.
name|class
argument_list|)
condition|)
block|{
return|return
operator|(
name|A
operator|)
name|value
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|IRI
operator|.
name|class
argument_list|)
condition|)
block|{
comment|//Who converts IRI -> IRI ^
return|return
operator|(
name|A
operator|)
name|value
return|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
name|type
operator|+
literal|" is not a supported target type for "
operator|+
name|IRI
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

