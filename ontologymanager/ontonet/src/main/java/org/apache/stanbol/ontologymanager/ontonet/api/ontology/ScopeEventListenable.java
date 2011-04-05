begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
comment|/**  * Implementations of this interface are able to fire events related to the  * modification of an ontology scope, not necessarily including its ontologies.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ScopeEventListenable
block|{
name|void
name|addScopeEventListener
parameter_list|(
name|ScopeEventListener
name|listener
parameter_list|)
function_decl|;
name|void
name|clearScopeEventListeners
parameter_list|()
function_decl|;
name|Collection
argument_list|<
name|ScopeEventListener
argument_list|>
name|getScopeEventListeners
parameter_list|()
function_decl|;
name|void
name|removeScopeEventListener
parameter_list|(
name|ScopeEventListener
name|listener
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

