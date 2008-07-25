/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.api.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.DotGit;
import edu.nyu.cs.javagit.api.WorkingTree;
import edu.nyu.cs.javagit.api.GitFileSystemObject;
import edu.nyu.cs.javagit.api.GitFileSystemObject.Status;
import edu.nyu.cs.javagit.api.GitFile;

import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;

public class TestGitFileSystem extends TestCase {

  private File repositoryDirectory;
  private WorkingTree workingTree;
  
  @Before
  public void setUp() throws JavaGitException, IOException {
    repositoryDirectory = FileUtilities.createTempDirectory("GitFileSystemTestRepository");
    HelperGitCommands.initRepo(repositoryDirectory);
    workingTree = WorkingTree.getInstance(repositoryDirectory);
  }

  /**
   * creates a single file and runs a series of tests on it
   */
  @Test
  public void testSingleGitFile() throws IOException, JavaGitException {
    //Create a file
    FileUtilities.createFile(repositoryDirectory, "file1", "Some data");

    //check contents
    List<GitFileSystemObject> children = workingTree.getTree();
    assertEquals("Error. Expecting only one file.", 1, children.size());

    GitFileSystemObject currentFile = children.get(0);
    assertEquals("Error. Expecting instance of GitFile.", GitFile.class, currentFile.getClass());

    GitFile gitFile = (GitFile)currentFile;
    assertEquals("Error. Expecting UNTRACKED status for the single file.", Status.UNTRACKED, gitFile.getStatus());
/*    
    gitFile.add();
    assertEquals("Error. Expecting NEW_TO_COMMIT status for the single file.", Status.NEW_TO_COMMIT, gitFile.getStatus());
    
    gitFile.commit("commit message");
    assertEquals("Error. Expecting IN_REPOSITORY status for the single file.", Status.IN_REPOSITORY, gitFile.getStatus());
    
    FileUtilities.modifyFileContents(gitFile.getFile(), "more data");
    assertEquals("Error. Expecting MODIFIED status for the single file.", Status.MODIFIED, gitFile.getStatus());
    
    gitFile.add();
    assertEquals("Error. Expecting MODIFIED_TO_COMMIT status for the single file.", Status.MODIFIED_TO_COMMIT, gitFile.getStatus());
*/
  }
  
  @Test
  /**
   * Adds more file system objects
   */
  public void testGitFileSystem() throws IOException, JavaGitException {
    //Add another file
    FileUtilities.createFile(repositoryDirectory, "file1", "Some data");
    FileUtilities.createFile(repositoryDirectory, "file2", "Some data");
    
    //check contents
    List<GitFileSystemObject> children = workingTree.getTree();
    assertEquals("Error. Expecting 2 files.", 2, children.size());
/*
    //attempt to commit (but without anything on the index)
    workingTree.commit("commit comment");
    for(int i=0; i<children.size(); ++i) {
      GitFile gitFile = (GitFile)children.get(i);
      assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile.getStatus());
    }

    //commit everything
    workingTree.commitAll("commit comment");
    for(int i=0; i<children.size(); ++i) {
      GitFile gitFile = (GitFile)children.get(i);
      assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, gitFile.getStatus());
    }
    */
  }
  
  @After
  public void tearDown() throws Exception {
    if ( repositoryDirectory.exists() ) {
      FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
    }
  }
}
