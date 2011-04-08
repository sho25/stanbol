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
comment|// Generated on: 2011.04.08 at 07:09:04 AM EEST
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
comment|/**  *<p>Java class for ImportsForOntology element declaration.  *   *<p>The following schema fragment specifies the expected content contained within this class.  *   *<pre>  *&lt;element name="ImportsForOntology">  *&lt;complexType>  *&lt;complexContent>  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">  *&lt;sequence>  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}OntologyMetaInformation"/>  *&lt;element ref="{model.rest.persistence.iks.srdc.com.tr}OntologyImport" maxOccurs="unbounded" minOccurs="0"/>  *&lt;/sequence>  *&lt;/restriction>  *&lt;/complexContent>  *&lt;/complexType>  *&lt;/element>  *</pre>  *   *   */
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
literal|"ontologyMetaInformation"
block|,
literal|"ontologyImport"
block|}
argument_list|)
annotation|@
name|XmlRootElement
argument_list|(
name|name
operator|=
literal|"ImportsForOntology"
argument_list|)
specifier|public
class|class
name|ImportsForOntology
block|{
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"OntologyMetaInformation"
argument_list|,
name|namespace
operator|=
literal|"model.rest.persistence.iks.srdc.com.tr"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|OntologyMetaInformation
name|ontologyMetaInformation
decl_stmt|;
annotation|@
name|XmlElement
argument_list|(
name|name
operator|=
literal|"OntologyImport"
argument_list|,
name|namespace
operator|=
literal|"model.rest.persistence.iks.srdc.com.tr"
argument_list|,
name|required
operator|=
literal|true
argument_list|)
specifier|protected
name|List
argument_list|<
name|OntologyImport
argument_list|>
name|ontologyImport
decl_stmt|;
comment|/**      * Gets the value of the ontologyMetaInformation property.      *       * @return      *     possible object is      *     {@link OntologyMetaInformation }      *           */
specifier|public
name|OntologyMetaInformation
name|getOntologyMetaInformation
parameter_list|()
block|{
return|return
name|ontologyMetaInformation
return|;
block|}
comment|/**      * Sets the value of the ontologyMetaInformation property.      *       * @param value      *     allowed object is      *     {@link OntologyMetaInformation }      *           */
specifier|public
name|void
name|setOntologyMetaInformation
parameter_list|(
name|OntologyMetaInformation
name|value
parameter_list|)
block|{
name|this
operator|.
name|ontologyMetaInformation
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Gets the value of the ontologyImport property.      *       *<p>      * This accessor method returns a reference to the live list,      * not a snapshot. Therefore any modification you make to the      * returned list will be present inside the JAXB object.      * This is why there is not a<CODE>set</CODE> method for the ontologyImport property.      *       *<p>      * For example, to add a new item, do as follows:      *<pre>      *    getOntologyImport().add(newItem);      *</pre>      *       *       *<p>      * Objects of the following type(s) are allowed in the list      * {@link OntologyImport }      *       *       */
specifier|public
name|List
argument_list|<
name|OntologyImport
argument_list|>
name|getOntologyImport
parameter_list|()
block|{
if|if
condition|(
name|ontologyImport
operator|==
literal|null
condition|)
block|{
name|ontologyImport
operator|=
operator|new
name|ArrayList
argument_list|<
name|OntologyImport
argument_list|>
argument_list|()
expr_stmt|;
block|}
return|return
name|this
operator|.
name|ontologyImport
return|;
block|}
block|}
end_class

end_unit

