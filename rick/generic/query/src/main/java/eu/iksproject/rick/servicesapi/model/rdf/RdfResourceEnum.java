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

begin_enum
specifier|public
enum|enum
name|RdfResourceEnum
block|{
comment|/**      * The representation concept      */
name|Representation
block|,
comment|/**      * The field used to store the type of the representation      */
name|representationType
block|,
comment|/**      * The Entity concept      */
name|Entity
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
block|,     ;
name|String
name|uri
decl_stmt|;
comment|/**      * Initialise a new property by using the parse URI. If<code>null</code> is      * parsed, the URI is generated by using the rick namespace (      * {@link NamespaceEnum#rick}).      * @param uri the uri of the element      */
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
name|rick
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
comment|/**      * Initialise a new property with the namespace and the {@link #name()} as      * local name.      * @param ns the namespace of the property or<code>null</code> to use the      * default namespace      */
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
comment|/**      * Initialise a new property with the parsed namespace and local name.      * @param ns the namespace of the property or<code>null</code> to use the      * default namespace      * @param localName the local name of the property or<code>null</code> to      * use the {@link #name()} as local name.      */
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
name|rick
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
comment|/**      * Initialise a new property with {@link NamespaceEnum#rick}) as namespace      * and the {@link #name()} as local name.      */
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
comment|/**      * Getter for the Unicode character of the URI      * @return      */
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
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_enum

end_unit

