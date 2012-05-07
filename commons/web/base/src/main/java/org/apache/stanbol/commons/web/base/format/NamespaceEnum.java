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
name|web
operator|.
name|base
operator|.
name|format
package|;
end_package

begin_enum
specifier|public
enum|enum
name|NamespaceEnum
block|{
comment|// TODO: change the namespace as soon as STANBOL-3 defines a namespace to be used for stanbol
name|enhancer
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
block|,
annotation|@
name|Deprecated
name|rickModel
argument_list|(
literal|"http://www.iks-project.eu/ontology/rick/model/"
argument_list|)
block|,
annotation|@
name|Deprecated
name|rickQuery
argument_list|(
literal|"http://www.iks-project.eu/ontology/rick/query/"
argument_list|)
block|,
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
name|atom
argument_list|(
literal|"http://www.w3.org/2005/Atom"
argument_list|)
block|,
name|bio
argument_list|(
literal|"dc-bio"
argument_list|,
literal|"http://purl.org/vocab/bio/0.1/"
argument_list|)
block|,
name|cc
argument_list|(
literal|"http://creativecommons.org/ns#"
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
name|cmisRa
argument_list|(
literal|"cmis-ra"
argument_list|,
literal|"http://docs.oasis-open.org/ns/cmis/restatom/200908/"
argument_list|)
block|,
name|cmis
argument_list|(
literal|"http://docs.oasis-open.org/ns/cmis/core/200908/"
argument_list|)
block|,
name|foaf
argument_list|(
literal|"http://xmlns.com/foaf/0.1/"
argument_list|)
block|,
name|goodRelations
argument_list|(
literal|"gr"
argument_list|,
literal|"http://purl.org/goodrelations/v1#"
argument_list|)
block|,
name|geo
argument_list|(
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#"
argument_list|)
block|,
name|geonames
argument_list|(
literal|"http://www.geonames.org/ontology#"
argument_list|)
block|,
name|georss
argument_list|(
literal|"http://www.georss.org/georss/"
argument_list|)
block|,
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
name|nie
argument_list|(
literal|"http://www.semanticdesktop.org/ontologies/2007/01/19/nie#"
argument_list|)
block|,
name|nfo
argument_list|(
literal|"http://www.semanticdesktop.org/ontologies/2007/03/22/nfo#"
argument_list|)
block|,
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
name|rss
argument_list|(
literal|"http://purl.org/rss/1.0/"
argument_list|)
block|,
name|schema
argument_list|(
literal|"http://schema.org/"
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
name|skos
argument_list|(
literal|"http://www.w3.org/2004/02/skos/core#"
argument_list|)
block|,
name|swrc
argument_list|(
literal|"http://swrc.ontoware.org/ontology#"
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
name|xml
argument_list|(
literal|"http://www.w3.org/XML/1998/namespace#"
argument_list|)
block|,
name|xsi
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema-instance#"
argument_list|)
block|,
name|xsd
argument_list|(
literal|"http://www.w3.org/2001/XMLSchema#"
argument_list|)
block|,     ;
name|String
name|ns
decl_stmt|;
name|String
name|prefix
decl_stmt|;
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
block|}
end_enum

end_unit

