begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
package|;
end_package

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_comment
comment|/**  * A Sign links three things together  *<ol>  *<li>the<b>signifier</b> (ID) used to identify the Sign  *<li>the<b>description</b> (Representation) for the signified entity  *<li>the<b>organisation</b> (Site) that acts as origin of the Sign  *</ol>  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Sign
block|{
comment|/** 	 * Enumeration over the different types of Signs defined by the RICK 	 * @author Rupert Westenthaler 	 * 	 */
specifier|public
specifier|static
enum|enum
name|SignTypeEnum
block|{
comment|/** 		 * The Sign - the default - type 		 */
name|Sign
argument_list|(
name|RdfResourceEnum
operator|.
name|Sign
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**  		 *  Symbols are Signs defined by this Rick instance 		 */
name|Symbol
argument_list|(
name|RdfResourceEnum
operator|.
name|Symbol
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/** 		 * EntityMappings are signs that do map Signs defined/managed by referenced 		 * Sites to Symbols. 		 */
name|EntityMapping
argument_list|(
name|RdfResourceEnum
operator|.
name|EntityMapping
operator|.
name|getUri
argument_list|()
argument_list|)
block|, 		;
specifier|private
name|String
name|uri
decl_stmt|;
name|SignTypeEnum
parameter_list|(
name|String
name|uri
parameter_list|)
block|{ 			 		}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|uri
return|;
block|}
block|}
comment|/** 	 * The default type of a {@link Sign} (set to {@link SignTypeEnum#Sign}) 	 */
name|SignTypeEnum
name|DEFAULT_SIGN_TYPE
init|=
name|SignTypeEnum
operator|.
name|Sign
decl_stmt|;
comment|/** 	 * The id (signifier) of this  sign. 	 * @return the id 	 */
name|String
name|getId
parameter_list|()
function_decl|;
name|String
name|SIGN_SITE
init|=
name|RdfResourceEnum
operator|.
name|signSite
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/** 	 * Getter for the id of the referenced Site that defines/manages this sign.<br> 	 * Note that the Rick allows that different referenced Sites  	 * provide representations for the same id ({@link Sign#getId()}).  	 * Therefore there may be different entity instances of {@link Sign} with  	 * the same id but different representations.<br> 	 * In other word different referenced Sites may manage representations by 	 * using the same id.<br> 	 * Note also, that the RICK assumes that all such representations 	 * are equivalent and interchangeable. Therefore Methods that searches for 	 * Entities on different Sites will return the first hit without searching 	 * for any others. 	 * @return the site of this Sign 	 */
name|String
name|getSignSite
parameter_list|()
function_decl|;
comment|//	/**
comment|//	 * Getter for the type of a sign. Subclasses may restrict values of this
comment|//	 * property. (e.g. {@link #getType()} for {@link Symbol} always returns
comment|//	 * {@link SignTypeEnum#Symbol})
comment|//	 * @return the type
comment|//	 */
comment|//	SignTypeEnum getType();
comment|/** 	 * Getter for the {@link Representation} of that sign as defined/managed by the site 	 * @return the representation 	 */
name|Representation
name|getRepresentation
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

