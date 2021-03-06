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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
package|;
end_package

begin_comment
comment|/**  *   */
end_comment

begin_enum
specifier|public
enum|enum
name|NamespaceEnum
block|{
comment|/**      * The Stanbol Enhancer namespace defining Enhancer, EnhancementEngine and      * EnhancementChain. This is NOT the namespace of the enhancement structure.      * As EnhancementStrucutre up to now still the old FISE namespace is used.      */
name|enhancer
argument_list|(
literal|"http://stanbol.apache.org/ontology/enhancer/enhancer#"
argument_list|)
block|,
name|dbpedia_ont
argument_list|(
literal|"dbpedia-ont"
argument_list|,
literal|"http://dbpedia.org/ontology/"
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
name|dc
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
block|,
name|skos
argument_list|(
literal|"http://www.w3.org/2004/02/skos/core#"
argument_list|)
block|,
comment|/**     * @deprecated All none core namespaces where deprecated. Users should use     * the NamespacePrefixService (module:     * org.apache.stanbol.commons.namespaceprefixservice) instead (see also     *<a href="https://issues.apache.org/jira/browse/STANBOL-824">STANBOL-824)</a>     */
name|foaf
argument_list|(
literal|"http://xmlns.com/foaf/0.1/"
argument_list|)
block|,
comment|/**     * @deprecated All none core namespaces where deprecated. Users should use     * the NamespacePrefixService (module:     * org.apache.stanbol.commons.namespaceprefixservice) instead (see also     *<a href="https://issues.apache.org/jira/browse/STANBOL-824">STANBOL-824)</a>     */
name|geonames
argument_list|(
literal|"http://www.geonames.org/ontology#"
argument_list|)
block|,
comment|/**     * @deprecated All none core namespaces where deprecated. Users should use     * the NamespacePrefixService (module:     * org.apache.stanbol.commons.namespaceprefixservice) instead (see also     *<a href="https://issues.apache.org/jira/browse/STANBOL-824">STANBOL-824)</a>     */
name|georss
argument_list|(
literal|"http://www.georss.org/georss/"
argument_list|)
block|,
comment|/**     * @deprecated All none core namespaces where deprecated. Users should use     * the NamespacePrefixService (module:     * org.apache.stanbol.commons.namespaceprefixservice) instead (see also     *<a href="https://issues.apache.org/jira/browse/STANBOL-824">STANBOL-824)</a>     */
name|geo
argument_list|(
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#"
argument_list|)
block|,
comment|/**     * @deprecated All none core namespaces where deprecated. Users should use     * the NamespacePrefixService (module:     * org.apache.stanbol.commons.namespaceprefixservice) instead (see also     *<a href="https://issues.apache.org/jira/browse/STANBOL-824">STANBOL-824)</a>     */
name|nie
argument_list|(
literal|"http://www.semanticdesktop.org/ontologies/2007/01/19/nie#"
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
comment|/**      * The FISE namespace (1st version of the Enhancement Structure).      * Will be replaced by the Stanbol Enhancement Structure by a future      * release (see STANBOL-3).      */
name|fise
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
block|,
comment|/**      * The W3C Ontology for Media Resources http://www.w3.org/TR/mediaont-10/      * @deprecated All none core namespaces where deprecated. Users should use      * the NamespacePrefixService (module:      * org.apache.stanbol.commons.namespaceprefixservice) instead (see also      *<a href="https://issues.apache.org/jira/browse/STANBOL-824">STANBOL-824)</a>      */
name|media
argument_list|(
literal|"http://www.w3.org/ns/ma-ont#"
argument_list|)
block|,
comment|/**      * The namespace of the Apache Stanbol Entityhub      */
name|entityhub
argument_list|(
literal|"http://stanbol.apache.org/ontology/entityhub/entityhub#"
argument_list|)
block|,
comment|/**      * Namespace for Disambiguation related properties and classes (added with      * STANBOL-1053)      */
name|dis
argument_list|(
literal|"http://stanbol.apache.org/ontology/disambiguation/disambiguation#"
argument_list|)
block|,
comment|/**      * Namespace used for EnhancementProperties      * @since 0.12.1      */
name|ehp
argument_list|(
literal|"http://stanbol.apache.org/ontology/enhancementproperties#"
argument_list|)
block|;
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
comment|// TODO Auto-generated method stub
return|return
name|ns
return|;
block|}
block|}
end_enum

end_unit

