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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
package|;
end_package

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
name|Graph
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

begin_comment
comment|/**  * This interface aims to enable different implementations to annotate external RDF data with CMS Vocabulary  * annotations defined in {@link CMSAdapterVocabulary} and generating RDF from the content repository. RDF  * bridges represent a bidirectional transformation between a content repository and an RDF data.  *<p>  * {@link RDFMapper}s are expected to store the RDF data annotated by bridges into the repository and generate  * a base RDF again to be annotated by the bridges.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RDFBridge
block|{
comment|/**      * Annotates a {@link Graph} with the properties defined in {@link CMSAdapterVocabulary}. The annotated      * graph is expected to contain annotations indicating CMS objects to be created or updated. Furthermore,      * those CMS objects can have parent assertions to indicate an hierarchy.      *<p>      * In the following RDF, bold resources show possible CMS Vocabulary annotations over an external RDF      * data.      *<p>      *       *<pre>      *<font size="3">      *&lt;rdf:RDF      *  xml:base="http://www.example.org#"      *  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"      *  xmlns:foaf="http://xmlns.com/foaf/0.1/"      *  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"      *  xmlns:cms="http://org.apache.stanbol/cms/"      *  xmlns:dbp-prop="http://dbpedia.org/property/":><br>      *&lt;rdf:Description rdf:about="#TomHanks">      *&lt;rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>      *&lt;foaf:givenname rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Tom&lt;/foaf:givenname>      *&lt;foaf:homepage rdf:datatype="http://www.w3.org/2001/XMLSchema#string">http://www.tomhanks-online.com&lt;/foaf:homepage>      *&lt;rdfs:comment rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Famous actor&lt;/rdfs:comment>      *&lt;dbp-prop:child rdf:resource="#ColinHanks"/>      *<b>&lt;rdf:type rdf:resource="http://org.apache.stanbol/cms#CMSObject"/>      *&lt;cms:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">TomHanks&lt;/cms:name></b>      *&lt;/rdf:Description>      *       *&lt;rdf:Description rdf:about="#ColinHanks">      *&lt;rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>      *&lt;foaf:givenname rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Colin&lt;/foaf:givenname>      *&lt;foaf:homepage rdf:datatype="http://www.w3.org/2001/XMLSchema#string">http://colin-hanks.net/&lt;/foaf:homepage>      *&lt;rdfs:comment rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Son of Tom Hanks&lt;/rdfs:comment>      *<b>&lt;rdf:type rdf:resource="http://org.apache.stanbol/cms#CMSObject"/>      *&lt;cms:parentRef rdf:resource="#TomHanks"/>      *&lt;cms:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">son&lt;/cms:name></b>      *&lt;/rdf:Description>      *&lt;/rdf:RDF>      *</font>      *</pre>      *       * @param rawRDF      *            {@link Graph} instance to be annotated      * @return annotated {@link MGraph}.      */
name|MGraph
name|annotateGraph
parameter_list|(
name|Graph
name|rawRDF
parameter_list|)
function_decl|;
comment|/**      * This method takes RDF data by an {@link MGraph} and annotates it in a reverse way of      * {@link #annotateGraph(Graph)}. It takes an CMS vocabulary annotated RDF and adds related assertions      * based on the internal implementation.      *<p>      * In the following example bold assertions show some possible annotations by this method      *       *<pre>      *<font size="3">      *&lt;rdf:RDF      *  xml:base="http://www.example.org#"      *  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"      *  xmlns:foaf="http://xmlns.com/foaf/0.1/"      *  xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"      *  xmlns:cms="http://org.apache.stanbol/cms/"      *&lt;dbp-prop:child rdf:resource="#ColinHanks"/><br>      *&lt;rdf:Description rdf:about="#TomHanks">      *<b>&lt;rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>      *&lt;foaf:givenname rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Tom&lt;/foaf:givenname>      *&lt;dbp-prop:child rdf:resource="#ColinHanks"/></b>      *&lt;foaf:homepage rdf:datatype="http://www.w3.org/2001/XMLSchema#string">http://www.tomhanks-online.com&lt;/foaf:homepage>      *&lt;rdfs:comment rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Famous actor&lt;/rdfs:comment>      *&lt;rdf:type rdf:resource="http://org.apache.stanbol/cms#CMSObject"/>      *&lt;cms:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">TomHanks&lt;/cms:name>      *&lt;/rdf:Description>      *       *&lt;rdf:Description rdf:about="#ColinHanks">      *<b>&lt;rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>      *&lt;foaf:givenname rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Colin&lt;/foaf:givenname></b>      *&lt;foaf:homepage rdf:datatype="http://www.w3.org/2001/XMLSchema#string">http://colin-hanks.net/&lt;/foaf:homepage>      *&lt;rdfs:comment rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Son of Tom Hanks&lt;/rdfs:comment>      *&lt;rdf:type rdf:resource="http://org.apache.stanbol/cms#CMSObject"/>      *&lt;cms:parentRef rdf:resource="#TomHanks"/>      *&lt;cms:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string">son&lt;/cms:name>      *&lt;/rdf:Description>      *&lt;/rdf:RDF>      *</font>      *</pre>      *       * @param graph      *            {@link MGraph} instance to be enhanced by this bridges      */
name|void
name|annotateCMSGraph
parameter_list|(
name|MGraph
name|graph
parameter_list|)
function_decl|;
comment|/**      * Specifies the content repository path to be affected by this bridge. For example, when a request to      * extract an RDF from the repository is done, CMS objects under this path will be converted to the RDF.      *<p>      * Path annotations should also be done according to this path configuration. Resultant paths are handled      * by {@link RDFMapper}.      *       * @return content repository path configuration      */
name|String
name|getCMSPath
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

