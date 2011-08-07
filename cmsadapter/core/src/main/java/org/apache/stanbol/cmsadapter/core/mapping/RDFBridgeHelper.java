begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|core
operator|.
name|mapping
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|PropertyType
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
name|rdf
operator|.
name|core
operator|.
name|Literal
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
name|rdf
operator|.
name|core
operator|.
name|MGraph
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
name|rdf
operator|.
name|core
operator|.
name|NonLiteral
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
name|rdf
operator|.
name|core
operator|.
name|Resource
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
name|rdf
operator|.
name|core
operator|.
name|Triple
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
name|rdf
operator|.
name|core
operator|.
name|TypedLiteral
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
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|CMSAdapterVocabulary
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
name|defaults
operator|.
name|NamespaceEnum
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
comment|/**  * Provides utility classes that are used parsing the RDF data  *   * @author suat  *   */
end_comment

begin_class
specifier|public
class|class
name|RDFBridgeHelper
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
name|RDFBridgeHelper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|RDF_TYPE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|rdf
operator|+
literal|"Type"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|base64Uri
init|=
name|dataTypeURI
argument_list|(
literal|"base64Binary"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|dateTimeUri
init|=
name|dataTypeURI
argument_list|(
literal|"dateTime"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|booleanUri
init|=
name|dataTypeURI
argument_list|(
literal|"boolean"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|stringUri
init|=
name|dataTypeURI
argument_list|(
literal|"string"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|xsdInteger
init|=
name|dataTypeURI
argument_list|(
literal|"integer"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|xsdInt
init|=
name|dataTypeURI
argument_list|(
literal|"int"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|xsdShort
init|=
name|dataTypeURI
argument_list|(
literal|"short"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|xsdLong
init|=
name|dataTypeURI
argument_list|(
literal|"long"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|xsdDouble
init|=
name|dataTypeURI
argument_list|(
literal|"double"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|UriRef
name|xsdAnyURI
init|=
name|dataTypeURI
argument_list|(
literal|"anyURI"
argument_list|)
decl_stmt|;
comment|/**      * Gets a list of {@link NonLiteral} which indicates URIs of resources representing the root objects in      * the graph e.g the object that do not have {@code CMSAdapterVocabulary#CMS_OBJECT_PARENT_REF} property      *       * @param annotatedGraph      * @return      */
specifier|public
specifier|static
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|getRootObjetsOfGraph
parameter_list|(
name|MGraph
name|annotatedGraph
parameter_list|)
block|{
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|roots
init|=
operator|new
name|ArrayList
argument_list|<
name|NonLiteral
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|annotatedGraph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF_TYPE
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT
argument_list|)
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|t
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|isRoot
argument_list|(
name|t
argument_list|,
name|annotatedGraph
argument_list|)
condition|)
block|{
name|roots
operator|.
name|add
argument_list|(
name|t
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|roots
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|getRootObjectsOfGraph
parameter_list|(
name|MGraph
name|annotatedGraph
parameter_list|,
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|candidates
parameter_list|)
block|{
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|roots
init|=
operator|new
name|ArrayList
argument_list|<
name|NonLiteral
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|annotatedGraph
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
name|RDF_TYPE
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT
argument_list|)
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|t
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|isRoot
argument_list|(
name|t
argument_list|,
name|annotatedGraph
argument_list|)
operator|&&
name|candidates
operator|.
name|contains
argument_list|(
name|t
operator|.
name|getSubject
argument_list|()
argument_list|)
condition|)
block|{
name|roots
operator|.
name|add
argument_list|(
name|t
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|roots
return|;
block|}
specifier|private
specifier|static
name|boolean
name|isRoot
parameter_list|(
name|Triple
name|cmsObjectTriple
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|NonLiteral
name|subject
init|=
name|cmsObjectTriple
operator|.
name|getSubject
argument_list|()
decl_stmt|;
if|if
condition|(
name|graph
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PARENT_REF
argument_list|,
literal|null
argument_list|)
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
comment|/**      * Gets {@link Resource} of {@code Triple} which is specified the<code>subject</code> and      *<code>propName</code> parameters      *       * @param subject      * @param propName      * @param graph      * @return specified resource if it exists, otherwise it returns<code>null</code>      */
specifier|public
specifier|static
name|Resource
name|getResource
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|propName
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|graph
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
name|propName
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
name|it
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
return|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"No triple for subject: {} and property: {}"
argument_list|,
name|subject
argument_list|,
name|propName
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Gets lexical form of {@code Triple} which is specified the<code>subject</code> and      *<code>propName</code> parameters if the target resource is an instance of {@link Literal}.      *       * @param subject      * @param propName      * @param graph      * @return lexical value of specified resource it exists and an instance of {@link Literal}, otherwise it      *         returns empty string      */
specifier|public
specifier|static
name|String
name|getResourceStringValue
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|propName
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|Resource
name|r
init|=
name|getResource
argument_list|(
name|subject
argument_list|,
name|propName
argument_list|,
name|graph
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|r
operator|instanceof
name|Literal
condition|)
block|{
return|return
operator|(
operator|(
name|Literal
operator|)
name|r
operator|)
operator|.
name|getLexicalForm
argument_list|()
return|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Resource value is not a Literal for subject: {} and property: {}"
argument_list|,
name|r
argument_list|)
expr_stmt|;
return|return
literal|""
return|;
block|}
block|}
else|else
block|{
return|return
literal|""
return|;
block|}
block|}
comment|/**      * Gets {@link UriRef} from the {@link Resource} of {@link Triple} which is specified the      *<code>subject</code> and<code>propName</code> parameters if the target resource is an instance of      * {@link UriRef}.      *       * @param subject      * @param propName      * @param graph      * @return {@link UriRef} of resource if it exists and is instance of {@link UriRef}      */
specifier|public
specifier|static
name|UriRef
name|getResourceURIValue
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|propName
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|Resource
name|r
init|=
name|getResource
argument_list|(
name|subject
argument_list|,
name|propName
argument_list|,
name|graph
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|r
operator|instanceof
name|UriRef
condition|)
block|{
return|return
operator|new
name|UriRef
argument_list|(
name|removeEndCharacters
argument_list|(
name|r
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Resource value is not a UriRef for subject: {} and property: {}"
argument_list|,
name|r
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
comment|/**      * Remove first {@link Triple} specified with<code>subject</code> and<code>predicate</code> parameters      * from the specified {@link MGraph}      *       * @param subject      *            {@link NonLiteral} subject of triple to be deleted      * @param predicate      *            {@link UriRef} predicate of triple to be deleted      * @param mGraph      *            {@link MGraph} from where the triple to be deleted      */
specifier|public
specifier|static
name|void
name|removeExistingTriple
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|predicate
parameter_list|,
name|MGraph
name|mGraph
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|mGraph
operator|.
name|filter
argument_list|(
name|subject
argument_list|,
name|predicate
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|mGraph
operator|.
name|remove
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Removes<b>&lt;</b> and<b>&gt;</b> characters from start and end of the string respectively      *       * @param resource      * @return      */
specifier|public
specifier|static
name|String
name|removeEndCharacters
parameter_list|(
name|String
name|resource
parameter_list|)
block|{
return|return
name|resource
operator|.
name|replace
argument_list|(
literal|"<"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|">"
argument_list|,
literal|""
argument_list|)
return|;
block|}
comment|/**      * Return related {@link PropertyType} according to data type of a {@link Resource} if it is an instance      * of {@link TypedLiteral} ot {@link UriRef}, otherwise it return {@code PropertyType#STRING} as default      * type.      *       * @param r      * @link {@link Resource} instance of which property type is demanded      * @return related {@link PropertyType}      */
specifier|public
specifier|static
name|int
name|getPropertyType
parameter_list|(
name|Resource
name|r
parameter_list|)
block|{
if|if
condition|(
name|r
operator|instanceof
name|TypedLiteral
condition|)
block|{
name|UriRef
name|type
init|=
operator|(
operator|(
name|TypedLiteral
operator|)
name|r
operator|)
operator|.
name|getDataType
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|stringUri
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|STRING
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|base64Uri
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|BINARY
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|booleanUri
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|BOOLEAN
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|dateTimeUri
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|DATE
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|xsdAnyURI
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|URI
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|xsdDouble
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|DOUBLE
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|xsdInt
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|DECIMAL
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|xsdInteger
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|DECIMAL
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|xsdLong
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|LONG
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|xsdShort
argument_list|)
condition|)
block|{
return|return
name|PropertyType
operator|.
name|DECIMAL
return|;
block|}
else|else
block|{
return|return
name|PropertyType
operator|.
name|STRING
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|r
operator|instanceof
name|UriRef
condition|)
block|{
return|return
name|PropertyType
operator|.
name|URI
return|;
block|}
else|else
block|{
return|return
name|PropertyType
operator|.
name|STRING
return|;
block|}
block|}
specifier|private
specifier|static
name|UriRef
name|dataTypeURI
parameter_list|(
name|String
name|type
parameter_list|)
block|{
return|return
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|xsd
operator|+
name|type
argument_list|)
return|;
block|}
block|}
end_class

end_unit

