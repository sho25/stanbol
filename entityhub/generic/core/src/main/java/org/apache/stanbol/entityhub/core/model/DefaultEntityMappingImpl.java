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
name|core
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
name|Date
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
name|EntityMapping
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
name|Reference
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

begin_comment
comment|/**  * This is the default implementation of MappedEntity that uses a {@link Representation}  * to store the Data.  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|DefaultEntityMappingImpl
extends|extends
name|DefaultSignImpl
implements|implements
name|EntityMapping
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DefaultEntityMappingImpl
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// NOTE: No longer used after switching to the DefaultSignFactory
comment|//    /**
comment|//     * Creates a new EntityMapping between the parsed symbol and entity
comment|//     * @param entityhubId The ID of the Entityhub that defines this mapping
comment|//     * @param symbol the ID of the symbol
comment|//     * @param entity the ID of the entity
comment|//     * @param state the state or<code>null</code> to use the {@link EntityMapping#DEFAULT_MAPPING_STATE}
comment|//     * @param representation The representation to store the information of this EntityMapping
comment|//     * @throws IllegalArgumentException If the EntityMapping Instance can not be created based on the parsed parameter.
comment|//     * This includes<ul>
comment|//     *<li> the Entityhub ID is<code>null</code> or empty
comment|//     *<li> the symbol ID is<code>null</code> or empty
comment|//     *<li> the entity ID is<code>null</code> or empty
comment|//     *<li> the representation is<code>null</code>
comment|//     *</ul>
comment|//     */
comment|//    protected DefaultEntityMappingImpl(String entityhubId, String symbol,String entity,MappingState state,Representation representation) throws IllegalArgumentException {
comment|//        super(entityhubId,representation);
comment|//        if(symbol == null || symbol.isEmpty()){
comment|//            throw new IllegalArgumentException("Parsed symbol ID MUST NOT be NULL nor empty");
comment|//        }
comment|//        if(entity == null || entity.isEmpty()){
comment|//            throw new IllegalArgumentException("Parsed entity ID MUST NOT be NULL nor empty");
comment|//        }
comment|//        representation.setReference(EntityMapping.SYMBOL_ID, symbol);
comment|//        representation.setReference(EntityMapping.ENTITY_ID, entity);
comment|//        if(state == null){
comment|//            state = EntityMapping.DEFAULT_MAPPING_STATE;
comment|//        }
comment|//        representation.setReference(EntityMapping.STATE, state.getUri());
comment|//    }
comment|/**      *      * @param siteId      * @param representation The representation that holds the state for the new EntityMapping instance      * @throws IllegalArgumentException If the EntityMapping Instance can not be created based on the parsed parameter.      * This includes<ul>      *<li> the Entityhub ID is<code>null</code> or empty      *<li> the parsed representation does not define a link to an entity      *      (provide a value for the {@link EntityMapping#ENTITY_ID} field)      *<li> the parsed representation does not define a link to a symbol      *      (provide a value for the {@link EntityMapping#SYMBOL_ID} field)      *<li> the representation is<code>null</code>      *</ul>      */
specifier|protected
name|DefaultEntityMappingImpl
parameter_list|(
name|String
name|siteId
parameter_list|,
name|Representation
name|representation
parameter_list|)
block|{
name|super
argument_list|(
name|siteId
argument_list|,
name|representation
argument_list|)
expr_stmt|;
comment|//check no longer required -> allows to set values after creation ...
comment|//        if(getEntityId() == null){
comment|//            throw new IllegalArgumentException("Representation "+getId()+" does not define required field "+EntityMapping.ENTITY_ID);
comment|//        }
comment|//        if(getSymbolId() == null){
comment|//            throw new IllegalArgumentException("Representation "+getId()+" does not define required field "+EntityMapping.SYMBOL_ID);
comment|//        }
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getEntityId
parameter_list|()
block|{
name|Object
name|id
init|=
name|representation
operator|.
name|getFirst
argument_list|(
name|EntityMapping
operator|.
name|ENTITY_ID
argument_list|)
decl_stmt|;
return|return
name|id
operator|!=
literal|null
condition|?
name|id
operator|.
name|toString
argument_list|()
else|:
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|Date
name|getExpires
parameter_list|()
block|{
return|return
name|representation
operator|.
name|getFirst
argument_list|(
name|EntityMapping
operator|.
name|EXPIRES
argument_list|,
name|Date
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|MappingState
name|getState
parameter_list|()
block|{
name|Reference
name|stateUri
init|=
name|representation
operator|.
name|getFirstReference
argument_list|(
name|EntityMapping
operator|.
name|STATE
argument_list|)
decl_stmt|;
if|if
condition|(
name|stateUri
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|MappingState
operator|.
name|isState
argument_list|(
name|stateUri
operator|.
name|getReference
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|MappingState
operator|.
name|getState
argument_list|(
name|stateUri
operator|.
name|getReference
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Value {} for field {} is not a valied MappingState! -> return null"
argument_list|,
name|stateUri
argument_list|,
name|EntityMapping
operator|.
name|STATE
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|String
name|getSymbolId
parameter_list|()
block|{
name|Object
name|id
init|=
name|representation
operator|.
name|getFirst
argument_list|(
name|EntityMapping
operator|.
name|SYMBOL_ID
argument_list|)
decl_stmt|;
return|return
name|id
operator|!=
literal|null
condition|?
name|id
operator|.
name|toString
argument_list|()
else|:
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|setExpires
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
name|representation
operator|.
name|set
argument_list|(
name|EntityMapping
operator|.
name|EXPIRES
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|setState
parameter_list|(
name|MappingState
name|state
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|state
operator|!=
literal|null
condition|)
block|{
name|representation
operator|.
name|setReference
argument_list|(
name|EntityMapping
operator|.
name|STATE
argument_list|,
name|state
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"MappingState can not be set to NULL!"
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

