begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_expr_stmt
unit|#
name|set
argument_list|(
name|$symbol_pound
operator|=
literal|'#'
argument_list|)
expr|#
name|set
argument_list|(
name|$symbol_dollar
operator|=
literal|'$'
argument_list|)
expr|#
name|set
argument_list|(
name|$symbol_escape
operator|=
literal|'\' )
package|package
name|$
block|{
package|package
block|}
end_expr_stmt

begin_empty_stmt
empty_stmt|;
end_empty_stmt

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
comment|/**  * Ideally this should be a dereferenceable ontology on the web. Given such   * an ontology a class of constant (similar to this) can be generated with  * the org.apache.clerezza:maven-ontologies-plugin  */
end_comment

begin_class
specifier|public
class|class
name|Ontology
block|{
comment|/**      * Resources of this type can be dereferenced and will return a description      * of the resource of which the IRI is specified in the "iri" query parameter.      *       */
specifier|public
specifier|static
specifier|final
name|UriRef
name|ResourceResolver
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://example.org/service-description${symbol_pound}ResourceResolver"
argument_list|)
decl_stmt|;
comment|/**      * Point to the resource resolved by the subject.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|describes
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://example.org/service-description${symbol_pound}describes"
argument_list|)
decl_stmt|;
comment|/**      * The description of a Request in the log.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|LoggedRequest
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://example.org/service-description${symbol_pound}LoggedRequest"
argument_list|)
decl_stmt|;
comment|/**      * The User Agent performing the requested described by the subject.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|userAgent
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://example.org/service-description${symbol_pound}userAgent"
argument_list|)
decl_stmt|;
comment|/**      * The Entity of which a description was requested in the request      * described by the subject.      */
specifier|public
specifier|static
specifier|final
name|UriRef
name|requestedEntity
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://example.org/service-description${symbol_pound}requestedEntity"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

