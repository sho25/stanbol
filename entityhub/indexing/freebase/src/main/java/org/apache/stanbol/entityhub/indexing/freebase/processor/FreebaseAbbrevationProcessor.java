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
name|indexing
operator|.
name|freebase
operator|.
name|processor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringUtils
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
name|indexing
operator|.
name|core
operator|.
name|EntityProcessor
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
name|defaults
operator|.
name|NamespaceEnum
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
name|Text
import|;
end_import

begin_class
specifier|public
class|class
name|FreebaseAbbrevationProcessor
implements|implements
name|EntityProcessor
block|{
specifier|public
specifier|static
specifier|final
name|String
name|FB_NS
init|=
literal|"http://rdf.freebase.com/ns/"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|FB_ALIAS
init|=
name|FB_NS
operator|+
literal|"common.topic.alias"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|FB_NAME
init|=
name|FB_NS
operator|+
literal|"type.object.name"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|RDFS_LABEL
init|=
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
parameter_list|)
block|{     }
annotation|@
name|Override
specifier|public
name|boolean
name|needsInitialisation
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|initialise
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{     }
annotation|@
name|Override
specifier|public
name|Representation
name|process
parameter_list|(
name|Representation
name|rep
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Text
argument_list|>
name|aliases
init|=
name|rep
operator|.
name|getText
argument_list|(
name|FB_ALIAS
argument_list|)
decl_stmt|;
while|while
condition|(
name|aliases
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Text
name|alias
init|=
name|aliases
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|StringUtils
operator|.
name|isAllUpperCase
argument_list|(
name|alias
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
name|rep
operator|.
name|add
argument_list|(
name|FB_NAME
argument_list|,
name|alias
argument_list|)
expr_stmt|;
name|rep
operator|.
name|add
argument_list|(
name|RDFS_LABEL
argument_list|,
name|alias
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|rep
return|;
block|}
block|}
end_class

end_unit

