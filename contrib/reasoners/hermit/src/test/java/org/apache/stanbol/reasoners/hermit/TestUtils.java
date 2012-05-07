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
name|reasoners
operator|.
name|hermit
package|;
end_package

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
name|HashSet
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
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLAxiom
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
name|OWLDataProperty
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
name|OWLDataPropertyAssertionAxiom
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
name|OWLDatatype
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_class
specifier|public
class|class
name|TestUtils
block|{
specifier|public
specifier|static
name|void
name|debug
parameter_list|(
name|OWLOntology
name|ont
parameter_list|,
name|Logger
name|log
parameter_list|)
block|{
comment|// For debug only
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
comment|// We show all axioms in this ontology
name|log
operator|.
name|debug
argument_list|(
literal|"OntologyID: {}"
argument_list|,
name|ont
operator|.
name|getOntologyID
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Imports:"
argument_list|)
expr_stmt|;
comment|// Imports
for|for
control|(
name|OWLOntology
name|o
range|:
name|ont
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getImports
argument_list|(
name|ont
argument_list|)
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" - {}"
argument_list|,
name|o
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|debug
argument_list|(
literal|"Axioms:"
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLAxiom
name|a
range|:
name|ont
operator|.
name|getAxioms
argument_list|()
control|)
name|log
operator|.
name|debug
argument_list|(
literal|" - {}"
argument_list|,
name|a
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|debug
parameter_list|(
name|Set
argument_list|<
name|?
extends|extends
name|OWLAxiom
argument_list|>
name|ax
parameter_list|,
name|Logger
name|log
parameter_list|)
block|{
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Axioms: "
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLAxiom
name|a
range|:
name|ax
control|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" - {}"
argument_list|,
name|a
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** 	 * This is for monitoring hermit with datatype properties. 	 *  	 * @param ont 	 */
specifier|public
specifier|static
name|void
name|checkProperties
parameter_list|(
name|OWLOntology
name|ont
parameter_list|,
name|Logger
name|log
parameter_list|)
block|{
comment|// When throw inconsistent exception = false and ignoreUnsupportedDatatypes=true
comment|//- Datatypes which are not builtIn break the reasoner
comment|//- Looks like rdf:PlainLiteral is not supported by Hermit, even if it is marked as BuiltIn datatype by OWLApi
comment|// This incoherence generates an unexpected error!
comment|//
name|Map
argument_list|<
name|OWLDataProperty
argument_list|,
name|Set
argument_list|<
name|OWLDatatype
argument_list|>
argument_list|>
name|properties
init|=
operator|new
name|HashMap
argument_list|<
name|OWLDataProperty
argument_list|,
name|Set
argument_list|<
name|OWLDatatype
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|OWLAxiom
argument_list|>
name|remove
init|=
operator|new
name|HashSet
argument_list|<
name|OWLAxiom
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLAxiom
name|a
range|:
name|ont
operator|.
name|getLogicalAxioms
argument_list|()
control|)
block|{
if|if
condition|(
name|a
operator|instanceof
name|OWLDataPropertyAssertionAxiom
condition|)
block|{
name|OWLDataPropertyAssertionAxiom
name|aa
init|=
operator|(
name|OWLDataPropertyAssertionAxiom
operator|)
name|a
decl_stmt|;
for|for
control|(
name|OWLDataProperty
name|p
range|:
name|aa
operator|.
name|getDataPropertiesInSignature
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|properties
operator|.
name|keySet
argument_list|()
operator|.
name|contains
argument_list|(
name|p
argument_list|)
condition|)
block|{
name|properties
operator|.
name|put
argument_list|(
name|p
argument_list|,
operator|new
name|HashSet
argument_list|<
name|OWLDatatype
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|OWLDatatype
name|dt
range|:
name|aa
operator|.
name|getDatatypesInSignature
argument_list|()
control|)
block|{
name|properties
operator|.
name|get
argument_list|(
name|p
argument_list|)
operator|.
name|add
argument_list|(
name|dt
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|log
operator|.
name|info
argument_list|(
literal|"Data properties : "
argument_list|)
expr_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|OWLDataProperty
argument_list|,
name|Set
argument_list|<
name|OWLDatatype
argument_list|>
argument_list|>
name|p
range|:
name|properties
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" - {} "
argument_list|,
name|p
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLDatatype
name|d
range|:
name|p
operator|.
name|getValue
argument_list|()
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" ---> {} [{}]"
argument_list|,
name|d
argument_list|,
name|d
operator|.
name|isBuiltIn
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|log
operator|.
name|info
argument_list|(
literal|"Data property axioms removed:"
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLAxiom
name|d
range|:
name|remove
control|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|" removed ---> {} "
argument_list|,
name|d
operator|.
name|getDataPropertiesInSignature
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
