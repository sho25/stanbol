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
name|File
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
name|OWLOntologyManager
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
name|util
operator|.
name|AutoIRIMapper
import|;
end_import

begin_comment
comment|/**  * An {@link OntologyInputSource} that recursively tries to hijack all import declarations to the directory  * containing the input ontology (i.e. to the parent directory of the file itself). It can be used for offline  * ontology loading, if one has the entire imports closure available in single directory.<br>  *<br>  * The behaviour of this class is inherited from the {@link AutoIRIMapper} in the OWL API, and so are its  * limitations and fallback policies.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|ParentPathInputSource
extends|extends
name|AbstractOntologyInputSource
block|{
comment|/**      * Creates a new parent path ontology input source. When created using this constructor, the only active      * IRI mapper will be the one that maps to any ontology found in the parent directory of the supplied      * file.      *       * @param rootFile      *            the root ontology file. Must not be a directory.      * @throws OWLOntologyCreationException      *             if<code>rootFile</code> does not exist, is not an ontology or one of its imports failed to      *             load.      */
specifier|public
name|ParentPathInputSource
parameter_list|(
name|File
name|rootFile
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
name|this
argument_list|(
name|rootFile
argument_list|,
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new parent path ontology input source. If the developer wishes to recycle an      * {@link OWLOntologyManager} (e.g. in order to keep the active IRI mappers attached to it), they can do      * so by passing it to the method. Please note that recycling ontology managers will increase the      * likelihood of {@link OWLOntologyAlreadyExistsException}s being thrown.      *       * @param rootFile      *            the root ontology file. Must not be a directory.      * @param mgr      *            the ontology manager to recycle. Note that an {@link AutoIRIMapper} will be added to it.      * @throws OWLOntologyCreationException      *             if<code>rootFile</code> does not exist, is not an ontology or one of its imports failed to      *             load.      */
specifier|public
name|ParentPathInputSource
parameter_list|(
name|File
name|rootFile
parameter_list|,
name|OWLOntologyManager
name|mgr
parameter_list|)
throws|throws
name|OWLOntologyCreationException
block|{
comment|// Directories are not allowed
if|if
condition|(
name|rootFile
operator|.
name|isDirectory
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Could not determine root ontology : file "
operator|+
name|rootFile
operator|+
literal|" is a directory. Only regular files are allowed."
argument_list|)
throw|;
name|AutoIRIMapper
name|mapper
init|=
operator|new
name|AutoIRIMapper
argument_list|(
name|rootFile
operator|.
name|getParentFile
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|mgr
operator|.
name|addIRIMapper
argument_list|(
name|mapper
argument_list|)
expr_stmt|;
name|bindRootOntology
argument_list|(
name|mgr
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|rootFile
argument_list|)
argument_list|)
expr_stmt|;
comment|// TODO : do we really want this to happen?
name|bindPhysicalIri
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
name|rootFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"ROOT_ONT_IRI<"
operator|+
name|getPhysicalIRI
argument_list|()
operator|+
literal|">"
return|;
block|}
block|}
end_class

end_unit

