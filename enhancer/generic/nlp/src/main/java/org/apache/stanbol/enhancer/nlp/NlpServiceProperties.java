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
name|enhancer
operator|.
name|servicesapi
operator|.
name|ServiceProperties
import|;
end_import

begin_interface
specifier|public
interface|interface
name|NlpServiceProperties
extends|extends
name|ServiceProperties
block|{
comment|/**      * Property Key used by NLP engines to provide their {@link NlpProcessingRole}      */
name|String
name|ENHANCEMENT_ENGINE_NLP_ROLE
init|=
literal|"org.apache.stanbol.enhancer.engine.nlp.role"
decl_stmt|;
block|}
end_interface

end_unit

