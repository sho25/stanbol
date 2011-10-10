begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|explanation
operator|.
name|impl
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
name|site
operator|.
name|ReferencedSiteManager
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
name|explanation
operator|.
name|api
operator|.
name|SchemaMatcher
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
name|ontologymanager
operator|.
name|registry
operator|.
name|api
operator|.
name|model
operator|.
name|Library
import|;
end_import

begin_comment
comment|/**  * A {@link SchemaMatcher} that uses the Apache Stanbol Entity Hub as a knowledge base connector.  */
end_comment

begin_interface
specifier|public
interface|interface
name|EntityHubSchemaMatcher
extends|extends
name|SchemaMatcher
argument_list|<
name|URI
argument_list|,
name|ReferencedSiteManager
argument_list|,
name|Library
argument_list|>
block|{  }
end_interface

end_unit

