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
name|ontologymanager
operator|.
name|registry
operator|.
name|xd
operator|.
name|vocabulary
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * Class Vocabulary  *   * @author Enrico Daga  *   */
end_comment

begin_enum
specifier|public
enum|enum
name|Vocabulary
block|{
name|ODP
argument_list|(
literal|"odp"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/schemas/repository.owl"
argument_list|,
literal|""
argument_list|)
block|,
name|XD_QUERY
argument_list|(
literal|"xdq"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/xd/selection/query.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Ontology Design Patterns Metadata Vocabulary 	 */
name|ODPM
argument_list|(
literal|"odpm"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/schemas/meta.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Ontology Metadata Vocabulary 	 */
name|OMV
argument_list|(
literal|"omv"
argument_list|,
literal|"http://omv.ontoware.org/2005/05/ontology"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Friend-Of-A-Friend 	 */
name|FOAF
argument_list|(
literal|"foaf"
argument_list|,
literal|"http://xmlns.com/foaf/0.1/"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * The Web Ontology Language 	 */
name|OWL
argument_list|(
literal|"owl"
argument_list|,
literal|"http://www.w3.org/2002/07/owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Simple Knowledge Organization System 	 */
name|SKOS
argument_list|(
literal|"skos"
argument_list|,
literal|"http://www.w3.org/2008/05/skos"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * eXtensible Markup Language 	 */
name|XML
argument_list|(
literal|"xml"
argument_list|,
literal|"http://www.w3.org/XML/1998/namespace"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * XML Schema Definition 	 */
name|XSD
argument_list|(
literal|"xsd"
argument_list|,
literal|"http://www.w3.org/2001/XMLSchema"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * RDFTerm Description Framework 	 */
name|RDF
argument_list|(
literal|"rdf"
argument_list|,
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * RDF Schema 	 */
name|RDFs
argument_list|(
literal|"rdfs"
argument_list|,
literal|"http://www.w3.org/2000/01/rdf-schema"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Dublin Core 	 */
name|DC
argument_list|(
literal|"dc"
argument_list|,
literal|"http://purl.org/dc/elements/1.1/"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * DC Terms 	 */
name|DT
argument_list|(
literal|"dterm"
argument_list|,
literal|"http://purl.org/dc/terms/"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Content Pattern Annotation Schema 	 */
name|CPA
argument_list|(
literal|"cpa"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/schemas/cpannotationschema.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * C-ODO core module 	 */
name|CODK
argument_list|(
literal|"codkernel"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cpont/codo/codkernel.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * C-ODO Data module 	 */
name|CODD
argument_list|(
literal|"coddata"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cpont/codo/coddata.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * C-ODO Solutions module 	 */
name|CODS
argument_list|(
literal|"cods"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cpont/codo/codsolutions.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * C-ODO Light root 	 */
name|CODL
argument_list|(
literal|"codlight"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cpont/codo/codolight.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * C-ODO Projects module 	 */
name|CODP
argument_list|(
literal|"codprojects"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cpont/codo/codprojects.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * C-ODO Tools module 	 */
name|CODT
argument_list|(
literal|"codtools"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cpont/codo/codtools.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * C-ODO Workflows module 	 */
name|CODW
argument_list|(
literal|"codworkflows"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cpont/codo/codworkflows.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Part-Of content pattern 	 */
name|PARTOF
argument_list|(
literal|"partof"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cp/owl/partof.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Descriptions and Situations content pattern 	 */
name|DESCASIT
argument_list|(
literal|"descriptionandsituation"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cp/owl/descriptionandsituation.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Intension/Extension content pattern 	 */
name|INTEXT
argument_list|(
literal|"intensionextension"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cp/owl/intensionextension.owl"
argument_list|,
literal|""
argument_list|)
block|,
comment|/** 	 * Information Objects and Representation Languages content pattern 	 */
name|REPRESENTATION
argument_list|(
literal|"representation"
argument_list|,
literal|"http://www.ontologydesignpatterns.org/cp/owl/informationobjectsandrepresentationlanguages.owl"
argument_list|,
literal|""
argument_list|)
block|;
comment|// This is the preferred prefix
specifier|public
specifier|final
name|String
name|prefix
decl_stmt|;
comment|// This is the standard URI
specifier|public
specifier|final
name|String
name|uri
decl_stmt|;
comment|// This is the location
specifier|public
specifier|final
name|String
name|url
decl_stmt|;
comment|/** 	 *  	 * @param prefix 	 * @param sUri 	 * @param sUrl 	 */
name|Vocabulary
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|sUri
parameter_list|,
name|String
name|sUrl
parameter_list|)
block|{
name|this
operator|.
name|prefix
operator|=
name|prefix
expr_stmt|;
name|this
operator|.
name|uri
operator|=
name|sUri
expr_stmt|;
name|this
operator|.
name|url
operator|=
name|sUrl
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|?
name|sUri
else|:
name|sUrl
expr_stmt|;
block|}
comment|/** 	 *  	 * @return URL 	 */
specifier|public
name|URL
name|getURL
parameter_list|()
block|{
try|try
block|{
if|if
condition|(
name|this
operator|.
name|url
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
return|return
operator|new
name|URL
argument_list|(
name|this
operator|.
name|uri
argument_list|)
return|;
return|return
operator|new
name|URL
argument_list|(
name|this
operator|.
name|url
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
comment|// This cannot happen!
return|return
literal|null
return|;
block|}
block|}
comment|/** 	 *  	 * @return URI 	 */
specifier|public
name|URI
name|getURI
parameter_list|()
block|{
return|return
name|URI
operator|.
name|create
argument_list|(
name|this
operator|.
name|uri
argument_list|)
return|;
block|}
comment|/** 	 * Default separator is '#' 	 *  	 * @return URI 	 */
specifier|public
name|URI
name|getURIWithElement
parameter_list|(
name|String
name|element
parameter_list|)
block|{
return|return
name|URI
operator|.
name|create
argument_list|(
name|this
operator|.
name|uri
operator|+
literal|"#"
operator|+
name|element
argument_list|)
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
name|this
operator|.
name|prefix
return|;
block|}
comment|/** 	 *  	 * @param uri 	 * @return String 	 */
specifier|public
specifier|static
name|String
name|getPrefix
parameter_list|(
name|URI
name|uri
parameter_list|)
block|{
for|for
control|(
name|Vocabulary
name|vocabulary
range|:
name|Vocabulary
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|vocabulary
operator|.
name|getURI
argument_list|()
operator|.
name|equals
argument_list|(
name|uri
argument_list|)
condition|)
return|return
name|vocabulary
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
literal|""
return|;
block|}
specifier|public
name|IRI
name|getIRI
parameter_list|()
block|{
return|return
name|IRI
operator|.
name|create
argument_list|(
name|this
operator|.
name|uri
argument_list|)
return|;
block|}
comment|/** 	 * @param string 	 * @return 	 */
specifier|public
name|IRI
name|getIRIWithElement
parameter_list|(
name|String
name|element
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
name|IRI
operator|.
name|create
argument_list|(
name|this
operator|.
name|uri
operator|+
literal|"#"
operator|+
name|element
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

