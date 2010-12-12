begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|yard
operator|.
name|solr
operator|.
name|query
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|rick
operator|.
name|yard
operator|.
name|solr
operator|.
name|model
operator|.
name|IndexField
import|;
end_import

begin_comment
comment|/**  * Interface used to encode constraints for a logical {@link IndexField}.<p>  * Typically a single constraint in a query needs to converted to several  * constraints in the index. Do give some examples let's assume, that we are  * interested in documents that contains "life" in there English title.  * This constraint would be represented by the following constraints in the  * index<ul>  *<li> A {@link IndexConstraintTypeEnum#LANG} set to be English  *<li> A {@link IndexConstraintTypeEnum#FIELD} set to "dc:title" (dublin core)  *<li> A {@link IndexConstraintTypeEnum#WILDCARD} set to life* or alternatively  *<li> {@link IndexConstraintTypeEnum#EQ} set to life.  *</ul>  * In addition it can be the case that for encoding one kind of constraint one  * needs also an other constraint to be present. e.g. when encoding a range  * constraint one needs always to have both the upper and lower bound to be  * present. Because of that implementations of this interface can define there  * {@link #dependsOn()} other {@link IndexConstraintTypeEnum} to be present.  * Such types are than added with the default constraint<code>null</code> if  * missing. Implementations can indicate with {@link #supportsDefault()} if  * they are able  to encode the constraint type when set to the default value  *<code>null</code>.<p>  * Finally different constraints need different types of values to be parsed.  * Therefore this interface uses a generic type and the {@link #acceptsValueType()}  * method can be used to check if the type of the parsed value is compatible  * with the implementation registered for the current  * {@link IndexConstraintTypeEnum}.<p>  * Please note that implementations of this interface need to be thread save,  * because typically there will only be one instance that is shared for all  * encoding taks.<p>  * TODO: Currently there is no generic implementation that implements the  * processing steps described here. However the SolrQueryFactory provides a  * Solr specific implementation.  * @author Rupert Westenthaler  * @param T the type of supported values!  */
end_comment

begin_interface
specifier|public
interface|interface
name|IndexConstraintTypeEncoder
parameter_list|<
name|T
parameter_list|>
block|{
comment|/**      * Encodes the parsed value and adds it to the IndexQueryConstraint.<p>      * If the encoder supports default values (meaning that      * {@link #supportsDefault()} returns<code>true</code>, than parsing      *<code>null</code> as value MUST result in returning the      * default value. Otherwise the encode method might throw an      * {@link IllegalArgumentException} if<code>null</code> is parsed.<p>      * Please note that<code>null</code> might also be a valid parameter even      * if an Encoder does not support a default value!      * @param value the value for the constraint      * @throws IllegalArgumentException if the parsed value is not supported.      * Especially if<code>null</code> is parsed and the implementation does not      * {@link #supportsDefault()}!      */
name|void
name|encode
parameter_list|(
name|EncodedConstraintParts
name|constraint
parameter_list|,
name|T
name|value
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
comment|/**      * Returns<code>true</code> if this Encoder supports a default value. This      * would be e.g. an open ended upper or lower bound for range constraints or      * any language for text constraints.      * @return if the encoder supports a default encoding if no constraint of that      * type is provided by the query.      */
name|boolean
name|supportsDefault
parameter_list|()
function_decl|;
comment|/**      * A set of other Constraints that need to be present, that the encoding provided      * by this encoder results in a valid query constraint. e.g. A range constraint      * always need a lower and an upper bound. So even if the query only defines      * an upper bound, than there MUST BE also a open ended lower bound encoded to      * result in a valid query constraint.      * @return set of other index constraint types that must be present. If none      * return {@link Collections#emptySet()}. This Method should return an      * unmodifiable collection (e.g. {@link Arrays#asList(Object...)} or using      * {@link Collections#unmodifiableCollection(Collection)})      */
name|Collection
argument_list|<
name|IndexConstraintTypeEnum
argument_list|>
name|dependsOn
parameter_list|()
function_decl|;
comment|/**      * The type of the constraint this implementation encodes      * @return the type of the constraints encoded by this implementation      */
name|IndexConstraintTypeEnum
name|encodes
parameter_list|()
function_decl|;
comment|/**      * Getter for the type of values this encoder accepts.      * @return the generic type of the index constraint type encoder      */
name|Class
argument_list|<
name|T
argument_list|>
name|acceptsValueType
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

