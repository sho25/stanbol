begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
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
comment|/**  * A cognitive schema, linguistic frame or knowledge pattern.  *   * @author alessandro  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Schema
block|{
specifier|public
name|IRI
name|getID
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

