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
name|owlapi
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
name|HashSet
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
name|serializedform
operator|.
name|Serializer
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
name|owl
operator|.
name|util
operator|.
name|OWLUtils
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
name|OWLOntologyAlreadyExistsException
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
name|OWLOntologyCreationException
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
name|OWLOntologyDocumentAlreadyExistsException
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
name|OWLOntologyManager
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
comment|/**  * OWLAPI-based (in-memory) ontology cache implementation.  *   * TODO re-introduce SCR annotations, just avoid loading the component  *   * @author alexdma  *   */
end_comment

begin_comment
comment|// @Component(immediate = true, metatype = false)
end_comment

begin_comment
comment|// @Service(OntologyProvider.class)
end_comment

begin_class
specifier|public
class|class
name|OWLAPIOntologyProvider
implements|implements
name|OntologyProvider
argument_list|<
name|OWLOntologyManager
argument_list|>
block|{
specifier|private
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
specifier|private
name|OWLOntologyManager
name|store
init|=
literal|null
decl_stmt|;
specifier|public
name|OWLAPIOntologyProvider
parameter_list|()
block|{
name|this
argument_list|(
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new instance of ClerezzaCache with an embedded {@link OWLOntologyManager}.      */
specifier|public
name|OWLAPIOntologyProvider
parameter_list|(
name|OWLOntologyManager
name|store
parameter_list|)
block|{
if|if
condition|(
name|store
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cache requires a non-null OWLOntologyManager."
argument_list|)
throw|;
name|this
operator|.
name|store
operator|=
name|store
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getKey
parameter_list|(
name|IRI
name|ontologyIRI
parameter_list|)
block|{
return|return
name|ontologyIRI
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getKeys
parameter_list|()
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLOntology
name|o
range|:
name|store
operator|.
name|getOntologies
argument_list|()
control|)
name|result
operator|.
name|add
argument_list|(
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|o
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntologyManager
name|getStore
parameter_list|()
block|{
return|return
name|store
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getStoredOntology
parameter_list|(
name|String
name|identifier
parameter_list|,
name|Class
argument_list|<
name|?
argument_list|>
name|returnType
parameter_list|)
block|{
if|if
condition|(
name|returnType
operator|==
literal|null
condition|)
block|{
name|returnType
operator|=
name|OWLOntology
operator|.
name|class
expr_stmt|;
name|log
operator|.
name|warn
argument_list|(
literal|"No return type given for ontologies. Will return a {}"
argument_list|,
name|returnType
argument_list|)
expr_stmt|;
block|}
name|boolean
name|canDo
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
range|:
name|getSupportedReturnTypes
argument_list|()
control|)
if|if
condition|(
name|clazz
operator|.
name|isAssignableFrom
argument_list|(
name|returnType
argument_list|)
condition|)
block|{
name|canDo
operator|=
literal|true
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|!
name|canDo
condition|)
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Return type "
operator|+
name|returnType
operator|+
literal|" is not allowed in this implementation. Only allowed return types are "
operator|+
name|getSupportedReturnTypes
argument_list|()
argument_list|)
throw|;
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|getSupportedReturnTypes
parameter_list|()
block|{
return|return
operator|new
name|Class
argument_list|<
name|?
argument_list|>
index|[]
block|{
name|OWLOntology
operator|.
name|class
block|}
empty_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|loadInStore
parameter_list|(
name|InputStream
name|data
parameter_list|,
name|String
name|formatIdentifier
parameter_list|,
name|boolean
name|force
parameter_list|)
block|{
try|try
block|{
name|OWLOntology
name|o
init|=
name|store
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|data
argument_list|)
decl_stmt|;
return|return
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|o
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|loadInStore
parameter_list|(
name|IRI
name|location
parameter_list|,
name|String
name|formatIdentifier
parameter_list|,
name|boolean
name|force
parameter_list|)
block|{
name|OWLOntology
name|o
init|=
literal|null
decl_stmt|;
try|try
block|{
name|o
operator|=
name|store
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyAlreadyExistsException
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|force
condition|)
name|o
operator|=
name|store
operator|.
name|getOntology
argument_list|(
name|e
operator|.
name|getOntologyID
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyDocumentAlreadyExistsException
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|force
condition|)
name|o
operator|=
name|store
operator|.
name|getOntology
argument_list|(
name|e
operator|.
name|getOntologyDocumentIRI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|OWLUtils
operator|.
name|guessOntologyIdentifier
argument_list|(
name|o
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Serializer
name|getSerializer
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

