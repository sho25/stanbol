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
name|topic
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|topic
operator|.
name|training
operator|.
name|TrainingSetException
import|;
end_import

begin_interface
specifier|public
interface|interface
name|BatchProcessor
parameter_list|<
name|T
parameter_list|>
block|{
name|int
name|process
parameter_list|(
name|List
argument_list|<
name|T
argument_list|>
name|batch
parameter_list|)
throws|throws
name|ClassifierException
throws|,
name|TrainingSetException
function_decl|;
block|}
end_interface

end_unit
