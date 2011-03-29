begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|kres
operator|.
name|jersey
package|;
end_package

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
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|ws
operator|.
name|rs
operator|.
name|core
operator|.
name|Application
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
name|kres
operator|.
name|jersey
operator|.
name|processors
operator|.
name|ViewProcessorImpl
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
name|kres
operator|.
name|jersey
operator|.
name|resource
operator|.
name|DocumentationResource
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
name|kres
operator|.
name|jersey
operator|.
name|resource
operator|.
name|RESTfulResource
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
name|kres
operator|.
name|jersey
operator|.
name|resource
operator|.
name|RootResource
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
name|kres
operator|.
name|jersey
operator|.
name|writers
operator|.
name|GraphWriter
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
name|kres
operator|.
name|jersey
operator|.
name|writers
operator|.
name|OWLOntologyWriter
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
name|kres
operator|.
name|jersey
operator|.
name|writers
operator|.
name|ResultSetWriter
import|;
end_import

begin_comment
comment|/**  * Statically define the list of available resources and providers to be used by the KReS JAX-RS Endpoint.  *   * The jersey auto-scan mechanism does not seem to work when deployed through OSGi's HttpService  * initialization.  *   * In the future this class might get refactored as an OSGi service to allow for dynamic configuration and  * deployment of additional JAX-RS resources and providers.  *   * @author andrea.nuzzolese  */
end_comment

begin_class
specifier|public
class|class
name|JerseyEndpointApplication
extends|extends
name|Application
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|getClasses
parameter_list|()
block|{
name|Set
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|classes
init|=
operator|new
name|HashSet
argument_list|<
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
comment|// resources
name|classes
operator|.
name|add
argument_list|(
name|RootResource
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|DocumentationResource
operator|.
name|class
argument_list|)
expr_stmt|;
comment|/* REST services */
name|classes
operator|.
name|add
argument_list|(
name|RESTfulResource
operator|.
name|class
argument_list|)
expr_stmt|;
comment|/* end rest services */
comment|// message body writers
name|classes
operator|.
name|add
argument_list|(
name|GraphWriter
operator|.
name|class
argument_list|)
expr_stmt|;
name|classes
operator|.
name|add
argument_list|(
name|ResultSetWriter
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// classes.add(OwlModelWriter.class);
name|classes
operator|.
name|add
argument_list|(
name|OWLOntologyWriter
operator|.
name|class
argument_list|)
expr_stmt|;
return|return
name|classes
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Object
argument_list|>
name|getSingletons
parameter_list|()
block|{
name|Set
argument_list|<
name|Object
argument_list|>
name|singletons
init|=
operator|new
name|HashSet
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
comment|// view processors
name|singletons
operator|.
name|add
argument_list|(
operator|new
name|ViewProcessorImpl
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|singletons
return|;
block|}
block|}
end_class

end_unit

