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
name|owl
operator|.
name|transformation
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Triple
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
name|impl
operator|.
name|SimpleMGraph
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
name|ParsingProvider
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
name|SerializingProvider
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
name|SupportedFormat
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
name|jena
operator|.
name|parser
operator|.
name|JenaParserProvider
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
name|jena
operator|.
name|serializer
operator|.
name|JenaSerializerProvider
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|graph
operator|.
name|Graph
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|ModelFactory
import|;
end_import

begin_comment
comment|/**  * This class provides static methods to convert:  *   *<ul>  *<li> a Jena Model (see {@link Model}) to a list of Clerezza triples (see {@link Triple})  *<li> a Jena Model to a Clerezza MGraph (see {@link MGraph})  *<li> a Clerezza MGraph a Jena Model  *<li> a Clerezza MGraph a Jena Graph (see {@link Graph}}  *</ul>  *   *   * @author andrea.nuzzolese  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|JenaToClerezzaConverter
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|JenaToClerezzaConverter
parameter_list|()
block|{}
comment|/** 	 *  	 * Converts a Jena {@link Model} to an {@link ArrayList} of Clerezza triples (instances of class {@link Triple}). 	 *  	 * @param model {@link Model} 	 * @return an {@link ArrayList} that contains the generated Clerezza triples (see {@link Triple})  	 */
specifier|public
specifier|static
name|ArrayList
argument_list|<
name|Triple
argument_list|>
name|jenaModelToClerezzaTriples
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|Triple
argument_list|>
name|clerezzaTriples
init|=
operator|new
name|ArrayList
argument_list|<
name|Triple
argument_list|>
argument_list|()
decl_stmt|;
name|MGraph
name|mGraph
init|=
name|jenaModelToClerezzaMGraph
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|tripleIterator
init|=
name|mGraph
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|tripleIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|triple
init|=
name|tripleIterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|clerezzaTriples
operator|.
name|add
argument_list|(
name|triple
argument_list|)
expr_stmt|;
block|}
return|return
name|clerezzaTriples
return|;
block|}
comment|/** 	 *  	 * Converts a Jena {@link Model} to Clerezza {@link MGraph}. 	 *  	 * @param model {@link Model} 	 * @return the equivalent Clerezza {@link MGraph}. 	 */
specifier|public
specifier|static
name|MGraph
name|jenaModelToClerezzaMGraph
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|model
operator|.
name|write
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|ByteArrayInputStream
name|in
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|out
operator|.
name|toByteArray
argument_list|()
argument_list|)
decl_stmt|;
name|ParsingProvider
name|parser
init|=
operator|new
name|JenaParserProvider
argument_list|()
decl_stmt|;
name|MGraph
name|mGraph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|parser
operator|.
name|parse
argument_list|(
name|mGraph
argument_list|,
name|in
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|,
literal|null
argument_list|)
expr_stmt|;
return|return
name|mGraph
return|;
block|}
comment|/** 	 * Converts a Clerezza {@link MGraph} to a Jena {@link Model}. 	 *  	 * @param mGraph {@link MGraph} 	 * @return the equivalent Jena {@link Model}. 	 */
specifier|public
specifier|static
name|Model
name|clerezzaMGraphToJenaModel
parameter_list|(
name|MGraph
name|mGraph
parameter_list|)
block|{
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|SerializingProvider
name|serializingProvider
init|=
operator|new
name|JenaSerializerProvider
argument_list|()
decl_stmt|;
name|serializingProvider
operator|.
name|serialize
argument_list|(
name|out
argument_list|,
name|mGraph
argument_list|,
name|SupportedFormat
operator|.
name|RDF_XML
argument_list|)
expr_stmt|;
name|ByteArrayInputStream
name|in
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|out
operator|.
name|toByteArray
argument_list|()
argument_list|)
decl_stmt|;
name|Model
name|jenaModel
init|=
name|ModelFactory
operator|.
name|createDefaultModel
argument_list|()
decl_stmt|;
name|jenaModel
operator|.
name|read
argument_list|(
name|in
argument_list|,
literal|null
argument_list|)
expr_stmt|;
return|return
name|jenaModel
return|;
block|}
comment|/** 	 * Converts a Clerezza {@link MGraph} to a Jena {@link Graph}. 	 *  	 * @param mGraph {@link MGraph} 	 * @return the equivalent Jena {@link Graph}. 	 */
specifier|public
specifier|static
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|graph
operator|.
name|Graph
name|clerezzaMGraphToJenaGraph
parameter_list|(
name|MGraph
name|mGraph
parameter_list|)
block|{
name|Model
name|jenaModel
init|=
name|clerezzaMGraphToJenaModel
argument_list|(
name|mGraph
argument_list|)
decl_stmt|;
if|if
condition|(
name|jenaModel
operator|!=
literal|null
condition|)
block|{
return|return
name|jenaModel
operator|.
name|getGraph
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

