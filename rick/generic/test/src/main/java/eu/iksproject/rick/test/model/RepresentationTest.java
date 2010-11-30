begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|test
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
name|Representation
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|RepresentationTest
block|{
comment|/** 	 * Subclasses implement this method to provide implementation instances of 	 * {@link Representation}. This method may be called an arbitrary amount of time, 	 * independently whether previously returned MGraph are still in use or not. 	 * 	 * @return an empty {@link Representation} of the implementation to be tested 	 */
specifier|protected
specifier|abstract
name|Representation
name|getEmptyRepresentation
parameter_list|()
function_decl|;
comment|//TODO: Write the tests!
block|}
end_class

end_unit

