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
name|heuristics
package|;
end_package

begin_comment
comment|/**  * Any entity that can be compared to another one using Description Logic constructs.  *   * @author alessandro  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Matcher
block|{
comment|/**      *       * @param arg the entity to compare this one against.      * @return true iff the two entities denote the same entity.      */
name|boolean
name|matches
parameter_list|(
name|Entity
name|arg0
parameter_list|,
name|Entity
name|arg1
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

