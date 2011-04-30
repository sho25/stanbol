begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|//
end_comment

begin_comment
comment|// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs
end_comment

begin_comment
comment|// See<a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
end_comment

begin_comment
comment|// Any modifications to this file will be lost upon recompilation of the source schema.
end_comment

begin_comment
comment|// Generated on: 2011.04.29 at 11:44:48 AM EEST
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
name|web
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
comment|/**  * defines a fixed and non-hierarchical set of  * 				properties (“schema”) that all objects of that type have  * 			  *   *<p>Java class for objectTypeDefinition element declaration.  *   *<p>The following schema fragment specifies the expected content contained within this class.  *   *<pre>  *&lt;element name="objectTypeDefinition">  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}localname"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}namespace" minOccurs="0"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}uniqueRef"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}parentRef" maxOccurs="unbounded" minOccurs="0"/>  *&lt;sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}propertyDefinition" maxOccurs="unbounded" minOccurs="0"/>  *&lt;/sequence>  *&lt;sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}childObjectDefinition" maxOccurs="unbounded" minOccurs="0"/>  *&lt;/sequence>  *&lt;/sequence>  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *&lt;/element>  *</pre>  *   *   */
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
literal|"localname"
block|,
literal|"namespace"
block|,
literal|"uniqueRef"
block|,
literal|"parentRef"
block|,
literal|"propertyDefinition"
block|,
literal|"childObjectDefinition"
block|}
argument_list|)
annotation|@
name|XmlRootElement
argument_list|(
name|name
operator|=
literal|"objectTypeDefinition"
argument_list|)
specifier|public
class|class
name|ObjectTypeDefinition
block|{
annotation|@
name|XmlElement
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|String
name|localname
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|)
specifier|protected
name|String
name|namespace
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|String
name|uniqueRef
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|parentRef
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|List
argument_list|<
name|PropertyDefinition
argument_list|>
name|propertyDefinition
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|namespace
operator|=
literal|"web.model.servicesapi.cmsadapter.stanbol.apache.org"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|List
argument_list|<
name|ChildObjectDefinition
argument_list|>
name|childObjectDefinition
decl_stmt|;
comment|/**      * Gets the value of the localname property.      *       * @return      *     possible object is      *     {@link String }      *           */
specifier|public
name|String
name|getLocalname
parameter_list|()
block|{
return|return
name|localname
return|;
block|}
comment|/**      * Sets the value of the localname property.      *       * @param value      *     allowed object is      *     {@link String }      *           */
specifier|public
name|void
name|setLocalname
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|localname
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the namespace property.      *       * @return      *     possible object is      *     {@link String }      *           */
specifier|public
name|String
name|getNamespace
parameter_list|()
block|{
return|return
name|namespace
return|;
block|}
comment|/**      * Sets the value of the namespace property.      *       * @param value      *     allowed object is      *     {@link String }      *           */
specifier|public
name|void
name|setNamespace
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|namespace
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the uniqueRef property.      *       * @return      *     possible object is      *     {@link String }      *           */
specifier|public
name|String
name|getUniqueRef
parameter_list|()
block|{
return|return
name|uniqueRef
return|;
block|}
comment|/**      * Sets the value of the uniqueRef property.      *       * @param value      *     allowed object is      *     {@link String }      *           */
specifier|public
name|void
name|setUniqueRef
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|uniqueRef
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the parentRef property.      *       *<p>      * This accessor method returns a reference to the live list,      * not a snapshot. Therefore any modification you make to the      * returned list will be present inside the JAXB object.      * This is why there is not a<CODE>set</CODE> method for the parentRef property.      *       *<p>      * For example, to add a new item, do as follows:      *<pre>      *    getParentRef().add(newItem);      *</pre>      *       *       *<p>      * Objects of the following type(s) are allowed in the list      * {@link String }      *       *       */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getParentRef
parameter_list|()
block|{
if|if
condition|(
name|parentRef
operator|==
literal|null
condition|)
block|{
name|parentRef
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
return|return
name|this
operator|.
name|parentRef
return|;
block|}
comment|/**      * Gets the value of the propertyDefinition property.      *       *<p>      * This accessor method returns a reference to the live list,      * not a snapshot. Therefore any modification you make to the      * returned list will be present inside the JAXB object.      * This is why there is not a<CODE>set</CODE> method for the propertyDefinition property.      *       *<p>      * For example, to add a new item, do as follows:      *<pre>      *    getPropertyDefinition().add(newItem);      *</pre>      *       *       *<p>      * Objects of the following type(s) are allowed in the list      * {@link PropertyDefinition }      *       *       */
specifier|public
name|List
argument_list|<
name|PropertyDefinition
argument_list|>
name|getPropertyDefinition
parameter_list|()
block|{
if|if
condition|(
name|propertyDefinition
operator|==
literal|null
condition|)
block|{
name|propertyDefinition
operator|=
operator|new
name|ArrayList
argument_list|<
name|PropertyDefinition
argument_list|>
argument_list|()
expr_stmt|;
block|}
return|return
name|this
operator|.
name|propertyDefinition
return|;
block|}
comment|/**      * Gets the value of the childObjectDefinition property.      *       *<p>      * This accessor method returns a reference to the live list,      * not a snapshot. Therefore any modification you make to the      * returned list will be present inside the JAXB object.      * This is why there is not a<CODE>set</CODE> method for the childObjectDefinition property.      *       *<p>      * For example, to add a new item, do as follows:      *<pre>      *    getChildObjectDefinition().add(newItem);      *</pre>      *       *       *<p>      * Objects of the following type(s) are allowed in the list      * {@link ChildObjectDefinition }      *       *       */
specifier|public
name|List
argument_list|<
name|ChildObjectDefinition
argument_list|>
name|getChildObjectDefinition
parameter_list|()
block|{
if|if
condition|(
name|childObjectDefinition
operator|==
literal|null
condition|)
block|{
name|childObjectDefinition
operator|=
operator|new
name|ArrayList
argument_list|<
name|ChildObjectDefinition
argument_list|>
argument_list|()
expr_stmt|;
block|}
return|return
name|this
operator|.
name|childObjectDefinition
return|;
block|}
block|}
end_class

end_unit

