begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|jcr
operator|.
name|processor
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|jcr
operator|.
name|Session
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|OntologyResourceHelper
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|MappingEngine
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|repository
operator|.
name|RepositoryAccess
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|JCRProcessor
block|{
specifier|protected
name|MappingEngine
name|engine
decl_stmt|;
specifier|protected
name|Session
name|session
decl_stmt|;
specifier|protected
name|RepositoryAccess
name|accessor
decl_stmt|;
specifier|protected
name|OntologyResourceHelper
name|ontologyResourceHelper
decl_stmt|;
specifier|protected
name|OntModel
name|jcrOntModel
decl_stmt|;
specifier|public
name|JCRProcessor
parameter_list|(
name|MappingEngine
name|engine
parameter_list|)
block|{
name|this
operator|.
name|engine
operator|=
name|engine
expr_stmt|;
name|this
operator|.
name|session
operator|=
operator|(
name|Session
operator|)
name|engine
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|this
operator|.
name|ontologyResourceHelper
operator|=
name|this
operator|.
name|engine
operator|.
name|getOntologyResourceHelper
argument_list|()
expr_stmt|;
name|this
operator|.
name|jcrOntModel
operator|=
name|this
operator|.
name|engine
operator|.
name|getOntModel
argument_list|()
expr_stmt|;
name|this
operator|.
name|accessor
operator|=
name|this
operator|.
name|engine
operator|.
name|getRepositoryAccessManager
argument_list|()
operator|.
name|getRepositoryAccess
argument_list|(
name|this
operator|.
name|engine
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|accessor
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Can not find suitable accessor"
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|boolean
name|matches
parameter_list|(
name|String
name|path
parameter_list|,
name|String
name|query
parameter_list|)
block|{
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|query
operator|.
name|endsWith
argument_list|(
literal|"%"
argument_list|)
condition|)
block|{
return|return
name|path
operator|.
name|contains
argument_list|(
name|query
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|query
operator|.
name|length
argument_list|()
operator|-
literal|2
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|path
operator|.
name|equals
argument_list|(
name|query
argument_list|)
return|;
block|}
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

