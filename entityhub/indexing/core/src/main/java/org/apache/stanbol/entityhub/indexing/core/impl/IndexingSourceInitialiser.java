begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EventObject
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
name|entityhub
operator|.
name|indexing
operator|.
name|core
operator|.
name|IndexingComponent
import|;
end_import

begin_comment
comment|/**  * Initialises an {@link IndexingComponent} and {@link IndexerImpl#notifyState()}   * if finished  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|IndexingSourceInitialiser
implements|implements
name|Runnable
block|{
specifier|private
specifier|final
name|IndexingComponent
name|source
decl_stmt|;
specifier|private
name|boolean
name|finished
init|=
literal|false
decl_stmt|;
specifier|private
specifier|final
name|Set
argument_list|<
name|IndexingSourceInitialiserListener
argument_list|>
name|listeners
decl_stmt|;
specifier|public
name|IndexingSourceInitialiser
parameter_list|(
name|IndexingComponent
name|source
parameter_list|)
block|{
name|this
operator|.
name|source
operator|=
name|source
expr_stmt|;
name|this
operator|.
name|listeners
operator|=
name|Collections
operator|.
name|synchronizedSet
argument_list|(
operator|new
name|HashSet
argument_list|<
name|IndexingSourceInitialiserListener
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|run
parameter_list|()
block|{
name|source
operator|.
name|initialise
argument_list|()
expr_stmt|;
name|finished
operator|=
literal|true
expr_stmt|;
name|fireInitialisationCompletedEvent
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|finished
parameter_list|()
block|{
return|return
name|finished
return|;
block|}
specifier|public
name|boolean
name|addIndexingSourceInitialiserListener
parameter_list|(
name|IndexingSourceInitialiserListener
name|listener
parameter_list|)
block|{
if|if
condition|(
name|listener
operator|!=
literal|null
condition|)
block|{
return|return
name|listeners
operator|.
name|add
argument_list|(
name|listener
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|public
name|boolean
name|removeIndexingSourceInitialiserListener
parameter_list|(
name|IndexingSourceInitialiserListener
name|listener
parameter_list|)
block|{
if|if
condition|(
name|listener
operator|!=
literal|null
condition|)
block|{
return|return
name|listeners
operator|.
name|remove
argument_list|(
name|listener
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|protected
name|void
name|fireInitialisationCompletedEvent
parameter_list|()
block|{
name|Set
argument_list|<
name|IndexingSourceInitialiserListener
argument_list|>
name|copy
decl_stmt|;
synchronized|synchronized
init|(
name|listeners
init|)
block|{
name|copy
operator|=
operator|new
name|HashSet
argument_list|<
name|IndexingSourceInitialiserListener
argument_list|>
argument_list|(
name|listeners
argument_list|)
expr_stmt|;
block|}
name|IndexingSourceEventObject
name|eventObject
init|=
operator|new
name|IndexingSourceEventObject
argument_list|(
name|this
argument_list|,
name|this
operator|.
name|source
argument_list|)
decl_stmt|;
for|for
control|(
name|IndexingSourceInitialiserListener
name|listener
range|:
name|copy
control|)
block|{
empty_stmt|;
name|listener
operator|.
name|indexingSourceInitialised
argument_list|(
name|eventObject
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
interface|interface
name|IndexingSourceInitialiserListener
block|{
name|void
name|indexingSourceInitialised
parameter_list|(
name|IndexingSourceEventObject
name|eventObject
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
class|class
name|IndexingSourceEventObject
extends|extends
name|EventObject
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|1L
decl_stmt|;
specifier|private
specifier|final
name|IndexingComponent
name|indexingSource
decl_stmt|;
specifier|protected
name|IndexingSourceEventObject
parameter_list|(
name|IndexingSourceInitialiser
name|initialiser
parameter_list|,
name|IndexingComponent
name|source
parameter_list|)
block|{
name|super
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|indexingSource
operator|=
name|source
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|IndexingSourceInitialiser
name|getSource
parameter_list|()
block|{
return|return
operator|(
name|IndexingSourceInitialiser
operator|)
name|super
operator|.
name|getSource
argument_list|()
return|;
block|}
comment|/**          * @return the indexingSource          */
specifier|public
name|IndexingComponent
name|getIndexingSource
parameter_list|()
block|{
return|return
name|indexingSource
return|;
block|}
block|}
block|}
end_class

end_unit

