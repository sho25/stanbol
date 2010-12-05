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
name|defaults
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

begin_enum
specifier|public
enum|enum
name|NamespaceEnum
block|{
comment|//Namespaces defined by RICK
name|rickModel
argument_list|(
literal|"rick"
argument_list|,
literal|"http://www.iks-project.eu/ontology/rick/model/"
argument_list|)
block|,
name|rickQuery
argument_list|(
literal|"rick-query"
argument_list|,
literal|"http://www.iks-project.eu/ontology/rick/query/"
argument_list|)
block|,
comment|//First the XML Namespaces
name|xsd
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema/"
argument_list|)
block|,
name|xsi
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema-instance/"
argument_list|)
block|,
name|xml
argument_list|(
literal|"http://www.w3.org/XML/1998/namespace/"
argument_list|)
block|,
comment|//Start with the semantic Web Namespaces
name|rdf
argument_list|(
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
argument_list|)
block|,
name|rdfs
argument_list|(
literal|"http://www.w3.org/2000/01/rdf-schema#"
argument_list|)
block|,
name|owl
argument_list|(
literal|"http://www.w3.org/2002/07/owl#"
argument_list|)
block|,
comment|//CMIS related
name|atom
argument_list|(
literal|"http://www.w3.org/2005/Atom"
argument_list|)
block|,
name|cmis
argument_list|(
literal|"http://docs.oasis-open.org/ns/cmis/core/200908/"
argument_list|)
block|,
name|cmisRa
argument_list|(
literal|"cmis-ra"
argument_list|,
literal|"http://docs.oasis-open.org/ns/cmis/restatom/200908/"
argument_list|)
block|,
comment|//now the JCR related Namespaces
name|jcr
argument_list|(
literal|"jcr"
argument_list|,
literal|"http://www.jcp.org/jcr/1.0/"
argument_list|)
block|,
name|jcrSv
argument_list|(
literal|"jcr-sv"
argument_list|,
literal|"http://www.jcp.org/jcr/sv/1.0/"
argument_list|)
block|,
name|jcrNt
argument_list|(
literal|"jcr-nt"
argument_list|,
literal|"http://www.jcp.org/jcr/nt/1.0/"
argument_list|)
block|,
name|jcrMix
argument_list|(
literal|"jcr-mix"
argument_list|,
literal|"http://www.jcp.org/jcr/mix/1.0/"
argument_list|)
block|,
comment|//Some well known Namespaces of Ontologies
name|geo
argument_list|(
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#"
argument_list|)
block|,
name|georss
argument_list|(
literal|"http://www.georss.org/georss/"
argument_list|)
block|,
name|dcElements
argument_list|(
literal|"dc-elements"
argument_list|,
literal|"http://purl.org/dc/elements/1.1/"
argument_list|)
block|,
name|dcTerms
argument_list|(
literal|"dc"
argument_list|,
literal|"http://purl.org/dc/terms/"
argument_list|)
block|,
comment|// RICK prefers DC-Terms, therefore use the "dc" prefix for the terms name space
name|foaf
argument_list|(
literal|"http://xmlns.com/foaf/0.1/"
argument_list|)
block|,
name|vCal
argument_list|(
literal|"http://www.w3.org/2002/12/cal#"
argument_list|)
block|,
name|vCard
argument_list|(
literal|"http://www.w3.org/2001/vcard-rdf/3.0#"
argument_list|)
block|,
name|skos
argument_list|(
literal|"http://www.w3.org/2004/02/skos/core#"
argument_list|)
block|,
name|sioc
argument_list|(
literal|"http://rdfs.org/sioc/ns#"
argument_list|)
block|,
name|siocTypes
argument_list|(
literal|"sioc-types"
argument_list|,
literal|"http://rdfs.org/sioc/types#"
argument_list|)
block|,
name|bio
argument_list|(
literal|"dc-bio"
argument_list|,
literal|"http://purl.org/vocab/bio/0.1/"
argument_list|)
block|,
name|rss
argument_list|(
literal|"http://purl.org/rss/1.0/"
argument_list|)
block|,
name|goodRelations
argument_list|(
literal|"gr"
argument_list|,
literal|"http://purl.org/goodrelations/v1#"
argument_list|)
block|,
comment|//Linked Data Ontologies
name|dbpediaOnt
argument_list|(
literal|"dbp-ont"
argument_list|,
literal|"http://dbpedia.org/ontology/"
argument_list|)
block|,
name|dbpediaProp
argument_list|(
literal|"dbp-prop"
argument_list|,
literal|"http://dbpedia.org/property/"
argument_list|)
block|,
name|geonames
argument_list|(
literal|"http://www.geonames.org/ontology#"
argument_list|)
block|, 	;
name|String
name|ns
decl_stmt|;
name|String
name|prefix
decl_stmt|;
specifier|private
name|NamespaceEnum
parameter_list|(
name|String
name|ns
parameter_list|)
block|{
if|if
condition|(
name|ns
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The namespace MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|ns
operator|=
name|ns
expr_stmt|;
block|}
specifier|private
name|NamespaceEnum
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|ns
parameter_list|)
block|{
name|this
argument_list|(
name|ns
argument_list|)
expr_stmt|;
name|this
operator|.
name|prefix
operator|=
name|prefix
expr_stmt|;
block|}
specifier|public
name|String
name|getNamespace
parameter_list|()
block|{
return|return
name|ns
return|;
block|}
specifier|public
name|String
name|getPrefix
parameter_list|()
block|{
return|return
name|prefix
operator|==
literal|null
condition|?
name|name
argument_list|()
else|:
name|prefix
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
name|ns
return|;
block|}
comment|/* 	 * ==== Code for Lookup Methods based on Prefix and Namespace ==== 	 */
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|NamespaceEnum
argument_list|>
name|prefix2Namespace
decl_stmt|;
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|NamespaceEnum
argument_list|>
name|namespace2Prefix
decl_stmt|;
static|static
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|NamespaceEnum
argument_list|>
name|p2n
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|NamespaceEnum
argument_list|>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|NamespaceEnum
argument_list|>
name|n2p
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|NamespaceEnum
argument_list|>
argument_list|()
decl_stmt|;
comment|//The Exceptions are only thrown to check that this Enum is configured
comment|//correctly!
for|for
control|(
name|NamespaceEnum
name|entry
range|:
name|NamespaceEnum
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|p2n
operator|.
name|containsKey
argument_list|(
name|entry
operator|.
name|getPrefix
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Prefix %s used for multiple namespaces: %s and %s"
argument_list|,
name|entry
operator|.
name|getPrefix
argument_list|()
argument_list|,
name|p2n
operator|.
name|get
argument_list|(
name|entry
operator|.
name|getPrefix
argument_list|()
argument_list|)
argument_list|,
name|entry
operator|.
name|getNamespace
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
name|p2n
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getPrefix
argument_list|()
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|n2p
operator|.
name|containsKey
argument_list|(
name|entry
operator|.
name|getNamespace
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Multiple Prefixs %s and %s for namespaces: %s"
argument_list|,
name|entry
operator|.
name|getPrefix
argument_list|()
argument_list|,
name|p2n
operator|.
name|get
argument_list|(
name|entry
operator|.
name|getNamespace
argument_list|()
argument_list|)
argument_list|,
name|entry
operator|.
name|getNamespace
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
name|n2p
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getNamespace
argument_list|()
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
name|prefix2Namespace
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|p2n
argument_list|)
expr_stmt|;
name|namespace2Prefix
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|n2p
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Getter for the {@link NamespaceEnum} entry based on the string namespace 	 * @param namespace the name space  	 * @return the {@link NamespaceEnum} entry or<code>null</code> if the prased 	 *    namespace is not present 	 */
specifier|public
specifier|static
name|NamespaceEnum
name|forNamespace
parameter_list|(
name|String
name|namespace
parameter_list|)
block|{
return|return
name|namespace2Prefix
operator|.
name|get
argument_list|(
name|namespace
argument_list|)
return|;
block|}
comment|/** 	 * Getter for the {@link NamespaceEnum} entry based on the prefix 	 * @param prefix the prefix 	 * @return the {@link NamespaceEnum} entry or<code>null</code> if the prased 	 *    prefix is not present 	 */
specifier|public
specifier|static
name|NamespaceEnum
name|forPrefix
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
return|return
name|prefix2Namespace
operator|.
name|get
argument_list|(
name|prefix
argument_list|)
return|;
block|}
comment|/** 	 * Lookup if the parsed URI uses one of the registered prefixes of this 	 * Enumeration. If this is the case, the prefix is replaced by the namespace 	 * and the full URI is returned. If no prefix is returned, the 	 * parsed URI is returned 	 * @param shortUri the short URI 	 * @return the full URI if the parsed shortUri uses a prefix defined by this 	 * Enumeration. Otherwise the parsed value. 	 */
specifier|public
specifier|static
name|String
name|getFullName
parameter_list|(
name|String
name|shortUri
parameter_list|)
block|{
if|if
condition|(
name|shortUri
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|int
name|index
init|=
name|shortUri
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>
literal|0
condition|)
block|{
name|NamespaceEnum
name|namespace
init|=
name|NamespaceEnum
operator|.
name|forPrefix
argument_list|(
name|shortUri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|namespace
operator|!=
literal|null
condition|)
block|{
name|shortUri
operator|=
name|namespace
operator|.
name|getNamespace
argument_list|()
operator|+
name|shortUri
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|shortUri
return|;
block|}
block|}
end_enum

end_unit

