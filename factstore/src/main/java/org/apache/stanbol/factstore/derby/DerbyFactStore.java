begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|factstore
operator|.
name|derby
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
name|DatabaseMetaData
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|DriverManager
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
name|java
operator|.
name|sql
operator|.
name|Statement
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
name|HashMap
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
name|commons
operator|.
name|codec
operator|.
name|binary
operator|.
name|Base64
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|factstore
operator|.
name|api
operator|.
name|FactStore
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
name|factstore
operator|.
name|model
operator|.
name|Fact
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
name|factstore
operator|.
name|model
operator|.
name|FactSchema
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|component
operator|.
name|ComponentContext
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

begin_comment
comment|/**  * Implementation of the FactStore interface based on an Apache Derby relational database.  *   * @author Fabian Christ  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|)
annotation|@
name|Service
specifier|public
class|class
name|DerbyFactStore
implements|implements
name|FactStore
block|{
specifier|private
specifier|static
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|DerbyFactStore
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|int
name|MAX_FACTSCHEMAURN_LENGTH
init|=
literal|96
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CreateTableFactSchemata
init|=
literal|"CREATE TABLE factschemata ( id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT factschema_id PRIMARY KEY, name VARCHAR(128) NOT NULL )"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|CreateTableFactRoles
init|=
literal|"CREATE TABLE factroles ( id INT GENERATED ALWAYS AS IDENTITY CONSTRAINT factrole_id PRIMARY KEY, factschema_id INT NOT NULL CONSTRAINT factschema_foreign_key REFERENCES factschemata ON DELETE CASCADE ON UPDATE RESTRICT, name VARCHAR(128) NOT NULL, type VARCHAR(512) NOT NULL )"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DB_URL
init|=
literal|"jdbc:derby:factstore;create=true"
decl_stmt|;
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|cc
parameter_list|)
throws|throws
name|Exception
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Activating FactStore..."
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Connecting to Derby DB {}"
argument_list|,
name|DB_URL
argument_list|)
expr_stmt|;
name|Connection
name|con
init|=
literal|null
decl_stmt|;
try|try
block|{
name|con
operator|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|DB_URL
argument_list|)
expr_stmt|;
if|if
condition|(
name|con
operator|!=
literal|null
condition|)
block|{
name|logger
operator|.
name|info
argument_list|(
literal|"Derby connection established."
argument_list|)
expr_stmt|;
try|try
block|{
if|if
condition|(
operator|!
name|existsTable
argument_list|(
literal|"factschemata"
argument_list|,
name|con
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|sqls
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|sqls
operator|.
name|add
argument_list|(
name|CreateTableFactSchemata
argument_list|)
expr_stmt|;
name|sqls
operator|.
name|add
argument_list|(
name|CreateTableFactRoles
argument_list|)
expr_stmt|;
name|this
operator|.
name|executeUpdate
argument_list|(
name|sqls
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Created FactStore meta tables."
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error creating meta data tables"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Derby DB error. Can't activate."
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|con
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|con
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
comment|/* ignore */
block|}
block|}
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"FactStore activated."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getMaxFactSchemaURNLength
parameter_list|()
block|{
return|return
name|MAX_FACTSCHEMAURN_LENGTH
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|existsFactSchema
parameter_list|(
name|String
name|factSchemaURN
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|factSchemaB64
init|=
name|Base64
operator|.
name|encodeBase64URLSafeString
argument_list|(
name|factSchemaURN
operator|.
name|getBytes
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|tableExists
init|=
literal|false
decl_stmt|;
name|Connection
name|con
init|=
literal|null
decl_stmt|;
try|try
block|{
name|con
operator|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|DB_URL
argument_list|)
expr_stmt|;
name|tableExists
operator|=
name|this
operator|.
name|existsTable
argument_list|(
name|factSchemaB64
argument_list|,
name|con
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error checking table existence"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|con
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|con
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
comment|/* ignore */
block|}
block|}
block|}
return|return
name|tableExists
return|;
block|}
specifier|private
name|boolean
name|existsTable
parameter_list|(
name|String
name|tableName
parameter_list|,
name|Connection
name|con
parameter_list|)
throws|throws
name|Exception
block|{
name|boolean
name|exists
init|=
literal|false
decl_stmt|;
name|ResultSet
name|res
init|=
literal|null
decl_stmt|;
try|try
block|{
name|con
operator|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|DB_URL
argument_list|)
expr_stmt|;
name|DatabaseMetaData
name|meta
init|=
name|con
operator|.
name|getMetaData
argument_list|()
decl_stmt|;
name|res
operator|=
name|meta
operator|.
name|getTables
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"TABLE"
block|}
argument_list|)
expr_stmt|;
while|while
condition|(
name|res
operator|.
name|next
argument_list|()
condition|)
block|{
if|if
condition|(
name|res
operator|.
name|getString
argument_list|(
literal|"TABLE_NAME"
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
name|tableName
argument_list|)
condition|)
block|{
name|exists
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error while reading tables' metadata to check if table '{}' exists"
argument_list|,
name|tableName
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error while reading tables' metadata"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
name|res
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|/* ignore */
block|}
block|}
return|return
name|exists
return|;
block|}
annotation|@
name|Override
specifier|public
name|FactSchema
name|getFactSchema
parameter_list|(
name|String
name|factSchemaURN
parameter_list|)
block|{
name|FactSchema
name|factSchema
init|=
literal|null
decl_stmt|;
name|Connection
name|con
init|=
literal|null
decl_stmt|;
try|try
block|{
name|con
operator|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|DB_URL
argument_list|)
expr_stmt|;
name|factSchema
operator|=
name|loadFactSchema
argument_list|(
name|factSchemaURN
argument_list|,
name|con
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error while loading fact schema"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|factSchema
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
name|con
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|/* ignore */
block|}
block|}
return|return
name|factSchema
return|;
block|}
specifier|private
name|FactSchema
name|loadFactSchema
parameter_list|(
name|String
name|factSchemaURN
parameter_list|,
name|Connection
name|con
parameter_list|)
throws|throws
name|Exception
block|{
name|FactSchema
name|factSchema
init|=
literal|null
decl_stmt|;
name|PreparedStatement
name|ps
init|=
literal|null
decl_stmt|;
name|ResultSet
name|rs
init|=
literal|null
decl_stmt|;
try|try
block|{
name|String
name|selectFactSchema
init|=
literal|"SELECT factschemata.name AS schemaURN, factroles.name AS role, factroles.type AS type FROM factroles JOIN factschemata ON ( factschemata.id = factroles.factschema_id ) WHERE factschemata.name = ?"
decl_stmt|;
name|ps
operator|=
name|con
operator|.
name|prepareStatement
argument_list|(
name|selectFactSchema
argument_list|)
expr_stmt|;
name|ps
operator|.
name|setString
argument_list|(
literal|1
argument_list|,
name|factSchemaURN
argument_list|)
expr_stmt|;
name|rs
operator|=
name|ps
operator|.
name|executeQuery
argument_list|()
expr_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
while|while
condition|(
name|rs
operator|.
name|next
argument_list|()
condition|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|factSchema
operator|=
operator|new
name|FactSchema
argument_list|()
expr_stmt|;
name|factSchema
operator|.
name|setFactSchemaURN
argument_list|(
name|rs
operator|.
name|getString
argument_list|(
literal|"schemaURN"
argument_list|)
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
name|String
name|typeFromDB
init|=
name|rs
operator|.
name|getString
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
name|String
index|[]
name|types
init|=
name|typeFromDB
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
if|if
condition|(
name|types
operator|.
name|length
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|String
name|type
range|:
name|types
control|)
block|{
name|factSchema
operator|.
name|addRole
argument_list|(
name|rs
operator|.
name|getString
argument_list|(
literal|"role"
argument_list|)
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|factSchema
operator|.
name|addRole
argument_list|(
name|rs
operator|.
name|getString
argument_list|(
literal|"role"
argument_list|)
argument_list|,
name|typeFromDB
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error while selecting fact schema meta data"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|rs
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|rs
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
comment|/* ignore */
block|}
block|}
if|if
condition|(
name|ps
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|ps
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
comment|/* ignore */
block|}
block|}
block|}
return|return
name|factSchema
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|createFactSchema
parameter_list|(
name|FactSchema
name|factSchema
parameter_list|)
throws|throws
name|Exception
block|{
comment|// TODO Implement roll back behavior (transaction)
name|String
name|factSchemaB64
init|=
name|Base64
operator|.
name|encodeBase64URLSafeString
argument_list|(
name|factSchema
operator|.
name|getFactSchemaURN
argument_list|()
operator|.
name|getBytes
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|createFactSchemaTable
init|=
name|this
operator|.
name|toSQLfromSchema
argument_list|(
name|factSchemaB64
argument_list|,
name|factSchema
argument_list|)
decl_stmt|;
name|Connection
name|con
init|=
literal|null
decl_stmt|;
try|try
block|{
name|con
operator|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|DB_URL
argument_list|)
expr_stmt|;
name|this
operator|.
name|executeUpdate
argument_list|(
name|createFactSchemaTable
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|this
operator|.
name|insertFactSchemaMetadata
argument_list|(
name|factSchema
argument_list|,
name|con
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error while creating fact schema"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
name|con
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|/* ignore */
block|}
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Fact schema {} created as {}"
argument_list|,
name|factSchema
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|,
name|factSchemaB64
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|insertFactSchemaMetadata
parameter_list|(
name|FactSchema
name|factSchema
parameter_list|,
name|Connection
name|con
parameter_list|)
throws|throws
name|Exception
block|{
name|PreparedStatement
name|ps
init|=
literal|null
decl_stmt|;
try|try
block|{
name|String
name|insertFactSchema
init|=
literal|"INSERT INTO factschemata (name) VALUES ( ? )"
decl_stmt|;
name|ps
operator|=
name|con
operator|.
name|prepareStatement
argument_list|(
name|insertFactSchema
argument_list|,
name|PreparedStatement
operator|.
name|RETURN_GENERATED_KEYS
argument_list|)
expr_stmt|;
name|ps
operator|.
name|setString
argument_list|(
literal|1
argument_list|,
name|factSchema
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|)
expr_stmt|;
name|ps
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
name|ResultSet
name|rs
init|=
name|ps
operator|.
name|getGeneratedKeys
argument_list|()
decl_stmt|;
name|int
name|factSchemaId
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|rs
operator|.
name|next
argument_list|()
condition|)
block|{
name|factSchemaId
operator|=
name|rs
operator|.
name|getInt
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|factSchemaId
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Could not obtain fact schema ID after insert"
argument_list|)
throw|;
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Inserted new fact schema {} with ID {}"
argument_list|,
name|factSchema
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|,
name|factSchemaId
argument_list|)
expr_stmt|;
name|String
name|insertFactRoles
init|=
literal|"INSERT INTO factroles (factschema_id, name, type) VALUES ( ?, ?, ? )"
decl_stmt|;
name|ps
operator|=
name|con
operator|.
name|prepareStatement
argument_list|(
name|insertFactRoles
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|role
range|:
name|factSchema
operator|.
name|getRoles
argument_list|()
control|)
block|{
name|ps
operator|.
name|setInt
argument_list|(
literal|1
argument_list|,
name|factSchemaId
argument_list|)
expr_stmt|;
name|ps
operator|.
name|setString
argument_list|(
literal|2
argument_list|,
name|role
argument_list|)
expr_stmt|;
name|StringBuilder
name|typeList
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
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
name|factSchema
operator|.
name|getTypesOfRole
argument_list|(
name|role
argument_list|)
control|)
block|{
if|if
condition|(
operator|!
name|first
condition|)
block|{
name|typeList
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
name|typeList
operator|.
name|append
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
name|ps
operator|.
name|setString
argument_list|(
literal|3
argument_list|,
name|typeList
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|ps
operator|.
name|addBatch
argument_list|()
expr_stmt|;
block|}
name|ps
operator|.
name|executeBatch
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error while inserting fact schema meta data"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|ps
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|ps
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
comment|/* ignore */
block|}
block|}
block|}
block|}
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|toSQLfromSchema
parameter_list|(
name|String
name|factSchemaB64
parameter_list|,
name|FactSchema
name|factSchema
parameter_list|)
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|String
argument_list|>
name|sqls
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|// TODO Add SQL command for index creation
name|StringBuilder
name|createTableSQL
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"CREATE TABLE "
argument_list|)
decl_stmt|;
name|createTableSQL
operator|.
name|append
argument_list|(
name|factSchemaB64
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
name|createTableSQL
operator|.
name|append
argument_list|(
literal|'('
argument_list|)
expr_stmt|;
name|createTableSQL
operator|.
name|append
argument_list|(
literal|"id INT GENERATED ALWAYS AS IDENTITY"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|role
range|:
name|factSchema
operator|.
name|getRoles
argument_list|()
control|)
block|{
name|createTableSQL
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|createTableSQL
operator|.
name|append
argument_list|(
name|role
argument_list|)
expr_stmt|;
name|createTableSQL
operator|.
name|append
argument_list|(
literal|" VARCHAR(1024)"
argument_list|)
expr_stmt|;
block|}
name|createTableSQL
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
name|sqls
operator|.
name|add
argument_list|(
name|createTableSQL
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sqls
return|;
block|}
specifier|private
name|void
name|executeUpdate
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|sqls
parameter_list|,
name|Connection
name|con
parameter_list|)
throws|throws
name|Exception
block|{
for|for
control|(
name|String
name|sql
range|:
name|sqls
control|)
block|{
name|int
name|res
init|=
operator|-
literal|1
decl_stmt|;
name|Statement
name|statement
init|=
literal|null
decl_stmt|;
try|try
block|{
name|statement
operator|=
name|con
operator|.
name|createStatement
argument_list|()
expr_stmt|;
name|res
operator|=
name|statement
operator|.
name|executeUpdate
argument_list|(
name|sql
argument_list|)
expr_stmt|;
if|if
condition|(
name|res
operator|<
literal|0
condition|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Negative result after executing SQL '{}'"
argument_list|,
name|sql
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Negative result after executing SQL"
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"Error executing SQL '{}'"
argument_list|,
name|sql
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error executing SQL"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
name|statement
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|/* ignore */
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|addFact
parameter_list|(
name|Fact
name|fact
parameter_list|)
throws|throws
name|Exception
block|{
name|Connection
name|con
init|=
literal|null
decl_stmt|;
try|try
block|{
name|con
operator|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|DB_URL
argument_list|)
expr_stmt|;
name|this
operator|.
name|addFact
argument_list|(
name|fact
argument_list|,
name|con
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error while inserting new fact"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
name|con
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|/* ignore */
block|}
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Fact created for {}"
argument_list|,
name|fact
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|addFact
parameter_list|(
name|Fact
name|fact
parameter_list|,
name|Connection
name|con
parameter_list|)
throws|throws
name|Exception
block|{
name|FactSchema
name|factSchema
init|=
name|this
operator|.
name|loadFactSchema
argument_list|(
name|fact
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|,
name|con
argument_list|)
decl_stmt|;
if|if
condition|(
name|factSchema
operator|!=
literal|null
condition|)
block|{
name|String
name|factSchemaB64
init|=
name|Base64
operator|.
name|encodeBase64URLSafeString
argument_list|(
name|fact
operator|.
name|getFactSchemaURN
argument_list|()
operator|.
name|getBytes
argument_list|()
argument_list|)
decl_stmt|;
name|StringBuilder
name|insertSB
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"INSERT INTO "
argument_list|)
operator|.
name|append
argument_list|(
name|factSchemaB64
argument_list|)
operator|.
name|append
argument_list|(
literal|'('
argument_list|)
decl_stmt|;
name|StringBuilder
name|valueSB
init|=
operator|new
name|StringBuilder
argument_list|(
literal|" VALUES ("
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|roleIndexMap
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|firstRole
init|=
literal|true
decl_stmt|;
name|int
name|roleIndex
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|role
range|:
name|factSchema
operator|.
name|getRoles
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|firstRole
condition|)
block|{
name|insertSB
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|valueSB
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
block|}
name|insertSB
operator|.
name|append
argument_list|(
name|role
argument_list|)
expr_stmt|;
name|valueSB
operator|.
name|append
argument_list|(
literal|'?'
argument_list|)
expr_stmt|;
name|firstRole
operator|=
literal|false
expr_stmt|;
name|roleIndex
operator|++
expr_stmt|;
name|roleIndexMap
operator|.
name|put
argument_list|(
name|role
argument_list|,
name|roleIndex
argument_list|)
expr_stmt|;
block|}
name|insertSB
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
operator|.
name|append
argument_list|(
name|valueSB
argument_list|)
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
name|PreparedStatement
name|ps
init|=
literal|null
decl_stmt|;
try|try
block|{
name|ps
operator|=
name|con
operator|.
name|prepareStatement
argument_list|(
name|insertSB
operator|.
name|toString
argument_list|()
argument_list|,
name|PreparedStatement
operator|.
name|RETURN_GENERATED_KEYS
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|role
range|:
name|fact
operator|.
name|getRoles
argument_list|()
control|)
block|{
name|Integer
name|roleIdx
init|=
name|roleIndexMap
operator|.
name|get
argument_list|(
name|role
argument_list|)
decl_stmt|;
if|if
condition|(
name|roleIdx
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Unknown role '"
operator|+
name|role
operator|+
literal|"' for fact schema "
operator|+
name|fact
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|)
throw|;
block|}
else|else
block|{
name|ps
operator|.
name|setString
argument_list|(
name|roleIdx
argument_list|,
name|role
argument_list|)
expr_stmt|;
block|}
block|}
name|ps
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
name|ResultSet
name|rs
init|=
name|ps
operator|.
name|getGeneratedKeys
argument_list|()
decl_stmt|;
name|int
name|factId
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|rs
operator|.
name|next
argument_list|()
condition|)
block|{
name|factId
operator|=
name|rs
operator|.
name|getInt
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|factId
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Could not obtain fact ID after insert"
argument_list|)
throw|;
block|}
name|logger
operator|.
name|info
argument_list|(
literal|"Inserted new fact with ID {} into fact schema table {}"
argument_list|,
name|factId
argument_list|,
name|factSchemaB64
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error while writing fact into database"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|ps
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|ps
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
comment|/* ignore */
block|}
block|}
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Unknown fact schema "
operator|+
name|fact
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|addFacts
parameter_list|(
name|Set
argument_list|<
name|Fact
argument_list|>
name|factSet
parameter_list|)
throws|throws
name|Exception
block|{
comment|// TODO Improve roll back behavior if single fact of set could not be committed
name|Connection
name|con
init|=
literal|null
decl_stmt|;
try|try
block|{
name|con
operator|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|DB_URL
argument_list|)
expr_stmt|;
for|for
control|(
name|Fact
name|fact
range|:
name|factSet
control|)
block|{
name|this
operator|.
name|addFact
argument_list|(
name|fact
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|logger
operator|.
name|info
argument_list|(
literal|"Fact created for {}"
argument_list|,
name|fact
operator|.
name|getFactSchemaURN
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Error while inserting new facts"
argument_list|,
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
try|try
block|{
name|con
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|/* ignore */
block|}
block|}
block|}
block|}
end_class

end_unit

