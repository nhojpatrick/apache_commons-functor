/* 
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//functor/src/test/org/apache/commons/functor/example/lines/TestLines.java,v 1.1 2003/11/25 23:13:38 rwaldhoff Exp $
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
package org.apache.commons.functor.example.lines;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.functor.adapter.ProcedureUnaryProcedure;
import org.apache.commons.functor.core.Offset;
import org.apache.commons.functor.core.collection.Size;


/**
 * @version $Revision: 1.1 $ $Date: 2003/11/25 23:13:38 $
 * @author Rodney Waldhoff
 */
public class TestLines extends TestCase {

    public TestLines(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(TestLines.class);
    }
    
    private Reader reader = null;
    
    public void setUp() throws Exception {
        super.setUp();
        reader = new StringReader(DOCUMENT);        
    }
    
    public void tearDown() throws Exception {
        super.tearDown();
        reader = null;
    }
    
    public void testCountCharacters() throws Exception {
        Object result = Lines
            .from(reader)
                .apply(Size.instance())
                    .inject(new Integer(0),Sum.instance());
                    
        assertEquals("Expected 990 characters",new Integer(990),result);
    }                    

    public void testCountWords() throws Exception {
        Object result = Lines
            .from(reader)
                .apply(WordCount.instance())
                    .inject(new Integer(0),Sum.instance());
                    
        assertEquals("Expected 157 words",new Integer(157),result);
    }

    public void testCountLines() throws Exception {
        Count count = new Count();
        Lines
            .from(reader)
                .foreach(ProcedureUnaryProcedure.adapt(count));
                                   
        assertEquals("Expected 16 lines",16,count.getCount());
    }

    public void testCountWordsExcludingComments() throws Exception {
        Object result = Lines
            .from(reader)
                .reject(new StartsWith("#"))
                    .apply(WordCount.instance())
                        .inject(new Integer(0),Sum.instance());
                    
        assertEquals("Expected 90 words",new Integer(90),result);
    }

    public void testCountCommentLines() throws Exception {
        Count count = new Count();
        Lines
            .from(reader)
                .select(new StartsWith("#"))
                    .foreach(ProcedureUnaryProcedure.adapt(count));
                                   
        assertEquals("Expected 6 lines",6,count.getCount());
    }

    public void testFindMatchingLines() throws Exception {
        Collection matches = 
            Lines
                .from(reader)
                    .select(new Contains("lo"))
                        .toCollection();
        assertEquals("Expected 5 lines",5,matches.size());
    }

    public void testFindMatchingFromTail() throws Exception {
        Collection matches = 
            Lines
                .from(reader)
                    .select(new Offset(8))
                        .select(new Contains("lo"))
                            .toCollection();
        assertEquals("Expected 2 lines",2,matches.size());
    }
    
    
    private static final String DOCUMENT = 
        "# Lorem ipsum dolor sit amet, consectetuer adipiscing elit. \n" +
        "# Aliquam erat volutpat. Donec nec eros. Etiam eget tortor eu \n" +
        "tortor rutrum cursus. Pellentesque ornare pretium risus. Nulla \n" +
        "libero pede, blandit nec, rutrum ut, sodales eu, enim. Duis leo. \n" +
        "Nunc non est. Nunc consequat lobortis nisl. Vivamus et tortor in \n" +
        "# nunc euismod elementum. Ut ut dui. Morbi semper, pede eu cursus \n" +
        "# tristique, diam nunc luctus nibh, id tempor justo metus eget lorem.\n" +
        "\n" +
        "Quisque pharetra hendrerit odio. Etiam consequat ante et dui. Etiam \n" +
        "accumsan elit ac augue. Mauris porta pulvinar tellus. Nulla ac enim ac \n"+
        "augue ornare pharetra. Nunc dignissim eros et nibh. Sed justo dolor, \n" +
        "# ullamcorper non, posuere eget, tempus non, ipsum. Praesent at velit. \n" +
        "# Mauris tempor nisl sed tortor. Vestibulum ante ipsum primis in faucibus \n" +
        "orci luctus et ultrices posuere cubilia Curae; Integer mollis malesuada \n" +
        "dolor. Vestibulum cursus, mi in dictum blandit, eros enim convallis wisi, \n" +
        "id ullamcorper metus tortor interdum dui.\n";
}
