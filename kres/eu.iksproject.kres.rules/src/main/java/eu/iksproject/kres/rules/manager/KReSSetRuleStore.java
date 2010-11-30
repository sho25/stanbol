begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|rules
operator|.
name|manager
package|;
end_package

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|rules
operator|.
name|RuleStore
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|rules
operator|.
name|SetRuleStore
import|;
end_import

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
name|Service
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
name|OWLOntologyCreationException
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
comment|/**  *  * @author elvio  */
end_comment

begin_comment
comment|//@Component(immediate = true, metatype = true)
end_comment

begin_class
annotation|@
name|Service
argument_list|(
name|SetRuleStore
operator|.
name|class
argument_list|)
specifier|public
class|class
name|KReSSetRuleStore
implements|implements
name|SetRuleStore
block|{
specifier|private
name|String
name|file
decl_stmt|;
specifier|private
name|RuleStore
name|store
decl_stmt|;
specifier|private
name|KReSLoadRuleFile
name|load
decl_stmt|;
specifier|private
name|OWLOntology
name|ont
decl_stmt|;
specifier|private
name|SetRuleStore
name|setRuleStore
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|KReSSetRuleStore
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * To directly set the store with a file contains the rules and the recipe      * @param filepath {The string contains the file path.}      */
specifier|public
name|KReSSetRuleStore
parameter_list|(
name|String
name|filepath
parameter_list|)
block|{
name|this
operator|.
name|file
operator|=
name|filepath
expr_stmt|;
name|this
operator|.
name|store
operator|=
operator|new
name|KReSRuleStore
argument_list|()
expr_stmt|;
name|this
operator|.
name|load
operator|=
operator|new
name|KReSLoadRuleFile
argument_list|(
name|file
argument_list|,
name|store
argument_list|)
expr_stmt|;
block|}
comment|/**      * To directly set the store with an ontology alredy contains rules and recipe      * @param ontology      * @throws OWLOntologyCreationException      */
specifier|public
name|KReSSetRuleStore
parameter_list|(
name|File
name|ontology
parameter_list|)
block|{
name|this
operator|.
name|store
operator|=
operator|new
name|KReSRuleStore
argument_list|()
expr_stmt|;
try|try
block|{
name|this
operator|.
name|ont
operator|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
operator|.
name|loadOntologyFromOntologyDocument
argument_list|(
name|ontology
argument_list|)
expr_stmt|;
name|this
operator|.
name|store
operator|.
name|setStore
argument_list|(
name|ont
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * To get the rule store;      * @return {A RuleStore object.}      */
specifier|public
name|RuleStore
name|returnStore
parameter_list|()
block|{
return|return
name|this
operator|.
name|load
operator|.
name|getStore
argument_list|()
return|;
block|}
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|,
name|String
name|filepath
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Activated KReS Set Rule Store"
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Deactivated KReS Set Rule Store"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

