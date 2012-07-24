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
name|semanticindex
operator|.
name|index
package|;
end_package

begin_comment
comment|/**  * Well known RESTful endpoint types offered by {@link SemanticIndex}.  * Use {@link EndpointTypeEnum#getUri()} or {@link EndpointTypeEnum#toString()}  * to parse this endpoint types to the {@link SemanticIndexManager} interface.  */
end_comment

begin_enum
specifier|public
enum|enum
name|EndpointTypeEnum
block|{
comment|/**      * RESTful endpoint of the Solr      */
name|SOLR
argument_list|(
literal|"http://lucene.apache.org/solr"
argument_list|)
block|,
comment|/**      * RESTful search endpoint specific to the Contenthub      */
name|CONTENTHUB
argument_list|(
literal|"http://stanbol.apache.org/ontology/contenthub#endpointType_CONTENTHUB"
argument_list|)
block|,
comment|/**      * RESTful search endpoint specific to the Entityhub /query interface      */
name|ENTITYHUB_QUERY
argument_list|(
literal|"http://stanbol.apache.org/ontology/entityhub#endpointType_FIELD_QUERY"
argument_list|)
block|,
comment|/**      * RESTful search endpoint specific to the Entityhub /find interface      */
name|ENTITYHUB_FIND
argument_list|(
literal|"http://stanbol.apache.org/ontology/entityhub#endpointType_find"
argument_list|)
block|,
comment|/**      * SPARQL query interface      */
name|SPARQL
argument_list|(
literal|"http://www.w3.org/TR/rdf-sparql-query/"
argument_list|)
block|;
specifier|private
specifier|final
name|String
name|uri
decl_stmt|;
name|EndpointTypeEnum
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uri
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
name|uri
return|;
block|}
block|}
end_enum

end_unit

