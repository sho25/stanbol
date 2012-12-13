begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|engines
operator|.
name|entityhublinking
package|;
end_package

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
name|UriRef
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
name|enhancer
operator|.
name|engines
operator|.
name|entitylinking
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|EnhancementEngineHelper
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
name|model
operator|.
name|clerezza
operator|.
name|RdfValueFactory
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
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_class
specifier|public
class|class
name|EntityhubEntity
extends|extends
name|Entity
block|{
specifier|private
specifier|static
name|RdfValueFactory
name|vf
init|=
name|RdfValueFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|UriRef
name|entityRanking
init|=
operator|new
name|UriRef
argument_list|(
name|RdfResourceEnum
operator|.
name|entityRank
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
specifier|public
name|EntityhubEntity
parameter_list|(
name|Representation
name|rep
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|UriRef
argument_list|(
name|rep
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|,
operator|(
name|MGraph
operator|)
name|vf
operator|.
name|toRdfRepresentation
argument_list|(
name|rep
argument_list|)
operator|.
name|getRdfGraph
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Float
name|getEntityRanking
parameter_list|()
block|{
return|return
name|EnhancementEngineHelper
operator|.
name|get
argument_list|(
name|data
argument_list|,
name|uri
argument_list|,
name|entityRanking
argument_list|,
name|Float
operator|.
name|class
argument_list|,
name|lf
argument_list|)
return|;
block|}
block|}
end_class

end_unit
