begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|writers
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
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|InputSource
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
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

begin_class
specifier|public
class|class
name|JenaModelTransformer
block|{
specifier|public
name|String
name|toText
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
name|OutputStream
name|outputStream
init|=
name|getStringOutputStream
argument_list|()
decl_stmt|;
name|model
operator|.
name|write
argument_list|(
name|outputStream
argument_list|)
expr_stmt|;
return|return
name|outputStream
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|Document
name|toDocument
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
name|Document
name|dom
init|=
literal|null
decl_stmt|;
name|OutputStream
name|outputStream
init|=
name|getStringOutputStream
argument_list|()
decl_stmt|;
name|model
operator|.
name|write
argument_list|(
name|outputStream
argument_list|)
expr_stmt|;
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|DocumentBuilder
name|builder
decl_stmt|;
try|try
block|{
name|builder
operator|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
expr_stmt|;
name|InputSource
name|is
init|=
operator|new
name|InputSource
argument_list|(
operator|new
name|StringReader
argument_list|(
name|outputStream
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|dom
operator|=
name|builder
operator|.
name|parse
argument_list|(
name|is
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
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
name|SAXException
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
name|IOException
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
return|return
name|dom
return|;
block|}
specifier|private
name|OutputStream
name|getStringOutputStream
parameter_list|()
block|{
name|OutputStream
name|outputStream
init|=
operator|new
name|OutputStream
argument_list|()
block|{
specifier|private
name|StringBuilder
name|string
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|write
parameter_list|(
name|int
name|b
parameter_list|)
throws|throws
name|IOException
block|{
name|this
operator|.
name|string
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|b
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|this
operator|.
name|string
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
decl_stmt|;
return|return
name|outputStream
return|;
block|}
block|}
end_class

end_unit

