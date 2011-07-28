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
comment|/**  * Defines commonly used name spaces to prevent multiple definitions in several  * classes  * @author Rupert Westenthaler  *  */
end_comment

begin_enum
specifier|public
enum|enum
name|NamespaceEnum
block|{
comment|//Namespaces defined by the entityhub
name|entityhubModel
argument_list|(
literal|"entityhub"
argument_list|,
literal|"http://www.iks-project.eu/ontology/rick/model/"
argument_list|)
block|,
name|entityhubQuery
argument_list|(
literal|"entityhub-query"
argument_list|,
literal|"http://www.iks-project.eu/ontology/rick/query/"
argument_list|)
block|,
comment|//First the XML Namespaces
name|xsd
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema#"
argument_list|)
block|,
name|xsi
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema-instance#"
argument_list|)
block|,
name|xml
argument_list|(
literal|"http://www.w3.org/XML/1998/namespace#"
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
comment|// Entityhub prefers DC-Terms, therefore use the "dc" prefix for the terms name space
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
name|swrc
argument_list|(
literal|"http://swrc.ontoware.org/ontology#"
argument_list|)
block|,
comment|//The Semantic Web for Research Communities Ontology
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
block|,
comment|//copyright and license
name|cc
argument_list|(
literal|"http://creativecommons.org/ns#"
argument_list|)
block|,
comment|//Schema.org (see http://schema.org/docs/schemaorg.owl for the Ontology)
name|schema
argument_list|(
literal|"http://schema.org/"
argument_list|,
literal|true
argument_list|)
block|,     ;
comment|/**      * The logger      */
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
name|NamespaceEnum
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|String
name|ns
decl_stmt|;
specifier|private
specifier|final
name|String
name|prefix
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|defaultPrefix
decl_stmt|;
comment|/**      * Defines a namespace that used the {@link #name()} as prefix.      * @param ns the namespace. MUST NOT be NULL nor empty      */
name|NamespaceEnum
parameter_list|(
name|String
name|ns
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|ns
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * Defines a namespace by using the {@link #name()} as prefix. If      *<code>true</code> is parsed a second parameter this namespace is marked      * as the default<p>      *<b>NOTE:</b> Only a single namespace can be defined as default. In case      * multiple namespaces are marked as default the one with the lowest      * {@link #ordinal()} will be used as default. This will be the topmost entry      * in this enumeration.      * @param ns the namespace. MUST NOT be<code>null</code> nor empty      * @param defaultPrefix the default namespace indicator      */
name|NamespaceEnum
parameter_list|(
name|String
name|ns
parameter_list|,
name|boolean
name|defaultPrefix
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|ns
argument_list|,
name|defaultPrefix
argument_list|)
expr_stmt|;
block|}
comment|/**      * Defines a namespace with a customised prefix. This should be used if the      * prefix needs to be different as the {@link #name()} of the enumeration      * entry.      * @param prefix the prefix. If<code>null</code> the {@link #name()} is      * used. MUST NOT be an empty string      * @param ns the namespace. MUST NOT be<code>null</code> nor empty      */
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
name|prefix
argument_list|,
name|ns
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * Defines a namespace with a customised prefix. This should be used if the      * prefix needs to be different as the {@link #name()} of the enumeration      * entry.<p>      *<b>NOTE:</b> Only a single namespace can be defined as default. In case      * multiple namespaces are marked as default the one with the lowest      * {@link #ordinal()} will be used as default. This will be the topmost entry      * in this enumeration.      * @param prefix the prefix. If<code>null</code> the {@link #name()} is      * used. MUST NOT be an empty string      * @param ns the namespace. MUST NOT be<code>null</code> nor empty      * @param defaultPrefix the default namespace indicator      */
name|NamespaceEnum
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|ns
parameter_list|,
name|boolean
name|defaultPrefix
parameter_list|)
block|{
if|if
condition|(
name|ns
operator|==
literal|null
operator|||
name|ns
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The namespace MUST NOT be NULL nor empty"
argument_list|)
throw|;
block|}
name|this
operator|.
name|ns
operator|=
name|ns
expr_stmt|;
if|if
condition|(
name|prefix
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|prefix
operator|=
name|name
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefix
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The prefix MUST NOT be emtpty."
operator|+
literal|"Use NULL to use the name or parse the prefix to use"
argument_list|)
throw|;
block|}
else|else
block|{
name|this
operator|.
name|prefix
operator|=
name|prefix
expr_stmt|;
block|}
name|this
operator|.
name|defaultPrefix
operator|=
name|defaultPrefix
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
comment|/*      * ==== Code for Lookup Methods based on Prefix and Namespace ====      */
specifier|private
specifier|final
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
specifier|final
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|NamespaceEnum
argument_list|>
name|namespace2Prefix
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|NamespaceEnum
name|defaultNamespace
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
name|NamespaceEnum
name|defaultNs
init|=
literal|null
decl_stmt|;
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
name|entry
operator|.
name|isDefault
argument_list|()
condition|)
block|{
if|if
condition|(
name|defaultNs
operator|==
literal|null
condition|)
block|{
name|defaultNs
operator|=
name|entry
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Found multiple default namespace definitions! Will use the one with the lowest ordinal value."
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"> used default: prefix:{}, namespace:{}, ordinal:{}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|defaultNs
operator|.
name|getPrefix
argument_list|()
block|,
name|defaultNs
operator|.
name|getNamespace
argument_list|()
block|,
name|defaultNs
operator|.
name|ordinal
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"> this one    : prefix:{}, namespace:{}, ordinal:{}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|entry
operator|.
name|getPrefix
argument_list|()
block|,
name|entry
operator|.
name|getNamespace
argument_list|()
block|,
name|entry
operator|.
name|ordinal
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
block|}
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
name|log
operator|.
name|debug
argument_list|(
literal|"add {} -> {} mapping"
argument_list|,
name|entry
operator|.
name|getPrefix
argument_list|()
argument_list|,
name|entry
operator|.
name|getNamespace
argument_list|()
argument_list|)
expr_stmt|;
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
name|log
operator|.
name|debug
argument_list|(
literal|"add {} -> {} mapping"
argument_list|,
name|entry
operator|.
name|getNamespace
argument_list|()
argument_list|,
name|entry
operator|.
name|getPrefix
argument_list|()
argument_list|)
expr_stmt|;
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
name|defaultNamespace
operator|=
name|defaultNs
expr_stmt|;
block|}
comment|/**      * Getter for the {@link NamespaceEnum} entry based on the string namespace      * @param namespace the name space      * @return the {@link NamespaceEnum} entry or<code>null</code> if the prased      *    namespace is not present      */
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
comment|/**      * Getter for the {@link NamespaceEnum} entry based on the prefix      * @param prefix the prefix or<code>null</code> to get the default namespace      * @return the {@link NamespaceEnum} entry or<code>null</code> if the prased      *    prefix is not present      */
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
name|prefix
operator|==
literal|null
condition|?
name|defaultNamespace
else|:
name|prefix2Namespace
operator|.
name|get
argument_list|(
name|prefix
argument_list|)
return|;
block|}
comment|/**      * Lookup if the parsed short URI (e.g "rdfs:label") uses one of the       * registered prefixes of this Enumeration of if the parsed short URI uses      * the default namespace (e.g. "name"). In case the prefix could not be found      * the parsed URI is returned unchanged      * @param shortUri the short URI      * @return the full URI if the parsed shortUri uses a prefix defined by this      * Enumeration. Otherwise the parsed value.      */
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
block|{
return|return
literal|null
return|;
block|}
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
elseif|else
if|if
condition|(
name|defaultNamespace
operator|!=
literal|null
condition|)
block|{
name|shortUri
operator|=
name|defaultNamespace
operator|.
name|getNamespace
argument_list|()
operator|+
name|shortUri
expr_stmt|;
block|}
return|return
name|shortUri
return|;
block|}
comment|/**      * Parsed the namespace of the parsed full URI by searching the last occurrence      * of '#' or '/' and than looks if the namespace is part of this enumeration.      * If a namesoace is found it is replaced by the registered prefix. If not      * the parsed URI is resturned      * @param fullUri the full uri to convert      * @return the converted URI or the parsed value of<code>null</code> was      * parsed, no local name was present (e.g. if the namespace itself was parsed)      * or the parsed namespace is not known.      */
specifier|public
specifier|static
name|String
name|getShortName
parameter_list|(
name|String
name|fullUri
parameter_list|)
block|{
if|if
condition|(
name|fullUri
operator|==
literal|null
condition|)
block|{
return|return
name|fullUri
return|;
block|}
name|int
name|index
init|=
name|Math
operator|.
name|max
argument_list|(
name|fullUri
operator|.
name|lastIndexOf
argument_list|(
literal|'#'
argument_list|)
argument_list|,
name|fullUri
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
argument_list|)
decl_stmt|;
comment|//do not convert if the parsed uri does not contain a local name
if|if
condition|(
name|index
operator|>
literal|0
operator|&&
name|index
operator|+
literal|1
operator|<
name|fullUri
operator|.
name|length
argument_list|()
condition|)
block|{
name|String
name|ns
init|=
name|fullUri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
operator|+
literal|1
argument_list|)
decl_stmt|;
name|NamespaceEnum
name|namespace
init|=
name|namespace2Prefix
operator|.
name|get
argument_list|(
name|ns
argument_list|)
decl_stmt|;
if|if
condition|(
name|namespace
operator|!=
literal|null
condition|)
block|{
return|return
name|namespace
operator|.
name|getPrefix
argument_list|()
operator|+
literal|':'
operator|+
name|fullUri
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
return|;
block|}
block|}
return|return
name|fullUri
return|;
block|}
comment|/**      * @return the defaultPrefix      */
specifier|public
name|boolean
name|isDefault
parameter_list|()
block|{
return|return
name|defaultPrefix
return|;
block|}
block|}
end_enum

end_unit

