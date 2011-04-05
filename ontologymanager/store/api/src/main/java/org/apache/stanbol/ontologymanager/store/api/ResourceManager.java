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
name|store
operator|.
name|api
package|;
end_package

begin_interface
specifier|public
interface|interface
name|ResourceManager
block|{
comment|/**      * For registration of the ontology to the Resource Manager      *       * @param ontologyURI      *            the URI of the ontology to be registered      */
name|void
name|registerOntology
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
comment|/**      * For registration of the ontology class to the Resource Manager The Resource Manager assigns a unique      * URL (or path) to the ontology and updates the internal hashtables and database      *       * @param ontologyURI      *            the URI of the ontology      * @param classURI      *            the URI of the class that is being registered      */
name|void
name|registerClass
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|classURI
parameter_list|)
function_decl|;
comment|/**      * For registration of the data type property to the Resource Manager The Resource Manager assigns a      * unique URL (or path) to the data type property and updates the internal hashtables and database      *       * @param ontologyURI      *            the URI of the ontology      * @param dataPropertyURI      *            the URI of the data type property that is being registered      */
name|void
name|registerDatatypeProperty
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|dataPropertyURI
parameter_list|)
function_decl|;
comment|/**      * For registration of the object property to the Resource Manager The Resource Manager assigns a unique      * URL (or path) to the object property and updates the internal hashtables and database      *       * @param ontologyURI      *            the URI of the ontology      * @param objectPropertyURI      *            the URI of the object property that is being registered      */
name|void
name|registerObjectProperty
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|objectPropertyURI
parameter_list|)
function_decl|;
comment|/**      * For registration of the individual to the Resource Manager The Resource Manager assigns a unique URL      * (or path) to the individual and updates the internal hashtables and database      *       * @param ontologyURI      *            the URI of the ontology      * @param individualURI      *            the URI of the individual that is being registered      */
name|void
name|registerIndividual
parameter_list|(
name|String
name|ontologyURI
parameter_list|,
name|String
name|individualURI
parameter_list|)
function_decl|;
name|boolean
name|hasOntology
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
name|String
name|getOntologyPath
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
name|String
name|getOntologyFullPath
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
name|String
name|getResourceFullPath
parameter_list|(
name|String
name|resourceURI
parameter_list|)
function_decl|;
name|String
name|getOntologyURIForPath
parameter_list|(
name|String
name|ontologyPath
parameter_list|)
function_decl|;
name|String
name|getResourceURIForPath
parameter_list|(
name|String
name|ontologyPath
parameter_list|,
name|String
name|resourcePath
parameter_list|)
function_decl|;
comment|/**      * Converts referenceable REST sub-path of a class, property or individual into URI      *       * @param entityPath      *            Path to be converted      * @return URI of the specified entity      */
name|String
name|convertEntityRelativePathToURI
parameter_list|(
name|String
name|entityPath
parameter_list|)
function_decl|;
name|String
name|getResourceType
parameter_list|(
name|String
name|resourceURI
parameter_list|)
function_decl|;
name|void
name|removeOntology
parameter_list|(
name|String
name|ontologyURI
parameter_list|)
function_decl|;
name|void
name|removeResource
parameter_list|(
name|String
name|resourceURI
parameter_list|)
function_decl|;
comment|// FIXME:: make sure that this method returns the reference to the imported
comment|// class!!!
name|String
name|resolveOntologyURIFromResourceURI
parameter_list|(
name|String
name|resourceURI
parameter_list|)
function_decl|;
comment|/**      * To be used together with Jena's cleanDB function which deletes all stored triples      */
name|void
name|clearResourceManager
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

