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
name|engines
operator|.
name|entitytagging
operator|.
name|impl
package|;
end_package

begin_import
import|import static
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
operator|.
name|Properties
operator|.
name|DC_TYPE
import|;
end_import

begin_import
import|import static
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
operator|.
name|Properties
operator|.
name|ENHANCER_SELECTED_TEXT
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
name|commons
operator|.
name|rdf
operator|.
name|BlankNodeOrIRI
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
name|commons
operator|.
name|rdf
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
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringUtils
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|TechnicalClasses
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

begin_class
specifier|public
specifier|final
class|class
name|NamedEntity
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|NamedEntity
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|BlankNodeOrIRI
name|entity
decl_stmt|;
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
specifier|private
specifier|final
name|IRI
name|type
decl_stmt|;
specifier|private
name|NamedEntity
parameter_list|(
name|BlankNodeOrIRI
name|entity
parameter_list|,
name|String
name|name
parameter_list|,
name|IRI
name|type
parameter_list|)
block|{
name|this
operator|.
name|entity
operator|=
name|entity
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
comment|/**      * Getter for the Node providing the information about that entity      * @return the entity      */
specifier|public
specifier|final
name|BlankNodeOrIRI
name|getEntity
parameter_list|()
block|{
return|return
name|entity
return|;
block|}
comment|/**      * Getter for the name      * @return the name      */
specifier|public
specifier|final
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**      * Getter for the type      * @return the type      */
specifier|public
specifier|final
name|IRI
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|entity
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|instanceof
name|NamedEntity
operator|&&
name|entity
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|NamedEntity
operator|)
name|o
operator|)
operator|.
name|entity
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
name|String
operator|.
name|format
argument_list|(
literal|"NamedEntity %s (name=%s|type=%s)"
argument_list|,
name|entity
argument_list|,
name|name
argument_list|,
name|type
argument_list|)
return|;
block|}
comment|/**      * Extracts the information of an {@link NamedEntity} from an      * {@link TechnicalClasses#ENHANCER_TEXTANNOTATION} instance.      * @param graph the graph with the information      * @param textAnnotation the text annotation instance      * @return the {@link NamedEntity} or<code>null</code> if the parsed      * text annotation is missing required information.      */
specifier|public
specifier|static
name|NamedEntity
name|createFromTextAnnotation
parameter_list|(
name|Graph
name|graph
parameter_list|,
name|BlankNodeOrIRI
name|textAnnotation
parameter_list|)
block|{
name|String
name|selected
init|=
name|EnhancementEngineHelper
operator|.
name|getString
argument_list|(
name|graph
argument_list|,
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|)
decl_stmt|;
if|if
condition|(
name|selected
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Unable to create NamedEntity for TextAnnotation {} "
operator|+
literal|"because property {} is not present"
argument_list|,
name|textAnnotation
argument_list|,
name|ENHANCER_SELECTED_TEXT
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|String
name|name
init|=
name|selected
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Unable to process TextAnnotation {} because its selects "
operator|+
literal|"an empty Stirng !"
argument_list|,
name|textAnnotation
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// remove punctuation form the search string
name|name
operator|=
name|cleanupKeywords
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Unable to process TextAnnotation {} because its selects "
operator|+
literal|"an stirng with punktations only (selected: {})!"
argument_list|,
name|textAnnotation
argument_list|,
name|selected
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|IRI
name|type
init|=
name|EnhancementEngineHelper
operator|.
name|getReference
argument_list|(
name|graph
argument_list|,
name|textAnnotation
argument_list|,
name|DC_TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to process TextAnnotation {} because property {}"
operator|+
literal|" is not present!"
argument_list|,
name|textAnnotation
argument_list|,
name|DC_TYPE
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
operator|new
name|NamedEntity
argument_list|(
name|textAnnotation
argument_list|,
name|name
argument_list|,
name|type
argument_list|)
return|;
block|}
comment|/**      * Removes punctuation form a parsed string      */
specifier|private
specifier|static
name|String
name|cleanupKeywords
parameter_list|(
name|String
name|keywords
parameter_list|)
block|{
return|return
name|keywords
operator|.
name|replaceAll
argument_list|(
literal|"\\p{P}"
argument_list|,
literal|" "
argument_list|)
operator|.
name|trim
argument_list|()
return|;
block|}
block|}
end_class

end_unit

