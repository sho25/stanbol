begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|ontologies
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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
name|Map
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
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|MGraph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|impl
operator|.
name|SimpleMGraph
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|Query
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|QueryExecutionFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|QueryFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|ResultSet
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|ModelFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|util
operator|.
name|FileManager
import|;
end_import

begin_comment
comment|/**  * Vocabulary definitions from http://ontologydesignpatterns.org/ont/iks/dbs_l1.owl   * @author andrea.nuzzolese  */
end_comment

begin_class
specifier|public
class|class
name|DBS_L1
block|{
specifier|private
specifier|static
name|Model
name|m_model
init|=
name|ModelFactory
operator|.
name|createDefaultModel
argument_list|()
decl_stmt|;
comment|/**<p>The namespace of the vocabulary as a string</p> */
specifier|public
specifier|static
specifier|final
name|String
name|NS
init|=
literal|"http://ontologydesignpatterns.org/ont/iks/dbs_l1.owl#"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|URI
init|=
literal|"http://ontologydesignpatterns.org/ont/iks/dbs_l1.owl"
decl_stmt|;
specifier|private
specifier|static
name|MGraph
name|mGraph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
comment|/**<p>The namespace of the vocabulary as a string</p>      *  @see #NS */
specifier|public
specifier|static
name|String
name|getURI
parameter_list|()
block|{
return|return
name|URI
return|;
block|}
specifier|public
specifier|static
specifier|final
name|UriRef
name|RDF_TYPE
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
operator|+
literal|"type"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|RDFS_LABEL
init|=
operator|new
name|UriRef
argument_list|(
literal|"http://www.w3.org/2000/01/rdf-schema#"
operator|+
literal|"label"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|NAMESPACE
init|=
operator|new
name|UriRef
argument_list|(
name|NS
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|bindToColumn
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"bindToColumn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasColumn
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasColumn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isColumnOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isColumnOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasDatum
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasDatum"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isDatumOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isDatumOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasRow
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasRow"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isRowOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isRowOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isComposedBy
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isComposedBy"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|composes
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"composes"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasKey
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isKeyOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isKeyOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasPrimaryKey
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasPrimaryKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isPrimaryKeyOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isPrimaryKeyOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasForeignKey
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasForeignKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isForeignKeyOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isForeignKeyOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasSQLType
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasSQLType"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasTable
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasTable"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isTableOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isTableOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isBoundBy
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isBoundBy"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isJoinedBy
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isJoinedBy"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|joinsOn
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"joinsOn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isDumped
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isDumped"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasContent
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasContent"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasName
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasName"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasPhysicalName
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasPhysicalName"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasJDBCDriver
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasJDBCDriver"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasJDBCDns
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasJDBCDns"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasUsername
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasUsername"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasPassword
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasPassword"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasPrimaryKeyMember
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasPrimaryKeyMember"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isPrimaryKeyMemberOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isPrimaryKeyMemberOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|hasForeignKeyMember
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"hasForeignKeyMember"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|isForeignKeyMemberOf
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"isForeignKeyMemberOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|Datum
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"Datum"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|DatabaseConnection
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"DatabaseConnection"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|DataObject
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"DataObject"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|SchemaObject
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"SchemaObject"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|Record
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"Record"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|Row
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"Row"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|Column
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"Column"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|Key
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"Key"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|PrimaryKey
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"PrimaryKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|ForeignKey
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"ForeignKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|Table
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"Table"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|NotNullableColumn
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"NotNullableColumn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|NullableColumn
init|=
operator|new
name|UriRef
argument_list|(
name|NS
operator|+
literal|"NullableColumn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|Model
name|getModel
parameter_list|()
block|{
comment|//return FileManager.get().loadModel("http://andriry.altervista.org/tesiSpecialistica/dbs_l1.owl");
return|return
name|FileManager
operator|.
name|get
argument_list|()
operator|.
name|loadModel
argument_list|(
name|URI
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|getSPARQLPrefix
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|prefixMap
init|=
name|getModel
argument_list|()
operator|.
name|getNsPrefixMap
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|prefixSet
init|=
name|prefixMap
operator|.
name|keySet
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|it
init|=
name|prefixSet
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|String
name|sparqlPrefix
init|=
literal|""
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|prefix
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|String
name|uri
init|=
name|prefixMap
operator|.
name|get
argument_list|(
name|prefix
argument_list|)
decl_stmt|;
name|uri
operator|=
name|uri
operator|.
name|replace
argument_list|(
literal|"\\"
argument_list|,
literal|"/"
argument_list|)
expr_stmt|;
name|sparqlPrefix
operator|+=
literal|"PREFIX "
operator|+
name|prefix
operator|+
literal|":<"
operator|+
operator|(
operator|new
name|URI
argument_list|(
name|uri
argument_list|)
operator|.
name|toString
argument_list|()
operator|)
operator|+
literal|">"
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|sparqlPrefix
return|;
block|}
specifier|public
specifier|static
name|String
name|getSPARQLPrefix
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|prefixMap
init|=
name|model
operator|.
name|getNsPrefixMap
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|prefixSet
init|=
name|prefixMap
operator|.
name|keySet
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|it
init|=
name|prefixSet
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|String
name|sparqlPrefix
init|=
literal|""
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|prefix
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
try|try
block|{
name|String
name|uri
init|=
name|prefixMap
operator|.
name|get
argument_list|(
name|prefix
argument_list|)
decl_stmt|;
name|uri
operator|=
name|uri
operator|.
name|replace
argument_list|(
literal|"\\"
argument_list|,
literal|"/"
argument_list|)
expr_stmt|;
name|sparqlPrefix
operator|+=
literal|"PREFIX "
operator|+
name|prefix
operator|+
literal|":<"
operator|+
operator|(
operator|new
name|URI
argument_list|(
name|uri
argument_list|)
operator|.
name|toString
argument_list|()
operator|)
operator|+
literal|">"
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|sparqlPrefix
return|;
block|}
specifier|public
specifier|static
name|ResultSet
name|executeQuery
parameter_list|(
name|String
name|querySPARQL
parameter_list|)
block|{
name|Query
name|query
init|=
name|QueryFactory
operator|.
name|create
argument_list|(
name|querySPARQL
argument_list|)
decl_stmt|;
return|return
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|getModel
argument_list|()
argument_list|)
operator|.
name|execSelect
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|ResultSet
name|executeQuery
parameter_list|(
name|Model
name|model
parameter_list|,
name|String
name|querySPARQL
parameter_list|)
block|{
name|Query
name|query
init|=
name|QueryFactory
operator|.
name|create
argument_list|(
name|querySPARQL
argument_list|)
decl_stmt|;
return|return
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|model
argument_list|)
operator|.
name|execSelect
argument_list|()
return|;
block|}
block|}
end_class

end_unit

