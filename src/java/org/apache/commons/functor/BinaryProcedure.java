/* 
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//functor/src/java/org/apache/commons/functor/BinaryProcedure.java,v 1.1 2003/01/27 19:33:38 rwaldhoff Exp $
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived 
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.commons.functor;

/**
 * A functor that takes two arguments and has no return value.
 * <p>
 * Implementors are encouraged but not required to make their functors 
 * {@link java.io.Serializable Serializable}.
 * </p>
 * 
 * @since 1.0
 * @version $Revision: 1.1 $ $Date: 2003/01/27 19:33:38 $
 * @author Rodney Waldhoff
 */
public interface BinaryProcedure {
    /** 
     * Execute this procedure. 
     * 
     * @param left the first element of the ordered pair of arguments
     * @param right the second element of the ordered pair of arguments
     */
    void run(Object left, Object right);
    
    /**
     * Returns a human readable description of this functor.
     * Implementators are strongly encouraged but not
     * strictly required to override the default {@link Object}
     * implementation of this method.
     * 
     * @return a human readable description of this functor
     */
    String toString();

    /**
     * Returns a hash code for this functor adhering to the
     * general {@link Object#hashCode Object.hashCode} contract.
     * Implementators are strongly encouraged but not
     * strictly required to override the default {@link Object}
     * implementation of this method.
     * 
     * @see #equals
     * @return a hash code for this functor
     */
    int hashCode();

    /**
     * Indicates whether some other object is &quot;equal to&quot; 
     * this functor.  This method must adhere to
     * general {@link Object#equals Object.equals} contract.
     * Additionally, this method can return
     * <tt>true</tt> <i>only</i> if the specified Object implements
     * the same functor interface and is known to produce the same
     * results and/or side-effects for the same arguments (if any).
     * <p>
     * While implementators are strongly encouraged to override 
     * the default Object implementation of this method,
     * note that the default Object implementation
     * does in fact adhere to the functor <code>equals</code> contract.
     * </p>
     * @param that the object to compare this functor to
     * @see #hashCode
     * @return <code>true</code> iff the given object implements
     *         this functor interface, and is known to produce the same
     *         results and/or side-effects for the same arguments 
     *         (if any).
     */
    boolean equals(Object that);
}