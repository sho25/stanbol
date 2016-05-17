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
name|htmlextractor
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
name|helper
operator|.
name|EnhancementEngineHelper
operator|.
name|randomUUID
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
name|HashSet
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
name|commons
operator|.
name|rdf
operator|.
name|BlankNode
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
name|RDFTerm
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
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|impl
operator|.
name|utils
operator|.
name|simple
operator|.
name|SimpleGraph
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
name|impl
operator|.
name|utils
operator|.
name|TripleImpl
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
comment|/**  * Utilities functions for RDF Graphs  *   * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  *   */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ClerezzaRDFUtils
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|ClerezzaRDFUtils
parameter_list|()
block|{}
specifier|private
specifier|static
specifier|final
name|Logger
name|LOG
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ClerezzaRDFUtils
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|void
name|urifyBlankNodes
parameter_list|(
name|Graph
name|model
parameter_list|)
block|{
name|HashMap
argument_list|<
name|BlankNode
argument_list|,
name|IRI
argument_list|>
name|blankNodeMap
init|=
operator|new
name|HashMap
argument_list|<
name|BlankNode
argument_list|,
name|IRI
argument_list|>
argument_list|()
decl_stmt|;
name|Graph
name|remove
init|=
operator|new
name|SimpleGraph
argument_list|()
decl_stmt|;
name|Graph
name|add
init|=
operator|new
name|SimpleGraph
argument_list|()
decl_stmt|;
for|for
control|(
name|Triple
name|t
range|:
name|model
control|)
block|{
name|BlankNodeOrIRI
name|subj
init|=
name|t
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|RDFTerm
name|obj
init|=
name|t
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|IRI
name|pred
init|=
name|t
operator|.
name|getPredicate
argument_list|()
decl_stmt|;
name|boolean
name|match
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|subj
operator|instanceof
name|BlankNode
condition|)
block|{
name|match
operator|=
literal|true
expr_stmt|;
name|IRI
name|ru
init|=
name|blankNodeMap
operator|.
name|get
argument_list|(
name|subj
argument_list|)
decl_stmt|;
if|if
condition|(
name|ru
operator|==
literal|null
condition|)
block|{
name|ru
operator|=
name|createRandomUri
argument_list|()
expr_stmt|;
name|blankNodeMap
operator|.
name|put
argument_list|(
operator|(
name|BlankNode
operator|)
name|subj
argument_list|,
name|ru
argument_list|)
expr_stmt|;
block|}
name|subj
operator|=
name|ru
expr_stmt|;
block|}
if|if
condition|(
name|obj
operator|instanceof
name|BlankNode
condition|)
block|{
name|match
operator|=
literal|true
expr_stmt|;
name|IRI
name|ru
init|=
name|blankNodeMap
operator|.
name|get
argument_list|(
name|obj
argument_list|)
decl_stmt|;
if|if
condition|(
name|ru
operator|==
literal|null
condition|)
block|{
name|ru
operator|=
name|createRandomUri
argument_list|()
expr_stmt|;
name|blankNodeMap
operator|.
name|put
argument_list|(
operator|(
name|BlankNode
operator|)
name|obj
argument_list|,
name|ru
argument_list|)
expr_stmt|;
block|}
name|obj
operator|=
name|ru
expr_stmt|;
block|}
if|if
condition|(
name|match
condition|)
block|{
name|remove
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|add
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|subj
argument_list|,
name|pred
argument_list|,
name|obj
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|model
operator|.
name|removeAll
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|model
operator|.
name|addAll
argument_list|(
name|add
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|IRI
name|createRandomUri
parameter_list|()
block|{
return|return
operator|new
name|IRI
argument_list|(
literal|"urn:rnd:"
operator|+
name|randomUUID
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|void
name|makeConnected
parameter_list|(
name|Graph
name|model
parameter_list|,
name|BlankNodeOrIRI
name|root
parameter_list|,
name|IRI
name|property
parameter_list|)
block|{
name|Set
argument_list|<
name|BlankNodeOrIRI
argument_list|>
name|roots
init|=
name|findRoots
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|LOG
operator|.
name|debug
argument_list|(
literal|"Roots: {}"
argument_list|,
name|roots
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|found
init|=
name|roots
operator|.
name|remove
argument_list|(
name|root
argument_list|)
decl_stmt|;
comment|//connect all hanging roots to root by property
for|for
control|(
name|BlankNodeOrIRI
name|n
range|:
name|roots
control|)
block|{
name|model
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|root
argument_list|,
name|property
argument_list|,
name|n
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|Set
argument_list|<
name|BlankNodeOrIRI
argument_list|>
name|findRoots
parameter_list|(
name|Graph
name|model
parameter_list|)
block|{
name|Set
argument_list|<
name|BlankNodeOrIRI
argument_list|>
name|roots
init|=
operator|new
name|HashSet
argument_list|<
name|BlankNodeOrIRI
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|BlankNodeOrIRI
argument_list|>
name|visited
init|=
operator|new
name|HashSet
argument_list|<
name|BlankNodeOrIRI
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Triple
name|t
range|:
name|model
control|)
block|{
name|BlankNodeOrIRI
name|subj
init|=
name|t
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|findRoot
argument_list|(
name|model
argument_list|,
name|subj
argument_list|,
name|roots
argument_list|,
name|visited
argument_list|)
expr_stmt|;
block|}
return|return
name|roots
return|;
block|}
specifier|private
specifier|static
name|void
name|findRoot
parameter_list|(
name|Graph
name|model
parameter_list|,
name|BlankNodeOrIRI
name|node
parameter_list|,
name|Set
argument_list|<
name|BlankNodeOrIRI
argument_list|>
name|roots
parameter_list|,
name|Set
argument_list|<
name|BlankNodeOrIRI
argument_list|>
name|visited
parameter_list|)
block|{
if|if
condition|(
name|visited
operator|.
name|contains
argument_list|(
name|node
argument_list|)
condition|)
block|{
return|return;
block|}
name|visited
operator|.
name|add
argument_list|(
name|node
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|it
init|=
name|model
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
name|node
argument_list|)
decl_stmt|;
comment|// something that is not the object of some stement is a root
if|if
condition|(
operator|!
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|roots
operator|.
name|add
argument_list|(
name|node
argument_list|)
expr_stmt|;
name|LOG
operator|.
name|debug
argument_list|(
literal|"Root found: {}"
argument_list|,
name|node
argument_list|)
expr_stmt|;
return|return;
block|}
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Triple
name|t
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|BlankNodeOrIRI
name|subj
init|=
name|t
operator|.
name|getSubject
argument_list|()
decl_stmt|;
name|findRoot
argument_list|(
name|model
argument_list|,
name|subj
argument_list|,
name|roots
argument_list|,
name|visited
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

