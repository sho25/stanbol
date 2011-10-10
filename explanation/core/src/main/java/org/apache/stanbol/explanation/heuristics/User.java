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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_interface
specifier|public
interface|interface
name|User
extends|extends
name|Entity
block|{
comment|/**      * This method is used to obtain all the objects that identify the user in multiple contexts (e.g.      * foaf:name, user name in the CMS, Social Security Number etc.).      *       * @return      */
name|Set
argument_list|<
name|Identifier
argument_list|>
name|getIDs
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

