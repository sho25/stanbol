begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|semion
operator|.
name|reengineer
operator|.
name|db
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
name|sql
operator|.
name|ResultSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
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
name|Hashtable
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
name|Literal
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
name|LiteralFactory
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
name|access
operator|.
name|TcManager
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
name|TripleImpl
import|;
end_import

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
name|AddAxiom
import|;
end_import

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
name|OWLClass
import|;
end_import

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
name|OWLClassAssertionAxiom
import|;
end_import

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
name|OWLDataFactory
import|;
end_import

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
name|OWLDataProperty
import|;
end_import

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
name|OWLIndividual
import|;
end_import

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
name|OWLLiteral
import|;
end_import

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
name|OWLObjectProperty
import|;
end_import

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
name|OWLOntology
import|;
end_import

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
name|OWLOntologyCreationException
import|;
end_import

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
name|OWLOntologyManager
import|;
end_import

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
name|OWLOntologyRenameException
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
name|ontology
operator|.
name|OntModel
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
name|QueryExecution
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
name|QuerySolution
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
name|Resource
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
name|vocabulary
operator|.
name|RDF
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|manager
operator|.
name|KReSONManager
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|semion
operator|.
name|ReengineeringException
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|api
operator|.
name|semion
operator|.
name|util
operator|.
name|SemionUriRefGenerator
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|ontologies
operator|.
name|DBS_L1
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|ontologies
operator|.
name|DBS_L1_OWL
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|semion
operator|.
name|reengineer
operator|.
name|db
operator|.
name|connection
operator|.
name|DatabaseConnection
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|shared
operator|.
name|transformation
operator|.
name|JenaToOwlConvert
import|;
end_import

begin_class
specifier|public
class|class
name|SemionDBDataTransformer
extends|extends
name|SemionUriRefGenerator
block|{
specifier|private
name|KReSONManager
name|onManager
decl_stmt|;
specifier|private
name|OWLOntology
name|schemaOntology
decl_stmt|;
specifier|private
name|DatabaseConnection
name|databaseConnection
decl_stmt|;
specifier|public
name|SemionDBDataTransformer
parameter_list|(
name|KReSONManager
name|onManager
parameter_list|,
name|OWLOntology
name|schemaOntology
parameter_list|)
block|{
name|this
operator|.
name|onManager
operator|=
name|onManager
expr_stmt|;
name|this
operator|.
name|schemaOntology
operator|=
name|schemaOntology
expr_stmt|;
name|this
operator|.
name|databaseConnection
operator|=
operator|new
name|DatabaseConnection
argument_list|(
name|schemaOntology
argument_list|)
expr_stmt|;
block|}
specifier|public
name|OWLOntology
name|transformData
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|ontologyIRI
parameter_list|)
throws|throws
name|ReengineeringException
block|{
name|OWLOntology
name|dataOntology
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|schemaOntology
operator|!=
literal|null
condition|)
block|{
name|OWLOntologyManager
name|manager
init|=
name|onManager
operator|.
name|getOwlCacheManager
argument_list|()
decl_stmt|;
name|OWLDataFactory
name|factory
init|=
name|onManager
operator|.
name|getOwlFactory
argument_list|()
decl_stmt|;
name|graphNS
operator|=
name|graphNS
operator|.
name|replace
argument_list|(
literal|"#"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|String
name|schemaNS
init|=
name|graphNS
operator|+
literal|"/schema#"
decl_stmt|;
if|if
condition|(
name|ontologyIRI
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|dataOntology
operator|=
name|manager
operator|.
name|createOntology
argument_list|(
name|ontologyIRI
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ReengineeringException
argument_list|()
throw|;
block|}
block|}
else|else
block|{
try|try
block|{
name|dataOntology
operator|=
name|manager
operator|.
name|createOntology
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OWLOntologyCreationException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ReengineeringException
argument_list|()
throw|;
block|}
block|}
name|graphNS
operator|+=
literal|"#"
expr_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|ArrayList
argument_list|<
name|DBColumn
argument_list|>
argument_list|>
name|tableColumnHash
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|ArrayList
argument_list|<
name|DBColumn
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|OWLClass
name|databaseConnectionClass
init|=
name|factory
operator|.
name|getOWLClass
argument_list|(
name|DBS_L1_OWL
operator|.
name|DatabaseConnection
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|databaseConnections
init|=
name|databaseConnectionClass
operator|.
name|getIndividuals
argument_list|(
name|schemaOntology
argument_list|)
decl_stmt|;
if|if
condition|(
name|databaseConnections
operator|!=
literal|null
operator|&&
name|databaseConnections
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|ArrayList
argument_list|<
name|DBColumn
argument_list|>
name|columnList
init|=
operator|new
name|ArrayList
argument_list|<
name|DBColumn
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLIndividual
name|dbConnection
range|:
name|databaseConnections
control|)
block|{
name|OWLObjectProperty
name|hasTable
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|DBS_L1_OWL
operator|.
name|hasTable
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|tables
init|=
name|dbConnection
operator|.
name|getObjectPropertyValues
argument_list|(
name|hasTable
argument_list|,
name|schemaOntology
argument_list|)
decl_stmt|;
for|for
control|(
name|OWLIndividual
name|tableIndividual
range|:
name|tables
control|)
block|{
name|OWLDataProperty
name|hasName
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|DBS_L1_OWL
operator|.
name|hasName
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLLiteral
argument_list|>
name|tableNames
init|=
name|tableIndividual
operator|.
name|getDataPropertyValues
argument_list|(
name|hasName
argument_list|,
name|schemaOntology
argument_list|)
decl_stmt|;
name|String
name|tableName
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|tableNames
operator|!=
literal|null
operator|&&
name|tableNames
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
for|for
control|(
name|OWLLiteral
name|tableNameLiteral
range|:
name|tableNames
control|)
block|{
name|tableName
operator|=
name|tableNameLiteral
operator|.
name|getLiteral
argument_list|()
expr_stmt|;
block|}
block|}
name|OWLObjectProperty
name|hasColumn
init|=
name|factory
operator|.
name|getOWLObjectProperty
argument_list|(
name|DBS_L1_OWL
operator|.
name|hasColumn
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|tableColumns
init|=
name|tableIndividual
operator|.
name|getObjectPropertyValues
argument_list|(
name|hasColumn
argument_list|,
name|schemaOntology
argument_list|)
decl_stmt|;
if|if
condition|(
name|tableColumns
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|OWLIndividual
name|tableColumn
range|:
name|tableColumns
control|)
block|{
name|String
name|columnName
init|=
literal|null
decl_stmt|;
name|String
name|columnType
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|OWLLiteral
argument_list|>
name|columnNameLiterals
init|=
name|tableColumn
operator|.
name|getDataPropertyValues
argument_list|(
name|hasName
argument_list|,
name|schemaOntology
argument_list|)
decl_stmt|;
if|if
condition|(
name|columnNameLiterals
operator|!=
literal|null
operator|&&
name|columnNameLiterals
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
for|for
control|(
name|OWLLiteral
name|columnNameLiteral
range|:
name|columnNameLiterals
control|)
block|{
name|columnName
operator|=
name|columnNameLiteral
operator|.
name|getLiteral
argument_list|()
expr_stmt|;
block|}
block|}
name|OWLDataProperty
name|hasSQLType
init|=
name|factory
operator|.
name|getOWLDataProperty
argument_list|(
name|DBS_L1_OWL
operator|.
name|hasSQLType
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|OWLLiteral
argument_list|>
name|columnTypeLiterals
init|=
name|tableColumn
operator|.
name|getDataPropertyValues
argument_list|(
name|hasSQLType
argument_list|,
name|schemaOntology
argument_list|)
decl_stmt|;
if|if
condition|(
name|columnTypeLiterals
operator|!=
literal|null
operator|&&
name|columnNameLiterals
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
for|for
control|(
name|OWLLiteral
name|columnTypeLiteral
range|:
name|columnNameLiterals
control|)
block|{
name|columnType
operator|=
name|columnTypeLiteral
operator|.
name|getLiteral
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|columnName
operator|!=
literal|null
operator|&&
name|columnType
operator|!=
literal|null
condition|)
block|{
name|columnList
operator|.
name|add
argument_list|(
operator|new
name|DBColumn
argument_list|(
name|columnName
argument_list|,
name|columnType
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|tableColumnHash
operator|.
name|put
argument_list|(
name|tableName
argument_list|,
name|columnList
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|tableNames
init|=
name|tableColumnHash
operator|.
name|keySet
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|tableName
range|:
name|tableNames
control|)
block|{
name|databaseConnection
operator|.
name|openDBConnection
argument_list|()
expr_stmt|;
name|String
name|sqlQuery
init|=
literal|"SELECT * FROM "
operator|+
name|tableName
decl_stmt|;
name|ResultSet
name|resultSetSQL
init|=
name|databaseConnection
operator|.
name|executeQuerySafeMemory
argument_list|(
name|sqlQuery
argument_list|)
decl_stmt|;
try|try
block|{
for|for
control|(
name|int
name|z
init|=
literal|0
init|;
name|resultSetSQL
operator|.
name|next
argument_list|()
condition|;
name|z
operator|++
control|)
block|{
name|IRI
name|recordIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
operator|+
name|tableName
operator|+
literal|"_record_"
operator|+
name|z
argument_list|)
decl_stmt|;
name|OWLClassAssertionAxiom
name|record
init|=
name|createOWLClassAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|Row
argument_list|,
name|recordIRI
argument_list|)
decl_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|record
argument_list|)
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|foreignKeys
init|=
operator|new
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|notForeignKeys
init|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|primaryKeys
init|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|notPrimaryKeys
init|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|DBColumn
argument_list|>
name|dbColumns
init|=
name|tableColumnHash
operator|.
name|get
argument_list|(
name|tableName
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|,
name|k
init|=
name|dbColumns
operator|.
name|size
argument_list|()
init|;
name|j
operator|<
name|k
condition|;
name|j
operator|++
control|)
block|{
name|DBColumn
name|dbColumn
init|=
name|dbColumns
operator|.
name|get
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|String
name|dbColumnName
init|=
name|dbColumn
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|dbColumnSQLType
init|=
name|dbColumn
operator|.
name|getSqlType
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|resultSetSQL
operator|.
name|getString
argument_list|(
name|dbColumnName
argument_list|)
decl_stmt|;
name|IRI
name|valueResIRI
decl_stmt|;
name|String
name|content
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|primaryKeys
operator|.
name|contains
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|j
argument_list|)
argument_list|)
condition|)
block|{
name|valueResIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
operator|+
name|tableName
operator|+
literal|"_"
operator|+
name|dbColumnName
operator|+
literal|"_"
operator|+
name|value
argument_list|)
expr_stmt|;
name|OWLClassAssertionAxiom
name|valueRes
init|=
name|createOWLClassAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|Datum
argument_list|,
name|valueResIRI
argument_list|)
decl_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|valueRes
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|notPrimaryKeys
operator|.
name|contains
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|j
argument_list|)
argument_list|)
condition|)
block|{
name|valueResIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
operator|+
name|tableName
operator|+
literal|"_record_"
operator|+
name|z
operator|+
literal|"_datum_"
operator|+
name|j
argument_list|)
expr_stmt|;
name|OWLClassAssertionAxiom
name|valueRes
init|=
name|createOWLClassAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|Datum
argument_list|,
name|valueResIRI
argument_list|)
decl_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|valueRes
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JenaToOwlConvert
name|jenaToOwlConvert
init|=
operator|new
name|JenaToOwlConvert
argument_list|()
decl_stmt|;
name|OntModel
name|ontModel
init|=
name|jenaToOwlConvert
operator|.
name|ModelOwlToJenaConvert
argument_list|(
name|schemaOntology
argument_list|,
literal|"RDF/XML"
argument_list|)
decl_stmt|;
name|String
name|sparql
init|=
literal|"SELECT ?c WHERE { "
operator|+
literal|"?c<"
operator|+
name|DBS_L1
operator|.
name|hasName
operator|+
literal|"> \""
operator|+
name|tableName
operator|+
literal|"-column_"
operator|+
name|dbColumnName
operator|+
literal|"\"^^xsd:string . "
operator|+
literal|"?c<"
operator|+
name|DBS_L1
operator|.
name|isPrimaryKeyMemberOf
operator|+
literal|"> ?p "
operator|+
literal|"}"
decl_stmt|;
name|Query
name|sparqlQuery
init|=
name|QueryFactory
operator|.
name|create
argument_list|(
name|sparql
argument_list|)
decl_stmt|;
name|QueryExecution
name|qexec
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|sparqlQuery
argument_list|,
name|ontModel
argument_list|)
decl_stmt|;
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
name|jenaRs
init|=
name|qexec
operator|.
name|execSelect
argument_list|()
decl_stmt|;
if|if
condition|(
name|jenaRs
operator|!=
literal|null
operator|&&
name|jenaRs
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|valueResIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
operator|+
name|tableName
operator|+
literal|"_"
operator|+
name|dbColumnName
operator|+
literal|"_"
operator|+
name|value
argument_list|)
expr_stmt|;
name|OWLClassAssertionAxiom
name|valueRes
init|=
name|createOWLClassAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|Datum
argument_list|,
name|valueResIRI
argument_list|)
decl_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|valueRes
argument_list|)
argument_list|)
expr_stmt|;
name|primaryKeys
operator|.
name|add
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|j
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|valueResIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
operator|+
name|tableName
operator|+
literal|"_record_"
operator|+
name|z
operator|+
literal|"_datum_"
operator|+
name|j
argument_list|)
expr_stmt|;
name|OWLClassAssertionAxiom
name|valueRes
init|=
name|createOWLClassAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|Datum
argument_list|,
name|valueResIRI
argument_list|)
decl_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|valueRes
argument_list|)
argument_list|)
expr_stmt|;
name|notPrimaryKeys
operator|.
name|add
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|j
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|IRI
name|joinIRI
decl_stmt|;
name|String
name|joinName
decl_stmt|;
if|if
condition|(
operator|(
name|joinName
operator|=
name|foreignKeys
operator|.
name|get
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|j
argument_list|)
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|joinIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
operator|+
name|tableName
operator|+
literal|"_"
operator|+
name|joinName
operator|+
literal|"_"
operator|+
name|value
argument_list|)
expr_stmt|;
name|OWLClassAssertionAxiom
name|join
init|=
name|createOWLClassAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|Datum
argument_list|,
name|joinIRI
argument_list|)
decl_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|join
argument_list|)
argument_list|)
expr_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|createOWLObjectPropertyAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|hasContent
argument_list|,
name|valueResIRI
argument_list|,
name|joinIRI
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|notForeignKeys
operator|.
name|contains
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|j
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|createOWLDataPropertyAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|hasContent
argument_list|,
name|valueResIRI
argument_list|,
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|sparql
operator|=
literal|"SELECT ?p ?n WHERE { "
operator|+
literal|"?c<"
operator|+
name|DBS_L1
operator|.
name|hasName
operator|+
literal|"> \""
operator|+
name|tableName
operator|+
literal|"-column_"
operator|+
name|dbColumnName
operator|+
literal|"\"^^xsd:string . "
operator|+
literal|"?c<"
operator|+
name|DBS_L1
operator|.
name|joinsOn
operator|+
literal|"> ?p . "
operator|+
literal|"?p<"
operator|+
name|DBS_L1
operator|.
name|hasName
operator|+
literal|"> ?n "
operator|+
literal|"}"
expr_stmt|;
name|Query
name|sparqlQuery2
init|=
name|QueryFactory
operator|.
name|create
argument_list|(
name|sparql
argument_list|)
decl_stmt|;
name|QueryExecution
name|qexec2
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|sparqlQuery2
argument_list|,
name|ontModel
argument_list|)
decl_stmt|;
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
name|jenaRs2
init|=
name|qexec
operator|.
name|execSelect
argument_list|()
decl_stmt|;
if|if
condition|(
name|jenaRs2
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|qs
init|=
name|jenaRs2
operator|.
name|next
argument_list|()
decl_stmt|;
name|Resource
name|joinColumn
init|=
name|qs
operator|.
name|getResource
argument_list|(
literal|"?p"
argument_list|)
decl_stmt|;
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
name|Literal
name|joinNameNode
init|=
name|qs
operator|.
name|getLiteral
argument_list|(
literal|"?n"
argument_list|)
decl_stmt|;
name|joinName
operator|=
name|joinNameNode
operator|.
name|getLexicalForm
argument_list|()
expr_stmt|;
name|joinIRI
operator|=
name|IRI
operator|.
name|create
argument_list|(
name|graphNS
operator|+
name|joinName
operator|+
literal|"_datum_"
operator|+
name|value
argument_list|)
expr_stmt|;
name|OWLClassAssertionAxiom
name|join
init|=
name|createOWLClassAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|Datum
argument_list|,
name|joinIRI
argument_list|)
decl_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|join
argument_list|)
argument_list|)
expr_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|createOWLObjectPropertyAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|hasContent
argument_list|,
name|valueResIRI
argument_list|,
name|joinIRI
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|foreignKeys
operator|.
name|put
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|j
argument_list|)
argument_list|,
name|joinName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|notForeignKeys
operator|.
name|add
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|j
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
literal|"null"
argument_list|)
condition|)
block|{
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|createOWLDataPropertyAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|hasContent
argument_list|,
name|valueResIRI
argument_list|,
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|createOWLObjectPropertyAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|hasDatum
argument_list|,
name|recordIRI
argument_list|,
name|valueResIRI
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|IRI
name|tableIRI
init|=
name|IRI
operator|.
name|create
argument_list|(
name|schemaNS
operator|+
literal|"table_"
operator|+
name|tableName
argument_list|)
decl_stmt|;
name|manager
operator|.
name|applyChange
argument_list|(
operator|new
name|AddAxiom
argument_list|(
name|dataOntology
argument_list|,
name|createOWLObjectPropertyAssertionAxiom
argument_list|(
name|factory
argument_list|,
name|DBS_L1_OWL
operator|.
name|isRowOf
argument_list|,
name|recordIRI
argument_list|,
name|tableIRI
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|OWLOntologyRenameException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ReengineeringException
argument_list|()
throw|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ReengineeringException
argument_list|()
throw|;
block|}
block|}
block|}
return|return
name|dataOntology
return|;
block|}
block|}
end_class

begin_class
class|class
name|DBColumn
block|{
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|sqlType
decl_stmt|;
specifier|public
name|DBColumn
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|sqlType
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|sqlType
operator|=
name|sqlType
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|String
name|getSqlType
parameter_list|()
block|{
return|return
name|sqlType
return|;
block|}
block|}
end_class

end_unit

