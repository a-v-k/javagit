package edu.nyu.cs.javagit.cli.add;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.cli.IParser;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * Parser class that implements <code>IParser</code> for implementing a parser for &lt;git-add&gt; output.
 */
public class GitAddParser implements IParser {

    private int                lineNum;
    private GitAddResponseImpl response;
    private boolean            error = false;
    private List<Error>        errorList;

    public GitAddParser() {
        lineNum = 0;
        response = new GitAddResponseImpl();
    }

    public void parseLine(String line) {
        if (line == null || line.length() == 0) {
            return;
        }
        lineNum++;
        if (isError(line)) {
            error = true;
            errorList.add(new Error(lineNum, line));
        }
        else if (isComment(line))
            response.setComment(lineNum, line);
        else
            processLine(line);
    }

    private boolean isError(String line) {
        if (line.trim().startsWith("fatal") || line.trim().startsWith("error")) {
            if (errorList == null) {
                errorList = new ArrayList<Error>();
            }
            return true;
        }
        return false;
    }

    private boolean isComment(String line) {
        if (line.startsWith("Nothing specified") || line.contains("nothing added") || line.contains("No changes")
                || line.contains("Maybe you wanted to say") || line.contains("usage")) {
            return true;
        }
        return false;
    }

    /**
     * Lines that start with "add" have the second token as the name of the file added by &lt;git-add&gt.
     * 
     * @param line
     */
    private void processLine(String line) {
        if (line.startsWith("add")) {
            StringTokenizer st = new StringTokenizer(line);

            if (st.nextToken().equals("add") && st.hasMoreTokens()) {
                String extractedFileName = filterFileName(st.nextToken());
                if (extractedFileName != null && extractedFileName.length() > 0) {
                    File file = new File(extractedFileName);
                    response.add(file);
                }
            }
        }
        else {
            processSpaceDelimitedFilePaths(line);
        }
    }

    private void processSpaceDelimitedFilePaths(String line) {
        if (!line.startsWith("\\s+")) {
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                File file = new File(st.nextToken());
                response.add(file);
            }
        }
    }

    public String filterFileName(String token) {
        if (token.length() > 0 && enclosedWithSingleQuotes(token)) {
            int firstQuote = token.indexOf("'");
            int nextQuote = token.indexOf("'", firstQuote + 1);
            if (nextQuote > firstQuote) {
                return token.substring(firstQuote + 1, nextQuote);
            }
        }
        return null;
    }

    public boolean enclosedWithSingleQuotes(String token) {
        if (token.matches("'.*'")) {
            return true;
        }
        return false;
    }

    public void processExitCode(int code) {
    }

    /**
     * Gets a <code>GitAddResponse</code> object containing the info generated by &lt;git-add&gt; command. If there was
     * an error generated while running &lt;git-add&gt; then it throws an exception.
     * 
     * @return GitAddResponse object containing &lt;git-add&gt; response.
     * 
     * @throws JavaGitException If there are any errors generated by &lt;git-add&gt; command.
     */
    public GitAddResponse getResponse() throws JavaGitException {
        if (error) {
            throw new JavaGitException(401000, ExceptionMessageMap.getMessage("401000")
                    + " - git add error message: { " + getError() + " }");
        }
        return response;
    }

    /**
     * Retrieves all the errors in the error list and concatenate them together in one string.
     * 
     * @return
     */
    private String getError() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < errorList.size(); i++) {
            buf.append("Line " + errorList.get(i).lineNum + ". " + errorList.get(i).getErrorString());
            if (i < errorList.size() - 1) {
                buf.append(" ");
            }
        }
        return buf.toString();
    }

    /**
     * Class for storing error details from the &lt;git-add&gt; output.
     * 
     */
    private static class Error {

        final int    lineNum;
        final String errorStr;

        Error(int lineNum, String errorStr) {
            this.lineNum = lineNum;
            this.errorStr = errorStr;
        }

        public String getErrorString() {
            return errorStr;
        }
    }
}