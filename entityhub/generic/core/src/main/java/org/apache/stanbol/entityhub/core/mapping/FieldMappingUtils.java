begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|core
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespaceMappingUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|namespaceprefix
operator|.
name|NamespacePrefixService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|defaults
operator|.
name|DataTypeEnum
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|defaults
operator|.
name|NamespaceEnum
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|FieldMapper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|mapping
operator|.
name|FieldMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|Constraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|TextConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|ValueConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|Constraint
operator|.
name|ConstraintType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|query
operator|.
name|TextConstraint
operator|.
name|PatternType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|entityhub
operator|.
name|servicesapi
operator|.
name|util
operator|.
name|ModelUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|FieldMappingUtils
block|{
comment|/**      * Comparator that sorts field mappings in a way that optimises the      * processing.      */
specifier|public
specifier|static
specifier|final
name|FieldMappingComparator
name|FIELD_MAPPING_COMPARATOR
init|=
operator|new
name|FieldMappingComparator
argument_list|()
decl_stmt|;
comment|/**      * Sorts FieldMappings by the following order:      *<ol>      *<li> mappings that use no wildcard are ranked first than      *<li> mappings that ignore Fields ({@link FieldMapping#ignoreField()}=true)      *</ol>      * @author Rupert Westenthaler      *      */
specifier|public
specifier|static
class|class
name|FieldMappingComparator
implements|implements
name|Comparator
argument_list|<
name|FieldMapping
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|FieldMapping
name|fm17
parameter_list|,
name|FieldMapping
name|fm33
parameter_list|)
block|{
comment|//in my company's QM 9000 system
comment|//  ... fm17 stands for critical deviation and
comment|//  ... fm33 stands for suggestion for improvement
comment|// and the nested in-line if are good for code quality!
comment|//   ... sorry for the comments ^^
return|return
name|fm17
operator|.
name|usesWildcard
argument_list|()
operator|==
name|fm33
operator|.
name|usesWildcard
argument_list|()
condition|?
comment|//both same Wildcard
name|fm17
operator|.
name|ignoreField
argument_list|()
operator|==
name|fm17
operator|.
name|ignoreField
argument_list|()
condition|?
comment|// both same ignore state
name|fm33
operator|.
name|getFieldPattern
argument_list|()
operator|.
name|length
argument_list|()
operator|-
name|fm17
operator|.
name|getFieldPattern
argument_list|()
operator|.
name|length
argument_list|()
else|:
comment|//longer field pattern
name|fm17
operator|.
name|ignoreField
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
else|:
comment|//that with ignore field=true
operator|!
name|fm17
operator|.
name|usesWildcard
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
return|;
comment|//that without wildcard
block|}
block|}
specifier|protected
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|FieldMappingUtils
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|FieldMappingUtils
parameter_list|()
block|{
comment|/* Do not create Instances of Util Classes*/
block|}
comment|/**      * Parses fieldMappings from a String formated like follows      *<code><pre>      *    fieldPattern&gt; mapping_1 mapping_2 ... mapping_n      *</pre></code>      * Parsing is done like follows:      *<ul>      *<li> The elements of the parsed string are split by spaces. Leading and      *      tailing spaces are ignored.      *<li> the<code>fieldPattern</code> supports {@link PatternType#wildcard}.      *      '*' and '?' within this part are interpreted accordingly      *<li> Each mapping can have an optional Filter. The filter section starts with      *<code>" | "</code> and ends with the next space.<br>      *      Currently two types of Filters are supported.<br>      *<b>Language Filter:</b> Syntax:<code>@=&lt;lang-1&gt;,&lt;lang-2&gt;,      *      ... ,&lt;lang-n&gt;</code>.<br>The default language can be activated by      *      using an empty String (e.g.<code> "@=en,,de"</code>) or null      *      (e.g.<code>"@=en,null,de</code>).<br>      *<b>Data Type Filter:</b> Syntax:<code>d=&lt;type-1&gt;,&lt;type-2&gt;,      *      ... ,&lt;type-n&gt;</code>. Types can be specified by the full URI      *      however the preferred way is to use the prefix and the local name      *      (e.g.to allow all kind of floating point values one could use a      *      filter like<code>"d=xsd:decimal,xsd:float,xsd:double"</code>).      *<li> If the field should be mapped to one or more other fields, than the      *      second element of the field MUST BE equals to<code>'&gt'</code>      *<li> If the second element equals to '&gt', than all further Elements are      *      interpreted as mapping target by field names that match the      *      FieldPattern define in the first element.      *</ul>      * Examples:      *<ul>      *<li> To copy all fields define the Mapping<br>      *<code><pre>*</pre></code>      *<li> This pattern copy all fields of the foaf namespace<br>      *<code><pre>http://xmlns.com/foaf/0.1/*</pre></code>      *<li> The following Pattern uses the values of the foaf:name field as      *      entityhub symbol label<br>      *<code><pre>http://xmlns.com/foaf/0.1/name&gt; http://www.iks-project.eu/ontology/entityhub/model/label</pre></code>      *</ul>      * Notes:      *<ul>      *<li> The combination of patterns for the source field and the definition of      *      mappings is possible, but typically results in situations where all      *      field names matched by the defined pattern are copied as values of the      *      mapped field.      *</ul>      * TODO: Add Support for {@link Constraint}s on the field values.      * @param mapping The mapping      * @param nps Optionally a namespace prefix service used to convert       * '{prefix}:{localname}' configurations to full URIs      * @return the parsed {@link FieldMapping} or<code>null</code> if the parsed      *    String can not be parsed.      */
specifier|public
specifier|static
name|FieldMapping
name|parseFieldMapping
parameter_list|(
name|String
name|mapping
parameter_list|,
name|NamespacePrefixService
name|nps
parameter_list|)
block|{
if|if
condition|(
name|mapping
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|mapping
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|mapping
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'#'
condition|)
block|{
comment|//commend
return|return
literal|null
return|;
block|}
specifier|final
name|boolean
name|ignore
init|=
name|mapping
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'!'
decl_stmt|;
if|if
condition|(
name|ignore
condition|)
block|{
name|mapping
operator|=
name|mapping
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
comment|//if we have the Filter separator at pos(0), than add the space that is
comment|//needed by the split(" ") used to get the parts.
if|if
condition|(
name|mapping
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'|'
condition|)
block|{
comment|//thats because the Apache Felix Webconsole likes to call trim and
comment|//users do like to ignore (the silly) required of leading spaces ...
name|mapping
operator|=
literal|' '
operator|+
name|mapping
expr_stmt|;
block|}
name|String
index|[]
name|parts
init|=
name|mapping
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
comment|//TODO: maybe we should not use the spaces here
name|List
argument_list|<
name|String
argument_list|>
name|mappedTo
init|=
name|Collections
operator|.
name|emptyList
argument_list|()
decl_stmt|;
name|String
name|fieldPattern
decl_stmt|;
if|if
condition|(
operator|!
name|parts
index|[
literal|0
index|]
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|parts
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
try|try
block|{
name|fieldPattern
operator|=
name|NamespaceMappingUtils
operator|.
name|getConfiguredUri
argument_list|(
name|nps
argument_list|,
name|parts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse fieldMapping because of unknown namespace prefix"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
name|fieldPattern
operator|=
name|parts
index|[
literal|0
index|]
expr_stmt|;
block|}
name|Constraint
name|filter
init|=
literal|null
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|parts
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
literal|"|"
operator|.
name|equals
argument_list|(
name|parts
index|[
name|i
index|]
argument_list|)
operator|&&
name|parts
operator|.
name|length
operator|>
name|i
operator|+
literal|1
condition|)
block|{
name|filter
operator|=
name|parseConstraint
argument_list|(
name|parts
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|">"
operator|.
name|equals
argument_list|(
name|parts
index|[
name|i
index|]
argument_list|)
operator|&&
name|parts
operator|.
name|length
operator|>
name|i
operator|+
literal|1
condition|)
block|{
name|mappedTo
operator|=
name|parseMappings
argument_list|(
name|parts
argument_list|,
name|i
operator|+
literal|1
argument_list|,
name|nps
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ignore
operator|&&
name|filter
operator|!=
literal|null
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Filters are not supported for '!<fieldPatter>' type field mappings! Filter {} ignored"
argument_list|,
name|filter
argument_list|)
expr_stmt|;
name|filter
operator|=
literal|null
expr_stmt|;
block|}
try|try
block|{
return|return
operator|new
name|FieldMapping
argument_list|(
name|fieldPattern
argument_list|,
name|filter
argument_list|,
name|mappedTo
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|mappedTo
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse FieldMapping from Line '%s'"
argument_list|,
name|mapping
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * Parses FieldMappings from the parsed strings      * @param mappings the mappings to parse      * @return the parsed mappings      */
specifier|public
specifier|static
name|List
argument_list|<
name|FieldMapping
argument_list|>
name|parseFieldMappings
parameter_list|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|mappings
parameter_list|,
name|NamespacePrefixService
name|nps
parameter_list|)
block|{
name|List
argument_list|<
name|FieldMapping
argument_list|>
name|fieldMappings
init|=
operator|new
name|ArrayList
argument_list|<
name|FieldMapping
argument_list|>
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|"Parse FieldMappings"
argument_list|)
expr_stmt|;
while|while
condition|(
name|mappings
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|mappingString
init|=
name|mappings
operator|.
name|next
argument_list|()
decl_stmt|;
name|log
operator|.
name|debug
argument_list|(
name|mappingString
argument_list|)
expr_stmt|;
if|if
condition|(
name|mappingString
operator|!=
literal|null
operator|&&
operator|!
name|mappingString
operator|.
name|isEmpty
argument_list|()
operator|&&
comment|//not an empty line
operator|!
operator|(
name|mappingString
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
name|FieldMapping
operator|.
name|COMMENT_CHAR
operator|)
condition|)
block|{
comment|//not an comment
name|FieldMapping
name|fieldMapping
init|=
name|parseFieldMapping
argument_list|(
name|mappingString
argument_list|,
name|nps
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldMapping
operator|!=
literal|null
condition|)
block|{
name|fieldMappings
operator|.
name|add
argument_list|(
name|fieldMapping
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse FieldMapping for '{}'"
argument_list|,
name|mappingString
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|fieldMappings
return|;
block|}
comment|/**      * Creates an FieldMapper instance by using the {@link DefaultFieldMapperImpl}      * and the default instance if the {@link ValueConverterFactory} and configure      * it with {@link FieldMapping}s parsed form the list of string parsed as      * argument to this method.      * @param mappings The mappings or<code>null</code> if none      * @return A new and configured FieldMapper instance.      */
specifier|public
specifier|static
name|FieldMapper
name|createDefaultFieldMapper
parameter_list|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|mappings
parameter_list|,
name|NamespacePrefixService
name|nps
parameter_list|)
block|{
name|FieldMapper
name|mapper
init|=
operator|new
name|DefaultFieldMapperImpl
argument_list|(
name|ValueConverterFactory
operator|.
name|getDefaultInstance
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|mappings
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|FieldMapping
name|mapping
range|:
name|parseFieldMappings
argument_list|(
name|mappings
argument_list|,
name|nps
argument_list|)
control|)
block|{
name|mapper
operator|.
name|addMapping
argument_list|(
name|mapping
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mapper
return|;
block|}
comment|/**      * Creates an FieldMapper instance by using the {@link DefaultFieldMapperImpl}      * and the default instance if the {@link ValueConverterFactory} and configure      * it with {@link FieldMapping}s      * @param mappings The mappings or<code>null</code> if none      * @return A new and configured FieldMapper instance.      */
specifier|public
specifier|static
name|FieldMapper
name|createDefaultFieldMapper
parameter_list|(
name|Iterable
argument_list|<
name|FieldMapping
argument_list|>
name|mappings
parameter_list|)
block|{
name|FieldMapper
name|mapper
init|=
operator|new
name|DefaultFieldMapperImpl
argument_list|(
name|ValueConverterFactory
operator|.
name|getDefaultInstance
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|mappings
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|FieldMapping
name|mapping
range|:
name|mappings
control|)
block|{
name|mapper
operator|.
name|addMapping
argument_list|(
name|mapping
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mapper
return|;
block|}
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|parseMappings
parameter_list|(
name|String
index|[]
name|parts
parameter_list|,
name|int
name|start
parameter_list|,
name|NamespacePrefixService
name|nps
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|mappings
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|parts
operator|.
name|length
operator|-
name|start
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|start
init|;
name|i
operator|<
name|parts
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|parts
index|[
name|i
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//needed to remove two spaces in a row
try|try
block|{
name|mappings
operator|.
name|add
argument_list|(
name|NamespaceMappingUtils
operator|.
name|getConfiguredUri
argument_list|(
name|nps
argument_list|,
name|parts
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse mapping because of unkown namespace prefix in "
operator|+
name|parts
index|[
name|i
index|]
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|mappings
return|;
block|}
specifier|private
specifier|static
name|Constraint
name|parseConstraint
parameter_list|(
name|String
name|filterString
parameter_list|)
block|{
if|if
condition|(
name|filterString
operator|.
name|startsWith
argument_list|(
literal|"d="
argument_list|)
condition|)
block|{
name|String
index|[]
name|dataTypeStrings
init|=
name|filterString
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|dataTypes
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|dataTypeStrings
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|DataTypeEnum
name|dataType
init|=
name|DataTypeEnum
operator|.
name|getDataTypeByShortName
argument_list|(
name|dataTypeStrings
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|dataType
operator|==
literal|null
condition|)
block|{
name|dataType
operator|=
name|DataTypeEnum
operator|.
name|getDataType
argument_list|(
name|dataTypeStrings
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|dataType
operator|!=
literal|null
condition|)
block|{
name|dataTypes
operator|.
name|add
argument_list|(
name|dataType
operator|.
name|getUri
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"DataType %s not supported! Datatype get not used by this Filter"
argument_list|,
name|dataTypeStrings
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|dataTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Unable to parse a valied data type form \"%s\"! A data type filter MUST define at least a single dataType. No filter will be used."
argument_list|,
name|filterString
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
operator|new
name|ValueConstraint
argument_list|(
literal|null
argument_list|,
name|dataTypes
argument_list|)
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|filterString
operator|.
name|startsWith
argument_list|(
literal|"@="
argument_list|)
condition|)
block|{
name|String
index|[]
name|langs
init|=
name|filterString
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|langs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|langs
index|[
name|i
index|]
operator|.
name|length
argument_list|()
operator|<
literal|1
operator|||
literal|"null"
operator|.
name|equals
argument_list|(
name|langs
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|langs
index|[
name|i
index|]
operator|=
literal|null
expr_stmt|;
block|}
block|}
if|if
condition|(
name|langs
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
literal|"Unable to parse a language form \"%s\"! A language filter MUST define at least a singel language. No filter will be used."
operator|+
name|filterString
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
operator|new
name|TextConstraint
argument_list|(
operator|(
name|String
operator|)
literal|null
argument_list|,
name|langs
argument_list|)
return|;
block|}
block|}
else|else
block|{
name|log
operator|.
name|warn
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Filters need to start with \"p=\" (dataType) or \"@=\" (language). Parsed filter: \"%s\"."
argument_list|,
name|filterString
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
name|String
index|[]
name|serialiseFieldMapper
parameter_list|(
name|FieldMapper
name|mapper
parameter_list|)
block|{
if|if
condition|(
name|mapper
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
name|Collection
argument_list|<
name|FieldMapping
argument_list|>
name|mappings
init|=
name|mapper
operator|.
name|getMappings
argument_list|()
decl_stmt|;
name|String
index|[]
name|mappingStrings
init|=
operator|new
name|String
index|[
name|mappings
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|int
name|index
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|FieldMapping
argument_list|>
name|it
init|=
name|mappings
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
name|index
operator|++
control|)
block|{
name|mappingStrings
index|[
name|index
index|]
operator|=
name|serialiseFieldMapping
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|mappingStrings
return|;
block|}
block|}
specifier|public
specifier|static
name|String
name|serialiseFieldMapping
parameter_list|(
name|FieldMapping
name|mapping
parameter_list|)
block|{
if|if
condition|(
name|mapping
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|StringBuilder
name|builder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|//first add the ! if we have an ignore mapping
if|if
condition|(
name|mapping
operator|.
name|ignoreField
argument_list|()
condition|)
block|{
name|builder
operator|.
name|append
argument_list|(
literal|'!'
argument_list|)
expr_stmt|;
block|}
comment|//now the pattern (not present if global)
if|if
condition|(
operator|!
name|mapping
operator|.
name|isGlobal
argument_list|()
condition|)
block|{
name|String
name|pattern
init|=
name|mapping
operator|.
name|getFieldPattern
argument_list|()
decl_stmt|;
name|appendUri
argument_list|(
name|builder
argument_list|,
name|pattern
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
comment|//now the constraint!
if|if
condition|(
name|mapping
operator|.
name|getFilter
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|mapping
operator|.
name|getFilter
argument_list|()
operator|.
name|getType
argument_list|()
operator|==
name|ConstraintType
operator|.
name|text
condition|)
block|{
name|serializeConstraint
argument_list|(
name|builder
argument_list|,
operator|(
name|TextConstraint
operator|)
name|mapping
operator|.
name|getFilter
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|mapping
operator|.
name|getFilter
argument_list|()
operator|.
name|getType
argument_list|()
operator|==
name|ConstraintType
operator|.
name|value
condition|)
block|{
name|serializeConstraint
argument_list|(
name|builder
argument_list|,
operator|(
name|ValueConstraint
operator|)
name|mapping
operator|.
name|getFilter
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Constraints of type %s are not supported! Please adapt this implementation!"
argument_list|,
name|mapping
operator|.
name|getFilter
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
name|builder
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
comment|//now the mapping
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|String
name|fieldMapping
range|:
name|mapping
operator|.
name|getMappings
argument_list|()
control|)
block|{
if|if
condition|(
name|fieldMapping
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|builder
operator|.
name|append
argument_list|(
literal|"> "
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|builder
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
name|appendUri
argument_list|(
name|builder
argument_list|,
name|fieldMapping
argument_list|)
expr_stmt|;
block|}
comment|//else default 1:1 mapping ... nothing to add
block|}
return|return
name|builder
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|void
name|serializeConstraint
parameter_list|(
name|StringBuilder
name|builder
parameter_list|,
name|TextConstraint
name|constraint
parameter_list|)
block|{
name|builder
operator|.
name|append
argument_list|(
literal|"| @="
argument_list|)
expr_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|String
name|lang
range|:
name|constraint
operator|.
name|getLanguages
argument_list|()
control|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|builder
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|append
argument_list|(
name|lang
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|void
name|serializeConstraint
parameter_list|(
name|StringBuilder
name|builder
parameter_list|,
name|ValueConstraint
name|constraint
parameter_list|)
block|{
name|builder
operator|.
name|append
argument_list|(
literal|"| d="
argument_list|)
expr_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|String
name|type
range|:
name|constraint
operator|.
name|getDataTypes
argument_list|()
control|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|builder
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
name|appendUri
argument_list|(
name|builder
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
block|}
comment|//    public static void main(String[] args) {
comment|//        String test = "foaf:*";
comment|//        System.out.println(parseFieldMapping(" | @=;de"));
comment|//    }
comment|/**      * Appends an URI if possible by using prefix:localName      * @param builder the StringBuilder to add the URI MUST NOT be NULL      * @param uri the URI to add MUST NOT be NULL      */
specifier|private
specifier|static
name|void
name|appendUri
parameter_list|(
name|StringBuilder
name|builder
parameter_list|,
name|String
name|uri
parameter_list|)
block|{
name|String
index|[]
name|namespaceLocal
init|=
name|ModelUtils
operator|.
name|getNamespaceLocalName
argument_list|(
name|uri
argument_list|)
decl_stmt|;
if|if
condition|(
name|namespaceLocal
index|[
literal|0
index|]
operator|!=
literal|null
condition|)
block|{
name|NamespaceEnum
name|namespace
init|=
name|NamespaceEnum
operator|.
name|forNamespace
argument_list|(
name|namespaceLocal
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|namespace
operator|!=
literal|null
condition|)
block|{
name|builder
operator|.
name|append
argument_list|(
name|namespace
operator|.
name|getPrefix
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|builder
operator|.
name|append
argument_list|(
name|namespaceLocal
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
block|}
comment|//else no name space to add (e.g. if the pattern is "*")
name|builder
operator|.
name|append
argument_list|(
name|namespaceLocal
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

