begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|stlab
operator|.
name|xd
operator|.
name|registry
operator|.
name|models
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_interface
specifier|public
interface|interface
name|RegistryItem
block|{
specifier|public
specifier|abstract
name|String
name|getName
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|RegistryLibrary
name|getParent
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|URL
name|getURL
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|boolean
name|isLibrary
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|boolean
name|isOntology
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|void
name|setName
parameter_list|(
name|String
name|string
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|void
name|setParent
parameter_list|(
name|RegistryLibrary
name|parent
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|void
name|setURL
parameter_list|(
name|URL
name|url
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|String
name|toString
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

