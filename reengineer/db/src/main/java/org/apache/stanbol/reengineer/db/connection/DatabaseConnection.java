begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reengineer
operator|.
name|db
operator|.
name|connection
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Connection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|PreparedStatement
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
name|access
operator|.
name|TcManager
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
name|vocabulary
operator|.
name|RDF
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
name|reengineer
operator|.
name|db
operator|.
name|vocab
operator|.
name|DBS_L1
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
name|owl
operator|.
name|trasformation
operator|.
name|JenaToOwlConvert
import|;
end_import

begin_class
specifier|public
class|class
name|DatabaseConnection
block|{
specifier|private
name|OWLOntology
name|schemaOntology
decl_stmt|;
specifier|private
name|Connection
name|connection
decl_stmt|;
specifier|private
name|java
operator|.
name|sql
operator|.
name|Statement
name|stmt
decl_stmt|;
specifier|private
name|PreparedStatement
name|preparedStatement
decl_stmt|;
specifier|public
name|DatabaseConnection
parameter_list|(
name|OWLOntology
name|schemaOntology
parameter_list|)
block|{
name|this
operator|.
name|schemaOntology
operator|=
name|schemaOntology
expr_stmt|;
block|}
specifier|public
name|void
name|openDBConnection
parameter_list|()
block|{
name|String
name|sparql
init|=
literal|"SELECT ?dbName ?jdbcDNS ?jdbcDriver ?username ?password "
operator|+
literal|"WHERE ?db<"
operator|+
name|RDF
operator|.
name|type
operator|.
name|getURI
argument_list|()
operator|+
literal|"><"
operator|+
name|DBS_L1
operator|.
name|DatabaseConnection
operator|.
name|toString
argument_list|()
operator|+
literal|"> . "
operator|+
literal|"?db<"
operator|+
name|DBS_L1
operator|.
name|hasPhysicalName
operator|.
name|toString
argument_list|()
operator|+
literal|"> ?dbName . "
operator|+
literal|"?db<"
operator|+
name|DBS_L1
operator|.
name|hasJDBCDns
operator|.
name|toString
argument_list|()
operator|+
literal|"> ?jdbcDNS . "
operator|+
literal|"?db<"
operator|+
name|DBS_L1
operator|.
name|hasJDBCDriver
operator|.
name|toString
argument_list|()
operator|+
literal|"> ?jdbcDriver . "
operator|+
literal|"?db<"
operator|+
name|DBS_L1
operator|.
name|hasUsername
operator|.
name|toString
argument_list|()
operator|+
literal|"> ?username . "
operator|+
literal|"?db<"
operator|+
name|DBS_L1
operator|.
name|hasPassword
operator|.
name|toString
argument_list|()
operator|+
literal|"> ?password "
decl_stmt|;
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
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|QuerySolution
name|qs
init|=
name|jenaRs
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|jdbcDNS
init|=
name|qs
operator|.
name|getLiteral
argument_list|(
literal|"jdbcDNS"
argument_list|)
operator|.
name|getLexicalForm
argument_list|()
decl_stmt|;
name|String
name|jdbcDriver
init|=
name|qs
operator|.
name|getLiteral
argument_list|(
literal|"jdbcDriver"
argument_list|)
operator|.
name|getLexicalForm
argument_list|()
decl_stmt|;
name|String
name|username
init|=
name|qs
operator|.
name|getLiteral
argument_list|(
literal|"username"
argument_list|)
operator|.
name|getLexicalForm
argument_list|()
decl_stmt|;
name|String
name|password
init|=
name|qs
operator|.
name|getLiteral
argument_list|(
literal|"password"
argument_list|)
operator|.
name|getLexicalForm
argument_list|()
decl_stmt|;
if|if
condition|(
name|jdbcDNS
operator|!=
literal|null
operator|&&
name|username
operator|!=
literal|null
operator|&&
name|password
operator|!=
literal|null
operator|&&
name|jdbcDriver
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|Class
operator|.
name|forName
argument_list|(
name|jdbcDriver
argument_list|)
expr_stmt|;
name|connection
operator|=
name|java
operator|.
name|sql
operator|.
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|jdbcDNS
argument_list|,
name|username
argument_list|,
name|password
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
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
catch|catch
parameter_list|(
name|SQLException
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
block|}
block|}
specifier|public
name|ResultSet
name|executeQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
try|try
block|{
name|openDBConnection
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|preparedStatement
operator|=
name|connection
operator|.
name|prepareStatement
argument_list|(
name|query
argument_list|)
expr_stmt|;
return|return
name|preparedStatement
operator|.
name|executeQuery
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|synchronized
name|ResultSet
name|executeQuerySafeMemory
parameter_list|(
name|String
name|query
parameter_list|)
block|{
try|try
block|{
name|openDBConnection
argument_list|()
expr_stmt|;
name|stmt
operator|=
name|connection
operator|.
name|createStatement
argument_list|(
name|java
operator|.
name|sql
operator|.
name|ResultSet
operator|.
name|TYPE_FORWARD_ONLY
argument_list|,
name|java
operator|.
name|sql
operator|.
name|ResultSet
operator|.
name|CONCUR_READ_ONLY
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Integer.MIN_VALUE: "
operator|+
name|Integer
operator|.
name|MIN_VALUE
argument_list|)
expr_stmt|;
name|stmt
operator|.
name|setFetchSize
argument_list|(
name|Integer
operator|.
name|MIN_VALUE
argument_list|)
expr_stmt|;
return|return
name|stmt
operator|.
name|executeQuery
argument_list|(
name|query
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|void
name|closeDBConnection
parameter_list|()
block|{
try|try
block|{
name|connection
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
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
specifier|public
name|void
name|closeStatement
parameter_list|()
block|{
try|try
block|{
name|stmt
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
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
specifier|public
name|void
name|closePreparedStatement
parameter_list|()
block|{
try|try
block|{
name|preparedStatement
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
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
block|}
end_class

end_unit

