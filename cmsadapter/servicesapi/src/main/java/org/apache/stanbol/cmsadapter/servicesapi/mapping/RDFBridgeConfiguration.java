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

begin_comment
comment|/**  * This interface declares the methods to create mappings between RDF data and JCR/CMIS based repositories.  * Each {@code RDFBridgeConfiguration} indicates a set of target resource in the RDF data. Resources filtered  * based on the configuration of the bridge are created as nodes/object in the repository. This interface also  * aims to get configuration values for properties and names of objects to be created in the repository.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RDFBridgeConfiguration
block|{
comment|/**      * Gets the {@link UriRef} to specify target resources for this bridge in RDF.<br>      *<br>      * For example, if this method returns<b>http://www.w3.org/1999/02/22-rdf-syntax-ns#Type</b> URI, it      * means that resources having predicate this URI will be selected for this bridge. Implementations of      * this interface should also return corresponding value for this target resource URI with      * {@link #getTargetResourceValue()}      *       * @return {@link UriRef} of predicate used to obtain resources from RDF      * @see {@link #getTargetResourceValue()}      */
name|UriRef
name|getTargetResourcePredicate
parameter_list|()
function_decl|;
comment|/**      * Gets the {@link UriRef} value for target resource returned by {@link #getTargetResourcePredicate()}.      * This method and {@link #getTargetResourcePredicate()} specify the resources that are to be created as      * nodes in the repository.<br>      *<br>      * For exapmle, if {@link #getTargetResourcePredicate()} returns      *<b>http://www.w3.org/1999/02/22-rdf-syntax-ns#Type</b> and this method returns      *<b>http://www.w3.org/2004/02/skos/core#Concept</b>, it means resources that skos:Concept value as their      * rdf:Type predicate will be created as nodes in the repository.      *       * @return {@link UriRef} of value of predicates to obtain resources from RDF      * @see {@link #getTargetResourcePredicate()}      */
name|UriRef
name|getTargetResourceValue
parameter_list|()
function_decl|;
comment|/**      * Gets the {@link UriRef} which specifies the predicate that is used for representing the name of      * selected resources. Obtained values are used names of nodes in the repository.<br>      *<br>      * Considering a triple like (s,p,o),<b>s</b> the subject of the triple is obtained using the values      * provided by {@link #getTargetResourcePredicate()} and {@link #getTargetResourceValue()} methods. And      * this method provides the predicate<b>p</b>. Eventually, object values as<b>o</b> are used as names of      * nodes in the repository.      *       * @return {@link UriRef} predicate which represents names of resources that are obtained from RDF      */
name|UriRef
name|getNameResource
parameter_list|()
function_decl|;
comment|/**      * Gets the {@link Map} of which keys represents the {@link UriRef}s predicates that will be created as      * properties of nodes in the repository. Values corresponding to those URIs represents the names of the      * properties and they should be either {@link String}s or {@link UriRef}s. String values directly used as      * the names of properties in the repository. In case of URI, corresponding name value of property is      * obtained through the triple whose subject is obtained by {@link #getTargetResourcePredicate()} and      * {@link #getTargetResourceValue()} methods and predicate is this URI.      *       * @return {@link Map} keeping the URIs and name indicators of properties to be created in the repository      */
name|Map
argument_list|<
name|UriRef
argument_list|,
name|Object
argument_list|>
name|getTargetPropertyResources
parameter_list|()
function_decl|;
comment|/**      * Gets the {@link Map} of which keys represents the {@link UriRef}s of predicates that represents      * children of resources that are obtained by {@link #getTargetResourcePredicate()} and      * {@link #getTargetResourceValue()} as in other methods of this interface. Values corresponding to those      * URIs represents the names of the child nodes and they should be either {@link String}s or      * {@link UriRef}s. String values directly used as the names of children in the repository. In case of      * URI, corresponding name value of child node is obtained through the property represented by this URI of      * child resource.<br>      *<br>      * Assume we have a resource having subject<b>s</b> and a pair in the map like      *<b>(http://www.w3.org/2004/02/skos/core#narrower, narrowConcept)</b>. In this case, child represented      * by skos:narrower will be created as child of the node created thanks to resource indicated by<b>s</b>.      * And name of the child node will narrowConcept.<br>      *<br>      * Assume we have a resource having subject<b>s</b> and a pair in the map like      *<b>(http://www.w3.org/2004/02/skos/core#narrower, http://www.w3.org/2000/01/rdf-schema#label)</b>. In      * this case, child represented by skos:narrower will be created as child of the node created thanks to      * resource indicated by<b>s</b>. Let the child resource has<b>s2</b> subject. Then child name      * represented by<b>o</b> will be the value obtained throught the triple (s2, rdfs:label, o).      *       * @return {@link Map} keeping the URIs and name indicators of child nodes to be created in the repository      */
name|Map
argument_list|<
name|UriRef
argument_list|,
name|Object
argument_list|>
name|getChildrenResources
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

