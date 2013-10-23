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
operator|.
name|rdf
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
name|servicesapi
operator|.
name|defaults
operator|.
name|NamespaceEnum
import|;
end_import

begin_enum
specifier|public
enum|enum
name|RdfResourceEnum
block|{
comment|/**      * The representation concept      */
name|Representation
block|,
comment|/**      * The Entity concept      */
name|Entity
block|,
comment|/**      * The site that defines/manages an {@link RdfResourceEnum#Entity}      */
name|site
block|,
comment|/**      * The ranking of the entity by this site in the value range of [0..1]      * A sign with the rank 1 would be (one of) the most important entities      * managed by this Site. A sign with rank 0 has no relevance. Even that this      * is still within the value range one could wonder why this site does      * even manage a representation about that entity.      */
name|entityRank
block|,
comment|/**      * The rdf:type used for Metadata. Note that foaf:Document is used as Type       * for the Metadata      */
name|Metadata
argument_list|(
name|NamespaceEnum
operator|.
name|foaf
argument_list|,
literal|"Document"
argument_list|)
block|,
comment|/**      * relation used to link from the       * {@link org.apache.stanbol.entityhub.servicesapi.model.Entity#getMetadata() metadata}      * to the {@link org.apache.stanbol.entityhub.servicesapi.model.Entity#getRepresentation() representation}      * of an {@link org.apache.stanbol.entityhub.servicesapi.model.Entity}.<p>      * Note that this uses a property in the entityhub namespace to ensure that      * no other (external) Information does accidently use the same property.      */
name|aboutRepresentation
argument_list|(
literal|null
argument_list|,
literal|"about"
argument_list|)
block|,
comment|//    /**
comment|//     * The representation about an Entity (domain=Entity, range=Representation).
comment|//     */
comment|//    representation,
comment|/**      * The label of an Entity      */
name|label
block|,
comment|/**      * The description of an Entity      */
name|description
block|,
comment|/**      * Predecessors of an Entity      */
name|predecessor
block|,
comment|/**      * Successors of an Entity      */
name|successor
block|,
comment|/**      * The property used for the state of locally managed Entities      */
name|hasState
block|,
comment|/**      * The Concept used to type instances of EntityStates      */
name|EntityState
block|,
comment|/**      * The Individual representing the active state of a locally managed Entity      */
name|entityStateActive
argument_list|(
literal|null
argument_list|,
literal|"entityState-active"
argument_list|)
block|,
comment|/**      * The Individual representing the depreciated state of a locally managed Entity      */
name|entityStateDepreciated
argument_list|(
literal|null
argument_list|,
literal|"entityState-depreciated"
argument_list|)
block|,
comment|/**      * The Individual representing the proposed state of a locally managed Entity      */
name|entityStateProposed
argument_list|(
literal|null
argument_list|,
literal|"entityState-proposed"
argument_list|)
block|,
comment|/**      * The Individual representing the removed state of a locally managed Entity      */
name|entityStateRemoved
argument_list|(
literal|null
argument_list|,
literal|"entityState-removed"
argument_list|)
block|,
comment|/**      * Property used to refer to mapped entities. This directly links the       * mapped entity. To get the mapping information one needs to lookup the      * Entity mapping for the source and target of this relation.<p>      */
name|mappedTo
block|,
comment|/**      * A directed mapping between two entities that holds additional       * information about the mapping      */
name|EntityMapping
block|,
comment|/**      * Property used to reference the source of the mapping       */
name|mappingSource
block|,
comment|/**      * Property used to refer to the target of the mapping      */
name|mappingTarget
block|,
comment|/**      * The property used for the state of the MappedEntity      */
name|hasMappingState
block|,
comment|/**      * The expires date of a representation      */
name|expires
block|,
comment|/**      * The Concept used to type instances of mapping states      */
name|MappingState
block|,
comment|/**      * The Individual representing the confirmed state of MappedEntities      */
name|mappingStateConfirmed
argument_list|(
literal|null
argument_list|,
literal|"mappingState-confirmed"
argument_list|)
block|,
comment|/**      * The Individual representing the expired state of MappedEntities      */
name|mappingStateExpired
argument_list|(
literal|null
argument_list|,
literal|"mappingState-expired"
argument_list|)
block|,
comment|/**      * The Individual representing the proposed state of MappedEntities      */
name|mappingStateProposed
argument_list|(
literal|null
argument_list|,
literal|"mappingState-proposed"
argument_list|)
block|,
comment|/**      * The Individual representing the rejected state of MappedEntities      */
name|mappingStateRejected
argument_list|(
literal|null
argument_list|,
literal|"mappingState-rejected"
argument_list|)
block|,
comment|/**      * The Individual representing the result set of an field query      */
name|QueryResultSet
parameter_list|(
name|NamespaceEnum
operator|.
name|entityhubQuery
parameter_list|)
operator|,
comment|/**      * The property used to link from the {@link #QueryResultSet} to the      * {@link org.apache.stanbol.entityhub.servicesapi.model.Representation} nodes.      */
constructor|queryResult(NamespaceEnum.entityhubQuery
block|)
enum|,
comment|/**      * Property used to link the Literal with the executed Query to the      * {@link #QueryResultSet}      */
name|query
argument_list|(
name|NamespaceEnum
operator|.
name|entityhubQuery
argument_list|)
operator|,
comment|/**      * The score of the result in respect to the parsed query.      */
name|resultScore
argument_list|(
name|NamespaceEnum
operator|.
name|entityhubQuery
argument_list|,
literal|"score"
argument_list|)
operator|,
comment|/**      * The id of the site the result was found      */
name|resultSite
argument_list|(
name|NamespaceEnum
operator|.
name|entityhubQuery
argument_list|)
operator|,
comment|/**      * The data type URI for the {@link org.apache.stanbol.entityhub.servicesapi.model.Reference}      * interface. Used  entityhub-model:ref      */
name|ReferenceDataType
argument_list|(
literal|null
argument_list|,
literal|"ref"
argument_list|)
operator|,
comment|/**      * The data type URI for the {@link org.apache.stanbol.entityhub.servicesapi.model.Text}      * interface. Uses entityhub-model:text      */
name|TextDataType
argument_list|(
literal|null
argument_list|,
literal|"text"
argument_list|)
operator|,
comment|/*      * Metadata for Entities      */
comment|/**      * Tells if an returned Entity represents an locally cached version      */
name|isChached
operator|,
comment|//    /**
comment|//     * Full text search field. This can be used in {@link FieldQuery} to
comment|//     * indicate that a constraint should be applied to the full text field.
comment|//     * {@link Yard} implementations will need to specially treat this field
comment|//     * (e.g. in SPARQL one need to use a variable instead if this URI)
comment|//     * @see SpecialFieldEnum#fullText
comment|//     */
comment|//    fullTextField(null,"fullText"),
comment|//    /**
comment|//     * Field that contains all {@link Reference}s of an {@link Entity}. {@link Yard}
comment|//     * implementation will need to treat this field specially. (e.g. in
comment|//     * SPARQL one needs to use a variable as property instead of this URI).
comment|//     * @see SpecialFieldEnum#references
comment|//     */
comment|//    referencesField(null,"references")
enum|;
end_enum

begin_decl_stmt
specifier|private
name|String
name|uri
decl_stmt|;
end_decl_stmt

begin_comment
comment|/**      * Initialise a new property by using the parse URI. If<code>null</code> is      * parsed, the URI is generated by using the Entityhub model namespace (      * {@link NamespaceEnum#entityhub}).      * @param uri the uri of the element      */
end_comment

begin_expr_stmt
name|RdfResourceEnum
argument_list|(
name|String
name|uri
argument_list|)
block|{
if|if
condition|(
name|uri
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|uri
operator|=
name|NamespaceEnum
operator|.
name|entityhub
operator|+
name|name
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
end_expr_stmt

begin_comment
unit|}
comment|/**      * Initialise a new property with the namespace and the {@link #name()} as      * local name.      * @param ns the namespace of the property or<code>null</code> to use the      * default namespace      */
end_comment

begin_expr_stmt
unit|RdfResourceEnum
operator|(
name|NamespaceEnum
name|ns
operator|)
block|{
name|this
argument_list|(
name|ns
argument_list|,
literal|null
argument_list|)
block|;     }
comment|/**      * Initialise a new property with the parsed namespace and local name.      * @param ns the namespace of the property or<code>null</code> to use the      * default namespace      * @param localName the local name of the property or<code>null</code> to      * use the {@link #name()} as local name.      */
name|RdfResourceEnum
argument_list|(
name|NamespaceEnum
name|ns
argument_list|,
name|String
name|localName
argument_list|)
block|{
name|String
name|uri
block|;
if|if
condition|(
name|ns
operator|==
literal|null
condition|)
block|{
name|uri
operator|=
name|NamespaceEnum
operator|.
name|entityhub
operator|.
name|getNamespace
argument_list|()
expr_stmt|;
block|}
end_expr_stmt

begin_else
else|else
block|{
name|uri
operator|=
name|ns
operator|.
name|getNamespace
argument_list|()
expr_stmt|;
block|}
end_else

begin_if
if|if
condition|(
name|localName
operator|==
literal|null
condition|)
block|{
name|uri
operator|=
name|uri
operator|+
name|name
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|uri
operator|=
name|uri
operator|+
name|localName
expr_stmt|;
block|}
end_if

begin_expr_stmt
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
end_expr_stmt

begin_comment
unit|}
comment|/**      * Initialise a new property with {@link NamespaceEnum#entityhub}) as namespace      * and the {@link #name()} as local name.      */
end_comment

begin_expr_stmt
unit|RdfResourceEnum
operator|(
operator|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
block|;     }
comment|/**      * Getter for the Unicode character of the URI      * @return      */
specifier|public
name|String
name|getUri
argument_list|()
block|{
return|return
name|uri
return|;
block|}
end_expr_stmt

begin_function
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
end_function

unit|}
end_unit

