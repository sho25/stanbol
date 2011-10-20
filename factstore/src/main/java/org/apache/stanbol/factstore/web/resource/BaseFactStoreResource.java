begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|factstore
operator|.
name|web
operator|.
name|resource
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|ScriptResource
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|resource
operator|.
name|BaseStanbolResource
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
name|factstore
operator|.
name|web
operator|.
name|FactStoreWebFragment
import|;
end_import

begin_class
specifier|public
class|class
name|BaseFactStoreResource
extends|extends
name|BaseStanbolResource
block|{
annotation|@
name|Override
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|ScriptResource
argument_list|>
name|getRegisteredScriptResources
parameter_list|()
block|{
if|if
condition|(
name|servletContext
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|ScriptResource
argument_list|>
name|scriptResources
init|=
operator|(
name|List
argument_list|<
name|ScriptResource
argument_list|>
operator|)
name|servletContext
operator|.
name|getAttribute
argument_list|(
name|SCRIPT_RESOURCES
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ScriptResource
argument_list|>
name|fragmentsScriptResources
init|=
operator|new
name|ArrayList
argument_list|<
name|ScriptResource
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ScriptResource
name|scriptResource
range|:
name|scriptResources
control|)
block|{
if|if
condition|(
name|scriptResource
operator|.
name|getFragmentName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"home"
argument_list|)
operator|||
name|scriptResource
operator|.
name|getFragmentName
argument_list|()
operator|.
name|equals
argument_list|(
name|FactStoreWebFragment
operator|.
name|NAME
argument_list|)
condition|)
block|{
name|fragmentsScriptResources
operator|.
name|add
argument_list|(
name|scriptResource
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|fragmentsScriptResources
return|;
block|}
else|else
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit
