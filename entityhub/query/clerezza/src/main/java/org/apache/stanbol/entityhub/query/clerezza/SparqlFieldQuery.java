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
name|query
operator|.
name|clerezza
package|;
end_package

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
name|query
operator|.
name|clerezza
operator|.
name|SparqlQueryUtils
operator|.
name|EndpointTypeEnum
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
name|query
operator|.
name|sparql
operator|.
name|SparqlEndpointTypeEnum
import|;
end_import

begin_comment
comment|/**  * This class moved to {@link org.apache.stanbol.entityhub.query.sparql.SparqlFieldQuery}.  * and now extends the new one. NOTE that calls to {@link #setEndpointType(EndpointTypeEnum)}  * and {@link #getEndpointType()} will translate {@link EndpointTypeEnum} instances  * to {@link SparqlEndpointTypeEnum}.  * @author Rupert Westenthaler  * @see org.apache.stanbol.entityhub.query.sparql.SparqlFieldQuery  */
end_comment

begin_class
annotation|@
name|Deprecated
specifier|public
class|class
name|SparqlFieldQuery
extends|extends
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|query
operator|.
name|sparql
operator|.
name|SparqlFieldQuery
block|{
specifier|protected
name|SparqlFieldQuery
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|SparqlFieldQuery
parameter_list|(
name|EndpointTypeEnum
name|endpointType
parameter_list|)
block|{
name|super
argument_list|(
name|endpointType
operator|==
literal|null
condition|?
literal|null
else|:
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|query
operator|.
name|sparql
operator|.
name|SparqlEndpointTypeEnum
operator|.
name|valueOf
argument_list|(
name|endpointType
operator|.
name|name
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deprecated
specifier|public
specifier|final
name|EndpointTypeEnum
name|getEndpointType
parameter_list|()
block|{
name|EndpointTypeEnum
name|type
decl_stmt|;
try|try
block|{
name|type
operator|=
name|EndpointTypeEnum
operator|.
name|valueOf
argument_list|(
name|super
operator|.
name|getSparqlEndpointType
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|type
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|type
return|;
block|}
annotation|@
name|Deprecated
specifier|public
specifier|final
name|void
name|setEndpointType
parameter_list|(
name|EndpointTypeEnum
name|endpointType
parameter_list|)
block|{
name|SparqlEndpointTypeEnum
name|type
init|=
name|endpointType
operator|==
literal|null
condition|?
literal|null
else|:
name|SparqlEndpointTypeEnum
operator|.
name|valueOf
argument_list|(
name|endpointType
operator|.
name|name
argument_list|()
argument_list|)
decl_stmt|;
name|setSparqlEndpointType
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
comment|/**      * Clones the query (including the field to var name mapping)      */
annotation|@
name|Override
specifier|public
name|SparqlFieldQuery
name|clone
parameter_list|()
block|{
return|return
name|clone
argument_list|(
operator|new
name|SparqlFieldQuery
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|super
operator|.
name|equals
argument_list|(
name|obj
argument_list|)
operator|&&
name|obj
operator|instanceof
name|SparqlFieldQuery
return|;
block|}
block|}
end_class

end_unit

