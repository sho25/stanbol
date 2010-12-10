begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|rdf
package|;
end_package

begin_enum
specifier|public
enum|enum
name|NamespaceEnum
block|{
comment|// TODO: use capital for constants instead of lower-case
name|fise
argument_list|(
literal|"http://fise.iks-project.eu/ontology/"
argument_list|)
block|,
name|dbpedia_ont
argument_list|(
literal|"dbpedia-ont"
argument_list|,
literal|"http://dbpedia.org/ontology/"
argument_list|)
block|,
name|rdf
argument_list|(
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
argument_list|)
block|,
name|rdfs
argument_list|(
literal|"http://www.w3.org/2000/01/rdf-schema#"
argument_list|)
block|,
name|dc
argument_list|(
literal|"http://purl.org/dc/terms/"
argument_list|)
block|,
name|skos
argument_list|(
literal|"http://www.w3.org/2004/02/skos/core#"
argument_list|)
block|,
name|foaf
argument_list|(
literal|"http://xmlns.com/foaf/0.1/"
argument_list|)
block|,
name|geonames
argument_list|(
literal|"http://www.geonames.org/ontology#"
argument_list|)
block|,
name|georss
argument_list|(
literal|"http://www.georss.org/georss/"
argument_list|)
block|,
name|geo
argument_list|(
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#"
argument_list|)
block|,
name|nie
argument_list|(
literal|"http://www.semanticdesktop.org/ontologies/2007/01/19/nie#"
argument_list|)
block|;
name|String
name|ns
decl_stmt|;
name|String
name|prefix
decl_stmt|;
name|NamespaceEnum
parameter_list|(
name|String
name|ns
parameter_list|)
block|{
if|if
condition|(
name|ns
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The namespace MUST NOT be NULL"
argument_list|)
throw|;
block|}
name|this
operator|.
name|ns
operator|=
name|ns
expr_stmt|;
block|}
name|NamespaceEnum
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|ns
parameter_list|)
block|{
name|this
argument_list|(
name|ns
argument_list|)
expr_stmt|;
name|this
operator|.
name|prefix
operator|=
name|prefix
expr_stmt|;
block|}
specifier|public
name|String
name|getNamespace
parameter_list|()
block|{
return|return
name|ns
return|;
block|}
specifier|public
name|String
name|getPrefix
parameter_list|()
block|{
return|return
name|prefix
operator|==
literal|null
condition|?
name|name
argument_list|()
else|:
name|prefix
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
name|ns
return|;
block|}
block|}
end_enum

end_unit

