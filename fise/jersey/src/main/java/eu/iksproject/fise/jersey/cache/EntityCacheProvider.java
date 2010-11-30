begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|jersey
operator|.
name|cache
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

begin_interface
specifier|public
interface|interface
name|EntityCacheProvider
block|{
specifier|public
name|MGraph
name|getEntityCache
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

