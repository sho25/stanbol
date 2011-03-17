begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reengineer
operator|.
name|db
operator|.
name|ontology
package|;
end_package

begin_comment
comment|/* CVS $Id$ */
end_comment

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Vocabulary definitions from http://andriry.altervista.org/tesiSpecialistica/schema.owl   * @author Auto-generated by schemagen on 07 Jul 2010 17:13   */
end_comment

begin_class
specifier|public
class|class
name|Schema
block|{
comment|/**<p>The RDF model that holds the vocabulary terms</p> */
specifier|private
specifier|static
name|Model
name|m_model
init|=
name|ModelFactory
operator|.
name|createDefaultModel
argument_list|()
decl_stmt|;
comment|/**<p>The namespace of the vocabulary as a string</p> */
specifier|public
specifier|static
specifier|final
name|String
name|NS
init|=
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#"
decl_stmt|;
comment|/**<p>The namespace of the vocabulary as a string</p>      *  @see #NS */
specifier|public
specifier|static
name|String
name|getURI
parameter_list|()
block|{
return|return
name|NS
return|;
block|}
comment|/**<p>The namespace of the vocabulary as a resource</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|NAMESPACE
init|=
name|m_model
operator|.
name|createResource
argument_list|(
name|NS
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Annotation
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Annotation"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Any
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Any"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|AnyAttribute
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#AnyAttribute"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Appinfo
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Appinfo"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Attribute
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Attribute"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|AttributeGroup
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#AttributeGroup"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Choice
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Choice"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|ComplexContent
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#ComplexContent"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|ComplexType
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#ComplexType"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Documentation
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Documentation"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Element
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Element"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Enumeration
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Enumeration"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Extension
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Extension"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Field
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Field"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|FractionDigits
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#FractionDigits"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Group
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Group"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|HasFacet
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#HasFacet"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|HasFacetAndPropertyhasProperty
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#HasFacetAndPropertyhasProperty"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Import
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Import"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Key
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Key"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|List
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#List"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|MaxEclusive
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#MaxEclusive"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|MinEclusive
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#MinEclusive"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|MaxInclusive
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#MaxInclusive"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|MinInclusive
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#MinInclusive"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|MinLength
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#MinLength"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Notation
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Notation"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Pattern
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Pattern"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|All
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#All"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Restriction
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Restriction"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Schema
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Schema"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Selector
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Selector"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Sequence
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Sequence"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|SimpleType
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#SimpleType"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Union
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Union"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|WhiteSpace
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#WhiteSpace"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|ScopeAbsent
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Absent"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|ScopeLocal
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Local"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|ScopeGlobal
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#Global"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|VC_NONE
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#VC_NONE"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|VC_DEFAULT
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#VC_DEFAULT"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|VC_FIXED
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#VC_FIXED"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|DERIVATION_EXTENSION
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#DERIVATION_EXTENSION"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|DERIVATION_RESTRICTION
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#DERIVATION_RESTRICTION"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|DERIVATION_NONE
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#DERIVATION_NONE"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|PROHIBITED_EXTENSION
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#PROHIBITED_EXTENSION"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|PROHIBITED_RESTRICTION
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#PROHIBITED_RESTRICTION"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|PROHIBITED_NONE
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#PROHIBITED_NONE"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|COLLAPSE
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#COLLAPSE"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|PRESERVE
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#PRESERVE"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|REPLACE
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#REPLACE"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|abstractProperty
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#abstract"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|name
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#name"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|type
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#type"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasScope
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasScope"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasConstraintType
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasConstraintType"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasFinal
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasFinal"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|constraint
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#constraint"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|maxOccurs
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#maxOccurs"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|minOccurs
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#minOccurs"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasProhibitedSubstitutions
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasProhibitedSubstitutions"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|required
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#required"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasParticle
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasParticle"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasCompositor
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasCompositor"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|value
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#value"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasEnumeration
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasEnumeration"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasWhitespace
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasWhitespace"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|base
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#base"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasAnnotation
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasAnnotation"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasPattern
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasPattern"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|hasAttributeUse
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://andriry.altervista.org/tesiSpecialistica/schema.xsd#hasAttributeUse"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

