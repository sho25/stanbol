begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
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
name|Collection
import|;
end_import

begin_comment
comment|/**  * Implementations of this interface are able to fire events related to the  * modification of ontologies within an ontology scope.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ScopeOntologyListenable
block|{
specifier|public
name|void
name|addOntologyScopeListener
parameter_list|(
name|ScopeOntologyListener
name|listener
parameter_list|)
function_decl|;
specifier|public
name|void
name|clearOntologyScopeListeners
parameter_list|()
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ScopeOntologyListener
argument_list|>
name|getOntologyScopeListeners
parameter_list|()
function_decl|;
specifier|public
name|void
name|removeOntologyScopeListener
parameter_list|(
name|ScopeOntologyListener
name|listener
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

