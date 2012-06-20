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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|io
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|access
operator|.
name|TcProvider
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
name|serializedform
operator|.
name|Parser
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
name|OWLOntology
import|;
end_import

begin_class
specifier|public
class|class
name|GraphImportsClosureSource
extends|extends
name|GraphContentInputSource
implements|implements
name|SetInputSource
block|{
comment|/**      * Creates a new graph input source by parsing<code>content</code>. Every supported format will be tried      * until one is parsed successfully. The resulting graph is created in-memory, and its triples will have      * to be manually added to a stored graph if necessary.      *       * @param content      *            the serialized graph content.      */
specifier|public
name|GraphImportsClosureSource
parameter_list|(
name|InputStream
name|content
parameter_list|)
block|{
name|this
argument_list|(
name|content
argument_list|,
operator|(
name|String
operator|)
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new graph input source by parsing<code>content</code> assuming it has the given format. The      * resulting graph is created in-memory, and its triples will have to be manually added to a stored graph      * if necessary.      *       * @param content      *            the serialized graph content.      * @param formatIdentifier      *            the format to parse the content as.      */
specifier|public
name|GraphImportsClosureSource
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|String
name|formatIdentifier
parameter_list|)
block|{
name|this
argument_list|(
name|content
argument_list|,
name|formatIdentifier
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new graph input source by parsing<code>content</code> into a graph created using the      * supplied {@link TcProvider}, assuming it has the given format.      *       * @param content      *            the serialized graph content.      * @param formatIdentifier      *            the format to parse the content as.      * @param tcProvider      *            the provider that will create the graph where the triples will be stored.      */
specifier|public
name|GraphImportsClosureSource
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|String
name|formatIdentifier
parameter_list|,
name|TcProvider
name|tcProvider
parameter_list|)
block|{
name|this
argument_list|(
name|content
argument_list|,
name|formatIdentifier
argument_list|,
name|tcProvider
argument_list|,
name|Parser
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new graph input source by parsing<code>content</code> (using the supplied {@link Parser})      * into a graph created using the supplied {@link TcProvider}, assuming it has the given format.      *       * @param content      *            the serialized graph content.      * @param formatIdentifier      *            the format to parse the content as.      * @param tcProvider      *            the provider that will create the graph where the triples will be stored.      * @param parser      *            the parser to use for creating the graph. If null, the default one will be used.      */
specifier|public
name|GraphImportsClosureSource
parameter_list|(
name|InputStream
name|content
parameter_list|,
name|String
name|formatIdentifier
parameter_list|,
name|TcProvider
name|tcProvider
parameter_list|,
name|Parser
name|parser
parameter_list|)
block|{
name|super
argument_list|(
name|content
argument_list|,
name|formatIdentifier
argument_list|,
name|tcProvider
argument_list|,
name|parser
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologies
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

