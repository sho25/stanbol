begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|standalone
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Triple
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|NoSuchEntityException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|access
operator|.
name|WeightedTcProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|SimpleMGraph
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
name|Reference
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|ContentItem
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|Store
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|ContentItemHelper
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|InMemoryContentItem
import|;
end_import

begin_comment
comment|/** Trivial in-memory Store for standalone FISE server */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|false
argument_list|)
annotation|@
name|Service
comment|// Use a low service ranking so that "real" stores replace this
annotation|@
name|Property
argument_list|(
name|name
operator|=
literal|"service.ranking"
argument_list|,
name|intValue
operator|=
operator|-
literal|1000
argument_list|)
specifier|public
class|class
name|InMemoryStore
implements|implements
name|Store
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|InMemoryStore
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|ContentItem
argument_list|>
name|data
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ContentItem
argument_list|>
argument_list|()
decl_stmt|;
annotation|@
name|Reference
specifier|private
name|WeightedTcProvider
name|tcProvider
decl_stmt|;
annotation|@
name|Property
argument_list|(
name|value
operator|=
literal|"http://fise.iks-project.eu/graphs/enhancements"
argument_list|)
specifier|public
specifier|static
specifier|final
name|String
name|GRAPH_URI
init|=
literal|"eu.iksproject.fise.standalone.store.graphUri"
decl_stmt|;
specifier|public
name|InMemoryStore
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|ContentItem
name|create
parameter_list|(
name|String
name|id
parameter_list|,
name|byte
index|[]
name|content
parameter_list|,
name|String
name|mimeType
parameter_list|)
block|{
name|UriRef
name|uri
init|=
name|id
operator|==
literal|null
condition|?
name|ContentItemHelper
operator|.
name|makeDefaultUrn
argument_list|(
name|content
argument_list|)
else|:
operator|new
name|UriRef
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"create ContentItem for id "
operator|+
name|uri
operator|+
literal|" on TC Manager= "
operator|+
name|tcProvider
argument_list|)
expr_stmt|;
specifier|final
name|MGraph
name|g
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
return|return
operator|new
name|InMemoryContentItem
argument_list|(
name|uri
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
name|content
argument_list|,
name|mimeType
argument_list|,
name|g
argument_list|)
return|;
block|}
specifier|public
name|ContentItem
name|get
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|ContentItem
name|result
decl_stmt|;
synchronized|synchronized
init|(
name|data
init|)
block|{
name|result
operator|=
name|data
operator|.
name|get
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
specifier|public
name|String
name|put
parameter_list|(
name|ContentItem
name|ci
parameter_list|)
block|{
synchronized|synchronized
init|(
name|data
init|)
block|{
name|data
operator|.
name|put
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|,
name|ci
argument_list|)
expr_stmt|;
comment|// remove any previously stored data about ci
name|MGraph
name|g
init|=
name|getEnhancementGraph
argument_list|()
decl_stmt|;
name|UriRef
name|uri
init|=
operator|new
name|UriRef
argument_list|(
name|ci
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|toRemove
init|=
name|g
operator|.
name|filter
argument_list|(
name|uri
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
while|while
condition|(
name|toRemove
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|toRemove
operator|.
name|next
argument_list|()
expr_stmt|;
name|toRemove
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|toRemove
operator|=
name|g
operator|.
name|filter
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
name|uri
argument_list|)
expr_stmt|;
while|while
condition|(
name|toRemove
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|toRemove
operator|.
name|next
argument_list|()
expr_stmt|;
name|toRemove
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
comment|// TODO: how to handle orphan indirect triples?
comment|// accumulate all triples recently collected
name|getEnhancementGraph
argument_list|()
operator|.
name|addAll
argument_list|(
name|ci
operator|.
name|getMetadata
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ci
operator|.
name|getId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|public
name|MGraph
name|getEnhancementGraph
parameter_list|()
block|{
specifier|final
name|UriRef
name|graphUri
init|=
operator|new
name|UriRef
argument_list|(
name|GRAPH_URI
argument_list|)
decl_stmt|;
try|try
block|{
return|return
name|tcProvider
operator|.
name|getMGraph
argument_list|(
name|graphUri
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchEntityException
name|e
parameter_list|)
block|{
return|return
name|tcProvider
operator|.
name|createMGraph
argument_list|(
name|graphUri
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

