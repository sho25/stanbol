begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|Properties
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
name|Property
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|AnalysedText
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
name|enhancer
operator|.
name|nlp
operator|.
name|model
operator|.
name|AnalysedTextFactory
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|Blob
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
name|enhancer
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|ContentItemHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

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
argument_list|(
name|value
operator|=
name|AnalysedTextFactory
operator|.
name|class
argument_list|)
annotation|@
name|Properties
argument_list|(
name|value
operator|=
block|{
annotation|@
name|Property
argument_list|(
name|name
operator|=
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|,
name|intValue
operator|=
name|Integer
operator|.
name|MIN_VALUE
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|AnalysedTextFactoryImpl
extends|extends
name|AnalysedTextFactory
block|{
annotation|@
name|Override
specifier|public
name|AnalysedText
name|createAnalysedText
parameter_list|(
name|Blob
name|blob
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|text
init|=
name|ContentItemHelper
operator|.
name|getText
argument_list|(
name|blob
argument_list|)
decl_stmt|;
return|return
operator|new
name|AnalysedTextImpl
argument_list|(
name|blob
argument_list|,
name|text
argument_list|)
return|;
block|}
block|}
end_class

end_unit
