/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//functor/src/test/org/apache/commons/functor/core/composite/TestWhileDoProcedure.java,v 1.1 2003/11/11 23:36:00 rwaldhoff Exp $
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
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.adapter.BoundPredicate;
import org.apache.commons.functor.core.ConstantPredicate;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.functor.core.collection.IsEmpty;

import java.util.LinkedList;
import java.util.List;

/**
 * @version $Revision: 1.1 $ $Date: 2003/11/11 23:36:00 $
 * @author Herve Quiroz
 */
public class TestWhileDoProcedure extends BaseFunctorTest {

    // Conventional
    // ------------------------------------------------------------------------

    public TestWhileDoProcedure(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestWhileDoProcedure.class);
    }

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    protected Object makeFunctor() {
        return new WhileDoProcedure(new ConstantPredicate(false), new NoOp());
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
    public class ListRemoveFirstProcedure implements Procedure {
        protected List list;


        public ListRemoveFirstProcedure(List list) {
            this.list=list;
        }


        public void run() {
            list.remove(0);
        }
    }


    private List getList() {
        List list=new LinkedList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        return list;
    }


    public void testLoopWithAction() throws Exception {
        List list=getList();

        Procedure action=new ListRemoveFirstProcedure(list);
        Predicate condition=new Not(new BoundPredicate(new IsEmpty(), list));
        Procedure procedure=new WhileDoProcedure(condition, action);

        assertTrue("The condition should be true before running the loop", condition.test());
        assertFalse("The list should not be empty then", list.isEmpty());
        procedure.run();
        assertFalse("The condition should be false after running the loop", condition.test());
        assertTrue("The list should be empty then", list.isEmpty());

        list=getList();
        action=new ListRemoveFirstProcedure(list);
        condition=new Predicate() {
                      private int count=2;

                      public boolean test() {
                          return count-- > 0;
                      }
                  };
        procedure=new WhileDoProcedure(condition, action);
        procedure.run();
        assertFalse("The list should not contain \"a\" anymore", list.contains("a"));
        assertFalse("The list should not contain \"b\" anymore", list.contains("b"));
        assertTrue("The list should still contain \"c\"", list.contains("c"));
        assertTrue("The list should still contain \"d\"", list.contains("d"));
    }

    public void testLoopForNothing() {
        List list=getList();
        Procedure action=new ListRemoveFirstProcedure(list);
        Procedure procedure=new WhileDoProcedure(new ConstantPredicate(false), action);
        assertTrue("The list should contain 4 elements before runnning the loop", list.size()==4);
        procedure.run();
        assertTrue("The list should contain 4 elements after runnning the loop", list.size()==4);
    }
}
