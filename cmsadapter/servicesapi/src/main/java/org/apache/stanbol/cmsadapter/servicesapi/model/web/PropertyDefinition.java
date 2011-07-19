begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
comment|// Generated on: 2011.05.17 at 10:53:39 AM EEST
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
name|math
operator|.
name|BigInteger
import|;
end_import

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
comment|/**  *<p>Java class for anonymous complex type.  *   *<p>The following schema fragment specifies the expected content contained within this class.  *   *<pre>  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}sourceObjectTypeRef" minOccurs="0"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}localname"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}namespace" minOccurs="0"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}uniqueRef"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}propertyType"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}cardinality" minOccurs="0"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}required" minOccurs="0"/>  *&lt;sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}valueConstraint" maxOccurs="unbounded" minOccurs="0"/>  *&lt;/sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}annotation" minOccurs="0"/>  *&lt;/sequence>  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *</pre>  *   *   */
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
literal|"sourceObjectTypeRef"
block|,
literal|"localname"
block|,
literal|"namespace"
block|,
literal|"uniqueRef"
block|,
literal|"propertyType"
block|,
literal|"cardinality"
block|,
literal|"required"
block|,
literal|"valueConstraint"
block|,
literal|"annotation"
block|}
argument_list|)
annotation|@
name|XmlRootElement
argument_list|(
name|name
operator|=
literal|"propertyDefinition"
argument_list|)
specifier|public
class|class
name|PropertyDefinition
block|{
specifier|protected
name|String
name|sourceObjectTypeRef
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|String
name|localname
decl_stmt|;
specifier|protected
name|String
name|namespace
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
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
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|PropType
name|propertyType
decl_stmt|;
specifier|protected
name|BigInteger
name|cardinality
decl_stmt|;
specifier|protected
name|Boolean
name|required
decl_stmt|;
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|valueConstraint
decl_stmt|;
specifier|protected
name|AnnotationType
name|annotation
decl_stmt|;
comment|/**      * Gets the value of the sourceObjectTypeRef property.      *       * @return      *     possible object is      *     {@link String }      *           */
specifier|public
name|String
name|getSourceObjectTypeRef
parameter_list|()
block|{
return|return
name|sourceObjectTypeRef
return|;
block|}
comment|/**      * Sets the value of the sourceObjectTypeRef property.      *       * @param value      *     allowed object is      *     {@link String }      *           */
specifier|public
name|void
name|setSourceObjectTypeRef
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|sourceObjectTypeRef
operator|=
name|value
expr_stmt|;
block|}
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
comment|/**      * Gets the value of the propertyType property.      *       * @return      *     possible object is      *     {@link PropType }      *           */
specifier|public
name|PropType
name|getPropertyType
parameter_list|()
block|{
return|return
name|propertyType
return|;
block|}
comment|/**      * Sets the value of the propertyType property.      *       * @param value      *     allowed object is      *     {@link PropType }      *           */
specifier|public
name|void
name|setPropertyType
parameter_list|(
name|PropType
name|value
parameter_list|)
block|{
name|this
operator|.
name|propertyType
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the cardinality property.      *       * @return      *     possible object is      *     {@link BigInteger }      *           */
specifier|public
name|BigInteger
name|getCardinality
parameter_list|()
block|{
return|return
name|cardinality
return|;
block|}
comment|/**      * Sets the value of the cardinality property.      *       * @param value      *     allowed object is      *     {@link BigInteger }      *           */
specifier|public
name|void
name|setCardinality
parameter_list|(
name|BigInteger
name|value
parameter_list|)
block|{
name|this
operator|.
name|cardinality
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the required property.      *       * @return      *     possible object is      *     {@link Boolean }      *           */
specifier|public
name|Boolean
name|isRequired
parameter_list|()
block|{
return|return
name|required
return|;
block|}
comment|/**      * Sets the value of the required property.      *       * @param value      *     allowed object is      *     {@link Boolean }      *           */
specifier|public
name|void
name|setRequired
parameter_list|(
name|Boolean
name|value
parameter_list|)
block|{
name|this
operator|.
name|required
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the valueConstraint property.      *       *<p>      * This accessor method returns a reference to the live list,      * not a snapshot. Therefore any modification you make to the      * returned list will be present inside the JAXB object.      * This is why there is not a<CODE>set</CODE> method for the valueConstraint property.      *       *<p>      * For example, to add a new item, do as follows:      *<pre>      *    getValueConstraint().add(newItem);      *</pre>      *       *       *<p>      * Objects of the following type(s) are allowed in the list      * {@link String }      *       *       */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getValueConstraint
parameter_list|()
block|{
if|if
condition|(
name|valueConstraint
operator|==
literal|null
condition|)
block|{
name|valueConstraint
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
name|valueConstraint
return|;
block|}
comment|/**      * Gets the value of the annotation property.      *       * @return      *     possible object is      *     {@link AnnotationType }      *           */
specifier|public
name|AnnotationType
name|getAnnotation
parameter_list|()
block|{
return|return
name|annotation
return|;
block|}
comment|/**      * Sets the value of the annotation property.      *       * @param value      *     allowed object is      *     {@link AnnotationType }      *           */
specifier|public
name|void
name|setAnnotation
parameter_list|(
name|AnnotationType
name|value
parameter_list|)
block|{
name|this
operator|.
name|annotation
operator|=
name|value
expr_stmt|;
block|}
block|}
end_class

end_unit

