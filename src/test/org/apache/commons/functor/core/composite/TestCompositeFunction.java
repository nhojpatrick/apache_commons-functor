/* 
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//functor/src/test/org/apache/commons/functor/core/composite/Attic/TestCompositeFunction.java,v 1.1 2003/01/27 19:33:43 rwaldhoff Exp $
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
package org.apache.commons.functor.core.composite;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryFunction;
import org.apache.commons.functor.core.ConstantFunction;
import org.apache.commons.functor.core.IdentityFunction;

/**
 * @version $Revision: 1.1 $ $Date: 2003/01/27 19:33:43 $
 * @author Rodney Waldhoff
 */
public class TestCompositeFunction extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestCompositeFunction(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestCompositeFunction.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new CompositeFunction(new IdentityFunction(),new ConstantFunction(new Integer(3)));
    }

    // Lifecycle
    // ------------------------------------------------------------------------

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    // Tests
    // ------------------------------------------------------------------------
    
    public void testEvaluate() throws Exception {
        // empty composite acts like identity function
        assertEquals("xyzzy",(new CompositeFunction()).evaluate("xyzzy"));
        assertNull(null,(new CompositeFunction()).evaluate(null));

        assertEquals(new Integer(4),(new CompositeFunction(new ConstantFunction(new Integer(4)))).evaluate(null));
        
        assertEquals(new Integer(4),(new CompositeFunction(new ConstantFunction(new Integer(4)),new ConstantFunction(new Integer(3)))).evaluate("xyzzy"));
        assertEquals(new Integer(3),(new CompositeFunction(new ConstantFunction(new Integer(3)),new ConstantFunction(new Integer(4)))).evaluate("xyzzy"));
    }
    
    public void testOf() throws Exception {
        CompositeFunction f = new CompositeFunction();
        assertNull(f.evaluate(null));
        for(int i=0;i<10;i++) {
            f.of(new UnaryFunction() {
                    public Object evaluate(Object obj) {
                        if(obj instanceof Integer) {
                            return new Integer((((Integer)obj).intValue())+1);
                        } else { 
                            return new Integer(1);
                        }
                    }
                });
            assertEquals(new Integer(i+1),f.evaluate(null));
        }
    }
    
    public void testEquals() throws Exception {
        CompositeFunction f = new CompositeFunction();
        assertEquals(f,f);
        CompositeFunction g = new CompositeFunction();
        assertObjectsAreEqual(f,g);

        for(int i=0;i<3;i++) {
            f.of(new ConstantFunction("x"));
            assertObjectsAreNotEqual(f,g);
            g.of(new ConstantFunction("x"));
            assertObjectsAreEqual(f,g);
            f.of(new CompositeFunction(new ConstantFunction("y"),new ConstantFunction("z")));
            assertObjectsAreNotEqual(f,g);            
            g.of(new CompositeFunction(new ConstantFunction("y"),new ConstantFunction("z")));
            assertObjectsAreEqual(f,g);            
        }
                
        assertObjectsAreNotEqual(f,new ConstantFunction("y"));
    }

}