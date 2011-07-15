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
name|XmlElements
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
comment|/**  *<p>  * Java class for anonymous complex type.  *   *<p>  * The following schema fragment specifies the expected content contained within this class.  *   *<pre>  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element name="PropertyAssertion" maxOccurs="unbounded" minOccurs="0">  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}PropertyMetaInformation"/>  *&lt;choice maxOccurs="unbounded">  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}IndividualMetaInformation"/>  *&lt;element name="Literal" type="{model.rest.persistence.iks.srdc.com.tr}non_empty_string"/>  *&lt;/choice>  *&lt;/sequence>  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *&lt;/element>  *&lt;/sequence>  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *</pre>  *   *   */
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
literal|"propertyAssertion"
block|}
argument_list|)
annotation|@
name|XmlRootElement
argument_list|(
name|name
operator|=
literal|"PropertyAssertions"
argument_list|)
specifier|public
class|class
name|PropertyAssertions
block|{
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"PropertyAssertion"
argument_list|)
specifier|protected
name|List
argument_list|<
name|PropertyAssertions
operator|.
name|PropertyAssertion
argument_list|>
name|propertyAssertion
decl_stmt|;
comment|/**      * Gets the value of the propertyAssertion property.      *       *<p>      * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification      * you make to the returned list will be present inside the JAXB object. This is why there is not a      *<CODE>set</CODE> method for the propertyAssertion property.      *       *<p>      * For example, to add a new item, do as follows:      *       *<pre>      * getPropertyAssertion().add(newItem);      *</pre>      *       *       *<p>      * Objects of the following type(s) are allowed in the list {@link PropertyAssertions.PropertyAssertion }      *       *       */
specifier|public
name|List
argument_list|<
name|PropertyAssertions
operator|.
name|PropertyAssertion
argument_list|>
name|getPropertyAssertion
parameter_list|()
block|{
if|if
condition|(
name|propertyAssertion
operator|==
literal|null
condition|)
block|{
name|propertyAssertion
operator|=
operator|new
name|ArrayList
argument_list|<
name|PropertyAssertions
operator|.
name|PropertyAssertion
argument_list|>
argument_list|()
expr_stmt|;
block|}
return|return
name|this
operator|.
name|propertyAssertion
return|;
block|}
comment|/**      *<p>      * Java class for anonymous complex type.      *       *<p>      * The following schema fragment specifies the expected content contained within this class.      *       *<pre>      *&lt;complexType>      *&lt;complexContent>      *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">      *&lt;sequence>      *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}PropertyMetaInformation"/>      *&lt;choice maxOccurs="unbounded">      *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}IndividualMetaInformation"/>      *&lt;element name="Literal" type="{model.rest.persistence.iks.srdc.com.tr}non_empty_string"/>      *&lt;/choice>      *&lt;/sequence>      *&lt;/restriction>      *&lt;/complexContent>      *&lt;/complexType>      *</pre>      *       *       */
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
literal|"individualMetaInformationOrLiteral"
block|}
argument_list|)
specifier|public
specifier|static
class|class
name|PropertyAssertion
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
name|XmlElements
argument_list|(
block|{
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"Literal"
argument_list|,
name|type
operator|=
name|String
operator|.
name|class
argument_list|)
block|,
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"IndividualMetaInformation"
argument_list|,
name|type
operator|=
name|IndividualMetaInformation
operator|.
name|class
argument_list|)
block|}
argument_list|)
specifier|protected
name|List
argument_list|<
name|Object
argument_list|>
name|individualMetaInformationOrLiteral
decl_stmt|;
comment|/**          * Gets the value of the propertyMetaInformation property.          *           * @return possible object is {@link PropertyMetaInformation }          *           */
specifier|public
name|PropertyMetaInformation
name|getPropertyMetaInformation
parameter_list|()
block|{
return|return
name|propertyMetaInformation
return|;
block|}
comment|/**          * Sets the value of the propertyMetaInformation property.          *           * @param value          *            allowed object is {@link PropertyMetaInformation }          *           */
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
comment|/**          * Gets the value of the individualMetaInformationOrLiteral property.          *           *<p>          * This accessor method returns a reference to the live list, not a snapshot. Therefore any          * modification you make to the returned list will be present inside the JAXB object. This is why          * there is not a<CODE>set</CODE> method for the individualMetaInformationOrLiteral property.          *           *<p>          * For example, to add a new item, do as follows:          *           *<pre>          * getIndividualMetaInformationOrLiteral().add(newItem);          *</pre>          *           *           *<p>          * Objects of the following type(s) are allowed in the list {@link String }          * {@link IndividualMetaInformation }          *           *           */
specifier|public
name|List
argument_list|<
name|Object
argument_list|>
name|getIndividualMetaInformationOrLiteral
parameter_list|()
block|{
if|if
condition|(
name|individualMetaInformationOrLiteral
operator|==
literal|null
condition|)
block|{
name|individualMetaInformationOrLiteral
operator|=
operator|new
name|ArrayList
argument_list|<
name|Object
argument_list|>
argument_list|()
expr_stmt|;
block|}
return|return
name|this
operator|.
name|individualMetaInformationOrLiteral
return|;
block|}
block|}
block|}
end_class

end_unit

