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
name|enhancer
operator|.
name|engines
operator|.
name|htmlextractor
operator|.
name|impl
package|;
end_package

begin_comment
comment|/**  *<code>InitializationException</code> is thrown when an initialization step  * fails.  *  * @author Joerg Steffen, DFKI  * @version $Id: InitializationException.java 1068358 2011-02-08 12:58:11Z bdelacretaz $  */
end_comment

begin_class
specifier|public
class|class
name|InitializationException
extends|extends
name|Exception
block|{
comment|/**      * This creates a new instance of<code>InitializationException</code> with      * null as its detail message. The cause is not initialized.      */
specifier|public
name|InitializationException
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * This creates a new instance of<code>InitializationException</code> with      * the given detail message. The cause is not initialized.      *      * @param message      *            a<code>String</code> with the detail message      */
specifier|public
name|InitializationException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/**      * This creates a new instance of<code>InitializationException</code> with      * the specified cause and a detail message of (cause==null ? null :      * cause.toString()) (which typically contains the class and detail message      * of cause).      *      * @param cause      *            a<code>Throwable</code> with the cause of the exception      *            (which is saved for later retrieval by the {@link #getCause()}      *            method). (A<tt>null</tt> value is permitted, and indicates      *            that the cause is nonexistent or unknown.)      */
specifier|public
name|InitializationException
parameter_list|(
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|cause
argument_list|)
expr_stmt|;
block|}
comment|/**      * This creates a new instance of<code>InitializationException</code> with      * the given detail message and the given cause.      *      * @param message      *            a<code>String</code> with the detail message      * @param cause      *            a<code>Throwable</code> with the cause of the exception      *            (which is saved for later retrieval by the {@link #getCause()}      *            method). (A<tt>null</tt> value is permitted, and indicates      *            that the cause is nonexistent or unknown.)      */
specifier|public
name|InitializationException
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

