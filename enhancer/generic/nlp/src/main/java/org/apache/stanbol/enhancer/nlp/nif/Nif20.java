begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

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
name|pos
operator|.
name|LexicalCategory
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
name|pos
operator|.
name|Pos
import|;
end_import

begin_enum
specifier|public
enum|enum
name|Nif20
block|{
comment|/** 	 * A URI Scheme for NIF which is able to refer to a single, consecutive  	 * string in a context. Note that any scheme subclassing this class,  	 * requires the existence of beginIndex, endIndex and referenceContext . 	 *<p> 	 * This is an abstract class and should not be serialized.  	 */
name|CString
block|,
comment|/** 	 * An abitrary URI (e.g. a URN) for an arbitrary string of the context.  	 * This is roughly the same as TextAnnotations are currently implemented in Stanbol. 	 */
name|CStringInst
block|,
comment|/** 	 * The string that serves as a context for its substrings. The Unicode String  	 * given in the nif:isString property must be used to calculate the begin and  	 * endIndex for all nif:Strings that have a nif:referenceContext property to  	 * this URI. For further information, see  	 * http://svn.aksw.org/papers/2013/ISWC_NIF/public.pdf  	 */
name|Context
block|,
comment|/** 	 * A collection of contexts used to create an unordered set of context via  	 * the nif:hasContext property. This can be compared to a document collection,  	 * but here it is a collection of nif:Context and therefore a collection of  	 * annotated strings, not documents.  	 */
name|ContextCollection
block|,
comment|/** 	 * see<a href="http://jens-lehmann.org/files/2012/ekaw_nif.pdf"> 	 * Linked-Data Aware URI Schemes for Referencing Text Fragments</a>  	 * by Sebastian Hellmann, Jens Lehmann und Sören Auer in EKAW 2012 for more 	 * information. 	 */
name|ContextHashBasedString
block|,
comment|/** 	 * see<a href="http://jens-lehmann.org/files/2012/ekaw_nif.pdf"> 	 * Linked-Data Aware URI Schemes for Referencing Text Fragments</a>  	 * by Sebastian Hellmann, Jens Lehmann und Sören Auer in EKAW 2012 for more 	 * information. 	 */
name|OffsetBasedString
block|,
comment|/** 	 * A Paragraph 	 */
name|Paragraph
block|,
comment|/** 	 * A nif:Phrase can be a nif:String, that is a chunk of several words or a  	 * word itself (e.g. a NounPhrase as a Named Entity). The term is underspecified  	 * and can be compatible with many defintitions of phrase. Please subClass  	 * it to specify the meaning (e.g. for Chunking or Phrase Structure Grammar).  	 * Example: ((My dog)(also)(likes)(eating (sausage)))  	 */
name|Phrase
block|,
comment|/** 	 * URIs of this class have to conform with the syntax of<a  	 * href="http://tools.ietf.org/html/rfc5147">RFC 5147</a> in a way that the  	 * end on a valid identifier, if you remove the prefix. Note that unlike  	 * RFC 5147 NIF does not requrire '#' URIs. So valid URIs are  	 * http://example.org#char=0,28 , http://example.org/whatever/char=0,28 ,  	 * http://example.org/nif?char=0,28 	 */
name|RFC5147String
block|,
comment|/** 	 * A Sentence  	 */
name|Sentence
block|,
comment|/** 	 * Individuals of this class are a string, i.e. Unicode characters, who  	 * have been given a URI and are used in the subject of an RDF statement. 	 *<p> 	 * This class is abstract and should not be serialized. 	 *<p> 	 * NIF-Stanbol (nif-stanbol.ttl): subclassOf nifs:Annotation because it  	 * "annotates" strings for example with begin and end index. The class is  	 * similar to fise:TextAnnotation 	 */
name|String
block|,
comment|/** 	 * A structure is a more or less arbitrary label for a partitioning of a  	 * string. We do not follow a strict approach for what a word, phrase,  	 * sentence, title, paragraph is. These labels enable the definition  	 * processes for tool chains, e.g. tool analyses nif:Paragraph and  	 * calculates term frequency. 	 *<p> 	 * This is an abstract class and should not be serialized.  	 */
name|Structure
block|,
comment|/** 	 * A title within a text. 	 */
name|Title
block|,
comment|/** 	 * A URI Scheme for NIF, subclasses need to define guidelines on the URI  	 * Scheme as well as the text it refers to. This class is just to keep some  	 * order, and should not be serialized. 	 *<p> 	 * This is an abstract class and should not be serialized.  	 */
name|URIScheme
block|,
comment|/** 	 *  The Word class represents strings that are tokens or words. A string is  	 *  a Word, if it is a word. We don't nitpic about whether it is a a pronoun,  	 *  a name, a punctuation mark or an apostrophe or whether it is separated  	 *  by white space from another Word or something else. The string  	 *  'He enters the room.' for example has 5 words. Words are assigned by a  	 *  tokenizer NIF Implementation. Single word phrases might be tagged as  	 *  nif:Word and nif:Phrase. 	 *   	 *  Example 1: "The White House" are three Words separated by whitespace 	 *   	 *  Comment 1: We adopted the definition style from foaf:Person, see  	 *  here: http://xmlns.com/foaf/spec/#term_Person We are well aware that  	 *  the world out there is much more complicated, but we are ignorant about  	 *  it, for the following reasons: 	 *   	 *  Comment 2:<ol> 	 *<li> NIF has a client-server and the client has the ability to  	 *  dictate the tokenization to the server (i.e. the NIF Implementation) by  	 *  sending properly tokenized NIF annotated with nif:Word. All NIF  	 *  Implementations are supposed to honor and respect the current assignment  	 *  of the Word class. Thus the client should decide which NIF Implementation  	 *  should create the tokenization. Therefore this class is not descriptive,  	 *  but prescriptive. 	 *<li>The client may choose to send an existing tokenization to a NIF  	 *  Implementation, with the capability to change (for better or for worse)  	 *  the tokenization. 	 *</ol> 	 *   	 *  The class has not been named 'Token' as the NLP definition of 'token'  	 *  is descriptive (and not well-defined), while the assignment of what is  	 *  a Word and what not is prescriptive, e.g. "can't" could be described as  	 *  one, two or three tokens or defined as being one, two or three words.  	 *  For further reading, we refer the reader to: By all these lovely tokens...  	 *  Merging conflicting tokenizations by Christian Chiarcos, Julia Ritz, and  	 *  Manfred Stede. Language Resources and Evaluation 46(1):53-74 (2012) or  	 *  the short form: http://www.aclweb.org/anthology/W09-3005 	 *   	 *  There the task at hand is to merge two tokenization T_1 and T_2 which  	 *  is normally not the case in the NIF world as tokenization is prescribed,  	 *  i.e. given as a baseline (Note that this ideal state might not be  	 *  achieved by all implementations.) 	 */
name|Word
block|,
comment|//Object Properties
comment|/** 	 * see<a href="http://svn.aksw.org/papers/2012/PeoplesWeb/public_preprint.pdf> 	 * Towards Web-Scale Collaborative Knowledge Extraction</a> ‎ page 21 	 */
name|annotation
block|,
comment|/** 	 * This property should be used to express that one Context is contained in  	 * another Context, e.g. several sentences of a document are modelled  	 * indivudally and refer to the broader context of the whole document. 	 */
name|broaderContext
block|,
comment|/** 	 * A dependency relation pointing from gov to dep. 	 */
name|dependency
block|,
comment|/** 	 * Links a nif:ContextCollection to its contexts.  	 */
name|hasContext
block|,
comment|/** 	 * This property links sentences to their first word. 	 */
name|firstWord
block|,
comment|/** 	 * This property links sentences to their last word. 	 */
name|lastWord
block|,
comment|/** 	 * This property links sentences to their words. 	 */
name|word
block|,
comment|/** 	 * This object property models a relation between two nif:Strings.  	 * The name "inter" is kept generic and can be used to express any kind of  	 * relation in between (inter) two nif:Strings. Extensions can create  	 * rdfs:subPropertyOf for "head", "dependent", nif:substring and  	 * nif:nextWord.  	 */
name|inter
block|,
comment|/** 	 * Defines the language of a substring of the context.  	 * If the language for the nif:Context should be specified,  	 * nif:predominantLanguage must be used.  	 */
name|lang
block|,
comment|/** 	 * The inverse of nif:broaderContex 	 */
name|narrowerContext
block|,
comment|/** 	 * This property can be used to make resources of  	 * nif:Sentence traversable, it can not be assumed that no gaps  	 * or whitespaces between sentences or words exist, i.e. string adjacency  	 * is not mandatory. The transitivity axioms are included in nif-core-inf.ttl  	 * and need to be included separately to keep a low reasoning profile.  	 * They are modeled after skos:broader and skos:broaderTransitive 	 */
name|nextSentence
block|,
comment|/** 	 * transitive version of {@link #nextSentence} 	 */
name|nextSentenceTrans
block|,
comment|/** 	 * This property can be used to make resources of  	 * nif:Word traversable, it can not be assumed that no gaps  	 * or whitespaces between sentences or words exist, i.e. string adjacency  	 * is not mandatory. The transitivity axioms are included in nif-core-inf.ttl  	 * and need to be included separately to keep a low reasoning profile.  	 * They are modeled after skos:broader and skos:broaderTransitive 	 */
name|nextWord
block|,
comment|/** 	 * transitive version of {@link #nextWord} 	 */
name|nextWordTrans
block|,
comment|/** 	 * This property links a string to a URI from one of the OLiA Annotation model,  	 *  - members of the {@link Pos} enumeration  	 */
name|oliaLink
block|,
comment|/** 	 * This property is used to link to a<a href="http://marl.gi2mo.org/?page_id=1#overview">marl:Opinion</a>.  	 * We have not investigated marl, so it might be replaced. 	 *<p> 	 * InverseOf marl:extractedFrom 	 */
name|opinion
block|,
comment|/** 	 * Defines the predominant language of the text. If this annotation is given  	 * on a nif:Context, all NIF tools have to treat the text to be in this  	 * language unless specified differently for a subpart. To change the  	 * language for a smaller substring nif:lang must be used. 	 *<p> 	 * This property requires a uri as an argument. We expect this to be a URI  	 * from the lexvo.org namespace, e.g. http://lexvo.org/id/iso639-3/eng using  	 * ISO639-3 	 *<p> 	 * Examples: 	 *<p> 	 * "The dealer says: "Rien ne va plus!" " 	 *<p> 	 * has nif:predomintLanguage http://lexvo.org/id/iso639-3/eng and  	 * nif:lang http://www.lexvo.org/page/iso639-3/fra 	 *<p> 	 * see also: http://www.w3.org/TR/its20/#selection-local 	 *<p> 	 * Tests for RDFUnit (not written yet): 	 *<p> 	 * - write a test for RDFUnit, so people do not use  	 * http://www.lexvo.org/page/iso639-3/eng  	 */
name|predLang
block|,
comment|/** 	 * This property can be used to make resources of  	 * nif:Sentence, it can not be assumed that no gaps  	 * or whitespaces between sentences or words exist, i.e. string adjacency  	 * is not mandatory. The transitivity axioms are included in nif-core-inf.ttl  	 * and need to be included separately to keep a low reasoning profile.  	 * They are modeled after skos:broader and skos:broaderTransitive 	 */
name|previousSentence
block|,
comment|/** 	 * Transitive version of {@link #previousSentence} 	 */
name|previousSentenceTrans
block|,
comment|/** 	 * This property can be used to make resources of  	 * nif:Word, it can not be assumed that no gaps  	 * or whitespaces between sentences or words exist, i.e. string adjacency  	 * is not mandatory. The transitivity axioms are included in nif-core-inf.ttl  	 * and need to be included separately to keep a low reasoning profile.  	 * They are modeled after skos:broader and skos:broaderTransitive 	 */
name|previousWord
block|,
comment|/** 	 * Transitive version of {@link #previousWord} 	 */
name|previousWordTrans
block|,
comment|/** 	 * Links to the URI describing the provenance 	 */
name|oliaProv
block|,
comment|/** 	 * Links a URI of a string to its reference context of type nif:Context.  	 * The reference context determines the calculation of begin and end index 	 *<p> 	 * Each String that is not an instance of nif:Context MUST have exactly one  	 * reference context. 	 *<p> 	 * Inferences (nif-core-inf.ttl): 	 *<p> 	 * Instances of nif:Context do have itself as reference context, this is  	 * inferred automatically, MAY be materialized, as well. 	 *<p> 	 * OWL validation (nif-core-val.ttl): 	 *<p> 	 * This property is functional. 	 */
name|referenceContext
block|,
comment|/** 	 * This property links words to their sentence. 	 */
name|sentence
block|,
comment|/** 	 * The URL the context was extracted from, e.g. the blog or news article url.  	 * Doesn't matter whether it is HTML or XML or plain text. rdfs:range is  	 * foaf:Document. Subproperty of prov:hadPrimarySource. In case the string  	 * comes from another NIF String and gives the exact provenance, please use  	 * nif:wasConvertedFrom or a subProperty thereof. 	 */
name|sourceUrl
block|,
comment|/** 	 * This property together with nif:subString, nif:superString, and their  	 * transitive extension can be used to express that one string is contained  	 * in another one. Examples: "a" nif:subString "apple" , "apple"  	 * nif:subString "apple". The transitivity axioms are included in  	 * nif-core-inf.ttl and need to be included separately to keep a low  	 * reasoning profile. They are modeled after skos:broader and  	 * skos:broaderTransitive 	 */
name|subString
block|,
comment|/** 	 * Inverse of {@link #subString} 	 */
name|superString
block|,
comment|/** 	 * Transitive version of {@link #dependency} 	 */
name|dependencyTrans
block|,
comment|/** 	 * Transitive version of {@link #subString} 	 */
name|subStringTrans
block|,
comment|/** 	 * Transitive version of {@link #superString} 	 */
name|superStringTrans
block|,
comment|/** 	 * This property should be used, when mapping one nif:String or nif:Context  	 * to another and is often confused with nif:sourceUrl. 	 *<p> 	 * While nif:sourceUrl is built on PROV-O and is used to link the nif:Context  	 * to the document URL for provenance information, nif:convertedFrom is more  	 * precise and pinpoints exact locations where a certain NIF String  	 * "wasConvertedFrom". 	 *<p> 	 * nif:wasConvertedFrom is therefore used to provide *exact* provenance  	 * during a conversion process, e.g. when removing tags from XHTML and then  	 * linking XPath URIs to NIF index based URIs (e.g. RFC 5147 with char=x,y).  	 * An example of the usage of this property can be found here:  	 * http://www.w3.org/TR/its20/#conversion-to-nif 	 *<p> 	 * Example 	 *<p> 	 * # "Dublin" 	 *<p> 	 *&lt;http://example.com/myitsservice?informat=html&intype=url&input=http://example.com/doc.html&char=11,17&gt; 	 *<p> 	 * nif:wasConvertedFrom 	 *<p> 	 *&lt;http://example.com/myitsservice?informat=html&intype=url&input=http://example.com/doc.html&xpath=/html/body[1]/h2[1]/span[1]/text()[1]&gt;. 	 */
name|wasConvertedFrom
block|,
comment|//Datatype properties
comment|/** 	 * For each string you can include a snippet (e.g. 10-40 characters of text),  	 * that occurs immediately after the subject string. 	 */
name|after
block|,
comment|/** 	 * The string, which the URI is representing as an RDF Literal. Some use  	 * cases require this property, as it is necessary for certain sparql queries.  	 */
name|anchorOf
block|,
comment|/** 	 * For each string you can include a snippet (e.g. 10-40 characters of text),  	 * that occurs immediately before the subject string. 	 */
name|before
block|,
comment|/** 	 * The begin index of a character range as defined in  	 * http://tools.ietf.org/html/rfc5147#section-2.2.1 and  	 * http://tools.ietf.org/html/rfc5147#section-2.2.2, measured as the gap  	 * between two characters, starting to count from 0 (the position before  	 * the first character of a text). 	 *<p> 	 * Example: Index "2" is the postion between "Mr" and "." in "Mr. Sandman". 	 *<p> 	 * Note: RFC 5147 is re-used for the definition of character ranges. RFC 5147  	 * is assuming a text/plain MIME type. NIF builds upon Unicode and is content  	 * agnostic. 	 *<p> 	 * Requirement (1): This property has the same value the "Character position"  	 * of RFC 5147 and it MUST therefore be castable to xsd:nonNegativeInteger,  	 * i.e. it MUST not have negative values. 	 *<p> 	 * Requirement (2): The index of the subject string MUST be calculated  	 * relative to the nif:referenceContext of the subject. If available, this  	 * is the rdf:Literal of the nif:isString property. 	 */
name|beginIndex
block|,
comment|/** 	 * The confidence is relative to the tool and can be between 0.0 and 1.0,  	 * it is for nif:oliaLink and therefore also for nif:oliaCategory. 	 */
name|oliaConf
block|,
comment|/** 	 * The end index of a character range as defined in  	 * http://tools.ietf.org/html/rfc5147#section-2.2.1 and  	 * http://tools.ietf.org/html/rfc5147#section-2.2.2, measured as the gap  	 * between two characters, starting to count from 0 (the position before  	 * the first character of a text). 	 *<p> 	 * Example: Index "2" is the postion between "Mr" and "." in "Mr. Sandman". 	 *<p> 	 * Note: RFC 5147 is re-used for the definition of character ranges. RFC 5147  	 * is assuming a text/plain MIME type. NIF builds upon Unicode and is content  	 * agnostic. 	 *<p> 	 * Requirement (1): This property has the same value the "Character position"  	 * of RFC 5147 and it must therefore be an xsd:nonNegativeInteger . 	 *<p> 	 * Requirement (2): The index of the subject string MUST be calculated  	 * relative to the nif:referenceContext of the subject. If available, this  	 * is the rdf:Literal of the nif:isString property. 	 */
name|endIndex
block|,
comment|/** 	 * The first few chars of the nif:anchorOf. Typically used if the nif:anchorOf 	 * is to long for inclusion as RDF literal. 	 */
name|head
block|,
comment|/** 	 * The reference text as rdf:Literal for this nif:Context resource. 	 * NIF requires that the reference text (i.e. the context) is always  	 * included in the RDF as an rdf:Literal. 	 *<p> 	 * Note, that the isString property is *the* place to keep the string itself  	 * in RDF. 	 *<p> 	 * All other nif:Strings and nif:URISchemes relate to the text of this  	 * property to calculate character position and indices. 	 */
name|isString
block|,
comment|/** 	 * The lemma(s) of the nif:String. 	 */
name|lemma
block|,
comment|/** 	 * see<a href=http://svn.aksw.org/papers/2012/PeoplesWeb/public_preprint.pdf"> 	 * Towards Web-Scale Collaborative Knowledge Extraction</a>‎ page 21 . 	 */
name|literalAnnotation
block|,
comment|/** 	 * To include the pos tag as it comes out of the NLP tool as RDF Literal.  	 * This property is discouraged to use alone, please use oliaLink and  	 * oliaCategory. We included it, because some people might still want it  	 * and will even create their own property, if the string variant is missing   	 */
name|posTag
block|,
comment|/** 	 * Between -1 negative and 1 positive 	 */
name|sentimentValue
block|,
comment|/** 	 * The stem(s) of the nif:String. 	 */
name|stem
block|,
comment|//Annotation properties
comment|/** 	 * A simple annotation for machine learning purposes. The object can be  	 * anything, e.g. the literal "A. PRESS: Reportage" from Brown or any URI.  	 */
name|category
block|,
comment|/** 	 * see<a href=http://svn.aksw.org/papers/2012/PeoplesWeb/public_preprint.pdf"> 	 * Towards Web-Scale Collaborative Knowledge Extraction</a>‎ page 12 . 	 */
name|classAnnotation
block|,
comment|/** 	 * This property marks the most specific class from itsrdf:taClassRef.  	 * The rule is: from the set S of itsrdf:taClassRef attached to this resource  	 * taMscRef points to the one that does not have any subclasses in the set  	 * S except itself. So if taClassRef is owl:Thing, dbo:Agent, dbo:Person,  	 * dbp:Actor taMsClassRef is dbo:Actor  	 */
name|taMsClassRef
block|,
comment|/** 	 * This property links a string URI to classes of the OLiA Reference model.  	 * It provides a direct link for querying, thus it is a redundant optimization. 	 *<p> 	 * Values are expected to be member of {@link Pos} 	 */
name|oliaCategory
block|, 	;
specifier|public
specifier|final
specifier|static
name|String
name|NAMESPACE
init|=
literal|"http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#"
decl_stmt|;
name|UriRef
name|uri
decl_stmt|;
specifier|private
name|Nif20
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

