begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|servicesapi
operator|.
name|rdf
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
name|stanbol
operator|.
name|enhancer
operator|.
name|servicesapi
operator|.
name|EnhancementEngine
import|;
end_import

begin_comment
comment|/**  * Defines the {@link UriRef}s for all classes and properties defined by the  * Stanbol Enhancer Execution Plan ontology.  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|ExecutionPlan
block|{
specifier|private
name|ExecutionPlan
parameter_list|()
block|{
comment|/* No instances of Utility classes*/
block|}
comment|/**      * The Class ep:ExecutionPlan      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|EXECUTION_PLAN
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|ep
operator|+
literal|"ExecutionPlan"
argument_list|)
decl_stmt|;
comment|/**      * The property ep:chain linking an {@link #EXECUTION_PLAN} to the name      * of the chain this plan is defined for      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|CHAIN
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|ep
operator|+
literal|"chain"
argument_list|)
decl_stmt|;
comment|/**      * the property ep:hasExecutionNode linking an {@link #EXECUTION_PLAN} with      * all its {@link #EXECUTION_NODE}s      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|HAS_EXECUTION_NODE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|ep
operator|+
literal|"hasExecutionNode"
argument_list|)
decl_stmt|;
comment|/**      * The Class ep:ExecutionMode      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|EXECUTION_NODE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|ep
operator|+
literal|"ExecutionNode"
argument_list|)
decl_stmt|;
comment|/**      * The property ep:engine linking an {@link #EXECUTION_NODE} with the name of       * the {@link EnhancementEngine} to be executed.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|ENGINE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|ep
operator|+
literal|"engine"
argument_list|)
decl_stmt|;
comment|/**      * The property ep:dependsOn defining the list of other {@link #EXECUTION_NODE}s      * this one depends on      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|DEPENDS_ON
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|ep
operator|+
literal|"dependsOn"
argument_list|)
decl_stmt|;
comment|/**      * The property ep:optional that can be used to define that the execution of      * an {@link #EXECUTION_NODE} is optional. The default is<code>false</code>.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|OPTIONAL
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|ep
operator|+
literal|"optional"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

