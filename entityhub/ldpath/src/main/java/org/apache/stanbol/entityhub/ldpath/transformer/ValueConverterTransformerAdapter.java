begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|ldpath
operator|.
name|transformer
package|;
end_package

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
name|core
operator|.
name|mapping
operator|.
name|ValueConverterFactory
operator|.
name|ValueConverter
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
name|core
operator|.
name|model
operator|.
name|InMemoryValueFactory
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
name|model
operator|.
name|Reference
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
name|model
operator|.
name|Text
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
name|model
operator|.
name|ValueFactory
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|LDPath
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|backend
operator|.
name|RDFBackend
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|api
operator|.
name|transformers
operator|.
name|NodeTransformer
import|;
end_import

begin_comment
comment|/**  * LDPath {@link NodeTransformer} internally using the Entityhub   * {@link ValueConverter}.<p>  * This transformer should be used for plain literals and references (xsd:anyURI)  * to ensure that nodes are transformed to {@link Text} and {@link Reference}  * instances.<p>  * Users should use {@link LDPathUtils#createAndInitLDPath(RDFBackend, ValueFactory)}  * to ensure that {@link LDPath} instances are configured accordingly.  *    * @author Rupert Westenthaler.  * @see LDPathUtils#createAndInitLDPath(RDFBackend, ValueFactory)  * @param<T>  */
end_comment

begin_class
specifier|public
class|class
name|ValueConverterTransformerAdapter
parameter_list|<
name|T
parameter_list|>
implements|implements
name|NodeTransformer
argument_list|<
name|T
argument_list|,
name|Object
argument_list|>
block|{
specifier|private
specifier|final
name|ValueFactory
name|vf
decl_stmt|;
specifier|private
specifier|final
name|ValueConverter
argument_list|<
name|T
argument_list|>
name|vc
decl_stmt|;
specifier|public
name|ValueConverterTransformerAdapter
parameter_list|(
name|ValueConverter
argument_list|<
name|T
argument_list|>
name|vc
parameter_list|,
name|ValueFactory
name|vf
parameter_list|)
block|{
name|this
operator|.
name|vf
operator|=
name|vf
operator|==
literal|null
condition|?
name|InMemoryValueFactory
operator|.
name|getInstance
argument_list|()
else|:
name|vf
expr_stmt|;
name|this
operator|.
name|vc
operator|=
name|vc
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|T
name|transform
parameter_list|(
name|RDFBackend
argument_list|<
name|Object
argument_list|>
name|backend
parameter_list|,
name|Object
name|node
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|T
name|value
init|=
name|vc
operator|.
name|convert
argument_list|(
name|node
argument_list|,
name|vf
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|value
operator|=
name|vc
operator|.
name|convert
argument_list|(
name|backend
operator|.
name|stringValue
argument_list|(
name|node
argument_list|)
argument_list|,
name|vf
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to transform node '"
operator|+
name|node
operator|+
literal|"' to data type '"
operator|+
name|vc
operator|.
name|getDataType
argument_list|()
operator|+
literal|"'!"
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|value
return|;
block|}
block|}
block|}
end_class

end_unit
