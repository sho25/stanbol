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
name|impl
operator|.
name|ontology
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
operator|.
name|OntologyInputSource
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
name|CoreOntologySpace
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
name|CustomOntologySpace
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
name|OntologyScope
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
name|OntologySpaceFactory
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
name|OntologySpaceListener
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
name|ScopeOntologyListener
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
name|SessionOntologySpace
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
name|UnmodifiableOntologySpaceException
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
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * The default implementation of an ontology scope.  *   * @author alessandro  *   */
end_comment

begin_class
specifier|public
class|class
name|OntologyScopeImpl
implements|implements
name|OntologyScope
implements|,
name|OntologySpaceListener
block|{
comment|/**      * The core ontology space for this scope, always set as default.      */
specifier|protected
name|CoreOntologySpace
name|coreSpace
decl_stmt|;
comment|/**      * The custom ontology space for this scope. This is optional, but cannot be set after the scope has been      * setup.      */
specifier|protected
name|CustomOntologySpace
name|customSpace
decl_stmt|;
comment|/**      * The unique identifier for this scope.      */
specifier|protected
name|IRI
name|id
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ScopeOntologyListener
argument_list|>
name|listeners
init|=
operator|new
name|HashSet
argument_list|<
name|ScopeOntologyListener
argument_list|>
argument_list|()
decl_stmt|;
comment|/**      * An ontology scope knows whether it's write-locked or not. Initially it is not.      */
specifier|protected
name|boolean
name|locked
init|=
literal|false
decl_stmt|;
comment|/**      * Maps session IDs to ontology space. A single scope has at most one space per session.      */
specifier|protected
name|Map
argument_list|<
name|IRI
argument_list|,
name|SessionOntologySpace
argument_list|>
name|sessionSpaces
decl_stmt|;
specifier|public
name|OntologyScopeImpl
parameter_list|(
name|IRI
name|id
parameter_list|,
name|OntologySpaceFactory
name|factory
parameter_list|,
name|OntologyInputSource
name|coreRoot
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
name|factory
argument_list|,
name|coreRoot
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|OntologyScopeImpl
parameter_list|(
name|IRI
name|id
parameter_list|,
name|OntologySpaceFactory
name|factory
parameter_list|,
name|OntologyInputSource
name|coreRoot
parameter_list|,
name|OntologyInputSource
name|customRoot
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
name|NullPointerException
argument_list|(
literal|"Ontology scope must be identified by a non-null IRI."
argument_list|)
throw|;
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|coreSpace
operator|=
name|factory
operator|.
name|createCoreOntologySpace
argument_list|(
name|id
argument_list|,
name|coreRoot
argument_list|)
expr_stmt|;
name|this
operator|.
name|coreSpace
operator|.
name|addOntologySpaceListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// let's just lock it. Once the core space is done it's done.
name|this
operator|.
name|coreSpace
operator|.
name|setUp
argument_list|()
expr_stmt|;
comment|// if (customRoot != null) {
try|try
block|{
name|setCustomSpace
argument_list|(
name|factory
operator|.
name|createCustomOntologySpace
argument_list|(
name|id
argument_list|,
name|customRoot
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologySpaceException
name|e
parameter_list|)
block|{
comment|// Can't happen unless the factory or space implementations are
comment|// really naughty.
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
operator|.
name|warn
argument_list|(
literal|"KReS :: Ontology scope "
operator|+
name|id
operator|+
literal|" was denied creation of its own custom space upon initialization! This should not happen."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|customSpace
operator|.
name|addOntologySpaceListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// }
name|sessionSpaces
operator|=
operator|new
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|SessionOntologySpace
argument_list|>
argument_list|()
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @seeeu.iksproject.kres.api.manager.ontology.OntologyScope# addOntologyScopeListener      * (eu.iksproject.kres.api.manager.ontology.ScopeOntologyListener)      */
annotation|@
name|Override
specifier|public
name|void
name|addOntologyScopeListener
parameter_list|(
name|ScopeOntologyListener
name|listener
parameter_list|)
block|{
name|listeners
operator|.
name|add
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#addSessionSpace      * (eu.iksproject.kres.api.manager.ontology.OntologySpace, org.semanticweb.owlapi.model.IRI)      */
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|addSessionSpace
parameter_list|(
name|OntologySpace
name|sessionSpace
parameter_list|,
name|IRI
name|sessionId
parameter_list|)
throws|throws
name|UnmodifiableOntologySpaceException
block|{
if|if
condition|(
name|sessionSpace
operator|instanceof
name|SessionOntologySpace
condition|)
block|{
name|sessionSpaces
operator|.
name|put
argument_list|(
name|sessionId
argument_list|,
operator|(
name|SessionOntologySpace
operator|)
name|sessionSpace
argument_list|)
expr_stmt|;
name|sessionSpace
operator|.
name|addOntologySpaceListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|getCustomSpace
argument_list|()
operator|!=
literal|null
condition|)
operator|(
operator|(
name|SessionOntologySpace
operator|)
name|sessionSpace
operator|)
operator|.
name|attachSpace
argument_list|(
name|this
operator|.
name|getCustomSpace
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
else|else
operator|(
operator|(
name|SessionOntologySpace
operator|)
name|sessionSpace
operator|)
operator|.
name|attachSpace
argument_list|(
name|this
operator|.
name|getCoreSpace
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*      * (non-Javadoc)      *       * @seeeu.iksproject.kres.api.manager.ontology.OntologyScope# clearOntologyScopeListeners()      */
annotation|@
name|Override
specifier|public
name|void
name|clearOntologyScopeListeners
parameter_list|()
block|{
name|listeners
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|fireOntologyAdded
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
block|{
for|for
control|(
name|ScopeOntologyListener
name|listener
range|:
name|listeners
control|)
name|listener
operator|.
name|onOntologyAdded
argument_list|(
name|this
operator|.
name|getID
argument_list|()
argument_list|,
name|ontologyIri
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|fireOntologyRemoved
parameter_list|(
name|IRI
name|ontologyIri
parameter_list|)
block|{
for|for
control|(
name|ScopeOntologyListener
name|listener
range|:
name|listeners
control|)
name|listener
operator|.
name|onOntologyRemoved
argument_list|(
name|this
operator|.
name|getID
argument_list|()
argument_list|,
name|ontologyIri
argument_list|)
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#getCoreSpace()      */
annotation|@
name|Override
specifier|public
name|OntologySpace
name|getCoreSpace
parameter_list|()
block|{
return|return
name|coreSpace
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#getCustomSpace()      */
annotation|@
name|Override
specifier|public
name|OntologySpace
name|getCustomSpace
parameter_list|()
block|{
return|return
name|customSpace
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#getID()      */
annotation|@
name|Override
specifier|public
name|IRI
name|getID
parameter_list|()
block|{
return|return
name|id
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @seeeu.iksproject.kres.api.manager.ontology.OntologyScope# getOntologyScopeListeners()      */
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|ScopeOntologyListener
argument_list|>
name|getOntologyScopeListeners
parameter_list|()
block|{
return|return
name|listeners
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#getSessionSpace      * (org.semanticweb.owlapi.model.IRI)      */
annotation|@
name|Override
specifier|public
name|SessionOntologySpace
name|getSessionSpace
parameter_list|(
name|IRI
name|sessionID
parameter_list|)
block|{
return|return
name|sessionSpaces
operator|.
name|get
argument_list|(
name|sessionID
argument_list|)
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#getSessionSpaces()      */
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OntologySpace
argument_list|>
name|getSessionSpaces
parameter_list|()
block|{
return|return
operator|new
name|HashSet
argument_list|<
name|OntologySpace
argument_list|>
argument_list|(
name|sessionSpaces
operator|.
name|values
argument_list|()
argument_list|)
return|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologySpaceListener#onOntologyAdded      * (org.semanticweb.owlapi.model.IRI, org.semanticweb.owlapi.model.IRI)      */
annotation|@
name|Override
specifier|public
name|void
name|onOntologyAdded
parameter_list|(
name|IRI
name|spaceId
parameter_list|,
name|IRI
name|addedOntology
parameter_list|)
block|{
comment|// Propagate events to scope listeners
name|fireOntologyAdded
argument_list|(
name|addedOntology
argument_list|)
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @seeeu.iksproject.kres.api.manager.ontology.OntologySpaceListener#      * onOntologyRemoved(org.semanticweb.owlapi.model.IRI, org.semanticweb.owlapi.model.IRI)      */
annotation|@
name|Override
specifier|public
name|void
name|onOntologyRemoved
parameter_list|(
name|IRI
name|spaceId
parameter_list|,
name|IRI
name|removedOntology
parameter_list|)
block|{
comment|// Propagate events to scope listeners
name|fireOntologyRemoved
argument_list|(
name|removedOntology
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeOntologyScopeListener
parameter_list|(
name|ScopeOntologyListener
name|listener
parameter_list|)
block|{
name|listeners
operator|.
name|remove
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#setCustomSpace(      * eu.iksproject.kres.api.manager.ontology.OntologySpace)      */
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|setCustomSpace
parameter_list|(
name|OntologySpace
name|customSpace
parameter_list|)
throws|throws
name|UnmodifiableOntologySpaceException
block|{
if|if
condition|(
name|this
operator|.
name|customSpace
operator|!=
literal|null
operator|&&
name|this
operator|.
name|customSpace
operator|.
name|isLocked
argument_list|()
condition|)
throw|throw
operator|new
name|UnmodifiableOntologySpaceException
argument_list|(
name|getCustomSpace
argument_list|()
argument_list|)
throw|;
elseif|else
if|if
condition|(
operator|!
operator|(
name|customSpace
operator|instanceof
name|CustomOntologySpace
operator|)
condition|)
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"supplied object is not a CustomOntologySpace instance."
argument_list|)
throw|;
else|else
block|{
name|this
operator|.
name|customSpace
operator|=
operator|(
name|CustomOntologySpace
operator|)
name|customSpace
expr_stmt|;
name|this
operator|.
name|customSpace
operator|.
name|addOntologySpaceListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|customSpace
operator|.
name|attachCoreSpace
argument_list|(
name|this
operator|.
name|coreSpace
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#setUp()      */
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|setUp
parameter_list|()
block|{
if|if
condition|(
name|locked
operator|||
operator|(
name|customSpace
operator|!=
literal|null
operator|&&
operator|!
name|customSpace
operator|.
name|isLocked
argument_list|()
operator|)
condition|)
return|return;
name|this
operator|.
name|coreSpace
operator|.
name|addOntologySpaceListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|coreSpace
operator|.
name|setUp
argument_list|()
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|customSpace
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|customSpace
operator|.
name|addOntologySpaceListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|customSpace
operator|.
name|setUp
argument_list|()
expr_stmt|;
block|}
name|locked
operator|=
literal|true
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @see eu.iksproject.kres.api.manager.ontology.OntologyScope#tearDown()      */
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|tearDown
parameter_list|()
block|{
comment|// this.coreSpace.addOntologySpaceListener(this);
name|this
operator|.
name|coreSpace
operator|.
name|tearDown
argument_list|()
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|customSpace
operator|!=
literal|null
condition|)
block|{
comment|// this.customSpace.addOntologySpaceListener(this);
name|this
operator|.
name|customSpace
operator|.
name|tearDown
argument_list|()
expr_stmt|;
block|}
name|locked
operator|=
literal|false
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *       * @see java.lang.Object#toString()      */
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getID
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|synchronizeSpaces
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
block|}
block|}
end_class

end_unit

