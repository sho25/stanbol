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
operator|.
name|io
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
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|OWLOntologyID
import|;
end_import

begin_comment
comment|/**  * A wrapper class for whatever can be used for physically referencing a resource (typically an ontology).  * Currently the supported types are:  *<ul>  *<li> {@link IRI}, which is interpreted as the physical location of the resource.  *<li> {@link OWLOntologyID}, which is interpreted as the public key of an ontology already stored by Stanbol.  *<li> {@link UriRef}, which is interpreted as the name of a graph to be retrieved from an underlying Clerezza  * store (typically a {@link TcProvider}).  *</ul>  *   * @author alexdma  *   * @param<R>  *            the resource reference.  */
end_comment

begin_class
specifier|public
class|class
name|Origin
parameter_list|<
name|R
parameter_list|>
block|{
comment|/**      * Creates a new Origin for a resource that can be retrieved by dereferencing the given IRI as an URL.      *       * @param physicalURL      *            the physical location of the resource      * @return the origin that wraps this IRI.      */
specifier|public
specifier|static
name|Origin
argument_list|<
name|IRI
argument_list|>
name|create
parameter_list|(
name|IRI
name|physicalURL
parameter_list|)
block|{
return|return
operator|new
name|Origin
argument_list|<
name|IRI
argument_list|>
argument_list|(
name|physicalURL
argument_list|)
return|;
block|}
comment|/**      * Creates a new Origin for a resource whose public key is known. What a "public key" is interpreted to be      * is implementation-dependent.      *       * @param publicKey      *            the public key      * @return the origin that wraps this IRI.      */
specifier|public
specifier|static
name|Origin
argument_list|<
name|OWLOntologyID
argument_list|>
name|create
parameter_list|(
name|OWLOntologyID
name|publicKey
parameter_list|)
block|{
return|return
operator|new
name|Origin
argument_list|<
name|OWLOntologyID
argument_list|>
argument_list|(
name|publicKey
argument_list|)
return|;
block|}
comment|/**      * Creates a new Origin for a resource that can be retrieved by querying a Clerezza store for a graph with      * the given name.      *       * @param graphName      *            the graph name      * @return the origin that wraps this graph name.      */
specifier|public
specifier|static
name|Origin
argument_list|<
name|UriRef
argument_list|>
name|create
parameter_list|(
name|UriRef
name|graphName
parameter_list|)
block|{
return|return
operator|new
name|Origin
argument_list|<
name|UriRef
argument_list|>
argument_list|(
name|graphName
argument_list|)
return|;
block|}
specifier|private
name|R
name|ref
decl_stmt|;
comment|/**      * Creates a new instance of {@link Origin}.      *       * @param reference      *            the physical reference. Cannot be null      * @throws IllegalArgumentException      *             if a null value was supplied for<code>reference</code>.      */
specifier|protected
name|Origin
parameter_list|(
name|R
name|reference
parameter_list|)
block|{
if|if
condition|(
name|reference
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Class "
operator|+
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" does not allow a null reference object."
operator|+
literal|" "
operator|+
literal|"If a null object is needed, developers should use a null Origin instead."
argument_list|)
throw|;
name|ref
operator|=
name|reference
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|arg0
parameter_list|)
block|{
if|if
condition|(
name|arg0
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|arg0
operator|instanceof
name|Origin
argument_list|<
name|?
argument_list|>
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|this
operator|.
name|getReference
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Origin
argument_list|<
name|?
argument_list|>
operator|)
name|arg0
operator|)
operator|.
name|getReference
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns the actual reference object that was wrapped by this Origin.      *       * @return the reference object.      */
specifier|public
name|R
name|getReference
parameter_list|()
block|{
return|return
name|ref
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
literal|"Origin("
operator|+
name|ref
operator|.
name|toString
argument_list|()
operator|+
literal|")"
return|;
block|}
block|}
end_class

end_unit

