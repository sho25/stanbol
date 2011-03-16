begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

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
name|core
operator|.
name|utils
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
name|Collections
import|;
end_import

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
name|Iterator
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

begin_class
specifier|public
class|class
name|TextIterator
extends|extends
name|AdaptingIterator
argument_list|<
name|Object
argument_list|,
name|Text
argument_list|>
implements|implements
name|Iterator
argument_list|<
name|Text
argument_list|>
block|{
comment|/**      * Creates an instance that iterates over values and returns {@link Text}      * instances that confirm to the active languages. If no languages are parsed      * or<code>null</code> is parsed as a language, this Iterator also creates      * and returns {@link Text} instances for {@link String} values.      * @param valueFactory the factory used to create text instances for String values      * @param it the iterator      * @param languages The active languages or no values to accept all languages      */
specifier|public
name|TextIterator
parameter_list|(
name|ValueFactory
name|valueFactory
parameter_list|,
name|Iterator
argument_list|<
name|Object
argument_list|>
name|it
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
name|super
argument_list|(
name|it
argument_list|,
operator|new
name|TextAdapter
argument_list|(
name|valueFactory
argument_list|,
name|languages
argument_list|)
argument_list|,
name|Text
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
class|class
name|TextAdapter
implements|implements
name|Adapter
argument_list|<
name|Object
argument_list|,
name|Text
argument_list|>
block|{
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|languages
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|isNullLanguage
decl_stmt|;
specifier|private
specifier|final
name|ValueFactory
name|valueFactory
decl_stmt|;
specifier|public
name|TextAdapter
parameter_list|(
name|ValueFactory
name|valueFactory
parameter_list|,
name|String
modifier|...
name|languages
parameter_list|)
block|{
if|if
condition|(
name|valueFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Parsed ValueFactory MUST NOT be NULL!"
argument_list|)
throw|;
block|}
name|this
operator|.
name|valueFactory
operator|=
name|valueFactory
expr_stmt|;
if|if
condition|(
name|languages
operator|!=
literal|null
operator|&&
name|languages
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|this
operator|.
name|languages
operator|=
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|languages
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|isNullLanguage
operator|=
name|this
operator|.
name|languages
operator|.
name|contains
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|languages
operator|=
literal|null
expr_stmt|;
name|isNullLanguage
operator|=
literal|true
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Text
name|adapt
parameter_list|(
name|Object
name|value
parameter_list|,
name|Class
argument_list|<
name|Text
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|Text
condition|)
block|{
name|Text
name|text
init|=
operator|(
name|Text
operator|)
name|value
decl_stmt|;
if|if
condition|(
name|languages
operator|==
literal|null
operator|||
name|languages
operator|.
name|contains
argument_list|(
name|text
operator|.
name|getLanguage
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|text
return|;
block|}
else|else
block|{
comment|//language does not fit -> filter
return|return
literal|null
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|isNullLanguage
operator|&&
name|value
operator|instanceof
name|String
condition|)
block|{
return|return
name|valueFactory
operator|.
name|createText
argument_list|(
operator|(
name|String
operator|)
name|value
argument_list|)
return|;
block|}
else|else
block|{
comment|//type does not fit -> filter
return|return
literal|null
return|;
block|}
block|}
block|}
comment|//    @Override
comment|//    public final void remove() {
comment|//        /*
comment|//         * TODO: Any Iterator that filters elements of the underlying Iterator
comment|//         * needs to call Iterator#next() in the underlying Iterator to get the
comment|//         * next element that confirms with the filter.
comment|//         * However the Iterator#remove() is defined as removing the last element
comment|//         * to be returned by Iterator#next(). Therefore calling hasNext would
comment|//         * change the element to be removed by this method.
comment|//         * Currently I do not know a way around that but I would also like to
comment|//         * keep the remove functionality for Iterator that filter elements of an
comment|//         * underlying Iterator. To prevent unpredictable behaviour in such cases
comment|//         * I throw an IllegalStateException in such cases.
comment|//         * This decision assumes, that in most usage scenarios hasNext will not
comment|//         * likely be called before calling remove and even in such cases
comment|//         * it will be most likely be possible to refactor the code to confirm
comment|//         * with this restriction.
comment|//         * I hope this will help developers that encounter this exception to
comment|//         * modify there code!
comment|//         * If someone has a better Idea how to solve this please let me know!
comment|//         * best
comment|//         * Rupert Westenthaler
comment|//         */
comment|//        if(hasNext!= null){
comment|//            throw new IllegalStateException("Remove can not be called after calling hasNext() because this Method needs to call next() on the underlying Interator and therefore would change the element to be removed :(");
comment|//        }
comment|//        it.remove();
comment|//    }
comment|//
comment|//    @Override
comment|//    public final Text next() {
comment|//        hasNext(); //call hasNext (to init next Element if not already done)
comment|//        if(!hasNext){
comment|//            throw new NoSuchElementException();
comment|//        } else {
comment|//            Text current = next;
comment|//            hasNext = null;
comment|//            return current;
comment|//        }
comment|//    }
comment|//
comment|//    @Override
comment|//    public final boolean hasNext() {
comment|//        if(hasNext == null){ // only once even with multiple calls
comment|//            next = prepareNext();
comment|//            hasNext = next != null;
comment|//        }
comment|//        return hasNext;
comment|//    }
comment|//    protected Text prepareNext(){
comment|//        Object check;
comment|//        while(it.hasNext()){
comment|//            check = it.next();
comment|//            if(check instanceof Text){
comment|//                Text text = (Text)check;
comment|//                if(languages == null || languages.contains(text.getLanguage())){
comment|//                    return text;
comment|//                }
comment|//            } else if(isNullLanguage&& check instanceof String){
comment|//                return valueFactory.createText((String)check);
comment|//            } //type does not fit -> ignore
comment|//        }
comment|//        //no more element and still nothing found ... return end of iteration
comment|//        return null;
comment|//    }
block|}
end_class

end_unit

