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
comment|// Generated on: 2011.04.14 at 03:06:41 PM EEST
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
comment|/**  *<p>Java class for anonymous complex type.  *   *<p>The following schema fragment specifies the expected content contained within this class.  *   *<pre>  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element name="Query" type="{mapping.model.servicesapi.cmsadapter.stanbol.apache.org}QueryType"/>  *&lt;element ref="{mapping.model.servicesapi.cmsadapter.stanbol.apache.org}PropertyBridge" maxOccurs="unbounded" minOccurs="0"/>  *&lt;/sequence>  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *</pre>  *   *   */
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
literal|"query"
block|,
literal|"propertyBridge"
block|}
argument_list|)
annotation|@
name|XmlRootElement
argument_list|(
name|name
operator|=
literal|"InstanceBridge"
argument_list|)
specifier|public
class|class
name|InstanceBridge
block|{
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"Query"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|String
name|query
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"PropertyBridge"
argument_list|)
specifier|protected
name|List
argument_list|<
name|PropertyBridge
argument_list|>
name|propertyBridge
decl_stmt|;
comment|/**      * Gets the value of the query property.      *       * @return      *     possible object is      *     {@link String }      *           */
specifier|public
name|String
name|getQuery
parameter_list|()
block|{
return|return
name|query
return|;
block|}
comment|/**      * Sets the value of the query property.      *       * @param value      *     allowed object is      *     {@link String }      *           */
specifier|public
name|void
name|setQuery
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|query
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the propertyBridge property.      *       *<p>      * This accessor method returns a reference to the live list,      * not a snapshot. Therefore any modification you make to the      * returned list will be present inside the JAXB object.      * This is why there is not a<CODE>set</CODE> method for the propertyBridge property.      *       *<p>      * For example, to add a new item, do as follows:      *<pre>      *    getPropertyBridge().add(newItem);      *</pre>      *       *       *<p>      * Objects of the following type(s) are allowed in the list      * {@link PropertyBridge }      *       *       */
specifier|public
name|List
argument_list|<
name|PropertyBridge
argument_list|>
name|getPropertyBridge
parameter_list|()
block|{
if|if
condition|(
name|propertyBridge
operator|==
literal|null
condition|)
block|{
name|propertyBridge
operator|=
operator|new
name|ArrayList
argument_list|<
name|PropertyBridge
argument_list|>
argument_list|()
expr_stmt|;
block|}
return|return
name|this
operator|.
name|propertyBridge
return|;
block|}
block|}
end_class

end_unit

