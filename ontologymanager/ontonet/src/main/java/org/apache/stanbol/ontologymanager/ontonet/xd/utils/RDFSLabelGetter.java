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
name|ontonet
operator|.
name|xd
operator|.
name|utils
package|;
end_package

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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|xd
operator|.
name|lang
operator|.
name|Language
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
name|apibinding
operator|.
name|OWLManager
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
name|OWLAnnotationAssertionAxiom
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
name|OWLAnnotationValue
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
name|OWLDataFactory
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
name|OWLLiteral
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

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|vocab
operator|.
name|OWLRDFVocabulary
import|;
end_import

begin_comment
comment|/**  * Extracts rdfs:label(s) for entity  *   * @author Enrico Daga  *   */
end_comment

begin_class
specifier|public
class|class
name|RDFSLabelGetter
block|{
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|allLabels
decl_stmt|;
specifier|private
name|IRI
name|subject
decl_stmt|;
specifier|private
name|boolean
name|strict
decl_stmt|;
specifier|private
name|OWLDataFactory
name|owlFactory
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
specifier|public
name|RDFSLabelGetter
parameter_list|(
name|OWLOntology
name|ontology
parameter_list|,
name|IRI
name|subject
parameter_list|,
name|boolean
name|strict
parameter_list|)
block|{
name|this
operator|.
name|subject
operator|=
name|subject
expr_stmt|;
name|this
operator|.
name|strict
operator|=
name|strict
expr_stmt|;
name|Set
argument_list|<
name|OWLAnnotationAssertionAxiom
argument_list|>
name|individualAnnotations
init|=
name|ontology
operator|.
name|getAnnotationAssertionAxioms
argument_list|(
name|subject
argument_list|)
decl_stmt|;
name|allLabels
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|OWLAnnotationAssertionAxiom
name|annotation
range|:
name|individualAnnotations
control|)
block|{
if|if
condition|(
name|annotation
operator|.
name|getProperty
argument_list|()
operator|.
name|equals
argument_list|(
name|owlFactory
operator|.
name|getOWLAnnotationProperty
argument_list|(
name|OWLRDFVocabulary
operator|.
name|RDFS_LABEL
operator|.
name|getIRI
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
name|OWLAnnotationValue
name|value
init|=
name|annotation
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|value
operator|instanceof
name|IRI
condition|)
block|{
name|IRI
name|asIRI
init|=
operator|(
name|IRI
operator|)
name|value
decl_stmt|;
name|allLabels
operator|.
name|put
argument_list|(
name|asIRI
operator|.
name|toQuotedString
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|OWLLiteral
condition|)
block|{
name|OWLLiteral
name|sLiteral
init|=
operator|(
name|OWLLiteral
operator|)
name|value
decl_stmt|;
name|allLabels
operator|.
name|put
argument_list|(
name|sLiteral
operator|.
name|getLiteral
argument_list|()
argument_list|,
name|sLiteral
operator|.
name|getLang
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|String
name|getPreferred
parameter_list|()
block|{
name|String
index|[]
name|s
init|=
name|getForLang
argument_list|(
name|Language
operator|.
name|EN
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|s
operator|.
name|length
operator|==
literal|0
operator|)
condition|?
literal|""
else|:
name|s
index|[
literal|0
index|]
return|;
block|}
specifier|public
name|String
index|[]
name|getForLang
parameter_list|(
name|String
name|lang
parameter_list|)
block|{
if|if
condition|(
name|lang
operator|==
literal|null
condition|)
name|lang
operator|=
literal|""
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|forLang
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|allLabels
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|lang
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|forLang
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|forLang
operator|.
name|isEmpty
argument_list|()
operator|&&
name|allLabels
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// This entity has no labels at all:(
comment|// If we are not in strict mode, we must return something!
comment|// If it has a fragment we assume it is human readable
if|if
condition|(
operator|!
name|strict
condition|)
block|{
if|if
condition|(
name|subject
operator|.
name|toURI
argument_list|()
operator|.
name|getFragment
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|subject
operator|.
name|toURI
argument_list|()
operator|.
name|getFragment
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|forLang
operator|.
name|add
argument_list|(
name|subject
operator|.
name|toURI
argument_list|()
operator|.
name|getFragment
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
name|forLang
operator|.
name|add
argument_list|(
name|subject
operator|.
name|toQuotedString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|allLabels
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|forLang
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
return|return
name|forLang
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|forLang
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
block|}
end_class

end_unit

