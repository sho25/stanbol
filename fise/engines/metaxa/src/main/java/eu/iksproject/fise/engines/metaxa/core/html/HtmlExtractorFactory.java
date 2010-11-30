begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|engines
operator|.
name|metaxa
operator|.
name|core
operator|.
name|html
package|;
end_package

begin_import
import|import
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|Extractor
import|;
end_import

begin_comment
comment|/**  * HtmlExtractorFactory.java  *   * @author<a href="mailto:kasper@dfki.de">Walter Kasper</a>  *   */
end_comment

begin_class
specifier|public
class|class
name|HtmlExtractorFactory
extends|extends
name|org
operator|.
name|semanticdesktop
operator|.
name|aperture
operator|.
name|extractor
operator|.
name|html
operator|.
name|HtmlExtractorFactory
block|{
comment|/** {@inheritDoc} */
annotation|@
name|Override
specifier|public
name|Extractor
name|get
parameter_list|()
block|{
return|return
operator|new
name|IksHtmlExtractor
argument_list|()
return|;
block|}
block|}
end_class

end_unit

