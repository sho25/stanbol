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
name|impl
operator|.
name|clerezza
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
name|List
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
name|api
operator|.
name|ontology
operator|.
name|OntologyProvider
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
name|api
operator|.
name|ontology
operator|.
name|OntologySpace
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
name|api
operator|.
name|ontology
operator|.
name|SpaceType
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
name|owl
operator|.
name|util
operator|.
name|URIUtils
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
name|AddImport
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
name|OWLImportsDeclaration
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
name|model
operator|.
name|OWLOntologyChange
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
name|RemoveImport
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

begin_comment
comment|/**  * Abstract Clerezza-native implementation of {@link OntologySpace}.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractOntologySpaceImpl
extends|extends
name|AbstractOntologyCollectorImpl
implements|implements
name|OntologySpace
block|{
specifier|protected
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|protected
name|SpaceType
name|type
decl_stmt|;
specifier|public
name|AbstractOntologySpaceImpl
parameter_list|(
name|String
name|spaceID
parameter_list|,
name|IRI
name|namespace
parameter_list|,
name|SpaceType
name|type
parameter_list|,
name|OntologyProvider
argument_list|<
name|?
argument_list|>
name|ontologyProvider
parameter_list|)
block|{
name|super
argument_list|(
name|spaceID
argument_list|,
name|namespace
argument_list|,
name|ontologyProvider
argument_list|)
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|getOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
block|{
return|return
name|getOntology
argument_list|(
name|ontologyIri
argument_list|,
literal|false
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|getOntology
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|,
name|boolean
name|merge
parameter_list|)
block|{
comment|// Remove the check below. It might be an unmanaged dependency (TODO remove from collector and
comment|// reintroduce check?).
comment|// if (!hasOntology(ontologyIri)) return null;
name|OWLOntology
name|o
decl_stmt|;
name|o
operator|=
operator|(
name|OWLOntology
operator|)
name|ontologyProvider
operator|.
name|getStoredOntology
argument_list|(
name|ontologyIri
argument_list|,
name|OWLOntology
operator|.
name|class
argument_list|,
name|merge
argument_list|)
expr_stmt|;
comment|// Rewrite import statements
name|List
argument_list|<
name|OWLOntologyChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<
name|OWLOntologyChange
argument_list|>
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|df
init|=
name|o
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
comment|/*          * TODO manage import rewrites better once the container ID is fully configurable (i.e. instead of          * going upOne() add "session" or "ontology" if needed).          */
if|if
condition|(
operator|!
name|merge
condition|)
block|{
for|for
control|(
name|OWLImportsDeclaration
name|oldImp
range|:
name|o
operator|.
name|getImportsDeclarations
argument_list|()
control|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|RemoveImport
argument_list|(
name|o
argument_list|,
name|oldImp
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|s
init|=
name|oldImp
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|s
operator|=
name|s
operator|.
name|substring
argument_list|(
name|s
operator|.
name|indexOf
argument_list|(
literal|"::"
argument_list|)
operator|+
literal|2
argument_list|,
name|s
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|managed
init|=
name|managedOntologies
operator|.
name|contains
argument_list|(
name|oldImp
operator|.
name|getIRI
argument_list|()
argument_list|)
decl_stmt|;
comment|// For space, always go up at least one
name|IRI
name|ns
init|=
name|getNamespace
argument_list|()
decl_stmt|;
name|IRI
name|target
init|=
name|IRI
operator|.
name|create
argument_list|(
operator|(
name|managed
condition|?
name|ns
operator|+
literal|"/"
operator|+
name|getID
argument_list|()
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
index|[
literal|0
index|]
operator|+
literal|"/"
else|:
name|URIUtils
operator|.
name|upOne
argument_list|(
name|ns
argument_list|)
operator|+
literal|"/"
operator|)
operator|+
name|s
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|AddImport
argument_list|(
name|o
argument_list|,
name|df
operator|.
name|getOWLImportsDeclaration
argument_list|(
name|target
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|o
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|applyChanges
argument_list|(
name|changes
argument_list|)
expr_stmt|;
block|}
return|return
name|o
return|;
block|}
comment|/**      *       * @param id      *            The ontology space identifier. This implementation only allows non-null and non-empty      *            alphanumeric sequences, case-sensitive and preferably separated by a single slash character,      *            with optional dashes or underscores.      */
annotation|@
name|Override
specifier|protected
name|void
name|setID
parameter_list|(
name|String
name|id
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Space ID cannot be null."
argument_list|)
throw|;
name|id
operator|=
name|id
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Space ID cannot be empty."
argument_list|)
throw|;
if|if
condition|(
name|id
operator|.
name|matches
argument_list|(
literal|"[\\w-]+"
argument_list|)
condition|)
name|log
operator|.
name|warn
argument_list|(
literal|"Space ID {} is a single alphanumeric sequence, with no separating slash."
operator|+
literal|" This is legal but strongly discouraged. Please consider using"
operator|+
literal|" space IDs of the form [scope_id]/[space_type], e.g. Users/core ."
argument_list|,
name|id
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
operator|!
name|id
operator|.
name|matches
argument_list|(
literal|"[\\w-]+/[\\w-]+"
argument_list|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Illegal space ID "
operator|+
name|id
operator|+
literal|" - Must be an alphanumeric sequence, (preferably two, "
operator|+
literal|" slash-separated), with optional underscores or dashes."
argument_list|)
throw|;
name|this
operator|.
name|_id
operator|=
name|id
expr_stmt|;
block|}
block|}
end_class

end_unit

