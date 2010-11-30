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
name|rules
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
name|manager
operator|.
name|ontology
operator|.
name|OntologySpace
import|;
end_import

begin_comment
comment|/**  *   * @author andrea.nuzzolese  *  */
end_comment

begin_class
specifier|public
class|class
name|NoSuchRecipeException
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
comment|/** 	 * Creates a new instance of OntologySpaceModificationException. 	 *  	 * @param space 	 *            the ontology space whose modification was attempted. 	 */
specifier|public
name|NoSuchRecipeException
parameter_list|(
name|IRI
name|recipeIRI
parameter_list|)
block|{
name|this
operator|.
name|recipeIRI
operator|=
name|recipeIRI
expr_stmt|;
block|}
comment|/** 	 * Returns the {@link IRI} of the recipe that threw the exception. 	 *  	 * @return the recipe {@link IRI} on which the exception was thrown. 	 */
specifier|public
name|IRI
name|getRecipeIRI
parameter_list|()
block|{
return|return
name|recipeIRI
return|;
block|}
block|}
end_class

end_unit

