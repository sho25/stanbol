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
name|commons
operator|.
name|namespaceprefix
operator|.
name|mappings
package|;
end_package

begin_comment
comment|/**  * Enumeration defining the Namespace Prefix Mappings used exposed by the   * Stanbol Default Namespace Mapping Provider.  */
end_comment

begin_enum
specifier|public
enum|enum
name|DefaultNamespaceMappingsEnum
block|{
comment|/*      * STANBOL ENHANCER       */
comment|/**      * The Stanbol Enhancer namespace defining Enhancer, EnhancementEngine and      * EnhancementChain. This is NOT the namespace of the enhancement structure.      * As EnhancementStrucutre up to now still the old FISE namespace is used.      */
name|enhancer
argument_list|(
literal|"http://stanbol.apache.org/ontology/enhancer/enhancer#"
argument_list|)
block|,
comment|/**      * The FISE namespace (1st version of the Enhancement Structure).      * Will be replaced by the Stanbol Enhancement Structure by a future      * release (see STANBOL-3).      */
name|fise
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
block|,
comment|/**      * Namespace for the Stanbol Enhancer Execution Plan ontology      */
name|ep
argument_list|(
literal|"http://stanbol.apache.org/ontology/enhancer/executionplan#"
argument_list|)
block|,
comment|/**      * Namespace for the Stanbol Enhancer Execution Metadata ontology      */
name|em
argument_list|(
literal|"http://stanbol.apache.org/ontology/enhancer/executionmetadata#"
argument_list|)
block|,
comment|/*      * STANBOL Entityhub      */
comment|/**      * The namespace of the Apache Stanbol Entityhub      */
name|entityhub
argument_list|(
literal|"http://stanbol.apache.org/ontology/entityhub/entityhub#"
argument_list|)
block|,
comment|/**      * The namespace used by the Entityhub to define query related concepts      * e.g. the full text search field, semantic context field, result score ...      */
name|entityhubQuery
argument_list|(
literal|"entityhub-query"
argument_list|,
literal|"http://stanbol.apache.org/ontology/entityhub/query#"
argument_list|)
block|,
comment|/*      * Namespaces directly referenced by Stanbol      */
comment|/**      * Stanbol Enhancement Structure uses dc:terms with the prefix 'dc'      */
name|dc
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
block|,
comment|/**      * The dbpedia ontology as used by the Enhancer for NamedEntit      */
name|dbpedia_ont
argument_list|(
literal|"dbpedia-ont"
argument_list|,
literal|"http://dbpedia.org/ontology/"
argument_list|)
block|,
comment|/**      * SKOS is used for hierarchical controlled vocabularies       */
name|skos
argument_list|(
literal|"http://www.w3.org/2004/02/skos/core#"
argument_list|)
block|,
comment|/*      * XML related namespaces      */
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
comment|/*      * Semantic Web Technology core name spaces      */
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
comment|/*      * CMS related namespaces      */
comment|//CMIS related
name|atom
argument_list|(
literal|"http://www.w3.org/2005/Atom/"
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
comment|/*      * Other Namespaces defined by Stanbol before the introduction of the      * NamespacePrefixService      */
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
name|gml
argument_list|(
literal|"http://www.opengis.net/gml/"
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
literal|"dct"
argument_list|,
literal|"http://purl.org/dc/terms/"
argument_list|)
block|,
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
comment|/**      * The Semantic Web for Research Communities Ontology      */
name|swrc
argument_list|(
literal|"http://swrc.ontoware.org/ontology#"
argument_list|)
block|,
comment|/**      * Nepomuk Information Element Ontology      */
name|nie
argument_list|(
literal|"http://www.semanticdesktop.org/ontologies/2007/01/19/nie#"
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
comment|/**      * The "dbpedia-owl" prefix was used by a single mapping of the dbpedia      * indexing tool. This was actually not intended, but as the new service does      * validate prefixes this now causes errors. So this prefix was added to the      * list. However it is not recommended to be used - hence deprecated      * @deprecated      */
name|dbpediaOnt2
argument_list|(
literal|"dbpedia-owl"
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
argument_list|)
block|,
comment|/**      * The W3C Ontology for Media Resources http://www.w3.org/TR/mediaont-10/      */
name|media
argument_list|(
literal|"http://www.w3.org/ns/ma-ont#"
argument_list|)
block|,
comment|/*      * eHealth domain       */
comment|/**      * DrugBank is a repository of almost 5000 FDA-approved small molecule and       * biotech drugs.       */
name|drugbank
argument_list|(
literal|"http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/"
argument_list|)
block|,
comment|/**      * Dailymed is published by the National Library of Medicine,       * and provides high quality information about marketed drugs.      */
name|dailymed
argument_list|(
literal|"http://www4.wiwiss.fu-berlin.de/dailymed/resource/dailymed/"
argument_list|)
block|,
comment|/**      * SIDER contains information on marketed drugs and their adverse effects.       * The information is extracted from public documents and package inserts.      */
name|sider
argument_list|(
literal|"http://www4.wiwiss.fu-berlin.de/sider/resource/sider/"
argument_list|)
block|,
comment|/**      * The Linked Clinical Trials (LinkedCT) project aims at publishing the       * first open Semantic Web data source for clinical trials data.      */
name|linkedct
argument_list|(
literal|"http://data.linkedct.org/resource/linkedct/"
argument_list|)
block|,
comment|/**      * STITCH contains information on chemicals and proteins as well as their       * interactions and links.      */
name|stitch
argument_list|(
literal|"http://www4.wiwiss.fu-berlin.de/stitch/resource/stitch/"
argument_list|)
block|,
comment|/**      * Diseasome publishes a network of 4,300 disorders and disease genes linked       * by known disorder-gene associations for exploring all known phenotype and       * disease gene associations, indicating the common genetic origin of many       * diseases.      */
name|diseasome
argument_list|(
literal|"http://www4.wiwiss.fu-berlin.de/diseasome/resource/diseasome/"
argument_list|)
block|,
comment|/**      * National Cancer Institute Thesaurus (http://www.mindswap.org/2003/CancerOntology/)      */
name|nci
argument_list|(
literal|"http://www.mindswap.org/2003/nciOncology.owl#"
argument_list|)
block|,
name|tcm
argument_list|(
literal|"http://purl.org/net/tcm/tcm.lifescience.ntu.edu.tw/"
argument_list|)
block|,
comment|/**      * The Music Ontology (http://musicontology.com/)      */
name|mo
argument_list|(
literal|"http://purl.org/ontology/mo/"
argument_list|)
block|,
comment|/**      *  The Time ontology (http://www.w3.org/TR/owl-time/)      */
name|owlTime
argument_list|(
literal|"owl-time"
argument_list|,
literal|"http://www.w3.org/2006/time#"
argument_list|)
block|,
comment|/**      *  The Event ontology (http://purl.org/NET/c4dm/event.owl#)      */
name|event
argument_list|(
literal|"http://purl.org/NET/c4dm/event.owl#"
argument_list|)
block|,
comment|/**      *  The Timeline ontology (http://purl.org/NET/c4dm/timeline.owl#)      */
name|timeline
argument_list|(
literal|"http://purl.org/NET/c4dm/timeline.owl#"
argument_list|)
block|,
comment|/**      *  Relationship: A vocabulary for describing relationships between people      *  (http://purl.org/vocab/relationship/)      */
name|rel
argument_list|(
literal|"http://purl.org/vocab/relationship/"
argument_list|)
block|,
comment|/**      *  Expression of Core FRBR Concepts in RDF (http://vocab.org/frbr/core)      */
name|frbr
argument_list|(
literal|"http://purl.org/vocab/frbr/core#"
argument_list|)
block|,
comment|/*      * Freebase namesoaces      */
comment|/**      * The freebase.com namespace      */
name|fb
argument_list|(
literal|"http://rdf.freebase.com/ns/"
argument_list|)
block|,
comment|/**      * The freebase.com key namespace. Keys are used to refer to keys used by      * for freebase topics (entities) on external sites (e.g. musicbrainz,       * wikipedia ...).      */
name|key
argument_list|(
literal|"http://rdf.freebase.com/key/"
argument_list|)
block|,
comment|/**      * The EnhancementProperties namespace as introduced by<a       * href="https://issues.apache.org/jira/browse/STANBOL-488">STANBOL-488</a>      */
name|ehp
argument_list|(
literal|"http://stanbol.apache.org/ontology/enhancementproperties#"
argument_list|)
block|,
comment|/*      * Added several mappings form prefix.cc for namespaces defined above      */
comment|/**      * Alternative to {@link #dcElements}      */
name|dce
argument_list|(
literal|"http://purl.org/dc/elements/1.1/"
argument_list|)
block|,
comment|/**      * Alternative for {@link #dbpedia_ont}      */
name|dbo
argument_list|(
literal|"http://dbpedia.org/ontology/"
argument_list|)
block|,
comment|/**      * DBpedia resources      */
name|dbr
argument_list|(
literal|"http://dbpedia.org/resource/"
argument_list|)
block|,
comment|/**      * Alternative to {@link #dbpediaProp}      */
name|dbp
argument_list|(
literal|"http://dbpedia.org/property/"
argument_list|)
block|,
comment|/**      * Alternative to {@link #geonames}      */
name|gn
argument_list|(
literal|"http://www.geonames.org/ontology#"
argument_list|)
block|;
specifier|private
name|String
name|namespace
decl_stmt|;
specifier|private
name|String
name|prefix
decl_stmt|;
name|DefaultNamespaceMappingsEnum
parameter_list|(
name|String
name|namespace
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|namespace
argument_list|)
expr_stmt|;
block|}
name|DefaultNamespaceMappingsEnum
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|namespace
parameter_list|)
block|{
name|this
operator|.
name|prefix
operator|=
name|prefix
operator|==
literal|null
condition|?
name|name
argument_list|()
else|:
name|prefix
expr_stmt|;
name|this
operator|.
name|namespace
operator|=
name|namespace
expr_stmt|;
block|}
specifier|public
name|String
name|getNamespace
parameter_list|()
block|{
return|return
name|namespace
return|;
block|}
specifier|public
name|String
name|getPrefix
parameter_list|()
block|{
return|return
name|prefix
return|;
block|}
comment|/**      * "{prefix}\t{namespace}"      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|prefix
operator|+
literal|"\t"
operator|+
name|namespace
return|;
block|}
block|}
end_enum

end_unit

