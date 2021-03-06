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
name|servicesapi
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
name|OWLClass
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
name|OWLIndividual
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
name|OWLObjectProperty
import|;
end_import

begin_comment
comment|/**  * An internal representation of the vocabulary that Stanbol uses internally for representing stored  * ontologies and virtual ontology networks, and restoring them on startup. This vocabulary is not intended to  * be used for ontologies exposed to the public.  *   * TODO create the objects through reflection after parsing the corresponding OWL schema.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|Vocabulary
block|{
specifier|private
specifier|static
name|OWLDataFactory
name|__df
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
comment|/**      * The default namespace for the Stanbol OntoNet metadata vocabulary      */
specifier|public
specifier|static
specifier|final
name|String
name|_NS_ONTONET
init|=
literal|"http://stanbol.apache.org/ontology/meta/ontonet#"
decl_stmt|;
comment|/**      * This namespace is used for representing Stanbol resources internally. It should applied to all portable      * resources that might be moved from one host to another, e.g. scopes and sessions.<br/>      *<br>      * This namespace MUST NOT be used for identifying resources in the outside world, e.g. RESTful services:      * it MUST be converted to the public namespace before exporting.      */
specifier|public
specifier|static
specifier|final
name|String
name|_NS_STANBOL_INTERNAL
init|=
literal|"http://stanbol.apache.org/ontology/.internal/"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_APPENDED_TO
init|=
literal|"isAppendedTo"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_DEPENDS_ON
init|=
literal|"dependsOn"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_ENTRY
init|=
literal|"Entry"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_GRAPH
init|=
literal|"ImmutableGraph"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_HAS_APPENDED
init|=
literal|"hasAppended"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_HAS_DEPENDENT
init|=
literal|"hasDependenct"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_HAS_ONTOLOGY_IRI
init|=
literal|"hasOntologyIRI"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_HAS_SPACE_CORE
init|=
literal|"hasCoreSpace"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_HAS_SPACE_CUSTOM
init|=
literal|"hasCustomSpace"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_HAS_STATUS
init|=
literal|"hasStatus"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_HAS_VERSION_IRI
init|=
literal|"hasVersionIRI"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_IS_MANAGED_BY
init|=
literal|"isManagedBy"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_IS_MANAGED_BY_CORE
init|=
literal|"isManagedByCore"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_IS_MANAGED_BY_CUSTOM
init|=
literal|"isManagedByCustom"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_IS_SPACE_CORE_OF
init|=
literal|"isCoreSpaceOf"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_IS_SPACE_CUSTOM_OF
init|=
literal|"isCustomSpaceOf"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_MANAGES
init|=
literal|"manages"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_MANAGES_IN_CORE
init|=
literal|"managesInCore"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_MANAGES_IN_CUSTOM
init|=
literal|"managesInCustom"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_MAPS_TO_GRAPH
init|=
literal|"mapsToGraph"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_PRIMARY_ENTRY
init|=
literal|"PrimaryEntry"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_RETRIEVED_FROM
init|=
literal|"retrievedFrom"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_SCOPE
init|=
literal|"Scope"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_SESSION
init|=
literal|"Session"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_SIZE_IN_AXIOMS
init|=
literal|"hasSizeInAxioms"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_SIZE_IN_TRIPLES
init|=
literal|"hasSizeInTriples"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_SPACE
init|=
literal|"Space"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_STATUS
init|=
literal|"Status"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_STATUS_ACTIVE
init|=
name|_SHORT_STATUS
operator|+
literal|".ACTIVE"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|_SHORT_STATUS_INACTIVE
init|=
name|_SHORT_STATUS
operator|+
literal|".INACTIVE"
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isAppendedTo</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|APPENDED_TO
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_APPENDED_TO
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isAppendedTo</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|APPENDED_TO_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_APPENDED_TO
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>dependsOn</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|DEPENDS_ON
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_DEPENDS_ON
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>dependsOn</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|DEPENDS_ON_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_DEPENDS_ON
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Entry</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLClass
name|ENTRY
init|=
name|__df
operator|.
name|getOWLClass
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_ENTRY
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Entry</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|ENTRY_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_ENTRY
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>ImmutableGraph</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLClass
name|GRAPH
init|=
name|__df
operator|.
name|getOWLClass
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_GRAPH
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>ImmutableGraph</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|GRAPH_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_GRAPH
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>hasAppended</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|HAS_APPENDED
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_APPENDED
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>hasAppended</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|HAS_APPENDED_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_APPENDED
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>hasDependent</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|HAS_DEPENDENT
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_DEPENDENT
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasDependent</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|HAS_DEPENDENT_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_DEPENDENT
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasOntologyIRI</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLDataProperty
name|HAS_ONTOLOGY_IRI
init|=
name|__df
operator|.
name|getOWLDataProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_ONTOLOGY_IRI
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasOntologyIRI</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|HAS_ONTOLOGY_IRI_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_ONTOLOGY_IRI
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isManagedBy</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|HAS_SPACE_CORE
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_SPACE_CORE
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>hasCoreSpace</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|HAS_SPACE_CORE_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_SPACE_CORE
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isManagedBy</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|HAS_SPACE_CUSTOM
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_SPACE_CUSTOM
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>hasCustomSpace</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|HAS_SPACE_CUSTOM_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_SPACE_CUSTOM
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>hasStatus</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|HAS_STATUS
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_STATUS
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>hasStatus</tt>.      */
specifier|public
specifier|static
specifier|final
name|IRI
name|HAS_STATUS_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_STATUS
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasVersionIRI</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLDataProperty
name|HAS_VERSION_IRI
init|=
name|__df
operator|.
name|getOWLDataProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_VERSION_IRI
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasVersionIRI</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|HAS_VERSION_IRI_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_HAS_VERSION_IRI
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isManagedBy</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|IS_MANAGED_BY
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_MANAGED_BY
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isManagedByCore</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|IS_MANAGED_BY_CORE
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_MANAGED_BY_CORE
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isManagedByCore</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|IS_MANAGED_BY_CORE_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_MANAGED_BY_CORE
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isManagedByCustom</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|IS_MANAGED_BY_CUSTOM
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_MANAGED_BY_CUSTOM
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isManagedByCustom</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|IS_MANAGED_BY_CUSTOM_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_MANAGED_BY_CUSTOM
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isManagedBy</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|IS_MANAGED_BY_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_MANAGED_BY
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isCoreSpaceOf</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|IS_SPACE_CORE_OF
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_SPACE_CORE_OF
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isCoreSpaceOf</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|IS_SPACE_CORE_OF_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_SPACE_CORE_OF
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isCustomSpaceOf</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|IS_SPACE_CUSTOM_OF
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_SPACE_CUSTOM_OF
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>isCustomSpaceOf</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|IS_SPACE_CUSTOM_OF_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_IS_SPACE_CUSTOM_OF
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>manages</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|MANAGES
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_MANAGES
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>managesInCore</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|MANAGES_IN_CORE
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_MANAGES_IN_CORE
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>managesInCore</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|MANAGES_IN_CORE_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_MANAGES_IN_CORE
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>managesInCustom</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|MANAGES_IN_CUSTOM
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_MANAGES_IN_CUSTOM
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>managesInCustom</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|MANAGES_IN_CUSTOM_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_MANAGES_IN_CUSTOM
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>manages</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|MANAGES_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_MANAGES
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>mapsToGraph</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLObjectProperty
name|MAPS_TO_GRAPH
init|=
name|__df
operator|.
name|getOWLObjectProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_MAPS_TO_GRAPH
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>object property</b><tt>mapsToGraph</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|MAPS_TO_GRAPH_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_MAPS_TO_GRAPH
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>PrimaryEntry</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLClass
name|PRIMARY_ENTRY
init|=
name|__df
operator|.
name|getOWLClass
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_PRIMARY_ENTRY
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>PrimaryEntry</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|PRIMARY_ENTRY_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_PRIMARY_ENTRY
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>retrievedFrom</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLDataProperty
name|RETRIEVED_FROM
init|=
name|__df
operator|.
name|getOWLDataProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_RETRIEVED_FROM
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>retrievedFrom</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|RETRIEVED_FROM_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_RETRIEVED_FROM
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Scope</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLClass
name|SCOPE
init|=
name|__df
operator|.
name|getOWLClass
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SCOPE
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Scope</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|SCOPE_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SCOPE
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Session</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLClass
name|SESSION
init|=
name|__df
operator|.
name|getOWLClass
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SESSION
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Session</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|SESSION_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SESSION
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasSizeInAxioms</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLDataProperty
name|SIZE_IN_AXIOMS
init|=
name|__df
operator|.
name|getOWLDataProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SIZE_IN_AXIOMS
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasSizeInAxioms</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|SIZE_IN_AXIOMS_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SIZE_IN_AXIOMS
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasSizeInTriples</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLDataProperty
name|SIZE_IN_TRIPLES
init|=
name|__df
operator|.
name|getOWLDataProperty
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SIZE_IN_TRIPLES
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>datatype property</b><tt>hasSizeInTriples</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|SIZE_IN_TRIPLES_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SIZE_IN_TRIPLES
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Space</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLClass
name|SPACE
init|=
name|__df
operator|.
name|getOWLClass
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SPACE
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Space</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|SPACE_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_SPACE
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Status</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLClass
name|STATUS
init|=
name|__df
operator|.
name|getOWLClass
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_STATUS
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>individual</b><tt>Status.ACTIVE</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLIndividual
name|STATUS_ACTIVE
init|=
name|__df
operator|.
name|getOWLNamedIndividual
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_STATUS_ACTIVE
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>individual</b><tt>Status.ACTIVE</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|STATUS_ACTIVE_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_STATUS_ACTIVE
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>individual</b><tt>Status.INACTIVE</tt>.      */
specifier|public
specifier|static
specifier|final
name|OWLIndividual
name|STATUS_INACTIVE
init|=
name|__df
operator|.
name|getOWLNamedIndividual
argument_list|(
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
operator|.
name|create
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_STATUS_INACTIVE
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>individual</b><tt>Status.INACTIVE</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|STATUS_INACTIVE_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_STATUS_INACTIVE
argument_list|)
decl_stmt|;
comment|/**      * The OWL<b>class</b><tt>Status</tt> (in IRI form).      */
specifier|public
specifier|static
specifier|final
name|IRI
name|STATUS_URIREF
init|=
operator|new
name|IRI
argument_list|(
name|_NS_ONTONET
operator|+
name|_SHORT_STATUS
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

