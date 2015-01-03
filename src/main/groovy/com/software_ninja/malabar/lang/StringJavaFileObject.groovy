
package com.software_ninja.malabar.lang;




/*
 * Copyright 2005-2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.CharBuffer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject.Kind;
import java.nio.CharBuffer;
import javax.tools.JavaFileObject;

/**
 * Provides simple implementations for most methods in JavaFileObject.
 * This class is designed to be subclassed and used as a basis for
 * JavaFileObject implementations.  Subclasses can override the
 * implementation and specification of any method of this class as
 * long as the general contract of JavaFileObject is obeyed.
 *
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */



public class StringJavaFileObject implements JavaFileObject {
  /**
   * A URI for this file object.
   */
  protected final URI uri;


  final String code;
  final String name = "hamster";
  /**
   * The kind of this file object.
   */
  protected final Kind kind;

  /**
   * Construct a SimpleJavaFileObject of the given kind and with the
   * given URI.
   *va
   * @param uri  the URI for this file object
   * @param kind the kind of this file object
   */
  protected StringJavaFileObject(String code) {
    // null checks
    code.getClass();
    uri = URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension);
      //   if (f.toURI().getPath() == null)
      // hrow new IllegalArgumentException("URI must have a path: " + uri);
    this.kind = Kind.SOURCE;
    this.code = code;
  }

  public URI toUri() {
    return uri;
  }
  
  public String getName() {
    return toUri().getPath();
  }
  @Override
  boolean isNameCompatible(String simpleName,
			   JavaFileObject.Kind kind) {
    return true;
  }

  @Override
  public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
    return code;
  }

  /**
   * This implementation always throws {@linkplain
   * UnsupportedOperationException}.  Subclasses can change this
   * behavior as long as the contract of {@link FileObject} is
   * obeyed.
   */
  public InputStream openInputStream() throws IOException {
    throw new UnsupportedOperationException();
  }

  /**
   * This implementation always throws {@linkplain
   * UnsupportedOperationException}.  Subclasses can change this
   * behavior as long as the contract of {@link FileObject} is
   * obeyed.
   */
  public OutputStream openOutputStream() throws IOException {
    throw new UnsupportedOperationException();
  }

  /**
   * Wraps the result of {@linkplain #getCharContent} in a Reader.
   * Subclasses can change this behavior as long as the contract of
   * {@link FileObject} is obeyed.
   *
   * @param  ignoreEncodingErrors {@inheritDoc}
   * @return a Reader wrapping the result of getCharContent
   * @throws IllegalStateException {@inheritDoc}
   * @throws UnsupportedOperationException {@inheritDoc}
   * @throws IOException {@inheritDoc}
   */
  public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
    CharSequence charContent = getCharContent(ignoreEncodingErrors);
    if (charContent == null)
      throw new UnsupportedOperationException();
    if (charContent instanceof CharBuffer) {
      CharBuffer buffer = (CharBuffer)charContent;
      if (buffer.hasArray())
	return new CharArrayReader(buffer.array());
    }
    return new StringReader(charContent.toString());
  }


  /**
   * Wraps the result of openOutputStream in a Writer.  Subclasses
   * can change this behavior as long as the contract of {@link
   * FileObject} is obeyed.
   *
   * @return a Writer wrapping the result of openOutputStream
   * @throws IllegalStateException {@inheritDoc}
   * @throws UnsupportedOperationException {@inheritDoc}
   * @throws IOException {@inheritDoc}
   */
  public Writer openWriter() throws IOException {
    return new OutputStreamWriter(openOutputStream());
  }

  /**
   * This implementation returns {@code 0L}.  Subclasses can change
   * this behavior as long as the contract of {@link FileObject} is
   * obeyed.
   *
   * @return {@code 0L}
   */
  public long getLastModified() {
    return 0L;
  }

  /**
   * This implementation does nothing.  Subclasses can change this
   * behavior as long as the contract of {@link FileObject} is
   * obeyed.
   *
   * @return {@code false}
   */
  public boolean delete() {
    return false;
  }

  /**
   * @return {@code this.kind}
   */
  public Kind getKind() {
    return kind;
  }


  /**
   * This implementation returns {@code null}.  Subclasses can
   * change this behavior as long as the contract of
   * {@link JavaFileObject} is obeyed.
   */
  public NestingKind getNestingKind() { return null; }

  /**
   * This implementation returns {@code null}.  Subclasses can
   * change this behavior as long as the contract of
   * {@link JavaFileObject} is obeyed.
   */
  public Modifier getAccessLevel()  { return null; }

  @Override
  public String toString() {
    return getClass().getName() + "[" + toUri() + "]";
  }
  
}




// import javax.tools.SimpleJavaFileObject;
// import java.nio.CharBuffer;


// public class FileJavaFileObject extends SimpleJavaFileObject {

//   private final File file;

//   public static FileJavaFileObject getInstance(File f) {
//     return new FileJavaFileObject(f.toURI());
//   }

//   public FileJavaFileObject(URI uri, File f) {
//     super(uri, Kind.SOURCE);
//     this.file = f;
//   }
  
//   @Override
//   public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
//     CharBuffer rtnval = CharBuffer.allocate(file.length());
//     FileReader fr =  new FileReader(file);
//     fr.read(rtnval);
//     rtnval;
//   }
// }
