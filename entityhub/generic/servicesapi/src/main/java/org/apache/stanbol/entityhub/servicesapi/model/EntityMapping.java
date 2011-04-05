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
name|Date
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

begin_interface
specifier|public
interface|interface
name|EntityMapping
extends|extends
name|Sign
block|{
comment|/**      * The default state for newly created instances if not otherwise configured      */
name|MappingState
name|DEFAULT_MAPPING_STATE
init|=
name|MappingState
operator|.
name|proposed
decl_stmt|;
comment|/**      * Enumeration that defines the different states of {@link EntityMapping}      * instances.      * @author Rupert Westenthaler      *      */
enum|enum
name|MappingState
block|{
comment|/**          * Mapping the entity to the symbol was rejected by some user/process.          * Such mappings MUST NOT be used in any application context other than          * some administrative interfaces.          */
name|rejected
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateRejected
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**          * Indicated, that a mapping of the entity to the symbol is proposed.          * Such mappings still wait for some kind of confirmation to be fully          * established. Based on the application context it might already be          * OK to used them.          */
name|proposed
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateProposed
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**          * This indicates that this mapping has expired. This indicated, that          * this mapping was once {@link MappingState#confirmed} but now waits for          * some confirmation activity. Based on the application context it might          * still be OK to use mappings with that state.          */
name|expired
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateExpired
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**          * Indicated, that this mapping is fully valied and can be used in any          * application context          */
name|confirmed
argument_list|(
name|RdfResourceEnum
operator|.
name|mappingStateConfirmed
operator|.
name|getUri
argument_list|()
argument_list|)
block|,         ;
specifier|private
name|String
name|uri
decl_stmt|;
name|MappingState
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
comment|// ---- reverse Mapping based on URI ----
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|MappingState
argument_list|>
name|uri2state
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|MappingState
argument_list|>
name|mappings
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|MappingState
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|MappingState
name|state
range|:
name|MappingState
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
name|uri2state
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|mappings
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|MappingState
name|getState
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
return|return
name|uri2state
operator|.
name|get
argument_list|(
name|uri
argument_list|)
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
name|uri2state
operator|.
name|containsKey
argument_list|(
name|uri
argument_list|)
return|;
block|}
block|}
comment|//    /**
comment|//     * Getter for the identifier.
comment|//     * @return the identifier
comment|//     */
comment|//    String getId();
comment|/**      * The key to be used for the id of the mapped entity      */
name|String
name|ENTITY_ID
init|=
name|RdfResourceEnum
operator|.
name|mappedEntity
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|/**      * Getter for the ID of the entity      * @return the mapped entity      */
name|String
name|getEntityId
parameter_list|()
function_decl|;
comment|/**      * The key to be used for the id of the mapped symbol      */
name|String
name|SYMBOL_ID
init|=
name|RdfResourceEnum
operator|.
name|mappedSymbol
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|/**      * Getter for the ID of the symbol      * @return the symbol the entity is mapped to      */
name|String
name|getSymbolId
parameter_list|()
function_decl|;
comment|/**      * The key to be used for the state of the MappedEntity instance      */
name|String
name|STATE
init|=
name|RdfResourceEnum
operator|.
name|hasMappingState
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|/**      * The state of this mapping      * @return the state      */
name|MappingState
name|getState
parameter_list|()
function_decl|;
comment|/**      * Setter for the mapping state      * @param state the new state      * @throws IllegalArgumentException if the parsed state is<code>null</code>      */
name|void
name|setState
parameter_list|(
name|MappingState
name|state
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
comment|/**      * The property used to hold the expires date of the representation (if any)      */
name|String
name|EXPIRES
init|=
name|RdfResourceEnum
operator|.
name|expires
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * Getter for the date this representation expires. If this representation      * does not expire this method returns<code>null</code>.      * @return the expire date or<code>null</code> if not applicable.      */
name|Date
name|getExpires
parameter_list|()
function_decl|;
comment|/**      * Setter for the expire date for this representation.      * @param date the date or<code>null</code> if this representation does not      * expire      */
name|void
name|setExpires
parameter_list|(
name|Date
name|date
parameter_list|)
function_decl|;
comment|//    /**
comment|//     * Getter for the Representation of this EntityMapping
comment|//     * @return The representation
comment|//     */
comment|//    Representation getRepresentation();
block|}
end_interface

end_unit

