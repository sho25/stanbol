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
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_class
specifier|public
class|class
name|DBS_L1_OWL
block|{
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
comment|/**<p>The namespace of the vocabulary as a string</p>      *  @see #NS */
specifier|public
specifier|static
name|String
name|getURI
parameter_list|()
block|{
return|return
name|NS
return|;
block|}
specifier|public
specifier|static
specifier|final
name|IRI
name|RDF_TYPE
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
operator|+
literal|"type"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|RDFS_LABEL
init|=
name|IRI
operator|.
name|create
argument_list|(
literal|"http://www.w3.org/2000/01/rdf-schema#"
operator|+
literal|"label"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|NAMESPACE
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|bindToColumn
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"bindToColumn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasColumn
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasColumn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isColumnOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isColumnOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasDatum
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasDatum"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isDatumOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isDatumOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasRow
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasRow"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isRowOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isRowOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isComposedBy
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isComposedBy"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|composes
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"composes"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasKey
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isKeyOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isKeyOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasPrimaryKey
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasPrimaryKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isPrimaryKeyOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isPrimaryKeyOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasForeignKey
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasForeignKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isForeignKeyOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isForeignKeyOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasSQLType
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasSQLType"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasTable
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasTable"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isTableOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isTableOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isBoundBy
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isBoundBy"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isJoinedBy
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isJoinedBy"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|joinsOn
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"joinsOn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isDumped
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isDumped"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasContent
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasContent"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasName
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasName"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasPhysicalName
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasPhysicalName"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasJDBCDriver
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasJDBCDriver"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasJDBCDns
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasJDBCDns"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasUsername
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasUsername"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasPassword
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasPassword"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasPrimaryKeyMember
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasPrimaryKeyMember"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isPrimaryKeyMemberOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isPrimaryKeyMemberOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|hasForeignKeyMember
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"hasForeignKeyMember"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|isForeignKeyMemberOf
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"isForeignKeyMemberOf"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|Datum
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"Datum"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|DatabaseConnection
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"DatabaseConnection"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|DataObject
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"DataObject"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|SchemaObject
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"SchemaObject"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|Record
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"Record"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|Row
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"Row"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|Column
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"Column"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|Key
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"Key"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|PrimaryKey
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"PrimaryKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|ForeignKey
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"ForeignKey"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|Table
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"Table"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|NotNullableColumn
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"NotNullableColumn"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|IRI
name|NullableColumn
init|=
name|IRI
operator|.
name|create
argument_list|(
name|NS
operator|+
literal|"NullableColumn"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

