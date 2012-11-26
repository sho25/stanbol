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
name|nif
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

begin_import
import|import
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
name|utils
operator|.
name|NIFHelper
import|;
end_import

begin_enum
specifier|public
enum|enum
name|StringOntology
block|{
comment|/**      * The URI of this String was created with the URI Recipe Context-Hash, see      * http://aksw.org/Projects/NIF#context-hash-nif-uri-recipe.      *       * @see NIFHelper#getNifHashURI(UriRef, int, int, String)      */
name|ContextHashBasedString
block|,
comment|/**      * A document is a string that can be considered a closed unit content-wise. In NIF a document is given an      * URL that references the String of the document. Furthermore a document can have several sources. It can      * be a string, a HTML document, a PDF document, text file or any other arbitrary string. The uri denoting      * the actual document should be able to reproduce that document, i.e. either the string is directly      * included via the property sourceString or an url can be given that contains the string via the property      * sourceUrl. Depending on the feedback, this might also become the Graph URI or a subclass of      * owl:Ontology      */
name|Document
block|,
comment|/**      * The URI of this String was created with the URI Recipe Context-Hash, see      * http://aksw.org/Projects/NIF#offset-nif-uri-recipe      */
name|OffsetBasedString
block|,
comment|/**      * temporariliy added this declaration.      */
name|menas
block|,
comment|/**      * The source url, which makes up the document. Annotators should ensure that the source text can be      * downloaded from the url and stays stable otherwise :sourceString should be used.      */
name|sourceUri
block|,
name|subString
block|,
name|subStringTrans
block|,
name|superString
block|,
name|superStringTrans
block|,
comment|/**      * The string, which the uri is representing as an RDF Literal. This property is mandatory for every      * String.      */
name|anchorOf
block|,
comment|/**      * The index of the first character of the String relative to the document. This should be identical with      * the first number used in the offset URI recipe.      */
name|beginIndex
block|,
comment|/**      * The index of last character of the String relative to the document. This should be identical with the      * second number used in the offset URI recipe.      */
name|endIndex
block|,
comment|/**      * The left context of the string. The length of the context is undefined. To fix the length subProperties      * can be used: e.g. :leftContext20 rdfs:subPropertyOf :leftContext gives the 20 characters to the left of      * the string. Using this property can increase the size of the produced RDF immensely.      */
name|leftContext
block|,
comment|/**      * The right context of the string. The length of the context is undefined. To fix the length      * subProperties can be used: e.g. :rightContext20 rdfs:subPropertyOf :rightContext gives the 20      * characters to the right of the string. Using this property can increase the size of the produced RDF      * immensely.      */
name|rightContext
block|,
comment|/**      * The source string, which makes up the document. Used to reproduce the original text. Takes priority      * over :sourceUrl . Not to be confused with :anchorOf      */
name|sourceString
block|;
specifier|public
specifier|final
specifier|static
name|String
name|NAMESPACE
init|=
literal|"http://nlp2rdf.lod2.eu/schema/string/"
decl_stmt|;
name|UriRef
name|uri
decl_stmt|;
specifier|private
name|StringOntology
parameter_list|()
block|{
name|uri
operator|=
operator|new
name|UriRef
argument_list|(
name|NAMESPACE
operator|+
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getLocalName
parameter_list|()
block|{
return|return
name|name
argument_list|()
return|;
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

