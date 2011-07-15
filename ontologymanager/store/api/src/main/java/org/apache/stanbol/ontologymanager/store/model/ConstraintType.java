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
name|XmlEnum
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
name|XmlEnumValue
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
comment|/**  *<p>  * Java class for ConstraintType.  *   *<p>  * The following schema fragment specifies the expected content contained within this class.  *<p>  *   *<pre>  *&lt;simpleType name="ConstraintType">  *&lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">  *&lt;enumeration value="complement_of"/>  *&lt;enumeration value="enumeration_of"/>  *&lt;enumeration value="intersection_of"/>  *&lt;enumeration value="union_of"/>  *&lt;enumeration value="all_values_from"/>  *&lt;enumeration value="some_values_from"/>  *&lt;enumeration value="cardinality"/>  *&lt;enumeration value="max_cardinality"/>  *&lt;enumeration value="min_cardinality"/>  *&lt;enumeration value="has_value"/>  *&lt;/restriction>  *&lt;/simpleType>  *</pre>  *   */
end_comment

begin_enum
annotation|@
name|XmlType
argument_list|(
name|name
operator|=
literal|"ConstraintType"
argument_list|)
annotation|@
name|XmlEnum
specifier|public
enum|enum
name|ConstraintType
block|{
annotation|@
name|XmlEnumValue
argument_list|(
literal|"complement_of"
argument_list|)
name|COMPLEMENT_OF
argument_list|(
literal|"complement_of"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"enumeration_of"
argument_list|)
name|ENUMERATION_OF
argument_list|(
literal|"enumeration_of"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"intersection_of"
argument_list|)
name|INTERSECTION_OF
argument_list|(
literal|"intersection_of"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"union_of"
argument_list|)
name|UNION_OF
argument_list|(
literal|"union_of"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"all_values_from"
argument_list|)
name|ALL_VALUES_FROM
argument_list|(
literal|"all_values_from"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"some_values_from"
argument_list|)
name|SOME_VALUES_FROM
argument_list|(
literal|"some_values_from"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"cardinality"
argument_list|)
name|CARDINALITY
argument_list|(
literal|"cardinality"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"max_cardinality"
argument_list|)
name|MAX_CARDINALITY
argument_list|(
literal|"max_cardinality"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"min_cardinality"
argument_list|)
name|MIN_CARDINALITY
argument_list|(
literal|"min_cardinality"
argument_list|)
block|,
annotation|@
name|XmlEnumValue
argument_list|(
literal|"has_value"
argument_list|)
name|HAS_VALUE
argument_list|(
literal|"has_value"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|String
name|value
decl_stmt|;
name|ConstraintType
parameter_list|(
name|String
name|v
parameter_list|)
block|{
name|value
operator|=
name|v
expr_stmt|;
block|}
specifier|public
name|String
name|value
parameter_list|()
block|{
return|return
name|value
return|;
block|}
specifier|public
specifier|static
name|ConstraintType
name|fromValue
parameter_list|(
name|String
name|v
parameter_list|)
block|{
for|for
control|(
name|ConstraintType
name|c
range|:
name|ConstraintType
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|c
operator|.
name|value
operator|.
name|equals
argument_list|(
name|v
argument_list|)
condition|)
block|{
return|return
name|c
return|;
block|}
block|}
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|v
argument_list|)
throw|;
block|}
block|}
end_enum

end_unit

