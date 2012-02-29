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
name|contenthub
operator|.
name|store
operator|.
name|solr
operator|.
name|util
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|contenthub
operator|.
name|servicesapi
operator|.
name|store
operator|.
name|vocabulary
operator|.
name|SolrVocabulary
operator|.
name|SolrFieldName
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
comment|/**  *   * @author anil.sinaci  *   */
end_comment

begin_class
specifier|public
class|class
name|QueryGenerator
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|QueryGenerator
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|getFieldQuery
parameter_list|(
name|SolrFieldName
name|fieldName
parameter_list|)
block|{
switch|switch
condition|(
name|fieldName
condition|)
block|{
case|case
name|PLACES
case|:
return|return
name|getPlaceEntities
argument_list|()
return|;
case|case
name|PEOPLE
case|:
return|return
name|getPersonEntities
argument_list|()
return|;
case|case
name|ORGANIZATIONS
case|:
return|return
name|getOrganizationEntities
argument_list|()
return|;
case|case
name|COUNTRIES
case|:
return|return
name|getCountries
argument_list|()
return|;
case|case
name|IMAGECAPTIONS
case|:
return|return
name|getImageCaptions
argument_list|()
return|;
case|case
name|REGIONS
case|:
return|return
name|getRegions
argument_list|()
return|;
case|case
name|GOVERNORS
case|:
return|return
name|getGovernors
argument_list|()
return|;
case|case
name|CAPITALS
case|:
return|return
name|getCapitals
argument_list|()
return|;
case|case
name|LARGESTCITIES
case|:
return|return
name|getLargestCities
argument_list|()
return|;
case|case
name|LEADERNAMES
case|:
return|return
name|getLeaderNames
argument_list|()
return|;
case|case
name|GIVENNAMES
case|:
return|return
name|getGivenNames
argument_list|()
return|;
case|case
name|KNOWNFORS
case|:
return|return
name|getKnownFors
argument_list|()
return|;
case|case
name|BIRTHPLACES
case|:
return|return
name|getBirthPlaces
argument_list|()
return|;
case|case
name|PLACEOFBIRTHS
case|:
return|return
name|getPlacesOfBirths
argument_list|()
return|;
case|case
name|WORKINSTITUTIONS
case|:
return|return
name|getWorkInstitutions
argument_list|()
return|;
case|case
name|CAPTIONS
case|:
return|return
name|getCaptions
argument_list|()
return|;
case|case
name|SHORTDESCRIPTIONS
case|:
return|return
name|getShortDescriptions
argument_list|()
return|;
case|case
name|FIELDS
case|:
return|return
name|getFields
argument_list|()
return|;
default|default:
name|logger
operator|.
name|error
argument_list|(
literal|"No SPARQL query for the fieldName: {}"
argument_list|,
name|fieldName
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
specifier|final
name|String
name|getPlaceEntities
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|PLACES
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-type<http://dbpedia.org/ontology/Place>.\n"
operator|+
literal|" ?enhancement fise:entity-label ?"
operator|+
name|SolrFieldName
operator|.
name|PLACES
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|public
specifier|static
specifier|final
name|String
name|getPersonEntities
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|PEOPLE
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-type<http://dbpedia.org/ontology/Person>.\n"
operator|+
literal|" ?enhancement fise:entity-label ?"
operator|+
name|SolrFieldName
operator|.
name|PEOPLE
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|public
specifier|static
specifier|final
name|String
name|getOrganizationEntities
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|ORGANIZATIONS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-type<http://dbpedia.org/ontology/Organisation>.\n"
operator|+
literal|" ?enhancement fise:entity-label ?"
operator|+
name|SolrFieldName
operator|.
name|ORGANIZATIONS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getCountries
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbont:<http://dbpedia.org/ontology/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|COUNTRIES
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbont:country ?"
operator|+
name|SolrFieldName
operator|.
name|COUNTRIES
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getImageCaptions
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|IMAGECAPTIONS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:imageCaption ?"
operator|+
name|SolrFieldName
operator|.
name|IMAGECAPTIONS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getRegions
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|REGIONS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:region ?"
operator|+
name|SolrFieldName
operator|.
name|REGIONS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getGovernors
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|GOVERNORS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:governor ?"
operator|+
name|SolrFieldName
operator|.
name|GOVERNORS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getCapitals
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|CAPITALS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:capital ?"
operator|+
name|SolrFieldName
operator|.
name|CAPITALS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getLargestCities
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|LARGESTCITIES
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:largestCity ?"
operator|+
name|SolrFieldName
operator|.
name|LARGESTCITIES
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getLeaderNames
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|LEADERNAMES
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:leaderName ?"
operator|+
name|SolrFieldName
operator|.
name|LEADERNAMES
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getGivenNames
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|GIVENNAMES
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity foaf:givenName ?"
operator|+
name|SolrFieldName
operator|.
name|GIVENNAMES
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getKnownFors
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|KNOWNFORS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:knownFor ?"
operator|+
name|SolrFieldName
operator|.
name|KNOWNFORS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getBirthPlaces
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbont:<http://dbpedia.org/ontology/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|BIRTHPLACES
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbont:birthPlace ?"
operator|+
name|SolrFieldName
operator|.
name|BIRTHPLACES
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getPlacesOfBirths
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|PLACEOFBIRTHS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:placeOfBirth ?"
operator|+
name|SolrFieldName
operator|.
name|PLACEOFBIRTHS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getWorkInstitutions
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|WORKINSTITUTIONS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:workInstitutions ?"
operator|+
name|SolrFieldName
operator|.
name|WORKINSTITUTIONS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getCaptions
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|CAPTIONS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:caption ?"
operator|+
name|SolrFieldName
operator|.
name|CAPTIONS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getShortDescriptions
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbprop:<http://dbpedia.org/property/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|SHORTDESCRIPTIONS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbprop:shortDescription ?"
operator|+
name|SolrFieldName
operator|.
name|SHORTDESCRIPTIONS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|getFields
parameter_list|()
block|{
name|String
name|query
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/>\n"
operator|+
literal|"PREFIX dc:<http://purl.org/dc/terms/>\n"
operator|+
literal|"PREFIX dbont:<http://dbpedia.org/ontology/>\n"
operator|+
literal|"SELECT DISTINCT ?"
operator|+
name|SolrFieldName
operator|.
name|FIELDS
operator|.
name|toString
argument_list|()
operator|+
literal|" WHERE { \n"
operator|+
literal|" ?enhancement a fise:EntityAnnotation.\n"
operator|+
literal|" ?enhancement dc:relation ?textEnh.\n"
operator|+
literal|" ?textEnh a fise:TextAnnotation.\n"
operator|+
literal|" ?enhancement fise:entity-reference ?entity.\n"
operator|+
literal|" ?entity dbont:field ?"
operator|+
name|SolrFieldName
operator|.
name|FIELDS
operator|.
name|toString
argument_list|()
operator|+
literal|".\n"
operator|+
literal|"}\n"
decl_stmt|;
return|return
name|query
return|;
block|}
comment|/* 	 * public static final String getExternalPlacesQuery() { 	 *  	 * String query = "PREFIX fise:<http://fise.iks-project.eu/ontology/>\n" + 	 * "PREFIX pf:<http://jena.hpl.hp.com/ARQ/property#>\n" + 	 * "PREFIX dc:<http://purl.org/dc/terms/>\n" + "SELECT distinct ?ref \n" 	 * + "WHERE {\n" + "  ?enhancement a fise:EntityAnnotation .\n" + 	 * "  ?enhancement dc:relation ?textEnh.\n" + 	 * "  ?enhancement fise:entity-label ?label.\n" + 	 * "  ?textEnh a fise:TextAnnotation .\n" + 	 * "  ?enhancement fise:entity-type ?type.\n" + 	 * "  ?enhancement fise:entity-reference ?ref.\n" + 	 * "FILTER sameTerm(?type,<http://dbpedia.org/ontology/Place>) }\n" + 	 * "ORDER BY DESC(?extraction_time)"; 	 *  	 * return query; } 	 */
specifier|public
specifier|static
specifier|final
name|String
name|getEnhancementsOfContent
parameter_list|(
name|String
name|contentID
parameter_list|)
block|{
name|String
name|enhancementQuery
init|=
literal|"PREFIX fise:<http://fise.iks-project.eu/ontology/> "
operator|+
literal|"SELECT DISTINCT ?enhID WHERE { "
operator|+
literal|"  { ?enhID fise:extracted-from ?contentID . } UNION "
operator|+
literal|"  { ?enhancement fise:extracted-from ?contentID . "
operator|+
literal|"		 ?enhancement a fise:EntityAnnotation . "
operator|+
literal|"		 ?enhancement fise:entity-reference ?enhID . } "
operator|+
literal|"    FILTER sameTerm(?contentID,<"
operator|+
name|contentID
operator|+
literal|">) "
operator|+
literal|"}"
decl_stmt|;
return|return
name|enhancementQuery
return|;
block|}
comment|/* 	 * public static final String getRecentlyEnhancedDocuments(int pageSize, int 	 * offset) { String query = 	 * "PREFIX enhancer:<http://fise.iks-project.eu/ontology/> " + 	 * "PREFIX dc:<http://purl.org/dc/terms/> " + 	 * "SELECT DISTINCT ?content WHERE { " + 	 * "  ?enhancement enhancer:extracted-from ?content ." + 	 * "  ?enhancement dc:created ?extraction_time . } " + 	 * "ORDER BY DESC(?extraction_time) LIMIT %d OFFSET %d"; return 	 * String.format(query, pageSize, offset); } 	 */
block|}
end_class

end_unit

