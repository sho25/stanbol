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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|LiteralFactory
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
name|UriRef
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
name|impl
operator|.
name|TripleImpl
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
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
name|impl
operator|.
name|Util
import|;
end_import

begin_comment
comment|/**  * Provides utility classes that are used during the RDF bridging process  *   * @author suat  *   */
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
literal|"type"
argument_list|)
decl_stmt|;
specifier|public
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
specifier|public
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
specifier|public
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
specifier|public
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
specifier|public
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
specifier|public
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
specifier|public
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
specifier|public
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
specifier|public
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
specifier|public
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
specifier|private
specifier|static
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\"(.*?)\""
argument_list|)
decl_stmt|;
comment|/**      * Extracts a list of {@link NonLiteral} which indicates URIs of resources representing the root objects      * in the graph e.g the object that do not have {@code CMSAdapterVocabulary#CMS_OBJECT_PARENT_REF}      * property. Returned URIs should also be included in the candidate URIs passed as a parameter.      *       * @param candidates      *            candidate URI list      * @param graph      *            {@link MGraph} in which root URIs will be searched      * @return list of {@link NonLiteral}s      */
specifier|public
specifier|static
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|getRootObjetsOfGraph
parameter_list|(
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|candidates
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|roots
init|=
name|getRootObjectsOfGraph
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|rootsToBeReturned
init|=
operator|new
name|ArrayList
argument_list|<
name|NonLiteral
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|NonLiteral
name|root
range|:
name|roots
control|)
block|{
if|if
condition|(
name|candidates
operator|.
name|contains
argument_list|(
name|root
argument_list|)
condition|)
block|{
name|rootsToBeReturned
operator|.
name|add
argument_list|(
name|root
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|rootsToBeReturned
return|;
block|}
comment|/**      * Extracts a list of {@link NonLiteral} which indicates URIs of resources representing the root objects      * in the graph e.g the object that do not have {link CMSAdapterVocabulary#CMS_OBJECT_PARENT_REF}      * property. Returned URIs should have {@link CMSAdapterVocabulary#CMS_OBJECT_NAME} assertions which have      * value equal with the<code>path</code> parameter passed. In other words, this method determines the      * root objects under the<code>path</code> specified.      *       * @param path      *            content repository path      * @param graph      *            {@link MGraph} in which root URIs will be searched      * @return list of {@link NonLiteral}s      */
specifier|public
specifier|static
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|getRootObjectsOfGraph
parameter_list|(
name|String
name|path
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|roots
init|=
name|getRootObjectsOfGraph
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|rootsToBeReturned
init|=
operator|new
name|ArrayList
argument_list|<
name|NonLiteral
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|NonLiteral
name|root
range|:
name|roots
control|)
block|{
if|if
condition|(
name|isUnderAbsolutePath
argument_list|(
name|path
argument_list|,
name|root
argument_list|,
name|graph
argument_list|)
condition|)
block|{
name|rootsToBeReturned
operator|.
name|add
argument_list|(
name|root
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|rootsToBeReturned
return|;
block|}
comment|/**      * Extracts a list of {@link NonLiteral} which indicates URIs of resources representing the root objects      * in the graph e.g the object that do not have {link CMSAdapterVocabulary#CMS_OBJECT_PARENT_REF}      * property.      *       * @param graph      *            {@link MGraph} in which root URIs will be searched      * @return list of {@link NonLiteral}s      */
specifier|public
specifier|static
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|getRootObjectsOfGraph
parameter_list|(
name|MGraph
name|graph
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
name|graph
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
name|graph
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
name|isUnderAbsolutePath
parameter_list|(
name|String
name|path
parameter_list|,
name|NonLiteral
name|subject
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|String
name|name
init|=
name|getResourceStringValue
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|graph
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|String
name|objectPath
init|=
name|getResourceStringValue
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PATH
argument_list|,
name|graph
argument_list|)
decl_stmt|;
name|int
name|nameIndex
init|=
name|objectPath
operator|.
name|lastIndexOf
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|nameIndex
operator|==
operator|-
literal|1
condition|)
block|{
return|return
literal|false
return|;
block|}
name|String
name|precedingPath
init|=
name|objectPath
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|nameIndex
argument_list|)
decl_stmt|;
return|return
name|precedingPath
operator|.
name|contentEquals
argument_list|(
name|path
argument_list|)
operator|||
name|precedingPath
operator|.
name|contentEquals
argument_list|(
name|path
operator|+
literal|"/"
argument_list|)
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
comment|/**      * Gets lexical form of the {@link Resource} of {@link Triple} which is specified the<code>subject</code>      * and<code>propName</code> parameters if the target resource is an instance of {@link Literal}.      *       * @param subject      * @param predicate      * @param graph      * @return lexical value of specified resource it exists and an instance of {@link Literal}, otherwise it      *         returns empty string      */
specifier|public
specifier|static
name|String
name|getResourceStringValue
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|predicate
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
name|predicate
argument_list|,
name|graph
argument_list|)
decl_stmt|;
return|return
name|getResourceStringValue
argument_list|(
name|r
argument_list|)
return|;
block|}
comment|/**      * Gets lexical form of the specified {@link Resource} if it is an instance of {@link Literal}.      *       * @param r      * @return lexical value of specified resource it is not null and an instance of {@link Literal},      *         otherwise it returns empty string      */
specifier|public
specifier|static
name|String
name|getResourceStringValue
parameter_list|(
name|Resource
name|r
parameter_list|)
block|{
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
comment|/**      * Gets {@link UriRef} from the {@link Resource} of {@link Triple} which is specified by the      *<code>subject</code> and<code>propName</code> parameters if the target resource is an instance of      * {@link UriRef}.      *       * @param subject      *            subject of the target triple      * @param predicate      *            predicate of the target triple      * @param graph      *            graph which the target triple is in      * @return {@link UriRef} of resource if it exists and is instance of {@link UriRef}, otherwise      *<code>null</code>      */
specifier|public
specifier|static
name|UriRef
name|getResourceURIValue
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|predicate
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
name|predicate
argument_list|,
name|graph
argument_list|)
decl_stmt|;
return|return
name|getResourceURIValue
argument_list|(
name|r
argument_list|)
return|;
block|}
comment|/**      * Gets {@link UriRef} from the specified {@link Resource}.      *       * @param r      * @return {@link UriRef} of resource if is not<code>null</code> and instance of an {@link UriRef},      *         otherwise<code>null</code>      */
specifier|public
specifier|static
name|UriRef
name|getResourceURIValue
parameter_list|(
name|Resource
name|r
parameter_list|)
block|{
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
comment|/**      * Extracts a short URI e.g<b>skos:Concept</b> from a specified {@link Resource}.      *       * @param r      * @return short URI if the resource is an instance of {@link Literal} or {@link UriRef}      */
specifier|public
specifier|static
name|String
name|getShortURIFromResource
parameter_list|(
name|Resource
name|r
parameter_list|)
block|{
name|String
name|shortURI
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|r
operator|instanceof
name|Literal
condition|)
block|{
name|shortURI
operator|=
name|getResourceStringValue
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|r
operator|instanceof
name|UriRef
condition|)
block|{
name|UriRef
name|uri
init|=
name|getResourceURIValue
argument_list|(
name|r
argument_list|)
decl_stmt|;
name|shortURI
operator|=
name|NamespaceEnum
operator|.
name|getShortName
argument_list|(
name|removeEndCharacters
argument_list|(
name|uri
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unexpected resource type:{} of mixin type resource"
argument_list|,
name|r
argument_list|)
expr_stmt|;
block|}
return|return
name|shortURI
return|;
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
comment|/**      * Tries to separate the local name from the given {@link NonLiteral}      *       * @param uri      *            absolute URI from which local name will be extracted      * @return extracted local name      */
specifier|public
specifier|static
name|String
name|extractLocalNameFromURI
parameter_list|(
name|NonLiteral
name|subject
parameter_list|)
block|{
name|String
name|uri
init|=
name|RDFBridgeHelper
operator|.
name|removeEndCharacters
argument_list|(
name|subject
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|uri
operator|.
name|substring
argument_list|(
name|Util
operator|.
name|splitNamespace
argument_list|(
name|uri
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Add path annotations to the resources whose rdf:Type's is {@link CMSAdapterVocabulary#CMS_OBJECT}.      * Paths of objects are constructed according to {@link CMSAdapterVocabulary#CMS_OBJECT_PARENT_REF}      * annotations among the objects.      *       * @param rootPath      *            the path representing the location in the CMS. This will be added as a prefix in front of      *            the path annotations      * @param graph      *            containing the target resource to be annotated      */
specifier|public
specifier|static
name|void
name|addPathAnnotations
parameter_list|(
name|String
name|rootPath
parameter_list|,
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|candidates
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
comment|// first detect root objects
name|List
argument_list|<
name|NonLiteral
argument_list|>
name|roots
init|=
name|getRootObjetsOfGraph
argument_list|(
name|candidates
argument_list|,
name|graph
argument_list|)
decl_stmt|;
comment|// assign paths to children recursively
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
for|for
control|(
name|NonLiteral
name|root
range|:
name|roots
control|)
block|{
name|assignChildrenPaths
argument_list|(
name|rootPath
argument_list|,
name|root
argument_list|,
name|graph
argument_list|,
name|literalFactory
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|void
name|assignChildrenPaths
parameter_list|(
name|String
name|cmsRootPath
parameter_list|,
name|NonLiteral
name|root
parameter_list|,
name|MGraph
name|graph
parameter_list|,
name|LiteralFactory
name|literalFactory
parameter_list|,
name|boolean
name|firstLevel
parameter_list|)
block|{
name|String
name|rootName
init|=
name|getResourceStringValue
argument_list|(
name|root
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|graph
argument_list|)
decl_stmt|;
name|String
name|rootPath
init|=
name|cmsRootPath
decl_stmt|;
if|if
condition|(
name|firstLevel
condition|)
block|{
name|rootPath
operator|=
name|formRootPath
argument_list|(
name|cmsRootPath
argument_list|,
name|rootName
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|root
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PATH
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|rootPath
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
literal|null
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PARENT_REF
argument_list|,
name|root
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
name|NonLiteral
name|childSubject
init|=
name|it
operator|.
name|next
argument_list|()
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|String
name|childName
init|=
name|getResourceStringValue
argument_list|(
name|childSubject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|graph
argument_list|)
decl_stmt|;
name|String
name|childPath
init|=
name|formRootPath
argument_list|(
name|rootPath
argument_list|,
name|childName
argument_list|)
decl_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|childSubject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PATH
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|childPath
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assignChildrenPaths
argument_list|(
name|childPath
argument_list|,
name|childSubject
argument_list|,
name|graph
argument_list|,
name|literalFactory
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|String
name|formRootPath
parameter_list|(
name|String
name|targetRootPath
parameter_list|,
name|String
name|objectName
parameter_list|)
block|{
if|if
condition|(
operator|!
name|targetRootPath
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|targetRootPath
operator|+=
literal|"/"
expr_stmt|;
block|}
return|return
name|targetRootPath
operator|+
name|objectName
return|;
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
comment|/**      * Adds the specified<code>localName</code> at the end of the<code>baseURI</code>      *       * @param baseURI      * @param localName      * @return concatenated URI      */
specifier|public
specifier|static
name|String
name|appendLocalName
parameter_list|(
name|String
name|baseURI
parameter_list|,
name|String
name|localName
parameter_list|)
block|{
if|if
condition|(
name|baseURI
operator|.
name|endsWith
argument_list|(
literal|"#"
argument_list|)
operator|||
name|baseURI
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
return|return
name|baseURI
operator|+
name|localName
return|;
block|}
return|return
name|baseURI
operator|+
literal|"#"
operator|+
name|localName
return|;
block|}
comment|/**      * Returns if it is possible to get full URI of the specified short URI e.g<b>skos:Concept</b>      *       * @param shortURI      * @return<code>true</code> if it is possible to get full URI of the specified short URI      */
specifier|public
specifier|static
name|boolean
name|isShortNameResolvable
parameter_list|(
name|String
name|shortURI
parameter_list|)
block|{
name|String
name|fullName
init|=
name|NamespaceEnum
operator|.
name|getFullName
argument_list|(
name|shortURI
argument_list|)
decl_stmt|;
return|return
operator|!
name|fullName
operator|.
name|contentEquals
argument_list|(
name|shortURI
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|void
name|createDefaultPropertiesForRDF
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|MGraph
name|graph
parameter_list|,
name|String
name|path
parameter_list|,
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|valueCheck
argument_list|(
name|path
argument_list|)
condition|)
block|{
name|checkDefaultPropertyInitialization
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_PATH
argument_list|,
name|path
argument_list|,
name|graph
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|valueCheck
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|checkDefaultPropertyInitialization
argument_list|(
name|subject
argument_list|,
name|CMSAdapterVocabulary
operator|.
name|CMS_OBJECT_NAME
argument_list|,
name|name
argument_list|,
name|graph
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|String
name|parseStringValue
parameter_list|(
name|String
name|typedString
parameter_list|)
block|{
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|typedString
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
return|return
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|typedString
return|;
block|}
block|}
specifier|private
specifier|static
name|void
name|checkDefaultPropertyInitialization
parameter_list|(
name|NonLiteral
name|subject
parameter_list|,
name|UriRef
name|property
parameter_list|,
name|String
name|value
parameter_list|,
name|MGraph
name|graph
parameter_list|)
block|{
name|LiteralFactory
name|literalFactory
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|oldValue
init|=
name|RDFBridgeHelper
operator|.
name|getResourceStringValue
argument_list|(
name|subject
argument_list|,
name|property
argument_list|,
name|graph
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldValue
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|subject
argument_list|,
name|property
argument_list|,
name|literalFactory
operator|.
name|createTypedLiteral
argument_list|(
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|boolean
name|valueCheck
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|s
operator|!=
literal|null
operator|&&
operator|!
name|s
operator|.
name|trim
argument_list|()
operator|.
name|contentEquals
argument_list|(
literal|""
argument_list|)
return|;
block|}
block|}
end_class

end_unit

