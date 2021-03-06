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
name|multiplexer
operator|.
name|clerezza
operator|.
name|collector
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

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
name|Dictionary
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
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Reference
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|multiplexer
operator|.
name|clerezza
operator|.
name|impl
operator|.
name|CoreSpaceImpl
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
name|multiplexer
operator|.
name|clerezza
operator|.
name|impl
operator|.
name|CustomSpaceImpl
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
name|multiplexer
operator|.
name|clerezza
operator|.
name|impl
operator|.
name|ScopeImpl
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
name|servicesapi
operator|.
name|collector
operator|.
name|DuplicateIDException
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
name|servicesapi
operator|.
name|collector
operator|.
name|OntologyCollectorListener
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
name|servicesapi
operator|.
name|collector
operator|.
name|UnmodifiableOntologyCollectorException
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
name|servicesapi
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
name|servicesapi
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
name|servicesapi
operator|.
name|scope
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
name|servicesapi
operator|.
name|scope
operator|.
name|OntologySpace
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
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|scope
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
name|servicesapi
operator|.
name|scope
operator|.
name|Scope
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
name|servicesapi
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
name|servicesapi
operator|.
name|scope
operator|.
name|ScopeFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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
comment|/**  * Implementation of {@link OntologySpaceFactory} based on Clerezza.  *   * @author alexdma  *   */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|false
argument_list|)
annotation|@
name|Service
argument_list|(
block|{
name|OntologySpaceFactory
operator|.
name|class
block|,
name|ScopeFactory
operator|.
name|class
block|}
argument_list|)
specifier|public
class|class
name|ClerezzaCollectorFactory
implements|implements
name|OntologySpaceFactory
implements|,
name|ScopeFactory
block|{
specifier|protected
name|Collection
argument_list|<
name|ScopeEventListener
argument_list|>
name|listeners
decl_stmt|;
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
name|IRI
name|namespace
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|OntologyProvider
argument_list|<
name|TcProvider
argument_list|>
name|ontologyProvider
decl_stmt|;
specifier|public
name|ClerezzaCollectorFactory
parameter_list|()
block|{
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
specifier|public
name|ClerezzaCollectorFactory
parameter_list|(
name|OntologyProvider
argument_list|<
name|TcProvider
argument_list|>
name|provider
parameter_list|,
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|ontologyProvider
operator|=
name|provider
expr_stmt|;
try|try
block|{
name|activate
argument_list|(
name|configuration
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Unable to access servlet context."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Used to configure an instance within an OSGi container.      *       * @throws IOException      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|ClerezzaCollectorFactory
operator|.
name|class
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No valid"
operator|+
name|ComponentContext
operator|.
name|class
operator|+
literal|" parsed in activate!"
argument_list|)
throw|;
block|}
name|activate
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called within both OSGi and non-OSGi environments.      *       * @param configuration      * @throws IOException      */
specifier|protected
name|void
name|activate
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|debug
argument_list|(
name|ClerezzaCollectorFactory
operator|.
name|class
operator|+
literal|" activated."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addScopeEventListener
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
name|clearScopeEventListeners
parameter_list|()
block|{
name|listeners
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|configureSpace
parameter_list|(
name|OntologySpace
name|s
parameter_list|,
name|String
name|scopeID
parameter_list|)
block|{
comment|// Make sure someone is listening to ontology additions before core ontologies are added.
if|if
condition|(
name|ontologyProvider
operator|instanceof
name|OntologyCollectorListener
condition|)
name|s
operator|.
name|addOntologyCollectorListener
argument_list|(
operator|(
name|OntologyCollectorListener
operator|)
name|ontologyProvider
argument_list|)
expr_stmt|;
else|else
name|s
operator|.
name|addOntologyCollectorListener
argument_list|(
name|ontologyProvider
operator|.
name|getOntologyNetworkDescriptor
argument_list|()
argument_list|)
expr_stmt|;
comment|// s.setUp();
block|}
comment|/**      * Utility method for configuring ontology spaces after creating them.      *       * @param s      * @param scopeID      * @param rootSource      */
specifier|private
name|void
name|configureSpace
parameter_list|(
name|OntologySpace
name|s
parameter_list|,
name|String
name|scopeID
parameter_list|,
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
modifier|...
name|ontologySources
parameter_list|)
block|{
name|configureSpace
argument_list|(
name|s
argument_list|,
name|scopeID
argument_list|)
expr_stmt|;
if|if
condition|(
name|ontologySources
operator|!=
literal|null
condition|)
try|try
block|{
for|for
control|(
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
name|src
range|:
name|ontologySources
control|)
name|s
operator|.
name|addOntology
argument_list|(
name|src
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnmodifiableOntologyCollectorException
name|e
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Ontology space "
operator|+
name|s
operator|.
name|getID
argument_list|()
operator|+
literal|" was found locked at creation time!"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// s.setUp();
block|}
annotation|@
name|Override
specifier|public
name|OntologySpace
name|createCoreOntologySpace
parameter_list|(
name|String
name|scopeId
parameter_list|,
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
modifier|...
name|coreSources
parameter_list|)
block|{
name|OntologySpace
name|s
init|=
operator|new
name|CoreSpaceImpl
argument_list|(
name|scopeId
argument_list|,
name|namespace
argument_list|,
name|ontologyProvider
argument_list|)
decl_stmt|;
name|configureSpace
argument_list|(
name|s
argument_list|,
name|scopeId
argument_list|,
name|coreSources
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
annotation|@
name|Override
specifier|public
name|OntologySpace
name|createCustomOntologySpace
parameter_list|(
name|String
name|scopeId
parameter_list|,
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
modifier|...
name|customSources
parameter_list|)
block|{
name|OntologySpace
name|s
init|=
operator|new
name|CustomSpaceImpl
argument_list|(
name|scopeId
argument_list|,
name|namespace
argument_list|,
name|ontologyProvider
argument_list|)
decl_stmt|;
name|configureSpace
argument_list|(
name|s
argument_list|,
name|scopeId
argument_list|,
name|customSources
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
annotation|@
name|Override
specifier|public
name|Scope
name|createOntologyScope
parameter_list|(
name|String
name|scopeID
parameter_list|,
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
modifier|...
name|coreOntologies
parameter_list|)
throws|throws
name|DuplicateIDException
block|{
comment|// Scope constructor also creates core and custom spaces
name|Scope
name|scope
init|=
operator|new
name|ScopeImpl
argument_list|(
name|scopeID
argument_list|,
name|getDefaultNamespace
argument_list|()
argument_list|,
name|this
argument_list|,
name|coreOntologies
argument_list|)
decl_stmt|;
name|fireScopeCreated
argument_list|(
name|scope
argument_list|)
expr_stmt|;
return|return
name|scope
return|;
block|}
annotation|@
name|Override
specifier|public
name|OntologySpace
name|createOntologySpace
parameter_list|(
name|String
name|scopeId
parameter_list|,
name|SpaceType
name|type
parameter_list|,
name|OntologyInputSource
argument_list|<
name|?
argument_list|>
modifier|...
name|ontologySources
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|CORE
case|:
return|return
name|createCoreOntologySpace
argument_list|(
name|scopeId
argument_list|,
name|ontologySources
argument_list|)
return|;
case|case
name|CUSTOM
case|:
return|return
name|createCustomOntologySpace
argument_list|(
name|scopeId
argument_list|,
name|ontologySources
argument_list|)
return|;
default|default:
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Deactivation of the ONManagerImpl resets all its resources.      */
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|namespace
operator|=
literal|null
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|ClerezzaCollectorFactory
operator|.
name|class
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|fireScopeCreated
parameter_list|(
name|Scope
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
name|scopeCreated
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|IRI
name|getDefaultNamespace
parameter_list|()
block|{
return|return
name|this
operator|.
name|namespace
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getID
parameter_list|()
block|{
return|return
name|this
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|IRI
name|getNamespace
parameter_list|()
block|{
return|return
name|getDefaultNamespace
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|ScopeEventListener
argument_list|>
name|getScopeEventListeners
parameter_list|()
block|{
return|return
name|listeners
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeScopeEventListener
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
name|setDefaultNamespace
parameter_list|(
name|IRI
name|namespace
parameter_list|)
block|{
name|this
operator|.
name|namespace
operator|=
name|namespace
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setNamespace
parameter_list|(
name|IRI
name|namespace
parameter_list|)
block|{
name|setDefaultNamespace
argument_list|(
name|namespace
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

