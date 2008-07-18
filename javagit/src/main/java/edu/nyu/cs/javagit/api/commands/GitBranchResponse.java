package edu.nyu.cs.javagit.api.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.nyu.cs.javagit.api.Ref;

/**
 * A response data object for the git-branch command.
 */
abstract public class GitBranchResponse implements CommandResponse {
  /**
   * An enumeration of the types of response. In normal case a list of branches, otherwise some
   * message such as "Deleted branch".
   */
  public static enum responseType {
    BRANCH_LIST, MESSAGE, EMPTY
  }
  
  // The list of branches in the response of git-branch.
  protected List<Ref> branchList;
  
  protected List<BranchRecord> listOfBranchRecord;

  // String Buffer to store the message after execution of git-branch command.
  protected StringBuffer messages = new StringBuffer();

  // Variable to store the current branch.
  protected Ref currentBranch;

  // The type of this response.
  protected responseType responseType;

  /**
   * Constructor.
   */
  public GitBranchResponse() {
    branchList = new ArrayList<Ref>();
    listOfBranchRecord = new ArrayList<BranchRecord>();
  }

  /**
   * Get an <code>Iterator</code> with which to iterate over the branch list.
   * 
   * @return An <code>Iterator</code> with which to iterate over the branch list.
   */
  public Iterator<Ref> getBranchListIterator() {
    return (new ArrayList<Ref>(branchList).iterator());
  }

  /**
   * Get an <code>Iterator</code> with which to iterate over the branch record list.
   * 
   * @return An <code>Iterator</code> with which to iterate over the branch record list.
   */
  public Iterator<BranchRecord> getListOfBranchRecordIterator() {
    return (new ArrayList<BranchRecord>(listOfBranchRecord).iterator());
  }

  /**
   * Gets the type of the response. Branch list, message or empty.
   * 
   * @return The responseType.
   */
  public responseType getResponseType() {
    return responseType;
  }

  /**
   * Gets a message about the git-branch operation that was run.
   *
   * @return A message about the git-branch operation that was run. 
   */
  public String getMessages() {
    return messages.toString();
  }

  /**
   * Gets the current branch from the list of branches displayed by git-branch operation.
   * 
   * @return The current branch from the list of branches displayed by git-branch operation.
   */
  public Ref getcurrentBranch() {
    return currentBranch;
  }

  public static class BranchRecord {
    private Ref branch;
    
    // The SHA Refs of a branch in the response of git-branch with -v option.
    private Ref sha1;
    
    // String Buffer to store the comment after execution of git-branch command with -v option.
    private String comment;
    
    // Variable to store the current branch.
    private boolean isCurrentBranch;
    
    public BranchRecord(Ref branch, Ref sha1, String comment, boolean isCurrentBranch) {
      this.branch = branch;
      this.sha1 = sha1;
      this.comment = comment;
      this.isCurrentBranch = isCurrentBranch;
    }

    /**
     * Gets the branch from the record.
     * 
     * @return The branch from the record.
     */
    public Ref getBranch() {
      return branch;
    }

    /**
     * Gets the SHA1 from the record.
     * 
     * @return The SHA1 from the record.
     */
    public Ref getSha1() {
      return sha1;
    }

    /**
     * Gets the comment of the last commit on a branch or the last commit on the branch it has
     * originated from. Displayed when git-branch is run with -v option.
     * 
     * @return The comment of the recent commit on a branch.
     */
    public String getComment() {
      return comment;
    }

    /**
     * Gets the current branch from the list of branches displayed by git-branch operation.
     * 
     * @return The current branch from the list of branches displayed by git-branch operation.
     */
    public boolean isCurrentBranch() {
      return isCurrentBranch;
    }
  }
}
