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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|format
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|MediaType
import|;
end_import

begin_comment
comment|/**  * Additional MIME types for knowledge representation formats.  *   * @author andrea.nuzzolese  * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|KRFormat
extends|extends
name|MediaType
block|{
comment|/**      * "text/owl-functional"      */
specifier|public
specifier|static
specifier|final
name|String
name|FUNCTIONAL_OWL
init|=
literal|"text/owl-functional"
decl_stmt|;
comment|/**      * "text/owl-functional"      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|FUNCTIONAL_OWL_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"text"
argument_list|,
literal|"owl-functional"
argument_list|)
decl_stmt|;
comment|/**      * "text/owl-manchester"      */
specifier|public
specifier|static
specifier|final
name|String
name|MANCHESTER_OWL
init|=
literal|"text/owl-manchester"
decl_stmt|;
comment|/**      * "text/owl-manchester"      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|MANCHESTER_OWL_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"text"
argument_list|,
literal|"owl-manchester"
argument_list|)
decl_stmt|;
comment|/**      * "text/rdf+nt"      */
specifier|public
specifier|static
specifier|final
name|String
name|N_TRIPLE
init|=
literal|"text/rdf+nt"
decl_stmt|;
comment|/**      * "text/rdf+nt"      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|N_TRIPLE_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"text"
argument_list|,
literal|"rdf+nt"
argument_list|)
decl_stmt|;
comment|/**      * "text/rdf+n3"      */
specifier|public
specifier|static
specifier|final
name|String
name|N3
init|=
literal|"text/rdf+n3"
decl_stmt|;
comment|/**      * "text/rdf+n3"      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|N3_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"text"
argument_list|,
literal|"rdf+n3"
argument_list|)
decl_stmt|;
comment|/**      * "application/owl+xml"      */
specifier|public
specifier|static
specifier|final
name|String
name|OWL_XML
init|=
literal|"application/owl+xml"
decl_stmt|;
comment|/**      * "application/owl+xml"      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|OWL_XML_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"owl+xml"
argument_list|)
decl_stmt|;
comment|/**      * "application/rdf+json"      */
specifier|public
specifier|static
specifier|final
name|String
name|RDF_JSON
init|=
literal|"application/rdf+json"
decl_stmt|;
comment|/**      * "application/rdf+json"      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|RDF_JSON_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"rdf+json"
argument_list|)
decl_stmt|;
comment|/**      * "application/rdf+xml"      */
specifier|public
specifier|static
specifier|final
name|String
name|RDF_XML
init|=
literal|"application/rdf+xml"
decl_stmt|;
comment|/**      * "application/rdf+xml"      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|RDF_XML_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"rdf+xml"
argument_list|)
decl_stmt|;
comment|/**      * "application/turtle"      */
specifier|public
specifier|static
specifier|final
name|String
name|TURTLE
init|=
literal|"application/turtle"
decl_stmt|;
comment|/**      * "application/turtle"      */
specifier|public
specifier|static
specifier|final
name|MediaType
name|TURTLE_TYPE
init|=
operator|new
name|MediaType
argument_list|(
literal|"application"
argument_list|,
literal|"turtle"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

