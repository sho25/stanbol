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
name|geonames
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
name|core
operator|.
name|model
operator|.
name|InMemoryValueFactory
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
name|Reference
import|;
end_import

begin_enum
specifier|public
enum|enum
name|GeonamesPropertyEnum
block|{
comment|/*      * idx ... temporarily used during indexing      */
name|idx_id
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx
argument_list|,
literal|"id"
argument_list|)
block|,
comment|//the integer id
name|idx_CC
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx
argument_list|,
literal|"CC"
argument_list|)
block|,
comment|//the country code
name|idx_ADM
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx
argument_list|,
literal|"ADM"
argument_list|)
block|,
comment|//the string ADM
name|idx_ADM1
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx
argument_list|,
literal|"ADM1"
argument_list|)
block|,
comment|//the string ADM1
name|idx_ADM2
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx
argument_list|,
literal|"ADM2"
argument_list|)
block|,
comment|//the string ADM1.ADM2
name|idx_ADM3
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx
argument_list|,
literal|"ADM3"
argument_list|)
block|,
comment|//the string ADM1.ADM2.ADM3
name|idx_ADM4
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|idx
argument_list|,
literal|"ADM4"
argument_list|)
block|,
comment|//the string ADM1.ADM2.ADM3.ADM4
name|rdf_type
argument_list|(
name|NamespaceEnum
operator|.
name|rdf
operator|.
name|getNamespace
argument_list|()
argument_list|,
literal|"type"
argument_list|)
block|,
name|rdfs_label
argument_list|(
name|NamespaceEnum
operator|.
name|rdfs
operator|.
name|getNamespace
argument_list|()
argument_list|,
literal|"label"
argument_list|)
block|,
name|dc_creator
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|dct
argument_list|,
literal|"creator"
argument_list|)
block|,
name|dc_date
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|dct
argument_list|,
literal|"date"
argument_list|)
block|,
name|gn_Feature
argument_list|(
literal|"Feature"
argument_list|)
block|,
comment|//gn_Country("Country"),
name|gn_countryCode
argument_list|(
literal|"countryCode"
argument_list|)
block|,
comment|//gn_Map("Map"),
comment|//gn_RDFData("RDFData"),
comment|//gn_WikipediaArticle("WikipediaArticle"),
name|gn_parentFeature
argument_list|(
literal|"parentFeature"
argument_list|)
block|,
name|gn_parentCountry
argument_list|(
literal|"parentCountry"
argument_list|)
block|,
name|gn_parentADM1
argument_list|(
literal|"parentADM1"
argument_list|)
block|,
name|gn_parentADM2
argument_list|(
literal|"parentADM2"
argument_list|)
block|,
name|gn_parentADM3
argument_list|(
literal|"parentADM3"
argument_list|)
block|,
name|gn_parentADM4
argument_list|(
literal|"parentADM4"
argument_list|)
block|,
comment|//gn_childrenFeatures("childrenFeatures"),
comment|//gn_inCountry("inCountry"),
comment|//gn_locatedIn("locatedIn"),
comment|//gn_locationMap("locationMap"),
comment|//gn_nearby("nearby"),
comment|//gn_nearbyFeatures("nearbyFeatures"),
comment|//gn_neighbour("neighbour"),
comment|//gn_neighbouringFeatures("neighbouringFeatures"),
name|gn_wikipediaArticle
argument_list|(
literal|"wikipediaArticle"
argument_list|)
block|,
name|gn_featureClass
argument_list|(
literal|"featureClass"
argument_list|)
block|,
name|gn_featureCode
argument_list|(
literal|"featureCode"
argument_list|)
block|,
comment|//gn_tag("tag"),
name|gn_alternateName
argument_list|(
literal|"alternateName"
argument_list|)
block|,
name|gn_officialName
argument_list|(
literal|"officialName"
argument_list|)
block|,
name|gn_name
argument_list|(
literal|"name"
argument_list|)
block|,
name|gn_population
argument_list|(
literal|"population"
argument_list|)
block|,
name|gn_shortName
argument_list|(
literal|"shortName"
argument_list|)
block|,
name|gn_colloquialName
argument_list|(
literal|"colloquialName"
argument_list|)
block|,
name|gn_postalCode
argument_list|(
literal|"postalCode"
argument_list|)
block|,
name|geo_lat
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|geo
argument_list|,
literal|"lat"
argument_list|)
block|,
name|geo_long
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|geo
argument_list|,
literal|"long"
argument_list|)
block|,
name|geo_alt
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|geo
argument_list|,
literal|"alt"
argument_list|)
block|,
name|skos_notation
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"notation"
argument_list|)
block|,
name|skos_prefLabel
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"prefLabel"
argument_list|)
block|,
name|skos_altLabel
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"altLabel"
argument_list|)
block|,
name|skos_hiddenLabel
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"hiddenLabel"
argument_list|)
block|,
name|skos_note
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"note"
argument_list|)
block|,
name|skos_changeNote
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"changeNote"
argument_list|)
block|,
name|skos_definition
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"definition"
argument_list|)
block|,
name|skos_editorialNote
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"editorialNote"
argument_list|)
block|,
name|skos_example
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"example"
argument_list|)
block|,
name|skos_historyNote
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"historyNote"
argument_list|)
block|,
name|skos_scopeNote
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"scopeNote"
argument_list|)
block|,
name|skos_broader
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"broader"
argument_list|)
block|,
name|skos_narrower
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"narrower"
argument_list|)
block|,
name|skos_related
argument_list|(
name|GeonamesPropertyEnum
operator|.
name|skos
argument_list|,
literal|"related"
argument_list|)
block|,     ;
specifier|private
specifier|static
specifier|final
name|String
name|idx
init|=
literal|"urn:stanbol:entityhub:indexing:geonames:"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|ns
init|=
literal|"http://www.geonames.org/ontology#"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|geo
init|=
literal|"http://www.w3.org/2003/01/geo/wgs84_pos#"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|skos
init|=
literal|"http://www.w3.org/2004/02/skos/core#"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|dct
init|=
literal|"http://purl.org/dc/terms/"
decl_stmt|;
specifier|private
name|String
name|uri
decl_stmt|;
specifier|private
name|Reference
name|ref
decl_stmt|;
name|GeonamesPropertyEnum
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
name|GeonamesPropertyEnum
parameter_list|(
name|String
name|namespace
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|uri
operator|=
operator|(
name|namespace
operator|==
literal|null
condition|?
name|ns
else|:
name|namespace
operator|)
operator|+
name|name
expr_stmt|;
name|ref
operator|=
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
operator|.
name|createReference
argument_list|(
name|uri
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
specifier|public
name|Reference
name|getReference
parameter_list|()
block|{
return|return
name|ref
return|;
block|}
block|}
end_enum

end_unit

