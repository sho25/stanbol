begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|ldpath
operator|.
name|backend
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|core
operator|.
name|mapping
operator|.
name|ValueConverterFactory
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
name|entityhub
operator|.
name|core
operator|.
name|model
operator|.
name|InMemoryValueFactory
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
name|entityhub
operator|.
name|core
operator|.
name|query
operator|.
name|DefaultQueryFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|EntityhubException
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Entity
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|Representation
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|ValueFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQuery
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|FieldQueryFactory
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|QueryResultList
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|site
operator|.
name|ReferencedSiteManager
import|;
end_import

begin_class
specifier|public
class|class
name|SiteManagerBackend
extends|extends
name|AbstractBackend
block|{
specifier|protected
specifier|final
name|ReferencedSiteManager
name|siteManager
decl_stmt|;
specifier|private
name|ValueFactory
name|vf
init|=
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
name|FieldQueryFactory
name|qf
init|=
name|DefaultQueryFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|public
name|SiteManagerBackend
parameter_list|(
name|ReferencedSiteManager
name|siteManager
parameter_list|)
block|{
name|this
argument_list|(
name|siteManager
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SiteManagerBackend
parameter_list|(
name|ReferencedSiteManager
name|siteManager
parameter_list|,
name|ValueConverterFactory
name|valueConverter
parameter_list|)
block|{
name|super
argument_list|(
name|valueConverter
argument_list|)
expr_stmt|;
if|if
condition|(
name|siteManager
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The parsed ReferencedSiteManager MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|siteManager
operator|=
name|siteManager
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|FieldQuery
name|createQuery
parameter_list|()
block|{
return|return
name|qf
operator|.
name|createFieldQuery
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Representation
name|getRepresentation
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|EntityhubException
block|{
name|Entity
name|entity
init|=
name|siteManager
operator|.
name|getEntity
argument_list|(
name|id
argument_list|)
decl_stmt|;
return|return
name|entity
operator|!=
literal|null
condition|?
name|entity
operator|.
name|getRepresentation
argument_list|()
else|:
literal|null
return|;
block|}
annotation|@
name|Override
specifier|protected
name|ValueFactory
name|getValueFactory
parameter_list|()
block|{
return|return
name|vf
return|;
block|}
annotation|@
name|Override
specifier|protected
name|QueryResultList
argument_list|<
name|String
argument_list|>
name|query
parameter_list|(
name|FieldQuery
name|query
parameter_list|)
throws|throws
name|EntityhubException
block|{
return|return
name|siteManager
operator|.
name|findIds
argument_list|(
name|query
argument_list|)
return|;
block|}
block|}
end_class

end_unit

