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
name|nlp
operator|.
name|morpho
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
name|UriRef
import|;
end_import

begin_enum
specifier|public
enum|enum
name|Definitness
block|{
comment|/**      * Value referring to the capacity of identification of an entity. (http://www.isocat.org/datcat/DC-2004)      *<p>      * An entity is specified as definite when it refers to a particularized individual of the species denoted      * by the noun. (http://languagelink.let.uu.nl/tds/onto/LinguisticOntology.owl#definite)      *<p>      * Definite noun phrases are used to refer to entities which are specific and identifiable in a given      * context. (http://en.wikipedia.org/wiki/Definiteness 20.11.06)      */
name|Definite
block|,
comment|/**      * An entity is specified as indefinite when it refers to a non-particularized individual of the species      * denoted by the noun. (http://languagelink.let.uu.nl/tds/onto/LinguisticOntology.owl#indefinite)      *<p>      * Indefinite noun phrases are used to refer to entities which are not specific and identifiable in a      * given context. (http://en.wikipedia.org/wiki/Definiteness 20.11.06)      */
name|Indefinite
block|;
specifier|static
specifier|final
name|String
name|OLIA_NAMESPACE
init|=
literal|"http://purl.org/olia/olia.owl#"
decl_stmt|;
name|UriRef
name|uri
decl_stmt|;
name|Definitness
parameter_list|()
block|{
name|uri
operator|=
operator|new
name|UriRef
argument_list|(
name|OLIA_NAMESPACE
operator|+
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|UriRef
name|getUri
parameter_list|()
block|{
return|return
name|uri
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
name|uri
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
block|}
end_enum

end_unit
