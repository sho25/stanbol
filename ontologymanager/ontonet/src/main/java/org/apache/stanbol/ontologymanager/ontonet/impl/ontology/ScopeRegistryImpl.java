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
name|ontology
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
name|scope
operator|.
name|NoSuchScopeException
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
name|scope
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
name|scope
operator|.
name|ScopeEventListener
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
name|scope
operator|.
name|ScopeRegistry
import|;
end_import

begin_comment
comment|/**  * Default implementation of an ontology scope registry.  *   * @author alexdma  *   */
end_comment

begin_class
specifier|public
class|class
name|ScopeRegistryImpl
implements|implements
name|ScopeRegistry
block|{
specifier|protected
name|Set
argument_list|<
name|String
argument_list|>
name|activeScopeIRIs
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|ScopeEventListener
argument_list|>
name|listeners
decl_stmt|;
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|OntologyScope
argument_list|>
name|scopeMap
decl_stmt|;
specifier|public
name|ScopeRegistryImpl
parameter_list|()
block|{
name|scopeMap
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|OntologyScope
argument_list|>
argument_list|()
expr_stmt|;
name|activeScopeIRIs
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|listeners
operator|=
operator|new
name|HashSet
argument_list|<
name|ScopeEventListener
argument_list|>
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addScopeRegistrationListener
parameter_list|(
name|ScopeEventListener
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
annotation|@
name|Override
specifier|public
name|void
name|clearScopeRegistrationListeners
parameter_list|()
block|{
name|listeners
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|containsScope
parameter_list|(
name|String
name|scopeID
parameter_list|)
block|{
comment|// containsKey() is not reliable enough
return|return
name|scopeMap
operator|.
name|get
argument_list|(
name|scopeID
argument_list|)
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|deregisterScope
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
name|String
name|id
init|=
name|scope
operator|.
name|getID
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|containsScope
argument_list|(
name|id
argument_list|)
condition|)
throw|throw
operator|new
name|NoSuchScopeException
argument_list|(
name|id
argument_list|)
throw|;
comment|// For sure it is deactivated...
name|setScopeActive
argument_list|(
name|id
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// activeScopeIRIs.remove(id);
name|scopeMap
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
name|fireScopeDeregistered
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|fireScopeActivationChange
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|boolean
name|activated
parameter_list|)
block|{
name|OntologyScope
name|scope
init|=
name|scopeMap
operator|.
name|get
argument_list|(
name|scopeID
argument_list|)
decl_stmt|;
if|if
condition|(
name|activated
condition|)
for|for
control|(
name|ScopeEventListener
name|l
range|:
name|listeners
control|)
name|l
operator|.
name|scopeActivated
argument_list|(
name|scope
argument_list|)
expr_stmt|;
else|else
for|for
control|(
name|ScopeEventListener
name|l
range|:
name|listeners
control|)
name|l
operator|.
name|scopeDeactivated
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
comment|/**      * Notifies all registered scope listeners that an ontology scope has been removed.      *       * @param scope      *            the scope that was removed.      */
specifier|protected
name|void
name|fireScopeDeregistered
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
for|for
control|(
name|ScopeEventListener
name|l
range|:
name|listeners
control|)
name|l
operator|.
name|scopeUnregistered
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
comment|/**      * Notifies all registered scope listeners that an ontology scope has been added.      *       * @param scope      *            the scope that was added.      */
specifier|protected
name|void
name|fireScopeRegistered
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
for|for
control|(
name|ScopeEventListener
name|l
range|:
name|listeners
control|)
name|l
operator|.
name|scopeRegistered
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|OntologyScope
argument_list|>
name|getActiveScopes
parameter_list|()
block|{
name|Set
argument_list|<
name|OntologyScope
argument_list|>
name|scopes
init|=
operator|new
name|HashSet
argument_list|<
name|OntologyScope
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|activeScopeIRIs
control|)
name|scopes
operator|.
name|add
argument_list|(
name|scopeMap
operator|.
name|get
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|scopes
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|Set
argument_list|<
name|OntologyScope
argument_list|>
name|getRegisteredScopes
parameter_list|()
block|{
return|return
operator|new
name|HashSet
argument_list|<
name|OntologyScope
argument_list|>
argument_list|(
name|scopeMap
operator|.
name|values
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|OntologyScope
name|getScope
parameter_list|(
name|String
name|scopeID
parameter_list|)
block|{
return|return
name|scopeMap
operator|.
name|get
argument_list|(
name|scopeID
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|ScopeEventListener
argument_list|>
name|getScopeRegistrationListeners
parameter_list|()
block|{
return|return
name|listeners
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isScopeActive
parameter_list|(
name|String
name|scopeID
parameter_list|)
block|{
if|if
condition|(
operator|!
name|containsScope
argument_list|(
name|scopeID
argument_list|)
condition|)
throw|throw
operator|new
name|NoSuchScopeException
argument_list|(
name|scopeID
argument_list|)
throw|;
return|return
name|activeScopeIRIs
operator|.
name|contains
argument_list|(
name|scopeID
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|registerScope
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
name|registerScope
argument_list|(
name|scope
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|registerScope
parameter_list|(
name|OntologyScope
name|scope
parameter_list|,
name|boolean
name|activate
parameter_list|)
block|{
name|scopeMap
operator|.
name|put
argument_list|(
name|scope
operator|.
name|getID
argument_list|()
argument_list|,
name|scope
argument_list|)
expr_stmt|;
name|setScopeActive
argument_list|(
name|scope
operator|.
name|getID
argument_list|()
argument_list|,
name|activate
argument_list|)
expr_stmt|;
name|fireScopeRegistered
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeScopeRegistrationListener
parameter_list|(
name|ScopeEventListener
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
annotation|@
name|Override
specifier|public
name|void
name|setScopeActive
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|boolean
name|active
parameter_list|)
block|{
if|if
condition|(
operator|!
name|containsScope
argument_list|(
name|scopeID
argument_list|)
condition|)
throw|throw
operator|new
name|NoSuchScopeException
argument_list|(
name|scopeID
argument_list|)
throw|;
comment|// Prevent no-changes from firing events.
name|boolean
name|previousStatus
init|=
name|isScopeActive
argument_list|(
name|scopeID
argument_list|)
decl_stmt|;
name|OntologyScope
name|scope
init|=
name|getScope
argument_list|(
name|scopeID
argument_list|)
decl_stmt|;
if|if
condition|(
name|active
operator|==
name|previousStatus
condition|)
return|return;
if|if
condition|(
name|active
condition|)
block|{
name|scope
operator|.
name|setUp
argument_list|()
expr_stmt|;
name|activeScopeIRIs
operator|.
name|add
argument_list|(
name|scopeID
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|scope
operator|.
name|tearDown
argument_list|()
expr_stmt|;
name|activeScopeIRIs
operator|.
name|remove
argument_list|(
name|scopeID
argument_list|)
expr_stmt|;
block|}
name|fireScopeActivationChange
argument_list|(
name|scopeID
argument_list|,
name|active
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

