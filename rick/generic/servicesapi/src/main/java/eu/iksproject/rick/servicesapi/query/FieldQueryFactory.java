begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|query
package|;
end_package

begin_comment
comment|/**  * Factory interface for creating instances of FieldQueries  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|FieldQueryFactory
block|{
comment|/** 	 * Creates a new field query instance without any constraints or selected 	 * fields 	 * @return a new and empty field query instance 	 */
name|FieldQuery
name|createFieldQuery
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

