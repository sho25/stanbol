begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one  * or more contributor license agreements.  See the NOTICE file  * distributed with this work for additional information  * regarding copyright ownership.  The ASF licenses this file  * to you under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance  * with the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing,  * software distributed under the License is distributed on an  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY  * KIND, either express or implied.  See the License for the  * specific language governing permissions and limitations  * under the License.  */
end_comment

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
name|security
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|MessageDigest
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|NoSuchAlgorithmException
import|;
end_import

begin_comment
comment|/**  * Utility methods for converting passwords.  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|PasswordUtil
block|{
comment|/**     * Restrict instantiation     */
specifier|private
name|PasswordUtil
parameter_list|()
block|{}
specifier|private
specifier|static
specifier|final
name|char
index|[]
name|HEXDIGITS
init|=
literal|"0123456789abcdef"
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
comment|/** 	 * @param bytes 	 *            array of bytes to be converted to a String of hexadecimal 	 *            numbers 	 * @return String of hexadecimal numbers representing the byte array 	 */
specifier|public
specifier|static
name|String
name|bytes2HexString
parameter_list|(
name|byte
index|[]
name|bytes
parameter_list|)
block|{
name|char
index|[]
name|result
init|=
operator|new
name|char
index|[
name|bytes
operator|.
name|length
operator|<<
literal|1
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|j
init|=
literal|0
init|;
name|i
operator|<
name|bytes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|result
index|[
name|j
operator|++
index|]
operator|=
name|HEXDIGITS
index|[
name|bytes
index|[
name|i
index|]
operator|>>
literal|4
operator|&
literal|0xF
index|]
expr_stmt|;
name|result
index|[
name|j
operator|++
index|]
operator|=
name|HEXDIGITS
index|[
name|bytes
index|[
name|i
index|]
operator|&
literal|0xF
index|]
expr_stmt|;
block|}
return|return
operator|new
name|String
argument_list|(
name|result
argument_list|)
return|;
block|}
comment|/** 	 * Encrypt the password with the SHA1 algorithm 	 * 	 * @param password 	 * @return the converted passwort as String 	 */
specifier|public
specifier|static
name|String
name|convertPassword
parameter_list|(
name|String
name|password
parameter_list|)
block|{
try|try
block|{
return|return
name|bytes2HexString
argument_list|(
name|MessageDigest
operator|.
name|getInstance
argument_list|(
literal|"SHA1"
argument_list|)
operator|.
name|digest
argument_list|(
name|password
operator|.
name|getBytes
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchAlgorithmException
name|nsae
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|()
throw|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|uee
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|()
throw|;
block|}
block|}
block|}
end_class

end_unit

