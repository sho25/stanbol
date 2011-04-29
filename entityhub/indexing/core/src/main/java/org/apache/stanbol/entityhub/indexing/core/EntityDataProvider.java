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
name|core
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
name|servicesapi
operator|.
name|model
operator|.
name|Representation
import|;
end_import

begin_comment
comment|/**  * Interface used to get the representation (data) for an entity based on the  * id. This Interface is used for indexing in cases, where the list of entities  * to index is known in advance and the data source provides the possibility to  * retrieve the entity data based on the ID (e.g. a RDF triple store).  * @see {@link EntityDataIterator}  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityDataProvider
extends|extends
name|IndexingComponent
block|{
name|Representation
name|getEntityData
parameter_list|(
name|String
name|id
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

