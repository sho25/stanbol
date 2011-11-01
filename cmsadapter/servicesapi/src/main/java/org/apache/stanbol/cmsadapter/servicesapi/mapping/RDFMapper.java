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
name|UriRef
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
comment|/**  * Goal of this interface is to provide a uniform mechanism to store RDF data to JCR or CMIS repositories  * based on CMS vocabulary annotations on top of the raw RDF. CMS vocabulary annotations are basically a few  * {@link UriRef}s defined in {@link CMSAdapterVocabulary} indicating content repository information.  *<p>  * See {@link #storeRDFinRepository(Object, MGraph)} and  * {@link #generateRDFFromRepository(Object, String, MGraph)} to learn behavior of this interface. Former  * method updates the content repository according to annotated RDF and the latter one generates an annotated  * RDF based on the content repository.  *   * @author suat  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|RDFMapper
block|{
comment|/**      * This method stores the data passed within an {@link MGraph} to repository according      * "CMS vocabulary annotations".      *<p>      * The only required annotation that this method handles is {@link CMSAdapterVocabulary#CMS_OBJECT}      * assertions. This method should create each resource having this assertion as its rdf:type should be      * created as a node/object in the repository.      *<p>      * The name of the CMS object to be created is first checked in      * {@link CMSAdapterVocabulary#CMS_OBJECT_NAME} assertion. If the resource has not this assertion, the      * name of the CMS object is set as the URI of the resource.      *<p>      * The location of the CMS object in the content repository is specified through the      * {@link CMSAdapterVocabulary#CMS_OBJECT_PATH} assertion. If the resource has not this assertion the path      * value is set with its name together with a preceding "/" character e.g "/"+name      *<p>      * Hierarchy between CMS object is set up by the {@link CMSAdapterVocabulary#CMS_OBJECT_PARENT_REF}      * assertions. CMS objects are started to be created from the root object and based on this assertions      * children are created.      *<p>      *       * @param session      *            This is a session object which is used to interact with JCR or CMIS repositories      * @param annotatedGraph      *            This {@link MGraph} object is an enhanced version of raw RDF data with "CMS vocabulary"      *            annotations      * @throws RDFBridgeException      */
name|void
name|storeRDFinRepository
parameter_list|(
name|Object
name|session
parameter_list|,
name|MGraph
name|annotatedGraph
parameter_list|)
throws|throws
name|RDFBridgeException
function_decl|;
comment|/**      * This method generates an RDF from the part specified with a path of the content repository. It      * transforms CMS objects into resources having {@link CMSAdapterVocabulary#CMS_OBJECT} rdf:type value. It      * also transforms properties and types of the CMS object into the RDF. Furthermore, parent assertions are      * added through the {@link CMSAdapterVocabulary#CMS_OBJECT_PARENT_REF}.      *       * @param baseURI      *            Base URI for the RDF to be generated      * @param session      *            This is a session object which is used to interact with JCR or CMIS repositories      * @param rootPath      *            Content repository path which is the root path indicating the root CMS object that will be      *            transformed into RDF together with its children      * @return annotated {@link MGraph}      * @throws RDFBridgeException      */
name|MGraph
name|generateRDFFromRepository
parameter_list|(
name|String
name|baseURI
parameter_list|,
name|Object
name|session
parameter_list|,
name|String
name|rootPath
parameter_list|)
throws|throws
name|RDFBridgeException
function_decl|;
comment|/**      * This method determines certain implementation of this interface is able to generate RDF from repository      * or update the repository based on the given RDF.      *       * @param connectionType      *            connection type for which an {@link RDFMapper} is requested      * @return whether this implementation can handle specified connection type      */
name|boolean
name|canMap
parameter_list|(
name|String
name|connectionType
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

