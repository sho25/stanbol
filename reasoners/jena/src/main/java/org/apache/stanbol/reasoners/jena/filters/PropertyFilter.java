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
name|reasoners
operator|.
name|jena
operator|.
name|filters
package|;
end_package

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Statement
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|util
operator|.
name|iterator
operator|.
name|Filter
import|;
end_import

begin_comment
comment|/**  * A filter to get only statements with the given property  */
end_comment

begin_class
specifier|public
class|class
name|PropertyFilter
extends|extends
name|Filter
argument_list|<
name|Statement
argument_list|>
block|{
specifier|private
name|Property
name|property
decl_stmt|;
specifier|public
name|PropertyFilter
parameter_list|(
name|Property
name|property
parameter_list|)
block|{
name|this
operator|.
name|property
operator|=
name|property
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|Statement
name|statement
parameter_list|)
block|{
comment|/**          * Only statements with the given property          */
return|return
name|statement
operator|.
name|getPredicate
argument_list|()
operator|.
name|equals
argument_list|(
name|property
argument_list|)
return|;
block|}
block|}
end_class

end_unit

