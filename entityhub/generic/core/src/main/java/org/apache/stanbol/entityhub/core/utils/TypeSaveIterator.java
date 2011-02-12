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
name|Iterator
import|;
end_import

begin_comment
comment|/**  * Filters elements of the base Iterator base on the generic type of this one  * @author Rupert Westenthaler  *  * @param<T> the type of elements returned by this iterator  */
end_comment

begin_class
specifier|public
class|class
name|TypeSaveIterator
parameter_list|<
name|T
parameter_list|>
extends|extends
name|AdaptingIterator
argument_list|<
name|Object
argument_list|,
name|T
argument_list|>
implements|implements
name|Iterator
argument_list|<
name|T
argument_list|>
block|{
comment|//    protected final Iterator<?> it;
comment|//    protected final Class<T> type;
comment|//    private T next;
comment|//    private Boolean hasNext;
comment|/**      * Constructs an iterator that selects only elements of the parsed iterator      * that are assignable to the parse type      * @param it the base iterator      * @param type the type all elements of this Iterator need to be assignable to.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|TypeSaveIterator
parameter_list|(
name|Iterator
argument_list|<
name|?
argument_list|>
name|it
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
name|super
argument_list|(
operator|(
name|Iterator
argument_list|<
name|Object
argument_list|>
operator|)
name|it
argument_list|,
operator|new
name|AssignableFormAdapter
argument_list|<
name|T
argument_list|>
argument_list|()
argument_list|,
name|type
argument_list|)
expr_stmt|;
comment|//        if(it == null){
comment|//            throw new IllegalArgumentException("Parsed iterator MUST NOT be NULL!");
comment|//        }
comment|//        if(type == null){
comment|//            throw new IllegalArgumentException("Parsed type MUST NOT be NULL!");
comment|//        }
comment|//        this.it = it;
comment|//        this.type = type;
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
comment|//    public final T next() {
comment|//        hasNext(); //call hasNext (to init next Element if not already done)
comment|//        if(!hasNext){
comment|//            throw new NoSuchElementException();
comment|//        } else {
comment|//            T current = next;
comment|//            next = null;
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
comment|//    @SuppressWarnings("unchecked")
comment|//    protected T prepareNext(){
comment|//        Object check;
comment|//        while(it.hasNext()){
comment|//            check = it.next();
comment|//            if(type.isAssignableFrom(check.getClass())){
comment|//                return (T)check;
comment|//            }
comment|//        }
comment|//        return null;
comment|//    }
comment|/**      * Adapter implementation that uses {@link Class#isAssignableFrom(Class)}      * to check whether a value can be casted to the requested type      */
specifier|private
specifier|static
class|class
name|AssignableFormAdapter
parameter_list|<
name|T
parameter_list|>
implements|implements
name|Adapter
argument_list|<
name|Object
argument_list|,
name|T
argument_list|>
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Override
specifier|public
name|T
name|adapt
parameter_list|(
name|Object
name|value
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|.
name|isAssignableFrom
argument_list|(
name|value
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
return|return
operator|(
name|T
operator|)
name|value
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
block|}
end_class

end_unit

