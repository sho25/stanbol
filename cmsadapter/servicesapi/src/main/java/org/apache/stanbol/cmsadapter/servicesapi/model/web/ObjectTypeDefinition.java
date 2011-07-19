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
comment|/**  *<p>Java class for anonymous complex type.  *   *<p>The following schema fragment specifies the expected content contained within this class.  *   *<pre>  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}localname"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}namespace" minOccurs="0"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}uniqueRef"/>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}parentRef" maxOccurs="unbounded" minOccurs="0"/>  *&lt;sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}propertyDefinition" maxOccurs="unbounded" minOccurs="0"/>  *&lt;/sequence>  *&lt;sequence>  *&lt;element ref="{web.model.servicesapi.cmsadapter.stanbol.apache.org}objectTypeDefinition" maxOccurs="unbounded" minOccurs="0"/>  *&lt;/sequence>  *&lt;/sequence>  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *</pre>  *   *   */
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
literal|"objectTypeDefinition"
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
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|parentRef
decl_stmt|;
specifier|protected
name|List
argument_list|<
name|PropertyDefinition
argument_list|>
name|propertyDefinition
decl_stmt|;
specifier|protected
name|List
argument_list|<
name|ObjectTypeDefinition
argument_list|>
name|objectTypeDefinition
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
comment|/**      * Gets the value of the objectTypeDefinition property.      *       *<p>      * This accessor method returns a reference to the live list,      * not a snapshot. Therefore any modification you make to the      * returned list will be present inside the JAXB object.      * This is why there is not a<CODE>set</CODE> method for the objectTypeDefinition property.      *       *<p>      * For example, to add a new item, do as follows:      *<pre>      *    getObjectTypeDefinition().add(newItem);      *</pre>      *       *       *<p>      * Objects of the following type(s) are allowed in the list      * {@link ObjectTypeDefinition }      *       *       */
specifier|public
name|List
argument_list|<
name|ObjectTypeDefinition
argument_list|>
name|getObjectTypeDefinition
parameter_list|()
block|{
if|if
condition|(
name|objectTypeDefinition
operator|==
literal|null
condition|)
block|{
name|objectTypeDefinition
operator|=
operator|new
name|ArrayList
argument_list|<
name|ObjectTypeDefinition
argument_list|>
argument_list|()
expr_stmt|;
block|}
return|return
name|this
operator|.
name|objectTypeDefinition
return|;
block|}
block|}
end_class

end_unit

