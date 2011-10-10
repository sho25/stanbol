begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
operator|.
name|api
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_comment
comment|/**  * A registry of knowledge schemas to be matched against in order to filter relevant statements for  * explanations.  *   * @author alessandro  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SchemaCatalog
block|{
specifier|public
specifier|static
specifier|final
name|String
name|CUSTOM_SCHEMAS
init|=
literal|"org.apache.stanbol.explanation.schema.catalog.customschemas"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"org.apache.stanbol.explanation.schema.catalog.id"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|LOCATION
init|=
literal|"org.apache.stanbol.explanation.schema.catalog.location"
decl_stmt|;
comment|/**      * Adds or updates a schema in the catalog. If the exact same or an equal schema is already present in the      * catalog, no action is taken. If a different schema with the same ID is present, it will be replaced.      *       * @param schema      */
name|void
name|addSchema
parameter_list|(
name|Schema
name|schema
parameter_list|)
function_decl|;
comment|/**      * Removes all schemas from the catalog.      */
name|void
name|clearSchems
parameter_list|()
function_decl|;
comment|/**      * Returns the catalog identifier.      *       * @return the catalog identifier.      */
name|String
name|getId
parameter_list|()
function_decl|;
comment|/**      *       * @param schemaID      * @return the requested schema, or null if not present.      */
name|Schema
name|getSchema
parameter_list|(
name|IRI
name|schemaID
parameter_list|)
function_decl|;
comment|/**      * Returns all the schemas in the catalog.      *       * @return      */
name|Set
argument_list|<
name|Schema
argument_list|>
name|getSchemas
parameter_list|()
function_decl|;
comment|/**      * Determines if a schema with the given ID is already present in the catalog.      *       * @param schemaID      * @return true if a schema with<code>schemaID</code> is present, false otherwise.      */
name|boolean
name|hasSchema
parameter_list|(
name|IRI
name|schemaID
parameter_list|)
function_decl|;
comment|/**      * Removes any schema whose ID is equal to the supplied one. If no such schema is present, no action is      * taken.      *       * @param schemaId      */
name|void
name|removeSchema
parameter_list|(
name|IRI
name|schemaId
parameter_list|)
function_decl|;
comment|/**      * Tries to remove. Note that if a schema with the same ID as the supplied one, but which is not      * equivalent by {@link Object#equals(Object)}, is found, it will not be removed. To ensure that any      * schema with that ID is removed, use {@link #removeSchema(IRI)}.      *       * @param schema      */
name|void
name|removeSchema
parameter_list|(
name|Schema
name|schema
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

