begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|refactor
operator|.
name|api
package|;
end_package

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

begin_comment
comment|/**  *   * @author andrea.nuzzolese  *  */
end_comment

begin_class
specifier|public
class|class
name|SemionRefactoringException
extends|extends
name|Exception
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
name|IRI
name|recipeIRI
decl_stmt|;
comment|/** 	 * Creates a new instance of SemionRefactoringException. 	 */
specifier|public
name|SemionRefactoringException
parameter_list|()
block|{ 		 	}
block|}
end_class

end_unit

