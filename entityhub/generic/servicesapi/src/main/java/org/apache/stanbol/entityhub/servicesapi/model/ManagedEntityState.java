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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
package|;
end_package

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
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_comment
comment|/**  * Enumeration that defines the different states of Entities managed by  * the Entityhub  * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|ManagedEntityState
block|{
comment|/**      * This symbol is marked as removed      */
name|removed
argument_list|(
name|RdfResourceEnum
operator|.
name|entityStateRemoved
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**      * This symbol should no longer be moved. Usually there are one or more      * new symbols that should be used instead of this one. See      * {@link Symbol#getSuccessors()} for more information      */
name|depreciated
argument_list|(
name|RdfResourceEnum
operator|.
name|entityStateDepreciated
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**      * Indicates usually a newly created {@link Symbol} that needs some kind      * of confirmation.      */
name|proposed
argument_list|(
name|RdfResourceEnum
operator|.
name|entityStateProposed
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**      * Symbols with that state are ready to be used.      */
name|active
argument_list|(
name|RdfResourceEnum
operator|.
name|entityStateActive
operator|.
name|getUri
argument_list|()
argument_list|)
block|,     ;
specifier|private
name|String
name|uri
decl_stmt|;
name|ManagedEntityState
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|ManagedEntityState
argument_list|>
name|URI_TO_STATE
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|ManagedEntityState
argument_list|>
name|mappings
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ManagedEntityState
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ManagedEntityState
name|state
range|:
name|ManagedEntityState
operator|.
name|values
argument_list|()
control|)
block|{
name|mappings
operator|.
name|put
argument_list|(
name|state
operator|.
name|getUri
argument_list|()
argument_list|,
name|state
argument_list|)
expr_stmt|;
block|}
name|URI_TO_STATE
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|mappings
argument_list|)
expr_stmt|;
block|}
comment|/**      * Getter for the State based on the URI.      * @param uri the URI      * @return the State      * @throws IllegalArgumentException if the parsed URI does not represent      * a state      */
specifier|public
specifier|static
name|ManagedEntityState
name|getState
parameter_list|(
name|String
name|uri
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|ManagedEntityState
name|state
init|=
name|URI_TO_STATE
operator|.
name|get
argument_list|(
name|uri
argument_list|)
decl_stmt|;
if|if
condition|(
name|state
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unknown SymbolState URI %s (supported states URIs: %s)"
argument_list|,
name|uri
argument_list|,
name|URI_TO_STATE
operator|.
name|keySet
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
return|return
name|state
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isState
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
return|return
name|URI_TO_STATE
operator|.
name|containsKey
argument_list|(
name|uri
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

