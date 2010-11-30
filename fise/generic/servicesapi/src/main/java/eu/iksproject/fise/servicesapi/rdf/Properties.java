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

begin_comment
comment|/**  * Namespace of standard properties to be used as typed metadata by  * EnhancementEngine.  *  * Copy and paste the URLs in a browser to access the official definitions (RDF  * schema) of those properties to.  *  * @author ogrisel  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Properties
block|{
comment|/**      * The canonical way to give the type of a resource. It is very common that      * the target of this property is an owl:Class such as the ones defined is      * {@link OntologyClass}      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|RDF_TYPE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|rdf
operator|+
literal|"type"
argument_list|)
decl_stmt|;
comment|/**      * A label for resources of any type.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|RDFS_LABEL
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|rdfs
operator|+
literal|"label"
argument_list|)
decl_stmt|;
comment|/**      * Used to relate a content item to another named resource such as a person,      * a location, an organization, ...      *      * @deprecated use FISE_ENTITY instead      */
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_REFERENCES
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"references"
argument_list|)
decl_stmt|;
comment|/**      * Creation date of a resource. Used by FISE to annotate the creation date      * of the enhancement by the enhancement engine      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_CREATED
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"created"
argument_list|)
decl_stmt|;
comment|/**      * The entity responsible for the creation of a resource. Used by FISE to      * annotate the enhancement engine that created an enhancement      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_CREATOR
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"creator"
argument_list|)
decl_stmt|;
comment|/**      * The nature or genre of the resource. FISE uses this property to refer to      * the type of the enhancement. Values should be URIs defined in some      * controlled vocabulary      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_TYPE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"type"
argument_list|)
decl_stmt|;
comment|/**      * A related resource that is required by the described resource to support      * its function, delivery, or coherence. FISE uses this property to refer to      * other enhancements an enhancement depends on.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_REQUIRES
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"requires"
argument_list|)
decl_stmt|;
comment|/**      * A related resource. FISE uses this property to define enhancements that      * are referred by the actual one      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_RELATION
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"relation"
argument_list|)
decl_stmt|;
comment|/**      * A point on the surface of the earth given by two signed floats (latitude      * and longitude) concatenated as a string literal using a whitespace as      * separator.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|GEORSS_POINT
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|georss
operator|+
literal|"point"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|GEO_LAT
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|geo
operator|+
literal|"lat"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|GEO_LONG
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|geo
operator|+
literal|"long"
argument_list|)
decl_stmt|;
comment|/**      * Refers to the content item the enhancement was extracted form      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_EXTRACTED_FROM
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"extracted-from"
argument_list|)
decl_stmt|;
comment|/**      * the character position of the start of a text selection.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_START
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"start"
argument_list|)
decl_stmt|;
comment|/**      * the character position of the end of a text selection.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_END
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"end"
argument_list|)
decl_stmt|;
comment|/**      * The text selected by the text annotation. This is an optional property      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_SELECTED_TEXT
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"selected-text"
argument_list|)
decl_stmt|;
comment|/**      * The context (surroundings) of the text selected. (e.g. the sentence      * containing a person selected by a NLP enhancer)      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_SELECTION_CONTEXT
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"selection-context"
argument_list|)
decl_stmt|;
comment|/**      * A positive double value to rank extractions according to the algorithm      * confidence in the accuracy of the extraction.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_CONFIDENCE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"confidence"
argument_list|)
decl_stmt|;
comment|/**      * This refers to the URI identifying the referred named entity      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_ENTITY_REFERENCE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"entity-reference"
argument_list|)
decl_stmt|;
comment|/**      * This property can be used to specify the type of the entity (Optional)      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_ENTITY_TYPE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"entity-type"
argument_list|)
decl_stmt|;
comment|/**      * The label(s) of the referred entity      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_ENTITY_LABEL
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|fise
operator|+
literal|"entity-label"
argument_list|)
decl_stmt|;
comment|/**      * Internet Media Type of a content item.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_FILEFORMAT
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"FileFormat"
argument_list|)
decl_stmt|;
comment|/**      * Language of the content item text.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_LANGUAGE
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"language"
argument_list|)
decl_stmt|;
comment|/** 	 * Plain text content of a content item. 	  */
specifier|public
specifier|static
specifier|final
name|UriRef
name|NIE_PLAINTEXTCONTENT
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|nie
operator|+
literal|"plainTextContent"
argument_list|)
decl_stmt|;
comment|/**      * The topic of the resource. Used to relate a content item to a      * skos:Concept modelling one of the overall topic of the content.      *      * @deprecated rwesten: To my knowledge no longer used by FISE enhancement      *             specification      */
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|DC_SUBJECT
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|dc
operator|+
literal|"subject"
argument_list|)
decl_stmt|;
comment|/**      * The sha1 hexadecimal digest of a content item.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|FOAF_SHA1
init|=
operator|new
name|UriRef
argument_list|(
name|NamespaceEnum
operator|.
name|foaf
operator|+
literal|"sha1"
argument_list|)
decl_stmt|;
comment|/**      * Link an semantic extraction or a manual annotation to a content item.      */
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_RELATED_CONTENT_ITEM
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://iksproject.eu/ns/extraction/source-content-item"
argument_list|)
decl_stmt|;
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_RELATED_TOPIC
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://iksproject.eu/ns/extraction/related-topic"
argument_list|)
decl_stmt|;
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_RELATED_TOPIC_LABEL
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://iksproject.eu/ns/extraction/related-topic-label"
argument_list|)
decl_stmt|;
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_MENTIONED_ENTITY_POSITION_START
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://iksproject.eu/ns/extraction/mention/position-start"
argument_list|)
decl_stmt|;
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_MENTIONED_ENTITY_POSITION_END
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://iksproject.eu/ns/extraction/mention/position-end"
argument_list|)
decl_stmt|;
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_MENTIONED_ENTITY_CONTEXT
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://iksproject.eu/ns/extraction/mention/context"
argument_list|)
decl_stmt|;
annotation|@
name|Deprecated
specifier|public
specifier|static
specifier|final
name|UriRef
name|FISE_MENTIONED_ENTITY_OCCURENCE
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://iksproject.eu/ns/extraction/mention/occurence"
argument_list|)
decl_stmt|;
block|}
end_interface

end_unit

