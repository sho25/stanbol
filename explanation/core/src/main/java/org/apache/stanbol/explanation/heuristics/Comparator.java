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

begin_interface
specifier|public
interface|interface
name|Comparator
block|{
comment|/**      *       * @param arg0      * @param arg1      * @return a positive integer if<tt>arg1</tt> compares greater than<tt>arg2</tt>, a negative integer if      *<tt>arg1</tt> compares greater than<tt>arg2</tt>, zero if they are equal.      *       * @throws IncomparableException      *             if the two entities are not comparable by this criterion.      */
name|int
name|compare
parameter_list|(
name|Entity
name|arg0
parameter_list|,
name|Entity
name|arg1
parameter_list|)
throws|throws
name|IncomparableException
function_decl|;
block|}
end_interface

end_unit

