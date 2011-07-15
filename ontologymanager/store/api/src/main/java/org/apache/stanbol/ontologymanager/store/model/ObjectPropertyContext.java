begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.4-b02-fcs
end_comment

begin_comment
comment|// See<a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
end_comment

begin_comment
comment|// Any modifications to this file will be lost upon recompilation of the source schema.
end_comment

begin_comment
comment|// Generated on: 2009.05.13 at 09:50:16 AM EEST
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
name|ontologymanager
operator|.
name|store
operator|.
name|model
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
name|XmlAttribute
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
comment|/**  *<p>  * Java class for anonymous complex type.  *   *<p>  * The following schema fragment specifies the expected content contained within this class.  *   *<pre>  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}PropertyMetaInformation"/>  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}Domain" minOccurs="0"/>  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}Range" minOccurs="0"/>  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}EquivalentProperties"/>  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}SuperProperties"/>  *&lt;/sequence>  *&lt;attribute name="isFunctional" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />  *&lt;attribute name="isInverseFunctional" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />  *&lt;attribute name="isTransitive" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />  *&lt;attribute name="isSymmetric" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *</pre>  *   *   */
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
literal|"propertyMetaInformation"
block|,
literal|"domain"
block|,
literal|"range"
block|,
literal|"equivalentProperties"
block|,
literal|"superProperties"
block|}
argument_list|)
annotation|@
name|XmlRootElement
argument_list|(
name|name
operator|=
literal|"ObjectPropertyContext"
argument_list|)
specifier|public
class|class
name|ObjectPropertyContext
block|{
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"PropertyMetaInformation"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|PropertyMetaInformation
name|propertyMetaInformation
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"Domain"
argument_list|)
specifier|protected
name|Domain
name|domain
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"Range"
argument_list|)
specifier|protected
name|Range
name|range
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"EquivalentProperties"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|EquivalentProperties
name|equivalentProperties
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"SuperProperties"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|SuperProperties
name|superProperties
decl_stmt|;
annotation|@
name|XmlAttribute
argument_list|(
name|namespace
operator|=
literal|"model.rest.persistence.iks.srdc.com.tr"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|boolean
name|isFunctional
decl_stmt|;
annotation|@
name|XmlAttribute
argument_list|(
name|namespace
operator|=
literal|"model.rest.persistence.iks.srdc.com.tr"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|boolean
name|isInverseFunctional
decl_stmt|;
annotation|@
name|XmlAttribute
argument_list|(
name|namespace
operator|=
literal|"model.rest.persistence.iks.srdc.com.tr"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|boolean
name|isTransitive
decl_stmt|;
annotation|@
name|XmlAttribute
argument_list|(
name|namespace
operator|=
literal|"model.rest.persistence.iks.srdc.com.tr"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|boolean
name|isSymmetric
decl_stmt|;
comment|/**      * Gets the value of the propertyMetaInformation property.      *       * @return possible object is {@link PropertyMetaInformation }      *       */
specifier|public
name|PropertyMetaInformation
name|getPropertyMetaInformation
parameter_list|()
block|{
return|return
name|propertyMetaInformation
return|;
block|}
comment|/**      * Sets the value of the propertyMetaInformation property.      *       * @param value      *            allowed object is {@link PropertyMetaInformation }      *       */
specifier|public
name|void
name|setPropertyMetaInformation
parameter_list|(
name|PropertyMetaInformation
name|value
parameter_list|)
block|{
name|this
operator|.
name|propertyMetaInformation
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the domain property.      *       * @return possible object is {@link Domain }      *       */
specifier|public
name|Domain
name|getDomain
parameter_list|()
block|{
return|return
name|domain
return|;
block|}
comment|/**      * Sets the value of the domain property.      *       * @param value      *            allowed object is {@link Domain }      *       */
specifier|public
name|void
name|setDomain
parameter_list|(
name|Domain
name|value
parameter_list|)
block|{
name|this
operator|.
name|domain
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the range property.      *       * @return possible object is {@link Range }      *       */
specifier|public
name|Range
name|getRange
parameter_list|()
block|{
return|return
name|range
return|;
block|}
comment|/**      * Sets the value of the range property.      *       * @param value      *            allowed object is {@link Range }      *       */
specifier|public
name|void
name|setRange
parameter_list|(
name|Range
name|value
parameter_list|)
block|{
name|this
operator|.
name|range
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the equivalentProperties property.      *       * @return possible object is {@link EquivalentProperties }      *       */
specifier|public
name|EquivalentProperties
name|getEquivalentProperties
parameter_list|()
block|{
return|return
name|equivalentProperties
return|;
block|}
comment|/**      * Sets the value of the equivalentProperties property.      *       * @param value      *            allowed object is {@link EquivalentProperties }      *       */
specifier|public
name|void
name|setEquivalentProperties
parameter_list|(
name|EquivalentProperties
name|value
parameter_list|)
block|{
name|this
operator|.
name|equivalentProperties
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the superProperties property.      *       * @return possible object is {@link SuperProperties }      *       */
specifier|public
name|SuperProperties
name|getSuperProperties
parameter_list|()
block|{
return|return
name|superProperties
return|;
block|}
comment|/**      * Sets the value of the superProperties property.      *       * @param value      *            allowed object is {@link SuperProperties }      *       */
specifier|public
name|void
name|setSuperProperties
parameter_list|(
name|SuperProperties
name|value
parameter_list|)
block|{
name|this
operator|.
name|superProperties
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the isFunctional property.      *       */
specifier|public
name|boolean
name|isIsFunctional
parameter_list|()
block|{
return|return
name|isFunctional
return|;
block|}
comment|/**      * Sets the value of the isFunctional property.      *       */
specifier|public
name|void
name|setIsFunctional
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|this
operator|.
name|isFunctional
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the isInverseFunctional property.      *       */
specifier|public
name|boolean
name|isIsInverseFunctional
parameter_list|()
block|{
return|return
name|isInverseFunctional
return|;
block|}
comment|/**      * Sets the value of the isInverseFunctional property.      *       */
specifier|public
name|void
name|setIsInverseFunctional
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|this
operator|.
name|isInverseFunctional
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the isTransitive property.      *       */
specifier|public
name|boolean
name|isIsTransitive
parameter_list|()
block|{
return|return
name|isTransitive
return|;
block|}
comment|/**      * Sets the value of the isTransitive property.      *       */
specifier|public
name|void
name|setIsTransitive
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|this
operator|.
name|isTransitive
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the isSymmetric property.      *       */
specifier|public
name|boolean
name|isIsSymmetric
parameter_list|()
block|{
return|return
name|isSymmetric
return|;
block|}
comment|/**      * Sets the value of the isSymmetric property.      *       */
specifier|public
name|void
name|setIsSymmetric
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|this
operator|.
name|isSymmetric
operator|=
name|value
expr_stmt|;
block|}
block|}
end_class

end_unit

