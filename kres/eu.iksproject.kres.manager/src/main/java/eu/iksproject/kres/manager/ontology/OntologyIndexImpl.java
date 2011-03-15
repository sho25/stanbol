begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
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
name|KReSONManager
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
name|ontology
operator|.
name|OntologyIndex
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
name|ScopeRegistry
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

begin_class
specifier|public
class|class
name|OntologyIndexImpl
implements|implements
name|OntologyIndex
block|{
specifier|private
specifier|final
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
comment|/* 	 * We only use IRIs, so the actual scopes can get garbage-collected once 	 * they are deregistered. 	 */
specifier|private
name|Map
argument_list|<
name|IRI
argument_list|,
name|Set
argument_list|<
name|IRI
argument_list|>
argument_list|>
name|ontScopeMap
decl_stmt|;
specifier|private
name|ScopeRegistry
name|scopeRegistry
decl_stmt|;
specifier|private
name|KReSONManager
name|onm
decl_stmt|;
specifier|public
name|OntologyIndexImpl
parameter_list|(
name|KReSONManager
name|onm
parameter_list|)
block|{
name|ontScopeMap
operator|=
operator|new
name|HashMap
argument_list|<
name|IRI
argument_list|,
name|Set
argument_list|<
name|IRI
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
if|if
condition|(
name|onm
operator|==
literal|null
condition|)
name|this
operator|.
name|scopeRegistry
operator|=
operator|new
name|ScopeRegistryImpl
argument_list|()
expr_stmt|;
else|else
block|{
name|this
operator|.
name|onm
operator|=
name|onm
expr_stmt|;
name|this
operator|.
name|scopeRegistry
operator|=
name|onm
operator|.
name|getScopeRegistry
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|scopeRegistry
operator|.
name|addScopeRegistrationListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Given a scope, puts its ontologies in its scopeMap 	 *  	 * @param scope 	 */
specifier|private
name|void
name|addScopeOntologies
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
for|for
control|(
name|OWLOntology
name|o
range|:
name|getOntologiesForScope
argument_list|(
name|scope
argument_list|)
control|)
block|{
name|IRI
name|ontid
init|=
name|o
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|IRI
argument_list|>
name|scopez
init|=
name|ontScopeMap
operator|.
name|get
argument_list|(
name|ontid
argument_list|)
decl_stmt|;
if|if
condition|(
name|scopez
operator|==
literal|null
condition|)
name|scopez
operator|=
operator|new
name|HashSet
argument_list|<
name|IRI
argument_list|>
argument_list|()
expr_stmt|;
name|scopez
operator|.
name|add
argument_list|(
name|scope
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|ontScopeMap
operator|.
name|put
argument_list|(
name|ontid
argument_list|,
name|scopez
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|getOntologiesForScope
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
name|Set
argument_list|<
name|OWLOntology
argument_list|>
name|ontologies
init|=
operator|new
name|HashSet
argument_list|<
name|OWLOntology
argument_list|>
argument_list|()
decl_stmt|;
try|try
block|{
comment|// ontologies.add(scope.getCoreSpace().getTopOntology());
name|ontologies
operator|.
name|addAll
argument_list|(
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getOntologies
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{ 		}
try|try
block|{
name|ontologies
operator|.
name|addAll
argument_list|(
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getOntologies
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{ 		}
comment|// for (OWLOntology o : ontologies) {
comment|// System.out.println(o.getOntologyID());
comment|// for (OWLImportsDeclaration im: o.getImportsDeclarations())
comment|// System.out.println("\t"+im);
comment|// }
return|return
name|ontologies
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
parameter_list|)
block|{
name|Set
argument_list|<
name|IRI
argument_list|>
name|scopez
init|=
name|ontScopeMap
operator|.
name|get
argument_list|(
name|ontologyIri
argument_list|)
decl_stmt|;
if|if
condition|(
name|scopez
operator|==
literal|null
operator|||
name|scopez
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|;
name|OntologyScope
name|scope
init|=
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|scopez
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|ont
operator|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getOntology
argument_list|(
name|ontologyIri
argument_list|)
expr_stmt|;
if|if
condition|(
name|ont
operator|!=
literal|null
condition|)
return|return
name|ont
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{ 		}
try|try
block|{
name|ont
operator|=
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getOntology
argument_list|(
name|ontologyIri
argument_list|)
expr_stmt|;
if|if
condition|(
name|ont
operator|!=
literal|null
condition|)
return|return
name|ont
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{ 		}
return|return
name|ont
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
name|IRI
name|scopeId
parameter_list|)
block|{
name|OWLOntology
name|ont
init|=
literal|null
decl_stmt|;
name|OntologyScope
name|scope
init|=
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|scopeId
argument_list|)
decl_stmt|;
try|try
block|{
name|ont
operator|=
name|scope
operator|.
name|getCustomSpace
argument_list|()
operator|.
name|getOntology
argument_list|(
name|ontologyIri
argument_list|)
expr_stmt|;
if|if
condition|(
name|ont
operator|!=
literal|null
condition|)
return|return
name|ont
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{ 		}
try|try
block|{
name|ont
operator|=
name|scope
operator|.
name|getCoreSpace
argument_list|()
operator|.
name|getOntology
argument_list|(
name|ontologyIri
argument_list|)
expr_stmt|;
if|if
condition|(
name|ont
operator|!=
literal|null
condition|)
return|return
name|ont
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{ 		}
return|return
name|ont
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|IRI
argument_list|>
name|getReferencingScopes
parameter_list|(
name|IRI
name|ontologyIRI
parameter_list|,
name|boolean
name|includingSessionSpaces
parameter_list|)
block|{
return|return
name|ontScopeMap
operator|.
name|get
argument_list|(
name|ontologyIRI
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isOntologyLoaded
parameter_list|(
name|IRI
name|ontologyIRI
parameter_list|)
block|{
name|Set
argument_list|<
name|IRI
argument_list|>
name|scopez
init|=
name|ontScopeMap
operator|.
name|get
argument_list|(
name|ontologyIRI
argument_list|)
decl_stmt|;
return|return
name|scopez
operator|!=
literal|null
operator|&&
operator|!
name|scopez
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onOntologyAdded
parameter_list|(
name|IRI
name|scopeId
parameter_list|,
name|IRI
name|addedOntology
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Ontology "
operator|+
name|addedOntology
operator|+
literal|" added to scope "
operator|+
name|scopeId
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|IRI
argument_list|>
name|scopez
init|=
name|ontScopeMap
operator|.
name|get
argument_list|(
name|addedOntology
argument_list|)
decl_stmt|;
if|if
condition|(
name|scopez
operator|==
literal|null
condition|)
name|scopez
operator|=
operator|new
name|HashSet
argument_list|<
name|IRI
argument_list|>
argument_list|()
expr_stmt|;
name|scopez
operator|.
name|add
argument_list|(
name|scopeId
argument_list|)
expr_stmt|;
name|ontScopeMap
operator|.
name|put
argument_list|(
name|addedOntology
argument_list|,
name|scopez
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|IRI
argument_list|>
name|scopez2
init|=
name|ontScopeMap
operator|.
name|get
argument_list|(
name|addedOntology
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|scopez2
operator|.
name|contains
argument_list|(
name|scopeId
argument_list|)
condition|)
name|log
operator|.
name|warn
argument_list|(
literal|"Addition was not reindexed!"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onOntologyRemoved
parameter_list|(
name|IRI
name|scopeId
parameter_list|,
name|IRI
name|removedOntology
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Removing ontology "
operator|+
name|removedOntology
operator|+
literal|" from scope "
operator|+
name|scopeId
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|IRI
argument_list|>
name|scopez
init|=
name|ontScopeMap
operator|.
name|get
argument_list|(
name|removedOntology
argument_list|)
decl_stmt|;
if|if
condition|(
name|scopez
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|scopez
operator|.
name|contains
argument_list|(
name|scopeId
argument_list|)
condition|)
name|scopez
operator|.
name|remove
argument_list|(
name|scopeId
argument_list|)
expr_stmt|;
else|else
block|{
comment|//				System.out.println("...but it was not indexed!");
name|log
operator|.
name|warn
argument_list|(
literal|"The scope "
operator|+
name|scopeId
operator|+
literal|" is not indexed"
argument_list|)
expr_stmt|;
block|}
name|Set
argument_list|<
name|IRI
argument_list|>
name|scopez2
init|=
name|ontScopeMap
operator|.
name|get
argument_list|(
name|removedOntology
argument_list|)
decl_stmt|;
if|if
condition|(
name|scopez2
operator|.
name|contains
argument_list|(
name|scopeId
argument_list|)
condition|)
comment|/** 				 * FIXME 				 * This message is obscure 				 */
name|log
operator|.
name|warn
argument_list|(
literal|"Removal was not reindexed!"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|removeScopeOntologies
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Removing all ontologies from Scope "
operator|+
name|scope
argument_list|)
expr_stmt|;
for|for
control|(
name|OWLOntology
name|o
range|:
name|getOntologiesForScope
argument_list|(
name|scope
argument_list|)
control|)
block|{
name|IRI
name|ontid
init|=
name|o
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|IRI
argument_list|>
name|scopez
init|=
name|ontScopeMap
operator|.
name|get
argument_list|(
name|ontid
argument_list|)
decl_stmt|;
if|if
condition|(
name|scopez
operator|!=
literal|null
condition|)
block|{
name|scopez
operator|.
name|remove
argument_list|(
name|scope
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|scopez
operator|.
name|isEmpty
argument_list|()
condition|)
name|ontScopeMap
operator|.
name|remove
argument_list|(
name|ontid
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|scopeActivated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Scope "
operator|+
name|scope
operator|.
name|getID
argument_list|()
operator|+
literal|" activated."
argument_list|)
expr_stmt|;
name|scope
operator|.
name|removeOntologyScopeListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|scopeCreated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
comment|// scope.addOntologyScopeListener(this);
name|this
operator|.
name|scopeDeactivated
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|scopeDeactivated
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
comment|// Scope has been deactivated but not due to deregistration
comment|// if (scopeRegistry.containsScope(scope.getID())) {
name|scope
operator|.
name|addOntologyScopeListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Adding index as listener for scope "
operator|+
name|scope
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
comment|// }
name|log
operator|.
name|info
argument_list|(
literal|"Scope "
operator|+
name|scope
operator|.
name|getID
argument_list|()
operator|+
literal|" deactivated."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|scopeDeregistered
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Scope "
operator|+
name|scope
operator|.
name|getID
argument_list|()
operator|+
literal|" deregistered."
argument_list|)
expr_stmt|;
name|this
operator|.
name|scopeDeactivated
argument_list|(
name|scope
argument_list|)
expr_stmt|;
name|removeScopeOntologies
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|scopeRegistered
parameter_list|(
name|OntologyScope
name|scope
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Scope "
operator|+
name|scope
operator|.
name|getID
argument_list|()
operator|+
literal|" registered. Now you can check for its activation status."
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|OntologyScope
argument_list|>
name|scopez
init|=
name|scopeRegistry
operator|.
name|getRegisteredScopes
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|token
range|:
name|onm
operator|.
name|getUrisToActivate
argument_list|()
control|)
block|{
try|try
block|{
name|IRI
name|scopeId
init|=
name|IRI
operator|.
name|create
argument_list|(
name|token
operator|.
name|trim
argument_list|()
argument_list|)
decl_stmt|;
name|scopeRegistry
operator|.
name|setScopeActive
argument_list|(
name|scopeId
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|scopez
operator|.
name|remove
argument_list|(
name|scopeRegistry
operator|.
name|getScope
argument_list|(
name|scopeId
argument_list|)
argument_list|)
expr_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"KReS :: Ontology scope "
operator|+
name|scopeId
operator|+
literal|" "
operator|+
literal|" activated."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchScopeException
name|ex
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"KReS :: Tried to activate unavailable scope "
operator|+
name|token
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|log
operator|.
name|error
argument_list|(
literal|"Exception caught while activating scope "
operator|+
name|token
operator|+
literal|" "
operator|+
name|ex
operator|.
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
block|}
comment|// Stop deactivating other scopes
comment|//		for (OntologyScope scopp : scopez) {
comment|//			IRI scopeId = scopp.getID();
comment|//			try {
comment|//				if (scopeRegistry.isScopeActive(scopeId)) {
comment|//					scopeRegistry.setScopeActive(scopeId, false);
comment|//					System.out.println("KReS :: Ontology scope " + scopeId
comment|//							+ " " + " deactivated.");
comment|//				}
comment|//			} catch (NoSuchScopeException ex) {
comment|//				// Shouldn't happen because we already have the scope handle,
comment|//				// however exceptions could be thrown erroneously...
comment|//				System.err
comment|//						.println("KReS :: Tried to deactivate unavailable scope "
comment|//								+ scopeId + ".");
comment|//			} catch (Exception ex) {
comment|//				System.err.println("Exception caught while deactivating scope "
comment|//						+ scope.getID() + " " + ex.getClass());
comment|//				continue;
comment|//			}
comment|//		}
name|addScopeOntologies
argument_list|(
name|scope
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

