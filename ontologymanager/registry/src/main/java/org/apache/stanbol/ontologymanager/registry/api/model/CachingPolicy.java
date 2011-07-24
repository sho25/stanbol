begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
package|;
end_package

begin_comment
comment|/**  * The possible policies a registry manager can adopt for distributed caching.  */
end_comment

begin_enum
specifier|public
enum|enum
name|CachingPolicy
block|{
comment|/**      * A single ontology manager will be used for all known registries, which implies that only one possible      * version of each ontology can be loaded at one time.      */
name|CROSS_REGISTRY
block|,
comment|/**      * Every registry is assigned its own ontology manager for caching ontologies once they are loaded. If a      * library is referenced across multiple registries, an ontology set will be instantiated for each.      */
name|PER_REGISTRY
block|; }
end_enum

end_unit

