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
operator|.
name|resource
operator|.
name|reconcile
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|Path
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
name|web
operator|.
name|base
operator|.
name|ContextHelper
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
name|Entityhub
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
name|EntityhubException
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
name|model
operator|.
name|Representation
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
name|query
operator|.
name|FieldQuery
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
name|query
operator|.
name|QueryResultList
import|;
end_import

begin_class
annotation|@
name|Path
argument_list|(
literal|"/entityhub/reconcile"
argument_list|)
specifier|public
class|class
name|EntityhubReconcileResource
extends|extends
name|BaseGoogleRefineReconcileResource
block|{
specifier|private
name|Entityhub
name|_entityhub
decl_stmt|;
specifier|public
name|EntityhubReconcileResource
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|private
name|Entityhub
name|getEntityhub
parameter_list|()
block|{
if|if
condition|(
name|_entityhub
operator|==
literal|null
condition|)
block|{
name|_entityhub
operator|=
name|ContextHelper
operator|.
name|getServiceFromContext
argument_list|(
name|Entityhub
operator|.
name|class
argument_list|,
name|servletContext
argument_list|)
expr_stmt|;
if|if
condition|(
name|_entityhub
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The Entityhub service is currently not available!"
argument_list|)
throw|;
block|}
block|}
return|return
name|_entityhub
return|;
block|}
annotation|@
name|Override
specifier|protected
name|QueryResultList
argument_list|<
name|Representation
argument_list|>
name|performQuery
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
name|getEntityhub
argument_list|()
operator|.
name|find
argument_list|(
name|query
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|getSiteName
parameter_list|()
block|{
return|return
literal|"Entityhub (local managed Entities)"
return|;
block|}
annotation|@
name|Override
specifier|protected
name|FieldQuery
name|createFieldQuery
parameter_list|()
block|{
return|return
name|getEntityhub
argument_list|()
operator|.
name|getQueryFactory
argument_list|()
operator|.
name|createFieldQuery
argument_list|()
return|;
block|}
block|}
end_class

end_unit

