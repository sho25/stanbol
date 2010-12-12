begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
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
name|eu
operator|.
name|iksproject
operator|.
name|rick
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
comment|/**      * The Sign concept      */
name|Sign
block|,
comment|/**      * The field used to store the type of the representation      */
name|signType
block|,
comment|/**      * The site that defines/manages a sign      */
name|signSite
block|,
comment|/**      * The ranking of the entity by this site in the value range of [0..1]      * A sign with the rank 1 would be (one of) the most important entities      * managed by this Site. A sign with rank 0 has no relevance. Even that this      * is still within the value range one could wonder why this site does      * even manage a representation about that entity.      */
name|signRank
block|,
comment|/**      * The representation of the Sign (domain=Sign, range=Representation).      */
name|signRepresentation
block|,
comment|/**      * The Symbol concept      */
name|Symbol
block|,
comment|/**      * The label of a Symbol      */
name|label
block|,
comment|/**      * The description of a Symbol      */
name|description
block|,
comment|/**      * Predecessors of a Symbol      */
name|predecessor
block|,
comment|/**      * Successors of a Symbol      */
name|successor
block|,
comment|/**      * The property used for the state of the symbol      */
name|hasSymbolState
block|,
comment|/**      * The Concept used to type instances of SymbolStates      */
name|SymbolState
block|,
comment|/**      * The Individual representing the active state of a Symbol      */
name|symbolStateActive
argument_list|(
literal|null
argument_list|,
literal|"symbolState-active"
argument_list|)
block|,
comment|/**      * The Individual representing the depreciated state of a Symbol      */
name|symbolStateDepreciated
argument_list|(
literal|null
argument_list|,
literal|"symbolState-depreciated"
argument_list|)
block|,
comment|/**      * The Individual representing the proposed state of a Symbol      */
name|symbolStateProposed
argument_list|(
literal|null
argument_list|,
literal|"symbolState-proposed"
argument_list|)
block|,
comment|/**      * The Individual representing the removed state of a Symbol      */
name|symbolStateRemoved
argument_list|(
literal|null
argument_list|,
literal|"symbolState-removed"
argument_list|)
block|,
comment|/**      * Property used to reference MappedEntites mapped to a Symbol      */
name|hasMapping
block|,
comment|/**      * A EntityMapping that links an Entity to a Symbol      */
name|EntityMapping
block|,
comment|/**      * Property used to reference the mapped entity.      */
name|mappedEntity
block|,
comment|/**      * Property used to reference the mapped symbol      */
name|mappedSymbol
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
name|rickQuery
parameter_list|)
operator|,
comment|/**      * The property used to link from the {@link #QueryResultSet} to the      * {@link eu.iksproject.rick.servicesapi.model.Representation} nodes.      */
constructor|queryResult(NamespaceEnum.rickQuery
block|)
enum|,
comment|/**      * The score of the result in respect to the parsed query.      */
name|resultScore
argument_list|(
name|NamespaceEnum
operator|.
name|rickQuery
argument_list|,
literal|"score"
argument_list|)
operator|,
comment|/**      * The id of the site the result was found      */
name|resultSite
argument_list|(
name|NamespaceEnum
operator|.
name|rickQuery
argument_list|)
operator|,
comment|/**      * The data type URI for the {@link eu.iksproject.rick.servicesapi.model.Reference}      * interface. Used  rick-model:ref      */
name|ReferenceDataType
argument_list|(
literal|null
argument_list|,
literal|"ref"
argument_list|)
operator|,
comment|/**      * The data type URI for the {@link eu.iksproject.rick.servicesapi.model.Text}      * interface. Uses rick-model:text      */
name|TextDataType
argument_list|(
literal|null
argument_list|,
literal|"text"
argument_list|)
operator|,
enum|;
end_enum

begin_decl_stmt
name|String
name|uri
decl_stmt|;
end_decl_stmt

begin_comment
comment|/**      * Initialise a new property by using the parse URI. If<code>null</code> is      * parsed, the URI is generated by using the rick namespace (      * {@link NamespaceEnum#rickModel}).      * @param uri the uri of the element      */
end_comment

begin_constructor
specifier|private
name|RdfResourceEnum
parameter_list|(
name|String
name|uri
parameter_list|)
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
name|rickModel
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
block|}
end_constructor

begin_comment
comment|/**      * Initialise a new property with the namespace and the {@link #name()} as      * local name.      * @param ns the namespace of the property or<code>null</code> to use the      * default namespace      */
end_comment

begin_constructor
specifier|private
name|RdfResourceEnum
parameter_list|(
name|NamespaceEnum
name|ns
parameter_list|)
block|{
name|this
argument_list|(
name|ns
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
end_constructor

begin_comment
comment|/**      * Initialise a new property with the parsed namespace and local name.      * @param ns the namespace of the property or<code>null</code> to use the      * default namespace      * @param localName the local name of the property or<code>null</code> to      * use the {@link #name()} as local name.      */
end_comment

begin_constructor
specifier|private
name|RdfResourceEnum
parameter_list|(
name|NamespaceEnum
name|ns
parameter_list|,
name|String
name|localName
parameter_list|)
block|{
name|String
name|uri
decl_stmt|;
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
name|rickModel
operator|.
name|getNamespace
argument_list|()
expr_stmt|;
block|}
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
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
block|}
end_constructor

begin_comment
comment|/**      * Initialise a new property with {@link NamespaceEnum#rickModel}) as namespace      * and the {@link #name()} as local name.      */
end_comment

begin_constructor
specifier|private
name|RdfResourceEnum
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
end_constructor

begin_comment
comment|/**      * Getter for the Unicode character of the URI      * @return      */
end_comment

begin_function
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
end_function

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
operator|.
name|toString
argument_list|()
return|;
block|}
end_function

unit|}
end_unit

