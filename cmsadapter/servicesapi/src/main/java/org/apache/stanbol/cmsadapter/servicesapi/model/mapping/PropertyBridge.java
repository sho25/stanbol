begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|//
end_comment

begin_comment
comment|// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3-
end_comment

begin_comment
comment|// See<a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
end_comment

begin_comment
comment|// Any modifications to this file will be lost upon recompilation of the source schema.
end_comment

begin_comment
comment|// Generated on: 2011.05.09 at 02:52:53 PM EEST
end_comment

begin_comment
comment|//
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|annotation
operator|.
name|XmlAccessType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|annotation
operator|.
name|XmlAccessorType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|annotation
operator|.
name|XmlElement
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|annotation
operator|.
name|XmlRootElement
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|annotation
operator|.
name|XmlType
import|;
end_import

begin_comment
comment|/**  *<p>Java class for anonymous complex type.  *   *<p>The following schema fragment specifies the expected content contained within this class.  *   *<pre>  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element name="PredicateName" type="{mapping.model.servicesapi.cmsadapter.stanbol.apache.org}QueryType"/>  *&lt;element name="SubjectQuery" type="{mapping.model.servicesapi.cmsadapter.stanbol.apache.org}QueryType" minOccurs="0"/>  *&lt;element ref="{mapping.model.servicesapi.cmsadapter.stanbol.apache.org}PropertyAnnotation" minOccurs="0"/>  *&lt;/sequence>  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *</pre>  *   *   */
end_comment

begin_class
annotation|@
name|XmlAccessorType
argument_list|(
name|XmlAccessType
operator|.
name|FIELD
argument_list|)
annotation|@
name|XmlType
argument_list|(
name|name
operator|=
literal|""
argument_list|,
name|propOrder
operator|=
block|{
literal|"predicateName"
block|,
literal|"subjectQuery"
block|,
literal|"propertyAnnotation"
block|}
argument_list|)
annotation|@
name|XmlRootElement
argument_list|(
name|name
operator|=
literal|"PropertyBridge"
argument_list|)
specifier|public
class|class
name|PropertyBridge
block|{
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"PredicateName"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|String
name|predicateName
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"SubjectQuery"
argument_list|)
specifier|protected
name|String
name|subjectQuery
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"PropertyAnnotation"
argument_list|)
specifier|protected
name|PropertyAnnotation
name|propertyAnnotation
decl_stmt|;
comment|/**      * Gets the value of the predicateName property.      *       * @return      *     possible object is      *     {@link String }      *           */
specifier|public
name|String
name|getPredicateName
parameter_list|()
block|{
return|return
name|predicateName
return|;
block|}
comment|/**      * Sets the value of the predicateName property.      *       * @param value      *     allowed object is      *     {@link String }      *           */
specifier|public
name|void
name|setPredicateName
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|predicateName
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the subjectQuery property.      *       * @return      *     possible object is      *     {@link String }      *           */
specifier|public
name|String
name|getSubjectQuery
parameter_list|()
block|{
return|return
name|subjectQuery
return|;
block|}
comment|/**      * Sets the value of the subjectQuery property.      *       * @param value      *     allowed object is      *     {@link String }      *           */
specifier|public
name|void
name|setSubjectQuery
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|subjectQuery
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the propertyAnnotation property.      *       * @return      *     possible object is      *     {@link PropertyAnnotation }      *           */
specifier|public
name|PropertyAnnotation
name|getPropertyAnnotation
parameter_list|()
block|{
return|return
name|propertyAnnotation
return|;
block|}
comment|/**      * Sets the value of the propertyAnnotation property.      *       * @param value      *     allowed object is      *     {@link PropertyAnnotation }      *           */
specifier|public
name|void
name|setPropertyAnnotation
parameter_list|(
name|PropertyAnnotation
name|value
parameter_list|)
block|{
name|this
operator|.
name|propertyAnnotation
operator|=
name|value
expr_stmt|;
block|}
block|}
end_class

end_unit

