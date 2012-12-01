begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|namespaceprefix
operator|.
name|provider
operator|.
name|stanbol
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Property
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|namespaceprefix
operator|.
name|NamespacePrefixProvider
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
name|namespaceprefix
operator|.
name|impl
operator|.
name|NamespacePrefixProviderImpl
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
name|namespaceprefix
operator|.
name|mappings
operator|.
name|DefaultNamespaceMappingsEnum
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|value
operator|=
literal|"1000000"
argument_list|)
specifier|public
class|class
name|DefaultNamespaceMappingsProvider
extends|extends
name|NamespacePrefixProviderImpl
implements|implements
name|NamespacePrefixProvider
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DefaultNamespaceMappingsProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|DefaultNamespaceMappingsProvider
parameter_list|()
block|{
name|super
argument_list|(
name|Collections
operator|.
name|EMPTY_MAP
argument_list|)
expr_stmt|;
for|for
control|(
name|DefaultNamespaceMappingsEnum
name|m
range|:
name|DefaultNamespaceMappingsEnum
operator|.
name|values
argument_list|()
control|)
block|{
name|String
name|current
init|=
name|addMapping
argument_list|(
name|m
operator|.
name|getPrefix
argument_list|()
argument_list|,
name|m
operator|.
name|getNamespace
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|current
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Found duplicate mapping for prefix '{}'->[{},{}] in {}"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|m
operator|.
name|getPrefix
argument_list|()
block|,
name|current
block|,
name|m
operator|.
name|getNamespace
argument_list|()
block|,
name|DefaultNamespaceMappingsEnum
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit
